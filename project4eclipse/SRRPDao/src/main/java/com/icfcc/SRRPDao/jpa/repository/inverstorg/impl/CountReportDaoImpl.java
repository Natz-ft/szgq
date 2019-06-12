package com.icfcc.SRRPDao.jpa.repository.inverstorg.impl;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.ReportBeanEnterpriseResult;
import com.icfcc.SRRPDao.jpa.entity.managedept.FinacingStatisticsResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.SRRPDao.pojo.CountReportAreaBean;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component 
public class CountReportDaoImpl extends BaseNativeQueryDao {

	private String unionSQL = "select da.rank,da.cou_amount,da.enterprise_id ,da.name,IFNULL(db.cou, 0) cou from ( SELECT enterprise_id,name,amount as cou_amount,@rownum \\:= @rownum + 1 AS rank FROM ( SELECT rcb.enterprise_id,rcb. NAME,IFNULL(ROUND(SUM(rfe.amount),2), 0) AS amount FROM rp_company_base rcb,(SELECT rfe.info_id,rfe.exchange_ratio + IFNULL(rif.amount, 0) AS amount,rfe.enterprise_id,rfe.event_id,rfe.investorg_id FROM rp_finacing_event rfe LEFT JOIN (SELECT sum(rif.amount) AS amount, rif.info_id, rif.event_id FROM rp_investor_follow rif GROUP BY rif.info_id) rif ON rfe.event_id = rif.event_id WHERE rfe.`status` > '32' AND rfe.`status` < '99' ";
	private String finacingSql = "SELECT IFNULL(SUM(amountCNYmax),0) as demandamount,IFNULL(COUNT(*),0) as demandnumber,IFNULL(SUM(amountCNYmin),0) as finacingamount,IFNULL(COUNT(*),0) as solutionsNumber, DATE_FORMAT(t.operdate,:operdate) as finacingtime FROM rp_finacing_demand t,rp_company_base c WHERE t.enterprise_id =c.enterprise_id and t.status in('01','02','03') ";
	private String finacingAmountSql = "SELECT finacingTime,IFNULL(ROUND(SUM(amount),2), 0) FROM (SELECT rfe.info_id, rfe.exchange_ratio + IFNULL(rif.amount, 0) AS amount, rfe.enterprise_id, rfe.event_id, rfe.investorg_id, DATE_FORMAT(operdate,:operdate) AS finacingTime FROM rp_finacing_event rfe LEFT JOIN ( SELECT sum(rif.amount) AS amount, rif.event_id FROM rp_investor_follow rif GROUP BY rif.event_id ) rif ON rfe.event_id = rif.event_id WHERE rfe.`status` > '32' AND rfe.`status` < '99' ";
	private String demandNumberSql = "SELECT DATE_FORMAT(operdate,:operdate) as finacingTime, IFNULL(COUNT(*),0) FROM rp_finacing_demand t,rp_company_base c WHERE t.enterprise_id =c.enterprise_id  and t.status in('01','02','03') ";
	private String solutionsNumberSql = "SELECT  finacingTime, COUNT( t.info_id)  FROM (SELECT rfe.info_id, rfe.exchange_ratio + IFNULL(rif.amount, 0) AS amount, rfe.enterprise_id, rfe.event_id, rfe.investorg_id, DATE_FORMAT(operdate,:operdate) AS finacingTime FROM rp_finacing_event rfe LEFT JOIN ( SELECT sum(rif.amount) AS amount, rif.event_id FROM rp_investor_follow rif GROUP BY rif.event_id ) rif ON rfe.event_id = rif.event_id WHERE rfe.`status` > '32' AND rfe.`status` < '99'  ";
	private String companyNumberSql = "SELECT IFNULL(COUNT(*),0)  FROM rp_company_base c,system_user u WHERE c.enterprise_id = u.org_id  AND u.user_type IN ('02', '0201')  ";
	private String investorNumberSql = "SELECT IFNULL(COUNT(*),0)   FROM rp_investor  where 1=1 ";
	private String userNumberSql = "select count(su.USER_NAME) as amount from system_user su,rp_investor ri  where  su.ORG_ID = ri.investor_id  ";

	@SuppressWarnings("unchecked")
	public CountReportAreaBean generateReportBeanTotal(String mounth) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		CountReportAreaBean bean = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			String sql = "SELECT   SUM(demand_amount) demand_amount,  SUM(loan_amount) loan_amount,  SUM(enterprise_count) enterprise_count,  SUM(org_count) org_count FROM  rp_report_total t WHERE time_id = :mounth GROUP BY time_id";
			Query q = entityManager.createNativeQuery(sql.toString());
			q.setParameter("mounth", mounth);
			List<Object[]> tres = (List<Object[]>) q.getResultList();
			bean = new CountReportAreaBean();
			bean.setPost_finance(new BigDecimal(0));
			bean.setSolve_finance(new BigDecimal(0));
			bean.setCompany_count(new BigDecimal(0));
			bean.setOrg_count(new BigDecimal(0));
			for (Object[] o : tres) {
				bean.setPost_finance((BigDecimal) o[0]);
				bean.setSolve_finance((BigDecimal) o[1]);
				bean.setCompany_count((BigDecimal) o[2]);
				bean.setOrg_count((BigDecimal) o[3]);
			}
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return bean;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> sumAmountByArea(String mounth) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<Map> list = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			String sql = "SELECT  IFNULL(SUM(enterprise_count),0) enterprise_count, diccode AS AREA FROM platform_dic_detail d LEFT JOIN rp_report_total t ON t.area=d.DICCODE AND t.time_id = :mounth WHERE d.dictype='14' GROUP BY d.diccode";
			Query q = entityManager.createNativeQuery(sql.toString());
			q.setParameter("mounth", mounth);
			List<Object[]> tres = (List<Object[]>) q.getResultList();
			list = new ArrayList<Map>();
			Map<String, String> map = new HashMap<String, String>();
			for (Object[] o : tres) {
				map.put("area_" + (String) o[1], String.valueOf(o[0]));
			}
			list.add(map);
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ReportBeanEnterpriseResult> getCompaniesList(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<ReportBeanEnterpriseResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())) {
				whereCase.append(" and date_format(rfe.operdate, '%Y-%m') >= :beginTimeStr");

			}
			if (StringUtils.isNotEmpty(queryCondition.getEndTimeStr())) {
				whereCase.append(" and date_format(rfe.operdate, '%Y-%m') <= :endTimeStr");

			}
			if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
				 whereCase.append(") rfe LEFT JOIN rp_finacing_demand rfd ON rfe.info_id = rfd.info_id WHERE rcb.enterprise_id = rfe.enterprise_id and rcb.rearea = :area ");
			}else{
				whereCase.append(") rfe LEFT JOIN rp_finacing_demand rfd ON rfe.info_id = rfd.info_id WHERE rcb.enterprise_id = rfe.enterprise_id ");

			}
			if (StringUtils.isNotEmpty(queryCondition.getFinacingTurn())) {
				whereCase.append(" and rfd.finacing_turn = :finacingTurn ");
			}
			whereCase.append(
					//LIMIT 0,10
					" GROUP BY rcb.enterprise_id,rcb.NAME ORDER BY SUM(rfe.amount) DESC ) AS report_amount,(SELECT @rownum \\:= 0) AS report_rank) da LEFT JOIN (SELECT count(c.enterprise_id) AS cou,c.enterprise_id FROM rp_report_company c WHERE 1 = 1 GROUP BY c.enterprise_id,c.`name`) db ON da.enterprise_id = db.enterprise_id ORDER BY rank");
			Query query = entityManager.createNativeQuery(unionSQL + whereCase.toString(),
					ReportBeanEnterpriseResult.class);
			if (StringUtils.isNotEmpty(queryCondition.getFinacingTurn())) {
				query.setParameter("finacingTurn", queryCondition.getFinacingTurn());
			}
			if (StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())) {
				query.setParameter("beginTimeStr", queryCondition.getBeginTimeStr());
			}
			if (StringUtils.isNotEmpty(queryCondition.getEndTimeStr())) {
				query.setParameter("endTimeStr", queryCondition.getEndTimeStr());
			}
			if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {
				query.setParameter("area", queryCondition.getArea());
			}
			res = (List<ReportBeanEnterpriseResult>) query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	/**
	 * 融资统计列表
	 * 
	 * @param queryCondition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FinacingStatisticsResult> getFinacingStatistics(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<FinacingStatisticsResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer(finacingSql);
			if ("monthId".equals(queryCondition.getStatisticalCycleId())) {
				if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
					whereCase.append("  AND date_format(t.operdate,'%Y-%m') >= :beginTimeStr ");
				}
				if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
					whereCase.append(" AND date_format(t.operdate,'%Y-%m') <= :endTimeStr ");
				}
				
			} 
			if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
				whereCase.append(" and c.rearea = :area ");
			}
			whereCase.append(" GROUP BY finacingtime order by finacingTime asc ");
			Query query = entityManager.createNativeQuery(whereCase.toString(), FinacingStatisticsResult.class);
			if ("monthId".equals(queryCondition.getStatisticalCycleId())) {
				query.setParameter("operdate", "%Y-%m");
				if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
					query.setParameter("beginTimeStr",queryCondition.getBeginTimeStr());	
				}
				if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
					query.setParameter("endTimeStr",queryCondition.getEndTimeStr());
				}
			} else {
				query.setParameter("operdate", "%Y");
			} 
			if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
				query.setParameter("area", queryCondition.getArea());
			}
			res = (List<FinacingStatisticsResult>) query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	/**
	 * 获取平台相应日期的平台企业数 机构数 用户数
	 * 
	 * @param queryCondition
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public Map<String, String> getCount(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Map<String, String> res = new HashMap<String, String>();
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = null;
			if ("monthId".equals(queryCondition.getStatisticalCycleId())) {
				if ("companyNumberSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(companyNumberSql);
					if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
						whereCase.append("  AND date_format(c.redate,'%Y-%m') >= :beginTimeStr ");
					}
					if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
						whereCase.append(" AND date_format(c.redate,'%Y-%m') <= :endTimeStr ");
					}
					if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
						whereCase.append(" and c.rearea = :area ");
					}
				} else if ("investorNumberSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(investorNumberSql);
					if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
						whereCase.append("  AND date_format(create_time,'%Y-%m') >= :beginTimeStr ");
					}
					if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
						whereCase.append(" AND date_format(create_time,'%Y-%m') <= :endTimeStr ");
					}
				} else if ("userNumberSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(userNumberSql);
					if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
						whereCase.append("  AND date_format(ri.create_time,'%Y-%m') >= :beginTimeStr ");
					}
					if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
						whereCase.append(" AND date_format(ri.create_time,'%Y-%m') <= :endTimeStr ");
					}
				}
			} else {
				if ("companyNumberSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(companyNumberSql);
					if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
						whereCase.append(" and rearea = :area ");
					}
				} else if ("investorNumberSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(investorNumberSql);
				} else if ("userNumberSql".equals(queryCondition.getSqlFlag())) {
						whereCase = new StringBuffer(userNumberSql);
				}
				
			}
			Query query = entityManager.createNativeQuery(whereCase.toString());
			if ("monthId".equals(queryCondition.getStatisticalCycleId())) {
				if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
					query.setParameter("beginTimeStr",queryCondition.getBeginTimeStr());	
				}
				if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
					query.setParameter("endTimeStr",queryCondition.getEndTimeStr());
				}
			} 
			if ("companyNumberSql".equals(queryCondition.getSqlFlag())) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {
					query.setParameter("area", queryCondition.getArea());
				}
			}
			
			res = new HashMap<String, String>();
			List<Object> statisticsCount = query.getResultList();
			res.put("statisticsCount", String.valueOf(statisticsCount.get(0)));
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	/**
	 * 获取相应日期的发布需求个数 融资金额 解决融资金额个数
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Map<String, String> getMap(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Map<String, String> res = new HashMap<String, String>();
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = null;
			if ("monthId".equals(queryCondition.getStatisticalCycleId())) {
				if ("finacingAmountSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(finacingAmountSql);
					if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
						whereCase.append(" AND date_format(rfe.operdate,'%Y-%m') >= :beginTimeStr ");
					}
					if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
						whereCase.append(" AND date_format(rfe.operdate,'%Y-%m') <= :endTimeStr  ");
					}
					if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
						whereCase.append(" ) t, rp_company_base c WHERE t.enterprise_id = c.enterprise_id  and c.rearea = :area GROUP BY finacingTime ");

					}else{
						whereCase.append(" ) t, rp_company_base c WHERE t.enterprise_id = c.enterprise_id GROUP BY finacingTime ");
					}

				} else if ("demandNumberSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(demandNumberSql);
					if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
						whereCase.append("  AND date_format(t.operdate,'%Y-%m') >= :beginTimeStr ");
					}
					if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
						whereCase.append(" AND date_format(t.operdate,'%Y-%m') <= :endTimeStr ");
					}
					whereCase.append(" GROUP BY finacingTime ");
				} else if ("solutionsNumberSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(solutionsNumberSql);
					if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
						whereCase.append(" AND date_format(rfe.operdate,'%Y-%m') >= :beginTimeStr ");
					}
					if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
						whereCase.append(" AND date_format(rfe.operdate,'%Y-%m') <= :endTimeStr  ");
					}
					if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
						whereCase.append(" ) t, rp_company_base c WHERE t.enterprise_id = c.enterprise_id  and c.rearea = :area GROUP BY finacingTime ");

					}else{
						whereCase.append(" ) t, rp_company_base c WHERE t.enterprise_id = c.enterprise_id GROUP BY finacingTime ");
					}
				}
			} else {
				if ("finacingAmountSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(finacingAmountSql);
					if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
						whereCase.append(" ) t, rp_company_base c WHERE t.enterprise_id = c.enterprise_id  and c.rearea = :area GROUP BY finacingTime ");

					}else{
						whereCase.append(" ) t, rp_company_base c WHERE t.enterprise_id = c.enterprise_id GROUP BY finacingTime ");
					}

				} else if("demandNumberSql".equals(queryCondition.getSqlFlag())){
					whereCase = new StringBuffer(demandNumberSql);
					whereCase.append(" GROUP BY finacingTime ");
				} else if ("solutionsNumberSql".equals(queryCondition.getSqlFlag())) {
					whereCase = new StringBuffer(solutionsNumberSql);
					if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
						whereCase.append(" ) t, rp_company_base c WHERE t.enterprise_id = c.enterprise_id  and c.rearea = :area GROUP BY finacingTime ");

					}else{
						whereCase.append(" ) t, rp_company_base c WHERE t.enterprise_id = c.enterprise_id GROUP BY finacingTime ");
					}
				}
			}
			Query query = entityManager.createNativeQuery(whereCase.toString());
			
			if ("monthId".equals(queryCondition.getStatisticalCycleId())) {
				query.setParameter("operdate", "%Y-%m");
				if(StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())){
					query.setParameter("beginTimeStr",queryCondition.getBeginTimeStr());	
				}
				if(StringUtils.isNotEmpty(queryCondition.getEndTimeStr())){
					query.setParameter("endTimeStr",queryCondition.getEndTimeStr());
				}
			} else {
				query.setParameter("operdate", "%Y");
			} 
			if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {
				query.setParameter("area", queryCondition.getArea());
			}
			res = new HashMap<String, String>();
			@SuppressWarnings("unchecked")
			List<Object[]> tres = (List<Object[]>) query.getResultList();
			for (Object[] obj : tres) {
				res.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}
}

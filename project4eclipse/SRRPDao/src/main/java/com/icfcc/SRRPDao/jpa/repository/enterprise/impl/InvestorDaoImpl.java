package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import io.netty.util.internal.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.managedept.InvestorManageResutList;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 
 * @author LiWCh
 *
 */
@Component
public class InvestorDaoImpl extends BaseNativeQueryDao {
//SELS刷考
	private String unionSQL = "select * from rp_investor e where  e.stop_flag= :stopFlag and(e.audit_status='4' or e.audit_status='5' or e.audit_status= '6' or e.audit_status= '22' )";
	private String manageInvestSQL = "select e.*,c.score as score,(case e.audit_status WHEN '1' THEN '1' WHEN '2' THEN '2' WHEN '4' THEN '3' WHEN '5' THEN '4' WHEN '22' THEN '3' WHEN '3' THEN '5' WHEN '6' THEN '6' WHEN '23' THEN '6'  end ) status from rp_investor e left join (select o.* from rp_company_creditscores o,(SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) a where o.cop_id = a.cop_id and a.cop_id = (select b.cop_id from (SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) b where a.creditcode = b.creditcode ORDER BY b.mt desc limit 1)) c on e.certno =c.creditcode where  1=1 and e.audit_status != 0 ";
	private String manageInvestSQL_ordeby = "ORDER BY status ASC,create_time DESC";

	@SuppressWarnings("unchecked")
	public List<InvestorManageResutList> getInvestorsForCharge(
			String nameOrCode, String status, String auditStatus, int page,
			int size, String userType) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<InvestorManageResutList> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (userType.equals(SRRPConstant.USER_TYPE_05)) {
				whereCase
						.append(" and e.audit_status in('2','4','5','6','22','23') ");
			}
			// if(userType.equals(SRRPConstant.USER_TYPE_04)||userType.equals(SRRPConstant.USER_TYPE_06)){
			// whereCase.append(" and e.audit_status !='22' and e.audit_status !='23'  ");
			// }
			if (!StringUtils.isEmpty(nameOrCode)) {
				// 2018年1月8日 15:47:50 LIWCH 给sql传参数的时候，参数不一致没找到参数
				whereCase.append(" and (e.name like :name ");
				whereCase.append(" or e.certno = :code )");
			}
			if (!StringUtils.isEmpty(status)) {
				whereCase.append(" and e.stop_flag = :status ");
			}
			if (!StringUtils.isEmpty(auditStatus)) {
				if (userType.equals(SRRPConstant.USER_TYPE_05)) {
					if ("21".equals(auditStatus)) {// 如果查询条件为21 则查询审核通过的未进行激活的
						whereCase.append(" and e.audit_status = '2' ");
					} else if ("22".equals(auditStatus)) {// 如果查询条件为22
															// 则查询已经激活成功的
						whereCase.append(" and (e.audit_status = '22' ");
						whereCase.append(" or e.audit_status = '4' ");
						whereCase.append(" or e.audit_status = '5' ");
						whereCase.append(" or e.audit_status = '6') ");
					} else if ("23".equals(auditStatus)) {// 如果查询条件为22
															// 则查询已经激活不成功的未进行修改的
						whereCase.append(" and e.audit_status = '23' ");

					}
				} else if (userType.equals(SRRPConstant.USER_TYPE_04)) {
					if ("1".equals(auditStatus)) {// 如果查询条件为1 则查询未进行审核
						whereCase.append(" and e.audit_status = '1' ");
					} else if ("2".equals(auditStatus)) {// 如果查询条件为2
															// 则查询注册初审通过未进行激活
						whereCase
								.append(" and (e.audit_status = '2'or e.audit_status = '22') ");
					} else if ("3".equals(auditStatus)) {// 如果查询条件为3 则查询注册初审未通过
						whereCase.append(" and e.audit_status = '3' ");
					} else if ("4".equals(auditStatus)) {// 如果查询条件为4 则查询修改为审核
						whereCase.append(" and e.audit_status = '4' ");
					} else if ("5".equals(auditStatus)) {// 如果查询条件为5 则查询修改审核通过
						whereCase.append(" and e.audit_status = '5' ");
					} else if ("6".equals(auditStatus)) {// 如果查询条件为6 则查询修改审核不通过
						whereCase.append(" and e.audit_status = '6' ");
					}
				} else if (userType.equals(SRRPConstant.USER_TYPE_03)) {
					whereCase.append(" and e.audit_status= :auditStatus ");// 登录用户为现金融办则正常查询
				} else if (userType.equals(SRRPConstant.USER_TYPE_06)) {
					if ("1".equals(auditStatus)) {// 如果查询条件为1 则查询未进行审核
						whereCase.append(" and e.audit_status = '1' ");
					} else if ("2".equals(auditStatus)) {// 如果查询条件为2
															// 则查询注册初审通过未进行激活
						whereCase
								.append(" and (e.audit_status = '2'or e.audit_status = '22') ");
					} else if ("3".equals(auditStatus)) {// 如果查询条件为3 则查询注册初审未通过
						whereCase.append(" and e.audit_status = '3' ");
					} else if ("4".equals(auditStatus)) {// 如果查询条件为4 则查询修改为审核
						whereCase.append(" and e.audit_status = '4' ");
					} else if ("5".equals(auditStatus)) {// 如果查询条件为5 则查询修改审核通过
						whereCase.append(" and e.audit_status = '5' ");
					} else if ("6".equals(auditStatus)) {// 如果查询条件为6 则查询修改审核不通过
						whereCase.append(" and e.audit_status = '6' ");
					}
				}
			}
			whereCase.append(manageInvestSQL_ordeby);
			// 拼接分頁sql
			whereCase.append(" limit ");
			if (page == 1) {
				whereCase.append("0");
			} else {
				whereCase.append((page - 1) * size);
			}
			whereCase.append("," + size);
			Query query = entityManager.createNativeQuery(manageInvestSQL
					+ whereCase.toString(), InvestorManageResutList.class);
			if (!StringUtils.isEmpty(nameOrCode)) {
				query.setParameter("name", "%" + nameOrCode + "%");
				query.setParameter("code", nameOrCode);

			}
			if (!StringUtils.isEmpty(status)) {
				query.setParameter("status", status);
			}
			if (!StringUtils.isEmpty(auditStatus)) {
				if (userType.equals(SRRPConstant.USER_TYPE_03)) {
					query.setParameter("auditStatus", auditStatus);
				}
			}
			res = (List<InvestorManageResutList>) query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	public Object getInvestorsForChargeCount(String nameOrCode, String status,
			String auditStatus, String userType) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();

			whereCase.append(" select count(*) as resultnum from ( ");
			whereCase.append(manageInvestSQL);
			if (userType.equals(SRRPConstant.USER_TYPE_05)) {
				whereCase
						.append(" and e.audit_status in('2','6','4','5','22','23') ");
			}
			if (!StringUtils.isEmpty(nameOrCode)) {
				// 2018年1月8日 15:47:50 LIWCH 给sql传参数的时候，参数不一致没找到参数
				whereCase.append(" and e.name like :name");
				whereCase.append(" or e.certno = :code");
			}
			if (!StringUtils.isEmpty(status)) {
				whereCase.append(" and e.stop_flag = :status");
			}
			if (!StringUtils.isEmpty(auditStatus)) {
				if (userType.equals(SRRPConstant.USER_TYPE_05)) {
					if ("21".equals(auditStatus)) {// 如果查询条件为21 则查询审核通过的未进行激活的
						whereCase.append(" and e.audit_status = '2' ");
					} else if ("22".equals(auditStatus)) {// 如果查询条件为22
															// 则查询已经激活成功的
						whereCase.append(" and (e.audit_status = '22' ");
						whereCase.append(" or e.audit_status = '4' ");
						whereCase.append(" or e.audit_status = '5' ");
						whereCase.append(" or e.audit_status = '6') ");
					} else if ("23".equals(auditStatus)) {// 如果查询条件为22
															// 则查询已经激活不成功的未进行修改的
						whereCase.append(" and e.audit_status = '23' ");
					}
				} else if (userType.equals(SRRPConstant.USER_TYPE_04)) {
					if ("1".equals(auditStatus)) {// 如果查询条件为1 则查询未进行审核
						whereCase.append(" and e.audit_status = '1' ");
					} else if ("2".equals(auditStatus)) {// 如果查询条件为2
															// 则查询注册初审通过未进行激活
						whereCase
								.append(" and ( e.audit_status = '2' or e.audit_status = '22')");
					} else if ("3".equals(auditStatus)) {// 如果查询条件为3 则查询注册初审未通过
						whereCase.append(" and e.audit_status = '3' ");
					} else if ("4".equals(auditStatus)) {// 如果查询条件为4 则查询修改为审核
						whereCase.append(" and e.audit_status = '4' ");
					} else if ("5".equals(auditStatus)) {// 如果查询条件为5 则查询修改审核通过
						whereCase.append(" and e.audit_status = '5' ");
					} else if ("6".equals(auditStatus)) {// 如果查询条件为6 则查询修改审核不通过
						whereCase.append(" and e.audit_status = '6' ");
					}
				} else if (userType.equals(SRRPConstant.USER_TYPE_03)) {
					whereCase.append(" and e.audit_status= :auditStatus ");// 登录用户为现金融办则正常查询
				} else if (userType.equals(SRRPConstant.USER_TYPE_06)) {
					if ("1".equals(auditStatus)) {// 如果查询条件为1 则查询未进行审核
						whereCase.append(" and e.audit_status = '1' ");
					} else if ("2".equals(auditStatus)) {// 如果查询条件为2
															// 则查询注册初审通过未进行激活
						whereCase
								.append(" and ( e.audit_status = '2' or e.audit_status = '22')");
					} else if ("3".equals(auditStatus)) {// 如果查询条件为3 则查询注册初审未通过
						whereCase.append(" and e.audit_status = '3' ");
					} else if ("4".equals(auditStatus)) {// 如果查询条件为4 则查询修改为审核
						whereCase.append(" and e.audit_status = '4' ");
					} else if ("5".equals(auditStatus)) {// 如果查询条件为5 则查询修改审核通过
						whereCase.append(" and e.audit_status = '5' ");
					} else if ("6".equals(auditStatus)) {// 如果查询条件为6 则查询修改审核不通过
						whereCase.append(" and e.audit_status = '6' ");
					}
				}
			}
			Query query = entityManager.createNativeQuery(whereCase.toString()
					+ " ) result");
			if (!StringUtils.isEmpty(nameOrCode)) {
				query.setParameter("name", "%" + nameOrCode + "%");
				query.setParameter("code", nameOrCode);
			}
			if (!StringUtils.isEmpty(status)) {
				query.setParameter("status", status);
			}
			if (!StringUtils.isEmpty(auditStatus)) {
				if (userType.equals(SRRPConstant.USER_TYPE_03)) {
					query.setParameter("auditStatus", auditStatus);
				}
			}
			res = query.getSingleResult();
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
	 * <通过多条件进行投资机构查询>
	 * 
	 * @param finacingStage
	 *            投资阶段
	 * @param finacingTrade
	 *            投资行业
	 * @param orgType
	 *            机构类型
	 * @param registerDate
	 *            注册时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Investor> findInverstorInfoList(QueryCondition queryCondition,
			String stopFlag) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<Investor> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			// 如果不为空执行字符串拼接，为空的话什么也不做
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinanceStage())) {
					String[] values = queryCondition.getFinanceStage()
							.toString().split(",");
					List<String> list = java.util.Arrays.asList(values);
					int i = 0;
					for (String stage : list) {
						if (i == 0) {
							whereCase.append(" and (find_in_set('" + stage
									+ "', e.finance_stage)  ");
						} else {
							whereCase.append(" or find_in_set('" + stage
									+ "', e.finance_stage)  ");
						}
						i++;
					}
					if(list.size()>0){
						whereCase.append(" ) ");
					}
				}
				// 判断投资行业是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinanceTrade())) {
					String[] values = queryCondition.getFinanceTrade()
							.toString().split(",");
					List<String> list = java.util.Arrays.asList(values);
					int i = 0;
					for (String trade : list) {
						if (i == 0) {
							whereCase.append(" and ( find_in_set('" + trade
									+ "', e.finance_trade)  ");
						} else {
							whereCase.append(" or find_in_set('" + trade
									+ "', e.finance_trade)  ");
						}
						i++;
					}
					if(list.size()>0){
						whereCase.append(" ) ");
					}
				}
				// 判断投资机构是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrgType())) {
					whereCase.append(" and e.org_type in (:orgType)");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getCurrency())) {
					whereCase.append(" and e.currency in (:currency)");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getAmountCode())) {
					String[] values = queryCondition.getAmountCode().toString()
							.split(",");
					List<String> list = java.util.Arrays.asList(values);
					int i = 0;
					whereCase.append(" and (");
					for (String trade : list) {
						String amountRange = RedisGetValue.getValueByCode(
								SRRPConstant.DD_FINACINGMONEY, trade);
						if (amountRange.indexOf("-") > 0) {
							if(i>=1){
								whereCase.append(" or");
							}
							String[] amoutArray = amountRange.split("-");
							whereCase
									.append("(e.capital_max BETWEEN "
											+ amoutArray[0].replaceAll("M", "")
											+ " and "
											+ amoutArray[1].replaceAll("M", "")
											+ " ) or (e.capital_min BETWEEN "
											+ amoutArray[0].replaceAll("M", "")
											+ " and "
											+ amoutArray[1].replaceAll("M", "")
											+ ")");
						} else {
							if(i>=1){
								whereCase.append(" or");
							}
							if (amountRange.indexOf("M") > 0) {
								String[] amoutArray = amountRange.split("M");
								whereCase.append(" e.capital_min >="
										+ amoutArray[0]);
							}
						}
						i++;
					}
					whereCase.append(" ) ");
				}
				// 添加排序条件
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrderCase())
						|| queryCondition.getOrderCase() != "默认排序") {
					whereCase.append(" ORDER BY :orderCase DESC");
				}
			}
			whereCase.append(this.getPageInfos(queryCondition));
			Query query = entityManager.createNativeQuery(
					unionSQL + whereCase.toString(), Investor.class);
			query.setParameter("stopFlag", stopFlag);
			if (null != queryCondition) {
				// 判断投资机构是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrgType())) {
					String[] values = queryCondition.getOrgType().toString()
							.split(",");
					List list = java.util.Arrays.asList(values);
					query.setParameter("orgType", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getCurrency())) {
					String[] values = queryCondition.getCurrency().toString()
							.split(",");
					List list = java.util.Arrays.asList(values);
					query.setParameter("currency", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrderCase())
						|| queryCondition.getOrderCase() != "默认排序") {
					if (queryCondition.getOrderCase() == "管理资本量") {
						query.setParameter("orderCase", "e.capital");
					} else {
						query.setParameter("orderCase", "");
					}
				}
			}
			res = (List<Investor>) query.getResultList();
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
	 * 投资机构查询/查询机构总数
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Object getInvestorCount(QueryCondition queryCondition,
			String stopFlag) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer coutSql = new StringBuffer(
					" select count(*) as resultnum from ( ");
			coutSql.append(unionSQL);
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinanceStage())) {
					String[] values = queryCondition.getFinanceStage()
							.toString().split(",");
					List<String> list = java.util.Arrays.asList(values);
					int i = 0;
					for (String stage : list) {
						if (i == 0) {
							coutSql.append(" and ( find_in_set('" + stage
									+ "', e.finance_stage)  ");
						} else {
							coutSql.append(" or find_in_set('" + stage
									+ "', e.finance_stage)  ");
						}
						
						i++;
					}
					if(list.size()>0){
						coutSql.append(" ) ");
					}
				}
				// 判断投资行业是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinanceTrade())) {
					String[] values = queryCondition.getFinanceTrade()
							.toString().split(",");
					List<String> list = java.util.Arrays.asList(values);
					int i = 0;
					for (String trade : list) {
						if (i == 0) {
							coutSql.append(" and (find_in_set('" + trade
									+ "', e.finance_trade)  ");
						} else {
							coutSql.append(" or find_in_set('" + trade
									+ "', e.finance_trade)  ");
						}
						i++;
					}
					if(list.size()>0){
						coutSql.append(" ) ");
					}
				}
				// 判断投资机构是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrgType())) {
					coutSql.append(" and e.org_type in (:orgType)");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getCurrency())) {
					coutSql.append(" and e.currency in (:currency)");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getAmountCode())) {
					String[] values = queryCondition.getAmountCode().toString()
							.split(",");
					List<String> list = java.util.Arrays.asList(values);
					int i = 0;
					coutSql.append(" and (");
					for (String trade : list) {
						String amountRange = RedisGetValue.getValueByCode(
								SRRPConstant.DD_FINACINGMONEY, trade);
						if (amountRange.indexOf("-") > 0) {
							if(i>=1){
								coutSql.append(" or");
							}
							String[] amoutArray = amountRange.split("-");
							coutSql
									.append("(e.capital_max BETWEEN "
											+ amoutArray[0].replaceAll("M", "")
											+ " and "
											+ amoutArray[1].replaceAll("M", "")
											+ " ) or (e.capital_min BETWEEN "
											+ amoutArray[0].replaceAll("M", "")
											+ " and "
											+ amoutArray[1].replaceAll("M", "")
											+ ")");
						} else {
							if(i>=1){
								coutSql.append(" or");
							}
							if (amountRange.indexOf("M") > 0) {
								String[] amoutArray = amountRange.split("M");
								coutSql.append(" e.capital_min >="
										+ amoutArray[0]);
							}
						}
						i++;
					}
					coutSql.append(" ) ");
				}
				// 添加排序条件
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrderCase())
						|| queryCondition.getOrderCase() != "默认排序") {
					coutSql.append(" ORDER BY :orderCase DESC");
				}
			}
			coutSql.append(" ) result ");
			Query query = entityManager.createNativeQuery(coutSql.toString());
			query.setParameter("stopFlag", stopFlag);
			if (null != queryCondition) {
				// 判断投资机构是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrgType())) {
					String[] values = queryCondition.getOrgType().toString()
							.split(",");
					List list = java.util.Arrays.asList(values);
					query.setParameter("orgType", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getCurrency())) {
					String[] values = queryCondition.getCurrency().toString()
							.split(",");
					List list = java.util.Arrays.asList(values);
					query.setParameter("currency", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrderCase())
						|| queryCondition.getOrderCase() != "默认排序") {
					if (queryCondition.getOrderCase() == "管理资本量") {
						query.setParameter("orderCase", "e.capital");
					} else {
						query.setParameter("orderCase", "e.registe_time");
					}
				}
			}
			res = query.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Long> countByArea(String area, Date start, Date end,
			Integer limit) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Map<String, Long> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer sql = new StringBuffer(
					"select t.areaCode,count(t) from Investor t where 1=1");
			if (StringUtils.isNotEmpty(area)) {
				sql.append(" and t.areaCode = :area");
			}
			if (start != null) {
				sql.append(" and t.time >=:start");
			}
			if (end != null) {
				sql.append(" and t.time<=:end");
			}
			sql.append(" group by t.areaCode");
			sql.append(" order by count(t) desc");
			if (limit != null) {
				sql.append(" limit " + limit);
			}
			EntityManager em = this.getEntityManager();
			Query q = em.createQuery(sql.toString());
			if (StringUtils.isNotEmpty(area)) {
				q.setParameter("area", area);
			}
			if (start != null) {
				q.setParameter("start", start);
			}
			if (end != null) {
				q.setParameter("end", end);
			}
			List<Object[]> tres = (List<Object[]>) q.getResultList();
			res = new HashMap<String, Long>();
			for (Object[] o : tres) {
				res.put((String) o[0], (Long) o[1]);
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

	/**
	 * 根据条件查询进行机构信息的查询
	 * 
	 * @param investorName
	 *            机构名
	 * @param orgType
	 *            机构类型
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Investor> listInvestorByOrgType(QueryCondition queryCondition,
			String stopFlag) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<Investor> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			// 判断机构名称是否为空
			// 如果不为空执行字符串拼接，为空的话什么也不做
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getInvestorName())) {
					whereCase.append(" and e.name like :investorName");
				}
				// 判断投资机构是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrgType())) {
					whereCase.append(" and e.org_type= :orgType");
				}
			}
			// 拼接分页的功能
			whereCase.append(this.getPageInfos(queryCondition));
			Query query = entityManager.createNativeQuery(
					unionSQL + whereCase.toString(), Investor.class);
			query.setParameter("stopFlag", stopFlag);
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getInvestorName())) {
					query.setParameter("investorName",
							"%" + queryCondition.getInvestorName() + "%");
				}
				// 判断投资机构是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrgType())) {
					query.setParameter("orgType", queryCondition.getOrgType());
				}
			}
			res = (List<Investor>) query.getResultList();
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
	 * 选择机构/查询机构总数
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Object getInvestorCountByOrgType(QueryCondition queryCondition,
			String stopFlag) {

		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer coutSql = new StringBuffer(
					" select count(*) as resultnum from ( ");
			coutSql.append(unionSQL);
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getInvestorName())) {
					coutSql.append(" and e.name like :investorName");
				}
				// 判断投资机构是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrgType())) {
					coutSql.append(" and e.org_type= :orgType");
				}
			}
			coutSql.append(" ) result ");
			Query query = entityManager.createNativeQuery(coutSql.toString());
			query.setParameter("stopFlag", stopFlag);
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getInvestorName())) {
					query.setParameter("investorName",
							"%" + queryCondition.getInvestorName() + "%");
				}
				// 判断投资机构是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getOrgType())) {
					query.setParameter("orgType", queryCondition.getOrgType());
				}
			}
			res = query.getSingleResult();
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

package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import io.netty.util.internal.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryCompanyScoresResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.credit.platform.util.SRRPConstant;

@Component
public class CompanyBaseDaoImpl extends BaseNativeQueryDao {
	// 根据企业的基本信息查询企业的信用评分
	/**
	 * 查询结果 企业评分
	 */
  //private String SQL = "select cp.enterprise_id,cp.name,cp.codetype,cp.redate,cp.rearea,cp.code,cp.audit_stage,cp.stop_flag,cp.audit_status,ccs.score,u.CREATE_TIME,u.lock_flag,CASE WHEN substring(a.audit_status, 2,1) = '1' THEN '90' WHEN substring(a.audit_status, 2,1) = '4' THEN '80' WHEN substring(a.audit_status, 2,1) = '2' THEN	'70' WHEN substring(a.audit_status, 2,1) = '5' THEN '60' WHEN substring(a.audit_status, 2,1) = '3' THEN '50' WHEN substring(a.audit_status, 2,1) = '6' THEN '40' ELSE '10'END sort  from system_user u, rp_company_base_supplement cps,rp_company_base cp left join rp_company_creditscores ccs on cp.code=ccs.creditcode and cp.codetype=ccs.creditype  where u.USER_NAME=cp.code and cp.enterprise_id=cps.enterprise_id and e.audit_status != 00";
	private String X_SQL = "select cp.enterprise_id,cp.name,cp.codetype,cp.redate,cp.rearea,cp.code,cp.audit_stage,cp.stop_flag,cp.audit_status,cp.OPERDATE,cp.create_time companytime,ccs.score,u.USER_ID,u.CREATE_TIME,u.lock_flag,CASE WHEN cp.audit_status = '01' THEN '90' WHEN cp.audit_status = '04' THEN '80' WHEN cp.audit_status = '02' THEN '70' WHEN cp.audit_status = '05' THEN '60' WHEN cp.audit_status = '03' THEN '50' WHEN cp.audit_status = '06' THEN '30' WHEN cp.audit_status = '22' THEN '70' WHEN cp.audit_status = '23' THEN '70' ELSE '5' END sort  from rp_company_base cp left join (select o.* from rp_company_creditscores o,(SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) a where o.cop_id = a.cop_id and a.cop_id = (select b.cop_id from (SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) b where a.creditcode = b.creditcode ORDER BY b.mt desc limit 1)) ccs on cp.code=ccs.creditcode left JOIN  system_user u  on cp.code=u.USER_NAME  where 1=1 ";
	private String S_SQL = "select cp.enterprise_id,cp.name,cp.codetype,cp.redate,cp.rearea,cp.code,cp.audit_stage,cp.stop_flag,cp.audit_status,cp.OPERDATE,cp.create_time companytime,ccs.score,u.USER_ID,u.CREATE_TIME,u.lock_flag,CASE WHEN cp.audit_status = '01' THEN '90' WHEN cp.audit_status = '04' THEN '80' WHEN cp.audit_status = '02' THEN '70' WHEN cp.audit_status = '05' THEN '60' WHEN cp.audit_status = '03' THEN '50' WHEN cp.audit_status = '06' THEN '30' WHEN cp.audit_status = '22' THEN '70' WHEN cp.audit_status = '23' THEN '70' ELSE '5' END sort  from rp_company_base cp left join (select o.* from rp_company_creditscores o,(SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) a where o.cop_id = a.cop_id and a.cop_id = (select b.cop_id from (SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) b where a.creditcode = b.creditcode ORDER BY b.mt desc limit 1)) ccs on cp.code=ccs.creditcode left JOIN  system_user u on cp.code=u.USER_NAME where 1=1 ";
	private String Z_SQL = "select cp.enterprise_id,cp.name,cp.codetype,cp.redate,cp.rearea,cp.code,cp.audit_stage,cp.stop_flag,cp.audit_status,cp.OPERDATE,cp.create_time companytime,ccs.score,u.USER_ID,u.CREATE_TIME,u.lock_flag,CASE WHEN cp.audit_status = '02' THEN '90' WHEN cp.audit_status = '22' THEN '80' WHEN cp.audit_status = '23' THEN '70' WHEN cp.audit_status = '04' THEN '80' WHEN cp.audit_status = '05' THEN '80' WHEN cp.audit_status = '06' THEN '80' ELSE '60' END sort from rp_company_base cp left join (select o.* from rp_company_creditscores o,(SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) a where o.cop_id = a.cop_id and a.cop_id = (select b.cop_id from (SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) b where a.creditcode = b.creditcode ORDER BY b.mt desc limit 1)) ccs on cp.code=ccs.creditcode left JOIN  system_user u on cp.code=u.USER_NAME  where   1=1 ";
	private String D_SQL = "select cp.enterprise_id,cp.name,cp.codetype,cp.redate,cp.rearea,cp.code,cp.audit_stage,cp.stop_flag,cp.audit_status,cp.OPERDATE,cp.create_time companytime,ccs.score,u.USER_ID,u.CREATE_TIME,u.lock_flag,CASE WHEN cp.audit_status = '01' THEN '90' WHEN cp.audit_status = '04' THEN '80' WHEN cp.audit_status = '02' THEN '70' WHEN cp.audit_status = '05' THEN '60' WHEN cp.audit_status = '03' THEN '50' WHEN cp.audit_status = '06' THEN '30' WHEN cp.audit_status = '22' THEN '70' WHEN cp.audit_status = '23' THEN '70' ELSE '5' END sort  from rp_company_base cp left join (select o.* from rp_company_creditscores o,(SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) a where o.cop_id = a.cop_id and a.cop_id = (select b.cop_id from (SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) b where a.creditcode = b.creditcode ORDER BY b.mt desc limit 1)) ccs on cp.code=ccs.creditcode left JOIN  system_user u on cp.code=u.USER_NAME where 1=1 ";

	/**
	 * 按注册时间，区域统计企业数量
	 * 
	 * @param area
	 * @param start
	 * @param end
	 * @param limit
	 * @return
	 */
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
					"select t.rearea ,count(t) from CompanyBase t where 1=1");
			if (StringUtils.isNotEmpty(area)) {
				sql.append(" and t.rearea = :area");
			}
			if (start != null) {
				sql.append(" and t.redate >=:start");
			}
			if (end != null) {
				sql.append(" and t.redate <=:end");
			}
			sql.append(" group by t.rearea");
			sql.append(" order by count(t) desc");
			if (limit != null) {
				sql.append(" limit " + limit);
			}
			Query q = entityManager.createQuery(sql.toString());
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
	 * 根据多条件查询待审核信息列表
	 * 
	 * @param queryCondition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<QueryCompanyScoresResult> findCompanyBaseByWhereCase(
			QueryCondition queryCondition,String userType) {

		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryCompanyScoresResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			// 事物的开始
			entityTransaction.begin();
			String  SQL="";
			if (SRRPConstant.USER_TYPE_03.equals(userType)) {
				SQL=X_SQL;
			}else if (SRRPConstant.USER_TYPE_04.equals(userType)) {
				SQL=S_SQL;
			}else if (SRRPConstant.USER_TYPE_05.equals(userType)) {
				SQL=Z_SQL;
			}else if (SRRPConstant.USER_TYPE_06.equals(userType)) {
				SQL=D_SQL;
			}
				
			// 拼接条件的对象
			StringBuffer whereCase = new StringBuffer();
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getNameOrCode())) {
					whereCase.append(" and( cp.name like :name or cp.code = :code )");
					
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
					whereCase.append(" and( cp.rearea = :area )");
					
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStage())) {//查询企业是根据区县金融办所在的区域
					whereCase.append(" and( cp.audit_stage = :auditStage )");
					
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getStopFlag())) {
					whereCase.append(" and cp.stop_flag=:stopFlag ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStatus())) {
					if (SRRPConstant.USER_TYPE_03.equals(userType)) {//县级金融办登录
						whereCase.append(" and cp.audit_status=:auditStatus ");
					}else if (SRRPConstant.USER_TYPE_04.equals(userType)) {
						
						if ("02".equals(queryCondition.getAuditStatus())) {// 如果查询条件为2
							// 则查询注册初审通过未进行激活
							whereCase.append(" and (cp.audit_status in( '02','22','23')) ");
						} else {
							whereCase.append(" and cp.audit_status=:auditStatus ");
						}
					}else if (SRRPConstant.USER_TYPE_05.equals(userType)) {
						if(queryCondition.getAuditStatus().equals("21")){
							whereCase.append(" and cp.audit_status='02' ");
						}else if(queryCondition.getAuditStatus().equals("22")){
							whereCase.append(" and (cp.audit_status in ('22','04','05','06') ) ");
						}else if(queryCondition.getAuditStatus().equals("23")){
							whereCase.append(" and cp.audit_status='23' ");
						}
					}else if(SRRPConstant.USER_TYPE_06.equals(userType)) {
						
						if ("02".equals(queryCondition.getAuditStatus())) {// 如果查询条件为2
							// 则查询注册初审通过未进行激活
							whereCase.append(" and (cp.audit_status in( '02','22','23')) ");
						} else {
							whereCase.append(" and cp.audit_status=:auditStatus ");
						}
				 }
			  }else{
				  if (SRRPConstant.USER_TYPE_05.equals(userType)) {//县级金融办登录
						whereCase.append(" and cp.audit_status in('02','22','23','04','05','06') ");
					}else {
						whereCase.append(" and cp.audit_status in('01','02','03','04','05','06','22','23') ");
					}
			  }
			}
			whereCase.append(" order by sort desc,cp.OPERDATE desc");
			whereCase.append(this.getPageInfos(queryCondition));
			
			Query query = entityManager.createNativeQuery(
					SQL + whereCase.toString(), QueryCompanyScoresResult.class);
			// 创建预查询
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getNameOrCode())) {
					query.setParameter("name", "%" + queryCondition.getNameOrCode()
							+ "%");
					query.setParameter("code", queryCondition.getNameOrCode());
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {
					query.setParameter("area", queryCondition.getArea());
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getStopFlag())) {
					query.setParameter("stopFlag", queryCondition.getStopFlag());
				}
				
				
				if (SRRPConstant.USER_TYPE_03.equals(userType)) {//县级金融办登录
					if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStatus())) {
						query.setParameter("auditStatus",queryCondition.getAuditStatus());
					}
				}else if (SRRPConstant.USER_TYPE_04.equals(userType) || SRRPConstant.USER_TYPE_06.equals(userType)) {
					
					if (!"02".equals(queryCondition.getAuditStatus())) {// 如果查询条件为2
						if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStatus())) {
							query.setParameter("auditStatus",queryCondition.getAuditStatus());
						}
					} 
				}
				

				if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStage())) {
					query.setParameter("auditStage",queryCondition.getAuditStage());
				}
			}
			res = (List<QueryCompanyScoresResult>) query.getResultList();
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
	 * 获得查询到的数量
	 * 
	 * @param queryCondition
	 * @return
	 */
	public Object CountCompanyBase(QueryCondition queryCondition,String userType) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer coutSql = new StringBuffer(
					" select count(*) as resultnum from ( ");
			String  SQL="";
			if (SRRPConstant.USER_TYPE_03.equals(userType)) {
				SQL=X_SQL;
			}else if (SRRPConstant.USER_TYPE_04.equals(userType)){
				SQL=S_SQL;
			}else if (SRRPConstant.USER_TYPE_05.equals(userType)) {
				SQL=Z_SQL;
			}else if(SRRPConstant.USER_TYPE_06.equals(userType)) {
				SQL=D_SQL;
			}
			coutSql.append(SQL);
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getNameOrCode())) {
					coutSql.append(" and( cp.name like :name or cp.code = :code) ");
					
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStage())) {//查询企业是根据区县金融办所在的区域
					coutSql.append(" and( cp.audit_stage = :auditStage )");
					
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {//查询企业是根据区县金融办所在的区域
					coutSql.append(" and( cp.rearea = :area )");
					
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getStopFlag())) {
					coutSql.append(" and cp.stop_flag=:stopFlag ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStatus())) {
					if (SRRPConstant.USER_TYPE_03.equals(userType)) {//县级金融办登录
						coutSql.append(" and cp.audit_status=:auditStatus ");
					}else if (SRRPConstant.USER_TYPE_04.equals(userType)) {
						
						if ("02".equals(queryCondition.getAuditStatus())) {// 如果查询条件为2
							// 则查询注册初审通过未进行激活
							coutSql.append(" and (cp.audit_status in( '02','22','23')) ");
						} else {
							coutSql.append(" and cp.audit_status=:auditStatus ");
						}
					}else if (SRRPConstant.USER_TYPE_05.equals(userType)) {
						if(queryCondition.getAuditStatus().equals("21")){
							coutSql.append(" and cp.audit_status='02' ");
						}else if(queryCondition.getAuditStatus().equals("22")){
							coutSql.append(" and (cp.audit_status in ('22','04','05','06') ) ");
						}else if(queryCondition.getAuditStatus().equals("23")){
							coutSql.append(" and cp.audit_status='23' ");
						}
					}else if(SRRPConstant.USER_TYPE_06.equals(userType)) {
						
						if ("02".equals(queryCondition.getAuditStatus())) {// 如果查询条件为2
							// 则查询注册初审通过未进行激活
							coutSql.append(" and (cp.audit_status in( '02','22','23')) ");
						} else {
							coutSql.append(" and cp.audit_status=:auditStatus ");
						}
				 }
			  }else{
				  if (SRRPConstant.USER_TYPE_05.equals(userType)) {//县级金融办登录
					  coutSql.append(" and cp.audit_status in('02','22','23','04','05','06') ");
					}else {
						coutSql.append(" and cp.audit_status in('01','02','03','04','05','06','22','23') ");
					}
			  }
				coutSql.append(" order by cp.audit_status asc");
			}
			coutSql.append(" ) result ");
			Query query = entityManager.createNativeQuery(coutSql.toString());
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getNameOrCode())) {
					query.setParameter("name", "%" + queryCondition.getNameOrCode()
							+ "%");
					query.setParameter("code", queryCondition.getNameOrCode());
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStage())) {
					query.setParameter("auditStage",queryCondition.getAuditStage());
				}			
				if (!StringUtil.isNullOrEmpty(queryCondition.getArea())) {
					query.setParameter("area", queryCondition.getArea());
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getStopFlag())) {
					query.setParameter("stopFlag", queryCondition.getStopFlag());
				}
				if (SRRPConstant.USER_TYPE_03.equals(userType)) {//县级金融办登录
					if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStatus())) {
						query.setParameter("auditStatus",queryCondition.getAuditStatus());
					}
				}else if (SRRPConstant.USER_TYPE_04.equals(userType) || SRRPConstant.USER_TYPE_06.equals(userType)) {
					
					if (!"02".equals(queryCondition.getAuditStatus())) {// 如果查询条件为2
						if (!StringUtil.isNullOrEmpty(queryCondition.getAuditStatus())) {
							query.setParameter("auditStatus",queryCondition.getAuditStatus());
						}
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
}

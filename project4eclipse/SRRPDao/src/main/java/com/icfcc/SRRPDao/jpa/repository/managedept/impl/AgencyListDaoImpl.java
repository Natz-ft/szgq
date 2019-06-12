package com.icfcc.SRRPDao.jpa.repository.managedept.impl;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.managedept.AgencyListResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

@Component
public class AgencyListDaoImpl extends BaseNativeQueryDao {

	private String sql = "SELECT @rownum \\:= @rownum + 1 AS rank,report_org.* FROM (SELECT e.investorg_id, i.NAME, IFNULL(ROUND(SUM(e.exchange_ratio),2), 0) as amounts , COUNT(DISTINCT e.enterprise_id) as toalid, COUNT(e.info_id) as demands FROM rp_finacing_event e, rp_investor i,rp_finacing_demand d, rp_company_base c WHERE e.amount is not null and e.info_id = d.info_id and e.`investorg_id` = i.`investor_id` AND e.`enterprise_id` = c.enterprise_id  ";
	private String SendInfosql = "SELECT count(*)  as resultnum FROM rp_finacing_event re,rp_finacing_demand rf WHERE re.info_id =rf.info_id  and re.investorg_id  =:investorgId and rf.open='1' ";

	/**
	 * 通过起止时间，融资轮次统计机构榜单
	 * 
	 * @Title: getInvestorStatistics
	 * @param queryCondition
	 * @return List<AgencyListResult> 返回类型
	 */
	@SuppressWarnings("unchecked")
	public List<AgencyListResult> getInvestorStatistics(
			QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<AgencyListResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer("");
			if (null != queryCondition) {
				if (StringUtils.isNotEmpty(queryCondition.getFinacingTurn())) {
					whereCase.append(" and d.finacing_turn = :finacingTurn ");
				}
				if (StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())) {
					whereCase
							.append(" and DATE_FORMAT(e.operdate,'%Y-%m') >= :beginTimeStr");
				}
				if (StringUtils.isNotEmpty(queryCondition.getEndTimeStr())) {
					whereCase
							.append(" and DATE_FORMAT(e.operdate,'%Y-%m') <= :endTimeStr");
				}
			}
			//LIMIT 0, 10
			whereCase
					.append(" GROUP BY e.investorg_id, i.`name` ORDER BY amounts DESC ) report_org,(SELECT  @rownum \\:= 0) AS report_rank ");
			Query query = entityManager.createNativeQuery(
					sql + whereCase.toString(), AgencyListResult.class);
			if (null != queryCondition) {
				if (StringUtils.isNotEmpty(queryCondition.getFinacingTurn())) {
					query.setParameter("finacingTurn",
							queryCondition.getFinacingTurn());
				}
				if (StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())) {
					query.setParameter("beginTimeStr",
							queryCondition.getBeginTimeStr());
				}
				if (StringUtils.isNotEmpty(queryCondition.getEndTimeStr())) {
					query.setParameter("endTimeStr",
							queryCondition.getEndTimeStr());
				}
			}
			res = (List<AgencyListResult>) query.getResultList();
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
	public Object sendInforDemandCount(String investorgId,QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer("");
			if (null != queryCondition) {
				
				if (StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())) {
					whereCase
							.append(" and DATE_FORMAT(re.operdate,'%Y-%m') >= :beginTimeStr");
				}
				if (StringUtils.isNotEmpty(queryCondition.getEndTimeStr())) {
					whereCase
							.append(" and DATE_FORMAT(re.operdate,'%Y-%m') <= :endTimeStr");
				}
			}
			Query query = entityManager.createNativeQuery(SendInfosql+ whereCase.toString());
			if (null != queryCondition) {
				if (StringUtils.isNotEmpty(queryCondition.getBeginTimeStr())) {
					query.setParameter("beginTimeStr",
							queryCondition.getBeginTimeStr());
				}
				if (StringUtils.isNotEmpty(queryCondition.getEndTimeStr())) {
					query.setParameter("endTimeStr",
							queryCondition.getEndTimeStr());
				}
			}
				if (StringUtils.isNotEmpty(investorgId)) {
					query.setParameter("investorgId",
							investorgId);
				}
			res =  query.getSingleResult();
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

package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryInvestorLoanResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanOrgTmp;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
	
@Component
public class InvestorLoanDaoImpl extends BaseNativeQueryDao {
	private String unionSQL = "select e.name,e.area_code,e.investor_id,e.finance_stage,fe.info_id,il.event_id,il.loandate,il.amount ,il.currency,il.loan_id from rp_investor_loan il,rp_investor e,rp_finacing_event fe where fe.investorg_id=e.investor_id and il.event_id=fe.event_id and il.event_id= :eventId";

	/**
	 * 根据融资事件的id查询机构放款信息
	 * 
	 * @param eventId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<QueryInvestorLoanResult> findUnionInfoLoans(String eventId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryInvestorLoanResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createNativeQuery(unionSQL,
					QueryInvestorLoanResult.class);
			query.setParameter("eventId", eventId);
			res = (List<QueryInvestorLoanResult>) query.getResultList();
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
	 * 按机构统计放款
	 * 
	 * @param orgId
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> sumAmountByOrgId(String orgId, Date start,
			Date end, Integer limit) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Map<String, BigDecimal> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer sql = new StringBuffer(
					" select t.org_id,sum(t.amount) from rp_investor_loan t,rp_company_base t1 where t.enterprise_id=t1.enterprise_id");
			if (StringUtils.isNotEmpty(orgId)) {
				sql.append(" and t.org_id=:orgId");
			}
			if (start != null) {
				sql.append(" and t.loandate >= :start");
			}
			if (end != null) {
				sql.append(" and t.loandate <= :end");
			}
			sql.append(" group by t.org_id");
			sql.append(" order by sum(t.amount) desc");
			if (limit != null) {
				sql.append(" limit " + limit);
			}
			EntityManager em = this.getEntityManager();
			Query q = em.createNativeQuery(sql.toString());
			if (StringUtils.isNotEmpty(orgId)) {
				q.setParameter("orgId", orgId);
			}
			if (start != null) {
				q.setParameter("start", start);
			}
			if (end != null) {
				q.setParameter("end", end);
			}
			List<Object[]> tres = (List<Object[]>) q.getResultList();
			res = new HashMap<String, BigDecimal>();
			for (Object[] o : tres) {
				res.put((String) o[0], new BigDecimal((Double) o[1]));
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
	 * 按机构统计放款
	 * 
	 * @param orgId
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportBeanOrgTmp> sumAmountByOrg(String orgId, Date start,
			Date end, Integer limit) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<ReportBeanOrgTmp> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer sql = new StringBuffer(
					" select t.org_id,t.orgname as name,  sum(t.amount) as amount ,count(t.event_id) as demand_count,count(DISTINCT t.enterprise_id) as company_count from rp_investor_loan t,rp_company_base t1 where t.enterprise_id=t1.enterprise_id ");
			if (StringUtils.isNotEmpty(orgId)) {
				sql.append(" and t.org_id=:orgId");
			}
			if (start != null) {
				sql.append(" and t.loandate >= :start");
			}
			if (end != null) {
				sql.append(" and t.loandate <= :end");
			}
			sql.append(" group by t.org_id,t.orgname ");
			sql.append(" order by sum(t.amount) desc");
			if (limit != null) {
				sql.append(" limit " + limit);
			}
			EntityManager em = this.getEntityManager();
			Query q = em.createNativeQuery(sql.toString(),
					ReportBeanOrgTmp.class);
			if (StringUtils.isNotEmpty(orgId)) {
				q.setParameter("orgId", orgId);
			}
			if (start != null) {
				q.setParameter("start", start);
			}
			if (end != null) {
				q.setParameter("end", end);
			}
			res = (List<ReportBeanOrgTmp>) q.getResultList();
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
	 * 按区域统计放款
	 * 
	 * @param area
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> sumAmountByArea(String area, Date start,
			Date end, Integer limit) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Map<String, BigDecimal> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer sql = new StringBuffer(
					"select t1.rearea,sum(t.amount) from rp_investor_loan t,rp_company_base t1 where t.enterprise_id=t1.enterprise_id");
			if (StringUtils.isNotEmpty(area)) {
				sql.append(" and t1.rearea=:area");
			}
			if (start != null) {
				sql.append(" and t.loandate >= :start");
			}
			if (end != null) {
				sql.append(" and t.loandate <= :end");
			}
			sql.append(" group by t1.rearea");
			sql.append(" order by sum(t.amount) desc");
			if (limit != null) {
				sql.append(" limit " + limit);
			}
			EntityManager em = this.getEntityManager();
			Query q = em.createNativeQuery(sql.toString());

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
			res = new HashMap<String, BigDecimal>();
			for (Object[] o : tres) {
				res.put((String) o[0], new BigDecimal((Double) o[1]));
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

	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> sumAmountByEnterpriseId(String enterpriseId,
			Date start, Date end, Integer limit) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Map<String, BigDecimal> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer sql = new StringBuffer(
					"select t.enterprise_id,sum(t.amount) from rp_investor_loan t where 1=1 ");
			if (StringUtils.isNotEmpty(enterpriseId)) {
				sql.append(" and t.enterprise_id=:enterpriseId");
			}
			if (start != null) {
				sql.append(" and t.loandate >= :start");
			}
			if (end != null) {
				sql.append(" and t.loandate <= :end");
			}
			sql.append(" group by t.enterprise_id");
			sql.append(" order by sum(t.amount) desc");
			if (limit != null) {
				sql.append(" limit " + limit);
			}
			EntityManager em = this.getEntityManager();
			Query q = em.createNativeQuery(sql.toString());

			if (StringUtils.isNotEmpty(enterpriseId)) {
				q.setParameter("enterpriseId", enterpriseId);
			}
			if (start != null) {
				q.setParameter("start", start);
			}
			if (end != null) {
				q.setParameter("end", end);
			}
			List<Object[]> tres = (List<Object[]>) q.getResultList();
			res = new HashMap<String, BigDecimal>();
			for (Object[] o : tres) {
				res.put((String) o[0], new BigDecimal((Double) o[1]));
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

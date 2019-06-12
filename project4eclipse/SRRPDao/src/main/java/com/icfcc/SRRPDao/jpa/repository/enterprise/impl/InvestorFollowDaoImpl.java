package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryFollowInvestorResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class InvestorFollowDaoImpl extends BaseNativeQueryDao {
	/**
	 * 根据机构信息查询跟投机构表
	 */
	private String  sql="SELECT rif.event_id, rif.follow_id, rif.amount, rif.orgname, ri.`name`,ri.investor_id, ri.org_type FROM rp_investor_follow rif LEFT JOIN rp_investor ri ON ri.`name` = rif.orgname WHERE rif.event_id =:eventId ";
	
	
	public List<QueryFollowInvestorResult> findInvestorFollowListByEventId(
			String eventId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryFollowInvestorResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();

			Query query = entityManager.createNativeQuery(sql,
					QueryFollowInvestorResult.class);
			query.setParameter("eventId", eventId);
			res = query.getResultList();
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

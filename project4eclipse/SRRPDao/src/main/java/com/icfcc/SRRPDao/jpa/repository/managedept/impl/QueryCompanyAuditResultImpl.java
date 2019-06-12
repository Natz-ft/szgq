package com.icfcc.SRRPDao.jpa.repository.managedept.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.managedept.QueryCompanyAuditResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class QueryCompanyAuditResultImpl extends BaseNativeQueryDao {
	public String sql = "SELECT hsb.his_id,hsb.audit_id,hsb.enterprise_id,	hsb.audit_content,	hsb.audit_time,	hsb.`status`,sysu.USER_NAME FROM	rp_his_company_base hsb,system_user sysu WHERE	hsb.audit_id = sysu.USER_ID and hsb.enterprise_id=:enterpriseId order by hsb.audit_time desc";

	@SuppressWarnings("unchecked")
	public List<QueryCompanyAuditResult> findCompanyAuditList(
			String enterpriseId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryCompanyAuditResult> companyAuditResultList = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createNativeQuery(sql,
					QueryCompanyAuditResult.class);
			query.setParameter("enterpriseId", enterpriseId);
			companyAuditResultList = (List<QueryCompanyAuditResult>) query
					.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return companyAuditResultList;
	}
}

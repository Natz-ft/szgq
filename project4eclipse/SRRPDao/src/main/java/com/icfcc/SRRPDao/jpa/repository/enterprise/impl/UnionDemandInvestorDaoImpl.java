package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryInvestorFinacingEventResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.UnionDemandInvestorResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

/**
 * 关于机构对需求的操作记录
 * 
 * @author Administrator
 *
 */
@Component 
public class UnionDemandInvestorDaoImpl extends BaseNativeQueryDao {
	private String SQl = "SELECT fd.open, fe.event_id, fe.info_id, fe.investorg_id, i.`name`,max(fe.status) as status, fe.operuser,fe.publish_flag,fe.enterprise_id FROM rp_finacing_demand fd, rp_finacing_event fe, rp_investor i WHERE fe.info_id=fd.info_id and fe.investorg_id = i.investor_id and fe.status !='01' AND fe.info_id = :infoId GROUP BY fe.investorg_id order by fe.status desc";

	public List<UnionDemandInvestorResult> findDemandInvestorResult(String infoId) {
		List<UnionDemandInvestorResult> results = null;
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createNativeQuery(SQl,
					UnionDemandInvestorResult.class);
			query.setParameter("infoId",infoId);
			results = query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return results;
	}

}

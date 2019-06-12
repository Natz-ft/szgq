package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingEventInvestor;



/**
 * 有关融资事件的相关操作的dao
 * 
 * @author john
 *
 */
@Component
public class FinacingEventInvestorDao  extends BaseNativeQueryDao {
	private String querySQL = "SELECT a.event_id,b.name,b.org_type,a.amount,c.scalemax as reserve FROM rp_finacing_event a,rp_investor b,rp_finacing_demand c WHERE a.investorg_id = b.investor_id and a.info_id=c.info_id and a.amount is not null and c.`status`='03' order by a.operdate desc";
    
	@SuppressWarnings("unchecked")
	public List<FinacingEventInvestor> getFinacingEventInvestor(int n) {
		 EntityManager entityManager = this.getEntityManager();
	     EntityTransaction entityTransaction = null;
		List<FinacingEventInvestor> list =null;
		String eventSql=null;
		try {
			entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            eventSql=querySQL+" limit "+ n;
			Query query = entityManager.createNativeQuery(eventSql,FinacingEventInvestor.class);
			list = (List<FinacingEventInvestor>) query.getResultList();
			entityTransaction.commit();
		  } catch (Exception e) {
	            e.printStackTrace();
	            entityTransaction.rollback();
	        } finally {
	        	 this.closeEntityManager(entityManager);
	        	 }
		
		return list;
	}
	
}

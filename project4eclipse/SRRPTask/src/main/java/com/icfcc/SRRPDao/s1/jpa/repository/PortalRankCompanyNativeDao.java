package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankCompany;

@Component
public class PortalRankCompanyNativeDao extends BaseNativeQueryDao {

	String excueteSql = " select t.rid ,t.name , t.amount as finanmoney,t.rank_num as uplistnum,t.time_id as countdate ,t.rank_count as ranking from rp_report_company t ";

	/**
	 * 
	 * <p>
	 * 功能描述:[待迁移门户企业榜单]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@SuppressWarnings("unchecked")
	public List<PlatformRankCompany> portalRankCompayList() {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<PlatformRankCompany> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createNativeQuery(excueteSql,
					PlatformRankCompany.class);
			res = (List<PlatformRankCompany>) query.getResultList();
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

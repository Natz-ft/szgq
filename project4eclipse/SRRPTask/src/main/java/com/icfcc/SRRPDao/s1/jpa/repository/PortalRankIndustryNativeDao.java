package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankIndustry;

@Component
public class PortalRankIndustryNativeDao extends BaseNativeQueryDao {

	String excueteSql = " select a.rid,a.industry as trades,a.amount as finanmoney,a.time_id as countdate,a.rank as rankingnum,a.status,a.demandnum from rp_report_industry a  ";

	/**
	 * 
	 * <p>
	 * 功能描述:[待迁移门户行業榜单]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@SuppressWarnings("unchecked")
	public List<PlatformRankIndustry> portalRankIndustryList() {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<PlatformRankIndustry> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createNativeQuery(excueteSql,
					PlatformRankIndustry.class);
			res = (List<PlatformRankIndustry>) query.getResultList();
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

package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformRankArea;

@Component
public class PortalRankAreaNativeDao extends BaseNativeQueryDao {

	String excueteSql = " select a.rid,a.area as adminarea,a.amount as finanMoney ,a.time_id as countdate,a.rank as rankingnum,a.status,a.demandnum from rp_report_area a ";

	/**
	 * 
	 * <p>
	 * 功能描述:[待迁移到门户区域榜单]
	 * </p>
	 * 
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@SuppressWarnings("unchecked")
	public List<PlatformRankArea> portalRankAreaList() {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<PlatformRankArea> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createNativeQuery(excueteSql,PlatformRankArea.class);
			res = (List<PlatformRankArea>) query.getResultList();
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

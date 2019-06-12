package com.icfcc.SRRPDao.jpa.repository.gataway;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankArea;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankCompany;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankFinacingTurn;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankIndustry;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankInvestor;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayStatic;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformFaq;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformFaqShow;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class GataWayRankEcologyDao extends BaseNativeQueryDao {

	// 运行成果-融资统计
	private String rankTotalSql = "select * from platform_portal_statictis where 1=1 ";
	// 运行成果-企业榜单
	private String rankCompanySql = "select * from platform_portal_company_rangking where 1=1 ";
	// 运行成果-机构榜单
	private String rankInvestorSql = "select * from platform_portal_rangking_investor where 1=1 ";
	// 运行成果-区域榜单
	private String rankAreaSql = "select * from platform_portal_rangking_company_area where 1=1 ";
	// 运行成果-行业榜单
	private String rankIndustrySql = "select * from platform_portal_rangking_company_industry where 1=1 ";
	// 运行成果-轮次统计
	private String finacTurnSql = "select * from platform_portal_rangking_finacturn where 1=1 ";
	
	//首页动态广告
	private String faqDy = "select * from platform_faq where type = '0003' ORDER BY create_time desc limit 1 ";
	
	
	
		// 运行成果-区域榜单
		@SuppressWarnings("unchecked")
		public List<String> getMaxCount(String randSl) {
			EntityManager entityManager = null;
			EntityTransaction entityTransaction = null;
			List<String> countDate = null;
			try {
				entityManager = this.getEntityManager();
				entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				Query query = entityManager.createNativeQuery(randSl);
				countDate = query.getResultList();
				entityTransaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				entityTransaction.rollback();
			} finally {
				this.closeEntityManager(entityManager);
			}
			return countDate;
		}
	@SuppressWarnings("unchecked")
	@Autowired(required = false)
	// 运行成果-融资统计
	public List<GataWayStatic> findRankTotalList(String countDate) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<GataWayStatic> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (null != countDate) {
				whereCase.append(" and countdate= :countDate ");
			}
			Query query = entityManager.createNativeQuery(rankTotalSql
					+ whereCase.toString(), GataWayStatic.class);
			if (null != countDate) {
				query.setParameter("countDate", (countDate));
			}
			res = (List<GataWayStatic>) query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	// 运行成果-企业榜单
	@SuppressWarnings("unchecked")
	public List<GataWayRankCompany> findRankCompanyList(String countDate) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<GataWayRankCompany> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (null != countDate) {
				whereCase.append(" and countdate= :countDate ");
			}
			whereCase.append(" order by ranking asc ");
			Query query = entityManager.createNativeQuery(rankCompanySql
					+ whereCase.toString(), GataWayRankCompany.class);
			if (null != countDate) {
				query.setParameter("countDate", (countDate));
			}
			res = (List<GataWayRankCompany>) query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	// 运行成果-机构榜单
	@SuppressWarnings("unchecked")
	public List<GataWayRankInvestor> findRankInvestorList(String countDate) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<GataWayRankInvestor> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (null != countDate) {
				whereCase.append(" and countdate= :countDate ");
			}
			whereCase.append(" order by raking asc ");
			Query query = entityManager.createNativeQuery(rankInvestorSql
					+ whereCase.toString(), GataWayRankInvestor.class);
			if (null != countDate) {
				query.setParameter("countDate", (countDate));
			}
			res = (List<GataWayRankInvestor>) query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	// 运行成果-区域榜单
	@SuppressWarnings("unchecked")
	public List<GataWayRankArea> findRankAreaList(String countDate,
			String status) {
		EntityManager entityManager = null;
		EntityTransaction entityTransaction = null;
		List<GataWayRankArea> res = null;
		try {
			entityManager = this.getEntityManager();
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (null != countDate) {
				whereCase.append(" and countdate= :countDate ");
			}
			if (null != status) {
				whereCase.append(" and status= :status ");
			}
			whereCase.append(" order by rankingnum asc ");
			Query query = entityManager.createNativeQuery(rankAreaSql
					+ whereCase.toString(), GataWayRankArea.class);
			if (null != countDate) {
				query.setParameter("countDate", countDate);
			}
			if (null != status) {
				query.setParameter("status", status);
			}
			res = (List<GataWayRankArea>) query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	// 运行成果-行业榜单
	@SuppressWarnings("unchecked")
	public List<GataWayRankIndustry> findRankIndustryList(String countDate,
			String status,String orderBy) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<GataWayRankIndustry> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (null != countDate) {
				whereCase.append(" and countdate= :countDate ");
			}
			if (null != status) {
				whereCase.append(" and status= :status ");
			}
			whereCase.append(" order by "+orderBy+" ");
			Query query = entityManager.createNativeQuery(rankIndustrySql
					+ whereCase.toString(), GataWayRankIndustry.class);
			if (null != countDate) {
				query.setParameter("countDate", countDate);
			}
			if (null != status) {
				query.setParameter("status", status);
			}
			res = (List<GataWayRankIndustry>) query.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	// 运行成果-融资轮次
	@SuppressWarnings("unchecked")
	public List<GataWayRankFinacingTurn> findRankFinacTurnList(
			String countDate, String status,String type) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<GataWayRankFinacingTurn> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (null != countDate) {
				whereCase.append(" and countdate= :countDate ");
			}
			if (null != status) {
				whereCase.append(" and status= :status ");
			}  
			if (null != type) {
				whereCase.append(" and type= :type ");
			}  
			if("01".equals(type)){//融资轮次
				whereCase.append(" order by rid asc ");
			}else{//月度统计
				whereCase.append("  ORDER BY STR_TO_DATE(dimension,'%Y-%m')  asc ");

			}
			Query query = entityManager.createNativeQuery(finacTurnSql
					+ whereCase.toString(), GataWayRankFinacingTurn.class);
			if (null != countDate) {
				query.setParameter("countDate", countDate);
			}
			if (null != status) {
				query.setParameter("status", status);
			}
			if (null != type) {
				query.setParameter("type", type);
			}
			res = (List<GataWayRankFinacingTurn>) query.getResultList();
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
	public PlatformFaqShow findFaqDy() {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		PlatformFaqShow res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createNativeQuery(faqDy,PlatformFaqShow.class);
			res = (PlatformFaqShow) query.getSingleResult();
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

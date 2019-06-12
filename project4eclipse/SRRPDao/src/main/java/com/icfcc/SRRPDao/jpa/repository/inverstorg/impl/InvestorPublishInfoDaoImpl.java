package com.icfcc.SRRPDao.jpa.repository.inverstorg.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorPublishInfoResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.InvestorPublishInfoDao;
import io.netty.util.internal.StringUtil;

/**
 * 披露信息实现类
 * 
 * @ClassName: InvestorPublishInfoDaoImpl
 * @author daiyx
 * @date 2017年9月15日 下午7:00:43
 */
@Component
public class InvestorPublishInfoDaoImpl extends BaseNativeQueryDao implements InvestorPublishInfoDao {

	private String sqlGetList = "select t1.*,cc.score from (select fe.project_name,cb.name,ip.*,cb.code ,cb.codetype from rp_investor_publish ip, rp_finacing_event fe, rp_company_base cb where ip.enterprise_id=cb.enterprise_id and ip.event_id=fe.event_id ";
	private String LEFTJOINsql = ") t1 LEFT JOIN rp_company_creditscores cc on t1.codetype=cc.creditype and t1.code=cc.creditcode ";
	private String sqlFindById = "select t1.*,cc.score from (select fe.project_name,cb.name,ip.*,cb.code ,cb.codetype from rp_investor_publish ip, rp_finacing_event fe, rp_company_base cb where ip.enterprise_id=cb.enterprise_id and ip.event_id=fe.event_id and ip.publish_id = :publishId) t1 LEFT JOIN rp_company_creditscores cc on t1.codetype=cc.creditype and t1.code=cc.creditcode ";

	/**
	 * 条件查询披露信息列表 @Title: investorPublishList @param infotype @param
	 * projectName @param startTime @param endTime @return List
	 * <InvestorPublishInfo> @throws
	 */
	@SuppressWarnings("unchecked")
	public List<InvestorPublishInfoResult> getInvestorPublishList(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<InvestorPublishInfoResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (!StringUtil.isNullOrEmpty(queryCondition.getInfotype())) {
				whereCase.append(" and ip.infotype = :infotype ");
			}
			if (!StringUtil.isNullOrEmpty(queryCondition.getNameAndProjectName())) {
				whereCase.append(" and ( cb.name like :name or fe.project_name like :name )  ");
			}
			if (null != queryCondition.getBeginTime()) {
				whereCase.append(" and ip.publish_time >= :beginTime ");
			}
			if (null != queryCondition.getEndTime()) {
				whereCase.append(" and ip.publish_time <= :endTime ");
			}
			whereCase.append(LEFTJOINsql);
			whereCase.append(" order by t1.publish_time desc ");
			// 拼接分頁sql
			whereCase.append(this.getPageInfos(queryCondition));

			Query query = entityManager.createNativeQuery(sqlGetList + whereCase.toString(),
					InvestorPublishInfoResult.class);

			if (!StringUtil.isNullOrEmpty(queryCondition.getInfotype())) {
				query.setParameter("infotype", queryCondition.getInfotype());
			}
			if (!StringUtil.isNullOrEmpty(queryCondition.getNameAndProjectName())) {
				query.setParameter("name", "%"+ queryCondition.getNameAndProjectName() + "%");
			}

			if (null != queryCondition.getBeginTime()) {
				query.setParameter("beginTime", queryCondition.getBeginTime());
			}
			if (null != queryCondition.getEndTime()) {
				query.setParameter("endTime", queryCondition.getEndTime());
			}
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
	/**
	 * 
	 * 根据条件查询总条数
	 */
	public Object getInvestorPublishListCount(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer countSql = new StringBuffer(" select count(*) as resultnum from ( ");
			
			StringBuffer whereCase = new StringBuffer();
			if (!StringUtil.isNullOrEmpty(queryCondition.getInfotype())) {
				whereCase.append(" and ip.infotype = :infotype ");
			}
			if (!StringUtil.isNullOrEmpty(queryCondition.getNameAndProjectName())) {
				whereCase.append(" and ( cb.name like :name or fe.project_name like :name )  ");
			}
			if (null != queryCondition.getBeginTime()) {
				whereCase.append(" and ip.publish_time >= :beginTime ");
			}
			if (null != queryCondition.getEndTime()) {
				whereCase.append(" and ip.publish_time <= :endTime ");
			}
			whereCase.append(LEFTJOINsql);
			whereCase.append(" order by t1.publish_time desc ");
			countSql.append(sqlGetList);
			countSql.append(whereCase.toString());
			countSql.append(") result ");
			// 拼接分頁sql

			Query query = entityManager.createNativeQuery(countSql.toString());

			if (!StringUtil.isNullOrEmpty(queryCondition.getInfotype())) {
				query.setParameter("infotype", queryCondition.getInfotype());
			}
			if (!StringUtil.isNullOrEmpty(queryCondition.getNameAndProjectName())) {
				query.setParameter("name", "%"+queryCondition.getNameAndProjectName() + "%");
			}

			if (null != queryCondition.getBeginTime()) {
				query.setParameter("beginTime", queryCondition.getBeginTime());
			}
			if (null != queryCondition.getEndTime()) {
				query.setParameter("endTime", queryCondition.getEndTime());
			}
			res = query.getSingleResult();

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
	 * 通过publishId查询披露信息 @Title: getInvestorPublishInfoById @param
	 * publishId @return InvestorPublishInfo @throws
	 */
	public InvestorPublishInfoResult getInvestorPublishInfoById(String publishId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		InvestorPublishInfoResult res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createNativeQuery(sqlFindById, InvestorPublishInfoResult.class);
			query.setParameter("publishId", publishId);
			res = (InvestorPublishInfoResult) query.getSingleResult();
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

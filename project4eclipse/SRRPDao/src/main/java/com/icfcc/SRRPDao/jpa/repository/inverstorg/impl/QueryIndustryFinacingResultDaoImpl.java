package com.icfcc.SRRPDao.jpa.repository.inverstorg.impl;

import io.netty.util.internal.StringUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.QueryIndustryFinacingResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class QueryIndustryFinacingResultDaoImpl extends BaseNativeQueryDao {//

	private String finacingEventSql = "SELECT '' as publish_count,'' as publish_amount,'' as rank,industrys  as industry,demand_amount as okamount,demandnum as okcount FROM (SELECT IFNULL(ROUND(SUM(amount),2), 0) demand_amount,LEFT (industry, 2) industrys,COUNT( t.info_id) demandnum FROM (SELECT rfe.info_id,rfe.exchange_ratio + IFNULL(rif.amount, 0) AS amount,rfe.enterprise_id,rfe.event_id, rfe.investorg_id FROM rp_finacing_event rfe LEFT JOIN (SELECT sum(rif.amount) AS amount,rif.info_id,rif.event_id FROM rp_investor_follow rif GROUP BY rif.info_id ) rif ON rfe.event_id = rif.event_id WHERE rfe.`status` > '32' AND rfe.`status` < '99' ";
	private String finacingDemandSql = "SELECT  '' as rank,'' as okcount, '' as okamount,industrys  as industry,demand_amount as  publish_amount,demandnum  as publish_count FROM ( SELECT IFNULL(SUM(amountCNYmax), 0) demand_amount,LEFT (industry, 2) industrys,COUNT(t.info_id) demandnum	FROM rp_finacing_demand t,rp_company_base_supplement c WHERE t.enterprise_id = c.enterprise_id AND t.`status` NOT IN ('00', '99') ";

	@SuppressWarnings("unchecked")
	public List<QueryIndustryFinacingResult> getFinacingEventResult(
			String beginTime, String endTime) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryIndustryFinacingResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			// 根据页面的开始时间进行查询
			if (!StringUtil.isNullOrEmpty(beginTime)) {
				whereCase
						.append(" AND date_format(rfe.operdate, '%Y-%m') >= :beginTime ");
			}
			// 根据页面的结束时间
			if (!StringUtil.isNullOrEmpty(endTime)) {
				whereCase  
						.append("AND date_format(rfe.operdate, '%Y-%m') <= :endTime ");
			}
			whereCase
					.append(" ) t,rp_company_base_supplement c WHERE	t.enterprise_id = c.enterprise_id GROUP BY industrys ORDER BY COUNT( t.info_id) DESC ) AS report_amount ");
			Query query = entityManager.createNativeQuery(finacingEventSql
					+ whereCase.toString(), QueryIndustryFinacingResult.class);
			if (!StringUtil.isNullOrEmpty(beginTime)) {
				query.setParameter("beginTime", beginTime);
			}
			if (!StringUtil.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime);
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

	@SuppressWarnings("unchecked")
	public List<QueryIndustryFinacingResult> getFinacingDemandResult(
			String beginTime, String endTime) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryIndustryFinacingResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			// GROUP BY s.industry
			// 根据页面的开始时间进行查询
			if (!StringUtil.isNullOrEmpty(beginTime)) {
				whereCase .append(" AND date_format(t.operdate, '%Y-%m') >= :beginTime ");
			}
			// 根据页面的结束时间
			if (!StringUtil.isNullOrEmpty(endTime)) {
				whereCase.append(" AND date_format(t.operdate, '%Y-%m') <= :endTime ");
			}
			whereCase.append(" GROUP BY industrys ORDER BY COUNT( t.info_id) DESC) AS report_amount ,(	SELECT @curRank \\:= 0 ,@preRank \\:= NULL) AS report_rank");
			Query query = entityManager.createNativeQuery(finacingDemandSql
					+ whereCase.toString(), QueryIndustryFinacingResult.class);
			if (!StringUtil.isNullOrEmpty(beginTime)) {
				query.setParameter("beginTime", beginTime);
			}
			if (!StringUtil.isNullOrEmpty(endTime)) {
				query.setParameter("endTime", endTime);
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
}

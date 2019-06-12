package com.icfcc.SRRPDao.jpa.repository.inverstorg.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

import io.netty.util.internal.StringUtil;

@Component
public class FinacingDemandInfoDaoImpl extends BaseNativeQueryDao {

	private String sql = "select fd.*,cb.name,cb.codetype,cb.code,cbs.industry,cc.score, '' as showflag,'' as event_id from ((rp_finacing_demand fd left join rp_company_base cb on fd.enterprise_id = cb.enterprise_id) left join  rp_company_base_supplement cbs on fd.enterprise_id=cbs.enterprise_id) left join  (select o.* from rp_company_creditscores o,(SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) a where o.cop_id = a.cop_id and a.cop_id = (select b.cop_id from (SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) b where a.creditcode = b.creditcode ORDER BY b.mt desc limit 1)) cc on cb.codetype=cc.creditype and cb.`code`=cc.creditcode where 1=1 ";
//	private String investLookSql = "SELECT de.* FROM (SELECT fd.info_id,fd.enterprise_id,fd.project_name,fd.amountmin,fd.amountmax,fd.currency,fd.follow_time,fd.finacing_turn,fd.sell,fd.scalemin,fd.scalemax,fd.operdate,fd.appoint_investor,fd.open,cb.NAME,cb.codetype,cb.CODE,cbs.industry,cc.score,IFNULL(fd.status,'01') status,CASE WHEN ( rfe.status IS NULL OR rfe.status IN ('12','22','32') ) THEN '0' ELSE '1' END showflag,rfe.event_id FROM rp_finacing_demand fd INNER JOIN rp_company_base cb ON fd.enterprise_id = cb.enterprise_id LEFT JOIN rp_finacing_event rfe ON  fd.info_id = rfe.info_id # LEFT JOIN rp_company_base_supplement cbs ON fd.enterprise_id = cbs.enterprise_id LEFT JOIN rp_company_creditscores cc ON cb.codetype = cc.creditype AND cb.code = cc.creditcode WHERE fd.STATUS NOT IN ('00','99') and fd.`open` = '0')  de WHERE ((follow_time >= CURDATE() AND STATUS='01' ) OR STATUS>'01') ";
	private String investLookSql = "SELECT de.* FROM (SELECT fd.info_id,fd.enterprise_id,fd.revoke_flag,fd.project_name,fd.amountmin,fd.amountmax,fd.currency,fd.follow_time,fd.finacing_turn,fd.sell,fd.scalemin,fd.scalemax,fd.operdate,fd.appoint_investor,fd.open,cb.NAME,cb.codetype,cb.CODE,cbs.industry,cc.score,IFNULL(fd.status,'01') status,CASE WHEN ( rfe.status IS NULL OR rfe.status IN ('12','22','32','99') ) THEN '0' ELSE '1' END showflag,rfe.event_id FROM rp_finacing_demand fd INNER JOIN rp_company_base cb ON fd.enterprise_id = cb.enterprise_id LEFT JOIN rp_finacing_event rfe ON  fd.info_id = rfe.info_id # LEFT JOIN rp_company_base_supplement cbs ON fd.enterprise_id = cbs.enterprise_id LEFT JOIN (select o.* from rp_company_creditscores o,(SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) a where o.cop_id = a.cop_id and a.cop_id = (select b.cop_id from (SELECT cop_id,creditcode,str_to_date(left(right(linejson,9),6),'%Y%m') mt FROM rp_company_creditscores) b where a.creditcode = b.creditcode ORDER BY b.mt desc limit 1)) cc ON cb.codetype = cc.creditype AND cb.code = cc.creditcode WHERE fd.STATUS NOT IN ('00','99') and fd.`open` = '0' and fd.revoke_flag='0')  de WHERE 1=1  ";
	@SuppressWarnings("unchecked")
	@Autowired(required = false)
	/**
	 * 查询所有融资需求信息列表
	 * 
	 * @param sql
	 * @param finacingDemand
	 * @return
	 */
	public List<FinacingDemandInfoResult> getFinacingDemandInfoList(
			QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<FinacingDemandInfoResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			List list1 = new ArrayList(); 

			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					for(Object industryStr:list){
						if(industryStr.toString().length()<4){
							list1.add(industryStr.toString());
						}
					}
					if(list1!=null && list1.size()>0){
						whereCase.append(" and (cbs.industry in( :industry) ");

						whereCase.append(" or left(cbs.industry,2) in( :industry1)) ");
					}else{
						whereCase.append(" and cbs.industry in( :industry) ");

					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinacingTurn())) {
					whereCase.append(" and fd.finacing_turn in (:finacingTurn )");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getAmountCode())) {
					String amountRange = RedisGetValue.getValueByCode(
							SRRPConstant.DD_FINACINGMONEY,
							queryCondition.getAmountCode());
					if (amountRange.indexOf("-") > 0) {
						String[] amoutArray = amountRange.split("-");
						whereCase.append(" and ((fd.amountmax BETWEEN "
								+ amoutArray[0].replaceAll("M", "") + " and "
								+ amoutArray[1].replaceAll("M", "")
								+ " ) or (fd.amountmin BETWEEN "
								+ amoutArray[0].replaceAll("M", "") + " and "
								+ amoutArray[1].replaceAll("M", "") + ")) ");
					} else {
						if (amountRange.indexOf("M") > 0) {
							String[] amoutArray = amountRange.split("M");
							whereCase.append(" and fd.amountmin >="
									+ amoutArray[0]);
						}
					}

				}

				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					
					
					String[] values = queryCondition.getStatus().toString().split(",");
					List list = java.util.Arrays.asList(values);
					if(list.size()==1){
						if(list.contains("88")){
							whereCase.append(" and  fd.revoke_flag='1' ");
						}else{
							whereCase.append(" and (fd.status in ( :status ) and fd.revoke_flag='0') ");
						}
					}else{
						if(list.contains("88")){
							whereCase.append("and (fd.status in ( :status ) and fd.revoke_flag='0') or  fd.revoke_flag='1' ");
						}else{
							whereCase.append(" and (fd.status in ( :status ) and fd.revoke_flag='0') ");
						}

					}
//					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getNameAndProjectName())) {
					whereCase
							.append(" and ( cb.name like :nameAndProjectName or fd.project_name like :nameAndProjectName )  ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getName())) {
					whereCase
							.append(" and cb.name like :name ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getProjectName())) {
					whereCase
							.append(" fd.project_name like :projectName  ");
				}
				if (null != queryCondition.getBeginTime()) {
					whereCase.append(" and fd.follow_time>= :beginTime ");
				}
				if (null != queryCondition.getEndTime()) {
					whereCase.append(" and fd.follow_time<= :endTime ");
				}
			}
			whereCase.append(" order by fd.operdate desc ");

			// 拼接分頁sql
			whereCase.append(this.getPageInfos(queryCondition));

			Query query = entityManager.createNativeQuery(
					sql + whereCase.toString(), FinacingDemandInfoResult.class);

			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("industry", list);
					if(list1!=null && list1.size()>0){
						query.setParameter("industry1", list1);
					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinacingTurn())) {
					String[] values = queryCondition.getFinacingTurn().toString().split(",");
					List list = java.util.Arrays.asList(values);
					query.setParameter("finacingTurn",list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					String[] values = queryCondition.getStatus().toString().split(",");
					List list = java.util.Arrays.asList(values);
					if(list.size()==1){
						if(!list.contains("88")){
							query.setParameter("status", list);
						}
					}else{
						query.setParameter("status", list);
					}
				}
					if (!StringUtil.isNullOrEmpty(queryCondition
						.getNameAndProjectName())) {
					query.setParameter("nameAndProjectName",
							"%" + queryCondition.getNameAndProjectName() + "%");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getName())) {
					query.setParameter("name",
							"%" + queryCondition.getName() + "%");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getProjectName())) {
					query.setParameter("projectName",
							"%" + queryCondition.getProjectName() + "%");
				}
				if (null != queryCondition.getBeginTime()) {
					query.setParameter("beginTime",
							queryCondition.getBeginTime());
				}
				if (null != queryCondition.getEndTime()) {
					query.setParameter("endTime", queryCondition.getEndTime());
				}
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
	 * 查询公开的融资信息列表
	 * 
	 * @Title: getOpenFinacingDemandInfos
	 * @param queryCondition
	 * @return List<FinacingDemandInfoResult> 返回类型
	 */
	@SuppressWarnings("unchecked")
	public List<FinacingDemandInfoResult> getOpenFinacingDemandInfos(
			QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<FinacingDemandInfoResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			List list1 = new ArrayList(); 
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					for(Object industryStr:list){
						if(industryStr.toString().length()<4){
							list1.add(industryStr.toString());
						}
					}
					if(list1!=null && list1.size()>0){
						whereCase.append(" and (de.industry in( :industry) ");

						whereCase.append(" or left(de.industry,2) in( :industry1) )");
					}else{
						whereCase.append(" and de.industry in( :industry) ");

					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinacingTurn())) {
					whereCase.append(" and de.finacing_turn in(:finacingTurn) ");
				}

				if (!StringUtil.isNullOrEmpty(queryCondition.getAmountCode())) {
					String amountRange = RedisGetValue.getValueByCode(
							SRRPConstant.DD_FINACINGMONEY,
							queryCondition.getAmountCode());
					if (amountRange.indexOf("-") > 0) {
						String[] amoutArray = amountRange.split("-");
						whereCase.append(" and ((de.amountmax BETWEEN "
								+ amoutArray[0].replaceAll("M", "") + " and "
								+ amoutArray[1].replaceAll("M", "")
								+ " ) or (de.amountmin BETWEEN "
								+ amoutArray[0].replaceAll("M", "") + " and "
								+ amoutArray[1].replaceAll("M", "") + ")) ");
					} else {
						if (amountRange.indexOf("M") > 0) {
							String[] amoutArray = amountRange.split("M");
							whereCase.append(" and de.amountmin >="
									+ amoutArray[0]);
						}
					}

				}

				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
						whereCase.append(" and de.status in ( :status )");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getName())) {
					whereCase.append(" and  de.name like :name   ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					whereCase
							.append(" and  de.project_name like :projectName   ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getNameAndProjectName())) {
					whereCase
							.append(" and ( de.name like :nameAndProjectName or de.project_name like :nameAndProjectName )  ");
				}
				if (null != queryCondition.getBeginTime()) {
					whereCase.append(" and de.follow_time>= :beginTime ");
				}
				if (null != queryCondition.getEndTime()) {
					whereCase.append(" and de.follow_time<= :endTime ");
				}
			}
			whereCase.append(" order by de.status,de.operdate desc ");

			// 拼接分頁sql
			whereCase.append(this.getPageInfos(queryCondition));
			String newsql = investLookSql;
			if (null != queryCondition.getUserName()) {
				newsql = newsql.replace("#", " AND rfe.operuser ='"
						+ queryCondition.getUserName() + "'");
			} else {
				newsql = newsql.replace("#", "");
			}
			Query query = entityManager.createNativeQuery(
					newsql + whereCase.toString(),
					FinacingDemandInfoResult.class);

			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("industry", list);
					if(list1!=null && list1.size()>0){
						query.setParameter("industry1", list1);
					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinacingTurn())) {
					String[] values = queryCondition.getFinacingTurn().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("finacingTurn",list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					String[] values = queryCondition.getStatus().toString().split(",");
					List list = java.util.Arrays.asList(values);
					query.setParameter("status", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getName())) {
					query.setParameter("name", "%"+queryCondition.getName() + "%");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					query.setParameter("projectName","%"+
							queryCondition.getProjectName() + "%");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getNameAndProjectName())) {
					query.setParameter("nameAndProjectName","%"+
							queryCondition.getNameAndProjectName() + "%");
				}
				if (null != queryCondition.getBeginTime()) {
					query.setParameter("beginTime",
							queryCondition.getBeginTime());
				}
				if (null != queryCondition.getEndTime()) {
					query.setParameter("endTime", queryCondition.getEndTime());
				}
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
	 * 根据条件查询总条数
	 * 
	 * @Title: getFinacingDemandInfoCount
	 * @param queryCondition
	 * @return Object 返回类型
	 */
	public Object getFinacingDemandInfoCount(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer countSql = new StringBuffer(
					" select count(*) as resultnum from ( ");
			countSql.append(sql);
			List list1 = new ArrayList(); 
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					
					for(Object industryStr:list){
						if(industryStr.toString().length()<4){
							list1.add(industryStr.toString());
						}
					}
					if(list1!=null && list1.size()>0){
						countSql.append(" and (cbs.industry in( :industry) ");

						countSql.append(" or left(cbs.industry,2) in( :industry1)) ");
					}else{
						countSql.append(" and cbs.industry in( :industry) ");

					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinacingTurn())) {
					countSql.append(" and fd.finacing_turn in( :finacingTurn) ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getAmountCode())) {
					String amountRange = RedisGetValue.getValueByCode(
							SRRPConstant.DD_FINACINGMONEY,
							queryCondition.getAmountCode());
					if (amountRange.indexOf("-") > 0) {
						String[] amoutArray = amountRange.split("-");
						countSql.append(" and ((fd.amountmax BETWEEN "
								+ amoutArray[0].replaceAll("M", "") + " and "
								+ amoutArray[1].replaceAll("M", "")
								+ " ) or (fd.amountmin BETWEEN "
								+ amoutArray[0].replaceAll("M", "") + " and "
								+ amoutArray[1].replaceAll("M", "") + ")) ");
					} else {
						if (amountRange.indexOf("M") > 0) {
							String[] amoutArray = amountRange.split("M");
							countSql.append(" and fd.amountmin >="
									+ amoutArray[0]);
						}
					}

				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					countSql.append(" and fd.status in (:status) ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getNameAndProjectName())) {
					countSql.append(" and ( cb.name like :nameAndProjectName or fd.project_name like :nameAndProjectName ) ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getName())) {
					countSql.append(" and  de.name like :name   ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					countSql.append(" and  de.project_name like :projectName   ");
				}
				if (null != queryCondition.getBeginTime()) {
					countSql.append(" and fd.follow_time>= :beginTime ");
				}
				if (null != queryCondition.getEndTime()) {
					countSql.append(" and fd.follow_time<= :endTime ");
				}
			}
			countSql.append(") result ");
			Query query = entityManager.createNativeQuery(countSql.toString());
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("industry", list);
					if(list1!=null && list1.size()>0){
						query.setParameter("industry1", list1);
					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinacingTurn())) {
					String[] values = queryCondition.getFinacingTurn().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("finacingTurn",list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					String[] values = queryCondition.getStatus().toString().split(",");
					List list = java.util.Arrays.asList(values);
					query.setParameter("status", list);
				}
					if (!StringUtil.isNullOrEmpty(queryCondition
						.getNameAndProjectName())) {
					query.setParameter("nameAndProjectName",
							"%" + queryCondition.getNameAndProjectName() + "%");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getName())) {
					query.setParameter("name","%" + queryCondition.getName() + "%");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getProjectName())) {
					query.setParameter("projectName","%" + queryCondition.getProjectName() + "%");
				}
				if (null != queryCondition.getBeginTime()) {
					query.setParameter("beginTime",
							queryCondition.getBeginTime());
				}
				if (null != queryCondition.getEndTime()) {
					query.setParameter("endTime", queryCondition.getEndTime());
				}
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
	 * 获取公开的融资机构信息
	 * 
	 * @Title: getOpenFinacingDemandInfoCount
	 * @param queryCondition
	 * @return Object 返回类型
	 */
	public Object getOpenFinacingDemandInfoCount(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			// 拼接分頁sql
			String newsql = investLookSql;
			if (null != queryCondition.getUserName()) {
				newsql = newsql.replace("#", " AND rfe.operuser ='"
						+ queryCondition.getUserName() + "'");
			} else {
				newsql = newsql.replace("#", "");
			}
			StringBuffer countSql = new StringBuffer(
					" select count(*) as resultnum from ( ");
			countSql.append(newsql);
			List list1 = new ArrayList(); 
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getAmountCode())) {
					String amountRange = RedisGetValue.getValueByCode(
							SRRPConstant.DD_FINACINGMONEY,
							queryCondition.getAmountCode());
					if (amountRange.indexOf("-") > 0) {
						String[] amoutArray = amountRange.split("-");
						countSql.append(" and ((de.amountmax BETWEEN "
								+ amoutArray[0].replaceAll("M", "") + " and "
								+ amoutArray[1].replaceAll("M", "")
								+ " ) or (de.amountmin BETWEEN "
								+ amoutArray[0].replaceAll("M", "") + " and "
								+ amoutArray[1].replaceAll("M", "") + ")) ");
					} else {
						if (amountRange.indexOf("M") > 0) {
							String[] amoutArray = amountRange.split("M");
							countSql.append(" and de.amountmin >="
									+ amoutArray[0]);
						}
						System.out
								.println("dd:finacingmoney format is error ,please check!");
					}

				}
				
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					
					for(Object industryStr:list){
						if(industryStr.toString().length()<4){
							list1.add(industryStr.toString());
						}
					}
					if(list1!=null && list1.size()>0){
						countSql.append(" and (de.industry in( :industry) ");
						countSql.append(" or left(de.industry,2) in( :industry1)) ");
					}else{
						countSql.append(" and de.industry in( :industry) ");

					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinacingTurn())) {
					countSql.append(" and de.finacing_turn in(:finacingTurn) ");
				}
					if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
						countSql.append(" and de.status in ( :status )");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getNameAndProjectName())) {
					countSql.append(" and ( de.name like :nameAndProjectName or de.project_name like :nameAndProjectName ) ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getName())) {
					countSql.append(" and  de.name like :name  ");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getProjectName())) {
					countSql.append(" and  de.project_name like :projectName  ");
				}
				if (null != queryCondition.getBeginTime()) {
					countSql.append(" and de.follow_time>= :beginTime ");
				}
				if (null != queryCondition.getEndTime()) {
					countSql.append(" and de.follow_time<= :endTime ");
				}
			}
			countSql.append(") result ");
			Query query = entityManager.createNativeQuery(countSql.toString());
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getIndustry())) {
					String[] values = queryCondition.getIndustry().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("industry", list);
					if(list1!=null && list1.size()>0){
						query.setParameter("industry1", list1);
					}
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getFinacingTurn())) {
					String[] values = queryCondition.getFinacingTurn().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("finacingTurn",list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					String[] values = queryCondition.getStatus().toString().split(",");
					List list = java.util.Arrays.asList(values);
					query.setParameter("status", list);
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getNameAndProjectName())) {
					query.setParameter("nameAndProjectName",
							"%" + queryCondition.getNameAndProjectName() + "%");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getName())) {
					query.setParameter("name",
							 "%" + queryCondition.getName() + "%");
				}
				if (!StringUtil.isNullOrEmpty(queryCondition
						.getProjectName())) {
					query.setParameter("projectName",
							"%" + queryCondition.getProjectName() + "%");
				}
				if (null != queryCondition.getBeginTime()) {
					query.setParameter("beginTime",
							queryCondition.getBeginTime());
				}
				if (null != queryCondition.getEndTime()) {
					query.setParameter("endTime", queryCondition.getEndTime());
				}
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

	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> sumAmountByEnterpriseId(String enterpriseId,
			Date start, Date end, Integer limit) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Map<String, BigDecimal> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer sql = new StringBuffer(
					"select t.enterprise_id, sum(t.amountmax) from rp_finacing_demand t , rp_company_base t1 where t.enterprise_id=t1.enterprise_id ");
			if (StringUtils.isNotEmpty(enterpriseId)) {
				sql.append(" and t.enterprise_id=:enterpriseId");
			}
			if (start != null) {
				sql.append(" and t.operdate >=:start");
			}
			if (end != null) {
				sql.append(" and t.operdate <=:end");
			}
			sql.append(" group by t.enterprise_id");
			sql.append(" order by sum(t.amount) desc");
			if (limit != null) {
				sql.append(" limit " + limit);
			}
			Query q = entityManager.createNativeQuery(sql.toString());
			if (StringUtils.isNotEmpty(enterpriseId)) {
				q.setParameter("enterpriseId", enterpriseId);
			}
			if (start != null) {
				q.setParameter("start", start);
			}
			if (end != null) {
				q.setParameter("end", end);
			}
			List<Object[]> tres = (List<Object[]>) q.getResultList();
			res = new HashMap<String, BigDecimal>();
			for (Object[] o : tres) {
				res.put((String) o[0], (BigDecimal) o[1]);
			}
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
	public Map<String, BigDecimal> sumAmountByArea(String area, Date start,
			Date end, Integer limit) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Map<String, BigDecimal> res = new HashMap<String, BigDecimal>();
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer sql = new StringBuffer(
					"select t1.rearea,sum(t.amountmax) from rp_finacing_demand t , rp_company_base t1 where t.enterprise_id=t1.enterprise_id ");
			if (StringUtils.isNotEmpty(area)) {
				sql.append(" and t.rearea=:area");
			}
			if (start != null) {
				sql.append(" and t.operdate >=:start");
			}
			if (end != null) {
				sql.append(" and t.operdate <=:end");
			}
			sql.append(" group by t1.rearea");
			sql.append(" order by sum(t.amount) desc");
			if (limit != null) {
				sql.append(" limit " + limit);
			}
			Query q = entityManager.createNativeQuery(sql.toString());
			if (StringUtils.isNotEmpty(area)) {
				q.setParameter("area", area);
			}
			if (start != null) {
				q.setParameter("start", start);
			}
			if (end != null) {
				q.setParameter("end", end);
			}
			List<Object[]> tres = (List<Object[]>) q.getResultList();
			for (Object[] o : tres) {
				res.put((String) o[0], (BigDecimal) o[1]);
			}
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
     * 机构下的基金用户数
     * @param  infoId
     * @return 
     */
	public Object getInvestorUserCount(String orgNo) {
		String hqlString = "select count(*) from system_user where USER_DESC1 = '1' and org_id = '" + orgNo + "'"; 
		
		
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer countSql = new StringBuffer("select count(*) from system_user where USER_DESC1 = '1' and org_id = '" + orgNo + "'");
			Query query = entityManager.createNativeQuery(countSql.toString());
			res = query.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
		
		
		
	};

}

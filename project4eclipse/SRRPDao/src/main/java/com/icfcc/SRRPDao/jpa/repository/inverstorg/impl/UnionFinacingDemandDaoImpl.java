package com.icfcc.SRRPDao.jpa.repository.inverstorg.impl;

import io.netty.util.internal.StringUtil;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.QueryFinacingDemandResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class UnionFinacingDemandDaoImpl extends BaseNativeQueryDao {

	private String SQLById = "select fd.info_id,fd.project_name,fd.close_reason,fd.revoke_flag,fd.update_flag,fd.open,fd.enterprise_id,fd.finacing_turn,fd.currency,fd.amountmin,fd.amountmax,fd.sell,fd.scalemin,fd.scalemax,fd.operdate,fd.follow_time,fd.status from rp_finacing_demand fd where fd.enterprise_id= :enterpriseId ";

	/**
	 * 根据企业id查询企业的融资需求的详细信息 <多条件进行查询融资需求的详细信息>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<QueryFinacingDemandResult> findFinacingDemandListById(
			QueryCondition queryCondition, String enterpriseId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryFinacingDemandResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer("");
			if (null != queryCondition) {
				// 判断项目名是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					whereCase.append(" and fd.project_name like :projectName");
				}
				// 判断状态是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					if("88".equals(queryCondition.getStatus())){
						whereCase.append(" and  fd.revoke_flag='1' ");
					}else if("89".equals(queryCondition.getStatus())){
						whereCase.append(" and  fd.revoke_flag='2' ");
					}else if("99".equals(queryCondition.getStatus())){
						whereCase.append(" and fd.status = :status and fd.revoke_flag='0' ");
					}else{
						 whereCase.append(" and fd.status = :status");
					}
				}
				// 判断开始时间是否为空
				if (queryCondition.getBeginTime() != null) {
					whereCase.append(" and date(fd.operdate) >= :beginTime");
				}
				// 判断结束时间是否为空
				if (queryCondition.getEndTime() != null) {
					whereCase.append(" and date(fd.operdate) <= :endTime");
				}
			}
			whereCase.append(" order by fd.operdate desc,fd.follow_time desc");
			// 添加分页的代码
			whereCase.append(this.getPageInfos(queryCondition));
			Query query = entityManager.createNativeQuery(
					SQLById + whereCase.toString(),
					QueryFinacingDemandResult.class);
			query.setParameter("enterpriseId", enterpriseId);
			if (null != queryCondition) {
				// 判断项目名称是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					query.setParameter("projectName",
							"%" + queryCondition.getProjectName() + "%");
				}
				// 判断状态是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					if(!"88".equals(queryCondition.getStatus())&& !"89".equals(queryCondition.getStatus())){
						query.setParameter("status", queryCondition.getStatus());
					}
				}
				// 判断开始时间是否为空
				if (queryCondition.getBeginTime() != null) {
					query.setParameter("beginTime",
							queryCondition.getBeginTime());
				}
				// 判断结束时间是否为空
				if (queryCondition.getEndTime() != null) {
					query.setParameter("endTime", queryCondition.getEndTime());
				}
			}
			res = (List<QueryFinacingDemandResult>) query.getResultList();
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
	 * 查询所有融资需求信息总数
	 * 
	 * @param sql
	 * @param finacingDemand
	 * @return
	 */
	public Object getFinacingDemandInfoCount(QueryCondition queryCondition,
			String enterpriseId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer coutSql = new StringBuffer(
					" select count(*) as resultnum from ( ");
			coutSql.append(SQLById);
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					coutSql.append(" and fd.project_name like :projectName");
				}
				// 判断状态是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					if("88".equals(queryCondition.getStatus())){
						coutSql.append(" and fd.status = :status and fd.revoke_flag='1' ");
					}else if("99".equals(queryCondition.getStatus())){
						coutSql.append(" and fd.status = :status and fd.revoke_flag !='1' ");
					}else {
						coutSql.append(" and fd.status = :status");
					}
				}
				// 判断开始时间是否为空
				if (queryCondition.getBeginTime() != null) {
					coutSql.append(" and date(fd.operdate) >= :beginTime");
				}
				// 判断结束时间是否为空
				if (queryCondition.getEndTime() != null) {
					coutSql.append(" and date(fd.operdate) <= :endTime");
				}
				coutSql.append(" order by fd.operdate desc,fd.follow_time desc");
			}
			coutSql.append(" ) result ");
			Query query = entityManager.createNativeQuery(coutSql.toString());
			query.setParameter("enterpriseId", enterpriseId);
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					query.setParameter("projectName",
							queryCondition.getProjectName());
				}
				// 判断状态是否为空
				if (!StringUtil.isNullOrEmpty(queryCondition.getStatus())) {
					if("88".equals(queryCondition.getStatus())){
						query.setParameter("status", "99");
					}else{
						query.setParameter("status", queryCondition.getStatus());
					}
				}
				// 判断开始时间是否为空
				if (queryCondition.getBeginTime() != null) {
					query.setParameter("beginTime",
							queryCondition.getBeginTime());
				}
				// 判断结束时间是否为空
				if (queryCondition.getEndTime() != null) {
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
}

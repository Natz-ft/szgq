package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import io.netty.util.internal.StringUtil;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.EventBeanForCharge;
import com.icfcc.SRRPDao.jpa.entity.enterprise.EventBeanForOrg;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryInvestorFinacingEventResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.credit.platform.util.SRRPConstant;

@Component
public class FinacingEventDaoImpl extends BaseNativeQueryDao {
	private String remindPushStatus = "'" + SRRPConstant.FINANCPHASE44 + "'";
	private String remindLoanStatus = "'" + SRRPConstant.FINANCPHASE31 + "','"
			+ SRRPConstant.FINANCPHASE41 + "','" + SRRPConstant.FINANCPHASE43
			+ "'";
	private String pushStatus = "'" + SRRPConstant.FINANCPHASE42 + "','"
			+ SRRPConstant.FINANCPHASE43 + "','" + SRRPConstant.FINANCPHASE44
			+ "'" + ",'" + SRRPConstant.FINANCPHASE45 + "'";
	private String loanStatus = "'" + SRRPConstant.FINANCPHASE31 + "','"
			+ SRRPConstant.FINANCPHASE41 + "','" + SRRPConstant.FINANCPHASE42
			+ "','" + SRRPConstant.FINANCPHASE43 + "'";
	private String unionSQL = "select fe.event_id,fe.info_id,fe.investorg_id,fe.currency,fe.amount,fe.status,fe.maildate,fe.operdate,fe.project_name,e.finance_stage,e.name,e.org_type,f.finacing_turn ,f.open from rp_finacing_event fe ,rp_investor e,rp_finacing_demand f where f.status!='99' and e.investor_id=fe.investorg_id and f.info_id=fe.info_id and fe.status in ('31','41','42','43','44','45') and fe.enterprise_id= :enterpriseId";
	private String investSqlFirst = "SELECT 	de.*,cc.score  FROM (	SELECT	c.event_id,c.info_id,c.enterprise_id,c.investorg_id,c.status,c.currency,c.amount,c.ratio,c.mailuser,c.maildate,c.operuser,c.operdate,c.exchange_ratio,c.publish_flag,c.investor_level,c.fund_id,c.schedule,c.sche_date,b.project_name, b.follow_time,	b.multi,b.open,b.revoke_flag ,	b.amountmin,b.amountmax,	d.`name` enterprise_name,	d.codetype,	d. CODE,b.status  demand_status	,b.operdate demand_operdate,b.currency rfd_currency,d.reg_currency  reg_currency FROM	rp_finacing_demand b,	rp_finacing_event c,	rp_company_base d WHERE 	b.enterprise_id = c.enterprise_id	AND c.info_id = b.info_id 	AND b.`status` != '00'  AND d.enterprise_id = c.enterprise_id ";
	private String investSqlSecond = " ) de LEFT JOIN rp_company_creditscores cc ON de.`code` = cc.creditcode AND de.codetype = cc.creditype ORDER BY 	de.revoke_flag ASC,de.operdate DESC ,de. STATUS ASC";
	private String countByStatus = "SELECT	opertype,	COUNT(opertype)FROM	(		SELECT			CASE		WHEN a.`status` = '01' 	AND a.operuser =?1 	AND d.`status` != '99' and d.revoke_flag='0'	 THEN			'focus' WHEN a.`status` = '11'		AND a.operuser =?2 THEN			'talks'		WHEN a.`status` = '21'		AND a.operuser =?3 THEN			'investor'		WHEN a.`status` IN ("
			+ remindLoanStatus
			+ ")		AND a.operuser =?4 THEN			'loan'		WHEN a.`status` IN ("
			+ remindPushStatus
			+ ") AND a.operuser =?5 THEN			'push'	WHEN a.`status` in ('42') AND a.operuser =?6 THEN 'unPushed'	END  'opertype'		FROM	rp_finacing_demand d,rp_finacing_event a,			rp_company_base b		WHERE			a.enterprise_id = b.enterprise_id		AND a.`status` != '00'		AND a.`status` != '99' and a.info_id =d.info_id		AND a.investorg_id =?7 ) c GROUP BY	opertype";

	/**
	 * fe.event_id 事件的id fe.info_id 信息的id fe.investorg_id 机构的id fe.currency货币
	 * fe.maildate 投资时间 fe.amount金额 fe.operdate操作时间 fe.project_name项目名称
	 * e.name机构名称 e.finance_stage投资阶段 f.finacing_turn融资轮次
	 */

	/**
	 * 根据传递的页面参数进行多条件查询
	 * 
	 * @param projectName
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<QueryInvestorFinacingEventResult> listUnionFinacingEventByWhereCase(
			QueryCondition queryCondition, String enterpriseId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryInvestorFinacingEventResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			// 模拟字符串进行拼接
			// 模拟字符串项目名称模糊查询
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					whereCase.append(" and fe.project_name like :projectName");
				}

				// 根据页面的开始时间进行查询
				if (queryCondition.getBeginTime() != null) {
					whereCase.append(" and fe.operdate >= :beginTime");
				}
				// 根据页面的结束时间
				if (queryCondition.getEndTime() != null) {
					whereCase.append(" and fe.operdate <= :endTime");
				}
				whereCase
						.append(" group by fe.operuser,fe.info_id  order by fe.operdate desc,fe.status desc ");

			}
			whereCase.append(this.getPageInfos(queryCondition));
			Query query = entityManager.createNativeQuery(
					unionSQL + whereCase.toString(),
					QueryInvestorFinacingEventResult.class);
			// 创建预查询

			if (null != queryCondition) {
				query.setParameter("enterpriseId", enterpriseId);
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					query.setParameter("projectName",
							"%" + queryCondition.getProjectName() + "%");
				}
				if (queryCondition.getBeginTime() != null) {
					query.setParameter("beginTime",
							queryCondition.getBeginTime());
				}
				if (queryCondition.getEndTime() != null) {
					Date endTime = queryCondition.getEndTime();
					Calendar c = Calendar.getInstance();
					c.setTime(endTime);
					c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
					Date newEndTime = c.getTime();
					query.setParameter("endTime", newEndTime);
				}
			}
			res = (List<QueryInvestorFinacingEventResult>) query
					.getResultList();
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
	 * 查询企业所有融资事件信息总数
	 * 
	 * @param sql
	 * @param finacingDemand
	 * @return
	 */
	public Object getFinacingEventInfoCount(QueryCondition queryCondition,
			String enterpriseId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer coutSql = new StringBuffer(
					" select count(*) as resultnum from ( ");
			coutSql.append(unionSQL);
			if (null != queryCondition) {
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					coutSql.append(" and fe.project_name like :projectName");
				}
				// 根据页面的开始时间进行查询
				if (queryCondition.getBeginTime() != null) {
					coutSql.append(" and fe.operdate >= :beginTime ");
				}
				// 根据页面的结束时间
				if (queryCondition.getEndTime() != null) {
					coutSql.append(" and :endTime >= fe.operdate");
				}
			}
			coutSql.append(" group by fe.operuser,fe.info_id ) result ");
			Query query = entityManager.createNativeQuery(coutSql.toString());
			if (null != queryCondition) {
				query.setParameter("enterpriseId", enterpriseId);
				if (!StringUtil.isNullOrEmpty(queryCondition.getProjectName())) {
					query.setParameter("projectName",
					// 缺少模糊查询
							"%" + queryCondition.getProjectName() + "%");
				}
				if (queryCondition.getBeginTime() != null) {
					query.setParameter("beginTime",
							queryCondition.getBeginTime());
				}
				if (queryCondition.getEndTime() != null) {
					Date endTime = queryCondition.getEndTime();
					Calendar c = Calendar.getInstance();
					c.setTime(endTime);
					c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
					Date newEndTime = c.getTime();
					query.setParameter("endTime", newEndTime);
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
	public List<EventBeanForOrg> findForOrg(String orgId, String status,
			Integer page, Integer size) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<EventBeanForOrg> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			int start = 0;
			StringBuffer sql = new StringBuffer(
					"select t.event_id,t.status,t.enterprise_id,t.amount,t.currency,t.maildate,t.operdate,t1.name enterprise_name from rp_finacing_event t,rp_company_base t1 where t.enterprise_id=t1.enterprise_id ");
			if (StringUtils.isNotEmpty(orgId)) {
				sql.append(" and t.investorg_id = :orgId ");
			}
			if (StringUtils.isNotEmpty(status)) {
				sql.append(" and t.status = :status ");
			}
			sql.append(" order by t.maildate desc ");
			if (page != null && size != null) {
				sql.append(" limit :start ,:size");
			}
			Query q = entityManager.createNativeQuery(sql.toString(),
					EventBeanForOrg.class);
			if (StringUtils.isNotEmpty(orgId)) {
				q.setParameter("orgId", orgId);
			}
			if (StringUtils.isNotEmpty(status)) {
				q.setParameter("status", status);
			}
			if (page != null && size != null) {
				start = page * size;
				q.setParameter("start", start);
				q.setParameter("size", size);
			}
			res = q.getResultList();
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
	 * 机构首页待办查询列表
	 * 
	 * @param queryCondition
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<EventBeanForOrg> findForInvestor(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<EventBeanForOrg> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer("");
			StringBuffer whereCaseOperUser = new StringBuffer("");
			if (StringUtils.isNotEmpty(queryCondition.getInvestorId())) {
				whereCase.append(" and c.investorg_id = :investorId ");
			}
			if (StringUtils.isNotEmpty(queryCondition.getOperType())) {
				whereCase.append(" and c.status in (  ");
				switch (queryCondition.getOperType()) {
				case "focus":
					whereCaseOperUser.append(" and c.operuser = :operUser and b.`status` !='99' and b.`revoke_flag` ='0'");
					whereCase.append("'" + SRRPConstant.FINANCPHASE01 + "'");
					break;
				case "talks":
					whereCaseOperUser.append(" and c.operuser = :operUser ");
					whereCase.append("'" + SRRPConstant.FINANCPHASE11 + "'");
					break;
				case "investor":
					whereCaseOperUser.append(" and c.operuser = :operUser  ");
					whereCase.append("'" + SRRPConstant.FINANCPHASE21 + "'");
					break;
				case "loan":
					whereCaseOperUser.append(" and c.operuser = :operUser ");
					whereCase.append(loanStatus);
					break;
				case "push":
					whereCaseOperUser.append(" and c.operuser = :operUser ");
					whereCase.append(pushStatus);
					break;
				default:
					whereCase.append("'" + SRRPConstant.FINANCPHASE01 + "'");
					whereCaseOperUser.append(" and b.revoke_flag = '1'");
					break;
				}
				whereCase.append(" )");
			}
			if (queryCondition.getCurPage() >= 1
					&& queryCondition.getMaxSize() > 0) {
				whereCase.append(this.getPageInfos(queryCondition));
			}
			Query q = entityManager.createNativeQuery(investSqlFirst
					+ whereCase.toString() + whereCaseOperUser.toString()
					+ investSqlSecond, EventBeanForOrg.class);
			if (StringUtils.isNotEmpty(queryCondition.getInvestorId())) {
				q.setParameter("investorId", queryCondition.getInvestorId());
			}
			if (StringUtils.isNotEmpty(queryCondition.getOperType())) {
				switch (queryCondition.getOperType()) {
				case "focus":
					q.setParameter("operUser", queryCondition.getUserId());
					break;
				case "talks":
					q.setParameter("operUser", queryCondition.getUserId());
					break;
				case "investor":
					q.setParameter("operUser", queryCondition.getUserId());
					break;
				case "loan":
					q.setParameter("operUser", queryCondition.getUserId());
					break;
				case "push":
					q.setParameter("operUser", queryCondition.getUserId());
					break;
				}
			}
			res = q.getResultList();
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
	 * @Title: findCount @Description: 获取符合条件的条数 @param @param queryCondition @param @return
	 *         设定文件 @return int 返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	public Map<String, BigInteger> findCount(QueryCondition queryCondition) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Map<String, BigInteger> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer countSql = new StringBuffer(countByStatus);
			Query query = entityManager.createNativeQuery(countSql.toString());
			if (StringUtils.isNotEmpty(queryCondition.getInvestorId())) {
				query.setParameter(1, queryCondition.getUserId());
				query.setParameter(2, queryCondition.getUserId());
				query.setParameter(3, queryCondition.getUserId());
				query.setParameter(4, queryCondition.getUserId());
				query.setParameter(5, queryCondition.getUserId());
				query.setParameter(6, queryCondition.getUserId());
				query.setParameter(7, queryCondition.getInvestorId());

			}
			List<Object[]> tres = (List<Object[]>) query.getResultList();
			res = new HashMap<String, BigInteger>();
			for (Object[] o : tres) {
				res.put((String) o[0], (BigInteger) o[1]);
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
	public List<EventBeanForCharge> findForCharge(String trade, String turn,
			Double amountTop, Double amountBottom, String status, Integer page,
			Integer size) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<EventBeanForCharge> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			int start = 0;
			StringBuffer sql = new StringBuffer(
					"select t2.info_id,t2.enterprise_id,t2.project_name,t3.name enterprise_name,t4.industry trade,t2.finacing_turn turn,t2.amountmin,t2.amountmax ,t2.scalemin,t2.scalemax sell,t2.operdate publish_time,t2.follow_time focus_time,t2.status from rp_finacing_demand t2,rp_company_base t3,rp_company_base_supplement t4 where  t2.enterprise_id=t3.enterprise_id and t2.enterprise_id=t4.enterprise_id");
			if (StringUtils.isNotEmpty(trade)) {
				sql.append(" and t4.industry=:trade ");
			}
			if (StringUtils.isNotEmpty(turn)) {
				sql.append(" and t2.finacing_turn=:turn ");
			}
			if (amountTop != null) {
				sql.append(" and t2.amount <=:amountTop ");
			}
			if (amountBottom != null) {
				sql.append(" and t2.amount >=:amountBottom");
			}
			if (StringUtils.isNotEmpty(turn)) {
				sql.append(" and t2.status=:status ");
			}
			sql.append(" order by t2.operdate desc");
			if (page != null && size != null) {
				sql.append(" limit :start ,:size");
			}
			Query q = entityManager.createNativeQuery(sql.toString(),
					EventBeanForCharge.class);
			if (StringUtils.isNotEmpty(trade)) {
				q.setParameter("trade", trade);
			}
			if (StringUtils.isNotEmpty(turn)) {
				q.setParameter("turn", turn);
			}
			if (amountTop != null) {
				q.setParameter("amountTop", amountTop);
			}
			if (amountBottom != null) {
				q.setParameter("amountBottom", amountBottom);
			}
			if (StringUtils.isNotEmpty(status)) {
				q.setParameter("status", status);
			}
			if (page != null && size != null) {
				start = page * size;
				q.setParameter("start", start);
				q.setParameter("size", size);
			}
			res = q.getResultList();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		} finally {
			this.closeEntityManager(entityManager);
		}
		return res;
	}

	public Object countByOrgIdStatus(String status, String investOrgId) {
		String sql = "select count(*) from(select d.info_id  from rp_finacing_event d,rp_finacing_demand fd where d.status >= :status and d.status<>99  and fd.status<> 99 and d.info_id=fd.info_id and d.investorg_id = :investOrgId) dd";
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query q = entityManager.createNativeQuery(sql);
			q.setParameter("status", status);
			q.setParameter("investOrgId", investOrgId);
			res = q.getSingleResult();
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityTransaction.rollback();
		}finally{
			this.closeEntityManager(entityManager);
		}
		return res;
	}
}

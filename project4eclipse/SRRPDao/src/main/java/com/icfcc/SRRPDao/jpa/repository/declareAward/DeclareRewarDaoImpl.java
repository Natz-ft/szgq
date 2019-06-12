package com.icfcc.SRRPDao.jpa.repository.declareAward;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarInfor;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarSearshBean;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.credit.platform.util.SRRPConstant;

import io.netty.util.internal.StringUtil;

@Component
public class DeclareRewarDaoImpl extends BaseNativeQueryDao {

	private String QUERY_DECLAREREWAR_SQL = "SELECT  a.loan_id loanid,a.loandate loandate,IF(b.rearea = @rearea,@rownum \\:= @rownum + 1,@rownum \\:= 1)AS rownum,SUM(a.amount)*10000 amount,a.currency as currency ,a.org_id as investorgid,a.event_id as eventid,a.info_id as inforid,@rearea \\:= b.rearea as rearea,b.code as code,b.name as name FROM (SELECT @rownum \\:= 0,@rearea \\:= NULL) r,rp_investor_loan a,rp_company_base b where a.enterprise_id = b.enterprise_id ";
	private String MYDECLARE_LISTS_SQL = "select  * from rp_declare_award t1 where 1=1 and t1.investor_id = :investorId ";
	private String MANAGE_DECLARE_LISTS_SQL = "select  * from rp_declare_award t1 where 1=1 and declare_status != '02' ";
	private String DECLARE_ISEXIT_SQL = "select  COUNT(1)as count  from rp_declare_award t1 where 1=1  and  declare_status != '02' and t1.investor_id = :investorId";
	private String QUERY_DECLAREREWARLoan_SQL = "SELECT  a.loan_id loanid,a.loandate loandate,IF(b.rearea = @rearea,@rownum \\:= @rownum + 1,@rownum \\:= 1)AS rownum,a.amount*10000 amount,a.currency as currency ,a.org_id as investorgid,a.event_id as eventid,a.info_id as inforid,@rearea \\:= b.rearea as rearea,b.code as code,b.name as name FROM (SELECT @rownum \\:= 0,@rearea \\:= NULL) r,rp_investor_loan a,rp_company_base b where a.enterprise_id = b.enterprise_id ";

	
	SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd"); 

	/**
	 * 判断是否申报过 @Title: investorPublishList @param infotype @param
	 * projectName @param startTime @param endTime @return List
	 * <InvestorPublishInfo> @throws
	 */
	@SuppressWarnings("unchecked")
	public Object FindDeclareRewarInforIsExitve(DeclareRewarInfor declare) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;

		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (null != declare) {
				
				if (!StringUtil.isNullOrEmpty(declare.getCompanyAddress())) {
					whereCase.append(" and t1.company_address = :rearea ");
				}
				if (declare.getDeclareBeginTime()!= null) {
					whereCase.append(" and t1.declare_begin_time =:beginTime ");
				}
				
				if (declare.getDeclareEndTime() != null) {
					whereCase.append(" and t1.declare_end_time=:endTime ");
				}
				
			}
			// 拼接分頁sql
			Query query = entityManager.createNativeQuery(DECLARE_ISEXIT_SQL + whereCase.toString());
			
			query.setParameter("investorId",declare.getInvestorId());
			if (null != declare) {
				if (!StringUtil.isNullOrEmpty(declare
						.getCompanyAddress())) {
					query.setParameter("rearea", declare.getCompanyAddress());
				}
				if (null != declare.getDeclareBeginTime()) {
					String  beginDate=sDateFormat.format(declare.getDeclareBeginTime());
					query.setParameter("beginTime",beginDate);
				}
				if (null != declare.getDeclareEndTime()) {
					String  endDate=sDateFormat.format(declare.getDeclareEndTime());
					query.setParameter("endTime", endDate);
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
	 * 查询奖励申报信息
	 * 
	 * @param sql
	 * @param DeclareRewarSearshBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DeclareAwardResult> queryDeclareRewarInfor(
			DeclareRewarSearshBean reward,String type) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<DeclareAwardResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			String inforSql_1="select * from ( ";
			if (null != reward) {
				
				if (!StringUtil.isNullOrEmpty(reward
						.getInvestorId())) {
					whereCase.append(" and a.org_id =:orgId ");
				}
				if("2".equals(type) || "3".equals(type)){
					if (!StringUtil.isNullOrEmpty(reward.getRearea())) {
						whereCase.append(" and b.rearea =:rearea ");
					}
				}
				
				if (reward
						.getDeclare_begin_time()!= null) {
					whereCase.append(" and a.loandate >=:beginTime ");
				}
				if (reward.getDeclare_end_time()!= null) {
					whereCase.append(" and a.loandate <=:endTime ");
				}
				
			}
			
			if("1".equals(type)){
				String inforSql_2=" ) a where a.rownum=1 ";

				whereCase.append("  GROUP BY a.enterprise_id ORDER BY a.loandate DESC ");
				Query query = entityManager.createNativeQuery(inforSql_1+QUERY_DECLAREREWAR_SQL + whereCase.toString()+inforSql_2, DeclareAwardResult.class);
				if (null != reward) {
					
					if (!StringUtil.isNullOrEmpty(reward
							.getInvestorId())) {
						query.setParameter("orgId",reward.getInvestorId() );
					}
					if (null != reward.getDeclare_begin_time()) {
						String  beginTime=sDateFormat.format(reward.getDeclare_begin_time());
						query.setParameter("beginTime", beginTime);
					}
					if (null != reward.getDeclare_end_time()) {
						String  endTime=sDateFormat.format(reward.getDeclare_end_time());
						query.setParameter("endTime", endTime);
					}
					res = (List<DeclareAwardResult>)query.getResultList();
				}
			}else if("2".equals(type)){
				whereCase.append("  GROUP BY a.event_id ORDER BY a.loandate DESC ");
				Query query = entityManager.createNativeQuery(QUERY_DECLAREREWAR_SQL + whereCase.toString(), DeclareAwardResult.class);
				if (null != reward) {
					
					if (!StringUtil.isNullOrEmpty(reward
							.getInvestorId())) {
						query.setParameter("orgId",reward.getInvestorId() );
					}
					if (!StringUtil.isNullOrEmpty(reward
							.getRearea())) {
						query.setParameter("rearea",reward.getRearea());
					}
					if (null != reward.getDeclare_begin_time()) {
						String  beginTime=sDateFormat.format(reward.getDeclare_begin_time());
						query.setParameter("beginTime", beginTime);
					}
					if (null != reward.getDeclare_end_time()) {
						String  endTime=sDateFormat.format(reward.getDeclare_end_time());
						query.setParameter("endTime", endTime);
					}
					res = (List<DeclareAwardResult>)query.getResultList();
				}
			}else if("3".equals(type)){
//				whereCase.append("  GROUP BY a.event_id ORDER BY a.loandate DESC ");
				Query query = entityManager.createNativeQuery(QUERY_DECLAREREWARLoan_SQL + whereCase.toString(), DeclareAwardResult.class);
				if (null != reward) {
					
					if (!StringUtil.isNullOrEmpty(reward
							.getInvestorId())) {
						query.setParameter("orgId",reward.getInvestorId() );
					}
					if (!StringUtil.isNullOrEmpty(reward
							.getRearea())) {
						query.setParameter("rearea",reward.getRearea());
					}
					if (null != reward.getDeclare_begin_time()) {
						String  beginTime=sDateFormat.format(reward.getDeclare_begin_time());
						query.setParameter("beginTime", beginTime);
					}
					if (null != reward.getDeclare_end_time()) {
						String  endTime=sDateFormat.format(reward.getDeclare_end_time());
						query.setParameter("endTime", endTime);
					}
					res = (List<DeclareAwardResult>)query.getResultList();
				}
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
	 * 条件查询我的申报列表 @Title: investorPublishList @param infotype @param
	 * projectName @param startTime @param endTime @return List
	 * <InvestorPublishInfo> @throws
	 */
	@SuppressWarnings("unchecked")
	public List<DeclareRewarInfor> getDeclareLists(DeclareRewarSearshBean declareRewardSearsh) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<DeclareRewarInfor> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (null != declareRewardSearsh) {
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getRearea())) {
					whereCase.append(" and t1.company_address in(:rearea) ");
				}
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareStatus())) {
					whereCase.append(" and t1.declare_status = :declareStatus ");
				}
				
				if (declareRewardSearsh.getDeclare_begin_time()!= null) {
					whereCase.append(" and t1.declare_begin_time >=:beginTime ");
				}
				
				if (declareRewardSearsh.getDeclare_end_time()!= null) {
					whereCase.append(" and t1.declare_end_time <=:endTime ");
				}
				
			}
			whereCase.append(" order by t1.declare_create_time desc ");
			// 拼接分頁sql
			whereCase.append(this.getPageInfos(declareRewardSearsh));
			Query query = entityManager.createNativeQuery(MYDECLARE_LISTS_SQL + whereCase.toString(),
					DeclareRewarInfor.class);
			
			query.setParameter("investorId",declareRewardSearsh.getInvestorId());
			if (null != declareRewardSearsh) {
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh
						.getRearea())) {
					String[] values = declareRewardSearsh.getRearea().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("rearea", list);
				}
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareStatus())) {
					query.setParameter("declareStatus",declareRewardSearsh.getDeclareStatus() );
				}
				if (null != declareRewardSearsh.getDeclare_begin_time()) {
					String  beginTime=sDateFormat.format(declareRewardSearsh.getDeclare_begin_time());
					query.setParameter("beginTime", beginTime);
				}
				if (null != declareRewardSearsh.getDeclare_end_time()) {
					String  endTime=sDateFormat.format(declareRewardSearsh.getDeclare_end_time());
					query.setParameter("endTime", endTime);
				}
			}
			res = (List<DeclareRewarInfor>)query.getResultList();
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
	public Object getDeclareCount(DeclareRewarSearshBean declareRewardSearsh) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer countSql = new StringBuffer(" select count(*) as resultnum from ( ");
			
			StringBuffer whereCase = new StringBuffer();
			if (null != declareRewardSearsh) {
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getRearea())) {
					whereCase.append(" and t1.company_address in(:rearea) ");
				}
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareStatus())) {
					whereCase.append(" and t1.declare_status = :declareStatus ");
				}
				
				if (declareRewardSearsh.getDeclare_begin_time()!= null) {
					whereCase.append(" and t1.declare_begin_time >=:beginTime ");
				}
				
				if (declareRewardSearsh.getDeclare_end_time()!= null) {
					whereCase.append(" and t1.declare_end_time <=:endTime ");
				}
				
			}
			whereCase.append(" order by t1.declare_create_time desc ");
			countSql.append(MYDECLARE_LISTS_SQL);
			countSql.append(whereCase.toString());
			countSql.append(") result ");
			// 拼接分頁sql

			Query query = entityManager.createNativeQuery(countSql.toString());
			query.setParameter("investorId",declareRewardSearsh.getInvestorId());
			if (null != declareRewardSearsh) {
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh
						.getRearea())) {
					String[] values = declareRewardSearsh.getRearea().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("rearea", list);
				}
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareStatus())) {
					query.setParameter("declareStatus",declareRewardSearsh.getDeclareStatus() );
				}
				if (null != declareRewardSearsh.getDeclare_begin_time()) {
					String  beginTime=sDateFormat.format(declareRewardSearsh.getDeclare_begin_time());
					query.setParameter("beginTime", beginTime);
				}
				if (null != declareRewardSearsh.getDeclare_end_time()) {
					String  endTime=sDateFormat.format(declareRewardSearsh.getDeclare_end_time());
					query.setParameter("endTime", endTime);
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
	 * 条件查询主管 申报查询 @Title: investorPublishList @param infotype @param
	 * projectName @param startTime @param endTime @return List
	 * <InvestorPublishInfo> @throws
	 */
	@SuppressWarnings("unchecked")
	public List<DeclareRewarInfor> getManageDeclareLists(DeclareRewarSearshBean declareRewardSearsh) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<DeclareRewarInfor> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer whereCase = new StringBuffer();
			if (null != declareRewardSearsh) {
//				if(!declareRewardSearsh.getUserType().equals(SRRPConstant.USER_TYPE_03)){
					if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getRearea())) {
						whereCase.append(" and t1.company_address in(:rearea) ");
					}
//				}
				
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareName())) {
					whereCase.append(" and( t1.declare_name like :name or t1.certno = :code )");
				}
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getCompanyName())) {
					whereCase.append(" and t1.declare_id in (select declare_id from rp_declare_award_report t2 where   t2.invested_enterprise like :companyName or t2.invested_enterprise_code = :companyCode )");
				}
				
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareStatus())) {
					whereCase.append(" and t1.declare_status = :declareStatus ");
				}
				if (declareRewardSearsh.getDeclare_begin_time()!= null) {
					whereCase.append(" and t1.declare_begin_time >=:beginTime ");
				}
				
				if (declareRewardSearsh.getDeclare_end_time()!= null) {
					whereCase.append(" and t1.declare_end_time <=:endTime ");
				}
				
			}
			whereCase.append(" order by t1.declare_create_time desc ");
			// 拼接分頁sql
			whereCase.append(this.getPageInfos(declareRewardSearsh));
			Query query = entityManager.createNativeQuery(MANAGE_DECLARE_LISTS_SQL + whereCase.toString(),
					DeclareRewarInfor.class);
			if (null != declareRewardSearsh) {
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareName())) {
					query.setParameter("name", "%" + declareRewardSearsh.getDeclareName()
							+ "%");
					query.setParameter("code", declareRewardSearsh.getDeclareName());
				}
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getCompanyName())) {
					query.setParameter("companyName", "%" + declareRewardSearsh.getCompanyName()
					+ "%");
			        query.setParameter("companyCode", declareRewardSearsh.getCompanyName());
				}
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh
						.getRearea())) {
//					if(!declareRewardSearsh.getUserType().equals(SRRPConstant.USER_TYPE_03)){
						String[] values = declareRewardSearsh.getRearea().toString().split(",");
						List list = java.util.Arrays.asList(values); 
						query.setParameter("rearea", list);
//					}

					
				}
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareStatus())) {
					query.setParameter("declareStatus",declareRewardSearsh.getDeclareStatus() );
				}
				if (null != declareRewardSearsh.getDeclare_begin_time()) {
					String  beginTime=sDateFormat.format(declareRewardSearsh.getDeclare_begin_time());
					query.setParameter("beginTime", beginTime);
				}
				if (null != declareRewardSearsh.getDeclare_end_time()) {
					String  endTime=sDateFormat.format(declareRewardSearsh.getDeclare_end_time());
					query.setParameter("endTime", endTime);
				}
			}
			res = (List<DeclareRewarInfor>)query.getResultList();
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
	 * 条件查询主管 申报查询  总条数 @Title: investorPublishList @param infotype @param
	 * projectName @param startTime @param endTime @return List
	 * <InvestorPublishInfo> @throws
	 */
	@SuppressWarnings("unchecked")
	public 	Object getManageDeclareCount(DeclareRewarSearshBean declareRewardSearsh) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		Object res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			StringBuffer countSql = new StringBuffer(" select count(*) as resultnum from ( ");
			StringBuffer whereCase = new StringBuffer();
			if (null != declareRewardSearsh) {
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getRearea())) {
					whereCase.append(" and t1.company_address in(:rearea) ");
				}
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareName())) {
					whereCase.append(" and( t1.declare_name like :name or t1.certno = :code )");
				}
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getCompanyName())) {
					whereCase.append(" and t1.declare_id in (select declare_id from rp_declare_award_report t2 where   t2.invested_enterprise like :companyName or t2.invested_enterprise_code = :companyCode )");
				}
				
				
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareStatus())) {
					whereCase.append(" and t1.declare_status = :declareStatus ");
				}
				if (declareRewardSearsh.getDeclare_begin_time()!= null) {
					whereCase.append(" and t1.declare_begin_time >=:beginTime ");
				}
				
				if (declareRewardSearsh.getDeclare_end_time()!= null) {
					whereCase.append(" and t1.declare_end_time <=:endTime ");
				}
			}
			whereCase.append(" order by t1.declare_create_time desc ");
			countSql.append(MANAGE_DECLARE_LISTS_SQL);
			countSql.append(whereCase.toString());
			countSql.append(") result ");
			// 拼接分頁sql
			Query query = entityManager.createNativeQuery(countSql.toString());
			if (null != declareRewardSearsh) {
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareName())) {
					query.setParameter("name", "%" + declareRewardSearsh.getDeclareName()
							+ "%");
					query.setParameter("code", declareRewardSearsh.getDeclareName());
				}
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getCompanyName())) {
					query.setParameter("companyName", "%" + declareRewardSearsh.getDeclareName()
					+ "%");
			        query.setParameter("companyCode", declareRewardSearsh.getDeclareName());
				}
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh
						.getRearea())) {
					String[] values = declareRewardSearsh.getRearea().toString().split(",");
					List list = java.util.Arrays.asList(values); 
					query.setParameter("rearea", list);
				}
				if (!StringUtil.isNullOrEmpty(declareRewardSearsh.getDeclareStatus())) {
					query.setParameter("declareStatus",declareRewardSearsh.getDeclareStatus() );
				}
				if (null != declareRewardSearsh.getDeclare_begin_time()) {
					String  beginTime=sDateFormat.format(declareRewardSearsh.getDeclare_begin_time());
					query.setParameter("beginTime",beginTime);
				}
				if (null != declareRewardSearsh.getDeclare_end_time()) {
					String  endTime=sDateFormat.format(declareRewardSearsh.getDeclare_end_time());
					query.setParameter("endTime", endTime);
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

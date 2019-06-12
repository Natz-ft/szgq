package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryCompanyFinacingEventResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryInvestorFinacingEventResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.investorDicArea;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class InvestorInfoListDaoImpl extends BaseNativeQueryDao {

	// 根据融资事件的id查询融资事件表，融资信息表，融资机构表的基本信息的sql语句
	private String unionSQL = "select fe.event_id,fe.info_id,fe.investorg_id,fe.status,fe.currency,fe.amount,fe.maildate,fe.operdate,fe.project_name,e.finance_stage,e.name,e.org_type,f.finacing_turn,f.open from rp_finacing_event fe ,rp_investor e,rp_finacing_demand f where e.investor_id=fe.investorg_id and f.info_id=fe.info_id and fe.event_id=:eventId";

	// 根据机构的的id 查询融资事件表E，融资信息表D，企业表B，企业补充表S的基本信息 的sql语句
//	private String unionSQLEDBS = "select cb.enterprise_id,cb.name,cb.rearea,cbs.industry,fe.event_id,fe.info_id,fe.project_name,fe.amount,fe.currency,fe.operdate,fd.finacing_turn from rp_finacing_event fe,rp_finacing_demand fd,rp_company_base cb,rp_company_base_supplement cbs where fe.info_id=fd.info_id and fe.enterprise_id=cb.enterprise_id and cb.enterprise_id=cbs.enterprise_id and fe.investorg_id= :investorId";
	//edit by loudw 20171113 投资机构详情->投资事件 企业补充信息为空时，展示数据记录少
	private String unionSQLEDBS = "SELECT cb.enterprise_id,cb.name,cb.rearea,cbs.industry,fe.event_id,fe.info_id,fe.project_name,fe.amount as amount,fe.currency,fe.operuser,fe.operdate,fd.finacing_turn,TIMESTAMPDIFF(DAY,fd.operdate,fe.operdate)+1 as duringdate FROM rp_finacing_event fe,rp_finacing_demand fd,rp_company_base cb LEFT JOIN rp_company_base_supplement cbs ON cb.enterprise_id = cbs.enterprise_id WHERE fe.status >31 AND fd.status<>99 AND fe.status <>99 AND fe.info_id = fd.info_id  AND fe.enterprise_id = cb.enterprise_id AND fe.investorg_id =:investorId  order by fe.operdate desc ";
	private String unionSQLbyInvestor = "SELECT cb.enterprise_id,cb.name,cb.rearea,cbs.industry,fe.event_id,fe.info_id,fe.project_name,fe.amount as amount,fe.currency,fe.operuser,fe.operdate,fd.finacing_turn,TIMESTAMPDIFF(DAY,fd.operdate,fe.operdate)+1 as duringdate FROM rp_finacing_event fe,rp_finacing_demand fd,rp_company_base cb LEFT JOIN rp_company_base_supplement cbs ON cb.enterprise_id = cbs.enterprise_id WHERE fe.status >31 AND fd.status<>99 AND fe.status <>99 AND fe.info_id = fd.info_id AND fe.enterprise_id = cb.enterprise_id AND fe.investorg_id =:investorId order by fe.operdate desc  ";
	  private String AREASQL = "SELECT * FROM platform_dic_area dic where level =:level ";
	  private String ALLAREASQL = "SELECT * FROM platform_dic_area dic where  code =:code ";
	  private String ALLAREASQL1 = "SELECT * FROM platform_dic_area dic  where  parentcode =:code ";
	/**
	 * 根据机构的的id 查询融资事件表E，融资信息表D，企业表B，企业补充表S的基本信息
	 * 
	 * @param fiancingInfoId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<QueryCompanyFinacingEventResult> findUnionCompanyEventList(
			String investorId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryCompanyFinacingEventResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();

			Query query = entityManager.createNativeQuery(unionSQLEDBS,
					QueryCompanyFinacingEventResult.class);
			query.setParameter("investorId", investorId);
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
	 * 根据机构的的id 查询融资事件表E，融资信息表D，企业表B，企业补充表S的基本信息
	 * 
	 * @param fiancingInfoId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<QueryCompanyFinacingEventResult> findEventListByInvestor(
			String investorId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryCompanyFinacingEventResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();

			Query query = entityManager.createNativeQuery(unionSQLbyInvestor,
					QueryCompanyFinacingEventResult.class);
			query.setParameter("investorId", investorId);
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
	 * 根据需求的id查询融资事件表，融资信息表，机构表的基本信息
	 * 
	 * @param fiancingInfoId
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<QueryInvestorFinacingEventResult> findUnionInvestorList(
			String eventId) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<QueryInvestorFinacingEventResult> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Query query = entityManager.createNativeQuery(unionSQL,
					QueryInvestorFinacingEventResult.class);
			query.setParameter("eventId", eventId);
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
	
	public List<investorDicArea> getAreaDate(String level)
	  {
	    EntityManager entityManager = getEntityManager();
	    EntityTransaction entityTransaction = null;
	    List res = null;
	    try {
	      entityTransaction = entityManager.getTransaction();
	      entityTransaction.begin();

	      Query query = entityManager.createNativeQuery(this.AREASQL, 
	        investorDicArea.class);
	      query.setParameter("level", level);

	      res = query.getResultList();
	      entityTransaction.commit();
	    } catch (Exception e) {
	      e.printStackTrace();
	      entityTransaction.rollback();
	    } finally {
	      closeEntityManager(entityManager);
	    }
	    return res;
	  }

	  public List<investorDicArea> getAreaDateByCode(String code, String type)
	  {
	    EntityManager entityManager = getEntityManager();
	    EntityTransaction entityTransaction = null;
	    List res = null;
	    String sql = "";
	    try {
	      entityTransaction = entityManager.getTransaction();
	      entityTransaction.begin();
	      if ("1".equals(type))
	        sql = this.ALLAREASQL;
	      else {
	        sql = this.ALLAREASQL1;
	      }
	      Query query = entityManager.createNativeQuery(sql, 
	        investorDicArea.class);
	      query.setParameter("code", code);

	      res = query.getResultList();
	      entityTransaction.commit();
	    } catch (Exception e) {
	      e.printStackTrace();
	      entityTransaction.rollback();
	    } finally {
	      closeEntityManager(entityManager);
	    }
	    return res;
	  }
}

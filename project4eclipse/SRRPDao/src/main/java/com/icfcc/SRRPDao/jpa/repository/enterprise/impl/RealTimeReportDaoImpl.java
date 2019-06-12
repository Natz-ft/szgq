package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanArea;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTrade;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.RealTimeReportDao;

@Component
public class RealTimeReportDaoImpl extends BaseNativeQueryDao implements RealTimeReportDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<ReportBeanTrade> findTradeInvestByOrgId(String orgId, Date start, Date end, Integer limit) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<ReportBeanTrade> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            String groupSql = " group by t2.industry order by sum(t1.amount) desc";
            StringBuffer sql = new StringBuffer(
                    "select t2.industry trade,sum(t1.amount) amount from rp_company_base_supplement t2 left join  rp_investor_loan t1 on t1.enterprise_id=t2.enterprise_id where 1=1 ");
            if (!StringUtils.isEmpty(orgId)) {
                sql.append(" and t1.org_id=:orgId ");
            }
            if (start != null) {
                sql.append(" and t1.loandate >= :start");
            }
            if (end != null) {
                sql.append(" and t1.loandate <= :end");
            }
            sql.append(groupSql);
            Query q = entityManager.createNativeQuery(sql.toString());
            if (limit != null) {
                q.setMaxResults(limit);
            }
            if (!StringUtils.isEmpty(orgId)) {
                q.setParameter("orgId", orgId);
            }
            if (start != null) {
                q.setParameter("start", start);
            }
            if (end != null) {
                q.setParameter("end", end);
            }
            List<Object[]> tres = (List<Object[]>) q.getResultList();
            res = new ArrayList<ReportBeanTrade>();
            for (Object[] o : tres) {
                ReportBeanTrade oo = new ReportBeanTrade();
                oo.setAmount(new BigDecimal((Double) o[1]));
                oo.setIndustry((String) o[0]);
                res.add(oo);
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

    @Override
    public Double sumInvestByOrgId(String orgId, Date start, Date end) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        Double res = 0d;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer sql = new StringBuffer("select sum(t.amount) from rp_investor_loan t where 1=1 ");
            if (!StringUtils.isEmpty(orgId)) {
                sql.append(" and t.org_id=:orgId ");
            }
            if (start != null) {
                sql.append(" and t.loandate >= :start");
            }
            if (end != null) {
                sql.append(" and t.loandate <= :end");
            }
            Query q = entityManager.createNativeQuery(sql.toString());
            if (!StringUtils.isEmpty(orgId)) {
                q.setParameter("orgId", orgId);
            }
            if (start != null) {
                q.setParameter("start", start);
            }
            if (end != null) {
                q.setParameter("end", end);
            }
            res = (Double) q.getSingleResult();
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
    @Override
    public List<ReportBeanArea> findAreaInvestByOrgId(String orgId, Date start, Date end, Integer limit) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<ReportBeanArea> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            String groupSql = " group by t2.rearea order by sum(t1.amount) desc";
            StringBuffer sql = new StringBuffer("select IFNULL(t2.rearea,'--') area,sum(t1.amount) amount from rp_finacing_event t1 left join rp_company_base t2  on  t1.enterprise_id=t2.enterprise_id where 1=1 ");
            if (!StringUtils.isEmpty(orgId)) {
                sql.append(" and t1.org_id=:orgId ");
            }
            if (start != null) {
                sql.append(" and t1.operdate >= :start");
            }
            if (end != null) {
                sql.append(" and t1.operdate <= :end");
            }
            sql.append(groupSql);
            EntityManager em = this.getEntityManager();
            Query q = em.createNativeQuery(sql.toString());
            if (limit != null) {
                q.setMaxResults(limit);
            }
            if (!StringUtils.isEmpty(orgId)) {
                q.setParameter("orgId", orgId);
            }
            if (start != null) {
                q.setParameter("start", start);
            }
            if (end != null) {
                q.setParameter("end", end);
            }
            List<Object[]> tres = (List<Object[]>) q.getResultList();
            res = new ArrayList<ReportBeanArea>();
            for (Object[] o : tres) {
                ReportBeanArea oo = new ReportBeanArea();
                oo.setAmount(new BigDecimal((Double) o[1]));
                oo.setArea((String) o[0]);
                res.add(oo);
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
	public List<ReportBeanTrade> findTradeInvestByOrgIdStr(String orgId, String start, String end, Integer limit,String username) {
		EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<ReportBeanTrade> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            String groupSql = " group by industrys order by sum(t1.exchange_ratio) desc";
           //StringBuffer sql = new StringBuffer( "select t2.industry trade,sum(t1.amount) amount from rp_company_base_supplement t2 left join  rp_finacing_event t1 on t1.enterprise_id=t2.enterprise_id where 1=1 ");
           //由放款表取值调整为融资事件表，页面统计投资金额
            StringBuffer sql = new StringBuffer( "select industrys industry,amount amount from (select IFNULL(left(t2.industry,2) , '--') industrys,sum(t1.exchange_ratio) amount from  rp_finacing_event t1 left join rp_company_base_supplement t2 on t1.enterprise_id=t2.enterprise_id where 1=1 ");
            if (!StringUtils.isEmpty(orgId)) {
                sql.append(" and t1.investorg_id=:orgId ");
            }
            if (!StringUtils.isEmpty(username)) {
                sql.append(" and t1.operuser=:username ");
            }
            if (!StringUtils.isEmpty(start)) {
                sql.append(" and DATE_FORMAT(t1.operdate,'%Y-%m') >= :start");
            }
            if (!StringUtils.isEmpty(end)) {
                sql.append(" and DATE_FORMAT(t1.operdate,'%Y-%m') <= :end");
            }
            sql.append(groupSql);
            sql.append(" ) t");
            Query q = entityManager.createNativeQuery(sql.toString());

            if (!StringUtils.isEmpty(orgId)) {
                q.setParameter("orgId", orgId);
            }
            if (!StringUtils.isEmpty(username)) {
                q.setParameter("username", username);
            }
            if (!StringUtils.isEmpty(start)) {
                q.setParameter("start", start);
            }
            if (!StringUtils.isEmpty(end)) {
                q.setParameter("end", end);
            }
            List<Object[]> tres = (List<Object[]>) q.getResultList();
            res = new ArrayList<ReportBeanTrade>();
            for (Object[] o : tres) {
                ReportBeanTrade oo = new ReportBeanTrade();
                if(o[1]==null)
                	continue;
                oo.setAmount(new BigDecimal(o[1].toString()));
                oo.setIndustry((String) o[0]);
                res.add(oo);
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

	public Double sumInvestByOrgIdStr(String orgId, String start, String end,String username) {
		EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        Double res = 0d;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer sql = new StringBuffer("select IFNULL(sum(t.exchange_ratio),0) amount from rp_finacing_event t where 1=1 ");
            if (!StringUtils.isEmpty(orgId)) {
                sql.append(" and t.investorg_id=:orgId ");
            }
            if (!StringUtils.isEmpty(username)) {
                sql.append(" and t.operuser=:username ");
            }
            if (!StringUtils.isEmpty(start)) {
                sql.append(" and DATE_FORMAT(t.operdate,'%Y-%m') >= :start");
            }
            if (!StringUtils.isEmpty(end)) {
                sql.append(" and DATE_FORMAT(t.operdate,'%Y-%m') <= :end");
            }
            Query q = entityManager.createNativeQuery(sql.toString());
            if (!StringUtils.isEmpty(orgId)) {
                q.setParameter("orgId", orgId);
            }
            if (!StringUtils.isEmpty(username)) {
                q.setParameter("username", username);
            }
            if (!StringUtils.isEmpty(start)) {
                q.setParameter("start", start);
            }
            if (!StringUtils.isEmpty(end)) {
                q.setParameter("end", end);
            }
            res = Double.valueOf(q.getSingleResult().toString());
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

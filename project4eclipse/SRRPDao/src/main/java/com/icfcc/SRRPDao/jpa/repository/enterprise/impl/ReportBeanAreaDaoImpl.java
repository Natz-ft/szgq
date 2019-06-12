package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanArea;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import com.icfcc.SRRPDao.pojo.CountReportAreaBean;

@Component
public class ReportBeanAreaDaoImpl extends BaseNativeQueryDao {

    @SuppressWarnings("unchecked")
    public List<CountReportAreaBean> findStatisticResult(String dateflag) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<CountReportAreaBean> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            String sql = "SELECT a.rank,a.amount,a.area FROM  rp_report_area_rank a  WHERE a.time_id=:time_id ORDER BY rank  ";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("time_id", dateflag);
            List<Object[]> tres = (List<Object[]>) query.getResultList();
            res = new ArrayList<CountReportAreaBean>();
            for (Object[] o : tres) {
                CountReportAreaBean oo = new CountReportAreaBean();
                oo.setRank((Integer) o[0]);
                oo.setArea((String) o[2]);
                oo.setAmount((BigDecimal) o[1]);
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

    /**
     * 查找区域排名的统计结果
     * 
     * @param enterpriseId
     * @param start
     * @param end
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ReportBeanArea> findStatisticResult1(String area, Date start, Date end, Integer limit) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<ReportBeanArea> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer sql = new StringBuffer(
                    "select t.rid,t.timeId,t.area,sum(t.amount) ,count(t.rid) ,t.timePoint from ReportBeanArea t where 1=1 ");
            if (StringUtils.isNotEmpty(area)) {
                sql.append(" and t.area=:area");
            }
            if (start != null) {
                sql.append(" and t.timePoint >=:start");
            }
            if (end != null) {
                sql.append(" and t.timePoint<=:end");
            }
            sql.append(" group by t.area order by sum(t.amount) desc");

            Query q = entityManager.createQuery(sql.toString());// ,
            // ReportBeanEnterpriseDTO.class);
            if (limit != null) {
                q.setMaxResults(limit);
            }
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
            res = new ArrayList<ReportBeanArea>();
            for (Object[] o : tres) {
                ReportBeanArea oo = new ReportBeanArea();
                oo.setRid((String) o[0]);
                oo.setTimeId((String) o[1]);
                oo.setArea((String) o[2]);
                oo.setAmount(new BigDecimal((Double) o[3]));
                // oo.setRank((Long) o[4]);
                oo.setTimePoint((Date) o[5]);
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

}

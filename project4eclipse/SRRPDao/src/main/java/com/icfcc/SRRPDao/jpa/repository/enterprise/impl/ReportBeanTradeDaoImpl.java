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

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTrade;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class ReportBeanTradeDaoImpl extends BaseNativeQueryDao {

    /**
     * 查找行业排名的统计结果
     * 
     * @param enterpriseId
     * @param start
     * @param end
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ReportBeanTrade> findStatisticResult(String trade, Date start, Date end, Integer limit) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<ReportBeanTrade> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer sql = new StringBuffer(
                    "select t.rid,t.timeId,t.trade,sum(t.amount) ,count(t.rid) ,t.timePoint from ReportBeanTrade t where 1=1 ");
            if (StringUtils.isNotEmpty(trade)) {
                sql.append(" and t.trade=:trade");
            }
            if (start != null) {
                sql.append(" and t.timePoint >=:start");
            }
            if (end != null) {
                sql.append(" and t.timePoint<=:end");
            }
            sql.append(" group by t.trade order by sum(t.amount) desc");
            Query q = entityManager.createQuery(sql.toString());// ,
            if (limit != null) {
                q.setMaxResults(limit);
            }
            if (StringUtils.isNotEmpty(trade)) {
                q.setParameter("area", trade);
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
                oo.setRid((String) o[0]);
                oo.setTimeId((String) o[1]);
                oo.setIndustry((String) o[2]);
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

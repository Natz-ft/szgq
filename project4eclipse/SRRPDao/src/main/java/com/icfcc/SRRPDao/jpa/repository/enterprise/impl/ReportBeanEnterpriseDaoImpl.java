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

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanEnterprise;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class ReportBeanEnterpriseDaoImpl extends BaseNativeQueryDao {

    /**
     * 查找企业排名的统计结果
     * 
     * @param enterpriseId
     * @param start
     * @param end
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<ReportBeanEnterprise> findStatisticResult(String enterpriseId, Date start, Date end, Integer limit) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<ReportBeanEnterprise> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer sql = new StringBuffer(
                    "select t.rid,t.timeId,t.enterpriseId,sum(t.amount) ,count(t.rid) ,t.timePoint from ReportBeanEnterprise t where 1=1 ");
            if (StringUtils.isNotEmpty(enterpriseId)) {
                sql.append(" and t.enterpriseId=:enterpriseId");
            }
            if (start != null) {
                sql.append(" and t.timePoint >=:start");
            }
            if (end != null) {
                sql.append(" and t.timePoint<=:end");
            }
            sql.append(" group by t.enterpriseId order by sum(t.amount) desc");

            EntityManager em = this.getEntityManager();

            Query q = em.createQuery(sql.toString());// ,
                                                     // ReportBeanEnterpriseDTO.class);
            if (limit != null) {
                q.setMaxResults(limit);
            }
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
            res = new ArrayList<ReportBeanEnterprise>();
            for (Object[] o : tres) {
                ReportBeanEnterprise oo = new ReportBeanEnterprise();
                oo.setRid((String) o[0]);
                oo.setTimeId((String) o[1]);
                oo.setEnterpriseId((String) o[2]);
                oo.setAmount((BigDecimal) o[3]);
                oo.setRankCount((Long) o[4]);
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

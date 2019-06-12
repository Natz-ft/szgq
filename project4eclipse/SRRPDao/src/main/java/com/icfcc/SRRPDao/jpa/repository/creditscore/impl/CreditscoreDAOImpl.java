package com.icfcc.SRRPDao.jpa.repository.creditscore.impl;

import io.netty.util.internal.StringUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.creditscore.score.RpCompanyCreditscores;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class CreditscoreDAOImpl extends BaseNativeQueryDao {

    /**
     * 查询评分详情
     * 
     * @param queryCondition
     * @return
     */

    public RpCompanyCreditscores getScoreInfo(String creditType, String creditCode) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        RpCompanyCreditscores res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer querySQL = new StringBuffer("select  r.*  from rp_company_creditscores r where ");
            if (!StringUtil.isNullOrEmpty(creditType) && !StringUtil.isNullOrEmpty(creditCode)) {
                querySQL.append(" r.creditcode= :code ");
                querySQL.append("  and r.creditype= :type ");
            }
            Query query = entityManager.createNativeQuery(querySQL.toString(),RpCompanyCreditscores.class);
            if (!StringUtil.isNullOrEmpty(creditType) && !StringUtil.isNullOrEmpty(creditCode)) {
                query.setParameter("code", creditCode);
                query.setParameter("type", creditType);
            }
            res = (RpCompanyCreditscores)query.getSingleResult();
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

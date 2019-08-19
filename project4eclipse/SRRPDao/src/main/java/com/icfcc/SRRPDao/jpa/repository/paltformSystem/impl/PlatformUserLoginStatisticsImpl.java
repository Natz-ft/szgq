package com.icfcc.SRRPDao.jpa.repository.paltformSystem.impl;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserLoginStatistics;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

@Component
public class PlatformUserLoginStatisticsImpl extends BaseNativeQueryDao {

    /**
     * 查询所有
     */

    public List<PlatformUserLoginStatistics> findall(String time) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<PlatformUserLoginStatistics> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer querySQL = new StringBuffer("SELECT name,sum( CASE WHEN user_type IN ( 01 ) THEN cnt ELSE 0 END ) investor,sum( CASE WHEN user_type IN ( 02, 0201 ) THEN cnt ELSE 0 END ) enterprise,sum( CASE WHEN user_type IN ( 04 ) THEN cnt ELSE 0 END ) admin,sum( CASE WHEN user_type IN ( 01, 02, 0201, 04 ) THEN cnt ELSE 0 END ) allu FROM v_login_statistics_new ");
            if (!StringUtil.isNullOrEmpty(time) && !StringUtil.isNullOrEmpty(time)) {
                querySQL.append(" WHERE time <  " + time);
            }else{
                querySQL.append(" WHERE time <  1");
            }
            querySQL.append("  GROUP BY name order by case when name='其他' then 1 else 0 end, sum( CASE WHEN user_type IN ( 01, 02, 0201, 04 ) THEN cnt ELSE 0 END ) desc ");
            Query query = entityManager.createNativeQuery(querySQL.toString(),PlatformUserLoginStatistics.class);

            res = (List<PlatformUserLoginStatistics>)query.getResultList();
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

package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayNews;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformFaqShow;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformNews;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

@Component
public class GataWayNewsDao extends BaseNativeQueryDao {

    private String sql = "select * from platform_portal_news where 1=1 ";

    @SuppressWarnings("unchecked")
    public List<GataWayNews> findNewsListByType(String infoType) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<GataWayNews> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer whereCase = new StringBuffer();
            if (null != infoType) {
                if ("portal.index".equals(infoType)) {
                    whereCase.append(" and purpose in ('01','03') ");
                    

                }
                if ("portal.news".equals(infoType)) {
                    whereCase.append(" and purpose in ('01') ");

                }
            }
            whereCase.append(" order by news_create_time desc");
            Query query = entityManager.createNativeQuery(sql + whereCase.toString(), GataWayNews.class);
            res = (List<GataWayNews>) query.getResultList();
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
    public List<PlatformNews> findNewsListLunbo() {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<PlatformNews> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            String sql = "select * from platform_news where is_lunbo = 1 ORDER BY lunbo_ord";
            Query query = entityManager.createNativeQuery(sql, PlatformNews.class);
            res = (List<PlatformNews>) query.getResultList();
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            this.closeEntityManager(entityManager);
        }
        return res;
    }

    //动态广告
    public List<PlatformFaqShow> findFaqDy() {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<PlatformFaqShow> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            String sql = "select * from platform_faq where type = '0003' ORDER BY create_time desc";
            Query query = entityManager.createNativeQuery(sql, PlatformFaqShow.class);
            res = (List<PlatformFaqShow>) query.getResultList();
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

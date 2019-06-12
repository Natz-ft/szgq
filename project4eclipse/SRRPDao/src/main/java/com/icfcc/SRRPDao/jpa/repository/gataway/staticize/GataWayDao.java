package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayNews;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformNews;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class GataWayDao extends BaseNativeQueryDao {

    private String sql = "update srrp_synchronize_job set ismodify = ";

    @SuppressWarnings("unchecked")
    public void changeJobStatus(String status) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer whereCase = new StringBuffer();
            if ("2".equals(status)) {
                whereCase.append(" 2 ");
            }else {
            	whereCase.append(" 0 ");
			}
            Query query = entityManager.createNativeQuery(sql + whereCase.toString(), Integer.class);
            query.executeUpdate();
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            this.closeEntityManager(entityManager);
        }
    }
    
    
    private String sql2 = "update platform_portal_index_manual set enterprise_count = ";
    
    @SuppressWarnings("unchecked")
    public void changeEnterpriseCount(int count) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            Query query = entityManager.createNativeQuery(sql2 + count, Integer.class);
            query.executeUpdate();
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            this.closeEntityManager(entityManager);
        }
    }
    
}

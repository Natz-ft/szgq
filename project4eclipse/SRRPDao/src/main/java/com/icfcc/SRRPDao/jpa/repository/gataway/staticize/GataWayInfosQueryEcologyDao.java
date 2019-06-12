package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayInfos;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public class GataWayInfosQueryEcologyDao extends BaseNativeQueryDao {

    private String sql = "select * from platform_portal_infos where 1=1 ";

    @SuppressWarnings("unchecked")
    public List<GataWayInfos> findInfosList(String infoType) {
        EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List<GataWayInfos> res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer whereCase = new StringBuffer();
            // 资本实力
            if (null != infoType) {
                whereCase.append(" and infotype= :infoType ");
            }
            whereCase.append(" order by time desc,infoid desc");
            Query query = entityManager.createNativeQuery(sql + whereCase.toString(), GataWayInfos.class);
            if (null != infoType) {
                query.setParameter("infoType", infoType);
            }
            res = (List<GataWayInfos>) query.getResultList();
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

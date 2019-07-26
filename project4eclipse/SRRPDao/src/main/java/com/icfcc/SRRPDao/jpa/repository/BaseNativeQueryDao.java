package com.icfcc.SRRPDao.jpa.repository;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarSearshBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class BaseNativeQueryDao {
    
    @Autowired(required = false)
    @Qualifier("entityManagerFactory")
    private LocalContainerEntityManagerFactoryBean ens;

    public EntityManager getEntityManager() {
        return ens.getNativeEntityManagerFactory().createEntityManager();
    }
    /*
     * 分页sql拼接
     */
    public String getPageInfos(QueryCondition queryCondition) {
        StringBuffer whereCase = new StringBuffer();
        if(queryCondition != null){
            whereCase.append(" limit ");
            if (queryCondition.getCurPage() == 1) {
                whereCase.append("0");
            } else {
                whereCase.append((queryCondition.getCurPage() - 1) * queryCondition.maxSize);
            }
            whereCase.append("," + queryCondition.maxSize);
        }
        return whereCase.toString();
    }

    /*
     * 分页sql拼接
     */
    public String getPageInfos(DeclareRewarSearshBean queryCondition) {
        StringBuffer whereCase = new StringBuffer();
        if(queryCondition != null){
            whereCase.append(" limit ");
            if (queryCondition.getCurPage() == 1) {
                whereCase.append("0");
            } else {
                whereCase.append((queryCondition.getCurPage() - 1) * queryCondition.maxSize);
            }
            whereCase.append("," + queryCondition.maxSize);
        }
        return whereCase.toString();
    }
    public void closeEntityManager(EntityManager entityManager){
        if(null != entityManager){
            entityManager.close();
        }
    }
}

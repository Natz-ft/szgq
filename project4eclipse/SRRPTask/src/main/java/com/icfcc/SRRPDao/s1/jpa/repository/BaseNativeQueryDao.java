package com.icfcc.SRRPDao.s1.jpa.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;


@Component
public class BaseNativeQueryDao {
    
    @Autowired(required = false)
    @Qualifier("entityManagerFactory1")
    private LocalContainerEntityManagerFactoryBean ens;

    public EntityManager getEntityManager() {
        return ens.getNativeEntityManagerFactory().createEntityManager();
    }
    public void closeEntityManager(EntityManager entityManager){
        if(null != entityManager){
            entityManager.close();
        }
    }
}

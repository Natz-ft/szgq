package com.icfcc.SRRPDao.jpa.repository.enterprise.impl;
/**
 * 关联查询的工具类
 */
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.util.ProtozoaSqlUtil;

@Component
public class BaseDaoImpl {

	
    @Autowired(required = false)
    @Qualifier("entityManagerFactory")
    private LocalContainerEntityManagerFactoryBean ens;

    public EntityManager getEntityManager() {
        return ens.getNativeEntityManagerFactory().createEntityManager();
    }

}

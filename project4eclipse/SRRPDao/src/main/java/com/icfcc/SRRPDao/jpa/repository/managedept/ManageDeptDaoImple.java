package com.icfcc.SRRPDao.jpa.repository.managedept;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.util.ProtozoaSqlUtil;

@Component
public class ManageDeptDaoImple {
    @Autowired(required = false)
    @Qualifier("entityManagerFactory")
    private LocalContainerEntityManagerFactoryBean ens;

    private EntityManager getEntityManager() {
        return ens.getNativeEntityManagerFactory().createEntityManager();
    }

    public <T> List<T> auditInfoQuery(String sql, Class<T> clazz) {
        EntityManager entityManager = getEntityManager();
        return ProtozoaSqlUtil.queryInfos(sql, clazz, entityManager);
    }
    // public <T> List<T> findObjectByIdList(List<String> ids, Class<T> clazz) {
    // EntityManager entityManager = getEntityManager();
    // entityManager.getTransaction().begin();
    // try {
    // String replace =
    // FIND_OBJECT_BY_ID_LIST_SQL.replace("TABLE_NAME",getTableNameByClass(clazz));
    // Query query = entityManager.createNativeQuery(replace, clazz);
    // query.setParameter("ids", ids);
    // entityManager.getTransaction().commit();
    // return (List<T>) query.getResultList();
    // } catch (Exception e) {
    // return null;
    // } finally {
    // entityManager.close();
    // }
    // }
    // @SuppressWarnings("rawtypes")
    // public static String getTableNameByClass(Class c) {
    // Annotation annos[] = c.getAnnotations();
    // for(Annotation a : annos){
    // if("Table".equals(a.annotationType().getSimpleName())){
    // javax.persistence.Table t = (javax.persistence.Table)a;
    // return t.name();
    // }
    // }
    // return null;
    // }
    // @SuppressWarnings("unchecked")
    // public <T> List<T> auditInfoQuery(String sql,Class<T> clazz) {
    //
    // EntityManager entityManager = getEntityManager();
    // try {
    // Query query = entityManager.createNativeQuery(sql, clazz);
    // List<T> res=(List<T>) query.getResultList();
    // return res;
    // } catch (Exception e) {
    // e.printStackTrace();
    // return null;
    // }
    //
    // }
}

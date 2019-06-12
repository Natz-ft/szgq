package com.icfcc.SRRPDao.jpa.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * 原生SQL执行工具类 <功能详细描述>
 */
public class ProtozoaSqlUtil {
    @SuppressWarnings("unchecked")
    public static <T> List<T> queryInfos(String sql, Class<T> clazz,EntityManager entityManager) {
        System.out.println("sql=============="+sql);
        try {
            Query query = entityManager.createNativeQuery(sql, clazz);
            List<T> res = (List<T>) query.getResultList();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

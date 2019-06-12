package com.icfcc.SRRPDao.jpa.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyUnionInfoResult;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

import io.netty.util.internal.StringUtil;

@Component
public class SomeLogDaoImpl extends BaseNativeQueryDao {


	@SuppressWarnings("unchecked")
	public void saveSomeLog(String sql) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();

			Query query = entityManager.createNativeQuery(sql);
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

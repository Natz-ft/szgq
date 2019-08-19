package com.icfcc.SRRPDao.jpa.repository.managedept.impl;

import com.icfcc.SRRPDao.jpa.entity.managedept.CountLoginInfo;
import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

@Component
public class CountLoginStatisticsDaoImpl extends BaseNativeQueryDao {


	private String[] levels = new String[]{"1","3","7","30","90"};

	private String[] types = new String[]{"(01)","(02,0201)","(04)","(01,02,0201,04)"};
	/**
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<CountLoginInfo> getCountLoginStatistics(String area, String type, String id) {
		EntityManager entityManager = this.getEntityManager();
		EntityTransaction entityTransaction = null;
		List<CountLoginInfo> res = null;
		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			String sql = "select t.* from (SELECT area,user_name,nick_name, case when user_type=01 then '机构/基金' when user_type in(02,0201) then '企业' when user_type=04 then '主管' end type,count(1) cnt FROM v_login_statistics_info WHERE area = '#0' AND user_type IN #1 AND datediff(now(), login_time) < #2 group by area,user_name,nick_name) t order by t.cnt desc";
			String tempType = "";
			if(type.equals("investor")){
				tempType = "(01)";
			}else if(type.equals("enterprise")){
				tempType = "(02,0201)";
			}else if(type.equals("admin")){
				tempType = "(04)";
			}else{
				tempType = "(01,02,0201,04)";
			}
			Query query = entityManager.createNativeQuery(
					sql.replace("#0",area).replace("#1",tempType).replace("#2",id)
					, CountLoginInfo.class);
			res = (List<CountLoginInfo>) query.getResultList();
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

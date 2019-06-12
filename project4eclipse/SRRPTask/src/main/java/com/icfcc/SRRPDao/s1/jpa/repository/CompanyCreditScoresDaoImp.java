package com.icfcc.SRRPDao.s1.jpa.repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;


/**
 * 
* @ClassName: PlatformNewsDao
* @Description: TODO( 查询机构总数)
* @author xb
* @date 2017年9月14日 下午7:20:12
*
 */
@Component
public class CompanyCreditScoresDaoImp extends BaseNativeQueryDao{
	
	
	/**
	 * 查询出来 机构总数
	 * @return
	 */
	public long countCompany(){
		    EntityManager entityManager = this.getEntityManager();
	        EntityTransaction entityTransaction = null;
	        long res = 0;
	        try {
	            entityTransaction = entityManager.getTransaction();
	            entityTransaction.begin();
	            StringBuffer querySQL = new StringBuffer("SELECT sum(b) FROM (SELECT count(*) as b FROM rp_company_base c union all SELECT count(*) as b FROM rp_investor f) d ");
	            Query query = entityManager.createNativeQuery(querySQL.toString());
	            BigDecimal b  =   (BigDecimal) query.getSingleResult();
	             res = b.longValue();
	            entityTransaction.commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	            entityTransaction.rollback();
	        } finally {
	        	closeEntityManager(entityManager);
	        }
	        return res;
	}
	
	public List  queryCodes(int start,int end){
	    EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List  res = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer querySQL = new StringBuffer("select DISTINCT a.certno ,a.certtype FROM (SELECT c.codetype certtype,c.`code` certno FROM rp_company_base c union all  SELECT b.certtype,b.certno FROM rp_investor b) a ");
            querySQL.append(" limit "+start+","+end);
            Query query = entityManager.createNativeQuery(querySQL.toString());
            res = query.getResultList();
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
        	closeEntityManager(entityManager);
        }
        return res;
}
	

	public Set<String>  queryCodes(){
	    EntityManager entityManager = this.getEntityManager();
        EntityTransaction entityTransaction = null;
        List list = null ;
        Set<String> set =null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            StringBuffer querySQL = new StringBuffer("select DISTINCT a.certno FROM (SELECT c.codetype certtype,c.`code` certno FROM rp_company_base c union all  SELECT b.certtype,b.certno FROM rp_investor b) a ");
            Query query = entityManager.createNativeQuery(querySQL.toString());
            list = query.getResultList();
            set= new HashSet<>(list);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
        	closeEntityManager(entityManager);
        }
        return set;
}
	
}

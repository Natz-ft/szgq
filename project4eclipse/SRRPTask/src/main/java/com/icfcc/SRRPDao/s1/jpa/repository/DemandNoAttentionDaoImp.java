package com.icfcc.SRRPDao.s1.jpa.repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.FinacingDemandInfo;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingEvent;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformUser;
import com.icfcc.SRRPDao.s1.jpa.entity.SendSmsLog;

/**
 * 
* @ClassName: PlatformNewsDao
* @Description: TODO( 查询机构总数)
* @author xb
* @date 2017年9月14日 下午7:20:12
*
 */
@Component
public class DemandNoAttentionDaoImp extends BaseNativeQueryDao{
	
	
	/**
	 * 查询出来 机构总数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<FinacingDemandInfo> findFinacingDemandNoAttention(String day){
		    EntityManager entityManager = this.getEntityManager();
	        EntityTransaction entityTransaction = null;
	        List<FinacingDemandInfo> res = null;
	        try {
	            entityTransaction = entityManager.getTransaction();
	            entityTransaction.begin();
///	            StringBuffer querySQL = new StringBuffer("select  * from  rp_finacing_demand t where datediff(now(),operdate)= :day and status = '01' and open='1' ");
	            StringBuffer querySQL = new StringBuffer("select  * from  rp_finacing_demand t where  datediff(now(),operdate) = :day and status = '01' and open='1' and revoke_flag='0'");

	            Query query = entityManager.createNativeQuery(querySQL.toString(),FinacingDemandInfo.class);
				query.setParameter("day", day);
				res = (List<FinacingDemandInfo>) query.getResultList();
	            entityTransaction.commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	            entityTransaction.rollback();
	        } finally {
	        	closeEntityManager(entityManager);
	        }
	        return res;
	}
	
	/**
	 * 查询出来 机构总数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<FinacingEvent> findFinacingEventNoInvest(String day){
		    EntityManager entityManager = this.getEntityManager();
	        EntityTransaction entityTransaction = null;
	        List<FinacingEvent> res = null;
	        try {
	            entityTransaction = entityManager.getTransaction();
	            entityTransaction.begin();
//	            StringBuffer querySQL = new StringBuffer("select  * from  rp_finacing_event t where datediff(now(),operdate) = 0 and datediff(now(),operdate) >=:day and status = '21' ");
	            StringBuffer querySQL = new StringBuffer("select  * from  rp_finacing_event t where datediff(now(),operdate) = 0 and datediff(now(),operdate)>=:day and status = '21' ");

	            Query query = entityManager.createNativeQuery(querySQL.toString(),FinacingEvent.class);
				query.setParameter("day", day);
				res = (List<FinacingEvent>) query.getResultList();
	            entityTransaction.commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	            entityTransaction.rollback();
	        } finally {
	        	closeEntityManager(entityManager);
	        }
	        return res;
	}

	@SuppressWarnings("unchecked")
	public  List<FinacingEvent> findFinacingEventNoTalks(String day){
		    EntityManager entityManager = this.getEntityManager();
	        EntityTransaction entityTransaction = null;
	        List<FinacingEvent> res = null;
	        try {
	            entityTransaction = entityManager.getTransaction();
	            entityTransaction.begin();
//	            StringBuffer querySQL = new StringBuffer("select  * from  rp_finacing_event t where datediff(now(),operdate) = :day and status = '11' ");
	            StringBuffer querySQL = new StringBuffer("select  * from  rp_finacing_event t where datediff(now(),operdate) = :day and status = '11' ");

	            Query query = entityManager.createNativeQuery(querySQL.toString(),FinacingEvent.class);
				query.setParameter("day", day);
				res = (List<FinacingEvent>) query.getResultList();
	            entityTransaction.commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	            entityTransaction.rollback();
	        } finally {
	        	closeEntityManager(entityManager);
	        }
	        return res;
	}
	/**
	 * 查询出来 机构总数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<SendSmsLog> findFinacingEventLog(String type,String evenId){
		    EntityManager entityManager = this.getEntityManager();
	        EntityTransaction entityTransaction = null;
	        List<SendSmsLog> res = null;
	        try {
	            entityTransaction = entityManager.getTransaction();
	            entityTransaction.begin();
	            StringBuffer querySQL=null;
	            if(type.equals("1")){//已回复 机构
		             querySQL = new StringBuffer("select  * from  rp_send_sms_log t where answer_date=(select MAX(answer_date) from rp_send_sms_log where send_type in('06','07') and send_status='03' and infoorevent_id= :evenId ) and send_type in('06','07') and send_status='03' and infoorevent_id= :evenId1 ");

	            }else if(type.equals("2")){//未回复 机构
		             querySQL = new StringBuffer("select  * from  rp_send_sms_log t where send_date=(select MAX(send_date) from rp_send_sms_log where send_type='06' and send_status='01' and  infoorevent_id= :evenId ) and send_type='06' and send_status='01' and infoorevent_id= :evenId1 ");

	            }else  if(type.equals("21")){//未回复 机构
		             querySQL = new StringBuffer("select  * from  rp_send_sms_log t where send_date=(select MAX(send_date) from rp_send_sms_log where send_type='07'  and send_status='01' and infoorevent_id= :evenId ) and send_type='07'  and send_status='01' and infoorevent_id= :evenId1 ");

	            } else  if(type.equals("3")){//未回复 企业
		             querySQL = new StringBuffer("select  * from  rp_send_sms_log t where send_date=(select MAX(send_date) from rp_send_sms_log where send_type='01' and send_status='01' and infoorevent_id= :evenId ) and send_type='01' and send_status='01' and infoorevent_id= :evenId1 ");

	            }
	            Query query = entityManager.createNativeQuery(querySQL.toString(),SendSmsLog.class);
				query.setParameter("evenId", evenId);
				query.setParameter("evenId1", evenId);
				res = (List<SendSmsLog>) query.getResultList();
	            entityTransaction.commit();
	        } catch (Exception e) {
	            e.printStackTrace();
	            entityTransaction.rollback();
	        } finally {
	        	closeEntityManager(entityManager);
	        }
	        return res;
	}

	public static void main(String[] args) {
//		   Date date = new Date();
		   Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-21");
			   System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(date));
				  Calendar cal = Calendar.getInstance();
				  cal.setTime(date);
				  cal.add(Calendar.DAY_OF_MONTH, 20);
				  System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime()));
			        Calendar c = Calendar.getInstance();
			        Date  now = new Date();
			        if(date.after(now)) {
			            System.out.println("超过了今天");
			        }
			        else {
			            System.out.println("没有超过今天");
			        }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		}
	
}

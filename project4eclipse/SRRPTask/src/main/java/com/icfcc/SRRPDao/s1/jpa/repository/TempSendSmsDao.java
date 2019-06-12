package com.icfcc.SRRPDao.s1.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.icfcc.SRRPDao.s1.jpa.entity.SendSmsLog;
import com.icfcc.SRRPDao.s1.jpa.entity.TempSendSms;


public interface  TempSendSmsDao extends JpaRepository<TempSendSms, String>,
JpaSpecificationExecutor<TempSendSms>{

	
	/**
	 * 通过企业ID查询企业基本信息
	 * @param id
	 * @return
	 */
	@Query("select c from TempSendSms c where c.tssId=?1")
	TempSendSms findById(String tssId);
	@Query("select c from TempSendSms c where c.infooreventId=?1 and c.sendType=?2 ")
	TempSendSms findByEnventId(String infooreventId,String type);
	@Query("select c from TempSendSms c where c.rssId=?1 and c.sendType=?2 ")
	TempSendSms findByLogId(String rssId,String type);
	@Modifying
	@Query("delete  from TempSendSms c where c.rssId=?1 and c.sendType=?2 ")
	public void DeleteByLogId(String rssId,String type);
	@Modifying
	@Query("delete  from TempSendSms c where c.rssId=?1 ")
	public void DeleteByLogId(String rssId);
	
	
	
}


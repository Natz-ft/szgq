package com.icfcc.SRRPDao.s1.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.icfcc.SRRPDao.s1.jpa.entity.SendSmsLog;


public interface  SendSmsLogDao extends JpaRepository<SendSmsLog, String>,
JpaSpecificationExecutor<SendSmsLog>{

	
	/**
	 * 通过企业ID查询企业基本信息
	 * @param id
	 * @return
	 */
	@Query("select c from SendSmsLog c where c.sid=?1")
	SendSmsLog findById(String enterpriseId);
	@Query("select c from SendSmsLog c where c.infoId=?1 and sendType=?2 ")
	SendSmsLog findlogbyInfo(String infoId,String type);
	@Query("select c from SendSmsLog c where c.infoId=?1 and sendType in('06','07') and  sendStatus='03'")
	SendSmsLog findlogbyInfo(String infoId);
	@Query("select c from SendSmsLog c where c.infoId=?1 and sendType in('06','07','012') ")
	SendSmsLog getlogbyInfo(String infoId);
	
	
}


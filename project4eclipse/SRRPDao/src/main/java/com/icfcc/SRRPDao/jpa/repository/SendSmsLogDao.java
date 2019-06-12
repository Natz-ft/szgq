package com.icfcc.SRRPDao.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.icfcc.SRRPDao.jpa.entity.SendSmsLog;



public interface  SendSmsLogDao extends JpaRepository<SendSmsLog, String>,
JpaSpecificationExecutor<SendSmsLog>{

	
	/**
	 * 通过企业ID查询企业基本信息
	 * @param id
	 * @return
	 */
	@Query("select c from SendSmsLog c where c.sid=?1")
	SendSmsLog findById(String sid);
	@Query("select c from SendSmsLog c where c.rssId=?1")
	SendSmsLog findByrssId(String rssId);
	
	
}


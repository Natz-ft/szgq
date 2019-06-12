package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.icfcc.credit.platform.jpa.entity.system.TempSendSms;




public interface  TempSendSmsDao extends JpaRepository<TempSendSms, String>,
JpaSpecificationExecutor<TempSendSms>{

	
	/**
	 * 通过企业ID查询企业基本信息
	 * @param id
	 * @return
	 */

	@Query("select c from TempSendSms c where c.sendStatus=?1 ")
	List<TempSendSms> findBySendType(String status);

	
	
}


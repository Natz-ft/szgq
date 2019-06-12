package com.icfcc.SRRPDao.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.SendSmsLog;
import com.icfcc.SRRPDao.jpa.entity.SomeLog;


@Component
public interface  SomeLogDao extends JpaRepository<SomeLog, String>,JpaSpecificationExecutor<SomeLog>{

	
	
}


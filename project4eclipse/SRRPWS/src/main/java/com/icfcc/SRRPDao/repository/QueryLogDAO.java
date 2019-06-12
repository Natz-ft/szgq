package com.icfcc.SRRPDao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.entity.QueryLog;


public interface QueryLogDAO  extends  PagingAndSortingRepository<QueryLog, String>,JpaSpecificationExecutor<QueryLog> {

}

package com.icfcc.SRRPDao.jpa.repository.creditscore;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.creditscore.QueryLog;

public interface QueryLogDAO  extends  PagingAndSortingRepository<QueryLog, String>,JpaSpecificationExecutor<QueryLog> {

}

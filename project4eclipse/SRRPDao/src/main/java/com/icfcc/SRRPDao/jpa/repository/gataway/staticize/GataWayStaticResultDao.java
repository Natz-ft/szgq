package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayStaticResult;

public interface GataWayStaticResultDao extends PagingAndSortingRepository<GataWayStaticResult, String>,
        JpaSpecificationExecutor<GataWayStaticResult> {

    @Query("from GataWayStaticResult order by staticType asc ")
    List<GataWayStaticResult> findAll();

}

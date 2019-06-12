package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayInvestorInfo;

public interface GataWayInvestorInfoDao extends PagingAndSortingRepository<GataWayInvestorInfo, String>, JpaSpecificationExecutor<GataWayInvestorInfo> {

    @Query("from GataWayInvestorInfo")
    List<GataWayInvestorInfo> findAll();

}

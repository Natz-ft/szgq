package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayDemandQuery;

public interface GataWayDemandQueryDao extends PagingAndSortingRepository<GataWayDemandQuery, String>,
        JpaSpecificationExecutor<GataWayDemandQuery> {

    @Query("select u from GataWayDemandQuery u where u.infoId=? ")
    GataWayDemandQuery findById(String infoId);

    @Query("from GataWayDemandQuery")
    List<GataWayDemandQuery> findAll();
}

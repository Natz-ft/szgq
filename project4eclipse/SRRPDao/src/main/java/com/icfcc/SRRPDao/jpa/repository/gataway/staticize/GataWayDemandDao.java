package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayDemand;

public interface GataWayDemandDao extends PagingAndSortingRepository<GataWayDemand, String>,
        JpaSpecificationExecutor<GataWayDemand> {

    @Query("select d from GataWayDemand d")
    List<GataWayDemand> findAll(Sort sort);

}

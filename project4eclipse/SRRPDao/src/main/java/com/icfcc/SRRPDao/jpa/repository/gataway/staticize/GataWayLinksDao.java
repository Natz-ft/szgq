package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayLinks;

public interface GataWayLinksDao extends PagingAndSortingRepository<GataWayLinks, String>, JpaSpecificationExecutor<GataWayLinks> {

    @Query("from GataWayLinks")
    List<GataWayLinks> findAll();

}

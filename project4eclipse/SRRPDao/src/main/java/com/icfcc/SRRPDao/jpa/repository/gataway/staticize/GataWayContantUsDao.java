package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayContantUs;

public interface GataWayContantUsDao extends PagingAndSortingRepository<GataWayContantUs, String>,
        JpaSpecificationExecutor<GataWayContantUs> {

    @Query("from GataWayContantus")
    List<GataWayContantUs> findAll();

}

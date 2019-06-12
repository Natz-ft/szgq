package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayStatic;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformNews;

import com.icfcc.SRRPDao.jpa.repository.BaseNativeQueryDao;

@Component
public interface GataWayStaticDao extends PagingAndSortingRepository<GataWayStatic, String>,JpaSpecificationExecutor<GataWayStatic> {

    @Query("from GataWayStatic")
    GataWayStatic findById();
    
}

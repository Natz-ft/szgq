package com.icfcc.SRRPDao.s.jpa.repository.portal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalContantUs;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalContantUsDist;


public interface PlatformPortalContantUsDistDao extends PagingAndSortingRepository<PlatformPortalContantUsDist, String>,
        JpaSpecificationExecutor<PlatformPortalContantUs> {

    @Query("from GataWayContantusDist")
    List<PlatformPortalContantUsDist> findAll();

}

package com.icfcc.SRRPDao.s.jpa.repository.portal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s.jpa.entity.portal.GataWayStaticResult;

@Component
public interface PortalStaticByAreaDao extends PagingAndSortingRepository<GataWayStaticResult, String>,
        JpaSpecificationExecutor<GataWayStaticResult> {

}

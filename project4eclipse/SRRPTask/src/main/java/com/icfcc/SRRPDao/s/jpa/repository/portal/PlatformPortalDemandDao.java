package com.icfcc.SRRPDao.s.jpa.repository.portal;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalDemand;

@Repository
public interface PlatformPortalDemandDao extends PagingAndSortingRepository<PlatformPortalDemand, String>,
        JpaSpecificationExecutor<PlatformPortalDemand> {


}

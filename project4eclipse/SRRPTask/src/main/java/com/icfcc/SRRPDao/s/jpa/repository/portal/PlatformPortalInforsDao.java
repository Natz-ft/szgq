package com.icfcc.SRRPDao.s.jpa.repository.portal;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalInfos;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalPartner;

public interface PlatformPortalInforsDao extends PagingAndSortingRepository<PlatformPortalInfos, String>,
        JpaSpecificationExecutor<PlatformPortalInfos> {

}

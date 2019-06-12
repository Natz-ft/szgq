package com.icfcc.SRRPDao.s.jpa.repository.portal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalFaqAndNotice;

public interface PlatformPortalFaqAndNoticeDao extends PagingAndSortingRepository<PlatformPortalFaqAndNotice, String>,
        JpaSpecificationExecutor<PlatformPortalFaqAndNotice> {

    @Query("from GataWayFaqAndNotice")
    List<PlatformPortalFaqAndNotice> findAll();

}

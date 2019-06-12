package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformLog;

public interface PlatformLogDao extends PagingAndSortingRepository<PlatformLog, Long>, JpaSpecificationExecutor<PlatformLog>
{
    
}

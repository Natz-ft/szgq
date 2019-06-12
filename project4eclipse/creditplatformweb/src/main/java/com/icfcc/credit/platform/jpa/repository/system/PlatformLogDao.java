package com.icfcc.credit.platform.jpa.repository.system;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformLog;

public interface PlatformLogDao extends PagingAndSortingRepository<PlatformLog, Long>, JpaSpecificationExecutor<PlatformLog>
{
    
}

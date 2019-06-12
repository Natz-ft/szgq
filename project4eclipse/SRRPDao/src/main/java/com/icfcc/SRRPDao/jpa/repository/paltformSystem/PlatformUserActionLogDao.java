package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserActionLog;


public interface PlatformUserActionLogDao extends PagingAndSortingRepository<PlatformUserActionLog, String>
,JpaSpecificationExecutor<PlatformUserActionLog>{
	@Query("select action from PlatformUserActionLog action where action.id=?1")
	List<PlatformUserActionLog> findById(String id);
}

package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformUserActionLog;


public interface PlatformUserActionLogDao extends PagingAndSortingRepository<PlatformUserActionLog, String>
,JpaSpecificationExecutor<PlatformUserActionLog>{
	@Query("select action from PlatformUserActionLog action where action.id=?1")
	List<PlatformUserActionLog> findById(String id);
}

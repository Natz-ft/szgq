package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserLoginLog;


public interface PlatformUserLoginLogDao extends PagingAndSortingRepository<PlatformUserLoginLog, String>
,JpaSpecificationExecutor<PlatformUserLoginLog> {
	@Query("select s from PlatformUserLoginLog s where s.userId=?1 order by s.loginTime desc")
	List<PlatformUserLoginLog> findByUserId(String id);
	
}

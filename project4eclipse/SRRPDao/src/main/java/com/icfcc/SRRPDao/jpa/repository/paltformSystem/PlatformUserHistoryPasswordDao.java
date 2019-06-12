package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserHistoryPassword;
/**
 * 
* @ClassName: PlatformUserHistoryPasswordDao
* @Description: TODO()
* @author hugt
* @date 2017��9��14�� ����7:24:27
*
 */
public interface PlatformUserHistoryPasswordDao extends PagingAndSortingRepository<PlatformUserHistoryPassword, String>
,JpaSpecificationExecutor<PlatformUserHistoryPassword>{
	@Query("select p from PlatformUserHistoryPassword p where p.userId=?1")
	List<PlatformUserHistoryPassword> findByUserId(String id);
}

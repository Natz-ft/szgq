package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformUserHistoryPassword;
/**
 * 
* @ClassName: PlatformUserHistoryPasswordDao
* @Description: TODO()
* @author hugt
* @date 2017年9月14日 下午7:24:27
*
 */
public interface PlatformUserHistoryPasswordDao extends PagingAndSortingRepository<PlatformUserHistoryPassword, String>
,JpaSpecificationExecutor<PlatformUserHistoryPassword>{
	@Query("select p from PlatformUserHistoryPassword p where p.userId=?1 order by createTime desc ")
	List<PlatformUserHistoryPassword> findByUserId(String id);
}

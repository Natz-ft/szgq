package com.icfcc.SRRPDao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.entity.PlatformUser;

/**
 * 
* @ClassName: PlatformUserDao
* @Description: TODO(用户信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:23:41
*
 */
public interface PlatformUserDao extends PagingAndSortingRepository<PlatformUser, String>
,JpaSpecificationExecutor<PlatformUser> 
{
	@Query("select u from PlatformUser u where u.username=?1")
	PlatformUser findByUserName(String username);
}

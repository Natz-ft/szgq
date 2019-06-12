package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformUserRole;
/**
 * 
* @ClassName: SysUserRoleDao
* @Description: TODO(用户权限的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:25:37
*
 */
public interface SysUserRoleDao extends PagingAndSortingRepository<PlatformUserRole, Long>, JpaSpecificationExecutor<PlatformUserRole> {
	/**
	 * <根据userId 查询 用户角色关系>
	 * 
	 * @param userId
	 * @return
	 * 
	 */
	@Query(" select r from PlatformUserRole r where r.userId=?1 ")
	List<PlatformUserRole> findByUserId(String userId);
}

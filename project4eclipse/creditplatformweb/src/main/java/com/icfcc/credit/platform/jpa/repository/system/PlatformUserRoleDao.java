package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformUserRole;
/**
 * 
* @ClassName: PlatformUserRoleDao
* @Description: TODO(实现用户权限的增删改查)
* @author hugt
* @date 2017年9月14日 下午7:24:41
*
 */
public interface PlatformUserRoleDao extends PagingAndSortingRepository<PlatformUserRole, Long>,
    JpaSpecificationExecutor<PlatformUserRole>
{
	
    
    /**
     * <根据userId 查询 用户角色关系>
     * 
     * @param userId
     * @return
     * 
     */
    @Query(" select r from PlatformUserRole r where r.userId=?1 ")
    List<PlatformUserRole> findByUserId(String userId);
    
    /**
     * <根据userId + roleId 删除 用户角色关系>
     * 
     * @param uId
     * @param rId
     * 
     */
    @Modifying
    @Query("delete  from PlatformUserRole s where s.userId=?1 and s.roleId=?2 ")
    void deleteByUserRoleId(String uId, Long rId);
    
    /**
     * <根据userId删除所有的关联关系，用于角色变更时>
     * @param userId
     */
    @Modifying
    @Query("delete from PlatformUserRole s where s.userId=?1")
    void deleteByUserId(String userId);
    /**
     * <根据 userId 和 roleId查询 是否存在 用户角色关系>
     * 
     * @param uId
     * @param rId
     * @return
     */
    @Query(" select COUNT(r) from PlatformUserRole r where r.userId=?1  and r.roleId=?2")
    Long getCountByUserRoleId(String uId, Long rId);
    
    /**
     * <根据用户id 查询 角色id>
     * 
     * @param string
     * @return
     */
    @Query(" select roleId from PlatformUserRole r where r.userId=?1 ")
    List<Long> findRoleIdByUserId(String string);
    
}

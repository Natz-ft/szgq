package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleButton;
/**
 * 
* @ClassName: PlatformRoleButtonDao
* @Description: TODO(角色按钮信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:21:51
*
 */
public interface PlatformRoleButtonDao extends PagingAndSortingRepository<PlatformRoleButton, Long>,
    JpaSpecificationExecutor<PlatformRoleButton>
{
    /**
     * 根据角色ID查询角色按钮权限关系
     * 
     * @param roleId
     * @return
     * 
     */
    List<PlatformRoleButton> findByRoleId(Long roleId);
    
    /**
     * 根据按钮代码查询角色按钮权限关系
     * 
     * @param buttonCode
     * @return
     * 
     */
    List<PlatformRoleButton> findByButtonCode(String buttonCode);
    
    /**
     * 删除角色对应的按钮权限
     * 
     * @param roleId
     * 
     */
    @Modifying
    @Query("delete from PlatformRoleButton u where u.roleId=?1")
    void deleteByRoleId(Long roleId);
    
    /**
     * 根据按钮代码删除按钮权限
     * 
     * @param buttonCode
     * 
     */
    @Modifying
    @Query("delete from PlatformRoleButton u where u.buttonCode=?1")
    void deleteByButtonCode(String buttonCode);
    
    /**
     * 根据按钮代码删除按钮权限
     * 
     * @param buttonCode
     * 
     */
    @Modifying
    @Query("update PlatformRoleButton u set u.buttonCode = ?1 where u.roleId = ?2")
    void updateButtonCodeByRoleId(String buttonCode, Long roleId);
}

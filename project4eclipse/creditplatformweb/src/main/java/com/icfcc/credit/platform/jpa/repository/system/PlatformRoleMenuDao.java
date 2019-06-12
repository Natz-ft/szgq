package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleMenu;
/**
 * 
* @ClassName: PlatformRoleMenuDao
* @Description: TODO(菜单权限的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:23:10
*
 */
public interface PlatformRoleMenuDao extends PagingAndSortingRepository<PlatformRoleMenu, String>, JpaSpecificationExecutor<PlatformRoleMenu> {
	/**
     * <根据菜单Id和角色Id删除按钮权限>
     * 
     * @param menuId
     * @param roleId
     * 
     */
    @Modifying
    @Query("delete from PlatformRoleMenu u where u.menuId=?1")
    void deleteByMenuId(String menuId);
    
    List<PlatformRoleMenu> findByRoleId(Long roleId);
    
    @Modifying
    @Query("delete from PlatformRoleMenu u where u.roleId=?1")
    void deleteByRoleId(Long id);
    
    
    
    /**
     * <一句话功能简述>
     * 
     * @param roleIdList
     * @return
     * 
     */
    @Query("select distinct(menuId) from  PlatformRoleMenu u where u.roleId in(?1)")
    List<String> findMenuIdByRoleId(List<Long> roleIdList);
}

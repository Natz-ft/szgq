package com.icfcc.credit.platform.jpa.repository.system;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformQuickMenu;

/**
 * 
* @ClassName: PlatformQuickMenuDao
* @Description: TODO(快捷菜单的增删改查实现)
* @author hugt
* @date 2017年9月14日 下午7:21:29
*
 */
public interface PlatformQuickMenuDao extends PagingAndSortingRepository<PlatformQuickMenu, Long>,
JpaSpecificationExecutor<PlatformQuickMenu>
{
	/**
     * 根据角色ID查询快捷菜单
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformQuickMenu findById(Long id);
	
	
	/**
     * 根据角色ID删除快捷菜单
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformQuickMenu s where s.id=?1")
	void deleteById(Long id);
	/**
	 * 添加一个新的快捷菜单
	 * @param message
	 * @return
	 */
	
	
	
	
	/**
     * 查询所有快捷菜单
     * 
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformQuickMenu> findAll();
	
	
	
	
}

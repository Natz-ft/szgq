package com.icfcc.credit.platform.jpa.repository.system;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.icfcc.credit.platform.jpa.entity.system.PlatformfriendlyLinks;

/**
 * 
* @ClassName: PlatformFriendlyLinksDao
* @Description: TODO(友情链接信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:18:10
*
 */
public interface PlatformFriendlyLinksDao extends PagingAndSortingRepository<PlatformfriendlyLinks, Long>,
JpaSpecificationExecutor<PlatformfriendlyLinks>
{
	/**
     * 根据角色ID查询友情链接
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformfriendlyLinks findById(Long id);
	
	
	/**
     * 根据角色ID删除友情链接
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformfriendlyLinks s where s.id=?1")
	void deleteById(Long id);
	/**
	 * 添加一个新的友情链接
	 * @param message
	 * @return
	 */
	
	
	
	
	/**
     * 查询所有友情链接
     * 
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformfriendlyLinks> findAll();
	
	
	
	
}

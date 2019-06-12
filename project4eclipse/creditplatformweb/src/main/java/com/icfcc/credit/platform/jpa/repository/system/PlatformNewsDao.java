package com.icfcc.credit.platform.jpa.repository.system;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.icfcc.credit.platform.jpa.entity.system.PlatformNews;

/**
 * 
* @ClassName: PlatformNewsDao
* @Description: TODO( 新闻动态信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:20:12
*
 */
public interface PlatformNewsDao extends PagingAndSortingRepository<PlatformNews, Long>,
JpaSpecificationExecutor<PlatformNews>
{
	/**
     * 根据角色ID查询新闻动态
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformNews findById(Long id);
	
	
	/**
     * 根据角色ID删除新闻动态
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformNews s where s.id=?1")
	void deleteById(Long id);
	/**
	 * 添加一个新的新闻动态
	 * @param message
	 * @return
	 */
	
	
	
	
	/**
     * 查询所有新闻动态
     * 
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformNews> findAll();
	
	
	
	
}

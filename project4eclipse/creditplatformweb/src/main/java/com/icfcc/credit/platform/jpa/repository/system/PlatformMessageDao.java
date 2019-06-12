package com.icfcc.credit.platform.jpa.repository.system;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformMessage;
/**
 * 
* @ClassName: PlatformMessageDao
* @Description: TODO( 消息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:19:53
*
 */
public interface PlatformMessageDao extends PagingAndSortingRepository<PlatformMessage, Long>,
JpaSpecificationExecutor<PlatformMessage>
{
	/**
     * 根据角色ID查询消息
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformMessage findById(Long id);
	
	
	/**
     * 根据角色ID删除消息
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformMessage s where s.id=?1")
	void deleteById(Long id);
	/**
	 * 添加一个新的消息
	 * @param message
	 * @return
	 */
	
	
	
	
	/**
     * 查询所有消息
     * 
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformMessage> findAll();
	
	
	
	
}

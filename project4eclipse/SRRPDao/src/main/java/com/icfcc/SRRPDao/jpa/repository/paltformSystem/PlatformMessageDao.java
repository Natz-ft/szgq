package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformMessage;
/**
 * 
* @ClassName: PlatformMessageDao
* @Description: TODO( ��Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:19:53
*
 */
public interface PlatformMessageDao extends PagingAndSortingRepository<PlatformMessage, Long>,
JpaSpecificationExecutor<PlatformMessage>
{
	/**
     * ���ݽ�ɫID��ѯ��Ϣ
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformMessage findById(Long id);
	
	
	/**
     * ���ݽ�ɫIDɾ����Ϣ
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformMessage s where s.id=?1")
	void deleteById(Long id);
	/**
	 * ���һ���µ���Ϣ
	 * @param message
	 * @return
	 */
	
	
	
	
	/**
     * ��ѯ������Ϣ
     * 
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformMessage> findAll();
	
	
	
	
}

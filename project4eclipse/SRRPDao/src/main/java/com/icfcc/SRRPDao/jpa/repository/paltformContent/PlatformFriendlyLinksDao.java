package com.icfcc.SRRPDao.jpa.repository.paltformContent;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformfriendlyLinks;


/**
 * 
* @ClassName: PlatformFriendlyLinksDao
* @Description: TODO(����������Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:18:10
*
 */
public interface PlatformFriendlyLinksDao extends PagingAndSortingRepository<PlatformfriendlyLinks, Long>,
JpaSpecificationExecutor<PlatformfriendlyLinks>
{
	/**
     * ���ݽ�ɫID��ѯ��������
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformfriendlyLinks findById(Long id);
	
	
	/**
     * ���ݽ�ɫIDɾ����������
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformfriendlyLinks s where s.id=?1")
	void deleteById(Long id);
	/**
	 * ���һ���µ���������
	 * @param message
	 * @return
	 */
	
	
	
	
	/**
     * ��ѯ������������
     * 
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformfriendlyLinks> findAll();
	
	
	
	
}

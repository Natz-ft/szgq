package com.icfcc.SRRPDao.jpa.repository.paltformContent;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformFaq;


/**
 * 
* @ClassName: PlatformFaqDao
* @Description: TODO(����������Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:17:16
*
 */
public interface PlatformFaqDao extends PagingAndSortingRepository<PlatformFaq, Long>,
JpaSpecificationExecutor<PlatformFaq>
{
	/**
     * ���ݽ�ɫID��ѯ��Ϣ
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformFaq findById(Long id);
	
	
	/**
     * ���ݽ�ɫIDɾ����Ϣ
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformFaq s where s.id=?1")
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
	ArrayList<PlatformFaq> findAll();
	
	
	
	
}

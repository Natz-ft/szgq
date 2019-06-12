package com.icfcc.SRRPDao.jpa.repository.paltformContent;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformNews;


/**
 * 
* @ClassName: PlatformNewsDao
* @Description: TODO( ���Ŷ�̬��Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:20:12
*
 */
public interface PlatformNewsDao extends PagingAndSortingRepository<PlatformNews, Long>,
JpaSpecificationExecutor<PlatformNews>
{
	/**
     * ���ݽ�ɫID��ѯ���Ŷ�̬
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformNews findById(Long id);
	
	
	/**
     * ���ݽ�ɫIDɾ�����Ŷ�̬
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformNews s where s.id=?1")
	void deleteById(Long id);
	/**
	 * ���һ���µ����Ŷ�̬
	 * @param message
	 * @return
	 */
	
	
	
	
	/**
     * ��ѯ�������Ŷ�̬
     * 
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformNews> findAll();
	
	
	
	
}

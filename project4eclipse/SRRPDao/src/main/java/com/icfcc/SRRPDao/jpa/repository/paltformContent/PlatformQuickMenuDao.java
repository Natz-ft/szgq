package com.icfcc.SRRPDao.jpa.repository.paltformContent;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformQuickMenu;


/**
 * 
* @ClassName: PlatformQuickMenuDao
* @Description: TODO(��ݲ˵�����ɾ�Ĳ�ʵ��)
* @author hugt
* @date 2017��9��14�� ����7:21:29
*
 */
public interface PlatformQuickMenuDao extends PagingAndSortingRepository<PlatformQuickMenu, Long>,
JpaSpecificationExecutor<PlatformQuickMenu>
{
	/**
     * ���ݽ�ɫID��ѯ��ݲ˵�
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformQuickMenu findById(Long id);
	
	
	/**
     * ���ݽ�ɫIDɾ����ݲ˵�
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformQuickMenu s where s.id=?1")
	void deleteById(Long id);
	/**
	 * ���һ���µĿ�ݲ˵�
	 * @param message
	 * @return
	 */
	
	
	
	
	/**
     * ��ѯ���п�ݲ˵�
     * 
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformQuickMenu> findAll();
	
	
	
	
}

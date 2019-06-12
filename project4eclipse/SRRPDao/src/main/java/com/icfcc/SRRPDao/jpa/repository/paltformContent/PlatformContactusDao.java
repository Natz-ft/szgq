package com.icfcc.SRRPDao.jpa.repository.paltformContent;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformContactus;

/**
 * 
* @ClassName: PlatformContactusDao
* @Description: TODO(��ϵ������Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:15:31
*
 */
public interface PlatformContactusDao extends PagingAndSortingRepository<PlatformContactus, Long>,
    JpaSpecificationExecutor<PlatformContactus>
{
   
    
    /**
     * ����id��ȡ���������
     * 
     * @param id
     * @return
     */
    PlatformContactus findById(Long id);;
    
    /**
     * ����IDɾ��
     * 
     * @param id
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformContactus s where s.id=?1")
	void deleteById(Long id);
	/**
     * ��ѯ���к������
     * 
     * @param 
     * @return
     * 
     */
	ArrayList<PlatformContactus> findAll();
	
    
  
    
}

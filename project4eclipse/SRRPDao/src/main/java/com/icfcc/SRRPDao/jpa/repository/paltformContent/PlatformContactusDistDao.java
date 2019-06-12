package com.icfcc.SRRPDao.jpa.repository.paltformContent;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformContactusDist;

/**
 * 
* @ClassName: PlatformContactusDistDao
* @Description: TODO(��ϵ���ǵ��¼���ϵ����ɾ �Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:16:10
*
 */
public interface PlatformContactusDistDao extends PagingAndSortingRepository<PlatformContactusDist, Long>,
    JpaSpecificationExecutor<PlatformContactusDist>
{
   
    
    /**
     * @param id
     * @return
     */
    PlatformContactusDist findById(Long id);
    
    /**
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformContactusDist s where s.id=?1")
	void deleteById(Long id);
	/**
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformContactusDist> findAll();
    
}

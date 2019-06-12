package com.icfcc.credit.platform.jpa.repository.system;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformContactusDist;
/**
 * 
* @ClassName: PlatformContactusDistDao
* @Description: TODO(联系我们的下级联系的增删 改查接口)
* @author hugt
* @date 2017年9月14日 下午7:16:10
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

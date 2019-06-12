package com.icfcc.credit.platform.jpa.repository.system;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.credit.platform.jpa.entity.system.PlatformContactus;
/**
 * 
* @ClassName: PlatformContactusDao
* @Description: TODO(联系我们信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:15:31
*
 */
public interface PlatformContactusDao extends PagingAndSortingRepository<PlatformContactus, Long>,
    JpaSpecificationExecutor<PlatformContactus>
{
   
    
    /**
     * 根据id获取合作伙伴项
     * 
     * @param id
     * @return
     */
    PlatformContactus findById(Long id);;
    
    /**
     * 根据ID删除
     * 
     * @param id
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformContactus s where s.id=?1")
	void deleteById(Long id);
	/**
     * 查询所有合作伙伴
     * 
     * @param 
     * @return
     * 
     */
	ArrayList<PlatformContactus> findAll();
	
    
  
    
}

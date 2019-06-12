package com.icfcc.credit.platform.jpa.repository.system;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.icfcc.credit.platform.jpa.entity.system.PlatformBusinessPartner;

/**
 * 
* @ClassName: PlatformBusinessPartnerDao
* @Description: TODO(合作伙伴信息增删改查接口)
* @author hugt
* @date 2017年9月14日 下午6:46:22
*
 */
public interface PlatformBusinessPartnerDao extends PagingAndSortingRepository<PlatformBusinessPartner, Long>,
JpaSpecificationExecutor<PlatformBusinessPartner>
{
	/**
     * 根据角色ID查询合作单位
     * 
     * @param messageId
     * @return
     * 
     */
	PlatformBusinessPartner findById(Long id);
	
	
	/**
     * 根据角色ID删除合作单位
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from PlatformBusinessPartner s where s.id=?1")
	void deleteById(Long id);
	/**
	 * 添加一个新的合作单位
	 * @param message
	 * @return
	 */
	
	
	
	
	/**
     * 查询所有合作单位
     * 
     * @param messageId
     * @return
     * 
     */
	ArrayList<PlatformBusinessPartner> findAll();
	
	
	
	
}

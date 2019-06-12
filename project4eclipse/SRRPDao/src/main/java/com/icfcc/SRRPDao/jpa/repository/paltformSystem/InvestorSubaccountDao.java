package com.icfcc.SRRPDao.jpa.repository.paltformSystem;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.InvestorSubaccount;



/**
 * 
* @ClassName: PlatformFaqDao
* @Description: TODO(常见问题信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:17:16
*
 */
public interface InvestorSubaccountDao extends PagingAndSortingRepository<InvestorSubaccount, Long>,
JpaSpecificationExecutor<InvestorSubaccount>
{
	/**
     * 根据角色ID查询消息
     * 
     * @param messageId
     * @return
     * 
     */
	@Query("select s from InvestorSubaccount s where s.userId=?1")
	InvestorSubaccount findById(String id);
	
	@Query("select s from InvestorSubaccount s where s.certno=?1")
    InvestorSubaccount findByCertNo(String certno);
	
	/**
     * 根据角色ID删除消息
     * 
     * @param messageId
     * @return
     * 
     */
	@Modifying
	@Query("delete from InvestorSubaccount s where s.userId=?1")
	void deleteById(String id);
	
	
	
	
	
	
	
	
	
	
}

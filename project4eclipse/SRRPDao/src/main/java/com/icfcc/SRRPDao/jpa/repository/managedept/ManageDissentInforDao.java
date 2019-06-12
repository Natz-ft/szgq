package com.icfcc.SRRPDao.jpa.repository.managedept;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageDissentInfor;

/**
* @ClassName: ManageDissentInforDao
* @Description: TODO(主管异议功能dao层)
* @author hugt
* @date 2018年12月25日 上午11:21:34
*/
@Component
public interface ManageDissentInforDao extends PagingAndSortingRepository<ManageDissentInfor, String>,
JpaSpecificationExecutor<ManageDissentInfor> {
	
	/**
	* @Title: findDissentById
	* @Description: TODO(根据主键查询异议信息)
	* @author  
	* @date  2018年12月25日 上午11:22:35
	* @param loanIds
	* @return ManageDissentInfor
	*/
	@Query("select m from ManageDissentInfor m where dissentId =?1 ")
	ManageDissentInfor findDissentById(String dissentId);
	/**
	 * 
	* @Title: findDissentBycompanyId
    * @Description: TODO(根据企业Id查询异议信息)
	* @author  
	* @date  2018年12月25日 上午11:44:17
	* @param companyId
	* @return ManageDissentInfor
	 */
	@Query("select m from ManageDissentInfor m where companyId =?1  ")
    ManageDissentInfor findDissentBycompanyId(String companyId);
	
	/**
	 * 
	* @Title: deleteByDissentId
	* @Description: TODO(删除操作)
	* @author  
	* @date  2018年12月25日 上午11:44:21
	* @param dissentId
	 */
	@Modifying
    @Query("delete from ManageDissentInfor u where u.dissentId =?1 ")
    public void deleteByDissentId(String dissentId);
    
//    @Modifying
//    @Query("update PlatformUser u set u.stopFlag = ?2 where u.dissentId =?1 ")
//    public void updateStatus(String orgId, int stopFlag);
}

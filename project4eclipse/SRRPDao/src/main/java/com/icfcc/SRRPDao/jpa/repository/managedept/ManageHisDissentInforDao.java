package com.icfcc.SRRPDao.jpa.repository.managedept;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageDissentInfor;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageHisDissentInfor;

/**
* @ClassName: ManageDissentInforDao
* @Description: TODO(主管异议功能历史记录dao层)
* @author hugt
* @date 2018年12月25日 上午11:21:34
*/
@Component
public interface ManageHisDissentInforDao extends PagingAndSortingRepository<ManageHisDissentInfor, String>,
JpaSpecificationExecutor<ManageHisDissentInfor> {
	
	/**
	* @Title: findDissentById
	* @Description: TODO(根据主键查询异议信息)
	* @author  
	* @date  2018年12月25日 上午11:22:35
	* @param loanIds
	* @return ManageDissentInfor
	*/
	@Query("select m from ManageHisDissentInfor m where dissentId =?1 ")
	List<ManageHisDissentInfor> findDissentById(String dissentId);
	/**
	 * 
	* @Title: findDissentBycompanyId
    * @Description: TODO(根据企业Id查询异议信息)
	* @author  
	* @date  2018年12月25日 上午11:44:17
	* @param companyId
	* @return ManageDissentInfor
	 */
	@Query("select m from ManageHisDissentInfor m where companyId =?1 order by operUpdateDate desc ")
	List<ManageHisDissentInfor> findDissentBycompanyId(String companyId);
	
	/**
	 * 
	* @Title: deleteByDissentId
	* @Description: TODO(删除操作)
	* @author  
	* @date  2018年12月25日 上午11:44:21
	* @param dissentId
	 */
	@Modifying
    @Query("delete from ManageHisDissentInfor u where u.dissentId =?1 ")
    public void deleteByDissentId(String dissentId);
    
//    @Modifying
//    @Query("update PlatformUser u set u.stopFlag = ?2 where u.dissentId =?1 ")
//    public void updateStatus(String orgId, int stopFlag);
}

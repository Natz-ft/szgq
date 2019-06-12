package com.icfcc.SRRPDao.jpa.repository.managedept;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageDissentInfor;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageHisDissentInfor;
import com.icfcc.SRRPDao.jpa.entity.managedept.MessageAlertInfo;

/**
* @ClassName: ManageDissentInforDao
* @Description: TODO(主管消息提醒dao层)
* @author hugt
* @date 2018年12月25日 上午11:21:34
*/
@Component
public interface MessageAlertInfoDao extends PagingAndSortingRepository<MessageAlertInfo, String>,
JpaSpecificationExecutor<MessageAlertInfo> {
	
	/**
	* @Title: findDissentById
	* @Description: TODO(根据主键消息提醒信息)
	* @author  
	* @date  2018年12月25日 上午11:22:35
	* @param loanIds
	* @return ManageDissentInfor
	*/
	@Query("select m from MessageAlertInfo m where messagUserId =?1 ")
	List<MessageAlertInfo> findMessageById(String messagUserId);
	/**
	 * 
	* @Title: findDissentBycompanyId
    * @Description: TODO(根据企业Id查询异议信息)
	* @author  
	* @date  2018年12月25日 上午11:44:17
	* @param companyId
	* @return ManageDissentInfor
	 */
	@Query("select m from MessageAlertInfo m where companyId =?1 and messageType=?2 and messagUserId =?3 order by operDate desc")
	List<MessageAlertInfo> findMessageBycompanyId(String companyId,String type,String userId);
	
	@Query("select m from MessageAlertInfo m where messagUserId =?1 and messageType =?2 order by operDate desc")
    List<MessageAlertInfo> findMessageByMessagType(String messagUserId,String type);
	
	@Query("select m from MessageAlertInfo m where m.companyId =?1 and m.messagUserId =?2 order by operDate desc ")
    List<MessageAlertInfo> findMessageByMessagUserId(String companyId,String userId );
	
	/**
	 * 
	* @Title: deleteByDissentId
	* @Description: TODO(删除操作)
	* @author  
	* @date  2018年12月25日 上午11:44:21
	* @param dissentId
	 */
	@Modifying
    @Query("delete from MessageAlertInfo u where u.companyId =?1 and messagUserId =?2 and messageType=?3")
    public void deleteByMessageId(String companyId,String userId,String type);
	/**
     * 
    * @Title: deleteByDissentId
    * @Description: TODO(删除操作)
    * @author  
    * @date  2018年12月25日 上午11:44:21
    * @param dissentId
     */
    @Modifying
    @Query("delete from MessageAlertInfo u where  messagUserId =?1 and messageType=?2")
    public void deleteByMessageId(String userId,String type);

}

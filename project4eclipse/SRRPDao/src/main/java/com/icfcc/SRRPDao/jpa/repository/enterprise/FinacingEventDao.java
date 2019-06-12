package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEvent;

/**
 * 有关融资事件的相关操作的dao
 * 
 * @author john
 *
 */
@Component
public interface FinacingEventDao
		extends PagingAndSortingRepository<FinacingEvent, String>, JpaSpecificationExecutor<FinacingEvent> {
	// 查询所有的融资事件
	// @Query("select fe from FinacingEventDTO fe")
	// List<Object> listFinacingEvent();

	// 根据多条件进行融资事件查询
	@Query("select fe from FinacingEvent fe ")
	public List<FinacingEvent> listFinacingEventByMailtyTerm(String term);

	/**
	 * 
	 * @param infoId
	 * @param investOrgId
	 * @return
	 */
	@Query("select count(d) from FinacingEvent d where d.infoId=?1")
	public Long countByInfoId(String infoId);

	/**
	 * 根据融资信息的id查询融资事件
	 * 
	 * @param infoId
	 * @return
	 */
	@Query("select fe from FinacingEvent fe where fe.infoId=?1")
	public List<FinacingEvent> findFinacingEventByInfoId(String infoId);

	
	@Query("select fe from FinacingEvent fe where fe.status !=?1 and fe.infoId=?2")
	public List<FinacingEvent> findFinacingEventByInfoIdAndStatus(String status,String infoId);
	/**
	 * 根据需求的id
	 * 查询已经投资的投资事件
	 * @param status
	 * @param infoId
	 * @return
	 */
	@Query(value="select fe.* from rp_finacing_event fe where fe.status in ('31','41','42','43','44','45') and fe.info_id=?1 ORDER BY fe.operdate limit 1",nativeQuery=true)
	public FinacingEvent findOKFinacingEventByInfoId(String infoId);
	
	@Query("select fe from FinacingEvent fe where fe.status !=?1 and fe.investorgId =?2 and fe.infoId=?3 order by fe.status desc")
	public List<FinacingEvent> findFinacingEventByInfoIdAndInvestor(String status,String investorg_id,String infoId);
	/**
	 * 
	 * @param status
	 * @param investorgId
	 * @param pageable
	 * @return
	 */
	@Query("select d from FinacingEvent d where d.status=?1 and d.investorgId=?2 order by d.operdate asc")
	public Page<FinacingEvent> findByStatusOrgId(String status, String investorgId, Pageable pageable);

	/**
	 * 
	 * @param status
	 * @param investOrgId
	 * @return
	 */
	@Query("select count(d) from FinacingEvent d where d.status>=?1 and d.status<>99 and d.investorgId=?2")
	public Long countByStatusOrgId(String status, String investOrgId);

	@Query("select count(d) from FinacingEvent d where d.status ='11'and d.operuser=?1")
	public Long countFocusByUser(String userName);

	
	
	@Query("select d from FinacingEvent d where d.status ='11'and d.operuser=?1")
	public List<FinacingEvent> findFocusByUser(String userName);
	/**
	 * 
	 * @param status
	 * @param eventId
	 */
	@Modifying
	@Query("update FinacingEvent u set u.status = ?1 where u.eventId =?2 ")
	public void updateStatus(String status, String eventId);

	@Modifying
	@Query("update FinacingEvent u set u.currency = ?1 where u.amount =?2 ")
	public void updateInvest(String currency, Double amount);
	
	@Modifying
	@Query("update FinacingEvent u set u.publishFlag ='0' where u.eventId =?1")
	public void updatePublishFlag(String eventId);
	
	@Modifying
	@Query("update FinacingEvent u set u.publishFlag ='2' where u.eventId =?1")
	public void updateUnPublishFlag(String eventId);
	/**
	 * 根据需求infoId与企业enterpriseId查询出融资事件对象 
	 * @Title: findInvestorByIds 
	 * @param infoId 
	 * @param enterpriseId 
	 * @return FinacingEvent 返回类型
	 * @throws
	 */
	@Query("select d from FinacingEvent d where d.infoId=?1 and d.enterpriseId=?2")
	public FinacingEvent findInvestorByIds(String infoId, String enterpriseId);
   
	/**
	 * 根据需求infoId与企业enterpriseId与投资机构ID查询出融资事件对象 
	 * @Title: findInvestorByIds 
	 * @param infoId 
	 * @param enterpriseId 
	 * @param investorId
	 * @return FinacingEvent 返回类型
	 * @throws
	 */
	@Query("select d from FinacingEvent d where d.infoId=?1 and d.enterpriseId=?2  and d.operuser=?3")
	public FinacingEvent findFinacingEventByIds(String infoId, String enterpriseId ,String operuser);
   
	@Query("select d from FinacingEvent d where d.infoId=?1 and d.investorgId=?2 and d.operuser=?3")
	public FinacingEvent findFinacingEventByInfoId(String infoId,String investorId,String operuser);
	
	@Query("select d from FinacingEvent d where d.infoId=?1 and d.investorgId=?2 ")
	public List<FinacingEvent> geteventByInfoidAndOrgId(String infoId,String investorId);
	
	
	@Query("select d from FinacingEvent d where d.infoId=?1 and d.investorgId=?2 and investLevel=?3 ")
	public FinacingEvent geteventByMangageOrg(String infoId,String investorId,String level);
	@Query("select d from FinacingEvent d where d.infoId=?1 and d.investorgId=?2 and investLevel=?3 and foundId=?4")
	public List<FinacingEvent> geteventByFundOrg(String infoId,String investorId,String level,String foundId);
	/**
	 * 根据机构ID与需求ID 获取事件表中其他机构对该融资事件的最靠后的需求进度
	 */
	@Query("select max(status) from FinacingEvent fe where fe.status!='10' and fe.status!='12' and fe.status!='22' and fe.status!='32' and fe.infoId =?1 and fe.investorgId!=?2")
	public String getMaxStatus(String infoId, String investorId);

	 
	/**
	 * 根据机构ID与需求ID 获取事件表中其他机构对该融资事件的最靠后的需求进度
	*/
	@Query("SELECT MAX(status) FROM FinacingEvent WHERE infoId =?1 AND status IN('11','21','31','41','42','43','51') ")
	public String getMaxEventStatus(String infoId);
	/**
	 * 根据机构ID与需求ID 获取事件表中其他机构对该融资事件的最靠后的需求进度
	*/
	@Query("SELECT status FROM FinacingDemandInfo WHERE infoId =?1 AND status IN('01','02') ")
	public String getDemandStatus(String infoId);

	@Modifying
	@Query("update FinacingDemandInfo u set u.status = ?1 where u.infoId =?2 ")
	public void updateDemandStatus(String status, String infoId);
	
	@Query("SELECT fe.schedule FROM FinacingEvent fe WHERE fe.eventId =?1 ")
	public String getScheduleById(String eventId);
}

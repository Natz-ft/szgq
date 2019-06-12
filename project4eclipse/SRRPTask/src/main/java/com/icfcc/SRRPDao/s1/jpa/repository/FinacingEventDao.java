package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.FinacingEvent;

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
	@Query("select fe from FinacingEvent fe where fe.infoId=?1 and fe.status in (41,42,43,44,45)")
	public List<FinacingEvent> findAllByInfoId(String infoId);
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
 * @param infoId
 * @return
 */
	@Query("select fe from FinacingEvent fe where fe.infoId=?1")
	public List<FinacingEvent> findFinacingEventByInfoId(String infoId);
	/**
	 * 
	 * @param infoId
	 * @return
	 */
	@Query(value="select fe.* from rp_finacing_event fe where fe.status in ('31','41','42','43','44','45') and fe.info_id=?1 ORDER BY fe.operdate limit 1",nativeQuery=true)
	public FinacingEvent findOKFinacingEventByInfoId(String infoId);
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
	@Query("select count(d) from FinacingEvent d where d.status=?1 and d.investorgId=?2")
	public Long countByStatusOrgId(String status, String investOrgId);

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
    
	/**
	 * 根据需求infoId与企业enterpriseId查询出融资事件对象
	* @Title: findInvestorByIds 
	* @param infoId
	* @param enterpriseId
	* @return FinacingEvent    返回类型 
	* @throws
	 */
	@Query("select d from FinacingEvent d where d.infoId=?1 and d.enterpriseId=?2")
	public FinacingEvent findInvestorByIds(String infoId, String enterpriseId);

}

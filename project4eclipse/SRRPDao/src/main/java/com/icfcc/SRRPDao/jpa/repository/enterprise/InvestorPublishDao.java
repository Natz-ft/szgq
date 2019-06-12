package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.InvestorPublish;

/**
 * 有关融资事件的相关操作的dao
 * 
 * @author john
 *
 */
@Component
public interface InvestorPublishDao
		extends PagingAndSortingRepository<InvestorPublish, String>, JpaSpecificationExecutor<InvestorPublish> {

	
	/**
	 * 
	 * @param eventId
	 * @return
	 */
	@Query("select d from InvestorPublish d where d.eventId=?1  order by d.publishTime DESC")
	public List<InvestorPublish> findByEventId(String eventId,Pageable page);
	
	/**
	 * 
	 * @param eventId
	 * @return
	 */
	@Query("select count(d) from InvestorPublish d where d.eventId=?1  ")
	public Long countByEventId(String eventId);
	
	/**
	 * 
	 * <p>功能描述:投资机构是否披露过</p>
	 * @param eventId
	 * @param enterpriseId
	 * @param orgId
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Query("select d from InvestorPublish d where d.eventId=?1 and d.enterpriseId=?2  and d.orgId=?3 ")
	public List<InvestorPublish> ifPublished(String eventId,String enterpriseId,String orgId);


}

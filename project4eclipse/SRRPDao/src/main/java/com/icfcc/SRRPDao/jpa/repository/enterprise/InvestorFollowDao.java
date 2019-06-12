package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.InvestorFollow;

/**
 * 有关融资事件的相关操作的dao
 * 
 * @author john
 *
 */
@Component
public interface InvestorFollowDao extends
		PagingAndSortingRepository<InvestorFollow, String>,
		JpaSpecificationExecutor<InvestorFollow> {

	/**
	 * 
	 * @param eventId
	 * @return
	 */
	@Query("select d from InvestorFollow d where d.eventId=?1  ")
	public List<InvestorFollow> findByEventId(String eventId);

	/**
	 * 
	 * <p>
	 * 功能描述:[根据需求ID获取所有跟投信息]
	 * </p>
	 * 
	 * @param infoId
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	@Query("select d from InvestorFollow d where d.infoId=?1  ")
	public List<InvestorFollow> findInvestorInfo(String infoId);

}

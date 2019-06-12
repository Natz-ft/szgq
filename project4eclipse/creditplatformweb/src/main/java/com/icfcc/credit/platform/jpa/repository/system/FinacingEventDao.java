package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.system.FinacingEvent;

/**
 * 有关融资事件的相关操作的dao
 * 
 * @author john
 *
 */
@Component
public interface FinacingEventDao
		extends PagingAndSortingRepository<FinacingEvent, String>, JpaSpecificationExecutor<FinacingEvent> {

	/**
	 * 根据融资信息的id查询融资事件
	 * 
	 * @param infoId
	 * @return
	 */
	@Query("select fe from FinacingEvent fe where fe.infoId=?1")
	public List<FinacingEvent> findFinacingEventByInfoId(String infoId);

	
	@Query("select fe from FinacingEvent fe where fe.eventId=?1")
	public FinacingEvent findFinacingEventId(String eventId);
	
	
}

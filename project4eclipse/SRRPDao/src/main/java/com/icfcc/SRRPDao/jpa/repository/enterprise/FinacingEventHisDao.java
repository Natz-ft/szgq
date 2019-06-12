package com.icfcc.SRRPDao.jpa.repository.enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEventHis;

/**
 * 融资事件历史
 * 
 * @author zhanglf
 *
 */
@Component
public interface FinacingEventHisDao
		extends PagingAndSortingRepository<FinacingEventHis, String>, JpaSpecificationExecutor<FinacingEventHis> {
}

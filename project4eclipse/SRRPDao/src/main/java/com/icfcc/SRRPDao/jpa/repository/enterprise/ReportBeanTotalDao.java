package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTotal;

@Component
public interface ReportBeanTotalDao
		extends PagingAndSortingRepository<ReportBeanTotal, String>, JpaSpecificationExecutor<ReportBeanTotal> {
	
	/**
	 * 
	 * @param timeId
	 * @return
	 */
	@Query("select t from ReportBeanTotal t where t.timeId=?1")
	public List<ReportBeanTotal> findByTimeId(String timeId);

}

package com.icfcc.SRRPDao.s.jpa.repository.portal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PReportBeanTotal;

@Component
public interface PReportBeanTotalDao
		extends PagingAndSortingRepository<PReportBeanTotal, String>, JpaSpecificationExecutor<PReportBeanTotal> {

	/**
	 * 
	 * @param timeId
	 * @return
	 */
	@Query("select t from PReportBeanTotal t where t.timeId=?1")
	public List<PReportBeanTotal> findByTimeId(String timeId);

}

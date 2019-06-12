package com.icfcc.SRRPDao.jpa.repository.inverstorg;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.ReportByArea;


@Component
public interface ReportByAreaDao extends
		PagingAndSortingRepository<ReportByArea, String>,
		JpaSpecificationExecutor<ReportByArea> {

	@Query("SELECT ra FROM ReportByArea ra WHERE ra.timeId=(SELECT MAX(ra.timeId) FROM ReportByArea ra) and ra.status=?1 ORDER BY ra.rank ")
	public List<ReportByArea> findByTimeId(String status);
}

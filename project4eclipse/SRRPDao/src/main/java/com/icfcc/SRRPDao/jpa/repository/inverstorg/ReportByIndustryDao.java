package com.icfcc.SRRPDao.jpa.repository.inverstorg;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.ReportByIndustry;

@Component
public interface ReportByIndustryDao extends
		PagingAndSortingRepository<ReportByIndustry, String>,
		JpaSpecificationExecutor<ReportByIndustry> {

	@Query("SELECT ri FROM ReportByIndustry ri WHERE ri.timeId =(SELECT MAX(ri.timeId) FROM ReportByIndustry ri) and ri.status=?1 ORDER BY ri.rank ")
	public List<ReportByIndustry> findReportByIndustry(String status);

}

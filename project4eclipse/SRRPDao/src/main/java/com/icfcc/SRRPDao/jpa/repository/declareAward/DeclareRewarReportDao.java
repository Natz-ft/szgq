package com.icfcc.SRRPDao.jpa.repository.declareAward;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarReport;

@Component
public interface DeclareRewarReportDao extends PagingAndSortingRepository<DeclareRewarReport, String>,
JpaSpecificationExecutor<DeclareRewarReport> {
	/**
	 * 通过申报记录查询投资记录
	 * 
	 * @param id
	 * @return
	 */
	@Query("select c from DeclareRewarReport c where c.declareId=?1")
	List<DeclareRewarReport> findByDeclarId(String declarId);
	
	
	@Query("select c from DeclareRewarReport c where c.eventId=?1")
	List<DeclareRewarReport> findByeventId(String eventId);
	
}

package com.icfcc.SRRPDao.jpa.repository.declareAward;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarReport;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarReportRecord;

@Component
public interface DeclareRewarReportRecordrDao extends PagingAndSortingRepository<DeclareRewarReportRecord, String>,
JpaSpecificationExecutor<DeclareRewarReportRecord> {
	/**
	 * 通过申报记录查询投资记录
	 * 
	 * @param id
	 * @return
	 */
	@Query("select count(c) from DeclareRewarReportRecord c where loanId in (:loanIds) and status<>'02' ")
	Long findByRecordCount(@Param("loanIds") List<String> loanIds);
	
	@Modifying
	@Query("update DeclareRewarReportRecord set status = ?1  where declareId=?2")
	void updateRecord(String status,String declareId);
	
}

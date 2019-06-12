package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s1.jpa.entity.FinacingRecord;


@Component
public interface FinacingRecordDao
		extends PagingAndSortingRepository<FinacingRecord, String>, JpaSpecificationExecutor<FinacingRecord> {
	
	@Modifying
	@Query("delete  from  FinacingRecord efr where efr.infoId=?1")
	public void deleteFinacingRecord(String infoId);
	
}

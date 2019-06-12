package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.system.FinacingRecord;

@Component
public interface FinacingRecordDao
		extends PagingAndSortingRepository<FinacingRecord, String>, JpaSpecificationExecutor<FinacingRecord> {

}

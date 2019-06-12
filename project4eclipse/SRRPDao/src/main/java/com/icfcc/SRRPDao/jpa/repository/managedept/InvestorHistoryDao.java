package com.icfcc.SRRPDao.jpa.repository.managedept;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.managedept.InvestorHistory;

@Component
public interface InvestorHistoryDao extends
PagingAndSortingRepository<InvestorHistory, String>, JpaSpecificationExecutor<InvestorHistory> {
	
	@Query("select u from InvestorHistory u where u.certno=? ")
	InvestorHistory findByCertNo(String certNo);
}

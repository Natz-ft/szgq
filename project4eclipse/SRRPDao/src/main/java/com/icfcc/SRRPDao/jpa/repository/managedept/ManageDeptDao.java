package com.icfcc.SRRPDao.jpa.repository.managedept;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageDept;

@Component
public interface ManageDeptDao
		extends PagingAndSortingRepository<Investor, String>, JpaSpecificationExecutor<ManageDept> {
//	@Query("select u from InvestorDTO u where u.id=? ")
//	InvestorDTO findById(String investor_id);
}

package com.icfcc.SRRPDao.jpa.repository.enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyListed;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyTag;

@Component
public interface CompanyListedDao extends
		PagingAndSortingRepository<CompanyListed, String>,
		JpaSpecificationExecutor<CompanyListed> {

	
}

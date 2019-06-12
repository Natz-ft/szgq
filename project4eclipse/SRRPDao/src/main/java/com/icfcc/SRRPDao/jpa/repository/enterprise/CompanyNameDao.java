package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyListed;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyName;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyTag;

@Component
public interface CompanyNameDao extends
		PagingAndSortingRepository<CompanyName, String>,
		JpaSpecificationExecutor<CompanyName> {

    @Query("select c from CompanyName c where relatedValue =?1")
    List<CompanyName> findByName(String name );
    
    @Query("select c from CompanyName c where busCompanyId =?1")
    List<CompanyName> findBybusCompanyId(String busCompanyId );
}

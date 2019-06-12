package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyTag;

@Component
public interface CompanyTagDao extends
		PagingAndSortingRepository<CompanyTag, String>,
		JpaSpecificationExecutor<CompanyTag> {

    @Query("select c from CompanyTag c where companyId is null")
    List<CompanyTag> findCompanyTag();
    
    @Query("select c from CompanyTag c where companyId is not null")
    List<CompanyTag> findCompanyTag2();
    @Modifying
    @Query("update CompanyTag c set c.companyId=?1 where c.id=?2")
    public void updateCompanyId(String companyId, String id);
}

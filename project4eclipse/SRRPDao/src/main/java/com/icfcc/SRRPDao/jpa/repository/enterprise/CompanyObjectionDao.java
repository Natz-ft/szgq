package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjection;

@Component
public interface CompanyObjectionDao extends PagingAndSortingRepository<CompanyObjection, String>,
JpaSpecificationExecutor<CompanyObjection> {

	@Query("select co from CompanyObjection co where co.enterpriseId=?1 order by co.operDate desc")
	List<CompanyObjection> findByEntepriseId(String enterpriseId);

	
	@Modifying
	@Query("delete from CompanyObjection s where s.enterpriseId=?1")
	void delByEnterpriseId(String enterpriseId);

}

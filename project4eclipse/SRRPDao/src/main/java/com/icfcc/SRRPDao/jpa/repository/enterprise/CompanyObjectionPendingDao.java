package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjection;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjectionPending;

public interface CompanyObjectionPendingDao  extends PagingAndSortingRepository< CompanyObjectionPending, String>,
JpaSpecificationExecutor< CompanyObjectionPending> {

	
	@Query("select co from CompanyObjectionPending co where co.enterpriseId=?1 order by co.operDate desc")
	List<CompanyObjectionPending> findByEntepriseId(String enterpriseId);

	
	@Modifying
	@Query("delete from CompanyObjectionPending s where s.enterpriseId=?1")
	void delByEnterpriseId(String enterpriseId);
}

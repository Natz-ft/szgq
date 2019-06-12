package com.icfcc.SRRPDao.s1.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s1.jpa.entity.CompanyBaseSupplement;
@Component
public interface CompanyBaseSupplementDao extends PagingAndSortingRepository<CompanyBaseSupplement, String>,
JpaSpecificationExecutor<CompanyBaseSupplement> {

	/**
	 *根据企业enterpriseId查询企业的补充信息
	 */
	@Query("select cbs from CompanyBaseSupplement cbs where cbs.enterpriseId=?1")
	CompanyBaseSupplement findById(String enterpriseId);
	
}

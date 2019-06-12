package com.icfcc.SRRPDao.jpa.repository.enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
@Component
public interface CompanyBaseSupplementDao extends PagingAndSortingRepository<CompanyBaseSupplement, String>,
JpaSpecificationExecutor<CompanyBaseSupplement> {

	/**
	 *根据企业enterpriseId查询企业的补充信息
	 */
	@Query("select cbs from CompanyBaseSupplement cbs where cbs.enterpriseId=?1")
	CompanyBaseSupplement findById(String enterpriseId);
	
}

package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyProduct;

@Component
public interface CompanyProductDao extends PagingAndSortingRepository<CompanyProduct, String>,
JpaSpecificationExecutor<CompanyProduct>{

	/**
	 * 根据企业ID查询产品信息列表
	 */
	@Query("select cp from CompanyProduct cp where cp.enterpriseId=?1 order by cp.createTime asc")
	List<CompanyProduct> findAll(String enterpriseId);
	
}

package com.icfcc.credit.platform.jpa.repository.system;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.system.CompanyBusinessplan;
@Component
public interface CompanyBusinessPlanDao extends PagingAndSortingRepository<CompanyBusinessplan, String>,
JpaSpecificationExecutor<CompanyBusinessplan>{

	
	/**
	 * 通过融资需求ID查询企业商业计划书信息
	 * @param id
	 * @return
	 */
	@Query("select c from CompanyBusinessplan c where c.infoId=?1")
	CompanyBusinessplan findById(String infoId);
}

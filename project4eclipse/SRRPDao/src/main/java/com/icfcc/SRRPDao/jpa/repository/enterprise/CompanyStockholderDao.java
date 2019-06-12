package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholder;
@Component
public interface CompanyStockholderDao extends PagingAndSortingRepository<CompanyStockholder, String>,
JpaSpecificationExecutor<CompanyStockholder>{

	/**
	 * 根据企业enterpriseId查询股东信息列表
	 */
	@Query("select c from CompanyStockholder c where c.enterpriseId=?1 order by c.contribution desc")
	List<CompanyStockholder> findAll(String enterpriseId);
	@Modifying
	@Query("delete from CompanyStockholder s where s.enterpriseId=?1")
	void deleteById(String enterpriseId);
}

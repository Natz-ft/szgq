package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholderPending;

@Component
public interface CompanyStockholderPendingDao extends PagingAndSortingRepository<CompanyStockholderPending, String>,
JpaSpecificationExecutor<CompanyStockholderPending> {

	/**
	 * 根据企业enterpriseId查询股东信息待审核列表
	 */
	@Query("select c from CompanyStockholderPending c where c.enterpriseId=?1 order by c.contribution desc")
	List<CompanyStockholderPending> findAll(String enterpriseId);
	
	/**
	 * 删除待审核股东信息
	 * @param enterpriseId
	 */
	@Modifying
	@Query("delete from CompanyStockholderPending s where s.enterpriseId=?1")
	void deleteById(String enterpriseId);
}

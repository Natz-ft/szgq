package com.icfcc.SRRPDao.jpa.repository.enterprise;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBasePending;

@Component
public interface CompanyBasePendingDao extends
		PagingAndSortingRepository<CompanyBasePending, String>,
		JpaSpecificationExecutor<CompanyBasePending> {

	/**
	 * 根据企业的id查询待审核表中是否有数据
	 * 
	 * @param EnterpriseId
	 * @return
	 */
	@Query("select c from CompanyBasePending c where c.enterpriseId=?1")
	CompanyBasePending findByEnterpriseId(String EnterpriseId);
	
	@Query("select c from CompanyBasePending c where c.code=?1")
	CompanyBasePending findByCode(String code);
	
	
	@Modifying
	@Query("delete from CompanyBasePending s where s.code=?1")
	public void deleteByCode(String code);
}

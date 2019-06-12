package com.icfcc.SRRPDao.s1.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.icfcc.SRRPDao.s1.jpa.entity.CompanyBase;

@Repository
public interface CompanyBaseDao extends JpaRepository<CompanyBase, String>,
JpaSpecificationExecutor<CompanyBase>{

	
	/**
	 * 通过企业ID查询企业基本信息
	 * @param id
	 * @return
	 */
	@Query("select c from CompanyBase c where c.enterpriseId=?1")
	CompanyBase findById(String enterpriseId);
	
	@Query("select count(c) from CompanyBase c ")
	public long countCompany();
	


	
}

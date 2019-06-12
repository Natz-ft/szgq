package com.icfcc.SRRPDao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.entity.CompanyBase;


@Component
public interface CompanyBaseDao extends PagingAndSortingRepository<CompanyBase, String>,JpaSpecificationExecutor<CompanyBase> {

	/**
	 * 通过企业ID查询企业基本信息
	 * 
	 * @param id
	 * @return
	 */
	@Query("select c from CompanyBase c where c.enterpriseId=?1")
	CompanyBase findById(String enterpriseId);

	@Modifying
	@Query("update CompanyBase c set c.stopFlag=?1 where c.enterpriseId=?2")
	public void updateStopFlag(String stopFlag, String enterprsiseId);
	
	@Query("select count(u) from CompanyBase u where  u.code=? ")
	public Long countByCertno(String code);
	
	@Query("select u from CompanyBase u where  u.code=? ")
	public CompanyBase queryByCertno(String code);

}

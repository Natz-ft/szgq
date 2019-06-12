package com.icfcc.SRRPDao.jpa.repository.enterprise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;

@Component
public interface CompanyBaseDao extends
		PagingAndSortingRepository<CompanyBase, String>,
		JpaSpecificationExecutor<CompanyBase> {

	/**
	 * 通过企业ID查询企业基本信息
	 * 
	 * @param id
	 * @return
	 */
	@Query("select c from CompanyBase c where c.enterpriseId=?1")
	CompanyBase findById(String enterpriseId);
	
	@Query("select c from CompanyBase c where c.name=?1")
    List<CompanyBase> findByName(String name);

	@Modifying
	@Query("update CompanyBase c set c.stopFlag=?1 where c.enterpriseId=?2")
	public void updateStopFlag(String stopFlag, String enterprsiseId);
	
	@Modifying
    @Query("update CompanyBase c set c.stopFlag=?1 where c.stopFlag is null")
    public void updateAllStopFlag(String stopFlag);
	
	@Query("select count(u) from CompanyBase u where  u.code=? ")
	public Long countByCertno(String code);
	
	@Query("select u from CompanyBase u where  u.code=? ")
	public CompanyBase queryByCertno(String code);
    
	@Modifying
	@Query("delete from CompanyBase s where s.code=?1")
	public void deleteByCode(String code);
}

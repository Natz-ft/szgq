package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s1.jpa.entity.FinacingDemandInfo;

@Component
public interface FinacingInfoDao
		extends PagingAndSortingRepository<FinacingDemandInfo, String>, JpaSpecificationExecutor<FinacingDemandInfo> {

	/**
	 * 查询融资需求信息列表
	 * 
	 * @param infoId
	 * @return
	 */
	@Query("select f from FinacingDemandInfo f where f.enterpriseId=?1")
	List<FinacingDemandInfo> findById(String infoId);
	//@Query("select f from FinacingDemandInfo f where f.infoId=?1 and open='0' ")
	@Query("select f from FinacingDemandInfo f where f.infoId=?1")
	FinacingDemandInfo findByOpenDemand(String infoId);
	/**
	 * 根据需求infoId查询融资信息
	 * 
	 * @param infoId
	 * @return
	 */
	@Query("select f from FinacingDemandInfo f where f.infoId=?1")
	FinacingDemandInfo findByInfoId(String infoId);
	
	@Query("select count(f) from FinacingDemandInfo f")
	public Long countInfo();
	
	@Query("select sum(f.amountMax) from FinacingDemandInfo f")
	public Double sumInfo();
	
	@Modifying
	@Query("delete from FinacingEvent u  where u.infoId =?1")
	public void deleteFinacingEventByinfoId(String infoId);
}

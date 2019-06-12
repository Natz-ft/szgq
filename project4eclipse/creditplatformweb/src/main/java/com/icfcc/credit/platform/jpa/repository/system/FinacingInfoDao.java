package com.icfcc.credit.platform.jpa.repository.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.system.FinacingDemand;

@Component
public interface FinacingInfoDao extends PagingAndSortingRepository<FinacingDemand, String>,
JpaSpecificationExecutor<FinacingDemand> {
   
	/**
     * 根据需求infoId查询融资信息
     * @param  infoId
     * @return 
     */
	@Query("select f from FinacingDemand f where f.infoId=?1")
	FinacingDemand findByInfoId(String infoId);
	
}

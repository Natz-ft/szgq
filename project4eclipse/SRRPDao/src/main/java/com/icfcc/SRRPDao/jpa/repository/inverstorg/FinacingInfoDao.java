package com.icfcc.SRRPDao.jpa.repository.inverstorg;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEvent;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;

@Component
public interface FinacingInfoDao extends PagingAndSortingRepository<FinacingDemandInfo, String>,
JpaSpecificationExecutor<FinacingDemandInfo> {
   

	 /**
     * 查询融资需求信息列表
     * @param  infoId
     * @return 
     */
	@Query("select f from FinacingDemandInfo f where f.enterpriseId=?1")
	List<FinacingDemandInfo> findById(String infoId);
	
	@Modifying
	@Query("delete from FinacingEvent u  where u.infoId =?1")
	public void deleteFinacingEventByinfoId(String infoId);
	
	/**
     * 根据需求infoId查询融资信息
     * @param  infoId
     * @return 
     */
	@Query("select f from FinacingDemandInfo f where f.infoId=?1")
	FinacingDemandInfo findByInfoId(String infoId);
	/**
	 * 检查定投需求是否有机构关注
	 * 
	 */
	@Modifying
	@Query("select   u  from FinacingEvent u  where u.infoId =?1 and u.status not in( '01','12','22')  ")
	 List<FinacingEvent>  isFoucsDemandInfo(String infoId);
}

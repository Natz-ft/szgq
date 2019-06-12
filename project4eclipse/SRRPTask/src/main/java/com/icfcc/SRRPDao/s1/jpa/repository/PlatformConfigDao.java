package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;

/**
 * 
* @ClassName: PlatformConfigDao
* @Description: TODO(閰嶇疆淇℃伅寰楀鍒犳敼鏌ユ帴鍙�)
* @author hugt
* @date 2017骞�9鏈�14鏃� 涓嬪崍7:15:02
*
 */
public interface PlatformConfigDao extends PagingAndSortingRepository<PlatformConfig, Long>,
    JpaSpecificationExecutor<PlatformConfig>
{
    /**
     * <鏍规嵁鍙傛暟鍚嶆煡璇�>
     * 
     * @param configName 鍙傛暟鍚嶇О
     * @return
     */
    PlatformConfig findByConfigName(String configName);
    
    @Query("select s from PlatformConfig s where configName in ('noFocusTime','noTalksTime','noInvestInfoTime','noStopsrrpTime')")
    List<PlatformConfig> findByConfigNames();

}

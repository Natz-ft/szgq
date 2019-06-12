package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformContactus;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformContactusDist;

/**
 * 
* @ClassName: PlatformContactusDistDao
* @Description: TODO(联系我们的下级联系的增删 改查接口)
* @author hugt
* @date 2017年9月14日 下午7:16:10
*
 */
public interface PlatformContactusDistDao extends PagingAndSortingRepository<PlatformContactusDist, Long>,
    JpaSpecificationExecutor<PlatformContactusDist>
{
	ArrayList<PlatformContactusDist> findAll();
 
    
}

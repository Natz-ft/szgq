package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformDic;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformNews;

/**
 * 
* @ClassName: PlatformNewsDao
* @Description: TODO( 新闻动态信息的增删改查接口)
* @author hugt
* @date 2017年9月14日 下午7:20:12
*
 */
public interface PlatformNewsDao extends PagingAndSortingRepository<PlatformNews, Long>,
JpaSpecificationExecutor<PlatformNews>
{
	
	
	
}

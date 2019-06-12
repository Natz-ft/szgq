package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformLinks;



/**
 * 
* @ClassName: PlatformFriendlyLinksDao
* @Description: TODO(����������Ϣ����ɾ�Ĳ�ӿ�)
* @author hugt
* @date 2017��9��14�� ����7:18:10
*
 */
public interface PlatformLinksDao extends PagingAndSortingRepository<PlatformLinks, Long>,
JpaSpecificationExecutor<PlatformLinks>
{
	
	
	
}

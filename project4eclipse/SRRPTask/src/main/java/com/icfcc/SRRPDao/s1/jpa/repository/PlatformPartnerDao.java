package com.icfcc.SRRPDao.s1.jpa.repository;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformPartner;

/**
 * 
* @ClassName: PlatformBusinessPartnerDao
* @Description: TODO(合作伙伴信息增删改查接口)
* @author hugt
* @date 2017年9月14日 下午6:46:22
*
 */
public interface PlatformPartnerDao extends PagingAndSortingRepository<PlatformPartner, Long>,
JpaSpecificationExecutor<PlatformPartner>
{
	
	
}

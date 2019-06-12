package com.icfcc.SRRPDao.jpa.repository.paltformContent;

import java.util.ArrayList;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformBusinessPartner;



public interface PlatformBusinessPartnerDao extends PagingAndSortingRepository<PlatformBusinessPartner, Long>,
JpaSpecificationExecutor<PlatformBusinessPartner>
{
	
	PlatformBusinessPartner findById(Long id);
	
	
	
	@Modifying
	@Query("delete from PlatformBusinessPartner s where s.id=?1")
	void deleteById(Long id);
	
	
	
	
	ArrayList<PlatformBusinessPartner> findAll();
	
	
	
	
}

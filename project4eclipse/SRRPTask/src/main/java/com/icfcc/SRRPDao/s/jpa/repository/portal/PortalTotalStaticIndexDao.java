package com.icfcc.SRRPDao.s.jpa.repository.portal;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PortailStaticTotalIndexDB;


@Component
public interface PortalTotalStaticIndexDao
		extends PagingAndSortingRepository<PortailStaticTotalIndexDB, String>, JpaSpecificationExecutor<PortailStaticTotalIndexDB> {
	
}

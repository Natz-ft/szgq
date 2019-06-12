package com.icfcc.SRRPDao.s.jpa.repository.portal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.icfcc.SRRPDao.s.jpa.entity.portal.platformPortalEventQueryInvestor;

@Repository
public interface platformPortalEventQueryInvestorDao extends JpaRepository<platformPortalEventQueryInvestor, String>,
JpaSpecificationExecutor<platformPortalEventQueryInvestor> {


    
}

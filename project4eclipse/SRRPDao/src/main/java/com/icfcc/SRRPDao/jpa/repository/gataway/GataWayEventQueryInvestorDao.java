package com.icfcc.SRRPDao.jpa.repository.gataway;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayEventQueryInvestor;

public interface GataWayEventQueryInvestorDao extends PagingAndSortingRepository<GataWayEventQueryInvestor, String>,
        JpaSpecificationExecutor<GataWayEventQueryInvestor> {


    @Query("select u from GataWayEventQueryInvestor u where u.eventId=? ")
    List<GataWayEventQueryInvestor> findInvestorByEventId(String eventId);
}

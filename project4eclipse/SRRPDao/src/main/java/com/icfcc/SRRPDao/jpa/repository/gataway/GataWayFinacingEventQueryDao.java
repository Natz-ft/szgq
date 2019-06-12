package com.icfcc.SRRPDao.jpa.repository.gataway;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayFinacingEventQuery;


public interface GataWayFinacingEventQueryDao extends PagingAndSortingRepository<GataWayFinacingEventQuery, String>,
        JpaSpecificationExecutor<GataWayFinacingEventQuery> {

    @Query("select u from GataWayFinacingEventQuery u where u.eventId=? ")
    GataWayFinacingEventQuery findById(String eventId);

    @Query("select v from GataWayFinacingEventQuery v where v.industry=?1 and v.finacingTurn=?2")
    List<GataWayFinacingEventQuery> findAll(String industry,String finacingTurn);
}

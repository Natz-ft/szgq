package com.icfcc.SRRPDao.jpa.repository.gataway.staticize;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorQuery;

public interface GataWayInvestorQueryDao extends PagingAndSortingRepository<GataWayInvestorQuery, String>,
        JpaSpecificationExecutor<GataWayInvestorQuery> {

    @Query("select u from GataWayInvestorQuery u where u.investorId=? ")
    GataWayInvestorQuery findById(String investorId);

    @Query("from GataWayInvestorQuery")
    List<GataWayInvestorQuery> findAll();
}

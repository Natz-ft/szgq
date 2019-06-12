package com.icfcc.SRRPService.gataway;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorQuery;
import com.icfcc.SRRPDao.jpa.repository.gataway.GataWayInvestorQueryEcologyDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayInvestorQueryDao;

@Service
@Transactional(value = "transactionManager")
public class GataWayInvestorQueryService {

    private static Logger log = LoggerFactory.getLogger(GataWayInvestorQueryService.class);

    @Autowired
    private GataWayInvestorQueryDao gataWayInvestorQueryDao;

    @Autowired
    private GataWayInvestorQueryEcologyDao gataWayInvestorQueryEcologyDao;

    public List<GataWayInvestorQuery> findGataWayInvestorQuery() {
        List<GataWayInvestorQuery> dataList = null;
        try {
            dataList = gataWayInvestorQueryDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }

    public GataWayInvestorQuery findDemandById(String investorId) {
        return gataWayInvestorQueryDao.findById(investorId);
    }

    public List<GataWayInvestorQuery> findInvestorList(QueryCondition queryCondition) {
        List<GataWayInvestorQuery> dataList = null;
        try {
            dataList = gataWayInvestorQueryEcologyDao.findInvestorList(queryCondition);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }
}

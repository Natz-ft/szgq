package com.icfcc.SRRPService.gataway;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayEventQueryInvestor;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayFinacingEventQuery;
import com.icfcc.SRRPDao.jpa.repository.gataway.GataWayEventQueryInvestorDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.GataWayFinacingEventQueryDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.GataWayFinacingEventQueryEcologyDao;

@Service
@Transactional(value = "transactionManager")
public class GataWayFinacingEventQueryService {

    private static Logger log = LoggerFactory.getLogger(GataWayFinacingEventQueryService.class);

    @Autowired
    private GataWayFinacingEventQueryDao gataWayFinacingEventQueryDao;
    @Autowired
    GataWayFinacingEventQueryEcologyDao gataWayFinacingEventQueryDaoImple;

    @Autowired
    GataWayEventQueryInvestorDao gataWayEventQueryInvestorDao;

    public List<GataWayFinacingEventQuery> findFinacingEventQuery(String industry, String finacingTurn) {
        List<GataWayFinacingEventQuery> dataList = null;
        try {
            dataList = gataWayFinacingEventQueryDao.findAll(industry, finacingTurn);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }

    public GataWayFinacingEventQuery findFinacingEventById(String eventId) {
        return gataWayFinacingEventQueryDao.findById(eventId);
    }

    public List<GataWayFinacingEventQuery> findFinacingEventQuery(QueryCondition queryCondition) {
        List<GataWayFinacingEventQuery> dataList = null;
        try {
            dataList = gataWayFinacingEventQueryDaoImple.findEventList(queryCondition);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }
    //投融事件/投资机构查询
    public List<GataWayEventQueryInvestor> findInvestorByEventId(String eventId) {
        List<GataWayEventQueryInvestor> dataList = null;
        try {
            dataList = gataWayEventQueryInvestorDao.findInvestorByEventId(eventId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }
}

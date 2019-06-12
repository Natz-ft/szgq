package com.icfcc.SRRPService.gataway;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayDemandQuery;
import com.icfcc.SRRPDao.jpa.repository.gataway.GataWayDemandQueryEcologyDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayDemandQueryDao;

/**
 * <门户融资需求非静态化查询>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayDemandQueryService {
    private static Logger log = LoggerFactory.getLogger(GataWayDemandQueryService.class);
    @Autowired
    private GataWayDemandQueryDao gataWayDemandQueryDao;

    @Autowired
    private GataWayDemandQueryEcologyDao gataWayDemandQueryEcologyDao;

    public List<GataWayDemandQuery> findGataWayDemand(QueryCondition queryCondition) {
        List<GataWayDemandQuery> listGataWayDemand = null;
        try {
            listGataWayDemand = gataWayDemandQueryEcologyDao.findDemandList(queryCondition);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return listGataWayDemand;
    }

    public GataWayDemandQuery findDeamndInfoById(String infoId) {
        return gataWayDemandQueryDao.findOne(infoId);
    }

}

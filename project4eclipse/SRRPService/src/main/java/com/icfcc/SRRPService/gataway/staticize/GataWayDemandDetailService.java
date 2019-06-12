package com.icfcc.SRRPService.gataway.staticize;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayDemandDetail;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayDemandDetailDao;

/**
 * <门户静态化融资需求详情>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayDemandDetailService {
    private static Logger log = LoggerFactory.getLogger(GataWayDemandDetailService.class);

    @Autowired
    private GataWayDemandDetailDao gataWayDemandDetailDao;

    public List<GataWayDemandDetail> findGataWayDemandDetail() {
        List<GataWayDemandDetail> listGataWayDemand = null;
        try {
            listGataWayDemand = gataWayDemandDetailDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return listGataWayDemand;
    }

}

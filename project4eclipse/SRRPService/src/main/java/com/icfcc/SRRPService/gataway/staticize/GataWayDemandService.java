package com.icfcc.SRRPService.gataway.staticize;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayDemand;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayDemandDao;

/**
 * <门户静态化融资需求>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayDemandService {
    private static Logger log = LoggerFactory.getLogger(GataWayDemandService.class);
    @Autowired
    private GataWayDemandDao gataWayDemandDao;

    public List<GataWayDemand> findGataWayDemand() {
        List<GataWayDemand> listGataWayDemand = null;
        try {

            Sort sort = new Sort(Sort.Direction.DESC,"operdate");
            listGataWayDemand = gataWayDemandDao.findAll(sort);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return listGataWayDemand;
    }

}

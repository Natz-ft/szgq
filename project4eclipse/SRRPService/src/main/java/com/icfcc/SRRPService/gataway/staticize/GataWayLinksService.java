package com.icfcc.SRRPService.gataway.staticize;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayLinks;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayLinksDao;

/**
 * <门户静态化友情链接>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayLinksService {
    private static Logger log = LoggerFactory.getLogger(GataWayLinksService.class);

    @Autowired
    private GataWayLinksDao gataWayLinksDao;

    public List<GataWayLinks> findGataWayLinks() {
        List<GataWayLinks> listGataWayLinks = null;
        try {
            listGataWayLinks = gataWayLinksDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return listGataWayLinks;
    }

}

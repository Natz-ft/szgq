package com.icfcc.SRRPService.gataway.staticize;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayPartner;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayPartnerDao;

/**
 * <门户静态化合作伙伴>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayPartnerService {
    private static Logger log = LoggerFactory.getLogger(GataWayPartnerService.class);

    @Autowired
    private GataWayPartnerDao gataWayPartnerDao;

    public List<GataWayPartner> findGataWayPartners() {
        List<GataWayPartner> listGataWayPartners = null;
        try {
            listGataWayPartners = gataWayPartnerDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return listGataWayPartners;
    }

}

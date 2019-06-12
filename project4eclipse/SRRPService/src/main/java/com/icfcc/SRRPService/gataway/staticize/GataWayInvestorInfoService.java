package com.icfcc.SRRPService.gataway.staticize;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayInvestorInfo;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayInvestorInfoDao;

/**
 * <门户静态化投资动态>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayInvestorInfoService {
    private static Logger log = LoggerFactory.getLogger(GataWayInvestorInfoService.class);

    @Autowired
    private GataWayInvestorInfoDao gataWayInvestorInfoDao;

    public List<GataWayInvestorInfo> findGataWayInvestorInfo() {
        List<GataWayInvestorInfo> listGataWayInvestorInfo = null;
        try {
            listGataWayInvestorInfo = gataWayInvestorInfoDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return listGataWayInvestorInfo;
    }

}

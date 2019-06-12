package com.icfcc.SRRPService.gataway;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResult;
import com.icfcc.SRRPDao.jpa.repository.gataway.TestPageDao;

@Component
@Transactional(value = "transactionManager")
public class TestPageService {
    private static Logger log = LoggerFactory.getLogger(TestPageService.class);
    
    @Autowired
    private TestPageDao testPageDao;
    
    public List<FinacingDemandInfoResult> getFinacingDemandInfoList(QueryCondition queryCondition) {
        List<FinacingDemandInfoResult> dataList = null;
        try {
            dataList = testPageDao.getFinacingDemandInfoList(queryCondition);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return dataList;
    }

    public Object getCounts(QueryCondition queryCondition){
        return testPageDao.getFinacingDemandInfoCount(queryCondition);
    }

}

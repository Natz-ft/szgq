package com.icfcc.SRRPService.gataway.staticize;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayNews;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayNewsDao;

/**
 * <门户静态化新闻动态>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayNewsService {
    private static Logger log = LoggerFactory.getLogger(GataWayNewsService.class);

    @Autowired
    private GataWayNewsDao gataWayNewsDao;

    public List<GataWayNews> findGataWayNews(String infoType) {
        List<GataWayNews> listGataWayNews = null;
        try {
            listGataWayNews = gataWayNewsDao.findNewsListByType(infoType);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return listGataWayNews;
    }

}

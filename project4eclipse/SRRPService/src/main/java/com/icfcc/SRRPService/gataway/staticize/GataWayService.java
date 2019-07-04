package com.icfcc.SRRPService.gataway.staticize;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayIndexStatic;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayStatic;
import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformFaqShow;
import com.icfcc.SRRPDao.jpa.repository.gataway.GataWayRankEcologyDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayStaticDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayStaticIndexDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <门户静态化统计>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayService {
	private static Logger log = LoggerFactory.getLogger(GataWayService.class);

	@Autowired
	private GataWayStaticDao gataWayStaticDao;

	@Autowired
	private GataWayRankEcologyDao rankDao;

	@Autowired
	private GataWayStaticIndexDao indexDao;

	public GataWayStatic getStaticResult() {
		try {
			return gataWayStaticDao.findById();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	public GataWayStatic getStaticResultByQueryDate(String queryDate) {
		try {
			return (rankDao.findRankTotalList(queryDate)).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * 功能描述:[首頁-統計值]
	 * </p>
	 * 
	 * @param queryDate
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public GataWayIndexStatic getIndexStaticResultByQueryDate(String queryDate) {
		try {
			return (indexDao.findByDate(queryDate)).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	public PlatformFaqShow findFaqDy(String id) {
		return rankDao.findFaqDy(id);
	}
}

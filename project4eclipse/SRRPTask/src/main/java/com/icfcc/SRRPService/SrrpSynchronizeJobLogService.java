/**
 * 
 */
package com.icfcc.SRRPService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.SRRPDao.s1.jpa.repository.SrrpSynchronizeJobLogDao;

/**
 * @author lijj
 * 新闻业务
 */
@Component
public class SrrpSynchronizeJobLogService {

	@Autowired
	private SrrpSynchronizeJobLogDao srrpSynchronizeJobLogDao;
	
	
	
		@Transactional(value = "transactionManager1")
		public  void save(SrrpSynchronizeJobLog log) {
			srrpSynchronizeJobLogDao.save(log);
		}
		
}

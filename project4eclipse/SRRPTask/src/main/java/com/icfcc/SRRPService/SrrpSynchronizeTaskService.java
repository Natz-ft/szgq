/**
 * 
 */
package com.icfcc.SRRPService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPDao.s1.jpa.repository.SrrpSynchronizeTaskDao;

/**
 * @author lijj
 * 新闻业务
 */
@Component
public class SrrpSynchronizeTaskService {

	@Autowired
	private SrrpSynchronizeTaskDao srrpSynchronizeTaskDao;
	
	/**
	 * 获取r
	 * @param n
	 * @return
	 */
		@Transactional(value = "transactionManager1")
		public List<SrrpSynchronizeJobBean> getTask() {
			List<SrrpSynchronizeJobBean> list=null;
			try{
				list=(List<SrrpSynchronizeJobBean>) srrpSynchronizeTaskDao.getTask();      
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return list;
		}
	
		@Transactional(value = "transactionManager1")
		public SrrpSynchronizeJobBean findByJobName(String jobName) {
			SrrpSynchronizeJobBean jobsbean=null;
			try{
				jobsbean=srrpSynchronizeTaskDao.findByJobName(jobName);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return jobsbean;
		}
	
		@Transactional(value = "transactionManager1")
		public  void save(SrrpSynchronizeJobBean jobsbean) {
			srrpSynchronizeTaskDao.save(jobsbean);
		}
		
}

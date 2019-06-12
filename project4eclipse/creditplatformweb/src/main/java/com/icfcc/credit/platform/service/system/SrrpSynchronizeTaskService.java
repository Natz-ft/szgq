/**
 * 
 */
package com.icfcc.credit.platform.service.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.PlatformNews;
import com.icfcc.credit.platform.jpa.entity.system.SrrpSynchronizeJobBean;
import com.icfcc.credit.platform.jpa.repository.system.SrrpSynchronizeTaskDao;
import com.icfcc.credit.platform.util.jpa.PageUtil;

/**
 * @author lijj
 * 新闻业务
 */
@Component
@Transactional(value = "transactionManager")
public class SrrpSynchronizeTaskService {

	@Autowired
	private SrrpSynchronizeTaskDao srrpSynchronizeTaskDao;
	/**
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param direction
	 * @param orderBy
	 * @return 任务列表
	 */
	 public Page<SrrpSynchronizeJobBean> getSystemContentTaskList(Map<String, Object> searchParams, 
	    		int pageNumber, int pageSize,String direction, String orderBy){
	        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
	        Specification<SrrpSynchronizeJobBean> spec = PageUtil.buildSpecification(searchParams, SrrpSynchronizeJobBean.class);
	        return srrpSynchronizeTaskDao.findAll(spec, pageRequest);
	    }
	/**
	 * 获取r
	 * @param n
	 * @return
	 */
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
		public SrrpSynchronizeJobBean findById(Long id) {
			SrrpSynchronizeJobBean jobsbean=null;
			try{
				jobsbean=srrpSynchronizeTaskDao.findById(id);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return jobsbean;
		}
		public  void save(SrrpSynchronizeJobBean jobsbean) {
			srrpSynchronizeTaskDao.save(jobsbean);
		}
		
}

package com.icfcc.srrptask.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalContantUs;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalContantUsDist;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalFaqAndNotice;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.util.SRRPConstant;
/**
 * 
* @ClassName: MakePortalContactUsJob 
* @Description: TODO(更新门户联系我们的页面) 
* @author hugt
* @date 2018年2月01日 上午9:34:42 > vcz
*
 */
public class MakePortalContactUsJob  extends JobObkect {
	
	private final Logger log = LoggerFactory.getLogger(MakePortalIndexJob.class);
	private String jobName="";
	private Long jobId;
	// job执行方法
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobName = context.getJobDetail().getKey().toString();
		jobName=StringUtils.substringAfter(jobName, ".");
		SrrpSynchronizeJobBean jobsbean=srrpSynchronizeTaskService.findByJobName(jobName);
		jobsbean.setStatus(SRRPConstant.EXECUTESTART);
		log.info("jobName:"+jobName+"描述:"+jobsbean.getJobDescription());
		jobId=jobsbean.getJobId();
		log.info("迁移联系我门的数据");
		String result=moveContantUs();
		log.info("完成迁移联系我门的数据");
		
		//如果迁移失败，则不生成动态html
		if(!SRRPConstant.EXECUTEXCEPTION.equals(result)){
			log.info("生成首页动态HTML");
			callMakeHtmlControl(contantUsURL, jobName,jobId);
			log.info("完成生成首页动态HTML");
		}
		jobsbean.setStatus(result);
		jobsbean.setJobData(new Date());
		jobsbean.setNextjobData(getNextCron(jobsbean.getJobCron()));
		jobsbean.setExeCount(jobsbean.getExeCount()+1);//记录任务执行次数
		srrpSynchronizeTaskService.save(jobsbean);
		log.info("完成"+jobsbean.getJobDescription());   

		
	}
	/**
	 * 
	* @Title: moveFinacingEvent 
	* @Description: TODO(迁移用户门户融资需求查询列表的数据) 
	* @param @return  参数说明 
	* @return Map<String,String>    返回类型 
	* @throws
	 */
	public String moveContantUs() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		List<PlatformPortalContantUs> res=null;
		List<PlatformPortalContantUsDist> resDist = null;
		List<PlatformPortalFaqAndNotice> res1=null;
		try {
			jobLog.setJobId(jobId);
			jobLog.setExeResult(executeResult);// 就绪
			jobLog.setJobName(jobName);// 任务名称
			jobLog.setStartTime(new Date());// 记录任务开始时间
			res=contantUsService.getLatestContantUs();
			resDist = contantUsService.getLatestContantUsDist();
			res1=faqService.getLatestFaq(10);
			faqService.save(res1);
			contantUsService.saveContantUs(res);
			contantUsService.saveContantUsDist(resDist);
			executeResult = SRRPConstant.EXECUTSUCC;
			msg = "完成迁移联系我门的数据";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
		 	log.error("迁移联系我门的数据异常:"+e.getMessage());

		}finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}
		return executeResult;
	
	}
	
	
}


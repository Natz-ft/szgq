package com.icfcc.srrptask.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalInfos;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.util.SRRPConstant;

public class MakePortalNewsJob  extends JobObkect {
	private final Logger log = LoggerFactory.getLogger(MakePortalIndexJob.class);
	private Long jobId;
	private String jobName="";
	// job执行方法
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobName = context.getJobDetail().getKey().toString();
		jobName=StringUtils.substringAfter(jobName, ".");
		SrrpSynchronizeJobBean jobsbean=srrpSynchronizeTaskService.findByJobName(jobName );
		log.info("jobName:"+jobName+"描述:"+jobsbean.getJobDescription());
		jobsbean.setStatus(SRRPConstant.EXECUTESTART);
		jobId=jobsbean.getJobId();
		log.info("开始执行迁移门户新闻动态的数据");
		String result=moveNews();
		log.info("完成迁移门户融资需求查询列表的数据");
		if(!SRRPConstant.EXECUTEXCEPTION.equals(result)){
			log.info("开始生成首页新闻动态HTML");
			callMakeHtmlControl(newsURL, jobName,jobId);
			log.info("完成生成首页新闻动态HTML");
		}
		jobsbean.setStatus(result);
		jobsbean.setJobData(new Date());
		jobsbean.setExeCount(jobsbean.getExeCount()+1);//记录任务执行次数
		jobsbean.setNextjobData(getNextCron(jobsbean.getJobCron()));
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
	public String  moveNews() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "开始迁移门户新闻动态的数据";
		try {
			jobLog.setJobId(jobId);
			jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
			jobLog.setJobName(jobName);// 任务名称
			jobLog.setStartTime(new Date());// 记录任务开始时间
			List<PlatformPortalInfos> platformPortalInfos = null;
			platformPortalInfos =newService.NewestRelsultNewsInfors();
			newService.saveLatestNewsInfors(platformPortalInfos);
			executeResult = SRRPConstant.EXECUTSUCC;
			msg = "完成迁移门户新闻动态的数据";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("执行迁移门户新闻动态的数据异常:"+msg);

		}finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}
	   return executeResult;
	}

}

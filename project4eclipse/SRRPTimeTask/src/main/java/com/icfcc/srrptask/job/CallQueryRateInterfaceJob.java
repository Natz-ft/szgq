package com.icfcc.srrptask.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.util.SRRPConstant;

public class CallQueryRateInterfaceJob  extends JobObkect {
	private final Logger log = LoggerFactory.getLogger(MakePortalIndexJob.class);
	
	// job执行方法
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobName = context.getJobDetail().getKey().toString();
		SrrpSynchronizeJobBean jobsbean=srrpSynchronizeTaskService.findByJobName(StringUtils.substringAfter(jobName, ".") );
		jobsbean.setStatus(SRRPConstant.EXECUTESTART);
		log.info("jobName:"+jobName+"描述:"+jobsbean.getJobDescription());
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		try {
			//记录日志
			jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
			jobLog.setJobName("CallQueryRateInterfaceJob");// 任务名称
			jobLog.setStartTime(new Date());// 记录任务开始时间
			// 先判断评分状态是否正常
			log.info("开始同步汇率");
			rateService.UpdateRate();
			executeResult = SRRPConstant.EXECUTSUCC;
		} catch (Exception e) {
			log.error("执行同步汇率异常:"+e.getMessage());
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			
		}finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
			jobsbean.setJobData(new Date());
			jobsbean.setStatus(executeResult);
			jobsbean.setNextjobData(getNextCron(jobsbean.getJobCron()));
			jobsbean.setExeCount(jobsbean.getExeCount()+1);//记录任务执行次数
			srrpSynchronizeTaskService.save(jobsbean);
		}
		log.info("完成"+jobsbean.getJobDescription());
	}

}

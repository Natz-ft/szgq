package com.icfcc.srrptask.listener;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class QuartzJobListener implements JobListener {
	private final Logger log = LoggerFactory.getLogger(QuartzJobListener.class);
	public static final String LISTENER_NAME = "SRRPJobListenerName";
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return LISTENER_NAME;
	}
	     
	//任务执行之前执行
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		// TODO Auto-generated method stub
		String jobName = context.getJobDetail().getKey().toString();
//		SrrpSynchronizeJobBean jobsbean=srrpSynchronizeTaskService.findByJobName(StringUtils.substringAfter(jobName, ".") );
//		jobsbean.setStartTime(new Date());
//		jobsbean.setStatus("00");
//		srrpSynchronizeTaskService.save(jobsbean);
		log.info("Job : " + jobName + " is going to start...");
	}
    //这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法
	//需要注意的是 如果方法(2)执行 那么(1),(3)这个俩个方法不会执行,因为任务被终止了嘛.
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		String jobName = context.getJobDetail().getKey().toString();
		// TODO Auto-generated method stub
		log.info("Job : " + jobName + " 执行中断...");

	}
    // 任务执行完成后执行,jobException如果它不为空则说明任务在执行过程中出现了异常
	//
	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// TODO Auto-generated method stub
		String jobName = context.getJobDetail().getKey().toString();
//		SrrpSynchronizeJobBean jobsbean=srrpSynchronizeTaskService.findByJobName(StringUtils.substringAfter(jobName, ".") );
//		jobsbean.setLastTime(new Date());
//		jobsbean.setStatus("01");
		
		log.info("Job : " + jobName + " is finished...");
		if (jobException!=null) {
//			jobsbean.setStatus("02");
			log.info("Exception thrown by: " + jobName
				+ " Exception: " + jobException.getMessage());
		}
//		srrpSynchronizeTaskService.save(jobsbean);
	}

}

package com.icfcc.srrptask.start;

import java.util.List;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPService.ReportproductFunction;
import com.icfcc.SRRPService.SrrpSynchronizeTaskService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.srrptask.job.JobObkect;
import com.icfcc.srrptask.util.QuartzManager;
import com.icfcc.srrptask.util.ReadApplicationContext;

@Component
public class SchedulerJobStart {
	private final Logger log = LoggerFactory.getLogger(SchedulerJobStart.class);

	@Autowired
	private SrrpSynchronizeTaskService srrpSynchronizeTaskService;
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	public void startTask() {
		List<String> jobNames = null;
		List<SrrpSynchronizeJobBean> list = null;
		SrrpSynchronizeJobBean jobsbean = null;
		try {
			Scheduler sche = schedulerFactoryBean.getScheduler();
			list = srrpSynchronizeTaskService.getTask();
			if (list != null) {
				jobNames = sche.getJobGroupNames();
				log.info("【系统启动】开始扫描任务表...");
				for (SrrpSynchronizeJobBean job : list) {
					if ("1".equals(job.getStopFlag())) {
						jobsbean = srrpSynchronizeTaskService.findByJobName(job.getJobName());
						if (!"".equals(job.getJobClass()) && job.getJobClass() != null) {
							JobObkect jobClass = (JobObkect) Class
									.forName(jobsbean.getJobClass()).newInstance();
							if (jobNames.size() > 0) {
								// 判断是否已经存在正在执行的job
								if (jobNames.contains(job.getJobName()+"_JOB_GROUP")) {
									// 判断是否重置
									if ("1".equals(job.getIsModify())) {
										//log.info("重置定时时间为" + job.getJobCron());
										QuartzManager.modifyJobTime(sche, job.getJobName(), job.getJobName()+"_JOB_GROUP",
												 job.getJobName()+"_TRIGGER", job.getJobName()+"_TRIGGER_GROUP", job.getJobCron());
										jobsbean.setIsModify("0");
										srrpSynchronizeTaskService.save(jobsbean);
									}
									if ("2".equals(job.getIsModify())) {
										log.info("立即执行为" + job.getJobCron());
//										QuartzManager.removeJob(sche,  job.getJobName(), job.getJobName()+"_JOB_GROUP",
//												 job.getJobName()+"_TRIGGER", job.getJobName()+"_TRIGGER_GROUP");
										QuartzManager.runAJobNow(sche,  job.getJobName(), job.getJobName()+"_JOB_GROUP" );
//										QuartzManager.removeJob(sche,  job.getJobName(), job.getJobName()+"_JOB_GROUP",
//												 job.getJobName()+"_TRIGGER", job.getJobName()+"_TRIGGER_GROUP");
										jobsbean.setIsModify("0");
										srrpSynchronizeTaskService.save(jobsbean);
									}
								} else {
									QuartzManager.addJob(sche, job.getJobName(), job.getJobName()+"_JOB_GROUP",
											 job.getJobName()+"_TRIGGER", job.getJobName()+"_TRIGGER_GROUP",
											jobClass.getClass(), job.getJobCron());
									jobsbean.setStatus(SRRPConstant.EXECUTESTART);
									srrpSynchronizeTaskService.save(jobsbean);
								}
							} else {
								if ("1".equals(job.getIsModify())) {
									//log.info("重置定时时间为" + job.getJobCron());
									QuartzManager.modifyJobTime(sche, job.getJobName(), job.getJobName()+"_JOB_GROUP",
											 job.getJobName()+"_TRIGGER", job.getJobName()+"_TRIGGER_GROUP", job.getJobCron());
									jobsbean.setIsModify("0");
								}else if ("2".equals(job.getIsModify())) {
									log.info("立即执行为" + job.getJobCron());
									
									QuartzManager.runAJobNow(sche,  job.getJobName(), job.getJobName()+"_JOB_GROUP" );
									
									jobsbean.setIsModify("0");
									srrpSynchronizeTaskService.save(jobsbean);
								}else{
									log.info("添加任务" + job.getJobName());
									QuartzManager.addJob(sche, job.getJobName(), job.getJobName()+"_JOB_GROUP",
											 job.getJobName()+"_TRIGGER", job.getJobName()+"_TRIGGER_GROUP",
											jobClass.getClass(), job.getJobCron());
									jobsbean.setStatus(SRRPConstant.EXECUTESTART);
								}
								srrpSynchronizeTaskService.save(jobsbean);

								
							}
						}
					} else {
						if (jobNames.contains(job.getJobName())) {
							QuartzManager.removeJob(sche,  job.getJobName(), job.getJobName()+"_JOB_GROUP",
									 job.getJobName()+"_TRIGGER", job.getJobName()+"_TRIGGER_GROUP");
						}
					}
				}
				log.info("【系统启动】扫描任务表结束...");
			}
		} catch (Exception e) {
			log.error("【系统启动】扫描任务表结束出现异常...");
			e.printStackTrace();
		}
	}
}

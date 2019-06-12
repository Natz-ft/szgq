package com.icfcc.srrptask.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.icfcc.SRRPDao.s.jpa.entity.portal.InvestorQuery;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.util.SRRPConstant;

public class MakePortalInvestorDataJob extends JobObkect {
	
	private final Logger log = LoggerFactory.getLogger(MakePortalIndexJob.class);
	private String jobName="";
	private Long jobId;
	// job执行方法
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
			jobName = context.getJobDetail().getKey().toString();
			jobName=StringUtils.substringAfter(jobName, ".") ;
			SrrpSynchronizeJobBean jobsbean=srrpSynchronizeTaskService.findByJobName(jobName);
			log.info("jobName:"+jobName+"描述:"+jobsbean.getJobDescription());
			jobId=jobsbean.getJobId();
			jobsbean.setStatus(SRRPConstant.EXECUTESTART);
			SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
			String executeResult = SRRPConstant.EXECUTESTART;
			String msg = "";
			String SynCount = "20";
			PlatformConfig sc = systemConfigDao.findByConfigName("finacingCount");
			if (null != sc) {
				SynCount = sc.getConfigValue();
			}
			try {
				jobLog.setJobId(jobId);
				jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
				jobLog.setJobName(jobName);// 任务名称
				jobLog.setStartTime(new Date());// 记录任务开始时间
				List<InvestorQuery> res = null;
				res = investorService.generateLatest(Integer.parseInt(SynCount));
				investorService.saveLatest(res);
				executeResult = SRRPConstant.EXECUTSUCC;
				log.info("完成"+jobsbean.getJobDescription());
				 msg = "完成"+jobsbean.getJobDescription();
			} catch (Exception e) {
				executeResult = SRRPConstant.EXECUTEXCEPTION;
				msg = e.getMessage();
				e.printStackTrace();
				log.error(jobsbean.getJobDescription()+"异常:"+msg);

			}finally {
				jobLog.setExeResult(executeResult);
				jobLog.setFailReason(msg);
				jobLog.setEndTime(new Date());// 记录执行结束时间
				jobsbean.setJobData(new Date());
				jobsbean.setNextjobData(getNextCron(jobsbean.getJobCron()));
				jobsbean.setStatus(executeResult);
				jobsbean.setExeCount(jobsbean.getExeCount()+1);//记录任务执行次数
				srrpSynchronizeJobLogService.save(jobLog);
				srrpSynchronizeTaskService.save(jobsbean);

			}
		
		}
}

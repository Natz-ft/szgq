package com.icfcc.srrptask.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icfcc.SRRPDao.s.jpa.entity.portal.FinancingEventQuery;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalDemand;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalLinks;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalNews;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalPartner;
import com.icfcc.SRRPDao.s.jpa.entity.portal.platformPortalEventQueryInvestor;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 
 * @ClassName: MakePortalIndexJob
 * @Description: 更新门户首页步骤: 1.有需要加工的数据，先执行调用存储过程 
 *                           2.执行从业务库迁移到前台库
 *                           3.调用Control,动态生成生成HTML
 * @author hugt 记录日志
 * @date 2018年2月8日 上午10:07:42
 *
 */
// @DisallowConcurrentExecution
public class MakePortalIndexJob extends JobObkect {
	
	private final Logger log = LoggerFactory.getLogger(MakePortalIndexJob.class);
	private String jobName="";
	private Long jobId;

	// job执行入口
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobName = context.getJobDetail().getKey().toString();
		jobName=StringUtils.substringAfter(jobName, ".");
		SrrpSynchronizeJobBean jobsbean=srrpSynchronizeTaskService.findByJobName(jobName);
		jobId=jobsbean.getJobId();
		log.info("jobName:"+jobName+"描述:"+jobsbean.getJobDescription());
		jobsbean.setStatus(SRRPConstant.EXECUTESTART);
		log.info("开始统计、迁移首页-统计值");
		callIndexTotal();
		log.info("开始迁移门户首页-融资需求");
		moveFinancingDemand();
		log.info("开始迁移门户首页-新闻动态");
		moveNews();
		log.info("开始迁移门户首页-最新投资动态");
		moveFinancingEvent();
		log.info("开始迁移迁移门户首页-合作伙伴");
		movePartner();
		log.info("开始迁移迁移门户首页-友情链接");
		moveLinks();
		
		log.info("开始生成首页动态HTML");
		callMakeHtmlControl(inexURL, jobName,jobId);
		
		jobsbean.setStatus(SRRPConstant.EXECUTSUCC);
		jobsbean.setJobData(new Date());
		jobsbean.setNextjobData(getNextCron(jobsbean.getJobCron()));
		jobsbean.setExeCount(jobsbean.getExeCount()+1);//记录任务执行次数
		srrpSynchronizeTaskService.save(jobsbean);
		log.info(jobsbean.getJobDescription()+"执行完毕");

	}
	/**
	 * 
	* @Title: callIndexTotal 
	* @Description: TODO(调用存储过程，统计首页数值) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void callIndexTotal() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		try {
			jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
			jobLog.setJobName(jobName);// 任务名称
			jobLog.setStartTime(new Date());// 记录任务开始时间
			jobLog.setJobId(jobId);

			String timeId = null;
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			cal.add(Calendar.MONTH, 0);
			timeId = sdf.format(cal.getTime());
			// 统计门户首页值
			String sql = "{CALL report_total_index(?)}";
			reportProductFuncation.call(timeId, sql);
			// 迁移首页-统计值
			portalTotalStaticService.indexTrannfer();
			msg="统计、迁移首页-统计值执行完毕";
			executeResult = SRRPConstant.EXECUTSUCC;
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg ="统计、迁移首页-统计值执行异常:"+ e.getMessage();
			e.printStackTrace();
			log.error(msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}
	}

    /**
     * 
    * @Title: moveFinancingDemand 
    * @Description: TODO(迁移业务库的融资需求到门户) 
    * @param   参数说明 
    * @return void    返回类型 
    * @throws
     */
	public void moveFinancingDemand() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		jobLog.setJobId(jobId);

		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		List<PlatformPortalDemand> res = null;
		try {
			res = financingDemandService.NewestRelsult(5);
			financingDemandService.saveLatest(res);
			executeResult = SRRPConstant.EXECUTSUCC;
			msg="迁移门户首页-融资需求执行完毕";

		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = "迁移门户首页-融资需求执行异常:"+e.getMessage();
			e.printStackTrace();
			log.error(msg);
		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}

	}

	/**
	 * 
	* @Title: moveNews 
	* @Description: TODO(迁移业务库的新闻到门户) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveNews() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setJobId(jobId);
		jobLog.setStartTime(new Date());// 记录任务开始时间
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		List<PlatformPortalNews> res = null;
		try {
			res = newService.NewestRelsult(Integer.MAX_VALUE);
			newService.saveLatest(res);
			executeResult = SRRPConstant.EXECUTSUCC;
			msg="迁移门户首页-新闻动态执行完毕";

		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = "迁移门户首页-新闻动态执行异常:"+e.getMessage();
			e.printStackTrace();
			log.error(msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}

	}

	/**
	 * 
	* @Title: moveFinancingEvent 
	* @Description: TODO(迁移业务库的投融事件到门户) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveFinancingEvent() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setJobId(jobId);
		jobLog.setStartTime(new Date());// 记录任务开始时间
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		String SynCount = "20";
		PlatformConfig sc = systemConfigDao.findByConfigName("countPortal");
		if (null != sc) {
			SynCount = sc.getConfigValue();
		}
		List<platformPortalEventQueryInvestor> res = null;
		try {
			financingEventService.extractFinancingEvent(Integer.parseInt(SynCount));
			executeResult = SRRPConstant.EXECUTSUCC;
			msg="迁移门户投资动态执行完毕";
			log.info(msg);
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = "迁移门户投资动态执行异常:"+e.getMessage();
			e.printStackTrace();
			log.error(msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}
	}

	/**
	 * 
	* @Title: movepartner 
	* @Description: TODO(迁移业务库的合作伙伴到门户) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void movePartner() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		jobLog.setJobId(jobId);

		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		List<PlatformPortalPartner> res = null;
		try {
			res = partnerService.getLatestPartner(7);
			partnerService.save(res);
			executeResult = SRRPConstant.EXECUTSUCC;
			msg="迁移门户首页-合作伙伴执行完毕";

		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = "迁移门户首页-合作伙伴执行异常:"+e.getMessage();
			e.printStackTrace();
			log.info(msg);
		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}
	}

	/**
	 * 
	* @Title: moveLinks 
	* @Description: TODO(迁移业务库的友情链接数据到门户) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveLinks() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		jobLog.setJobId(jobId);
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		List<PlatformPortalLinks> res = null;
		try {
			res = friendlyLinksService.getLatestLink(7);
			friendlyLinksService.save(res);
			executeResult = SRRPConstant.EXECUTSUCC;
			msg="迁移门户首页-友情链接执行完毕";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = "迁移门户首页-友情链接执行异常:"+e.getMessage();
			e.printStackTrace();
			log.error(msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}
	}


}

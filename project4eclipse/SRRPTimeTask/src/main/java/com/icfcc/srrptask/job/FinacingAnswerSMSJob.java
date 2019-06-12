package com.icfcc.srrptask.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.s.jpa.entity.portal.FinancingInfoQuery;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.util.SRRPConstant;

public class FinacingAnswerSMSJob  extends JobObkect {
	// job执行方法
	private final Logger log = LoggerFactory.getLogger(MakePortalIndexJob.class);
	private String jobName="";
	private Long jobId;
	// job执行方法
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobName = context.getJobDetail().getKey().toString();
		jobName=StringUtils.substringAfter(jobName, ".") ;
		SrrpSynchronizeJobBean jobsbean=srrpSynchronizeTaskService.findByJobName(jobName);
		jobsbean.setStatus(SRRPConstant.EXECUTESTART);
		jobId=jobsbean.getJobId();
		log.info("jobName:"+jobName+"描述:"+jobsbean.getJobDescription());
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		String executeResult = SRRPConstant.EXECUTESTART;
		String 	msg = "完成关注后的需求超过有限期发送短信通知";
		String SynCount = platformConfigService.getConfigValueByName("noFocusTime");
		int day=Integer.parseInt(SynCount)+1;
		try {
			jobLog.setJobId(jobId);
			jobLog.setExeResult(executeResult);// 就绪
			jobLog.setJobName(jobName);// 任务名称
			jobLog.setStartTime(new Date());// 记录任务开始时间
			//每天检查20内天无机构关注的需求，新增临时表发送短信			
			financingInfoDetailService.queryNoAnswer(String.valueOf(day));//最后一天凌晨未回复的关闭需求
			financingEventService.cancelFinacingEventNoTalks(String.valueOf(day));//关注的需求超过20天凌晨自动取消关注
			executeResult = SRRPConstant.EXECUTSUCC;
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("执行迁移门户融资需求查询列表的数据异常:"+msg);

		}finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
			jobsbean.setJobData(new Date());
			jobsbean.setStatus(executeResult);
			jobsbean.setExeCount(jobsbean.getExeCount()+1);//记录任务执行次数
			jobsbean.setNextjobData(getNextCron(jobsbean.getJobCron()));
			srrpSynchronizeTaskService.save(jobsbean);
		}
		
		
		log.info("完成"+jobsbean.getJobDescription());
	}
	
	/**
	 * 
	* @Title: callMakeHtmlControl 
	* @Description: TODO(远程调用生成HTML的方法) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void callMakeHtmlControl(String url,String jopName,Long jobId) {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		try {
			executeResult = SRRPConstant.EXECUTESTART;

			jobLog.setExeResult(executeResult);// 就绪
			jobLog.setJobName(jopName);// 任务名称
			jobLog.setStartTime(new Date());// 记录任务开始时间
			jobLog.setJobId(jobId);
			HttpClient httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			// HttpGet get = new
			// HttpGet("http://localhost:8080/SRRPBusinesWeb/index/initIndex/");
			HttpResponse response1;
			response1 = httpClient.execute(get);
			HttpEntity entity2 = response1.getEntity();
			String str = EntityUtils.toString(entity2, "utf-8");
			 Object succesResponse = JSON.parse(str);    //先转换成Object
		        @SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>)succesResponse;         //Object强转换为Map
			if(map.get("code").equals("02")){
				msg = map.get("msg");
				log.info("远程调用URL:"+url+"生成动态页面执行异常");
			}else{
				msg="远程调用URL生成动态页面执行完毕";
			}
			executeResult = map.get("code");
			
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = "生成动态页面执行异常:"+e.getMessage();
			e.printStackTrace();
		 	log.error("远程调用URL:"+url+"生成动态页面执行异常:"+e.getMessage());

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);

		}
	}
}

package com.icfcc.srrptask.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icfcc.SRRPDao.s1.jpa.entity.RpCompanyCreditscores;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.util.SRRPConstant;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

public class CallQueryScoreInterfaceJob  extends JobObkect {
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
			jobLog.setJobName("MakePortalIndexJob");// 任务名称
			jobLog.setStartTime(new Date());// 记录任务开始时间
			// 先判断评分状态是否正常
			if (client.getStatus()) {
				//若接口成功返回评分数据，则本月内不在执行
					log.info("调用评分接口迁移到业务评分表");
					// 先查询机构表的总机构数
					long total = queryScoreService.queryCount(); //1010
					// 机构表机构总数 / 100 = 循环几次调用接口
					int i = (int) Math.ceil((total / num));		//11
					int start = 0;
					ArrayList<RpCompanyCreditscores> scoreInfos=new ArrayList<RpCompanyCreditscores>();
					Set<String> scoreCodeList=queryScoreService.queryComList();	//['1','2']
					System.out.println("begin...:"+new Date());
					for (int a = 0; a <= i; a++) {
						List scoreList = queryScoreService.queryComList(start, num);//0-100,100,100
						String codes = queryScoreService.returnCodes(scoreList);   //0-100
						// 返回的查询结果
						String result = client.getScoreInfos(codes);
						try {
							if (null != result && !"".equals(result.trim())) {
								// 先清空当前表数据，然后在进行持久化操作
//								JSONArray compScores = JSONArray.fromObject(result);
								JSONArray array = JSON.parseArray(result);
								Iterator<Object> it = array.iterator();
								// 循环，解析每条json 封装到对象
								while (it.hasNext())  {
									RpCompanyCreditscores scoreInfo = new RpCompanyCreditscores();
									// 取出来第一个json
									JSONObject scoreJson = (JSONObject) it.next();
									// 解析封装成对象
									queryScoreService.analysisJSON(scoreInfo, scoreJson,scoreCodeList,scoreInfos);
									// 持久化
									
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						start += num;// 循环一次+100
					}
					if(scoreInfos.size()>0){
						queryScoreService.saveScore(scoreInfos);
					}
					System.out.println("end...:"+new Date());
					//返回数据，修改定时时间为下月1号执行
					jobsbean.setJobCron("0 0 3 1 * ?");
					jobsbean.setIsModify("1");
					
					log.info("调用评分接口迁移到业务评分表结束");
					msg = "完成调用评分接口迁移到业务评分表";
			}else{//接口返回评分数据失败
				//没有返回数据，修改定时时间为每天执行
				Calendar c = Calendar.getInstance();
				// 得到本月的那一天
				int today = c.get(Calendar.DAY_OF_MONTH);
				// 然后判断是不是本月的第一天
				if(today ==1){
					//没有返回数据，修改定时时间为每天执行
					jobsbean.setJobCron("0 0 2 * * ?");
					jobsbean.setIsModify("1");
				//是
				}
			}
			executeResult = SRRPConstant.EXECUTSUCC;
		} catch (Exception e) {
			log.error("执行调用评分接口迁移到业务评分表异常:"+e.getMessage());
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
	
	public static void main(String arg[]){
		int i = (int) Math.ceil((17178 / 100));
		System.out.println("i:"+i);
		int start = 0;
		int num=100;
		for (int a = 0; a <= i; a++) {
			System.out.println("start"+start);
	//		List scoreList = queryScoreService.queryComList(start, num);
			start += num;// 循环一次+100
			
		}
			
			
	}
}

package com.icfcc.srrptask.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icfcc.SRRPDao.s.jpa.entity.portal.GataWayStaticResult;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankArea;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankCompany;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankFinacingTurn;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankIndustry;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PortalRankInvestor;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobBean;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.util.SRRPConstant;
/**
 * 
* @ClassName: MakePortalStaticResultJob 
* @Description: TODO(加工统计运行成果数据任务) 
* @author hugt
* @date 2018年2月01日 上午9:23:29 
*
 */
public class MakePortalStaticResultJob  extends JobObkect {
	
	// 用于区域排名结果表加工
	public static final String AREA_CALL = "{CALL report_area(?)}";
	public static final String COMPANY_CALL = "{CALL report_company(?)}";
	public static final String INSDUSTRY_CALL = "{CALL report_industry(?)}";
	public static final String INVETSOR_CALL = "{CALL report_investor(?)}";
	public static final String TOTAL_CALL = "{CALL report_total(?)}";
	public static final String FINACINGTURN_CALL = "{CALL report_finacingturn(?)}";
	public static final String MONTHLY_CALL = "{CALL report_monthly(?)}";
	private final Logger log = LoggerFactory.getLogger(MakePortalIndexJob.class);
	private Long jobId;
   public String jobName="";
	// job执行方法
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		jobName = context.getJobDetail().getKey().toString();
		jobName=StringUtils.substringAfter(jobName, ".");
		SrrpSynchronizeJobBean jobsbean=srrpSynchronizeTaskService.findByJobName(jobName);
		log.info("jobName:"+jobName+"描述:"+jobsbean.getJobDescription());
		jobsbean.setStatus(SRRPConstant.EXECUTESTART);
		jobId=jobsbean.getJobId();
		log.info("调用加工报表统计存储过程");
		productCallReport();
		log.info("完成调用加工报表统计存储过程");
		log.info("迁移用于展示运行成果历史记录展示的统计数值");
		moveTotalStatic();
		log.info("完成用于展示运行成果历史记录展示的统计数值");
			
		log.info("迁移用于运行成果展示平台用户的数据");
		moveStaticByArea();
		log.info("完成迁移用于运行成果展示平台用户的数据");
			
		log.info("迁移用于运行成果展示融资轮次统计的数据");
		moveFinacingTurn();
		log.info("完成迁移用于运行成果展示融资轮次统计的数据");
		
		log.info("迁移用于运行成果月度榜单展示企业排名的榜单");
		moveRankCompany();
		log.info("完成迁移用于运行成果月度榜单展示企业排名的榜单");
		
		log.info("迁移用于运行成果月度榜单展示企业区域排名的榜单");
		moveRankArea();
		log.info("完成迁移用于运行成果月度榜单展示企业区域排名的榜单");
			
		log.info("迁移用于运行成果月度榜单展示行业排名的数据");
		moveIndustryInfos();
		log.info("完成迁移用于运行成果月度榜单展示行业排名的数据");
			
		log.info("迁移用于运行成果机构月度榜单展示机构排名的数据");
		moveInvestor();
		log.info("迁移用于运行成果机构月度榜单展示机构排名的数据");
		
		log.info("开始生成首页动态HTML");
		callMakeHtmlControl(mothlyRankURL, jobName,jobId);
		callMakeHtmlControl(staticURL, jobName,jobId);
		log.info("开始生成首页动态HTML");
		jobsbean.setStatus(SRRPConstant.EXECUTSUCC);
		jobsbean.setJobData(new Date());
		jobsbean.setNextjobData(getNextCron(jobsbean.getJobCron()));
		jobsbean.setExeCount(jobsbean.getExeCount()+1);//记录任务执行次数
		srrpSynchronizeTaskService.save(jobsbean);
		log.info("完成"+jobsbean.getJobDescription());
		
	}
	
	public void productCallReport(){
		call(AREA_CALL);// 用于区域排名结果表加工
		call(COMPANY_CALL);// 用于企业排名结果表加工
		call(INSDUSTRY_CALL);// 用于行业排名结果表加工
		call(INVETSOR_CALL);// 用于投资机构排名结果表加工
		call(TOTAL_CALL);// 用于融资统计功能的结果表加工
		call(FINACINGTURN_CALL);// 用于融资轮次的结果表加工
		call(MONTHLY_CALL);// 月度融资统计
	}
	
	/**
	 * 
	* @Title: moveTotalStatic 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveTotalStatic() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setJobId(jobId);
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "开始迁移运行成果历史记录";
		try {
			portalTotalStaticService.staticTrannfer();
			executeResult = SRRPConstant.EXECUTSUCC;
			 msg = "完成迁移运行成果历史记录";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("迁移用于运行成果历史记录展异常:"+msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}

	}
	/**
	 * 
	* @Title: moveTotalStatic 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveStaticByArea() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setJobId(jobId);
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "开始迁移平台用户";
		String monthly = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		cal.add(Calendar.MONTH, -1);
		monthly = sdf.format(cal.getTime());
		try {
			List<GataWayStaticResult> enterpriseCountList = portalStaticByAreaService.portStaticResultByType("04", monthly);
			List<GataWayStaticResult> investorCountList = portalStaticByAreaService.portStaticResultByType("05", monthly);
			if (enterpriseCountList != null) {
				portalStaticByAreaService.saveStaticResults(enterpriseCountList, "04");

			}
			if (investorCountList != null) {
				portalStaticByAreaService.saveStaticResults(investorCountList, "05");

			}
//			List<GataWayStaticResult> orgUserList = portalStaticByAreaService.portStaticResultByType("07", monthly);
//			if (investorCountList != null) {
//				portalStaticByAreaService.saveStaticResults(orgUserList, "07");
//
//			}
			executeResult = SRRPConstant.EXECUTSUCC;
			 msg = "完成迁移平台用户";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("迁移平台用户异常:"+msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}

	}
	/**
	 * 
	* @Title: moveFinacingTurn 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveFinacingTurn() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setJobId(jobId);
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "开始迁移融资轮次统计";
		try {
			List<PortalRankFinacingTurn> dataList = portalRankFinacTurnService.portalRankFinacTurnList();
			if (dataList != null && dataList.size() > 0) {
				portalRankFinacTurnService.saveFinacingTurnInfos(dataList);
			}
			executeResult = SRRPConstant.EXECUTSUCC;
			msg = "完成迁移融资轮次统计";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("迁移融资轮次统计异常:"+msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}

	}
	/**
	 * 
	* @Title: moveRankCompany 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveRankCompany() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setJobId(jobId);
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "开始迁移企业排名的榜单";
		try {
			List<PortalRankCompany> companyRankList = portalRankCompanyService.portalRankCompayList();
			if (companyRankList != null && companyRankList.size() > 0) {
				portalRankCompanyService.saveCompanyInfos(companyRankList);
			}
			executeResult = SRRPConstant.EXECUTSUCC;
			 msg = "完成迁移企业排名的榜单";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("迁移企业排名的榜单异常:"+msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}

	}
	/**
	 * 
	* @Title: moveRankArea 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveRankArea() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setJobId(jobId);
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "开始迁移企业区域排名的榜单";
		try {
			List<PortalRankArea> areaRankList = portalRankAreaService.portalRankAreaList();

			if (areaRankList != null && areaRankList.size() > 0) {
				portalRankAreaService.saveAreasInfos(areaRankList);
			}
			executeResult = SRRPConstant.EXECUTSUCC;
			msg = "完成迁移企业区域排名的榜单";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("迁移企业区域排名的榜单异常:"+msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}

	}
	/**
	 * 
	* @Title: moveIndustryInfos 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveIndustryInfos() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setJobId(jobId);
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "开始迁移行业排名";
		try {
			List<PortalRankIndustry> industryRankList = portalRankIndustryService.portalRankIndustryList();
			if (industryRankList != null && industryRankList.size() > 0) {
				portalRankIndustryService.saveIndustryInfos(industryRankList);
			}
			executeResult = SRRPConstant.EXECUTSUCC;
			msg = "完成迁移行业排名";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("迁移行业排名异常:"+msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
		}

	}
	/**
	 * 
	* @Title: moveIndustryInfos 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void moveInvestor() {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		jobLog.setJobId(jobId);
		jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
		jobLog.setJobName(jobName);// 任务名称
		jobLog.setStartTime(new Date());// 记录任务开始时间
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "开始迁移机构排名";
		
		try {
			List<PortalRankInvestor> investorRankList = portalRankInvestorService.portalRankInvestorList();
			if (investorRankList != null && investorRankList.size() > 0) {
				portalRankInvestorService.saveInvestorInfo(investorRankList);
			}
			executeResult = SRRPConstant.EXECUTSUCC;
			 msg = "完成开始迁移机构排名";
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("迁移迁移机构排名异常:"+msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);
			
		}

	}
	/**
	 * 
	* @Title: callIndexTotal 
	* @Description: TODO(调用存储过程，统计首页数值) 
	* @param   参数说明 
	* @return void    返回类型 
	* @throws
	 */
	public void call(String sql) {
		SrrpSynchronizeJobLog jobLog = new SrrpSynchronizeJobLog();
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "开始调用存储过程:"+sql;
		try {
			jobLog.setJobId(jobId);
			jobLog.setExeResult(SRRPConstant.EXECUTESTART);// 就绪
			jobLog.setJobName(jobName);// 任务名称
			jobLog.setStartTime(new Date());// 记录任务开始时间
			String timeId = null;
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			cal.add(Calendar.MONTH, -1);
			timeId = sdf.format(cal.getTime());
			// 统计门户首页值
			reportProductFuncation.call(timeId, sql);
			executeResult = SRRPConstant.EXECUTSUCC;
			 msg = "完成调用存储过程:"+sql;
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error("执行:"+sql+"存储过程异常:"+msg);

		} finally {
			jobLog.setExeResult(executeResult);
			jobLog.setFailReason(msg);
			jobLog.setEndTime(new Date());// 记录执行结束时间
			srrpSynchronizeJobLogService.save(jobLog);

		}
	}
}

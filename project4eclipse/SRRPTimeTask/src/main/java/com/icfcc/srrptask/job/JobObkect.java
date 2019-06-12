package com.icfcc.srrptask.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cfcc.cap.ws.creditscore.WSWebServiceClient;
import com.icfcc.SRRPDao.s1.jpa.entity.SrrpSynchronizeJobLog;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyCreditScoresDao;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyCreditScoresDaoImp;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformConfigDao;
import com.icfcc.SRRPService.ContantUsService;
import com.icfcc.SRRPService.FaqService;
import com.icfcc.SRRPService.FinancingDemandService;
import com.icfcc.SRRPService.FinancingEventInverstorService;
import com.icfcc.SRRPService.FinancingEventService;
import com.icfcc.SRRPService.FinancingInfoDetailService;
import com.icfcc.SRRPService.FriendlyLinksService;
import com.icfcc.SRRPService.InvestorService;
import com.icfcc.SRRPService.MakerStaticPortalHtmlService;
import com.icfcc.SRRPService.NewsService;
import com.icfcc.SRRPService.PartnerService;
import com.icfcc.SRRPService.PlatformConfigService;
import com.icfcc.SRRPService.PortalRankAreaService;
import com.icfcc.SRRPService.PortalRankCompanyService;
import com.icfcc.SRRPService.PortalRankFinacTurnService;
import com.icfcc.SRRPService.PortalRankIndustryService;
import com.icfcc.SRRPService.PortalRankInvestorService;
import com.icfcc.SRRPService.PortalStaticByAreaService;
import com.icfcc.SRRPService.PortalTotalStaticService;
import com.icfcc.SRRPService.QueryScoreService;
import com.icfcc.SRRPService.RateService;
import com.icfcc.SRRPService.ReportproductFunction;
import com.icfcc.SRRPService.SrrpSynchronizeJobLogService;
import com.icfcc.SRRPService.SrrpSynchronizeTaskService;
import com.icfcc.credit.platform.util.SRRPConstant;
@Component
public class JobObkect implements Job{
	private final Logger log = LoggerFactory.getLogger(MakePortalIndexJob.class);

	@Value("${inexURL}")
	public String inexURL;// 门户首页Control路径
	@Value("${staticURL}")
	public String staticURL;// 门户运行成果Control路径
	@Value("${mothlyRankURL}")
	public String mothlyRankURL;// 门户运行成果月榜单Control路径
	@Value("${contantUsURL}")
	public String contantUsURL;// 门户联系我们Control路径
	@Value("${newsURL}")
	public String newsURL;// 门户新闻动态Control路径
	@Value("${smscallbackURL}")
	public String smscallbackURL;// 门户新闻动态Control路径
	@Value("${smscallURL}")
	public String smscallURL;// 门户新闻动态Control路径
	
	@Autowired
	public SrrpSynchronizeJobLogService srrpSynchronizeJobLogService;
	@Autowired
	public FinancingDemandService financingDemandService;
	@Autowired
	public MakerStaticPortalHtmlService makerStaticPortalHtmlService;
	@Autowired
	public ReportproductFunction reportProductFuncation;
	@Autowired
	public NewsService newService;
	@Autowired
	public FinancingEventInverstorService financingEventInverstorService;
	@Autowired
	public PlatformConfigDao systemConfigDao;
	@Autowired
	public FriendlyLinksService friendlyLinksService;
	@Autowired
	public PartnerService partnerService;
	@Autowired
	public FinancingInfoDetailService financingInfoDetailService;
	@Autowired
	public FinancingEventService financingEventService;
	@Autowired
	public InvestorService investorService;
	@Autowired
	public ContantUsService contantUsService;
	@Autowired
	public CompanyCreditScoresDaoImp crsDaoImp;
	@Autowired
	public CompanyCreditScoresDao crsDao;
	@Autowired
	public QueryScoreService queryScoreService;
	@Autowired
	public WSWebServiceClient client;
	
	@Autowired
	public PortalTotalStaticService portalTotalStaticService;
	@Autowired
	public PortalStaticByAreaService portalStaticByAreaService;
	@Autowired
	public PortalRankFinacTurnService portalRankFinacTurnService;
	@Autowired
	public PortalRankAreaService portalRankAreaService;
	@Autowired
	public PortalRankIndustryService portalRankIndustryService;
	@Autowired
	public PortalRankInvestorService portalRankInvestorService;
	@Autowired
	public SrrpSynchronizeTaskService srrpSynchronizeTaskService;
	@Autowired
	public  PortalRankCompanyService portalRankCompanyService;
	@Autowired
	public  PlatformConfigService platformConfigService;
	@Autowired
	public RateService rateService;
	@Value("${count}")
	public int num;
	@Autowired
	public FaqService faqService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}
	public String getNextCron(String cron)  {
		String NextCron=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();  
	       try {
			cronTriggerImpl.setCronExpression(cron);
			 Calendar calendar = Calendar.getInstance();
		       Date now = calendar.getTime();  
		       calendar.add(Calendar.MONTH, 1);//把统计的区间段设置为从现在到2年后的今天（主要是为了方法通用考虑，如那些1个月跑一次的任务，如果时间段设置的较短就不足20条)  
		       List<Date> dates = TriggerUtils.computeFireTimesBetween(cronTriggerImpl, null, now, calendar.getTime());//这个是重点，一行代码搞定~~  
		       if(dates.size()<1){
		    	   NextCron="时间已过期";
		       }else{
		    	   NextCron =df.format(dates.get(0));
		       }
		       
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		return NextCron;
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



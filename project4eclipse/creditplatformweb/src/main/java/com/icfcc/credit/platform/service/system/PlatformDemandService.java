package com.icfcc.credit.platform.service.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.CompanyBusinessplan;
import com.icfcc.credit.platform.jpa.entity.system.FinacingDemand;
import com.icfcc.credit.platform.jpa.entity.system.FinacingEvent;
import com.icfcc.credit.platform.jpa.entity.system.FinacingRecord;
import com.icfcc.credit.platform.jpa.entity.system.GataWayFinacingEventQuery;
import com.icfcc.credit.platform.jpa.entity.system.SendSmsLog;
import com.icfcc.credit.platform.jpa.entity.system.TempSendSms;
import com.icfcc.credit.platform.jpa.repository.business.CompanyBaseDao;
import com.icfcc.credit.platform.jpa.repository.system.CompanyBusinessPlanDao;
import com.icfcc.credit.platform.jpa.repository.system.FinacingEventDao;
import com.icfcc.credit.platform.jpa.repository.system.FinacingInfoDao;
import com.icfcc.credit.platform.jpa.repository.system.FinacingRecordDao;
import com.icfcc.credit.platform.jpa.repository.system.GataWayFinacingEventQueryDao;
import com.icfcc.credit.platform.jpa.repository.system.SendSmsLogDao;
import com.icfcc.credit.platform.jpa.repository.system.TempSendSmsDao;
import com.icfcc.credit.platform.util.jpa.PageUtil;

@Component
@Transactional(value = "transactionManager")
public class PlatformDemandService {
	@Autowired
	private FinacingInfoDao finacingInfoDao;
	@Autowired
	private TempSendSmsDao tempSendSmsDao;
	@Autowired
	private SendSmsLogDao sendsmslogdao;
	@Autowired
	private CompanyBusinessPlanDao planDao;
	@Autowired
	private CompanyBaseDao baseDao;
	
	@Autowired
	private FinacingEventDao eventDao;
	@Autowired
	private FinacingRecordDao finacingRecordDao;
	@Value("${redic.dic.url}")
	public String dicUrl;// 更新redis

	
	@Autowired
	private GataWayFinacingEventQueryDao finacingEventQueryDao;


	
	
	
	public SendSmsLog  saveSmsLog(SendSmsLog log){
		SendSmsLog log1 =sendsmslogdao.save( log);
		return log1;
	}
	public void sendSms(FinacingDemand event, String type,SendSmsLog log,String name) {
		TempSendSms temp = new TempSendSms();
		temp.setSendType(type);// 给企业发送短信
		temp.setEnterporinvestId(event.getEnterpriseId());
		temp.setInfooreventId(event.getInfoId());
		temp.setInfoName(event.getProjectName());
		temp.setSid(log.getSid());
		temp.setRssId(log.getRssId());
		temp.setMobile(log.getMobile());
		temp.setName(name);
		temp.setSendStatus("00");
		tempSendSmsDao.save(temp);
		callMakeHtmlControl();
	}
	public void callMakeHtmlControl() {
		String msg = "";
		String url=StringUtils.substringBefore(dicUrl, "initInfo/initDD/")+"sms/smscall"; 
		try {
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
			}else{
				msg="远程调用URL生成动态页面执行完毕";
			}
			
		} catch (Exception e) {
			msg = "生成动态页面执行异常:"+e.getMessage();
			e.printStackTrace();

		}
	}
	public Page<FinacingDemand> getSystemFinacindDemandList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<FinacingDemand> spec = PageUtil.buildSpecification(searchParams, FinacingDemand.class);
        Page<FinacingDemand> page=finacingInfoDao.findAll(spec, pageRequest);
        List<FinacingDemand> demands =page.getContent();
        for (FinacingDemand finacingDemand : demands) {
        	finacingDemand.setEnterpriseName(baseDao.getNameById(finacingDemand.getEnterpriseId()));
		}
        
        return page;
    }
	
	public FinacingDemand findById(String id){
		FinacingDemand demand=finacingInfoDao.findByInfoId(id);
		return demand;
	}
	
	public CompanyBusinessplan findPlanById(String id){
		CompanyBusinessplan plan=planDao.findById(id);
		return plan;
	}
	
	
	public void saveDemand(FinacingDemand demand){
		finacingInfoDao.save(demand);
	}
	
	public void saveFinacingEvent(FinacingEvent event){
		eventDao.save(event);
	}
	
	public List<FinacingEvent> findFinacingEventByInfoId(String infoId){
		return eventDao.findFinacingEventByInfoId(infoId);
	}
	
	public void saveREcord(FinacingRecord finacingRecord){
		finacingRecordDao.save(finacingRecord);
	}
	
	
	public Page<GataWayFinacingEventQuery> getFinacindEventList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
		 PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
	        Specification<GataWayFinacingEventQuery> spec = PageUtil.buildSpecification(searchParams, GataWayFinacingEventQuery.class);
	        Page<GataWayFinacingEventQuery> page=finacingEventQueryDao.findAll(spec, pageRequest);
	        List<GataWayFinacingEventQuery> demands =page.getContent();
	        return page;
	}
	
	
	public GataWayFinacingEventQuery findByEventId(String eventId){
		GataWayFinacingEventQuery demand=finacingEventQueryDao.findById(eventId);
		return demand;
	}
	
	
	public FinacingEvent findFinacingEventId(String eventId){
		return eventDao.findFinacingEventId(eventId);
	}

	public void upProjectName(GataWayFinacingEventQuery finacingEvent,
			FinacingEvent event) {
		finacingEventQueryDao.save(finacingEvent);
		eventDao.save(event);
	}
	
}

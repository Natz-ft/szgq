package com.icfcc.SRRPService.enterprise;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.icfcc.SRRPDao.jpa.entity.SendSmsLog;
import com.icfcc.SRRPDao.jpa.entity.TempSendSms;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEvent;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingRecord;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.jpa.repository.SendSmsLogDao;
import com.icfcc.SRRPDao.jpa.repository.TempSendSmsDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBaseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.FinacingEventDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.FinacingRecordDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.InvestorDao;
import com.icfcc.SRRPDao.jpa.repository.inverstorg.FinacingInfoDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformUserDao;
import com.icfcc.SRRPService.PlatformSystem.PlatformConfigService;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.inverstorg.DemandInfoService;
import com.icfcc.SRRPService.util.SmsClientUtil;
import com.icfcc.credit.platform.util.SRRPConstant;

@Service
@Transactional(value = "transactionManager")
public class SendSmService {
	@Autowired
	private TempSendSmsDao tempSendSmsDao;
	@Autowired
	private SendSmsLogDao sendSmsLogDao;
	@Autowired
	private FinacingEventDao financingEventDao;
	@Autowired
	private FinacingRecordDao finacingRecordDao;
	@Autowired
	private InvestorDao investorDao;
	@Autowired
	private FinacingInfoDao finacingInfoDao;
	@Autowired
	private PlatformUserDao platformUserDao;
	@Autowired
	private  PlatformConfigService config;
	@Autowired
	private DemandInfoService demandInfoService;
	@Autowired
	private CompanyBaseDao companyBaseDao;
	@Autowired
	private FinacingEventService finacingEventService;
	@Autowired
	private PlatformUserService serService;
	public Random random = new Random();
	public List<TempSendSms>  getTempSendSms(String status ) {

		return tempSendSmsDao.findBySendType(status);
	}
	public SendSmsLog  getSendSmsLogBysid(String sid ) {

		return sendSmsLogDao.findById(sid);
	}
	public SendSmsLog  findByrssId(String rssId ) {

		return sendSmsLogDao.findByrssId(rssId);
	}
	public void deleteTempSendSmsBySid(String id ) {

		 tempSendSmsDao.delete(id);;
	}
	public SendSmsLog saveSendSmsLog(SendSmsLog sendSmsLog ) {

		return sendSmsLogDao.save(sendSmsLog);
	}
	public void SaveSendLog(String operType,String enterporinvestId,String infooreventId,String type,String phone,String name,String status,String sendSid){
		if(sendSid.equals("")||sendSid==null){
			long sid =System.currentTimeMillis() / 10000+random.nextInt(999999)%900000+100000;
			sendSid=String.valueOf(sid);
		}
		SendSmsLog log=new SendSmsLog();
		log.setInfoId(infooreventId);
		log.setEnterporinvestId(enterporinvestId);
		log.setOperdate(new Date());
		log.setSendStatus(status);
		log.setSid(sendSid);
		log.setMobile(phone);
		log.setSendType(type);
		log=sendSmsLogDao.save(log);
		if(operType.equals("00")){//需要回复 记录任务表
			TempSendSms temp=new TempSendSms();
			temp.setSendType(type);//给企业发送短信
			temp.setEnterporinvestId(enterporinvestId);
			temp.setSid(sendSid);
			temp.setInfooreventId(infooreventId);
			temp.setName(name);
			temp.setSendStatus(status);//未发送
			temp.setMobile(phone);
			temp.setRssId(log.getRssId());
			tempSendSmsDao.save(temp);
		}
	}
	public void saveTempSendSms(TempSendSms tempSendSms ) {

		tempSendSmsDao.save(tempSendSms);;
	}
	public void answerClose(String eventId ) {
		FinacingEvent event=financingEventDao.findOne(eventId);
		if(event!=null){
			event.setStatus(SRRPConstant.FINANCPHASE32);
			event.setOperdate(new Date());
			financingEventDao.save(event);
			String name="";
			if(event.getInvestLevel().equals("0")){
				Investor investor = this.investorDao.findOne(event.getInvestorgId());
				name=investor.getCertno();
			}else{
				PlatformUser PlatformUser = this.platformUserDao.findOne(event.getFoundId());
				name=PlatformUser.getUsername();			
				}
			FinacingRecord finacingRecord = new FinacingRecord();
   			finacingRecord.setEventId(event.getEventId());
   			finacingRecord.setInfoId(event.getInfoId());
   			finacingRecord.setEnterpriseid(event.getEnterpriseId());
   			finacingRecord.setInvestorgid(event.getInvestorgId());
   			finacingRecord.setOpertype(SRRPConstant.FINANCPHASE32);
			String operContent =   "【" + name + "】" + "拒绝投资";;
			finacingRecord.setOpercontent(operContent);
			finacingRecord.setStatus(SRRPConstant.DEMANDSTATUS02);
			finacingRecord.setOperuser(event.getOperuser());
			finacingRecord.setOperdate(new Date());
    		finacingRecordDao.save(finacingRecord);
    		FinacingDemandInfo finacingDemandInfo=finacingInfoDao.findByInfoId(event.getInfoId());
    		int updateFlag=finacingDemandInfo.getUpdateFlag();
            finacingDemandInfo.setUpdateFlag(updateFlag+1);
            finacingInfoDao.save(finacingDemandInfo);
		}
		
	 
		
	}
	public void answerFincineDemandOpen(String infoId ) {
		
		FinacingDemandInfo demand=finacingInfoDao.findByInfoId(infoId);
		if(demand!=null){
			 int upflag= demand.getUpdateFlag();
	    	 upflag=upflag+1;
	    	 demand.setUpdateFlag(upflag);
			 demand.setOpen("0");
     		 demand.setOperdate(new Date());
     		 finacingInfoDao.save(demand);//需改为公开
     		 finacingInfoDao.deleteFinacingEventByinfoId(demand.getInfoId());//删除融资事件信息
     		 finacingRecordDao.deleteFinacingRecord(demand.getInfoId());//删除记录表

		}
	}
	//转公开之前拆查询当前需求是否有机构关注或撤回  
	public boolean isFoucsDemandInfo(String infoId ) {
		   boolean isOpen=true;
			FinacingDemandInfo demand=finacingInfoDao.findByInfoId(infoId);
			if(demand.getRevokeFlag().equals("0")){//没有撤回
		        List<FinacingEvent> list= finacingInfoDao.isFoucsDemandInfo(infoId);//检查
		        if(list!=null&& list.size()>0){//有机构关注
		        	isOpen=false;
		        }
			}else{//撤回
				isOpen=false;
			}
	        System.out.println("isOpen=========="+isOpen);
	        return isOpen;
	}
	
	//转公开之前拆查询当前需求是否有机构关注或撤回
		public boolean isRevokeFlag(String eventId ) {
			   boolean isOpen=true;
			   FinacingEvent event=financingEventDao.findOne(eventId);
			   FinacingDemandInfo demand=finacingInfoDao.findByInfoId(event.getInfoId());
			   if(!demand.getRevokeFlag().equals("0")){//没有撤回
					isOpen=false;
				}
		      return isOpen;
		}
	public String AnswerMessge(String type,String name,String demandName ,String sid,String operDate,String phone) {
		boolean result = true;
		String status="";
		String arry[] = new String[7];
		if("01".equals(type)){//关注前通知是否转公开
			arry[0] = name;
			arry[1] = operDate;
			arry[2] = String.valueOf(sid);
			arry[3] = String.valueOf(sid);
		}else if("02".equals(type)){//关注后通知是否披露信息给基金
			arry[0] = name;
			arry[1] = demandName;
			arry[2] = String.valueOf(sid);
			arry[3] = String.valueOf(sid);
		}else if("03".equals(type)){//关注后通知是否披露信息给机构
			arry[0] = name;
			arry[1] = demandName;
			arry[2] = String.valueOf(sid);
			arry[3] = String.valueOf(sid);
		}else if("04".equals(type)){//关注前通知机构提示有效期
			arry[0] = name;
		}else if("05".equals(type)){//关注后关注有效期提醒
			arry[0] = name;
		}else if("06".equals(type)){//约谈后询问项目进度 第一次
			arry[0] = name;
			arry[1] = demandName;
			arry[2] = String.valueOf(sid);
			arry[3] = String.valueOf(sid);
		}else if("07".equals(type)){//约谈后询问项目进度 第二次
			arry[0] = name;
			arry[1] = demandName;
			arry[2] = String.valueOf(sid);
			arry[3] = String.valueOf(sid);
		}
//		else if("09".equals(type)){//约谈后询问项目进度 第二次
//			arry[0] = name;
//		}
		result = sendMessge(type, arry, phone);
		if (result) {
			status=SRRPConstant.SMS_STATUS_SEND;
		} else {
			status=SRRPConstant.SMS_STATUS_SENDERROR;
		}
		return status;
	}
	public boolean sendMessge(String type, String arry[], String mobile) {
		boolean result = false;
		try {
			// 获取页面输入的联系人电话，调用发送短信的接口
			String[] keys = keys(type);
			result = SmsClientUtil.querySms(arry, keys, mobile, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//披露基金
	public void orgFundPublish(String eventId) {
		try {
			//基金
			FinacingEvent event = finacingEventService.findFinacingEventById(eventId);
			FinacingEvent OrgEvent=null;
			CompanyBase base=companyBaseDao.findById(event.getEnterpriseId());
			String operuser=base.getName();
			//披露机构 只披露机构
			FinacingRecord finacingRecord = new FinacingRecord();
			finacingRecord.setEventId(event.getEventId());
			finacingRecord.setInfoId(event.getInfoId());
			finacingRecord.setEnterpriseid(event.getEnterpriseId());
			finacingRecord.setInvestorgid(event.getInvestorgId());
			finacingRecord.setOpercontent(operuser+"向投资者用户披露了信息");
			finacingRecord.setOperdate(new Date());
			finacingRecord.setOperuser(operuser);
			demandInfoService.saveRecord(finacingRecord);
			//根据融资事件的id进行信息披露
			finacingEventService.updatePublishFlag(event.getEventId());
			 OrgEvent=finacingEventService.geteventByMangageOrg(event.getInfoId(), event.getInvestorgId(), "0");
				if(OrgEvent!=null){
					PlatformUser	platformUser2 =serService.getUser(OrgEvent.getFoundId());
					FinacingRecord finacingRecord1 = new FinacingRecord();
					finacingRecord1.setEventId(OrgEvent.getEventId());
					finacingRecord1.setInfoId(OrgEvent.getInfoId());
					finacingRecord1.setEnterpriseid(OrgEvent.getEnterpriseId());
					finacingRecord1.setInvestorgid(OrgEvent.getInvestorgId());
					finacingRecord1.setOpercontent(platformUser2.getUsername()+"向投资者用户披露了信息");
					finacingRecord1.setOperdate(new Date());
					finacingRecord1.setOperuser(operuser);
					demandInfoService.saveRecord(finacingRecord);
					//根据融资事件的id进行信息披露
					finacingEventService.updatePublishFlag(OrgEvent.getEventId());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//披露机构
	public void orgPublish(String eventId) {
		try {
			//根据事件id查询融资事件的需求id
			FinacingEvent event = finacingEventService.findFinacingEventById(eventId);
			CompanyBase base=companyBaseDao.findById(event.getEnterpriseId());
			String operuser=base.getCode();
			//披露机构 只披露机构
			FinacingRecord finacingRecord = new FinacingRecord();
			finacingRecord.setEventId(event.getEventId());
			finacingRecord.setInfoId(event.getInfoId());
			finacingRecord.setEnterpriseid(event.getEnterpriseId());
			finacingRecord.setInvestorgid(event.getInvestorgId());
			finacingRecord.setOpercontent("【" + operuser + "】" +"向投资者用户披露了信息");
			finacingRecord.setOperdate(new Date());
			finacingRecord.setOperuser(operuser);
			demandInfoService.saveRecord(finacingRecord);
			//根据融资事件的id进行信息披露
			finacingEventService.updatePublishFlag(eventId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 public String [] keys(String flag) {
			String []params =new String[3];
		    String appid = (config.getConfigValueByName("appid"));
		    String appkey = config.getConfigValueByName("appkey");
		    params[0]=appid;
		    params[1]=appkey;
		    if("1".equals(flag) || "2".equals(flag)){//注册成功
		    	params[2] = (config.getConfigValueByName("register"));
			}else if("3".equals(flag) || "4".equals(flag) ){//审核通过
				params[2] = (config.getConfigValueByName("pass"));
			}else if("5".equals(flag) || "6".equals(flag)){//审核不通过
				params[2] = (config.getConfigValueByName("noPass"));
			}else if("16".equals(flag)){
				params[2] = (config.getConfigValueByName("noUpdatePass"));
			}else if("7".equals(flag)){
				params[2] = (config.getConfigValueByName("onlineAnswer"));
			}else if("01".equals(flag)){
				params[2] = (config.getConfigValueByName("IsOpen"));
			}else if("02".equals(flag)){
				params[2] = (config.getConfigValueByName("isInvestPush"));
			}else if("03".equals(flag)){
				params[2] = (config.getConfigValueByName("isInvestFundPush"));
			}else if("04".equals(flag)){
				params[2] = (config.getConfigValueByName("focusBefoExprice"));
			}else if("05".equals(flag)){
				params[2] = (config.getConfigValueByName("focusAfterExprice"));
			}else if("06".equals(flag)){
				params[2] = (config.getConfigValueByName("talkExpriceOne"));
			}else if("07".equals(flag)){
				params[2] = (config.getConfigValueByName("talkExpriceTwo"));
			}else if("08".equals(flag)){
				params[2] = (config.getConfigValueByName("answerOpen"));
			}else if("010".equals(flag)){
				params[2] = (config.getConfigValueByName("answerError"));
			}else if("012".equals(flag)){
				params[2] = (config.getConfigValueByName("answergiveup"));//放弃投资生效通知
			}else if("013".equals(flag)){
				params[2] = (config.getConfigValueByName("answerSucess"));
			}else if("061".equals(flag)){
				params[2] = (config.getConfigValueByName("answerInvesting"));
			}else if("062".equals(flag)){
				params[2] = (config.getConfigValueByName("answerInvested"));
			}else if("063".equals(flag)){
				params[2] = (config.getConfigValueByName("answerinvest"));//回复放弃投资回执通知
			}else  if("09".equals(flag)){//撤回
				params[2] = (config.getConfigValueByName("revoke"));
			}else  if("091".equals(flag)){//
				params[2] = (config.getConfigValueByName("revokeAuditPass"));
			}else  if("092".equals(flag)){
				params[2] = (config.getConfigValueByName("revokeAuditNoPass"));
			}
		    
		    return params;
		}
}

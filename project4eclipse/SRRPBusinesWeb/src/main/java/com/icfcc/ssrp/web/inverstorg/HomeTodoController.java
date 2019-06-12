package com.icfcc.ssrp.web.inverstorg;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.SendSmsLog;
import com.icfcc.SRRPDao.jpa.entity.TempSendSms;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.EventBeanForOrg;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEvent;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEventHis;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingRecord;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.enterprise.InvestorFollow;
import com.icfcc.SRRPDao.jpa.entity.enterprise.InvestorPublish;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorLoan;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformConfig;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBaseDao;
import com.icfcc.SRRPService.PlatformSystem.PlatformConfigService;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.FinacingEventService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.SRRPService.enterprise.SendSmService;
import com.icfcc.SRRPService.inverstorg.DemandInfoService;
import com.icfcc.SRRPService.inverstorg.FinacingDemandService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/homeTodo")
public class HomeTodoController extends SRRPBaseController {
	private static Logger _log = LoggerFactory.getLogger(HomeTodoController.class);

	@Autowired
	private FinacingEventService finacingEventService;
	@Autowired
	private PlatformConfigService platformConfigService;
	@Autowired
	private InvestorService investorService;
	@Autowired
	private DemandInfoService demandInfoService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private FinacingDemandService finacingDemandService;
	@Autowired
	private PlatformUserService serService;
	@Autowired
	private CompanyBaseDao companyBaseDao;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	@Autowired
	private SendSmService sendSmService;
	public Random random = new Random();

	/**
	 * 查询提醒机构关注的融资信息
	 * 
	 * @Title: sendInfo
	 * @param request
	 * @param response
	 * @return String 返回类型
	 * 
	 */
	@RequestMapping(value = "/sendHomeTodo")
	public String sendHomeTodo(HttpServletRequest request, HttpServletResponse response) {
		Boolean changeFlag = false;// 定义一个是否脱敏的标识
		try {
			Map<String, String> userInfos = this.getUserInfo(request, response);
			String orgNo = userInfos.get(SRRPConstant.LOGINORGNO);
			String userId = userInfos.get(SRRPConstant.LOGINUSERNAME);
			String operType = request.getParameter("operType");
			operType = operType == null ? "focus" : operType;
			String resultPage = "WEB-INF/views/inverstorg/";
			switch (operType) {
			case "focus":
				resultPage += "sendInfo";
				break;
			case "talks":
				resultPage += "meetInfo";
				break;
			case "investor":
				resultPage += "investInfo";
				break;
			case "loan":
				resultPage += "loanInfo";
				break;
			case "push":
				resultPage += "publishInfo";
				break;
			default:
				resultPage += "sendInfo";
				break;
			}
			QueryCondition queryCondition = new QueryCondition();
			queryCondition.setOperType(operType);
			queryCondition.setInvestorId(orgNo);
			queryCondition.setCurPage(-1);
			queryCondition.setMaxSize(-1);
			queryCondition.setUserId(userId);
			// 要提醒的条数
			Map<String, BigInteger> map = finacingEventService.getCount(queryCondition);
			request.setAttribute("map", map);
			// 发布状态融资信息
			List<EventBeanForOrg> eventBeanForOrgs = finacingEventService.getEventBeansByStatusInvestor(queryCondition);
			if ("focus".equals(operType) || "push".equals(operType)) {
				if (eventBeanForOrgs != null && eventBeanForOrgs.size() > 0) {
					for (EventBeanForOrg data : eventBeanForOrgs) {

						if ("push".equals(operType)) {
							List<InvestorPublish> investorPublish = finacingEventService.ifPublished(data.getEventId(),
									data.getEnterpriseId(), orgNo);
							if (investorPublish != null && investorPublish.size() > 0) {
								data.setIsPublished(SRRPConstant.PUBLISHED);
							} else {
								data.setIsPublished(SRRPConstant.UNPUBLISHED);
							}
						}
						if ("focus".equals(operType)) {
							Date date = new Date();
							if (!"长期".equals(data.getFollowTimeStr())) {
								if (Integer.parseInt(sdf.format(date)) > Integer
										.parseInt(data.getFollowTimeStr().replaceAll("-", ""))) {
									data.setIsOvered(SRRPConstant.OVERED);
								} else {
									data.setIsOvered(SRRPConstant.UNOVERED);
								}
							}
						}
					}
				}
			}
			// 在向我投递阶段不进行需求的脱敏，在启动约谈阶段只有公开的需求才进行脱敏其他时刻不进行脱敏
			//1.在启动越疼阶段
			for (EventBeanForOrg eventBeanForOrg : eventBeanForOrgs) {
				eventBeanForOrg.setRemainTime(getRemainTime(eventBeanForOrg.getOperdate(), eventBeanForOrg.getStatus()));
				eventBeanForOrg.setShowFlag("0");
				if ("0".equals(eventBeanForOrg.getOpen()) && "11".equals(eventBeanForOrg.getStatus())) {
				        eventBeanForOrg.setProjectName(DesensitizationUtil .changeProjectName(eventBeanForOrg.getProjectName(), eventBeanForOrg.getEnterpriseName()));// 更改项目名称
                        eventBeanForOrg.setEnterpriseNameShow( DesensitizationUtil.enterpriseName(eventBeanForOrg.getEnterpriseName()));// 更改企业名称
                        eventBeanForOrg.setShowFlag("1");// 征信报告隐藏基本信息、信贷、抵质押、负面信息
				} else {
					eventBeanForOrg.setEnterpriseNameShow(eventBeanForOrg.getEnterpriseName());// 不
																								// 更改企业名称
				}
			}
			request.setAttribute("eventBeanForOrgs",
					JSON.toJSONString(eventBeanForOrgs, SerializerFeature.DisableCircularReferenceDetect));
			return resultPage;
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return "";
	}

	/**
	 * 异步判断该需求是否被关闭
	 */
	@RequestMapping(value = "/checkStatus")
	public void checkStatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			String infoId = request.getParameter("infoId");
			String statusInfo = request.getParameter("statusInfo");
			FinacingDemandInfo finacingDemandInfo = demandInfoService.getFinacingInfo(infoId);
			if (!SRRPConstant.DEMANDSTATUS99.equals(finacingDemandInfo.getStatus())) {
				response.getWriter().print("success");
			} else {
				if (statusInfo != null && statusInfo != "") {
					response.getWriter().print("wrong");
				} else {
					response.getWriter().print("error");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	/**
	 * 点击按钮触发状态改变的事件 首页待办事项 ：1、关注 2、忽略
	 * 
	 * @Title: delInfo
	 * @param page
	 * @param request
	 * @param response
	 * @return void 返回类型
	 */
	@RequestMapping(value = "/delInfo")
	public void changeStatus(PageBean page, HttpServletRequest request, HttpServletResponse response) {
		try {
			String operType = request.getParameter("operType");
			String eventId = request.getParameter("eventId");
			String infoId = request.getParameter("infoId");
			// String status = request.getParameter("status");
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String orgNo = userInfos.get(SRRPConstant.LOGINORGNO);
			String userName = userInfos.get(SRRPConstant.LOGINUSERNAME);
			FinacingDemandInfo finacingDemandInfo = demandInfoService.getFinacingInfo(infoId);
			int updateFlag = finacingDemandInfo.getUpdateFlag();
			finacingDemandInfo.setUpdateFlag(updateFlag + 1);
			// step 1 修改需求表

			// step 2 切换融资事件表状态
			FinacingEvent finacingEvent = null;
			if (eventId != null && !"".equals(eventId)) {
				finacingEvent = finacingEventService.findFinacingEventById(eventId);
			}
			boolean isSendSMS=false;
			// 公开项目点击关注
			if (finacingEvent == null) {
				finacingEvent = new FinacingEvent();
				// 获取UUID并转化为String对象
				String uuid = UUID.randomUUID().toString().replace("-", "");
				finacingEvent.setEventId(uuid);
				finacingEvent.setEnterpriseId(finacingDemandInfo.getEnterpriseId());
				finacingEvent.setInfoId(infoId);
				// 向事件表中存机构ID 通过登录用户获取
				finacingEvent.setInvestorgId(orgNo);
				finacingEvent.setCurrency(finacingDemandInfo.getCurrency());
				finacingEvent.setMailuser(finacingDemandInfo.getOperuser());
				finacingEvent.setMaildate(finacingDemandInfo.getFollowTime());
				finacingEvent.setProjectName(finacingDemandInfo.getProjectName());
				finacingEvent.setInvestLevel(userInfos.get("level"));
				finacingEvent.setFoundId(userInfos.get("userId"));
				finacingEvent.setPublishFlag("1");
				
				//根据机构的id获取机构信息，查询主机构是否有融资事件，如果有的话，把主机构的披露信息复制，如果没有的话，
				Investor investor= investorService.findInverstorById(orgNo);
				String name=investor.getCertno();
				
				if(userInfos.get("level").equals("0")){//机构用户
					List<FinacingEvent> orgEvents = finacingEventService.geteventByInfoidAndOrgId(infoId, orgNo);
					for(FinacingEvent event:orgEvents ){
						if(event.getPublishFlag().equals("0")&&event.getInvestLevel().equals("1")){
							finacingEvent.setPublishFlag("0");
							isSendSMS=true;//如果已经披露下机构的基金，不需要披露给机构，并且不需要发送短信
						}
					}
				}
				if(!isSendSMS){
					String type="";
					String phone=finacingDemandInfo.getRelPhone();
					String copanyName="";
					CompanyBase base=companyBaseDao.findById(finacingDemandInfo.getEnterpriseId());
					copanyName=base.getName();
					String investorName="";
					if(userInfos.get("level").equals("1")){//基金
						PlatformUser	platformUser2=serService.getUser(userInfos.get("userId"));
						investorName=platformUser2.getNickname();
						type=SRRPConstant.SMS_TYPE_ISINVESTFUNDPUSH;
						
					}else{//机构
						investorName=investor.getName();
						type=SRRPConstant.SMS_TYPE_ISINVESTPUSH;
					}
					//关注后-通知企业是否披露信息给基金
					long sid =System.currentTimeMillis() / 10000+random.nextInt(999999)%900000+100000;
					String status1=sendSmService.AnswerMessge(type,copanyName,investorName ,String.valueOf(sid),"",phone);
					sendSmService.SaveSendLog("00",orgNo,uuid,type,phone,name,status1,String.valueOf(sid));
				}
				
			} else {
				// 若同一机构不同用户操作同一需求，则之前事件进历史。
				if (!userName.equals(finacingEvent.getOperuser())) {
					FinacingEventHis eventHis = new FinacingEventHis();
					BeanUtils.copyProperties(eventHis, finacingEvent);
					finacingEventService.saveFinacingEventHis(eventHis);
				}
				if(!finacingEvent.getPublishFlag().equals("0")&&finacingDemandInfo.getOpen().equals("0")){
					if(userInfos.get("level").equals("0")){//机构用户
						List<FinacingEvent> orgEvents = finacingEventService.geteventByInfoidAndOrgId(infoId, orgNo);
						for(FinacingEvent event:orgEvents ){
							if(event.getPublishFlag().equals("0")&&event.getInvestLevel().equals("1")){
								finacingEvent.setPublishFlag("0");
								isSendSMS=true;//如果已经披露下机构的基金，不需要披露给机构，并且不需要发送短信
							}
						}
					}
					if(!isSendSMS){
						// 获取UUID并转化为String对象
						//根据机构的id获取机构信息，查询主机构是否有融资事件，如果有的话，把主机构的披露信息复制，如果没有的话，
						Investor investor= investorService.findInverstorById(orgNo);
						String name=investor.getCertno();
						String type="";
						String phone=finacingDemandInfo.getRelPhone();
						String copanyName="";
						CompanyBase base=companyBaseDao.findById(finacingDemandInfo.getEnterpriseId());
						copanyName=base.getName();
						String investorName="";
						if(userInfos.get("level").equals("1")){//基金
							PlatformUser	platformUser2=serService.getUser(userInfos.get("userId"));
							investorName=platformUser2.getNickname();
							type=SRRPConstant.SMS_TYPE_ISINVESTFUNDPUSH;
							
						}else{//机构
							investorName=investor.getName();
							type=SRRPConstant.SMS_TYPE_ISINVESTPUSH;
						}
						//关注后-通知企业是否披露信息给基金
						long sid =System.currentTimeMillis() / 10000+random.nextInt(999999)%900000+100000;
						String status1=sendSmService.AnswerMessge(type,copanyName,investorName ,String.valueOf(sid),"",phone);
						sendSmService.SaveSendLog("00",orgNo,finacingEvent.getEventId(),type,phone,name,status1,String.valueOf(sid));
					
					}
					}
			}

			// 获取当前时间 当做操作时间
			Date day = new Date();
			String nowData = df.format(day);
			finacingEvent.setOperdate(df.parse(nowData));
			finacingEvent.setOperuser(userName);

			// step 3 切换融资进度表状态
			FinacingRecord finacingRecord = new FinacingRecord();
			finacingRecord.setEventId(finacingEvent.getEventId());
			finacingRecord.setInfoId(infoId);
			finacingRecord.setEnterpriseid(finacingDemandInfo.getEnterpriseId());
			finacingRecord.setInvestorgid(orgNo);

			// 融资记录表操作内容
			String operContent = "";
			// 投融事件状态
			String eventStatus = "";
			// 需求状态
			String demandStatus = "";
			switch (operType) {
			case "focus": // 首待-关注
				eventStatus = SRRPConstant.FINANCPHASE11;
				operContent = "【" + userName + "】" + "关注了该需求";
				demandStatus = SRRPConstant.DEMANDSTATUS02;
				break;
			case "ignore":// 首待-忽略
				eventStatus = SRRPConstant.FINANCPHASE12;
				operContent = "【" + userName + "】" + "忽略了该需求";
				demandStatus = SRRPConstant.DEMANDSTATUS01;
				break;
			case "talks":// 启动约谈
				eventStatus = SRRPConstant.FINANCPHASE21;
				operContent = "【" + userName + "】" + "启动约谈了该需求";
				demandStatus = SRRPConstant.DEMANDSTATUS02;
				break;
			case "abolish":// 取消关注
				eventStatus = SRRPConstant.FINANCPHASE22;
				operContent = "【" + userName + "】" + "取消关注该需求";
				demandStatus = SRRPConstant.DEMANDSTATUS02;
				break;
			case "investor":// 投资
				eventStatus = SRRPConstant.FINANCPHASE41;
				operContent = "【" + userName + "】" + "投资了该需求";
				demandStatus = SRRPConstant.DEMANDSTATUS03;
				break;
			case "refused":// 拒投
				eventStatus = SRRPConstant.FINANCPHASE32;
				operContent = "【" + userName + "】" + "拒绝投资";
				demandStatus = SRRPConstant.DEMANDSTATUS02;
				break;
			case "loan":// 放款
				eventStatus = SRRPConstant.FINANCPHASE42;
				operContent = "【" + userName + "】" + "出资";
				demandStatus = SRRPConstant.DEMANDSTATUS03;
				break;
			default:
				eventStatus = SRRPConstant.FINANCPHASE11;
				operContent = "【" + userName + "】" + "关注了该需求";
				break;
			}
			finacingRecord.setOpertype(eventStatus);
			finacingRecord.setOpercontent(operContent);
			finacingRecord.setStatus(demandStatus);
			finacingRecord.setOperuser(userName);
			finacingRecord.setOperdate(df.parse(nowData));
			// 更新投融事件
			finacingEvent.setStatus(eventStatus);
			finacingEventService.saveEvent(finacingEvent);
			// 添加一条事件操作记录
			demandInfoService.saveRecord(finacingRecord);
			// 更新项目需求调用一个公共方法
			finacingDemandService.saveFinacingDemand(finacingDemandInfo);
			finacingEventService.updateDemadStatus(infoId);
			this.writeJson(page, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}

	/**
	 * 定向投递的投资或放款的弹出框
	 * 
	 * @Title: investAmountInfo
	 * @param request
	 * @param response
	 * @return String 返回类型
	 */
	@RequestMapping(value = "/investAmountInfo")
	public String investAmountInfo(HttpServletRequest request, HttpServletResponse response) {
		String status = request.getParameter("status");
		String multi = request.getParameter("multi");
		String eventId = request.getParameter("eventId");
		String infoId = request.getParameter("infoId");
		String operType = request.getParameter("operType");
		// 获取机构类型字典值集合
		List<DD> ddPublishtype = RedisGetValue.getDataList(SRRPConstant.DD_PUBLISHTYPE);
		List<DD> ddCurrency = RedisGetValue.getDataList(SRRPConstant.DD_CURRENCY);
		request.setAttribute("ddCurrency", ddCurrency);
		request.setAttribute("ddPublishtype", ddPublishtype);
		request.setAttribute("infoId", infoId);
		request.setAttribute("eventId", eventId);
		request.setAttribute("status", status);
		request.setAttribute("multi", multi);
		request.setAttribute("operType", operType);
		return "WEB-INF/views/inverstorg/invest";
	}

	/**
	 * 投资、放款、信息披露操作
	 * 
	 * @Title: updateFinacingEvent
	 * @param request
	 * @param response
	 * @return void 返回类型
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/updateFinacingEvent")
	public void updateFinacingEvent(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String investorId = userInfos.get(SRRPConstant.LOGINORGNO);
			String userName = userInfos.get(SRRPConstant.LOGINUSERNAME);

			String updateFinacingEvent = request.getParameter("updateFinacingEvent");

			QueryCondition queryCondition = new QueryCondition();
			queryCondition.setInvestorId(investorId);
			if (updateFinacingEvent != null && !"".equals(updateFinacingEvent) && !"\"\"".equals(updateFinacingEvent)) {
				queryCondition = (QueryCondition) JSON.parseObject(updateFinacingEvent, QueryCondition.class);
			}
			String operType = queryCondition.getOperType();
			FinacingDemandInfo finacingDemandInfo = demandInfoService.getFinacingInfo(queryCondition.getInfoId());
			int updateFlag = finacingDemandInfo.getUpdateFlag();
			finacingDemandInfo.setUpdateFlag(updateFlag + 1);
			// // step 1 投融事件
			FinacingEvent finacingEvent = finacingEventService.findFinacingEventById(queryCondition.getEventId());
			String investorName = investorService.findInverstorById(investorId).getName();
			// 融资记录表操作内容
			String operContent = "";
			String recordOperType = "";
			String recordStatus = "";
			switch (operType) {

			case "investor": // 投资
				finacingEvent.setStatus(SRRPConstant.FINANCPHASE41);
				Double sumAmountD=Math.round(Double.valueOf(queryCondition.getAmount())*1000000)/100000000.00000000;
				String sumAmountS="";
				if(sumAmountD.intValue()-sumAmountD==0){//判断是否符合取整条件
					sumAmountS=	String.valueOf(sumAmountD.intValue());
				}else{
					sumAmountS=String.valueOf(sumAmountD);
				}
				
				
				
				// 获取当前时间 当做操作时间
				Date day = new Date();
				String nowData = df.format(day);
				finacingEvent.setOperdate(df.parse(nowData));
				finacingEvent.setOperuser(userName);
				recordOperType = SRRPConstant.FINANCPHASE41;
				recordStatus = SRRPConstant.DEMANDSTATUS03;
				finacingEvent.setAmount(queryCondition.getAmount());
				finacingEvent.setCurrency(queryCondition.getCurrency());
				// ------------------------------计划整改--------------------------//
				if (SRRPConstant.USD_KEY.equals(queryCondition.getCurrency())) {
					finacingEvent.setExchangeRatio(getUSDToCNY(sumAmountD)+"");
				} else if (SRRPConstant.CNY_KEY.equals(queryCondition.getCurrency())) {
					finacingEvent.setExchangeRatio(sumAmountS);
				}
				CompanyBaseSupplement supplement = companyInfoService
						.getCompanyBaseSupplement(finacingEvent.getEnterpriseId());
				if (StringUtils.isNotEmpty(supplement.getIndustry())) {
					String industryStr = supplement.getIndustry();// 获取数据库中行业的展示
					if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
						supplement.setIndustry2(supplement.getIndustry());
						supplement.setIndustryStr(supplement.getIndustry2Dicname());
					} else {// 如果是一级的行业显示以及行业
						supplement.setIndustry(supplement.getIndustry());
						supplement.setIndustryStr(supplement.getIndustryDicname());
					}
				}
				String currencyStr = "";
				if (SRRPConstant.CNY_KEY.equals(finacingEvent.getCurrency())) {
					currencyStr = "元";
				} else if(SRRPConstant.USD_KEY.equals(finacingEvent.getCurrency())){
					currencyStr = "美元";
				}
				DecimalFormat df = new DecimalFormat("#######.######");
				//重新拼接项目名称
				String projectName = supplement.getIndustryStr() + "行业某公司"
						+ finacingDemandInfo.getFinacingTurnDicname() + "成功融资"
						+ finacingEvent.getAmount() + "万" + currencyStr;
				finacingEvent.setProjectName(projectName);
				// ------------------------------计划整改--------------------------//
				finacingEvent.setRatio(queryCondition.getRatio());
				finacingEventService.saveEvent(finacingEvent);
				
				// ------------------------------计划整改--------------------------//
				// ------------------------------计划整改--------------------------//
//				double sumAmountD=Double.valueOf(queryCondition.getAmount())/100;

			
				String sumAmount = "";
				if(SRRPConstant.USD_KEY.equals(queryCondition.getCurrency())){
//					sumAmount = df.format(Double.valueOf(queryCondition.getAmount())/100)+"百万美元";
					sumAmount = sumAmountS+"百万美元";

				}else if(SRRPConstant.CNY_KEY.equals(queryCondition.getCurrency())){
//					sumAmount = df.format(Double.valueOf(queryCondition.getAmount()) / 100)+"百万元";
					sumAmount = sumAmountS+"百万元";
					
//					Math.round(Double.valueOf(queryCondition.getAmount())*100000000)/100000000.00000000;
//					System.out.println(Math.round(Double.valueOf(queryCondition.getAmount())*100000000)/100000000.00000000);

				}
				
				// ------------------------------计划整改--------------------------//
				operContent = "【" + userName + "】" + "决定投资" + sumAmount;
				break;
			case "loan":// 放款
				DecimalFormat df1 = new DecimalFormat("#######.########");
				// ------------------------------计划整改--------------------------//
				String d = "";
				Double dD=Math.round(Double.valueOf(queryCondition.getAmount())*1000000)/100000000.00000000;
				String dS="";
				if(dD.intValue()-dD==0){//判断是否符合取整条件
					dS=	String.valueOf(dD.intValue());
				}else{
					dS=String.valueOf(dD);
				}
				
				
				if (SRRPConstant.USD_KEY.equals(queryCondition.getCurrency())) {
					d =dS+"百万美元";
				} else if (SRRPConstant.CNY_KEY.equals(queryCondition.getCurrency())) {
					d = dS+"百万元";
				}
				// ------------------------------计划整改--------------------------//
				operContent = "【" + userName + "】" + "出资" + d;
				// 累计放款金额
				double sumInvestorAmout = sumInvestorAmoutByevent(queryCondition.getEventId());
				String eventStatus = "";
				// 放款金额 > 累计放款金额
				//-------------------------------计划整改---------------------//
				// 放款金额 > 累计放款金额
				if ((SRRPConstant.CNY_KEY.equals(queryCondition.getCurrency())) &&(Double.valueOf(queryCondition.getAmount() )>= (Double.valueOf(finacingEvent.getAmount() ) - sumInvestorAmout*100))) {
					if(!(SRRPConstant.FINANCPHASE43.equals(finacingEvent.getStatus()) || SRRPConstant.FINANCPHASE45.equals(finacingEvent.getStatus()))){
						//如果当前状态为放款中已经披露状态则状态改为披露完成
						eventStatus = SRRPConstant.FINANCPHASE44;
					}else{
						eventStatus = finacingEvent.getStatus();
					}
				} else if((SRRPConstant.USD_KEY.equals(queryCondition.getCurrency())) &&(getUSDToCNY(Double.valueOf(queryCondition.getAmount())) >= (Double.valueOf(finacingEvent.getExchangeRatio()) - sumInvestorAmout)*100)){
					if(!(SRRPConstant.FINANCPHASE43.equals(finacingEvent.getStatus()) || SRRPConstant.FINANCPHASE45.equals(finacingEvent.getStatus()))){
						eventStatus = SRRPConstant.FINANCPHASE44;
					}else{
						eventStatus = finacingEvent.getStatus();
					}
				}else{
					if(!(SRRPConstant.FINANCPHASE43.equals(finacingEvent.getStatus()) || SRRPConstant.FINANCPHASE45.equals(finacingEvent.getStatus()))){
						eventStatus = SRRPConstant.FINANCPHASE42;
					}else{
						eventStatus = finacingEvent.getStatus();
					}
					
				}
				//-------------------------------计划整改--------------------//
				recordOperType = eventStatus;
				recordStatus = SRRPConstant.DEMANDSTATUS03;
				finacingEventService.updateEventStatus(eventStatus, queryCondition.getEventId());
				// 添加一条放款信息
				InvestorLoan loan = new InvestorLoan();
				loan.setAmount(queryCondition.getAmount());
				loan.setCurrency(queryCondition.getCurrency());
				loan.setEventId(queryCondition.getEventId());
				loan.setOrgId(investorId);
				//-----------------------计划整改----------------------//
				if(SRRPConstant.USD_KEY.equals(queryCondition.getCurrency())){
					loan.setExchangeRatio(getUSDToCNY(dD)+"");
				}else if(SRRPConstant.CNY_KEY.equals(queryCondition.getCurrency())){
					loan.setExchangeRatio(dS);
				}
				//-----------------------计划整改----------------------//
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date loanTime = sdf.parse(queryCondition.getLoanTime());
				loan.setLoanDate(loanTime);
				loan.setEnterpriseId(finacingEvent.getEnterpriseId());
				loan.setOperUser(userName);
				loan.setFinacingEvent(finacingEvent);
				loan.setInfoId(queryCondition.getInfoId());
				loan.setOrgname(investorName);
				finacingEventService.addLoan(loan);
				break;
			case "push":// 信息披露
				operContent = "【" + userName + "】" + "进行了信息披露";
				// 累计放款金额
				sumInvestorAmout = sumInvestorAmout(finacingDemandInfo.getInfoId());
				eventStatus = "";
				// 放款金额 > 累计放款金额
				if (SRRPConstant.CNY_KEY.equals(finacingEvent.getCurrency()) && Double.valueOf(sumInvestorAmout)*100 >= Double.valueOf(finacingEvent.getAmount())) {
					eventStatus = SRRPConstant.FINANCPHASE45;
				}if (SRRPConstant.USD_KEY.equals(finacingEvent.getCurrency()) && Double.valueOf(sumInvestorAmout)*100 >= getUSDToCNY(Double.valueOf(finacingEvent.getAmount()))) {
					eventStatus = SRRPConstant.FINANCPHASE45;
				} else {
					eventStatus = SRRPConstant.FINANCPHASE43;
				}
				recordOperType = eventStatus;
				recordStatus = SRRPConstant.DEMANDSTATUS03;
				finacingEventService.updateEventStatus(eventStatus, queryCondition.getEventId());
				InvestorPublish publish = new InvestorPublish();
				publish.setEnterpriseId(finacingEvent.getEnterpriseId());
				publish.setPublishFileName(queryCondition.getPublishFileName());
				publish.setPublishFilePath(queryCondition.getPublishFilePath());
				publish.setEventId(queryCondition.getEventId());
				publish.setFinacingEvent(finacingEvent);
				publish.setInfoTitle(queryCondition.getInfoTitle());
				publish.setInfoType(queryCondition.getInfotype());
				publish.setOrgId(investorId);
				publish.setOperuser(userName);
				publish.setOrgName(investorName);
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				publish.setPublishTime(dateFormat.parse(dateFormat.format(now)));
				publish.setInfoContent(queryCondition.getInfoContent());
				finacingDemandService.saveFinacingDemand(finacingDemandInfo);
				finacingEventService.addPublish(publish);
				break;
			default:
				break;
			}
			// step 2 融资记录
			FinacingRecord finacingRecord = new FinacingRecord();
			finacingRecord.setEventId(queryCondition.getEventId());
			finacingRecord.setInfoId(queryCondition.getInfoId());
			finacingRecord.setEnterpriseid(finacingDemandInfo.getEnterpriseId());
			finacingRecord.setInvestorgid(investorId);
			finacingRecord.setOpertype(recordOperType);

			finacingRecord.setOpercontent(operContent);
			finacingRecord.setStatus(recordStatus);
			finacingRecord.setOperuser(userName);
			// 获取当前时间 当做操作时间
			Date day = new Date();
			String nowData = df.format(day);
			finacingRecord.setOperdate(df.parse(nowData));
			// 添加一条事件操作记录
			demandInfoService.saveRecord(finacingRecord);
			// step 3 更新需求表状态
			// 更新项目需求调用一个公共方法
			finacingDemandService.saveFinacingDemand(finacingDemandInfo);
			finacingEventService.updateDemadStatus(queryCondition.getInfoId());
			this.writeJson(userInfos, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
	}
	
	/**
	 * 校验出资金额与投资金额的大小的方法
	 * @param request
	 * @param response
	 * @return
	 */
   @RequestMapping(value = "/chechedUserLevel")
   public void chechedUserLevel(HttpServletRequest request, HttpServletResponse response){
	   //首先获取传递的参数
	   Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
	   // 未登录强制登录
	   if (userInfos.isEmpty()) {
		noLogin(request, response);
		}
	   Map map = new HashMap<String, Object>();
		String userLevel = userInfos.get("level");
		map.put("userLevel", userLevel);
		if("0".equals(userLevel)) {
			String orgNo= userInfos.get("orgNo");
			Object userCount = finacingDemandService.getInvestorUserCount(orgNo);
			map.put("userCount", userCount);
		}
		request.setAttribute("result",map);
		this.writeJson(map, request, response);
   }
	
	/**
	 * 校验出资金额与投资金额的大小的方法
	 * @param request
	 * @param response
	 * @return
	 */
   @RequestMapping(value = "/chechedAmount")
   public void chechedAmount(HttpServletRequest request, HttpServletResponse response){
	   //首先获取传递的参数
	   Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
		// 未登录强制登录
		if (userInfos.isEmpty()) {
			noLogin(request, response);
		}
		String investorId = userInfos.get(SRRPConstant.LOGINORGNO);
		String userName = userInfos.get(SRRPConstant.LOGINUSERNAME);

		String updateFinacingEvent = request.getParameter("updateFinacingEvent");

		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setInvestorId(investorId);
		if (updateFinacingEvent != null && !"".equals(updateFinacingEvent) && !"\"\"".equals(updateFinacingEvent)) {
			queryCondition = (QueryCondition) JSON.parseObject(updateFinacingEvent, QueryCondition.class);
		}
		String operType = queryCondition.getOperType();
		FinacingEvent finacingEvent = finacingEventService.findFinacingEventById(queryCondition.getEventId());
		//构造方法返回值
		String result = "true";
		switch (operType) {

		case "investor": // 投资
			
			break;
		case "loan":// 放款
			DecimalFormat df1 = new DecimalFormat("#######.########");
			// 累计放款金额
			Double sumInvestorAmout = sumInvestorAmoutByevent(queryCondition.getEventId());
			String eventStatus = "";
			if (SRRPConstant.CNY_KEY.equals(finacingEvent.getCurrency()) && Double.valueOf(sumInvestorAmout)*100 >= Double.valueOf(finacingEvent.getAmount())) {
				result="false";
			} else if((SRRPConstant.USD_KEY.equals(queryCondition.getCurrency())) &&(getUSDToCNY(Double.valueOf(queryCondition.getAmount())) >= (Double.valueOf(finacingEvent.getExchangeRatio()) - sumInvestorAmout)*100)){
				result="false";
			}else{
				result="true";
			}
			break;
		case "push":// 信息披露
			break;
		default:
			break;
		}
		request.setAttribute("result",result);
		this.writeJson(result, request, response);
   }
	 //获取投资总额
	private Double sumInvestorAmoutByevent(String eventId) {
		Double sumAmount = 0d;
		List<InvestorLoan> dataList = finacingEventService.findLoanInfoByEventId(eventId);
		if (dataList != null && dataList.size() > 0) {
			for (InvestorLoan investorLoan : dataList) {
				if(investorLoan.getExchangeRatio()!=null){
					sumAmount += Double.valueOf(investorLoan.getExchangeRatio());
				}
			}
		}
		return sumAmount;
	}
	//------------------------------计划修改-----------------------//
	  // 获取投资总额
	  private Double sumInvestorAmout(String infoId) {
			Double sumAmount = 0d;
			List<InvestorLoan> dataList = finacingEventService.findLoanInfoByInfoId(infoId);
			if (dataList != null && dataList.size() > 0) {
				for (InvestorLoan investorLoan : dataList) {
					if(investorLoan.getExchangeRatio()!=null){
						sumAmount += Double.valueOf(investorLoan.getExchangeRatio());
					}
					
				}
			}
			return sumAmount;
	   }

	// ------------------------------计划整改--------------------------//
	// 将美元兑换成人民币
	public double getUSDToCNY(Double sum) {
		double amountAfterExchange = 0 ;
		try {
			PlatformConfig sc = platformConfigService.getSystemConfigByName("rate");
			double doubleValue = new Double(sc.getConfigValue());
			amountAfterExchange = sum * doubleValue;
			return amountAfterExchange;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return amountAfterExchange;
	}

	private  String getRemainTime(Date opertime,String status){
		String remaintime="";
		Date now = new Date();//获取当前时间
		 long l=now.getTime()-opertime.getTime();       //获取时间差
		 long day=l/(24*60*60*1000);
		 long hour=(l/(60*60*1000)-day*24);
			if((0 <= day) && (day <= 19)){
				remaintime=(20-day)+"天";
			}else if(day==20){
				remaintime=(24-hour)+"小时";
			}
		return remaintime;
	}
	
	
	@RequestMapping(value = "/countFocus")
	public void countFocus(HttpServletRequest request, HttpServletResponse response) {
		try {
			//获取到登录用户的用户名
			//查询关注状态的需求
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			String userName = userInfos.get(SRRPConstant.LOGINUSERNAME);
			int count =finacingEventService.countFocusByUser(userName).intValue();
			List<FinacingEvent> finacingEvents =finacingEventService.findFocusByUser(userName);
			for (FinacingEvent finacingEvent : finacingEvents) {
				FinacingDemandInfo finacingDemandInfo = demandInfoService.getFinacingInfo(finacingEvent.getInfoId());
				if("1".equals(finacingDemandInfo.getRevokeFlag())){
					count -= 1;
				}
			}
			request.setAttribute("count",count);
			this.writeJson(count, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}

	}
	public  static void main(String  arg[]){
		  DecimalFormat de = new DecimalFormat("0.00000000");
		String pi = "1111111.11";
		double ll = 100;
		double parseDouble = Double.parseDouble(pi)/100;
		double floatOrDoubleV = Double.valueOf(pi);
		Double  aaa=Math.round(Double.parseDouble(pi)*1000000)/100000000.00000000;
		if(aaa.intValue()-aaa==0){//判断是否符合取整条件
			pi=	String.valueOf(aaa.intValue());
		}else{
			pi=String.valueOf(aaa);
		}

		
	}
	
}

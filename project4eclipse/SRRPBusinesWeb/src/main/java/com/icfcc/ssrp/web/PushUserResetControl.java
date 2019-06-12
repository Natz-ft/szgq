package com.icfcc.ssrp.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icfcc.SRRPDao.jpa.entity.SendSmsLog;
import com.icfcc.SRRPDao.jpa.entity.TempSendSms;
import com.icfcc.SRRPService.PlatformSystem.PlatformConfigService;
import com.icfcc.SRRPService.enterprise.FinacingEventService;
import com.icfcc.SRRPService.enterprise.SendSmService;
import com.icfcc.SRRPService.gataway.staticize.GataWayCompanyInfoService;
import com.icfcc.SRRPService.inverstorg.DemandInfoService;
import com.icfcc.SRRPService.inverstorg.FinacingDemandService;
import com.icfcc.SRRPService.util.SmsClientUtil;
import com.icfcc.credit.platform.qcloudsms.SmsStatusPullReplyResult;
import com.icfcc.credit.platform.qcloudsms.SmsStatusPuller;
import com.icfcc.credit.platform.qcloudsms.SmsStatusPullReplyResult.Reply;
import com.icfcc.credit.platform.util.SRRPConstant;

@RestController
public class PushUserResetControl extends SRRPBaseController {

	@Autowired
	private SendSmService sendSmService;
	@Autowired
	private  PlatformConfigService config;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd号");

	/**
	 * 
	 * @Title: smscallback @Description: TODO(用户收到短信后，回复短信，腾讯云短信服务可以通过回调业务
	 * url) @param @param request @param @param response @param @return
	 * 参数说明 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/sms/smscallback")
	public String smscallback() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			System.out.println("进入回执接口，接收用户回复的短信");
			List<TempSendSms> tempList01 = sendSmService.getTempSendSms(SRRPConstant.SMS_STATUS_SEND);
			int maxNum = 100;
//			SmsStatusPuller mspuller = new SmsStatusPuller(1400055022, "e86f0a2f85cd8c8577e8010a54d685d1");
			int appid = Integer.parseInt(config.getConfigValueByName("appid"));
		    String appkey = config.getConfigValueByName("appkey");
			SmsStatusPuller mspuller = new SmsStatusPuller(appid, appkey);
			SmsStatusPullReplyResult replyResult = mspuller.pullReply(maxNum);
			ArrayList<Reply> replyList1 = replyResult.getReplys();
			System.out.println("进入回执接口，接收用户回复的短信+replyList1" + replyList1.size());
			if (replyList1.size() > 0) {
				for (Reply reply : replyList1) {
					String content = reply.getText();
					System.out.println("打印回复内容=====================" + content);
					if (content.length() == 10) {
						String strend = content.substring(9, 10);
						String id = content.substring(0, 9);
						// 机构回复是否项目进度
						for (TempSendSms temp : tempList01) {
							SendSmsLog log = sendSmService.findByrssId(temp.getRssId());
							if (id.equals(temp.getSid())) {
								// 1.进行相应的业务操作 2.发送短信回复结果 3.记录日志 4.删除临时表
								log.setSendStatus(SRRPConstant.SMS_STATUS_ANSWER);
								log.setAnswer_content(strend);
								log.setAnswerDate(new java.util.Date(reply.getTime() * 1000));
								if(temp.getSendType().equals(SRRPConstant.SMS_TYPE_DEMANDISOPEN)){//企业回复 是否转公开
									if (strend.equals(SRRPConstant.SMS_ANSWER_1)) {//转为公开
										//检查需求是否有机构关注
										if(sendSmService.isFoucsDemandInfo(temp.getInfooreventId())){
											sendSmService.answerFincineDemandOpen(temp.getInfooreventId());//转公开
											sendSmService.deleteTempSendSmsBySid(temp.getTssId());
											sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWEROPEN,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());

										}else{
											System.out.println("撤回或已关注的不做操作===========");
											sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										}
									} else if (strend.equals(SRRPConstant.SMS_ANSWER_2)) {//不转公开
										sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERSUCESS,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
										sendSmService.deleteTempSendSmsBySid(temp.getTssId());
									}  else {//回复内容错误
										log.setSendStatus(SRRPConstant.SMS_STATUS_ERRORANSWER);
									}
								}else if(temp.getSendType().equals(SRRPConstant.SMS_TYPE_TALKEXPRICEONE)){//机构回复 回复融资进度 第一次
									if (strend.equals(SRRPConstant.SMS_ANSWER_1)) {//进行中
										if(sendSmService.isRevokeFlag(temp.getInfooreventId())){
											sendSmService.deleteTempSendSmsBySid(temp.getTssId());
											sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERINVEST1,temp.getName(),temp.getInfoName(),temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());

										}
									}else if (strend.equals(SRRPConstant.SMS_ANSWER_2)) {// 已投资
										if(sendSmService.isRevokeFlag(temp.getInfooreventId())){
											//sendSmService.deleteTempSendSmsBySid(temp.getTssId());
											sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERINVEST2,temp.getName(),temp.getInfoName(),temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
										}else{
											sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										}
										
									} else if (strend.equals(SRRPConstant.SMS_ANSWER_3)) {// 放弃投资
										if(sendSmService.isRevokeFlag(temp.getInfooreventId())){
											sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERINVEST3,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
										}else{
											sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										}

									} else {
										log.setSendStatus(SRRPConstant.SMS_STATUS_ERRORANSWER);
									}
								}else if(temp.getSendType().equals(SRRPConstant.SMS_TYPE_TALKEXPRICETWO)){//机构回复 回复融资进度 第二次
									if (strend.equals(SRRPConstant.SMS_ANSWER_1)) {//进行中
										if(sendSmService.isRevokeFlag(temp.getInfooreventId())){
											//sendSmService.deleteTempSendSmsBySid(temp.getTssId());
											sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERINVEST1,temp.getName(),temp.getInfoName() ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
										}else{
											sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										}
									} else if (strend.equals(SRRPConstant.SMS_ANSWER_2)) {// 已投资
										if(sendSmService.isRevokeFlag(temp.getInfooreventId())){
											//sendSmService.deleteTempSendSmsBySid(temp.getTssId());
											sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERINVEST2,temp.getName(),temp.getInfoName() ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
										}else{
											sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										}
									} else if (strend.equals(SRRPConstant.SMS_ANSWER_3)) {// 放弃投资
										//sendSmService.answerClose(temp.getInfooreventId());
										//发送短信通知恢复成功，回复结果次日生效。
										if(sendSmService.isRevokeFlag(temp.getInfooreventId())){
											sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERINVEST3,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
										}else{
											sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										}
										//sendSmService.deleteTempSendSmsBySid(temp.getTssId());
									} else {
										log.setSendStatus(SRRPConstant.SMS_STATUS_ERRORANSWER);
									}
								}else if(temp.getSendType().equals(SRRPConstant.SMS_TYPE_ISINVESTPUSH)){//是否披露该机构
									if (strend.equals(SRRPConstant.SMS_ANSWER_1)) {//愿意 披露给基金或机构
										sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										sendSmService.orgPublish(temp.getInfooreventId());
										sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERSUCESS,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
									} else if (strend.equals(SRRPConstant.SMS_ANSWER_2)) {//不愿意
										sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERSUCESS,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
									} else {
										log.setSendStatus(SRRPConstant.SMS_STATUS_ERRORANSWER);
									}
								}
								else if(temp.getSendType().equals(SRRPConstant.SMS_TYPE_ISINVESTFUNDPUSH)){//是否披露该基金
									if (strend.equals(SRRPConstant.SMS_ANSWER_1)) {//愿意 披露给基金或机构
										sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										sendSmService.orgFundPublish(temp.getInfooreventId());										sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERSUCESS,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
										sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERSUCESS,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
									} else if (strend.equals(SRRPConstant.SMS_ANSWER_3)) {//不愿意
										sendSmService.deleteTempSendSmsBySid(temp.getTssId());
										sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERSUCESS,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
									} else {
										log.setSendStatus(SRRPConstant.SMS_STATUS_ERRORANSWER);
									}
								}
								if(log.getSendStatus().equals(SRRPConstant.SMS_STATUS_ERRORANSWER)){
									sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERERROR,temp.getName(),"" ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
								}
								sendSmService.saveSendSmsLog(log);
							}
						}
					} else {// 回复格式错误
						sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_ANSWERERROR,"","" ,"","",reply.getMobile());
					}
				}
			}
			map.put("result", 0);
			map.put("errmsg", "OK");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("result", 1);
			map.put("errmsg", e.getMessage());
			e.printStackTrace();
		}
		
		net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);
		return json.toString();

	}

	@RequestMapping(value = "/sms/smscall")
	public void smscall(HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";

		try {
			List<TempSendSms> tempList = sendSmService.getTempSendSms(SRRPConstant.SMS_STATUS_WAITSEND);
			if (tempList != null) {
				for (TempSendSms temp : tempList) {
					SendSmsLog log = sendSmService.findByrssId(temp.getRssId());
					String result = "";
					result=sendSmService.AnswerMessge(temp.getSendType(),temp.getName(),temp.getInfoName() ,temp.getSid(),sdf.format(log.getOperdate()),temp.getMobile());
					log.setSendDate(new Date());
					temp.setSendTime(new Date());
					log.setSendStatus(result);
					temp.setSendStatus(result);
					sendSmService.saveSendSmsLog(log);
					sendSmService.saveTempSendSms(temp);
				}

			}
			// 验证码用随机数

			executeResult = SRRPConstant.EXECUTSUCC;
			map.put("code", executeResult);
			map.put("msg", msg);
		} catch (Exception e) {
			msg = e.getMessage();
			e.printStackTrace();
			map.put("code", executeResult);
			map.put("errmsg", e.getMessage());
		}

		
		this.writeJson(map, request, response);
	}
	 
	public static void main(String[] args) {
		int days = 90;
		System.out.println(days % 30 == 0);

	}

	//
	// //-------------------main test
	// restFull--------------------------------------------------------
	// public static void main(String arg[]) {
	// String result ="";
	// String url="http://localhost:8080/SRRPWS/cxf/RESTful";
	// System.out.println("调用信用报告接口:"+url);
	// WebClient client = WebClient.create(url);
	// ClientConfiguration config = WebClient.getConfig(client);
	// client.path("/webSzyhWSClient").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
	// //封装企业信息
	// CompanyInfoVo companyRestVo=new CompanyInfoVo();
	// companyRestVo.setCode("121");
	// companyRestVo.setName("AAA");
	// JSONObject json = JSONObject.fromObject(companyRestVo);
	// String companyInfoJsonStr = AESUtil.encrypt(json.toString(),
	// "E053040812ACB857");//加密
	// //传参
	// String typeStr = AESUtil.encrypt("01", "E053040812ACB857");//加密
	// client.replaceQueryParam("companyInfoJsonStr",
	// companyInfoJsonStr).replaceQueryParam("type", typeStr);
	// result =client.get(String.class);
	// result=AESUtil.decrypt(result, "E053040812ACB857");//解密
	// Map maps = (Map)JSON.parse(result); //转map
	// System.out.println("调用信用报告接口结束，返回结果:"+maps.get("validStatus"));
	//
	// }
}

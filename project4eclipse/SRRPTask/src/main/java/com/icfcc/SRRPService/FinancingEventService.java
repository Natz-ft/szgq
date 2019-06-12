/**
 * 
 */
package com.icfcc.SRRPService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.icfcc.SRRPDao.s.jpa.entity.portal.FinancingEventQuery;
import com.icfcc.SRRPDao.s.jpa.entity.portal.platformPortalEventQueryInvestor;
import com.icfcc.SRRPDao.s.jpa.repository.portal.FinancingEventQueryDao;
import com.icfcc.SRRPDao.s.jpa.repository.portal.platformPortalEventQueryInvestorDao;
import com.icfcc.SRRPDao.s1.jpa.entity.CompanyBase;
import com.icfcc.SRRPDao.s1.jpa.entity.CompanyBaseSupplement;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingDemandInfo;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingEvent;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingRecord;
import com.icfcc.SRRPDao.s1.jpa.entity.Investor;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformUser;
import com.icfcc.SRRPDao.s1.jpa.entity.SendSmsLog;
import com.icfcc.SRRPDao.s1.jpa.entity.TempSendSms;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyBaseDao;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyBaseSupplementDao;
import com.icfcc.SRRPDao.s1.jpa.repository.DemandNoAttentionDaoImp;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingEventDao;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingInfoDao;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingRecordDao;
import com.icfcc.SRRPDao.s1.jpa.repository.InvestorDao;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformUserDao;
import com.icfcc.SRRPDao.s1.jpa.repository.SendSmsLogDao;
import com.icfcc.SRRPDao.s1.jpa.repository.TempSendSmsDao;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.util.DigitFormatUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author lijj 首页 投融事件查询
 *
 */
@Service
public class FinancingEventService {

	@Autowired
	private FinancingEventQueryDao financingEventQueryDao;
	@Autowired
	private FinacingEventDao financingEventDao;
	@Autowired
	private platformPortalEventQueryInvestorDao ppqueryDao;
	@Autowired
	private FinacingInfoDao finacingInfoDao;
	@Autowired
	private CompanyBaseDao companyBaseDao;
	@Autowired
	private CompanyBaseSupplementDao companyBaseSupplementDao;
	@Autowired
	private InvestorDao investorDao;
	@Autowired
	private DemandNoAttentionDaoImp demandNoAttentionDaoImp;
	@Autowired
	private SendSmsLogDao sendSmsLogDao;
	@Autowired
	private TempSendSmsDao tempSendSmsDao;
	@Autowired
	private FinacingRecordDao finacingRecordDao;
	@Autowired
	private PlatformUserDao platformUserDao;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static int daysBetween(Date smdate, Date bdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
//		long minutes = (time2 - time1) / (1000 * 60);

		return Integer.parseInt(String.valueOf(between_days));
	}
	 public static boolean isSameDay(Date date1, Date date2) {
		 String time1 = new SimpleDateFormat("yyyy-MM-dd").format(date1);
		 String time2 = new SimpleDateFormat("yyyy-MM-dd").format(date2);
       
	        if(time1.equals(time2)) {
	            return true;
	        } else {
	        	return false;
	        }
	    }

	    
	/**
	 * 关注需求后有效期为20天，发送短信
	 * 
	 * @param day
	 */
	@Transactional(value = "transactionManager")
	public void findFinacingEventNoTalks(String day) {
		List<FinacingEvent> listOne = demandNoAttentionDaoImp.findFinacingEventNoTalks(day);
		Random random = new Random();
		try {
			if (listOne != null&&listOne.size()>0) {
				for (FinacingEvent event : listOne) {
					String phone = "";
					String name = "";
					if (event.getInvestLevel().equals("0")) {
						Investor investor = this.investorDao.findOne(event.getInvestorgId());
						phone = investor.getRelPhone();
						name = investor.getName();
					} else {
						PlatformUser PlatformUser = this.platformUserDao.findOne(event.getFoundId());
						phone = PlatformUser.getTelephone();
						name = PlatformUser.getNickname();
					}

					FinacingDemandInfo demand = finacingInfoDao.findByInfoId(event.getInfoId());
					if (demand.getRevokeFlag().equals("0")) {
						// 获取页面输入的联系人电话，调用发送短信的接口
						SendSmsLog log = sendSmsLogDao.findlogbyInfo(event.getEventId(),
								SRRPConstant.SMS_TYPE_FOCUSAFTEREXPRICE);
						long sid = System.currentTimeMillis() / 10000 + random.nextInt(999999) % 900000 + 100000;
						if (StringUtils.isEmpty(log)) {
							log = new SendSmsLog();
							log.setInfoId(event.getEventId());
							log.setEnterporinvestId(event.getInvestorgId());
							log.setMobile(phone);
							log.setOperdate(event.getOperdate());
							log.setSendType(SRRPConstant.SMS_TYPE_FOCUSAFTEREXPRICE);// 未关注发送短信
							log.setSendStatus(SRRPConstant.SMS_STATUS_WAITSEND);
							log.setSid(String.valueOf(sid));
							log = sendSmsLogDao.save(log);
							TempSendSms temp = new TempSendSms();
							temp.setSendType(SRRPConstant.SMS_TYPE_FOCUSAFTEREXPRICE);// 给企业发送短信
							temp.setEnterporinvestId(event.getInvestorgId());
							temp.setSid(String.valueOf(sid));
							temp.setInfooreventId(event.getEventId());
							temp.setTssId(String.valueOf(sid));
							temp.setRssId(log.getRssId());
							temp.setMobile(phone);
							temp.setName(name);
							temp.setInfoName(demand.getProjectName());
							temp.setSendStatus(SRRPConstant.SMS_STATUS_WAITSEND);// 未发送
							tempSendSmsDao.save(temp);
						}
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 关注需求后有效期为20天，需求取消关注
	 * 
	 * @param day
	 */
	@Transactional(value = "transactionManager")
	public void cancelFinacingEventNoTalks(String day) {
		List<FinacingEvent> listOne = demandNoAttentionDaoImp.findFinacingEventNoTalks(day);
		try {
			if (listOne != null&&listOne.size()>0) {
				for (FinacingEvent event : listOne) {
					FinacingDemandInfo finacingDemandInfo = finacingInfoDao.findByInfoId(event.getInfoId());
					FinacingRecord finacingRecord = new FinacingRecord();
					finacingRecord.setEventId(event.getEventId());
					finacingRecord.setInfoId(event.getInfoId());
					finacingRecord.setEnterpriseid(event.getEnterpriseId());
					finacingRecord.setInvestorgid(event.getInvestorgId());
					finacingRecord.setOpertype(SRRPConstant.FINANCPHASE22);
					String operContent = "";
					String userName = "";
					if (event.getInvestLevel().equals("0")) {
						Investor investor = this.investorDao.findOne(event.getInvestorgId());
						userName = investor.getCertno();
					} else {
						PlatformUser PlatformUser = this.platformUserDao.findOne(event.getFoundId());
						userName = PlatformUser.getUsername();
					}
					operContent = "由于该用户关注需求后20天内未操作,平台已自动取消关注";
					finacingRecord.setOpercontent(operContent);
					finacingRecord.setStatus(SRRPConstant.DEMANDSTATUS02);
					finacingRecord.setOperuser(event.getOperuser());
					finacingRecord.setOperdate(new Date());
					finacingRecordDao.save(finacingRecord);
					String nowData = df.format(new Date());
					event.setOperdate(df.parse(nowData));
					event.setOperuser(userName);
					event.setStatus(SRRPConstant.FINANCPHASE22);
					financingEventDao.save(event);
					int updateFlag = finacingDemandInfo.getUpdateFlag();
					finacingDemandInfo.setUpdateFlag(updateFlag + 1);
					finacingInfoDao.save(finacingDemandInfo);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param event
	 * @param rssId
	 * @param type
	 */
	@Transactional(value = "transactionManager1")
	public void sendSms(FinacingEvent event, String rssId, String type) {
		String phone = "";
		String name = "";
		Random random = new Random();
		long sid = System.currentTimeMillis() / 10000 + random.nextInt(999999) % 900000 + 100000;
		if (event.getInvestLevel().equals("0")) {
			Investor investor = this.investorDao.findOne(event.getInvestorgId());
			phone = investor.getRelPhone();
			name = investor.getName();
		} else {
			PlatformUser PlatformUser = this.platformUserDao.findOne(event.getFoundId());
			phone = PlatformUser.getTelephone();
			name = PlatformUser.getNickname();
		}
		TempSendSms temp = new TempSendSms();
		temp.setSendType(type);// 给企业发送短信
		temp.setEnterporinvestId(event.getInvestorgId());
		temp.setInfooreventId(event.getEventId());
		temp.setInfoName(event.getProjectName());
		temp.setSid(String.valueOf(sid));
		temp.setRssId(rssId);
		temp.setMobile(phone);
		temp.setName(name);
		temp.setSendStatus(SRRPConstant.SMS_STATUS_WAITSEND);
		tempSendSmsDao.save(temp);
	}
	@Transactional(value = "transactionManager1")
	public void answerClose(FinacingEvent event) {

		FinacingRecord finacingRecord = new FinacingRecord();
		finacingRecord.setEventId(event.getEventId());
		finacingRecord.setInfoId(event.getInfoId());
		finacingRecord.setEnterpriseid(event.getEnterpriseId());
		finacingRecord.setInvestorgid(event.getInvestorgId());
		finacingRecord.setOpertype(SRRPConstant.DEMANDSTATUS99);
		String operContent = "";
		if (event.getInvestLevel().equals("0")) {
			operContent = "由于该用户启动约谈后连续70天未更新项目进度,平台已自动终止业务";
		} else {
			operContent = "由于该用户启动约谈后连续70天未更新项目进度,平台已自动终止业务";
		}
		finacingRecord.setOpercontent(operContent);
		finacingRecord.setStatus(SRRPConstant.FINANCPHASE99);
		finacingRecord.setOperuser(event.getOperuser());
		finacingRecord.setOperdate(new Date());
		finacingRecordDao.save(finacingRecord);
		event.setStatus(SRRPConstant.FINANCPHASE99);
		financingEventDao.save(event);
		FinacingDemandInfo finacingDemandInfo = finacingInfoDao.findByInfoId(event.getInfoId());
		int updateFlag = finacingDemandInfo.getUpdateFlag();
		finacingDemandInfo.setUpdateFlag(updateFlag + 1);
		finacingInfoDao.save(finacingDemandInfo);
	}
	@Transactional(value = "transactionManager1")
    public void updateLog(SendSmsLog sendSmsLog,String type,String status){
    	sendSmsLog.setSendStatus(status);
		sendSmsLog.setSendType(type);
		sendSmsLogDao.save(sendSmsLog);
    }
	@Transactional(value = "transactionManager1")
    public void deleteLog(SendSmsLog sendSmsLog){
		sendSmsLogDao.delete(sendSmsLog.getRssId());
    }
	/**
	 * 
	 * @param status
	 * @param day
	 */
	@Transactional(value = "transactionManager1")
	public void queryFinancingNoInvest(String noInvestCount) {
		List<FinacingEvent> listOne = demandNoAttentionDaoImp.findFinacingEventNoInvest(noInvestCount);
		Random random = new Random();
		int noInvestDate = Integer.parseInt(noInvestCount);
		if (listOne != null&&listOne.size()>0) {
			for (FinacingEvent event : listOne) {
				FinacingDemandInfo demand = finacingInfoDao.findByInfoId(event.getInfoId());
				if (demand.getRevokeFlag().equals("0")) {
					// 检查第二次未回复
					SendSmsLog sendSmsLog = sendSmsLogDao.findlogbyInfo(event.getEventId(),
							SRRPConstant.SMS_TYPE_TALKEXPRICETWO);
					if(!StringUtils.isEmpty(sendSmsLog)){
						int day = daysBetween(sendSmsLog.getSendDate(), new Date());
						if (day == 10) {
							if (sendSmsLog.getSendStatus().equals(SRRPConstant.SMS_STATUS_SEND)
									|| sendSmsLog.getSendStatus().equals(SRRPConstant.SMS_STATUS_ERRORANSWER)) {
								if(!isSRRPUpdate( event, noInvestDate)){
									updateLog(sendSmsLog,SRRPConstant.SMS_TYPE_ANSWERGIVEUP,SRRPConstant.SMS_STATUS_NOANSWER );
									answerClose(event);
									deleteTempLog(sendSmsLog.getRssId(),SRRPConstant.SMS_TYPE_TALKEXPRICETWO);
								}else{
									deleteLog( sendSmsLog);
								}
							}
						}
					}
					//回复放弃投资
					SendSmsLog log1 = sendSmsLogDao.findlogbyInfo(event.getEventId());
					// 1.检查是否已经发送短息通知
					if (!StringUtils.isEmpty(log1)) {// 第一发送短息通知
						if(!isSRRPUpdate( event, noInvestDate)){
							// 第一次发送短信
								//如果是今天回复的短信，不处理，明天处理
								if(!isSameDay(log1.getAnswerDate(), new Date())){
										String content = log1.getAnswer_content();
										if (content.equals(SRRPConstant.SMS_ANSWER_3)) {// 已回复
											answerClose(event.getEventId());
											deleteTempLog(log1.getRssId());
											sendSms(event, log1.getRssId(), SRRPConstant.SMS_TYPE_ANSWERGIVEUP);
											updateLog(log1,SRRPConstant.SMS_TYPE_ANSWERGIVEUP,log1.getSendStatus());
										}
								} 
						}
						
					}
					//每隔30天发送短信
					int days = daysBetween(event.getOperdate(), new Date());
					
					SendSmsLog log = sendSmsLogDao.getlogbyInfo(event.getEventId());
					if ((days) % (noInvestDate) == 0) {// 每隔30天发送短信询问项目进度
						// 1.检查是否已经发送短息通知
						if (StringUtils.isEmpty(log)) {// 第一发送短息通知
							if (!isSRRPUpdate( event, noInvestDate)) {
								// 第一次发送短信
								String phone = "";
								long sid = System.currentTimeMillis() / 10000 + random.nextInt(999999) % 900000 + 100000;
								if (event.getInvestLevel().equals("0")) {
									Investor investor = this.investorDao.findOne(event.getInvestorgId());
									phone = investor.getRelPhone();
								} else {
									PlatformUser PlatformUser = this.platformUserDao.findOne(event.getFoundId());
									phone = PlatformUser.getTelephone();
								}
								log = new SendSmsLog();
								log.setInfoId(event.getEventId());
								log.setEnterporinvestId(event.getEnterpriseId());
								log.setMobile(phone);
								log.setOperdate(event.getOperdate());
								log.setSendType(SRRPConstant.SMS_TYPE_TALKEXPRICEONE);
								log.setSendStatus(SRRPConstant.SMS_STATUS_WAITSEND);
								log.setSid(String.valueOf(sid));
								log = sendSmsLogDao.save(log);
								sendSms(event, log.getRssId(), SRRPConstant.SMS_TYPE_TALKEXPRICEONE);
							}
						} else {// 以下判断是否需要发送短信通知
							if(log.getSendType().equals(SRRPConstant.SMS_TYPE_TALKEXPRICEONE)){
								if (log.getSendStatus().equals(SRRPConstant.SMS_STATUS_ANSWER)) {// 第一次已回复
									String content = log.getAnswer_content();
									if (content.equals(SRRPConstant.SMS_ANSWER_1)
											|| content.equals(SRRPConstant.SMS_ANSWER_2)) {
										if(!isSRRPUpdate( event, noInvestDate)){
											sendSms(event, log.getRssId(), SRRPConstant.SMS_TYPE_TALKEXPRICEONE);
											updateLog(log,SRRPConstant.SMS_TYPE_TALKEXPRICEONE,log.getSendStatus());
										}
									}
								} else if (log.getSendStatus().equals(SRRPConstant.SMS_STATUS_SEND)
										|| log.getSendStatus().equals(SRRPConstant.SMS_STATUS_ERRORANSWER)
										|| log.getSendStatus().equals(SRRPConstant.SMS_STATUS_PLATFORUPDATE)) {// 未回复
									// 第二次发送短信
									if(!isSRRPUpdate( event, noInvestDate)){
										 if(log.getSendStatus().equals(SRRPConstant.SMS_STATUS_PLATFORUPDATE)){
											sendSms(event, log.getRssId(), SRRPConstant.SMS_TYPE_TALKEXPRICEONE);
											updateLog(log,SRRPConstant.SMS_TYPE_TALKEXPRICEONE,log.getSendStatus());
										}else{
											sendSms(event, log.getRssId(), SRRPConstant.SMS_TYPE_TALKEXPRICETWO);
											updateLog(log,SRRPConstant.SMS_TYPE_TALKEXPRICETWO,log.getSendStatus());
										}
										
									}else{
										updateLog(log,log.getSendType(),SRRPConstant.SMS_STATUS_PLATFORUPDATE);
									}
									

								}
							}else if(log.getSendType().equals(SRRPConstant.SMS_TYPE_TALKEXPRICETWO)){
								
								if (log.getSendStatus().equals(SRRPConstant.SMS_STATUS_ANSWER)) {// 第二次已回复
									
										if(!isSRRPUpdate( event, noInvestDate)){//没有更新
											sendSms(event, log.getRssId(), SRRPConstant.SMS_TYPE_TALKEXPRICEONE);
											log.setAnswer_content("");
											updateLog(log,SRRPConstant.SMS_TYPE_TALKEXPRICEONE,log.getSendStatus());
										}else{
											updateLog(log,log.getSendType(),SRRPConstant.SMS_STATUS_PLATFORUPDATE);
										}
								} 
							}
								
						}
					}
					
				}
			}
		}
	}
    public boolean isSRRPUpdate(FinacingEvent event,int noInvestDate){
    	if (event.getScheDate() == null) {
    		return false;
    	}else{
    		int ss = daysBetween(event.getScheDate(), new Date());
			if (ss > noInvestDate) {
				return false;
			}
    	}
    	return true;
    }
	
    @Transactional(value = "transactionManager1")
	public void deleteTempLog(String rssId) {
    	tempSendSmsDao.DeleteByLogId(rssId);
    }
    @Transactional(value = "transactionManager1")
	public void deleteTempLog(String rssId,String  type) {
    	tempSendSmsDao.DeleteByLogId(rssId,type);
    }
	public static void main(String srg[]) {
		int aa = daysBetween(null, new Date());
		System.out.println("========1========" + aa);

	}
    @Transactional(value = "transactionManager1")
	public void answerClose(String eventId) {
		FinacingEvent event = financingEventDao.findOne(eventId);
		if (event != null) {
			event.setStatus(SRRPConstant.FINANCPHASE32);
			event.setOperdate(new Date());
			financingEventDao.save(event);
			String name = "";
			if (event.getInvestLevel().equals("0")) {
				Investor investor = this.investorDao.findOne(event.getInvestorgId());
				name = investor.getCertno();
			} else {
				PlatformUser PlatformUser = this.platformUserDao.findOne(event.getFoundId());
				name = PlatformUser.getUsername();
			}
			FinacingRecord finacingRecord = new FinacingRecord();
			finacingRecord.setEventId(event.getEventId());
			finacingRecord.setInfoId(event.getInfoId());
			finacingRecord.setEnterpriseid(event.getEnterpriseId());
			finacingRecord.setInvestorgid(event.getInvestorgId());
			finacingRecord.setOpertype(SRRPConstant.FINANCPHASE32);
			String operContent = "【" + name + "】" + "拒绝投资";
			;
			finacingRecord.setOpercontent(operContent);
			finacingRecord.setStatus(SRRPConstant.DEMANDSTATUS02);
			finacingRecord.setOperuser(event.getOperuser());
			finacingRecord.setOperdate(new Date());
			finacingRecordDao.save(finacingRecord);
			FinacingDemandInfo finacingDemandInfo = finacingInfoDao.findByInfoId(event.getInfoId());
			int updateFlag = finacingDemandInfo.getUpdateFlag();
			finacingDemandInfo.setUpdateFlag(updateFlag + 1);
			finacingInfoDao.save(finacingDemandInfo);
		}
	}

	/**
	 * 获取最新的n个融资事件
	 * 
	 * @param n
	 * @return
	 */
	@Transactional(value = "transactionManager1")
	public HashMap<Object, Object> generateLatest(int n) {
		Specification<FinacingEvent> spec = new Specification<FinacingEvent>() {
			@Override
			public Predicate toPredicate(Root<FinacingEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> statusP = root.get("status");
				Predicate p = null;
				p = cb.conjunction();
				String[] values = { SRRPConstant.FINANCPHASE41, SRRPConstant.FINANCPHASE42, SRRPConstant.FINANCPHASE43,
						SRRPConstant.FINANCPHASE44, SRRPConstant.FINANCPHASE45 };
				p = cb.and(p, statusP.in(values));
				return p;
			}
		};
		Sort sort = new Sort(Direction.DESC, "operdate");
		Pageable page = new PageRequest(0, n, sort);
		Page<FinacingEvent> tres = financingEventDao.findAll(spec, page);
		List<FinancingEventQuery> res = new ArrayList<FinancingEventQuery>();
		List<platformPortalEventQueryInvestor> res1 = new ArrayList<platformPortalEventQueryInvestor>();
		for (FinacingEvent o : tres.getContent()) {
			FinacingDemandInfo financingInfo = this.finacingInfoDao.findByOpenDemand(o.getInfoId());
			if (financingInfo != null) {
				CompanyBase companyBase = this.companyBaseDao.findById(o.getEnterpriseId());
				CompanyBaseSupplement companyBaseS = this.companyBaseSupplementDao.findById(o.getEnterpriseId());
				Investor investor = this.investorDao.findOne(o.getInvestorgId());
				FinancingEventQuery oo = new FinancingEventQuery();
				oo.setEventId(o.getEventId());
				oo.setOperDate(o.getOperdate());
				oo.setProjectName(o.getProjectName());
				oo.setInfoId(o.getInfoId());
				if (o.getExchangeRatio() == null) {
					oo.setAmount(0.00);
				} else {
					oo.setAmount(o.getExchangeRatio());

				}
				if (companyBase != null) {
					oo.setEnterpriseName(companyBase.getName());
					oo.setRearea(companyBase.getRearea());
				}
				if (companyBaseS != null) {
					oo.setFinanceStage(companyBaseS.getFinancingphase());
					oo.setIndustry(companyBaseS.getIndustry());
				}
				if (investor != null) {
					oo.setInvestorName(investor.getName());

				}
				List<FinacingEvent> finacingEvents = this.financingEventDao.findAllByInfoId(o.getInfoId());
				for (FinacingEvent envet : finacingEvents) {
					platformPortalEventQueryInvestor peqi = new platformPortalEventQueryInvestor();
					Investor invest = this.investorDao.findOne(envet.getInvestorgId());
					peqi.setInvestorName(invest.getName());
					peqi.setEventId(o.getEventId());
					peqi.setInfoId(envet.getInfoId());
					peqi.setOrgType(invest.getOrgType());
					peqi.setReserve(invest.getCapitalType() == null ? "" : invest.getCapitalType());
					if (envet.getExchangeRatio() == null) {
						peqi.setAmount(0.00);
					} else {
						peqi.setAmount(envet.getExchangeRatio());

					}
					res1.add(peqi);
				}
				oo.setDescription(financingInfo.getDescription());
				oo.setFinancingTurn(financingInfo.getFinacingTurn());
				oo.setOpen(financingInfo.getOpen());
				String tmpScale = "";
//				if ("0".equals(financingInfo.getSell())) {
					if (financingInfo.getScaleMin() == null || "".equals(financingInfo.getScaleMin())) {
						tmpScale = "0.00%";
					} else {
						tmpScale = DigitFormatUtil.digitFormat(financingInfo.getScaleMin()) + "%";
					}
					if (financingInfo.getScaleMax() == null || "".equals(financingInfo.getScaleMax())) {
						tmpScale += "~" + "0.00%";
					} else {
						tmpScale += "~" + DigitFormatUtil.digitFormat(financingInfo.getScaleMax()) + "%";
					}
//				} else {
//					tmpScale = "N/A";
//				}
				oo.setScale(financingInfo.getSell()+","+tmpScale);
				res.add(oo);

			}

		}
		HashMap<Object, Object> map = Maps.newHashMap();
		map.put("event", res);
		map.put("investor", res1);
		return map;
	}

	/**
	 * 门户存储融资事件
	 * 
	 * @param res
	 */
	@Transactional(value = "transactionManager")
	public void saveLatest(List<FinancingEventQuery> res) {
		this.financingEventQueryDao.deleteAll();
		this.financingEventQueryDao.save(res);
	}

	/**
	 * 门户存储融资事件
	 * 
	 * @param res
	 */
	@Transactional(value = "transactionManager")
	public void saveLatestEventInvestor(List<platformPortalEventQueryInvestor> res) {
		this.ppqueryDao.deleteAll();
		this.ppqueryDao.save(res);
	}

	@Transactional(value = "transactionManager")
	public void deleteLatest() {
		this.ppqueryDao.deleteAll();
	}

	/**
	 * 完成移动投资事件到门户
	 * 
	 * @param n
	 */
	@SuppressWarnings("unchecked")
	public void extractFinancingEvent(int n) {
		HashMap<Object, Object> map = this.generateLatest(n);
		List<FinancingEventQuery> res1 = (List<FinancingEventQuery>) map.get("event");
		List<platformPortalEventQueryInvestor> res2 = (List<platformPortalEventQueryInvestor>) map.get("investor");
		this.saveLatest(res1);
		this.saveLatestEventInvestor(res2);
		/*
		 * List<platformPortalEventQueryInvestor> resInv =
		 * this.generateLatestEventInvestor(n);
		 * this.saveLatestEventInvestor(resInv);
		 */
	}

}

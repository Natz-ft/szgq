/**
 * 
 */
package com.icfcc.SRRPService;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.alibaba.fastjson.JSONArray;
import com.icfcc.SRRPDao.s.jpa.entity.portal.FinancingInfoDetail;
import com.icfcc.SRRPDao.s.jpa.entity.portal.FinancingInfoQuery;
import com.icfcc.SRRPDao.s.jpa.entity.portal.PlatformPortalDemand;
import com.icfcc.SRRPDao.s.jpa.repository.portal.FinancingInfoQueryDao;
import com.icfcc.SRRPDao.s1.jpa.entity.CompanyBase;
import com.icfcc.SRRPDao.s1.jpa.entity.CompanyBaseSupplement;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingDemandInfo;
import com.icfcc.SRRPDao.s1.jpa.entity.FinacingEvent;
import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;
import com.icfcc.SRRPDao.s1.jpa.entity.SendSmsLog;
import com.icfcc.SRRPDao.s1.jpa.entity.TempSendSms;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyBaseDao;
import com.icfcc.SRRPDao.s1.jpa.repository.CompanyBaseSupplementDao;
import com.icfcc.SRRPDao.s1.jpa.repository.DemandNoAttentionDaoImp;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingEventDao;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingInfoDao;
import com.icfcc.SRRPDao.s1.jpa.repository.FinacingRecordDao;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformConfigDao;
import com.icfcc.SRRPDao.s1.jpa.repository.SendSmsLogDao;
import com.icfcc.SRRPDao.s1.jpa.repository.TempSendSmsDao;
import com.icfcc.SRRPService.redis.RedisUtil;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.util.DigitFormatUtil;

import redis.clients.jedis.Jedis;

/**
 * @author lijj 门户-融资需求查询
 */
@Service
@Transactional(value = "transactionManager")
public class FinancingInfoDetailService   {
	@Autowired
	private PlatformConfigDao systemConfigDao;
	@Autowired
	private FinancingInfoQueryDao finacingInfoQueryDao;
	@Autowired
	private  DemandNoAttentionDaoImp demandNoAttentionDaoImp;
	
	@Autowired
	private  SendSmsLogDao sendSmsLogDao;
	@Autowired
	private TempSendSmsDao tempSendSmsDao;
	@Autowired
	private FinacingInfoDao finacingInfoDao;
	@Autowired
	private FinacingEventDao finacingEventDao;
	@Autowired
	private CompanyBaseDao companyBaseDao;
	@Autowired
	private CompanyBaseSupplementDao companyBaseSupplementDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private FinacingRecordDao finacingRecordDao;
	private static Calendar startDate = Calendar.getInstance();
	private static Calendar endDate = Calendar.getInstance();

	public static final String STATUS_CLOSE = "99";
	public static final String open = "0";
	public static final String STATUS_DRAFT = "00";
	public static final String STATUS_SUCESS = "03";
	/**
	 * 
	* @Title: saveLatest 
	* @Description: TODO(查询发布后的18天内无机构关注的定向投递的需求) 
	* @param @param res  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	@Transactional(value = "transactionManager1")
	public void queryFinancingNoFocus(String noFocusCount)  {
		List<FinacingDemandInfo> list=	demandNoAttentionDaoImp.findFinacingDemandNoAttention(noFocusCount);
		Random random = new Random();
		if(list!=null&&list.size()>0){
			for(FinacingDemandInfo demand:list){
				SendSmsLog log=sendSmsLogDao.findlogbyInfo(demand.getInfoId(),SRRPConstant.SMS_TYPE_DEMANDISOPEN);
				CompanyBase base=companyBaseDao.findById(demand.getEnterpriseId());
                long sid =System.currentTimeMillis() / 10000+random.nextInt(999999)%900000+100000;
				if(StringUtils.isEmpty(log)){
					log=new SendSmsLog();
					log.setInfoId(demand.getInfoId());
					log.setEnterporinvestId(demand.getEnterpriseId());
					log.setMobile(demand.getRelPhone());
					log.setOperdate(demand.getOperdate());
					log.setSendType(SRRPConstant.SMS_TYPE_DEMANDISOPEN);//未关注发送短信
   	   			    log.setSendStatus(SRRPConstant.SMS_STATUS_WAITSEND);
					log.setSid(String.valueOf(sid));
					log=sendSmsLogDao.save(log);
					 TempSendSms temp= new TempSendSms();
					 temp.setSendType(SRRPConstant.SMS_TYPE_DEMANDISOPEN);//给企业发送短信
						temp.setEnterporinvestId(base.getEnterpriseId());
						temp.setSid(String.valueOf(sid));
						temp.setInfooreventId(demand.getInfoId());
						temp.setTssId(String.valueOf(sid));
						temp.setRssId(log.getRssId());
						temp.setMobile(demand.getRelPhone());
						temp.setName(base.getName());
						temp.setSendStatus(SRRPConstant.SMS_STATUS_WAITSEND);
						tempSendSmsDao.save(temp);
				}
			}
		}
		
	}
	/**
	 * 处理企业回复是否转公开的需求
	 * @param day
	 */
	@Transactional(value = "transactionManager1")
	public void queryNoAnswer(String day) {
		List<FinacingDemandInfo> list=	demandNoAttentionDaoImp.findFinacingDemandNoAttention(day);
		if(list!=null&&list.size()>0){
			for(FinacingDemandInfo demand:list){
				SendSmsLog log=sendSmsLogDao.findlogbyInfo(demand.getInfoId(),SRRPConstant.SMS_TYPE_DEMANDISOPEN);
        	   if(!StringUtils.isEmpty(log)){//未回复 关闭需求
        		   if (log.getSendStatus().equals(SRRPConstant.SMS_STATUS_ANSWER)) {// 第一次已回复
						String content = log.getAnswer_content();
						if (content.equals(SRRPConstant.SMS_ANSWER_2)) {
							log.setSendStatus(SRRPConstant.SMS_STATUS_ANSWER);
	  				         demand.setStatus(SRRPConstant.DEMANDSTATUS99);//关闭需求
	  			        	 try {
								demand.setCloseReason("需求到期平台已自动关闭需求");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	  			        	int upflag= demand.getUpdateFlag();
	  				    	upflag=upflag+1;
	  				    	demand.setUpdateFlag(upflag);
					    	demand.setOperdate(new Date());
	  			        	 finacingInfoDao.save(demand);//
	  			        	 finacingInfoDao.deleteFinacingEventByinfoId(demand.getInfoId());//删除融资事件信息
	  			        	 finacingRecordDao.deleteFinacingRecord(demand.getInfoId());//删除记录表
						}
					} else if (log.getSendStatus().equals(SRRPConstant.SMS_STATUS_SEND)
							|| log.getSendStatus().equals(SRRPConstant.SMS_STATUS_ERRORANSWER) || log.getSendStatus().equals(SRRPConstant.SMS_STATUS_SENDERROR)) {// 未回复
						log.setSendStatus(SRRPConstant.SMS_STATUS_NOANSWER);
				         demand.setStatus(SRRPConstant.DEMANDSTATUS99);//关闭需求
			        	 demand.setOperdate(new Date());
			        	 try {
								demand.setCloseReason("需求到期平台已自动关闭需求");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	 int upflag= demand.getUpdateFlag();
					    	upflag=upflag+1;
					    	demand.setUpdateFlag(upflag);
					    	demand.setOperdate(new Date());
			        	 finacingInfoDao.save(demand);//
			        	 finacingInfoDao.deleteFinacingEventByinfoId(demand.getInfoId());//删除融资事件信息
			        	 finacingRecordDao.deleteFinacingRecord(demand.getInfoId());//删除记录表
					}
        		   
        		   sendSmsLogDao.save(log);
        		  
        	   }

			}

		}
	}

	/**
	 * 获得最新n个融资需求
	 * 
	 * @param n
	 * @return
	 * @throws Exception 
	 */
	@Transactional(value = "transactionManager1")
	public List<FinancingInfoQuery> generateLatest(int n) throws Exception {
		List<FinancingInfoQuery> extraRes = new ArrayList<FinancingInfoQuery>();
			// DicWord dicWord = DicWord.getDicWord();

			Specification<FinacingDemandInfo> spec = new Specification<FinacingDemandInfo>() {
				@Override
				public Predicate toPredicate(Root<FinacingDemandInfo> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					Path<String> statusP = root.get("status");
					Path<String> openP = root.get("open");
					Path<String> revokeFlag = root.get("revokeFlag");

					Predicate p = null;
					p = cb.conjunction();
					p = cb.and(p, cb.notEqual(statusP, STATUS_CLOSE));
					p = cb.and(p, cb.notEqual(statusP, STATUS_DRAFT));
//					p = cb.and(p, cb.notEqual(statusP, STATUS_SUCESS));
					p = cb.and(p, cb.equal(openP, open));
					p = cb.and(p,cb.equal(revokeFlag, "0"));


					return p;
				}
			};

			Sort sort = new Sort(Direction.DESC, "operdate");
			Pageable page = new PageRequest(0, n, sort);
			Page<FinacingDemandInfo> res = this.finacingInfoDao.findAll(spec, page);
			Map<String, String> ddEntPeriodMap = reverseDD(SRRPConstant.DD_ENTPERIOD);
			Map<String, String> ddFinacingMoneyMap = reverseDD(SRRPConstant.DD_FINACINGMONEY);
			for (FinacingDemandInfo info :res.getContent()) {
			 if(isFinacingEvent(info.getStatus(),info.getInfoId())){
				 CompanyBase company = companyBaseDao.findById(info.getEnterpriseId());
					CompanyBaseSupplement companyExt = this.companyBaseSupplementDao.findById(info.getEnterpriseId());
					FinancingInfoQuery d = new FinancingInfoQuery();
					d.setAmount(info.getAmountMax());
					String tmpAmount = "";
					if (info.getAmountMin() == null || "".equals(info.getAmountMin())) {
						tmpAmount = "0M";
					} else {
						tmpAmount = DigitFormatUtil.digitFormat(info.getAmountMin()).toString()+"M";
					}
					if (info.getAmountMax() == null || "".equals(info.getAmountMax())) {
						tmpAmount += "M~" + "0M";
					} else {
						tmpAmount += "~"
								+ DigitFormatUtil.digitFormat(info.getAmountMax()).toString()+"M";
					}
					d.setFinancingPurposes(tmpAmount);
					// d.setAmountRange(dicWord.getDicByType(Constants.KEY_DICTYPE_RZJE).get(DicTools.getRZJEKey(info.getAmount())));
					d.setDescription(info.getDescription());
					if (companyExt != null) {
						d.setIndustry(companyExt.getIndustry());
					}
					d.setInfoId(info.getInfoId());
					d.setOperDate(info.getOperdate());
					d.setProjectName(info.getProjectName());
					long yearsBetween =0;
					if (company != null) {
						d.setRearea(company.getRearea());
						 yearsBetween = yearsBetween(company.getRedate(), new Date());
						
					}
					d.setFinancingMode(info.getFinacingTurn());//轮次
					String tmpScale = "";
//					if ("0".equals(info.getSell())) {
						if (info.getScaleMin() == null || "".equals(info.getScaleMin())) {
							tmpScale = "0.00%";
						} else {
							tmpScale = DigitFormatUtil.digitFormat(info.getScaleMin()) + "%";
						}
						if (info.getScaleMax() == null || "".equals(info.getScaleMax())) {
							tmpScale += "~" + "0.00%";
						} else {
							tmpScale += "~" + DigitFormatUtil.digitFormat(info.getScaleMax())
									+ "%";
						}
//					} else {
//						tmpScale= "N/A";
//					}
					d.setIntentionMoney(info.getSell()+","+tmpScale);
					d.setRelName(info.getRelName());
					d.setRelPhone(info.getRelPhone());
					// 经营年限
					if (ddEntPeriodMap != null && ddEntPeriodMap.size() > 0) {
						
						for (String key : ddEntPeriodMap.keySet()) {
							String periodValue = ddEntPeriodMap.get(key);
							// 20年以上
							if (periodValue.indexOf("-") == -1) {
								if (yearsBetween >= Long.parseLong(periodValue)) {
									d.setEnterprisePeriod(key);
								}
							} else {
								long start = Long.parseLong(periodValue.split("-")[0]);
								long end = Long.parseLong(periodValue.split("-")[1]);
								// 1-3年
								if (start == 1) {
									if (yearsBetween == 0 || (yearsBetween >= start && yearsBetween < end)) {
										d.setEnterprisePeriod(key);
									}
								} else {
									if (yearsBetween >= start && yearsBetween < end) {
										d.setEnterprisePeriod(key);
									}
								}
							}
						}
					}
					// 融资金额
					if (ddFinacingMoneyMap != null && ddFinacingMoneyMap.size() > 0) {
						for (String key : ddFinacingMoneyMap.keySet()) {
							String financingMoney = ddFinacingMoneyMap.get(key);
							// 100M以上
							if (financingMoney.indexOf("-") == -1) {
								if (d.getAmount() >= Double.parseDouble(financingMoney)) {
									d.setFinacingMoney(key);
								}
							} else {
								double start = Double.parseDouble(financingMoney.split("-")[0]);
								double end = Double.parseDouble(financingMoney.split("-")[1]);
								if (d.getAmount() >= start && d.getAmount() <= end) {
									d.setFinacingMoney(key);
								}
							}
						}
					}
					extraRes.add(d);
			 }	
				
			}
		

		return extraRes;
	}

	/**
	 * 门户存储融资需求
	 * 
	 * @param res
	 */
	@Transactional(value = "transactionManager")
	public void saveLatest(List<FinancingInfoQuery> res) {
		this.finacingInfoQueryDao.deleteAll();
			this.finacingInfoQueryDao.save(res);
	}

	 public static int differentDays(Date date1,Date date2)
	    {
	        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
	        return days;
	    }
	 public boolean isFinacingEvent(String status,String infoId){
			if("03".equals(status)){//判断是否有完成的融资事件
				//融资需求查询已经进行投资的融资事件
			     FinacingEvent finacingEvent=finacingEventDao.findOKFinacingEventByInfoId(infoId);
			     //获取最小的完成时间
			     //获取当前时间
			     Date now = new Date();
			     //判断两个时间是否相差三个月
			     if(finacingEvent!=null) {
			    	 int diffrent=  differentDays(finacingEvent.getOperdate(),now);
			    	 if(diffrent > 90){
			    		 return false;
			    		 
			    	 }else{
			    		 return true;
			    	 }
			     }else{
			    	 return false;
			     }
			}else{
				return true;
			}
		}
	/**
	 * 
	 * <p>
	 * 功能描述:[将数据转换为字典值]
	 * </p>
	 * 
	 * @param ddType
	 * @return
	 * @author:zhanglf
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	private Map<String, String> reverseDD(String ddType) {
		String tmp = "0";
		Map<String, String> ddMap = new HashMap<String, String>();
		Jedis jedis = null;
		try {
			jedis = redisUtil.getConnection();
			String ddValue = jedis.get(ddType);
			List<DD> ddList = JSONArray.parseArray(ddValue, DD.class);
			if (ddList != null && ddList.size() > 0) {
				for (DD dd : ddList) {
					if (dd.getDicName().indexOf("以上") > -1) {
						tmp = dd.getDicName().replaceAll("M以上", "").replaceAll("年以上", "");
					} else {
						tmp = dd.getDicName().replaceAll("年", "").replaceAll("M", "");
					}
					ddMap.put(dd.getDicCode(), tmp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			redisUtil.closeConnection(jedis);

		}
		return ddMap;
	}

	/**
	 * 计算两个时间相差多少年，不足一年返回 0
	 * 
	 * @param early
	 * @param late
	 * @return
	 * @throws ParseException
	 */
	private long yearsBetween(Date start, Date end) throws ParseException {
		startDate.setTime(start);
		endDate.setTime(end);
		// 设置时间为0时
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		endDate.set(Calendar.HOUR_OF_DAY, 0);
		endDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.SECOND, 0);
		// 得到两个日期相差多少秒
		long years = (endDate.getTimeInMillis() - startDate.getTimeInMillis()) / 1000 / 60 / 60 / 24 / 365;
		return years;
	}
	 
}

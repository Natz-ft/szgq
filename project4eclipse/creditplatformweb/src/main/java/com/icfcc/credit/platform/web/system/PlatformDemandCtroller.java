package com.icfcc.credit.platform.web.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icfcc.credit.platform.constants.DD;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.query.CompanyBase;
import com.icfcc.credit.platform.jpa.entity.system.CompanyBusinessplan;
import com.icfcc.credit.platform.jpa.entity.system.FinacingDemand;
import com.icfcc.credit.platform.jpa.entity.system.FinacingEvent;
import com.icfcc.credit.platform.jpa.entity.system.FinacingRecord;
import com.icfcc.credit.platform.jpa.entity.system.SendSmsLog;
import com.icfcc.credit.platform.jpa.entity.system.SrrpSynchronizeJobBean;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;

@Slf4j
@Controller
@RequestMapping(value="demand")
public class PlatformDemandCtroller extends PlatformBasicController {
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	/**
	 * 条件查询需求管理的列表
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="curdDemand")
	public String curdDemand(Model model,PageBean page,HttpServletRequest request){
		Page<FinacingDemand>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        searchParams.put("IN_status","01,02,03,99");
	        String name=(String) searchParams.get("LIKE1_projectName");
	        String status=(String) searchParams.get("EQ_status");
	        if(status!=null){
	        	if(status.equals("88")){
	        		//searchParams.put("EQ_status","99");//撤回
	        		searchParams.put("EQ_revokeFlag","1");//撤回
	        		searchParams.remove("EQ_status");
	        	}else if(status.equals("89")){
	        		//searchParams.put("EQ_status","99");//撤回
	        		searchParams.put("EQ_revokeFlag","2");//待审核
	        		searchParams.remove("EQ_status");
	        	}else{
	        		searchParams.put("EQ_revokeFlag","0");
	        	}
	        }
	        if(name!=null){
				Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
				String[] str = name.split("~");
				StringBuffer sb = new StringBuffer();
				for (String s : str) {
					Matcher matcher = compile.matcher(s);
					if(matcher.find()) {
						String o = matcher.group();
						String n = String.valueOf(Double.valueOf(o)/100);
						if(n.endsWith(".0")) {
							n=n.substring(0,n.length()-2);
						}
						sb.append(s.replace(o, n)+"~");
					}
				}
				if(sb.length()>0) {
					name = sb.toString().substring(0, sb.length()-1).replace("万", "百万");
				}
	        	searchParams.put("LIKE1_projectName",name);
	        	 StringBuffer enterpriseIds=new StringBuffer();
	        	 List<String> ids=companyInfoService.queryIdsByName(name);
	 	        int i =0;
	 	        for (String id : ids) {
	 				if(i==0){ 
	 					enterpriseIds.append(id);
	 				}else{
	 					enterpriseIds.append(","+id);
	 				}
	 				i++;
	 			}
	 	       searchParams.put("IN1_enterpriseId",enterpriseIds.toString());
	        }
	        queryResults = platformDemandService.getSystemFinacindDemandList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDER);
	        Iterator<FinacingDemand> iterator = queryResults.iterator();
			while (iterator.hasNext()) {
				FinacingDemand demand=iterator.next();
				if(demand.getStatus().equals("99") && demand.getRevokeFlag().equals("0")){
					iterator.remove();
				}
			}
	        List<DD> dataList=VipCont.getValueList("dd:demandstatus");
			model.addAttribute("demandStatus", dataList);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/demand/curdDemand";
	}
	/**
	 * 查询需求详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="detaildemand")
	public ModelAndView faqDetail(String id,HttpServletRequest request){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/demand/detailDemand");
    	FinacingDemand demand=new FinacingDemand();
    	demand=platformDemandService.findById(id);
    	demand.setSystemLook("0");
    	//根据企业的id查询商业企划书
    	String url=request.getRequestURL().toString();
    	url=url.substring(0, url.indexOf("creditplatformweb"))+"SRRPBusinesWeb/away/";
    	CompanyBusinessplan businessplan = platformDemandService.findPlanById(id);
    	demand.setFilePath(url+businessplan.getFileinfo());
    	demand.setFileName(businessplan.getFileName());
		model.addObject("demand",demand);
		return model;
	}
	
	
	/**
	 * 初始化撤回面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="revokeDemand")
	public ModelAndView revokeDemand(String id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/demand/revoke");
    	FinacingDemand demand=new FinacingDemand();
    	demand=platformDemandService.findById(id);
		model.addObject("demand",demand);
		return model;
	}
	/**
	 *提交审核
	 * @param id
	 * @return
	 */
	@RequestMapping(value="auditDemand")
	@ResponseBody
	public String auditDemand(ResultBean rs,HttpServletRequest req){
		String infoId = req.getParameter("infoId");
		String auditResult = req.getParameter("auditResult");
		String auditContent = req.getParameter("auditContent");
		try {
	    	FinacingDemand demand=new FinacingDemand();
	    	demand=platformDemandService.findById(infoId);
	    	String type="";
	    	if(auditResult!=null){
	    		if("1".equals(auditResult)){//审核通过
	    			demand.setRevokeFlag("0");
	    			demand.setOperdate(new Date());
	    			if ("1".equals(demand.getOpen())) {// 当选择定向投递的时候
	    				Date now = new Date();// 获取当前时间
	    				demand.setFollowTime(getAfterTime(now, 20));
	    			}
	    			List<FinacingEvent> finacingEvents = platformDemandService.findFinacingEventByInfoId(infoId);
	    	    	for (FinacingEvent finacingEvent : finacingEvents) {
	    				//创建一个融资纪录的对象
	    	    		FinacingRecord finacingRecord = new FinacingRecord();
	    	    		finacingRecord.setEnterpriseid(finacingEvent.getEnterpriseId());
	    	    		finacingRecord.setEventId(finacingEvent.getEventId());
	    	    		finacingRecord.setInfoId(infoId);
	    	    		finacingRecord.setInvestorgid(finacingEvent.getInvestorgId());
	    	    		finacingRecord.setOpercontent("管理部门已审核通过需求。");
	    	    		finacingRecord.setOperdate(new Date());
//	    	    		getCurrentUser().getName()
	    	    		finacingRecord.setOperuser("系统管理员");
	    	    		//finacingRecord.setStatus("99");
	    	    		finacingRecord.setUnLook("1");
	    	    		platformDemandService.saveREcord(finacingRecord);
	    			}
	    	    	type="091";
	    		}else if ("2".equals(auditResult)){//审核不通过
	    			demand.setCloseReason(auditContent);
	    	    	demand.setRevokeFlag("1");
	    	    	List<FinacingEvent> finacingEvents = platformDemandService.findFinacingEventByInfoId(infoId);
	    	    	for (FinacingEvent finacingEvent : finacingEvents) {
	    				//创建一个融资纪录的对象
	    	    		FinacingRecord finacingRecord = new FinacingRecord();
	    	    		finacingRecord.setEnterpriseid(finacingEvent.getEnterpriseId());
	    	    		finacingRecord.setEventId(finacingEvent.getEventId());
	    	    		finacingRecord.setInfoId(infoId);
	    	    		finacingRecord.setInvestorgid(finacingEvent.getInvestorgId());
	    	    		finacingRecord.setOpercontent("管理部门审核未通过。");
	    	    		finacingRecord.setOperdate(new Date());
//	    	    		getCurrentUser().getName()
	    	    		finacingRecord.setOperuser("系统管理员");
	    	    		//finacingRecord.setStatus("99");
	    	    		finacingRecord.setUnLook("1");
	    	    		platformDemandService.saveREcord(finacingRecord);
	    			}
	    	    	type="092";
	    		}
	    		CompanyBase base=companyInfoService.findById(demand.getEnterpriseId());
		    	if(base!=null){
		    		String phone = "";
		    		String name = "";
		    		if(!StringUtils.isEmpty(base.getName())&&!StringUtils.isEmpty(demand.getRelPhone())){
		    			phone=demand.getRelPhone();
		    			name=base.getName();
		    			Random random = new Random();
		    			long sid = System.currentTimeMillis() / 10000 + random.nextInt(999999) % 900000 + 100000;
		    			SendSmsLog log = new SendSmsLog();
						log.setInfoId(demand.getEnterpriseId());
						log.setEnterporinvestId(demand.getInfoId());
						log.setMobile(phone);
						log.setOperdate(demand.getOperdate());
						log.setSendType(type);// 未关注发送短信
						log.setSendStatus("00");
						log.setSid(String.valueOf(sid));
						log = platformDemandService.saveSmsLog(log);
		    			platformDemandService.sendSms(demand,type,log,name) ;
		    		}
		    	}
	    		int upflag= demand.getUpdateFlag();
		    	upflag=upflag+1;
		    	
		    	demand.setUpdateFlag(upflag);
		    	platformDemandService.saveDemand(demand);
	    	}
	    	
	    	
	    	//查询出融资事件的id
	    	
	    	updateTask();
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 提交撤回信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="subRevoke")
	@ResponseBody
	public String subRevoke(ResultBean rs,HttpServletRequest req){
		String infoId = req.getParameter("infoId");
		String closeReason = req.getParameter("closeReason");
		try {
	    	FinacingDemand demand=new FinacingDemand();
	    	demand=platformDemandService.findById(infoId);
	    	demand.setCloseReason(closeReason);
	    	demand.setRevokeFlag("1");
	    	int upflag= demand.getUpdateFlag();
	    	upflag=upflag+1;
	    	demand.setUpdateFlag(upflag);
	    	//查询出融资事件的id
	    	List<FinacingEvent> finacingEvents = platformDemandService.findFinacingEventByInfoId(infoId);
	    	for (FinacingEvent finacingEvent : finacingEvents) {
				//创建一个融资纪录的对象
	    		FinacingRecord finacingRecord = new FinacingRecord();
	    		finacingRecord.setEnterpriseid(finacingEvent.getEnterpriseId());
	    		finacingRecord.setEventId(finacingEvent.getEventId());
	    		finacingRecord.setInfoId(infoId);
	    		finacingRecord.setInvestorgid(finacingEvent.getInvestorgId());
	    		finacingRecord.setOpercontent("需求疑似包含不合法信息，管理部门决定撤回此需求。");
	    		finacingRecord.setOperdate(new Date());
//	    		getCurrentUser().getName()
	    		finacingRecord.setOperuser("系统管理员");
	    		finacingRecord.setStatus("99");
	    		finacingRecord.setUnLook("1");
	    		platformDemandService.saveREcord(finacingRecord);
			}
	    	platformDemandService.saveDemand(demand);
	    	CompanyBase base=companyInfoService.findById(demand.getEnterpriseId());
	    	if(base!=null){
	    		String phone = "";
	    		String name = "";
	    		if(!StringUtils.isEmpty(base.getName())&&!StringUtils.isEmpty(demand.getRelPhone())){
	    			phone=demand.getRelPhone();
	    			name=base.getName();
	    			Random random = new Random();
	    			long sid = System.currentTimeMillis() / 10000 + random.nextInt(999999) % 900000 + 100000;
	    			SendSmsLog log = new SendSmsLog();
					log.setInfoId(demand.getEnterpriseId());
					log.setEnterporinvestId(demand.getInfoId());
					log.setMobile(phone);
					log.setOperdate(demand.getOperdate());
					log.setSendType("09");// 未关注发送短信
					log.setSendStatus("00");
					log.setSid(String.valueOf(sid));
					log = platformDemandService.saveSmsLog(log);
	    			platformDemandService.sendSms(demand,"09",log,name) ;
	    		}
	    	}
	    	
	    	updateTask();
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	public void updateTask(){
		  SrrpSynchronizeJobBean jobsbean1 =taskService.findByJobName("DAY_INDEX");
		  jobsbean1.setIsModify("2");//立即执行标识
          taskService.save(jobsbean1);
          SrrpSynchronizeJobBean jobsbean2 =taskService.findByJobName("DAY_FINACINGINFOR");
		  jobsbean2.setIsModify("2");//立即执行标识
          taskService.save(jobsbean2);
	}
	/**
	 * 初始化修改项目名称页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="upProjectName")
	public ModelAndView upProjectName(String id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/demand/upProjectName");
    	FinacingDemand demand=new FinacingDemand();
    	demand=platformDemandService.findById(id);
		model.addObject("demand",demand);
		return model;
	}
	
	
	/**
	 * 提交更改的项目名称
	 * @param id
	 * @return
	 */
	@RequestMapping(value="subProjectName")
	@ResponseBody
	public String subProjectName(ResultBean rs,HttpServletRequest req){
		String infoId = req.getParameter("infoId");
		String projectName = req.getParameter("projectName");
		try {
	    	FinacingDemand demand=new FinacingDemand();
	    	demand=platformDemandService.findById(infoId);
	    	demand.setProjectName(projectName);
	    	//判断融资事件表中是否有对应的融资事件，并查询出融资事件的list
//	    	List<FinacingEvent> finacingEvents = platformDemandService.findFinacingEventByInfoId(infoId);
	    	//循环遍历融资事件的
//	    	for (FinacingEvent finacingEvent : finacingEvents) {
//	    		finacingEvent.setProjectName(projectName);
//	    		platformDemandService.saveFinacingEvent(finacingEvent);
//			}
	    	platformDemandService.saveDemand(demand);
	    	updateTask();
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	public static Date getAfterTime(Date now, int amount) throws ParseException {
		Date currTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 获得calendar对象
		Calendar calendar = Calendar.getInstance();
		// 设置当前时间
		calendar.setTime(now);
		// 在当前时间下加若干天
		calendar.add(calendar.DAY_OF_MONTH, amount);
		currTime = calendar.getTime();
		return currTime;
	}
}

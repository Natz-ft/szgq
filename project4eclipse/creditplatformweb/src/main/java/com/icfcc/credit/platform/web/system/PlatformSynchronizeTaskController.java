package com.icfcc.credit.platform.web.system;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.icfcc.credit.platform.jpa.entity.system.*;

import org.hibernate.service.spi.ServiceException;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.credit.platform.constants.DD;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.service.system.SrrpSynchronizeTaskService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;

/**
 * 用户管理类，用于管理员增删改查用户
 * 重置用户密码
 * 停用用户账户
 * 解锁用户账户
 * 用户添加角色
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "task")
public class PlatformSynchronizeTaskController extends PlatformBasicController {
    private static Logger log = LoggerFactory.getLogger(PlatformSynchronizeTaskController.class);
    @Autowired
    private SrrpSynchronizeTaskService taskService;
    private static final String Cron="* * * * * ?";
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ?";  
//    private static final String CRON_Timer_FORMAT = "ss mm HH * * ?";  

    /**
     * 用户分页信息
     * @param model
     * @param page
     * @param request
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = "taskList")
    public String taskList(Model model, PageBean page, HttpServletRequest request) throws ParseException {
    	Page<SrrpSynchronizeJobBean>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        searchParams.put("EQ_isValid", "0");//查询当前有效的任务列表
	        queryResults = taskService.getSystemContentTaskList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, "jobId");
			HttpSession session = request.getSession();
	        List<DD> dataList=VipCont.getValueList("dd:tasktatus");
	        Map<String, String> dataMap = new HashMap<String, String>();
	        for(DD dd: dataList){
	        	dataMap.put(dd.getDicCode(), dd.getDicName());
	        }
//	        model.addAttribute("statusList", dataList);
            session.setAttribute("statusList1", dataMap);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/task/taskList";
    }
    @RequestMapping(value="resetCron")
	public ModelAndView resetCron(String id){
		ModelAndView model = new ModelAndView();
		model.setViewName("system/task/resetTimer");
		model.addObject("id",id);
		return model;
	}
    
    @RequestMapping(value="updateCron")
  	public void updateCron(ResultBean rs,HttpServletRequest req,
			HttpServletResponse response){
    	String restTimer = req.getParameter("restTimer");
    	String cron="";
		try{
			Map mapTypes = JSON.parseObject(restTimer);  
			String id = (String) mapTypes.get("id");
	    	Long jobId = Long.valueOf(id);
	    	SrrpSynchronizeJobBean jobsbean=null;
			jobsbean=taskService.findById(jobId);
			if("3".equals(mapTypes.get("type"))){//指定执行
				jobsbean.setIsModify("2");//重置标识
			}else{
				cron=getCron(mapTypes);
		    	System.out.println("cron======================"+cron);
				jobsbean.setIsModify("1");//重置标识
				jobsbean.setJobCron(cron);
			}
			taskService.save(jobsbean);
			rs = ResultBean.sucessResultBean();
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		this.writeJson(rs, req, response);
  	}
    
    @RequestMapping(value = "startJob")
    @ResponseBody
    public String startJob(ResultBean rs, String id, String stopReason) {
        try {
            SrrpSynchronizeJobBean jobsbean =taskService.findById(Long.valueOf(id));
            jobsbean.setStopFlag("1");
            taskService.save(jobsbean);
            rs = ResultBean.sucessResultBean();
        } catch (Exception e) {
            rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
            log.error(e.getMessage());
        }
        return rs.toJSONStr();
    }
    @RequestMapping(value = "stopJob")
    @ResponseBody
    public String stopJob(ResultBean rs, String id, String stopReason) {
        try {
            SrrpSynchronizeJobBean jobsbean =taskService.findById(Long.valueOf(id));
            jobsbean.setStopFlag("2");
            taskService.save(jobsbean);
            rs = ResultBean.sucessResultBean();
        } catch (Exception e) {
            rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
            log.error(e.getMessage());
        }
        return rs.toJSONStr();
    }
    public String getCron(Map mapTypes) throws ParseException{
    	 String CRON_Timer_FORMAT = "ss mm HH day * week";
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if("1".equals(mapTypes.get("type"))){//指定执行
	    		String appointDate = (String) mapTypes.get("appointDate");
	    		 SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
	    		 Date now = sdf1.parse(appointDate); 
		    	 CRON_Timer_FORMAT = sdf.format(now);
	    	}else{//重复执行
	    		//0 0 12 ? * 1,2
	    		System.out.println("reTimer======================"+mapTypes.get("reTimer"));
	    		String[] retimer=((String) mapTypes.get("reTimer")).split(":");
        		CRON_Timer_FORMAT=CRON_Timer_FORMAT.replace("ss", retimer[2]);
        		CRON_Timer_FORMAT=CRON_Timer_FORMAT.replace("mm", retimer[1]);
        		CRON_Timer_FORMAT=CRON_Timer_FORMAT.replace("HH", retimer[0]);
        		String weekstr=(String) mapTypes.get("weekstr");
	        	if(weekstr!=null && !"".equals(weekstr)){
	        		//0 0 12 ? * 1,2
		    		CRON_Timer_FORMAT=CRON_Timer_FORMAT.replace("week", weekstr);
		    		CRON_Timer_FORMAT=CRON_Timer_FORMAT.replace("day", "?");
	        	}else{
	        		//0 0 12 1 * ?
	        		    String days = (String) mapTypes.get("days");
	    	    		System.out.println("dates======================"+days);
	        			CRON_Timer_FORMAT=CRON_Timer_FORMAT.replace("day", days);
			    		CRON_Timer_FORMAT=CRON_Timer_FORMAT.replace("week", "?");
	        	}
	    	}
			return CRON_Timer_FORMAT;
    }
    public void writeJson(Object object, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			String json = JSON.toJSONString(object,
					SerializerFeature.DisableCircularReferenceDetect);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}
	}
  
    
}

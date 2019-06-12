package com.icfcc.credit.platform.web.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icfcc.credit.platform.constants.DD;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.query.CompanyBase;
import com.icfcc.credit.platform.jpa.entity.query.CompanyBaseSupplement;
import com.icfcc.credit.platform.jpa.entity.query.Investor;
import com.icfcc.credit.platform.jpa.entity.query.InvestorLoan;
import com.icfcc.credit.platform.jpa.entity.system.CompanyBusinessplan;
import com.icfcc.credit.platform.jpa.entity.system.FinacingDemand;
import com.icfcc.credit.platform.jpa.entity.system.FinacingEvent;
import com.icfcc.credit.platform.jpa.entity.system.GataWayFinacingEventQuery;
import com.icfcc.credit.platform.jpa.entity.system.SrrpSynchronizeJobBean;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;


@Slf4j
@Controller
@RequestMapping(value="event")
public class PlatformEventCtroller extends PlatformBasicController {

	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	/**
	 * 查询公开的融资事件列表
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="curdEvent")
	public String curdDemand(Model model,PageBean page,HttpServletRequest request){
		Page<GataWayFinacingEventQuery>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        String name=(String) searchParams.get("LIKE1_projectName");
	        StringBuffer enterpriseName=new StringBuffer();
	        enterpriseName.append(name);
	        searchParams.put("LIKE2_enterpriseName",enterpriseName.toString());
	        queryResults = platformDemandService.getFinacindEventList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDEREvent);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		List<DD> dataList=VipCont.getValueList("dd:finacingturn");
		model.addAttribute("finacingTurnDic", dataList);
		processQueryResults(model,page, queryResults);
		return "system/demand/curdEvent";
	}
	
	/**
	 * 查询融资事件详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value="detailEvent")
	public ModelAndView faqDetail(String eventId,HttpServletRequest request){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/demand/detailEvent");
    	//根据事件的id查询融资事件
    	FinacingEvent event = new FinacingEvent();
    	event=platformDemandService.findFinacingEventId(eventId);
    	//根据融资事件查询企业、机构、企业补充信息表的相关数据
    	CompanyBase base=new CompanyBase();
    	base=companyInfoService.findById(event.getEnterpriseId());
    	CompanyBaseSupplement baseSupplement = new CompanyBaseSupplement();
    	baseSupplement =eventService.findCompanyBaseSupplement(event.getEnterpriseId());
    	Investor investor = new Investor();
    	investor =eventService.findByInvestorId(event.getInvestorgId());
    	FinacingDemand demand=new FinacingDemand();
    	demand=platformDemandService.findById(event.getInfoId());
    	//根据需求的id查询需求的商业企划书
    	CompanyBusinessplan businessplan = platformDemandService.findPlanById(event.getInfoId());
    	String url=request.getRequestURL().toString();
    	url=url.substring(0, url.indexOf("creditplatformweb"))+"SRRPBusinesWeb/away/";
    	demand.setFilePath(url+businessplan.getFileinfo());
    	demand.setFileName(businessplan.getFileName());
    	//根据融资事件查询出资信息
    	List<InvestorLoan> loans = eventService.findByEventId(eventId);
    	model.addObject("event",event);
    	model.addObject("base",base);
    	model.addObject("baseSupplement",baseSupplement);
    	model.addObject("loans",loans);
    	model.addObject("investor",investor);
    	model.addObject("demand",demand);
		return model;
	}
	
	/**
	 * 初始化修改项目名称页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="upProjectName")
	public ModelAndView upProjectName(String id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/demand/upEventProjectName");
    	GataWayFinacingEventQuery finacingEvent=new GataWayFinacingEventQuery();
    	finacingEvent=platformDemandService.findByEventId(id);
		model.addObject("finacingEvent",finacingEvent);
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
		String eventId = req.getParameter("eventId");
		String projectName = req.getParameter("projectName");
		try {
			
			GataWayFinacingEventQuery finacingEvent=new GataWayFinacingEventQuery();
	    	finacingEvent=platformDemandService.findByEventId(eventId);
	    	FinacingEvent event = new FinacingEvent();
	    	event=platformDemandService.findFinacingEventId(eventId);
	    	finacingEvent.setProjectName(projectName);
	    	event.setProjectName(projectName);
	    	platformDemandService.upProjectName(finacingEvent,event);
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
//        SrrpSynchronizeJobBean jobsbean2 =taskService.findByJobName("DAY_FINACINGINFOR");
//		  jobsbean2.setIsModify("2");//立即执行标识
//        taskService.save(jobsbean2);
	}
}

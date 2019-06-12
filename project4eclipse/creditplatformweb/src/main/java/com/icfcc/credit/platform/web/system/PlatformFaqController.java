package com.icfcc.credit.platform.web.system;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.PlatformFaq;
import com.icfcc.credit.platform.jpa.entity.system.PlatformNews;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
/**
 * 
* @ClassName: PlatformFaqController
* @Description: TODO(处理常见问题功能模块的请求)
* @author hugt
* @date 2017年9月14日 下午7:40:42
*
 */
@Slf4j
@Controller
@RequestMapping(value="faq")
public class PlatformFaqController extends PlatformBasicController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	/**
	* @Title: curdNews 
	* @Description: TODO(处理查询请求) 
	* @param @param model
	* @param @param page
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="curdFaq")
	public String curdFaq(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformFaq>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        searchParams.put("EQ_faqtype", "0001");
	        queryResults = systemContentFaqService.getSystemContentFaqList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/faq/curdFaq";
	}
	
	
	@RequestMapping(value="createPage")
	public String createPage(){
		return "system/faq/createFaq";
	}
	
	
	@RequestMapping(value="detailFaq")
	public ModelAndView faqDetail(String id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/faq/detailFaq");
    	PlatformFaq faq=new PlatformFaq();
		 faq=systemContentFaqService.findById(Long.valueOf(id));
		model.addObject("faq",faq);
		return model;
	}
	
	/**
	 * 
	* @Title: delNews 
	* @Description: TODO(处理删除请求) 
	* @param @param rs
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="delFaq")
	@ResponseBody
	public String delNews(ResultBean rs,String id){
		try{
			String[] ids=id.split(",");
			for(String everyId:ids){
				if(StringUtils.isNotBlank(everyId)){
					Long sid=Long.parseLong(everyId);
					systemContentFaqService.deleteById(sid);
				}
			}
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 
	* @Title: updateNews 
	* @Description: TODO(处理修改前的请求) 
	* @param @param model
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="updateFaq")
	public String updateNews(Model model,String id){
		Long sid=Long.parseLong(id);
		PlatformFaq faq=systemContentFaqService.findById(sid);
		model.addAttribute("faq", faq);
		return "system/faq/updateFaq";
	}
	/**
	 * 
	* @Title: update 
	* @Description: TODO(修改删除后的请求) 
	* @param @param rs
	* @param @param faq
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public String update(ResultBean rs,HttpServletRequest req) {
		String faqStr = req.getParameter("faq");
		PlatformFaq faq =null;
		try{
			if (faqStr != null && !"".equals(faqStr) && !"\"\"".equals(faqStr)) {
				faq = (PlatformFaq) JSON.parseObject(faqStr, PlatformFaq.class);
			}
			faq.setCreateTime(new Date());
			systemContentFaqService.update(faq);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 
	* @Title: insertNews 
	* @Description: TODO(响应新增的请求) 
	* @param @param rs
	* @param @param faq
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="insertFaq")
	@ResponseBody
	public String insertNews(ResultBean rs,HttpServletRequest req){
		String faqStr = req.getParameter("faq");
		PlatformFaq faq =null;
		try{
			if (faqStr != null && !"".equals(faqStr) && !"\"\"".equals(faqStr)) {
				faq = (PlatformFaq) JSON.parseObject(faqStr, PlatformFaq.class);
			}
			faq.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			faq.setCreateUser(createUser);
			systemContentFaqService.save(faq);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	@RequestMapping(value="curdInform")
	public String curdInform(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformFaq>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        searchParams.put("EQ_faqtype", "0002");
	        queryResults = systemContentFaqService.getSystemContentFaqList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/faq/curdInform";
	}
	@RequestMapping(value="createPageInform")
	public String createPageInform(){
		return "system/faq/createInform";
	}
	@RequestMapping(value="detailInform")
	public ModelAndView InformDetail(String id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/faq/detailInform");
    	PlatformFaq faq=new PlatformFaq();
		 faq=systemContentFaqService.findById(Long.valueOf(id));
		model.addObject("faq",faq);
		return model;
	}
	
	/**
	 * 
	* @Title: delNews 
	* @Description: TODO(处理删除请求) 
	* @param @param rs
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="delInform")
	@ResponseBody
	public String delInform(ResultBean rs,String id){
		try{
			String[] ids=id.split(",");
			for(String everyId:ids){
				if(StringUtils.isNotBlank(everyId)){
					Long sid=Long.parseLong(everyId);
					systemContentFaqService.deleteById(sid);
				}
			}
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 
	* @Title: updateNews 
	* @Description: TODO(处理修改前的请求) 
	* @param @param model
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="InformUpdate")
	public String InformUpdate(Model model,String id){
		Long sid=Long.parseLong(id);
		PlatformFaq faq=systemContentFaqService.findById(sid);
		model.addAttribute("faq", faq);
		return "system/faq/updateInform";
	}
	/**
	 * 
	* @Title: update 
	* @Description: TODO(修改删除后的请求) 
	* @param @param rs
	* @param @param faq
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="updateInform")
	@ResponseBody
	public String updateInform(ResultBean rs,HttpServletRequest req) {
		String faqStr = req.getParameter("faq");
		PlatformFaq faq =null;
		try{
			if (faqStr != null && !"".equals(faqStr) && !"\"\"".equals(faqStr)) {
				faq = (PlatformFaq) JSON.parseObject(faqStr, PlatformFaq.class);
			}
			faq.setCreateTime(new Date());
			systemContentFaqService.update(faq);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 
	* @Title: insertNews 
	* @Description: TODO(响应新增的请求) 
	* @param @param rs
	* @param @param faq
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="insertInform")
	@ResponseBody
	public String insertInform(ResultBean rs,HttpServletRequest req){
		String faqStr = req.getParameter("faq");
		PlatformFaq faq =null;
		try{
			if (faqStr != null && !"".equals(faqStr) && !"\"\"".equals(faqStr)) {
				faq = (PlatformFaq) JSON.parseObject(faqStr, PlatformFaq.class);
			}
			faq.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			faq.setCreateUser(createUser);
			systemContentFaqService.save(faq);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	
	
	
//***********************************************************************************************************//
	
	@RequestMapping(value="curdDy")
	public String curdDy(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformFaq>  queryResults = null;
		try{
			Map<String, Object> searchParams = processRequestParams(page,request);
			searchParams.put("EQ_faqtype", "0003");
			queryResults = systemContentFaqService.getSystemContentFaqList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/faq/curdDy";
	}
	
	@RequestMapping(value="createPageDy")
	public String createPageDy(){
		return "system/faq/createFaqDy";
	}
	
	
	@RequestMapping(value="detailFaqDy")
	public ModelAndView detailFaqDy(String id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/faq/detailFaqDy");
    	PlatformFaq faq=new PlatformFaq();
		 faq=systemContentFaqService.findById(Long.valueOf(id));
		model.addObject("faq",faq);
		return model;
	}
	
	@RequestMapping(value="updateFaqDy")
	public String updateFaqDy(Model model,String id){
		Long sid=Long.parseLong(id);
		PlatformFaq faq=systemContentFaqService.findById(sid);
		model.addAttribute("faq", faq);
		return "system/faq/updateFaqDy";
	}
	/**
	 * 
	* @Title: update 
	* @Description: TODO(修改删除后的请求) 
	* @param @param rs
	* @param @param faq
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="updateDy")
	@ResponseBody
	public String updateDy(ResultBean rs,HttpServletRequest req) {
		String faqStr = req.getParameter("faq");
		PlatformFaq faq =null;
		try{
			if (faqStr != null && !"".equals(faqStr) && !"\"\"".equals(faqStr)) {
				faq = (PlatformFaq) JSON.parseObject(faqStr, PlatformFaq.class);
			}
			faq.setCreateTime(new Date());
			systemContentFaqService.update(faq);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 
	* @Title: insertNews 
	* @Description: TODO(响应新增的请求) 
	* @param @param rs
	* @param @param faq
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="insertFaqDy")
	@ResponseBody
	public String insertFaqDy(ResultBean rs,HttpServletRequest req){
		String faqStr = req.getParameter("faq");
		PlatformFaq faq =null;
		try{
			if (faqStr != null && !"".equals(faqStr) && !"\"\"".equals(faqStr)) {
				faq = (PlatformFaq) JSON.parseObject(faqStr, PlatformFaq.class);
			}
			faq.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			faq.setCreateUser(createUser);
			//systemContentFaqService.save(faq);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	
	
	
}




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
import com.icfcc.credit.platform.jpa.entity.system.PlatformContactus;
import com.icfcc.credit.platform.jpa.entity.system.PlatformNews;
import com.icfcc.credit.platform.jpa.entity.system.PlatformfriendlyLinks;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
/**
 * 
* @ClassName: PlatformContactusController
* @Description: TODO(处理联系我们功能模块的请求)
* @author hugt
* @date 2017年9月14日 下午7:39:34
*
 */
@Slf4j
@Controller
@Scope("prototype")
@RequestMapping(value="contactus")
public class PlatformContactusController extends PlatformBasicController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 
	* @Title: curdNews 
	* @Description: TODO(响应查询请求) 
	* @param @param model
	* @param @param page
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="curdContactus")
	public String curdNews(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformContactus>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
            queryResults = systemContentContactusService.getSystemContentContactusList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/contactus/curdContactus";
	}
	@RequestMapping(value="detailContactus")
	public ModelAndView newsDetail(String id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/contactus/detailContactus");
    	PlatformContactus contactus=new PlatformContactus();
    	contactus=systemContentContactusService.findById(Long.valueOf(id));
		model.addObject("contactus",contactus);
		return model;
	}
	/**
	 * 
	* @Title: delNews 
	* @Description: TODO(响应删除请求) 
	* @param @param rs
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="delContactus")
	@ResponseBody
	public String delNews(ResultBean rs,String id){
		try{
			String[] ids=id.split(",");
			for(String everyId:ids){
				if(StringUtils.isNotBlank(everyId)){
					Long sid=Long.parseLong(everyId);
					systemContentContactusService.deleteById(sid);
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
	* @Description: TODO(响应修改请求) 
	* @param @param model
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="updateContactus")
	public String updateNews(Model model,String id){
		PlatformContactus contactus=systemContentContactusService.findById(Long.valueOf(id));
		model.addAttribute("contactus", contactus);
		return "system/contactus/updateContactus";
	}
	/**
	 * 
	* @Title: update 
	* @Description: TODO(响应修改请求) 
	* @param @param rs
	* @param @param contactus
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public String update(ResultBean rs,HttpServletRequest req) {
		// 获取页面中传递查询条件的参数
		   String  contactusStr= req.getParameter("contactus");
		   PlatformContactus contactus =null;
		   try{
				if (contactusStr != null && !"".equals(contactusStr) && !"\"\"".equals(contactusStr)) {
						contactus = (PlatformContactus) JSON.parseObject(contactusStr, PlatformContactus.class);
				}
			contactus.setCreateTime(new Date());
			systemContentContactusService.update(contactus);
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
	* @Description: TODO(响应新增请求) 
	* @param @param rs
	* @param @param contactus
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="insertContactus")
	@ResponseBody
	public String insertNews(ResultBean rs,PlatformContactus contactus,HttpServletRequest req){
		try{
			contactus.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			contactus.setCreateUser(createUser);
			systemContentContactusService.save(contactus);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
}

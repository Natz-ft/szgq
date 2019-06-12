package com.icfcc.credit.platform.web.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icfcc.credit.platform.jpa.entity.system.PlatformButton;
import com.icfcc.credit.platform.service.system.PlatformButtonService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.credit.platform.web.BaseController;
@Slf4j
@Controller
@RequestMapping(value = "/systemButton")
public class PlatformButtonController  extends BaseController{
	
	private static Logger log = LoggerFactory.getLogger(PlatformButtonController.class);
	private final static String BUTTONLIST = "system/button/button_list";
	private final static String UPDATE_PAGE= "system/button/update_page";	
	@Autowired
	private  PlatformButtonService systemButtonService;

	/**
	 * 按钮管理
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/getButtonList")
	public String getSystemConfigList(Model model,PageBean page,HttpServletRequest request) {
		
		Page<PlatformButton>  queryResults = null;
		try {
			  Map<String, Object> searchParams = processRequestParams(page,request);
		      queryResults = systemButtonService.getButtonList(searchParams, page.getCurPage(),page.getMaxSize(),"desc", "updateTime" );
		      log.debug("page:"+page);
		} catch (Exception e) {
			log.error("systemConfigList:"+e.getMessage());
    	}
		processQueryResults(model,page, queryResults);
		return BUTTONLIST;
	}
	
	/**
	 *  删除
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete_button", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete_button(Model model,HttpServletRequest request,String ids  ) {
		 ResultBean rs;
		  Map<String,String> map = new HashMap<String,String>();
		  try {
			     String[] idsStr = ids.split(",");
			     for (int i = 0; i < idsStr.length; i++) {
			    	 systemButtonService.deleteButtonByButtonId(Long.parseLong(idsStr[i]));
		}
	    rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
		}
			return  rs.toJSONStr();
	}	
	
	/**
	 * 新建
	 * @param model
	 * @param request
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "create_systemButton")
	@ResponseBody
	public String create_systemButton(Model model,HttpServletRequest request, PlatformButton  button) {
		
		 ResultBean rs;
		  try {
			  button.setCreateTime(new Date());
			  ShiroUser user = getCurrentUser();
			  button.setCreateUser(user.getName());
			  button.setCreateTime(new Date());
			  button.setUpdateTime(new Date());
			  systemButtonService.saveButton(button);
			  rs = ResultBean.sucessResultBean();
		  } catch (Exception e) {
			  rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			  e.printStackTrace();
			log.error(e.getMessage());
		}
			return  rs.toJSONStr();
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "findByButtonId")
	public String findByButtonId(Model model,HttpServletRequest request,Long id) {
		PlatformButton button= null;
		try {
			   button   = systemButtonService.findByButtonId(id);
			  log.debug("button:"+button);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		  model.addAttribute("button", button);
		   return UPDATE_PAGE;
	}
	
	
	/**
	 * 修改
	 * @param model
	 * @param request
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "update_systemButton")
	@ResponseBody
	public String update_systemButton(Model model,HttpServletRequest request,PlatformButton button) {
		
		 ResultBean rs;
		  Map<String, Object> map =new HashMap<String, Object>();
		try {
			  button.setUpdateTime(new Date());
			  systemButtonService.updateButton(button);
			  rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
		}
		return  rs.toJSONStr();
	}

}

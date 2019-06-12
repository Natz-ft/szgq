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

import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.PlatformQuickMenu;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
/**
 * 
* @ClassName: PlatformQuickMenuController
* @Description: TODO(处理快捷菜单功能模块的请求)
* @author hugt
* @date 2017年9月14日 下午7:43:00
*
 */
@Slf4j
@Controller
@Scope("prototype")
@RequestMapping(value="quickMenu")
public class PlatformQuickMenuController extends PlatformBasicController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 
	* @Title: curdQuickMenu 
	* @Description: TODO(响应查询请求) 
	* @param @param model
	* @param @param page
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="curdQuickMenu")
	public String curdQuickMenu(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformQuickMenu>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        queryResults = systemContentQuickMenuService.getSystemContentQuickMenuList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/quickMenu/curdQuickMenu";
	}
	/**
	 * 
	* @Title: delQuickMenu 
	* @Description: TODO(响应删除请求) 
	* @param @param rs
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="delQuickMenu")
	@ResponseBody
	public String delQuickMenu(ResultBean rs,String id){
		try{
			String[] ids=id.split(",");
			for(String everyId:ids){
				if(StringUtils.isNotBlank(everyId)){
					Long sid=Long.parseLong(everyId);
					systemContentQuickMenuService.deleteById(sid);
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
	* @Title: updateQuickMenu 
	* @Description: TODO(响应修改前的请求) 
	* @param @param model
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="updateQuickMenu")
	public String updateQuickMenu(Model model,String id){
		Long sid=Long.parseLong(id);
		PlatformQuickMenu quickMenu=systemContentQuickMenuService.findById(sid);
		model.addAttribute("quickMenu", quickMenu);
		return "system/quickMenu/update_page";
	}
	/**
	 * 
	* @Title: update 
	* @Description: TODO(响应修改后的请求) 
	* @param @param rs
	* @param @param quickMenu
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public String update(ResultBean rs,PlatformQuickMenu quickMenu,HttpServletRequest req) {
		try{
			quickMenu.setCreateTime(new Date());
			systemContentQuickMenuService.update(quickMenu);
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
	* @Title: insertQuickMenu 
	* @Description: TODO(响应新增的请求) 
	* @param @param rs
	* @param @param quickMenu
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="insertQuickMenu")
	@ResponseBody
	public String insertQuickMenu(ResultBean rs,PlatformQuickMenu quickMenu,HttpServletRequest req){
		try{
			quickMenu.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			quickMenu.setCreateUser(createUser);
			systemContentQuickMenuService.save(quickMenu);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
}

package com.icfcc.credit.platform.web.system;

/***
 * 系统菜单
 */
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.jpa.entity.system.PlatformMenu;
import com.icfcc.credit.platform.service.system.PlatformMenuService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.credit.platform.web.BaseController;

@Slf4j
@Controller
@RequestMapping(value = "/sysMenu")
public class PlatformMenuController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(PlatformMenuController.class);
	private final static String MENULIST = "system/menu/menuList";
	private final static String UPDATEPAGE = "system/menu/updatePage";
	private final static String CREATEPAGE = "system/menu/createPage";
	private final static String DIRECTION = "desc";
	private final static String ORDERBY = "updateTime";
	private final static String TEST = "system/menu/test";
	private final static String validationEngine = "system/menu/validationEngine";
	@Autowired
	private PlatformMenuService systemMenuService;

	@RequestMapping("/test")
	public String test(Model model, PageBean page, HttpServletRequest request) {

		return validationEngine;
	}

	/**
	 * 分页列表
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/menuList")
	public String menuList(Model model, PageBean page, HttpServletRequest request) {

		Page<PlatformMenu> queryResults = null;
		try {
			Map<String, Object> searchParams = processRequestParams(page, request);
 			queryResults = systemMenuService.getMenuList(searchParams, page.getCurPage(), page.getMaxSize(), DIRECTION, ORDERBY);
			log.debug("page:" + page);
		} catch (Exception e) {
			log.error("systemConfigList:" + e.getMessage());
		}
		processQueryResults(model, page, queryResults);
		return MENULIST;
	}

	/**
	 * 添加方法
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "saveMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveMenu(Model model, HttpServletRequest request, PlatformMenu menu) {

		ResultBean rs;
		log.debug("saveOrUpdateMenu");
		try {
			// 获取用户名
			ShiroUser user = getCurrentUser();
			menu.setCreateUser(user.getName());
			menu.setCreateTime(new Date());
			menu.setUpdateTime(new Date());
			systemMenuService.saveMenu(menu);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 修改方法
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "updateMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateMenu(Model model, HttpServletRequest request, PlatformMenu menu) {

		ResultBean rs;
		log.debug("saveOrUpdateMenu");
		try {
			// 获取用户名
			ShiroUser user = getCurrentUser();
			menu.setUpdateTime(new Date());
			systemMenuService.updateMenu(menu);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 判断菜单ID不能存在
	 * 
	 * @param validateId
	 * @param validateValue
	 * @param validateError
	 * @return
	 */
	@RequestMapping(value = "ajaxFindById")
	@ResponseBody
	public String ajaxFindById(String validateId, String validateValue, String validateError) {

		log.debug("ajaxFindById");
		Object[] result = new Object[3];
		List<PlatformMenu> list = systemMenuService.findById(validateValue);
		Map<String, Object> map = new HashMap<String, Object>();
		result[0] = validateId;
		result[1] = validateError;
		if (CollectionUtils.isNotEmpty(list)) {
			result[2] = "false";
		} else {
			result[2] = "true";
		}
		map.put("jsonValidateReturn", result);
		return JSON.toJSONString(map);
	}

	/**
	 * 判断父级菜单ID是否存在
	 * 
	 * @param validateId
	 * @param validateValue
	 * @param validateError
	 * @return
	 */
	@RequestMapping(value = "ajaxFindByParentId")
	@ResponseBody
	public String ajaxFindByParentId(String validateId, String validateValue, String validateError) {

		log.debug("ajaxFindByParentId");
		Object[] result = new Object[3];
		List<PlatformMenu> list = systemMenuService.findById(validateValue);
		Map<String, Object> map = new HashMap<String, Object>();
		result[0] = validateId;
		result[1] = validateError;
		if (CollectionUtils.isNotEmpty(list)) {
			result[2] = "true";
		} else {
			result[2] = "false";
		}
		map.put("jsonValidateReturn", result);
		return JSON.toJSONString(map);
	}

	/**
	 * 根据id查询菜单对象
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSystemMenuById")
	public String getSystemMenuById(String id, Model model) {

		PlatformMenu menu = null;
		try {
			menu = systemMenuService.findById(id).get(0);
			log.debug("menu" + menu);
			model.addAttribute("menu", menu);
		} catch (Exception e) {
			log.error("getSystemMenuById:" + e.getMessage());
		}
		return UPDATEPAGE;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteMenu", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteMenu(Model model, HttpServletRequest request, String ids) {

		ResultBean rs = null;
		try {
			String[] idsStr = ids.split(",");
			for (int i = 0; i < idsStr.length; i++) {
				systemMenuService.deleteMenuById(idsStr[i]);
			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 跳转
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/createPage")
	public String createPage() {

		return CREATEPAGE;
	}

}

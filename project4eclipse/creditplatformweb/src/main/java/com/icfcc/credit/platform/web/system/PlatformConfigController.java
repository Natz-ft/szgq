package com.icfcc.credit.platform.web.system;

import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.jpa.entity.query.ConfigQuery;
import com.icfcc.credit.platform.jpa.entity.system.PlatformConfig;
import com.icfcc.credit.platform.service.system.PlatformConfigService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.credit.platform.web.BaseController;

@Slf4j
@Controller
@RequestMapping(value = "/systemConfig")
public class PlatformConfigController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(PlatformConfigController.class);
	private final static String CONFIGLIST = "system/config/config_list";
	private final static String CONFIG_CREATE = "system/config/config_create";
	private final static String UPDATE_PAGE = "system/config/update_page";
	private final static String TABS = "system/config/tabs";
	private final static String validationEngine = "system/config/test";
	private final static String CREATE = "system/config/create_page";
	private final static String PASSWORD="password";
	private final static String HIDDEN="*******";
	@Autowired
	private PlatformConfigService systemConfigService;

	/**
	 * 参数管理
	 * 
	 * @param model
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/systemConfigList")
	public String systemConfigList(Model model, PageBean page, HttpServletRequest request) {

		Page<PlatformConfig> queryResults = null;
		try {
			Map<String, Object> searchParams = processRequestParams(page, request);
			queryResults = systemConfigService.getSystemConfigList(searchParams, page.getCurPage(), page.getMaxSize(), "desc", "updateTime");
			//以下用于删除参数配置中的password项
			Iterator<PlatformConfig> iterator = queryResults.iterator();
//			while(iterator.hasNext()){
////				SystemConfig config=iterator.next();
////				if(PASSWORD.equals(config.getConfigName())){
////					config.setConfigValue(HIDDEN);
////				}
//				String configName=iterator.next().getConfigName();
//				if(PASSWORD.equals(configName)){
//					iterator.remove();
//				}
//			}
			
			log.debug("page:" + page);
		} catch (Exception e) {
			log.error("systemConfigList:" + e.getMessage());
		}
		processQueryResults(model, page, queryResults);
		return CONFIGLIST;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete_systemConfig", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String systemConfigList(Model model, HttpServletRequest request, String ids) {

		ResultBean rs = null;
		try {
			String[] idsStr = ids.split(",");
			for (int i = 0; i < idsStr.length; i++) {
				systemConfigService.deleteConfigById(Long.parseLong(idsStr[i]));
			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());

		}
		return rs.toJSONStr();

	}

	/**
	 * 跳转添加页面
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/add_systemConfig_ui")
	public String systemConfig_add_ui(Model model, ConfigQuery page, HttpServletRequest request) {
		return CONFIG_CREATE;
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
	@RequestMapping(value = "add_systemConfig", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String add_systemConfig(Model model, HttpServletRequest request, Long id, PlatformConfig config) {

		ResultBean rs;
		try {
			// 获取用户名
			ShiroUser user = getCurrentUser();
			config.setCreateUser(user.getName());
			systemConfigService.saveSystemConfig(config);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();

	}

	/**
	 * 修 改 跳转界面
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getSystemConfigById")
	public String getSystemConfigById(Model model, HttpServletRequest request, Long id) {

		PlatformConfig config = systemConfigService.getSystemConfigById(id);
		model.addAttribute("config", config);
		return UPDATE_PAGE;
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
	@RequestMapping("/update_systemConfig")
	@ResponseBody
	public String update_systemConfig(Model model, PlatformConfig config) {

		ResultBean rs;
		try {
			PlatformConfig dbconfig=systemConfigService.getSystemConfigById(config.getConfigId());
			dbconfig.setConfigDesc(config.getConfigDesc());
			dbconfig.setConfigValue(config.getConfigValue());
			systemConfigService.updateSystemConfig(dbconfig);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 页签
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/tabs")
	public String update_systemConfig(Model model, HttpServletRequest request) {

		return TABS;
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param config
	 * @param createTime
	 * @return
	 */
	@RequestMapping("/submit_first")
	@ResponseBody
	public String update_systemConfig(Model model, String name, String id) {
		Map map = new HashMap<String, Object>();
		map.put("msg", 1);
		System.err.println("---------------------成功------------------------");
		return JSON.toJSONString(map);
	}

	/**
	 * 测试验证框架
	 * 
	 * @param model
	 * @return validationEngine
	 */
	@RequestMapping("/vtest")
	public String vtest(Model model) {

		return CREATE;
	}

	/**
	 * 测试验证框架
	 * 
	 * @param model
	 * @return validationEngine
	 */
	@RequestMapping("/etest")
	public String etest(Model model) {

		return validationEngine;
	}
}

package com.icfcc.credit.platform.web.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.icfcc.credit.platform.jpa.entity.system.PlatformDic;
import com.icfcc.credit.platform.jpa.entity.system.PlatformOrgConfig;
import com.icfcc.credit.platform.service.system.PlatformButtonService;
import com.icfcc.credit.platform.service.system.PlatformDicService;
import com.icfcc.credit.platform.service.system.PlatformOrgService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.web.BaseController;

/**
 * 机构配置管理
 * 
 * @author tanshengrui
 * 
 */
@Slf4j
@Controller
@RequestMapping(value = "/orgConfig")
public class PlatformOrgConfigController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(PlatformOrgConfigController.class);
	private final static String ORDERBY = "createTime";
	private final static String ORGCONFIGLIST = "system/orgConfig/orgConfigList";
	private final static String UPDATEPAGE = "system/orgConfig/updatePage";
	@Autowired
	private PlatformOrgService systemOrgService;
	// 字典实现类
	@Autowired
	private PlatformDicService systemDicService;
	@Autowired
	private PlatformButtonService buttonService;

	@RequestMapping("/test")
	public String test1(Model model, PageBean page, HttpServletRequest request) {
		buttonService.findByButtonCode("button_add");
		buttonService.findByButtonCode("button_add");
		buttonService.findByButtonCode("button_add");
		buttonService.findByButtonCode("button_add");
		buttonService.findByButtonCode("button_add");
		log.info("aaaaaaaaaaaaaaaaaaaaaaaa");
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteOrgConfig", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete_button(Model model, HttpServletRequest request, String ids) {
		ResultBean rs;
		Map<String, String> map = new HashMap<String, String>();
		try {
			String[] idsStr = ids.split(",");
			for (int i = 0; i < idsStr.length; i++) {
				systemOrgService.deleteOrgConfig(idsStr[i]);
			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
		}
		return rs.toJSONStr();
	}

	/**
	 * 机构配置管理
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOrgConfig")
	public String getSystemOrg(Model model, PageBean page, HttpServletRequest request) {
		Page<PlatformOrgConfig> queryResults = null;
		try {
			Map<String, Object> searchParams = processRequestParams(page, request);
			queryResults = systemOrgService.getSystemOrgConfig(searchParams, page.getCurPage(), page.getMaxSize(), Constant.DIRECTION, ORDERBY);
			log.debug("page:" + page);
		} catch (Exception e) {
			log.error("systemConfigList:" + e.getMessage());
		}
		processQueryResults(model, page, queryResults);
		systemDicType(model);
		return ORGCONFIGLIST;
	}

	private void systemDicType(Model model) {
		// 字典类型查询
		List<PlatformDic> dicList = null;
		try {
			dicList = systemDicService.getDicListByType("pageType");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("dicList", dicList);
		List<PlatformDic> dicListstartFlag = null;
		try {
			dicListstartFlag = systemDicService.getDicListByType("startFlag");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("dicListstartFlag", dicListstartFlag);
	}

	/**
	 * 通过ID查询 SystemOrgConfig
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/orgConfigFindById")
	public String orgFindById(Model model, String id) {

		PlatformOrgConfig orgConfig = null;
		try {
			orgConfig = systemOrgService.findById(id).get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		systemDicType(model);
		model.addAttribute("orgConfig", orgConfig);
		return UPDATEPAGE;
	}

	/**
	 * 判断 AppId是否已经存在
	 * 
	 * @param validateId
	 * @param validateValue
	 * @param validateError
	 * @return11
	 */
	@RequestMapping(value = "ajaxFindByAppid")
	@ResponseBody
	public String ajaxFindByAppid(String validateId, String validateValue, String validateError) {

		log.debug("ajaxFindByAppid");
		Object[] result = new Object[3];
		List<PlatformOrgConfig> list = systemOrgService.findByAppid(validateValue);
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
	 * 机构配置 修改
	 * 
	 * @param model
	 * @param request
	 * @param org
	 * @return
	 */
	@RequestMapping(value = "updateOrgConfig")
	@ResponseBody
	public String updateOrgConfig(Model model, HttpServletRequest request, PlatformOrgConfig orgConfig) {

		ResultBean rs = null;
		try {
			systemOrgService.updateOrgConfig(orgConfig);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
		}
		return rs.toJSONStr();
	}

	/**
	 * 机构 新建
	 * 
	 * @param model
	 * @param request
	 * @param org
	 * @return
	 */
	@RequestMapping(value = "saveOrgConfig")
	@ResponseBody
	public String saveOrgConfig(Model model, HttpServletRequest request, PlatformOrgConfig orgConfig) {

		ResultBean rs = null;
		try {
			orgConfig.setCreateTime(new Date());
			systemOrgService.saveOrgConfig(orgConfig);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
		}
		return rs.toJSONStr();
	}

	
}

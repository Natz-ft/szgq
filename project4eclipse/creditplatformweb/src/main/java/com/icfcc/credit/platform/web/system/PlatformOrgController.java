package com.icfcc.credit.platform.web.system;

/***
 * 机构管理
 */
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.jpa.entity.system.PlatformOrg;
import com.icfcc.credit.platform.jpa.entity.system.PlatformOrgConfig;
import com.icfcc.credit.platform.service.system.PlatformOrgService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.HttpUrlUtils;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.web.BaseController;

@Slf4j
@Controller
@RequestMapping(value = "/sysOrg")
public class PlatformOrgController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(PlatformOrgController.class);
	// 机构信息页面
	private final static String ORG = "system/org/orgList";
	// 机构基本信息
	private final static String ORGDETAIL = "system/org/orgDetail";
	private final static String ORGAPPLICATION = "system/org/orgApplication";
	// 详情 -机构页面
	private final static String ORGCONFDETAIL = "system/org/orgConfDetail";
	private final static String CREATEPAGE = "system/menu/createPage";
	private final static String DIRECTION = "desc";
	private final static String ORDERBY = "orgid";
	// 创建-机构页面
	private static final String ORGCONFCREATEPAGE = "/system/org/orgConfCreate";
	// 配置应用启用标志
	private final static String START_FLAG = "1";
	// 配置应用停用标志
	private final static String STOP_FLAG = "2";
	// 机构修改页面
	private final static String ORGCONFUPDATE = "/system/org/orgConfUpdate";
	private static final String ORGCREATE = "/system/org/orgCreate";
	private static final String ORGUPDATE = "/system/org/orgUpdate";
	@Autowired
	private PlatformOrgService sysOrgService;

	/**
	 * 修改 添加 详情页面 实现 页面相互跳转
	 * 
	 * @param orgid
	 * @return
	 */
	@RequestMapping("/findByOrgId")
	public String findByOrgId(String orgid, Model model, String operType) {
		log.info("findByOrgId");
		try {
			// List<SystemOrg> listOrg = sysOrgService.findByAllValid();
			Map<String, String> map = sysOrgService.getOrg();
			model.addAttribute("orgMap", map);
			if (StringUtils.isNotBlank(orgid)) {
				PlatformOrg sysOrg = sysOrgService.findByOrgid(orgid).get(0);
				if (sysOrg != null) {
					// 调到查询详情页面
					if (StringUtils.equals("Q", operType)) {
						model.addAttribute("org", sysOrg);
						return ORGDETAIL;
					} else {
						model.addAttribute("org", sysOrg);
						return ORGUPDATE;
					}
				} else {
					return ORGCREATE;
				}
			} else {
				return ORGCREATE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
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
	@RequestMapping(value = "deleteOrg", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete_button(Model model, HttpServletRequest request, String ids) {
		log.info("deleteOrg:start");
		ResultBean rs;
		try {
			String[] idsStr = ids.split(",");
			for (int i = 0; i < idsStr.length; i++) {
				List<PlatformOrgConfig> listOrgConfig = null;
				try {
					sysOrgService.deleteByOrgid(idsStr[i]);
					// 获取删除某机构下应用
					listOrgConfig = sysOrgService.findByStartFlag(START_FLAG, "D");
					log.info("listOrgConfig:" + listOrgConfig);
					if (CollectionUtils.isNotEmpty(listOrgConfig)) {
						log.info("listOrgConfig:" + listOrgConfig);
						for (int j = 0; j < listOrgConfig.size(); j++) {
							String pageUrl = listOrgConfig.get(j).getPageUrl();
							if (StringUtils.isNotBlank(pageUrl)) {
								pageUrl = pageUrl.replace("xxx", idsStr[i]);
								log.info("pageUrl:" + pageUrl);
								// http://localhost:8080
								// request.getScheme():http
								// request.getServerName() localhost
								// request.getServerPort() 8080
								log.info("request.getScheme():" + request.getScheme());
								log.info("request.getServerName():" + request.getServerName());
								log.info("request.getServerPort():" + request.getServerPort());
								String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
								log.info("basePath:" + basePath);
								pageUrl = basePath + pageUrl;
								log.info("pageUrl:" + pageUrl);
								String msg = HttpUrlUtils.httpUrlget(pageUrl);
								log.info("msg:" + msg);
							} else {
								log.info("pageUrl:is null ");
							}

						}
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			rs = ResultBean.sucessResultBean();
			log.info("result:" + rs);
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
		}
		log.info("deleteOrg:start");
		return rs.toJSONStr();
	}

	/**
	 * 判断机编码的唯一性
	 * 
	 * @param validateId
	 * @param validateValue
	 * @param validateError
	 * @return
	 */
	@RequestMapping(value = "ajaxFindByOrgId")
	@ResponseBody
	public String ajaxFindByOrgId(String validateId, String validateValue, String validateError) {

		log.debug("ajaxFindByOrgId");
		Object[] result = new Object[3];
		List<PlatformOrg> orgList = sysOrgService.findByOrgid(validateValue);
		Map<String, Object> map = new HashMap<String, Object>();
		result[0] = validateId;
		result[1] = validateError;
		if (CollectionUtils.isNotEmpty(orgList)) {
			result[2] = "false";
		} else {
			result[2] = "true";
		}
		map.put("jsonValidateReturn", result);
		return JSON.toJSONString(map);
	}

	/**
	 * 机构 新建
	 * 
	 * @param model
	 * @param request
	 * @param org
	 * @return
	 */
	@RequestMapping(value = "createSystemOrg")
	@ResponseBody
	public String createSystemOrg(Model model, HttpServletRequest request, PlatformOrg org) {
		ResultBean rs = null;
		try {
			org.setRecordStopTime(new Date());
			org.setRecordStopFlag(Constant.RECORDSTOPFLAGStSTART);
			org.setDeleteState(Constant.EXISTSTATE);
			PlatformOrg orgNew = sysOrgService.saveOrg(org);
			rs = ResultBean.resultBeanData(Constant.SUCCESSMSG, Constant.SUCCESSCODE, orgNew);
			log.info("sysOrgService新建成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
		}
		return rs.toJSONStr();
	}

	/**
	 * 机构 修改
	 * 
	 * @param model
	 * @param request
	 * @param org
	 * @return
	 */
	@RequestMapping(value = "updateSystemOrg")
	@ResponseBody
	public String updateSystemOrg(Model model, HttpServletRequest request, PlatformOrg org) {

		ResultBean rs = null;
		try {
			// org.setRecordStopTime(new Date());
			sysOrgService.updateOrg(org);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgid", org.getOrgid() + "");
			rs = ResultBean.sucessResultBean(null, map);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
		}
		return rs.toJSONStr();
	}

	/**
	 * 跳转
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/test")
	public String createPage() {
		List<PlatformOrg> list = sysOrgService.findByOrgid("1");
		log.info("adfasfdasef:" + JSON.toJSONString(list));
		return ORGDETAIL;
	}

	/**
	 * 机构分页管理
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSystemOrg")
	public String getSystemOrg(Model model, PageBean page, HttpServletRequest request) {

		Page<PlatformOrg> queryResults = null;
		try {
			Map<String, Object> searchParams = processRequestParams(page, request);
			// 将DELETESTATE = 2 的Org删除过滤掉
			searchParams.put("EQ_deleteState", Constant.EXISTSTATE);
			queryResults = sysOrgService.getSystemOrg(searchParams, page.getCurPage(), page.getMaxSize(), DIRECTION, ORDERBY);
			log.debug("page:" + page);
		} catch (Exception e) {
			log.error("systemConfigList:" + e.getMessage());
		}
		processQueryResults(model, page, queryResults);
		return ORG;
	}

	/**
	 * 跳转到创建页面 查询出已启用应用配置
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/getOrgConfCreate")
	public String getOrgConfCreate(Model model, String orgid, String operType) {
		model.addAttribute("orgid", orgid);
		getListOrgConfig(model, operType);
		return ORGCONFCREATEPAGE;
	}

	/**
	 * 机构 详情查询
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @param orgid
	 * @return
	 */
	@RequestMapping("/getOrgFindByOrgidQuery")
	public String getOrgFindByOrgidQuery(Model model, String orgid, String operType) {
		try {
			List<PlatformOrg> orgs = sysOrgService.findByOrgid(orgid);
			PlatformOrg org = new PlatformOrg();
			if (CollectionUtils.isNotEmpty(orgs)) {
				org = orgs.get(0);
			}
			model.addAttribute("org", org);
			model.addAttribute("operType", operType);
			model.addAttribute("orgid", orgid);
			getListOrgConfig(model, operType);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return ORGCONFCREATEPAGE;
	}

	/**
	 * 机构 修改查询
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @param orgid
	 * @return
	 */
	@RequestMapping("/getOrgFindByOrgidUpdate")
	public String getOrgFindByOrgid(Model model, String orgid) {
		model.addAttribute("orgid", orgid);
		getListOrgConfig(model, Constant.PAGETYPE_UPDATE);
		return ORGCONFCREATEPAGE;
	}

	/**
	 * 查询机构配置应用
	 * 
	 * @param model
	 * @param org
	 */
	private void getListOrgConfig(Model model, String pageType) {
		List<PlatformOrgConfig> orgConfigs = sysOrgService.findByStartFlag(START_FLAG, pageType);
		if (CollectionUtils.isNotEmpty(orgConfigs)) {
			log.info(JSON.toJSONString(orgConfigs));
			model.addAttribute("orgConfigs", orgConfigs);
		} else {
			log.info("orgConfigs is null");
		}
	}

	/**
	 * 查询机构配置应用
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/getSystemOrgConfig")
	public String getSystemOrgConfig(Model model) {

		List<PlatformOrgConfig> orgConfigs = sysOrgService.getSystemOrgConfig();
		model.addAttribute("orgConfigs", orgConfigs);
		return ORGAPPLICATION;
	}

	/**
	 * 启用 / 停用
	 * 
	 * @param id
	 * @param recordStopFlag
	 *            1:启用 2:停用
	 * @return
	 */
	@RequestMapping(value = "updateStopFlag")
	@ResponseBody
	public String updateStopFlag(String id, String recordStopFlag) {
		PlatformOrg org = sysOrgService.findById(id, Constant.EXISTSTATE).get(0);
		org.setRecordStopTime(new Date());
		org.setRecordStopFlag(recordStopFlag);
		ResultBean rs = null;
		try {
			PlatformOrg orgSave = sysOrgService.saveOrg(org);
			//rs = ResultBean.resultBeanData(Constant.SUCCESSMSG, Constant.SUCCESSCODE, orgSave);
		} catch (Exception e) {
			log.error(e.getMessage());
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
		}
		return rs.toJSONStr();
	}

	/**
	 * 开发应用Id
	 * 
	 * @param appid
	 * @return
	 */
	@RequestMapping("/findByAppId")
	@ResponseBody
	public String findByAppId(String appid) {
		log.info("param:" + appid);
		PlatformOrgConfig orgConfig = null;
		try {
			orgConfig = sysOrgService.findByAppid(START_FLAG, Constant.PAGETYPE_UPDATE, appid).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(orgConfig);
	}

}

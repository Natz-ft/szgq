package com.icfcc.credit.platform.web.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.icfcc.credit.platform.constants.DD;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.PlatformFunction;
import com.icfcc.credit.platform.jpa.entity.system.PlatformOperateLog;
import com.icfcc.credit.platform.jpa.entity.system.SrrpSynchronizeJobLog;
import com.icfcc.credit.platform.service.system.PlatformFunctionService;
import com.icfcc.credit.platform.service.system.PlatformOperateLogService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SysDate;
import com.icfcc.credit.platform.web.BaseController;

/**
 * 
 * 增删改 的操做日志
 */
@Slf4j
@Controller
@RequestMapping(value = "/sysOperate")
public class PlatformOperateLogController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(PlatformOperateLogController.class);
	private static final String LTE_createTime = "LTE_createTime";
	@Autowired
	private PlatformOperateLogService operateLogService;
	// 操作日志分页列表
	private final static String SYSTEMOPERATELIST = "system/operate/operateList";
	private final static String CREATTIME = "startTime";
	@Autowired
	private PlatformFunctionService systemFunctionService;

	/**
	 * 日志管理
	 * 
	 * @param model
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/systemOperateList")
	public String systemOperateList(Model model, PageBean page, HttpServletRequest request, String search_GTE_createTime_DATE, String search_LTE_createTime_DATE) {

		Page<SrrpSynchronizeJobLog> queryResults = null;
			
		try {
			Map<String, Object> searchParams = processRequestParams(page, request);
			currentDate2MaxDate(searchParams);
			queryResults = operateLogService.getTaskJobLogList(searchParams, page.getCurPage(), page.getMaxSize(), Constant.DIRECTION, CREATTIME);
			HttpSession session = request.getSession();
			List<DD> dataList=VipCont.getValueList("dd:tasktatus");
	        Map<String, String> dataMap = new HashMap<String, String>();
			 for(DD dd: dataList){
		        	dataMap.put(dd.getDicCode(), dd.getDicName());
		        }
//		        model.addAttribute("statusList", dataList);
	            session.setAttribute("statusList2", dataMap);
			log.debug("page:" + page);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("------------systemOperateList-------出现异常");
			log.error(e.getMessage());
		}
		processQueryResults(model, page, queryResults, systemFunctionService);
		model.addAttribute("LTE_createTime", search_LTE_createTime_DATE);
		model.addAttribute("GTE_createTime", search_GTE_createTime_DATE);
		return SYSTEMOPERATELIST;
	}

	/**
	 * 将当前时间修改为当天最大时间，eg: 2016-11-10 11:23:43 --> 2016-11-10 23:59:59
	 * 
	 * @param searchParams
	 * @param gte_loginTime
	 */
	private void currentDate2MaxDate(Map<String, Object> searchParams) {
		Object lte_createTime = searchParams.get(LTE_createTime);
		if (null != lte_createTime) {
			Date createTime = (Date) lte_createTime;
			Date max_createTime = SysDate.getCurrentDateMaxTime(createTime);
			searchParams.put(LTE_createTime, max_createTime);
		}
	}

	/**
	 * 封装构造操作日志
	 * 
	 * @param model
	 * @param page
	 * @param queryResults
	 * @param systemFunctionService
	 */
	public void processQueryResults(Model model, PageBean page, Page<SrrpSynchronizeJobLog> queryResults, PlatformFunctionService systemFunctionService) {
		log.info("processQueryResults:param:page:" + JSON.toJSONString(page) + "queryResults:" + JSON.toJSONString(queryResults));
		if (null == queryResults)
			return;
		List<SrrpSynchronizeJobLog> listLog = queryResults.getContent();
		if (CollectionUtils.isEmpty(listLog)) {
			return;
		}
//		log.info("listLog:" + listLog);
//		for (int i = 0; i < listLog.size(); i++) {
//			operateLog = listLog.get(i);
//			log.info("operateLog:" + listLog);
//			if (operateLog != null) {
//				try {
//					systemFunction = systemFunctionService.getFunctionMethodNameAndServicePath(StringUtils.trim(operateLog.getMethodName()), StringUtils.trim(operateLog.getServicePath())).get(0);
//					if (systemFunction == null) {
//						return;
//					}
//					// 功能模块名称
//					operateLog.setFunctionName(systemFunction.getFunctionName());
//				} catch (Exception e) {
//					e.printStackTrace();
//					log.error(e.getMessage());
//				}
//			}
//			list.add(operateLog);
//		}
		if (CollectionUtils.isNotEmpty(listLog)) {
			page.setList(listLog);
			// 设置总的页数
			page.setPageCnt(queryResults.getTotalPages());
			// 设置总的条数
			page.setRecordCnt(new Long(queryResults.getTotalElements()).intValue());
			page.pageResult(listLog, page.getRecordCnt(), page.getMaxSize(), page.getCurPage());
		}
		log.info("page:" + JSON.toJSONString(page));
		model.addAttribute("page", page);
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteSystemOperate", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete_button(Model model, HttpServletRequest request, String ids) {

		ResultBean rs;
		Map<String, String> map = new HashMap<String, String>();
		try {
			String[] idsStr = ids.split(",");
			for (int i = 0; i < idsStr.length; i++) {
				operateLogService.removeperateLog(idsStr[i]);
			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
		}
		return rs.toJSONStr();
	}
}

package com.icfcc.ssrp.web.gataway;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icfcc.SRRPDao.pojo.StaticResult;
import com.icfcc.SRRPService.gataway.staticize.GataWayStaticResultService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.web.SRRPBaseController;

/**
 * 门户运行成果初始化
 * 
 * @zhanglf
 */
@Controller
@RequestMapping(value = "/result")
public class GataWayStaticResultMakerController extends SRRPBaseController {
	private static Logger log = LoggerFactory
			.getLogger(GataWayStaticResultMakerController.class);

	@Autowired
	private GataWayStaticResultService service;

	@RequestMapping(value = "initInfos")
	public void initInfos(HttpServletRequest request,
			HttpServletResponse response) {
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		try {
			service.writeJsonFile();
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error(e.getMessage());
		}
		executeResult = SRRPConstant.EXECUTSUCC;
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", executeResult);
		resultMap.put("msg", msg);
		//发送客户端响应码
		this.writeJson(resultMap, request, response);
	}

	@RequestMapping(value = "initMothlyRank")
	public void initMothlyRank(HttpServletRequest request,
			HttpServletResponse response) {
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		try {
			String queryDate = request.getParameter("queryDate");
			if ("".equals(queryDate) || null == queryDate
					|| "\"\"".equals(queryDate)) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				cal.add(Calendar.MONTH, -1);
				queryDate = sdf.format(cal.getTime());
			}
			service.writeRankJsonFile(queryDate);
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error(e.getMessage());
		}
		executeResult = SRRPConstant.EXECUTSUCC;
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", executeResult);
		resultMap.put("msg", msg);
		//发送客户端响应码
		this.writeJson(resultMap, request, response);
	}

	@RequestMapping(value = "initHistoryRank")
	public void initHistoryRank(HttpServletRequest request,
			HttpServletResponse response) {
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		try {
			String queryDate = request.getParameter("queryDate");
			if ("".equals(queryDate) || null == queryDate
					|| "\"\"".equals(queryDate)) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				cal.add(Calendar.MONTH, -1);
				queryDate = sdf.format(cal.getTime());
			}
			StaticResult result = service.historyRankQuery(queryDate, true);
			this.writeJson(result, request, response);
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
			log.error(e.getMessage());
		}
		executeResult = SRRPConstant.EXECUTSUCC;
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("code", executeResult);
		resultMap.put("msg", msg);
		//发送客户端响应码
		this.writeJson(resultMap, request, response);
	}
}

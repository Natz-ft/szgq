package com.icfcc.ssrp.web.gataway;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icfcc.SRRPService.gataway.staticize.GataWayInfosService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisManager;
import com.icfcc.ssrp.web.SRRPBaseController;

/**
 * 门户新闻初始化
 * 
 * @zhanglf
 */
@Controller
@RequestMapping(value = "/newInfos")
public class GataWayNewsInfoMakerController extends SRRPBaseController {
	private static Logger log = LoggerFactory
			.getLogger(GataWayNewsInfoMakerController.class);

	@Autowired
	private RedisManager redisManager;

	// 行业动态&政策指南
	@Autowired
	private GataWayInfosService gataWayInfosService;

	@RequestMapping(value = "initInfos")
	public void initInfos(HttpServletRequest request,
			HttpServletResponse response) {
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		try {
			gataWayInfosService.makeInfoDataJson();
			gataWayInfosService.makeInfoDetail();
			gataWayInfosService.makeNewsHtmlAndDetail();
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

	@RequestMapping(value = "addReadAmount")
	public void addReadAmount(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String showAmount = "";
			String infoId = request.getParameter("infoId");
			String readAmount = redisManager
					.get(SRRPConstant.NEWSREADAMOUNTPREFIX + infoId);
			if (readAmount == null || "".equals(readAmount)) {
				redisManager.setNoExpire(SRRPConstant.NEWSREADAMOUNTPREFIX
						+ infoId, SRRPConstant.DEFALUTREADAMOUNT);
				showAmount = String.valueOf(SRRPConstant.DEFALUTREADAMOUNT);
			} else {
				showAmount = String.valueOf(Integer.parseInt(readAmount)
						+ SRRPConstant.READAMOUNTINCREMENT);
				redisManager.setNoExpire(SRRPConstant.NEWSREADAMOUNTPREFIX
						+ infoId, showAmount);
			}
			this.writeJson(Integer.parseInt(showAmount), request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
}

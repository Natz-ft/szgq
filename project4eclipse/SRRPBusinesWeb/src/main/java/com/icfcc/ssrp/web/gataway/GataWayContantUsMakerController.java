package com.icfcc.ssrp.web.gataway;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icfcc.SRRPDao.jpa.entity.managedept.ManagedeptOnlineForum;
import com.icfcc.SRRPService.gataway.staticize.GataWayContantUsService;
import com.icfcc.SRRPService.managedept.ManagedeptOnlineForumService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisManager;
import com.icfcc.ssrp.web.SRRPBaseController;

/**
 * 门户新闻初始化
 * 
 * @zhanglf
 */
@Controller
@RequestMapping(value = "/contantUs")
public class GataWayContantUsMakerController extends SRRPBaseController {
	private static Logger log = LoggerFactory
			.getLogger(GataWayContantUsMakerController.class);

	@Autowired
	private RedisManager redisManager;

	// 联系我们
	@Autowired
	private GataWayContantUsService gataWayContantUsService;

	@Autowired
	private ManagedeptOnlineForumService managedeptOnlineForumService;

	@RequestMapping(value = "initInfos")
	public void initInfos(HttpServletRequest request,
			HttpServletResponse response) {
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
		try {
			gataWayContantUsService.makeContantUsHtml();
			gataWayContantUsService.makeNoticesDetail();
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
					.get(SRRPConstant.NOTICEREADAMOUNTPREFIX + infoId);
			if (readAmount == null || "".equals(readAmount)) {
				showAmount = SRRPConstant.DEFALUTREADAMOUNT;
				redisManager.setNoExpire(SRRPConstant.NOTICEREADAMOUNTPREFIX
						+ infoId, SRRPConstant.DEFALUTREADAMOUNT);
			} else {
				showAmount = String.valueOf(Integer.parseInt(readAmount)
						+ SRRPConstant.READAMOUNTINCREMENT);
				redisManager.setNoExpire(SRRPConstant.NOTICEREADAMOUNTPREFIX
						+ infoId, showAmount);
			}
			this.writeJson(Integer.parseInt(showAmount), request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 添加在线提问问题
	 * 
	 * @param
	 */
	@RequestMapping(value = "addOnlineForum")
	@ResponseBody
	public void addOnlineForum(ResultBean rs,
			ManagedeptOnlineForum managedeptOnlineForum,
			HttpServletRequest request, HttpServletResponse respons) {
		try {
			managedeptOnlineForum.setMessagedate(new Date());
			managedeptOnlineForumService.update(managedeptOnlineForum);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, respons);
	}

	public static <V> void main(String args[]) {
	}

}

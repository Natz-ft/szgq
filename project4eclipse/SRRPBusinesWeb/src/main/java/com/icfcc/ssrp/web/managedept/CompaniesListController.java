package com.icfcc.ssrp.web.managedept;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.ReportBeanEnterpriseResult;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.managedept.CountReportService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/enterpriseStatistics")
public class CompaniesListController extends SRRPBaseController {
	private static Logger _log = LoggerFactory.getLogger(CompaniesListController.class);

	@Autowired
	private CountReportService reportDataMakerService;
	@Autowired
	private PlatformUserService userService;
	@RequestMapping(value = "/initInfo")
	public String controllerTest(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/views/managedept/companiesListQuery";
	}
	@RequestMapping("/companiesList")
	public void getcompaniesList(Model model, PageBean page,HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryConditionString = request.getParameter("queryCondition");
			QueryCondition queryCondition = new QueryCondition();
			if (queryConditionString != null && !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				queryCondition = (QueryCondition) JSON.parseObject(queryConditionString, QueryCondition.class);
			}
			String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
			if (SRRPConstant.USER_TYPE_03.equals(userType)) {// 区县金融办用户
																// 查询本区域的企业用户
				ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject()
						.getPrincipal();
				PlatformUser user = userService.getUser(shiroUser.getId());
				queryCondition.setArea(user.getDesc3());
			}
			List<ReportBeanEnterpriseResult> dataList = reportDataMakerService.getCompaniesList(queryCondition);
			// 将数据传输到前端
			this.writeJson(dataList, request, response);
		} catch (Exception e) {
			_log.error(e.getMessage());
			e.printStackTrace();
		}
		
	}

}

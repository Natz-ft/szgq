package com.icfcc.ssrp.web.managedept;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.managedept.AgencyListResult;
import com.icfcc.SRRPService.managedept.AgencyListService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.ssrp.web.SRRPBaseController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/investorStatistics")
public class AgencyListController extends SRRPBaseController {

	private static Logger _log = LoggerFactory
			.getLogger(AgencyListController.class);
	@Autowired
	private AgencyListService agencyListService;

	@RequestMapping(value = "/initInfo")
	public String controllerTest(HttpServletRequest request,
			HttpServletResponse response) {
		return "WEB-INF/views/managedept/investorStatisticsQuery";
	}

	@RequestMapping("/investorList")
	public void getcompaniesList(Model model, PageBean page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryConditionString = request
					.getParameter("queryCondition");
			QueryCondition queryCondition = new QueryCondition();
			if (queryConditionString != null
					&& !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				queryCondition = (QueryCondition) JSON.parseObject(
						queryConditionString, QueryCondition.class);
			}
			List<AgencyListResult> dataList = agencyListService
					.getInvestorStatistics(queryCondition);
			// 将数据传输到前端
			this.writeJson(dataList, request, response);
		} catch (Exception e) {
			_log.error(e.getMessage());
			e.printStackTrace();
		}

	}

}

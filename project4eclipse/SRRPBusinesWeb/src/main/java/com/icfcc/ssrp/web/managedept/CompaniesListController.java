package com.icfcc.ssrp.web.managedept;


import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResultSub;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.ReportBeanEnterpriseResult;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.inverstorg.FinacingDemandInfoService;
import com.icfcc.SRRPService.managedept.CountReportService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

	/**
	 * 获取用户类型
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserType")
	public String getUserType(HttpServletRequest request, HttpServletResponse response){
		return  RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
	}


	@RequestMapping(value = "/initInfoDemand")
	public String toCompaniesListDemand(HttpServletRequest request, HttpServletResponse response) {
		//苏州地区
		List<DD> ddList = RedisGetValue.getDDList("rearea");
		List<DD> reareas = new ArrayList<>();
		for(DD dd : ddList){
			if(dd.getDicCode().startsWith("3205")){
				reareas.add(dd);
			}
		}
		request.setAttribute("reareas", reareas);
		return "WEB-INF/views/managedept/companiesListDemand";
	}

	// 融资需求信息查询
	@Autowired
	private FinacingDemandInfoService infoService;

	@RequestMapping("/companiesListDemand")
	public void companiesListDemand(Model model, PageBean page,HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryConditionString = request.getParameter("queryCondition");
			String currentPage = request.getParameter("start");
			String maxSize = request.getParameter("limit");
			// 查詢條件對象需要传到Service,进行sql拼装
			QueryCondition queryCondition = new QueryCondition();
			if (queryConditionString != null&& !"".equals(queryConditionString)&& !"\"\"".equals(queryConditionString)) {
				queryCondition = (QueryCondition) JSON.parseObject(queryConditionString, QueryCondition.class);
			}
			if (StringUtils.isNotBlank(currentPage)) {
				queryCondition.setCurPage(Integer.parseInt(currentPage));
			}
			if (StringUtils.isNotBlank(maxSize)) {
				queryCondition.setMaxSize(Integer.parseInt(maxSize));
			}

			// 查询公开的融资需求列表
			List<FinacingDemandInfoResultSub> finacingDemandInfoResults = infoService.getOpenFinacingDemandInfosSub(queryCondition);
			for (FinacingDemandInfoResultSub finacingDemandInfoResult : finacingDemandInfoResults) {


				if (StringUtils.isNotEmpty(finacingDemandInfoResult.getIndustry())) {
					String industryStr = finacingDemandInfoResult.getIndustry();// 获取数据库中行业的展示
					if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
						finacingDemandInfoResult.setIndustry2(finacingDemandInfoResult.getIndustry());
						finacingDemandInfoResult.setIndustryStr(finacingDemandInfoResult.getIndustry2Dicname());
					} else {// 如果是一级的行业显示以及行业
						finacingDemandInfoResult.setIndustry(finacingDemandInfoResult.getIndustry());
						finacingDemandInfoResult.setIndustryStr(finacingDemandInfoResult.getIndustryDicname());
					}
				}

			}
			page.setList(finacingDemandInfoResults);
			if (CollectionUtils.isNotEmpty(finacingDemandInfoResults)) {
				page.setList(finacingDemandInfoResults);
				// 设置总的条数
				Integer total = new Integer(String.valueOf(infoService
						.getOpenFinacingDemandInfoCountSub(queryCondition)));
				page.setRecordCnt(total);
				if (StringUtils.isNotBlank(maxSize)) {
					page.setMaxSize(Integer.parseInt(maxSize));
				}
				if (StringUtils.isNotBlank(currentPage)) {
					page.setCurPage(Integer.parseInt(currentPage));
				}
				page.pageResult(finacingDemandInfoResults, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			// 将数据传输到前端
			this.writeJson(page, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}

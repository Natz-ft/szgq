package com.icfcc.ssrp.web.managedept;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.QueryAreaFinacingResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.QueryIndustryFinacingResult;
import com.icfcc.SRRPService.enterprise.ReportService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/reportController")
public class ReportController extends SRRPBaseController {

	@Autowired
	private ReportService reportService;

	/**
	 * 初始化企业管理列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/reportByIndustryList")
	public String reportByIndustryInit(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return "WEB-INF/views/inverstorg/ReportByIndustry";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/reportByAreaList")
	public String reportByAreaInit(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			return "WEB-INF/views/inverstorg/ReportByArea";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/reportArea")
	public String findReportByArea(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			List<QueryAreaFinacingResult> finacingResultAreaList = reportService
					.getFinacingResultArea(beginTime, endTime);
			for (QueryAreaFinacingResult queryAreaFinacingResult : finacingResultAreaList) {
				queryAreaFinacingResult.setAreaDicname(RedisGetValue
						.getValueByCode(SRRPConstant.DD_AREA,
								queryAreaFinacingResult.getArea()));
			}
			// System.out.println(JSON.toJSONString(finacingResultAreaList));
			this.writeJson(finacingResultAreaList, request, response);
			return "WEB-INF/views/inverstorg/ReportByIndustry";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/reportIndustry")
	public String findReportByIndustry(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String beginTime = request.getParameter("beginTime");
			String endTime = request.getParameter("endTime");
			List<QueryIndustryFinacingResult> finacingResultList = reportService
					.getFinacingResultIndustry(beginTime, endTime);
			for (QueryIndustryFinacingResult queryIndustryFinacingResult : finacingResultList) {
				if (StringUtils.isNotEmpty(queryIndustryFinacingResult
						.getIndustry())) {
					String industryStr = queryIndustryFinacingResult
							.getIndustry();// 获取数据库中行业的展示
					if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
						queryIndustryFinacingResult
								.setIndustry2Dicname(RedisGetValue
										.getValueByCode(
												SRRPConstant.DD_INDUSTRY_2,
												queryIndustryFinacingResult
														.getIndustry()));
						queryIndustryFinacingResult
								.setIndustryStr(queryIndustryFinacingResult
										.getIndustry2Dicname());
					} else {// 如果是一级的行业显示以及行业
						queryIndustryFinacingResult
								.setIndustryDicname(RedisGetValue
										.getValueByCode(
												SRRPConstant.DD_INDUSTRY,
												queryIndustryFinacingResult
														.getIndustry()));
						queryIndustryFinacingResult
								.setIndustryStr(queryIndustryFinacingResult
										.getIndustryDicname());
					}
				}
			}
			// System.out.println(JSON.toJSONString(finacingResultList));
			this.writeJson(finacingResultList, request, response);
			return "WEB-INF/views/inverstorg/ReportByIndustry";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}

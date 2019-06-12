package com.icfcc.ssrp.web.enterprise;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBasePending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyComment;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryFollowInvestorResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryInvestorFinacingEventResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryInvestorLoanResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyCommentService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.FinacingEventService;
import com.icfcc.SRRPService.enterprise.InvestorLoanService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.SRRPService.inverstorg.DemandInfoService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/finacingEvent")
public class FinacingEventCotroller extends SRRPBaseController {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory
			.getLogger(FinacingEventCotroller.class);
	@Autowired
	private FinacingEventService finacingEventService;

	@Autowired
	private DemandInfoService demandInfoService;
	@Autowired
	private InvestorService investorService;
	@Autowired
	private InvestorLoanService investorLoanService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private PlatformUserService serService;
	@Autowired
	private CompanyCommentService companyCommentService;

	/**
	 * 初始化我的融资页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/eventList")
	public String showFinacingEvent(HttpServletRequest request,
			HttpServletResponse response) {
		// 获得到登录用户的企业id
		String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
		Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
		// 未登录强制登录
		if (userInfos.isEmpty()) {
			noLogin(request, response);
		}
		String userId = userInfos.get(SRRPConstant.LOGINUSERID);// 获取用户的id
		// 根据用户的id查询用户信息用户
		PlatformUser platformUser = serService.getUser(userId);
		boolean isShow = false;
		// 根据用户的id 查询企业用户的存量信息的字段
		String status = platformUser.getDesc2();
		if (StringUtils.isNotEmpty(status)) {
			if ("01".equals(status)) {
				// request.setAttribute("status", status);
				isShow = true;
			}
		}
			// 获取需求状态的字典值
			List<DD> ddFinacingStatus = RedisGetValue
					.getDataList(SRRPConstant.DD_DEMANDSTATUS);
			request.setAttribute("isShow", isShow);
			request.setAttribute("ddFinacingStatus", ddFinacingStatus);
			return "WEB-INF/views/enterprise/MyFinacingEvent";
	}

	/**
	 * 企业菜单/我的融资/融资事件/融资事件列表
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/unionEventList")
	public String findUnionFinacingInvestorByCase(Model model, PageBean page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取登录企业的企业id
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			String queryConditionString = request
					.getParameter("queryCondition");
			// 查询条件对象需要传到Service,进行sql拼装
			String currentPage = request.getParameter("start");
			String maxSize = request.getParameter("limit");
			// 查询条件对象需要传到Service,进行sql拼装
			QueryCondition queryCondition = new QueryCondition();
			if (queryConditionString != null
					&& !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				queryCondition = (QueryCondition) JSON.parseObject(
						queryConditionString, QueryCondition.class);
			}
			if (StringUtils.isNotEmpty(currentPage)) {
				queryCondition.setCurPage(Integer.parseInt(currentPage));
			}
			if (StringUtils.isNotEmpty(maxSize)) {
				queryCondition.setMaxSize(Integer.parseInt(maxSize));
			}
			// 查询列表（多表关联查询）
			List<QueryInvestorFinacingEventResult> queryFinacingEventList = finacingEventService
					.listUnionFinacingEventByWhereCase(queryCondition,
							enterpriseId);
			// 循环遍历保存拟投资机构和拟投资行业的相关信息
			for (int i = 0; i < queryFinacingEventList.size(); i++) {
				QueryInvestorFinacingEventResult eventResult = queryFinacingEventList
						.get(i);
				String[] splitFinanceStage = eventResult.getFinanceStage()
						.split("\\,");
				String financeStage = "";
				for (int j = 0; j < splitFinanceStage.length; j++) {
					financeStage = financeStage
							+ ","
							+ RedisGetValue.getValueByCode(
									SRRPConstant.DD_INVESTMENT,
									splitFinanceStage[j]);
				}
				financeStage = (financeStage).substring(1);
				eventResult.setFinanceStageDicname(financeStage);
				eventResult.setProjectName(eventResult.getProjectName().replaceAll("融资", "成功融资"));
			}
			// 给页面对象传递参数
			page.setList(queryFinacingEventList);
			page.setRecordCnt(0);
			if (CollectionUtils.isNotEmpty(queryFinacingEventList)) {
				page.setList(queryFinacingEventList);
				// 设置总的条数
				page.setRecordCnt(Integer.parseInt(finacingEventService
						.getCounts(queryCondition, enterpriseId).toString()));
				if (StringUtils.isNotEmpty(maxSize)) {
					page.setMaxSize(Integer.parseInt(maxSize));
				}
				if (StringUtils.isNotEmpty(currentPage)) {
					page.setCurPage(Integer.parseInt(currentPage));
				}
				page.pageResult(queryFinacingEventList, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			// 将数据传输到页面
			this.writeJson(page, request, response);
			return "WEB-INF/views/enterprise/MyFinacingEvent";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 企业菜单/我的融资/融资列表/详情-------融资详情
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/finacingDetail")
	public String findFinacingEventDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取页面传递的融资需求的id
			String infoId = request.getParameter("infoId");
			String eventId = request.getParameter("eventId");
			// 获取登录用户的企业id
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			// 通过融资事件的id进行查询融资信息
			FinacingDemandInfo finacingInfo = demandInfoService
					.getFinacingInfo(infoId);
			finacingInfo.setAmountShow(finacingInfo.getAmount());
			// 通过企业的id查询企业详细信息
			CompanyBase companyBase = companyInfoService
					.getCompanyBase(enterpriseId);
			// 通过企业的id查询企业补充信息
			CompanyBaseSupplement companyBaseSupplement = companyInfoService
					.getCompanyBaseSupplement(enterpriseId);
			// 根据融资融资信息的id查询机构和融资信息的联合查询
			// 根据融资事件的id去查询机构信息以及融资事件的id联合查询
			List<QueryInvestorFinacingEventResult> unionInverstorlist = investorService
					.findUnionInvestorList(eventId);
			// 根据融资事件的id查询跟投机构的列表
//			List<QueryFollowInvestorResult> investorFollowList = finacingEventService
//					.findInvestorFollowListByEventId(eventId);
//			for (QueryFollowInvestorResult queryFollowInvestorResult : investorFollowList) {
//				QueryInvestorFinacingEventResult result = new QueryInvestorFinacingEventResult();
//				// BeanUtils.copyProperties(result,queryFollowInvestorResult);
//				result.setInvestorName(queryFollowInvestorResult
//						.getInvestorName());
//				result.setAmount(queryFollowInvestorResult.getAmount());
//				result.setOrgType(queryFollowInvestorResult.getOrgType());
//				result.setInvestorgId(queryFollowInvestorResult.getInvestorId());
//				unionInverstorlist.add(result);
//			}

			// 通过融资事件的id查询事件的放款信息列表
			List<QueryInvestorLoanResult> unionInfoLoanList = investorLoanService
					.listUnionInfoLoan(eventId);
			// 根据融资事件的id查询评价信息列表
			List<CompanyComment> companyCommentList = companyCommentService
					.findCompanyCommentByEventId(eventId);
			if (companyCommentList.size() > 0) {
				request.setAttribute("companyCommentList", JSON.toJSONString(
						companyCommentList,
						SerializerFeature.DisableCircularReferenceDetect));
			}

			if (StringUtils.isNotEmpty(companyBaseSupplement.getIndustry())) {
				String industryStr = companyBaseSupplement.getIndustry();// 获取数据库中行业的展示
				if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
					companyBaseSupplement.setIndustry2(companyBaseSupplement
							.getIndustry());
					companyBaseSupplement.setIndustryStr(companyBaseSupplement
							.getIndustry2Dicname());
				} else {// 如果是一级的行业显示以及行业
					companyBaseSupplement.setIndustry(companyBaseSupplement
							.getIndustry());
					companyBaseSupplement.setIndustryStr(companyBaseSupplement
							.getIndustryDicname());
				}
			}
			finacingInfo.setProjectName(finacingInfo.getProjectName().replaceAll("融资", "成功融资"));
			request.setAttribute("finacingInfo", finacingInfo);
			request.setAttribute("companyBase", companyBase);
			request.setAttribute("eventId", eventId);
			request.setAttribute("companyBaseSupplement", companyBaseSupplement);
			request.setAttribute("unioninverstorlist", JSON.toJSONString(
					unionInverstorlist,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("unionInfoLoanList", JSON.toJSONString(
					unionInfoLoanList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyCommentList", JSON.toJSONString(
					companyCommentList,
					SerializerFeature.DisableCircularReferenceDetect));
			return "WEB-INF/views/enterprise/FinacingEventDetail";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
package com.icfcc.ssrp.web.enterprise;

import io.netty.util.internal.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.IndustryVo;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryCompanyFinacingEventResult;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorRegiter;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievement;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievementPending;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditPending;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditRecord;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievement;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievementPending;
import com.icfcc.SRRPDao.jpa.entity.managedept.MessageAlertInfo;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.InvestorSubaccount;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.FinacingEventService;
import com.icfcc.SRRPService.enterprise.InvestorAuditService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.SRRPService.inverstorg.InvestorEditorService;
import com.icfcc.SRRPService.managedept.ManageDissentService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.PdfUtils;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.ExcelUtil;
import com.icfcc.ssrp.web.SRRPBaseController;
import com.icfcc.ssrp.web.inverstorg.DesensitizationUtil;

@Slf4j
@Controller
@RequestMapping(value = "/investor")
public class InvestorController extends SRRPBaseController {

	@Autowired
	private InvestorService investorService;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private FinacingEventService finacingEventService;
	// 根据投资机构的id查询审核记录列表
	@Autowired
	private InvestorAuditService investorAuditService;
	// 根据机构id调取对应service方法
	@Autowired
	private InvestorEditorService investorEditorService;
	@Autowired
	private PlatformUserService serService;
	
	@Autowired
    private ManageDissentService manageDissentService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static Logger log = LoggerFactory
			.getLogger(InvestorController.class);

	 DecimalFormat df = new DecimalFormat("###.##");
	/**
	 * 初始化投资机构查询列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/investorList")
	public String controllerTest(HttpServletRequest request,
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
		// 查询页面中需要的字典值，并回传到页面中去
		// 获取机构类型字典值集合
		List<DD> ddOrgType = RedisGetValue.getDataList(SRRPConstant.DD_ORGTYPE);
		// 获取拟投资阶段字典值集合
		List<DD> ddfinanceStage = RedisGetValue
				.getDataList(SRRPConstant.DD_INVESTMENT);
		// 获取拟投资行业字典值集合
		List<DD> ddfinanceTrade = RedisGetValue
				.getDataList(SRRPConstant.DD_INDUSTRY);
		// 获取管理资本量的字典值展示
		List<DD> ddcaptail = RedisGetValue
				.getDataList(SRRPConstant.DD_FINACINGMONEY);
		// 获取管理资本量币种的展示
		List<DD> ddCurrency = RedisGetValue
				.getDataList(SRRPConstant.DD_CURRENCY);
		// 注册时间
		List<DD> ddList = new ArrayList<DD>();
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);
		for (int i = 0; i < 10; i++) {
			DD dd = new DD();
			dd.setDicCode(String.valueOf(nowYear - i));
			dd.setDicName(String.valueOf(nowYear - i));
			ddList.add(dd);
		}
		request.setAttribute("ddRegisterDate", ddList);
		request.setAttribute("ddOrgType", ddOrgType);
		request.setAttribute("ddcaptail", ddcaptail);
		request.setAttribute("ddCurrency", ddCurrency);
		request.setAttribute("ddfinanceStage", ddfinanceStage);
		request.setAttribute("ddfinanceTrade", ddfinanceTrade);
		request.setAttribute("isShow", isShow);
		return "WEB-INF/views/enterprise/InvestorList";
	}

	/**
	 * 初始化选择投资机构/投资机构查询列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selectInvestorList")
	public String selectInvestorList(HttpServletRequest request,
			HttpServletResponse response) {
		String selected = request.getParameter("selected");
		try {
		String investorIdList = request.getParameter("investorIdList");
		String investorNameList = URLDecoder.decode(request.getParameter("investorNameList"), "UTF-8");
		// 获取机构类型字典值集合
		List<DD> ddOrgType = RedisGetValue.getDataList(SRRPConstant.DD_ORGTYPE);
		request.setAttribute("investorIdList", investorIdList.replaceAll("'", ""));
		request.setAttribute("investorNameList", investorNameList.replaceAll("'", ""));
		request.setAttribute("ddOrgType",ddOrgType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("true".equals(selected)) {
			return "WEB-INF/views/inverstorg/selectInvestor";
		} else {
			return "WEB-INF/views/enterprise/SelectInvestorList";
		}
	}

	/**
	 * 选择投资机构/投资机构查询
	 * 
	 * @param model
	 * @param page
	 * @param registeTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selectInvestorListByOrgType")
	public String findInvestorByOrgType(Model model, PageBean page,
			String registeTime, HttpServletRequest request,
			HttpServletResponse response) {
		String selected = request.getParameter("selected");
		try {
			// 获取页面中传递的参数
			String queryConditionString = request
					.getParameter("queryCondition");
			String currentPage = request.getParameter("start");
			String maxSize = request.getParameter("limit");
			// queryCondition
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
			// 调用service的函数查询相对应的机构
			List<Investor> investorList = investorService
					.listInvestorByOrgType(queryCondition);
			if (investorList != null && investorList.size() > 0) {
				for (Investor investor : investorList) {
					String areaName = RedisGetValue.getValueByCode(
							SRRPConstant.DD_AREA, investor.getAreaCode());
					investor.setTopInvestorDicname(areaName);
					investor.setAreaName(areaName);
				}
			}
			page.setList(investorList);
			page.setRecordCnt(0);
			if (CollectionUtils.isNotEmpty(investorList)) {
				page.setList(investorList);
				// 设置总的条数
				page.setRecordCnt(Integer.parseInt(investorService
						.getInvestorCountByOrgType(queryCondition).toString()));
				if (StringUtils.isNotEmpty(maxSize)) {
					page.setMaxSize(Integer.parseInt(maxSize));
				}
				if (StringUtils.isNotEmpty(currentPage)) {
					page.setCurPage(Integer.parseInt(currentPage));
				}
				page.pageResult(investorList, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			// 将数据传输到前端
			this.writeJson(page, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("true".equals(selected)) {
			return "WEB-INF/views/inverstorg/selectInvestor";
		} else {
			return "WEB-INF/views/enterprise/SelectInvestorList";
		}
	}

	/**
	 * 企业菜单/投资机构查询/投资机构列表--------多条件查询投资机构列表
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/investorInfoList")
	public String findInvestorList(Model model, PageBean page,
			String registeTime, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取页面传参
			String queryConditionString = request
					.getParameter("queryCondition");
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
			// 查询投资机构
			List<Investor> investorList = investorService
					.listInvestorByWhereCase(queryCondition);
			if (investorList != null && investorList.size() > 0) {
				for (Investor investor : investorList) {
					investor.setAreaName(RedisGetValue.getValueByCode(
							SRRPConstant.DD_AREA, investor.getAreaCode()));
					investor.setCountOKEvent(finacingEventService
							.countOKEventsByOrgStatus(investor.getInvestorId()));
				}
			}
			// 给page对象赋值
			page.setList(investorList);
			page.setRecordCnt(0);
			if (CollectionUtils.isNotEmpty(investorList)) {
				page.setList(investorList);
				// 设置总的条数
				page.setRecordCnt(Integer.parseInt(investorService
						.getInvestorCount(queryCondition).toString()));
				if (StringUtils.isNotEmpty(maxSize)) {
					page.setMaxSize(Integer.parseInt(maxSize));
				}
				if (StringUtils.isNotEmpty(currentPage)) {
					page.setCurPage(Integer.parseInt(currentPage));
				}
				page.pageResult(investorList, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			// 将数据传输到前端
			this.writeJson(page, request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/enterprise/InvestorList";
	}

	/**
	 * 企业菜单/投资机构查询/投资机构列表/投资机构名---------投资机构详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/investorDetail")
	public String findInvestorDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 我的需求-详情-投融时间-投资机构-查看详情
			String operate = request.getParameter("operate");
			// 获取页面传递的投资机构的id
			String investorId = request.getParameter("investorId");
			String flag = request.getParameter("flag");
			investorId = investorId.replaceAll("'", "");
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String orgNo = userInfos.get(SRRPConstant.LOGINORGNO);
			// 根据登陆的企业名称，查询企业名称
			CompanyBase baseInfo = companyInfoService.getCompanyBase(orgNo);
			// 根据机构的id查询机构的基本信息
			Investor investor = investorService.findInverstorById(investorId);
			investor.setAreaName(RedisGetValue.getValueByCode(
					SRRPConstant.DD_AREA, investor.getAreaCode()));
			investor.setTopInvestor(RedisGetValue.getValueByCode(
					SRRPConstant.DD_AREA, investor.getTopInvestor()));
			investor.setOperationQualification3Dicname(RedisGetValue
					.getValueByCode(SRRPConstant.DD_RADIO,
							investor.getOperationQualification3()));
			investor.setOrganizationalFormDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_ORGFORM, investor.getOrganizationalForm()));
			investor.setCreditUnhealthyDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_RADIO, investor.getCreditUnhealthyDicname()));
			investor.setMechanismDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_RADIO, investor.getMechanism()));
			// 鏍规嵁鎶曡祫鏈烘瀯鐨刬d锛屾煡璇㈡姇璧勬満鏋勫巻鍙茶繘琛屾姇璧勪簨浠剁殑鏌ヨ
			List<QueryCompanyFinacingEventResult> companyFinacingEventList = investorService
					.findUnionCompanyEventList(investor.getInvestorId(),orgNo);
			for (QueryCompanyFinacingEventResult queryCompanyFinacingEventResult : companyFinacingEventList) {
				if (queryCompanyFinacingEventResult.getIndustry().length() > 2) {
					queryCompanyFinacingEventResult
							.setIndustryStr(queryCompanyFinacingEventResult
									.getIndustry2Dicname());
				} else {
					queryCompanyFinacingEventResult
							.setIndustryStr(queryCompanyFinacingEventResult
									.getIndustryDicName());
				}
				if(baseInfo!=null){
					//给相对应的企业名称进行脱敏
					if (!baseInfo.getName().equals(
							queryCompanyFinacingEventResult.getEnterpriseName())) {
						queryCompanyFinacingEventResult
								.setEnterpriseName(DesensitizationUtil
										.enterpriseName(queryCompanyFinacingEventResult
												.getEnterpriseName()));
					}
				}
				
				PlatformUser platformUser=serService.findUserByUserName(queryCompanyFinacingEventResult.getOperuser());
				queryCompanyFinacingEventResult.setInvestorUser(platformUser.getNickname());
				
			}
			// 鏍规嵁鎶曡祫鏈烘瀯鐨刬d鏌ヨ绠＄悊涓氱哗鎯呭喌
			request.setAttribute("inverstor", investor);
			request.setAttribute("companyFinacingEventList", JSON.toJSONString(
					companyFinacingEventList,
					SerializerFeature.DisableCircularReferenceDetect));
			if (StringUtil.isNullOrEmpty(flag)) {
				request.setAttribute("flag", "true");
			}
			request.setAttribute("operate", operate);
			return "WEB-INF/views/enterprise/InvestorDetail";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/selectInvestorPerFlagById")
	public void selectInvestorPerFlagById(ResultBean rs,
			HttpServletRequest request, HttpServletResponse response) {
		String flag = "0";
		try {
			// 获取登录投资机构用户的id
			String investorId = request.getParameter("investorId");
			// 根据机构的id查询机构的基本信息
			Investor investor = investorService.findInverstorById(investorId);
			flag = investor.getPerFlag();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.writeJson(flag, request, response);
	}
	/**
	 * 查询机构信息
	 * 
	 */
	@RequestMapping(value = "/selectInvestorFlagById")
	public void selectInvestorFlagById(ResultBean rs,
			HttpServletRequest request, HttpServletResponse response) {
		String flag = "0";
		try {
			// 获取登录投资机构用户的id
			String investorId = request.getParameter("investorId");
			// 根据机构的id查询机构的基本信息
			Investor investor = investorService.findInverstorById(investorId);
			flag = investor.getBaseFlag();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.writeJson(flag, request, response);
	}

	 /**
	 * 企业菜单/投资机构查询/投资机构列表/投资机构名---------投资机构详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/findSelectInvestorDetail")
	public String findSelectInvestorDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取页面传递的投资机构的id
			String investorId = request.getParameter("investorId");
			investorId = investorId.replaceAll("'", "");
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String orgNo = userInfos.get(SRRPConstant.LOGINORGNO);
			// 根据机构的id查询机构的基本信息
			Investor investor = investorService.findInverstorById(investorId);
			// 鏍规嵁鎶曡祫鏈烘瀯鐨刬d锛屾煡璇㈡姇璧勬満鏋勫巻鍙茶繘琛屾姇璧勪簨浠剁殑鏌ヨ
			List<QueryCompanyFinacingEventResult> companyFinacingEventList = investorService
					.findUnionCompanyEventList(investor.getInvestorId(),orgNo);
			for (QueryCompanyFinacingEventResult queryCompanyFinacingEventResult : companyFinacingEventList) {
				if (queryCompanyFinacingEventResult.getIndustry().length() > 2) {
					queryCompanyFinacingEventResult
							.setIndustryStr(queryCompanyFinacingEventResult
									.getIndustry2Dicname());
				} else {
					queryCompanyFinacingEventResult
							.setIndustryStr(queryCompanyFinacingEventResult
									.getIndustryDicName());
				}
			}
			// 鏍规嵁鎶曡祫鏈烘瀯鐨刬d鏌ヨ绠＄悊涓氱哗鎯呭喌
			request.setAttribute("inverstor", investor);
			request.setAttribute("companyFinacingEventList", JSON.toJSONString(
					companyFinacingEventList,
					SerializerFeature.DisableCircularReferenceDetect));
			return "WEB-INF/views/enterprise/selectInvestorDetail";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
	 /**
	 * 投资机构下需求的融资进度查询------投资机构名称超链接----详请查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/findDetailByInvestor")
	public String findDetailByInvestor(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 我的需求-详情-投融时间-投资机构-查看详情
			String operate = request.getParameter("operate");
			// 获取页面传递的投资机构的id
			String investorId = request.getParameter("investorId");
			String flag = request.getParameter("flag");
			investorId = investorId.replaceAll("'", "");
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String orgNo = userInfos.get(SRRPConstant.LOGINORGNO);
			// 根据登陆的企业名称，查询企业名称
			CompanyBase baseInfo = companyInfoService.getCompanyBase(orgNo);
			// 根据机构的id查询机构的基本信息
			Investor investor = investorService.findInverstorById(investorId);
			investor.setAreaName(RedisGetValue.getValueByCode(
					SRRPConstant.DD_AREA, investor.getAreaCode()));
			investor.setTopInvestor(RedisGetValue.getValueByCode(
					SRRPConstant.DD_AREA, investor.getTopInvestor()));
			investor.setOperationQualification3Dicname(RedisGetValue
					.getValueByCode(SRRPConstant.DD_RADIO,
							investor.getOperationQualification3()));
			investor.setOrganizationalFormDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_ORGFORM, investor.getOrganizationalForm()));
			investor.setCreditUnhealthyDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_RADIO, investor.getCreditUnhealthyDicname()));
			investor.setMechanismDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_RADIO, investor.getMechanism()));
			List<InvestorAchievement> investorAchievements = investorService
					.findAchievementsById(investorId);
			List<InvestorManageAchievement> manageAchievementList = investorService
					.findAllManageAchievementById(investorId);
			if (investorAchievements != null) {
				for (InvestorAchievement investorAchievement : investorAchievements) {
					if(investorAchievement.getExitTime()!=null){
						investorAchievement.setExitTimeStr(sdf
								.format(investorAchievement.getExitTime()));
					}
					if(investorAchievement.getRateOfReturn()!=null){
					    investorAchievement.setRateOfReturnStr(df.format(investorAchievement.getRateOfReturn()));
						if(investorAchievement.getRateOfReturn()==0){
							investorAchievement.setRateOfReturnStr("");
						}
					}
					investorAchievement.setInvestmentTimeStr(sdf
							.format(investorAchievement.getInvestmentTime()));
				}
			}
			// 鏍规嵁鎶曡祫鏈烘瀯鐨刬d锛屾煡璇㈡姇璧勬満鏋勫巻鍙茶繘琛屾姇璧勪簨浠剁殑鏌ヨ
			List<QueryCompanyFinacingEventResult> companyFinacingEventList = investorService
					.findEventListByInvestor(investor.getInvestorId());
			for (QueryCompanyFinacingEventResult queryCompanyFinacingEventResult : companyFinacingEventList) {
				if (queryCompanyFinacingEventResult.getIndustry().length() > 2) {
					queryCompanyFinacingEventResult
							.setIndustryStr(queryCompanyFinacingEventResult
									.getIndustry2Dicname());
				} else {
					queryCompanyFinacingEventResult
							.setIndustryStr(queryCompanyFinacingEventResult
									.getIndustryDicName());
				}
				if(baseInfo!=null){
					//给相对应的企业名称进行脱敏
					if (!baseInfo.getName().equals(
							queryCompanyFinacingEventResult.getEnterpriseName())) {
						queryCompanyFinacingEventResult
								.setEnterpriseName(DesensitizationUtil
										.enterpriseName(queryCompanyFinacingEventResult
												.getEnterpriseName()));
					}
				}
				
				PlatformUser platformUser=serService.findUserByUserName(queryCompanyFinacingEventResult.getOperuser());
				queryCompanyFinacingEventResult.setInvestorUser(platformUser.getNickname());
				
			}
			request.setAttribute("investorAchievements",
					net.sf.json.JSONArray.fromObject(investorAchievements));
			request.setAttribute("manageAchievementList",
					net.sf.json.JSONArray.fromObject(manageAchievementList));
			// 鏍规嵁鎶曡祫鏈烘瀯鐨刬d鏌ヨ绠＄悊涓氱哗鎯呭喌
			request.setAttribute("inverstor", investor);
			request.setAttribute("companyFinacingEventList", JSON.toJSONString(
					companyFinacingEventList,
					SerializerFeature.DisableCircularReferenceDetect));
			if (StringUtil.isNullOrEmpty(flag)) {
				request.setAttribute("flag", "true");
			}
			request.setAttribute("operate", operate);
			return "WEB-INF/views/inverstorg/InvestorDetail";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 查询机构信息
	 * 
	 */
	@RequestMapping(value = "/selectInvestorById")
	public void selectInvestorById(ResultBean rs, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取登录投资机构用户的id
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			// 根据机构的id查询机构的基本信息
			Investor investor = investorService.findInverstorById(investorId);
			request.setAttribute("investor", investor);
			Object ob = investor;
			rs = new ResultBean(Constant.SUCCESSMSG, Constant.SUCCESSCODE, ob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.writeJson(rs, request, response);
	}

	/**
	 * 编辑机构信息回显到编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/investorEdit")
	public String investorEdit(HttpServletRequest request,
			HttpServletResponse response) {
		return "WEB-INF/views/inverstorg/investorEdit";
	}

	/**
	 * 编辑机构信息回显到编辑页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editInvestorType")
	public String editInvestorType(HttpServletRequest request,
			HttpServletResponse response) {
		String resultPage;
		try {
			String operType = request.getParameter("operType");
			// 获取登录投资机构用户的id
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			Investor investor = investorService.findInverstorById(investorId);
			InvestorAuditPending editorInvestor = investorEditorService
					.selectInvestorAuditPending(investorId);
			String FinanceTrade = "";
			if (investor.getAuditStatus().equals("1")
					|| investor.getAuditStatus().equals("4")
					|| investor.getAuditStatus().equals("2")
					|| investor.getAuditStatus().equals("6")
					|| investor.getAuditStatus().equals("3")
					|| investor.getAuditStatus().equals("23")) {
				FinanceTrade = editorInvestor.getFinanceTrade();
				editorInvestor.setAuditStatus(investor.getAuditStatus());
				editorInvestor.setBaseFlag(investor.getBaseFlag());
				editorInvestor.setPerFlag(investor.getPerFlag());
				request.setAttribute("investor", editorInvestor);
			} else {
				request.setAttribute("investor", investor);
				FinanceTrade = investor.getFinanceTrade();
			}
			resultPage = "WEB-INF/views/inverstorg/";
			operType = operType == null ? "basicInformation" : operType;
			switch (operType) {
			case "basicInformation":
				// 根据机构的id查询机构的基本信息
				// 获取机构类型字典值集合
				List<DD> ddOrgType = RedisGetValue
						.getDataList(SRRPConstant.DD_ORGTYPE);
				// 获取资本类型字典值集合
				List<DD> ddCapitaltype = RedisGetValue
						.getDataList(SRRPConstant.DD_CAPITALTYPE);
				// 获取拟投资阶段字典值集合
				List<DD> ddfinanceStage = RedisGetValue
						.getDataList(SRRPConstant.DD_INVESTMENT);
				// 获取拟投资行业字典值集合
				List<DD> ddfinanceTrade = RedisGetValue
						.getDataList(SRRPConstant.DD_INDUSTRY);
				// 获取证件代码类型字典值集合
				List<DD> ddcerttype = RedisGetValue
						.getDataList(SRRPConstant.DD_CERTIFICATE);
				// 获取证件代码类型字典值集合
				List<DD> ddarea = RedisGetValue
						.getDataList(SRRPConstant.DD_AREA);
				List<DD> otherddarea = new ArrayList<DD>();
				List<DD> suzouddarea = new ArrayList<DD>();
				for (DD dd : ddarea) {
					if (dd.getDicCode().contains("3205")) {
						suzouddarea.add(dd);
					} else {
						otherddarea.add(dd);
					}
				}
				// 获取单选是否的字典值集合
				List<DD> ddradio = RedisGetValue
						.getDataList(SRRPConstant.DD_RADIO);
				// 获取组织形式的字典值
				List<DD> ddOrgform = RedisGetValue
						.getDataList(SRRPConstant.DD_ORGFORM);
				// 获取币种字典值
				List<DD> ddCurrency = RedisGetValue
						.getDataList(SRRPConstant.DD_CURRENCY);
				List<IndustryVo> industryVo = checkIndustry(FinanceTrade);
				// 将investor中拟投资阶段与拟投资行业字典值切割并赋value值
				request.setAttribute("industryVo", net.sf.json.JSONArray
						.fromObject(industryVo).toString());
				// 将investor中拟投资阶段与拟投资行业字典值切割并赋value值
				request.setAttribute("ddOrgType", ddOrgType);
				request.setAttribute("ddOrgform", ddOrgform);
				request.setAttribute("ddcerttype", ddcerttype);
				request.setAttribute("ddfinanceTrade", ddfinanceTrade);
				request.setAttribute("ddfinanceStage", ddfinanceStage);
				request.setAttribute("ddCapitaltype", ddCapitaltype);
				request.setAttribute("ddarea", ddarea);
				request.setAttribute("otherddarea", otherddarea);
				request.setAttribute("suzouddarea", suzouddarea);
				request.setAttribute("ddradio", ddradio);
				request.setAttribute("ddCurrency", ddCurrency);
				resultPage += "baseEdit";
				break;
			case "performanceInformation":
				List<InvestorAchievement> investorAchievement = investorService
						.findAchievementsById(investorId);// 获取投资业绩的信息集合
				List<InvestorManageAchievement> investorManageAchievement = investorService
						.findAllManageAchievementById(investorId); // 获取管理业绩信息集合
				List<InvestorAchievementPending> investorAchievementPending = investorService
						.findAchievementspendingById(investorId);// 获取投资业绩的信息集合
				List<InvestorManageAchievementPending> manageAchievementPending = investorService
						.findAllManageAchievementPendingById(investorId); // 获取管理业绩信息集合
				if (investor.getAuditStatus().equals("1")
						|| investor.getAuditStatus().equals("4")
						|| investor.getAuditStatus().equals("2")
						|| investor.getAuditStatus().equals("6")
						|| investor.getAuditStatus().equals("3")
						|| investor.getAuditStatus().equals("23")) {
					request.setAttribute("investorManageAchievement",
							 JSON.toJSONString(manageAchievementPending,
										SerializerFeature.DisableCircularReferenceDetect));
				} else {
					request.setAttribute("investorManageAchievement",
							 JSON.toJSONString(investorManageAchievement,
										SerializerFeature.DisableCircularReferenceDetect));
				}
				if (investor.getAuditStatus().equals("1")
						|| investor.getAuditStatus().equals("4")
						|| investor.getAuditStatus().equals("2")
						|| investor.getAuditStatus().equals("6")
						|| investor.getAuditStatus().equals("3")
						|| investor.getAuditStatus().equals("23")) {
					request.setAttribute("investorAchievement",
							JSON.toJSONString(investorAchievementPending,
									SerializerFeature.DisableCircularReferenceDetect));
				} else {
					request.setAttribute("investorAchievement",
							JSON.toJSONString(investorAchievement,
									SerializerFeature.DisableCircularReferenceDetect));
				}
				// investorService.save(investor);
				resultPage += "performanceEdit";
				break;
			case "imageInformation":
				resultPage += "imageEdit";
				break;
			case "auditSituation":
				List<InvestorAuditRecord> investorAuditRecord = investorAuditService
						.getAuditRecordByInvestorId(investorId);
				resultPage += "auditSituation";
				request.setAttribute("investorAuditRecords",
						net.sf.json.JSONArray.fromObject(investorAuditRecord));
				break;
			}
			List<DD> ddfinanceTrade = RedisGetValue
					.getDataList(SRRPConstant.DD_INDUSTRY);
			List<DD> ddfinanceTrade2 = RedisGetValue
					.getDataList(SRRPConstant.DD_INDUSTRY_2);
			List<IndustryVo> map = new ArrayList<IndustryVo>();
			for (DD trade : ddfinanceTrade) {
				IndustryVo vo = new IndustryVo();
				vo.setId(trade.getDicCode());
				vo.setpId("0");
				vo.setName(trade.getDicName());
				map.add(vo);
				for (DD trade2 : ddfinanceTrade2) {
					if (trade.getDicCode().equals(
							trade2.getDicCode().substring(0, 2))) {
						IndustryVo vo2 = new IndustryVo();
						vo2.setId(trade2.getDicCode());
						vo2.setpId(trade.getDicCode());
						vo2.setName(trade2.getDicName());
						map.add(vo2);
					}
				}
			}
			if (investor.getAuditStatus().equals("1")
					|| investor.getAuditStatus().equals("4")
					|| investor.getAuditStatus().equals("2")
					|| investor.getAuditStatus().equals("6")
					|| investor.getAuditStatus().equals("3")
					|| investor.getAuditStatus().equals("23")) {
				FinanceTrade = editorInvestor.getFinanceTrade();
				editorInvestor.setAuditStatus(investor.getAuditStatus());
				editorInvestor.setBaseFlag(investor.getBaseFlag());
				editorInvestor.setPerFlag(investor.getPerFlag());
				request.setAttribute("investor", editorInvestor);
			} else {
				request.setAttribute("investor", investor);
				FinanceTrade = investor.getFinanceTrade();
			}
			request.setAttribute("industryJsonData",
					net.sf.json.JSONArray.fromObject(map));

			return resultPage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * @Title: updateInvestor
	 * @Description: 当页面修改投资机构相关信息时相应的修改数据库对应信息或添加待审核表相关信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/imageEdit")
	public void updateInvestorImg(ResultBean rs, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取页面传来的参数
			String investorString = request.getParameter("investorData");
			// 根据登录的投资机构用户获取投资机构id
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			// 根据session中的机构id回去未修改之前的机构用户的信息
			Investor noChangeinvestor = investorService
					.findInverstorById(investorId);
			// 页面传过来的机构信息
			Investor changeinvestor = null;
			// 创建出待审核表对象
			InvestorAuditPending editorInvestor = investorEditorService
					.selectInvestorAuditPending(investorId);
			// 通过此方法将未修改的投资机构对象的值赋给待审核对象
//			 BeanUtils.copyProperties(investor, editorInvestor);

			if (investorString != null && !"".equals(investorString)
					&& !"\"\"".equals(investorString)) {
				changeinvestor = (Investor) JSON.parseObject(investorString,
						Investor.class);
			}
			// 查看机构主要信息是否修改，
			Object ob = "";
			if (noChangeinvestor.getAuditStatus().equals("0")) {// 刚注册的机构用户
				editorInvestor = new InvestorAuditPending();
				noChangeinvestor.setLogoName(changeinvestor.getLogoName());
				noChangeinvestor.setLogoPath(changeinvestor.getLogoPath());
				noChangeinvestor.setBaseFlag("1");
				noChangeinvestor.setLicensePath(changeinvestor.getLicensePath());
				noChangeinvestor.setFileName(changeinvestor.getFileName());
				noChangeinvestor.setRegisterAutographName(changeinvestor.getRegisterAutographName());
				noChangeinvestor.setRegisterAutographPath(changeinvestor.getRegisterAutographPath());
				noChangeinvestor.setCreditAuthorizationName(changeinvestor.getCreditAuthorizationName());
				noChangeinvestor.setCreditAuthorizationPath(changeinvestor.getCreditAuthorizationPath());
				noChangeinvestor.setAuditStatus(SRRPConstant.REFISTER_STATUS_PENGDING);
				BeanUtils.copyProperties(noChangeinvestor, editorInvestor);
				noChangeinvestor.setBaseFlag("2");
				noChangeinvestor.setPerFlag("2");
				investorEditorService.addInvestorAuditPending(editorInvestor);
				investorService.save(noChangeinvestor);
				List< MessageAlertInfo> messageAlertInfos =new ArrayList< MessageAlertInfo>();
                List<PlatformUser> users=serService.findQjrbUser();
                for(PlatformUser user:users){
                    MessageAlertInfo messageAlertInfo =new MessageAlertInfo();
                    messageAlertInfo.setCompanyId(noChangeinvestor.getInvestorId());
                    
                    messageAlertInfo.setMeassgeContent("");
                    messageAlertInfo.setMessageType(SRRPConstant.MESSAGE_TYPE_03);//企业新增提示
                    messageAlertInfo.setMessagUserId(user.getId());
                    messageAlertInfo.setOperDate(new Date());
                    messageAlertInfos.add(messageAlertInfo);
                }
                manageDissentService.saveMessageAlertInfo(messageAlertInfos);

			} else {
				if (changeinvestor.getRegisterAutographPath().equals(
						noChangeinvestor.getRegisterAutographPath())
						&& changeinvestor.getLicensePath().equals(
								noChangeinvestor.getLicensePath())
						&& changeinvestor.getCreditAuthorizationPath().equals(
								noChangeinvestor.getCreditAuthorizationPath())) {
					ob = "01";
					noChangeinvestor.setOperdate(new Date());
					investorService.save(noChangeinvestor);
					// 机构主要信息未修改，修改了附加信息
				} else {// 主要信息修改
					ob = "02";
					if (editorInvestor == null) {
						editorInvestor = new InvestorAuditPending();
						BeanUtils.copyProperties(noChangeinvestor,
								editorInvestor);//
					}
					if (noChangeinvestor.getAuditStatus().equals("23")) {
						noChangeinvestor.setAuditStatus("2");
					} else if (noChangeinvestor.getAuditStatus().equals(
							SRRPConstant.REFISTER_STATUS_NOPASS)) {
						noChangeinvestor
								.setAuditStatus(SRRPConstant.REFISTER_STATUS_PENGDING);
					} else if (noChangeinvestor.getAuditStatus().equals(
							SRRPConstant.EDIT_STATUS_NOPASS)
							|| noChangeinvestor.getAuditStatus().equals(
									SRRPConstant.EDIT_STATUS_PASS)) {
						noChangeinvestor
								.setAuditStatus(SRRPConstant.EDIT_STATUS_PENGDING);
					} else if (noChangeinvestor.getAuditStatus().equals("22")) {
						noChangeinvestor
								.setAuditStatus(SRRPConstant.EDIT_STATUS_PENGDING);
					}
					noChangeinvestor.setBaseFlag("2");
					noChangeinvestor.setPerFlag("2");
					// 更新待审核表信息
					editorInvestor.setLicensePath(changeinvestor
							.getLicensePath());
					editorInvestor.setFileName(changeinvestor.getFileName());
					editorInvestor.setLogoName(changeinvestor.getLogoName());
					editorInvestor.setLogoPath(changeinvestor.getLogoPath());
					editorInvestor.setRegisterAutographName(changeinvestor
							.getRegisterAutographName());
					editorInvestor.setRegisterAutographPath(changeinvestor
							.getRegisterAutographPath());
					editorInvestor.setCreditAuthorizationName(changeinvestor
							.getCreditAuthorizationName());
					editorInvestor.setCreditAuthorizationPath(changeinvestor
							.getCreditAuthorizationPath());
					editorInvestor.setOperdate(new Date());
					investorEditorService.addInvestorAuditPending(editorInvestor);
					// 当修改主要信息时审核状态改变
					investorEditorService.updateInvestorStatus(noChangeinvestor);
				}
			}
			rs = new ResultBean(Constant.SUCCESSMSG, Constant.SUCCESSCODE, ob);
		} catch (BeansException e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, response);
	}

	/**
	 * 添加管理业绩跳转弹出层页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addManageAchievement")
	public String addManageAchievement(HttpServletRequest request,
			HttpServletResponse response) {
		List<DD> ddarea = RedisGetValue.getDataList(SRRPConstant.DD_AREA);

		List<DD> otherddarea = new ArrayList<DD>();
		List<DD> suzouddarea = new ArrayList<DD>();
		for (DD dd : ddarea) {
			if (dd.getDicCode().contains("3205")) {
				suzouddarea.add(dd);
			} else {
				otherddarea.add(dd);
			}
		}
		// 获取拟投资阶段字典值集合
		List<DD> ddfinanceStage = RedisGetValue
				.getDataList(SRRPConstant.DD_INVESTMENT);
		// 获取拟投资行业字典值集合
		List<DD> ddfinanceTrade = RedisGetValue
				.getDataList(SRRPConstant.DD_INDUSTRY);
		List<DD> ddsubacType = RedisGetValue
				.getDataList(SRRPConstant.DD_SUBACTYPE);
		List<DD> ddCurrency = RedisGetValue
				.getDataList(SRRPConstant.DD_CURRENCY);
		request.setAttribute("otherddarea", otherddarea);
		request.setAttribute("suzouddarea", suzouddarea);
		request.setAttribute("ddfinanceStage", ddfinanceStage);
		request.setAttribute("ddfinanceTrade", ddfinanceTrade);
		request.setAttribute("ddsubacType", ddsubacType);
		request.setAttribute("ddCurrency", ddCurrency);
		// 注册时间
		return "WEB-INF/views/inverstorg/addManageAchievement";
	}

	/**
	 * 添加投资业绩跳转弹出层页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addInvestAchievement")
	public String addInvestAchievement(HttpServletRequest request,
			HttpServletResponse response) {
		List<DD> ddarea = RedisGetValue.getDataList(SRRPConstant.DD_AREA);
		List<DD> otherddarea = new ArrayList<DD>();
		List<DD> suzouddarea = new ArrayList<DD>();
		for (DD dd : ddarea) {
			if (dd.getDicCode().contains("3205")) {
				suzouddarea.add(dd);
			} else {
				otherddarea.add(dd);
			}
		}
		// 获取行业类型字典值集合
		// List<DD> ddIndustry =
		// RedisGetValue.getDataList(SRRPConstant.DD_INDUSTRY);
		// 获取融资阶段字类型典值集合
		List<DD> ddfinanceStage = RedisGetValue
				.getDataList(SRRPConstant.DD_INVESTMENT);
		// 融资轮次
		List<DD> ddFinacingturn = RedisGetValue
				.getDataList(SRRPConstant.DD_FINACINGTURN);
		List<DD> ddfinanceTrade = RedisGetValue
				.getDataList(SRRPConstant.DD_INDUSTRY);
		List<DD> ddfinanceTrade2 = RedisGetValue
				.getDataList(SRRPConstant.DD_INDUSTRY_2);
		List<IndustryVo> map = new ArrayList<IndustryVo>();
		for (DD trade : ddfinanceTrade) {
			IndustryVo vo = new IndustryVo();
			vo.setId(trade.getDicCode());
			vo.setpId("0");
			vo.setName(trade.getDicName());
			map.add(vo);
			for (DD trade2 : ddfinanceTrade2) {
				if (trade.getDicCode().equals(
						trade2.getDicCode().substring(0, 2))) {
					IndustryVo vo2 = new IndustryVo();
					vo2.setId(trade2.getDicCode());
					vo2.setpId(trade.getDicCode());
					vo2.setName(trade2.getDicName());
					map.add(vo2);
				}
			}
		}

		// 币种字典值集合
		List<DD> ddCurrency = RedisGetValue
				.getDataList(SRRPConstant.DD_CURRENCY);
		request.setAttribute("ddIndustry",
				net.sf.json.JSONArray.fromObject(map));
		request.setAttribute("ddfinanceStage", ddfinanceStage);
		request.setAttribute("otherddarea", otherddarea);
		request.setAttribute("suzouddarea", suzouddarea);
		request.setAttribute("ddFinacingturn", ddFinacingturn);
		request.setAttribute("ddCurrency", ddCurrency);
		return "WEB-INF/views/inverstorg/addInvestAchievement";
	}

	/**
	 * 添加投资业绩信息
	 * 
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "/investorAchievement")
	public void addInvestorAchievement(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		try {
			String investorAchievementString = request
					.getParameter("investorAchievementData");
			InvestorAchievement investorAchievement = new InvestorAchievement();
			if (investorAchievementString != null
					&& !"".equals(investorAchievementString)
					&& !"\"\"".equals(investorAchievementString)) {
				investorAchievement = (InvestorAchievement) JSON.parseObject(
						investorAchievementString, InvestorAchievement.class);
			}
			if (StringUtils.isNotEmpty(investorAchievement.getIndustry2())) {
				investorAchievement
						.setInvestedEnterpriseIndustry(investorAchievement
								.getIndustry2());
			} else {
				investorAchievement
						.setInvestedEnterpriseIndustry(investorAchievement
								.getIndustry1());
			}
			// 根据登录的投资机构用户获取投资机构id
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			investorAchievement.setInvestId(investorId);
			this.writeJson(investorAchievement, request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 添加管理业绩信息
	 * 
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addInvestorManageAchievement")
	public void addInvestorManageAchievement(HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		try {
			String investorManageAchievementString = request
					.getParameter("investorManageAchievementData");
			InvestorManageAchievement investorManageAchievement = new InvestorManageAchievement();
			if (investorManageAchievementString != null
					&& !"".equals(investorManageAchievementString)
					&& !"\"\"".equals(investorManageAchievementString)) {
				investorManageAchievement = (InvestorManageAchievement) JSON
						.parseObject(investorManageAchievementString,
								InvestorManageAchievement.class);
			}
			// 获取UUID并转化为String对象
			// 根据登录的投资机构用户获取投资机构id
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			investorManageAchievement.setInvestId(investorId);
			this.writeJson(investorManageAchievement, request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 删除业绩信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteInvestorManageAchievement")
	public void deleteInvestorManageAchievement(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String achievementId = request.getParameter("achievementId");
			String manageId = request.getParameter("manageId");
			if (manageId != null && manageId.length() > 0) {
				investorService.deleteInvestorManageAchievementById(manageId);
			} else if (achievementId != null && achievementId.length() > 0) {
				investorService.deleteInvestorAchievementById(achievementId);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/performanceEdit")
	public void submitMangaeAchievementDesc(ResultBean rs,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String desc = request.getParameter("desc");
			String manaData = request.getParameter("manaData");
			String achieDatata = request.getParameter("achieDatata");
			String manageAchievementFalg = "";
			String achievementFalg = "";
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			Investor investor = investorService.findInverstorById(investorId);
			Map map = (Map) JSON.parse(desc);
			manageAchievementFalg = (String) map.get("manageAchievementFalg");
			achievementFalg = (String) map.get("achievementFalg");
			List<InvestorManageAchievement> investorManageAchievements = new ArrayList<InvestorManageAchievement>();
			List<InvestorAchievement> investorAchievements = new ArrayList<InvestorAchievement>();
			List<InvestorAchievementPending> investorAchievementPendings = new ArrayList<InvestorAchievementPending>();
			List<InvestorManageAchievementPending> investorManageAchievementPendings = new ArrayList<InvestorManageAchievementPending>();
			String manageUpdateFlag = (String) map.get("manageUpdateFlag");// 管理业绩表的更新标识
			String achieveUpdateFlag = (String) map.get("achieveUpdateFlag");// 投资业绩表的更新标识
			if ("0".equals(investor.getAuditStatus())) {// 新增
				investor.setPerFlag("1");
				if ("1".equals(manageAchievementFalg)) {// 判断前台选择是否有管理业绩
					if (manaData.contains("[],")) {
						manaData = manaData.replace("[],", "");
					}
					if (manaData.contains(",[]]")) {
						manaData = manaData.replace(",[]]", "]");
					}
					investorManageAchievements=(List<InvestorManageAchievement>)JSON.parseArray(manaData, InvestorManageAchievement.class);
					com.alibaba.fastjson.JSONArray json = (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSONArray.parse(manaData);
					investorService.updateManageAchievementsById(investorId);
					investorService.saveManageAchievementList(investorManageAchievements);
					investorService.updateManageAchievementPendingsById(investorId);
					for (InvestorManageAchievement investorManageAchievement : investorManageAchievements) {
						InvestorManageAchievementPending investorManageAchievementPending = new InvestorManageAchievementPending();
						BeanUtils.copyProperties(investorManageAchievement,investorManageAchievementPending);
						investorManageAchievementPendings.add(investorManageAchievementPending);
					}
					investorService
							.saveManageAchievementPendingList(investorManageAchievementPendings);
					investor.setManageAchievementFalg("1");
					investor.setManageAchievementDesc("");
				} else {
					investor.setManageAchievementDesc(map.get("madFlag")
							.toString());
					investor.setManageAchievementFalg("2");
					manageUpdateFlag = "01";
					investorService.updateManageAchievementsById(investorId);
				}
				if ("1".equals(achievementFalg)) {// 判断前台选择是否有投资项目业绩
					investorService.updateAchievementsById(investorId);
					if (achieDatata.contains("[],")) {
						achieDatata = achieDatata.replace("[],", "");
					}
					if (achieDatata.contains(",[]]")) {
						achieDatata = achieDatata.replace(",[]]", "]");
					}
					investorAchievements=(List<InvestorAchievement>)JSON.parseArray(achieDatata, InvestorAchievement.class);

					investorService.saveAchievementList(investorAchievements);
					investorService.updateAchievementPendingsById(investorId);
					for (InvestorAchievement investorAchievement : investorAchievements) {
						InvestorAchievementPending investorAchievementPending = new InvestorAchievementPending();
						BeanUtils.copyProperties(investorAchievement,
								investorAchievementPending);
						investorAchievementPendings.add(investorAchievementPending);

					}
					investorService
							.saveAchievementPendingList(investorAchievementPendings);
					investor.setAchievementFalg("1");
					investor.setAchievementDesc("");
				} else {
					investor.setAchievementDesc(map.get("adFlag").toString());
					investor.setAchievementFalg("2");
					achieveUpdateFlag = "01";
					investorService.updateAchievementsById(investorId);
				}
				investorService.save(investor);
			} else {// 修改
				InvestorAuditPending pending = investorEditorService.selectInvestorAuditPending(investorId);
				if (pending == null) {
					pending = new InvestorAuditPending();
					BeanUtils.copyProperties(investor, pending);
				}
				 investor.setPerFlag("2");
				//判断选择有管理基金时，是否修改
				  if ("1".equals(manageAchievementFalg)) {
					//查询正式表业绩
					  List<InvestorManageAchievement> investorManageAchievement = investorService.findAllManageAchievementById(investorId); // 获取管理业绩信息集合
					//获取页面业绩信息、
					    if (manaData.contains("[],")) {
							manaData = manaData.replace("[],", "");
						}
						if (manaData.contains(",[]]")) {
							manaData = manaData.replace(",[]]", "]");
						}
	                    investorManageAchievementPendings=(List<InvestorManageAchievementPending>)JSON.parseArray(manaData, InvestorManageAchievementPending.class);
	                   
	                    if(investorManageAchievementPendings.size()!=investorManageAchievement.size()){
	                    	manageUpdateFlag = "01";
	                    	investor.setPerFlag("1");
	                    }else{
	                    	List ManageachivevementPendingStr=new ArrayList();
		                      for(InvestorManageAchievementPending achivevementPending :investorManageAchievementPendings){
		                    	ManageachivevementPendingStr.add(achivevementPending.toString());
			                  } 
			                  for(InvestorManageAchievement Manageachivevement :investorManageAchievement){
			                	  if(!ManageachivevementPendingStr.contains(Manageachivevement.toString())){
			                		  manageUpdateFlag = "01";
			                	  }
			                  }  
			                  if(manageUpdateFlag.equals("01")){
			                	  investor.setPerFlag("1");
			                  }
	                    }
	                    pending.setManageAchievementFalg("1");
						pending.setManageAchievementDesc("");
	                    	investorService.updateManageAchievementPendingsById(investorId);
	                    	investorService.saveManageAchievementPendingList(investorManageAchievementPendings);
	                    
				  }else{
					  if (investor.getManageAchievementDesc() != null) {//
							if (!investor.getManageAchievementDesc().equals(
									map.get("madFlag").toString())) {
								manageUpdateFlag = "01";
								pending.setManageAchievementDesc(map.get("madFlag")
										.toString());
								pending.setManageAchievementFalg("2");
								investor.setPerFlag("1");
								investorService
										.updateManageAchievementPendingsById(investorId);
	
							}
						}
				  }
				  if ("1".equals(achievementFalg)) {
					  if (achieDatata.contains("[],")) {
							achieDatata = achieDatata.replace("[],", "");
						}
						if (achieDatata.contains(",[]]")) {
							achieDatata = achieDatata.replace(",[]]", "]");
						}
						//1.1查询正式表业绩
						List<InvestorAchievement> investorAchievement = investorService.findAchievementById(investorId);// 获得投资业绩的信息
						investorAchievementPendings=(List<InvestorAchievementPending>)JSON.parseArray(achieDatata, InvestorAchievementPending.class);
						 if(investorAchievementPendings.size()!=investorAchievement.size()){
							 achieveUpdateFlag = "01";
							 investor.setPerFlag("1");
	                    }else{
	                    	List achivevementPendingStr=new ArrayList();
		                    for(InvestorAchievementPending achivevementPending :investorAchievementPendings){
		                    	achivevementPendingStr.add(achivevementPending.toString());
			                  } 
		                    //1.2 正式表信息和页面的列表信息对比
		                  for(InvestorAchievement achivevement :investorAchievement){
		                	  if(!achivevementPendingStr.contains(achivevement.toString())){
		                		  achieveUpdateFlag = "01";
		                	  }
		                  }  
		                  if(achieveUpdateFlag.equals("01")){
		                	  investor.setPerFlag("1");
		                  }
	                    }
	                    	investorService.updateAchievementPendingsById(investorId);
							investorService.saveAchievementPendingList(investorAchievementPendings);
					pending.setAchievementFalg("1");
					pending.setAchievementDesc("");
				  }else{
					  if (investor.getAchievementDesc() != null) {
							if (!investor.getAchievementDesc().equals(
								map.get("adFlag").toString())) {
								achieveUpdateFlag = "01";
		                    	investor.setPerFlag("1");
								pending.setAchievementDesc(map.get("adFlag").toString());
								pending.setAchievementFalg("2");
								investorService.updateAchievementPendingsById(investorId);
							}
						}
				  }
				pending.setManageUpdateFlag(manageUpdateFlag);
				pending.setAchieveUpdateFlag(achieveUpdateFlag);
				investorEditorService.addInvestorAuditPending(pending);
            	investorService.save(investor);

			}

			Object ob = "";
			rs = new ResultBean(Constant.SUCCESSMSG, Constant.SUCCESSCODE, ob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
		}
		this.writeJson(rs, request, response);
	}

	/**
	 * 提交机构信息编辑内容
	 * 
	 * @param rs
	 * @param request
	 * @param response
	 */
	/*
	 * @RequestMapping(value = "/updateInvestors") public void
	 * updateInvestors(ResultBean rs, HttpServletRequest request,
	 * HttpServletResponse response) { try { updateInvestor(rs, request,
	 * response); updateInvestorImg(rs, request, response);
	 * submitMangaeAchievementDesc(rs, request, response); rs = new
	 * ResultBean(Constant.SUCCESSMSG, Constant.SUCCESSCODE); } catch
	 * (UnsupportedEncodingException e) { rs = new
	 * ResultBean(Constant.ERRORCODE, Constant.ERRORMSG); e.printStackTrace();
	 * log.error(e.getMessage()); } this.writeJson(rs, request, response); }
	 */

	/**
	 * @Title: updateInvestor
	 * @Description: 当页面修改投资机构相关信息时相应的修改数据库对应信息或添加待审核表相关信息
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/baseEdit")
	public void updateInvestor(ResultBean rs, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		try {
			// 查看机构主要信息是否修改，
			Object ob = "";
			// 获取页面传来的参数
			String investorString = request.getParameter("investorData");
			// 根据登录的投资机构用户获取投资机构id
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			// 根据session中的机构id回去未修改的机构用户的信息
			Investor investor = investorService.findInverstorById(investorId);
			// 创建出待审核表对象
			InvestorAuditPending editorInvestor = new InvestorAuditPending();
			// 创建一个投资机构的中间表来接值
			Investor investorMiddle = new Investor();
			// 通过此方法将未修改的投资机构对象的值赋给待审核对象
			BeanUtils.copyProperties(investor, investorMiddle);
			if (investorString != null && !"".equals(investorString)
					&& !"\"\"".equals(investorString)) {
				investor = (Investor) JSON.parseObject(investorString,
						Investor.class);
			}
			// 给页面未涉及的内容赋值
//			if (investor.getBaseFlag().equals("0")
//					&& investor.getAuditStatus().equals("0")) {// 刚注册的机构用户
				if (investor.getAuditStatus().equals("0")) {// 刚注册的机构用户
				investor.setBaseFlag("1");
				investor.setPerFlag(investorMiddle.getPerFlag() == null ? ""
						: investorMiddle.getPerFlag());
				investor.setManageAchievementFalg(investorMiddle
						.getManageAchievementFalg() == null ? ""
						: investorMiddle.getManageAchievementFalg());
				investor.setManageAchievementDesc(investorMiddle
						.getManageAchievementDesc() == null ? ""
						: investorMiddle.getManageAchievementDesc());
				investor.setAchievementFalg(investorMiddle.getAchievementFalg() == null ? ""
						: investorMiddle.getAchievementFalg());
				investor.setAchievementDesc(investorMiddle.getAchievementDesc() == null ? ""
						: investorMiddle.getAchievementDesc());
				investor.setOperdate(new Date());
				investor.setCreateTime(investorMiddle.getCreateTime());
				investorService.save(investor);
			} else {
				if (investor.getBaseFlag().equals("1")||investor.getBaseFlag().equals("2")) {
			
				// 修改机构信息
				if (investor.getCerttype().equals(investorMiddle.getCerttype())
						&& investor.getCertno().equals(
								investorMiddle.getCertno())
						&& investor.getName().equals(investorMiddle.getName())
						&& investor.getRegisteredCapital().equals(
								investorMiddle.getRegisteredCapital())
						&& investor.getRegCurrency().equals(
								investorMiddle.getRegCurrency())
						&& investor.getLegalRepresentative().equals(
								investorMiddle.getLegalRepresentative())
						&& investor.getOrgType().equals(
								investorMiddle.getOrgType())
						&& investor.getRegisteredAddress().equals(
								investorMiddle.getRegisteredAddress())
						&& investor.getOfficeAddress().equals(
								investorMiddle.getOfficeAddress())
						&& investor.getOrganizationalForm().equals(
								investorMiddle.getOrganizationalForm())
						&& investor.getAreaCode().equals(
								investorMiddle.getAreaCode())
						&& investor.getLegalRepresentativeCall().equals(
								investorMiddle.getLegalRepresentativeCall())
						&& investor.getPaidCapital().equals(
								investorMiddle.getPaidCapital())
						&& investor.getPcCurrency().equals(
								investorMiddle.getPcCurrency())
						&& investor.getCapitalType().equals(
								investorMiddle.getCapitalType())
						&&(sdf.format(investor.getRegisteTime()).compareTo(sdf.format(investorMiddle.getRegisteTime()))==0)
						&& investor.getRelName().equals(
								investorMiddle.getRelName())
						&& investor.getRelPhone().equals(
								investorMiddle.getRelPhone())
						&& investor.getEmail()
								.equals(investorMiddle.getEmail())
						&& investor.getCorepersonnel().equals(
								investorMiddle.getCorepersonnel())
						&& investor.getCoreteam().equals(
								investorMiddle.getCoreteam())
						&& investor.getOperationQualification1().equals(
								investorMiddle.getOperationQualification1())
						&& investor.getOperationQualification2().equals(
								investorMiddle.getOperationQualification2())
						&& investor.getOperationQualification3().equals(
								investorMiddle.getOperationQualification3())
						&& investor.getTeamCount().equals(
								investorMiddle.getTeamCount())
						&& investor.getCreditUnhealthy().equals(
								investorMiddle.getCreditUnhealthy())
						&& investor.getSeniorManagement().equals(
								investorMiddle.getSeniorManagement())
						&& investor.getMechanism().equals(
								investorMiddle.getMechanism())
						&& investor
								.getCapitalMin()
								.toString()
								.equals(investorMiddle.getCapitalMin()
										.toString())
						&& investor
								.getCapitalMax()
								.toString()
								.equals(investorMiddle.getCapitalMax()
										.toString())
						&& investor.getCurrency().equals(
								investorMiddle.getCurrency())
						&& investor.getFinanceStage().equals(
								investorMiddle.getFinanceStage())	
						&& investor.getFinanceTrade().equals(
								investorMiddle.getFinanceTrade())) {
					investorMiddle.setBaseFlag("2");
					investorService.save(investorMiddle);
				} else {
					// 将修改后的值赋给待审核对象进行添加
					BeanUtils.copyProperties(investor, editorInvestor);
					// 当用户只修改了基本信息 未修改影像信息 将原先的影像信息付给待审核表
					editorInvestor.setFileName(investorMiddle.getFileName());
					editorInvestor.setLicensePath(investorMiddle
							.getLicensePath());
					editorInvestor.setLogoName(investorMiddle.getLogoName());
					editorInvestor.setLogoPath(investorMiddle.getLogoPath());
					editorInvestor.setAchievementFalg(investorMiddle
							.getAchievementFalg());
					editorInvestor.setAchievementDesc(investorMiddle
							.getAchievementDesc());
					editorInvestor.setCreditAuthorizationName(investorMiddle
							.getCreditAuthorizationName());
					editorInvestor.setCreditAuthorizationPath(investorMiddle
							.getCreditAuthorizationPath());
					editorInvestor.setRegisterAutographName(investorMiddle
							.getRegisterAutographName());
					editorInvestor.setRegisterAutographPath(investorMiddle
							.getRegisterAutographPath());
					editorInvestor.setManageAchievementFalg(investorMiddle
							.getManageAchievementFalg());
					editorInvestor.setManageAchievementDesc(investorMiddle
							.getManageAchievementDesc());
					editorInvestor.setOperdate(new Date());
					editorInvestor.setCreateTime(investorMiddle.getCreateTime());
					investorMiddle.setBaseFlag("1");	
					investorMiddle.setOperdate(new Date());
					investorService.save(investorMiddle);
					// 只要主要信息任意一条修改就往待审核表中添加一个对象
					investorEditorService.addInvestorAuditPending(editorInvestor);
				}
				}
			}
			rs = new ResultBean(Constant.SUCCESSMSG, Constant.SUCCESSCODE, ob);
		} catch (BeansException e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		this.writeJson(rs, request, response);
	}

	/**
	 * 
	 * @Title: findInvestorInfo
	 * @Description: 根据登录用户的投资机构id得到投资机构信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/investorInfo")
	public String findInvestorInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取登录投资机构用户的id
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			// 根据机构的id查询机构的基本信息
			Investor investor = investorService.findInverstorById(investorId);
			// 将investor中拟投资阶段与拟投资行业字典值切割并赋value值
			if (investor != null) {
				String[] splitFinanceStage = investor.getFinanceStage().split(
						"\\,");
				String[] splitFinanceTrade = investor.getFinanceTrade().split(
						"\\,");
				String financeStage = "";
				String financeTrade = "";
				for (int i = 0; i < splitFinanceStage.length; i++) {
					financeStage = financeStage
							+ ","
							+ RedisGetValue.getValueByCode(
									SRRPConstant.DD_INVESTMENT,
									splitFinanceStage[i]);
				}
				for (int i = 0; i < splitFinanceTrade.length; i++) {
					financeTrade = financeTrade
							+ ","
							+ RedisGetValue.getValueByCode(
									SRRPConstant.DD_INDUSTRY,
									splitFinanceTrade[i]);
				}
				financeStage = (financeStage).substring(1);
				financeTrade = (financeTrade).substring(1);
				investor.setFinanceStageDicname(financeStage);
				investor.setFinanceTradeDicname(financeTrade);
			}
			List<InvestorAuditRecord> investorAuditRecord = investorAuditService
					.getAuditRecordByInvestorId(investorId);
			request.setAttribute("investor", investor);
			request.setAttribute("investorAuditRecords",
					net.sf.json.JSONArray.fromObject(investorAuditRecord));
			return "WEB-INF/views/inverstorg/investorInfo";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: selectInvestor
	 * @Description: 根据页面传递过来的id查询机构信息回显
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/selecetInvestor")
	public String selectInvestor(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取投资机构id
			String investorId = request.getParameter("investorId");
			// 根据机构的id查询机构的基本信息做回显
			Investor investor = investorService.findInverstorById(investorId);
			// 获取机构类型字典值集合
			List<DD> ddOrgType = RedisGetValue
					.getDataList(SRRPConstant.DD_ORGTYPE);
			// 获取资本类型字典值集合
			List<DD> ddCapitaltype = RedisGetValue
					.getDataList(SRRPConstant.DD_CAPITALTYPE);
			// 获取拟投资阶段字典值集合
			List<DD> ddfinanceStage = RedisGetValue
					.getDataList(SRRPConstant.DD_INVESTMENT);
			// 获取拟投资行业字典值集合
			List<DD> ddfinanceTrade = RedisGetValue
					.getDataList(SRRPConstant.DD_INDUSTRY);
			// 获取证件代码类型字典值集合
			List<DD> ddcerttype = RedisGetValue
					.getDataList(SRRPConstant.DD_CERTIFICATE);
			// 获取证件代码类型字典值集合
			List<DD> ddarea = RedisGetValue.getDataList(SRRPConstant.DD_AREA);
			List<InvestorAuditRecord> investorAuditRecord = investorAuditService
					.getAuditRecordByInvestorId(investorId);
			request.setAttribute("investor", investor);
			request.setAttribute("ddOrgType", ddOrgType);
			request.setAttribute("ddcerttype", ddcerttype);
			request.setAttribute("ddfinanceTrade", ddfinanceTrade);
			request.setAttribute("ddfinanceStage", ddfinanceStage);
			request.setAttribute("ddCapitaltype", ddCapitaltype);
			request.setAttribute("ddarea", ddarea);
			request.setAttribute("investorAuditRecords",
					net.sf.json.JSONArray.fromObject(investorAuditRecord));
			return "WEB-INF/views/inverstorg/editInvestor";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: fileUpload @Description: 营业执照上传 @param file @param request @param
	 *         response @return @throws
	 */
	@RequestMapping("/fileUpload")
	public void fileUpload(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!file.isEmpty()) {
				// 定义输出流 将文件保存在D盘下的dataImage file.getOriginalFilename()为获得文件的名字
				String url = "F:/workspace/SRRPBusinesWeb/src/main/webapp/";
				String imgUrl = "static/images/";
				File files = new File(url + imgUrl);
				if (!files.exists()) {
					files.mkdirs(); // 创建文件
				}
				String path = files.getPath() + File.separator
						+ file.getOriginalFilename();
				FileOutputStream os = new FileOutputStream(path);
				InputStream in = file.getInputStream();
				int b = 0;
				while ((b = in.read()) != -1) { // 读取文件
					os.write(b);
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("code", "0");
				map.put("imgUrl", "../" + imgUrl + file.getOriginalFilename());
				this.writeJson(map, request, response);
				os.flush();
				os.close();// 关闭流
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/exportInvestorInfor")
	@ResponseBody
	public void exportInvestorInfor(ResultBean rs,
			GataWayInvestorRegiter registerInfo, HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			// String baseInfo = request.getParameter("baseInfo");
			Investor investor = investorService.findInverstorById(investorId);
			InvestorAuditPending editorInvestor = investorEditorService
					.selectInvestorAuditPending(investorId);
			
			// 入业务库
			List<InvestorAchievement> investorAchievement = investorService
					.findAchievementById(investorId);// 获得投资业绩的信息

			List<InvestorManageAchievement> investorManageAchievement = investorService
					.findAllManageAchievementById(investorId); // 获取管理业绩信息集合
			List<InvestorAchievementPending> investorAchievementPending = investorService
					.findAchievementspendingById(investorId);// 获取投资业绩的信息集合
			List<InvestorManageAchievementPending> manageAchievementPending = investorService
					.findAllManageAchievementPendingById(investorId); // 获取管理业绩信息集合

			List<DD> ddOrgForm = RedisGetValue
					.getDataList(SRRPConstant.DD_ORGFORM);
			List<DD> ddInvestment = RedisGetValue
					.getDataList(SRRPConstant.DD_INVESTMENT);
			List<DD> ddIndustrty = RedisGetValue
					.getDataList(SRRPConstant.DD_INDUSTRY);
			// List<DD> ddArea =
			// RedisGetValue.getDataList(SRRPConstant.DD_AREA);
			Map<Object, Object> o = new HashMap<Object, Object>();
			// 存入一个集合
			if("0".equals(investor.getAuditStatus())||SRRPConstant.EDIT_STATUS_PASS.equals(investor.getAuditStatus())||"22".equals(investor.getAuditStatus())){
				if("0".equals(investor.getAuditStatus())){
					editorInvestor.setAreaName(RedisGetValue.getValueByCode(
							SRRPConstant.DD_AREA, investor.getAreaCode()));
					o.put("investor", investor);
				}else{
					if(investor.getBaseFlag().equals("1")|| investor.getPerFlag().equals("1")){
						editorInvestor.setAreaName(RedisGetValue.getValueByCode(
								SRRPConstant.DD_AREA, investor.getAreaCode()));
						o.put("investor", editorInvestor);
					}else{
						investor.setAreaName(RedisGetValue.getValueByCode(
								SRRPConstant.DD_AREA, investor.getAreaCode()));
						o.put("investor", investor);
					}
				}

				
				
			}else{
				editorInvestor.setAreaName(RedisGetValue.getValueByCode(
						SRRPConstant.DD_AREA, investor.getAreaCode()));
				o.put("investor", editorInvestor);
			}
			
			o.put("ddOrgForm", ddOrgForm);
			o.put("ddInvestment", ddInvestment);
			o.put("ddIndustrty", ddIndustrty);
			if (manageAchievementPending.size() > 0) {
				o.put("investorManageAchievement", manageAchievementPending);
			} else {

				o.put("investorManageAchievement", investorManageAchievement);
			}

			if (investorAchievementPending.size() > 0) {
				o.put("investorAchievement", investorAchievementPending);
			} else {
				o.put("investorAchievement", investorAchievement);
			}
			String ftlPath = request.getSession().getServletContext()
					.getRealPath("/");
			ftlPath = ftlPath + "WEB-INF/views/inverstorg/";
			exportPDF(ftlPath, SRRPConstant.templatesName_invest, o, respons,
					SRRPConstant.ftlName_invest);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/exportWarrantInfor")
	@ResponseBody
	public void exportWarrantInfor(ResultBean rs,
			GataWayInvestorRegiter registerInfo, HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			Investor investor = investorService.findInverstorById(investorId);
			Map<Object, Object> o = new HashMap<Object, Object>();
			if (investor == null) {
				investor = new Investor();
			}
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			o.put("YEAR", String.valueOf(year));
			o.put("MONTH", (now.get(Calendar.MONTH) + 1));
			o.put("DAY_OF_MONTH", now.get(Calendar.DAY_OF_MONTH));
			o.put("investor", investor);
			String ftlPath = request.getSession().getServletContext()
					.getRealPath("/");
			ftlPath = ftlPath + "WEB-INF/views/inverstorg/";
			exportPDF(ftlPath, SRRPConstant.templatesName_warrant, o, respons,
					SRRPConstant.ftlName_warrant);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	@RequestMapping(value = "/exportJijinWarrantInfor")
	@ResponseBody
	public void exportInvestUserWarrantInfor(ResultBean rs,
			GataWayInvestorRegiter investorSubaccount, HttpServletRequest request,
			HttpServletResponse respons) {
		try {
//			InvestorSubaccount
			String subacName=request.getParameter("subacName");
			String certno=request.getParameter("certno");
			String relName=request.getParameter("relName");
			String relPhone=request.getParameter("relPhone");
			String legalRepresentative=request.getParameter("legalRepresentative");
			String legalRepresentativeCall=request.getParameter("legalRepresentativeCall");
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			Investor investor = investorService.findInverstorById(investorId);
//			Investor investor = new Investor();
	        investor.setLegalRepresentative(investor.getName()+"(委托代表:"+legalRepresentative+")");
			investor.setName(subacName);
			investor.setCertno(certno);
			if(certno.length()>10){
				investor.setCerttype("2");
			}else{
				investor.setCerttype("1");
			}
			
			investor.setLegalRepresentativeCall(legalRepresentativeCall);
			investor.setRelName(relName);
			investor.setRelPhone(relPhone);
			Map<Object, Object> o = new HashMap<Object, Object>();
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			o.put("YEAR", String.valueOf(year));
			o.put("MONTH", (now.get(Calendar.MONTH) + 1));
			o.put("DAY_OF_MONTH", now.get(Calendar.DAY_OF_MONTH));
			o.put("investor", investor);
			String ftlPath = request.getSession().getServletContext()
					.getRealPath("/");
			ftlPath = ftlPath + "WEB-INF/views/inverstorg/";
			exportPDF(ftlPath, SRRPConstant.templatesName_warrant, o, respons,
					SRRPConstant.ftlName_warrant);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	/**
	 * 
	 * @Title: readExcelModle @Description: TODO(批量上传) @param @param file @param @param
	 *         request @param @param response 参数说明 @return void 返回类型 @throws
	 */
	@RequestMapping("/readExcelModle")
	public void readExcelModle(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response,
			ResultBean rs) {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			List<InvestorManageAchievement> investorManageAchievements = new ArrayList<InvestorManageAchievement>();
			List<InvestorAchievement> investorAchievement = new ArrayList<InvestorAchievement>();
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			if (!file.isEmpty()) { // 判断文件是否存在
				 String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
				Workbook wb;

				// 根据文件后缀（xls/xlsx）进行判断
				if ("xls".equals(suffix)) {
					wb = new HSSFWorkbook(file.getInputStream());
				} else if ("xlsx".equals(suffix)) {
					OPCPackage opcPackage = OPCPackage.open(file.getInputStream());
//					XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
					wb = new XSSFWorkbook(opcPackage);
				} else {
					throw new Exception("文件类型错误");
				}
				int count = 0;
				boolean isGuiFan = false;
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					if (wb.getSheetName(i).equals("管理基金情况表")) {
						isGuiFan = true;
					}
				}
				if (!isGuiFan) {
					rs = new ResultBean(Constant.ERRORCODE,
							"失败原因：excel文件中没有管理基金情况表，请检查文件");
					this.writeJson(rs, request, response);
					return;
				}
				for (int i = 0; i < wb.getNumberOfSheets(); i++) {
					Sheet sheet = null;
					if (wb.getSheetName(i).equals("管理基金情况表")) {
						List<Object> results = new ArrayList<Object>();
						Map<Integer, String> beanpros = new HashMap<Integer, String>();
						// key:excel单元格的列号 ;value:excel单元格的值所对应的bean要set的属性;
						beanpros.put(0, "fundName");
						beanpros.put(1, "fundRegistDate");
						beanpros.put(2, "registAddressCode");
						beanpros.put(3, "trusteeship");
						beanpros.put(4, "manageCapitalMin");
						beanpros.put(5, "manageCapitalMax");
						beanpros.put(6, "currencyDicnameCode");
						beanpros.put(7, "iccFilingNo");
						beanpros.put(8, "foundTypeDicCode");
						beanpros.put(9, "financeTradeDicCode");
						beanpros.put(10, "financeStageDicCode");
						beanpros.put(11, "investmentProjects");
						beanpros.put(12, "cumulativeInvestment");
						beanpros.put(13, "ciCurrencyCode");
						beanpros.put(14, "implementExitProject");
						// 映射对象方法
						Class<?> clazz;
						clazz = Class
								.forName("com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievement");
						sheet = wb.getSheetAt(i);
						rs = ExcelUtil.checkManageDatasByrc(beanpros, results,
								sheet, rs, wb.getSheetName(i));
						if (rs.getCode().equals(Constant.SUCCESSCODE)) {
							// 读取excel数据映射到指定bean中 如过异常返回错误信息
							count += investorService
									.readManageAchievementExcel(file,
											investorId, sheet, beanpros, clazz,
											rs, count,
											investorManageAchievements);// 读取管理业绩并入库

						} else {
							this.writeJson(rs, request, response);
							return;
						}
					} else if ("填写说明".equals(wb.getSheetName(i))) {

					} else {
						List<Object> results = new ArrayList<Object>();
						sheet = wb.getSheetAt(i);
						Map<Integer, String> beanpros = new HashMap<Integer, String>();
						beanpros.put(0, "investmentFunds");
						beanpros.put(1, "investedEnterprise");
						beanpros.put(2, "investedEnterpriseIndustryCode");
						beanpros.put(3, "investedEnterpriseAreaCode");
						beanpros.put(4, "investmentTime");
						beanpros.put(5, "investmentAmount");
						beanpros.put(6, "iaCurrencyCode");
						beanpros.put(7, "shareRatio");
						beanpros.put(8, "roundOfInvestmentCode");
						beanpros.put(9, "investmentStageCode");
						beanpros.put(10, "enterpriseCapital");
						beanpros.put(11, "ecCurrencyCode");
						beanpros.put(12, "signOutCode");
						beanpros.put(13, "exitTime");
						beanpros.put(14, "rateOfReturn");
						Class<?> clazz;
						rs = ExcelUtil.checkDatasByrc(beanpros, results, sheet,
								rs, wb.getSheetName(i));
						if (rs.getCode().equals(Constant.SUCCESSCODE)) {
							clazz = Class
									.forName("com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievement");
							count = investorService.readAchievementExcel(file,
									investorId, sheet, beanpros, clazz, rs,
									count, investorAchievement);// 读取管理业绩并入库

						} else {
							this.writeJson(rs, request, response);
							return;
						}

					}
				}
				rs = new ResultBean(Constant.SUCCESSCODE, "导入成功，共导入" + count
						+ "条", investorManageAchievements, investorAchievement);
			}
			this.writeJson(rs, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.writeJson(map, request, response);
	}

	private File File() {
		// TODO Auto-generated method stub
		return null;
	}

	public void exportPDF(String ftlPath, String ftlName, Object data,
			HttpServletResponse respons, String fileName) {

		try {
			ByteArrayOutputStream bos;
			bos = PdfUtils.createPDF(ftlPath, ftlName, data);
			PdfUtils.renderPdf(respons, bos.toByteArray(), fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 回显行业二级
	public List<IndustryVo> checkIndustry(String industryStr) {
		List<DD> ddfinanceTrade = RedisGetValue
				.getDataList(SRRPConstant.DD_INDUSTRY);
		List<DD> ddfinanceTrade2 = RedisGetValue
				.getDataList(SRRPConstant.DD_INDUSTRY_2);
		String[] values = industryStr.toString().split(",");
		List<String> industryStrList = java.util.Arrays.asList(values);
		List<IndustryVo> list = new ArrayList<IndustryVo>();
		for (String industry : industryStrList) {
			IndustryVo vo = new IndustryVo();
			if (industry.length() == 2) {
				String name = RedisGetValue.getValueByCode(
						SRRPConstant.DD_INDUSTRY, industry);
				vo.setId(industry);
				vo.setName(name);
			} else {
				String name = RedisGetValue.getValueByCode(
						SRRPConstant.DD_INDUSTRY_2, industry);
				vo.setId(industry);
				vo.setName(name);
			}
			list.add(vo);
		}
		return list;
	}

	
}

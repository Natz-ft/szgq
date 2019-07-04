package com.icfcc.ssrp.web.managedept;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.IndustryVo;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.*;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResultNew;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.FinacingEventService;
import com.icfcc.SRRPService.enterprise.FinacingRecordService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.SRRPService.inverstorg.DemandInfoService;
import com.icfcc.SRRPService.inverstorg.FinacingDemandInfoService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: FinacingDemandManageController
 * @Description: TODO(融资进度查询)
 * @author hugt
 * @date 2017年10月18日 上午9:01:29
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/finacingManage")
public class FinacingDemandManageController extends SRRPBaseController {
	private static Logger log = LoggerFactory
			.getLogger(FinacingDemandManageController.class);

	// 融资需求信息查询
	@Autowired
	private FinacingDemandInfoService infoService;

	@Autowired
	private CompanyInfoService companyInfoService;

	@Autowired
	private DemandInfoService demandInfoService;

	@Autowired
	private PlatformUserService serService;
	@Autowired
	private InvestorService investorService;
	@Autowired
	private FinacingEventService finacingEventService;
	@Autowired
	private FinacingRecordService finacingRecordService;
	/**
	 * 
	 * @Title: controllerTest
	 * @Description: TODO(进入融资进度查询页面)
	 * @param @param request
	 * @param @param response
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/finacingManageInit")
	public String finacingManageInit(HttpServletRequest request,
			HttpServletResponse response) {
		return "WEB-INF/views/managedept/finacingDemand";
	}

	/**
	 * 
	 * @Title: initInfo
	 * @Description: TODO(查询融资进度列表)
	 * @param @param model
	 * @param @param page
	 * @param @param request
	 * @param @param response 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/initInfo")
	public void initInfo(Model model, PageBean page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryConditionString = request
					.getParameter("queryCondition");// 查询条件
			String currentPage = request.getParameter("start");// 前台分页传的当前页
			String maxSize = request.getParameter("limit");// 前台分页传的每页显示的条数
			// 查询条件对象需要传到Service,进行sql拼装
			QueryCondition queryCondition = new QueryCondition();

			if (queryConditionString != null
					&& !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				queryCondition = (QueryCondition) JSON.parseObject(
						queryConditionString, QueryCondition.class);
			}
			if (StringUtils.isNotBlank(currentPage)) {
				queryCondition.setCurPage(Integer.parseInt(currentPage));
			}
			if (StringUtils.isNotBlank(maxSize)) {
				queryCondition.setMaxSize(Integer.parseInt(maxSize));
			}
			
			String nameAndProjectName = queryCondition.getNameAndProjectName();
			if(nameAndProjectName!=null&&!"".equals(nameAndProjectName)) {
				Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
				String[] str = nameAndProjectName.split("~");
				StringBuffer sb = new StringBuffer();
				for (String s : str) {
					Matcher matcher = compile.matcher(s);
					if(matcher.find()) {
						String o = matcher.group();
						String n = String.valueOf(Double.valueOf(o)/100);
						if(n.endsWith(".0")) {
							n=n.substring(0,n.length()-2);
						}
						sb.append(s.replace(o, n)+"~");
					}
				}
				if(sb.toString().endsWith("~")) {
					nameAndProjectName = sb.toString().substring(0, sb.length()-1).replace("万", "百万");
				}
				queryCondition.setNameAndProjectName(nameAndProjectName);
			}
			
			// 条件查询融资信息列表
			List<FinacingDemandInfoResultNew> dataList = infoService.getFinacingDemandInfoList(queryCondition);
			int i = 0;
			for (FinacingDemandInfoResultNew finacingDemandInfoResult : dataList) {
				//System.out.print((++i)+"s----->"+finacingDemandInfoResult.getScore());
				if (StringUtils.isNotEmpty(finacingDemandInfoResult
						.getIndustry())) {
					String industryStr = finacingDemandInfoResult.getIndustry();// 获取数据库中行业的展示
					if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
						finacingDemandInfoResult
								.setIndustry2(finacingDemandInfoResult
										.getIndustry());
						finacingDemandInfoResult
								.setIndustryStr(finacingDemandInfoResult
										.getIndustry2Dicname());
					} else {// 如果是一级的行业显示以及行业
						finacingDemandInfoResult
								.setIndustry(finacingDemandInfoResult
										.getIndustry());
						finacingDemandInfoResult
								.setIndustryStr(finacingDemandInfoResult
										.getIndustryDicname());
					}
				}
				//System.out.println("e----->"+finacingDemandInfoResult.getScore());
			}
			page.setList(dataList);
			if (CollectionUtils.isNotEmpty(dataList)) {
				page.setList(dataList);
				// 设置总的条数
				Integer total = new Integer(String.valueOf(infoService
						.getFinacingDemandInfoCount(queryCondition)));
				page.setRecordCnt(total);
				if (StringUtils.isNotBlank(maxSize)) {
					page.setMaxSize(Integer.parseInt(maxSize));
				}
				if (StringUtils.isNotBlank(currentPage)) {
					page.setCurPage(Integer.parseInt(currentPage));
				}
				page.pageResult(dataList, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			// 将数据传输到前端
			this.writeJson(page, request, response);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "checkIndustry")
	public String checkIndustry(HttpServletRequest request,
			HttpServletResponse response) {
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
		System.out.println("json---------"+ net.sf.json.JSONArray.fromObject(map).toString());
		// String industryArr =
		// "{\"data\":[{id: 1, pId: 0, name:\" 互联网\", checked: false},{id: 11, pId: 1, name: \"IT\", checked: false}]}";
		request.setAttribute("industryJsonData",
				net.sf.json.JSONArray.fromObject(map));

		return "WEB-INF/views/managedept/industry";
	}
	
	/**
	 *查询需求详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findFinancingDemandDetail")
	public String findFinacingDemandById(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> userInfos = this.getUserInfo(request, response);
			// 我的需求-详情按钮
			String operate = request.getParameter("operate");
			String eventFlag = request.getParameter("eventFlag");
			// 获取页面传参
			String infoId = request.getParameter("demandInfoId");
			String enId = request.getParameter("enterpriseId");
			String enterpriseId = null;
			if (StringUtil.isNullOrEmpty(enId)) {
				enterpriseId = userInfos.get(SRRPConstant.LOGINORGNO);
			} else {
				enterpriseId = enId;
			}
			infoId = infoId.replaceAll("'", "");
			// 查询出企业的基本信息
			CompanyBase companyBase = companyInfoService
					.getCompanyBase(enterpriseId);
			// 查询企业补充基本信息
			CompanyBaseSupplement baseSupplement = companyInfoService
					.getCompanyBaseSupplement(enterpriseId);
			// 根据需求的id查询需求信息
			FinacingDemandInfo finacingDemandInfo = demandInfoService
					.getFinacingInfo(infoId);
			if (null != finacingDemandInfo.getAppointInvestor()
					&& !"".equals(finacingDemandInfo.getAppointInvestor())) {
				String investorId = finacingDemandInfo.getAppointInvestor();
				String[] investorIdArry = convertStrToArray(investorId);
				String investorNameList = "";
				// 通过遍历数组，机构名称进行拼接
				for (int i = 1; i < investorIdArry.length; i++) {
					if (i == investorIdArry.length - 1) {
						investorNameList = investorNameList
								+ investorService
										.findInvestorNameById(investorIdArry[i]);
					} else {
						investorNameList = investorNameList
								+ investorService
										.findInvestorNameById(investorIdArry[i])
								+ "  ,  ";
					}
				}
				request.setAttribute("investorNameList", investorNameList);
			}
			// 根据需求的id查询商业企划书
			CompanyBusinessplan businessPlanDTO = demandInfoService
					.getCompanyBusinessPlan(infoId);
			request.setAttribute("businessPlanDTO", businessPlanDTO);
			if (StringUtils.isNotEmpty(baseSupplement.getIndustry())) {
				String industryStr = baseSupplement.getIndustry();// 获取数据库中行业的展示
				if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
					baseSupplement.setIndustry2(baseSupplement.getIndustry());
					baseSupplement.setIndustryStr(baseSupplement
							.getIndustry2Dicname());
				} else {// 如果是一级的行业显示以及行业
					baseSupplement.setIndustry(baseSupplement.getIndustry());
					baseSupplement.setIndustryStr(baseSupplement
							.getIndustryDicname());
				}
			}
			// 根据融资事件的id与投资机构的id查询出融资机构的融资记录表
			List<UnionDemandInvestorResult> demandInvestorResults = finacingEventService
					.findDemandInvestorResult(infoId);
			for (UnionDemandInvestorResult unionDemandInvestorResult : demandInvestorResults) {
				// 主机构用户的用户名
				String investorCode = investorService.findInverstorById(
						unionDemandInvestorResult.getInvestorId()).getCertno();
				List<FinacingEvent> finacingEventList = (List<FinacingEvent>) finacingEventService
						.findFinacingEventByInfoIdAndInvestor(
								unionDemandInvestorResult.getInvestorId(),
								infoId);
				for (FinacingEvent finacingEvent : finacingEventList) {
					PlatformUser platformUser = serService
							.findUserByUserName(finacingEvent.getOperuser());
					finacingEvent.setRealUser(platformUser.getNickname());
					List<FinacingRecord> finacingRecordList = finacingRecordService
							.findFinacingRecordList(finacingEvent.getEventId(),
									finacingEvent.getInvestorgId());
					// 判断当前时间的操作用户是否为主机构用户，如果是的话，向主列表中添加融资纪录
					// 如果不是，就在当前时间的事件中添加记录列表
					if (finacingEvent.getOperuser().equals(investorCode)) {
						unionDemandInvestorResult
								.setFinacingRecordList(finacingRecordList);
						unionDemandInvestorResult.setPublishFlag(finacingEvent.getPublishFlag());
						unionDemandInvestorResult.setOrgStatus(finacingEvent.getStatus());
						unionDemandInvestorResult.setEventId(finacingEvent.getEventId());
					} else {
						finacingEvent.setFinacingRecordList(finacingRecordList);
						
					}
//					//添加融资纪录的更新方法
//					finacingRecordService.updateByeventId(finacingEvent.getEventId());
				}
				unionDemandInvestorResult
						.setFinacingEventList(finacingEventList);
			}
			request.setAttribute("demandInvestorResults", demandInvestorResults);
			request.setAttribute("companyBase", companyBase);
			request.setAttribute("baseSupplement", baseSupplement);
			request.setAttribute("finacingDemandInfo", finacingDemandInfo);
			request.setAttribute("operate", operate);
			request.setAttribute("eventFlag", eventFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/managedept/FinacingDemandDetail";
	}

	
	/**
	 * 根据企业的id查询企业的基本信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/findCompanyDetails")
	public String findCompanyDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 从页面接收企业的id根据企业的id查询企业的信息
			String enterpriseId = request.getParameter("enterpriseId");
			// 根据id查询基本信息
			CompanyBase companyBase = companyInfoService
					.getCompanyBase(enterpriseId);
			// 1.调接口 获取授权书和营业执照的name以及格式pdf/img
			// 2.如果存在获取的名字复制对象的fileName
			// 3..传前台一个标识 如果存在则下载的是工商的 如果不存在则显示本地的
			String authFlag="00";
			String linceFlag="00";
			CompanyBaseSupplement companyBaseSupplement = companyInfoService
					.getCompanyBaseSupplement(enterpriseId);
			// 根据id查询企业的待审核信息
			List<CompanyStockholder> companyStockholderList = companyInfoService
					.getCompanyStockholders(enterpriseId);
			List<CompanyProduct> companyProductList = companyInfoService
					.getCompanyProducts(enterpriseId);
			List<CompanyAttachment> companyAttachmentList = companyInfoService
					.getCompanyAttachments(enterpriseId);
			CompanyAttachmentPending companyAttachmentPending01 = companyInfoService
					.findAttachmentPendingByType(enterpriseId, "01");
			CompanyAttachmentPending companyAttachmentPending02 = companyInfoService
					.findAttachmentPendingByType(enterpriseId, "02");
			List<CompanyMember> companyMembers = companyInfoService
					.findCompanyMemberByEventId(enterpriseId);
			List<CompanyObjection> companyObjections=companyInfoService.getObjectionList(enterpriseId);
			String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
			String auditStatus = companyBase.getAuditStatus();
			if(companyBaseSupplement!=null){
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
			}
			
				CompanyAttachment companyAttachment01 = companyInfoService
						.findAttachmentByType(enterpriseId, "01");
				CompanyAttachment companyAttachment02 = companyInfoService
						.findAttachmentByType(enterpriseId, "02");
				if(companyBase.getPlatformFlag().equals("01")){
//					 if(companyAttachment01==null ||companyAttachment02==null){//如果没有在股权上传营业执照调用工商接口
						Map<String, String> map= companyInfoService.getQueryAuthName(enterpriseId);
						if(companyAttachment01==null){//不存在y
							 if(!map.get("QueryAuthName").toString().equals("01")){
								 companyBase.setCreditAuthorizationName(map.get("QueryAuthName").toString());
									companyBase.setAuthFlag("1");
								}else{
									companyBase.setAuthFlag("2");
								}
						}else{
							if(StringUtils.isEmpty(companyAttachment01.getFilepath())){//不存在y
								if(!map.get("QueryAuthName").toString().equals("01")){
									 companyBase.setCreditAuthorizationName(map.get("QueryAuthName").toString());
									 companyBase.setAuthFlag("1");
									}else{
										companyBase.setAuthFlag("2");
									}
							}else{
								companyBase.setAuthFlag("2");
							}
						}
						if(companyAttachment02==null){//
							if(!map.get("QueryLicenceName").toString().equals("01")){
							 companyBase.setLicenseName(map.get("QueryLicenceName").toString());
							 companyBase.setLinceFlag("1");
							}else{
								companyBase.setLinceFlag("2");
							}
						 }else{
								if(StringUtils.isEmpty(companyAttachment02.getFilepath())){//不存在y
									if(!map.get("QueryLicenceName").toString().equals("01")){
										 companyBase.setLicenseName(map.get("QueryLicenceName").toString());
										 companyBase.setLinceFlag("1");
										}else{
											companyBase.setLinceFlag("2");
										}
								}else{
									companyBase.setLinceFlag("2");
								}

						 }
//					 }
					 
				}
				request.setAttribute("companyBase", companyBase);
			request.setAttribute(
					"companyStockholderList",
					JSON.toJSONString(
							companyStockholderList,
							SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute(
					"companyObjections",
					JSON.toJSONString(
							companyObjections,
							SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("userType", userType);
			request.setAttribute("auditStatus", auditStatus);
			request.setAttribute("companyBaseSupplement", companyBaseSupplement);
			request.setAttribute("companyObjections", JSON.toJSONString(
					companyObjections,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyMembers", JSON.toJSONString(
					companyMembers,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyProductList", JSON.toJSONString(
					companyProductList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyAttachmentList", companyAttachmentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/managedept/enterpriseDetails";
	}
	
	/**
	 * 将用'_'拼接的字符串拆分成数组
	 * 
	 * @param str
	 * @return
	 */
	public static String[] convertStrToArray(String str) {
		String[] strArray = null;
		strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
		return strArray;
	}
	// @RequestMapping(value = "checkIndustryData")
	// public void checkIndustryData(HttpServletRequest request,
	// HttpServletResponse response) {
	// System.out.println("------222------");
	// String industryArr =
	// "{\"data\":[{id: 1, pId: 0, name:\" 互联网\", checked: false},{id: 11, pId: 1, name: \"IT\", checked: false}]}";
	// this.writeJson(JSON.parseObject(industryArr), request, response);
	// // return result;
	// }
	//
	public static void main(String[] args) {
		String va = "01";
		String va1 = "0102";
		System.out.println(va1.substring(0, 2));
	}
}

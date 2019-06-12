package com.icfcc.ssrp.web.enterprise;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.IndustryVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.QueryLog;
import com.icfcc.SRRPDao.jpa.entity.creditscore.score.RpCompanyCreditscores;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachment;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachmentPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBasePending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyMember;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjection;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjectionPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyProduct;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholder;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholderPending;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorRegiter;
import com.icfcc.SRRPDao.jpa.entity.managedept.MessageAlertInfo;
import com.icfcc.SRRPDao.jpa.entity.managedept.QueryCompanyAuditResult;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.creditscore.CreditScoreService;
import com.icfcc.SRRPService.creditscore.QueryLogService;
import com.icfcc.SRRPService.creditscore.client.WSWebServiceClient;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.gataway.staticize.GataWayIndexService;
import com.icfcc.SRRPService.managedept.ManageDissentService;
import com.icfcc.credit.platform.util.PdfUtils;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/enterprise")
public class CompanyInfoController extends SRRPBaseController {

	@Autowired
	private CompanyInfoService companyInfoService;
	// 调用通用上传方法
	@Autowired
	private GataWayIndexService service;
	@Autowired
	private PlatformUserService serService;
	@Autowired
	public WSWebServiceClient client;
	@Autowired
	private CreditScoreService scoreService;
	@Autowired
	private QueryLogService queryService;
	@Autowired
	private ManageDissentService manageDissentService;
	
	/**
	 * 初始化页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "enterpriseDetail")
	public String findEnterpriseDetail(HttpServletRequest request,
			HttpServletResponse response) {
		// 获得到登录用户的企业id
		try {
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
		// 获取工商接口返回企业补充信息，更新到本地库
		companyInfoService.getCompanyBaseSupplementVo(enterpriseId);
		// 根据企业的id查询企业的基本信息
		CompanyBase companyBase = companyInfoService.getCompanyBase(enterpriseId);
		// 查询待审核信息
		CompanyBasePending companyBasePending = companyInfoService
				.findCompanyBasePendingByEnterpriseId(enterpriseId);
		// 根据用户的id 查询企业用户的存量信息的字段
		String status = platformUser.getDesc2();
		if (StringUtils.isNotEmpty(status)) {
			if ("01".equals(status)) {
				// request.setAttribute("status", status);
				isShow = true;
			}
		}
		if (companyBasePending == null) {
			// 如果待审核表中为空的话查询基本信息表
			companyBasePending = new CompanyBasePending();
			// 将基本信息表里面的数据复制到待审核对象
			BeanUtils.copyProperties(companyBase, companyBasePending);
			companyBasePending.setRedate(companyBase.getRedate());

		}
		companyBasePending.setAuditStatusDicName(companyBase
				.getAuditStatusDicName());
		companyBasePending.setAuditStatusDicName(companyBase
				.getAuditStatusDicName());
		request.setAttribute("companyBase", companyBasePending);
		request.setAttribute("enterpriseId", enterpriseId);
		request.setAttribute("isShow", isShow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/enterprise/EnterpriseDetail";
	}

	/**
	 * 初始化编辑企业基本信息的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editEnterpriseDetail")
	public String editEnterpriseDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取登录用户的id
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			// 查询待审核表中是否有数据
			CompanyBase companyBase = new CompanyBase();
			CompanyBasePending companyBasePending = companyInfoService
					.findCompanyBasePendingByEnterpriseId(enterpriseId);
			if (companyBasePending == null) {
				// 如果待审核表中为空的话查询基本信息表
				companyBasePending = new CompanyBasePending();
				companyBase = companyInfoService.getCompanyBase(enterpriseId);
				// 将基本信息表里面的数据复制到待审核对象
				BeanUtils.copyProperties(companyBase, companyBasePending);
				companyBasePending.setRedate(companyBase.getRedate());
			}
			// 获得所属区域的字典值
			List<DD> ddAreaType = RedisGetValue
					.getDataList(SRRPConstant.DD_AREA);
			// 获取代码证类型的字典值
			List<DD> ddCodeType = RedisGetValue
					.getDataList(SRRPConstant.DD_CERTIFICATE);
			request.setAttribute("ddAreaType", ddAreaType);
			request.setAttribute("ddCodeType", ddCodeType);
			request.setAttribute("companyBasePending", companyBasePending);
			request.setAttribute("enterpriseId", enterpriseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/enterprise/EditEnterprise";

	}

	/**
	 * 提交更新企业的基本信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "updateEnterprise")
	public void updateEnterprise(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String companyBaseString = request.getParameter("companyInfo");
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			CompanyBasePending companyBasePending = null;
			// 将页面信息转成待审核信息表的对象
			if (companyBaseString != null && !"".equals(companyBaseString)
					&& !"\"\"".equals(companyBaseString)) {
				companyBasePending = (CompanyBasePending) JSON.parseObject(
						companyBaseString, CompanyBasePending.class);
			}
			companyBasePending.setOperdate(new Date());
			companyBasePending.setEnterpriseId(enterpriseId);
			// 将提交的信息转成对象存储到待审核表中分为两步
			// 1.查询待审核表中是否有数据
			CompanyBasePending companyBasePendingHis = companyInfoService
					.findCompanyBasePendingByEnterpriseId(enterpriseId);
			CompanyBase companyBase = companyInfoService
					.getCompanyBase(enterpriseId);
			companyBase.setAuditStatus("4");// 向数据库中插入审核状态，4是修改未审核
			companyBase.setStopFlag("1");// 输入启用停用状态，1是启用
			// 保存到数据库中
			companyInfoService.saveEnterprise(companyBase);

			// 判断
			if (companyBasePendingHis != null) {
				// 如果不为空时更新数据库中的数据
				// BeanUtils.copyProperties(companyBasePending,companyBasePendingHis);
				// companyBasePendingHis=companyBasePending;
				companyBasePending.setId(companyBasePendingHis.getId());
				companyInfoService.saveCompanyBasePending(companyBasePending);
			} else {
				companyInfoService.saveCompanyBasePending(companyBasePending);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询企业补充信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "enterpriseDetailSupplement")
	public String findEnterpriseSupplement(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String show = "";
			String authFlag="00";
			String linceFlag="00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Map<String, String> cate= new LinkedHashMap<String, String>();
			// 从缓存中获取登录用户企业的id
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			// 查询企业基本信息
			CompanyBase companyBase = companyInfoService.getCompanyBase(enterpriseId);
			
			CompanyBasePending companyBasePending = companyInfoService
					.findCompanyBasePendingByEnterpriseId(enterpriseId);
			if (companyBasePending == null) {
				// 如果待审核表中为空的话查询基本信息表
				companyBasePending = new CompanyBasePending();
				// 将基本信息表里面的数据复制到待审核对象
				BeanUtils.copyProperties(companyBase, companyBasePending);
				companyBasePending.setRedate(companyBase.getRedate());
			}
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String userId = userInfos.get(SRRPConstant.LOGINUSERID);// 获取用户的id
			// 根据用户的id查询用户信息用户
			PlatformUser platformUser = serService.getUser(userId);
			String userType=platformUser.getType();
			// 查询企业补充信息
			CompanyBaseSupplement companyBaseSupplement = companyInfoService
					.getCompanyBaseSupplement(enterpriseId);
			double countRatio = 0; // 获取股东类型的字典值
			int productNum = 0;// 定于主要技术产品数量
			List<DD> ddStockholderType = RedisGetValue
					.getDataList(SRRPConstant.DD_STOCKHOLDER);
			// 获取出资形式的字典集合
			List<DD> ddContributionType = RedisGetValue
					.getDataList(SRRPConstant.DD_INVESTMENTTYPR);
			// 获取融资阶段字类型典值集合
			List<DD> ddFinancingphase = RedisGetValue
					.getDataList(SRRPConstant.DD_INVESTMENT);
			// 获取企业相关附件字典类型典值集合
			List<DD> ddFileType = RedisGetValue
					.getDataList(SRRPConstant.DD_FILETYPE);

			List<DD> ddAreaType = RedisGetValue
					.getDataList(SRRPConstant.DD_AREA);
			List<DD> ddAreanNews = new ArrayList<DD>();
			for (DD dd : ddAreaType) {
				if ("3205".equals(dd.getDicCode().substring(0, 4))) {
					ddAreanNews.add(dd);
				}
			}
			String code=companyBase.getSources();
			String codePending="";
			String[] codes;
			if(companyBasePending!=null){
				codePending=companyBasePending.getSources();
			}else{
				codePending="isNo";
			}
			if(!"isNo".equals(code)){
				//当数据源不是“isNo”的时候拆分字符串
					codes=code.split(",");
				for (String key: codes) {
					if(!("00".equals(key)||"isNo".equals(key))){
						String value=RedisGetValue.getValueByCode(SRRPConstant.DD_OBJECTION_TYPE, key);
						cate.put(key, value);
					}
				}
				
			}else{
				if(!"isNo".equals(codePending)){
					codes=codePending.split(",");
				}else{
					codes=code.split(",");
				}
				for (String key: codes) {
					if(!"00".equals(key)){
						String value=RedisGetValue.getValueByCode(SRRPConstant.DD_OBJECTION_TYPE, key);
						cate.put(key, value);
					}
				}
			}
			// 获取代码证类型的字典值
			List<DD> ddCodeType = RedisGetValue
					.getDataList(SRRPConstant.DD_CERTIFICATE);
			request.setAttribute("ddAreaType", ddAreanNews);
			request.setAttribute("ddCodeType", ddCodeType);
			// 查询股东信息列表
			List<CompanyStockholder> companyStockholderList = companyInfoService
					.getCompanyStockholders(enterpriseId);
			// 查询股东待审核信息
			List<CompanyStockholderPending> stockholderPendingList = companyInfoService
					.getCompanyStockholderPendings(enterpriseId);
			// 查询企业的团队成员列表
			List<CompanyMember> companyMemberList = companyInfoService
					.findCompanyMemberByEventId(enterpriseId);
			// 查询相关附件列表
			List<QueryCompanyAuditResult> companyAuditResultList = companyInfoService
					.getAuditList(enterpriseId);
			List<CompanyAttachment> companyAttachmentList = companyInfoService
					.getCompanyAttachments(enterpriseId);
			// 营业执照
			CompanyAttachmentPending comAttchPending02 = companyInfoService
					.findAttachmentPendingByType(enterpriseId, "02");
			// 查询技术产品信息列表
			List<CompanyProduct> companyProductList = companyInfoService
					.getCompanyProducts(enterpriseId);
			List<CompanyObjection> companyObjections=companyInfoService.getObjectionList(enterpriseId);
			List<CompanyObjectionPending> companyObjectionPendings=companyInfoService.getObjectionPendingList(enterpriseId);
			if ("1".equals(companyBasePending.getSources())) {// 如果数据源为工商的话
				show = "1";
			} else {
				show = "0";
			}
			for (QueryCompanyAuditResult queryCompanyAuditResult : companyAuditResultList) {
				queryCompanyAuditResult.setAuditTimeStr(sdf
						.format(queryCompanyAuditResult.getAuditTime()));// 审核时间格式化
				queryCompanyAuditResult.setAuditStatusStr(RedisGetValue
						.getValueByCode(SRRPConstant.DD_COMPANY_AUSTSTAUS,
								queryCompanyAuditResult.getAuditStatus()));
			}
			if (companyAttachmentList != null
					&& companyAttachmentList.size() > 0) {
				for (CompanyAttachment companyAttachment : companyAttachmentList) {
					companyAttachment.setFileTypeName(RedisGetValue
							.getValueByCode(SRRPConstant.DD_FILETYPE,
									companyAttachment.getFileType()));
				}
			}
			if (comAttchPending02 != null) {// 如果待审核信息中有数据则
				companyBasePending.setLicenseName(comAttchPending02.getFileName());
				companyBasePending.setLicensePath(comAttchPending02.getFilepath());
				if(comAttchPending02.getFilepath()==null || comAttchPending02.getFilepath().equals("")){
					linceFlag="01";
				}
			} else {
				CompanyAttachment companyAttachment02 = companyInfoService.findAttachmentByType(enterpriseId, "02");
				if (companyAttachment02 != null) {
					companyBasePending.setLicenseName(companyAttachment02.getFileName());
					companyBasePending.setLicensePath(companyAttachment02.getFilepath());
					if(companyAttachment02.getFilepath()==null || companyAttachment02.getFilepath().equals("")){
						linceFlag="01";
					}
				}else{
					linceFlag="01";
				}
			}
			// 征信授权书
			CompanyAttachmentPending comAttchPending01 = companyInfoService
					.findAttachmentPendingByType(enterpriseId, "01");
			if (comAttchPending01 != null) {
				companyBasePending.setCreditAuthorizationName(comAttchPending01
						.getFileName());
				companyBasePending.setCreditAuthorizationPath(comAttchPending01
						.getFilepath());
				if(comAttchPending01.getFilepath()==null || comAttchPending01.getFilepath().equals("")){
					authFlag="01";
				}
			} else {
				CompanyAttachment companyAttachment01 = companyInfoService
						.findAttachmentByType(enterpriseId, "01");
				if (companyAttachment01 != null) {
					companyBasePending
							.setCreditAuthorizationName(companyAttachment01
									.getFileName());
					companyBasePending
							.setCreditAuthorizationPath(companyAttachment01
									.getFilepath());
					if(companyAttachment01.getFilepath()==null || companyAttachment01.getFilepath().equals("")){
						authFlag="01";
					}
				}else{
					authFlag="01";
				}
			}
			if(companyBase.getPlatformFlag().equals("01")){
				 if(linceFlag.equals("01") ||authFlag.equals("01")){//如果没有在股权上传营业执照调用工商接口
					Map<String, String> map= companyInfoService.getQueryAuthName(enterpriseId);
					if(authFlag.equals("01")){//不存在y
						 if(!map.get("QueryAuthName").toString().equals("01")){
							 companyBasePending.setCreditAuthorizationName(map.get("QueryAuthName").toString());
							 companyBasePending.setAuthFlag("1");
							}else{
								companyBasePending.setAuthFlag("2");
							}
					}
					if(linceFlag.equals("01")){//不存在授权书
						if(!map.get("QueryLicenceName").toString().equals("01")){
							companyBasePending.setLicenseName(map.get("QueryLicenceName").toString());
							companyBasePending.setLinceFlag("1");
						}else{
							companyBasePending.setLinceFlag("2");
						}
					 }
				 }
				 
			}
			
			// 背书影像
			CompanyAttachment companyAttachment04 = companyInfoService
					.findAttachmentByType(enterpriseId, "04");
			if (companyAttachment04 != null) {
				companyBasePending.setRegisterAutographName(companyAttachment04
						.getFileName());
				companyBasePending.setRegisterAutographPath(companyAttachment04
						.getFilepath());
			}
			// 商业计划书
			CompanyAttachment companyAttachment03 = companyInfoService
					.findAttachmentByType(enterpriseId, "03");
			if (companyAttachment03 != null) {
				companyBasePending.setBussinessPlanName(companyAttachment03
						.getFileName());
				companyBasePending.setBussinessPlanPath(companyAttachment03
						.getFilepath());
			}
			if (companyBaseSupplement == null) {
			} else {
				if (StringUtils.isNotEmpty(companyBaseSupplement.getIndustry())) {
					String industryStr = companyBaseSupplement.getIndustry();
					if (industryStr.length() == 4) {
						companyBaseSupplement.setIndustry(industryStr
								.substring(0, 2));
						companyBaseSupplement.setIndustry2(industryStr);
					}
				}
				request.setAttribute("companyBaseSupplement",
						companyBaseSupplement);
			}
			if (stockholderPendingList.size()!=0) {// 如果待审核信息表中有数据则展示待审核表中的数据
				//首先清空正式表里面的数据
				companyStockholderList.clear();
				for (CompanyStockholderPending companyStockholderPending : stockholderPendingList) {
					CompanyStockholder stockholder = new CompanyStockholder();
					// 复制待审核信息到审核信息中进行展示
					BeanUtils.copyProperties(companyStockholderPending,
							stockholder);
					stockholder.setContribution(companyStockholderPending
							.getContribution());
					companyStockholderList.add(stockholder);
				}
			}
			for (CompanyStockholder companyStockholder : companyStockholderList) {
				if(companyStockholder.getRatio()!=null && !"".equals(companyStockholder.getRatio())){
					countRatio += companyStockholder.getRatio();
				}
				
			}
			if(companyObjectionPendings.size()!=0){
				companyObjections.clear();
				for (CompanyObjectionPending companyObjectionPending : companyObjectionPendings) {
					CompanyObjection objection = new CompanyObjection();
					BeanUtils.copyProperties(companyObjectionPending, objection);
					companyObjections.add(objection);
				}
			}
			request.setAttribute("companyBase", companyBase);
			request.setAttribute("companyBasePending", companyBasePending);
			productNum = companyProductList.size();
			request.setAttribute("companyStockholderList", JSON.toJSONString(
					companyStockholderList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyMemberList", JSON.toJSONString(
					companyMemberList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyProductList", JSON.toJSONString(
					companyProductList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyObjections", JSON.toJSONString(
					companyObjections,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyAttachmentList", JSON.toJSONString(
					companyAttachmentList,
					SerializerFeature.DisableCircularReferenceDetect));
			// 向页面中传递参数
			request.setAttribute("companyAuditResultList", JSON.toJSONString(
					companyAuditResultList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("ddStockholderType", ddStockholderType);
			request.setAttribute("ddContributionType", ddContributionType);
			// 获取行业类型字典值集合
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

			request.setAttribute("industryJsonData",
					net.sf.json.JSONArray.fromObject(map));
			request.setAttribute("cate", cate);
			request.setAttribute("countRatio", countRatio);
			request.setAttribute("show", show);
			request.setAttribute("userType", userType);
			request.setAttribute("productNum", productNum);
			request.setAttribute("enterpriseId", enterpriseId);
			request.setAttribute("ddFinancingphase", ddFinancingphase);
			request.setAttribute("ddFileType", ddFileType);
			return "WEB-INF/views/enterprise/EnterpriseSupplement";

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新企业补充信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateEnterpriseDetailSupplement")
	public void addEnterpriseSupplement(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try {
			Boolean changeFlag=false;
			String companyBasePendingString = request
					.getParameter("companyBasePending");
			String stockFlag = request.getParameter("stockFlag");
			String stockChangFlag="00";
			String companyStockholderString = request
					.getParameter("companyStockholder");
			String objectionString = request
					.getParameter("objection");//接收页面的参数
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			// 查询条件对象需要传到Service,进行sql拼装
			CompanyBaseSupplement companyBaseSupplement = new CompanyBaseSupplement();
			// 根据企业id查询企业的基本信息
			CompanyBase companyBase = companyInfoService
								.getCompanyBase(enterpriseId);
			String oldStatus=companyBase.getAuditStatus();
			// 构造一个待审核信息对象
			CompanyBasePending companyBasePending = new CompanyBasePending();
			if (companyBasePendingString != null
					&& !"".equals(companyBasePendingString)
					&& !"\"\"".equals(companyBasePendingString)) {
				companyBasePending = (CompanyBasePending) JSON.parseObject(
						companyBasePendingString, CompanyBasePending.class);
			}
			if(companyBase!=null){
				if(!companyBasePending.getName().equals(companyBase.getName())){
					changeFlag=true;
				}
				if(companyBasePending.getLegalName()!=null && companyBase.getLegalName()!=null){
					if(!companyBasePending.getLegalName().equals(companyBase.getLegalName())){
					changeFlag=true;
				}
				}
				if(companyBase.getCodetype()!=null && companyBase.getCodetype()!=null){
					if(!companyBasePending.getCodetype().equals(companyBase.getCodetype())){
						changeFlag=true;
					}
				}
				if(companyBasePending.getCode()!=null && companyBase.getCode()!=null){
					if(!companyBasePending.getCode().equals(companyBase.getCode())){
						changeFlag=true;
					}
				}
				if(companyBasePending.getRegistArea()!=null && companyBase.getRegistArea()!=null){
					if(!companyBasePending.getRegistArea().equals(companyBase.getRegistArea())){
						changeFlag=true;
					}
				}
				if(companyBasePending.getObjection()!=null&&!"".equals(companyBasePending.getObjection())){
					if(companyBase.getObjection()==null){//当选择前没有选择之后有数据会进行修改状态
						changeFlag=true;
					}else if(!companyBase.getObjection().equals(companyBasePending.getObjection())){//修改前后不一致也会修改状态
						changeFlag=true;
					}
				}
//				companyBase.setObjection(companyBasePending.getObjection());
				if(companyBasePending.getRegcapital()!=null && companyBase.getRegcapital()!=null){
					if((companyBasePending.getRegcapital().compareTo(companyBase.getRegcapital()))!=0){
						changeFlag=true;
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
				if(companyBase.getRedate()!=null  && companyBasePending.getRedate()!=null){
					Date sqlDate = new java.sql.Date(companyBase.getRedate().getTime());
					if(sdf.format(sqlDate).compareTo(sdf.format(companyBasePending.getRedate()))!=0){
						changeFlag=true;
					}
				}
				if(companyBasePending.getRearea()!=null && companyBase.getRearea()!=null){
					if(!companyBasePending.getRearea().equals(companyBase.getRearea())){
					    changeFlag=true;	
					}
				}
			}
			CompanyAttachment attachment02 = companyInfoService
					.findAttachmentByType(enterpriseId, "02");
			if(attachment02!=null){
				if(!companyBasePending.getLicensePath().equals(attachment02.getFilepath())){
					changeFlag=true;
				}
			}
			CompanyAttachment attachment01 = companyInfoService
					.findAttachmentByType(enterpriseId, "01");
			//如果以上数据进行了修改则
			String stockAddFlag =companyBasePending.getStockAddFlag();//获得增加标记
			String stockDelFlag =companyBasePending.getStockDelFlag();//获得删除标记
			List<CompanyStockholderPending> ss = new ArrayList<CompanyStockholderPending>();
			if ("01".equals(stockFlag)) {// 新增
				if (companyStockholderString != null
						&& !"".equals(companyStockholderString)
						&& !"\"\"".equals(companyStockholderString)) {
					JSONArray jsonArray = JSONArray
							.fromObject(companyStockholderString);
					ss = (List) JSONArray.toCollection(jsonArray,
							CompanyStockholderPending.class);
				}
				List<CompanyStockholderPending> ss1 = companyInfoService
						.findAll(enterpriseId);// 茶招待审核表中的数据
				if (ss1.size()==0) {// 如果待审核表中没有数据向待审核表中添加数据
					companyInfoService.saveCompanyStockPendings(ss);
				} else {// 如果待审核表中有数据首先删除待审核表中的数据，再向待审核表中插入数据
					companyInfoService.deleteById(enterpriseId);
					companyInfoService.saveCompanyStockPendings(ss);
				}
				//根据企业的id查询股东信息表中的数据
				List<CompanyStockholder> ssd1 = companyInfoService.getCompanyStockholders(enterpriseId);
				if(ss.size()!=ssd1.size()){
					stockChangFlag="01";
				}else{
					if("01".equals(stockAddFlag)&&"01".equals(stockDelFlag)){
						stockChangFlag="01";
					}else{
						stockChangFlag="00";
					}
				}
			}
			if("0".equals(companyBasePending.getObjectionIs())){//如果选泽的是"是"的情况下
				List<CompanyObjectionPending> cop=new ArrayList<CompanyObjectionPending>();
				String objAddFlag=companyBasePending.getObjAddFlag();
				String objDelFlag=companyBasePending.getObjDelFlag();
				if("01".equals(objAddFlag)){//进行了增加操作
					if(objectionString!=null&& !"".equals(objectionString)
							&& !"\"\"".equals(objectionString)){
						JSONArray jsonArray = JSONArray
								.fromObject(objectionString);
						cop = (List) JSONArray.toCollection(jsonArray,
								CompanyObjectionPending.class);
					}	
					List<CompanyObjectionPending> cop1=companyInfoService.getObjectionPendingList(enterpriseId);
					if(cop1.size()>0){
						 companyInfoService.delObjectionPendingByEnterpriseId(enterpriseId);
					}
					for (CompanyObjectionPending companyObjectionPending : cop) {
						companyObjectionPending.setObjId(null);
						companyInfoService.addObjectionPending(companyObjectionPending);
					}
					changeFlag=true;
					companyBasePending.setObjecChangFlag("1");
					companyBase.setObjecChangFlag("1");
				}else{//如果没有进行增加操作，只进行了删除操作，则更新正式表里的数据
					if("01".equals(objDelFlag)){
					if("03".equals(companyBase.getAuditStatus())||"13".equals(companyBase.getAuditStatus())||"23".equals(companyBase.getAuditStatus())||"16".equals(companyBase.getAuditStatus())){//如果当前的状态是审核不通过状态，则向待审核表中插入数据
						if(objectionString!=null&& !"".equals(objectionString)
								&& !"\"\"".equals(objectionString)){
							JSONArray jsonArray = JSONArray
									.fromObject(objectionString);
							cop = (List) JSONArray.toCollection(jsonArray,
									CompanyObjectionPending.class);
						}	
						List<CompanyObjectionPending> cop1=companyInfoService.getObjectionPendingList(enterpriseId);
						if(cop1.size()>0){
							 companyInfoService.delObjectionPendingByEnterpriseId(enterpriseId);
						}
						for (CompanyObjectionPending companyObjectionPending : cop) {
							companyObjectionPending.setObjId(null);
							companyInfoService.addObjectionPending(companyObjectionPending);
						}
						changeFlag=true;
						companyBasePending.setObjecChangFlag("1");
						companyBase.setObjecChangFlag("1");
						}else{
							companyInfoService.delObjectionByEnterpriseId(enterpriseId);
							List<CompanyObjection> co= new ArrayList<CompanyObjection>();
							if(objectionString!=null&& !"".equals(objectionString)
									&& !"\"\"".equals(objectionString)){
								JSONArray jsonArray = JSONArray
										.fromObject(objectionString);
								co = (List) JSONArray.toCollection(jsonArray,
										CompanyObjection.class);
							}
							for (CompanyObjection companyObjection : co) {
								companyInfoService.addObjection(companyObjection);
							}
						}
					}
				}
			}else{//如果选择的是否则清除两个表中的数据
				companyInfoService.delObjectionPendingByEnterpriseId(enterpriseId);
				companyInfoService.delObjectionByEnterpriseId(enterpriseId);
				//同时将字符串置空
				objectionString="";
			}
			
			if(stockChangFlag.equals("01")){
				changeFlag=true;
			}
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			if ("00".equals(companyBase.getAuditStatus())) {//新增数据
				//如果当前状态的为未进行信息补充
                //向基本表中插入企业的基本信息
				companyBase.setLegalCal(companyBasePending.getLegalCal());
				companyBase.setStockName(companyBasePending.getStockName());
				companyBase.setStockCal(companyBasePending.getStockCal());
				companyBase.setRegistArea(companyBasePending.getRegistArea());
				companyBase.setRearea(companyBasePending.getRearea());
				companyBase.setRedate(companyBasePending.getRedate());
				companyBase.setObjection(companyBasePending.getObjection());
				companyBase.setRegcapital(companyBasePending.getRegcapital());
				companyBase.setRegCurrency(companyBasePending.getRegCurrency());
				companyBase.setRegistArea(companyBasePending.getRegistArea());
				companyBase.setLegalName(companyBasePending.getLegalName());
				companyBase.setAuditStatus("01");
				companyBase.setAuditStage("03");
				stockChangFlag="00";
				//同时向股东信息表中插数据
				List<CompanyStockholder> companyStockholders= new ArrayList<CompanyStockholder>();
				if (companyStockholderString != null
						&& !"".equals(companyStockholderString)
						&& !"\"\"".equals(companyStockholderString)) {
					JSONArray jsonArray = JSONArray
							.fromObject(companyStockholderString);
					companyStockholders = (List) JSONArray.toCollection(jsonArray,
							CompanyStockholder.class);
				}
				for (CompanyStockholder companyStockholder : companyStockholders) {
					companyInfoService.addCompanyStockholder(companyStockholder);
				}
				List<CompanyObjection> co= new ArrayList<CompanyObjection>();
				if(objectionString!=null&& !"".equals(objectionString)
						&& !"\"\"".equals(objectionString)){
					JSONArray jsonArray = JSONArray
							.fromObject(objectionString);
					co = (List) JSONArray.toCollection(jsonArray,
							CompanyObjection.class);
				}
				companyInfoService.addObjectionList(co);
				// 构造一个待审核信息对象历史数据
				CompanyBasePending companyBasePendingHis = new CompanyBasePending();
				companyBasePendingHis = companyInfoService
						.findCompanyBasePendingByEnterpriseId(enterpriseId);
				companyBasePending.setCreateTime(companyBase.getCreateTime());
				companyBasePending.setOperdate(new Date());
				// 判断待审核表中是否有数据
				if (companyBasePendingHis != null) {
					companyBasePending.setId(companyBasePendingHis.getId());
					companyBasePending.setEnterpriseId(enterpriseId);
					companyBasePending.setPlatformFlag(companyBase.getPlatformFlag());
					companyInfoService.saveCompanyBasePending(companyBasePending);
				} else {
					companyBasePending.setEnterpriseId(enterpriseId);
					companyInfoService.saveCompanyBasePending(companyBasePending);
				}
				
				if (StringUtils.isNotEmpty(companyBasePending.getRearea())) {
					companyBase.setRearea(companyBasePending.getRearea());
				}
				changeFlag=true;
				
				
			}else if ("03".equals(companyBase.getAuditStatus())) {// 如果为注册初审不通过则将审核状态改为注册未初审
				companyBase.setLegalCal(companyBasePending.getLegalCal());
				companyBase.setStockName(companyBasePending.getStockName());
				companyBase.setStockCal(companyBasePending.getStockCal());
				CompanyBasePending companyBasePendingHis = new CompanyBasePending();
				companyBasePendingHis = companyInfoService
						.findCompanyBasePendingByEnterpriseId(enterpriseId);
				companyBasePending.setOperdate(new Date());
				companyBasePending.setStockFlag(stockChangFlag);
				companyBasePending.setCreateTime(companyBase.getCreateTime());
				// 判断待审核表中是否有数据
				if (companyBasePendingHis != null) {
					companyBasePending.setPlatformFlag(companyBase.getPlatformFlag());
					companyBasePending.setId(companyBasePendingHis.getId());
					companyBasePending.setEnterpriseId(enterpriseId);
					companyInfoService.saveCompanyBasePending(companyBasePending);
				} else {
					companyBasePending.setEnterpriseId(enterpriseId);
					companyInfoService.saveCompanyBasePending(companyBasePending);
				}
				companyBase.setAuditStatus("01");
				changeFlag=true;
			}  else if ("23".equals(companyBase.getAuditStatus())) {// 如果为激活不通过则将审核状态改为注册未激活
				companyBase.setLegalCal(companyBasePending.getLegalCal());
				companyBase.setStockName(companyBasePending.getStockName());
				companyBase.setStockCal(companyBasePending.getStockCal());
				CompanyBasePending companyBasePendingHis = new CompanyBasePending();
				companyBasePending.setPlatformFlag(companyBase.getPlatformFlag());
				companyBasePendingHis = companyInfoService
						.findCompanyBasePendingByEnterpriseId(enterpriseId);
				companyBasePending.setOperdate(new Date());
				
				companyBasePending.setCreateTime(companyBase.getCreateTime());
				companyBasePending.setStockFlag(stockChangFlag);
				// 判断待审核表中是否有数据
				if (companyBasePendingHis != null) {
					companyBasePending.setId(companyBasePendingHis.getId());
					companyBasePending.setEnterpriseId(enterpriseId);
					companyInfoService.saveCompanyBasePending(companyBasePending);
				} else {
					companyBasePending.setEnterpriseId(enterpriseId);
					companyInfoService.saveCompanyBasePending(companyBasePending);
				}
				companyBase.setAuditStatus("02");
				changeFlag=true;
			}else if(changeFlag==false){//只要数据进行了修改就向待审核表中插入数据，更改状态，如果没有进行修改就正常的去提交到基本表中
				
				BeanUtils.copyProperties(companyBasePending, companyBase);
				if (StringUtils.isNotEmpty(companyBasePending.getRearea())) {
					companyBase.setRearea(companyBasePending.getRearea());
				}
				companyBase.setAuditStatus(oldStatus);
				companyBase.setEnterpriseId(enterpriseId);
				companyBase.setStockFlag(stockChangFlag);
			}else{
				companyBase.setLegalCal(companyBasePending.getLegalCal());
				companyBase.setStockName(companyBasePending.getStockName());
				companyBase.setStockCal(companyBasePending.getStockCal());
				 if ("06".equals(companyBase.getAuditStatus())) {// 如果为修改终审不通过则将审核状态改为注册未终审
						companyBase.setAuditStatus("04");
				} else if ("01".equals(companyBase.getAuditStatus())) {// 如果为注册初审未审核则状态依然为注册初审未审核
					companyBase.setAuditStatus("01");
				} else if ("02".equals(companyBase.getAuditStatus())) {// 如果为注册复审未审核则状态依然为注册复审未审核
					companyBase.setAuditStatus("01");
				} else if ("22".equals(companyBase.getAuditStatus())) {// 如果为未激活则将审核状态改为注册未激活
					companyBase.setAuditStatus("04");
				} else {
					companyBase.setAuditStatus("04");
				}
				// 构造一个待审核信息对象历史数据
				CompanyBasePending companyBasePendingHis = new CompanyBasePending();
				companyBasePendingHis = companyInfoService
						.findCompanyBasePendingByEnterpriseId(enterpriseId);
				companyBasePending.setOperdate(new Date());
				companyBasePending.setCreateTime(companyBase.getCreateTime());
				companyBasePending.setStockFlag(stockChangFlag);
				companyBasePending.setPlatformFlag(companyBase.getPlatformFlag());
				// 判断待审核表中是否有数据
				if (companyBasePendingHis != null) {
					companyBasePending.setId(companyBasePendingHis.getId());
					companyBasePending.setEnterpriseId(enterpriseId);
					companyInfoService.saveCompanyBasePending(companyBasePending);
				} else {
					companyBasePending.setEnterpriseId(enterpriseId);
					companyInfoService.saveCompanyBasePending(companyBasePending);
				}
				companyBase.setStockFlag(stockChangFlag);
			}
			companyBase.setOperdate(new Date());
			companyInfoService.saveEnterprise(companyBase);// 保存企业基本信息
			String userId = userInfos.get(SRRPConstant.LOGINUSERID);// 获取用户的id
			// 根据用户的id查询用户信息用户
			PlatformUser platformUser = serService.getUser(userId);
			// 从缓存中获取登录用户企业的id
			companyBaseSupplement.setEnterpriseId(enterpriseId);
			//向企业补充信息表中插入数据
			if (StringUtils.isNotEmpty(companyBasePending.getIndustry2())) {
				companyBaseSupplement.setIndustry(companyBasePending
						.getIndustry2());
			} else {
				companyBaseSupplement.setIndustry(companyBasePending
						.getIndustry());
			}
			if (companyBasePending.getPaidincapital() != null) {
				companyBaseSupplement.setPaidincapital(companyBasePending
						.getPaidincapital());
			}
			if (StringUtils.isNotEmpty(companyBasePending.getiSHiTechPark())) {
				companyBaseSupplement.setiSHiTechPark(companyBasePending
						.getiSHiTechPark());
			}
			if (StringUtils.isNotEmpty(companyBasePending.getiSEgggenerator())) {
				companyBaseSupplement.setiSEgggenerator(companyBasePending
						.getiSEgggenerator());
			}
			if (StringUtils.isNotEmpty(companyBasePending.getOrtherIndustry())) {
				companyBaseSupplement.setOrtherIndustry(companyBasePending
						.getOrtherIndustry());
			}
			if (StringUtils.isNotEmpty(companyBasePending.getCurrency())) {
				companyBaseSupplement.setCurrency(companyBasePending
						.getCurrency());
			}
			// 保存页面接收信息
			companyInfoService.addCompanyBaseSupplement(companyBaseSupplement);// 保存补充信息
			// 保存相关附件
			List<CompanyAttachment> entities = new ArrayList<CompanyAttachment>();
			if (attachment02 == null) {
				attachment02 = new CompanyAttachment();
				// 如果没有数据则保存数据
				attachment02.setEnterpriseId(enterpriseId);
				attachment02.setCreateTime(new Date());
				attachment02.setFileName(companyBasePending.getLicenseName());
				attachment02.setFilepath(companyBasePending.getLicensePath());
				attachment02.setFileType("02");
				entities.add(attachment02);
				// 同时保存数据到待审核表中
				CompanyAttachmentPending comAttchPending = companyInfoService
						.findAttachmentPendingByType(enterpriseId, "02");
				if (comAttchPending != null) {// 待审核表中数据是否为空
					comAttchPending.setCreateTime(new Date());
					comAttchPending.setFileName(companyBasePending
							.getLicenseName());
					comAttchPending.setFilepath(companyBasePending
							.getLicensePath());
					comAttchPending.setFileType("02");
					// 保存到待审核表中
					companyInfoService
							.addCompanyAttachmentPending(comAttchPending);
				}
			} else {// 相关附件表中有数据
				if (companyBasePending.getLicensePath().equals(
						attachment02.getFilepath())) {// 判断页面传递的数据是否与库中数据相同
					// 相同的情况下进行数据的保存
					attachment02.setCreateTime(new Date());
					attachment02.setFileName(companyBasePending
							.getLicenseName());
					attachment02.setFilepath(companyBasePending
							.getLicensePath());
					attachment02.setFileType("02");
					entities.add(attachment02);
				} else {// 不同的情况下将数据保存到待审核表中
						// 查询待审核表中数据
					CompanyAttachmentPending comAttchPending = companyInfoService
							.findAttachmentPendingByType(enterpriseId, "02");
					if (comAttchPending == null) {// 待审核表中数据是否为空
						comAttchPending = new CompanyAttachmentPending();
						comAttchPending.setEnterpriseId(enterpriseId);
					}
					comAttchPending.setCreateTime(new Date());
					comAttchPending.setFileName(companyBasePending
							.getLicenseName());
					comAttchPending.setFilepath(companyBasePending
							.getLicensePath());
					comAttchPending.setFileType("02");
					// 保存到待审核表中
					companyInfoService
							.addCompanyAttachmentPending(comAttchPending);
				}
			}
			if (attachment01 == null) {
				attachment01 = new CompanyAttachment();
				// 如果没有数据则保存数据
				attachment01.setEnterpriseId(enterpriseId);
				attachment01.setCreateTime(new Date());
				attachment01.setFilepath(companyBasePending
						.getCreditAuthorizationPath());
				attachment01.setFileName(companyBasePending
						.getCreditAuthorizationName());
				attachment01.setFileType("01");
				entities.add(attachment01);
				// 同时保存数据到待审核表中
				CompanyAttachmentPending comAttchPending01 = companyInfoService
						.findAttachmentPendingByType(enterpriseId, "01");
				if (comAttchPending01 != null) {// 待审核表中数据是否为空
					comAttchPending01.setCreateTime(new Date());
					comAttchPending01.setFileName(companyBasePending
							.getCreditAuthorizationName());
					comAttchPending01.setFilepath(companyBasePending
							.getCreditAuthorizationPath());
					comAttchPending01.setFileType("01");
					// 保存到待审核表中
					companyInfoService
							.addCompanyAttachmentPending(comAttchPending01);
				}
			} else {// 相关附件表中有数据
					// 相同的情况下进行数据的保存
					attachment01.setCreateTime(new Date());
					attachment01.setFileName(companyBasePending
							.getCreditAuthorizationName());
					attachment01.setFilepath(companyBasePending
							.getCreditAuthorizationPath());
					attachment01.setFileType("01");
					entities.add(attachment01);
			}
			companyInfoService.addCompanyAttachment(entities);
			if (platformUser != null) {// 如果用户不为空
				// 判断用户的类型
				if ("0201".equals(platformUser.getType())) {
					// 更改存量企业用户的状态
					platformUser.setDesc2("02");
					serService.saveSystemUser(platformUser);
				}

			}
			if(oldStatus.equals("00")){
			    
				//新增企业查询本月评分
				QueryLog querylog1 = new QueryLog();
				querylog1.setCertno(companyBasePending.getCode());
				querylog1.setCerttype(companyBasePending.getCodetype());
				querylog1.setUserid(companyBasePending.getCode());
				
				querylog1.setQueryname(companyBasePending.getName());
				querylog1.setBegintime(new Date());
				queryScore(companyBase.getCode(),companyBase.getCodetype(),querylog1);
				List< MessageAlertInfo> messageAlertInfos =new ArrayList< MessageAlertInfo>();
                List<PlatformUser> users=serService.findQjrbUser();
                for(PlatformUser user:users){
                    MessageAlertInfo messageAlertInfo =new MessageAlertInfo();
                    messageAlertInfo.setCompanyId(companyBasePending.getEnterpriseId());
                    
                    messageAlertInfo.setMeassgeContent("");
                    messageAlertInfo.setMessageType(SRRPConstant.MESSAGE_TYPE_01);//企业新增提示
                    messageAlertInfo.setMessagUserId(user.getId());
                    messageAlertInfo.setOperDate(new Date());
                    messageAlertInfos.add(messageAlertInfo);
                }
                manageDissentService.saveMessageAlertInfo(messageAlertInfos);
			}
			map.put("changeFlag",changeFlag);
			this.writeJson(map, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// return null;
		}

	}

	@RequestMapping(value = "/exportEnterpriseInfor")
	@ResponseBody
	public void exportEnterpriseInfor(ResultBean rs,
			GataWayInvestorRegiter registerInfo, HttpServletRequest request,
			HttpServletResponse respons) {
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			request.setCharacterEncoding("UTF-8");
			CompanyBasePending companyBasePendingHis = new CompanyBasePending();
			companyBasePendingHis.setName(new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8"));
			companyBasePendingHis.setCodetype(new String(request.getParameter("codetype").getBytes("iso-8859-1"), "utf-8"));
			companyBasePendingHis.setCode(new String(request.getParameter("code").getBytes("iso-8859-1"), "utf-8"));
			companyBasePendingHis.setContacName(new String(request.getParameter("contacName").getBytes("iso-8859-1"),"utf-8"));
			companyBasePendingHis.setContacCal(new String(request.getParameter("contacCal").getBytes("iso-8859-1"), "utf-8"));
			companyBasePendingHis.setLegalName(new String(request.getParameter("legalName").getBytes("iso-8859-1"), "utf-8"));
			companyBasePendingHis.setLegalCal(new String(request.getParameter("legalCal").getBytes("iso-8859-1"), "utf-8"));
			companyBasePendingHis.setStockName(new String(request.getParameter("stockName").getBytes("iso-8859-1"), "utf-8"));
			companyBasePendingHis.setStockCal(new String(request.getParameter("stockCal").getBytes("iso-8859-1"), "utf-8"));
			companyBasePendingHis.setOperdate(new Date());
			// 获取页面传递的json字符串参数
			Map<Object, Object> o = new HashMap<Object, Object>();
			// 存入一个集合
			o.put("companyBase", companyBasePendingHis);
			String ftlPath = request.getSession().getServletContext()
					.getRealPath("/");
			ftlPath = ftlPath + "WEB-INF/views/enterprise/";
			exportPDF(ftlPath, SRRPConstant.templatesName_enterprise, o,
					respons, SRRPConstant.ftlName_invest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@RequestMapping(value = "/exportWarrantInfor")
	@ResponseBody
	public void exportWarrantInfor(ResultBean rs,
			GataWayInvestorRegiter registerInfo, HttpServletRequest request,
			HttpServletResponse respons) {
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			request.setCharacterEncoding("UTF-8");
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			CompanyBaseSupplement companyBaseSupplement = new CompanyBaseSupplement();
			CompanyBasePending companyBasePendingHis = new CompanyBasePending();
			CompanyBasePending companyBasePending = companyInfoService
					.findCompanyBasePendingByEnterpriseId(enterpriseId);
			// 鍒ゆ柇寰呭鏍歌〃涓槸鍚︽湁鏁版嵁
			if (companyBasePending != null) {
				companyBasePendingHis.setId(companyBasePending.getId());
			}
			companyBasePendingHis.setEnterpriseId(enterpriseId);
			companyBasePendingHis.setName(request.getParameter("name"));
			companyBasePendingHis.setCodetype(request.getParameter("codetype"));
			companyBasePendingHis.setCode(request.getParameter("code"));
			companyBasePendingHis.setRegistArea(request
					.getParameter("registArea"));
			companyBasePendingHis.setRegcapital(Double.valueOf(request
					.getParameter("regcapital")));
			companyBasePendingHis.setRedate(df1.parse(request
					.getParameter("redate")));
			companyBasePendingHis.setRearea(request.getParameter("rearea"));
			companyBasePendingHis.setLegalName(request
					.getParameter("legalName"));
			companyBasePendingHis.setLegalCal(request.getParameter("legalCal"));
			companyBasePendingHis.setStockName(request
					.getParameter("stockName"));
			companyBasePendingHis.setStockCal(request.getParameter("stockCal"));
			Map<Object, Object> o = new HashMap<Object, Object>();
			Calendar now = Calendar.getInstance();
			o.put("YEAR", String.valueOf(now.get(Calendar.YEAR)));
			o.put("MONTH", (now.get(Calendar.MONTH) + 1));
			o.put("DAY_OF_MONTH", now.get(Calendar.DAY_OF_MONTH));
			o.put("companyBase", companyBasePendingHis);
			String ftlPath = request.getSession().getServletContext()
					.getRealPath("/");
			ftlPath = ftlPath + "WEB-INF/views/enterprise/";
			exportPDF(ftlPath, SRRPConstant.templatesName_enterwarrant, o,
					respons, SRRPConstant.ftlName_warrant);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	/**
	 * 添加股东信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addcompanyStockholder")
	public void addcompanyStockholder(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取页面传参
			String companyStockholderString = request
					.getParameter("companyStockholder");
			// queryCondition
			// 构建股东信息对象
			CompanyBase base = new CompanyBase();

			CompanyStockholderPending companyStockholder = new CompanyStockholderPending();
			// 获取当前登陆用户的id
			String userId = RedisGetValue.getRedisUser(request, "userId");
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			base = companyInfoService.getCompanyBase(enterpriseId);
			if (companyStockholderString != null
					&& !"".equals(companyStockholderString)
					&& !"\"\"".equals(companyStockholderString)) {
				companyStockholder = (CompanyStockholderPending) JSON
						.parseObject(companyStockholderString,
								CompanyStockholderPending.class);
			}
			companyStockholder.setCreateTime(new Date());
			companyStockholder.setEnterpriseId(enterpriseId);
			companyStockholder.setCreaterId(userId);
			// base.setStopFlag("1");
			// companyInfoService.saveEnterprise(base);
			// CompanyStockholderPending
			// com=companyInfoService.saveCompanyStockPending(companyStockholder);
			this.writeJson(companyStockholder, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除股东信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteStockholder")
	public void deleteStockholder(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获得页面的传递的参数
			String holderId = request.getParameter("holderId");
			// 根据id查询股东信息表是否有数据，有的话删除股东信息表中数据 ，没有的话删除待审核表中数据
			CompanyStockholder stockholder = companyInfoService
					.findStockholder(holderId);
			if (stockholder != null) {
				companyInfoService.deleteStockholder(holderId);
			} else {
				companyInfoService.deleteStockholderPeding(holderId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加主要技术产品
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addcompanyProduct")
	public void addCompanyProduct(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取页面传递的参数
			String companyProductString = request
					.getParameter("companyProduct");
			// 构建技术产品对象
			CompanyProduct companyProduct = new CompanyProduct();
			// 获取登录用户企业的id
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			// 获取登录用户的id
			String userId = RedisGetValue.getRedisUser(request, "userId");
			// 将json字符串转化成对象
			if (companyProductString != null
					&& !"".equals(companyProductString)
					&& !"\"\"".equals(companyProductString)) {
				companyProduct = (CompanyProduct) JSON.parseObject(
						companyProductString, CompanyProduct.class);
			}

			companyProduct.setEnterpriseId(enterpriseId);
			companyProduct.setCreaterid(userId);
			companyProduct.setCreateTime(new Date());
			// 调用保存函数向数据库中传递数据
			companyInfoService.addCompanyProduct(companyProduct);
//			@SuppressWarnings("rawtypes")
//			Map<String, Comparable> map = new HashMap<String, Comparable>();
//			map.put("code", 0);
			this.writeJson(companyProduct, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void queryScore(String code,String  codetype,QueryLog querylog ) {
		try {
			querylog.setQuerytype("4");// 信用报告查询
			// 记录日志
			// 先判断评分状态是否正常
			if (client.getStatus()) {
				// 若接口成功返回评分数据，则本月内不在执行
				// 先查询机构表的总机构数
				// 返回的查询结果
				String result = client.getScoreInfos(code+",");
				if (null != result && !"".equals(result.trim())) {
					// 先清空当前表数据，然后在进行持久化操作
					JSONArray compScores = JSONArray.fromObject(result);
					// 循环，解析每条json 封装到对象
					for (int ii = 0; ii < compScores.size(); ii++) {
						RpCompanyCreditscores scoreInfo = new RpCompanyCreditscores();
						// 取出来第一个json
						JSONObject scoreJson = (JSONObject) compScores.get(ii);
						// 解析封装成对象
						scoreService.analysisJSON(scoreInfo, scoreJson);
						List<RpCompanyCreditscores>  scor=scoreService.FindScore(code);
						if(scor.size()<1){
							scoreInfo.setCreditcode(code);
							scoreInfo.setCreditype(codetype);
							scoreService.saveScore(scoreInfo);
						}
						
					}
					// 持久化
				}
			}
			querylog.setEndtime(new Date());
			querylog.setIssuc("1");// 查询成功
			queryService.addquerylog(querylog);
		} catch (Exception e) {
			querylog.setEndtime(new Date());
			querylog.setIssuc("3");// 查询失败
			queryService.addquerylog(querylog);
			e.printStackTrace();
		}
	}
	/**
	 * 删除主要技术产品信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteProduct")
	public void deleteProduct(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String productId = request.getParameter("productId");
			companyInfoService.deleteProduct(productId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除主要技术产品信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteAttachment")
	public void deleteAttachment(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			/**
			 * 删除相关附件信息分两步
			 * 
			 * 1.根据数据库中的数据删除对应文件夹中的文件 2.删除数据库中的数据
			 */
			String createrId = request.getParameter("createrId");
			// 根据数据删除对应文件
			// 首先查询出文件的路径
			CompanyAttachment attachment = companyInfoService
					.findAttachmentById(createrId);
			// 获得到路径
			String URL = attachment.getFilepath();
			// 创建文件流
			File file = new File(URL);
			// 判断文件是否存在
			if (file.exists()) {
				// 如果存在的情况下
				file.delete();
			}
			// 删除数据

			companyInfoService.deleteAttachment(createrId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 上传企业相关附件
	 * 
	 * @param file
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "uploadCompanyAttachments")
	private void uoloadCompanyAttachments(
			@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String fileTypes = request.getParameter("fileTypes");
			System.out.println(fileTypes);
			// 判断传输的文件流是否为空
			if (!file.isEmpty()) {
				String fileType = request.getParameter("fileType");
				Map<String, String> map = new HashMap<String, String>();
				map = service.uploadPic(file, fileType);
				this.writeJson(map, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登记企业相关附件
	 * 
	 * @param file
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "registerCompanyAttachments")
	private void registerCompanyAttachments(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String fileName = request.getParameter("fileName");
			String fileUrl = request.getParameter("fileUrl");
			String fileType = request.getParameter("fileType");
			// 获取登陆的企业id
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			// 创建相关附件对象
			CompanyAttachment companyAttachment = new CompanyAttachment();
			companyAttachment.setEnterpriseId(enterpriseId);
			companyAttachment.setCreateTime(new Date());
			companyAttachment.setFileName(fileName);
			companyAttachment.setFilepath(fileUrl);
			companyAttachment.setFileType(fileType);
			companyInfoService.addCompanyAttachment(companyAttachment);
			map.put("code", "0");
		} catch (Exception e) {
			map.put("code", "1");
			e.printStackTrace();
		}
		this.writeJson(map, request, response);
	}

	@RequestMapping(value = "addObjection")
	public void addObjection(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取页面传递的参数
			String companyObjectionString = request.getParameter("companyObjection");
			// 构建技术产品对象
			CompanyObjection objection = new CompanyObjection();
			// 获取登录用户企业的id
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			// 将json字符串转化成对象
			if (companyObjectionString != null && !"".equals(companyObjectionString)
					&& !"\"\"".equals(companyObjectionString)) {
				objection = (CompanyObjection) JSON.parseObject(
						companyObjectionString, CompanyObjection.class);
			}
			objection.setEnterpriseId(enterpriseId);
			objection.setOperDate(new Date());
			this.writeJson(objection, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除异议信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteObjection")
	public void deleteObjection(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String objId = request.getParameter("objId");
			companyInfoService.delObjection(objId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 根据企业的id查询企业成员列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addCompanyMember")
	public void addCompanyMember(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取页面传递的参数
			String companymemberString = request.getParameter("companyMember");
			// 构建技术产品对象
			CompanyMember companyMember = new CompanyMember();
			// 获取登录用户企业的id
			String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
			// 获取登录用户的id
			String userId = RedisGetValue.getRedisUser(request, "userId");
			// 将json字符串转化成对象
			if (companymemberString != null && !"".equals(companymemberString)
					&& !"\"\"".equals(companymemberString)) {
				companyMember = (CompanyMember) JSON.parseObject(
						companymemberString, CompanyMember.class);
			}

			companyMember.setEnterpriseId(enterpriseId);
			companyMember.setCreateId(userId);
			companyMember.setCreateTime(new Date());
			// 调用保存函数向数据库中传递数据
			companyInfoService.saveCompanyMember(companyMember);
			this.writeJson(companyMember, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除成员信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteMember")
	public void deleteMember(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String memberId = request.getParameter("memberId");
			companyInfoService.deleteMember(memberId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 
	* @Title: downWarrantInfor 
	* @Description: TODO(工商返回byte，输出到页面，供下载) 
	* @param @param rs https://blog.csdn.net/chengzixiaohai/article/details/42743247
	* @param @param registerInfo
	* @param @param request
	* @param @param respons  参数说明 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/downWarrantInfor")
	@ResponseBody
	public void downWarrantInfor(ResultBean rs,
			GataWayInvestorRegiter registerInfo, HttpServletRequest request,
			HttpServletResponse respons) {
        File warrantFile=null;
       String type= request.getParameter("type");
       String fileName= request.getParameter("name");
       String enterpriseId= request.getParameter("enterpriseId");
    // 获取登录用户企业的id
        //String enterpriseId = RedisGetValue.getRedisUser(request, "orgNo");
		try {
			request.setCharacterEncoding("UTF-8");
			//设置浏览器输出方式
			respons.setContentType("application/x-download;charset=utf-8"); 
			respons.setHeader("Content-Disposition", "attachment; filename=\"" + fileName+ "\""); 
			warrantFile= new File("C:/Users/huguo/Downloads/123.pdf");//测试专用
			byte[] bytes = companyInfoService.getAuthByte(enterpriseId,type);
		       if (null != bytes) {    
		           try {    
		        	   respons.getOutputStream().write(bytes);    
		        	   respons.getOutputStream().flush();    
		           } catch (IOException e) {    
		               throw new IllegalArgumentException(e);    
		           }    
		       }   
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	   public static byte[] getBytesFromFile(File f){  
           if (f == null){  
               return null;  
           }  
           try {  
               FileInputStream stream = new FileInputStream(f);  
               ByteArrayOutputStream out = new ByteArrayOutputStream(1000);  
               byte[] b = new byte[1000];  
               int n;  
               while ((n = stream.read(b)) != -1)  
                   out.write(b, 0, n);  
               stream.close();  
               out.close();  
               return out.toByteArray();  
           } catch (IOException e){  
           }  
           return null;  
       }  
	   
}

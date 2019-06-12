package com.icfcc.ssrp.web.managedept;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachment;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachmentPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBasePending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyInfoVo;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyMember;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjection;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjectionPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyProduct;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholder;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholderPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.HistoryCompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.QueryCompanyScoresResult;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageDissentInfor;
import com.icfcc.SRRPDao.jpa.entity.managedept.MessageAlertInfo;
import com.icfcc.SRRPDao.jpa.entity.managedept.QueryCompanyAuditResult;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserLoginLog;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserRole;
import com.icfcc.SRRPService.PlatformSystem.PlatformConfigService;
import com.icfcc.SRRPService.PlatformSystem.PlatformRoleService;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.gataway.staticize.GataWayCompanyInfoService;
import com.icfcc.SRRPService.managedept.CompanyManageServise;
import com.icfcc.SRRPService.managedept.ManageDissentService;
import com.icfcc.SRRPService.util.AESUtil;
import com.icfcc.SRRPService.util.SmsClientUtil;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.FileUtil;
import com.icfcc.credit.platform.util.MD5;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.credit.platform.util.SysDate;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

/**
 * 主管部门菜单---企业管理
 * 
 * @author Administrator
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/companyManage")
public class CompanyManagerController extends SRRPBaseController {
	@Autowired
	private CompanyManageServise companyManageServise;
	@Autowired
	private CompanyInfoService companyInfoService;
	@Autowired
	private GataWayCompanyInfoService gataWayCompanyInfoService;
	@Autowired
	private PlatformUserService userService;
	// 处理新增权限的service
	@Autowired
	protected PlatformRoleService roleService;
	
    @Autowired
	private ManageDissentService manageDissentService;
	 
	private static final String PASSWORD = "password";
	// 获取系统参数的service
	@Autowired
	private PlatformConfigService config;

	/**
	 * 初始化企业管理列表页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/companyManageInit")
	public String investorManageInit(HttpServletRequest request,
			HttpServletResponse response) {
		String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
		String userId = RedisGetValue.getRedisUser(request, "userId");// 用户类型
		List<DD> ddAuditStatus2 = RedisGetValue
				.getDataList(SRRPConstant.DD_COMPANY_AUSTSTAUS);
		List<DD> ddAuditStatus = new ArrayList<DD>();
		if (SRRPConstant.USER_TYPE_05.equals(userType)) {
			for(DD dd:ddAuditStatus2){
				if(dd.getDicCode().equals("21")|| dd.getDicCode().equals("22")  || dd.getDicCode().equals("23")){
					ddAuditStatus.add(dd);
				}
			}	
		}else if (SRRPConstant.USER_TYPE_04.equals(userType)|| SRRPConstant.USER_TYPE_06.equals(userType)){
			for(DD dd:ddAuditStatus2){
				if(!dd.getDicCode().equals("00")&& !dd.getDicCode().equals("21")&& !dd.getDicCode().equals("22")  && !dd.getDicCode().equals("23")){
					ddAuditStatus.add(dd);
				}
			}	
		}else if(SRRPConstant.USER_TYPE_03.equals(userType)){
			for(DD dd:ddAuditStatus2){
				if(!dd.getDicCode().equals("00")&& !dd.getDicCode().equals("04")  && !dd.getDicCode().equals("06")&&!dd.getDicCode().equals("05")&& !dd.getDicCode().equals("21")){
					ddAuditStatus.add(dd);
				}
			}	
			for(DD dd:ddAuditStatus2){
				if(dd.getDicCode().equals("04")|| dd.getDicCode().equals("05")  || dd.getDicCode().equals("06")){
					ddAuditStatus.add(dd);
				}
			}	
			List<MessageAlertInfo> MessageAlertInfos= manageDissentService.findMessageByMessagType(userId,SRRPConstant.MESSAGE_TYPE_01);
			String isHaveMessage="0";
			if(MessageAlertInfos.size()>0){
			    isHaveMessage="1";
			}
            request.setAttribute("isHaveMessage", isHaveMessage);
		}
       if (SRRPConstant.USER_TYPE_04.equals(userType)|| SRRPConstant.USER_TYPE_06.equals(userType)){
            List<MessageAlertInfo> MessageAlertInfos= manageDissentService.findMessageByMessagType(userId,SRRPConstant.MESSAGE_TYPE_00);
            request.setAttribute("messageAlertInfos", MessageAlertInfos);
            int messageNum=0;
            if(MessageAlertInfos!=null){
                messageNum=MessageAlertInfos.size();
            }
            request.setAttribute("messageNum", messageNum);
		}
		
		String password = config.getConfigValueByName("password");
		
		request.setAttribute("userType", userType);
		request.setAttribute("password", password);
		request.setAttribute("ddAuditStatus", ddAuditStatus);
		return "WEB-INF/views/managedept/companyManage";
	}

	/**
	 * 企业审核页面的列表展示
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/companyManageList")
	public String findCompanyBase(Model model, PageBean page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String currentPage = request.getParameter("start");
			String maxSize = request.getParameter("limit");
			String queryConditionString = request
					.getParameter("queryCondition");
			String userId = RedisGetValue.getRedisUser(request, "userId");// 用户类型
			// 初始化字典值11
			QueryCondition queryCondition = new QueryCondition();
			// 如果参数不为空将参数转换成对象
			if (queryConditionString != null
					&& !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				queryCondition = (QueryCondition) JSON.parseObject(
						queryConditionString, QueryCondition.class);
			}
			String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型

			if (SRRPConstant.USER_TYPE_03.equals(userType)) {// 区县金融办用户
																// 查询本区域的企业用户
				ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject()
						.getPrincipal();
				PlatformUser user = userService.getUser(shiroUser.getId());
				queryCondition.setArea(user.getDesc3());
			}

			if (StringUtils.isNotEmpty(currentPage)) {
				queryCondition.setCurPage(Integer.parseInt(currentPage));
			}
			if (StringUtils.isNotEmpty(maxSize)) {
				queryCondition.setMaxSize(Integer.parseInt(maxSize));
			}
			// 首先获取用户所属区域，将用户所属区域作为参数调用查询方法
			List<QueryCompanyScoresResult> companyBaseList = companyManageServise
					.findCompanyBasePending(queryCondition, userType);
			for (QueryCompanyScoresResult queryCompanyScoresResult : companyBaseList) {
				queryCompanyScoresResult.setUserType(userType);
				queryCompanyScoresResult.setUserStatus(queryCompanyScoresResult
						.getAuditStatus());
                ManageDissentInfor manageDissentInfor=manageDissentService.findDissentBycompanyId(queryCompanyScoresResult.getEnterpriseId());
				if (SRRPConstant.USER_TYPE_03.equals(userType)) {
				    if(manageDissentInfor!=null){
				        queryCompanyScoresResult.setIdHaveDissent("2");//区县金融办：有异议。标识为2 则显示修改异议，否则显示新增异议
				        if(SRRPConstant.DISSENT_STATUS_03.equals(manageDissentInfor.getDissentStatus())){
	                        queryCompanyScoresResult.setIdHaveDissent("0");//区县金融办：有异议为解除状态。标识为0显示新增异议
				        }
				    }
				    List<MessageAlertInfo> MessageAlertInfos= manageDissentService.findMessageBycompanyId(queryCompanyScoresResult.getEnterpriseId(),SRRPConstant.MESSAGE_TYPE_01,userId);
                    if(MessageAlertInfos!=null){//判断是否未读
                        if(MessageAlertInfos.size()>0){
                            queryCompanyScoresResult.setIsReadDissent("1");
                        }
                    }    
                        
				}
				if (SRRPConstant.USER_TYPE_04.equals(userType)|| SRRPConstant.USER_TYPE_06.equals(userType)) {// 市金融办
					if (queryCompanyScoresResult.getAuditStatus().equals("23")||queryCompanyScoresResult.getAuditStatus().equals("22")) {
						queryCompanyScoresResult.setAuditStatus("02");
					}
					//判断每个企业是否有提异议
					if(manageDissentInfor!=null){
					    queryCompanyScoresResult.setIdHaveDissent("1");//有异议。标识为1
                        List<MessageAlertInfo> MessageAlertInfos= manageDissentService.findMessageBycompanyId(queryCompanyScoresResult.getEnterpriseId(),SRRPConstant.MESSAGE_TYPE_00,userId);
                           if(MessageAlertInfos!=null){//判断是否未读
                               if(MessageAlertInfos.size()>0){
                                   queryCompanyScoresResult.setIsReadDissent("1");
                               }
                           }

					}
					
				}
				if (SRRPConstant.USER_TYPE_05.equals(userType)) {// 征信公司
					if (queryCompanyScoresResult.getAuditStatus().equals("02")) {
						queryCompanyScoresResult.setAuditStatus("21");
					}
					if (queryCompanyScoresResult.getAuditStatus().equals("04")
							|| queryCompanyScoresResult.getAuditStatus()
									.equals("05")
							|| queryCompanyScoresResult.getAuditStatus()
									.equals("06")) {
						queryCompanyScoresResult.setAuditStatus("22");
					}
				}

			}
			page.setList(companyBaseList);
			if (CollectionUtils.isNotEmpty(companyBaseList)) {
				page.setList(companyBaseList);
				// 设置总的条数
				page.setRecordCnt(Integer.parseInt(companyManageServise
						.CountCompanyBasePending(queryCondition, userType)
						.toString()));
				if (StringUtils.isNotEmpty(maxSize)) {
					page.setMaxSize(Integer.parseInt(maxSize));
				}
				if (StringUtils.isNotEmpty(currentPage)) {
					page.setCurPage(Integer.parseInt(currentPage));
				}
				page.pageResult(companyBaseList, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			// 将数据传输到前端
			this.writeJson(page, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/managedept/companyManage";
	}

	/**
	 * 根据企业的id查询企业的基本信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/findCompanyDetail")
	public String findCompanyDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 从页面接收企业的id根据企业的id查询企业的信息
			String enterpriseId = request.getParameter("enterpriseId");
			// 根据id查询基本信息
			CompanyBase companyBase = companyInfoService.getCompanyBase(enterpriseId);
			// 1.调接口 获取授权书和营业执照的name以及格式pdf/img
			// 2.如果存在获取的名字复制对象的fileName
			// 3..传前台一个标识 如果存在则下载的是工商的 如果不存在则显示本地的
			String authFlag="00";
			String linceFlag="00";
			CompanyBaseSupplement companyBaseSupplement = companyInfoService
					.getCompanyBaseSupplement(enterpriseId);
			// 根据id查询企业的待审核信息
			CompanyBasePending companyBasePending = companyInfoService
					.findCompanyBasePendingByEnterpriseId(enterpriseId);
			List<CompanyStockholder> companyStockholderList = companyInfoService
					.getCompanyStockholders(enterpriseId);
			List<CompanyStockholderPending> stockholderPendings = companyInfoService
					.getCompanyStockholderPendings(enterpriseId);
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
			List<QueryCompanyAuditResult> auditResults = this
					.getAuditList(enterpriseId);
			List<CompanyObjection> companyObjections=companyInfoService.getObjectionList(enterpriseId);
			List<CompanyObjectionPending> companyObjectionPendings= companyInfoService.getObjectionPendingList(enterpriseId);
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
			}else{
				companyBaseSupplement=new CompanyBaseSupplement();
			}
			
			if ("01".equals(auditStatus) || "02".equals(auditStatus)
					|| "03".equals(auditStatus)|| "23".equals(auditStatus)) {
				
				if(stockholderPendings.size()>0){
					request.setAttribute(
						"companyStockholderList",
						JSON.toJSONString(
								stockholderPendings,
								SerializerFeature.DisableCircularReferenceDetect));
					request.setAttribute(
							"companyObjections",
							JSON.toJSONString(
									companyObjectionPendings,
									SerializerFeature.DisableCircularReferenceDetect));
				}else{
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
				}
				for (CompanyAttachment companyAttachment : companyAttachmentList) {
					if (companyAttachmentPending01 != null) {// 如果不为空，则展示待审核表中的数据
						if ("01".equals(companyAttachment.getFileType())) {
							companyAttachment
									.setFilepath(companyAttachmentPending01
											.getFilepath());
							companyAttachment
									.setFileName(companyAttachmentPending01
											.getFileName());
						}
					}else{
						authFlag="01";
					}
					if (companyAttachmentPending02 != null) {// 如果不为空，则展示待审核表中的数据
						if ("02".equals(companyAttachment.getFileType())) {
							companyAttachment
									.setFilepath(companyAttachmentPending02
											.getFilepath());
							companyAttachment
									.setFileName(companyAttachmentPending02
											.getFileName());
						}else{
							linceFlag="01";
						}
					}
				}
				request.setAttribute("companyBase", companyBasePending);
			} else {
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
			}
			// request.setAttribute("companyBase", companyBase);
			//查看未读消息完之后删除异议提醒
			 String userId = RedisGetValue.getRedisUser(request, "userId");// 用户类型
	        manageDissentService.deleteByMessageId(enterpriseId,userId,SRRPConstant.MESSAGE_TYPE_01);
			request.setAttribute("userType", userType);
			request.setAttribute("auditStatus", auditStatus);
			request.setAttribute("companyBaseSupplement", companyBaseSupplement);
			request.setAttribute("companyObjections", JSON.toJSONString(
					companyObjections,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyMembers", JSON.toJSONString(
					companyMembers,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("auditResults", JSON.toJSONString(
					auditResults,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyProductList", JSON.toJSONString(
					companyProductList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyAttachmentList", companyAttachmentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/managedept/enterpriseDetail";
	}

	/**
	 * 根据企业的id查询待审核企业的基本信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/findCompanyPendingDetail")
	public String findCompanyPendingDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 从页面接收企业的id根据企业的id查询企业的信息
			String enterpriseId = request.getParameter("enterpriseId");
			String authFlag="00";
			String linceFlag="00";
			// 根据id查询基本信息
			CompanyBase companyBase = companyInfoService
					.getCompanyBase(enterpriseId);
			CompanyBasePending companyBasePending = companyManageServise
					.findCompanyBasePendingByEntsrpriseId(enterpriseId);
			CompanyBaseSupplement companyBaseSupplement = companyInfoService
					.getCompanyBaseSupplement(enterpriseId);
			List<CompanyStockholder> companyStockholderList = companyInfoService
					.getCompanyStockholders(enterpriseId);
			List<CompanyStockholderPending> stockholderPendingList = companyInfoService
					.getCompanyStockholderPendings(enterpriseId);
			List<CompanyProduct> companyProductList = companyInfoService
					.getCompanyProducts(enterpriseId);
			List<CompanyAttachment> companyAttachmentList = companyInfoService
					.getCompanyAttachments(enterpriseId);
			List<CompanyMember> companyMembers = companyInfoService
					.findCompanyMemberByEventId(enterpriseId);
			List<QueryCompanyAuditResult> auditResults = this
					.getAuditList(enterpriseId);
			CompanyAttachmentPending comattchPengding02 = companyInfoService
					.findAttachmentPendingByType(enterpriseId, "02");
			CompanyAttachmentPending comattchPengding01 = companyInfoService
					.findAttachmentPendingByType(enterpriseId, "01");
			List<CompanyObjection> companyObjections=companyInfoService.getObjectionList(enterpriseId);
			List<CompanyObjectionPending> companyObjectionPendings= companyInfoService.getObjectionPendingList(enterpriseId);
			Map<String, String> map = new HashMap<String, String>();
			if (companyBasePending != null) {
				if (StringUtils.isNotEmpty(companyBase.getName())
						&& StringUtils.isNotEmpty(companyBasePending.getName())) {
					if (!(companyBase.getName().equals(companyBasePending
							.getName()))) {
						map.put("enterNameFlag", "1");
					}
				}// 企业名称
				if (StringUtils.isNotEmpty(companyBase.getCodetype())
						&& StringUtils.isNotEmpty(companyBasePending
								.getCodetype())) {
					if (!(companyBase.getCodetype().equals(companyBasePending
							.getCodetype()))) {
						map.put("codetypeFlag", "1");
					}
				}// 证件代码类型
				if (companyBase.getCode() != null
						&& companyBasePending.getCode() != null) {
					if (!(companyBase.getCode().equals(companyBasePending
							.getCode()))) {
						map.put("codeFlag", "1");
					}
				}// 证件代码
				if (companyBase.getRegcapital() != null
						&& companyBasePending.getRegcapital() != null) {
					if (!(companyBase.getRegcapital().equals(companyBasePending
							.getRegcapital()))) {
						map.put("regcapitalFlag", "1");
					}
				}// 注册资本
				if (companyBase.getRedate() != null
						&& companyBasePending.getRedate() != null) {
					if (!(companyBase.getRedate().equals(companyBasePending
							.getRedate()))) {
						map.put("redateFlag", "1");
					}
				}// 注册时间
				if (StringUtils.isNotEmpty(companyBase.getRearea())
						&& StringUtils.isNotEmpty(companyBasePending
								.getRearea())) {
					if (!(companyBase.getRearea().equals(companyBasePending
							.getRearea()))) {
						map.put("reareaFlag", "1");
					}
				}// 所属区域
				if (StringUtils.isNotEmpty(companyBasePending
								.getRegistArea())) {
					if(StringUtils.isEmpty(companyBase.getRegistArea())){
						map.put("registAreaFlag", "1");
					}else{
						if (!(companyBase.getRegistArea().equals(companyBasePending
								.getRegistArea()))) {
							map.put("registAreaFlag", "1");
						}
					} 
				}// 注册地址
				if (StringUtils.isNotEmpty(companyBase.getLegalName())
						&& StringUtils.isNotEmpty(companyBasePending
								.getLegalName())) {
					if (!(companyBase.getLegalName().equals(companyBasePending
							.getLegalName()))) {
						map.put("legalNameFlag", "1");
					}
				}// 法定代表人姓名
				if (StringUtils.isNotEmpty(companyBase.getLegalCal())
						&& StringUtils.isNotEmpty(companyBasePending
								.getLegalCal())) {
					if (!(companyBase.getLegalCal().equals(companyBasePending
							.getLegalCal()))) {
						map.put("legalCalFlag", "1");
					}
				}// 法定代表人联系方式
				if (StringUtils.isNotEmpty(companyBase.getStockName())
						&& StringUtils.isNotEmpty(companyBasePending
								.getStockName())) {
					if (!(companyBase.getStockName().equals(companyBasePending
							.getStockName()))) {
						map.put("stockNameFlag", "1");
					}
				}// 股权联系人姓名
				if (StringUtils.isNotEmpty(companyBase.getStockCal())
						&& StringUtils.isNotEmpty(companyBasePending
								.getStockCal())) {
					if (!(companyBase.getStockCal().equals(companyBasePending
							.getStockCal()))) {
						map.put("stockCalFlag", "1");
					}
				}// 股权联系人联系方式
			} else {
				companyBasePending = new CompanyBasePending();
				BeanUtils.copyProperties(companyBase, companyBasePending);
			}
			if ("01".equals(companyBase.getStockFlag())
					|| "01".equals(companyBasePending.getStockFlag())) {
				map.put("stockholderFlag", "1");
			}
			for (CompanyAttachment companyAttachment : companyAttachmentList) {
				if (comattchPengding02 != null) {
					if ("02".equals(companyAttachment.getFileType())) {
						companyAttachment.setFilepath(comattchPengding02
								.getFilepath());
						companyAttachment.setFileName(comattchPengding02
								.getFileName());
						map.put("file02Flag", "1");// 添加营业执照的修改判断
					}
				}else{
					linceFlag="01";
				}
				if (comattchPengding01 != null) {
					if ("01".equals(companyAttachment.getFileType())) {
						companyAttachment.setFilepath(comattchPengding01
								.getFilepath());
						companyAttachment.setFileName(comattchPengding01
								.getFileName());
						map.put("file01Flag", "1");// 添加授权书的修改判断
					}
				}else{
					authFlag="01";
				}
			}
			if(companyBase.getPlatformFlag().equals("01")){
					Map<String, String> map1= companyInfoService.getQueryAuthName(enterpriseId);
					CompanyAttachment companyAttachment01 = companyInfoService
							.findAttachmentByType(enterpriseId, "01");
					CompanyAttachment companyAttachment02 = companyInfoService
							.findAttachmentByType(enterpriseId, "02");
					if(companyAttachment01==null && comattchPengding01==null){//不存在y//已激活状态
						 if(!map1.get("QueryAuthName").toString().equals("01")){
							 companyBasePending.setCreditAuthorizationName(map1.get("QueryAuthName").toString());
							 companyBasePending.setAuthFlag("1");
							}else{
								companyBasePending.setAuthFlag("2");
							}
					}else{
						if(companyAttachment01!=null){
							if(StringUtils.isEmpty(companyAttachment01.getFilepath())){
								if(!map1.get("QueryAuthName").toString().equals("01")){
									companyBasePending.setCreditAuthorizationName(map1.get("QueryAuthName").toString());
									 companyBasePending.setAuthFlag("1");

									}else{
										companyBasePending.setAuthFlag("2");

									}
							}
						}
						if(comattchPengding01!=null){

							if(StringUtils.isEmpty(comattchPengding01.getFilepath())){
								if(!map1.get("QueryAuthName").toString().equals("01")){
									companyBasePending.setCreditAuthorizationName(map1.get("QueryAuthName").toString());
									 companyBasePending.setAuthFlag("1");

									}else{
										companyBasePending.setAuthFlag("2");

									}
							}
						}
					}
					if(companyAttachment02==null && comattchPengding02==null){//不存在y
						if(!map1.get("QueryLicenceName").toString().equals("01")){
							 companyBasePending.setLicenseName(map1.get("QueryLicenceName").toString());
							 companyBasePending.setLinceFlag("1");
						}else{
							companyBasePending.setLinceFlag("2");
						}
					 }else{

						 if(companyAttachment02!=null){
							 if(StringUtils.isEmpty(companyAttachment02.getFilepath())){
								 if(!map1.get("QueryLicenceName").toString().equals("01")){
									 companyBasePending.setLicenseName(map1.get("QueryLicenceName").toString());
									 companyBasePending.setLinceFlag("1");

									}else{
										companyBasePending.setLinceFlag("2");
									}
								}
						 }
						if(comattchPengding02!=null){
							if(StringUtils.isEmpty(comattchPengding02.getFilepath())){
								if(!map1.get("QueryLicenceName").toString().equals("01")){
									 companyBasePending.setLicenseName(map1.get("QueryLicenceName").toString());
									 companyBasePending.setLinceFlag("1");

								}else{
									companyBasePending.setLinceFlag("2");
								}
							}
						}
					 }
//				 }
				 
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
			//首先判断工商异议信息是否有更新
			if("1".equals(companyBase.getObjecChangFlag())){
				//如果有更新则展示待审核表里面的数据
				request.setAttribute("companyObjections", JSON.toJSONString(
						companyObjectionPendings,
						SerializerFeature.DisableCircularReferenceDetect));
				if(!("01".equals(companyBase.getAuditStatus()) || "02".equals(companyBase.getAuditStatus()))){
					//同时添加更新标识
					map.put("objectionFlag", "1");
				}
				
			}else{
				//展示基本表里面的数据
				request.setAttribute("companyObjections", JSON.toJSONString(
						companyObjections,
						SerializerFeature.DisableCircularReferenceDetect));
			}
			companyBasePending.setFlagMap(map);
			request.setAttribute("companyBase", companyBase);
			request.setAttribute("companyBasePending", companyBasePending);
			request.setAttribute("companyBaseSupplement", companyBaseSupplement);
			request.setAttribute("companyStockholderList", JSON.toJSONString(
					companyStockholderList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("stockholderPendingList", JSON.toJSONString(
					stockholderPendingList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyMembers", JSON.toJSONString(
					companyMembers,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("auditResults", JSON.toJSONString(
					auditResults,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyProductList", JSON.toJSONString(
					companyProductList,
					SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("companyAttachmentList", companyAttachmentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/managedept/companyPendingDetail";
	}

	/**
	 * 审核企业信息提交审核结果
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/auditCompany")
	public void auditCompany(ResultBean rs, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
			// 获取页面传递的参数enterpriseId,status,auditContent
			String enterpriseId = request.getParameter("enterpriseId");
			String status = request.getParameter("status");
			String auditContent = request.getParameter("auditContent");
			// 获取登陆用户的id
			String userId = RedisGetValue.getRedisUser(request, "userId");
			CompanyAttachmentPending comattchPengding02 = companyInfoService
					.findAttachmentPendingByType(enterpriseId, "02");// 查询待审核表中数据是否有数据
			CompanyAttachmentPending comattchPengding01 = companyInfoService
					.findAttachmentPendingByType(enterpriseId, "01");// 查询待审核表中数据是否有数据
			CompanyAttachment comattch01 = companyInfoService
					.findAttachmentByType(enterpriseId, "01");// 查询待审核表中数据是否有数据
//			List<CompanyObjection> companyObjections=companyInfoService.getObjectionList(enterpriseId);
			List<CompanyObjectionPending> companyObjectionPendings= companyInfoService.getObjectionPendingList(enterpriseId);
			// 查询待审核的股东信息列表
			List<CompanyStockholderPending> stockholderPendingList = companyInfoService
					.getCompanyStockholderPendings(enterpriseId);
			// 首先查询出待审核表中的数据根据企业的id
			CompanyBasePending companyBasePending = companyManageServise
					.findCompanyBasePendingByEntsrpriseId(enterpriseId);
			CompanyBase companyBase = companyInfoService
					.getCompanyBase(enterpriseId);

			CompanyBase base = new CompanyBase();
			if (companyBasePending != null) {
				BeanUtils.copyProperties(companyBasePending, base);
				base.setAuditStatus(companyBase.getAuditStatus());
			} else {
				BeanUtils.copyProperties(companyBase, base);
			}

				if (SRRPConstant.USER_TYPE_04.equals(userType)) {
					if ("2".equals(status)) {
						// 用待审核表中的信息给基本信息赋值
						companyBase.setAuditStage("05");
						companyBase.setAuditStatus("02");// 向数据库中插入审核状态，4是修改未审核
						companyInfoService.saveEnterprise(companyBase);
					} else if ("3".equals(status)) {
						companyBase.setAuditStage(userType);
						companyBase.setAuditStatus("03");
						companyInfoService.saveEnterprise(companyBase);
						sendAuditMassge(companyBase.getName(),
								companyBase.getCode(), "审核", "6", auditContent,
								base.getStockCal());
					} else if ("5".equals(status)) {
						String oldCode=companyBase.getCode();
						AusdiPass(base, companyBasePending, companyBase,
								enterpriseId, comattchPengding02,
								comattchPengding01, stockholderPendingList,companyObjectionPendings);
						base.setAuditStatus("05");
						base.setObjecChangFlag("0");
						base.setAuditStage(userType);
						companyInfoService.saveEnterprise(base);
						updateEnterpriseSZJF1(base,oldCode);
	
					} else if ("6".equals(status)) {// 修改审核不通过
						companyBase.setAuditStatus("06");
						base.setAuditStage(userType);
						companyInfoService.saveEnterprise(companyBase);
						sendAuditMassge(base.getName(), base.getCode(), "审核", "16",
								auditContent, base.getStockCal());
					}
					companyInfoService.saveEnterprise(companyBase);
			} else if (SRRPConstant.USER_TYPE_05.equals(userType)) {
				if ("2".equals(status)) {
					//获取到传值的map
					Map<String, Object> resultMap=gentcompanyMap(request,companyBase,comattchPengding01,comattch01);
					if(SRRPConstant.ZXSQ_RESULT_200.equals(resultMap.get("reponseCode"))){
					AusdiPass(base, companyBasePending, companyBase,
							enterpriseId, comattchPengding02,
							comattchPengding01, stockholderPendingList,companyObjectionPendings);
					base.setObjecChangFlag("0");
					// 用待审核表中的信息给基本信息赋值
					base.setAuditStage(userType);
					base.setAuditStatus("22");// 向数据库中插入审核状态，4是修改未审核
					// 审核通过之后发送审核通过的短信
					sendAuditMassge(base.getName(), base.getCode(), "审核", "4",
							auditContent, base.getStockCal());
					// 更新最新的基本信息
					companyInfoService.saveEnterprise(base);
					//激活通过，查询异议信息表中是否存在数据如果有数据，将待审核字段的数据改为审核通过
					
					updateEnterpriseSZJF(base);
					}else if(SRRPConstant.ZXSQ_RESULT_400.equals(resultMap.get("reponseCode") )&&"本机构已登记过该企业授权，不允许重复提交。".equals(resultMap.get("reponseMsg"))){ 
						AusdiPass(base, companyBasePending, companyBase,
								enterpriseId, comattchPengding02,
								comattchPengding01, stockholderPendingList,companyObjectionPendings);
						base.setObjecChangFlag("0");
						// 用待审核表中的信息给基本信息赋值
						base.setAuditStage(userType);
						base.setAuditStatus("22");// 向数据库中插入审核状态，4是修改未审核
						// 审核通过之后发送审核通过的短信
						sendAuditMassge(base.getName(), base.getCode(), "审核", "4",
								auditContent, base.getStockCal());
						// 更新最新的基本信息
						companyInfoService.saveEnterprise(base);
						//激活通过，查询异议信息表中是否存在数据如果有数据，将待审核字段的数据改为审核通过
						
						updateEnterpriseSZJF(base);
					}else{
						rs = new ResultBean(resultMap.get("reponseCode").toString(),resultMap.get("reponseMsg").toString());
						this.writeJson(rs, request, response);
						return;
					}
				} else if ("3".equals(status)) {// 激活不通过
					companyBase.setAuditStatus("23");
					companyBase.setAuditStage(userType);
					sendAuditMassge(base.getName(), base.getCode(), "审核", "6",
							auditContent, base.getStockCal());
					companyInfoService.saveEnterprise(companyBase);
				}
			} else if (SRRPConstant.USER_TYPE_06.equals(userType)) {
				if (base.getAuditStatus().equals("04")) {
					if ("5".equals(status)) {
						String oldCode=companyBase.getCode();
						AusdiPass(base, companyBasePending, companyBase,
								enterpriseId, comattchPengding02,
								comattchPengding01, stockholderPendingList,companyObjectionPendings);
						base.setAuditStatus("05");
						base.setObjecChangFlag("0");
						base.setAuditStage(userType);
						companyInfoService.saveEnterprise(base);
						updateEnterpriseSZJF1(base,oldCode);
					} else {
						companyBase.setAuditStatus("06");
						base.setAuditStage(userType);
						companyInfoService.saveEnterprise(companyBase);
						sendAuditMassge(base.getName(), base.getCode(), "审核",
								"16", auditContent, base.getStockCal());
					}

				} else {
					if ("2".equals(status)) {
						//获取到传值的map
						Map<String, Object> resultMap=gentcompanyMap(request,companyBase,comattchPengding01,comattch01);
						if(SRRPConstant.ZXSQ_RESULT_200.equals(resultMap.get("reponseCode"))){
							AusdiPass(base, companyBasePending, companyBase,
									enterpriseId, comattchPengding02,
									comattchPengding01, stockholderPendingList,companyObjectionPendings);
							// 用待审核表中的信息给基本信息赋值
							base.setAuditStage(userType);
							base.setObjecChangFlag("0");
							base.setAuditStatus("22");// 向数据库中插入审核状态，4是修改未审核
							// 审核通过之后发送审核通过的短信
							sendAuditMassge(base.getName(), base.getCode(), "审核",
									"4", auditContent, base.getStockCal());
							// 更新最新的基本信息
							companyInfoService.saveEnterprise(base);
							updateEnterpriseSZJF(base);
						}else if(SRRPConstant.ZXSQ_RESULT_400.equals(resultMap.get("reponseCode") )&&"本机构已登记过该企业授权，不允许重复提交。".equals(resultMap.get("reponseMsg"))){ 
							AusdiPass(base, companyBasePending, companyBase,
									enterpriseId, comattchPengding02,
									comattchPengding01, stockholderPendingList,companyObjectionPendings);
							// 用待审核表中的信息给基本信息赋值
							base.setAuditStage(userType);
							base.setObjecChangFlag("0");
							base.setAuditStatus("22");// 向数据库中插入审核状态，4是修改未审核
							// 审核通过之后发送审核通过的短信
							sendAuditMassge(base.getName(), base.getCode(), "审核",
									"4", auditContent, base.getStockCal());
							// 更新最新的基本信息
							companyInfoService.saveEnterprise(base);
							updateEnterpriseSZJF(base);
						}
						else{
							rs = new ResultBean(resultMap.get("reponseCode").toString(),resultMap.get("reponseMsg").toString());
							this.writeJson(rs, request, response);
							return;
						}
						
						// 将待审核表中的数据存到历史记录表中---因为：待审核用户是代是金融办审核，所以激活后需新增一条市金融办的审核记录
						HistoryCompanyBase historyCompanyBase = new HistoryCompanyBase();
						Date now = new Date();//获取当前时间
						Date afterDate = new Date(now.getTime() - 2000);
						if (companyBasePending != null) {
							BeanUtils
									.copyProperties(companyBasePending, historyCompanyBase);
							historyCompanyBase.setRedate(companyBasePending.getRedate());// 添加注册时间
							historyCompanyBase
									.setOpertime(companyBasePending.getOperdate());// 添加操作时间
							historyCompanyBase.setAuditTime(afterDate);// 添加审核时间
						} else {
							BeanUtils.copyProperties(companyBase, historyCompanyBase);
							historyCompanyBase.setRedate(companyBase.getRedate());// 添加注册时间
							historyCompanyBase.setAuditTime(afterDate);// 添加审核时间
						}
						if (auditContent != null) {
							historyCompanyBase.setAuditContent(auditContent);// 添加审核内容
						}
						historyCompanyBase.setAuditId(userId);// 添加审核人id 获取当前登录用户的id
						// 添加审核状态
						historyCompanyBase.setAuditStatus("02");// 六种状态：1.代表审核通过，2.代表审核不通过
						historyCompanyBase.setStopFlag(companyBase.getStopFlag());
						companyManageServise.addHistoryCompanyBase(historyCompanyBase);
						
						
						
					} else {
						companyBase.setAuditStatus("03");
						companyBase.setAuditStage(userType);
						sendAuditMassge(base.getName(), base.getCode(), "审核",
								"6", auditContent, base.getStockCal());
						companyInfoService.saveEnterprise(companyBase);
						
					}
					
					
				}
			}
			// 将待审核表中的数据存到历史记录表中
			HistoryCompanyBase historyCompanyBase = new HistoryCompanyBase();
			if (companyBasePending != null) {
				BeanUtils
						.copyProperties(companyBasePending, historyCompanyBase);
				historyCompanyBase.setRedate(companyBasePending.getRedate());// 添加注册时间
				historyCompanyBase
						.setOpertime(companyBasePending.getOperdate());// 添加操作时间
				historyCompanyBase.setAuditTime(new Date());// 添加审核时间
			} else {
				BeanUtils.copyProperties(companyBase, historyCompanyBase);
				historyCompanyBase.setRedate(companyBase.getRedate());// 添加注册时间
				historyCompanyBase.setAuditTime(new Date());// 添加审核时间
			}
			if (auditContent != null) {
				historyCompanyBase.setAuditContent(auditContent);// 添加审核内容
			}
			historyCompanyBase.setAuditId(userId);// 添加审核人id 获取当前登录用户的id
			// 添加审核状态
			historyCompanyBase.setAuditStatus(companyBase.getAuditStatus());// 六种状态：1.代表审核通过，2.代表审核不通过
			historyCompanyBase.setStopFlag(companyBase.getStopFlag());
			companyManageServise.addHistoryCompanyBase(historyCompanyBase);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("timed out")) {
				rs = new ResultBean("400", "网络连接超时，请稍后再试！");
			}else{
				rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			}
			
		}
		this.writeJson(rs, request, response);
	}
	
	public  Map<String, Object> gentcompanyMap(HttpServletRequest request,CompanyBase companyBase, CompanyAttachmentPending comattchPengding01, CompanyAttachment comattch01) throws Exception {
		Map<String, String> baseMap=new HashMap<String, String>();
		Map<String, Object> resultMap=null;
		String filePath="";
		if(comattchPengding01!=null){
			filePath=comattchPengding01.getFilepath();
		}else{
			filePath=comattch01.getFilepath();
		}
			String fileType=filePath.substring(filePath.lastIndexOf(".")+1);
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			baseMap.put("authFilePath",encoder.encode(FileUtil.getBytesByFile(filePath)));//影像信息的流
			baseMap.put("fileType",fileType);//企业或者机构的名称
			baseMap.put("corpName",companyBase.getName());//企业或者机构的名称
			baseMap.put("cardCode", companyBase.getCode());//组织机构码、社会信用码
			baseMap.put("cardType", companyBase.getCodetype());//证件码类型
			baseMap.put("legalName", companyBase.getLegalName());//法人姓名
			baseMap.put("legalPhone",companyBase.getLegalCal() );//法人电话
			baseMap.put("contactName",companyBase.getStockName() );//联系人姓名
			baseMap.put("contactPhone",companyBase.getStockCal() );//联系人电话
			resultMap=companyInfoService.importZXSQInfo(baseMap,"01");
		    return resultMap;
	}

	// 调用接口同步信息
	public void updateEnterpriseSZJF(CompanyBase base) {
		CompanyInfoVo companyRestVo = new CompanyInfoVo();
		companyRestVo.setName(base.getName());
		companyRestVo.setUser_id(base.getCode());
		companyRestVo.setCodetype(base.getCodetype());
		companyRestVo.setCode(base.getCode());
		companyRestVo.setContacname(base.getStockName());
		companyRestVo.setContaccal(base.getStockCal());
		companyRestVo.setLegalname(base.getLegalName());
		companyRestVo.setLegalcal(base.getLegalCal());
		companyRestVo.setBranchno(base.getRearea());
		companyRestVo.setStatus("2");
		Map maps = userService.addCompanyBaseUser(companyRestVo, "01");
		System.out.println("调用信用报告接口结束，返回结果:" + maps.get("validStatus"));
	}
	// 调用接口同步信息
    public void updateEnterpriseSZJFStop(CompanyBase base) {
        CompanyInfoVo companyRestVo = new CompanyInfoVo();
        companyRestVo.setName(base.getName());
        companyRestVo.setUser_id(base.getCode());
//        companyRestVo.setCodetype(base.getCodetype());
//        companyRestVo.setCode(base.getCode());
//        companyRestVo.setContacname(base.getStockName());
//        companyRestVo.setContaccal(base.getStockCal());
//        companyRestVo.setLegalname(base.getLegalName());
//        companyRestVo.setLegalcal(base.getLegalCal());
//        companyRestVo.setBranchno(base.getRearea());
//        companyRestVo.setStatus("2");
        Map maps = userService.addCompanyBaseUser(companyRestVo, "01");
        System.out.println("调用信用报告接口结束，返回结果:" + maps.get("validStatus"));
    }
	public void updateEnterpriseSZJF1(CompanyBase base,String oldCode) {
		CompanyInfoVo companyRestVo = new CompanyInfoVo();
		companyRestVo.setUser_id(base.getCode());
		companyRestVo.setName(base.getName());
		companyRestVo.setCodetype(base.getCodetype());
		companyRestVo.setOldCode(oldCode);
		companyRestVo.setCode(base.getCode());
		companyRestVo.setContacname(base.getStockName());
		companyRestVo.setContaccal(base.getStockCal());
		companyRestVo.setLegalname(base.getLegalName());
		companyRestVo.setLegalcal(base.getLegalCal());
		companyRestVo.setBranchno(base.getRearea());
		companyRestVo.setStatus("2");
		Map maps = userService.addCompanyBaseUser(companyRestVo, "02");
		System.out.println("调用信用报告接口结束，返回结果:" + maps.get("validStatus"));
	}

	// 不通过
	public void AusditNoPass(CompanyBasePending companyBasePending,
			CompanyAttachmentPending comattchPengding02,
			CompanyAttachmentPending comattchPengding01,
			List<CompanyStockholderPending> stockholderPendingList) {
		if (stockholderPendingList != null) {
			for (CompanyStockholderPending companyStockholderPending : stockholderPendingList) {
				companyInfoService
						.deleteStockholderPeding(companyStockholderPending
								.getHolderId());
			}
		}
		// 删除待审核表中的信息
		if (companyBasePending != null) {
			companyManageServise
					.deleteCompanyBasePendingById(companyBasePending.getId());
		}
		if (comattchPengding02 != null) {// 根据查询出的数据的createid删除待审核表中数据
			companyInfoService.deleCompanyAttachmentPending(comattchPengding02
					.getCreaterId());
		}
		if (comattchPengding01 != null) {// 根据查询出的数据的createid删除待审核表中数据
			companyInfoService.deleCompanyAttachmentPending(comattchPengding01
					.getCreaterId());
		}
		// 审核不通过之后发送审核不通过的短信
	}

	// 修改通过
	public void AusdiPass(CompanyBase base,
			CompanyBasePending companyBasePending, CompanyBase companyBase,
			String enterpriseId, CompanyAttachmentPending comattchPengding02,
			CompanyAttachmentPending comattchPengding01,
			List<CompanyStockholderPending> stockholderPendingList,List<CompanyObjectionPending> companyObjectionPendings) {
		// 创建企业基本信息对象
		// 用待审核表中的信息给基本信息赋值
		if (companyBasePending != null) {
			PlatformUser user = userService
					.findUserByUserName(companyBasePending.getCode());
			if (user == null) {// 如果为空，证明待审核表中的证件号码被修改了
				// 查询出基本信息表中的数据
				user = userService.findUserByUserName(companyBase.getCode());
			}
			user.setUsername(companyBasePending.getCode());// 更改用户的用户名
			user.setNickname(companyBasePending.getName());// 更改用户昵称
			userService.updateSystemUser(user);// 更新用户表的信
			// 获得到要更新的角色
			PlatformUserRole userRole = roleService.getRoleById(user.getId());
			if (userRole == null) {
				// 需要先删除原有的关联关系，然后存入新的关联关系
				roleService.DelAllRole(user.getId());
				String roleId = config.getConfigValueByName("entpriseUserRole");
				roleService.InveInsertUserRoleRel(user.getId(), roleId,
						user.getNickname());
			} else {
				String roleId = config.getConfigValueByName("entpriseUserRole");
				Long rId = Long.valueOf(roleId);
				userRole.setRoleId(rId);
				// 更新角色表
				roleService.UpdateUserRoleRel(userRole);
			}
		}
		if (stockholderPendingList.size() != 0) {// 如果表中有数据
			// 查询正式表中是否有数据
			List<CompanyStockholder> stockholderList = companyInfoService
					.getCompanyStockholders(enterpriseId);
			if (stockholderList.size() > 0) {
				for (CompanyStockholder companyStockholder : stockholderList) {
					companyInfoService.deleteStockholder(companyStockholder
							.getHolderId());
				}
			}
		}

		if (stockholderPendingList != null) {// 删除股东信息
			for (CompanyStockholderPending companyStockholderPending : stockholderPendingList) {
				CompanyStockholder stockholder = new CompanyStockholder();
				BeanUtils
						.copyProperties(companyStockholderPending, stockholder);
				companyInfoService.addCompanyStockholder(stockholder);
				companyInfoService
						.deleteStockholderPeding(companyStockholderPending
								.getHolderId());
			}
		}
		if(companyObjectionPendings!=null){
			//待审核表不为空则清空正式表里面的数据
			 companyInfoService.delObjectionByEnterpriseId(enterpriseId);
			for (CompanyObjectionPending companyObjectionPending : companyObjectionPendings) {
				CompanyObjection objection = new CompanyObjection();
				BeanUtils.copyProperties(companyObjectionPending,objection);
				//向正式表中插入新的数据
				companyInfoService.addObjection(objection);
			}
			//审核通过删除待审核表中的数据
			companyInfoService.delObjectionPendingByEnterpriseId(enterpriseId);
		}
		
		// 删除待审核表中的数据
		if (companyBasePending != null) {
			companyManageServise
					.deleteCompanyBasePendingById(companyBasePending.getId());
		}
		if (comattchPengding02 != null) {// 待审核的数据中有值
			// 查询正式表中的数据更新数据
			CompanyAttachment companyAttachment02 = companyInfoService
					.findAttachmentByType(enterpriseId, "02");
			companyAttachment02.setFilepath(comattchPengding02.getFilepath());
			companyAttachment02.setFileName(comattchPengding02.getFileName());
			companyInfoService.addCompanyAttachment(companyAttachment02);// 保存更新数据到表中
			companyInfoService.deleCompanyAttachmentPending(comattchPengding02
					.getCreaterId());
		}
		if (comattchPengding01 != null) {// 待审核的数据中有值
			// 查询正式表中的数据更新数据
			CompanyAttachment companyAttachment01 = companyInfoService
					.findAttachmentByType(enterpriseId, "01");
			companyAttachment01.setFilepath(comattchPengding01.getFilepath());
			companyAttachment01.setFileName(comattchPengding01.getFileName());
			companyInfoService.addCompanyAttachment(companyAttachment01);// 保存更新数据到表中
			companyInfoService.deleCompanyAttachmentPending(comattchPengding01
					.getCreaterId());// 删除待审核表中数据
		}

	}

	// 修改不通过
	public void updateAusditNoPass() {

	}

	/**
	 * 
	 * @Title: updateStatus @Description: TODO(修改机构状态) @param @param model @param @param
	 *         httpRequest @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/updateStopFlag")
	public void updateStatus(Model model, HttpServletRequest httpRequest) {
		try {
			String enterpriseId = httpRequest.getParameter("enterpriseId");
			String stopFlag = httpRequest.getParameter("stopFlag");
			// 根据企业的id更新企业的基本启用停用状态
			companyInfoService.updateStopFlag(stopFlag, enterpriseId);
			// 根据企业用户的id更新企业用户的状态
			int stopFlagNum = Integer.parseInt(stopFlag);
            userService.updateInvestorUserFlag(enterpriseId, stopFlagNum);
			CompanyBase companyBase = companyInfoService.getCompanyBase(enterpriseId);
		    CompanyInfoVo companyRestVo = new CompanyInfoVo();
            companyRestVo.setCode(companyBase.getCode());
             companyRestVo.setName(companyBase.getName());
             if(stopFlagNum==1){//1:启用
                 stopFlagNum=0;//金服字典转换
             }else{// 2:停用
                 stopFlagNum=1; 
             }
             companyRestVo.setStopFlag(String.valueOf(stopFlagNum));
            Map maps = userService.addCompanyBaseUser(companyRestVo, "02");
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: resetPassword @Description: TODO(重置密码) @param @param httpRequest @param @return
	 *         设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/resetPassword")
	@ResponseBody
	public void resetPassword(HttpServletRequest httpRequest) {
		String certNo = httpRequest.getParameter("userName");
		try {
			PlatformUser user = userService.findUserByUserName(certNo);
			CompanyInfoVo companyRestVo = new CompanyInfoVo();
			String password = config.getConfigValueByName(PASSWORD);
			companyRestVo.setUser_pwd(MD5.MD5(password));
			companyRestVo.setCode(certNo);
			CompanyBase base=companyInfoService.queryByCertno(certNo);
			if(base!=null){
				companyRestVo.setName(base.getName());
			}
			Map maps = userService.addCompanyBaseUser(companyRestVo, "02");
			String validStatus = (String) maps.get("validStatus");// 1// 修改密码成功，0修改密码失败
			if(user!=null){
				if (validStatus != null) {
					if ("1".equals(validStatus)) {// 修改密码成功
						userService.resetPassword(user);
						userService.addSystemUser(user);
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 企业用户解锁
	 * 
	 * @param httpRequest
	 */
	@RequestMapping(value = "/unlockUser")
	public void unlockUser(ResultBean rs, HttpServletRequest httpRequest) {
		String certNo = httpRequest.getParameter("id");
		try {
			PlatformUser user = userService.findUserByUserName(certNo);
			user.setLockFlag(0);
			userService.addSystemUser(user);
			PlatformUserLoginLog log = new PlatformUserLoginLog();
			log.setUserId(user.getId());
			log.setSuccessFlag(1);
			log.setLoginTime(SysDate.getSysDate());
			String ip = httpRequest.getRemoteAddr();
			log.setLoginIp(ip);
			log.setFailReason(user.getNickname() + "UNLOCK");
			userService.insertLoginLog(log);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
		}
	}

	/**
	 * 审核结果发送短信
	 * 
	 * @param name
	 *            企业名称
	 * @param userName
	 *            用户名
	 * @param keys
	 *            短信主键 4 的时候是审核通过 6的时候是审核不通过
	 * @param auditContent
	 *            审核不通过的原因 phoneNum
	 */
	public void sendAuditMassge(String name, String userName, String status,
			String flag, String auditContent, String phoneNum) {
		@SuppressWarnings("unused")
		boolean result = false;
		try {
			if ("4".equals(flag)) {// 审核通过的情况下
				String arry[] = new String[3];
				arry[0] = name;// 第一个参数为企业名称
				arry[1] = userName;// 第二个参数为用户名
				arry[2] = "发布";
				String[] keys = gataWayCompanyInfoService.keys(flag);
				result = SmsClientUtil.querySms(arry, keys, phoneNum, flag);
			} else if ("6".equals(flag) || "16".equals(flag)) {
				String arry[] = new String[3];
				arry[0] = name;// 第一个参数为企业名称
				arry[2] = auditContent;// 第三个参数为审核没有通过的原因
				arry[1] = status;// 第二个参数为审核没有通过的原因
				String[] keys = gataWayCompanyInfoService.keys(flag);
				result = SmsClientUtil.querySms(arry, keys, phoneNum, flag);
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}

	}

	public List<QueryCompanyAuditResult> getAuditList(String enterpriseId) {
		List<QueryCompanyAuditResult> auditResults = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			auditResults = companyManageServise.getAuditList(enterpriseId);
			for (QueryCompanyAuditResult queryCompanyAuditResult : auditResults) {
				queryCompanyAuditResult.setAuditTimeStr(sdf
						.format(queryCompanyAuditResult.getAuditTime()));// 审核时间格式化
				queryCompanyAuditResult.setAuditStatusStr(RedisGetValue
						.getValueByCode(SRRPConstant.DD_COMPANY_AUSTSTAUS,
								queryCompanyAuditResult.getAuditStatus()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return auditResults;
	}

	
	
}

package com.icfcc.ssrp.web.managedept;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievement;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAchievementPending;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditPending;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorAuditRecord;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievement;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.InvestorManageAchievementPending;
import com.icfcc.SRRPDao.jpa.entity.managedept.InvestorManageResutList;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManageDissentInfor;
import com.icfcc.SRRPDao.jpa.entity.managedept.MessageAlertInfo;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserLoginLog;
import com.icfcc.SRRPDao.jpa.repository.enterprise.ScoreDao;
import com.icfcc.SRRPService.PlatformSystem.PlatformConfigService;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.InvestorAuditService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.SRRPService.managedept.ManageDissentService;
import com.icfcc.ssrp.web.SRRPBaseController;
import io.netty.util.internal.StringUtil;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisCacheManager;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.credit.platform.util.SysDate;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: ManagedeptOnlineForumController
 * @Description: TODO(在线提问解答功能控制器)
 * @author hugt
 * @date 2017年9月23日 上午9:11:42
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/investorManage")
public class InvestorManageController extends SRRPBaseController {
	private static Logger log = LoggerFactory
			.getLogger(InvestorManageController.class);
	@Autowired
	private InvestorService investorService;
	@Autowired
	private InvestorAuditService investorAuditService;
	@Autowired
	private PlatformConfigService config;

	@Autowired
	protected PlatformUserService userService;
	@Autowired
	private ScoreDao scoreDao;
	
	@Autowired
	private ManageDissentService manageDissentService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 DecimalFormat df = new DecimalFormat("###.##");
	/**
	 * 
	 * @Title: investorManageInit @Description: TODO(进入投资机构管理页面) @param @param
	 *         request @param @param response @param @return HTML url @return
	 *         String 返回类型 @throws
	 */
	@RequestMapping(value = "/investorManageInit")
	public String investorManageInit(HttpServletRequest request,
			HttpServletResponse response) {
		String userType = RedisGetValue.getRedisUser(request, "orgNo");
	      String userId = RedisGetValue.getRedisUser(request, "userId");// 用户类型
		List<DD> ddAuditStatus2 = RedisGetValue.getDataList(SRRPConstant.DD_AUDITSTATE);
		List<DD> ddAuditStatus = new ArrayList<DD>();
		if (SRRPConstant.USER_TYPE_05.equals(userType)) {
			for(DD dd:ddAuditStatus2){
				if(dd.getDicCode().equals("21")|| dd.getDicCode().equals("22")  || dd.getDicCode().equals("23")){
					ddAuditStatus.add(dd);
				}
			}	
		}else if (SRRPConstant.USER_TYPE_04.equals(userType)|| SRRPConstant.USER_TYPE_06.equals(userType)){
			for(DD dd:ddAuditStatus2){
				if(!dd.getDicCode().equals("21")&& !dd.getDicCode().equals("22")  && !dd.getDicCode().equals("23")){
					ddAuditStatus.add(dd);
				}
			}	
			 List<MessageAlertInfo> MessageAlertInfos= manageDissentService.findMessageByMessagType(userId,SRRPConstant.MESSAGE_TYPE_02);
             request.setAttribute("messageAlertInfos", MessageAlertInfos);
             int messageNum=0;
             if(MessageAlertInfos!=null){
                 messageNum=MessageAlertInfos.size();
             }
             request.setAttribute("messageNum", messageNum);
		}else if(SRRPConstant.USER_TYPE_03.equals(userType)){
			for(DD dd:ddAuditStatus2){
				if(!dd.getDicCode().equals("4")&& !dd.getDicCode().equals("5")  && !dd.getDicCode().equals("6")&&!dd.getDicCode().equals("21")){
					ddAuditStatus.add(dd);
				}
			}	
			for(DD dd:ddAuditStatus2){
				if(dd.getDicCode().equals("4")|| dd.getDicCode().equals("5")  || dd.getDicCode().equals("6")){
					ddAuditStatus.add(dd);
				}
			}	
			List<MessageAlertInfo> MessageAlertInfos= manageDissentService.findMessageByMessagType(userId,SRRPConstant.MESSAGE_TYPE_03);
            String isHaveMessage="0";
            if(MessageAlertInfos.size()>0){
                isHaveMessage="1";
            }
            request.setAttribute("isHaveMessage", isHaveMessage);
		}
		
		request.setAttribute("ddStatus", ddAuditStatus);
		request.setAttribute("userType", userType);
		String password = config.getConfigValueByName("password");
		request.setAttribute("password", password);
		return "WEB-INF/views/managedept/investorManage";
	}

	/**
	 * 
	 * @Title: onlineForumList @Description: TODO(初始化投资机构管理列表数据并条件查询) @param @param
	 *         model @param @param page @param @param request @param @param
	 *         response 设定文件 @return void 返回类型 @throws
	 */
	@RequestMapping(value = "/initInfo")
	public void initInfo(Model model, PageBean page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String limitStr = request.getParameter("limit");// 获取前台每页显示的最大记录数
			String startStr = request.getParameter("start");// 获取前台起始页
			String nameOrCode = request.getParameter("nameOrCode");// 获取投资机构名称或证件代码的查询条件
			String rearea = request.getParameter("rearea");// 区域
			String status = request.getParameter("stopFlag");// 获取机构状态的查询条件
			String auditStatus = request.getParameter("auditStatus");// 获取审核状态的查询条件
	         String userId = RedisGetValue.getRedisUser(request, "userId");// 用户类型
			String userType = RedisGetValue.getRedisUser(request, "orgNo");
			if (StringUtils.isNotBlank(startStr)) {
				page.setCurPage(Integer.parseInt(startStr));
			}
			if (StringUtils.isNotBlank(limitStr)) {
				page.setMaxSize(Integer.parseInt(limitStr));
			}
			// 获取审核状态的查询条件
			if(auditStatus=="21"){
				auditStatus="2";
			}
			// 查询结果集和分页
			List<InvestorManageResutList> inverstorList = investorService
					.getInvestorsForCharge(nameOrCode,rearea, status, auditStatus,
							page.getCurPage(), page.getMaxSize(),userType);
			for (InvestorManageResutList invest : inverstorList) {
				invest.setUserType(userType);// 添加一个字段用于判断用户的审核按钮
				if (!invest.getAuditStatus().equals("1")
						&& !invest.getAuditStatus().equals("3")) {
					PlatformUser user = userService.findUserByUserName(invest
							.getCertno());
					if (user != null) {
						if (user.getLockFlag() != null) {
							invest.setLockFlag(user.getLockFlag());
						}
					}
				}
				//查询企业异议
                ManageDissentInfor manageDissentInfor=manageDissentService.findDissentBycompanyId(invest.getInvestorId());
				if (SRRPConstant.USER_TYPE_03.equals(userType)) {
                    if(manageDissentInfor!=null){
                        invest.setIdHaveDissent("2");//区县金融办：有异议。标识为2 则显示修改异议，否则显示新增异议
                        if(SRRPConstant.DISSENT_STATUS_03.equals(manageDissentInfor.getDissentStatus())){
                            invest.setIdHaveDissent("0");//区县金融办：有异议为解除状态。标识为0显示新增异议
                        }
                    }
                    List<MessageAlertInfo> MessageAlertInfos= manageDissentService.findMessageBycompanyId(invest.getInvestorId(),SRRPConstant.MESSAGE_TYPE_03,userId);
                    if(MessageAlertInfos!=null){//
                        if(MessageAlertInfos.size()>0){
                            invest.setIsReadDissent("1");
                        }
                    }
                   
                }
				if (SRRPConstant.USER_TYPE_05.equals(userType)) {
					if(invest.getAuditStatus().equals("2")){
						invest.setAuditStatus("21");
					}
					if(invest.getAuditStatus().equals("4")||invest.getAuditStatus().equals("5")||invest.getAuditStatus().equals("6")){
						invest.setAuditStatus("22");
					}
				}
				if (SRRPConstant.USER_TYPE_04.equals(userType) || SRRPConstant.USER_TYPE_06.equals(userType)) {
					if(invest.getAuditStatus().equals("22") || invest.getAuditStatus().equals("23")){
						invest.setAuditStatus("2");
					}
					//判断每个企业是否有提异议
                    if(manageDissentInfor!=null){
                        invest.setIdHaveDissent("1");//有异议。标识为1
                        List<MessageAlertInfo> MessageAlertInfos= manageDissentService.findMessageBycompanyId(invest.getInvestorId(),SRRPConstant.MESSAGE_TYPE_02,userId);
                           if(MessageAlertInfos!=null){//判断是否未读
                               if(MessageAlertInfos.size()>0){
                                   invest.setIsReadDissent("1");
                               }
                           }

                    }
				}
				

			}
			page.setList(inverstorList);
			if (CollectionUtils.isNotEmpty(inverstorList)) {
				page.setList(inverstorList);
				page.setRecordCnt(Integer.parseInt(investorService
						.getInvestorsForChargeCount(nameOrCode,rearea, status,
								auditStatus,userType).toString()));
				if (StringUtils.isNotBlank(limitStr)) {
					page.setMaxSize(Integer.parseInt(limitStr));
				}
				if (StringUtils.isNotBlank(startStr)) {
					page.setCurPage(Integer.parseInt(startStr));
				}
				page.pageResult(inverstorList, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			request.setAttribute("userType", userType);
			
			// 将数据传输到前端
			this.writeJson(page, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			// TODO: handle exception
		}
	}

	/**
	 * @Title: investorDetails @Description: TODO(查询机构信息详情) @param @param model @param @param
	 *         httpRequest @param @param investorId @param @return 设定文件 @return
	 *         String 返回类型 @throws
	 */
	@RequestMapping(value = "/investorDetails")
	public String investorDetails(Model model, HttpServletRequest httpRequest,
			String investorId) {
		try {
			
			Investor investor = investorService.findInverstorById(investorId);// 机构id
			String userType = RedisGetValue.getRedisUser(httpRequest, "orgNo");
			// 如果是审核通过或者审核不通过的，则显示正式表的机构信息， 市金融办：审核通过的详情  1，正式表   2，
			if ("22".equals(investor.getAuditStatus())|| "5".equals(investor.getAuditStatus())|| "4".equals(investor.getAuditStatus())|| "6".equals(investor.getAuditStatus())) {
				investor.setOperationQualification3Dicname(RedisGetValue
						.getValueByCode(SRRPConstant.DD_RADIO,
								investor.getOperationQualification3()));
				investor.setAreaName(RedisGetValue.getValueByCode(
						SRRPConstant.DD_AREA, investor.getAreaCode()));
				investor.setOrganizationalFormDicname(RedisGetValue
						.getValueByCode(SRRPConstant.DD_ORGFORM,
								investor.getOrganizationalForm()));
				investor.setCreditUnhealthyDicname(RedisGetValue
						.getValueByCode(SRRPConstant.DD_RADIO,
								investor.getCreditUnhealthy()));
				investor.setMechanismDicname(RedisGetValue.getValueByCode(
						SRRPConstant.DD_RADIO, investor.getMechanism()));
				investor.setUserType(userType);
				httpRequest.setAttribute("investor", investor);
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
				httpRequest.setAttribute("investorAchievements",
						net.sf.json.JSONArray.fromObject(investorAchievements));
				httpRequest.setAttribute("manageAchievementList",
						net.sf.json.JSONArray.fromObject(manageAchievementList));
			} else {
				InvestorAuditPending pending = investorAuditService
						.findAuditPendingById(investorId);
				if (pending != null) {
					String status = RedisGetValue.getValueByCode(
							SRRPConstant.DD_AUDITSTATE,
							investor.getAuditStatus());
					if (!status.equals("") && status != null) {
						pending.setAuditStatusDicname(status);
					}
					pending.setAuditStatus(investor.getAuditStatus());
				}

				pending.setOperationQualification3Dicname(RedisGetValue
						.getValueByCode(SRRPConstant.DD_RADIO,
								investor.getOperationQualification3()));
				pending.setAreaName(RedisGetValue.getValueByCode(
						SRRPConstant.DD_AREA, pending.getAreaCode()));
				pending.setOrganizationalFormDicname(RedisGetValue
						.getValueByCode(SRRPConstant.DD_ORGFORM,
								pending.getOrganizationalForm()));
				pending.setCreditUnhealthyDicname(RedisGetValue.getValueByCode(
						SRRPConstant.DD_RADIO,
						pending.getCreditUnhealthy()));
				pending.setMechanismDicname(RedisGetValue.getValueByCode(
						SRRPConstant.DD_RADIO, pending.getMechanism()));
				pending.setUserType(userType);
				httpRequest.setAttribute("investor", pending);
				List<InvestorAchievementPending> investorAchievementPending = investorService
						.findAchievementspendingById(investorId);// 获取投资业绩的信息集合
				List<InvestorManageAchievementPending> manageAchievementPending = investorService
						.findAllManageAchievementPendingById(investorId); // 获取管理业绩信息集合
//				for (InvestorAchievementPending investorAchievement : investorAchievementPending) {
//					if(investorAchievement.getRateOfReturn()!=null){
//					    investorAchievement.setRateOfReturnStr(df.format(investorAchievement.getRateOfReturn()));
//						if(investorAchievement.getRateOfReturn()==0){
//							investorAchievement.setRateOfReturnStr("");
//						}
//					}
//				}
				httpRequest.setAttribute("investorAchievements",
						net.sf.json.JSONArray.fromObject(investorAchievementPending));
				httpRequest.setAttribute("manageAchievementList",
						net.sf.json.JSONArray.fromObject(manageAchievementPending));
			}
			// 查询机构的审核记录
			List<InvestorAuditRecord> investorAuditRecords = investorAuditService
					.getAuditRecordByInvestorId(investorId);
			
			//查看未读消息完之后删除异议提醒
            String userId = RedisGetValue.getRedisUser(httpRequest, "userId");// 用户类型
           manageDissentService.deleteByMessageId(investorId,userId,SRRPConstant.MESSAGE_TYPE_03);
			httpRequest.setAttribute("investorAuditRecords",
					net.sf.json.JSONArray.fromObject(investorAuditRecords));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			// TODO: handle exception
		}

		// InverstorById(httpRequest,investorId);
		return "WEB-INF/views/managedept/investorDetails";

	}

	/**
	 * 
	 * @Title: auditInvestor @Description: TODO(查询审核页面) @param @param model @param @param
	 *         httpRequest @param @param investorId @param @return 设定文件 @return
	 *         String 返回类型 @throws
	 */
	@RequestMapping(value = "/auditInvestor")
	public String auditInvestor(Model model, HttpServletRequest httpRequest,
			String investorId) {
		try {
			Investor investor = investorService.findInverstorById(investorId);
			InvestorAuditPending pending = investorAuditService
					.findAuditPendingById(investorId);
			List<InvestorManageAchievement> manageAchievementList = investorService
					.findAllManageAchievementById(investorId);
			List<InvestorAchievement> investorAchievements = investorService
					.findAchievementsById(investorId);
			List<InvestorAchievementPending> investorAchievementPending = investorService
					.findAchievementspendingById(investorId);// 获取投资业绩的信息集合
			List<InvestorManageAchievementPending> manageAchievementPending = investorService
					.findAllManageAchievementPendingById(investorId); // 获取管理业绩信息集合
			for (InvestorAchievement investorAchievement : investorAchievements) {
				if(investorAchievement.getRateOfReturn()!=null){
				    investorAchievement.setRateOfReturnStr(df.format(investorAchievement.getRateOfReturn()));
					if(investorAchievement.getRateOfReturn()==0){
						investorAchievement.setRateOfReturnStr("");
					}
				}
			}
			if (investor.getAuditStatus() != SRRPConstant.EDIT_STATUS_PENGDING) {
				Map<String, String> map = new HashMap<String, String>();
				if (!(investor.getCerttype().equals(pending.getCerttype()))) {
					map.put("certtypeFlag", "1");
				}
				if (!(investor.getCertno().equals(pending.getCertno()))) {
					map.put("certnoFlag", "1");
				}
				if (!(investor.getOrganizationalForm().equals(pending.getOrganizationalForm()))) {
					map.put("organizationalFormFlag", "1");
				}
				if (!(investor.getName().equals(pending.getName()))) {
					map.put("nameFlag", "1");
				}
				if (!(investor.getRegisteTime()
						.equals(pending.getRegisteTime()))) {
					map.put("registeTimeFlag", "1");
				}
				if (!(investor.getAreaCode().equals(pending.getAreaCode()))) {
					map.put("areaCodeFlag", "1");
				}
				if (!(investor.getOrgType().equals(pending.getOrgType()))) {
					map.put("orgTypeFlag", "1");
				}
				if (!(investor.getCapitalType()
						.equals(pending.getCapitalType()))) {
					map.put("capitalTypeFlag", "1");
				}
				if (!(investor.getRegisteredCapital().toString().equals(pending
						.getRegisteredCapital().toString()))) {
					map.put("registeredCapitalFlag", "1");
				}
				if (!StringUtil.isNullOrEmpty(pending.getLicensePath())) {
					if (StringUtil.isNullOrEmpty(investor.getLicensePath())) {
						map.put("licensePathFlag", "1");
					} else if (!(investor.getLicensePath().equals(pending
							.getLicensePath()))) {
						map.put("licensePathFlag", "1");
					}
				}//营业执照的判断
				if (!StringUtil.isNullOrEmpty(pending.getCreditAuthorizationPath())) {
					if (StringUtil.isNullOrEmpty(investor.getCreditAuthorizationPath())) {
						map.put("authorizationPathFlag", "1");
					} else if (!(investor.getCreditAuthorizationPath().equals(pending
							.getCreditAuthorizationPath()))) {
						map.put("authorizationPathFlag", "1");
					}
				}//授权书的判断
				if (!StringUtil.isNullOrEmpty(pending.getRegisterAutographPath())) {
					if (StringUtil.isNullOrEmpty(investor.getRegisterAutographPath())) {
						map.put("registerPathFlag", "1");
					} else if (!(investor.getRegisterAutographPath().equals(pending
							.getRegisterAutographPath()))) {
						map.put("registerPathFlag", "1");
					}
				}//注册申请表
				if (!StringUtil.isNullOrEmpty(pending.getLogoPath())) {
					if (StringUtil.isNullOrEmpty(investor.getLogoPath())) {
						map.put("logoPathFlag", "1");
					} else if (!(investor.getLogoPath().equals(pending
							.getLogoPath()))) {
						map.put("logoPathFlag", "1");
					}
				}//logo
				if (!(investor.getRegCurrency()
						.equals(pending.getRegCurrency()))) {
					map.put("regCurrencyFlag", "1");
				}
				if (!(investor.getLegalRepresentative().equals(pending
						.getLegalRepresentative()))) {
					map.put("legalRepresentativeFlag", "1");
				}
				if (!(investor.getRegisteredAddress().equals(pending
						.getRegisteredAddress()))) {
					map.put("registeredAddressFlag", "1");
				}
				if (!(investor.getOfficeAddress().equals(pending
						.getOfficeAddress()))) {
					map.put("officeAddressFlag", "1");
				}
				if (!(investor.getOrganizationalForm().equals(pending
						.getOrganizationalForm()))) {
					map.put("organizationalFormFlag", "1");
				}
				if (!(investor.getLegalRepresentativeCall().equals(pending
						.getLegalRepresentativeCall()))) {
					map.put("legalRepresentativeCallFlag", "1");
				}
				if (!(investor.getPaidCapital()
						.equals(pending.getPaidCapital()))) {
					map.put("paidCapitalFlag", "1");
				}
				if (!(investor.getPcCurrency().equals(pending.getPcCurrency()))) {
					map.put("pcCurrencyFlag", "1");
				}
				if (!(investor.getRelName().equals(pending.getRelName()))) {
					map.put("relNameFlag", "1");
				}
				if (!(investor.getRelPhone().equals(pending.getRelPhone()))) {
					map.put("relPhoneFlag", "1");
				}
				if (!(investor.getEmail().equals(pending.getEmail()))) {
					map.put("emailFlag", "1");
				}
				if (!(investor.getCorepersonnel().equals(pending
						.getCorepersonnel()))) {
					map.put("corepersonnelFlag", "1");
				}
				if (!(investor.getCoreteam().equals(pending.getCoreteam()))) {
					map.put("coreteamFlag", "1");
				}
				if (!(investor.getOperationQualification1().equals(pending
						.getOperationQualification1()))) {
					map.put("operationQualification1Flag", "1");
				}
				if (!(investor.getOperationQualification2().equals(pending
						.getOperationQualification2()))) {
					map.put("operationQualification2Flag", "1");
				}
				if (!(investor.getOperationQualification3().equals(pending
						.getOperationQualification3()))) {
					map.put("operationQualification3Flag", "1");
				}
				if (!(investor.getTeamCount().equals(pending.getTeamCount()))) {
					map.put("teamCountFlag", "1");
				}
				if (!(investor.getCreditUnhealthy().equals(pending
						.getCreditUnhealthy()))) {
					map.put("creditUnhealthyFlag", "1");
				}
				if (!(investor.getSeniorManagement().equals(pending
						.getSeniorManagement()))) {
					map.put("seniorManagementFlag", "1");
				}
				if (!(investor.getMechanism().equals(pending.getMechanism()))) {
					map.put("mechanismFlag", "1");
				}
				if (!(investor.getCapitalMin().toString().equals(pending
						.getCapitalMin().toString()))) {
					map.put("capitalMinFlag", "1");
				}
				if (!(investor.getCapitalMax().toString().equals(pending
						.getCapitalMax().toString()))) {
					map.put("capitalMaxFlag", "1");
				}
				if (!(investor.getCurrencyDicname().equals(pending
						.getCurrencyDicname()))) {
					map.put("currencyDicnameFlag", "1");
				}
				if (!(investor.getPost().equals(pending.getPost()))) {
					map.put("postFlag", "1");
				}
				if (!(investor.getFinanceStage().equals(pending
						.getFinanceStage()))) {
					map.put("financeStageFlag", "1");
				}
				if (!(investor.getFinanceTrade().equals(pending
						.getFinanceTrade()))) {
					map.put("financeTradeFlag", "1");
				}
				pending.setFlagMap(map);
			}
			pending.setAuditStatusDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_AUDITSTATE, investor.getAuditStatus()));
			pending.setAuditStatus(investor.getAuditStatus());
			pending.setOperationQualification3Dicname(RedisGetValue
					.getValueByCode(SRRPConstant.DD_RADIO,
							pending.getOperationQualification3()));
			pending.setAreaName(RedisGetValue.getValueByCode(
					SRRPConstant.DD_AREA, pending.getAreaCode()));
			pending.setOrganizationalFormDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_ORGFORM, pending.getOrganizationalForm()));
			pending.setCreditUnhealthyDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_RADIO, pending.getCreditUnhealthy()));
			pending.setMechanismDicname(RedisGetValue.getValueByCode(
					SRRPConstant.DD_RADIO, pending.getMechanism()));
			httpRequest.setAttribute("investor", pending);
			// 查询机构的审核记录
			List<InvestorAuditRecord> investorAuditRecords = investorAuditService
					.getAuditRecordByInvestorId(investorId);
			
			String userId = RedisGetValue.getRedisUser(httpRequest, "userId");// 用户类型
	           manageDissentService.deleteByMessageId(investorId,userId,SRRPConstant.MESSAGE_TYPE_03);
			// 根据机构的id查询机构投资业绩
			// InvestorAchievement investorAchievement =
			// investorService.findAchievementById(investorId);
			if(investor.getAuditStatus().equals("1")|| investor.getAuditStatus().equals("4") || investor.getAuditStatus().equals("2")|| investor.getAuditStatus().equals("6")){
				httpRequest.setAttribute("investorAchievements",
						net.sf.json.JSONArray
								.fromObject(investorAchievementPending));
			}else{
				httpRequest.setAttribute("investorAchievements",
						net.sf.json.JSONArray
								.fromObject(investorAchievements));
			}
			if(investor.getAuditStatus().equals("1")|| investor.getAuditStatus().equals("4") || investor.getAuditStatus().equals("2")|| investor.getAuditStatus().equals("6")){
				httpRequest.setAttribute("manageAchievementList",
						net.sf.json.JSONArray
								.fromObject(manageAchievementPending));
			}else{
				httpRequest.setAttribute("manageAchievementList",
						net.sf.json.JSONArray
								.fromObject(manageAchievementList));
			}
			httpRequest.setAttribute("investorAuditRecords",
					net.sf.json.JSONArray.fromObject(investorAuditRecords));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			// TODO: handle exception
		}

		return "WEB-INF/views/managedept/auditInvestor";
	}

	/**
	 * 
	 * @Title: updateStatus @Description: TODO(修改机构状态) @param @param model @param @param
	 *         httpRequest @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/updateStatus")
	public void updateStatus(Model model, HttpServletRequest httpRequest) {
		try {
			String investorId = httpRequest.getParameter("investorId");
			String stopFlag = httpRequest.getParameter("stopFlag");
			investorService.updateStopFlag(investorId, stopFlag);
			InvestorAuditPending pending = null;
			pending = investorAuditService.findAuditPendingById(investorId);
			if (pending != null) {
				pending.setStopFlag(stopFlag);
				investorAuditService.saveInvestorAuditPending(pending);
			}
			userService.updateInvestorUserFlag(investorId,
					Integer.parseInt(stopFlag));
			List<PlatformUser> list = userService.findByUserOrg(investorId);
			for (PlatformUser user : list) {
				removeSessionId(user.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	private void removeSessionId(String userId) {
		WebApplicationContext wac = ContextLoader
				.getCurrentWebApplicationContext();
		RedisCacheManager redisCacheManager = (RedisCacheManager) wac
				.getBean("redisCacheManager");
		Cache<Object, Object> cache = redisCacheManager
				.getCache("shiro-kickout-session");
		String sessionId = null;
		if (cache.get(userId) != null) {
			sessionId = cache.get(userId).toString();
			cache.remove(sessionId);
		}

	}

	/**
	 * @Title: auditSubmit @Description: TODO(提交审核信息) @param @param rs @param @param
	 *         request @param @param response 设定文件 @return void 返回类型 @throws
	 */
	@RequestMapping(value = "/auditSubmit")
	public void auditSubmit(ResultBean rs, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String inverstorAuditStr = request.getParameter("investorAudit");// 审核信息
			InvestorAuditRecord investorAudit = null;
			String userType = RedisGetValue.getRedisUser(request, "orgNo");// 获取当前登陆用户的类型
			if (inverstorAuditStr != null && !"".equals(inverstorAuditStr)
					&& !"\"\"".equals(inverstorAuditStr)) {
				investorAudit = (InvestorAuditRecord) JSON.parseObject(
						inverstorAuditStr, InvestorAuditRecord.class);
			}
			Date nowDate = new Date();
			investorAudit.setAuditTime(nowDate);// 审核时间
			String auditorName = RedisGetValue
					.getRedisUser(request, "username");// 获取审核人
			String auditorId = RedisGetValue.getRedisUser(request, "userId");// 获取审核人ID
			investorAudit.setAuditorName(auditorName);
			investorAudit.setAuditorId(auditorId);
			String basePath=request.getSession().getServletContext().getRealPath("/");
			Map<String, Object> resultMap=investorAuditService.auditInvestor(investorAudit,userType,basePath);// 提交审核
			if(SRRPConstant.ZXSQ_RESULT_200.equals(resultMap.get("reponseCode"))){
				rs = ResultBean.sucessResultBean();
			}else{
				rs = new ResultBean(resultMap.get("reponseCode").toString(),resultMap.get("reponseMsg").toString());
			}
			
			//investorAudit.getAuditContent();

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			if (e.getMessage().contains("timed out")) {
				rs = new ResultBean("400", "网络连接超时，请稍后再试！");
			}else{
				rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			}
		}
		this.writeJson(rs, request, response);
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
			userService.resetPassword(user);
			userService.addSystemUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: resetPassword @Description: TODO(解锁用户) @param @param httpRequest @param @return
	 *         设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/setLock")
	@ResponseBody
	public void setLock(HttpServletRequest httpRequest) {
		String certNo = httpRequest.getParameter("userName");
		try {
			PlatformUser user = userService.findUserByUserName(certNo);
			user.setLockFlag(0);
			userService.saveUser(user);
			PlatformUserLoginLog log = new PlatformUserLoginLog();
			log.setUserId(user.getId());
			log.setSuccessFlag(1);
			log.setLoginTime(SysDate.getSysDate());
			String ip = httpRequest.getRemoteAddr();
			log.setLoginIp(ip);
			log.setFailReason(user.getNickname() + "UNLOCK");
			userService.insertLoginLog(log);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

}

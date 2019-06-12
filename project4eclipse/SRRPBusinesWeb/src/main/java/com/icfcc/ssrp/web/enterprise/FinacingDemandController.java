package com.icfcc.ssrp.web.enterprise;

import io.netty.util.internal.StringUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBusinessplan;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEvent;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingRecord;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.enterprise.UnionDemandInvestorResult;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.QueryFinacingDemandResult;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.FinacingEventService;
import com.icfcc.SRRPService.enterprise.FinacingRecordService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.SRRPService.enterprise.SendSmService;
import com.icfcc.SRRPService.inverstorg.DemandInfoService;
import com.icfcc.SRRPService.inverstorg.FinacingDemandInfoService;
import com.icfcc.SRRPService.inverstorg.FinacingDemandService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;
import com.icfcc.ssrp.web.inverstorg.HomeTodoController;

/**
 * 融资需求的controller
 * 
 * @author kf2
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/finacingDemand")
public class FinacingDemandController extends SRRPBaseController {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory
			.getLogger(FinacingDemandController.class);

	@Autowired
	private FinacingDemandInfoService finacingDemandInfoService;

	@Autowired
	private CompanyInfoService companyInfoService;

	@Autowired
	private DemandInfoService demandInfoService;

	@Autowired
	private InvestorService investorService;

	@Autowired
	private FinacingRecordService finacingRecordService;

	@Autowired
	private PlatformUserService serService;
	@Autowired
	private FinacingDemandService finacingDemandService;

	@Autowired
	private HomeTodoController homeTodoController;
	
	@Autowired
	private FinacingEventService finacingEventService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private SendSmService sendSmService;
	/**
	 * 初始化我的需求页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/demandList")
	public String findDemandList(HttpServletRequest request,
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
		return "WEB-INF/views/enterprise/MyFinacingDemand";
	}

	/**
	 * 初始化投递需求页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/finacingDemandInfoDetail")
	public String sendFinacingDemandDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
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
			// 获取页面传递的参数
			String investorId = request.getParameter("investorIdList");
			// 获取融资阶段的字典
			List<DD> ddFinacingTurn = RedisGetValue
					.getDataList(SRRPConstant.DD_FINACINGTURN);
			List<DD> ddCurrency = RedisGetValue
					.getDataList(SRRPConstant.DD_CURRENCY);
			// 构造一个标志用于页面的判断
			boolean flag = false;
			// 判断页面传递的参数否为空
			if (!StringUtil.isNullOrEmpty(investorId)) {
				flag = true;
				investorId = investorId.replaceAll("'", "");
				// 页面参数不为空执行以下操作
				// 将拼接的机构的id字符串拆分成字符串数组
				String[] investorIdArry = convertStrToArray(investorId);
				String investorNameList = "";
				// 通过遍历数组，机构名称进行拼接
				for (int i = 1; i < investorIdArry.length; i++) {
					// if (i == investorIdArry.length - 1) {
					// // 第一个前边没有逗号
					// investorNameList = investorNameList
					// + investorService
					// .findInvestorNameById(investorIdArry[i]);
					// } else {
					investorNameList = investorNameList
							+ " "
							+ investorService
									.findInvestorNameById(investorIdArry[i])
							+ "  ";
					// }
				}
				// 向页面中传递参数
				request.setAttribute("investorIdList", investorId);
				request.setAttribute("investorNameList", investorNameList);
			}
			if (flag = true) {
				request.setAttribute("flag", flag);
			}
			request.setAttribute("ddFinacingTurn", ddFinacingTurn);
			// 需求发布时 默认联系人 联系电话
			if (!userInfos.isEmpty()) {
				CompanyBase baseInfo = companyInfoService
						.getCompanyBase(userInfos.get(SRRPConstant.LOGINORGNO));
				if (baseInfo != null) {
					FinacingDemandInfo finacingInfo = new FinacingDemandInfo();
					finacingInfo.setRelName(baseInfo.getStockName());
					finacingInfo.setRelPhone(baseInfo.getStockCal());
					if (!StringUtil.isNullOrEmpty(investorId)) {
						finacingInfo.setOpen("1");
					}
					request.setAttribute("finacingInfo", finacingInfo);
				}

			}
			request.setAttribute("isShow", isShow);
			request.setAttribute("ddCurrency", ddCurrency);
			return "WEB-INF/views/enterprise/sendFinacingDemand";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 初始化关闭原因页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/closeDemandReason")
	public String closeDemandReason(HttpServletRequest request,
			HttpServletResponse response) {
		String demandInfoId = request.getParameter("demandInfoId");
		request.setAttribute("demandInfoId", demandInfoId);
		return "WEB-INF/views/enterprise/CloseFinacingReason";
	}

	/**
	 * 初始化关闭原因页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sendFinacingRead")
	public String registerUserRead(HttpServletRequest request,
			HttpServletResponse response) {
		String open = request.getParameter("open");
		request.setAttribute("open", open);
		return "WEB-INF/views/enterprise/SendFinacingRead";
	}

	/**
	 * 企业菜单/我的需求/查询全部需求列表显示
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/demandInfoList")
	public void findFinacingDemandList(Model model, PageBean page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取页面中传递查询条件的参数
			String queryConditionString = request
					.getParameter("queryCondition");
			// 获取登陆的企业的id
			String enterpriseId = this.getUserInfo(request, response).get(
					"orgNo");// RedisGetValue.getRedisUser(request, "orgNo");
			// 获取分页信息
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
			// 如果参数不为空将参数转换成对象

			List<DD> ddFinacingStatus = RedisGetValue
					.getDataList(SRRPConstant.DD_DEMANDSTATUS);
			// 查询需求的列表（封装相关的实体类）
			List<QueryFinacingDemandResult> finacingDemandList = finacingDemandInfoService
					.findFinacingDemandListById(queryCondition, enterpriseId);
			if (finacingDemandList != null && finacingDemandList.size() > 0) {
				for (QueryFinacingDemandResult data : finacingDemandList) {
					if(data.getOperdate()!=null){
						data.setRemainTime(getRemainTime(data.getOperdate(), data.getOpen()));
					}
					
					// 2018年1月10日 10:29:08 添加了是否能够发布的状态判断发布
					Date nowTime = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if (data.getFollowTime() != null) {
						if("00".equals(data.getStatus())){
							data.setPublishFlag("1");
						}else{
							if (data.getFollowTime().compareTo(nowTime) < 0) {
								data.setPublishFlag("0");// 当为1时，是可以进行发布
							}
						}
						
						
						data.setCopyShowFlag("1");
						// 融资成功不能进行复制
						if (!SRRPConstant.DEMANDSTATUS03.equals(data
								.getStatus())) {
							if (SRRPConstant.DEMANDSTATUS99.equals(data.getStatus())) {
								if("0".equals(data.getRevokeFlag())){//如果没有撤销，显示复制按钮
									data.setCopyShowFlag("0");
								}
							}
						}
					} else {
						if("00".equals(data.getStatus())){
							data.setPublishFlag("1");
						}
						data.setCopyShowFlag("1");
						// 融资成功不能进行复制
						if (!SRRPConstant.DEMANDSTATUS03.equals(data
								.getStatus())) {
							if (SRRPConstant.DEMANDSTATUS99.equals(data
									.getStatus())) {
								if("0".equals(data.getRevokeFlag())){//如果没有撤销，显示复制按钮
									data.setCopyShowFlag("0");
								}
							}
						}
					}
					
					// 判断需求是否为定向发布的需求
					if ("1".equals(data.getOpen())) {
						// 如果是定向的需求判断是否超过了有效关注时间
						Date now = new Date();
						Date sqlDate = new java.sql.Date(data.getFollowTime()
								.getTime());
						if("01".equals(data.getStatus())){//如果当前的需求状态为发布状态
							if (sqlDate.compareTo(now) < 0) {// 如果查询的时间小于当前时间的话则说明超过了有效关注时间
								data.setChangeOpenFlag("0");
								data.setCopyShowFlag("0");
							}
						}
						if("99".equals(data.getStatus())){//如果当前的需求状态为关闭状态
							if (data.getCloseReason().equals("需求到期平台已自动关闭需求")) {// 如果此需求是因为无机构关注过期，则出现公开按钮
								data.setChangeOpenFlag("0");
							}
						}
						data.setPublish("0");
					}else{
						//如果是公开发布的的需求，根据需求的id查询需求的相关融资事件，并判断是否有融资事件未进行信息披露
						//如果有信息未披露的企业或者基金，添加标识传递到前台
						List<FinacingEvent> finacingEvents =finacingEventService.findFinacingEventByInfoId(data.getInfoId());
						boolean flag =false;
						for (FinacingEvent finacingEvent : finacingEvents) {
							if(("1".equals(finacingEvent.getPublishFlag())||"2".equals(finacingEvent.getPublishFlag()))&&"11".equals(finacingEvent.getStatus())){
								flag=true;
							}
						}
						if(flag){
							data.setPublish("1");
						}else{
							data.setPublish("0");
						}
						if("1".equals(data.getRevokeFlag())){//如果没有撤销，显示复制按钮
							data.setPublish("0");
						}
					}
					if(data.getStatus().equals(SRRPConstant.DEMANDSTATUS00)){
						data.setOperdate(null);
					}
				}
			}
			// 向页面传参
			page.setList(finacingDemandList);
			page.setRecordCnt(0);
			if (CollectionUtils.isNotEmpty(finacingDemandList)) {
				page.setList(finacingDemandList);
				// 设置总的条数
				page.setRecordCnt(Integer.valueOf(finacingDemandInfoService
						.getCount(queryCondition, enterpriseId).toString()));
				if (StringUtils.isNotEmpty(maxSize)) {
					page.setMaxSize(Integer.parseInt(maxSize));
				}
				if (StringUtils.isNotEmpty(currentPage)) {
					page.setCurPage(Integer.parseInt(currentPage));
				}
				page.pageResult(finacingDemandList, page.getRecordCnt(),
						page.getMaxSize(), page.getCurPage());
			}
			// 将数据传输到前端
			this.writeJson(page, request, response);

			request.setAttribute("ddFinacingStatus", ddFinacingStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 投递需求页面中发布功能的实现
	 * 
	 * 从页面获取机构的id和表单信息映射出事件的基本信息保存到融资时间表中
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("sendFinancingDemand")
	private void sendFinacingDemand(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String orgNo = userInfos.get(SRRPConstant.LOGINORGNO);
			// 根据登陆的企业名称，查询企业名称
			CompanyBase baseInfo = companyInfoService.getCompanyBase(orgNo);
			CompanyBaseSupplement supplement = companyInfoService
					.getCompanyBaseSupplement(orgNo);
			// String userName =baseInfo.getName();
			// 暂存：publish 发布save
			String operType = request.getParameter("operType");
			// 获取页面传递的参数
			String finacingDemandInfoString = request
					.getParameter("finacingDemandInfo");
			String investorId = request.getParameter("investorIdList");
			String fileUpdatePath = request.getParameter("fileUpdatePath");
			String fileName = request.getParameter("fileName");
			// 获取到的机构id进行数组拆分
			String[] investorIdArry = convertStrToArray(investorId);
			FinacingDemandInfo demandInfo = null;
			CompanyBusinessplan companyBusinessplan = new CompanyBusinessplan();
			if (finacingDemandInfoString != null
					&& !"".equals(finacingDemandInfoString)
					&& !"\"\"".equals(finacingDemandInfoString)) {
				demandInfo = (FinacingDemandInfo) JSON.parseObject(
						finacingDemandInfoString, FinacingDemandInfo.class);
				if (SRRPConstant.USD_KEY.equals(demandInfo.getCurrency())) {
					demandInfo.setAmountCNYMax(homeTodoController.getUSDToCNY(demandInfo.getAmountMax()));
					demandInfo.setAmountCNYMin(homeTodoController.getUSDToCNY(demandInfo.getAmountMin()));
				} else if (SRRPConstant.CNY_KEY.equals(demandInfo.getCurrency())) {
					demandInfo.setAmountCNYMax(demandInfo.getAmountMax());
					demandInfo.setAmountCNYMin(demandInfo.getAmountMax());
				}
				
			}
			// 获取缓存中的登录用户的企业id
			demandInfo.setEnterpriseId(orgNo);
			if ("publish".equals(operType)) {
				demandInfo.setStatus(SRRPConstant.DEMANDSTATUS00);
			} else if ("save".equals(operType)) {
				demandInfo.setStatus(SRRPConstant.DEMANDSTATUS01);
			}
			String newInfoId;
			// 创建uuid作为主键
			newInfoId = UUID.randomUUID().toString();
			newInfoId = newInfoId.replace("-", "");

			if (StringUtils.isNotEmpty(supplement.getIndustry())) {
				String industryStr = supplement.getIndustry();// 获取数据库中行业的展示
				if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
					supplement.setIndustry2(supplement.getIndustry());
					supplement.setIndustryStr(supplement.getIndustry2Dicname());
				} else {// 如果是一级的行业显示以及行业
					supplement.setIndustry(supplement.getIndustry());
					supplement.setIndustryStr(supplement.getIndustryDicname());
				}
			}
			String currencyStr = "";
			if (SRRPConstant.CNY_KEY.equals(demandInfo.getCurrency())) {
				currencyStr = "元";
			} else if(SRRPConstant.USD_KEY.equals(demandInfo.getCurrency())){
				currencyStr = "美元";
			}
			String projectName = "";// 拼接项目名 规则：行业+某公司+轮次+融资+投资金额
			// baseInfo.getName()
			projectName = supplement.getIndustryStr() + "行业某公司"
					+ demandInfo.getFinacingTurnDicname() + "融资"
					+ demandInfo.getAmount() + "百万" + currencyStr;
			demandInfo.setProjectName(projectName);
			demandInfo.setInfoId(newInfoId);
			// 有效关注时间的赋值
			if ("1".equals(demandInfo.getOpen())) {// 当选择定向投递的时候
				Date now = new Date();// 获取当前时间
				demandInfo.setFollowTime(getAfterTime(now, 20));
			}
			demandInfo.setOperdate(new Date());
			String userId = this.getUserInfo(request, response).get("userId");// RedisGetValue.getRedisUser(request,
																				// "userId");
			// 查询登陆用户
			PlatformUser platformUser = serService.getUser(userId);
			String operUser = platformUser.getUsername();
			demandInfo.setOperuser(operUser);
			demandInfo.setAppointInvestor(investorId);
			companyBusinessplan.setFileinfo(fileUpdatePath);
			companyBusinessplan.setFileName(fileName);
			companyBusinessplan.setInfoId(newInfoId);
			// 保存添加的融资需求到数据库中
			finacingDemandService.saveFinacingDemand(demandInfo);
			// 保存商业企划书的对应的对象到数据库中
			finacingDemandService.addCompanyBusinessplan(companyBusinessplan);
			// 机构的id数组进行遍历存入不同的事件
			if ("save".equals(operType)) {
				for (int i = 1; i < investorIdArry.length; i++) {
					// 创建融资事件的对象
					FinacingEvent finacingEvent = new FinacingEvent();
					// 通过需求，去映射融资事件
					BeanUtils.copyProperties(demandInfo, finacingEvent);
					finacingEvent.setAmount(null);
					List<PlatformUser> platformUsers = findByUserOrg(investorIdArry[i]);
					// 根据机构的id查询机构信息
					Investor investor = investorService
							.findInverstorById(investorIdArry[i]);
					for (PlatformUser platformUser2 : platformUsers) {
						// 创建了一个新的融资事件的id
						String neweventId = UUID.randomUUID().toString();
						neweventId = neweventId.replace("-", "");
						finacingEvent.setEventId(neweventId);
						finacingEvent.setInvestorgId(investorIdArry[i]);
						finacingEvent.setInfoId(newInfoId);
						finacingEvent.setMailuser(operUser);
						finacingEvent.setMaildate(new Date());
						finacingEvent.setOperuser(platformUser2.getUsername());
						finacingEvent.setPublishFlag("0");
						finacingEvent.setInvestLevel(platformUser2.getDesc1());
						finacingEvent.setFoundId(platformUser2.getId());
						finacingEventService.saveEvent(finacingEvent);
						String phone="";
						String name=platformUser2.getNickname();
						if(platformUser2.getDesc1().equals("0")){//机构
							phone=investor.getRelPhone();
						}else{
							phone=platformUser2.getTelephone();
						}
						//发送信息通知机构有企业向您投递了需求
						String status=sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_FOCUSBEFOEXPRICE,platformUser2.getNickname(),"" ,"","",phone);
						sendSmService.SaveSendLog("01",investorIdArry[i],neweventId,SRRPConstant.SMS_TYPE_FOCUSBEFOEXPRICE,phone,name,status,"");
					}
				}
			}
			// this.sendMyFina(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 投递需求页面中暂存功能的实现
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saveFinancingDemand")
	private void saveFinacingDemand(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> userInfo = this.getUserInfo(request, response);
			// 获取页面中的传参
			String finacingDemandInfoString = request
					.getParameter("finacingDemandInfo");
			String fileUpdatePath = request.getParameter("fileUpdatePath");
			String fileName = request.getParameter("fileName");
			FinacingDemandInfo demandInfo = null;
			// 创建商业企划书对象
			CompanyBusinessplan companyBusinessplan = new CompanyBusinessplan();
			// 对象接受页面中的传参
			if (finacingDemandInfoString != null
					&& !"".equals(finacingDemandInfoString)
					&& !"\"\"".equals(finacingDemandInfoString)) {
				demandInfo = (FinacingDemandInfo) JSON.parseObject(
						finacingDemandInfoString, FinacingDemandInfo.class);
				if (SRRPConstant.USD_KEY.equals(demandInfo.getCurrency())) {
					demandInfo.setAmountCNYMax(homeTodoController.getUSDToCNY(demandInfo.getAmountMax()));
					demandInfo.setAmountCNYMin(homeTodoController.getUSDToCNY(demandInfo.getAmountMin()));
				} else if (SRRPConstant.CNY_KEY.equals(demandInfo.getCurrency())) {
					demandInfo.setAmountCNYMax(demandInfo.getAmountMax());
					demandInfo.setAmountCNYMin(demandInfo.getAmountMax());
				}
			}
			// 存入模拟的企业的id
			demandInfo.setEnterpriseId(userInfo.get(SRRPConstant.LOGINORGNO));
			// 暂存为草稿状态，状态定为00
			demandInfo.setStatus("00");
			// 创建uuid作为主键
			String newInfoId = UUID.randomUUID().toString();
			newInfoId = newInfoId.replace("-", "");
			demandInfo.setInfoId(newInfoId);
			demandInfo.setOperdate(new Date());
			String userId = userInfo.get(SRRPConstant.LOGINUSERID);
			// 查询登陆用户
			PlatformUser platformUser = serService.getUser(userId);
			demandInfo.setOperuser(platformUser.getUsername());
			// 先保存融资需求信息
			finacingDemandService.saveFinacingDemand(demandInfo);
			// 保存商业企划书的路径
			companyBusinessplan.setInfoId(newInfoId);
			companyBusinessplan.setFileName(fileName);
			companyBusinessplan.setFileinfo(fileUpdatePath);
			// 保存商业企划书
			finacingDemandService.addCompanyBusinessplan(companyBusinessplan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件到服务器
	 * 
	 * @param file
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/fileUpload")
	public void fileUpload(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// 判断流是否为空
			if (!file.isEmpty()) {
				// 定义输出流 将文件保存在D盘下的dataImage file.getOriginalFilename()为获得文件的名字
				/*
				 * File files = new
				 * File("F:/workspace/SRRPBusinesWeb/src/main/webapp/static/images/"
				 * );
				 */
				// 创建文件保存的虚拟路径
				String url = "D:/businessplan/";
				String fileUrl = "static/updatefile/";
				File files = new File(url + fileUrl);
				// 判断路径是否为空，如果为空的话创建路径
				if (!files.exists()) {
					files.mkdirs(); // 创建文件
				}
				// 在文件路径下创建文件
				String path = files.getPath() + File.separator
						+ file.getOriginalFilename();
				// 构建文件流对象
				FileOutputStream os = new FileOutputStream(path);
				InputStream in = file.getInputStream();
				int b = 0;
				while ((b = in.read()) != -1) { // 读取文件
					os.write(b);
				}
				Map map = new HashMap();
				map.put("code", 0);
				map.put("name", file.getOriginalFilename());
				map.put("url",
						"D:/businessplan/" + fileUrl
								+ file.getOriginalFilename());
				// .println("../"+imgUrl+file.getOriginalFilename()+"============");
				this.writeJson(map, request, response);
				os.flush();
				os.close();// 关闭流
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 我的需求/需求列表/修改/修改页面的确认功能
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateFinacingInfo")
	public void updateFinacingInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> userInfo = this.getUserInfo(request, response);
			// 页面获取参数
			// 发布需求页面的基本信息
			String finacingDemandInfoString = request
					.getParameter("finacingDemandInfo");
			// 选择的机构id列表
			String investorId = request.getParameter("investorIdList");
			investorId = investorId.replaceAll("'", "");
			// 上传文件的路径
			String fileUpdatePath = request.getParameter("fileUpdatePath");
			String fileName = request.getParameter("fileName");

			// 获取到的机构id进行数组拆分
			String[] investorIdArry = convertStrToArray(investorId);
			// 将json字符串转换成功需求的实体类
			FinacingDemandInfo demandInfo = null;
			if (finacingDemandInfoString != null
					&& !"\"\"".equals(finacingDemandInfoString)) {
				demandInfo = (FinacingDemandInfo) JSON.parseObject(
						finacingDemandInfoString, FinacingDemandInfo.class);
				if (SRRPConstant.USD_KEY.equals(demandInfo.getCurrency())) {
					demandInfo.setAmountCNYMax(homeTodoController.getUSDToCNY(demandInfo.getAmountMax()));
					demandInfo.setAmountCNYMin(homeTodoController.getUSDToCNY(demandInfo.getAmountMin()));
				} else if (SRRPConstant.CNY_KEY.equals(demandInfo.getCurrency())) {
					demandInfo.setAmountCNYMax(demandInfo.getAmountMax());
					demandInfo.setAmountCNYMin(demandInfo.getAmountMax());
				}
			}
			CompanyBaseSupplement supplement = companyInfoService
					.getCompanyBaseSupplement(userInfo.get(SRRPConstant.LOGINORGNO));
			if (StringUtils.isNotEmpty(supplement.getIndustry())) {
				String industryStr = supplement.getIndustry();// 获取数据库中行业的展示
				if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
					supplement.setIndustry2(supplement.getIndustry());
					supplement.setIndustryStr(supplement.getIndustry2Dicname());
				} else {// 如果是一级的行业显示以及行业
					supplement.setIndustry(supplement.getIndustry());
					supplement.setIndustryStr(supplement.getIndustryDicname());
				}
			}
			String currencyStr = "";
			if (SRRPConstant.CNY_KEY.equals(demandInfo.getCurrency())) {
				currencyStr = "元";
			} else if(SRRPConstant.USD_KEY.equals(demandInfo.getCurrency())){
				currencyStr = "美元";
			}
			String projectName = "";// 拼接项目名 规则：行业+某公司+轮次+融资+投资金额
			// baseInfo.getName()
			projectName = supplement.getIndustryStr() + "行业某公司"
					+ demandInfo.getFinacingTurnDicname() + "融资"
					+ demandInfo.getAmount() + "百万" + currencyStr;
			// 获取缓存中的登录用户的企业id
			demandInfo.setProjectName(projectName);
			demandInfo.setEnterpriseId(userInfo.get(SRRPConstant.LOGINORGNO));
			FinacingDemandInfo finacingInfo = demandInfoService.getFinacingInfo(demandInfo.getInfoId());
			demandInfo.setOperdate(finacingInfo.getOperdate());
			if ("1".equals(demandInfo.getOpen())) {// 当选择定向投递的时候
				if(finacingInfo.getFollowTime()==null){
					Date now = new Date();// 获取当前时间
					demandInfo.setFollowTime(getAfterTime(now, 20));
				}else{
					demandInfo.setFollowTime(finacingInfo.getFollowTime());
				}
				
			}
			String userId = userInfo.get(SRRPConstant.LOGINUSERID);
			// 查询登陆用户
			PlatformUser platformUser = serService.getUser(userId);
			demandInfo.setOperuser(platformUser.getUsername());
			if (demandInfo.getRevokeFlag().equals("1")) {
				demandInfo.setRevokeFlag("2");
			}
			// 保存添加的融资需求到数据库中
			finacingDemandService.saveFinacingDemand(demandInfo);
			if (demandInfo.getStatus().equals("01")&& demandInfo.getRevokeFlag().equals("0")) {
				// 根据需求的id查询融资事件表
				List<FinacingEvent> finacingEventList = finacingEventService
						.findFinacingEventByInfoId(demandInfo.getInfoId());
				List<String> oldInvest=new ArrayList<String>();
				if (finacingEventList.size() > 0) {
					for (FinacingEvent finacingEvent : finacingEventList) {
						// 清空所有的对应的融资事件的记录
						finacingEventService.deleteFinacingEvent(finacingEvent
								.getEventId());
						oldInvest.add(finacingEvent.getInvestorgId());
					}
				}
				
				// 创建新的融资事件
				for (int i = 1; i < investorIdArry.length; i++) {

					// 创建融资事件的对象
					FinacingEvent finacingEvent = new FinacingEvent();
					// 通过需求，去映射融资事件
					BeanUtils.copyProperties(demandInfo, finacingEvent);
					finacingEvent.setAmount(null);
					List<PlatformUser> platformUsers = findByUserOrg(investorIdArry[i]);
					// 根据机构的id查询机构信息
					Investor investor = investorService
							.findInverstorById(investorIdArry[i]);
					for (PlatformUser platformUser2 : platformUsers) {
						// 创建了一个新的融资事件的id
						String neweventId = UUID.randomUUID().toString();
						neweventId = neweventId.replace("-", "");
						finacingEvent.setEventId(neweventId);
						finacingEvent.setInvestorgId(investorIdArry[i]);
						finacingEvent.setInfoId(demandInfo.getInfoId());
						finacingEvent.setMailuser(demandInfo.getOperuser());
						finacingEvent.setProjectName(projectName);
						finacingEvent.setMaildate(new Date());
						finacingEvent.setOperuser(platformUser2.getUsername());
						finacingEvent.setOperdate(new Date());
						finacingEvent.setPublishFlag("1");
						finacingEvent.setInvestLevel(platformUser2.getDesc1());
						finacingEvent.setFoundId(platformUser2.getId());
						finacingEventService.saveEvent(finacingEvent);
						String phone="";
						if(!oldInvest.contains(investorIdArry[i])){
							String name=platformUser2.getNickname();
							if(platformUser2.getDesc1().equals("0")){//机构
								phone=investor.getRelPhone();
							}else{
								phone=platformUser2.getTelephone();
							}
							//发送信息通知机构有企业向您投递了需求
							String status=sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_FOCUSBEFOEXPRICE,platformUser2.getNickname(),"" ,"","",phone);
							sendSmService.SaveSendLog("01",investorIdArry[i],neweventId,SRRPConstant.SMS_TYPE_FOCUSBEFOEXPRICE,phone,name,status,"");
						}
					}
				}
			}
			// 根据需求的id查询商业企划书
			CompanyBusinessplan companyBusinessplan = demandInfoService
					.getCompanyBusinessPlan(demandInfo.getInfoId());
			if (companyBusinessplan == null) {
				CompanyBusinessplan businessplan = new CompanyBusinessplan();
				businessplan.setInfoId(demandInfo.getInfoId());
				companyBusinessplan = businessplan;
			}
			companyBusinessplan.setFileName(fileName);
			companyBusinessplan.setFileinfo(fileUpdatePath);
			// 将更改的商业企划书保存到数据库中
			finacingDemandService.addCompanyBusinessplan(companyBusinessplan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 我的需求/需求列表 ============发布操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("sendFinacingInfo")
	private void sendFinacingInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
		Map<String, String> userInfos = this.getUserInfo(request, response);
		// 未登录强制登录
		if (userInfos.isEmpty()) {
			noLogin(request, response);
		}
		String orgNo = userInfos.get(SRRPConstant.LOGINORGNO);
		// String userName = userInfos.get(SRRPConstant.LOGINUSERNAME);
		String userId = userInfos.get(SRRPConstant.LOGINUSERID);
		// 获取页面传送过来的参数
		String status = request.getParameter("status");
		String infotId = request.getParameter("demandInfoId");
		// 通过需求的id查询出需求信息
		FinacingDemandInfo finacingInfo = demandInfoService
				.getFinacingInfo(infotId);

		// 查询登陆用户
		PlatformUser platformUser = serService.getUser(userId);
		finacingInfo.setOperuser(platformUser.getUsername());
		finacingInfo.setStatus(status);
		finacingInfo.setOperdate(new Date());
		if ("1".equals(finacingInfo.getOpen())) {// 当选择定向投递的时候
			Date now = new Date();// 获取当前时间
			finacingInfo.setFollowTime(getAfterTime(now, 20));
		}
		finacingDemandService.saveFinacingDemand(finacingInfo);
		// 判断已经选择的机构是否为空
		if (!"".equals(finacingInfo.getAppointInvestor())
				&& finacingInfo.getAppointInvestor() != null) {
			// 列表中没有数据将创建融资事件
				String investorId = finacingInfo.getAppointInvestor();
				// 将选中的机构id进行拆分
				String[] investorIdArry = convertStrToArray(investorId);
				for (int i = 1; i < investorIdArry.length; i++) {
					// 创建融资事件的对象
					FinacingEvent finacingEvent = new FinacingEvent();
					List<PlatformUser> platformUsers = findByUserOrg(investorIdArry[i]);
					// 根据机构的id查询机构信息
					Investor investor = investorService
							.findInverstorById(investorIdArry[i]);
					for (PlatformUser platformUser2 : platformUsers) {
						String neweventId = UUID.randomUUID().toString();
						neweventId = neweventId.replace("-", "");
						// 通过需求，去映射融资事件
						BeanUtils.copyProperties(finacingInfo, finacingEvent);
						finacingEvent.setAmount(null);
						finacingEvent.setEventId(neweventId);
						finacingEvent.setInvestorgId(investorIdArry[i]);
						finacingEvent.setInfoId(finacingInfo.getInfoId());
						finacingEvent.setMailuser(finacingInfo.getOperuser());
						finacingEvent.setMaildate(new Date());
						finacingEvent.setOperuser(platformUser2.getUsername());
						finacingEvent.setOperdate(new Date());
						finacingEvent.setPublishFlag("0");
						finacingEvent.setInvestLevel(platformUser2.getDesc1());
						finacingEvent.setFoundId(platformUser2.getId());
						finacingEventService.saveEvent(finacingEvent);
						String phone="";
						String name=platformUser2.getNickname();
						if(platformUser2.getDesc1().equals("0")){//机构
							phone=investor.getRelPhone();
						}else{
							phone=platformUser2.getTelephone();
						}
						//发送信息通知机构有企业向您投递了需求
						String status1=sendSmService.AnswerMessge(SRRPConstant.SMS_TYPE_FOCUSBEFOEXPRICE,platformUser2.getNickname(),"" ,"","",phone);
						sendSmService.SaveSendLog("01",investorIdArry[i],neweventId,SRRPConstant.SMS_TYPE_FOCUSBEFOEXPRICE,phone,name,status1,"");
					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 我的需求/需求列表======== 复制功能
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("copyFinacingDemand")
	private void copyFinacingDemand(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> userInfos = this.getUserInfo(request, response);
			String userId = userInfos.get(SRRPConstant.LOGINUSERID);
			// 获取页面中传递的参数
			String infoId = request.getParameter("demandInfoId");
			// 根据需求的id查询需求信息
			FinacingDemandInfo finacingInfo = demandInfoService
					.getFinacingInfo(infoId);
			// 重新创建一个需求对象
			FinacingDemandInfo copyFinacingDemandInfo = new FinacingDemandInfo();
			BeanUtils.copyProperties(finacingInfo, copyFinacingDemandInfo);
			//查询商业企划书
			CompanyBusinessplan companyBusinessplan = demandInfoService
					.getCompanyBusinessPlan(infoId);
			CompanyBusinessplan copyBusinessplan =new CompanyBusinessplan();
			BeanUtils.copyProperties( companyBusinessplan,copyBusinessplan);
			// 将查询结果给复制结果进行赋值
			// copyFinacingDemandInfo=finacingInfo;
			// 产生一个新的uuid作为主键
			String newInfoId = UUID.randomUUID().toString();
			newInfoId = newInfoId.replace("-", "");
			copyFinacingDemandInfo.setInfoId(newInfoId);
			copyFinacingDemandInfo.setStatus(SRRPConstant.DEMANDSTATUS00);
			copyBusinessplan.setInfoId(newInfoId);
			// 查询登陆用户
			PlatformUser platformUser = serService.getUser(userId);
			copyFinacingDemandInfo.setOperuser(platformUser.getUsername());
			// copyFinacingDemandInfo.setSell("1");
			copyFinacingDemandInfo
					.setOperdate(new Date());
			// copyFinacingDemandInfo.setScale(null);
			copyFinacingDemandInfo.setCloseReason(null);
			// 将数据存入数据库中
			finacingDemandService.addCompanyBusinessplan(copyBusinessplan);
			finacingDemandService.saveFinacingDemand(copyFinacingDemandInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 超过有效关注时间的需求改为公开发布
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("openFinacingDemand")
	private void openFinacingDemand(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> userInfos = this.getUserInfo(request, response);
			String userId = userInfos.get(SRRPConstant.LOGINUSERID);
			// 获取页面中传递的参数
			String infoId = request.getParameter("demandInfoId");
			// 根据需求的id查询需求信息
			FinacingDemandInfo finacingInfo = demandInfoService
					.getFinacingInfo(infoId);
			finacingInfo.setOpen("0");
			finacingInfo.setAppointInvestor("");
			finacingInfo.setOperdate(new Date());
			finacingInfo.setFollowTime(null);
			PlatformUser platformUser = serService.getUser(userId);
			finacingInfo.setOperuser(platformUser.getUsername());
			finacingInfo.setStatus(SRRPConstant.DEMANDSTATUS01);
			//根据需求的id查询融资时间表，循环删除融资事件内容
			List<FinacingEvent> finacingEvents =finacingEventService.findFinacingEventByInfoId(infoId);
			for (FinacingEvent finacingEvent : finacingEvents) {
				// 清空所有的对应的融资事件的记录
				finacingEventService.deleteFinacingEvent(finacingEvent
						.getEventId());
			}
			// 将数据存入数据库中
			finacingDemandService.saveFinacingDemand(finacingInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存关闭原因
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saveCloseReason")
	private void saveCloseReason(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Map<String, String> userInfos = this.getUserInfo(request, response);
			String userId = userInfos.get(SRRPConstant.LOGINUSERID);
			// 接收页面传递过来的数据
			String infoId = request.getParameter("demandInfoId");
			String closereason = request.getParameter("closeReason");
			// 根据页面查询过来的需求id查询需求信息
			FinacingDemandInfo finacingInfo = demandInfoService
					.getFinacingInfo(infoId);

			// 更改需求状态
			finacingInfo.setStatus(SRRPConstant.DEMANDSTATUS99);
			// 查询登陆用户
			PlatformUser platformUser = serService.getUser(userId);
//			finacingInfo.setOperdate(new Date());
//			if ("1".equals(finacingInfo.getOpen())) {// 当选择定向投递的时候
//				Date now = new Date();// 获取当前时间
//				finacingInfo.setFollowTime(getAfterTime(now, 20));
//			}
			// 2018年1月5日 15:20:51 LIWCH 关闭需求添加操作时间
			finacingInfo.setOperuser(platformUser.getUsername());
			// 将需求的原因保存到数据库中
			finacingInfo.setCloseReason(closereason);
			// 保存数据
			finacingDemandService.saveFinacingDemand(finacingInfo);
			List<FinacingEvent> eventList = finacingEventService
					.findFinacingEventByInfoId(infoId);
			if (eventList != null && eventList.size() > 0) {
				for (FinacingEvent finacingEvent : eventList) {
					finacingEventService.updateEventStatus(
							SRRPConstant.DEMANDSTATUS99,
							finacingEvent.getEventId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回到我的需求查询页面
		// return "WEB-INF/views/enterprise/MyFinacingDemand";
	}

	/**
	 * 我的需求/需求列表===========修改需求
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("editFinacingDemand")
	private String editFinacingDemand(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取页面传递的参数 需求的id
			String infoId = request.getParameter("demandInfoId");
			infoId = infoId.replaceAll("'", "");
			// 根据需求的id查询需求的详情
			FinacingDemandInfo finacingInfo = demandInfoService
					.getFinacingInfo(infoId);
			// 根据需求的id查询出需求的商业企划书
			CompanyBusinessplan companyBusinessplan = demandInfoService
					.getCompanyBusinessPlan(infoId);
			// 获取融资轮次字典值集合
			List<DD> ddFinacingTurn = RedisGetValue
					.getDataList(SRRPConstant.DD_FINACINGTURN);
			// 获取后台的机构id拼接成的字符串
			String investorIdList = finacingInfo.getAppointInvestor();
			if (!StringUtil.isNullOrEmpty(investorIdList)) {
				String[] investorIdArry = convertStrToArray(investorIdList);
				String investorNameList = "";
				// 通过遍历数组，机构名称进行拼接
				for (int i = 1; i < investorIdArry.length; i++) {
					// if (i == investorIdArry.length - 1) {
					// investorNameList = investorNameList
					// + investorService
					// .findInvestorNameById(investorIdArry[i]);
					// } else {
					investorNameList = investorNameList
							+ " "
							+ investorService
									.findInvestorNameById(investorIdArry[i])
							+ "  ";
					// }
				}
				request.setAttribute("investorIdList", investorIdList);
				request.setAttribute("investorNameList", investorNameList);
			}
			if (companyBusinessplan != null) {
				// File file = new File(companyBusinessplan.getFileinfo());
				// String fileName = file.getName();
				// request.setAttribute("fileName", fileName);
			}
			request.setAttribute("companyBusinessplan", companyBusinessplan);
			request.setAttribute("ddFinacingTurn", ddFinacingTurn);
			request.setAttribute("finacingInfo", finacingInfo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/enterprise/sendFinacingDemand";
	}

	/**
	 * 我的需求/需求列表=============查询需求详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findFinancingDemandDetails")
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
			// 从缓存中获取登录用户的企业的id
			// String enterpriseId = RedisGetValue.getRedisUser(request,
			// "orgNo");
			// 查询出企业的基本信息
			CompanyBase companyBase = companyInfoService
					.getCompanyBase(enterpriseId);
			// 查询企业补充基本信息
			CompanyBaseSupplement baseSupplement = companyInfoService
					.getCompanyBaseSupplement(enterpriseId);
			// 根据需求的id查询需求信息
			FinacingDemandInfo finacingDemandInfo = demandInfoService
					.getFinacingInfo(infoId);
			finacingDemandInfo.setAmountShow(finacingDemandInfo.getAmount());
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
			request.setAttribute("companyBase", companyBase);
			request.setAttribute("baseSupplement", baseSupplement);
			request.setAttribute("finacingDemandInfo", finacingDemandInfo);
			request.setAttribute("operate", operate);
			request.setAttribute("eventFlag", eventFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/enterprise/FinacingDemandDetail";
	}
	/**
	 * 我的需求/需求列表=============查询项目进度
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findprojectProgress")
	public String findprojectProgress(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Map<String, String> userInfos = this.getUserInfo(request, response);
			// 获取页面传参
			String infoId = request.getParameter("demandInfoId");
			infoId = infoId.replaceAll("'", "");
			FinacingDemandInfo finacingDemandInfo = demandInfoService
					.getFinacingInfo(infoId);
			finacingDemandInfo.setUpdateFlag(0);
			// 根据需求信息的id查询融资事件表中融资机构名、当前状态
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
					//添加融资纪录的更新方法
					finacingRecordService.updateByeventId(finacingEvent.getEventId());
				}
				unionDemandInvestorResult
						.setFinacingEventList(finacingEventList);
			}
			request.setAttribute("finacingDemandInfo", finacingDemandInfo);
			request.setAttribute("demandInvestorResults", demandInvestorResults);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "WEB-INF/views/enterprise/ProjectProgress";
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

	/**
	 * 文件的下载功能
	 * 
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/download")
	public String downloadFile(@RequestParam("fileName") String fileName,
			HttpServletRequest request, HttpServletResponse response) {
		if (fileName != null) {
			// String realPath = request.getServletContext().getRealPath(
			// "WEB-INF/File/");
			String realPath = "D:/businessplan/static/updatefile/";
			File file = new File(realPath, fileName);
			if (file.exists()) {
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					response.setHeader(
							"Content-Disposition",
							"attachment;filename="
									+ new String(fileName.getBytes("gb2312"),
											"ISO8859-1"));
					response.setContentType("application/force-download");// 设置强制下载不打开
					// response.addHeader("Content-Disposition",
					// "attachment;fileName=" + fileName);// 设置文件名
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else {
				return "WEB-INF/views/enterprise/MyFinacingDemand";
			}
		}
		return null;
	}

	private boolean isShowCopyButton(String followTime) throws Exception {

		if (followTime != null && !"".equals(followTime)) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long now = new Date().getTime();
			Date currTime = getAfterTime(sdf.parse(followTime), 20);
			long from = currTime.getTime();
			int daysBetweens = (int) ((now - from) / (1000 * 60 * 60 * 24));
			if (daysBetweens > 0) {
				return true;
			}
		}
		return false;
	}

	public static Date getAfterTime(Date now, int amount) throws ParseException {
		Date currTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// 获得calendar对象
		Calendar calendar = Calendar.getInstance();
		// 设置当前时间
		calendar.setTime(now);
		// 在当前时间下加若干天
		calendar.add(calendar.DAY_OF_MONTH, amount);
		currTime = calendar.getTime();
		return currTime;
	}

	/**
	 * 根据机构的id查询机构的的所属用户
	 * 
	 * @param orgId
	 * @return
	 */
	public List<PlatformUser> findByUserOrg(String orgId) {
		List<PlatformUser> platformUsers = serService.findByUserOrg(orgId);

		return platformUsers;
	}

	/**
	 * 企业对基金用户进行的披露消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("publish")
	private void publish(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String userId = this.getUserInfo(request, response).get("userId");// RedisGetValue.getRedisUser(request,
			FinacingEvent OrgEvent=null;
			// "userId");
			// 查询登陆用户
			PlatformUser platformUser = serService.getUser(userId);
			String operUser = platformUser.getUsername();
			//获取到页面传递的参数
			String investorgId = request.getParameter("investorgId");
			String eventId = request.getParameter("eventId");
			FinacingEvent event=finacingEventService.findFinacingEventById(eventId);
//			if("11".equals(event.getStatus())&&"1".equals(event.getPublishFlag())){//如果机构用户的融资事件为关注状态且未进行取消披露
		   if("11".equals(event.getStatus())&&("1".equals(event.getPublishFlag())||"2".equals(event.getPublishFlag()))){//如果机构用户的融资事件为关注状态且未进行取消披露
			//创建融资纪录的对象，此昂融资记录表中存入值
			FinacingRecord finacingRecord = new FinacingRecord();
			finacingRecord.setEventId(event.getEventId());
			finacingRecord.setInfoId(event.getInfoId());
			finacingRecord.setEnterpriseid(event.getEnterpriseId());
			finacingRecord.setInvestorgid(investorgId);
			finacingRecord.setOpercontent( "【" + operUser + "】" +"向投资者用户披露了信息");
			finacingRecord.setOperdate(new Date());
			finacingRecord.setOperuser(operUser);
			demandInfoService.saveRecord(finacingRecord);
			 OrgEvent=finacingEventService.geteventByMangageOrg(event.getInfoId(), investorgId, "0");
				if(OrgEvent!=null){
					//如果机构用户关注状态，且未进行披露信息，则向机构进行披露
//					if("11".equals(OrgEvent.getStatus())&&"1".equals(OrgEvent.getPublishFlag())){//如果机构用户的融资事件为关注状态且未进行取消披露
					if("11".equals(OrgEvent.getStatus())&&("1".equals(OrgEvent.getPublishFlag())||"2".equals(event.getPublishFlag()))){//如果机构用户的融资事件为关注状态且未进行取消披露	
						FinacingRecord finacingRecord1 = new FinacingRecord();
						finacingRecord1.setEventId(OrgEvent.getEventId());
						finacingRecord1.setInfoId(OrgEvent.getInfoId());
						finacingRecord1.setEnterpriseid(OrgEvent.getEnterpriseId());
						finacingRecord1.setInvestorgid(investorgId);
						finacingRecord1.setOpercontent(operUser+"向投资者用户披露了信息");
						finacingRecord1.setOperdate(new Date());
						finacingRecord1.setOperuser(operUser);
						demandInfoService.saveRecord(finacingRecord1);
						//根据融资事件的id进行信息披露
						finacingEventService.updatePublishFlag(OrgEvent.getEventId());
					}
				}
			//根据融资事件的id进行信息披露
			finacingEventService.updatePublishFlag(eventId);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 企业对基金用户进行的披露消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("orgPublish")
	private void orgPublish(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String userId = this.getUserInfo(request, response).get("userId");// RedisGetValue.getRedisUser(request,
			// "userId");
			// 查询登陆用户
			PlatformUser platformUser = serService.getUser(userId);
			String operUser = platformUser.getUsername();
			//获取到页面传递的参数
			String investorgId = request.getParameter("investorgId");
			String eventId = request.getParameter("eventId");
			String result="";
			//根据事件id查询融资事件的需求id
			FinacingEvent event = finacingEventService.findFinacingEventById(eventId);
			if("11".equals(event.getStatus())&&("1".equals(event.getPublishFlag())||"2".equals(event.getPublishFlag()))){//如果机构用户的融资事件为关注状态且未进行取消披露				//披露机构 只披露机构
				FinacingRecord finacingRecord = new FinacingRecord();
				finacingRecord.setEventId(event.getEventId());
				finacingRecord.setInfoId(event.getInfoId());
				finacingRecord.setEnterpriseid(event.getEnterpriseId());
				finacingRecord.setInvestorgid(investorgId);
				finacingRecord.setOpercontent(operUser+"向投资者用户披露了信息");
				finacingRecord.setOperdate(new Date());
				finacingRecord.setOperuser(operUser);
				demandInfoService.saveRecord(finacingRecord);
				//根据融资事件的id进行信息披露
				finacingEventService.updatePublishFlag(event.getEventId());
			result=SRRPConstant.TYPE0001;
		  }else{
			  result=SRRPConstant.TYPE0002;
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 企业对基金用户进行取消披露信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("unPublish")
	private void unPublish(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String userId = this.getUserInfo(request, response).get("userId");// RedisGetValue.getRedisUser(request,
			FinacingEvent OrgEvent=null;
			// "userId");
			// 查询登陆用户
			PlatformUser platformUser = serService.getUser(userId);
			String operUser = platformUser.getUsername();
			//获取到页面传递的参数
			String investorgId = request.getParameter("investorgId");
			String eventId = request.getParameter("eventId");
			String result ="";
			FinacingEvent event=finacingEventService.findFinacingEventById(eventId);
			//判断当前事件是否已经进行了披露操作,
			if("11".equals(event.getStatus())&&"0".equals(event.getPublishFlag())){
			//创建融资纪录的对象，此昂融资记录表中存入值
			FinacingRecord finacingRecord = new FinacingRecord();
			finacingRecord.setEventId(event.getEventId());
			finacingRecord.setInfoId(event.getInfoId());
			finacingRecord.setEnterpriseid(event.getEnterpriseId());
			finacingRecord.setInvestorgid(investorgId);
			finacingRecord.setOpercontent(operUser+"取消向投资者用户披露了信息");
			finacingRecord.setOperdate(new Date());
			finacingRecord.setOperuser(operUser);
			demandInfoService.saveRecord(finacingRecord);
			//查询机构的融资事件
			 OrgEvent=finacingEventService.geteventByMangageOrg(event.getInfoId(), investorgId, "0");
			if(OrgEvent!=null){
					if("11".equals(OrgEvent.getStatus())&&"0".equals(OrgEvent.getPublishFlag())){//如果机构用户的融资事件为关注状态且为已经披露
						 FinacingRecord finacingRecord1 = new FinacingRecord();
						finacingRecord1.setEventId(OrgEvent.getEventId());
						finacingRecord1.setInfoId(OrgEvent.getInfoId());
						finacingRecord1.setEnterpriseid(OrgEvent.getEnterpriseId());
						finacingRecord1.setInvestorgid(investorgId);
						finacingRecord1.setOpercontent(operUser+"取消向投资者用户披露了信息");
						finacingRecord1.setOperdate(new Date());
						finacingRecord1.setOperuser(operUser);
						demandInfoService.saveRecord(finacingRecord1);
						//根据融资事件的id进行信息披露
						finacingEventService.updateUnPublishFlag(OrgEvent.getEventId());
					 }
				}
			//根据融资事件的id进行信息披露
			finacingEventService.updateUnPublishFlag(eventId);
			result=SRRPConstant.TYPE0001;
		  }else{
			  result=SRRPConstant.TYPE0002;
		  }
			this.writeJson(result, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 企业对投资机构用户取消披露操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("orgUnPublish")
	private void orgUnPublish(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String userId = this.getUserInfo(request, response).get("userId");// RedisGetValue.getRedisUser(request,
			// "userId");
			// 查询登陆用户
			PlatformUser platformUser = serService.getUser(userId);
			String operUser = platformUser.getUsername();
			//获取到页面传递的参数
			String investorgId = request.getParameter("investorgId");
			String eventId = request.getParameter("eventId");
			String result="";
			//根据事件id查询融资事件的需求id
			FinacingEvent event = finacingEventService.findFinacingEventById(eventId);
			if("11".equals(event.getStatus())&&"0".equals(event.getPublishFlag())){
				//披露机构 只披露机构
				FinacingRecord finacingRecord = new FinacingRecord();
				finacingRecord.setEventId(event.getEventId());
				finacingRecord.setInfoId(event.getInfoId());
				finacingRecord.setEnterpriseid(event.getEnterpriseId());
				finacingRecord.setInvestorgid(investorgId);
				finacingRecord.setOpercontent(operUser+"取消向投资者用户披露了信息");
				finacingRecord.setOperdate(new Date());
				finacingRecord.setOperuser(operUser);
				demandInfoService.saveRecord(finacingRecord);
				//根据融资事件的id进行信息披露
				finacingEventService.updateUnPublishFlag(event.getEventId());
			   result=SRRPConstant.TYPE0001;
		  }else{
			  result=SRRPConstant.TYPE0002;
		  }
			this.writeJson(result, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 取消披露操作
	 * @param opertime
	 * @param open
	 * @return
	 */
	private  String getRemainTime(String opertime,String open){
		String remaintime="";
		Date operdate=null; 
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			operdate=formatter.parse(opertime);
			Date now = new Date();//获取当前时间
			if("1".equals(open)){
				 long l=now.getTime()-operdate.getTime();       //获取时间差
				 long day=l/(24*60*60*1000);
				 long hour=(l/(60*60*1000)-day*24);
					if((0 <= day) && (day <= 19)){
						remaintime=(20-day)+"天";
					}else if(day==20){
						remaintime=(24-hour)+"小时";
					}
			}else{
				remaintime="长期";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return remaintime;
	}
}

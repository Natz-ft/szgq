package com.icfcc.ssrp.web.inverstorg;

import io.netty.util.internal.StringUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBusinessplan;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholder;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingEvent;
import com.icfcc.SRRPDao.jpa.entity.enterprise.FinacingRecord;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;
import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfoResult;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.FinacingEventService;
import com.icfcc.SRRPService.inverstorg.DemandInfoService;
import com.icfcc.SRRPService.inverstorg.FinacingDemandInfoService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/finacingDemand")
public class FinacingDemandInfoController extends SRRPBaseController {
	private static Logger _log = LoggerFactory
			.getLogger(FinacingDemandInfoController.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	// 融资需求信息查询
	@Autowired
	private FinacingDemandInfoService infoService;
	// 需求信息详情
	@Autowired
	private DemandInfoService demandInfoService;
	// 企业信息详情
	@Autowired
	private CompanyInfoService cmpanyInfoService;

	@Autowired
	private PlatformUserService serService;
	
	@Autowired
	private FinacingEventService eventService;

	@RequestMapping(value = "/finacingController")
	public String finacingController(HttpServletRequest request,
			HttpServletResponse response) {
		return "WEB-INF/views/inverstorg/finacingDemandInfoQuery";
	}

	@RequestMapping(value = "/initInfo")
	public void initInfo(Model model, PageBean page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryConditionString = request
					.getParameter("queryCondition");
			String currentPage = request.getParameter("start");
			String maxSize = request.getParameter("limit");
			// 查詢條件對象需要传到Service,进行sql拼装
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
			queryCondition.setUserName(RedisGetValue.getRedisUser(request,
					"username"));
			
			String projectName = queryCondition.getProjectName();
			if(projectName!=null&&!"".equals(projectName)) {
				Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
				String[] str = projectName.split("~");
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
				if(sb.length()>0) {
					projectName = sb.toString().substring(0, sb.length()-1).replace("万", "百万");
				}
				queryCondition.setProjectName(projectName);
			}
			
			// 查询公开的融资需求列表
			List<FinacingDemandInfoResult> finacingDemandInfoResults = infoService
					.getOpenFinacingDemandInfos(queryCondition);
			Date date = new Date();
			
			for (FinacingDemandInfoResult finacingDemandInfoResult : finacingDemandInfoResults) {
				
				if (finacingDemandInfoResult.getAppointInvestor().contains(
						(RedisGetValue.getRedisUser(request, "orgNo")))) {
					// 判断定向投递的需求是否包含当前登录用户id 包含将showflag变为true
					finacingDemandInfoResult.setShowflag(SRRPConstant.SHOWTRUE);
				}
				if("03".equals(finacingDemandInfoResult.getStatus())){//判断是否有完成的融资事件
					//融资需求查询已经进行投资的融资事件
				     FinacingEvent finacingEvent=eventService.findOKFinacingEventByInfoId(finacingDemandInfoResult.getInfoId());
				     //获取最小的完成时间
				     //获取当前时间
				     Date now = new Date();
				     //判断两个时间是否相差三个月
				     if(null != finacingEvent) {
				    	 int diffrent=  differentDays(finacingEvent.getOperdate(),now);
				    	 if(diffrent > 90){
				    		 finacingDemandInfoResult.setShowflag(SRRPConstant.SHOWFALSE);
				    	 }
				     }
				}
				// 判断是否拥有对应的融资事件（根据需求的id查询以及机构的id查询对应的融资事件）如果有的话判断阶段，没有的话进行正常的脱敏展示
				FinacingEvent finacingEvent = eventService
						.findFinacingEventByInfoId(
								finacingDemandInfoResult.getInfoId(),
								RedisGetValue.getRedisUser(request, "orgNo"),
								RedisGetValue.getRedisUser(request, "username"));
				if (finacingEvent != null) {
					if ("01".equals(finacingDemandInfoResult.getStatus())
							|| "11".equals(finacingEvent.getStatus())) {
						if(finacingEvent.getPublishFlag()!=null){
							if(finacingEvent.getPublishFlag().equals("0")){//已经披露
								finacingDemandInfoResult.setProjectName(finacingDemandInfoResult.getProjectName());// 不更改项目名称
								finacingDemandInfoResult.setEnterpriseNameShow(finacingDemandInfoResult.getName());// 不更改企业名称
								finacingDemandInfoResult.setClickFlag(true);
							}else{
								finacingDemandInfoResult.setProjectName(DesensitizationUtil.changeProjectName(finacingDemandInfoResult.getProjectName(),finacingDemandInfoResult.getName()));// 更改项目名称
						        finacingDemandInfoResult.setEnterpriseNameShow(DesensitizationUtil.enterpriseName(finacingDemandInfoResult.getName()));// 更改企业名称
						        finacingDemandInfoResult.setChangeFlag(true);
							}
						}else{
							finacingDemandInfoResult.setProjectName(DesensitizationUtil.changeProjectName(finacingDemandInfoResult.getProjectName(),finacingDemandInfoResult.getName()));// 更改项目名称
					        finacingDemandInfoResult.setEnterpriseNameShow(DesensitizationUtil.enterpriseName(finacingDemandInfoResult.getName()));// 更改企业名称
					        finacingDemandInfoResult.setChangeFlag(true);
						}	
							
					} else {
						finacingDemandInfoResult
								.setProjectName(finacingDemandInfoResult
										.getProjectName());// 不更改项目名称
						finacingDemandInfoResult
								.setEnterpriseNameShow(finacingDemandInfoResult
										.getName());// 不更改企业名称
						finacingDemandInfoResult.setClickFlag(true);
					}
				} else {
					finacingDemandInfoResult.setProjectName(DesensitizationUtil
							.changeProjectName(
									finacingDemandInfoResult.getProjectName(),
									finacingDemandInfoResult.getName()));// 更改项目名称
					finacingDemandInfoResult
							.setEnterpriseNameShow(DesensitizationUtil
									.enterpriseName(finacingDemandInfoResult
											.getName()));// 更改企业名称
					finacingDemandInfoResult.setChangeFlag(true);
				}
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
				
			}
			page.setList(finacingDemandInfoResults);
			if (CollectionUtils.isNotEmpty(finacingDemandInfoResults)) {
				page.setList(finacingDemandInfoResults);
				// 设置总的条数
				Integer total = new Integer(String.valueOf(infoService
						.getOpenFinacingDemandInfoCount(queryCondition)));
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

	/**
	 * 查看项目详情 向我投递、待启动约谈、待决定投资、待放款、信息披露 操作栏 详情按钮
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/demandInfoDetails")
	public String viewDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 向我投递、待启动约谈、待决定投资、待放款、信息披露
			String operType = request.getParameter("operType");
			String infoId = request.getParameter("infoId");
			String enterpriseId = request.getParameter("enterpriseId");
			// 根据状态判断 首页待办事项中点击详情时是否显示关注按钮
			String statusInfo = request.getParameter("statusInfo");
			String gotoUrl = request.getParameter("gotoUrl");
			// 根据事件ID 判断执相应的关注操作
			String eventId = request.getParameter("eventId");
			// 通过showflag的值判断是否关注过这条融资信息
			String showflag = request.getParameter("showflag");
			// 通过企业信息列表跳转的详情标志
			// String codetype = request.getParameter("codetype");
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String orgNo = userInfos.get(SRRPConstant.LOGINORGNO);
			String userName = userInfos.get(SRRPConstant.LOGINUSERNAME);
			PlatformUser user =serService.findUserByUserName(userName);
			// step 1
			// 根据需求ID获取需求详情
			FinacingDemandInfo finacingDemandInfo = demandInfoService
					.getFinacingInfo(infoId);
			// 定义一个字符串接收切割后机构ID
			if ("focus".equals(operType)) {
				// 向我投递/融资需求
				showflag = SRRPConstant.SHOWTRUE;
			}

			// 定向投递的项目详情页面处理
			// 如果从向我投递过来的就允许关注
			if ("send".equals(gotoUrl)) {
				showflag = SRRPConstant.SHOWTRUE;
			}
			// 如果从融资需求查询页面条状过来的就不让关注
			if ("finacing".equals(gotoUrl)) {
				showflag = SRRPConstant.SHOWFALSE;
			}

			Date date = new Date();
			if (finacingDemandInfo.getFollowTime() != null) {
				if (Integer.parseInt(sdf.format(date)) > Integer.parseInt(sdf
						.format(finacingDemandInfo.getFollowTime()))) {
					// 过有效关注日期
					showflag = SRRPConstant.SHOWFALSE;
				}
			}
			// step 2
			// 企业基本信息
			CompanyBase companyBase = cmpanyInfoService
					.getCompanyBase(enterpriseId);
			// step 3
			// 企业补充信息
			// 该返回对象中有机构的ID 根据机构ID查询机构信息
			FinacingEvent finacingEvent = demandInfoService
					.getFinacingEventByIds(infoId, enterpriseId, orgNo,
							userName);
			CompanyBaseSupplement companyBaseSupplement = cmpanyInfoService
					.getCompanyBaseSupplement(enterpriseId);
			if (null != finacingEvent) {
				if (!StringUtil.isNullOrEmpty(finacingEvent.getInvestorgId())) {
					Investor investor = demandInfoService
							.getInvestor(finacingEvent.getInvestorgId());
					request.setAttribute("investor", investor);
				}
				// 同机构不允许重复关注
				if (!(finacingEvent.getStatus().equals(
						SRRPConstant.FINANCPHASE01) || finacingEvent
						.getStatus().equals(SRRPConstant.FINANCPHASE22))) {
					showflag = SRRPConstant.SHOWFALSE;
				}
			}else{
				//当前用户没有融资事件且需求融资成功
				if("03".equals(finacingDemandInfo.getStatus())){//判断是否有完成的融资事件
					//融资需求查询已经进行投资的融资事件
				     FinacingEvent finacingEventHis=eventService.findOKFinacingEventByInfoId(finacingDemandInfo.getInfoId());
				     //获取最小的完成时间
				     //获取当前时间
				     Date now = new Date();
				     //判断两个时间是否相差三个月
				   int diffrent=  differentDays(finacingEventHis.getOperdate(),now);
				     if(diffrent > 90){
				    	 showflag = SRRPConstant.SHOWFALSE;
				     }
				}
			}
			// step4
			// 获取需求商业计划书
			CompanyBusinessplan companyBusinessplan = demandInfoService
					.getCompanyBusinessPlan(infoId);
			// step 5
			// 融资记录表
			// 操作状态
			String recordStatus = "";
			String operContent = "";
			switch (operType) {
			case "focus":
				recordStatus = SRRPConstant.FINANCPHASE11;
				operContent = "【" + userName + "】" + "在向我投递阶段查看了该需求";
				break;
			case "talks":
				recordStatus = SRRPConstant.FINANCPHASE21;
				operContent = "【" + userName + "】" + "在待启动约谈阶段查看了该需求";
				break;
			case "investor":
				recordStatus = SRRPConstant.FINANCPHASE41;
				operContent = "【" + userName + "】" + "在待投资阶段查看了该需求";
				break;
			case "loan":
				recordStatus = SRRPConstant.FINANCPHASE42;
				operContent = "【" + userName + "】" + "在待出资阶段查看了该需求";
				break;
			case "push":
				recordStatus = SRRPConstant.FINANCPHASE45;
				operContent = "【" + userName + "】" + "在信息披露阶段查看了该需求";
				break;
			default:
				recordStatus = SRRPConstant.FINANCPHASE11;// 默认已经查看过详情
				break;
			}
			List<FinacingRecord> recordsList = demandInfoService
					.findFinacingRecord(userName, recordStatus, orgNo, infoId);
			// 未添加过融资记录
			if (recordsList == null || recordsList.size() == 0) {
				Date day = new Date();
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String nowData = df.format(day);
				// 保存一条融资事件信息
				FinacingRecord finacingRecord = new FinacingRecord();
				finacingRecord.setEventId(eventId);
				finacingRecord.setInfoId(infoId);
				finacingRecord.setEnterpriseid(finacingDemandInfo
						.getEnterpriseId());
				finacingRecord.setInvestorgid(orgNo);
				finacingRecord.setOpertype(recordStatus);
				// 该投资机构关注此需求的状态
				// String investorName =
				// investorService.findInverstorById(orgNo).getName();
				finacingRecord.setOpercontent(operContent);
				finacingRecord.setStatus(finacingDemandInfo.getStatus());
				finacingRecord.setOperuser(userName);
				finacingRecord.setOperdate(df.parse(nowData));
				// 添加一条事件操作记录
				demandInfoService.saveRecord(finacingRecord);
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
			// 判断需求是否为公开的需求或者是定向投递的需求
			boolean changFlag = false;
			if ("0".equals(finacingDemandInfo.getOpen())) {// 如果需求为公开的需求则判断机构对于需求是否有操作
				if (finacingEvent != null) {// 如果有操作，判断是否为待约谈状态
					if ("01".equals(finacingDemandInfo.getStatus())
							|| ("02".equals(finacingDemandInfo.getStatus()) && "11"
									.equals(finacingEvent.getStatus()))|| ("02".equals(finacingDemandInfo.getStatus()) && "22"
											.equals(finacingEvent.getStatus()))) {
//						if("1".equals(finacingEvent.getPublishFlag())){
							changFlag = true;// 待约谈状态进行脱敏其余状态不进行脱敏
//						}
					}
				} else {
					changFlag = true;// 需求没有进行操作，进行脱敏
				}
			}
			if (changFlag) {
				List<CompanyStockholder> stockholders = cmpanyInfoService
						.getCompanyStockholders(enterpriseId);
				finacingDemandInfo.setDescription(DesensitizationUtil
						.changeSome(finacingDemandInfo.getDescription(),
								companyBase.getName()));
				finacingDemandInfo.setDescription(DesensitizationUtil
						.changeSome(finacingDemandInfo.getDescription(),
								finacingDemandInfo.getProjectName()));
				finacingDemandInfo.setDescription(DesensitizationUtil
						.changeSome(finacingDemandInfo.getDescription(),
								finacingDemandInfo.getRelName()));
				finacingDemandInfo.setDescription(DesensitizationUtil
						.changeSome(finacingDemandInfo.getDescription(),
								finacingDemandInfo.getRelPhone()));
				companyBase.setEnterpriseNameShow(DesensitizationUtil
						.enterpriseName(companyBase.getName()));// 企业名称进行脱敏
				companyBase.setLegalCal(DesensitizationUtil
						.mobilePhone(companyBase.getLegalCal()));// 证件代码进行脱敏
				finacingDemandInfo.setProjectName(DesensitizationUtil
						.changeProjectName(finacingDemandInfo.getProjectName(),
								companyBase.getName()));
				finacingDemandInfo.setRelName(DesensitizationUtil
						.chineseName(finacingDemandInfo.getRelName()));
				finacingDemandInfo.setRelPhone(DesensitizationUtil
						.mobilePhone(finacingDemandInfo.getRelPhone()));
				// 查询企业的股东列表
				for (CompanyStockholder companyStockholder : stockholders) {
					finacingDemandInfo.setDescription(DesensitizationUtil
							.changeSome(finacingDemandInfo.getDescription(),
									companyStockholder.getHolderName()));
				}
			} else {
				companyBase.setEnterpriseNameShow(companyBase.getName());
			}
			String nikename=user.getNickname();
			// 数据传输到html
			request.setAttribute("eventId", eventId);
			request.setAttribute("showflag", showflag);
			request.setAttribute("statusInfo", statusInfo);
			request.setAttribute("companyBase", companyBase);
			request.setAttribute("nikename", nikename);
			request.setAttribute("companyBaseSupplement", companyBaseSupplement);
			request.setAttribute("finacingDemandInfo", finacingDemandInfo);
			request.setAttribute("companyBusinessplan", companyBusinessplan);
			request.setAttribute("finacingEvent", finacingEvent);
			return "WEB-INF/views/inverstorg/demandDetails";
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	@RequestMapping(value = "/initInfoTmp")
	public void initInfoTmp(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String queryCondition = request.getParameter("queryCondition");
			QueryCondition condition = null;
			if (queryCondition != null && !"".equals(queryCondition)
					&& !"\"\"".equals(queryCondition)) {
				condition = (QueryCondition) JSON.parseObject(queryCondition,
						QueryCondition.class);
				System.out.println(condition.getFinacing_turn());
			}
			List<?> dataList = infoService.getFinacingDemandInfoList(condition);
			this.writeJson(dataList, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/downloadFile")
	public String download(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String fileName = new String(request.getParameter("fileName")
					.getBytes("ISO-8859-1"), "utf-8");
			String realPath = "D:/businessplan/static/updatefile/";
			File file = new File(realPath, fileName);
			if (file.exists()) {
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				response.addHeader(
						"Content-Disposition",
						"attachment;fileName="
								+ new String(fileName.getBytes("gb2312"),
										"ISO8859-1"));// 设置文件名
				response.setContentType("application/force-download");// 设置强制下载不打开
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
				if (bis != null) {
					bis.close();
				}
				if (fis != null) {
					fis.close();
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 查询历史的投资记录
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/InvestorSchedule")
	public String InvestorSchedule(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String eventId = request.getParameter("eventId");
		FinacingEvent event=eventService.findFinacingEventById(eventId);
		//根据融资事件查询投资进度的json串
		String scheduleJson=event.getSchedule();
//		String scheduleJson=demandInfoService.getScheduleById(eventId);
		if("".equals(scheduleJson)){
			scheduleJson="{}";
		}
		request.setAttribute("scheduleJson", scheduleJson);
		request.setAttribute("eventId", eventId);
		return "WEB-INF/views/inverstorg/InvestorSchedule";
	}
	
	
	/**
	 * 提交更新的融资纪录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateInvestorSchedule")
	public void updateInvestorSchedule(HttpServletRequest request,
			HttpServletResponse response) {
		String eventId = request.getParameter("eventId");
		String scheduleJson = request.getParameter("scheduleJson");
		//根据融资事件的id查询融资事件
		FinacingEvent event=eventService.findFinacingEventById(eventId);
		try {
			String histString =event.getSchedule();
			if(!histString.equals(scheduleJson)){
				event.setSchedule(scheduleJson);
				event.setScheDate(new Date());
				eventService.saveEvent(event);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

   /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
}

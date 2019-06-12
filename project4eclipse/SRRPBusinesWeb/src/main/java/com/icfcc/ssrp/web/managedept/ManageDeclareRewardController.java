package com.icfcc.ssrp.web.managedept;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarInfor;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarReport;
import com.icfcc.SRRPDao.jpa.entity.declareAward.DeclareRewarSearshBean;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorRegiter;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.declareAward.DeclareRewardService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.PdfUtils;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/manageRewardDeclare")
public class ManageDeclareRewardController extends SRRPBaseController {
	
	@Autowired
	private DeclareRewardService declareRewardService;
	
	@Autowired
	private PlatformUserService userService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static Logger log = LoggerFactory.getLogger(ManageDeclareRewardController.class);
	
	/**
	 * 初始化主管奖励申报查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/RewarDeclareList")
	public String MyRewarDeclare(HttpServletRequest request,
			HttpServletResponse response) {
		// 获得到登录用户的企业id
		Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
		// 未登录强制登录
		if (userInfos.isEmpty()) {
			noLogin(request, response);
		}
		String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
		request.setAttribute("userType", userType);
		//判断管理用户类型
		//1.如果是区县金融办用户，页面不展示区域筛选条件，否则展示
		return "WEB-INF/views/managedept/manageDeclareReward";
	}
	
	
	/**
	 * 初始化我的申报页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/DeclareListInitInfo")
	@ResponseBody
	public void MyDeclareList(Model model, PageBean page,HttpServletRequest request,HttpServletResponse response) {
		try {
			String queryConditionString = request.getParameter("queryCondition");// 查询条件
			String currentPage = request.getParameter("start");// 当前页
			String maxSize = request.getParameter("limit");// 每页显示的条数
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			// 获得到登录用户的企业id
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}
			String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
			DeclareRewarSearshBean declareRewardSearsh=new DeclareRewarSearshBean();
			
			if (queryConditionString != null
					&& !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				declareRewardSearsh = (DeclareRewarSearshBean) JSON.parseObject(
						queryConditionString, DeclareRewarSearshBean.class);
			}
			if (SRRPConstant.USER_TYPE_03.equals(userType)) {// 区县金融办用户
				// 查询本区域的企业用户
				ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipal();
				PlatformUser user = userService.getUser(shiroUser.getId());
				declareRewardSearsh.setRearea(user.getDesc3());
			}
			// 查询条件对象需要传到Service,进行sql拼装
			declareRewardSearsh.setUserType(userType);
			
			if (StringUtils.isNotBlank(currentPage)) {
				declareRewardSearsh.setCurPage(Integer.parseInt(currentPage));
			}
			if (StringUtils.isNotBlank(maxSize)) {
				declareRewardSearsh.setMaxSize(Integer.parseInt(maxSize));
			}
			declareRewardSearsh.setInvestorId(investorId);
			List<DeclareRewarInfor> dataList=declareRewardService.getManageDeclareLists(declareRewardSearsh);
			// 查询批露信息数据
			page.setList(dataList);
			if (CollectionUtils.isNotEmpty(dataList)) {

				page.setList(dataList);
				// 设置总的条数
				Integer total = new Integer(String.valueOf(declareRewardService.getManageDeclareCount(declareRewardSearsh)));
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
			request.setAttribute("userType", userType);
			// 将数据传输到前端
			this.writeJson(page, request, response);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 我的申报：查看详情
	 * @param rs
	 * @param registerInfo
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/declareDetail")
	public String declareDetail(ResultBean rs,HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			// 从页面接收企业的id根据企业的id查询企业的信息
			String declareId = request.getParameter("declareId");
			String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
			// 根据id查询基本信息
			DeclareRewarInfor declare=declareRewardService.getDeclareInfoById(declareId);
			List<DeclareRewarReport> listReport=declareRewardService.findByDeclarId(declareId);
			declare.setDeclareRewarReport(listReport);
			declare.setDeclareRewarReportJson(JSON.toJSONString(listReport,SerializerFeature.DisableCircularReferenceDetect));
			request.setAttribute("declare", declare);
			request.setAttribute("userType", userType);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "WEB-INF/views/inverstorg/declareDetail";

	}
	
	
	/**
	 * 主管--接收
	 * @param rs
	 * @param registerInfo
	 * @param request
	 * @param respons
	 */
	@RequestMapping(value = "/answerDeclare")
	@ResponseBody
	public void answerDeclare(ResultBean rs,HttpServletRequest request,
			HttpServletResponse respons) {
		try {
			// 从页面接收企业的id根据企业的id查询企业的信息
			String declareId = request.getParameter("declareId");
			String type = request.getParameter("type");
			
			// 根据id查询基本信息
			DeclareRewarInfor declare=declareRewardService.getDeclareInfoById(declareId);
//			if(!SRRPConstant.DECLARER_RECEIVED_YSE.equals(declare.getDeclareStatus())){
				declare.setDeclareStatus(type);
				declareRewardService.saveDeclareRewarInfor(declare);
				declareRewardService.updateRecord(type, declareId);
//			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
		}
		this.writeJson(rs, request, respons);
	}
}

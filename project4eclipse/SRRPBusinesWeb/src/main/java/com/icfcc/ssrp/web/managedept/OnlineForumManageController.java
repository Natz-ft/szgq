package com.icfcc.ssrp.web.managedept;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.dd.DD;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.managedept.ManagedeptOnlineForum;
import com.icfcc.SRRPService.gataway.staticize.GataWayCompanyInfoService;
import com.icfcc.SRRPService.managedept.ManagedeptOnlineForumService;
import com.icfcc.SRRPService.util.SmsClientUtil;
import com.icfcc.ssrp.session.RedisManager;
import com.icfcc.ssrp.web.SRRPBaseController;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.credit.platform.util.Servlets;
import com.icfcc.credit.platform.util.ShiroUser;


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
@RequestMapping(value = "/onlineForum")
public class OnlineForumManageController extends SRRPBaseController{
	private static Logger log = LoggerFactory.getLogger(OnlineForumManageController.class);
	// 注入在线提问解答业务层
	@Autowired
	private ManagedeptOnlineForumService onlineForumService;
	@Autowired
	private GataWayCompanyInfoService gataWayCompanyInfoService;
	@RequestMapping(value = "/onlineForumInit")
	public String onlineForumInit(HttpServletRequest request, HttpServletResponse response) {
		log.info("==========init==========is===========successed11111111111111===========");
		// return "gataway/view/institutionalInvestor/investOrgQuery";
		return "WEB-INF/views/managedept/onlineForum";
	}

	@RequestMapping(value = "/initInfo")
	public void onlineForumList(Model model, PageBean page, HttpServletRequest request, HttpServletResponse response) {
		try {
		String limitStr = request.getParameter("limit");
		String startStr = request.getParameter("start");
		if (limitStr!=null && !limitStr.equals("")&& startStr!=null && !startStr.equals("")) {
			page.setMaxSize(Integer.parseInt(limitStr));
			page.setCurPage( Integer.parseInt(startStr));
		}
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request,"search_",page);
		// 查询结果集和分页
		Page<ManagedeptOnlineForum> queryResults = onlineForumService.getPage(searchParams, page.getCurPage(),page.getMaxSize(),"desc", "messagedate");
		List<ManagedeptOnlineForum> dataList = queryResults.getContent();
		page.setList(dataList);
		page.setRecordCnt(0);
		if (CollectionUtils.isNotEmpty(dataList)) {
			page.setList(dataList);
			//设置总的页数
			page.setPageCnt(queryResults.getTotalPages());
			//设置总的条数
			page.setRecordCnt(new Long(queryResults.getTotalElements()).intValue());
		}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		// 将数据传输到前端
		this.writeJson(page, request, response);
		
	}
	@RequestMapping(value = "/onlineForumDetails")
    public String onlineForumDetails(Model model,HttpServletRequest httpRequest,String id) {
		String htmlUrl=null;
		try {
			ManagedeptOnlineForum onlineForum=onlineForumService.findOne(id);
	    	model.addAttribute("online", onlineForum);
	    	if("0".equals(onlineForum.getMessagestatus())) {
	    		htmlUrl= "WEB-INF/views/managedept/onlineForumAnswerDetails";
	        }else {
	        	htmlUrl= "WEB-INF/views/managedept/onlineForumDetails";
	        }
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
        	return htmlUrl;
       
    }
	
	@RequestMapping(value = "/update")
	@ResponseBody 
	public void update(ResultBean rs,HttpServletRequest request,HttpServletResponse response) {
		try{
			String onlineForumStr = request.getParameter("onlineForum");
	        //回复内容对象需要传到Service,进行sql拼装
	        ManagedeptOnlineForum onlineForum = null;
	        if (onlineForumStr != null && !"".equals(onlineForumStr) && !"\"\"".equals(onlineForumStr)) {
	        	onlineForum = (ManagedeptOnlineForum) JSON.parseObject(onlineForumStr, ManagedeptOnlineForum.class);
	            System.out.println(onlineForum.getContacts());
	        }
		onlineForum.setAnswerdate(new Date());
		String answerpeople=RedisGetValue.getRedisUser(request,"username");
		onlineForum.setAnswerpeople(answerpeople);
		onlineForum.setMessagestatus("1");
		//回复内容:发送短信通知
		sendAuditMassge(onlineForum.getAnswerdescribeText(),onlineForum.getContactnumber());
	    onlineForumService.update(onlineForum);
		rs = ResultBean.sucessResultBean();
	   }catch(Exception e){
		rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
		e.printStackTrace();
		log.error(e.getMessage());
	    }
		this.writeJson(rs, request, response);
	}

	/**
	 * 审核结果发送短信
	 * @param name    企业名称
	 * @param userName   用户名
	 * @param keys   短信主键   4 的时候是审核通过 6的时候是审核不通过
	 * @param auditContent    审核不通过的原因
	 * phoneNum
	 */
	public void sendAuditMassge(String auditContent,String phoneNum){
		boolean result = false;
		String flag="7";
		try {
				String arry[] = new String[1];
				arry[0]=":"+auditContent;//第一个参数为企业名称
				String []keys=gataWayCompanyInfoService.keys(flag);
				result=SmsClientUtil.querySms(arry,keys,phoneNum,flag);
		} catch (Exception e) {
			result =false;
			e.printStackTrace();
		}

	}

	

}

package com.icfcc.credit.platform.web.system;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icfcc.credit.platform.exception.ServiceException;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUser;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserLoginLog;
import com.icfcc.credit.platform.jpa.repository.system.PlatformUserDao;
import com.icfcc.credit.platform.service.system.PlatformUserLoginService;
import com.icfcc.credit.platform.util.Constant;	
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.SysDate;
import com.icfcc.credit.platform.web.BaseController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "system/userLogin")
public class PlatformUserLoginController extends BaseController{
	
	private static final String PREFIX="system/userLogin/";
	private static final String LTE_LOGIN_TIME = "LTE_loginTime" ;
	private static Logger log = LoggerFactory.getLogger(PlatformUserLoginController.class);
	
	@Autowired
	private PlatformUserLoginService userLoginService;
	@Autowired
	private PlatformUserDao userDao;
	
	/**
	 * <获取分页信息,默认排序>
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(Model model,PageBean page,HttpServletRequest request,String search_GTE_loginTime_DATE,String search_LTE_loginTime_DATE){
		Page<PlatformUserLoginLog>  queryResults = null;
		 Map<String,String> sysUserLogMap = new  LinkedHashMap<String,String>();
		try{
		
			Map<String, Object> searchParams = processRequestParams(page, request);
			currentDate2MaxDate(searchParams);
			queryResults = userLoginService.getPage(searchParams, page.getCurPage(), page.getMaxSize(),VipCont.DIRECTION,"loginTime");
	        List<PlatformUser> findAll = (List<PlatformUser>) userDao.findAll();
	        for (PlatformUser systemUser : findAll) {
	        	String userId = systemUser.getId();
	        	String username = systemUser.getUsername();
	        	sysUserLogMap.put(userId, username);
	        }
		}catch(Exception e){
			log.error(e.getMessage());
		}
		model.addAttribute("sysUserLogMap", sysUserLogMap);
		model.addAttribute("GTE_loginTime", search_GTE_loginTime_DATE);
		model.addAttribute("LTE_loginTime", search_LTE_loginTime_DATE);
		processQueryResults(model,page, queryResults);
		return PREFIX+"list";
	}
	/**
	 * 将当前时间修改为当天最大时间，eg: 2016-11-10 11:23:43   --> 2016-11-10 23:59:59
	 * @param searchParams
	 * @param gte_loginTime
	 */
	private void currentDate2MaxDate(Map<String, Object> searchParams) {
		Object lte_loginTime = searchParams.get(LTE_LOGIN_TIME);
		if(null!=lte_loginTime) {
			Date loginTime = (Date)lte_loginTime;
			Date max_loginTime = SysDate.getCurrentDateMaxTime(loginTime);
			searchParams.put(LTE_LOGIN_TIME, max_loginTime);
		}
	}

	/**
	 * <删除数据>
	 * @param rs
	 * @param id
	 * @return
	 */
	 @RequestMapping(value="delete")
		@ResponseBody
		public String delete(ResultBean rs,String ids){
			
			try{
				userLoginService.deleteIds(ids);
				rs = ResultBean.sucessResultBean();
			}catch(ServiceException e){
				rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
				log.error(e.getMessage());
			}
			return rs.toJSONStr();
		}
	 
}

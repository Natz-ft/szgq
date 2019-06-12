package com.icfcc.credit.platform.web.system;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icfcc.credit.platform.jpa.entity.system.PlatformFunction;
import com.icfcc.credit.platform.service.system.PlatformFunctionService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.web.BaseController;

/**
 * 功能日志配置管理
 */
@Slf4j
@Controller
@RequestMapping(value = "/sysFunction")
public class PlatformFunctionController  extends BaseController {
	
	private static Logger log = LoggerFactory.getLogger(PlatformFunctionController.class);
	//
	private final static String  CREATTIME  = "createTime";
	private final static String  FUNCTIONLIST  = "system/function/functionList";
	private final static String    UPDATEPAGE= "system/function/updatePage";
	@Autowired
	private PlatformFunctionService systemFunctionService ;
	/**
	 * 日志管理
	 * @param model
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/systemFunctionList")
	public String systemFunctionList(Model model,PageBean page,HttpServletRequest request) {
		
		Page<PlatformFunction>  queryResults = null;
		try {
	        Map<String, Object> searchParams = processRequestParams(page,request);
           queryResults = systemFunctionService.getSystemFunctionList(searchParams, page.getCurPage(),page.getMaxSize(), Constant.DIRECTION, CREATTIME );
		log.debug("page:"+page);
		} catch (Exception e) {
			log.error("systemConfigList:"+e.getMessage());
    	}
		processQueryResults(model,page, queryResults);
		return FUNCTIONLIST;
	}
	/**
	 *  删除
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteSystemFunctionById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleteSystemFunctionById(Model model,HttpServletRequest request ,String ids ) {
		
		  ResultBean rs = null;
		  try {
		     String[] idsStr = ids.split(",");
		     for (int i = 0; i < idsStr.length; i++) {
		    	 systemFunctionService.deleteSystemFunctionById(idsStr[i]);
			}
		    rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
			e.printStackTrace();
		}
		   return rs.toJSONStr();
	}
	
	
	/**
	 * 添加方法
	 * @param model
	 * @param request
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping(value = "saveSystemFunction", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveSystemFunction(Model model,HttpServletRequest request,PlatformFunction systemFunction ) {
		
		ResultBean rs;
		 try {
			 //获取用户名
//		    ShiroUser user = getCurrentUser();
//		    config.setCreateUser(user.getName());
//			systemConfigService.saveSystemConfig(config);
			 systemFunction.setCreateTime(new Date());
			 systemFunctionService.saveSystemFunction(systemFunction);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
			e.printStackTrace();
		}
			return  rs.toJSONStr();
	}
	
	/**
	 * 修 改 跳转界面
	 * @param model
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getSystemFunctionById")
	public String getSystemFunctionById(Model model,HttpServletRequest request,String id) {
		
			PlatformFunction function =null;;
			try {
				function = systemFunctionService.getSystemFunctionById(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   model.addAttribute("function", function);
		   return UPDATEPAGE;
	}
	
	
	/**
	 * 修改方法
	 * @param model
	 * @param request
	 * @param id
	 * @param config
	 * @return
	 */
	@RequestMapping("/updateSystemFunction")
	@ResponseBody
	public String updateSystemFunction(Model model,PlatformFunction  systemFunction) {
		
		ResultBean rs;
		  try {
  		     	systemFunctionService.updateSystemFunction(systemFunction);
		       rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
    		log.error(e.getMessage());
		}
			return  rs.toJSONStr();
	}
}

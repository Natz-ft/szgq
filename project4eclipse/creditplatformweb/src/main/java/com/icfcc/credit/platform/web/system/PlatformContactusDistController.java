package com.icfcc.credit.platform.web.system;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.PlatformContactusDist;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
/**
 * 
* @ClassName: PlatformContactusDistController
* @Description: TODO(处理联系我们功能模块的请求)
* @author hugt
* @date 2017年9月14日 下午7:59:04
*
 */
@Slf4j
@Controller
@Scope("prototype")
@RequestMapping(value="contactusDist")
public class PlatformContactusDistController extends PlatformBasicController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 
	* @Title: curdNews 
	* @Description: TODO(响应查询请求) 
	* @param @param model
	* @param @param page
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="curdContactusDist")
	public String curdNews(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformContactusDist>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        queryResults = systemContentContactusDistService.getSystemContentContactusDistList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/contactusDist/curdContactusDist";
	}
	/**
	 * 
	* @Title: delNews 
	* @Description: TODO(响应删除请求) 
	* @param @param rs
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="delContactusDist")
	@ResponseBody
	public String delNews(ResultBean rs,String id){
		try{
			String[] ids=id.split(",");
			for(String everyId:ids){
				if(StringUtils.isNotBlank(everyId)){
					Long sid=Long.parseLong(everyId);
					systemContentContactusDistService.deleteById(sid);
				}
			}
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 
	* @Title: updateNews 
	* @Description: TODO(响应修改前的请求) 
	* @param @param model
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="updateContactusDist")
	public String updateNews(Model model,String id){
		Long sid=Long.parseLong(id);
		PlatformContactusDist contactusDist=systemContentContactusDistService.findById(sid);
		model.addAttribute("contactusDist", contactusDist);
		return "system/contactusDist/update_page";
	}
	/**
	 * 
	* @Title: update 
	* @Description: TODO(响应修改后的请求) 
	* @param @param rs
	* @param @param faq
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public String update(ResultBean rs,PlatformContactusDist faq,HttpServletRequest req) {
		try{
			faq.setCreateTime(new Date());
			systemContentContactusDistService.update(faq);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 
	* @Title: insertNews 
	* @Description: TODO(响应新增请求) 
	* @param @param rs
	* @param @param faq
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="insertContactusDist")
	@ResponseBody
	public String insertNews(ResultBean rs,PlatformContactusDist faq,HttpServletRequest req){
		try{
			faq.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			faq.setCreateUser(createUser);
			systemContentContactusDistService.save(faq);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
}

package com.icfcc.credit.platform.web.system;


import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Random;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.PlatformBusinessPartner;
import com.icfcc.credit.platform.jpa.entity.system.PlatformfriendlyLinks;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
/**
 * 
* @ClassName: PlatformFriendlyLinksController
* @Description: TODO(处理友情链接功能模块的请求)
* @author hugt
* @date 2017年9月14日 下午7:41:14
*
 */
@Slf4j
@Controller
@Scope("prototype")
@RequestMapping(value="links")
public class PlatformFriendlyLinksController extends PlatformBasicController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 
	* @Title: curdLinks 
	* @Description: TODO(响应查询请求) 
	* @param @param model
	* @param @param page
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="curdLinks")
	public String curdLinks(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformfriendlyLinks>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        queryResults = systemContentLinksService.getSystemContentLinksList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/links/curdLinks";
	}
	@RequestMapping(value="createPage")
	public ModelAndView createPage(){
		ModelAndView model = new ModelAndView();
		model.setViewName("system/links/createLinks");
		String id=Integer.toHexString(new Random().nextInt());
		model.addObject("id",id);
		return model;
	}
	/**
	 * 
	* @Title: delLinks 
	* @Description: TODO(响应删除请求) 
	* @param @param rs
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="delLinks")
	@ResponseBody
	public String delLinks(ResultBean rs,String id,HttpServletRequest request){
		try{
			String[] ids=id.split(",");
			for(String everyId:ids){
				if(StringUtils.isNotBlank(everyId)){
					Long sid=Long.parseLong(everyId);
					PlatformfriendlyLinks links=systemContentLinksService.findById(sid);
					File file = new File(request.getSession().getServletContext().getRealPath("/"));
					String strParentDirectory =file.getParent();
					strParentDirectory=strParentDirectory+links.getLogo();;
					File file1 = new File(strParentDirectory);
					if(file1.exists()) {
						file1.delete();
					}
					systemContentLinksService.deleteById(sid);
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
	* @Title: updateLinks 
	* @Description: TODO(响应修改前的请求) 
	* @param @param model
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="updateLinks")
	public String updateLinks(Model model,String id){
		Long sid=Long.parseLong(id);
		PlatformfriendlyLinks links=systemContentLinksService.findById(sid);
		model.addAttribute("links", links);
		return "system/links/updateLinks";
	}
	/**
	 * 
	* @Title: update 
	* @Description: TODO(响应修改后请求) 
	* @param @param rs
	* @param @param links
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public String update(ResultBean rs,HttpServletRequest req) {
		// 获取页面中传递查询条件的参数
		String  linksStr= req.getParameter("links");
		PlatformfriendlyLinks links =null;
		try{
			if (linksStr != null && !"".equals(linksStr) && !"\"\"".equals(linksStr)) {
				links = (PlatformfriendlyLinks) JSON.parseObject(linksStr, PlatformfriendlyLinks.class);
			}
			links.setCreateTime(new Date());
			systemContentLinksService.update(links);
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
	* @Title: insertLinks 
	* @Description: TODO(响应新增请求) 
	* @param @param rs
	* @param @param links
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="insertLinks")
	@ResponseBody
	public String insertLinks(ResultBean rs,HttpServletRequest req){
		// 获取页面中传递查询条件的参数
				String  linksStr= req.getParameter("links");
				PlatformfriendlyLinks links =null;
				try{
					if (linksStr != null && !"".equals(linksStr) && !"\"\"".equals(linksStr)) {
						links = (PlatformfriendlyLinks) JSON.parseObject(linksStr, PlatformfriendlyLinks.class);
					}
						links.setCreateTime(new Date());
						ShiroUser user = getCurrentUser();
						String createUser=user.getName();
						links.setCreateUser(createUser);
						systemContentLinksService.save(links);
						rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
}

package com.icfcc.credit.platform.web.system;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.PlatformBusinessPartner;
import com.icfcc.credit.platform.jpa.entity.system.PlatformNews;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
/**
 * 
* @ClassName: PlatformBusinessPartnerController
* @Description: TODO(处理合作伙伴功能的请求)
* @author hugt
* @date 2017年9月14日 下午7:35:50
*
 */
@Slf4j
@Controller
@Scope("prototype")
@RequestMapping(value="partner")
public class PlatformBusinessPartnerController extends PlatformBasicController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 
	* @Title: curdNews 
	* @Description: TODO(响应查询列表和分页的请求方法) 
	* @param model
	* @param @param page
	* @param @param request
	* @param @return   页初始化值，页面请求
	* @return String    返回页面路径 
	* @throws
	 */
	@RequestMapping(value="curdPartner")
	public String curdNews(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformBusinessPartner>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        queryResults = systemContentPartnerService.getSystemContentPartnerList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/partner/curdPartner";
	}
	@RequestMapping(value="createPage")
	public ModelAndView createPage(){
		ModelAndView model = new ModelAndView();
		model.setViewName("system/partner/createPartner");
		String id=Integer.toHexString(new Random().nextInt());
		model.addObject("id",id);
		return model;
	}
	/**
	 * 
	* @Title: delPartner 
	* @Description: TODO(响应删除请求) 
	* @param @param rs
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="delPartner")
	@ResponseBody
	public String delPartner(ResultBean rs,String id,HttpServletRequest request){
		try{
			String[] ids=id.split(",");
			for(String everyId:ids){
				if(StringUtils.isNotBlank(everyId)){
					Long sid=Long.parseLong(everyId);
					PlatformBusinessPartner partner=systemContentPartnerService.findById(sid);
					File file = new File(request.getSession().getServletContext().getRealPath("/"));
					String strParentDirectory =file.getParent();
					strParentDirectory=strParentDirectory+partner.getLogo();;
					File file1 = new File(strParentDirectory);
					if(file1.exists()) {
						file1.delete();
					}
					systemContentPartnerService.deleteById(sid);
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
	* @Description: TODO(响应修改请求) 
	* @param @param model
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="updatePartner")
	public String updateNews(Model model,String id){
		PlatformBusinessPartner partner=systemContentPartnerService.findById(Long.valueOf(id));
		model.addAttribute("partner", partner);
		return "system/partner/updatePartner";
	}
	/**
	 * 
	* @Title: update 
	* @Description: TODO(响应修改请求) 
	* @param @param rs
	* @param @param news
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public String update(ResultBean rs,HttpServletRequest req) {
		// 获取页面中传递查询条件的参数
				String  partnerStr= req.getParameter("partner");
				PlatformBusinessPartner partner =null;
				try{
					if (partnerStr != null && !"".equals(partnerStr) && !"\"\"".equals(partnerStr)) {
						partner = (PlatformBusinessPartner) JSON.parseObject(partnerStr, PlatformBusinessPartner.class);
					}
					partner.setCreateTime(new Date());
			systemContentPartnerService.update(partner);
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
	* @param @param news
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="insertPartner")
	@ResponseBody
	public String insertNews(ResultBean rs,HttpServletRequest req){
		// 获取页面中传递查询条件的参数
		String  partnerStr= req.getParameter("partner");
		PlatformBusinessPartner partner =null;
		try{
			if (partnerStr != null && !"".equals(partnerStr) && !"\"\"".equals(partnerStr)) {
				partner = (PlatformBusinessPartner) JSON.parseObject(partnerStr, PlatformBusinessPartner.class);
			}
			
			partner.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			partner.setCreateUser(createUser);
			systemContentPartnerService.save(partner);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
}

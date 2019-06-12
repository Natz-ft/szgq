package com.icfcc.credit.platform.web.system;


import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.icfcc.credit.platform.constants.DD;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.PlatformNews;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
/**
 * 
* @ClassName: PlatformNewsController
* @Description: TODO(处理管理新闻动态模块的请求)
* @author hugt
* @date 2017年9月14日 下午7:42:21
*
 */
@Slf4j
@Controller
@Scope("prototype")
@RequestMapping(value="news")
public class PlatformNewsController extends PlatformBasicController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	/** 
	* @Title: curdNews 
	* @Description: TODO(响应查询请求) 
	* @param @param model
	* @param @param page
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="curdNews")
	public String curdNews(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformNews>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        queryResults = systemContentNewsService.getSystemContentNewsList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, "newsDate");
	        List<DD> dataList=VipCont.getValueList("dd:newsType");
			model.addAttribute("statusList", dataList);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/news/curdNews";
	}
	@RequestMapping(value="detailNews")
	public ModelAndView newsDetail(String id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/news/detailNews");
    	PlatformNews news=new PlatformNews();
		news=systemContentNewsService.findById(Long.valueOf(id));
		model.addObject("news",news);
		return model;
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
	@RequestMapping(value="delNews")
	@ResponseBody
	public String delNews(ResultBean rs,String id,HttpServletRequest request){
		try{
			String[] ids=id.split(",");
			for(String everyId:ids){
				if(StringUtils.isNotBlank(everyId)){
					Long sid=Long.parseLong(everyId);
					PlatformNews news=systemContentNewsService.findById(sid);
					File file = new File(request.getSession().getServletContext().getRealPath("/"));
					String strParentDirectory =file.getParent();
					strParentDirectory=strParentDirectory+news.getPhoto();
					File file1 = new File(strParentDirectory);
					if(file1.exists()) {
						file1.delete();
					}
					systemContentNewsService.deleteById(sid);
				}
			}
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			e.printStackTrace();
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
	@RequestMapping(value="updateNews")
	public ModelAndView updateNews(String id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/news/updateNews");
		PlatformNews news=systemContentNewsService.findById(Long.valueOf(id));
		model.addObject("news", news);
		 List<DD> dataList=VipCont.getValueList("dd:newsType");
			model.addObject("statusList", dataList);
		return model;
	}
	@RequestMapping(value="createPage")
	public ModelAndView createPage(){
		ModelAndView model = new ModelAndView();
		model.setViewName("system/news/createNews");
		String id=Integer.toHexString(new Random().nextInt());
		model.addObject("id",id);
		 List<DD> dataList=VipCont.getValueList("dd:newsType");
		model.addObject("statusList", dataList);
		return model;
	}
	
	/**
	 * 
	* @Title: update 
	* @Description: TODO(响应修改后的请求) 
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
		String contentNews = req.getParameter("contentNews");
		PlatformNews news =new PlatformNews();
		try{
			if (contentNews != null && !"".equals(contentNews) && !"\"\"".equals(contentNews)) {
				news = (PlatformNews) JSON.parseObject(contentNews, PlatformNews.class);
			}
			news.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			news.setCreateUser(createUser);
			systemContentNewsService.update(news);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		System.out.println("update4========================");

		return rs.toJSONStr();
	}
	/**
	 * 
	* @Title: insertNews 
	* @Description: TODO(响应新增的请求) 
	* @param @param rs
	* @param @param news
	* @param @param req
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="insertNews")
	@ResponseBody
	public String insertNews(ResultBean rs,HttpServletRequest req){
		// 获取页面中传递查询条件的参数
		String contentNews = req.getParameter("contentNews");
		//更改初始化对象的方式
		PlatformNews news =new PlatformNews();
		try{
			if (contentNews != null && !"".equals(contentNews) && !"\"\"".equals(contentNews)) {
				news = (PlatformNews) JSON.parseObject(contentNews, PlatformNews.class);
			}
			
			news.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			news.setCreateUser(createUser);
			systemContentNewsService.save(news);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	
}

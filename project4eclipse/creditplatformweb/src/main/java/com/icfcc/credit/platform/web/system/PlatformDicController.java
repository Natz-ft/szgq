package com.icfcc.credit.platform.web.system;

import java.util.Date;
import java.util.Map;

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
import com.icfcc.credit.platform.jpa.entity.system.PlatformDic;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
/**
 * 
* @ClassName: PlatformDicController
* @Description: TODO(处理字典管理模块的请求)
* @author hugt
* @date 2017年9月14日 下午7:40:10
*
 */
@Slf4j
@Controller
@Scope("prototype")
@RequestMapping(value="systemDic")
public class PlatformDicController extends PlatformBasicController {
	
	private static Logger log = LoggerFactory.getLogger(PlatformConfigController.class);
	
	@RequestMapping(value="systemIdDicList")
	public String dicList(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformDic>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        queryResults = systemDicService.getSysDicList(searchParams, page.getCurPage(),page.getMaxSize() );
	        Map<String, String> typeMap = systemDicService.getDicType();
            HttpSession session = request.getSession();
            session.setAttribute("type", typeMap);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/dic/dic";
	}
	
	//更新或添加指定数据字典，并添加更新时间
	@RequestMapping(value="insertDic")
	@ResponseBody
	public String insertDic(ResultBean rs,PlatformDic dic){
		try{
			 dic.setValidityDate(new Date());
			//dic.setCreateTime(new Date());
			//实体持久化到数据库
			systemDicService.saveSysDic(dic);
			systemDicService.HttpClientGetSRRP();
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
     * 用于新建页面，获取下拉列表信息，此处为机构信息
     *
     * @return
     */
    @RequestMapping(value = "createPage")
    @ResponseBody
    public String addUser() {
        //此处dept实际根据页面参数的name值获取
    	 Map<String, String> type = systemDicService.getDicType();
        String result = JSON.toJSONString(type);
        return result;
    }
	//删除指定的数据字典
	@RequestMapping(value="delDic")
	@ResponseBody
	public String delDic(ResultBean rs,String id){
		String[] ids=id.split(",");
		try{
			for(int i=0;i<ids.length;i++){
				if(StringUtils.isNotBlank(ids[i])){
					Long sid=Long.parseLong(ids[i]);
					systemDicService.deleteSystemDic(sid);
				}
			}
			systemDicService.HttpClientGetSRRP();
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	
	@RequestMapping(value="updateDictionary")
	public ModelAndView updateDictionary(String id){
		ModelAndView model=new ModelAndView();
		model.setViewName("system/dic/update_page");
		PlatformDic dic=systemDicService.getSystemDic(Long.parseLong(id));
		model.addObject("dic", dic);
		return model;
	}
	@RequestMapping(value="updateDic")
	@ResponseBody
	public String updateDic(ResultBean rs,PlatformDic dic){
		try{
			//实体持久化到数据库
			dic.setValidityDate(new Date());
			//dic.setUpdateTime(new Date());
			systemDicService.updateSysDic(dic);
			systemDicService.HttpClientGetSRRP();
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	
}

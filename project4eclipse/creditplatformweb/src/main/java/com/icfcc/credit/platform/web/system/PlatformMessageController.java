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
import com.icfcc.credit.platform.jpa.entity.system.PlatformMessage;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
/**
 * 
* @ClassName: PlatformMessageController
* @Description: TODO(处理消息功能模块的请求)
* @author hugt
* @date 2017年9月14日 下午7:41:47
*
 */
@Slf4j
@Controller
@Scope("prototype")
@RequestMapping(value="message")
public class PlatformMessageController extends PlatformBasicController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	
	@RequestMapping(value="curdMessage")
	public String curdMessage(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformMessage>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        queryResults = systemMessageService.getSystemMessageList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "system/message/curdMessage";
	}
	
	@RequestMapping(value="delMessage")
	@ResponseBody
	public String delMessage(ResultBean rs,String id){
		try{
			String[] ids=id.split(",");
			for(String everyId:ids){
				if(StringUtils.isNotBlank(everyId)){
					Long sid=Long.parseLong(everyId);
					systemMessageService.deleteById(sid);
				}
			}
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	
	@RequestMapping(value="updateMessage")
	public String updateMessage(Model model,String id){
		Long sid=Long.parseLong(id);
		PlatformMessage message=systemMessageService.findById(sid);
		model.addAttribute("message", message);
		return "system/message/update_page";
	}
	
	@RequestMapping(value="update")
	@ResponseBody
	public String update(ResultBean rs,PlatformMessage message,HttpServletRequest req) {
		try{
			message.setCreateTime(new Date());
			message.setMsgtype("-1");
			systemMessageService.update(message);
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
	 * @param message
	 * @param req
	 * @return
	 */
	@RequestMapping(value="insertMessage")
	@ResponseBody
	public String insertMessage(ResultBean rs,PlatformMessage message,HttpServletRequest req){
		try{
			message.setCreateTime(new Date());
			ShiroUser user = getCurrentUser();
			String createUser=user.getName();
			message.setCreateUser(createUser);
			message.setMsgtype("-1");
			systemMessageService.save(message);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
}

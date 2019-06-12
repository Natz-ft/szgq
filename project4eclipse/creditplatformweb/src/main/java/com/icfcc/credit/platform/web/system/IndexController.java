package com.icfcc.credit.platform.web.system;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.PlatformMenu;
import com.icfcc.credit.platform.jpa.entity.system.PlatformMessage;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUser;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserLoginLog;
import com.icfcc.credit.platform.service.system.PlatformRoleService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ShiroUser;

@Controller
public class IndexController extends PlatformBasicController{
	
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private PlatformRoleService systemRole;
	
	@RequestMapping(value="success")
	public ModelAndView successPage(HttpServletRequest req, HttpServletResponse rep){
		ModelAndView model=new ModelAndView();
		ShiroUser shiroUser = getCurrentUser();
		PlatformUser user=userService.findUserByUserName(shiroUser.name);
		//获取最后一个日志对象
		List<PlatformUserLoginLog> loglist=userService.getAllLoginLog(user.getId());
		if(loglist==null || loglist.size()==0 ){
			return model;
		}
		PlatformUserLoginLog log=loglist.get(0);
		log.setSuccessFlag(1);
		//成功则无失败原因
		log.setFailReason("登陆成功");
		String ip=getIpAddr(req);
		log.setLoginIp(ip);
		userService.insertLoginLog(log);
		//默认显示征信菜单
		String sysNo="10000";
		model.addObject("sysNo", sysNo);
		model.setViewName("forward:/");
//		}
		return model;
	}
	/**
     * 获取登录用户IP地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
    	String ipAddress = request.getHeader("x-forwarded-for");  
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getHeader("Proxy-Client-IP");  
        }  
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            ipAddress = request.getRemoteAddr();  
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                //根据网卡取本机配置的IP  
                InetAddress inet=null;  
                try {  
                    inet = InetAddress.getLocalHost();  
                } catch (UnknownHostException e) {  
                    e.printStackTrace();  
                }  
                ipAddress= inet.getHostAddress();  
            }  
        }  
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
            if(ipAddress.indexOf(",")>0){  
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
            }  
        }  
        return ipAddress; 
    }
	@RequestMapping(value="password")
	public ModelAndView mainFrame(HttpServletRequest req, HttpServletResponse rep){
		ModelAndView model=new ModelAndView();
		model.setViewName("common/password");
		return model;
	}
	
	@RequestMapping(value="passwordHead")
	public ModelAndView passwordHead(HttpServletRequest req, HttpServletResponse rep){
		ModelAndView model=new ModelAndView();
		model.setViewName("common/password_head");
		return model;
	}
	
	@RequestMapping(value="changePassword")
	public ModelAndView changePassword(HttpServletRequest req, HttpServletResponse rep){
		ModelAndView model=new ModelAndView();
		model.setViewName("common/change_password");
		return model;
	}
	
	
	
	@RequestMapping(value = "head")
    public ModelAndView headPage(HttpServletRequest req, HttpServletResponse rep)
    {	 
		ModelAndView model = new ModelAndView();
    	model.setViewName("common/head");
		ShiroUser user = getCurrentUser();
		boolean iSselected=false;
		List<PlatformMenu> menus = sysMenuService.getMenuByUserId(user.getId(),iSselected);
		model.addObject("menus", menus);
		model.addObject("user", user);
        return model;
    }
	
	@RequestMapping(value = "menu")
    public ModelAndView menu(HttpServletRequest req, HttpServletResponse rep)
    {	
		ModelAndView model = new ModelAndView();
    	model.setViewName("common/menu");
    	String sysNo = req.getParameter("sysNo");
    	List<PlatformMenu> menus = null;
    	if(!StringUtils.isEmpty(sysNo)) {
    		menus = getSubMenuList(sysNo);
    	}
    	model.addObject("menus", menus);
    	
        return model;
		
    }
	@RequestMapping(value = "MenuTree")
    public String newMenu(HttpServletRequest req, HttpServletResponse rep)
    {	
    	List<PlatformMenu> menus = null;
    		menus = getAllUserMenu();
    		String json = JSON.toJSONString(menus, SerializerFeature.DisableCircularReferenceDetect);

    		System.out.println("============json========================="+json);
        return json;
		
    }
	@RequestMapping(value = "main")
   public String main(Model model,PageBean page,HttpServletRequest request)
	{
		Page<PlatformMessage>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
	        queryResults = systemMessageService.getSystemMessageList(searchParams, page.getCurPage(),page.getMaxSize(),VipCont.DIRECTION, VipCont.ORDERBY);
			
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		return "common/main";
        
    }
	@RequestMapping(value="message/detail")
	public ModelAndView messageDetail(Long id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/message/detail");
		PlatformMessage message=new PlatformMessage();
		message=systemMessageService.findById(id);
		model.addObject("message", message);
		return model;
	}
	/*@RequestMapping(value="news/detailNews")
	public ModelAndView newsDetail(Long id){
		ModelAndView model = new ModelAndView();
    	model.setViewName("system/news/newsDetail");
    	PlatformNews news=new PlatformNews();
    	System.out.println("==============="+id);
		 news=systemContentNewsService.findById(id);
		model.addObject("news",news);
		return model;
	}*/
	@RequestMapping("news/upload")
	@ResponseBody
	public Map<String,Object> image(MultipartFile file,HttpServletRequest request){
		 Map<String,Object> map = new  HashMap<String,Object>();
		
		 String type=request.getParameter("type");
		 String id=request.getParameter("id");
		 try {
//			 String imgPath=request.getSession().getServletContext().getRealPath(path);
		     Map<String,Object> data=platformUploadService.updateFile(file,type,id);
            map.put("code", 0);
            map.put("msg", "上传成功");
            map.put("data", data);;

		} catch (Exception e) {
			map.put("code", 1);
			map.put("msg", "上传失败");
			e.printStackTrace();
			// TODO: handle exception
		}
		 return map;
		 
	}
	
    @RequestMapping("news/uploadImage")
    @ResponseBody
    public Map<String,Object> uploadImage(MultipartFile file,HttpServletRequest request){
         Map<String,Object> map = new  HashMap<String,Object>();
         String id=request.getParameter("id");
         String type="/news/";
         try {
//           String imgPath=request.getSession().getServletContext().getRealPath(path);
             Map<String,Object> data=platformUploadService.updateFile(file,type,id);
            map.put("code", 0);
            map.put("msg", "上传成功");
            map.put("data", data);;

        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "上传失败");
            e.printStackTrace();
            // TODO: handle exception
        }
         return map;
         
    }
    
	
	@RequestMapping(value = "middle")
    public String middle(ModelMap model)
    {
        return "common/middle";
    }
	
	@RequestMapping(value = "foot")
    public String foot(ModelMap model)
    {
        return "common/foot";
    }
	
	@RequestMapping(value = "logout")
    public String logout(ModelMap model)
    {	
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			ShiroUser user = getCurrentUser();
			String username = user.getNickname();
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
			if (log.isDebugEnabled()) {
				log.debug("user {} logout",username);
			}
		}
        return "common/login";
    }
	@RequestMapping(value = "logoutIndex")
    public void logoutIndex(ModelMap model,HttpServletRequest req)
    {	System.out.println("logout=========================");
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			ShiroUser user = getCurrentUser();
			req.getSession().setAttribute("user", user);
			String username = user.getNickname();
			//subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
			if (log.isDebugEnabled()) {
				log.debug("user {} logout",username);
			}
		}
    }
}

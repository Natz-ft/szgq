package com.icfcc.credit.platform.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.icfcc.credit.platform.jpa.entity.system.PlatformUser;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserLoginLog;
import com.icfcc.credit.platform.security.UsernamePasswordCaptchaToken;
import com.icfcc.credit.platform.service.system.PlatformConfigService;
import com.icfcc.credit.platform.service.system.PlatformUserService;
import com.icfcc.credit.platform.session.RedisCacheManager;
import com.icfcc.credit.platform.util.ShiroUser;
/**
 * 自定义的身份验证过滤器
 * @author JERRY.CHEN
 *
 */
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	public static final String AUTH_NUM = "auth_num";
	@Autowired
	private PlatformUserService userService;
	private static Logger log = LoggerFactory.getLogger(FormAuthenticationCaptchaFilter.class);


	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {

		return captchaParam;

	}
	
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        // 先判断是否是登录操作
        if (isLoginSubmission(request, response)) {
        	if (log.isTraceEnabled()) {
        		 log.trace("Login submission detected.  Attempting to execute login.");
        	}
        	 return false;
        }
        //返回subject是否已经登陆验证通过了，因为没有logout，所以返回true
        return subject.isAuthenticated();
    }
	protected String getCaptcha(ServletRequest request) {

		return WebUtils.getCleanParam(request, getCaptchaParam());

	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		RedisCacheManager redisCacheManager=(RedisCacheManager)wac.getBean("redisCacheManager");
		Cache<Object, Object> cache = redisCacheManager.getCache("shiro-kickout-session");
		Session session = subject.getSession();
		ShiroUser user=(ShiroUser) subject.getPrincipal();
        HashMap<String, String> map=new HashMap<String, String>();
        map.put("username", user.getName());
        map.put("userId", user.getId());
        //获取当前用户所属公司组织机构代码
        PlatformUser systemUser=userService.findUserByUserName(user.getName());
        map.put("orgNo", systemUser.getOrg());
        map.put("type", systemUser.getType());
        map.put("level", systemUser.getDesc1());
		cache.put(session.getId(), map);//用户登陆信息放到缓存
		cache.put(user.getId(), session.getId());//用于用户与sessionId关联，用户可以拿到session
		
		HttpServletRequest hsreq=(HttpServletRequest) request;
		HttpSession session1= hsreq.getSession();
		String detailfinaceURL =  (String)session1.getAttribute("detailfinaceURL");
		 SavedRequest savedRequest = WebUtils.getSavedRequest(request);
	        String superURL = null;
	        if(savedRequest!=null){
	            superURL = savedRequest.getRequestUrl();
	            System.out.println("head.referer=" + hsreq.getHeader("referer"));
	            String host=hsreq.getHeader("referer");
	            String type=savedRequest.getQueryString();
	            host=host.substring(0, host.indexOf("/creditplatformweb/login"));
	            if(    superURL.contains("portal/demandQuery/viewDetail")|| 
	            		superURL.contains("portal/investorQuery/viewDetail.do")|| 
	            		superURL.contains("portal/detailfinace")||
	            		superURL.contains("portal/detailinvestevent")|| 
	            		superURL.contains("/portal/eventQuery/viewDetail")){
	            	session.setAttribute("user", "login");
		            	superURL=host+superURL;
		            	
	            }else if(superURL.contains("type=financeTalk")){
	            	superURL="/SRRPBusinesWeb/portal/finace.html";
	            	superURL=host+superURL;
	            	session.setAttribute("user", "login");
	            }else{
	            	 superURL = getSuccessUrl();
	            }
	        }else{
	            superURL = getSuccessUrl();
	        }
	        WebUtils.getAndClearSavedRequest(request);
	        WebUtils.redirectToSavedRequest(request, response, superURL);
		return false;
	}
	
	
//	@Override
//	protected boolean onLoginFailure(AuthenticationToken token,
//			AuthenticationException e, ServletRequest request,
//			ServletResponse response) {
//		setFailureAttribute(request, e);
//		String username=request.getParameter("username");
//		PlatformUser user=userService.findUserByUserName(username);
//		PlatformUserLoginLog log=userService.getAllLoginLog(user.getId()).get(0);
//		log.setSuccessFlag(0);
//		log.setFailReason(e.getMessage());
//		try {
//			WebUtils.redirectToSavedRequest(request, response, "/failure");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		return true;
//	}


	protected AuthenticationToken createToken(

	ServletRequest request, ServletResponse response) {

		String username = getUsername(request);

		String password = getPassword(request);

		String captcha = getCaptcha(request);

		boolean rememberMe = isRememberMe(request);

		String host = getHost(request);

		return new UsernamePasswordCaptchaToken(username,
				password.toCharArray(), rememberMe, host, captcha);

	}

}

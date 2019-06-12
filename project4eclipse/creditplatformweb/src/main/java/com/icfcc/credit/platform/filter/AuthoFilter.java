package com.icfcc.credit.platform.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.LoadingCache;
import com.icfcc.credit.platform.service.system.PlatformUserRoleMenuService;
//import com.icfcc.credit.platform.jpa.entity.system.ShiroUser;
import com.icfcc.credit.platform.session.RedisCacheManager;
import com.icfcc.credit.platform.util.FilterProperties;
import com.icfcc.credit.platform.util.GuavaCacheUtil;

public class AuthoFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(AuthoFilter.class);
	private static LoadingCache<String, List<String>> cacheMenu = null;
	private static LoadingCache<String, List<String>> cacheAll = null;
	private static List<String> speUrl = Arrays.asList(new String[] { "static", "login", "logout", "checkCode",
			"success", "finalPassword", "changePassword", "druid", "" });
	private final static String SHAREJSESSIONID = "SHAREJSESSIONID";
	private WebApplicationContext ctx;
	private FilterConfig config;

	@Override
	public void destroy() {
		config = null;
		ctx = null;
	}

	protected Object getBean(String name) {
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		}
		return ctx.getBean(name);
	}

	private String getUserNameFromCache(String sessionId) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		RedisCacheManager redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
		Cache<Object, Object> cache = redisCacheManager.getCache("shiro-kickout-session");
		Object object = cache.get(sessionId);
		Map map = (Map) object;
		String userId = (String) map.get("userId");
		return userId;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info(" start:");
		urlFilter(request, response, chain);
		log.info(" end");
	}

	private void urlFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		log.info("project url:" + req.getContextPath());
		// 请求访问的地址
		String urlNew = urlUtil(req);
		boolean isAllRoleUrl;
		boolean isUserRoleUrl = true;
		boolean isUrl = isMedthodUrl(urlNew);
		if (isUrl) {
			log.info("static:" + urlNew);
			chain.doFilter(request, response);

		} else {
			// String userId = getUserNameFromCache(sessionId);
			String sessionId = getSessionID(req);
			if (sessionId == null || getUserNameFromCache(sessionId) == null) {
				loginoOroutUrl(rep, "/login");
			}
			String userId = getUserNameFromCache(sessionId);
			if (null == userId) {
				log.info("shiroUser ==null ,urlNew={}", urlNew);
				chain.doFilter(request, response);
			} else {
				PlatformUserRoleMenuService userMenuService = (PlatformUserRoleMenuService) getBean(
						"sysUserRoleMenuService");
				initListAllCache(userMenuService);
				isAllRoleUrl = isAllAllRoleURL(urlNew);

				if (!isAllRoleUrl) {
					log.info("!isAllRoleUrl access ok! current url ={}", urlNew);
					chain.doFilter(request, response);
				} else {
					initListLinkCache(userMenuService, userId);
					isUserRoleUrl = isUserRoleUrl(urlNew, userId);
					if (isUserRoleUrl) {
						log.info("isUserRoleUrl access ok! current url ={}", urlNew);
						chain.doFilter(request, response);
					} else {
						log.info("access faild !!!!! current url ={}", urlNew);
						loginoOroutUrl(rep, "/login");
					}
				}
			}
		}

	}

	private boolean isMedthodUrl(String urlNew) {
		boolean isUrl = false;
		for (int i = 0; i < speUrl.size(); i++) {
			String methodUrl = speUrl.get(i);
			if (urlNew.contains(methodUrl)) {
				isUrl = true;
				break;
			} else {
				isUrl = false;
			}
		}
		return isUrl;
	}

	private void loginoOroutUrl(HttpServletResponse rep, String url) throws IOException {
		String contentPath = FilterProperties.getProjectName();
		String newUrl = contentPath + url;
		rep.sendRedirect(newUrl);
	}

	private String urlUtil(HttpServletRequest req) {
		String reqUrl = req.getRequestURI();
		String context = req.getContextPath() + "/";
		String urlNew = reqUrl.replace(context, "");
		log.info("urlNew:" + urlNew);
		return urlNew;
	}

	private boolean isUserRoleUrl(String urlNew, String userId) {
		boolean isUserRoleUrl = true;
		try {
			if (!"".equals(urlNew)) {
				urlNew = urlNew.substring(0, urlNew.lastIndexOf("/"));
			}
			log.info("urlNew:" + urlNew);
			List<String> listUserRoleUrl = cacheMenu.get(userId);
			if (CollectionUtils.isNotEmpty(listUserRoleUrl)) {
				log.info("listUserRoleUrl:" + JSON.toJSONString(listUserRoleUrl));
			}
			isUserRoleUrl = listUserRoleUrl.contains(urlNew);
		} catch (Exception e) {
			log.error(e.getMessage());
			isUserRoleUrl = false;
		}
		return isUserRoleUrl;
	}

	private String getSessionID(HttpServletRequest httpRequest) {
		Cookie[] cookies = httpRequest.getCookies();
		String sessionId = null;
		for (Cookie c : cookies) {
			String name = c.getName();
			if (!StringUtils.isEmpty(name) && SHAREJSESSIONID.equals(name)) {
				sessionId = c.getValue();
			}
		}
		return sessionId;
	}

	boolean isAllAllRoleURL(String urlNew) {
		boolean allLink;
		try {
			String urlLastIndex = urlNew.substring(0, urlNew.lastIndexOf("/"));
			log.info("urlLastIndex:" + urlLastIndex);
			List<String> listAllUrl = cacheAll.get("allLink");
			if (CollectionUtils.isNotEmpty(listAllUrl)) {
				log.info("listAllUrl:" + JSON.toJSONString(listAllUrl));
			}
			allLink = listAllUrl.contains(urlLastIndex);
		} catch (Exception e) {
			log.error(e.getMessage());
			allLink = false;
		}
		return allLink;
	}

	void initListLinkCache(PlatformUserRoleMenuService userMenuService, String userId) {
		List<String> listLink;
		List<String> listLinkCache = null;
		try {
			listLinkCache = cacheMenu.get(userId);
		} catch (Exception e) {
			if (CollectionUtils.isEmpty(listLinkCache)) {
				listLink = userMenuService.findByIdLink(userId);
				cacheMenu = GuavaCacheUtil.getListCache(listLink, userId);
			}
		}
	}

	void initListAllCache(PlatformUserRoleMenuService userMenuService) {
		List<String> listAll;
		List<String> listAllCache = null;
		try {
			listAllCache = cacheAll.get("allLink");
		} catch (Exception e) {
			if (CollectionUtils.isEmpty(listAllCache)) {
				listAll = userMenuService.getAllMenu();
				cacheAll = GuavaCacheUtil.getListCache(listAll, "allLink");
			}
		}
	}

	public void init(FilterConfig filterconfig) throws ServletException {
		this.config = filterconfig;
	}

}
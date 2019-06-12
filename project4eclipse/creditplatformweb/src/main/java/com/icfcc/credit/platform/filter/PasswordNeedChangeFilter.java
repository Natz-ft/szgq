package com.icfcc.credit.platform.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PasswordNeedChangeFilter implements Filter {
	
	private static Logger logger = LoggerFactory.getLogger(PasswordNeedChangeFilter.class);
	
	private final static String STATIC = "static";
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest req = (HttpServletRequest) request;      
		 HttpServletResponse rep = (HttpServletResponse) response;
		 String currentUrl = getCurrentUrl(req);
		 if (currentUrl.contains(STATIC)) {
				logger.debug("static:" + currentUrl);
				chain.doFilter(request, response);
		 }else{
			 chain.doFilter(request, response);
		 }
		 
	}
	private String getCurrentUrl(HttpServletRequest httpRequest) {
		String reqUrl = httpRequest.getRequestURI();
		String context = httpRequest.getContextPath() + "/";
		String urlNew = reqUrl.replace(context, "");
		return urlNew;
	}

	@Override
	public void destroy() {

	}

}

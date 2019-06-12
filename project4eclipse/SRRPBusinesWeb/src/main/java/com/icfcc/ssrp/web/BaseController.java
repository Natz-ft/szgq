package com.icfcc.ssrp.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.Servlets;
import com.icfcc.credit.platform.util.ShiroUser;

public abstract class BaseController {
	public final static String  QUERY_PARMA_PREFIX= "search_"; 
	
	public Map<String, Object> processRequestParams(PageBean page,
			HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request,QUERY_PARMA_PREFIX,page);
		return searchParams;
	}
	   /**
     * 取出Shiro中的当前用户.
     */
    public ShiroUser getCurrentUser()
    {
        ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
        return user;
    }
	/**
	 * 将查询结果，回填到返回的PageBean中�??
	 * @param page
	 * @param queryResults
	 */
	public <T> void processQueryResults(Model model,PageBean page, Page<T> queryResults) {
		if(null == queryResults)
			return;
		List<T> list = queryResults.getContent();
		if (CollectionUtils.isNotEmpty(list)) {
			page.setList(list);
			//设置总的页数
			page.setPageCnt(queryResults.getTotalPages());
			//设置总的条数
			page.setRecordCnt(new Long(queryResults.getTotalElements()).intValue());
			page.pageResult(list,page.getRecordCnt(), page.getMaxSize(), page.getCurPage());
		}
		model.addAttribute("page", page);
	}
	public String[] getParaStrings(String name,ServletRequest request) {
		String[] s = {};
		if (request.getParameterValues(name)==null) {
			return s;
		}
		return request.getParameterValues(name);
	}
	
	public String getParaString(String name,ServletRequest request) {
		if (request.getParameter(name)==null) {
			return "";
		}
		return request.getParameter(name);
	}
	
	public Long getParaLong(String name,ServletRequest request) {
		return Long.parseLong(getParaString(name,request));
	}
	
	public Integer getParaInteger(String name,ServletRequest request) {
		return Integer.parseInt(getParaString(name,request));
	}
	
	public BigDecimal getParaBigDecimal(String name,ServletRequest request) {
		return new BigDecimal(getParaString(name,request));
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空，false:不能为空
	}
	
}

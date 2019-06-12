/**
 *  Copyright (c)  2017-2027 ICFCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of ICFCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with ICFCC.
 */
package com.icfcc.ssrp.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * <获取用户相关信息>
 * 
 * @author Densen.Liu
 * @date 2017年6月30日
 */
public class RedisGetValue {

	private final static String SHAREJSESSIONID = "SHAREJSESSIONID";
	private final static String DEFAULTDD = "--";
	static WebApplicationContext wac = null;
	static RedisCacheManager redisCacheManager = null;
	static Cache<Object, Object> cache = null;

	/**
	 * <根据请求获取redis中存入的用户信息对象>
	 * 
	 * @param httpRequest
	 * @return
	 * @author Densen.Liu
	 * @date 2017年6月30日
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getUserInfo(HttpServletRequest httpRequest) {
		Map<String, String> map = null;

		try {
			if (wac == null) {
				wac = ContextLoader.getCurrentWebApplicationContext();
			}
			if (redisCacheManager == null) {
				redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
			}
			if (cache == null) {
				cache = redisCacheManager.getCache("shiro-kickout-session");
			}
			String sessionId = getSessionId(httpRequest);
			if (!StringUtils.isNotBlank(sessionId) || cache.get(sessionId) == null) {
				return new HashMap<String, String>();
			}
			map = (Map<String, String>) cache.get(sessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * <根据httprequest获取sessionid>
	 * 
	 * @param httpRequest
	 * @return
	 * @author Densen.Liu
	 * @date 2017年6月30日
	 */
	private static String getSessionId(HttpServletRequest httpRequest) {
		Cookie[] cookies = httpRequest.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		String sessionId = null;
		for (Cookie c : cookies) {
			String name = c.getName();
			if (!StringUtils.isEmpty(name) && SHAREJSESSIONID.equals(name)) {
				sessionId = c.getValue();
			}
		}
		return sessionId;
	}
	public static String getAreaValueByCode(String redisKey, String dicCode) {
		try {
			if (!StringUtils.isNotBlank(redisKey) || !StringUtils.isNotBlank(dicCode)) {
				return DEFAULTDD;
			}
			if (wac == null) {
				wac = ContextLoader.getCurrentWebApplicationContext();
			}
			if (redisCacheManager == null) {
				redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
			}
		
			String ddValus = redisCacheManager.getRedisManager().get(redisKey);
			if (!StringUtils.isNotBlank(ddValus)) {
				return DEFAULTDD;
			}
			List<investorArea> dataList = JSON.parseArray(ddValus, investorArea.class);
			if (dataList != null && dataList.size() > 0) {
				for (investorArea dd : dataList) {
					if (dicCode.equals(dd.getCode())) {
						return dd.getName();
					}
				}
			} else {
				return DEFAULTDD;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DEFAULTDD;
	}
	public static String getValueByCode(String redisKey, String dicCode) {
		try {
			if (!StringUtils.isNotBlank(redisKey) || !StringUtils.isNotBlank(dicCode)) {
				return DEFAULTDD;
			}
			if (wac == null) {
				wac = ContextLoader.getCurrentWebApplicationContext();
			}
			if (redisCacheManager == null) {
				redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
			}
		
			String ddValus = redisCacheManager.getRedisManager().get(redisKey);
			if (!StringUtils.isNotBlank(ddValus)) {
				return DEFAULTDD;
			}
			List<DD> dataList = JSON.parseArray(ddValus, DD.class);
			if (dataList != null && dataList.size() > 0) {
				for (DD dd : dataList) {
					if (dicCode.equals(dd.getDicCode())) {
						return dd.getDicName();
					}
				}
			} else {
				return DEFAULTDD;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DEFAULTDD;
	}
	public static String getCodeByValue(String redisKey, String value) {
		try {
			if (!StringUtils.isNotBlank(redisKey) || !StringUtils.isNotBlank(value)) {
				return "";
			}
			if (wac == null) {
				wac = ContextLoader.getCurrentWebApplicationContext();
			}
			if (redisCacheManager == null) {
				redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
			}
		
			String ddValus = redisCacheManager.getRedisManager().get(redisKey);
			if (!StringUtils.isNotBlank(ddValus)) {
				return "";
			}
			List<DD> dataList = JSON.parseArray(ddValus, DD.class);
			if (dataList != null && dataList.size() > 0) {
				for (DD dd : dataList) {
					if (value.equals(dd.getDicName())) {
						return dd.getDicCode();
					}
				}
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String getCodeByValueArea(String redisKey, String value) {
		try {
			if (!StringUtils.isNotBlank(redisKey) || !StringUtils.isNotBlank(value)) {
				return "";
			}
			if (wac == null) {
				wac = ContextLoader.getCurrentWebApplicationContext();
			}
			if (redisCacheManager == null) {
				redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
			}
		
			String ddValus = redisCacheManager.getRedisManager().get(redisKey);
			if (!StringUtils.isNotBlank(ddValus)) {
				return "";
			}
			List<DD> dataList = JSON.parseArray(ddValus, DD.class);
			if (dataList != null && dataList.size() > 0) {
				for (DD dd : dataList) {
					if (dd.getDicName().contains(":")) {
						if (value.equals(StringUtils.substringAfter(dd.getDicName(), ":"))) {
							return dd.getDicCode();
						}
					}else{
						if (dd.getDicName().equals(value)) {
							return dd.getDicCode();
						}
					}
				}
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static List<DD> getDataList(String redisKey) {
		List<DD> dataList = null;
		try {
			if (wac == null) {
				wac = ContextLoader.getCurrentWebApplicationContext();
			}
			if (redisCacheManager == null) {
				redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
			}
		
			String ddValus = redisCacheManager.getRedisManager().get(redisKey);
			if (!StringUtils.isNotBlank(ddValus)) {
				return new ArrayList<DD>();
			}
			dataList = JSON.parseArray(ddValus, DD.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	public static List<investorArea> getAeaDataList(String redisKey) {
		List<investorArea> dataList = null;
		try {
			if (wac == null) {
				wac = ContextLoader.getCurrentWebApplicationContext();
			}
			if (redisCacheManager == null) {
				redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
			}
		
			String ddValus = redisCacheManager.getRedisManager().get(redisKey);
			if (!StringUtils.isNotBlank(ddValus)) {
				return new ArrayList<investorArea>();
			}
			dataList = JSON.parseArray(ddValus, investorArea.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public static Map<String, String>  getDataMap(String redisKey) {
		Map<String, String> map= new LinkedHashMap<String, String>();
		List<DD> dataList = null;
		try {
			if (wac == null) {
				wac = ContextLoader.getCurrentWebApplicationContext();
			}
			if (redisCacheManager == null) {
				redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
			}
		
			String ddValus = redisCacheManager.getRedisManager().get(redisKey);
			if (!StringUtils.isNotBlank(ddValus)) {
				dataList= new ArrayList<DD>();
			}
			dataList = JSON.parseArray(ddValus, DD.class);
			for(DD list:dataList){
				map.put(list.getDicCode(), list.getDicName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * @Title: getDDList @Description: TODO(取查询页面的查询条件的列表字典值) @param @param type
	 * 前台ajax传过的type @param @return 设定文件 @return List<DD> 返回类型 @throws
	 */
	public static List<DD> getDDList(String type) {
		List<DD> dataList = null;
		String ddValus = null;
		try {
			if (wac == null) {
				wac = ContextLoader.getCurrentWebApplicationContext();
			}
			if (redisCacheManager == null) {
				redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
			}
			if ("industry".equals(type)) {// 行业
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_INDUSTRY);
			} else if ("finacingTurn".equals(type)) {// 融资轮次
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_FINACINGTURN);
			} else if ("demandstatus".equals(type)) {// 需求状态
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_DEMANDSTATUS);
			} else if ("infotype".equals(type)) {// 信息批露类型
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_PUBLISHTYPE);
			} else if ("amountCode".equals(type)) {// 融资金额范围
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_FINACINGMONEY);
			} else if ("rearea".equals(type)) {// 所属区域
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_AREA);
			} else if ("financingphase".equals(type)) {// 现融资阶段
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_INVESTMENT);
			} else if ("stopFlag".equals(type)) {// 停用标识
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_INVESTORSTATUS);
			} else if ("auditStatus".equals(type)) {// 审核状态
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_AUDITSTATE);
			}else if ("newsType".equals(type)) {// 审核状态
				ddValus = redisCacheManager.getRedisManager().get(SRRPConstant.DD_NEWSTYPR);
			}
			dataList = JSON.parseArray(ddValus, DD.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}

	public static String getRedisUser(HttpServletRequest httpRequest, String key) {
		Map<String, String> userInfo = RedisGetValue.getUserInfo(httpRequest);
		if(userInfo == null || userInfo.isEmpty()){
			return null;
		}
		String value = userInfo.get(key).trim();
		return value;
	}
	//获取用户登录信息
	public static Map<String, String> getRedisUserInfos(HttpServletRequest httpRequest) {
		Map<String, String> userInfo = RedisGetValue.getUserInfo(httpRequest);
		return userInfo;
	}
}

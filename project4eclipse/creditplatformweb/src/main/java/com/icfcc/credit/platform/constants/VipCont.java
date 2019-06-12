package com.icfcc.credit.platform.constants;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.session.RedisCacheManager;

public class VipCont {
	/**
	 * 
	 */
	public static final String CUS_TYPE_ID_BASIC = "1";
	/**
	 * 
	 */
	public static final String CUS_TYPE_ID_JUSTICE = "1";
	
	public static final String CUS_TYPE_ID_CREDIT = "1";
	
	public static final String FINAN_ID = "1";

	
	//数据库倒序关键字
    public final static String DIRECTION = "desc";
    
    
    public  final static String  ERRORCODE ="00001"; 
	public  final static String  ERRORMSG ="failed"; 
	public final static String  SUCCESSCODE ="00000";
	public final static String  SUCCESSMSG ="sucessful";
	
	
	
	public static final String DATA_STATE="0";
	
	public static final String DATA_STATE1="1";
	
	public static final String AUDIT_STATE = "0";
	
	public static final String COM_CERT_ID = "1";
	
	public static final String ORDERBY = "createTime";// 档案入库时间
	public static final String ORDER = "operdate";// 档案入库时间
	public static final String ORDEREvent = "operDate";// 档案入库时间
	public static String getValueByCode(String redisKey, String dicCode) {
		try {
			if (!StringUtils.isNotBlank(redisKey) || !StringUtils.isNotBlank(dicCode)) {
				return "--";
			}
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			RedisCacheManager redisCacheManager=(RedisCacheManager)wac.getBean("redisCacheManager");
			String ddValus = redisCacheManager.getRedisManager().get(redisKey);
			if (!StringUtils.isNotBlank(ddValus)) {
				return "--";
			}
			List<DD> dataList = JSON.parseArray(ddValus, DD.class);
			if (dataList != null && dataList.size() > 0) {
				for (DD dd : dataList) {
					if (dicCode.equals(dd.getDicCode())) {
						return dd.getDicName();
					}
				}
			} else {
				return "--";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "--";
	}
	public static List<DD> getValueList(String redisKey) {
		List<DD> dataList = null;
		try {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			RedisCacheManager redisCacheManager=(RedisCacheManager)wac.getBean("redisCacheManager");
			String ddValus = redisCacheManager.getRedisManager().get(redisKey);
			dataList = JSON.parseArray(ddValus, DD.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public static List<investorArea> getAeaDataList(String redisKey) {
		List<investorArea> dataList = null;
		try {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			RedisCacheManager redisCacheManager=(RedisCacheManager)wac.getBean("redisCacheManager");
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
}

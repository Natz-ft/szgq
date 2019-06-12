/**
 *  Copyright (c)  2017-2027 ICFCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of ICFCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with ICFCC.
 */
package com.icfcc.credit.platform.util;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * <JSON处理>
 * 
 * @author tanshengrui
 * @date 2017年6月29日
 */
public class JsonUtil {

	/**
	 * <实体转成Json字符串>
	 * @param object
	 * @param format
	 *            TODO  指定时间格式
	 * @param format
	 *            时间格式
	 * @return
	 * @author tanshengrui
	 * @date 2017年7月3日
	 */
	public static String toJSONString(Object object, String format) {
		if (StringUtils.isNotBlank(format)) {
			JSON.DEFFAULT_DATE_FORMAT = format;
		}
		String jsonString = JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}
	
	
	/**
	 * <实体转成Json字符串>
	 * @param object
	 * @return
	 * @author tanshengrui
	 * @date 2017年7月4日
	 */
	public static String toJSONString(Object object) {
		String jsonString = JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}
	
	public static String getString(net.sf.json.JSONObject vojo,String key){
		if(!StringUtils.isNotBlank(vojo.toString())){
			return "";
		}else{
			if(vojo.containsKey(key)){
				return vojo.getString(key);
			}else{
				return "";
			}
		}
	}
	
	public static int getInt(net.sf.json.JSONObject jsonstr,String key){
		if(!StringUtils.isNotBlank(jsonstr.toString())){
			return 0;
		}else{
			if(jsonstr.containsKey(key)){
				return jsonstr.getInt(key);
			}else{
				return 0;
			}
		}
	}
	
}

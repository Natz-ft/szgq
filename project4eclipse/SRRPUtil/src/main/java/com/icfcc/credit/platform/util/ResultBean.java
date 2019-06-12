package com.icfcc.credit.platform.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class ResultBean {
	private String msg;
	private String code;
	private Object objectData;
	private static ResultBean sucess = new ResultBean();
	private List<?> list1;
	private List<?> list2;
	public ResultBean() {

	}

	public static ResultBean sucessResultBean() {
		return sucessResultBean(null);
	}

	public static ResultBean sucessResultBean(String sucessMsg) {
		sucess.setCode(Constant.SUCCESSCODE);
		if (StringUtils.isEmpty(sucessMsg)) {
			sucess.setMsg(Constant.SUCCESSMSG);
		} else {
			sucess.setMsg(sucessMsg);
		}
		return sucess;
	}

	public static ResultBean sucessResultBean(String sucessMsg, Map<String, Object> map) {
		sucess.setCode(Constant.SUCCESSCODE);
		if (StringUtils.isEmpty(sucessMsg)) {
			sucess.setMsg(Constant.SUCCESSMSG);
		} else {
			sucess.setMsg(sucessMsg);
		}
		sucess.setObjectData(map);
		return sucess;
	}

	public ResultBean(String msg, String code, Object objectData) {
		super();
		this.msg = msg;
		this.code = code;
		this.objectData = objectData;
	}

	public ResultBean(String code, String msg) {
		super();
		this.msg = msg;
		this.code = code;
	}
	public ResultBean(String code, String msg,List<?> list1,List<?> list2) {
		super();
		this.msg = msg;
		this.code = code;
		this.list1=list1;
		this.list2=list2;
	}
	public static ResultBean resultBeanData(String msg, String code, Object objectData) {
		ResultBean result = new ResultBean(msg, code, objectData);
		return result;
	}

	/**
	 * 将实体转化转成JSON字符串
	 * 
	 * @param resultBean
	 * @return
	 */
	public String toJSONStr() {
		String result = JSON.toJSONString(this);
		return result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getObjectData() {
		return objectData;
	}

	public void setObjectData(Object objectData) {
		this.objectData = objectData;
	}

	public static ResultBean getSucess() {
		return sucess;
	}

	public static void setSucess(ResultBean sucess) {
		ResultBean.sucess = sucess;
	}

	public List<?> getList1() {
		return list1;
	}

	public void setList1(List<?> list1) {
		this.list1 = list1;
	}

	public List<?> getList2() {
		return list2;
	}

	public void setList2(List<?> list2) {
		this.list2 = list2;
	}

	/**
	 * 将实体转化转成JSON字符串
	 * 
	 * @param resultBean
	 * @return
	 */
	/*
	 * public static Object stringToObject( resultBean){
	 * 
	 * if (resultBean == null) return null; Object object =
	 * JSONObject.toJavaObject(JSON.parseObject(stuStr), Student.class); return
	 * object; }
	 */

}

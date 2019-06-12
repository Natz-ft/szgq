package com.icfcc.credit.platform.service;

import java.util.Map;

public class Constants {
	
	public static String KEY_DICTYPE_RZJD = "RZJD"; //机构融资阶段
	public static String KEY_DICTYPE_TZHY = "TZHY";//投资行业
	public static String KEY_DICTYPE_JGLX = "JGLX";//机构类型
	public static String KEY_DICTYPE_HBLX = "HBLX";//货币类型
	public static String KEY_DICTYPE_TZLC = "TZLC";//投资轮次
	public static String KEY_DICTYPE_ZBLX = "ZBLX";//资本了行
	public static String KEY_DICTYPE_DMLX = "DMLX";//机构代码类型
	public static String KEY_DICTYPE_SJJD = "SJJD";//融资时间阶段
	public static String KEY_DICTYPE_XXPL = "XXPL";//信息披露类型
	public static String KEY_DICTYPE_SHJG = "SHJG";//审核结果
	

	public static String KEY_USERTYPE_ENTERPRISE = "enterprise";//企业用户
	public static String KEY_USERTYPE_ORGANIZITION = "org";//投资机构用户
	public static String KEY_USERTYPE_CHARGE = "charge";//主管机构用户
	public static String KEY_USERTYPE_ADMIN = "admin";//管理员

	private Map<String, String> dicTypeMap;

	private Map<String, String> userType;

	public Map<String, String> getDicTypeMap() {
		return dicTypeMap;
	}

	public void setDicTypeMap(Map<String, String> dicTypeMap) {
		this.dicTypeMap = dicTypeMap;
	}

	public Map<String, String> getUserType() {
		return userType;
	}

	public void setUserType(Map<String, String> userType) {
		this.userType = userType;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package com.icfcc.credit.platform.jpa.entity.business;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DicWord {

	private static DicWord dicWord = new DicWord();
	
	private DicWord(){}

	public static DicWord getDicWord() {
		return dicWord;
	}

	public static Map<String,LinkedHashMap<String, String>> map=new HashMap<String,LinkedHashMap<String, String>>();

	public Map<String, LinkedHashMap<String, String>> getMap() {
		return map;
	}
	
	/**
	 * 根据类型，获取键值对集合，用于加载下拉列表
	 * @param type
	 * @return
	 */
	public static  Map<String,String> getDicByType(String type) {
		return map.get(type);
	}
	/**
	 * 根据类型和code获取对应值
	 * @param type
	 * @param code
	 * @return
	 */
	public static String getValue(String type,String code) {
		Map<String,String> typeMap =  map.get(type);
		if(null != typeMap && typeMap.size()>0) {
			return typeMap.get(code);
		}
		return null;
	}
	

}

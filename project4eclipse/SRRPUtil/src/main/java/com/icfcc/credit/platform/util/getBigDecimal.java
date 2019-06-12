package com.icfcc.credit.platform.util;

import java.math.BigDecimal;

/**
 * 获取财务报表金额类字段值
 * 
 * @author Min.Liu
 * @date 2017年8月29日
 */
public class getBigDecimal {
	/**
	 * <通过String类型获取BigDecimal类型的值>
	 * 
	 * @param val
	 * @return
	 * @author Min.Liu
	 * @return
	 * @date 2017年8月29日
	 */
	public static BigDecimal getBigDecimalByString(String val) {
		BigDecimal b = new BigDecimal(val);
		return b;
	}
	/**
	 * <通过Double类型数据获取BigDecimal类型的值>
	 * @param val
	 * @return
	 * @author Min.Liu
	 * @date 2017年8月29日
	 */
	public static BigDecimal getBigDecimalByDouble(Double val){
		BigDecimal valueOf = BigDecimal.valueOf(val);
		return valueOf;
	}
}

/**
 *  Copyright (c)  2011-2020 ICFCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of ICFCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with ICFCC.
 */
package com.icfcc.credit.check;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jerry.chen
 * @date 2017年7月8日 Desc: 统一社会信用代码证检验
 */
public class Regex_CreditCode {
	static String error_CreditCode = "社会信用代码有误";
	static String error_CreditCode_min = "社会信用代码不足18位，请核对后再输！";
	static String error_CreditCode_max = "社会信用代码大于18位，请核对后再输！";
	static String error_CreditCode_empty = "社会信用代码不能为空！";
	private static Map<String, Integer> datas = null;
	static int[] power = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24,
			10, 30, 28 };
	// 社会统一信用代码不含（I、O、S、V、Z） 等字母
	static char[] code = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
			'P', 'Q', 'R', 'T', 'U', 'W', 'X', 'Y' };
	static {
		datas = new HashMap<>();
		for (int i = 0; i < code.length; i++) {
			datas.put(code[i] + "", i);
		}
	}
	/**
	 * 判断是否是一个有效的社会信用代码
	 * 
	 * @param creditCode
	 * @return
	 */
	public static boolean isCreditCode(String creditCode) {
		
		if ("".equals(creditCode) || " ".equals(creditCode)) {
			return false;
		} else if (creditCode.length() < 18) {
			// System.out.println(error_CreditCode_min);
			return false;
		} else if (creditCode.length() > 18) {
			// System.out.println(error_CreditCode_max);
			return false;
		} else {
			char[] pre17s = pre17(creditCode);
			int sum = sum(pre17s);
			int temp = sum % 31;
			temp = temp == 0 ? 31 : temp;
			return creditCode.substring(17, 18).equals(code[31 - temp] + "");
		}
	}

	/**
	 * @param chars
	 * @return
	 */
	private static int sum(char[] chars) {
		int sum = 0;
		for (int i = 0; i < chars.length; i++) {
			int code = datas.get(chars[i] + "");
			sum += power[i] * code;
		}
		return sum;

	}

	/**
	 * 获取前17位字符
	 * 
	 * @param creditCode
	 */
	static char[] pre17(String creditCode) {
		String pre17 = creditCode.substring(0, 17);
		return pre17.toCharArray();
	}

	public static void main(String[] args) {
		
		String temp = "91320402251020508D";
		System.out.println(isCreditCode(temp));
	}
}

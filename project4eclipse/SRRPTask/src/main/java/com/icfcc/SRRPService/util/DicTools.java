/**
 * 
 */
package com.icfcc.SRRPService.util;

/**
 * @author lijj
 *
 */
public class DicTools {

	/**
	 * 从金额获取融资金额字典
	 * @param amount
	 * @return
	 */
	public static String getRZJEKey(Double amount) {
		if (amount == null) {
			return "0";
		}
		if (amount >= 0 && amount < 5000000) {
			return "0";
		}
		if (amount >= 5000000 && amount < 10000000) {
			return "1";
		}
		if (amount >= 10000000 && amount < 50000000) {
			return "2";
		}
		if (amount >= 50000000 && amount < 100000000) {
			return "3";
		}
		if (amount >= 100000000) {
			return "4";
		}
		return "0";
	}
	

}

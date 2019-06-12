package com.icfcc.util;


import java.math.BigDecimal;

/**
 * �жϷǿ�ֵ 
 * *��ֵ����ݣ�null��0��0�����壩�ж�Ϊ��ֵ 
 * *�ַ�����ݣ�null��""��ȫ�ո񣨿ո������壩�ж�Ϊ��ֵ
 * 
 * @author wy
 * 
 */
public class EmptyConditionUtils {

	/**
	 * �ж��Ƿ�ǿա�null��0��0������ʱ��Ϊ��
	 * 
	 * @param val
	 * @param zeromeaning
	 *            0�Ƿ�������
	 * @return
	 */
	public static boolean notEmptyLong(Long val, boolean zeromeaning) {
		boolean r = true;
		if (val == null) {
			r = false;
		} else {
			if ((!zeromeaning) && val == 0)
				r = false;
		}
		return r;
	}
	/**
	 * �ж��Ƿ�ǿա�null��0��0������ʱ��Ϊ��
	 * 
	 * @param val
	 * @param zeromeaning
	 *            0�Ƿ�������
	 * @return
	 */
	public static boolean notEmptyBigDecimal(BigDecimal val, boolean zeromeaning) {
		boolean r = true;
		if (val == null) {
			r = false;
		} else {
			if ((!zeromeaning) && val.intValue() == 0)
				r = false;
		}
		return r;
	}
	
	/**
	 * 0û����ʱ�ж��Ƿ�ǿ�
	 * 
	 * @param val
	 * @return
	 */
	public static boolean notEmptyLongZeroNoMeaning(Long val) {
		return notEmptyLong(val, false);
	}

	public static boolean notEmptyInt(Integer val, boolean zeromeaning) {
		if (val == null)
			return false;
		return notEmptyLong(val.longValue(), zeromeaning);
	}

	public static boolean notEmptyIntZeroNoMeaning(Integer val) {
		return notEmptyInt(val, false);
	}

	public static boolean notEmptyString(String val, boolean spacemeaning) {
		boolean r = true;
		if (val == null) {
			r = false;
		} else if (!spacemeaning) {
			val = val.trim();
		}
		if (r) {
			if (val.equals("")) {
				r = false;
			} else {
				r = true;
			}
		}
		return r;
	}

	public static boolean notEmptyStringSpaceNoMeaning(String val) {
		return notEmptyString(val, false);
	}
}

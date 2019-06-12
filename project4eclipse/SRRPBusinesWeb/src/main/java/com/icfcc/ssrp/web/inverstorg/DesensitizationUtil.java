package com.icfcc.ssrp.web.inverstorg;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 企业信息与需求信息的脱敏展示帮助类
 * 
 * @author Administrator
 *
 */
public class DesensitizationUtil {
	 private static final int SIZE =6;
	private static final String SYMBOL = "*";
	public static String AREA="苏州,常熟,张家港,昆山,太仓,吴江,江苏,姑苏,虎丘,吴中,相城";

	/**
	 * 其他的脱敏规则
	 * 
	 * 将需要脱敏的信息按照一定长度进行脱敏
	 * 
	 * @param value
	 * @param SIZE
	 * @return
	 */
	public static String toConceal(String value, int SIZE) {
		if (null == value || "".equals(value)) {
			return value;
		}
		int len = value.length();
		int pamaone = len / 2;
		int pamatwo = pamaone - 1;
		int pamathree = len % 2;
		StringBuilder stringBuilder = new StringBuilder();
		if (len <= 2) {
			if (pamathree == 1) {
				return SYMBOL;
			}
			stringBuilder.append(SYMBOL);
			stringBuilder.append(value.charAt(len - 1));
		} else {
			if (pamatwo <= 0) {
				stringBuilder.append(value.substring(0, 1));
				stringBuilder.append(SYMBOL);
				stringBuilder.append(value.substring(len - 1, len));

			} else if (pamatwo >= SIZE / 2 && SIZE + 1 != len) {
				int pamafive = (len - SIZE) / 2;
				stringBuilder.append(value.substring(0, pamafive));
				for (int i = 0; i < SIZE; i++) {
					stringBuilder.append(SYMBOL);
				}
				if ((pamathree == 0 && SIZE / 2 == 0)
						|| (pamathree != 0 && SIZE % 2 != 0)) {
					stringBuilder.append(value.substring(len - pamafive, len));
				} else {
					stringBuilder.append(value.substring((pamafive + SIZE),
							len));
				}
			} else {
				int pamafour = len - 2;
				stringBuilder.append(value.substring(0, 1));
				for (int i = 0; i < pamafour; i++) {
					stringBuilder.append(SYMBOL);
				}
				stringBuilder.append(value.substring(len - 1, len));
			}
		}
		return stringBuilder.toString();

	}

	/**
	 * 关于姓名的脱敏展示 保留姓氏其余的用*表示
	 * 
	 * @param fullName
	 * @return
	 */
	public static String chineseName(String fullName) {
		if (StringUtils.isBlank(fullName)) {
			return "";
		}
		String name = StringUtils.left(fullName, 1);
		return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
	}

	/**
	 * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
	 * 
	 * @param num
	 * @return
	 */
	public static String mobilePhone(String num) {
		if (StringUtils.isBlank(num)) {
			return "";
		}
		return StringUtils.left(num, 3).concat(
				StringUtils.removeStart(
						StringUtils.leftPad(StringUtils.right(num, 4),
								StringUtils.length(num), "*"), "***"));
	}

	/**
	 * [地址] 只显示到地区，不显示详细地址；我们要对个人信息增强保护<例子：北京市海淀区****>
	 * 所有地区保留至区县，如果没有区县保留到市，都没有保留前三个字符其余的详细地址省略
	 * 
	 * @param address
	 * @return
	 */
	public static String address(String address) {
		int sensitiveSize = address.lastIndexOf("区");
		if (sensitiveSize == -1) {
			sensitiveSize = address.lastIndexOf("市");
			if (sensitiveSize == -1) {
				sensitiveSize = address.lastIndexOf("县");
				if (sensitiveSize == -1) {
					sensitiveSize = 3;
				}
			}
		}
		sensitiveSize++;
		if (StringUtils.isBlank(address)) {
			return "";
		}
		int length = StringUtils.length(address);
		return StringUtils.rightPad(StringUtils.left(address, sensitiveSize),
				length, "*");
	}

	/**
	 * [企业名称] 只显示到地区，不显示具体的企业名称内容；
	 * 
	 * 企业名称中带有区域的保留到区域，没有包含区域的保留前两个字，企业名称尾部保留四个字符
	 * 
	 * @param name
	 * @return
	 */
	public static String enterpriseName(String name) {
		if (StringUtils.isBlank(name)) {
			return "";
		}
		if(name.length()>6){
		return StrName(name);
		}else{
			return toConceal(name,name.length()/2);
		}
	}

	/**
	 * [全部替换] 将数据中的所有字符替换成*
	 * 
	 * 证件代码的脱敏
	 * 
	 * @param value
	 * @return
	 */
	public static String changeAll(String value) {
		if (StringUtils.isBlank(value)) {
			return "";
		}
		int length = StringUtils.length(value);
		return StringUtils.rightPad(StringUtils.left(value, 0), length, "*");
	}

	/**
	 * [全部替换] 将数据中的所有字符替换成*
	 * 
	 * 证件代码的脱敏
	 * 
	 * @param value
	 * @return
	 */
	public static String changeAllObjection(String value) {
		if (StringUtils.isBlank(value)) {
			return "";
		}
		int length = StringUtils.length(value);
		if(length>=15){
			return "***************";
		}else{
			return StringUtils.rightPad(StringUtils.left(value, 0), length, "*");
		}
	}
	/**
	 * [局部替换] 某一个字段（A）包含另一个字段（B），用*表示该字段
	 * 
	 * 成员资历，以及融资需求
	 * 
	 * @param source
	 *            B字段的值
	 * @param value
	 *            A字段的值
	 * @return
	 */
	public static String changeSome(String value, String source) {
		if (StringUtils.isBlank(source)) {
			return "";
		}
		if (StringUtils.isBlank(value)) {
			return "";
		}
		String change = changeAll(source);
		value = value.replaceAll(source, change);
		return value;
	}

	/**
	 * [融资需求的名称] 项目名称中的企业名称进行脱敏展示
	 * 
	 * 成员资历，以及融资需求
	 * 
	 * @param source
	 * @param value
	 * @return
	 */
	public static String changeProjectName(String value, String source) {
		if (StringUtils.isBlank(source)) {
			return "";
		}
		if (StringUtils.isBlank(value)) {
			return "";
		}
		String change = enterpriseName(source);
		value = value.replaceAll(source, "某公司");
		return value;
	}
	public static String StrName(String name) {
			String LeftStr=StringUtils.left(name,2);
			String RightStr=StringUtils.right(name,4);
			String StripEndStr=StringUtils.stripEnd(name, RightStr);
			StringBuilder stringBuilder = new StringBuilder();
			if(AREA.contains(LeftStr)){
				if(StringUtils.left(name,3).equals("张家港")){
					LeftStr=StringUtils.left(name,3);
				}
				stringBuilder.append(StringUtils.rightPad(LeftStr,
						StripEndStr.length()-LeftStr.length(), "*"));
			}else{
				int centerIndex=StringUtils.length(StripEndStr);
				int mingwen=0;
				if(centerIndex<6){
					mingwen=1;
				}else if(centerIndex>5&&centerIndex<8){
					mingwen=2;
				}else if(centerIndex>8&&centerIndex<12){
					mingwen=3;
				}else{
					mingwen=4;
				}
				stringBuilder.append(StringUtils.center(StringUtils.mid(StripEndStr, centerIndex/2-1,mingwen),
						centerIndex, "*"));
			}
			if("有限公司".equals(RightStr)){
				
				stringBuilder.append(RightStr);
			}else{
				stringBuilder.append(StringUtils.leftPad(StringUtils.right(RightStr, 1),
						RightStr.length(), "*"));
			}
			return stringBuilder.toString();
	}
	public static void main(String[] args) {
		String name="张家港市";
		System.out.println(toConceal(name,name.length()/2));
		
		int length = StringUtils.length(name);
		if(length>6){
			String LeftStr=StringUtils.left(name,2);
			String RightStr=StringUtils.right(name,4);
			String StripEndStr=StringUtils.stripEnd(name, RightStr);
			StringBuilder stringBuilder = new StringBuilder();
			if(AREA.contains(LeftStr)){
				if(StringUtils.left(name,3).equals("张家港")){
					LeftStr=StringUtils.left(name,3);
				}
				stringBuilder.append(StringUtils.rightPad(LeftStr,
						StripEndStr.length()-LeftStr.length(), "*"));
			}else{
				int centerIndex=StringUtils.length(StripEndStr);
				int mingwen=0;
				if(centerIndex<6){
					mingwen=1;
				}else if(centerIndex>5&&centerIndex<8){
					mingwen=2;
				}else if(centerIndex>8&&centerIndex<12){
					mingwen=3;
				}else{
					mingwen=4;
				}
				stringBuilder.append(StringUtils.center(StringUtils.mid(StripEndStr, centerIndex/2-1,mingwen),
						centerIndex, "*"));
			}
			if("有限公司".equals(RightStr)){
				
				stringBuilder.append(RightStr);
			}else{
				stringBuilder.append(StringUtils.leftPad(StringUtils.right(RightStr, 1),
						RightStr.length(), "*"));
			}
			
			System.out.println(stringBuilder.toString());
			
			

		}

	}
}

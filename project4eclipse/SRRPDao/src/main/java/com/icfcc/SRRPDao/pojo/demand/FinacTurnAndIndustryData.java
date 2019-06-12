package com.icfcc.SRRPDao.pojo.demand;

import com.icfcc.ssrp.util.DigitFormatUtil;

public class FinacTurnAndIndustryData {

	// 需求数量
	private String pushCount;

	// 融资金额
	private String finacMoney;

	// 統計維度 行業/輪次/月度
	private String x_key;

	// 融资金额值
	private String y_value;

	// 解决需求数
	private String z_value;

	public String getPushCount() {
		if (pushCount == null || "".equals(pushCount)) {
			return "0";
		} else {
			return DigitFormatUtil.digitFormat(pushCount).toString();
		}
	}

	public void setPushCount(String pushCount) {
		this.pushCount = pushCount;
	}

	public String getFinacMoney() {
		if (finacMoney == null || "".equals(finacMoney)) {
			return "0";
		} else {
			return DigitFormatUtil.digitFormat(finacMoney).toString();
		}
	}

	public void setFinacMoney(String finacMoney) {
		this.finacMoney = finacMoney;
	}

	public String getX_key() {
		return x_key;
	}

	public void setX_key(String x_key) {
		this.x_key = x_key;
	}

	public String getY_value() {
		return y_value;
	}

	public void setY_value(String y_value) {
		this.y_value = y_value;
	}

	public String getZ_value() {
		return z_value;
	}

	public void setZ_value(String z_value) {
		this.z_value = z_value;
	}
}

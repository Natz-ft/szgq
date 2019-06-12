package com.icfcc.SRRPDao.pojo.demand;

import java.util.List;

import com.icfcc.ssrp.util.DigitFormatUtil;

public class AreaData {

	// 需求数量
	private String pushCount;

	// 融资金额
	private String finacMoney;

	// 各地区情况
	private List<?> data;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public String getPushCount() {
		if(pushCount == null ||"".equals(pushCount)){
			return "0";
		}else{
			return DigitFormatUtil.digitFormat(pushCount).toString() ;
		}
	}

	public void setPushCount(String pushCount) {
		this.pushCount = pushCount;
	}

	public String getFinacMoney() {
		if(finacMoney == null ||"".equals(finacMoney)){
			return "0";
		}else{
			return DigitFormatUtil.digitFormat(finacMoney).toString() ;
		}
		
	}

	public void setFinacMoney(String finacMoney) {
		this.finacMoney = finacMoney;
	}

}

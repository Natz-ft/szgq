package com.icfcc.SRRPDao.pojo;

import com.icfcc.SRRPDao.pojo.demand.AreaData;
import com.icfcc.SRRPDao.pojo.demand.FinacTurnAndIndustryData;

public class StaticResultDeamd {

	// 按地区统计融资需求数与融资金额
	private AreaData areaData;

	// 按融资轮次统计融资需求数与融资金额
	private FinacTurnAndIndustryData finacTurnData;

	// 按融资行业统计融资需求数与融资金额
	private FinacTurnAndIndustryData industryData;

	// 按融资行业统计融资需求数与融资金额
	private FinacTurnAndIndustryData monthlyData;
	
	 public String finacTotalDate;//统计融资轮次统计截至时间
	 public String areaTotalDate;//统计区域统计截至时间
	 public String indusryTotalDate;//统计区域统计截至时间
	 public String monthlyTotalDate;//统计区域统计截至时间

	public AreaData getAreaData() {
		return areaData;
	}

	public void setAreaData(AreaData areaData) {
		this.areaData = areaData;
	}

	public FinacTurnAndIndustryData getFinacTurnData() {
		return finacTurnData;
	}

	public void setFinacTurnData(FinacTurnAndIndustryData finacTurnData) {
		this.finacTurnData = finacTurnData;
	}

	public FinacTurnAndIndustryData getIndustryData() {
		return industryData;
	}

	public void setIndustryData(FinacTurnAndIndustryData industryData) {
		this.industryData = industryData;
	}

	public FinacTurnAndIndustryData getMonthlyData() {
		return monthlyData;
	}

	public void setMonthlyData(FinacTurnAndIndustryData monthlyData) {
		this.monthlyData = monthlyData;
	}

	public String getFinacTotalDate() {
		return finacTotalDate;
	}

	public void setFinacTotalDate(String finacTotalDate) {
		this.finacTotalDate = finacTotalDate;
	}

	public String getAreaTotalDate() {
		return areaTotalDate;
	}

	public void setAreaTotalDate(String areaTotalDate) {
		this.areaTotalDate = areaTotalDate;
	}

	public String getIndusryTotalDate() {
		return indusryTotalDate;
	}

	public void setIndusryTotalDate(String indusryTotalDate) {
		this.indusryTotalDate = indusryTotalDate;
	}

	public String getMonthlyTotalDate() {
		return monthlyTotalDate;
	}

	public void setMonthlyTotalDate(String monthlyTotalDate) {
		this.monthlyTotalDate = monthlyTotalDate;
	}


}

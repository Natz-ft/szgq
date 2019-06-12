package com.icfcc.SRRPDao.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 企业投资实时报表对象
 * 
 *
 */
public class CountReportAreaBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final DecimalFormat formatter = new DecimalFormat("#.##");

	private BigDecimal post_finance;
	private BigDecimal solve_finance;
	private BigDecimal company_count;
	private String area;// 地区
	private BigDecimal amount;// 统计总金额
	private Integer rank;
	private BigDecimal enterprise_count;
	
	public BigDecimal getEnterprise_count() {
		return enterprise_count;
	}
	public void setEnterprise_count(BigDecimal enterprise_count) {
		this.enterprise_count = enterprise_count;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public BigDecimal getPost_finance() {
		return post_finance;
	}
	public void setPost_finance(BigDecimal post_finance) {
		this.post_finance = post_finance;
	}
	public BigDecimal getSolve_finance() {
		return solve_finance;
	}
	public void setSolve_finance(BigDecimal solve_finance) {
		this.solve_finance = solve_finance;
	}
	public BigDecimal getCompany_count() {
		return company_count;
	}
	public void setCompany_count(BigDecimal company_count) {
		this.company_count = company_count;
	}
	public BigDecimal getOrg_count() {
		return org_count;
	}
	public void setOrg_count(BigDecimal org_count) {
		this.org_count = org_count;
	}
	private BigDecimal org_count;
	
	
}

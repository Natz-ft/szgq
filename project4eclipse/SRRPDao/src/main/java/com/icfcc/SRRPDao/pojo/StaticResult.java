package com.icfcc.SRRPDao.pojo;

import java.util.List;

import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayStaticResult;

public class StaticResult {

	// 发布融资总额
	private String post;

	// 解决融资总额
	private String solve;

	// 正在融资总额
	private String todo;

	// 企业数
	private String company;

	// 机构树
	private String org;
	// 机构树
	private String otherOrg;
	// 机构用户数
	private String orguser;
//截至统计时间
	private String endTotalDate;
	// 月度融资统计
	private String count;

	
	// 各地区数据数据
	private List<?> data;

	// 统计结果汇总
	private List<?> staticResult;

	// 企业榜单
	private List<?> companyRank;

	// 机构榜单
	private List<?> investorRank;

	// 区域榜单
	private List<?> areaRank;

	// 行业榜单
	private List<?> industryRank;

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getSolve() {
		return solve;
	}

	public void setSolve(String solve) {
		this.solve = solve;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public List<?> getCompanyRank() {
		return companyRank;
	}

	public void setCompanyRank(List<?> companyRank) {
		this.companyRank = companyRank;
	}

	public List<?> getInvestorRank() {
		return investorRank;
	}

	public String getEndTotalDate() {
		return endTotalDate;
	}

	public void setEndTotalDate(String endTotalDate) {
		this.endTotalDate = endTotalDate;
	}

	public void setInvestorRank(List<?> investorRank) {
		this.investorRank = investorRank;
	}

	public List<?> getAreaRank() {
		return areaRank;
	}

	public void setAreaRank(List<?> areaRank) {
		this.areaRank = areaRank;
	}

	public List<?> getIndustryRank() {
		return industryRank;
	}

	public void setIndustryRank(List<?> industryRank) {
		this.industryRank = industryRank;
	}

	public List<?> getStaticResult() {
		return staticResult;
	}

	public void setStaticResult(List<?> staticResult) {
		this.staticResult = staticResult;
	}

	public String getOtherOrg() {
		return otherOrg;
	}

	public void setOtherOrg(String otherOrg) {
		this.otherOrg = otherOrg;
	}

	public String getOrguser() {
		return orguser;
	}

	public void setOrguser(String orguser) {
		this.orguser = orguser;
	}


	
}

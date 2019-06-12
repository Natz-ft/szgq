package com.icfcc.ssrp.session;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;




import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;


/**
 * 管理业绩实体类
 * 
 * @author Administrator
 */

public class InvestorManageAchievement implements Serializable {

	private static final long serialVersionUID = 34610624223264977L;

	
	private String manageId;// 管理业绩id

	private String investId;// 投资机构id

	private String fundName;// 管理基金名称

	private Date fundRegistDate;// 注册时间

	private String registAddress;// 注册地址
	private String trusteeship;// 托管机构
	private Double manageCapitalMin;// 管理资金最小规模(万元)

	private Double manageCapitalMax;// 管理资金最大规模(万元)

	private String iccFilingNo;// 中基协备案号

	private String fundType;// 基金类型

	private String investTrade;// 投资行业定位

	private String investStage;// 投资阶段定位

	private Double investmentProjects;// 投资项目数量

	private Double cumulativeInvestment;// 累计投资金额

	private Double implementExitProject;// 实现退出项目数

	private String registDateString;// 展示注册时间字符串的字段
	private String curn1;// 拟投资行业字符串
	private String curn2;// 拟投资行业字符串
	
	private String manageCapital;// 管理资金拼接的字符串
	private String foundTypeDicname;// 资本类型字符串
	private String financeStageDicname;// 拟投资阶段字符串
	private String financeTradeDicname;// 拟投资行业字符串
	private String financeStageDicCode;// 拟投资阶段字符串
	private String financeTradeDicCode;// 拟投资行业字符串
	private String foundTypeDicCode;// 拟投资行业字符串
	public String getManageId() {
		return manageId;
	}
	public void setManageId(String manageId) {
		this.manageId = manageId;
	}
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public Date getFundRegistDate() {
		return fundRegistDate;
	}
	public void setFundRegistDate(Date fundRegistDate) {
		this.fundRegistDate = fundRegistDate;
	}
	public String getRegistAddress() {
		return registAddress;
	}
	public void setRegistAddress(String registAddress) {
		this.registAddress = registAddress;
	}
	public String getTrusteeship() {
		return trusteeship;
	}
	public void setTrusteeship(String trusteeship) {
		this.trusteeship = trusteeship;
	}
	public Double getManageCapitalMin() {
		return manageCapitalMin;
	}
	public void setManageCapitalMin(Double manageCapitalMin) {
		this.manageCapitalMin = manageCapitalMin;
	}
	public Double getManageCapitalMax() {
		return manageCapitalMax;
	}
	public void setManageCapitalMax(Double manageCapitalMax) {
		this.manageCapitalMax = manageCapitalMax;
	}
	public String getIccFilingNo() {
		return iccFilingNo;
	}
	public void setIccFilingNo(String iccFilingNo) {
		this.iccFilingNo = iccFilingNo;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public String getInvestTrade() {
		return investTrade;
	}
	public void setInvestTrade(String investTrade) {
		this.investTrade = investTrade;
	}
	public String getInvestStage() {
		return investStage;
	}
	public void setInvestStage(String investStage) {
		this.investStage = investStage;
	}
	public Double getInvestmentProjects() {
		return investmentProjects;
	}
	public void setInvestmentProjects(Double investmentProjects) {
		this.investmentProjects = investmentProjects;
	}
	public Double getCumulativeInvestment() {
		return cumulativeInvestment;
	}
	public void setCumulativeInvestment(Double cumulativeInvestment) {
		this.cumulativeInvestment = cumulativeInvestment;
	}
	public Double getImplementExitProject() {
		return implementExitProject;
	}
	public void setImplementExitProject(Double implementExitProject) {
		this.implementExitProject = implementExitProject;
	}
	public String getRegistDateString() {
		return registDateString;
	}
	public void setRegistDateString(String registDateString) {
		this.registDateString = registDateString;
	}
	public String getManageCapital() {
		return manageCapital;
	}
	public void setManageCapital(String manageCapital) {
		this.manageCapital = manageCapital;
	}
	public String getFoundTypeDicname() {
		return foundTypeDicname;
	}
	public void setFoundTypeDicname(String foundTypeDicname) {
		this.foundTypeDicname = foundTypeDicname;
	}
	public String getFinanceStageDicname() {
		return financeStageDicname;
	}
	public void setFinanceStageDicname(String financeStageDicname) {
		this.financeStageDicname = financeStageDicname;
	}
	public String getFinanceTradeDicname() {
		return financeTradeDicname;
	}
	public void setFinanceTradeDicname(String financeTradeDicname) {
		this.financeTradeDicname = financeTradeDicname;
	}
	public String getFinanceStageDicCode() {
		return financeStageDicCode;
	}
	public void setFinanceStageDicCode(String financeStageDicCode) {
		this.financeStageDicCode = financeStageDicCode;
	}
	public String getFinanceTradeDicCode() {
		return financeTradeDicCode;
	}
	public void setFinanceTradeDicCode(String financeTradeDicCode) {
		this.financeTradeDicCode = financeTradeDicCode;
	}
	public String getFoundTypeDicCode() {
		return foundTypeDicCode;
	}
	public void setFoundTypeDicCode(String foundTypeDicCode) {
		this.foundTypeDicCode = foundTypeDicCode;
	}
	public String getCurn1() {
		return curn1;
	}
	public void setCurn1(String curn1) {
		this.curn1 = curn1;
	}
	public String getCurn2() {
		return curn2;
	}
	public void setCurn2(String curn2) {
		this.curn2 = curn2;
	}
	
	

}

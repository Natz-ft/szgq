package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.constants.VipCont;

import lombok.Data;

/**
 * 管理业绩实体类
 * 
 * @author Administrator
 */
@Data
@Entity
@Table(name = "rp_investor_manage_achievement")
public class InvestorManageAchievement implements Serializable {

	private static final long serialVersionUID = 34610624223264977L;

	@Id
	@Column(name = "manage_id", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String manageId;// 管理业绩id

	@Column(name = "invest_id")
	private String investId;// 投资机构id

	@Column(name = "fund_name")
	private String fundName;// 管理基金名称

	@Column(name = "regist_date")
	private Date fundRegistDate;// 注册时间

	@Column(name = "regist_address")
	private String registAddress;// 注册地址
	@Column(name = "trusteeship")
	private String trusteeship;// 托管机构
	@Column(name = "manage_capital_min")
	private Double manageCapitalMin;// 管理资金最小规模(万元)

	@Column(name = "manage_capital_max")
	private Double manageCapitalMax;// 管理资金最大规模(万元)

	@Column(name = "currency")
	private String currency;// 管理资金规模币种

	@Column(name = "icc_filing_no")
	private String iccFilingNo;// 中基协备案号

	@Column(name = "fund_type")
	private String fundType;// 基金类型

	@Column(name = "invest_trade")
	private String investTrade;// 投资行业定位

	@Column(name = "invest_stage")
	private String investStage;// 投资阶段定位

	@Column(name = "investment_projects")
	private Double investmentProjects;// 投资项目数量

	@Column(name = "cumulative_investment")
	private Double cumulativeInvestment;// 累计投资金额

	@Column(name = "ci_currency")
	private String ciCurrency;// 累计投资金额币种

	@Column(name = "implement_exit_project")
	private Double implementExitProject;// 实现退出项目数

	@Transient
	private String registDateString; // 注册时间字符串

	@Transient
	private String registAddressStr;// 注册地字符串
	@Transient
	private String code;// 注册地字符串
	@Transient
	private String industryVo;
	
	public String getIndustryVo() {
		return industryVo;
	}

	public void setIndustryVo(String industryVo) {
		this.industryVo = industryVo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public void setRegistDateString(String registDateString) {
		this.registDateString = registDateString;
	}

	public String getManageId() {
		return manageId;
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

	public String getInvestTrade() {
		return investTrade;
	}

	public void setInvestTrade(String investTrade) {
		this.investTrade = investTrade;
	}

	public String getRegistDateString() {
		String str = null;
		if (fundRegistDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			str = sdf.format(fundRegistDate);
		}
		return str;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCiCurrency() {
		return ciCurrency;
	}

	public void setCiCurrency(String ciCurrency) {
		this.ciCurrency = ciCurrency;
	}

	public static void main(String arg[]) {
		String password = "12345678";
		for (int i = 0; i < password.length(); i++) {
			System.out.println(String.valueOf((int) password.charAt(i)));
		}
	}

	public String getRegistAddressStr() {
		if (this.registAddress != null) {
			registAddressStr = VipCont.getValueByCode("dd:area", this.registAddress);
		}
		return registAddressStr;
	}

	public void setRegistAddressStr(String registAddressStr) {
		this.registAddressStr = registAddressStr;
	}
	public String toJSONStr() {
		String result = JSON.toJSONString(this);
		return result;
	}
}

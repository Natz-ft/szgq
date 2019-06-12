package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

import lombok.Data;

@Data
@Entity
@Table(name = "rp_investor_achievement_pending")
public class InvestorAchievementPending implements Serializable {

	private static final long serialVersionUID = -2567202918695152532L;

	@Id
	@Column(name = "achievement_id", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String achievementId;// 投资业绩id

	@Column(name = "invest_id")
	private String investId;// 投资机构id

	@Column(name = "investment_funds")
	private String investmentFunds;// 投资基金名称

	@Column(name = "invested_enterprise")
	private String investedEnterprise;// 被投资企业名称

	@Column(name = "invested_enterprise_industry")
	private String investedEnterpriseIndustry;// 被投资企业行业

	@Column(name = "invested_enterprise_area")
	private String investedEnterpriseArea;// 被投资企业所属区域

	@Column(name = "investment_time")
	private Date investmentTime;// 投资时间

	@Column(name = "investment_Amount")
	private Double investmentAmount;// 投资金额（万元）

	@Column(name = "ia_currency")
	private String iaCurrency;// 投资金额币种

	@Column(name = "share_ratio")
	private Double shareRatio;// 股权比例

	@Column(name = "round_of_investment")
	private String roundOfInvestment;// 投资伦次

	@Column(name = "investment_stage")
	private String investmentStage;// 投资阶段

	@Column(name = "enterprise_capital")
	private Double enterpriseCapital;// 被投资企业注册资本

	@Column(name = "ec_currency")
	private String ecCurrency;// 被投企业注册资本币种

	@Column(name = "exit_time")
	private Date exitTime;// 退出时间

	@Column(name = "sign_out")
	private String signOut;// 是否已退出

	@Column(name = "rate_of_return")
	private Double rateOfReturn;// 回报率

	@Column(name = "corepersonnel")
	private String corepersonnel;// 综合实力、经营业绩、突出优势、核心人员

	@Transient
	private String exitTimeStr;// 退出时间字符创

	@Transient
	private String investmentAmountString;// 投资金额展示字符串

	@Transient
	private String investmentTimeStr;// 投资时间字符创

	@Transient
	private String investedEnterpriseIndustryStr;// 已投企业行业

	@Transient
	private String roundOfInvestmentStr;// 阶段

	@Transient
	private String investmentStageStr;// 轮次

	@Transient
	private String investedEnterpriseAreaStr;// 区域

	@Transient
	private String investedEnterpriseIndustryCode;// 已投企业行业

	@Transient
	private String roundOfInvestmentCode;// 阶段

	@Transient
	private String investmentStageCode;// 轮次

	@Transient
	private String investedEnterpriseAreaCode;// 区域

	@Transient
	private String iaCurrencyDicname;// 投资金额币种字典值

	@Transient
	private String iaCurrencyStr;// 投资金额币种字符串

	@Transient
	private String ecCurrencyDicname;// 被投资企业注册资本币种字典值

	@Transient
	private String ecCurrencyStr;// 被投资企业注册资本币种字符串
	@Transient
	private String iaCurrencyCode;// 投资金额币种字符串

	@Transient
	private String ecCurrencyCode;// 被投资企业注册资本币种字典值

	@Transient
	private String signOutStr;// 是否已退出字符串
	@Transient
	private String signOutCode;// 是否已退出字符串
	@Transient
	private String rateOfReturnStr;// 回报率字符串

	@Transient
	private String industry2;// 二级行业

	@Transient
	private String industry1;// 二级行业

	public String getIndustry1() {
		return industry1;
	}

	public void setIndustry1(String industry1) {
		this.industry1 = industry1;
	}

	public String getInvestedEnterpriseIndustryCode() {
		return investedEnterpriseIndustryCode;
	}

	public void setInvestedEnterpriseIndustryCode(
			String investedEnterpriseIndustryCode) {
		if (investedEnterpriseIndustryCode != null
				&& !"".equals(investedEnterpriseIndustryCode)) {
			this.investedEnterpriseIndustry = RedisGetValue.getCodeByValue(
					SRRPConstant.DD_INDUSTRY, investedEnterpriseIndustryCode);
		}
	}

	public String getRoundOfInvestmentCode() {
		return roundOfInvestmentCode;
	}

	public void setRoundOfInvestmentCode(String roundOfInvestmentCode) {
		if (roundOfInvestmentCode != null && !"".equals(roundOfInvestmentCode)) {
			this.roundOfInvestment = RedisGetValue.getCodeByValue(
					SRRPConstant.DD_FINACINGTURN, roundOfInvestmentCode);
		}
	}

	public String getInvestmentStageCode() {
		return investmentStageCode;
	}

	public void setInvestmentStageCode(String investmentStageCode) {
		if (investmentStageCode != null && !"".equals(investmentStageCode)) {
			this.investmentStage = RedisGetValue.getCodeByValue(
					SRRPConstant.DD_INVESTMENT, investmentStageCode);
		}
	}

	public String getInvestedEnterpriseAreaCode() {
		return investedEnterpriseAreaCode;
	}

	public void setInvestedEnterpriseAreaCode(String investedEnterpriseAreaCode) {
		if (investedEnterpriseAreaCode != null
				&& !"".equals(investedEnterpriseAreaCode)) {
			this.investedEnterpriseArea = RedisGetValue.getCodeByValueArea(
					SRRPConstant.DD_AREA, investedEnterpriseAreaCode);
		}
	}

	public String getAchievementId() {
		return achievementId;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	public void setInvestmentAmount(Double investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public Double getInvestmentAmount() {
		return investmentAmount;
	}

	public String getCorepersonnel() {
		return corepersonnel;
	}

	public void setCorepersonnel(String corepersonnel) {
		this.corepersonnel = corepersonnel;
	}

	public String getInvestmentAmountString() {
		if (this.investmentAmount != null) {
			return DigitFormatUtil.digitFormat(this.investmentAmount)
					.toString();
		} else {
			return null;
		}
	}

	public void setInvestmentAmountString(String investmentAmountString) {
		this.investmentAmountString = investmentAmountString;
	}

	public String getInvestmentFunds() {
		return investmentFunds;
	}

	public void setInvestmentFunds(String investmentFunds) {
		this.investmentFunds = investmentFunds;
	}

	public String getInvestedEnterprise() {
		return investedEnterprise;
	}

	public void setInvestedEnterprise(String investedEnterprise) {
		this.investedEnterprise = investedEnterprise;
	}

	public String getInvestedEnterpriseIndustry() {

		return investedEnterpriseIndustry;
	}

	public String getInvestedEnterpriseIndustryStr() {
		if (investedEnterpriseIndustry != null) {
			if (investedEnterpriseIndustry.length() > 2) {
				investedEnterpriseIndustryStr = RedisGetValue.getValueByCode(
						SRRPConstant.DD_INDUSTRY_2, investedEnterpriseIndustry);
			} else {
				investedEnterpriseIndustryStr = RedisGetValue.getValueByCode(
						SRRPConstant.DD_INDUSTRY, investedEnterpriseIndustry);
			}

		}

		return investedEnterpriseIndustryStr;
	}

	public void setInvestedEnterpriseIndustryStr(
			String investedEnterpriseIndustryStr) {
		this.investedEnterpriseIndustryStr = investedEnterpriseIndustryStr;
	}

	public String getRoundOfInvestmentStr() {
		if (roundOfInvestment != null) {
			roundOfInvestmentStr = RedisGetValue.getValueByCode(
					SRRPConstant.DD_FINACINGTURN, roundOfInvestment);
		}
		return roundOfInvestmentStr;
	}

	public void setRoundOfInvestmentStr(String roundOfInvestmentStr) {
		this.roundOfInvestmentStr = roundOfInvestmentStr;
	}

	public String getInvestmentStageStr() {
		if (investmentStage != null) {
			investmentStageStr = RedisGetValue.getValueByCode(
					SRRPConstant.DD_INVESTMENT, investmentStage);
		}
		return investmentStageStr;
	}

	public void setInvestmentStageStr(String investmentStageStr) {
		this.investmentStageStr = investmentStageStr;
	}

	public String getInvestedEnterpriseAreaStr() {
		if (investedEnterpriseArea != null) {
			investedEnterpriseAreaStr = RedisGetValue.getValueByCode(
					SRRPConstant.DD_AREA, investedEnterpriseArea);
		}
		return investedEnterpriseAreaStr;
	}

	public void setInvestedEnterpriseAreaStr(String investedEnterpriseAreaStr) {
		this.investedEnterpriseAreaStr = investedEnterpriseAreaStr;
	}

	public void setInvestedEnterpriseIndustry(String investedEnterpriseIndustry) {
		this.investedEnterpriseIndustry = investedEnterpriseIndustry;
	}

	public String getInvestedEnterpriseArea() {
		return investedEnterpriseArea;
	}

	public void setInvestedEnterpriseArea(String investedEnterpriseArea) {
		this.investedEnterpriseArea = investedEnterpriseArea;
	}

	public Date getInvestmentTime() {
		return investmentTime;
	}

	public void setInvestmentTime(Date investmentTime) {
		this.investmentTime = investmentTime;
	}

	public Double getShareRatio() {
		return shareRatio;
	}

	public void setShareRatio(Double shareRatio) {
		this.shareRatio = shareRatio;
	}

	public String getRoundOfInvestment() {
		return roundOfInvestment;
	}

	public void setRoundOfInvestment(String roundOfInvestment) {
		this.roundOfInvestment = roundOfInvestment;
	}

	public String getInvestmentStage() {
		return investmentStage;
	}

	public void setInvestmentStage(String investmentStage) {
		this.investmentStage = investmentStage;
	}

	public Double getEnterpriseCapital() {
		return enterpriseCapital;
	}

	public void setEnterpriseCapital(Double enterpriseCapital) {
		this.enterpriseCapital = enterpriseCapital;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public Double getRateOfReturn() {
		return rateOfReturn;
	}

	public void setRateOfReturn(Double rateOfReturn) {
		this.rateOfReturn = rateOfReturn;
	}

	public String getInvestmentTimeStr() {

		if (investmentTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			investmentTimeStr = sdf.format(investmentTime);
		}
		return investmentTimeStr;
	}

	public void setInvestmentTimeStr(String investmentTimeStr) {
		this.investmentTimeStr = investmentTimeStr;
	}

	public String getExitTimeStr() {
		if (exitTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			exitTimeStr = sdf.format(exitTime);
		}
		return exitTimeStr;
	}

	public void setExitTimeStr(String exitTimeStr) {
		this.exitTimeStr = exitTimeStr;
	}

	public String getIaCurrency() {
		return iaCurrency;
	}

	public void setIaCurrency(String iaCurrency) {
		this.iaCurrency = iaCurrency;
	}

	public String getEcCurrency() {
		return ecCurrency;
	}

	public void setEcCurrency(String ecCurrency) {
		this.ecCurrency = ecCurrency;
	}

	public String getIaCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY,
				this.iaCurrency);
	}

	public void setIaCurrencyDicname(String iaCurrencyDicname) {
		this.iaCurrencyDicname = iaCurrencyDicname;
	}

	public String getEcCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY,
				this.ecCurrency);
	}

	public void setEcCurrencyDicname(String ecCurrencyDicname) {
		this.ecCurrencyDicname = ecCurrencyDicname;
	}

	public String getIaCurrencyStr() {
		this.iaCurrencyStr = "";
		if (this.investmentAmount == 0) {
			iaCurrencyStr = "0";
		} else {
			iaCurrencyStr = DigitFormatUtil.digitFormat(this.investmentAmount)
					.toString() + " " + this.getIaCurrencyDicname();
		}
		return iaCurrencyStr;
	}

	public void setIaCurrencyStr(String iaCurrencyStr) {
		this.iaCurrencyStr = iaCurrencyStr;
	}

	public String getEcCurrencyStr() {
		this.ecCurrencyStr = "";
		if (this.enterpriseCapital == 0) {
			ecCurrencyStr = "0";
		} else {
			ecCurrencyStr = DigitFormatUtil.digitFormat(this.enterpriseCapital)
					.toString() + " " + this.getEcCurrencyDicname();
		}
		return ecCurrencyStr;
	}

	public void setEcCurrencyStr(String ecCurrencyStr) {

		this.ecCurrencyStr = ecCurrencyStr;
	}

	public String getIaCurrencyCode() {
		return iaCurrencyCode;
	}

	public void setIaCurrencyCode(String iaCurrencyCode) {
		String Trade = "";
		if (iaCurrencyCode != null && !"".equals(iaCurrencyCode)) {
			if ("人民币".equals(iaCurrencyCode)) {
				iaCurrencyCode = "CNY";
			} else if ("美元".equals(iaCurrencyCode)) {
				iaCurrencyCode = "USD";
			}
			Trade = RedisGetValue.getCodeByValue(SRRPConstant.DD_CURRENCY,
					iaCurrencyCode);
			this.iaCurrency = Trade;
		}
	}

	public String getEcCurrencyCode() {
		return ecCurrencyCode;
	}

	public void setEcCurrencyCode(String ecCurrencyCode) {
		String Trade = "";
		if (ecCurrencyCode != null && !"".equals(ecCurrencyCode)) {
			if ("人民币".equals(ecCurrencyCode)) {
				ecCurrencyCode = "CNY";
			} else if ("美元".equals(ecCurrencyCode)) {
				ecCurrencyCode = "USD";
			}
			Trade = RedisGetValue.getCodeByValue(SRRPConstant.DD_CURRENCY,
					ecCurrencyCode);
			this.ecCurrency = Trade;
		}
	}

	public String getIndustry2() {
		return industry2;
	}

	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}

	public String getSignOut() {
		return signOut;
	}

	public void setSignOut(String signOut) {
		this.signOut = signOut;
	}

	public String getSignOutStr() {
		if ("0".equals(signOut)) {
			signOutStr = "是";
		} else {
			signOutStr = "否";
		}
		return this.signOutStr;
	}

	public void setSignOutStr(String signOutStr) {
		this.signOutStr = signOutStr;
	}

	public String getRateOfReturnStr() {
		if (this.rateOfReturn != null) {
			DecimalFormat df = new DecimalFormat("###.##");
			rateOfReturnStr = df.format(rateOfReturn);
			if (this.rateOfReturn == 0) {
				rateOfReturnStr = "";
			}
		} else {
			rateOfReturnStr = "";
		}
		return rateOfReturnStr;
	}

	public void setRateOfReturnStr(String rateOfReturnStr) {
		this.rateOfReturnStr = rateOfReturnStr;
	}

	public String getSignOutCode() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO,
				this.signOut);
	}

	public void setSignOutCode(String signOutCode) {
		this.signOutCode = signOutCode;
	}
	 public String toString(){
		   String str=getInvestmentFunds()+getInvestedEnterprise()+getInvestedEnterpriseIndustry()+getInvestedEnterpriseArea()+
				   getInvestmentTimeStr()+getInvestmentAmount()+getIaCurrency()+getShareRatio()+getRoundOfInvestment()+getInvestmentStage()
				   +getEnterpriseCapital()+getEcCurrency()+getExitTimeStr()+getSignOut()+getRateOfReturn();
		   return str;
		   
	   }
}

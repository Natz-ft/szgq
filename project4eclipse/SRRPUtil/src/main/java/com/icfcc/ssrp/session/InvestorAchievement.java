package com.icfcc.ssrp.session;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;


/**
 * 投资业绩实体类
 * 
 * @author Administrator
 */
public class InvestorAchievement implements Serializable {

	private static final long serialVersionUID = -3543534598785480509L;

	
	private String achievementId;// 投资业绩id

	private String investId;// 投资机构id

	private String investmentFunds;// 投资基金名称

	private String investedEnterprise;// 被投资企业名称

	private String investedEnterpriseIndustry;// 被投资企业行业

	private String investedEnterpriseArea;// 被投资企业所属区域

	private Date investmentTime;// 投资时间

	private Double investmentAmount;// 投资金额（万元）
	
	private Double shareRatio;// 股权比例

	private String roundOfInvestment;// 投资伦次

	private String investmentStage;// 投资阶段

	private Double enterpriseCapital;// 被投资企业注册资本

	private Date exitTime;// 退出时间

	private Double rateOfReturn;// 回报率

	private String corepersonnel;// 综合实力、经营业绩、突出优势、核心人员

	private String exitTimeStr;//退出时间字符创
	
	private String investmentAmountString;// 投资金额展示字符串
    
	private String investmentTimeStr;//投资时间字符创
	
	private String investedEnterpriseIndustryStr;//已投企业行业
	
	private String roundOfInvestmentStr;//阶段
	
	private String investmentStageStr;//轮次
	
	private String investedEnterpriseAreaStr;//区域
 
	
	public String getAchievementId() {
		return achievementId;
	}

	public void setAchievementId(String achievementId) {
		this.achievementId = achievementId;
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
			return DigitFormatUtil.digitFormat(this.investmentAmount).toString();
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
		if(investedEnterpriseIndustry!=null){
			investedEnterpriseIndustryStr=RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, investedEnterpriseIndustry);
		}
		return investedEnterpriseIndustryStr;
	}

	public void setInvestedEnterpriseIndustryStr(String investedEnterpriseIndustryStr) {
		this.investedEnterpriseIndustryStr = investedEnterpriseIndustryStr;
	}

	public String getRoundOfInvestmentStr() {
		if(roundOfInvestment!=null){
			roundOfInvestmentStr=RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN, roundOfInvestment);
		}
		return roundOfInvestmentStr;
	}

	public void setRoundOfInvestmentStr(String roundOfInvestmentStr) {
		this.roundOfInvestmentStr = roundOfInvestmentStr;
	}

	public String getInvestmentStageStr() {
		if(investmentStage!=null){
			investmentStageStr=RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTMENT, investmentStage);
		}
		return investmentStageStr;
	}

	public void setInvestmentStageStr(String investmentStageStr) {
		this.investmentStageStr = investmentStageStr;
	}

	public String getInvestedEnterpriseAreaStr() {
		if(investedEnterpriseArea!=null){
			investedEnterpriseAreaStr=RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, investedEnterpriseArea);
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
		
		if(investmentTime!=null){
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			investmentTimeStr=sdf.format(investmentTime);
		}
		return investmentTimeStr;
	}

	public void setInvestmentTimeStr(String investmentTimeStr) {
		this.investmentTimeStr = investmentTimeStr;
	}

	public String getExitTimeStr() {
		if(exitTime!=null){
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			exitTimeStr=sdf.format(exitTime);
		}
		return exitTimeStr;
	}

	public void setExitTimeStr(String exitTimeStr) {
		this.exitTimeStr = exitTimeStr;
	}

	
	
}

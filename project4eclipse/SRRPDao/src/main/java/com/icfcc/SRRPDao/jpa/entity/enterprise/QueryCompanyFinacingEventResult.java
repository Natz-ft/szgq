package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

@Data
@Entity
public class QueryCompanyFinacingEventResult implements Serializable {

	private static final long serialVersionUID = 719113333308673747L;

	@Column(name = "enterprise_id")
	private String enterpriseId;;// 企业id

	@Column(name = "name")
	private String enterpriseName;// 企业name

	@Column(name = "rearea")
	private String rearea;// 企业所在区域

	@Id
	@Column(name = "info_id")
	private String infoId;// 融资信息id

	@Column(name = "industry")
	private String industry;// 企业行业

	@Temporal(TemporalType.DATE)
	@Column(name = "operdate")
	private Date operdate;// 投递时间

	@Column(name = "project_name")
	private String projectName;// 项目名称
	
	@Column(name = "operuser")
	private String operuser;// 操作用户
	
	@Column(name = "amount")
	private String amount;// 金额

	@Column(name = "currency")
	private String currency;// 货币

	@Transient
	private String currencyStr;// 货币字符串

	@Column(name = "finacing_turn")
	private String finacingTurn;// 融资轮次
	
	@Column(name = "duringdate")
	private String duringdate;// 投资用时
	@Transient
	private String reareaDicName;// 所属区域字典值

	@Transient
	private String industryDicName;
	
	@Transient
	private String investorUser;//投资方
	@Transient
	private String industry2;// 二级行业

	@Transient
	private String industry2Dicname;// 二级行业字典值

	@Transient
	private String industryStr;// 行业展示字段
	@Transient
	private String finacingTurnDicName;

	public String getFinacingTurnDicName() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN,
				this.finacingTurn);
	}

	public void setFinacingTurnDicName(String finacingTurnDicName) {
		this.finacingTurnDicName = finacingTurnDicName;
	}

	public String getIndustryDicName() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY,
				this.industry);
	}

	public void setIndustryDicName(String industryDicName) {
		this.industryDicName = industryDicName;
	}

	public String getReareaDicName() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, this.rearea);
	}

	public void setReareaDicName(String reareaDicName) {
		this.reareaDicName = reareaDicName;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getRearea() {
		return rearea;
	}

	public void setRearea(String rearea) {
		this.rearea = rearea;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getOperdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.operdate);
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAmount() {
		return "CNY  " + DigitFormatUtil.digitFormat(this.amount);
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFinacingTurn() {
		return finacingTurn;
	}

	public void setFinacingTurn(String finacingTurn) {
		this.finacingTurn = finacingTurn;
	}

	public String getCurrencyStr() {
		this.currencyStr = "";
		if (this.amount.equals("")||this.amount==null) {
			currencyStr = "0";
		} else {
			Double sumAmountD=Math.round(Double.valueOf(this.amount)*1000000)/100000000.00000000;
			String sumAmountS="";
			if(sumAmountD.intValue()-sumAmountD==0){//判断是否符合取整条件
				sumAmountS=	String.valueOf(sumAmountD.intValue());
			}else{
				sumAmountS=String.valueOf(sumAmountD);
			}
			currencyStr += sumAmountS
					+ " "
					+ RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY,
							this.currency);
		}
		return currencyStr;
	}

	public void setCurrencyStr(String currencyStr) {
		this.currencyStr = currencyStr;
	}

	public String getIndustry2() {
		return industry2;
	}

	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}

	public String getIndustry2Dicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2,
				this.industry);
	}

	public void setIndustry2Dicname(String industry2Dicname) {
		this.industry2Dicname = industry2Dicname;
	}

	public String getIndustryStr() {
		return industryStr;
	}

	public void setIndustryStr(String industryStr) {
		this.industryStr = industryStr;
	}

	public String getDuringdate() {
		return duringdate;
	}

	public void setDuringdate(String duringdate) {
		this.duringdate = duringdate;
	}

	public String getOperuser() {
		return operuser;
	}

	public void setOperuser(String operuser) {
		this.operuser = operuser;
	}

	public String getInvestorUser() {
		return investorUser;
	}

	public void setInvestorUser(String investorUser) {
		this.investorUser = investorUser;
	}
	
}

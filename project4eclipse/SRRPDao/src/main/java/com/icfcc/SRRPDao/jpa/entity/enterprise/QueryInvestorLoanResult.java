package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

/**
 * 
 * @author LiWCh 投资机构的放款信息的查询
 */
@Entity
public class QueryInvestorLoanResult implements Serializable {

	private static final long serialVersionUID = 4893132971620427337L;

	@Id
	@Column(name = "loan_id")
	private String loanId;// id

	@Column(name = "event_id")
	private String eventId;// 投资事件id

	@Column(name = "info_id")
	private String infoId;// 投资需求id

	@Column(name = "amount")
	private String amount;// 放款金额

	@Temporal(TemporalType.DATE)
	@Column(name = "loandate")
	private Date loanDate;// 放款时间

	@Column(name = "investor_id")
	private String investorId;// 投资机构的id

	@Column(name = "name")
	private String investorName;// 投资机构名称

	@Column(name = "area_code")
	private String areaCode;// 行政区划

	@Column(name = "currency")
	private String currency;// 币种

	@Transient
	private String amountStr;// 放款金额字符串
	
	@Transient
	private String amountStrShow;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "finance_stage")
	private byte[] financeStage;// 拟投资阶段

	@Transient
	private DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLoanDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.loanDate);
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCurrency() {
		return "CNY  " + DigitFormatUtil.digitFormat(this.amount);
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getFinanceStage() throws UnsupportedEncodingException {
		if (financeStage == null) {
			return "";
		} else {
			return new String(financeStage, SRRPConstant.DEFAULTCHARTS);
		}
	}

	public void setFinanceStage(String financeStage) throws UnsupportedEncodingException {
		this.financeStage = financeStage.getBytes(SRRPConstant.DEFAULTCHARTS);
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getAmountStr() {
		this.amountStr="";
		if(this.amount==null || "".equals(this.amount)){
			amountStr="0";
		}else{
			Double sumAmountD=Math.round(Double.valueOf(this.amount)*1000000)/100000000.00000000;
			String sumAmountS="";
			if(sumAmountD.intValue()-sumAmountD==0){//判断是否符合取整条件
				sumAmountS=	String.valueOf(sumAmountD.intValue());
			}else{
				sumAmountS=String.valueOf(sumAmountD);
			}
			amountStr+=sumAmountS +" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
		}
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	
	public String getAmountStrShow() {
		this.amountStrShow="";
		if(this.amount==null || "".equals(this.amount)){
			amountStrShow="0";
			return amountStrShow +" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
		}
		return this.amount +" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
	}

	public void setAmountStrShow(String amountStrShow) {
		this.amountStrShow = amountStrShow;
	}

}

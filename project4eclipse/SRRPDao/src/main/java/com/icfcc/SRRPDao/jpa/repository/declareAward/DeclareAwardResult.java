package com.icfcc.SRRPDao.jpa.repository.declareAward;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class DeclareAwardResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5267815611179002020L;

	
	@Id
	@Column(name = "eventid")
	private String  finacingEventId; // 机构解决需求个数
	
	@Column(name = "investorgid")
	private String investorgId; // 机构ID

	@Column(name = "rownum")
	private String rownum; // 名次

	@Column(name = "amount")
	private String amount; // 机构名称

	@Column(name = "currency")
	private String currency; // 机构投资总金额
	
	
	@Column(name = "inforid")
	private String  inforId; // 机构解决需求个数
	
	@Column(name = "rearea")
	private String  companyAddress; // 机构解决需求个数
	
	@Column(name = "code")
	private String  code; // 机构解决需求个数
	
	@Column(name = "name")
	private String  name; // 机构解决需求个数
	
	
	
	@Column(name = "loandate")
	private Date loandate;// 事件id
	
	@Column(name = "loanid")
	private String loanId;// 奖励申报id
	
	public String getInvestorgId() {
		return investorgId;
	}

	public void setInvestorgId(String investorgId) {
		this.investorgId = investorgId;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getFinacingEventId() {
		return finacingEventId;
	}

	public void setFinacingEventId(String finacingEventId) {
		this.finacingEventId = finacingEventId;
	}

	public String getInforId() {
		return inforId;
	}

	public void setInforId(String inforId) {
		this.inforId = inforId;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLoandate() {
		return loandate;
	}

	public void setLoandate(Date loandate) {
		this.loandate = loandate;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
     
	
	
	
	
	
}

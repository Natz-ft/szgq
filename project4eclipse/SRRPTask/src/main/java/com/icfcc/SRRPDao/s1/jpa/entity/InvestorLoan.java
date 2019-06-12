package com.icfcc.SRRPDao.s1.jpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 放款信息表
 * 
 * @author john
 * 
 */
@Entity
@Table(name = "rp_investor_loan")
public class InvestorLoan implements Serializable {

	private static final long serialVersionUID = -2202429463289241343L;

	@Id
	@Column(name = "loan_id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String loanId;

	@Column(name = "event_id")
	private String eventId;// 投资事件id

	@Column(name = "info_id")
	private String infoId;// 投资需求id

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "amount")
	private String amount;// 放款金额

	@Column(name = "currency")
	private String currency;// 放宽币种

	@Temporal(TemporalType.DATE)
	@Column(name = "loandate")
	private Date loanDate;// 放款时间

	@Column(name = "operuser")
	private String operUser;// 操作人
	
	@Column(name = "enterprise_id")
	private String enterpriseId;// 涉及企业

	// @ManyToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "event_id")
	@Transient
	private FinacingEvent finacingEvent;

	public String getEventId() {
		return eventId;
	}

	public void setEvenId(String eventId) {
		this.eventId = eventId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public FinacingEvent getFinacingEvent() {
		return finacingEvent;
	}

	public void setFinacingEvent(FinacingEvent finacingEvent) {
		this.finacingEvent = finacingEvent;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

}

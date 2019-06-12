package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rp_investor_follow")
public class InvestorFollow implements Serializable {

	private static final long serialVersionUID = -6189502450349434508L;

	@Column(name = "event_id")
	private String eventId;

	@Column(name = "org_id")
	private String orgId;

	@Id
	@Column(name = "follow_id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String followId;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "currency")
	private String currency;

	@Column(name = "info_id")
	private String infoId;

	@Column(name = "operuser")
	private String operUser;

	@Column(name = "orgname")
	private String orgName;

	// @ManyToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "event_id")
	@Transient
	private FinacingEvent finacingEvent;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getOperUser() {
		return operUser;
	}

	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public FinacingEvent getFinacingEvent() {
		return finacingEvent;
	}

	public void setFinacingEvent(FinacingEvent finacingEvent) {
		this.finacingEvent = finacingEvent;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

}

package com.icfcc.SRRPDao.s1.jpa.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;


/**
 * 融资需求详情信息查询列表，返回值类型封装类
 * 
 * @author Administrator
 *
 */


@Entity
public class FinacingEventInvestor implements Serializable {

	private static final long serialVersionUID = -9182307008368341300L;

	@Id
	@Column(name = "event_id", length = 32)
	private String eventId;

	@Column(name = "name", length = 32)
	private String investorName;

	@Column(name = "org_type", length = 60)
	private String orgType;

	@Column(name = "amount")
	private Double amount = 0.0;
	
	@Column(name = "reserve")
	private String reserve;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}


}

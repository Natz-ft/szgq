package com.icfcc.SRRPDao.jpa.entity.enterprise;

import io.netty.util.internal.StringUtil;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * 跟投表与机构表联合查询
 * @author Administrator
 *
 */
@Data
@Entity
public class QueryFollowInvestorResult implements Serializable {

	private static final long serialVersionUID = 1188761985764308589L;

/**
 * 跟投信息的id    事件id    跟投机构名称      跟投机构id   跟投金额       机构类型    机构名称
 */
	@Id
	@Column(name = "follow_id")
	private String followId;;// 跟投信息的id
	
	@Column(name = "event_id")
	private String eventId;;// 融资事件的id
	
	@Column(name = "orgname")
	private String orgname;;// 跟投机构名称
	
	@Column(name = "amount")
	private Double amount;;// 跟投金额
	
	@Column(name = "name")
	private String investorName;;// 跟投金额
	
	@Column(name = "org_type")
	private String orgType;// 跟投机构类型
	
	@Column(name = "investor_id")
	private String investorId;// 跟投机构id
	
	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}


	public String getInvestorName() {
		if(StringUtil.isNullOrEmpty(this.investorName)){
			this.investorName=this.orgname;
		}
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}



	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	
	
	
	
}

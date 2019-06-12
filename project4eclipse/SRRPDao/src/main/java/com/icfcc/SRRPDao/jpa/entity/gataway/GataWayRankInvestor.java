package com.icfcc.SRRPDao.jpa.entity.gataway;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_rangking_investor")
public class GataWayRankInvestor implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -7941713238014501423L;

	@Id
	@Column(name = "rid")
	private BigDecimal rid;

	@Column(name = "name")
	private String name;

//	@Column(name = "adminarea")
//	private String adminarea;

	@Column(name = "investnum")
	private String investNum;

	@Column(name = "amount", precision = 15, scale = 2)
	private double  amount;

	@Column(name = "solveorgnum")
	private String solveorgNum;

	@Column(name = "raking")
	private String raking;

	@Column(name = "countdate")
	private String countDate;

	public BigDecimal getRid() {
		return rid;
	}

	public void setRid(BigDecimal rid) {
		this.rid = rid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getAdminarea() {
//		return adminarea;
//	}
//
//	public void setAdminarea(String adminarea) {
//		this.adminarea = adminarea;
//	}

	public String getInvestNum() {
		return investNum;
	}

	public void setInvestNum(String investNum) {
		this.investNum = investNum;
	}

	public String getSolveorgNum() {
		return solveorgNum;
	}

	public void setSolveorgNum(String solveorgNum) {
		this.solveorgNum = solveorgNum;
	}

	public String getRaking() {
		return raking;
	}

	public void setRaking(String raking) {
		this.raking = raking;
	}

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
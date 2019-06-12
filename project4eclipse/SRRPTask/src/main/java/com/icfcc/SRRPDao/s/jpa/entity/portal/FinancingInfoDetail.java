/**
 * 
 */
package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lijj
 *
 */
@Entity
@Table(name = "platform_portal_finacing_demand_detail")
public class FinancingInfoDetail implements Serializable {

	private static final long serialVersionUID = -2852037810889799606L;

	@Id
	@Column(name = "info_id")
	private String infoId;
	@Column(name = "project_name")
	private String projectName;
	@Column(name = "highestinterest")
	private String highestInterest;
	@Column(name = "usedtime")
	private String usedtime;// 融资占用时间
	@Column(name = "amountrange")
	private String amountRange;
	@Column(name = "rearea")
	private String reArea;
	@Column(name = "industry")
	private String industry;
	@Column(name = "financingpurposes")
	private String financingPurposes;
	@Column(name = "intentionmoney")
	private String intentionMoney;
	@Column(name = "financingmode")
	private String financingMode;
	@Column(name = "rel_name")
	private String relName;
	@Column(name = "rel_phone")
	private String relPhone;
	@Column(name = "description")
	private String description;
	@Column(name = "operdate")
	private Date operDate;
	@Column(name = "amount")
	private Double amount;
	@Column(name = "financing_turn")
	private String financingTurn;
	@Column(name = "scale")
	private Double scale;
	@Column(name = "sell")
	private String sell;

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getHighestInterest() {
		return highestInterest;
	}

	public void setHighestInterest(String highestInterest) {
		this.highestInterest = highestInterest;
	}

	public String getUsedtime() {
		return usedtime;
	}

	public void setUsedtime(String usedtime) {
		this.usedtime = usedtime;
	}

	public String getAmountRange() {
		return amountRange;
	}

	public void setAmountRange(String amount) {
		this.amountRange = amount;
	}

	public String getReArea() {
		return reArea;
	}

	public void setReArea(String reArea) {
		this.reArea = reArea;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getFinancingPurposes() {
		return financingPurposes;
	}

	public void setFinancingPurposes(String financingPurposes) {
		this.financingPurposes = financingPurposes;
	}

	public String getIntentionMoney() {
		return intentionMoney;
	}

	public void setIntentionMoney(String intentionMoney) {
		this.intentionMoney = intentionMoney;
	}

	public String getFinancingMode() {
		return financingMode;
	}

	public void setFinancingMode(String financingMode) {
		this.financingMode = financingMode;
	}

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRelPhone() {
		return relPhone;
	}

	public void setRelPhone(String relPhone) {
		this.relPhone = relPhone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFinancingTurn() {
		return financingTurn;
	}

	public void setFinancingTurn(String financingTurn) {
		this.financingTurn = financingTurn;
	}

	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

}

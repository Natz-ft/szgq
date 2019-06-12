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
import javax.persistence.Transient;

/**
 * @author lijj
 *
 */
@Entity
@Table(name = "platform_portal_demand_query")
public class FinancingInfoQuery implements Serializable {

	private static final long serialVersionUID = -5532664525910444688L;

	@Id
	@Column(name = "info_id")
	private String infoId;
	@Column(name = "project_name")
	private String projectName;

	@Column(name = "rearea")
	private String rearea;
	@Column(name = "industry")
	private String industry;
	@Transient
	private String usedtime;

	@Transient
	private String highestInterest;

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
	// 经营年限
	@Column(name = "enterpriseperiod")
	private String enterprisePeriod;
	// 融资金额
	@Column(name = "finacingmoney")
	private String finacingMoney;

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

	public String getRearea() {
		return rearea;
	}

	public void setRearea(String rearea) {
		this.rearea = rearea;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getUsedtime() {
		return usedtime;
	}

	public void setUsedtime(String usedtime) {
		this.usedtime = usedtime;
	}

	public String getHighestInterest() {
		return highestInterest;
	}

	public void setHighestInterest(String highestInterest) {
		this.highestInterest = highestInterest;
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

	public String getEnterprisePeriod() {
		return enterprisePeriod;
	}

	public void setEnterprisePeriod(String enterprisePeriod) {
		this.enterprisePeriod = enterprisePeriod;
	}

	public String getFinacingMoney() {
		return finacingMoney;
	}

	public void setFinacingMoney(String finacingMoney) {
		this.finacingMoney = finacingMoney;
	}
}

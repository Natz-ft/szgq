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
@Table(name = "platform_portal_finacing_event_query")
public class FinancingEventQuery implements Serializable {

	private static final long serialVersionUID = 9101583099490150128L;

	@Id
	@Column(name = "event_id")
	private String eventId;
	@Column(name = "info_id")
	private String infoId;
	@Column(name = "project_name")
	private String projectName;
	@Column(name = "investorname")
	private String investorName;
	@Column(name = "enterprisename")
	private String enterpriseName;
	@Column(name = "industry")
	private String industry;
	@Column(name = "finacing_turn")
	private String financingTurn;
	@Column(name = "finance_stage")
	private String financeStage;
	@Column(name = "amount")
	private Double amount;
	@Column(name = "scale")
	private String scale;
	@Column(name = "description")
	private String description;

	@Column(name = "rearea")
	private String rearea;
	@Column(name = "operdate")
	private Date operDate;
	@Column(name = "open")
	private String open;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getFinancingTurn() {
		return financingTurn;
	}

	public void setFinancingTurn(String financingTurn) {
		this.financingTurn = financingTurn;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
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

	public String getRearea() {
		return rearea;
	}

	public void setRearea(String rearea) {
		this.rearea = rearea;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getFinanceStage() {
		return financeStage;
	}

	public void setFinanceStage(String financeStage) {
		this.financeStage = financeStage;
	}

	public String getInfoId()
	{
		return infoId;
	}

	public void setInfoId(String infoId)
	{
		this.infoId = infoId;
	}

	
	
}

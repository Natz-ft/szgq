package com.icfcc.SRRPDao.jpa.entity.managedept;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 融资统计结果实体类
 * 
 * @author Administrator
 *
 */
@Entity
public class FinacingStatisticsResult implements Serializable {

	private static final long serialVersionUID = -982781753722970683L;

	@Id
	@Column(name = "finacingtime")
	private String finacingTime; // 时间

	@Column(name = "demandnumber")
	private Integer demandNumber; // 平台发布需求个数

	@Column(name = "demandamount")
	private Double demandAmount; // 平台发布融资金额

	@Column(name = "finacingamount")
	private Double finacingAmount; // 平台融资金额

	@Column(name = "solutionsnumber")
	private Integer solutionsNumber; // 解决需求数

	public String getFinacingTime() {
		return finacingTime;
	}

	public void setFinacingTime(String finacingTime) {
		this.finacingTime = finacingTime;
	}

	public Integer getDemandNumber() {
		return demandNumber;
	}

	public void setDemandNumber(Integer demandNumber) {
		this.demandNumber = demandNumber;
	}

	public Integer getSolutionsNumber() {
		return solutionsNumber;
	}

	public void setSolutionsNumber(Integer solutionsNumber) {
		this.solutionsNumber = solutionsNumber;
	}

	public Double getDemandAmount() {
		return demandAmount;
	}

	public void setDemandAmount(Double demandAmount) {
		this.demandAmount = demandAmount;
	}

	public Double getFinacingAmount() {
		return finacingAmount;
	}

	public void setFinacingAmount(Double finacingAmount) {
		this.finacingAmount = finacingAmount;
	}

}

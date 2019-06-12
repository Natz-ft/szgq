package com.icfcc.SRRPDao.s1.jpa.entity;

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
@Table(name = "rp_report_area")
public class PlatformRankArea implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -7941713238014501423L;

	@Id
	@Column(name = "rid", unique = true, nullable = false)
	private String rid;

	@Column(name = "adminarea")
	private String adminarea;

	@Column(name = "rankingnum")
	private String rankingnum;

	@Column(name = "finanmoney", columnDefinition = "decimal(15,2)")
	private double finanMoney;

	@Column(name = "countdate")
	private String countDate;

	@Column(name = "status")
	private String status;

	@Column(name = "demandnum", columnDefinition = "decimal(15,2)")
	private BigDecimal demandNum;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRankingnum() {
		return rankingnum;
	}

	public void setRankingnum(String rankingnum) {
		this.rankingnum = rankingnum;
	}

	public double getFinanMoney() {
		return finanMoney;
	}

	public void setFinanMoney(double finanMoney) {
		this.finanMoney = finanMoney;
	}

	public String getAdminarea() {
		return adminarea;
	}

	public void setAdminarea(String adminarea) {
		this.adminarea = adminarea;
	}

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getDemandNum() {
		return demandNum;
	}

	public void setDemandNum(BigDecimal demandNum) {
		this.demandNum = demandNum;
	}
}
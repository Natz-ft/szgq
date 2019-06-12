package com.icfcc.SRRPDao.s.jpa.entity.portal;

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
@Table(name = "platform_portal_rangking_finacturn")
public class PortalRankFinacingTurn implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -7941713238014501423L;

	@Id
	@Column(name = "rid")
	private BigDecimal rid;
	/**
	 * 统计纬度 01时代表轮次；02时代表月度
	 */
	@Column(name = "dimension")
	private String dimension;

	@Column(name = "rankingnum")
	private String rankingnum;

	@Column(name = "finanmoney")
	private String finanMoney;

	@Column(name = "countdate")
	private String countDate;

	@Column(name = "demandnum", columnDefinition = "decimal(15,0)")
	private double demandNum;

	@Column(name = "status")
	private String status;

	@Column(name = "type")
	private String type;

	public BigDecimal getRid() {
		return rid;
	}

	public void setRid(BigDecimal rid) {
		this.rid = rid;
	}

	public String getRankingnum() {
		return rankingnum;
	}

	public void setRankingnum(String rankingnum) {
		this.rankingnum = rankingnum;
	}

	public String getFinanMoney() {
		return finanMoney;
	}

	public void setFinanMoney(String finanMoney) {
		this.finanMoney = finanMoney;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
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

	public double getDemandNum() {
		return demandNum;
	}

	public void setDemandNum(double demandNum) {
		this.demandNum = demandNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
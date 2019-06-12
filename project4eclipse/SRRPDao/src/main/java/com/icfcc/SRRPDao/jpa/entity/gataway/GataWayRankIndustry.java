package com.icfcc.SRRPDao.jpa.entity.gataway;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_rangking_company_industry")
public class GataWayRankIndustry implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -7941713238014501423L;

	@Id
	@Column(name = "rid")
	private BigDecimal rid;

	@Column(name = "trades")
	private String trades;

	@Column(name = "rankingnum")
	private String rankingnum;

	@Column(name = "finanmoney", columnDefinition = "decimal(15,2)")
	private double finanMoney;

	@Column(name = "countdate")
	private String countDate;

	@Column(name = "demandnum", columnDefinition = "decimal(15)")
	private double demandNum;

	@Column(name = "status")
	private String status;

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

	public double getFinanMoney() {
		return finanMoney;
	}

	public void setFinanMoney(double finanMoney) {
		this.finanMoney = finanMoney;
	}

	public String getTrades() {
		 if (StringUtils.isNotEmpty(trades)) {
				String industryStr = trades;// 获取数据库中行业的展示
				if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
					return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2, trades);
				} else {// 如果是一级的行业显示以及行业
					return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, trades);
				}
			}
		return "";
	}

	public void setTrades(String trades) {
		this.trades = trades;
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

}
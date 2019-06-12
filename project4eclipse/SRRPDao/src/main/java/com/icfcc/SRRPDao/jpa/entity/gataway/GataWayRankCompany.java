package com.icfcc.SRRPDao.jpa.entity.gataway;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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
@Table(name = "platform_portal_company_rangking")
public class GataWayRankCompany implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7941713238014501423L;

    @Id
    @Column(name = "rid")
    private BigDecimal rid;
    
    @Column(name = "name")
    private String name;


    @Column(name = "finanmoney", precision = 15, scale = 2)
    private double finanMoney;

    @Column(name = "uplistnum")
    private String upListNum;

    @Column(name = "ranking")
    private String ranking;

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

    public String getFinanMoney() {
        return new DecimalFormat("##.##").format(finanMoney);
    }

    public void setFinanMoney(double finanMoney) {
        this.finanMoney = finanMoney;
    }

    public String getUpListNum() {
        return upListNum;
    }

    public void setUpListNum(String upListNum) {
        this.upListNum = upListNum;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getCountDate() {
        return countDate;
    }

    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
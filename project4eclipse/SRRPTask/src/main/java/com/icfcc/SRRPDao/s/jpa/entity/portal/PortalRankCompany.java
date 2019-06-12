package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

/**
 */
@Data
@Entity
@IdClass(PortalRankCompany.class)
@Table(name = "platform_portal_company_rangking")
public class PortalRankCompany implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7941713238014501423L;
    
    @Id
    @Column(name = "rid")
    private BigDecimal rid;
    
    @Column(name = "countdate")
    private String countDate;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "finanmoney")
    private double finanMoney;

    @Column(name = "uplistnum")
    private String upListNum;

    @Column(name = "ranking")
    private String ranking;

	public BigDecimal getRid() {
		return rid;
	}

	public void setRid(BigDecimal rid) {
		this.rid = rid;
	}

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getFinanMoney() {
		return finanMoney;
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

}
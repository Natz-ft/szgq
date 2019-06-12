package com.icfcc.SRRPDao.s.jpa.entity.portal;

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
public class PortalRankInvestor implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7941713238014501423L;

    @Id
    @Column(name = "rid")
    private String rid;
    
    @Column(name = "name", unique = true, nullable = false, length = 200)
    private String name;
    
    @Column(name = "countdate")
    private String countDate;

    @Column(name = "investnum")
    private String investNum;

    @Column(name = "amount")
    private String amount;

    @Column(name = "solveorgnum")
    private String solveorgNum;

    @Column(name = "raking")
    private String raking;
    
    
    public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
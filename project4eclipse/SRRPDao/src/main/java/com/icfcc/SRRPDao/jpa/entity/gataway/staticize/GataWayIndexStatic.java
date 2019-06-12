package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * SystemOrg entity. @author MyEclipse Persistence Tools
 */
@Data
@Entity
@Table(name = "platform_portal_index")
public class GataWayIndexStatic implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "countdate")
    private String countDate;

    @Column(name = "finaccount")
    private String finacCount = "--";

    @Column(name = "enterprisecount")
    private String enterpriseCount= "--";

    @Column(name = "investorcount")
    private String investorCount= "--";

    @Column(name = "demondcount")
    private String demondCount= "--";
    
    @Column(name = "finacingcount")
    private String finacingCount= "--";
    
    @Column(name = "enterprisesuccesscount")
    private String enterpriseSuccessCount= "--";
    
    public String getEnterpriseSuccessCount() {
		return enterpriseSuccessCount;
	}


	public void setEnterpriseSuccessCount(String enterpriseSuccessCount) {
		this.enterpriseSuccessCount = enterpriseSuccessCount;
	}


	public String getFinacingCount() {
		return finacingCount;
	}


	public void setFinacingCount(String finacingCount) {
		this.finacingCount = finacingCount;
	}


	public String getCountDate() {
        return countDate;
    }


    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }


    public String getFinacCount() {
        return finacCount;
    }


    public void setFinacCount(String finacCount) {
        this.finacCount = finacCount;
    }


    public String getEnterpriseCount() {
        return enterpriseCount;
    }


    public void setEnterpriseCount(String enterpriseCount) {
        this.enterpriseCount = enterpriseCount;
    }


    public String getInvestorCount() {
        return investorCount;
    }


    public void setInvestorCount(String investorCount) {
        this.investorCount = investorCount;
    }


    public String getDemondCount() {
        return demondCount;
    }


    public void setDemondCount(String demondCount) {
        this.demondCount = demondCount;
    }


    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
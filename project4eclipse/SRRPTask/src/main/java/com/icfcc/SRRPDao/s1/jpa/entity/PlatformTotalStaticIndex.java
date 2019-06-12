package com.icfcc.SRRPDao.s1.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class PlatformTotalStaticIndex implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "countdate")
    private String countDate;

    //平台融资总额
    @Column(name = "finaccount")
    private String finacCount;

    //企业数
    @Column(name = "enterprisecount")
    private String enterpriseCount;
    
    //成功企业数
    @Column(name = "enterprisesuccesscount")
    private String enterpriseSuccessCount;

    public String getEnterpriseSuccessCount() {
		return enterpriseSuccessCount;
	}

	public void setEnterpriseSuccessCount(String enterpriseSuccessCount) {
		this.enterpriseSuccessCount = enterpriseSuccessCount;
	}

	//机构树
    @Column(name = "investorcount")
    private String investorCount;
    
    //需求数
    @Column(name = "finacingcount")
    private String finacingCount;

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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
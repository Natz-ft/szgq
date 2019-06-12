package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 机构榜单对象
 * 
 * @author lijj
 *
 */
@Entity
public class ReportBeanOrgTmp  {

    private static final long serialVersionUID = 4151322816041435070L;

    @Id
    @Column(name = "org_id")
    private String orgId;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "company_count")
    private Long companyCount;

    @Column(name = "demand_count")
    private Long demandCount;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(Long companyCount) {
        this.companyCount = companyCount;
    }

    public Long getDemandCount() {
        return demandCount;
    }

    public void setDemandCount(Long demandCount) {
        this.demandCount = demandCount;
    }
}

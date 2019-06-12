package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;

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
@Table(name = "platform_portal_finacing_demand_detail")
public class GataWayDemandDetail implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "info_id")
    private String infoId;
    @Column(name = "project_name")
    private String projectName;

    @Column(name = "usedtime")
    private String usedTime;

    @Column(name = "highestinterest")
    private String highestInterest;

    @Column(name = "amount")
    private String amount;

    @Column(name = "rearea")
    private String rearea;

    @Column(name = "industry")
    private String industry;

    @Column(name = "financingpurposes")
    private String financingPurposes;

    @Column(name = "intentionmoney")
    private String intentionMoney;

    @Column(name = "financingmode")
    private String financingMode;

    @Column(name = "rel_name")
    private String relName;

    @Column(name = "rel_phone")
    private String relPhone;

    @Column(name = "description")
    private String description;

    @Column(name = "operdate")
    private String operdate;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getHighestInterest() {
        return highestInterest;
    }

    public void setHighestInterest(String highestInterest) {
        this.highestInterest = highestInterest;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRearea() {
        return rearea;
    }

    public void setRearea(String rearea) {
        this.rearea = rearea;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getFinancingPurposes() {
        return financingPurposes;
    }

    public void setFinancingPurposes(String financingPurposes) {
        this.financingPurposes = financingPurposes;
    }

    public String getIntentionMoney() {
        return intentionMoney;
    }

    public void setIntentionMoney(String intentionMoney) {
        this.intentionMoney = intentionMoney;
    }

    public String getFinancingMode() {
        return financingMode;
    }

    public void setFinancingMode(String financingMode) {
        this.financingMode = financingMode;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getRelPhone() {
        return relPhone;
    }

    public void setRelPhone(String relPhone) {
        this.relPhone = relPhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperdate() {
        return operdate;
    }

    public void setOperdate(String operdate) {
        this.operdate = operdate;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
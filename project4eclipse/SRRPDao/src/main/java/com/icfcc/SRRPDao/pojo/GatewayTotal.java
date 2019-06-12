package com.icfcc.SRRPDao.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class GatewayTotal {
    
    /**
     * 融资总量
     */
    @JSONField(name="amount")
    private String amount;
    
    /**
     * 平台企业数
     */
    private String companyNum;
    
    /**
     * 平台机构数
     */
    private String organizationNum;
    
    /**
     * 融资需求数量
     */
    private String requirementNum;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }

    public String getOrganizationNum() {
        return organizationNum;
    }

    public void setOrganizationNum(String organizationNum) {
        this.organizationNum = organizationNum;
    }

    public String getRequirementNum() {
        return requirementNum;
    }

    public void setRequirementNum(String requirementNum) {
        this.requirementNum = requirementNum;
    }
    
}

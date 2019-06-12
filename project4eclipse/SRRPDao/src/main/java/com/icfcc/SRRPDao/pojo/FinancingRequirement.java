package com.icfcc.SRRPDao.pojo;

/**
 * 融资需求
 * 
 * @author kf2
 *
 */
public class FinancingRequirement {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 融资金额
     */
    private String financingAmount;

    /**
     * 出让股权
     */
    private String stockRightPer;

    /**
     * 輪次
     */
    private String financingInfo;
    /**
     * 详细
     * 
     */
    private String projectDetail;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFinancingAmount() {
        return financingAmount;
    }

    public void setFinancingAmount(String financingAmount) {
        this.financingAmount = financingAmount;
    }

    public String getStockRightPer() {
        return stockRightPer;
    }

    public void setStockRightPer(String stockRightPer) {
        this.stockRightPer = stockRightPer;
    }

    public String getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(String projectDetail) {
        this.projectDetail = projectDetail;
    }

    public String getFinancingInfo() {
        return financingInfo;
    }

    public void setFinancingInfo(String financingInfo) {
        this.financingInfo = financingInfo;
    }
}

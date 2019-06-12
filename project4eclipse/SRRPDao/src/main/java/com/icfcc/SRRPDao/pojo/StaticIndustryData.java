package com.icfcc.SRRPDao.pojo;

public class StaticIndustryData {

    // 行业
    private String industryName;
    // 统计金额
    private String amount;
    // 占比
    private String scale;

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

}

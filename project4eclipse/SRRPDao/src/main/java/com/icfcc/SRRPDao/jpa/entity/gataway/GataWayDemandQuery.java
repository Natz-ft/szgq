package com.icfcc.SRRPDao.jpa.entity.gataway;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_demand_query")
public class GataWayDemandQuery implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "info_id")
    private String infoId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "amount", columnDefinition = "decimal(15,2)")
    private double amount;

    @Column(name = "rearea")
    private String rearea;

    @Transient
    private String areaName;

    @Column(name = "industry")
    private String industry;
    
    @Transient
    private String industryName;

    //融资金额
    @Column(name = "finacingmoney")
    private String finacingMoney;

    //经营年限
    @Column(name = "enterpriseperiod")
    private String enterprisePeriod;

    //融资用途
    @Column(name = "financingpurposes")
    private String financingPurposes;

    //意向资金
    @Column(name = "intentionmoney")
    private String intentionMoney;

    //融资方式
    @Column(name = "financingmode")
    private String financingMode;

    @Transient
    private String financingModeName;
    
    @Column(name = "rel_name")
    private String relName;

    @Column(name = "rel_phone")
    private String relPhone;

    //项目概述
    @Column(name = "description")
    private String description;
    
    @Column(name = "operdate")
    private Date operDate;
    
    @Column(name = "open")
    private String open;
    @Transient
   	private String sellScale;// 融资金额与资金类型拼接的字符串
    
    @Transient
	private String projectNameShow;
    
    @Transient
    private String financingPurposesShow;
    
	public String getProjectNameShow() {
		Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
		String[] str = projectName.split("~");
		StringBuffer sb = new StringBuffer();
		for (String s : str) {
			Matcher matcher = compile.matcher(s);
			if(matcher.find()) {
				
				String o = matcher.group();
				String n = String.valueOf((int)(Double.valueOf(o)*100));
				sb.append(s.replace(o, n)+"~");
			}
		}
		return sb.toString().substring(0, sb.length()-1).replace("百万", "万");
	}
	
	public void setProjectNameShow(String projectNameShow) {
        this.projectNameShow = projectNameShow;
		
	}

	public String getFinancingPurposesShow() {
		Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
		String[] str = financingPurposes.split("~");
		StringBuffer sb = new StringBuffer();
		for (String s : str) {
			Matcher matcher = compile.matcher(s);
			if(matcher.find()) {
				
				String o = matcher.group();
				String n = String.valueOf((int)(Double.valueOf(o)*100));
				sb.append(s.replace(o, n)+"~");
			}
		}
		return sb.toString().substring(0, sb.length()-1).replace("M", "万元");
	}

	public void setFinancingPurposesShow(String financingPurposesShow) {
        this.financingPurposesShow = financingPurposesShow;
	}
    

   	public String getSellScale() {
   		String tmpScale = "";
   		String scatype=StringUtils.substringBefore(this.intentionMoney,",");
   		String scale=StringUtils.substringAfter(this.intentionMoney,",");

   		if ("0".equals(scatype) ){
   			 tmpScale = "原股权转让   ";
   		} else {
   			 tmpScale = "增资扩股     ";
   		}
   		tmpScale +=scale;
   		return tmpScale;
   	}

   	public void setSellScale(String sellScale) {
   		this.sellScale = sellScale;
   	}
    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRearea() {
        return rearea;
    }

    public void setRearea(String rearea) {
        this.rearea = rearea;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFinancingPurposes() {
        return financingPurposes;
    }

    public void setFinancingPurposes(String financingPurposes) {
        this.financingPurposes = financingPurposes;
    }

    public String getIntentionMoney() {
    	String scale=StringUtils.substringAfter(this.intentionMoney,",");
        return scale;
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

    public String getOperDate() {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(this.operDate);
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }
    
    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getFinacingMoney() {
        return finacingMoney;
    }

    public void setFinacingMoney(String finacingMoney) {
        this.finacingMoney = finacingMoney;
    }

    public String getEnterprisePeriod() {
        return enterprisePeriod;
    }

    public void setEnterprisePeriod(String enterprisePeriod) {
        this.enterprisePeriod = enterprisePeriod;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getFinancingModeName() {
		return financingModeName;
	}

	public void setFinancingModeName(String financingModeName) {
		this.financingModeName = financingModeName;
	}

    
    
}
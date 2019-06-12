package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

/**
 */
@Entity
@Table(name = "platform_portal_finacing_demand")
public class GataWayDemand implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "info_id")
    private String infoId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "amount", columnDefinition = "decimal(15,2)")
    private double amount;

    @Column(name = "scale")
    private String scale;

    @Column(name = "financing_turn")
    private String finacing_turn;

    @Column(name = "usedtime")
    private String usedTime;

    @Column(name = "highestinterest")
    private String highestInterest;

    @Column(name = "amountrange")
    private String amountrange;

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
    private Date operdate;
  
    @Transient
	private String finacing_turnStr;
    
    @Transient
	private String industryStr;
    
    @Transient
	private String sellScale;// 融资金额与资金类型拼接的字符串
    
    @Transient
    private String areaName;
    
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
			if(matcher.find()){
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
		return sb.toString().substring(0, sb.length()-1).replace("M", "万");
	}

	public void setFinancingPurposesShow(String financingPurposesShow) {
        this.financingPurposesShow = financingPurposesShow;;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getSellScale() {
		String tmpScale = "";
		String scatype=StringUtils.substringBefore(this.scale,",");
		String scale=StringUtils.substringAfter(this.scale,",");

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

    
    public String getIndustryStr() {
		if (StringUtils.isNotEmpty(this.industry)) {
			String industryStr = this.industry;// 获取数据库中行业的展示
			if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
				return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2, this.industry);
			} else {// 如果是一级的行业显示以及行业
				return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, this.industry);
			}
		}
	return "";
	}

	public void setIndustryStr(String industryStr) {
		this.industryStr = industryStr;
	}

	public String getFinacing_turnStr() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN, this.finacing_turn);
	}

	public void setFinacing_turnStr(String finacing_turnStr) {
		this.finacing_turnStr = finacing_turnStr;
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

    public String getFinacing_turn() {
        return finacing_turn;
    }

    public void setFinacing_turn(String finacing_turn) {
        this.finacing_turn = finacing_turn;
    }

    public String getScale() {
    	String scaleStr=StringUtils.substringAfter(this.scale,",");
        return scaleStr;
    }

    public void setScale(String scale) {
        this.scale = scale;
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
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(this.operdate);
    }

    public void setOperdate(Date operdate) {
        this.operdate = operdate;
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

    public String getAmountrange() {
        return amountrange;
    }

    public void setAmountrange(String amountrange) {
        this.amountrange = amountrange;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getIntentionMoney() {
    	
    	
        return intentionMoney;
    }

    public void setIntentionMoney(String intentionMoney) {
        this.intentionMoney = intentionMoney;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
public static void main(String arg[]){
	System.out.println(StringUtils.substringBefore("ssss,aaa",","));
	System.out.println(StringUtils.substringAfter("ssss,aaa",","));
}
}
package com.icfcc.SRRPDao.jpa.entity.gataway;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.icfcc.credit.platform.util.SRRPConstant;

@Entity
@Table(name = "platform_portal_investor_query")
public class GataWayInvestorQuery implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3903684524819260188L;

    @Id
    @Column(name = "investor_id")
    private String investorId;

    @Column(name = "name")
    private String investorName;

    @Column(name = "org_type")
    private String orgType;

    @Transient
    private String orgTypeName;

    @Column(name = "capital_type")
    private String capitalType;

    @Transient
    private String capitalTypeName;

    @Column(name = "capital")
    private String capital;

    @Column(name = "logo")
    private String logo;

    @Column(name = "registe_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date registeTime;

    // 拟投资阶段
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "finance_stage")
    private byte[] financeStage;

    // 拟投资行业阶段
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "finance_trade")
    private byte[] financeTrade;

    @Column(name = "description")
    private String description;

    // 成立时间
    @Column(name = "settime")
    private String setTime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "success_trade")
    private byte[] successTrade;
    
    @Transient
    private String financeStageStr;
    
    @Transient
    private String financeTradeStr;

    public String getInvestorId() {
        return investorId;
    }

    public void setInvestorId(String investorId) {
        this.investorId = investorId;
    }

    public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCapitalType() {
        return capitalType;
    }

    public void setCapitalType(String capitalType) {
        this.capitalType = capitalType;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Date getRegisteTime() {
        return registeTime;
    }

    public void setRegisteTime(Date registeTime) {
        this.registeTime = registeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }

    public String getCapitalTypeName() {
        return capitalTypeName;
    }

    public void setCapitalTypeName(String capitalTypeName) {
        this.capitalTypeName = capitalTypeName;
    }

    public String getFinanceStage() throws UnsupportedEncodingException {
        if (financeStage == null) {
            return "";
        } else {
            return new String(financeStage, SRRPConstant.DEFAULTCHARTS);
        }
    }

    public void setFinanceStage(String financeStage) throws UnsupportedEncodingException {
        this.financeStage = financeStage.getBytes(SRRPConstant.DEFAULTCHARTS);
    }

    public String getFinanceTrade() throws UnsupportedEncodingException {
        if (financeTrade == null) {
            return "";
        } else {
            return new String(financeTrade, SRRPConstant.DEFAULTCHARTS);
        }
    }

    public void setFinanceTrade(String financeTrade) throws UnsupportedEncodingException {
        this.financeTrade = financeTrade.getBytes(SRRPConstant.DEFAULTCHARTS);
    }

    public String getSetTime() {
        return setTime;
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }

    public String getSuccessTrade() throws UnsupportedEncodingException {
        if (successTrade == null) {
            return "";
        } else {
            return new String(successTrade, SRRPConstant.DEFAULTCHARTS);
        }
    }

    public void setSuccessTrade(String successTrade) throws UnsupportedEncodingException {
        this.successTrade = successTrade.getBytes(SRRPConstant.DEFAULTCHARTS);
    }

	public String getFinanceStageStr() {
		return financeStageStr;
	}

	public void setFinanceStageStr(String financeStageStr) {
		this.financeStageStr = financeStageStr;
	}

	public String getFinanceTradeStr() {
		return financeTradeStr;
	}

	public void setFinanceTradeStr(String financeTradeStr) {
		this.financeTradeStr = financeTradeStr;
	}
}

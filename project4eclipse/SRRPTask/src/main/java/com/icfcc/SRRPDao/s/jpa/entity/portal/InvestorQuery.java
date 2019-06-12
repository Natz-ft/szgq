/**
 * 
 */
package com.icfcc.SRRPDao.s.jpa.entity.portal;

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

import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * @author lijj
 *
 */
@Entity
@Table(name = "platform_portal_investor_query")
public class InvestorQuery implements Serializable {

	private static final long serialVersionUID = -264498480821844519L;
	@Id
	@Column(name = "investor_id")
	private String investorId;
	@Column(name = "name")
	private String name;
	@Column(name = "org_type")
	private String orgType;
	@Column(name = "capital_type")//资本实力
	private String capitalType;
	@Column(name = "capital")
	private String capital;
	@Column(name = "registe_time")
	private Date registeTime;
	@Column(name = "logo")
	private String logo;
	@Column(name = "finance_stage")
	private String financeStage;
	@Column(name = "finance_trade")
	private String financeTrade;
	@Column(name = "description")
	private String desciption;
	 @Lob
	 @Basic(fetch = FetchType.LAZY)
	 @Column(name = "success_trade")
	 private byte[] successTrade;
	@Column(name = "settime")//成立时间
	private String setTime;

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getRegisteTime() {
		return registeTime;
	}

	public void setRegisteTime(Date registTime) {
		this.registeTime = registTime;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getFinanceStage() {
		return financeStage;
	}

	public void setFinanceStage(String financeStage) {
		this.financeStage = financeStage;
	}

	public String getFinanceTrade() {
		return financeTrade;
	}

	public void setFinanceTrade(String financeTrade) {
		this.financeTrade = financeTrade;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
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
	public String getSetTime() {
		return setTime;
	}

	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}
}

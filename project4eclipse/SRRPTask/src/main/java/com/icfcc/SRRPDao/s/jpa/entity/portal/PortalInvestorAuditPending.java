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
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

import lombok.Data;


/**
 * 投资机构待审核表的实体类
 * 
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "platform_portal_investor_audit_pending")
public class PortalInvestorAuditPending implements Serializable {

	private static final long serialVersionUID = -7626945949361409246L;

	@Id
	@Column(name = "INVESTOR_ID", length = 32)
	private String investorId;// 投资机构的id

	@Column(name = "CERTTYPE")
	private String certtype;// 证件类型

	@Column(name = "CERTNO")
	private String certno;// 证件号码

	@Column(name = "NAME")
	private String name;// 投资机构名称

	@Column(name = "REGISTE_TIME")
	private Date registeTime;// 注册时间

	@Column(name = "TOP_INVESTOR")
	private String topInvestor;// 注册总部

	@Column(name = "ORG_TYPE")
	private String orgType;// 机构类型

	@Column(name = "CAPITAL_TYPE")
	private String capitalType;// 资本类型

	@Column(name = "AREA_CODE")
	private String areaCode;// 行政区划

	@Column(name = "LICENSE_PATH")
	private String licensePath;// 营业执照路径

	@Column(name = "ADDRESS")
	private String address;// 地址

	@Column(name = "ZIPCODE")
	private String zipcode;// 邮编

	@Column(name = "REL_NAME")
	private String relName;// 联系人姓名

	@Column(name = "REL_PHONE")
	private String relPhone;// 联系人电话

	@Column(name = "FAX")
	private String fax;// 传真

	@Column(name = "EMAIL")
	private String email;// 电子邮箱

	@Column(name = "DESCRIPTION")
	private String description;// 机构介绍

	@Column(name = "CAPITAL")
	private Double capital;// 管理资本量

	@Column(name = "CURRENCY")
	private String currency;// 币种

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "FINANCE_STAGE")
	private byte[] financeStage;// 拟投资阶段

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "FINANCE_TRADE")
	private byte[] financeTrade;// 拟投资行业
 
	@Column(name = "STOP_FLAG")
	private String stopFlag = "1";//启用停用状态

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	public String getCerttype() {
		return certtype;
	}

	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegisteTime() {
		return registeTime;
	}

	public void setRegisteTime(Date registeTime) {
		this.registeTime = registeTime;
	}

	public String getTopInvestor() {
		return topInvestor;
	}

	public void setTopInvestor(String topInvestor) {
		this.topInvestor = topInvestor;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getLicensePath() {
		return licensePath;
	}

	public void setLicensePath(String licensePath) {
		this.licensePath = licensePath;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public byte[] getFinanceStage() {
		return financeStage;
	}

	public void setFinanceStage(byte[] financeStage) {
		this.financeStage = financeStage;
	}

	public byte[] getFinanceTrade() {
		return financeTrade;
	}

	public void setFinanceTrade(byte[] financeTrade) {
		this.financeTrade = financeTrade;
	}

	public String getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}
}

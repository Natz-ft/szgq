package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

@Entity
@Table(name = "platform_portal_investorregister")
public class PortalInvestorRegiter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -772948341926350933L;

	/**
	 * 机构表中所很出现的列；
	 */
	@Id
	@Column(name = "investor_id", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String investorId;// 投资机构的id

	@Column(name = "certtype")
	private String certtype;// 证件类型

	@Column(name = "certno")
	private String certno;// 证件号码

	@Column(name = "name")
	private String name;// 投资机构名称

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "registe_time")
	private Date registeTime;// 注册时间

	@Column(name = "top_investor")
	private String topInvestor;// 注册总部

	@Column(name = "org_type")
	private String orgType;// 机构类型

	@Column(name = "stop_flag")
	private String stopFlag;// 启用停用标识

	@Column(name = "audit_status")
	private String auditStatus;// 审核状态

	@Column(name = "capital_type")
	private String capitalType;// 资本类型

	@Column(name = "area_code")
	private String areaCode;// 行政区划

	@Column(name = "license_path")
	private String licensePath;// 营业执照路径

	@Column(name = "address")
	private String address;// 地址

	@Column(name = "zipcode")
	private String zipcode;// 邮编

	@Column(name = "rel_name")
	private String relName;// 联系人姓名

	@Column(name = "rel_phone")
	private String relPhone;// 联系人电话

	@Column(name = "fax")
	private String fax;// 传真

	@Column(name = "email")
	private String email;// 电子邮箱

	@Column(name = "description")
	private String description;// 机构介绍

	@Column(name = "capital")
	private Double capital;// 管理资本量

	@Column(name = "currency")
	private String currency;// 币种

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "finance_stage")
	private byte[] financeStage;// 拟投资阶段

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "finance_trade")
	private byte[] financeTrade;// 拟投资行业

	@Temporal(TemporalType.DATE)
	@Column(name = "create_time")
	private Date createTime; // 平台注册时间

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

	public String getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

}

package com.icfcc.SRRPDao.s1.jpa.entity;

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

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

@Entity
@Table(name = "rp_investor")
public class Investor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7729483419263509375L;

	/**
	 * 机构表中所出现的列；
	 */
	@Id
	@Column(name = "investor_id", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "assigned")
	@GeneratedValue(generator = "system-uuid")
	private String investorId;// 投资机构的id

	@Column(name = "certtype")
	private String certtype;// 证件类型

	@Column(name = "certno")
	private String certno;// 证件号码

	@Column(name = "name")
	private String name;// 投资机构名称

	@Temporal(TemporalType.DATE)
	@Column(name = "registe_time")
	private Date registeTime;// 成立日期

	@Column(name = "top_investor")
	private String topInvestor;

	@Column(name = "org_type")
	private String orgType;// 机构类型

	@Column(name = "stop_flag")
	private String stopFlag;// 启用停用标识

	@Column(name = "audit_status")
	private String auditStatus;// 审核状态

	@Column(name = "capital_type")
	private String capitalType;// 资本类型

	@Column(name = "area_code")
	private String areaCode;// 所属区域

	@Column(name = "license_path")
	private String licensePath;// 营业执照路径

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

	@Column(name = "currency")
	private String currency;// 币种

	@Column(name = "organizational_form")
	private String organizationalForm;// 组织形式

	@Column(name = "amac_record")
	private String amacRecord;// 中基协备案号

	@Column(name = "office_address")
	private String officeAddress;// 办公地址

	@Column(name = "registered_capital")
	private Double registeredCapital;// 注册资本

	@Column(name = "paid_capital")
	private Double paidCapital;// 实缴资本

	@Column(name = "manage_org")
	private String manageOrg;// 管理机构

	@Column(name = "trusteeship")
	private String trusteeship;// 托管机构

	@Column(name = "legal_representative")
	private String legalRepresentative;// 法定代表人

	@Column(name = "post")
	private String post;// 职务

	@Column(name = "registered_address")
	private String registeredAddress;// 注册地址

	@Column(name = "operation_qualification1")
	private String operationQualification1;// 运营资质:是否在中国证券基金协会履行基金管理人登记手续

	@Column(name = "operation_qualification2")
	private String operationQualification2;// 运营资质:基金管理人登记编号

	@Column(name = "operation_qualification3")
	private String operationQualification3;// 运营资质:机构是否有过不良诚信记录

	@Column(name = "capital_min")
	private Double capitalMin;// 管理规模-最小值

	@Column(name = "capital_max")
	private Double capitalMax;// 管理规模-最大值

	@Column(name = "team_count")
	private String teamCount;// 团队成员:团队总人数

	@Column(name = "senior_management")
	private String seniorManagement;// 具备3年以上股权投资或相关工作经验的高级管理人数

	@Column(name = "credit_unhealthy")
	private String creditUnhealthy;// 团队成员是否有过不良诚信记录

	@Column(name = "mechanism")
	private String mechanism;// 是否具备严格合理的投资决策程序、风险控制机制以及健全的财务管理制度

	@Column(name = "credit_authorization_path")
	private String creditAuthorizationPath;// 征信授权书
	
	@Column(name = "credit_authorization_name")
	private String creditAuthorizationName;// 征信授权书文件名
	
	

	@Column(name = "register_autograph_path")
	private String registerAutographPath;// 签字、加盖公章登记信息路径

	@Column(name = "register_autograph_name")
	private String registerAutographName;// 签字、加盖公章登记信息文件名
	
	@Column(name = "create_time")
	private Date createTime; // 平台注册时间
	@Column(name = "baseedit_flag")
	private String baseFlag; // 基本信息录入标识
	@Column(name = "performancedit_flag")
	private String perFlag; // 业绩录入标识
	@Column(name = "imageedit_flag")
	private String imgFlag; // 影像录入标识
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "finance_stage")
	private byte[] financeStage;// 拟投资阶段

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "finance_trade")
	private byte[] financeTrade;// 拟投资行业

	@Column(name = "logo_name")
	private String logoName; // 机构logo的名称

	@Column(name = "logo_path")
	private String logoPath; // 机构logo的上传路径
	@Column(name = "corepersonnel")
	private String corepersonnel;// 综合实力
	@Transient
	private Long countOKEvent;
	@Transient
	private String score;
	/*
	 * @Transient private String amount;
	 */
	@Transient
	private String registeTimeString;

	@Transient
	private String areaName;

	@Transient
	private String stopFlagDicname;// 启用停用标识字符串
	@Transient
	private String auditStatusDicname;// 启用审核状态字符串
	@Transient
	private String orgTypeDicname;// 机构类型字符串
	@Transient
	private String certtypeDicname;// 证件类型字符串
	@Transient
	private String capitalTypeDicname;// 资本类型字符串
	@Transient
	private String financeStageDicname;// 拟投资阶段字符串
	@Transient
	private String financeTradeDicname;// 拟投资行业字符串
	@Transient
	private String topInvestorDicname;// 总部字典值
	@Transient
	private String areaCodeDicname;// 所属地区字符串
	@Column(name = "filename")
	private String fileName;// 上传文件名称
	@Transient
	private Integer lockFlag = 0;// 机构锁定标识
	@Transient
	private String capital;         //管理规模的展示字符串
	
	
	/**
	 * 添加新字段字典值的展示
	 */
	@Transient
	private String operationQualification3Dicname; // 机构不良用户字符串

	@Transient
	private String organizationalFormDicname; // 机构组织形式字典值

	@Transient
	private String creditUnhealthyDicname; // 团队成员不良用户字符串

	@Transient
	private String mechanismDicname; // 是否具备严格合理的投资决策程序、风险控制机制以及健全的财务管理制度字典值字符串

	/*
	 * @Transient private String capitalStr;
	 */

	// LiWCH 添加管理资本量的非空校验
	/*
	 * public String getCapitalStr() { if (this.capital == null) { return ""; }
	 * else { DecimalFormat df = new DecimalFormat("######0.00"); return
	 * df.format(this.capital); } }
	 * 
	 * public void setCapitalStr(String capitalStr) { this.capitalStr =
	 * capitalStr; }
	 */

	public Integer getLockFlag() {
		return lockFlag;
	}

	public String getCorepersonnel() {
		return corepersonnel;
	}

	public void setCorepersonnel(String corepersonnel) {
		this.corepersonnel = corepersonnel;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getAreaCodeDicname() {
		return RedisGetValue
				.getValueByCode(SRRPConstant.DD_AREA, this.areaCode);
	}

	public void setAreaCodeDicname(String areaCodeDicname) {
		this.areaCodeDicname = areaCodeDicname;
	}

	public String getTopInvestorDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AREA,
				this.topInvestor);
	}

	public void setTopInvestorDicname(String topInvestorDicname) {
		this.topInvestorDicname = topInvestorDicname;
	}

	/*
	 * public String getAmount() { if (this.capital == null || this.capital ==
	 * 0) { return ""; } else { return "CNY " +
	 * DigitFormatUtil.digitFormat(this.capital); } }
	 * 
	 * public void setAmount(String amount) { this.amount = amount; }
	 */

	public String getStopFlagDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTORSTATUS,
				this.stopFlag);
	}

	public String getAuditStatusDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AUDITSTATE,
				this.auditStatus);
	}

	public String getOrgTypeDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_ORGTYPE,
				this.orgType);
	}

	public String getCerttypeDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CERTIFICATE,
				this.certtype);
	}

	public String getFinanceStageDicname() throws UnsupportedEncodingException {
		String[] values = getFinanceStage().split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			String Stage = null;
			Stage = RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTMENT,
					values[i]);
			if (Stage != null) {
				if (sb.length() > 0) {
					sb.append(",").append(Stage);
				} else {
					sb.append(Stage);
				}
			}
		}
		return sb.toString();
	}

	public void setFinanceStageDicname(String financeStageDicname) {
		this.financeStageDicname = financeStageDicname;
	}

	public String getFinanceTradeDicname() throws UnsupportedEncodingException {
		String[] values = getFinanceTrade().split(",");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < values.length; i++) {
			String Trade = null;
			Trade = RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY,
					values[i]);
			if (Trade != null) {
				if (sb.length() > 0) {
					sb.append(",").append(Trade);
				} else {
					sb.append(Trade);
				}
			}
		}
		return sb.toString();
	}

	public void setFinanceTradeDicname(String financeTradeDicname) {
		this.financeTradeDicname = financeTradeDicname;
	}

	public String getCapitalTypeDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CAPITALTYPE,
				this.capitalType);
	}

	public void setCapitalTypeDicname(String capitalTypeDicname) {
		this.capitalTypeDicname = capitalTypeDicname;
	}

	public void setStopFlagDicname(String stopFlagDicname) {
		this.stopFlagDicname = stopFlagDicname;
	}

	public void setAuditStatusDicname(String auditStatusDicname) {
		this.auditStatusDicname = auditStatusDicname;
	}

	public void setOrgTypeDicname(String orgTypeDicname) {
		this.orgTypeDicname = orgTypeDicname;
	}

	public void setCerttypeDicname(String certtypeDicname) {
		this.certtypeDicname = certtypeDicname;
	}

	public Long getCountOKEvent() {
		return countOKEvent;
	}

	public void setCountOKEvent(Long countOKEvent) {
		this.countOKEvent = countOKEvent;
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

	public String getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public String getCreditAuthorizationName() {
		return creditAuthorizationName;
	}

	public void setCreditAuthorizationName(String creditAuthorizationName) {
		this.creditAuthorizationName = creditAuthorizationName;
	}

	public String getRegisterAutographName() {
		return registerAutographName;
	}

	public void setRegisterAutographName(String registerAutographName) {
		this.registerAutographName = registerAutographName;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getFinanceTrade() throws UnsupportedEncodingException {
		if (financeTrade == null) {
			return "";
		} else {
			return new String(financeTrade, SRRPConstant.DEFAULTCHARTS);
		}
	}

	public void setFinanceTrade(String financeTrade)
			throws UnsupportedEncodingException {
		this.financeTrade = financeTrade.getBytes(SRRPConstant.DEFAULTCHARTS);
	}

	public String getFinanceStage() throws UnsupportedEncodingException {
		if (financeStage == null) {
			return "";
		} else {
			return new String(financeStage, SRRPConstant.DEFAULTCHARTS);
		}
	}

	public void setFinanceStage(String financeStage)
			throws UnsupportedEncodingException {
		this.financeStage = financeStage.getBytes(SRRPConstant.DEFAULTCHARTS);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
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

	/*
	 * public Double getCapital() { return this.capital; }
	 * 
	 * public void setCapital(Double capital) { this.capital = capital; }
	 */

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCerttype() {
		return certtype;
	}

	public void setCerttype(String certtype) {
		this.certtype = certtype;
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

	public Investor() {

	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBaseFlag() {
		return baseFlag;
	}

	public void setBaseFlag(String baseFlag) {
		this.baseFlag = baseFlag;
	}

	public String getPerFlag() {
		return perFlag;
	}

	public void setPerFlag(String perFlag) {
		this.perFlag = perFlag;
	}

	public String getImgFlag() {
		return imgFlag;
	}

	public void setImgFlag(String imgFlag) {
		this.imgFlag = imgFlag;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getRegisteTimeString() {
		if(this.registeTime!=null){
			return this.registeTime.toString();
		}else{
			return "";
		}
		
	}

	public void setRegisteTimeString(String registeTimeString) {
		this.registeTimeString = registeTimeString;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getOrganizationalForm() {
		return organizationalForm;
	}

	public void setOrganizationalForm(String organizationalForm) {
		this.organizationalForm = organizationalForm;
	}

	public String getAmacRecord() {
		return amacRecord;
	}

	public void setAmacRecord(String amacRecord) {
		this.amacRecord = amacRecord;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getManageOrg() {
		return manageOrg;
	}

	public void setManageOrg(String manageOrg) {
		this.manageOrg = manageOrg;
	}

	public String getTrusteeship() {
		return trusteeship;
	}

	public void setTrusteeship(String trusteeship) {
		this.trusteeship = trusteeship;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getOperationQualification1() {
		return operationQualification1;
	}

	public void setOperationQualification1(String operationQualification1) {
		this.operationQualification1 = operationQualification1;
	}

	public String getOperationQualification2() {
		return operationQualification2;
	}

	public void setOperationQualification2(String operationQualification2) {
		this.operationQualification2 = operationQualification2;
	}

	public String getOperationQualification3() {
		return operationQualification3;
	}

	public void setOperationQualification3(String operationQualification3) {
		this.operationQualification3 = operationQualification3;
	}

	public String getTeamCount() {
		return teamCount;
	}

	public void setTeamCount(String teamCount) {
		this.teamCount = teamCount;
	}

	public String getSeniorManagement() {
		return seniorManagement;
	}

	public void setSeniorManagement(String seniorManagement) {
		this.seniorManagement = seniorManagement;
	}

	public String getCreditUnhealthy() {
		return creditUnhealthy;
	}

	public void setCreditUnhealthy(String creditUnhealthy) {
		this.creditUnhealthy = creditUnhealthy;
	}

	public String getMechanism() {
		return mechanism;
	}

	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}

	public String getCreditAuthorizationPath() {
		return creditAuthorizationPath;
	}

	public void setCreditAuthorizationPath(String creditAuthorizationPath) {
		this.creditAuthorizationPath = creditAuthorizationPath;
	}

	public String getRegisterAutographPath() {
		return registerAutographPath;
	}

	public void setRegisterAutographPath(String registerAutographPath) {
		this.registerAutographPath = registerAutographPath;
	}

	public void setFinanceStage(byte[] financeStage) {
		this.financeStage = financeStage;
	}

	public void setFinanceTrade(byte[] financeTrade) {
		this.financeTrade = financeTrade;
	}

	public Double getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Double registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public Double getPaidCapital() {
		return paidCapital;
	}

	public void setPaidCapital(Double paidCapital) {
		this.paidCapital = paidCapital;
	}

	public Double getCapitalMin() {
		return capitalMin;
	}

	public void setCapitalMin(Double capitalMin) {
		this.capitalMin = capitalMin;
	}

	public Double getCapitalMax() {
		return capitalMax;
	}

	public void setCapitalMax(Double capitalMax) {
		this.capitalMax = capitalMax;
	}

	public String getOperationQualification3Dicname() {
		return operationQualification3Dicname;
	}

	public void setOperationQualification3Dicname(
			String operationQualification3Dicname) {
		this.operationQualification3Dicname = operationQualification3Dicname;
	}

	public String getOrganizationalFormDicname() {
		return organizationalFormDicname;
	}

	public void setOrganizationalFormDicname(String organizationalFormDicname) {
		this.organizationalFormDicname = organizationalFormDicname;
	}

	public String getCreditUnhealthyDicname() {
		return creditUnhealthyDicname;
	}

	public void setCreditUnhealthyDicname(String creditUnhealthyDicname) {
		this.creditUnhealthyDicname = creditUnhealthyDicname;
	}

	public String getMechanismDicname() {
		return mechanismDicname;
	}

	public void setMechanismDicname(String mechanismDicname) {
		this.mechanismDicname = mechanismDicname;
	}

	public String getCapital() {
		String capital = "";
		if (this.capitalMin == null || "".equals(this.capitalMin)) {
			capital = "0";
		} else {
			capital = DigitFormatUtil.digitFormat(this.capitalMin)
					.toString();
		}
		if (this.capitalMax == null || "".equals(this.capitalMax)) {
			capital += "~" + "0";
		} else {
			capital += "~"
					+ DigitFormatUtil.digitFormat(this.capitalMax)
							.toString();
		}
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

}

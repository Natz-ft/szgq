package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 机构子账户信息
 * 
 * @ClassName: investorSubaccount
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hugt
 * @date 2018年3月15日 上午9:17:15
 *
 */
@Data
@Entity
@Table(name = "rp_investor_subaccount")
public class InvestorSubaccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1492693612539928063L;

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	/**
	 * 机构表中所出现的列；
	 */
	@Id
	@Column(name = "subac_id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String subac_id;// id

	@Column(name = "user_id")
	private String userId;// 投资机构ID

	@Column(name = "subac_name")
	private String subacName;// 投资机构名称

	@Column(name = "amac_record")
	private String amacRecord;// 中基协备案号

	@Column(name = "subac_type")
	private String subacType;// 基金类型

	@Column(name = "regist_address")
	private String registAddress;// 基金组织形式

	@Column(name = "currency")
	private String currency;// 币种

	@Column(name = "capital_min")
	private Double capitalMin;// 管理规模-最小值

	@Column(name = "capital_max")
	private Double capitalMax;// 管理规模-最大值

	@Column(name = "finance_stage")
	private String financeStage;// 拟投资阶段

	@Column(name = "finance_trade")
	private String financeTrade;// 拟投资行业

	@Temporal(TemporalType.DATE)
	@Column(name = "registe_time")
	private Date registeTime;// 成立日期

	@Temporal(TemporalType.DATE)
	@Column(name = "credte_time")
	private Date createTime;// 成立日期
	@Column(name = "rel_name")
	private String relName;// 联系人
	@Column(name = "rel_phone")
	private String relPhone;// 地址
	@Column(name = "email")
	private String email;// 邮箱
	@Column(name = "trusteeship")
	private String trusteeship;// 托管机构
	@Column(name = "trusteeship_radio")
	private String trusteeshipRadio;// 判断是否有托管机构

	@Column(name = "investment_projects")
	private Double investmentProjects;// 投资项目数量

	@Column(name = "cumulative_investment")
	private Double cumulativeInvestment;// 累计投资金额

	@Column(name = "ci_currency")
	private String ciCurrency;// 累计投资金额币种
	
	@Column(name = "implement_exit_project")
	private Double implementExitProject;// 实现退出项目数
	
	@Column(name = "area_province")
	private String areaProvince; //区域省代码
	
	@Column(name = "area_city")
	private String areaCity; // 区域市代码
	
	@Column(name = "area_county")
	private String areaCounty; // 区域区县代码
	@Column(name = "certno")
	private String certno;// 证件号码

	@Column(name = "certtype")
	private String certtype;// 证件类型
	
	@Column(name = "legal_representative")
	private String legalRepresentative;// 法定代表人

	@Column(name = "legal_representative_call")
	private String legalRepresentativeCall;// 法定代表人
	
	@Column(name = "credit_authorization_path")
	private String creditAuthorizationPath;// 征信授权书

	@Column(name = "credit_authorization_name")
	private String creditAuthorizationName;// 征信授权书文件名
	
	@Column(name = "license_path")
    private String licensePath;// 营业执照路径
    @Column(name = "license_name")
    private String fileName;// 上传文件名称
    
    
    
    public String getLicensePath() {
        return licensePath;
    }

    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getLegalRepresentativeCall() {
		return legalRepresentativeCall;
	}

	public void setLegalRepresentativeCall(String legalRepresentativeCall) {
		this.legalRepresentativeCall = legalRepresentativeCall;
	}

	public String getCreditAuthorizationPath() {
		return creditAuthorizationPath;
	}

	public void setCreditAuthorizationPath(String creditAuthorizationPath) {
		this.creditAuthorizationPath = creditAuthorizationPath;
	}

	public String getCreditAuthorizationName() {
		return creditAuthorizationName;
	}

	public void setCreditAuthorizationName(String creditAuthorizationName) {
		this.creditAuthorizationName = creditAuthorizationName;
	}

	public String getCerttype() {
		return certtype;
	}

	public void setCerttype(String certtype) {
		this.certtype = certtype;
	}

	public String getAreaProvince() {
		return areaProvince;
	}

	public void setAreaProvince(String areaProvince) {
		this.areaProvince = areaProvince;
	}

	public String getAreaCity() {
		return areaCity;
	}

	public void setAreaCity(String areaCity) {
		this.areaCity = areaCity;
	}

	public String getAreaCounty() {
		return areaCounty;
	}

	public void setAreaCounty(String areaCounty) {
		this.areaCounty = areaCounty;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	@Transient
	private String userName;// 用户账号

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubac_id() {
		return subac_id;
	}

	public void setSubac_id(String subac_id) {
		this.subac_id = subac_id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubacName() {
		return subacName;
	}

	public void setSubacName(String subacName) {
		this.subacName = subacName;
	}

	public String getAmacRecord() {
		return amacRecord;
	}

	public void setAmacRecord(String amacRecord) {
		this.amacRecord = amacRecord;
	}

	public String getSubacType() {
		return subacType;
	}

	public void setSubacType(String subacType) {
		this.subacType = subacType;
	}

	public String getRegistAddress() {
		return registAddress;
	}

	public void setRegistAddress(String registAddress) {
		this.registAddress = registAddress;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public Date getRegisteTime() {
		return registeTime;
	}

	public void setRegisteTime(Date registeTime) {
		this.registeTime = registeTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTrusteeship() {
		return trusteeship;
	}

	public void setTrusteeship(String trusteeship) {
		this.trusteeship = trusteeship;
	}

	public String getTrusteeshipRadio() {
		return trusteeshipRadio;
	}

	public void setTrusteeshipRadio(String trusteeshipRadio) {
		this.trusteeshipRadio = trusteeshipRadio;
	}

	public Double getInvestmentProjects()
	{
		return investmentProjects;
	}

	public void setInvestmentProjects(Double investmentProjects)
	{
		this.investmentProjects = investmentProjects;
	}

	public Double getCumulativeInvestment()
	{
		return cumulativeInvestment;
	}

	public void setCumulativeInvestment(Double cumulativeInvestment)
	{
		this.cumulativeInvestment = cumulativeInvestment;
	}

	public String getCiCurrency()
	{
		return ciCurrency;
	}

	public void setCiCurrency(String ciCurrency)
	{
		this.ciCurrency = ciCurrency;
	}

	public Double getImplementExitProject()
	{
		return implementExitProject;
	}

	public void setImplementExitProject(Double implementExitProject)
	{
		this.implementExitProject = implementExitProject;
	}

	
	
}

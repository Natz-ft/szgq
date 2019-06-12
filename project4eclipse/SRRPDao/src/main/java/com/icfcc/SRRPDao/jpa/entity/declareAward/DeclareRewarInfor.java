package com.icfcc.SRRPDao.jpa.entity.declareAward;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

import lombok.Data;

/**
 * 奖励申报表
 * 
 * @ClassName: DeclareRewarInfor
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hugt
 * @date 2018年3月15日 上午9:17:15
 *
 */
@Data
@Entity
@Table(name = "rp_declare_award")
public class DeclareRewarInfor implements Serializable {
	/**
	 * UUID
	 */
	private static final long serialVersionUID = 4462652635041052589L;

	@Id
	@Column(name = "declare_id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String declareId;//奖励申报id

	@Column(name = "investor_id")
	private String investorId;// 管理机构ID

	@Column(name = "declare_name")
	private String declareName;// 申报单位

	@Column(name = "certtype")
	private String certtype;//申报单位证件类型

	@Column(name = "certno")
	private String certno;//申报单位证件号码

	@Column(name = "company_address")
	private String companyAddress;//申报区域

	@Column(name = "rel_name")
	private String relName;// 联系人姓名

	@Column(name = "rel_phone")
	private String relPhone;// 联系人手机号

	@Column(name = "bank_deposit")
	private String bankDeposit;//开户银行

	@Column(name = "bank_account")
	private String bankAccount;//银行账号
	
	@Column(name = "declare_status")
	private String declareStatus;//申报状态

	@Column(name = "declare_create_time")
	private Date declareCreateTime;//申报时间
	
	@Temporal(TemporalType.DATE)
	@Column(name = "declare_begin_time")
	private Date declareBeginTime;//申报开始期间
	
	@Temporal(TemporalType.DATE)
	@Column(name = "declare_end_time")
	private Date declareEndTime;//申报结束期间
	
	@Column(name = "declare_user")
	private String declareUser;// 申报用户

	@Column(name = "declare_flag")
	private String declareFlag;//申报有效标识
	
	@Column(name = "invest_area_province")
	private String areaProvince; //区域省代码
	
	@Column(name = "invest_area_city")
	private String areaCity; // 区域市代码
	
	@Column(name = "invest_area_county")
	private String areaCounty; // 区域区县代码
	
	@Transient
	private List<DeclareRewarReport> declareRewarReport;//投资记录
	
	@Transient
	private List<DeclareRewarReportRecord> declareRewarReportRecord;//投资记录
	
	@Transient
	private String declareRewarReportJson;//投资记录json字符串
	@Transient
	private String reareaDicname;// 所属区域字典值
	
	@Transient
	private String declareStatusStr;//申报状态

	@Transient
	private String declareTimeStr;//申报状态
	
	@Transient
	private String investorAreaName;
	public String getDeclareId() {
		return declareId;
	}
	public void setDeclareId(String declareId) {
		this.declareId = declareId;
	}
	public String getInvestorId() {
		return investorId;
	}
	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}
	
	public String getDeclareRewarReportJson() {
		return declareRewarReportJson;
	}
	public void setDeclareRewarReportJson(String declareRewarReportJson) {
		this.declareRewarReportJson = declareRewarReportJson;
	}
	public String getDeclareName() {
		return declareName;
	}
	public void setDeclareName(String declareName) {
		this.declareName = declareName;
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
	
	
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getRelName() {
		return relName;
	}
	public void setRelName(String relName) {
		this.relName = relName;
	}
	
	public List<DeclareRewarReport> getDeclareRewarReport() {
		return declareRewarReport;
	}
	public void setDeclareRewarReport(List<DeclareRewarReport> declareRewarReport) {
		this.declareRewarReport = declareRewarReport;
	}
	public String getRelPhone() {
		return relPhone;
	}
	public void setRelPhone(String relPhone) {
		this.relPhone = relPhone;
	}
	public String getBankDeposit() {
		return bankDeposit;
	}
	public void setBankDeposit(String bankDeposit) {
		this.bankDeposit = bankDeposit;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getDeclareStatus() {
		return declareStatus;
	}
	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
	}

	public Date getDeclareCreateTime() {
		return declareCreateTime;
	}
	public void setDeclareCreateTime(Date declareCreateTime) {
		this.declareCreateTime = declareCreateTime;
	}
	public Date getDeclareBeginTime() {
		return declareBeginTime;
	}
	public void setDeclareBeginTime(Date declareBeginTime) {
		this.declareBeginTime = declareBeginTime;
	}
	public Date getDeclareEndTime() {
		return declareEndTime;
	}
	public void setDeclareEndTime(Date declareEndTime) {
		this.declareEndTime = declareEndTime;
	}
	
	public String getDeclareTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if(declareBeginTime!=null && declareEndTime!=null){
			declareTimeStr=sdf.format(declareBeginTime)+"～"+sdf.format(declareEndTime);
		}
		return declareTimeStr;
	}
	public void setDeclareTimeStr(String declareTimeStr) {
		this.declareTimeStr = declareTimeStr;
	}
	public String getDeclareUser() {
		return declareUser;
	}
	public void setDeclareUser(String declareUser) {
		this.declareUser = declareUser;
	}
	public String getDeclareFlag() {
		return declareFlag;
	}
	public void setDeclareFlag(String declareFlag) {
		this.declareFlag = declareFlag;
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
	public String getReareaDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, this.companyAddress);
	}

	public void setReareaDicname(String reareaDicname) {
		this.reareaDicname = reareaDicname;
	}
	public String getDeclareStatusStr() {
		String str ="";
		if(this.declareStatus!=null && !"".equals(this.declareStatus)){
			if("00".equals(this.declareStatus)){
				str="未接收";
			}else if("01".equals(this.declareStatus)){
				str="已接收";

			}else{
				str="已撤销";
			}
		}
				
		return str;
	}
	public void setDeclareStatusStr(String declareStatusStr) {
		this.declareStatusStr = declareStatusStr;
	}
	public String getInvestorAreaName() {
		investorAreaName=RedisGetValue.getAreaValueByCode(SRRPConstant.AREA_Province, this.areaProvince)+RedisGetValue.getAreaValueByCode(SRRPConstant.AREA_City, this.areaCity)+RedisGetValue.getAreaValueByCode(SRRPConstant.AREA_County, this.areaCounty);
		return investorAreaName;
	}
	public void setInvestorAreaName(String investorAreaName) {
		this.investorAreaName = investorAreaName;
	}
	public List<DeclareRewarReportRecord> getDeclareRewarReportRecord() {
		return declareRewarReportRecord;
	}
	public void setDeclareRewarReportRecord(List<DeclareRewarReportRecord> declareRewarReportRecord) {
		this.declareRewarReportRecord = declareRewarReportRecord;
	}
	
	
}

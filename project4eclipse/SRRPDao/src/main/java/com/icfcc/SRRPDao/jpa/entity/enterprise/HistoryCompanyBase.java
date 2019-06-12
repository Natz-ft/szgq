package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * 企业信息的审核历史表
 * 
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "rp_his_company_base")
public class HistoryCompanyBase implements Serializable {

	private static final long serialVersionUID = 1238940248051284486L;

	@Id
	@Column(name = "HIS_ID", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String hisId; // 历史信息的id

	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;// 企业信息的id

	@Column(name = "NAME", length = 50)
	private String name;// 企业名称

	@Column(name = "CODETYPE", length = 4)
	private String codetype;// 证件类型

	@Column(name = "CODE", length = 20)
	private String code;// 证件号码

	@Column(name = "REGCAPITAL", length = 12)
	private Double regcapital;// 注册资本

	@Temporal(TemporalType.DATE)
	@Column(name = "REDATE")
	private Date redate;// 注册时间

	@Column(name = "REAREA", length = 50)
	private String rearea;// 所属区域

	@Column(name = "REGISTAREA", length =256)
    private String registArea;//企业注册地址
	
	@Column(name = "CONTACTTYPE", length = 10)
	private String contacttype;// 联系方式

	@Column(name = "LEGALNAME", length = 10)
	private String legalName;// 法定代表人姓名

	@Column(name = "LEGALCAL", length = 15)
	private String legalCal;// 法定代表人手机号

	@Column(name = "CONTACNAME", length = 10)
	private String contacName;// 联系人姓名

	@Column(name = "CONTACCAL", length = 15)
	private String contacCal;// 联系人手机号

	@Column(name = "OPERTIME", length = 10)
	private Date opertime;// 操作时间

	@Column(name = "STATUS", length = 10)
	private String auditStatus;// 审核状态

	@Column(name = "AUDIT_ID", length = 15)
	private String auditId;// 审核人的ID

	@Column(name = "AUDIT_TIME", length = 10)
	private Date auditTime;// 审核时间

	@Column(name = "AUDIT_CONTENT", length = 15)
	private String auditContent;// 审核内容

	@Column(name="STOP_FLAG",length = 4)
	private String stopFlag;
	
	
	public Date getOpertime() {
		return opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}


	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}

	public String getHisId() {
		return hisId;
	}

	public void setHisId(String hisId) {
		this.hisId = hisId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodetype() {
		return codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getRegcapital() {
		return regcapital;
	}

	public void setRegcapital(Double regcapital) {
		this.regcapital = regcapital;
	}

	public Date getRedate() {
		return redate;
	}

	public void setRedate(Date redate) {
		this.redate = redate;
	}

	public String getRearea() {
		return rearea;
	}

	public void setRearea(String rearea) {
		this.rearea = rearea;
	}

	
	public String getRegistArea() {
		return registArea;
	}

	public void setRegistArea(String registArea) {
		this.registArea = registArea;
	}

	public String getContacttype() {
		return contacttype;
	}

	public void setContacttype(String contacttype) {
		this.contacttype = contacttype;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalCal() {
		return legalCal;
	}

	public void setLegalCal(String legalCal) {
		this.legalCal = legalCal;
	}

	public String getContacName() {
		return contacName;
	}

	public void setContacName(String contacName) {
		this.contacName = contacName;
	}

	public String getContacCal() {
		return contacCal;
	}

	public void setContacCal(String contacCal) {
		this.contacCal = contacCal;
	}

}

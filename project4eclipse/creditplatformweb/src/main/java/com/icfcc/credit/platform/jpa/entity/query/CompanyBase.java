package com.icfcc.credit.platform.jpa.entity.query;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.constants.VipCont;



/**
 * 企业基本信息实体类
 * 
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name = "rp_company_base")
public class CompanyBase implements Serializable {

	private static final long serialVersionUID = 5082323220869991788L;

	@Id
	@Column(name = "ENTERPRISE_ID", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String enterpriseId;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "CODETYPE", length = 4)
	private String codetype;

	@Transient
	private String codetypeDicname;// 证件代码类型字典值

	@Column(name = "CODE", length = 20)
	private String code;

	@Column(name = "REGCAPITAL", precision = 12, scale = 2)
	private Double regcapital;

	@Temporal(TemporalType.DATE)
	@Column(name = "REDATE")
	private Date redate;

	@Column(name = "REAREA", length = 50)
	private String rearea;

	@Transient
	private String reareaDicname;// 所属区域字典值

	@Column(name = "CONTACTTYPE", length = 10)
	private String contacttype;

	@Column(name = "LEGALNAME", length = 10)
	private String legalName;

	@Column(name = "LEGALCAL", length = 15)
	private String legalCal;

	@Column(name = "CONTACNAME", length = 10)
	private String contacName;

	@Column(name = "CONTACCAL", length = 15)
	private String contacCal;
	
	@Column(name = "STOCKNAME", length = 10)
	private String stockName;

	@Column(name = "STOCKCAL", length = 15)
	private String stockCal;
	
	@Column(name = "REGISTAREA", length =256)
    private String registArea;//企业注册地址
	
	@Column(name = "STOP_FLAG", length = 10)
	private String stopFlag="1";// 启用停用标识

	@Column(name = "AUDIT_STATUS", length = 15)
	private String auditStatus="1";// 审核状态

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

	public String getCodetypeDicname() {
		return codetypeDicname;
	}

	public void setCodetypeDicname(String codetypeDicname) {
		this.codetypeDicname = codetypeDicname;
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

	public String getReareaDicname() {
		return VipCont.getValueByCode("dd:area",this.rearea);
	}

	public void setReareaDicname(String reareaDicname) {
		this.reareaDicname = reareaDicname;
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

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockCal() {
		return stockCal;
	}

	public void setStockCal(String stockCal) {
		this.stockCal = stockCal;
	}

	public String getRegistArea() {
		return registArea;
	}

	public void setRegistArea(String registArea) {
		this.registArea = registArea;
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

	

}

package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

import lombok.Data;

/**
 * 审核记录表实体类
 * 
 * @ClassName: InvestorAuditRecordDTO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author daiyx
 * @date 2017年9月18日 下午5:41:24
 *
 */
@Entity
@Table(name = "rp_investor_audit_record")
public class InvestorAuditRecord implements Serializable {

	private static final long serialVersionUID = -2654740434445443160L;

	@Id
	@Column(name = "RECORD_ID", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String recordId;// 审核记录ID

	@Column(name = "INVESTOR_ID")
	private String investorId;// 投资机构id
	
	@Column(name = "ID_TYPE")
	private String idType;//

	@Column(name = "AUDITOR_ID")
	private String auditorId;// 操作人ID

	@Column(name = "AUDITOR_NAME")
	private String auditorName;// 操作人姓名
	
	@Column(name = "AUDIT_TIME")
	private Date auditTime;// 审核时间

	@Column(name = "AUDIT_RESULT")
	private String auditResult;// 审核结果

	@Column(name = "AUDIT_CONTENT")
	private String auditContent;// 内容
    
	@Transient
	private String auditResultDicname;// 审核结果字符串
	
	@Transient
	private String auditTimeStr;// 审核时间字符串
	
	public String getAuditResultDicname() {
		return  RedisGetValue.getValueByCode(SRRPConstant.DD_AUDITSTATE, this.auditResult);
	}

	public void setAuditResultDicname(String auditResultDicname) {
		this.auditResultDicname = auditResultDicname;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}
	
	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditTimeStr() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.format(this.auditTime); 
		return sdf.format(this.auditTime);
	}

	public void setAuditTimeStr(String auditTimeStr) {
		this.auditTimeStr = auditTimeStr;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}


}

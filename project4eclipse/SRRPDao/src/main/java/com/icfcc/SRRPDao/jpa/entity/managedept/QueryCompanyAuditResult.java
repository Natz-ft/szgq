package com.icfcc.SRRPDao.jpa.entity.managedept;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

/**
 * 查询企业审核记录结果的bean类
 * @author Liwch
 *
 */
@Data
@Entity
public class QueryCompanyAuditResult implements Serializable {

	
	private static final long serialVersionUID = -2016300162506480191L;

	@Id
	@Column(name = "his_id")
	private String hisId;;// 审核记录id
	
	@Column(name = "enterprise_id")
	private String enterpriseId;;// 企业id
	
	@Column(name = "AUDIT_ID", length = 15)
	private String auditId;// 审核人的ID
	
	@Column(name = "AUDIT_CONTENT", length = 15)
	private String auditContent;// 审核内容
	
	@Column(name = "AUDIT_TIME", length = 10)
	private Date auditTime;// 审核时间
	
	@Column(name = "STATUS", length = 10)
	private String auditStatus;// 审核状态
	
	@Column(name = "user_name", length = 10)
	private String username;// 审核人姓名

	
	@Transient
	private String auditStatusStr;// 审核状态的字符串
	
	@Transient
	private String auditTimeStr;// 审核时间的字符串
	
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

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuditStatusStr() {
		return auditStatusStr;
	}

	public void setAuditStatusStr(String auditStatusStr) {
		this.auditStatusStr = auditStatusStr;
	}

	public String getAuditTimeStr() {
		return auditTimeStr;
	}

	public void setAuditTimeStr(String auditTimeStr) {
		this.auditTimeStr = auditTimeStr;
	}
	
	
}

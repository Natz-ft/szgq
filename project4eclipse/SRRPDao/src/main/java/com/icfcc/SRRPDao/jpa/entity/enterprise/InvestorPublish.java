package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;



@Entity
@Table(name = "rp_investor_publish")
public class InvestorPublish implements Serializable {

	private static final long serialVersionUID = 2769902475353317255L;

	@Id
	@Column(name = "publish_id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String publishId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "orgname")
	private String orgName;

	@Column(name = "enterprise_id")
	private String enterpriseId;

	@Column(name = "event_id")
	private String eventId;

	@Lob
	@Column(name = "infocontent")
	private byte[] infoContent;

	@Column(name = "infotitle")
	private String infoTitle;

	@Column(name = "infotype")
	private String infoType;

	private String operuser;

	@Temporal(TemporalType.DATE)
	@Column(name = "publish_time")
	private Date publishTime;

	@Column(name = "file_name")
	private String publishFileName;
	
	@Column(name = "file_path")
	private String publishFilePath;
	
	@Transient
	private FinacingEvent finacingEvent;

	public InvestorPublish() {
	}

	public String getEnterpriseId() {
		return this.enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEventId() {
		return this.eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getOperuser() {
		return this.operuser;
	}

	public void setOperuser(String operuser) {
		this.operuser = operuser;
	}

	public Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getInfoContent() throws UnsupportedEncodingException {
		if (infoContent == null) {
            return "";
        } else {
            return new String(infoContent, SRRPConstant.DEFAULTCHARTS);
        }
	}

	public void setInfoContent(String infoContent) throws UnsupportedEncodingException {
		this.infoContent = infoContent.getBytes(SRRPConstant.DEFAULTCHARTS);
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getPublishId() {
		return publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public FinacingEvent getFinacingEvent() {
		return finacingEvent;
	}

	public void setFinacingEvent(FinacingEvent finacingEvent) {
		this.finacingEvent = finacingEvent;
	}

	public String getPublishFileName() {
		return publishFileName;
	}

	public void setPublishFileName(String publishFileName) {
		this.publishFileName = publishFileName;
	}

	public String getPublishFilePath() {
		return publishFilePath;
	}

	public void setPublishFilePath(String publishFilePath) {
		this.publishFilePath = publishFilePath;
	}

}
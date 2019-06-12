/**
 * 
 */
package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author lijj 给主管部门用的发布信息bean
 */
@Entity
public class PublishBeanForCharge implements Serializable {

	private static final long serialVersionUID = -4182908222793487049L;

	@Id
	@Column(name = "publish_id")
	private String publishId;
	@Column(name = "enterprise_id")
	private String enterpriseId;
	@Column(name = "enterprise_name")
	private String enterpriseName;
	@Column(name = "project_name")
	private String projectName;
	@Column(name = "infotitle")
	private String infoTitle;
	@Column(name = "infotype")
	private String infoType;
	@Column(name = "event_id")
	private String eventId;

	@Temporal(TemporalType.DATE)
	@Column(name = "publish_time")
	private Date publishTime;

	public String getPublishId() {
		return publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

}

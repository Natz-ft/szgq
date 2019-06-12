package com.icfcc.credit.platform.jpa.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * SysOrgConfig entity. @author MyEclipse Persistence Tools
 * 
 */
@Data
@Entity
@Table(name = "system_org_config")
public class PlatformOrgConfig implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String appid;
	private String pageUrl;
	private String pageType;
	private Date createTime;
	private String appName;
	private String startFlag;

	// Constructors

	/** default constructor */
	public PlatformOrgConfig() {
	}

	/** minimal constructor */
	public PlatformOrgConfig(String id, String appid) {
		this.id = id;
		this.appid = appid;
	}

	/** full constructor */
	public PlatformOrgConfig(String id, String appid, String pageUrl,
			String pageType, Date createTime, String appName,
			String startFlag) {
		this.id = id;
		this.appid = appid;
		this.pageUrl = pageUrl;
		this.pageType = pageType;
		this.createTime = createTime;
		this.appName = appName;
		this.startFlag = startFlag;
	}

	// Property accessors
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "APPID", nullable = false, length = 2)
	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	@Column(name = "PAGE_URL", length = 120)
	public String getPageUrl() {
		return this.pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	@Column(name = "PAGE_TYPE", length = 2)
	public String getPageType() {
		return this.pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "APP_NAME", length = 128)
	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name = "START_FLAG", length = 1)
	public String getStartFlag() {
		return this.startFlag;
	}

	public void setStartFlag(String startFlag) {
		this.startFlag = startFlag;
	}

}
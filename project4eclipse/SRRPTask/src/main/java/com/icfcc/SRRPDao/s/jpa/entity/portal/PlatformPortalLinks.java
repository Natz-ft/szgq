/**
 * 
 */
package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lijj
 *
 */
@Entity
@Table(name = "platform_portal_links")
public class PlatformPortalLinks implements Serializable {

	private static final long serialVersionUID = -7641545606770467233L;

	@Id
	@Column(name = "links_id")
	private Long linkId;
	@Column(name = "LINKS_NEME")
	private String linkName;
	@Column(name = "LINKS_URL")
	private String linkUrl;
	@Column(name = "LINKS_PICTURE")
	private String picture;
	@Column(name = "LINKS_CREATE_USER")
	private String createUser;
	@Column(name = "LINKS_CREATE_TIME")
	private Date createTime;

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

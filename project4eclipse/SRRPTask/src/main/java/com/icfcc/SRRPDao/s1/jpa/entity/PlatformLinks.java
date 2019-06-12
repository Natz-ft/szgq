package com.icfcc.SRRPDao.s1.jpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import lombok.Data;

/**
 * 
* @ClassName: PlatformfriendlyLinks
* @Description: TODO(<友情链接实体类>)
* @author hugt
* @date 2017年9月14日 下午5:55:39
*
 */
@Entity
@Table(name="platform_friendly_links")
public class PlatformLinks implements Serializable {
	/**
	 * 友情链接实体类
	 */
	private static final long serialVersionUID = -7785700936402013614L;
	//主键
	@Id
	@Column(name="LINKS_ID")
	private Long linkId;
	//友情链接
	@Column(name="LINKS_NEME")
	private String linkName;
	//友情链接路径
	@Column(name="LINKS_URL")
	private String linkUrl;
	//友情链接logo
	@Column(name="LINKS_LOGO")
	private String picture;
	//创建用户
	@Column(name="LINKS_CREATE_USER")
	private String createUser;
	//创建时间
	@Column(name="LINKS_CREATE_TIME")
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}

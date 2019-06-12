package com.icfcc.SRRPDao.jpa.entity.platformContent;

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
* @ClassName: PlatformBusinessPartner
* @Description: TODO(合作伙伴实体类)
* @author hugt
* @date 2017年9月14日 下午5:37:25
 */
@Entity
@Table(name="platform_business_partner")
public class PlatformBusinessPartner implements Serializable {
	
	private static final long serialVersionUID = -2757600314562534828L;

	//主键
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PARTNET_ID")
	private Long id;
	//合作伙伴名称
	@Column(name="PARTNET_NEME")
	private String name;
	//合作伙伴的logo
	@Column(name="PARTNET_LOGO")
	private String logo;
	//合作伙伴链接
	@Column(name="PARTNET_LINK")
	private String link;
	//创建者
	@Column(name="PARTNET_CREATE_USER")
	private String createUser;
	//创建时间
	@Column(name="PARTNET_CREATE_TIME")
	private Date createTime;
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public void setId(Long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}

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
* @ClassName: PlatformQuickMenu
* @Description: TODO(快捷菜单类)
* @author hugt
* @date 2017年9月14日 下午6:11:08
*
 */
@Entity
@Table(name="platform_quickmenu")
public class PlatformQuickMenu implements Serializable {

	
	
	private static final long serialVersionUID = -1898128901247616041L;
	/**
	 * 友情链接实体类
	 */
	//主键
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="QUICKMENU_ID")
	private Long id;
	//菜单名称
	@Column(name="QUICKMENU_NAME")
	private String name;
	//菜单链接
	@Column(name="QUICKMENU_URL")
	private String url;
	//菜单logo
	@Column(name="QUICKMENU_LOGO")
	private String logo;
	//创建发布人
	@Column(name="QUICKMENU_CREATE_USER")
	private String createUser;
	//创建发布时间
	@Column(name="QUICKMENU_CREATE_TIME")
	private Date createTime;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
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

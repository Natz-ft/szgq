package com.icfcc.SRRPDao.jpa.entity.platformSystem;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
* @ClassName: PlatformRole
* @Description: TODO(角色实体类)
* @author hugt
* @date 2017年9月14日 下午6:11:26
*
 */
@Entity
@Table(name = "platform_role")
public class PlatformRole implements Serializable
{
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
     * 注释
     */
    private static final long serialVersionUID = 3260110878096913809L;
    
    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ROLE_NAME")
    private String name;
    
    @Column(name = "ROLE_DESC")
    private String description;
    
    // 创建人
    @Column(name = "CREATE_USER")
    private String createUser;
    
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
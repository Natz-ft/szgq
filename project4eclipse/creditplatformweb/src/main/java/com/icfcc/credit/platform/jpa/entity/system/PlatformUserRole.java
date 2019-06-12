package com.icfcc.credit.platform.jpa.entity.system;

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
* @ClassName: PlatformUserRole
* @Description: TODO(这里用一句话描述这个类的作用)
* @author hugt
* @date 2017年9月14日 下午6:45:49
*
 */
@Data
@Entity
@Table(name = "platform_user_role_rel")
public class PlatformUserRole implements Serializable
{
    public Long getRelId() {
		return relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	private static final long serialVersionUID = -5642256333944208676L;
    
    // 注释
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REL_ID")
    private Long relId;
    // 注释
    @Column(name = "USER_ID")
    private String userId;
    
    // 注释
    @Column(name = "ROLE_ID")
    private Long roleId;
    
    // 注释
    @Column(name = "CREATE_USER")
    private String createUser;
    
    // 注释
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    // 注释
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    
    public Long getRoleId()
    {
        return roleId;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
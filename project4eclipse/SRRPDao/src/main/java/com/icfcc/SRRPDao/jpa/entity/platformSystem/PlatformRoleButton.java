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
* @ClassName: PlatformRoleButton
* @Description: TODO(按钮权限关联实体类)
* @author hugt
* @date 2017年9月14日 下午6:11:49
*
 */
@Entity
@Table(name = "platform_role_button_rel")
public class PlatformRoleButton implements Serializable
{
    public Long getRelId() {
		return relId;
	}

	public void setRelId(Long relId) {
		this.relId = relId;
	}

	public String getButtonCode() {
		return buttonCode;
	}

	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	private static final long serialVersionUID = 123532423524231L;
    
    // 注释
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REL_ID")
    private Long relId;
    
    // 注释
    @Column(name = "BUTTON_CODE")
    private String buttonCode;
    
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
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}

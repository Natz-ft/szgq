package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
* @ClassName: PlatformUser
* @Description: TODO(用户信息实体类)
* @author hugt
* @date 2017年9月14日 下午6:45:07
*
 */
@Entity
@Table(name = "system_user")
public class PlatformUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3903684524819260188L;


	@Id
    @Column(name = "USER_ID")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
    private String id;
	
	@Column(name = "USER_NAME")
    private String username;
	
	@Column(name = "NICK_NAME")
    private String nickname;
	
	@Column(name = "USER_PWD")
    private String password;
	
	@Transient
    @JsonIgnore
    private String plainPassword;
	
	@Column(name = "USER_SALT")
    private String salt;
	
	@Column(name = "USER_TYPE")
    private String type;
	
	@Column(name = "USER_EMAIL")
    private String email;
	
	@Column(name = "ORG_ID")
    private String org;
	
	@Column(name = "DEPT_ID")
    private String dept;
	
	@Column(name = "CREATE_TIME")
    private Date createTime;
	
	@Column(name = "CREATE_USERID")
    private String createUser;
	
	@Column(name = "UPDATE_TIME")
    private Date updateTime;
	
	@Column(name = "UPDATE_UID")
    private String updateUser;
	
	@Column(name = "PWD_UPDATE_TIME")
    private Date pwdUpdateTime;
	
	@Column(name = "RESET_FLAG")
    private Integer resetFlag=0;
	
	@Column(name = "STOP_FLAG")
    private Integer stopFlag=1;
	
	@Column(name = "STOP_TIME")
    private Date stopTime;
	
	@Column(name = "STOP_REASON")
    private String stopReason;
	
	@Column(name = "LOCK_FLAG")
    private Integer lockFlag=0;
	
	@Column(name = "TELEPHONE")
    private String telephone;
	
	@Column(name = "POST_CODE")
    private String postCode;
	
	@Column(name = "ADDRESS")
    private String address;
	
	@Column(name = "USER_DESC1")
    private String desc1;
	
	@Column(name = "USER_DESC2")
    private String desc2;
	
	@Column(name = "USER_DESC3")
    private String desc3;
	
	@Column(name = "USER_DESC4")
    private String desc4;
	@Column(name = "USER_SOURCE")
    private String userSource;
	@Transient
	private String subacName;// 投资机构名称

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getPwdUpdateTime() {
		return pwdUpdateTime;
	}

	public void setPwdUpdateTime(Date pwdUpdateTime) {
		this.pwdUpdateTime = pwdUpdateTime;
	}

	public Integer getResetFlag() {
		return resetFlag;
	}

	public void setResetFlag(Integer resetFlag) {
		this.resetFlag = resetFlag;
	}

	public Integer getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(Integer stopFlag) {
		this.stopFlag = stopFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getStopTime() {
		return stopTime;
	}
	
	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public Integer getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getDesc1() {
		return desc1;
	}

	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	public String getDesc3() {
		return desc3;
	}

	public void setDesc3(String desc3) {
		this.desc3 = desc3;
	}

	public String getDesc4() {
		return desc4;
	}

	public void setDesc4(String desc4) {
		this.desc4 = desc4;
	}

	public String getSubacName() {
        return subacName;
    }

    public void setSubacName(String subacName) {
        this.subacName = subacName;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}

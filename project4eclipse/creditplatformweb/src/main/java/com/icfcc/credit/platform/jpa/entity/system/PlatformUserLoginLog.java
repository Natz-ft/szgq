package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * 
* @ClassName: PlatformUserLoginLog
* @Description: TODO(这里用一句话描述这个类的作用)
* @author hugt
* @date 2017年9月14日 下午6:45:43
*
 */
@Data
@Entity
@Table(name="platform_user_login_log")
public class PlatformUserLoginLog implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3809339633691603695L;

	@Id
    @Column(name = "ID")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
    private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="FAIL_REASON", length=80)
	private String failReason;

	@Column(name="LOGIN_IP", length=20)
	private String loginIp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOGIN_TIME")
	private Date loginTime;
	
	@Column(name="SUCCESS_FLAG")
	private Integer successFlag=0;
	

	@Column(name="USER_ID",length=32)
	private String userId;

	@Column(name="USER_TYPE", length=2)
	private String userType;

	public PlatformUserLoginLog() {
	}

	public String getFailReason() {
		return this.failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getSuccessFlag() {
		return this.successFlag;
	}

	public void setSuccessFlag(Integer successFlag) {
		this.successFlag = successFlag;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
package com.icfcc.credit.platform.jpa.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

/**
 * 
* @ClassName: PlatformOperateLog
* @Description: TODO(操作日志信息实体类)
* @author hugt
* @date 2017年9月14日 下午5:59:59
*
 */
@Data
@Entity
@Table(name = "platform_operate_log")
public class PlatformOperateLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3878634715379942319L;
	private String id;
	private String userName;
	private Date createTime;
	private String servicePath;
	private String param;
	private String returnResult;
	private String methodName;
	private String operateTime;
	private String functionName;
	private String ip;

	// Constructors
	@Transient
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/** default constructor */
	public PlatformOperateLog() {
	}

	/** minimal constructor */
	public PlatformOperateLog(String id) {
		this.id = id;
	}

	/** full constructor */
	public PlatformOperateLog(String id, String userName, Date createTime, String servicePath, String param, String returnResult, String methodName, String operateTime) {
		this.id = id;
		this.userName = userName;
		this.createTime = createTime;
		this.servicePath = servicePath;
		this.param = param;
		this.returnResult = returnResult;
		this.methodName = methodName;
		this.operateTime = operateTime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "USER_NAME", length = 200)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "CREATE_TIME", length = 0)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "SERVICE_PATH", length = 200)
	public String getServicePath() {
		return this.servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	@Column(name = "PARAM", length = 65535)
	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name = "RETURN_RESULT", length = 65535)
	public String getReturnResult() {
		return this.returnResult;
	}

	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}

	@Column(name = "METHOD_NAME", length = 200)
	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(name = "OPERATE_TIME", length = 200)
	public String getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "IP", length = 15)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
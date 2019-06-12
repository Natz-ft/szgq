package com.icfcc.credit.platform.jpa.entity.system;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * 
* @ClassName: PlatformFunction
* @Description: TODO<系统平台功能实体类>
* @author hugt
* @date 2017年9月14日 下午5:56:03
*
 */
@Data
@Entity
@Table(name = "platform_function")
public class PlatformFunction implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2538560899612998281L;
	private String id;
	private String functionName;
	private String methodName;
	private String servicePath;
	private Date createTime;
	
	// Constructors

	/** default constructor */
	public PlatformFunction() {
	
	}

	/** minimal constructor */
	public PlatformFunction(String id) {
		this.id = id;
	}

	/** full constructor */
	public PlatformFunction(String id, String functionName, String methodName,
			String servicePath, Date createTime) {
		this.id = id;
		this.functionName = functionName;
		this.methodName = methodName;
		this.servicePath = servicePath;
		this.createTime = createTime;
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

	@Column(name = "FUNCTION_NAME", length = 200)
	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name = "METHOD_NAME", length = 200)
	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(name = "SERVICE_PATH", length = 200)
	public String getServicePath() {
		return this.servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath == null ?null:servicePath.trim();
	}

	@Column(name = "CREATE_TIME", length = 0)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
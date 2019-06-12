package com.icfcc.credit.platform.util;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * http 请求返回结果基类,定义所有返回码.
 * 
 */
public class HttpResult{

	// 返回代码定义 //
	// 按项目的规则进行定义，比如1xx代表客户端参数错误，2xx代表业务错误等.
	public static final String SUCCESS = "0";
	public static final String PARAMETER_ERROR = "101";
	public static final String AUTH_ERROR = "201";
	public static final String SYSTEM_ERROR = "300";
	public static final String SYSTEM_OK = "200";
	
	// WSResult基本属性 //
	private String code = SUCCESS;
	private String statusCode;
	private String message;
	public static HttpStatus HTTP_STATUS;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		this.statusCode = code;

	}
	public void setCode(HttpStatus code) {
		this.code = code.toString();
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessage(HttpStatus message) {
		this.message = message.getReasonPhrase();
	}

	/**
	 * 设置返回结果.
	 */
	public void setResult(String resultCode, String resultMessage) {
		code = resultCode;
		statusCode = resultCode;
		message = resultMessage;
	}
	
	public void setResult(HttpStatus httpStatus) {
		code = httpStatus.toString();
		statusCode  = httpStatus.toString();
		message = httpStatus.getReasonPhrase();
	}

	/**
	 * 设置为默认的系统内部未知错误.
	 */
	public void setSystemError() {
		setResult(SYSTEM_ERROR, "系统未知运行时错误");
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}

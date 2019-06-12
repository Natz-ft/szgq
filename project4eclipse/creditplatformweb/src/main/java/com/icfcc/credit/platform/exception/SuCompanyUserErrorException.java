package com.icfcc.credit.platform.exception;

import org.apache.shiro.authc.AuthenticationException;

public class SuCompanyUserErrorException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SuCompanyUserErrorException() {
		super();
	}

	public SuCompanyUserErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public SuCompanyUserErrorException(String message) {
		super(message);
	}

	public SuCompanyUserErrorException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	
}

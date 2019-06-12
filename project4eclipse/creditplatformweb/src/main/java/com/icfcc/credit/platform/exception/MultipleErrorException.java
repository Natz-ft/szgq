package com.icfcc.credit.platform.exception;

import org.apache.shiro.authc.AuthenticationException;

public class MultipleErrorException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MultipleErrorException() {
		super();
	}

	public MultipleErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MultipleErrorException(String message) {
		super(message);
	}

	public MultipleErrorException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	
}

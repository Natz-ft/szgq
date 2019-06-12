package com.icfcc.credit.platform.exception;

import org.apache.shiro.authc.AuthenticationException;

public class LockUserException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LockUserException() {
		super();
	}

	public LockUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public LockUserException(String message) {
		super(message);
	}

	public LockUserException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	
}

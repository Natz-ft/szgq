package com.icfcc.credit.platform.exception;

import org.apache.shiro.authc.AuthenticationException;

public class SimplePassException extends AuthenticationException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public SimplePassException(){
		super();
	}

	public SimplePassException(String message){
		super(message);
	}

	public SimplePassException(String message, Throwable cause) {
		super(message, cause);
	}

	public SimplePassException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	
}

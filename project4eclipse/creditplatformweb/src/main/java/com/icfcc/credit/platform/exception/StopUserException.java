package com.icfcc.credit.platform.exception;

import org.apache.shiro.authc.AuthenticationException;

public class StopUserException extends AuthenticationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StopUserException(){
		super();
	}
	
	public  StopUserException(String message){
		super(message);
	}
	
	public StopUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public StopUserException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	
}

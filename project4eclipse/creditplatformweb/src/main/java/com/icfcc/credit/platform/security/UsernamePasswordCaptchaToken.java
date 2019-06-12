package com.icfcc.credit.platform.security;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 * 自定义带验证码的认证类
 * @author JERRY.CHEN
 *
 */
public class UsernamePasswordCaptchaToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 1L;

	private String captcha;
	
	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public UsernamePasswordCaptchaToken() {
		super();

	}

	public UsernamePasswordCaptchaToken(String username, char[] password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

}

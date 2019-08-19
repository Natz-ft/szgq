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

	private String password1;

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

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
			boolean rememberMe, String host, String captcha,String password1) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.password1 = password1;
	}

}

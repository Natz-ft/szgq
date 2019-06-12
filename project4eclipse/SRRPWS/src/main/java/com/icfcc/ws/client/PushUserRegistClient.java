package com.icfcc.ws.client;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.springframework.beans.factory.annotation.Value;

import com.icfcc.util.AESUtil;
import com.icfcc.ws.PushUserRegistInfor;
import com.icfcc.ws.services.PlatformUserService;


public class PushUserRegistClient {
	//接口路径
		@Value("${reportURL}")
		public String url;
		//系统用户
		@Value("${reporUser}")
		public String reporUser;
		//访问类型
		@Value("${reason}")
		public String reason;
		//密钥
		@Value("${key}")
		public String key;
		//连接超时参数
		@Value("${connectionTimeout}")
		public Long connectionTimeout;
		//响应超时参数
		@Value("${receiveTimeout}")
		public Long receiveTimeout;

	public  String getResult(String corpCode) { 
		String result =null;
		System.out.println("调用信用报告接口:"+url);
		WebClient client = WebClient.create(url);
		ClientConfiguration config = WebClient.getConfig(client); 
		config.getHttpConduit().getClient().setConnectionTimeout(connectionTimeout);//连接超时
		config.getHttpConduit().getClient().setReceiveTimeout(receiveTimeout); //响应超时 
    	client.path("wsWebService/getReportInfo").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		System.out.println("调用信用报告接口参数(加密前):"+"name:"+reporUser+";corpCode;"+corpCode+";reason:"+reason);
    	String e_userName = AESUtil.encrypt(reporUser, key);
   	    String e_corpCode = AESUtil.encrypt(corpCode, key);
   	    String e_reason   = AESUtil.encrypt(reason, key);
		System.out.println("调用信用报告接口参数(加密后):"+"e_userName:"+e_userName+";e_corpCode;"+e_corpCode+";e_reason:"+e_reason);
    	client.replaceQueryParam("userName", e_userName).replaceQueryParam("corpCode", e_corpCode).replaceQueryParam("reason", e_reason);
    	result =AESUtil.decrypt( client.get(String.class), key);
		System.out.println("调用信用报告接口结束，返回结果:"+result);
		return result;
	} 
	public static void main(String[] args) {  
	    JAXRSServerFactoryBean jrf = new JAXRSServerFactoryBean();  
	    jrf.setResourceClasses(PlatformUserService.class);  
	    jrf.setResourceProvider(PlatformUserService.class,  
	            new SingletonResourceProvider(new PlatformUserService()));  
	    jrf.setAddress("http://localhost:8080/RESTful/");  
	    jrf.create();  
	}  
}

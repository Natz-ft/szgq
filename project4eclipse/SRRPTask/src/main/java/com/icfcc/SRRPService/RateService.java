/**
 * 
 */
package com.icfcc.SRRPService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformConfigDao;
import com.icfcc.ssrp.util.AESUtil;

/**
 * @author lijj
 * 友情链接业务
 */
@Service
@Transactional(value = "transactionManager")
public class RateService {
	@Autowired
	public PlatformConfigDao systemConfigDao;
	@Value("${reportURL}")
	public String url;
	// 系统用户
	@Value("${reporUser}")
	public String reporUser;
	// 访问类型
	@Value("${reason}")
	public String reason;
	// 密钥
	@Value("${reportkey}")
	public String key;
	// 连接超时参数
	@Value("${connectionTimeout}")
	public Long connectionTimeout;
	// 响应超时参数
	@Value("${receiveTimeout}")
	public Long receiveTimeout;
	
	
	/**
	 * 获取最新的n条友情链接
	 * @param n
	 * @return
	 */
	@Transactional(value = "transactionManager1")
	public void UpdateRate() throws Exception{
		PlatformConfig sc = systemConfigDao.findByConfigName("rate");
		String rate=null;
		rate=getResult();
		if(rate!=null){
			sc.setConfigValue(rate);
			}
		systemConfigDao.save(sc);
	}
	public String getResult() {
		String result = null;
		System.out.println("调用信用报告接口url:" + url);
		try{
			WebClient client = WebClient.create(url);
			ClientConfiguration config = WebClient.getConfig(client);
			config.getHttpConduit().getClient().setConnectionTimeout(connectionTimeout);// 连接超时
			config.getHttpConduit().getClient().setReceiveTimeout(receiveTimeout); // 响应超时
			client.path("wsWebService/getRate").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
			String e_userName = AESUtil.encryptSecs(reporUser, key);
			String e_type = AESUtil.encryptSecs("14", key);
			client.replaceQueryParam("userName", e_userName).replaceQueryParam("type", e_type);
			result = AESUtil.decryptSecs(client.get(String.class), key);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return result;
	}
	public static void main(String arg[]){
		String result = "";
		System.out.println("调用信用报告接口:" + "http://172.18.6.88:8094/ee-ws/");
		WebClient client = WebClient.create("http://172.18.6.88:8094/ee-ws/");
		ClientConfiguration config = WebClient.getConfig(client);
		config.getHttpConduit().getClient().setConnectionTimeout(10000);// 连接超时
		config.getHttpConduit().getClient().setReceiveTimeout(10000); // 响应超时
		client.path("wsWebService/getRate").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		String e_userName = AESUtil.encryptSecs("szgqpt", "E053040812ACB857");
		String e_type = AESUtil.encryptSecs("14", "E053040812ACB857");
		client.replaceQueryParam("userName", e_userName).replaceQueryParam("type", e_type);
		result = AESUtil.decryptSecs(client.get(String.class), "E053040812ACB857");
		System.out.println("jiemi ================"+client.get(String.class));
		System.out.println("jiemi ================"+result);

	}
}

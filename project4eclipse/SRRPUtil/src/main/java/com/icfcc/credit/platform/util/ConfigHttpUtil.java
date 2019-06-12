package com.icfcc.credit.platform.util;

import java.net.SocketTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.netty.channel.ConnectTimeoutException;

public class ConfigHttpUtil {
	public static ResourceManager resourceManager = ResourceManager.getInstance();
	private static Logger log = LoggerFactory.getLogger(ConfigHttpUtil.class);

	/**
	 * <根据httpClient访问系统项目参数表>
	 * 
	 * @param key
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws Exception
	 * @author tanshengrui
	 * @date 2017年7月22日
	 */
	public static String configHttp(String key) throws ConnectTimeoutException, SocketTimeoutException, Exception {
		String result = null;
		try {
			String server = resourceManager.getValue("config_server");
			String config_url = resourceManager.getValue("config_url");
			String url = server + config_url;
			// 加密
			String token = MD5.MD5(config_url + CommonConstant.MD5_SALT);
			result = HttpClientUtils.post(url, "name=" + key + "&token=" + token, "application/x-www-form-urlencoded",
					"utf-8", 1000, 3000);
			log.info("result:" + result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("configHttp:{}", e);
		}
		return result;
	}

}

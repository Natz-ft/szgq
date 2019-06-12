package com.icfcc.credit.platform.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUrlUtils {
	private static Logger log = LoggerFactory.getLogger(HttpUrlUtils.class);

	public static String httpUrlget(String httpUrl) {
		log.info("httpUrlget   httpUrl:" + httpUrl);
		try {
			// 建立与服务端的连接
			URL url = new URL(httpUrl);
			// 取得与服务端的连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			System.setProperty("sun.net.client.defaultConnectTimeout", "3000");
			System.setProperty("sun.net.client.defaultReadTimeout", "3000");
			InputStream is = conn.getInputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			StringBuffer buffer = new StringBuffer();
			while ((len = is.read(buf)) > 0) {
				buffer.append(new String(buf, 0, len));
			}
			// 关闭
			is.close();
			log.info(buffer.toString());
			return buffer.toString();
		} catch (Exception e) {
			log.error("httpUrlget:{}",e.getMessage());
			return null;
		}
	}

	public static void main(String[] args) {
		String custIdOfferFlag = HttpUrlUtils.httpUrlget(CommonConstant.COMMONURL + "custid");
		System.out.println(custIdOfferFlag);
	}

}

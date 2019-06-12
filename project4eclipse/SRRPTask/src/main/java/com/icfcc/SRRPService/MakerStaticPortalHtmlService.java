package com.icfcc.SRRPService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.icfcc.credit.platform.util.SRRPConstant;
/**
 * 生成动态html
 * @author hugt
 *
 */
@Service
public class MakerStaticPortalHtmlService {

	private static Logger log = LoggerFactory.getLogger(QueryScoreService.class);
	@Value("${inexURL}")
	public String inexURL;// 门户首页Control路径
	@Value("${staticURL}")
	public String staticURL;// 门户运行成果Control路径
	@Value("${mothlyRankURL}")
	public String mothlyRankURL;// 门户运行成果月榜单Control路径
	@Value("${contantUsURL}")
	public String contantUsURL;// 门户联系我们Control路径
	@Value("${newsURL}")
	public String newsURL;// 门户新闻动态Control路径

	
	public Map<String, String> HttpClientGetSRRP(String... URLS) {
		log.info("调用生成静态Html的Url");
		Map<String, String> resultMap = new HashMap<String, String>();
		String executeResult = SRRPConstant.EXECUTESTART;
		String msg = "";
//		List<String> urlList=Arrays.asList(URLS);
		try {
			for (String URL : URLS) {
				System.out.println("url===========" + URL);
				HttpClient httpClient = HttpClients.createDefault();
				HttpGet get = new HttpGet(URL);
				// HttpGet get = new
				// HttpGet("http://localhost:8080/SRRPBusinesWeb/index/initIndex/");
				HttpResponse response1;
				response1 = httpClient.execute(get);
				HttpEntity entity2 = response1.getEntity();
				String str = EntityUtils.toString(entity2, "utf-8");
				JSONObject obj = JSONObject.parseObject(str);
				resultMap.put("code", obj.getByte("code").toString());
				if(obj.getByte("msg")==null){
					resultMap.put("msg", "");
				}else{
					resultMap.put("msg", obj.getByte("msg").toString());
				}
				if(obj.getByte("code").equals(SRRPConstant.EXECUTEXCEPTION)){
					return resultMap;
				}
				
			}
		} catch (Exception e) {
			executeResult = SRRPConstant.EXECUTEXCEPTION;
			msg = e.getMessage();
			e.printStackTrace();
		}
		executeResult = SRRPConstant.EXECUTSUCC;
		resultMap.put("code", executeResult);
		resultMap.put("msg", msg);
		return resultMap;

	}
}

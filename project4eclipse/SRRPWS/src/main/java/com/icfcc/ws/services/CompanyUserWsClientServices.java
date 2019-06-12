package com.icfcc.ws.services;


import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.entity.CompanyInfoVo;
import com.icfcc.util.AESUtil;

import net.sf.json.JSONObject;

public class CompanyUserWsClientServices {
		
    public static void main(String arg[]){
        //http://localhost:8080/SRRPWS/cxf/RESTful?_wadlhttp://localhost:8180/SRRPWS/cxf/RESTful
          String result ="";
          String url="http://localhost:8080/SRRPWS/cxf/RESTful";
//          String url="http://192.168.10.34:8088/SRRPWS/cxf/RESTful";

//        String url="http://61.155.174.41";
          System.out.println("调用信用报告接口:"+url);
          WebClient client = WebClient.create(url);

          client.path("/updateCompanyInfo").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
          //封装企业信息
          CompanyInfoVo companyRestVo=new CompanyInfoVo();
          companyRestVo.setCode("20190101-4");
          companyRestVo.setStopFlag("0");
//          companyRestVo.setUser_pwd("218cca77804d2ba1922c33e0151105");
//          companyRestVo.setName("测试企业2");
//          companyRestVo.setCodetype("1");
//          companyRestVo.setCode("20181228-8");
//          companyRestVo.setUser_id("20181228-8");
//          //companyRestVo.setUser_pwd("12345678");
//          companyRestVo.setBranchno("320508");
//          companyRestVo.setContacname("胡国涛");
//          companyRestVo.setContaccal("17310853721");
//          companyRestVo.setLegalname("刘斯茜");
//          companyRestVo.setLegalcal("18862246031");
//          companyRestVo.setStatus("1");
//          companyRestVo.setOldCode("88888888-8");
          JSONObject json = JSONObject.fromObject(companyRestVo);
          String companyInfoJsonStr = AESUtil.encrypt(json.toString(), "E053040812ACB857");//加密
          //传参
          String typeStr = AESUtil.encrypt("03", "E053040812ACB857");// 加密
          // 传参
          client.replaceQueryParam("companyInfoJsonStr", companyInfoJsonStr);
//          client.replaceQueryParam("companyInfoJsonStr", companyInfoJsonStr);
          result =client.get(String.class);
//        String companyInfoStr =AESUtil.decrypt("1rghPsufmn1QnsLMPiWpqkkLeumLC1zijdSRpsjfe/ho3NirktnEtzRttvwwPtsU04TSdFAJTJPbKu3NYZhWtzEQa9zYbMnzXOcGcEzODeK4H5cp5SSwGT8S%2BKdTWvcDpzCbSw3AJa%2Bxw9mAEdNrdhTsuD1W5gJJ6rlcL1i17ibZS4YXF7pMGBc3dRiV1H8n/A1ICh8hfO5Dg3tWUTss6qT1IUojVWLnBcPQnTHAGp16ZwHiYZ2sjtGQPnLmiPCfNYCaayNNrGsyA0Nq72NXPQ6xZuDUTK0m3uCs7JzVXKVLOpMv85DOJCgvM%2B0ObIs68nRJQuRK%2BmAcjjPPd2scAoEDbxj6GN3bPFHmkePbPVy0lDdmQACXlRRhDPHjBi0P", "E053040812ACB857");//解密
//        JSONObject jsonObject = JSONObject.fromObject(companyInfoStr);
//        CompanyInfoVo companyInfo = (CompanyInfoVo) JSONObject.toBean(jsonObject, CompanyInfoVo.class);
          result = AESUtil.decrypt(result, "E053040812ACB857");// 解密
          Map maps = (Map) JSON.parse(result); // 转map
          System.out.println("调用信用报告接口结束，返回结果:"+AESUtil.decrypt(result, "E053040812ACB857"));//0苏金服 1 股权融资平台


          
          
//        WebClient client = WebClient.create(url);
//        client.path("/webSzyhWSClient").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
//        //封装企业信息
////          JSONObject json = JSONObject.fromObject(companyRestVo);
//        String companyInfoJsonStr = "07JC2l++g+Ubkgl3HAzAGRHlbi/0JbLrlhaGyClsG7K8OpSVDK2CTNviEw/ySiUbarggs80PMnYWpaOFgvipf+2L8cOPOAntEsJOVWIprnxgNwpb3ZTRETcCnbDXFnLBr5n6SaDKhhaCuOWz8DyscKBSIDQoOPRe0JwYjakhaqh0eqas831xmYuaEF2Bw0qu311CHgMOAbuhusjQ2iJ1/vqrkMaPIgKpmgfMAUoH7lIafeNrfyCGeoUKDDPiaPr90mHb3bxne72bKpEEScWFgoE3ZhkXXtPnJ4Q6suWUAgIqSBIgwaePbf2to/FXvwnx";//加密
//        String typeStr = AESUtil.encrypt("02", "E053040812ACB857");//加密
//        //传参
//        client.replaceQueryParam("companyInfoJsonStr", companyInfoJsonStr).replaceQueryParam("type", typeStr);;
//        result =client.get(String.class);
//        System.out.println("result============="+AESUtil.decrypt(result, "E053040812ACB857"));
      }

}

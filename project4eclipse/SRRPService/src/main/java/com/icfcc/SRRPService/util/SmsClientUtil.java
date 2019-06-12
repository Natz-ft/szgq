package com.icfcc.SRRPService.util;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.icfcc.credit.platform.qcloudsms.SmsSingleSender;
import com.icfcc.credit.platform.qcloudsms.SmsSingleSenderResult;

public class SmsClientUtil {
	
	
	/**
	 * 调用腾讯云的信息查询方法 传一个可变的数组参数
	 * array{"random"}
	 * array{"name","user","password"}
	 * array{"name","erroMessage"}
	 *  name         公司名称
	 *  user         用户名
	 *  password     用户密码
	 *  erroMessage  错误原因
	 *  random       验证码
	 * @param phone        电话号码
	 * @param flag         1 企业注册  2 机构注册  3 企业审核通过  4 机构审核通过 5企业审核不通过  6机构审核不通过
	 * @return
	 * @throws Exception
	 */
	public static boolean querySms(String array[],String keys[],String phone,String flag) throws Exception{
		boolean rs = true;
		try {
			//腾讯云端 配置的发送短信内容模板的 id 号码
			//注册成功      短信内容模板 对应的 id 号码 
			//审核通过      短信内容模板 对应的 id 号码 
			//审核不通过   短信内容模板 对应的 id 号码 
		    int appid = Integer.parseInt(keys[0]);
		    String appkey = keys[1];
		    int type = Integer.parseInt(keys[2]);
			SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult singleSenderResult = null;
			//根据flag 来判断使用不通的短信模板，和填充内容
			ArrayList<String> params =queryParam(array,flag);
			singleSenderResult = singleSender.sendWithParam("86", phone, type, params, "", "", "");
			if(0 != singleSenderResult.getResult()){
				//0 代表成功，其他的错误码，请参考腾讯云短信服务，错误码api
				System.out.println("返回的错误码："+singleSenderResult.getResult());
				rs=false;
				throw new Exception();
			}
		} catch (Exception e) {
			rs=false;
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 *    短信模板上面设置的发送内容参数，可以自己去数据进行查询填充  
	 *  name             公司名称
	 *  user               用户名
	 *  password       用户密码
	 *  erroMessage  错误原因
	 *  random         验证码
	 * @param flag               1 企业注册  2 机构注册  3 企业审核通过  4 机构审核通过 5企业审核不通过  6机构审核不通过
	 * @return
	 * 短信模板内容例子如下：
	 * 注册成功  ：【苏州股权融资平台】尊敬的用户，您的验证码是:{1}，有效期为120秒，请勿向他人泄露您的验证码。
	 * 审核通过  ：【苏州股权融资平台】{1}公司，您提交的注册申请已审核通过。用户名：{2} 密码：{3},可登录平台[查看/发布]融资需求。
	 * 审核不通过 :【苏州股权融资平台】{1}公司，您提交的注册申请未审核通过。原因：{2};请重新注册。
	 */
	public static ArrayList<String> queryParam(String array[],String flag){
		//根据flag 判断 是机构或者企业， 注册还是审核通过，来发送不通的内容
		ArrayList<String> params = new ArrayList<String>();
		if("1".equals(flag) || "2".equals(flag)){//注册成功
			params.add(array[0]);
//			params.add("1");
		}else if("3".equals(flag) || "4".equals(flag) ){//审核通过
			params.add(array[0]);
	    	params.add(array[1]);
	    	params.add(array[2]);
		}else if("5".equals(flag) || "16".equals(flag) ||  "6".equals(flag)){//审核不通过
			params.add(array[0]);
	    	params.add(array[1]);
	    	params.add(array[2]);
		}else if("7".equals(flag) ){//审核不通过
			params.add(array[0]);
		}else if("01".equals(flag)){
			params.add(array[0]);
	    	params.add(array[1]);
	    	params.add(array[2]);
	    	params.add(array[3]);
		}else if("02".equals(flag)){
			params.add(array[0]);
	    	params.add(array[1]);
	    	params.add(array[2]);
	    	params.add(array[3]);
		}else if("03".equals(flag)){
			params.add(array[0]);
	    	params.add(array[1]);
	    	params.add(array[2]);
	    	params.add(array[3]);
		}else if("04".equals(flag)){
			params.add(array[0]);
		}else if("05".equals(flag)){
			params.add(array[0]);
		}else if("06".equals(flag)){
			params.add(array[0]);
	    	params.add(array[1]);
	    	params.add(array[2]);
	    	params.add(array[3]);
		}else if("07".equals(flag)){
			params.add(array[0]);
	    	params.add(array[1]);
	    	params.add(array[2]);
	    	params.add(array[3]);
		}
		return params;
	}
	
	
	//例子
	public static void main(String[] args) {
    	try {
    		//请根据实际 appid 和 appkey 进行开发，以下只作为演示 sdk 使用
			//appid,appkey,templId申请方式可参考接入指南 https://www.qcloud.com/document/product/382/3785#5-.E7.9F.AD.E4.BF.A1.E5.86.85.E5.AE.B9.E9.85.8D.E7.BD.AE
    		int appid = 1400052310;
    		String appkey = "0800876c57719bd2ab1c6a4c751886e5";
    		String phoneNumber1 = "17310853721";
    		int tmplId = 61844;
    		 //初始化单发
    		SmsSingleSender singleSender = new SmsSingleSender(appid, appkey);
			SmsSingleSenderResult singleSenderResult;
	    	 //指定模板单发
			
	    	 //假设短信模板 id 为 1，模板内容为：测试短信，{1}，{2}，{3}，上学。
	    	ArrayList<String> params = new ArrayList<String>();
	    	params.add("肖大帅哥发来祝贺");
	    	params.add("5");
	    	singleSenderResult = singleSender.sendWithParam("86", phoneNumber1, tmplId, params, "", "", "");
	    	System.out.println(singleSenderResult);
	    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }

}

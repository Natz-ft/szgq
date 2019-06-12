package com.icfcc.SRRPDao.jpa.entity.companyInfo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 参数对象
 * @author huguo
 *
 */
public class CapcompanyinfoBean  implements java.io.Serializable{

	private static final long serialVersionUID = 6354626827455305977L;
	private static Log log = LogFactory.getLog(CapcompanyinfoBean.class);
	// 规模
	Map<String, String> companyScale_v1_map = null;
	
	//区域
	Map<String, String> corp_AREA_map = null;
	
	// 经营年限
	Map<String, String> bizTerm_v1_map = null;
	
	// 行业
	Map<String, Map<String,String>> service_CATEGORY_map = null;
	
	// 登记注册类型
	Map<String, String> register_type_map = null;
	
	// 投资类型
	Map<String, String> investment_type_map = null;
	
	// 放贷机构
	Map<String, String> lend_institution_map = null;
	Map<String, Map<String,String>> lend_institution_type_map = null;
	
	// 授权机构
	Map<String, String> auth_institution_map = null;

	public Map<String, String> getCompanyScale_v1_map() {
		
		return RedisGetValue.getDataMap(SRRPConstant.CompanyScale_v1);
	}

	public void setCompanyScale_v1_map(Map<String, String> companyScale_v1_map) {
		this.companyScale_v1_map = companyScale_v1_map;
	}

	public Map<String, String> getCorp_AREA_map() {
		return RedisGetValue.getDataMap(SRRPConstant.DD_CORP_AREA);
	}

	public void setCorp_AREA_map(Map<String, String> corp_AREA_map) {
		this.corp_AREA_map = corp_AREA_map;
	}

	public Map<String, String> getBizTerm_v1_map() {
		return RedisGetValue.getDataMap(SRRPConstant.DD_BIZTERM_V1);
	}

	public void setBizTerm_v1_map(Map<String, String> bizTerm_v1_map) {
		this.bizTerm_v1_map = bizTerm_v1_map;
	}

	public Map<String, Map<String, String>> getService_CATEGORY_map() {
		service_CATEGORY_map= new LinkedHashMap<String,  Map<String, String>>();
		Map<String, String> map1= RedisGetValue.getDataMap(SRRPConstant.SERVICE_CATEGORY_1);//一级行业
		Map<String, String> map2= RedisGetValue.getDataMap(SRRPConstant.SERVICE_CATEGORY_2);//二级行业
		 for(String  s1:map1.keySet()){
			 Map<String, String> cate= new LinkedHashMap<String, String>();
			 for(String  s2:map2.keySet()){//循环map：二级行业
				 if(s2.contains(s1)){
					 cate.put(s2, map2.get(s2));
				 }
			 }
			 service_CATEGORY_map.put(map1.get(s1), cate);//key：一级行业名称    value:map二级行业结果集
		}
		return service_CATEGORY_map;
	}

	public void setService_CATEGORY_map(Map<String, Map<String, String>> service_CATEGORY_map) {
		this.service_CATEGORY_map = service_CATEGORY_map;
	}

	public Map<String, String> getRegister_type_map() {
		return RedisGetValue.getDataMap(SRRPConstant.DD_REGISTER_TYPE) ;
	}

	public void setRegister_type_map(Map<String, String> register_type_map) {
		this.register_type_map = register_type_map;
	}

	public Map<String, String> getInvestment_type_map() {
		return RedisGetValue.getDataMap(SRRPConstant.DD_INVESTMENT_TYPE) ;
	}

	public void setInvestment_type_map(Map<String, String> investment_type_map) {
		this.investment_type_map = investment_type_map;
	}

	public Map<String, String> getLend_institution_map() {
		return lend_institution_map;
	}

	public void setLend_institution_map(Map<String, String> lend_institution_map) {
		this.lend_institution_map = lend_institution_map;
	}

	public Map<String, Map<String, String>> getLend_institution_type_map() {
		lend_institution_type_map= new LinkedHashMap<String,  Map<String, String>>();
		Map<String, String> map1= RedisGetValue.getDataMap(SRRPConstant.lend_institution);
		 for(String  s1:map1.keySet()){
				Map<String, String> map2= RedisGetValue.getDataMap(s1);//根据类型从redi获取机构类别
				lend_institution_type_map.put(map1.get(s1), map2);//key:放贷机构类别 value:放贷机构结果集
		}
		return lend_institution_type_map;
	}

	public void setLend_institution_type_map(Map<String, Map<String, String>> lend_institution_type_map) {
		this.lend_institution_type_map = lend_institution_type_map;
	}

	public Map<String, String> getAuth_institution_map() {
		auth_institution_map= new LinkedHashMap<String, String>();
		Map<String, String> map1= RedisGetValue.getDataMap(SRRPConstant.auth_institution);
		 for(String  s1:map1.keySet()){
				Map<String, String> map2= RedisGetValue.getDataMap(s1);//根据授权机构相对应的机构
				for(String  s2:map2.keySet()){
					auth_institution_map.put(s2,map2.get(s2));
				}
		}
		return auth_institution_map;
	}

	public void setAuth_institution_map(Map<String, String> auth_institution_map) {
		this.auth_institution_map = auth_institution_map;
	}

	


}



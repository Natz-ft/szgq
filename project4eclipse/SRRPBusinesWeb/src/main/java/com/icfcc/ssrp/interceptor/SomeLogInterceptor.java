package com.icfcc.ssrp.interceptor;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.SomeLog;
import com.icfcc.SRRPDao.jpa.entity.companyInfo.CapcompanyinfoBean;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.someLog.SomeLogService;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.ssrp.session.RedisGetValue;

import com.icfcc.ssrp.session.DD;
/**
  *  详细的日志配置
 * @author whyxs
 *
 */
@Component
public class SomeLogInterceptor implements HandlerInterceptor {

	private static Logger log = LoggerFactory.getLogger(SomeLogInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
			String methodName = request.getServletPath().replace("/", "_");
			Class<SomeLogInterceptor> clazz = SomeLogInterceptor.class;
			Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class);
			method.invoke(clazz.newInstance(), request);
			log.info(methodName + "：日志记录成功");
		} catch (Exception e) {
			log.error("日志记录失败");
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	private ApplicationContext getCtx (HttpServletRequest request) {
		return WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	}
	
	
	/**
	 * 企业查询-基础查询
	 */
	public void _companyInfo_initInfo (HttpServletRequest request) {
		String queryConditionStr = request.getParameter("queryCondition");
		QueryCondition queryCondition = new QueryCondition();
		if (queryConditionStr != null&& !"".equals(queryConditionStr)&& !"\"\"".equals(queryConditionStr)) {
			queryCondition = (QueryCondition) JSON.parseObject(queryConditionStr, QueryCondition.class);
		}
		Map<String, Object> map = new LinkedHashMap();
		map.put("排序方式",queryCondition.getSortord()!=null?"01".equals(queryCondition.getSortord())?"注册资本":"默认排序":"");
		map.put("名称/代码",queryCondition.getNameAndCode()!=null?queryCondition.getNameAndCode():"");
		map.put("所属区域",getValueByKey(queryCondition.getRearea(), RedisGetValue.getDDList("rearea")));
		map.put("行业",getValueByKey(queryCondition.getIndustry(), RedisGetValue.getDDList("industry")));
		
		
		SomeLog someLog  = new SomeLog();
		someLog.setOperName("企业查询-基础查询");
		someLog.setOperUserId(((ShiroUser)SecurityUtils.getSubject().getPrincipal()).getId());
		someLog.setOperdate(new Date());
		someLog.setParamContent(JSON.toJSONString(map));
		
		getCtx(request).getBean(SomeLogService.class).saveSomeLog(someLog);
		
	}
	
	/**
	 * 字典数据
	 */
	private List<String> getValueByKey(String os, List<DD> listOs) {
		List<String> reList = new ArrayList<String>();
		if (os != null && listOs != null) {
			List<String> list = Arrays.asList(os.split(","));
			for (DD dd : listOs) {
				if (list.contains(dd.getDicCode())) {
					reList.add(dd.getDicName());
				}
			}
		}
		return reList;
	}
	
	/**
	 * 字典数据
	 */
	private Object getValueByKey(String[] os, Map<String, String> map) {
		List<String> reList = new ArrayList<String>();
		if (os != null && map != null) {
			Iterator<Entry<String, String>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> entry = it.next();
				if(Arrays.asList(os).contains(entry.getKey())) {
					reList.add(entry.getValue());
				}
			}
		}
		return reList;
	}
	
	/**
	 * 字典数据
	 */
	private Object getValueByKey2(String[] os, Map<String, Map<String, String>> map) {
		List<String> reList = new ArrayList<String>();
		if (os != null && map != null) {
			Iterator<Entry<String, Map<String, String>>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map<String, String> subMap = it.next().getValue();
				Iterator<Entry<String, String>> subIt = subMap.entrySet().iterator();
				while (subIt.hasNext()) {
					Entry<String, String> entry = subIt.next();
					if(Arrays.asList(os).contains(entry.getKey())) {
						reList.add(entry.getValue());
					}
				}
				
			}
		}
		return reList;
	}
	
	
	/**
	 * 企业查询-深度查询
	 */
	public void _companyserch_query (HttpServletRequest request) {
		
		Map<String, Object> map = new LinkedHashMap();
		
		CapcompanyinfoBean capcompanyinfoBean=new CapcompanyinfoBean();
		
		String[] scale = request.getParameterValues("scale");
		String[] region = request.getParameterValues("region");
		String[] industry = request.getParameterValues("industry");
		String[] register_type = request.getParameterValues("register_type");
		String[] investment_type_map = request.getParameterValues("investment_type_map");
		String[] bizTerm = request.getParameterValues("bizTerm");
		
		map.put("规模",getValueByKey(scale,capcompanyinfoBean.getCompanyScale_v1_map()));
		map.put("区域",getValueByKey(region,capcompanyinfoBean.getCorp_AREA_map()));
		map.put("行业",getValueByKey2(industry,capcompanyinfoBean.getService_CATEGORY_map()));
		map.put("登记注册类型",getValueByKey(register_type,capcompanyinfoBean.getRegister_type_map()));
		map.put("投资类型",getValueByKey(investment_type_map,capcompanyinfoBean.getInvestment_type_map()));
		map.put("经营年限",getValueByKey(bizTerm,capcompanyinfoBean.getBizTerm_v1_map()));
		
		
		/**1**/
		/************************************************ 财务指标 *****************************/
		Map<String,Object> obj1 = new LinkedHashMap<>();
		
		/**1.1**/
		/** 资产负债科目 **/
		Map<String,Object> sobj11 = new LinkedHashMap<>();
		
		/**1.1.1**/
		// 总资产指标
		Map<String,Object> ssObj111 = new LinkedHashMap<>();
		String index1999F = request.getParameter("index1999F");
		boolean isCheck1999 = (index1999F==null||"null".equals(index1999F))?false:true;
		ssObj111.put("最近年度", isCheck1999?request.getParameter("index1999F_operator")+request.getParameter("index1999F_curr_amount"):"");
		ssObj111.put("连续两年", isCheck1999?request.getParameter("index1999F_two_rate_down")+request.getParameter("index1999F_two_rate_up"):"");
		ssObj111.put("连续三年", isCheck1999?request.getParameter("index1999F_three_rate_down")+request.getParameter("index1999F_three_rate_up"):"");
		sobj11.put("总资产", ssObj111);
		
		/**1.1.2**/
		// 负债合计
		Map<String,Object> ssObj112 = new LinkedHashMap<>();
		String index2999F = request.getParameter("index2999F");
		boolean isCheck2999 = (index2999F==null||"null".equals(index2999F))?false:true;
		ssObj112.put("最近年度", isCheck2999?request.getParameter("index2999F_operator")+request.getParameter("index2999F_curr_amount"):"");
		ssObj112.put("连续两年", isCheck2999?request.getParameter("index2999F_two_rate_down")+request.getParameter("index2999F_two_rate_up"):"");
		ssObj112.put("连续三年", isCheck2999?request.getParameter("index2999F_three_rate_down")+request.getParameter("index2999F_three_rate_up"):"");
		sobj11.put("负债合计", ssObj112);
		
		/**1.1.3**/
		// 实收资本
		Map<String,Object> ssObj113 = new LinkedHashMap<>();
		String index4001F = request.getParameter("index4001F");
		boolean isCheck4001 = (index4001F==null||"null".equals(index4001F))?false:true;
		ssObj113.put("最近年度", isCheck4001?request.getParameter("index4001F_operator")+request.getParameter("index4001F_curr_amount"):"");
		ssObj113.put("连续两年", isCheck4001?request.getParameter("index4001F_two_rate_down")+request.getParameter("index4001F_two_rate_up"):"");
		ssObj113.put("连续三年", isCheck4001?request.getParameter("index4001F_three_rate_down")+request.getParameter("index4001F_three_rate_up"):"");
		sobj11.put("实收资本", ssObj113);
		
		/**1.1.4**/
		// 净资产
		Map<String,Object> ssObj114 = new LinkedHashMap<>();
		String index4998F = request.getParameter("index4998F");
		boolean isCheck4998 = (index4998F==null||"null".equals(index4998F))?false:true;
		ssObj114.put("最近年度", isCheck4998?request.getParameter("index4998F_operator")+request.getParameter("index4998F_curr_amount"):"");
		ssObj114.put("连续两年", isCheck4998?request.getParameter("index4998F_two_rate_down")+request.getParameter("index4998F_two_rate_up"):"");
		ssObj114.put("连续三年", isCheck4998?request.getParameter("index4998F_three_rate_down")+request.getParameter("index4998F_three_rate_up"):"");
		sobj11.put("净资产", ssObj114);
		
		obj1.put("资产负债科目", sobj11);
		
		
		/**1.2**/
		/** 利润表科目 **/
		Map<String,Object> sobj12 = new LinkedHashMap<>();
		
		/**1.2.1**/
		// 营业收入
		Map<String,Object> ssObj121 = new LinkedHashMap<>();
		String index6001F = request.getParameter("index6001F");
		boolean isCheck6001 = (index6001F==null||"null".equals(index6001F))?false:true;
		ssObj121.put("最近年度", isCheck6001?request.getParameter("index6001F_operator")+request.getParameter("index6001F_curr_amount"):"");
		ssObj121.put("连续两年", isCheck6001?request.getParameter("index6001F_two_rate_down")+request.getParameter("index6001F_two_rate_up"):"");
		ssObj121.put("连续三年", isCheck6001?request.getParameter("index6001F_three_rate_down")+request.getParameter("index6001F_three_rate_up"):"");
		sobj12.put("营业收入", ssObj121);
		
		/**1.2.2**/
		// 营业利润
		Map<String,Object> ssObj122 = new LinkedHashMap<>();
		String index6799F = request.getParameter("index6799F");
		boolean isCheck6799 = (index6799F==null||"null".equals(index6799F))?false:true;
		ssObj122.put("最近年度", isCheck6799?request.getParameter("index6799F_operator")+request.getParameter("index6799F_curr_amount"):"");
		ssObj122.put("连续两年", isCheck6799?request.getParameter("index6799F_two_rate_down")+request.getParameter("index6799F_two_rate_up"):"");
		ssObj122.put("连续三年", isCheck6799?request.getParameter("index6799F_three_rate_down")+request.getParameter("index6799F_three_rate_up"):"");
		sobj12.put("营业利润", ssObj122);
		
		/**1.2.3**/
		// 利润总额
		Map<String,Object> ssObj123 = new LinkedHashMap<>();
		String index6899F = request.getParameter("index6899F");
		boolean isCheck6899 = (index6899F==null||"null".equals(index6899F))?false:true;
		ssObj123.put("最近年度", isCheck2999?request.getParameter("index6899F_operator")+request.getParameter("index6899F_curr_amount"):"");
		ssObj123.put("连续两年", isCheck6899?request.getParameter("index6899F_two_rate_down")+request.getParameter("index6899F_two_rate_up"):"");
		ssObj123.put("连续三年", isCheck6899?request.getParameter("index6899F_three_rate_down")+request.getParameter("index6899F_three_rate_up"):"");
		sobj12.put("利润总额", ssObj123);
		
		/**1.2.4**/
		// 净利润
		Map<String,Object> ssObj124 = new LinkedHashMap<>();
		String index6999F = request.getParameter("index6999F");
		boolean isCheck6999 = (index6999F==null||"null".equals(index6999F))?false:true;
		ssObj124.put("最近年度", isCheck2999?request.getParameter("index6999F_operator")+request.getParameter("index6999F_curr_amount"):"");
		ssObj124.put("连续两年", isCheck2999?request.getParameter("index6999F_two_rate_down")+request.getParameter("index6999F_two_rate_up"):"");
		ssObj124.put("连续三年", isCheck2999?request.getParameter("index6999F_three_rate_down")+request.getParameter("index6999F_three_rate_up"):"");
		sobj12.put("净利润", ssObj124);
		
		
		obj1.put("利润表科目", sobj12);
		
		map.put("财务指标", obj1);
		
		
		
		/**2**/
		/************************************************ 纳税指标 *****************************/
		Map<String,Object> obj2 = new LinkedHashMap<>();
		
		/**2.1**/
		/** 流转税 **/
		Map<String,Object> sobj21 = new LinkedHashMap<>();
		
		/**2.1.1**/
		// 增值税
		Map<String,Object> ssObj211 = new LinkedHashMap<>();
		String index0067F = request.getParameter("index0067F");
		boolean isCheck0067 = (index0067F==null||"null".equals(index0067F))?false:true;
		ssObj211.put("最近年度", isCheck0067?request.getParameter("index0067F_operator")+request.getParameter("index0067F_curr_amount"):"");
		ssObj211.put("连续两年", isCheck0067?request.getParameter("index0067F_two_rate_down")+request.getParameter("index0067F_two_rate_up"):"");
		ssObj211.put("连续三年", isCheck0067?request.getParameter("index0067F_three_rate_down")+request.getParameter("index0067F_three_rate_up"):"");
		sobj21.put("增值税", ssObj211);
		
		/**2.1.2**/
		// 消费税
		Map<String,Object> ssObj212 = new LinkedHashMap<>();
		String index0070F = request.getParameter("index0070F");
		boolean isCheck0070 = (index0070F==null||"null".equals(index0070F))?false:true;
		ssObj212.put("最近年度", isCheck0070?request.getParameter("index0070F_operator")+request.getParameter("index0070F_curr_amount"):"");
		ssObj212.put("连续两年", isCheck0070?request.getParameter("index0070F_two_rate_down")+request.getParameter("index0070F_two_rate_up"):"");
		ssObj212.put("连续三年", isCheck0070?request.getParameter("index2999F_three_rate_down")+request.getParameter("index0070F_three_rate_up"):"");
		sobj21.put("消费税", ssObj212);
		
		/**2.1.3**/
		// 营业税
		Map<String,Object> ssObj213 = new LinkedHashMap<>();
		String index0068F = request.getParameter("index0068F");
		boolean isCheck0068 = (index0068F==null||"null".equals(index0068F))?false:true;
		ssObj213.put("最近年度", isCheck0068?request.getParameter("index0068F_operator")+request.getParameter("index0068F_curr_amount"):"");
		ssObj213.put("连续两年", isCheck0068?request.getParameter("index0068F_two_rate_down")+request.getParameter("index0068F_two_rate_up"):"");
		ssObj213.put("连续三年", isCheck0068?request.getParameter("index0077F_three_rate_down")+request.getParameter("index0068F_three_rate_up"):"");
		sobj21.put("营业税", ssObj213);
		
		/**2.1.4**/
		// 流转税合计
		Map<String,Object> ssObj214 = new LinkedHashMap<>();
		String index0071F = request.getParameter("index0071F");
		boolean isCheck0071 = (index0071F==null||"null".equals(index0071F))?false:true;
		ssObj214.put("最近年度", isCheck0071?request.getParameter("index0071F_operator")+request.getParameter("index0071F_curr_amount"):"");
		ssObj214.put("连续两年", isCheck0071?request.getParameter("index0071F_two_rate_down")+request.getParameter("index0071F_two_rate_up"):"");
		ssObj214.put("连续三年", isCheck0071?request.getParameter("index0071F_three_rate_down")+request.getParameter("index0071F_three_rate_up"):"");
		sobj21.put("流转税合计", ssObj214);
		
		obj2.put("流转税", sobj21);
		
		
		/**2.2**/
		/** 所得税 **/
		Map<String,Object> sobj22 = new LinkedHashMap<>();
		
		/**2.2.1**/
		// 企业所得税
		Map<String,Object> ssObj221 = new LinkedHashMap<>();
		String index0069F = request.getParameter("index0069F");
		boolean isCheck0069 = (index0069F==null||"null".equals(index0069F))?false:true;
		ssObj221.put("最近年度", isCheck0069?request.getParameter("index0069F_operator")+request.getParameter("index0069F_curr_amount"):"");
		ssObj221.put("连续两年", isCheck0069?request.getParameter("index0069F_two_rate_down")+request.getParameter("index0069F_two_rate_up"):"");
		ssObj221.put("连续三年", isCheck0069?request.getParameter("index0069F_three_rate_down")+request.getParameter("index0069F_three_rate_up"):"");
		sobj22.put("企业所得税", ssObj221);
		
		/**2.2.2**/
		// 个人所得税
		Map<String,Object> ssObj222 = new LinkedHashMap<>();
		String index0072F = request.getParameter("index0072F");
		boolean isCheck0072 = (index0072F==null||"null".equals(index0072F))?false:true;
		ssObj222.put("最近年度", isCheck0072?request.getParameter("index0072F_operator")+request.getParameter("index0072F_curr_amount"):"");
		ssObj222.put("连续两年", isCheck0072?request.getParameter("index0072F_two_rate_down")+request.getParameter("index0072F_two_rate_up"):"");
		ssObj222.put("连续三年", isCheck0072?request.getParameter("index0072F_three_rate_down")+request.getParameter("index0072F_three_rate_up"):"");
		sobj22.put("个人所得税", ssObj222);
		
		/**2.2.3**/
		// 所得税合计
		Map<String,Object> ssObj223 = new LinkedHashMap<>();
		String index0073F = request.getParameter("index0073F");
		boolean isCheck0073 = (index0073F==null||"null".equals(index0073F))?false:true;
		ssObj223.put("最近年度", isCheck2999?request.getParameter("index0073F_operator")+request.getParameter("index0073F_curr_amount"):"");
		ssObj223.put("连续两年", isCheck0073?request.getParameter("index0073F_two_rate_down")+request.getParameter("index0073F_two_rate_up"):"");
		ssObj223.put("连续三年", isCheck0073?request.getParameter("index0073F_three_rate_down")+request.getParameter("index0073F_three_rate_up"):"");
		sobj12.put("所得税合计", ssObj223);
		
		obj2.put("所得税", sobj22);
		
		
		/**2.3**/
		/** 其他 **/
		Map<String,Object> sobj23 = new LinkedHashMap<>();
		
		/**2.3.1**/
		// 个人所得税申报人数
		Map<String,Object> ssObj231 = new LinkedHashMap<>();
		String index0074F = request.getParameter("index0074F");
		boolean isCheck0074 = (index0074F==null||"null".equals(index0074F))?false:true;
		ssObj231.put("最近年度", isCheck0074?request.getParameter("index0074F_operator")+request.getParameter("index0074F_curr_amount"):"");
		ssObj231.put("连续两年", isCheck0074?request.getParameter("index0074F_two_rate_down")+request.getParameter("index0074F_two_rate_up"):"");
		ssObj231.put("连续三年", isCheck0074?request.getParameter("index0074F_three_rate_down")+request.getParameter("index0074F_three_rate_up"):"");
		sobj23.put("个人所得税申报人数", ssObj231);
		
		obj2.put("其他", sobj23);
		
		map.put("纳税指标", obj2);
		
		/**3**/
		/************************************************ 信贷指标 *****************************/
		Map<String,Object> obj3 = new LinkedHashMap<>();
		
		//小额贷款状态
		Map<String,String> loan_status_map = new LinkedHashMap<String, String>();
		loan_status_map.put("030087", "正常");
		loan_status_map.put("010087", "逾期");
		loan_status_map.put("020087", "呆帐");
		loan_status_map.put("040087", "结清");
		loan_status_map.put("050087", "小额贷款历史");
		obj3.put("小额贷款状态", getValueByKey(request.getParameterValues("loan_status"),loan_status_map));
		
		//银行业金融机构贷款状态
		Map<String,String> yhjr_status_map = new LinkedHashMap<String, String>();
		yhjr_status_map.put("20107", "关注");
		yhjr_status_map.put("20107", "次级");
		yhjr_status_map.put("40107", "可疑");
		yhjr_status_map.put("50107", "损失");
		yhjr_status_map.put("60107", "无贷款历史");
		yhjr_status_map.put("70107", "有贷款历史");
		obj3.put("银行业金融机构贷款状态", getValueByKey(request.getParameterValues("yhjr_status"),yhjr_status_map));
		
		//上月末银行业金融机构有贷户贷款情况
		Map<String,Object> sobj31 = new LinkedHashMap<>();
		String index0108E = request.getParameter("index0108E");
		boolean isCheck0108 = (index0108E==null||"null".equals(index0108E))?false:true;
		sobj31.put("贷款余额", isCheck0108?request.getParameter("index0108E_operator")+request.getParameter("index0108E_count"):"");
		
		String index0109E = request.getParameter("index0109E");
		boolean isCheck0109 = (index0109E==null||"null".equals(index0109E))?false:true;
		sobj31.put("授信银行数", isCheck0109?request.getParameter("index0109E_operator")+request.getParameter("index0109E_count"):"");
		
		String index0110E = request.getParameter("index0110E");
		boolean isCheck0110 = (index0110E==null||"null".equals(index0110E))?false:true;
		sobj31.put("不良贷款余额", isCheck0110?request.getParameter("index0110E_operator")+request.getParameter("index0110E_count"):"");
		
		String index0111E = request.getParameter("index0111E");
		boolean isCheck0111 = (index0111E==null||"null".equals(index0111E))?false:true;
		sobj31.put("未结清贷款笔数", isCheck0111?request.getParameter("index0111E_operator")+request.getParameter("index0111E_count"):"");
		
		obj3.put("上月末银行业金融机构有贷户贷款情况", sobj31);
		
		map.put("信贷指标", obj3);
		
		
		/**4**/
		/************************************************ 其他指标 *****************************/
		Map<String,Object> obj4 = new LinkedHashMap<>();
		
		/**4.1**/
		/** 水电气 **/
		Map<String,Object> sobj41 = new LinkedHashMap<>();
		
		/**4.1.1**/
		// 水费
		Map<String,Object> ssObj411 = new LinkedHashMap<>();
		String index0075F = request.getParameter("index0075F");
		boolean isCheck0075 = (index0075F==null||"null".equals(index0075F))?false:true;
		ssObj411.put("最近年度", isCheck0075?request.getParameter("index0075F_operator")+request.getParameter("index0075F_curr_amount"):"");
		ssObj411.put("连续两年", isCheck0075?request.getParameter("index0075F_two_rate_down")+request.getParameter("index0075F_two_rate_up"):"");
		ssObj411.put("连续三年", isCheck0075?request.getParameter("index0075F_three_rate_down")+request.getParameter("index0075F_three_rate_up"):"");
		sobj41.put("水费", ssObj411);
		
		/**4.1.2**/
		// 电费
		Map<String,Object> ssObj412 = new LinkedHashMap<>();
		String index0076F = request.getParameter("index0076F");
		boolean isCheck0076 = (index0076F==null||"null".equals(index0076F))?false:true;
		ssObj412.put("最近年度", isCheck0076?request.getParameter("index0076F_operator")+request.getParameter("index0076F_curr_amount"):"");
		ssObj412.put("连续两年", isCheck0076?request.getParameter("index0076F_two_rate_down")+request.getParameter("index0076F_two_rate_up"):"");
		ssObj412.put("连续三年", isCheck0076?request.getParameter("index0076F_three_rate_down")+request.getParameter("index0076F_three_rate_up"):"");
		sobj41.put("电费", ssObj412);
		
		/**4.1.3**/
		// 气费
		Map<String,Object> ssObj413 = new LinkedHashMap<>();
		String index0077F = request.getParameter("index0077F");
		boolean isCheck0077 = (index0077F==null||"null".equals(index0077F))?false:true;
		ssObj413.put("最近年度", isCheck0077?request.getParameter("index0077F_operator")+request.getParameter("index0077F_curr_amount"):"");
		ssObj413.put("连续两年", isCheck0077?request.getParameter("index0077F_two_rate_down")+request.getParameter("index0077F_two_rate_up"):"");
		ssObj413.put("连续三年", isCheck0077?request.getParameter("index0077F_three_rate_down")+request.getParameter("index0077F_three_rate_up"):"");
		sobj41.put("气费", ssObj413);
		
		/**4.1.4**/
		// 用水量
		Map<String,Object> ssObj414 = new LinkedHashMap<>();
		String index0078F = request.getParameter("index0078F");
		boolean isCheck0078 = (index0078F==null||"null".equals(index0078F))?false:true;
		ssObj414.put("最近年度", isCheck0078?request.getParameter("index0078F_operator")+request.getParameter("index0078F_curr_amount"):"");
		ssObj414.put("连续两年", isCheck0078?request.getParameter("index0078F_two_rate_down")+request.getParameter("index0078F_two_rate_up"):"");
		ssObj414.put("连续三年", isCheck0078?request.getParameter("index0078F_three_rate_down")+request.getParameter("index0078F_three_rate_up"):"");
		sobj41.put("用水量", ssObj414);
		
		/**4.1.5**/
		// 用电量
		Map<String,Object> ssObj415 = new LinkedHashMap<>();
		String index0079F = request.getParameter("index0079F");
		boolean isCheck0079 = (index0079F==null||"null".equals(index0079F))?false:true;
		ssObj415.put("最近年度", isCheck0079?request.getParameter("index0079F_operator")+request.getParameter("index0079F_curr_amount"):"");
		ssObj415.put("连续两年", isCheck0079?request.getParameter("index0079F_two_rate_down")+request.getParameter("index0079F_two_rate_up"):"");
		ssObj415.put("连续三年", isCheck0079?request.getParameter("index0079F_three_rate_down")+request.getParameter("index0079F_three_rate_up"):"");
		sobj41.put("用电量", ssObj415);
		
		/**4.1.6**/
		// 用气量
		Map<String,Object> ssObj416 = new LinkedHashMap<>();
		String index0080F = request.getParameter("index0080F");
		boolean isCheck0080 = (index0080F==null||"null".equals(index0080F))?false:true;
		ssObj416.put("最近年度", isCheck0080?request.getParameter("index0080F_operator")+request.getParameter("index0080F_curr_amount"):"");
		ssObj416.put("连续两年", isCheck0080?request.getParameter("index0080F_two_rate_down")+request.getParameter("index0080F_two_rate_up"):"");
		ssObj416.put("连续三年", isCheck0080?request.getParameter("index0080F_three_rate_down")+request.getParameter("index0080F_three_rate_up"):"");
		sobj41.put("用气量", ssObj416);
		
		obj4.put("水电气", sobj41);
		
		
		/**4.2**/
		/** 社保公积金 **/
		Map<String,Object> sobj42 = new LinkedHashMap<>();
		
		/**4.2.1**/
		// 社保缴纳金额
		Map<String,Object> ssObj421 = new LinkedHashMap<>();
		String index0081F = request.getParameter("index0081F");
		boolean isCheck0081 = (index0081F==null||"null".equals(index0081F))?false:true;
		ssObj421.put("最近年度", isCheck0081?request.getParameter("index0081F_operator")+request.getParameter("index0081F_curr_amount"):"");
		ssObj421.put("连续两年", isCheck0081?request.getParameter("index0081F_two_rate_down")+request.getParameter("index0081F_two_rate_up"):"");
		ssObj421.put("连续三年", isCheck0081?request.getParameter("index0081F_three_rate_down")+request.getParameter("index0081F_three_rate_up"):"");
		sobj42.put("社保缴纳金额", ssObj421);
		
		/**4.2.2**/
		// 公积金缴纳金额
		Map<String,Object> ssObj422 = new LinkedHashMap<>();
		String index0082F = request.getParameter("index0082F");
		boolean isCheck0082 = (index0082F==null||"null".equals(index0082F))?false:true;
		ssObj422.put("最近年度", isCheck0082?request.getParameter("index0082F_operator")+request.getParameter("index0082F_curr_amount"):"");
		ssObj422.put("连续两年", isCheck0082?request.getParameter("index0082F_two_rate_down")+request.getParameter("index0082F_two_rate_up"):"");
		ssObj422.put("连续三年", isCheck0082?request.getParameter("index0082F_three_rate_down")+request.getParameter("index0082F_three_rate_up"):"");
		sobj42.put("公积金缴纳金额", ssObj422);
		
		/**4.2.3**/
		// 社保缴纳人数
		Map<String,Object> ssObj423 = new LinkedHashMap<>();
		String index0083F = request.getParameter("index0083F");
		boolean isCheck0083 = (index0083F==null||"null".equals(index0083F))?false:true;
		ssObj423.put("最近年度", isCheck0083?request.getParameter("index0083F_operator")+request.getParameter("index0083F_curr_amount"):"");
		ssObj423.put("连续两年", isCheck0083?request.getParameter("index0083F_two_rate_down")+request.getParameter("index0083F_two_rate_up"):"");
		ssObj423.put("连续三年", isCheck0083?request.getParameter("index0083F_three_rate_down")+request.getParameter("index0083F_three_rate_up"):"");
		sobj42.put("社保缴纳人数", ssObj423);
		
		/**4.2.4**/
		// 公积金缴纳人数
		Map<String,Object> ssObj424 = new LinkedHashMap<>();
		String index0084F = request.getParameter("index0084F");
		boolean isCheck0084 = (index0084F==null||"null".equals(index0084F))?false:true;
		ssObj424.put("最近年度", isCheck0084?request.getParameter("index0084F_operator")+request.getParameter("index0084F_curr_amount"):"");
		ssObj424.put("连续两年", isCheck0084?request.getParameter("index0084F_two_rate_down")+request.getParameter("index0084F_two_rate_up"):"");
		ssObj424.put("连续三年", isCheck0084?request.getParameter("index0084F_three_rate_down")+request.getParameter("index0084F_three_rate_up"):"");
		sobj42.put("公积金缴纳人数", ssObj424);
		
		
		obj4.put("社保公积金", sobj42);
		
		
		/**4.3**/
		/** 进出口 **/
		Map<String,Object> sobj43 = new LinkedHashMap<>();
		
		/**4.3.1**/
		// 进口总值
		Map<String,Object> ssObj431 = new LinkedHashMap<>();
		String index0085F = request.getParameter("index0085F");
		boolean isCheck0085 = (index0085F==null||"null".equals(index0085F))?false:true;
		ssObj431.put("最近年度", isCheck0085?request.getParameter("index0085F_operator")+request.getParameter("index0085F_curr_amount"):"");
		ssObj431.put("连续两年", isCheck0085?request.getParameter("index0085F_two_rate_down")+request.getParameter("index0085F_two_rate_up"):"");
		ssObj431.put("连续三年", isCheck0085?request.getParameter("index0085F_three_rate_down")+request.getParameter("index0085F_three_rate_up"):"");
		sobj43.put("进口总值", ssObj431);
		
		/**4.3.2**/
		// 出口总值
		Map<String,Object> ssObj432 = new LinkedHashMap<>();
		String index0086F = request.getParameter("index0086F");
		boolean isCheck0086 = (index0086F==null||"null".equals(index0086F))?false:true;
		ssObj432.put("最近年度", isCheck0086?request.getParameter("index0086F_operator")+request.getParameter("index0086F_curr_amount"):"");
		ssObj432.put("连续两年", isCheck0086?request.getParameter("index0086F_two_rate_down")+request.getParameter("index0086F_two_rate_up"):"");
		ssObj432.put("连续三年", isCheck0086?request.getParameter("index0086F_three_rate_down")+request.getParameter("index0086F_three_rate_up"):"");
		sobj43.put("出口总值", ssObj432);
		
		
		obj4.put("进出口", sobj43);


		//
		Map<String, String> hg_status_map = new LinkedHashMap();
		hg_status_map.put("010088", "高级认证");
		hg_status_map.put("020088", "一般认证");
		hg_status_map.put("030088", "失信企业");
		hg_status_map.put("040088", "无海关评级");
		obj4.put("海关评级", getValueByKey(request.getParameterValues("hg_status"),hg_status_map));
		
		Map<String, String> aq_status_map = new LinkedHashMap();
		aq_status_map.put("010089", "存在较大安全事故");
		aq_status_map.put("020089", "不存在安全事故记录");
		obj4.put("安全事故记录", getValueByKey(request.getParameterValues("aq_status"),aq_status_map));
		
		Map<String, String> nashui_status_map = new LinkedHashMap();
		nashui_status_map.put("010090", "非正常户");
		nashui_status_map.put("020090", "A级");
		nashui_status_map.put("030090", "无税务评价");
		obj4.put("税务部门评价", getValueByKey(request.getParameterValues("nashui_status"),nashui_status_map));
		
		
		Map<String, String> hb_status_map = new LinkedHashMap();
		hb_status_map.put("010091", "绿色");
		hb_status_map.put("020091", "蓝色");
		hb_status_map.put("030091", "黄色");
		hb_status_map.put("040091", "红色");
		hb_status_map.put("050091", "黑色");
		hb_status_map.put("060091", "无环保信用评价");
		obj4.put("环保信用评价", getValueByKey(request.getParameterValues("hb_status"),hb_status_map));
		
		Map<String, String> ss_status_map = new LinkedHashMap();
		ss_status_map.put("010106", "有涉诉信息");
		ss_status_map.put("020106", "无涉诉信息");
		obj4.put("涉诉", getValueByKey(request.getParameterValues("ss_status"),ss_status_map));
		
		
		/**4.4*/
		Map<String,Object> ssObj44 = new LinkedHashMap<>();
		
		String index0092F = request.getParameter("index0092F");
		boolean isCheck0092 = (index0092F==null||"null".equals(index0092F))?false:true;
		ssObj44.put("处罚次数", isCheck0092?request.getParameter("index0092F_operator")+request.getParameter("index0092E_count"):"");
		
		ssObj44.put("关联条件", "0".equals(request.getParameter("index0092E_operator_symbol"))?"或":"且");
		
		String index0093F = request.getParameter("index0093F");
		boolean isCheck0093 = (index0093F==null||"null".equals(index0093F))?false:true;
		ssObj44.put("处罚金额", isCheck0093?request.getParameter("index0093F_operator")+request.getParameter("index0093E_count"):"");
		
		obj4.put("行政处罚", ssObj44);
		
		/**4.5*/
		Map<String,Object> ssObj45 = new LinkedHashMap<>();
		
		String index0094F = request.getParameter("index0094F");
		boolean isCheck0094 = (index0094F==null||"null".equals(index0094F))?false:true;
		ssObj45.put("欠缴次数", isCheck0094?request.getParameter("index0094F_operator")+request.getParameter("index0094E_count"):"");
		
		ssObj45.put("关联条件", "0".equals(request.getParameter("index0094E_operator_symbol"))?"或":"且");
		
		String index0095F = request.getParameter("index0095F");
		boolean isCheck0095 = (index0095F==null||"null".equals(index0095F))?false:true;
		ssObj45.put("欠缴金额", isCheck0095?request.getParameter("index0095F_operator")+request.getParameter("index0095E_count"):"");
		
		obj4.put("社保公积金欠缴", ssObj45);
		
		//
		Map<String, String> rongyu_status_map = new LinkedHashMap();
		rongyu_status_map.put("010096", "获省级荣誉表彰");
		rongyu_status_map.put("020096", "获市级荣誉表彰");
		rongyu_status_map.put("030096", "获区县级荣誉表彰");
		obj4.put("荣誉表彰", getValueByKey(request.getParameterValues("rongyu_status"),rongyu_status_map));
		
		map.put("其他指标", obj4);
		
		
		/***************************        信用评分        *********************/
		Map<String,Object> obj5 = new LinkedHashMap<>();
		
		obj5.put("评分分值", request.getParameter("pffzstart")+"--"+request.getParameter("pffzend"));
		
		Map<String, String> xyfdj_map = new LinkedHashMap();
		xyfdj_map.put("R1", "优秀");
		xyfdj_map.put("R2", "良好");
		xyfdj_map.put("R3", "一般");
		xyfdj_map.put("R4", "较弱");
		xyfdj_map.put("R5", "最弱");
		obj5.put("信用等级", getValueByKey(request.getParameterValues("xyfdj"),xyfdj_map));
		
		map.put("信用评分", obj5);
		
		SomeLog someLog  = new SomeLog();
		someLog.setOperName("企业查询-深度查询");
		someLog.setOperUserId(((ShiroUser)SecurityUtils.getSubject().getPrincipal()).getId());
		someLog.setOperdate(new Date());
		someLog.setParamContent(JSON.toJSONString(map));
		
		getCtx(request).getBean(SomeLogService.class).saveSomeLog(someLog);
		
	}


	/**
	 * 企业信息详情
	 */
	public void _companyInfo_companyInfoDetails (HttpServletRequest request) {
		Map<String, Object> map = new LinkedHashMap();
		
		CompanyBase companyBase = getCtx(request).getBean(CompanyInfoService.class).getCompanyBase(request.getParameter("enterpriseId"));
		map.put("企业代码", companyBase.getCode());
		
		SomeLog someLog  = new SomeLog();
		someLog.setOperName("企业信息详情");
		someLog.setOperUserId(((ShiroUser)SecurityUtils.getSubject().getPrincipal()).getId());
		someLog.setOperdate(new Date());
		someLog.setParamContent(JSON.toJSONString(map));
		
		getCtx(request).getBean(SomeLogService.class).saveSomeLog(someLog);
	}
	
	/**
	 * 评分
	 */
	public void _creditscore_queryScore (HttpServletRequest request) {
		Map<String, Object> map = new LinkedHashMap();
		System.out.println(request.getParameter("enterpriseId"));
		CompanyBase companyBase = getCtx(request).getBean(CompanyInfoService.class).getCompanyBase(request.getParameter("enterpriseId"));
		map.put("企业代码", companyBase.getCode());
		
		SomeLog someLog  = new SomeLog();
		someLog.setOperName("企业征信评分");
		someLog.setOperUserId(((ShiroUser)SecurityUtils.getSubject().getPrincipal()).getId());
		someLog.setOperdate(new Date());
		someLog.setParamContent(JSON.toJSONString(map));
		
		getCtx(request).getBean(SomeLogService.class).saveSomeLog(someLog);
	}
	
	/**
	 * 报告
	 * @throws Exception 
	 */
	public void _creditscore_queryReport (HttpServletRequest request) throws Exception {
		Map<String, Object> map = new LinkedHashMap();
		System.out.println(request.getParameter("enterpriseId"));
		CompanyBase companyBase = getCtx(request).getBean(CompanyInfoService.class).getCompanyBase(request.getParameter("enterpriseId"));
		map.put("企业代码", companyBase.getCode());
		
		SomeLog someLog  = new SomeLog();
		someLog.setOperName("企业征信报告");
		someLog.setOperUserId(((ShiroUser)SecurityUtils.getSubject().getPrincipal()).getId());
		someLog.setOperdate(new Date());
		someLog.setParamContent(JSON.toJSONString(map));
		
		getCtx(request).getBean(SomeLogService.class).saveSomeLog(someLog);
	}
	
	
	


}

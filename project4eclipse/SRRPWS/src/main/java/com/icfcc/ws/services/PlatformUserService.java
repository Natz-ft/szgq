package com.icfcc.ws.services;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.entity.CompanyBase;
import com.icfcc.SRRPDao.entity.CompanyInfoVo;
import com.icfcc.SRRPDao.entity.CompanyStockholder;
import com.icfcc.SRRPDao.entity.PlatformUser;
import com.icfcc.SRRPDao.entity.QueryLog;
import com.icfcc.SRRPDao.entity.RpCompanyCreditscores;
import com.icfcc.SRRPDao.repository.CompanyBaseDao;
import com.icfcc.SRRPDao.repository.CompanyStockholderDao;
import com.icfcc.SRRPDao.repository.PlatformUserDao;
import com.icfcc.cap.ws.creditscore.WSWebServiceClient;
import com.icfcc.util.AESUtil;
import com.icfcc.util.EmptyConditionUtils;
import com.icfcc.util.JsonUtil;
import com.icfcc.util.ReportConstant;
import com.icfcc.ws.PushUserRegistInfor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class PlatformUserService implements PushUserRegistInfor {
	public static final int HASH_INTERATIONS = 1024;
	private static Logger log = LoggerFactory
			.getLogger(PlatformUserService.class);
	// 接口路径
	@Value("${URL}")
	public String url;
	// 同步信息接口方法
	@Value("${getPath}")
	public String getPath;
	// 信息变更接口方法
	@Value("${updatePath}")
	public String updatePath;
	// 验证企业用户接口方法
	@Value("${isValidofPath}")
	public String isValidofPath;
	// 密钥
	@Value("${key}")
	public String key;
	// 接口路径
	@Value("${reportURL}")
	public String reportURL;
	// 系统用户
	@Value("${reporUser}")
	public String reporUser;
	// 访问类型
	@Value("${reason}")
	public String reason;
	// 密钥
	@Value("${reportkey}")
	public String reportkey;
	// 连接超时参数
	@Value("${connectionTimeout}")
	public Long connectionTimeout;
	// 响应超时参数
	@Value("${receiveTimeout}")
	public Long receiveTimeout;
	@Autowired
	private QueryLogService queryService;
	@Autowired
	public WSWebServiceClient client;
	@Autowired
	public QueryScoreService queryScoreService;
	
	@Override
	public String getCompanyInfo(String companyInfoJsonStr) {
		String validStatus = "1";
		Map<Object, Object> map = new HashMap<Object, Object>();
			String companyInfoStr = AESUtil.decrypt(companyInfoJsonStr, key);
			JSONObject jsonObject = JSONObject.fromObject(companyInfoStr);
			CompanyInfoVo companyInfo = (CompanyInfoVo) JSONObject.toBean(jsonObject, CompanyInfoVo.class);
			CompanyBase base = queryService.queryByCertno(companyInfo.getCode());
			if (base == null) {
				log.info("getCompanyInfo begin date :"+new Date());
				Map<Object, Object> Resumap =queryService.saveCompanyInfo(companyInfo);
				validStatus=Resumap.get("validStatus").toString();
				if(validStatus.equals("1")){
					String  enterpriseId=Resumap.get("enterpriseId").toString();
					QueryLog querylog = new QueryLog();
					querylog.setCertno(companyInfo.getCode());
					querylog.setCerttype(companyInfo.getCodetype());
					querylog.setUserid(enterpriseId);
					
					querylog.setQueryname(companyInfo.getName());
					querylog.setBegintime(new Date());
					getCompanyBaseSupplementVo(enterpriseId,querylog);// 同步工商信息
					List<RpCompanyCreditscores>   scor=queryScoreService.FindScore(companyInfo.getCode());
					if(scor.size()<1){
						QueryLog querylog1 = new QueryLog();
						querylog1.setCertno(companyInfo.getCode());
						querylog1.setCerttype(companyInfo.getCodetype());
						querylog1.setUserid(enterpriseId);
						
						querylog1.setQueryname(companyInfo.getName());
						querylog1.setBegintime(new Date());
						queryScore(companyInfo.getCode(),companyInfo.getCodetype(),querylog1);
					}
				}
				log.info("getCompanyInfo end date :"+new Date());
			} else {
				validStatus = "0";
			}
		map.put("validStatus", validStatus);
		JSONObject json = JSONObject.fromObject(map);
		validStatus = AESUtil.encrypt(json.toString(), key);
		return validStatus;
	}

	@Override
	public String updateCompanyInfo(String companyInfoJsonStr) {
		String validStatus = "1";
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			String companyInfoStr = AESUtil.decrypt(companyInfoJsonStr, key);
			JSONObject jsonObject = JSONObject.fromObject(companyInfoStr);
			CompanyInfoVo companyInfo = (CompanyInfoVo) JSONObject.toBean(jsonObject, CompanyInfoVo.class);
			String oldCode="";
			if(StringUtils.isNotEmpty(companyInfo.getOldCode())){
			    log.info("code:"+companyInfo.getOldCode());
			    oldCode=companyInfo.getOldCode();
			}else{
		         if(StringUtils.isNotEmpty(companyInfo.getCode())){
		             oldCode=companyInfo.getCode();
		         }
			}
			System.out.println("code:==============="+oldCode);
			CompanyBase base =queryService.queryByCertno(oldCode);
			PlatformUser user=queryService.findByUserName(oldCode);
			String stopFlagNum =companyInfo.getStopFlag();
			if (base == null) {
				validStatus = "0";
			} else {
			    System.out.println("=======开始进行修改========");
				base.setCode(companyInfo.getCode());
				if (!base.getCode().equals("") && base.getCode() != null) {
					if (base.getCode().length() == 18) {
						base.setCodetype("2");
					} else {
						base.setCodetype("1");
					}
				}
				System.out.println("=======参数标识========"+stopFlagNum);
                if(StringUtils.isNotEmpty(stopFlagNum)){
				    if(stopFlagNum.equals("0")){//1:启用
	                     stopFlagNum="1";//金服字典转换
	                 }else{// 2:停用
	                     stopFlagNum="2"; 
	                 }
				    System.out.println("=======参数标识修改为========"+stopFlagNum);
	                base.setStopFlag(stopFlagNum);
	              
				}
				if(user!=null){
				    user.setStopFlag(Integer.parseInt(stopFlagNum));
                    user.setUsername(companyInfo.getCode());
                    queryService.saveUser(user);
                }
				 if(StringUtils.isNotEmpty(companyInfo.getName())){
				     base.setName(companyInfo.getName());
				 }
				 if(StringUtils.isNotEmpty(companyInfo.getLegalcal())){
                     
				     base.setLegalCal(companyInfo.getLegalcal());
                 }
				 if(StringUtils.isNotEmpty(companyInfo.getLegalname())){
                     
                     base.setLegalName(companyInfo.getLegalname());
                 }
				 if(StringUtils.isNotEmpty(companyInfo.getContacname())){
                     base.setContacName(companyInfo.getContacname());
                 }
				 if(StringUtils.isNotEmpty(companyInfo.getContaccal())){
                     base.setContacCal(companyInfo.getContaccal());
                 }
				 if(StringUtils.isNotEmpty(companyInfo.getBranchno())){
                     base.setRearea(companyInfo.getBranchno());
                 }
		        base.setOperdate(new Date());
				System.out.println("=======开始修改========"+base.getStopFlag());
				queryService.saveCompanyBaseDao(base);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			validStatus = "0";
		}
		map.put("validStatus", validStatus);
		JSONObject json = JSONObject.fromObject(map);
		validStatus = AESUtil.encrypt(json.toString(), key);
		return validStatus;
	}

	@Override
	public String webSzyhWSClient(String companyInfoJsonStr, String type) {
		String result = "";
		String path = "";
		String companyInfoStr = AESUtil.decrypt(companyInfoJsonStr, key);
		JSONObject jsonObject = JSONObject.fromObject(companyInfoStr);
		CompanyInfoVo companyInfo = (CompanyInfoVo) JSONObject.toBean(jsonObject, CompanyInfoVo.class);
		QueryLog querylog = new QueryLog();
		querylog.setCertno(companyInfo.getCode());
		querylog.setCerttype(companyInfo.getCodetype());
		querylog.setQueryname(companyInfo.getName());
		querylog.setBegintime(new Date());
		querylog.setUserid(companyInfo.getCode());
		String typeStr = AESUtil.decrypt(type, key);
		switch (typeStr) {
		case "01":
			path = getPath;
			break;
		case "02":
			path = updatePath;
			break;
		case "03":
			path = isValidofPath;
			break;
		}
		log.info("调用苏州金服接口:" + url);
		try{
	    querylog.setQuerytype(typeStr);// 信用报告查询
		WebClient client = WebClient.create(url);
		client.path(path).accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		// 传参
		//log.info("companyInfoJsonStr============="+companyInfoJsonStr);
		client.replaceQueryParam("companyInfoJsonStr", companyInfoJsonStr);
		result = client.get(String.class);
		log.info("返回result:"+AESUtil.decrypt(result, key));
		querylog.setEndtime(new Date());
		querylog.setIssuc("1");// 查询成功
		queryService.addquerylog(querylog);
		} catch (Exception e) {
			querylog.setEndtime(new Date());
			querylog.setIssuc("3");// 查询失败
			queryService.addquerylog(querylog);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: getCompanyBaseSupplementVo @Description:
	 * TODO(调用工商接口，更新本地库企业补充信息) @param @param enterpriseId @param @return
	 * 参数说明 @return CompanyBaseSupplement 返回类型 @throws
	 */
	public void getCompanyBaseSupplementVo(String enterpriseId,QueryLog querylog ) {
		try {
			querylog.setQuerytype("3");// 信用报告查询
			CompanyBase companyBase = queryService.findById(enterpriseId);// 查询企业id
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer isExitInfor = new StringBuffer();
			isExitInfor.append("00,");
			String companyStr = null;
			companyStr = getResult(companyBase.getCode());// 调用征信系统接口，返回工商数据，类型是json字符串
			querylog.setEndtime(new Date());
			querylog.setIssuc("1");// 查询成功
			if (companyStr == null) {
				return;
			} else {
				String jsonStatus = StringUtils.substringBefore(companyStr, "|");
				log.info("信用报告返回的状态码:" + jsonStatus);
				if ("0".equals(jsonStatus)) {// 判断状态码 为0 返回空
					return;
				}
			}
			companyStr = "[" + StringUtils.substringAfter(companyStr, "|") + "]";
			JSONArray reportJson = new JSONArray();
			String regCurrency = "";
			reportJson = JSONArray.fromObject(companyStr);
			Map<String, String> baseMap = new HashMap<String, String>();
			JSONObject jo;
			for (int i = 0; i < reportJson.size(); i++) {
				jo = JSONObject.fromObject(reportJson.get(i));
				Iterator<?> it = jo.keys();
				while (it.hasNext()) {
					String key = it.next().toString();
					baseMap.put(key, JsonUtil.getString(jo, key));
				}
			}
			String capitalStr = getStrFMap(baseMap, ReportConstant.D2_REGCAP).toString();// 解析返回的字符串

			if (!"".equals(capitalStr) && capitalStr != null) {

				if (capitalStr.contains("人民币")) {
					capitalStr = StringUtils.substringBefore(getStrFMap(baseMap, ReportConstant.D2_REGCAP).toString(),
							"万");
					regCurrency = "01";
				} else if (capitalStr.contains("美")) {
					capitalStr = StringUtils.substringBefore(getStrFMap(baseMap, ReportConstant.D2_REGCAP).toString(),
							"万");
					regCurrency = "02";
				} else {
					capitalStr = "";
				}
			}
			String D2_ESDATE = getStrFMap(baseMap, ReportConstant.D2_ESDATE);
			String d2_dom = getStrFMap(baseMap, ReportConstant.D2_DOM);// 注册地址

			String d2_legal_per = getStrFMap(baseMap, ReportConstant.D2_LEGAL_NAME);// 法定代表人姓名
			String b2list = getStrFMap(baseMap, ReportConstant.B2LIST.TYPE);
			String registArea = null;
			if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_dom)) {
				registArea = getStrFMap(baseMap, ReportConstant.D2_DOM);
			} else {
				registArea = getStrFMap(baseMap, ReportConstant.TIME_JGDZ);
			}
			String legalNameStr;
			if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_legal_per)) {
				legalNameStr = getStrFMap(baseMap, ReportConstant.D2_LEGAL_NAME);
			} else {
				legalNameStr = getStrFMap(baseMap, ReportConstant.D2_LEGAL_PER);
			}
			if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(b2list)) {// 更新
																			// 股东信息
				String isb2List = getb2List(b2list, enterpriseId);
				isExitInfor.append(isb2List).append(",");
			}
			if (!capitalStr.equals("") && capitalStr != null) {
				isExitInfor.append("01,");
				companyBase.setRegcapital(Double.valueOf(capitalStr));// 注册资本
			}
			if (!regCurrency.equals("") && regCurrency != null) {
				companyBase.setRegCurrency(regCurrency);
				isExitInfor.append("02,");
			}
			if (!D2_ESDATE.equals("") && D2_ESDATE != null) {
				Date date = sdf.parse(D2_ESDATE);
				Date sqlDate = new java.sql.Date(date.getTime());
				companyBase.setRedate(sqlDate);// 注册时间
				isExitInfor.append("03,");
			}
			if (registArea != null && !registArea.equals("")) {
				companyBase.setRegistArea(registArea);// 注册地址
				isExitInfor.append("04,");
			}
			if (!"".equals(legalNameStr) && legalNameStr != null) {
				isExitInfor.append("05,");
				if (!"".equals(companyBase.getLegalName()) && companyBase.getLegalName() != null) {
					if (!legalNameStr.equals(companyBase.getLegalName())) {
						companyBase.setLegalCal("");
					}
				}
				companyBase.setLegalName(legalNameStr);
			}
			companyBase.setSources(isExitInfor.toString());
			queryService.saveEnterprise(companyBase);
			queryService.addquerylog(querylog);
		} catch (Exception e) {
			querylog.setEndtime(new Date());
			querylog.setIssuc("3");// 查询失败
			queryService.addquerylog(querylog);
			e.printStackTrace();
		}

	}

	public String getb2List(String jsonarray, String enterpriseId) {
		new JSONArray();
		String isShow = "07";
		try {
			JSONArray reportJson = JSONArray.fromObject(jsonarray);
			CompanyStockholder vo = null;

			if (reportJson.size() > 0) {
			    queryService.deleteCompanyStockById(enterpriseId);
				for (int i = 0; i < reportJson.size(); i++) {
					JSONObject jo = JSONObject.fromObject(reportJson.get(i));
					vo = new CompanyStockholder();
					vo.setEnterpriseId(enterpriseId);
					// 股东姓名
					vo.setHolderName(JsonUtil.getString(jo, ReportConstant.B2LIST.B2_INV) == null ? ""
							: JsonUtil.getString(jo, ReportConstant.B2LIST.B2_INV));
					// 股东类型
					vo.setHolderType(
							getContributionType(JsonUtil.getString(jo, ReportConstant.B2LIST.B2_INVTYPE) == null ? ""
									: JsonUtil.getString(jo, ReportConstant.B2LIST.B2_INVTYPE)));
					// 出资额度
					String sub = JsonUtil.getString(jo, ReportConstant.B2LIST.B2_SUBCONAM) == null ? ""
							: JsonUtil.getString(jo, ReportConstant.B2LIST.B2_SUBCONAM);
					// b2_regcapcur
					String cur = JsonUtil.getString(jo, ReportConstant.B2LIST.B2_REGCAPCUR) == null ? ""
							: JsonUtil.getString(jo, ReportConstant.B2LIST.B2_REGCAPCUR);
					if (cur.equals("") || cur.equals("156")) {
						vo.setcCurrency("01");// CNY
					} else if (cur.equals("840")) {
						vo.setcCurrency("01");// CNY
					} else {
						vo.setcCurrency("01");// CNY
					}
					DecimalFormat format = new DecimalFormat(",#.#");
					double v = format.parse(sub).doubleValue();
					vo.setContribution(Double.valueOf(v));
					// 出资比例
					String share = JsonUtil.getString(jo, "b2_controlling_share") == null ? ""
							: JsonUtil.getString(jo, "b2_controlling_share");
					share = StringUtils.substringBefore(share, "%");
					vo.setRatio(Double.valueOf(share));
					vo.setCreateTime(new Date());
					System.out
							.println("name=============" + JsonUtil.getString(jo, ReportConstant.B2LIST.B2_INV) == null
									? "" : JsonUtil.getString(jo, ReportConstant.B2LIST.B2_INV));
					// 调用函数将对象保存到数据库中
					queryService.addCompanyStockholder(vo);
				}
			} else {
				return "isNo";
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return isShow;
	}

	

	public static String getContributionType(String dicName) {
		String diccode = "0";
		switch (dicName) {
		case ReportConstant.B2_INVTYPE_10:
			diccode = "10";
			break;
		case ReportConstant.B2_INVTYPE_11:
			diccode = "11";
			break;
		case ReportConstant.B2_INVTYPE_12:
			diccode = "12";
			break;
		case ReportConstant.B2_INVTYPE_13:
			diccode = "13";
			break;
		case ReportConstant.B2_INVTYPE_14:
			diccode = "14";
			break;
		case ReportConstant.B2_INVTYPE_15:
			diccode = "15";
			break;
		case ReportConstant.B2_INVTYPE_20:
			diccode = "20";
			break;
		case ReportConstant.B2_INVTYPE_30:
			diccode = "30";
			break;
		case ReportConstant.B2_INVTYPE_31:
			diccode = "31";
			break;
		case ReportConstant.B2_INVTYPE_32:
			diccode = "32";
			break;
		case ReportConstant.B2_INVTYPE_33:
			diccode = "33";
			break;
		case ReportConstant.B2_INVTYPE_34:
			diccode = "34";
			break;
		case ReportConstant.B2_INVTYPE_35:
			diccode = "35";
			break;
		case ReportConstant.B2_INVTYPE_36:
			diccode = "36";
			break;
		case ReportConstant.B2_INVTYPE_40:
			diccode = "40";
			break;
		case ReportConstant.B2_INVTYPE_50:
			diccode = "50";
			break;
		case ReportConstant.B2_INVTYPE_90:
			diccode = "90";
			break;

		}
		return diccode;
	}

	
	public String getStrFMap(Map<String, String> map, String key) {
		if (map == null)
			return "";
		Object _o = map.get(key);

		if (_o != null)
			return _o.toString();
		else
			return "";
	}

	public String getResult(String corpCode) {
		String result = "";
		log.info("调用信用报告接口:" + reportURL);
		WebClient client = WebClient.create(reportURL);
		ClientConfiguration config = WebClient.getConfig(client);
		config.getHttpConduit().getClient().setConnectionTimeout(connectionTimeout);// 连接超时
		config.getHttpConduit().getClient().setReceiveTimeout(receiveTimeout); // 响应超时
		client.path("wsWebService/getICInfo").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		log.info("调用信用报告接口参数(加密前):" + "name:" + reporUser + ";corpCode;" + corpCode + ";reason:" + reason);
		String e_userName = AESUtil.encrypt(reporUser, key);
		String e_corpCode = AESUtil.encrypt(corpCode, key);
		log.info("调用信用报告接口参数(加密后):" + "e_userName:" + e_userName + ";e_corpCode;" + e_corpCode);
		client.replaceQueryParam("userName", e_userName).replaceQueryParam("corpCode", e_corpCode);
		result = AESUtil.decrypt(client.get(String.class), key);
		log.info("jiemi ================" + client.get(String.class));
		log.info("调用信用报告接口结束，返回结果:" + result);
		return result;
	}

	public void queryScore(String code,String  codetype,QueryLog querylog ) {
		try {
			querylog.setQuerytype("4");// 信用报告查询
			// 记录日志
			// 先判断评分状态是否正常
			if (client.getStatus()) {
				// 若接口成功返回评分数据，则本月内不在执行
				// 先查询机构表的总机构数
				// 返回的查询结果
				String result = client.getScoreInfos(code+",");
				if (null != result && !"".equals(result.trim())) {
					// 先清空当前表数据，然后在进行持久化操作
					JSONArray compScores = JSONArray.fromObject(result);
					// 循环，解析每条json 封装到对象
					for (int ii = 0; ii < compScores.size(); ii++) {
						RpCompanyCreditscores scoreInfo = new RpCompanyCreditscores();
						// 取出来第一个json
						JSONObject scoreJson = (JSONObject) compScores.get(ii);
						// 解析封装成对象
						queryScoreService.analysisJSON(scoreInfo, scoreJson);
						List<RpCompanyCreditscores> scor=queryScoreService.FindScore(code);
						if(scor.size()<1){
							scoreInfo.setCreditcode(code);
							scoreInfo.setCreditype(codetype);
							queryScoreService.saveScore(scoreInfo);
						}
						
					}
					// 持久化
				}
			}
			querylog.setEndtime(new Date());
			querylog.setIssuc("1");// 查询成功
			queryService.addquerylog(querylog);
		} catch (Exception e) {
			querylog.setEndtime(new Date());
			querylog.setIssuc("3");// 查询失败
			queryService.addquerylog(querylog);
			e.printStackTrace();
		}
	}
	public static void main(String arg[]){
	    String code="";
	    CompanyInfoVo companyInfo=new CompanyInfoVo();
	    if(StringUtils.isNotEmpty(companyInfo.getOldCode())){
            System.out.println("1");
        }else{
            System.out.println("2");

        }
	}
}

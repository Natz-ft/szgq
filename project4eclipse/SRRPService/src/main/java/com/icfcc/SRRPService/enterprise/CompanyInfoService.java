package com.icfcc.SRRPService.enterprise;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.EmptyConditionUtils;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ReportConstant;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachment;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyAttachmentPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBasePending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBaseSupplement;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyMember;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjection;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyObjectionPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyProduct;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholder;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyStockholderPending;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyUnionInfoResult;
import com.icfcc.SRRPDao.jpa.entity.enterprise.HistoryCompanyBase;
import com.icfcc.SRRPDao.jpa.entity.managedept.QueryCompanyAuditResult;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyAttachmentDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyAttachmentPendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBaseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBasePendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyBaseSupplementDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyMemberDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyObjectionDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyObjectionPendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyProductDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyStockholderDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.CompanyStockholderPendingDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.HistoryCompanyBaseDao;
import com.icfcc.SRRPDao.jpa.repository.enterprise.impl.CompanyInfoDaoImpl;
import com.icfcc.SRRPDao.jpa.repository.managedept.impl.QueryCompanyAuditResultImpl;
import com.icfcc.SRRPService.util.AESUtil;
import com.icfcc.credit.platform.util.FileUtil;
import com.icfcc.credit.platform.util.JsonUtil;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 
 * 企业相关信息查询
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(value = "transactionManager")
public class CompanyInfoService {

	@Autowired
	private CompanyBaseDao companyBaseDao;

	@Autowired
	private HistoryCompanyBaseDao historyCompanyBaseDao;

	@Autowired
	private CompanyBaseSupplementDao companyBaseSupplementDao;

	@Autowired
	private CompanyBasePendingDao companyBasePendingDao;

	@Autowired
	private CompanyProductDao companyProductDao;
	@Autowired
	public QueryCompanyAuditResultImpl auditResultImpl;

	@Autowired
	private CompanyStockholderDao companyStockholderDao;
	@Autowired
	private CompanyStockholderPendingDao stockholderPendingDao;
	@Autowired
	private CompanyAttachmentDao companyAttachmentDao;
	@Autowired
	private CompanyAttachmentPendingDao companyAttachmentPendingDao;
	@Autowired
	private CompanyInfoDaoImpl companyInfoDaoImpl;
	
	@Autowired
	private CompanyObjectionDao objectionDao;
	
	@Autowired
	private CompanyObjectionPendingDao objectionPendingDao;
	
	@Autowired
	private CompanyMemberDao memberDao;
	// 接口路径
	@Value("${reportURL}")
	public String url;
	// 系统用户
	@Value("${reporUser}")
	public String reporUser;
	// 访问类型
	@Value("${reason}")
	public String reason;
	// 密钥
	@Value("${key}")
	public String key;
	// 连接超时参数
	@Value("${connectionTimeout}")
	public Long connectionTimeout;
	// 响应超时参数
	@Value("${receiveTimeout}")
	public Long receiveTimeout;
	@Value("${gqURL}")
	public String gqURL;
	@Value("${gqkey}")
	public String gqkey;
	
	@Value("${gqCompanyUser}")
	public String gqCompanyUser;
	@Value("${gqOrgUser}")
	public String gqOrgUser;
	/**
	 * 根据企业enterpriseId获取企业基本信息
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public CompanyBase getCompanyBase(String enterpriseId) {

		return companyBaseDao.findById(enterpriseId);
	}

	public CompanyBase queryByCertno(String contno) {
		CompanyBase baseInfo = companyBaseDao.queryByCertno(contno);
		return baseInfo;
	}

	/**
	 * 根据企业enterpriseId获取企业补充信息
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public CompanyBaseSupplement getCompanyBaseSupplement(String enterpriseId) {

		return companyBaseSupplementDao.findById(enterpriseId);
	}

	/**
	 * 根据企业enterpriseId获取企业产品信息列表
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public List<CompanyProduct> getCompanyProducts(String enterpriseId) {

		return (List<CompanyProduct>) companyProductDao.findAll(enterpriseId);
	}

	/**
	 * 根据企业enterpriseId获取企业股东信息列表
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public List<CompanyStockholder> getCompanyStockholders(String enterpriseId) {

		return (List<CompanyStockholder>) companyStockholderDao
				.findAll(enterpriseId);
	}

	/**
	 * 根据企业的id查询待审核的股东信息表
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public List<CompanyStockholderPending> getCompanyStockholderPendings(
			String enterpriseId) {

		return (List<CompanyStockholderPending>) stockholderPendingDao
				.findAll(enterpriseId);
	}

	/**
	 * 根据企业enterpriseId获取企业相关附件列表
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public List<CompanyAttachment> getCompanyAttachments(String enterpriseId) {

		return (List<CompanyAttachment>) companyAttachmentDao
				.findAll(enterpriseId);
	}

	/**
	 * 查询所有企业信息列表
	 * 
	 * @param wherecase
	 */
	public List<CompanyUnionInfoResult> getCompanyList(
			QueryCondition queryCondition) {

		return (List<CompanyUnionInfoResult>) companyInfoDaoImpl
				.getCompanyList(queryCondition);
	}

	/**
	 * 根据查询条件查询总条数
	 * 
	 * @Title: getCompanyCount
	 * @param companyInfo
	 * @return int
	 */
	public Object getCompanyCount(QueryCondition queryCondition) {
		return companyInfoDaoImpl.getCompanyCount(queryCondition);
	}

	/**
	 * 添加企业补充信息
	 */
	public void addCompanyBaseSupplement(CompanyBaseSupplement baseSupplement) {

		companyBaseSupplementDao.save(baseSupplement);
	}

	/**
	 * 添加股东信息表
	 * 
	 * @param companyStockholder
	 */
	public void addCompanyStockholder(CompanyStockholder companyStockholder) {

		companyStockholderDao.save(companyStockholder);
	}

	/**
	 * 添加技术产品信息
	 * 
	 * @param companyProduct
	 */
	public void addCompanyProduct(CompanyProduct companyProduct) {
		companyProductDao.save(companyProduct);
	}

	/**
	 * 添加公司相关附件信息
	 * 
	 * @param companyAttachment
	 */
	public void addCompanyAttachment(CompanyAttachment companyAttachment) {
		companyAttachmentDao.save(companyAttachment);
	}

	/**
	 * 根据股东信息的id删除股东信息
	 * 
	 * @param holderId
	 */
	public void deleteStockholder(String holderId) {
		companyStockholderDao.delete(holderId);
	}

	/**
	 * 根据股东信息id查询股东信息表中数据
	 * 
	 * @param holderId
	 * @return
	 */
	public CompanyStockholder findStockholder(String holderId) {
		return companyStockholderDao.findOne(holderId);
	}

	/**
	 * 根据股东信息的id删除待审核信息
	 * 
	 * @param holderId
	 */
	public void deleteStockholderPeding(String holderId) {
		stockholderPendingDao.delete(holderId);
	}

	/**
	 * 根据技术产品的id删除技术产品信息
	 * 
	 * @param productId
	 */
	public void deleteProduct(String productId) {
		companyProductDao.delete(productId);
	}

	/**
	 * 根据相关附件信息id删除相关附件
	 * 
	 * @param createrId
	 */
	public void deleteAttachment(String createrId) {
		companyAttachmentDao.delete(createrId);
	}

	/**
	 * 根据相关附件信息id删除相关附件
	 * 
	 * @param createrId
	 */
	public CompanyAttachment findAttachmentById(String createrId) {
		return companyAttachmentDao.findOne(createrId);
	}

	/**
	 * 企业菜单---企业信息---编辑企业信息--提交企业信息
	 * 
	 * @param companyBase
	 */
	public void saveEnterprise(CompanyBase companyBase) {
		companyBaseDao.save(companyBase);
	}

	/**
	 * 根据企业的id查询待审核表中是否有数据
	 * 
	 * @param EnterpriseId
	 * @return
	 */
	public CompanyBasePending findCompanyBasePendingByEnterpriseId(
			String EnterpriseId) {
		return companyBasePendingDao.findByEnterpriseId(EnterpriseId);
	}

	public void saveCompanyBasePending(CompanyBasePending companyBasePending) {
		companyBasePendingDao.save(companyBasePending);
	}
	
	public CompanyBasePending findByCode(
			String code) {
		return companyBasePendingDao.findByCode(code);
	}
	public void deleteByCode(String code) {
		companyBasePendingDao.deleteByCode(code);
	}
	/**
	 * 保存历史的企业信息
	 * 
	 * @param historyCompanyBase
	 */
	public void saveHisEnterprise(HistoryCompanyBase historyCompanyBase) {
		historyCompanyBaseDao.save(historyCompanyBase);
	}

	/**
	 * 根据企业的id更新企业的使用状态
	 * 
	 * @param enterprsiseId
	 * @param stopFlag
	 */
	public void updateStopFlag(String stopFlag, String enterpriseId) {
		companyBaseDao.updateStopFlag(stopFlag, enterpriseId);
	}
	/**
     * 企业停用所有stopflag为空的企业
     * 
     * @param enterprsiseId
     * @param stopFlag
     */
    public void updateAllStopFlag(String stopFlag) {
        companyBaseDao.updateAllStopFlag(stopFlag);
    }
	/**
	 * 添加公司相关附件信息
	 * 
	 * @param companyAttachment
	 */
	public void addCompanyAttachment(List<CompanyAttachment> entities) {

		companyAttachmentDao.save(entities);
	}

	/**
	 * 根据相关附件信息id删除相关附件
	 * 
	 * @param createrId
	 */
	public CompanyAttachment findAttachmentByType(String createrId, String type) {
		return companyAttachmentDao.findAttachmentByType(createrId, type);
	}

	/**
	 * 根据企业的id以及相关附件的类型查询待审核相关附件
	 * 
	 * @param createrId
	 * @param type
	 * @return
	 */
	public CompanyAttachmentPending findAttachmentPendingByType(
			String createrId, String type) {
		return companyAttachmentPendingDao.findAttachmentPendingByType(
				createrId, type);
	}

	/**
	 * 根据企业的id查询企业的成员列表
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public List<CompanyMember> findCompanyMemberByEventId(String enterpriseId) {
		return memberDao.findCompanyMemberByEventId(enterpriseId);
	}

	/**
	 * 添加团队成员信息
	 * 
	 * @param companyMember
	 */
	public void saveCompanyMember(CompanyMember companyMember) {
		memberDao.save(companyMember);
	}

	/**
	 * 添加股东待审核信息
	 * 
	 * @param stockholderPending
	 */
	public void saveCompanyStockPending(
			CompanyStockholderPending stockholderPending) {
		stockholderPendingDao.save(stockholderPending);
	}
	public void saveCompanyStockPendings(List<CompanyStockholderPending> CompanyStockholderPendings) {
		stockholderPendingDao.save(CompanyStockholderPendings);
	}
	public void deleteById(String enterId) {
		 stockholderPendingDao.deleteById(enterId);
	}
	public List<CompanyStockholderPending> findAll(String enterId) {
		return stockholderPendingDao.findAll(enterId);
	}
	
	/**
	 * 向待审核相关附件表中添加待审核的营业执照
	 * 
	 * @param comAttchPending
	 */
	public void addCompanyAttachmentPending(
			CompanyAttachmentPending comAttchPending) {
		companyAttachmentPendingDao.save(comAttchPending);
	}

	/**
	 * 根据主键删除待审核信息
	 * 
	 * @param createId
	 */
	public void deleCompanyAttachmentPending(String createId) {
		companyAttachmentPendingDao.delete(createId);
	}

	/**
	 * 根据成员的id删除成员信息
	 * 
	 * @param memberId
	 */
	public void deleteMember(String memberId) {
		memberDao.delete(memberId);
	}
	/**
	 * 
	* @Title: getAuthByte 
	* @Description: TODO(存量企业调用授权书) 
	* @param @return  参数说明 
	* @return byte[]    返回类型 
	* @throws
	 */
	public Map<String, String> getQueryAuthName(String enterpriseId){
		
			Map<String, String> authNameMap = new HashMap<String, String>();
		 try {
   		     CompanyBase companyBase = getCompanyBase(enterpriseId);// 查询企业id
			WebClient client = WebClient.create(gqURL);
	    	client.path("gqAuthService/getAuthStream").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
	    	client.replaceQueryParam("corpCode",companyBase.getCode()).replaceQueryParam("cardType", companyBase.getCodetype()).replaceQueryParam("wsUser", "szgqpt");
	    	String result = client.get(String.class);
	    	JSONObject jObject=null;
	    	if(AESUtil.decrypt(result, gqkey).contains("{")){
	    		JSONObject.fromObject(AESUtil.decrypt(result, gqkey));
	    		Map mapType = jObject;  
		    	if(mapType.containsKey("QueryAuthStr")){
		    		
		    		authNameMap.put("QueryAuthName", mapType.get("QueryAuthName").toString());
		    	}else{
		    		authNameMap.put("QueryAuthName", "01");
		    	}
		    	if(mapType.containsKey("QueryLicenceStr")){
		    		authNameMap.put("QueryLicenceName", mapType.get("QueryLicenceName").toString());
		    	}else if(mapType.containsKey("ClctLicenceStr")){
		    		authNameMap.put("QueryLicenceName", mapType.get("ClctLicenceName").toString());
		    	}else if(mapType.containsKey("WegtLicenceStr")){
		    		authNameMap.put("QueryLicenceName", mapType.get("WegtLicenceName").toString());
		    	}else{
		    		authNameMap.put("QueryLicenceName", "01");
		    	}
	    	}else{
	    		authNameMap.put("QueryAuthName", "01");
				authNameMap.put("QueryLicenceName", "01");
	    	}
	    	
	    	
		} catch (Exception e) {
			authNameMap.put("QueryAuthName", "01");
			authNameMap.put("QueryLicenceName", "01");
			e.printStackTrace();
			return authNameMap;
		}
	 return authNameMap;
	}
	public byte[] getAuthByte(String enterpriseId,String type){
		byte[] byte1=null;
	   	 try {
	   		    CompanyBase companyBase = getCompanyBase(enterpriseId);// 查询企业id
				WebClient client = WebClient.create(gqURL);
		    	client.path("gqAuthService/getAuthStream").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		    	client.replaceQueryParam("corpCode",companyBase.getCode()).replaceQueryParam("cardType", companyBase.getCodetype()).replaceQueryParam("wsUser", "szgqpt");
		    	String result = client.get(String.class);
		    	JSONObject jObject=JSONObject.fromObject(AESUtil.decrypt(result, gqkey));
		    	Map  mapType= jObject;  
		    	sun.misc.BASE64Decoder  decoder = new sun.misc.BASE64Decoder();
		    	if(type.equals("01")){
		    		if(mapType.containsKey("QueryAuthStr")){
			    		byte1=decoder.decodeBuffer(mapType.get("QueryAuthStr").toString());
			    	}
		    	}
		    	if(type.equals("02")){
		    		if(mapType.containsKey("QueryLicenceStr")){
			    		byte1=decoder.decodeBuffer(mapType.get("QueryLicenceStr").toString());
			    	}else if(mapType.containsKey("ClctLicenceStr")){
			    		byte1=decoder.decodeBuffer(mapType.get("ClctLicenceStr").toString());
			    	}else if(mapType.containsKey("WegtLicenceStr")){
			    		byte1=decoder.decodeBuffer(mapType.get("WegtLicenceStr").toString());
			    	}
		    	}
		    	
		    	
			} catch (Exception e) {
				e.printStackTrace();
			}
   	 
		return byte1;
   	 
    }
	public void SvaeImaORPdf(String byteStr ,String name,String imapath){
		OutputStream out = null;
		try {
		sun.misc.BASE64Decoder  decoder = new sun.misc.BASE64Decoder();
    	byte[] byte1=decoder.decodeBuffer(byteStr);
         out = new FileOutputStream(imapath);      
         out.write(byte1);  
         out.flush();
         out.close();  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	/**
	 * 
	 * @Title: getCompanyBaseSupplementVo
	 * @Description: TODO(调用工商接口，更新本地库企业补充信息)
	 * @param @param enterpriseId
	 * @param @return 参数说明
	 * @return CompanyBaseSupplement 返回类型
	 * @throws
	 */
	public void getCompanyBaseSupplementVo(String enterpriseId) {
		try {
			 CompanyBase companyBase = getCompanyBase(enterpriseId);// 查询企业id
//			String companyStr = AESUtil.decrypt("NPrTx0EOPjE8SfAZlThyqYVB2fpqaqwzjOui5LxZ4+HDbGtenw1Bq8i8JA7aFi7P5m5+gRO/f17zBK6BzZ8J6uDbRfpz63NRtVpFlAcrSLo6Go4vJXF3J4MzVA7y9Imhs0BMDbg2v45eLHj1DRrXqdQF7vqgIxV2y8GcK2Z4L7JUixdApoACi6KpSKzUJxYfXJO/tUxsBSOQ5NhlDt/AayFW4ratFogmpEC1T5k78j93Qdwf0Qrisr2TAPqUQkRr+VIA3J3pHHjxuXzSKM+2jkOiMH0ps81YXZK5qEAK+ZgyMMTrcl5I5LNMIE3/rxxbUDDAKxZdwOzMaipjB4TE9FwWkeMuU2wbqxD25aCDXRZR63BzSkG2CNwqVti9SOzQp2nxD3ox+r+R+/h6IIjIeQH377Ibin/QmDFO2cyiQreOyaL0CGXCvg2U42uqq9mSWVh/QQ0Bt2+6KSlTIoB8Dg+WoDwqxYl2MO+n0Wv6dmN+wu/NFqFGwdJavj0ux+8R9B2d1V3c7O1kZvBnTT+cZRag571+pIVSn3/8Am9jCVet7WcNc5jnCSK/7/pDeE5MDXIgwPyU/jnbqsirazR0Q3bbAhazpuPMCNbi9g70hEfacPQGm6TI7nXUfEmFuP2f/6tfPp8S4+Btz7EjKixmuhcUVcVRNBezyY7hNNkZLFQzFCqAsfJe6lyoOo+nrdlgj/sOnUUf84KUoGo/cLyabE0AwdXP2ppgVQkQUTN4gfiHcOIP3cgNY0qNVozZ8yn0HTmld3HLYGGzwWYTCmjck7aciyGtVH3S5YV6Q3QSWuF085SLyo2OnfAanw6UOgJPJnfooB3+VErADDfiFrC8BCXh5SYUFgzPjtln/p/CMou3wYm2/KyXp9FerTBe6Biu/hq1E/L2SHDsjUK/Yrjwi+oGC4e/NI5wGvQQ9I5Tbz1af10O40qjZRlxI08LNimhJDEkUJ6/CGcRQtNioIle+G0TINmp1oOUjeq/fhXAbnml4NDS1h0p98nj0iN/YeZP4tsAyJ1LDUgExPPBDsFkFnJdcfaLd7HFODrO6Fos5DzIw9EpyTbIX+CxTlPf2o0yAu/DD0eut2X1aU63ZNPe0JwJYd7NKjLNYtXXIND4URob+BcX0eB6mualyi1fIBp5FXhybp2LVR0xvCSYXMwE3/PuR9GzOD8j0lBi4D5VM4OV21RM88bUIUyfQsygmVJsYRnPiAUNVS/ekWfTMfrF8w==","E053040812ACB857");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer isExitInfor = new StringBuffer();
			isExitInfor.append("00,");
			String companyStr = null;
			companyStr = getResult(companyBase.getCode());// 调用征信系统接口，返回工商数据，类型是json字符串
			if (companyStr == null) {
				return;
			} else {
				String jsonStatus = StringUtils.substringBefore(companyStr, "|");
				System.out.println("信用报告返回的状态码:" + jsonStatus);
				if ("0".equals(jsonStatus)) {// 判断状态码 为0 返回空
					return;
				}
			}
			companyStr = "[" + StringUtils.substringAfter(companyStr, "|")
					+ "]";
			JSONArray reportJson = new JSONArray();
			String regCurrency="";
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
			CompanyBasePending companyBasePending = findCompanyBasePendingByEnterpriseId(enterpriseId);// 查询待审核企业信息表
			String capitalStr = getStrFMap(baseMap, ReportConstant.D2_REGCAP).toString();// 解析返回的字符串
			
			if (!"".equals(capitalStr) && capitalStr != null) {

				if (capitalStr.contains("人民币")) {
					capitalStr = StringUtils.substringBefore(
							getStrFMap(baseMap, ReportConstant.D2_REGCAP)
									.toString(), "万");
					regCurrency="01";
				} else if (capitalStr.contains("美")) {
					capitalStr = StringUtils.substringBefore(
							getStrFMap(baseMap, ReportConstant.D2_REGCAP)
									.toString(), "万");
					regCurrency="02";
				}else{
					capitalStr="";
				}
			}
			String D2_ESDATE = getStrFMap(baseMap, ReportConstant.D2_ESDATE);
			String d2_dom = getStrFMap(baseMap, ReportConstant.D2_DOM);// 注册地址

			String d2_legal_per = getStrFMap(baseMap,
					ReportConstant.D2_LEGAL_NAME);// 法定代表人姓名
			String b2list=getStrFMap(baseMap, ReportConstant.B2LIST.TYPE);
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
			if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(b2list)) {// 更新 股东信息
				String isb2List=getb2List(b2list,enterpriseId);
				isExitInfor.append(isb2List).append(",");
			}
//			String legalCalStr = getStrFMap(baseMap,
//					ReportConstant.D2_LEGAL_PHONE);

			if (companyBasePending == null) {// 保存基本信息
				if (!capitalStr.equals("") && capitalStr!=null) {
					isExitInfor.append("01,");
					companyBase.setRegcapital(Double.valueOf(capitalStr));// 注册资本
				}
				if(!regCurrency.equals("") && regCurrency!=null){
					companyBase.setRegCurrency(regCurrency);
//					isExitInfor.append("01,");
				}
				if (!D2_ESDATE.equals("") && D2_ESDATE!=null) {
					Date date = sdf.parse(D2_ESDATE);
					Date sqlDate = new java.sql.Date(date.getTime());
					companyBase.setRedate(sqlDate);// 注册时间
					isExitInfor.append("02,");
				}
				if (registArea != null && !registArea.equals("")) {
					companyBase.setRegistArea(registArea);// 注册地址
					isExitInfor.append("03,");
				}
				if (!"".equals(legalNameStr) && legalNameStr != null ) {
					isExitInfor.append("04,");
					if(!"".equals(companyBase.getLegalName()) && companyBase.getLegalName() != null ) {
						if(!legalNameStr.equals(companyBase.getLegalName())){
							companyBase.setLegalCal("");
						}
					}
					
					companyBase.setLegalName(legalNameStr);
				}
//				if (!"".equals(legalCalStr)) {
//					companyBase.setLegalCal(legalCalStr);
//					isExitInfor.append("06");
//				}
				//companyBase.setRegcapital(Double.valueOf("100.00"));// 注册资本
				companyBase.setSources(isExitInfor.toString());
				saveEnterprise(companyBase);
			} else {// 更新待审核信息
				if (!capitalStr.equals("") && capitalStr!=null) {
					companyBasePending
							.setRegcapital(Double.valueOf(capitalStr));// 注册资本
					isExitInfor.append("01,");
				}
				if(!regCurrency.equals("") && regCurrency!=null){
					companyBasePending.setRegCurrency(regCurrency);
					isExitInfor.append("01,");
				}
				if (!D2_ESDATE.equals("") && D2_ESDATE!=null) {
					Date date = sdf.parse(D2_ESDATE);
					Date sqlDate = new java.sql.Date(date.getTime());
					companyBasePending.setRedate(sqlDate);// 注册时间
					isExitInfor.append("02,");
				}
				if (registArea != null && !registArea.equals("")) {
					companyBasePending.setRegistArea(registArea);// 注册地址
					isExitInfor.append("03,");
				}
				if (!"".equals(legalNameStr) && legalNameStr != null ) {
					companyBasePending.setLegalName(legalNameStr);
					isExitInfor.append("04,");
				}
//				if (!"".equals(legalCalStr)) {
//					companyBasePending.setLegalCal(legalCalStr);
//					isExitInfor.append("06,");
//				}
				companyBasePending.setSources(isExitInfor.toString());
				saveCompanyBasePending(companyBasePending);
				companyBase.setSources(isExitInfor.toString());
				saveEnterprise(companyBase);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getb2List(String jsonarray , String enterpriseId) {
		new JSONArray();
		String isShow="05";
		try {
			JSONArray reportJson = JSONArray.fromObject(jsonarray);
			CompanyStockholder vo = null;
			
			if(reportJson.size()>0){
				companyStockholderDao.deleteById(enterpriseId);
			for (int i = 0; i < reportJson.size(); i++) {
				JSONObject jo = JSONObject.fromObject(reportJson.get(i));
				vo = new CompanyStockholder();
				vo.setEnterpriseId(enterpriseId);
				// 股东姓名
				vo.setHolderName(JsonUtil.getString(jo,
						ReportConstant.B2LIST.B2_INV) == null ? "" : JsonUtil
						.getString(jo, ReportConstant.B2LIST.B2_INV));
				// 股东类型
				vo.setHolderType(getContributionType(JsonUtil.getString(jo,
						ReportConstant.B2LIST.B2_INVTYPE) == null ? ""
						: JsonUtil.getString(jo,
								ReportConstant.B2LIST.B2_INVTYPE)));
				// 出资额度
				String sub = JsonUtil.getString(jo,
						ReportConstant.B2LIST.B2_SUBCONAM) == null ? ""
						: JsonUtil.getString(jo,
								ReportConstant.B2LIST.B2_SUBCONAM);
				//b2_regcapcur
				String cur = JsonUtil.getString(jo,
						ReportConstant.B2LIST.B2_REGCAPCUR) == null ? ""
						: JsonUtil.getString(jo,
								ReportConstant.B2LIST.B2_REGCAPCUR);
				if(cur.equals("")|| cur.equals("156")){
					vo.setcCurrency("01");//CNY
				}else if(cur.equals("840")){
					vo.setcCurrency("01");//CNY
				}else{
					vo.setcCurrency("01");//CNY
				}
				DecimalFormat format = new DecimalFormat(",#.#");
				if(!("").equals(sub)&& sub!=null){
					double v = format.parse(sub).doubleValue();
					vo.setContribution(Double.valueOf(v));
				}
				// 出资比例
				String share = JsonUtil.getString(jo, "b2_controlling_share") == null ? ""
						: JsonUtil.getString(jo, "b2_controlling_share");
				if(!("").equals(share)&& share!=null){
					share = StringUtils.substringBefore(share, "%");
					vo.setRatio(Double.valueOf(share));
				}
				vo.setCreateTime(new Date());
				System.out.println("name============="+ JsonUtil.getString(jo,ReportConstant.B2LIST.B2_INV) == null ? "" : JsonUtil.getString(jo,ReportConstant.B2LIST.B2_INV));
				// 调用函数将对象保存到数据库中
				addCompanyStockholder(vo);
			}
			}else{
				return "isNo";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
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
		System.out.println("调用信用报告接口:" + url);
		WebClient client = WebClient.create(url);
		ClientConfiguration config = WebClient.getConfig(client);
		config.getHttpConduit().getClient()
				.setConnectionTimeout(connectionTimeout);// 连接超时
		config.getHttpConduit().getClient().setReceiveTimeout(receiveTimeout); // 响应超时
		client.path("wsWebService/getICInfo")
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);
		System.out.println("调用信用报告接口参数(加密前):" + "name:" + reporUser
				+ ";corpCode;" + corpCode + ";reason:" + reason);
		String e_userName = AESUtil.encrypt(reporUser, key);
		String e_corpCode = AESUtil.encrypt(corpCode, key);
		System.out.println("调用信用报告接口参数(加密后):" + "e_userName:" + e_userName
				+ ";e_corpCode:" + e_corpCode);
		client.replaceQueryParam("userName", e_userName).replaceQueryParam(
				"corpCode", e_corpCode);
		result = AESUtil.decrypt(client.get(String.class), key);
		System.out.println("jiemi ================"+client.get(String.class));
		System.out.println("调用信用报告接口结束，返回结果:" + result);
		return result;
	}

	/**
	 * 根据企业的id查询企业审核信息
	 * 
	 * @param enterpriseId
	 * @return
	 */
	public List<QueryCompanyAuditResult> getAuditList(String enterpriseId) {
		return auditResultImpl.findCompanyAuditList(enterpriseId);
	}
	/**
	 * 1.调征信系统接口，同步企业授权书
	 * 2.接口返回导入结果
	 * 3.导入成功，企业激活，否则激活失败
	 * 征信系统通过金融办授权，一家企业可以被多家授权机构激活
	 * @param map
	 * @return
	 */
	
	public  Map<String, Object>  importZXSQInfo(Map<String, String> baseMap,String userType){
		WebClient client = WebClient.create(gqURL);
		if(userType.equals("01")){//企业
			baseMap.put("wsUser",gqCompanyUser);
		}else{//机构
			baseMap.put("wsUser",gqOrgUser);
		}
		Map<String, Object> resultMap=null;
		ClientConfiguration config = WebClient.getConfig(client);
		config.getHttpConduit().getClient().setConnectionTimeout(connectionTimeout);// 连接超时
		config.getHttpConduit().getClient().setReceiveTimeout(receiveTimeout); // 响应超时
		client.path("gqAuthService/saveGQAuth").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		String JSONbaseMap=AESUtil.encrypt(JSON.toJSONString(baseMap),gqkey);
		String  result =client.post(JSONbaseMap,String.class);
		result =AESUtil.decrypt(result, gqkey);
		resultMap = JSON.parseObject(result);
		return resultMap;
	}
	public static void main(String arg[]) {
   	 try {
			WebClient client = WebClient.create("http://172.18.6.88:8094/gq-ws/");
	    	client.path("gqAuthService/saveGQAuth").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
	    	Map<String, String> baseMap=new HashMap<String, String>();
	    	sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	    	baseMap.put("authFilePath",encoder.encode(FileUtil.getBytesByFile("E://123.pdf")));//影像信息的流
			baseMap.put("fileType","pdf");//企业或者机构的名称
			baseMap.put("corpName","四川秦巴新城投资集团有限公司1");//企业或者机构的名称
			baseMap.put("cardCode", "915119000582076372");//组织机构码、社会信用码
			baseMap.put("cardType", "2");//组织机构码、社会信用码
			baseMap.put("legalName", "胡国涛");//法人姓名
			baseMap.put("legalPhone","17310853721" );//法人电话
			baseMap.put("contactName","胡国涛" );//联系人姓名
			baseMap.put("contactPhone","17310853721" );//联系人电话
			baseMap.put("wsUser","szgqpt");//归属用户（企业：GQQY-SQLR-0201机构：szgqpt）
			String JSONbaseMap=AESUtil.encrypt(JSON.toJSONString(baseMap),"E903075E935D41E0");
//	    	client.replaceQueryParam("auth","JSONbaseMap1");//{"reponseCode":"200"}
			//{"reponseCode":"400","reponseMsg":"本机构已登记过该企业授权，不允许重复提交。"}

	    	String result = client.post(JSONbaseMap,String.class);
	    	if(result!=null && !result.equals("")){
	    		result=AESUtil.decrypt(result, "E903075E935D41E0");
	    		Map<String, Object> map = JSON.parseObject(result);
	    		System.out.println(map.get("reponseCode"));
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
//	companyObjectionDao
	/**
	 * 根据企业的id查询	
	 * @param enterpriseId
	 * @return
	 */
	public List<CompanyObjection> getObjectionList(String enterpriseId){
		return objectionDao.findByEntepriseId(enterpriseId);
	}
	
	/**
	 * 向表中插入数据
	 * @param objection
	 */
	public void addObjection(CompanyObjection objection){
		objectionDao.save(objection);
	}
	/**
	 * 根据主键删除
	 * @param objId
	 */
	public void delObjection(String objId){
		objectionDao.delete(objId);
	}
	
	public List<CompanyObjectionPending> getObjectionPendingList(String enterpriseId){
		return objectionPendingDao.findByEntepriseId(enterpriseId);
	}
	
	/**
	 * 向表中插入数据
	 * @param objection
	 */
	public void addObjectionPending(CompanyObjectionPending objectionPending){
		objectionPendingDao.save(objectionPending);
	}
	/**
	 * 根据主键删除
	 * @param objId
	 */
	public void delObjectionPending(String objId){
		objectionPendingDao.delete(objId);
	}
	
	public void addObjectionPendingList(List<CompanyObjectionPending> objectionPendings){
		objectionPendingDao.save(objectionPendings);
	}
	
    public void delObjectionPendingByEnterpriseId(String enterpriseId){
    	objectionPendingDao.delByEnterpriseId(enterpriseId);
    }
    public void delObjectionByEnterpriseId(String enterpriseId){
    	objectionDao.delByEnterpriseId(enterpriseId);
    }
    
    
    public void addObjectionList(List<CompanyObjection> objections){
    	objectionDao.save(objections);
	}
}

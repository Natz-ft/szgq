package com.icfcc.ssrp.web.gataway;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.dd.InstitutionDetail;
import com.icfcc.SRRPDao.jpa.entity.dd.InstitutionType;
import com.icfcc.SRRPDao.jpa.entity.dd.PlatformDicDetail;
import com.icfcc.SRRPService.dd.PlatformDicDetailService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.session.RedisManager;
import com.icfcc.ssrp.session.investorArea;
import com.icfcc.ssrp.web.SRRPBaseController;

/**
 * 字典初始化
 * 
 * @zhanglf
 */
@Slf4j
@Controller
@RequestMapping(value = "/initInfo")
public class InitDDController extends SRRPBaseController {
	private static Logger log = LoggerFactory.getLogger(InitDDController.class);

	// 字典
	@Autowired
	private PlatformDicDetailService service;

	@Autowired
	private RedisManager redisManager;

	@RequestMapping(value = "/initDD")
	public void initDD(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<PlatformDicDetail> dataList = service.findDDInfos();
			Map<String, List> dicMap = new HashMap<String, List>();
			String mapKey = "";
			if (dataList != null && dataList.size() > 0) {
				List<DD> ddList = null;
				for (PlatformDicDetail detail : dataList) {
					if ("0".equals(detail.getValidityFlag())) {
						DD dd = new DD();
						switch (detail.getDicType()) {
						case "01":
							mapKey = SRRPConstant.DD_CERTIFICATE;
							break;
						case "02":
							mapKey = SRRPConstant.DD_INVESTMENT;
							break;
						case "03":
							mapKey = SRRPConstant.DD_FINACINGTURN;
							break;
						case "04":
							mapKey = SRRPConstant.DD_INDUSTRY;
							break;
						case "05":
							mapKey = SRRPConstant.DD_ORGTYPE;
							break;
						case "06":
							mapKey = SRRPConstant.DD_AUDITSTATE;
							break;
						case "07":
							mapKey = SRRPConstant.DD_CAPITALTYPE;
							break;
						case "08":
							mapKey = SRRPConstant.DD_FINANCPHASE;
							break;
						case "09":
							mapKey = SRRPConstant.DD_STARLEVEL;
							break;
						case "10":
							mapKey = SRRPConstant.DD_STOCKHOLDER;
							break;
						case "11":
							mapKey = SRRPConstant.DD_INVESTMENTTYPR;
							break;
						case "12":
							mapKey = SRRPConstant.DD_PUBLISHTYPE;
							break;
						case "13":
							mapKey = SRRPConstant.DD_FINACINGMONEY;
							break;
						case "14":
							mapKey = SRRPConstant.DD_AREA;
							break;
						case "15":
							mapKey = SRRPConstant.DD_RADIO;
							break;
						case "16":
							mapKey = SRRPConstant.DD_INVESTORSTATUS;
							break;
						case "17":
							mapKey = SRRPConstant.DD_MSG;
							break;
						case "18":
							mapKey = SRRPConstant.DD_NEWSTYPR;
							break;
						case "19":
							mapKey = SRRPConstant.DD_CAPITALPOWER;
							break;
						case "20":
							mapKey = SRRPConstant.DD_ENTPERIOD;
							break;
						case "21":
							break;
						case "22":
							mapKey = SRRPConstant.DD_DEMANDSTATUS;
							break;
						case "23":
							mapKey = SRRPConstant.DD_TASKSTATUS;
							break;
						case "24":
							mapKey = SRRPConstant.DD_FILETYPE;
							break;
						case "25":
							mapKey = SRRPConstant.DD_ORGFORM;
							break;
						case "26":
							mapKey = SRRPConstant.DD_SUBACTYPE;
							break;
						case "27":
							mapKey = SRRPConstant.DD_SUBACFORM;
							break;
						case "28":
							mapKey = SRRPConstant.DD_INDUSTRY_2;
							break;
						case "29":
							mapKey = SRRPConstant.DD_CURRENCY;
							break;
						case "30":
							mapKey = SRRPConstant.DD_COMPANY_AUSTSTAUS;
							break;
						case "31":
							mapKey = SRRPConstant.DD_CORP_AREA;
							break;
						case "32":
							mapKey = SRRPConstant.SERVICE_CATEGORY_1;
							break;	
						case "33":
							mapKey = SRRPConstant.SERVICE_CATEGORY_2;
							break;	
						case "34":
							mapKey = SRRPConstant.DD_REGISTER_TYPE;
							break;
						case "35":
							mapKey = SRRPConstant.DD_INVESTMENT_TYPE;
							break;
						case "36":
							mapKey = SRRPConstant.DD_BIZTERM_V1;
							break;
						case "37":
							mapKey = SRRPConstant.CompanyScale_v1;
							break;	
						case "38":
							mapKey = SRRPConstant.DD_XYDJI;
							break;
						case "39":
							mapKey = SRRPConstant.DD_OBJECTION_TYPE;
							break;
						}
						if (dicMap.containsKey(mapKey)) {
							ddList = (List<DD>) dicMap.get(mapKey);
						} else {
							ddList = new ArrayList<DD>();
						}
						dd.setDicCode(detail.getDicCode());
						dd.setDicName(detail.getDicName());
						ddList.add(dd);
						dicMap.put(mapKey, ddList);
						mapKey = "";
					}
				}
			}
			//存储深度筛选放贷机构以及授权机构
			List<InstitutionType> typeList = service.findInstitution();
			for(InstitutionType enty:typeList){
				List<DD> ddList = null;
				DD dd = new DD();
				if(enty.getType().contains("L-")){//放款机构
					mapKey = SRRPConstant.lend_institution;
					List<InstitutionDetail> lendDetail=service.findByType(enty.getType());
					List<DD>  lendList = new ArrayList<DD>();
					for(InstitutionDetail lend:lendDetail){
						DD dd1 = new DD();
						dd1.setDicCode(lend.getName());
						dd1.setDicName(lend.getValue());
						lendList.add(dd1);
					}
					dicMap.put(enty.getType(), lendList);//存储放贷机构类别下的机构集
				}else if(enty.getType().contains("A-")){//授权机构
					mapKey = SRRPConstant.auth_institution;
					List<InstitutionDetail> authDetail=service.findByType(enty.getType());
					List<DD>  authList = new ArrayList<DD>();

					for(InstitutionDetail auth:authDetail){
						DD dd2 = new DD();
						dd2.setDicCode(auth.getName());
						dd2.setDicName(auth.getValue());
						authList.add(dd2);
					}
					dicMap.put(enty.getType(), authList);//存储授权机构类别下的机构集
				}
				if (dicMap.containsKey(mapKey)) {
					ddList = (List<DD>) dicMap.get(mapKey);
				} else {
					ddList = new ArrayList<DD>();
				}
				dd.setDicCode(enty.getType());
				dd.setDicName(enty.getName());
				ddList.add(dd);
				dicMap.put(mapKey, ddList);
				mapKey = "";
			}
			List<investorArea> areaProvince=service.getAreaDate("2");
			List<investorArea> areaCity=service.getAreaDate("3");
			List<investorArea> areaCounty=service.getAreaDate("4");
			dicMap.put(SRRPConstant.AREA_Province, areaProvince);
			dicMap.put(SRRPConstant.AREA_City, areaCity);
			dicMap.put(SRRPConstant.AREA_County, areaCounty);
			Iterator<String> it = dicMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				registerDD(key, JSON.toJSONString(dicMap.get(key)));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void registerDD(String redisKey, String redisValue) {
		try {
			if (StringUtils.isBlank(redisValue)) {
				System.out.println("请求数据为空");
				return;
			}
			redisManager.setNoExpire(redisKey, redisValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: getDDList @Description: TODO(前台页面取字典集合的公共方法) @param @param
	 * request @param @param response 设定文件 @return void 返回类型 @throws
	 */
	@RequestMapping(value = "/getDDList")
	public void getDDList(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		List<DD> ddList = null;
		if (name != null && !"".equals(name)) {
			ddList = RedisGetValue.getDDList(name);
			if ("auditStatus".equals(name)) {
				ddList.remove(2);// 不展示注册审核不通过的字典值
			}
		}

		this.writeJson(ddList, request, response);
	}

	public static <V> void main(String args[]) {
	}
}

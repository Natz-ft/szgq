package com.icfcc.ssrp.web.gataway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayDemandQuery;
import com.icfcc.SRRPDao.pojo.GataWayInitInfo;
import com.icfcc.SRRPService.gataway.GataWayDemandQueryService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.DD;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.session.RedisManager;
import com.icfcc.ssrp.web.SRRPBaseController;
import com.icfcc.ssrp.web.inverstorg.DesensitizationUtil;

/**
 * 门户融资需求查詢
 * 
 * @zhanglf
 */
@Slf4j
@Controller
@RequestMapping(value = "/portal/demandQuery")
public class GataWayDemandQueryController extends SRRPBaseController {
    private static Logger log = LoggerFactory.getLogger(GataWayDemandQueryController.class);

    // 融资需求查询非静态化
    @Autowired
    private GataWayDemandQueryService gataWayDemandQueryService;

    @Autowired
    private RedisManager redisManager;

    @RequestMapping(value = "/initInfo")
    public void initInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<?, ?> logonMap = RedisGetValue.getUserInfo(request);
        try {
            String queryCondition_ = request.getParameter("queryCondition");
            QueryCondition queryCondition = null;
            if (queryCondition_ != null && !"".equals(queryCondition_) && !"\"\"".equals(queryCondition_)) {
                queryCondition = (QueryCondition) JSON.parseObject(queryCondition_, QueryCondition.class);
            }
            List<GataWayDemandQuery> dataList = gataWayDemandQueryService.findGataWayDemand(queryCondition);
            List<GataWayDemandQuery> showDataList = new ArrayList<GataWayDemandQuery>();
            // 展示到页面
            GataWayInitInfo gataWayInitInfo = new GataWayInitInfo();
            if (dataList != null && dataList.size() > 0) {
                for (GataWayDemandQuery demandQuery : dataList) {
                    // 将字典code转为value
        			if (StringUtils.isNotEmpty(demandQuery.getIndustry())) {
    					String industryStr = demandQuery.getIndustry();// 获取数据库中行业的展示
    					if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
    	                    demandQuery.setIndustryName(RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2, demandQuery.getIndustry()));
    					} else {// 如果是一级的行业显示以及行业
    	                    demandQuery.setIndustryName(RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, demandQuery.getIndustry()));
    					}
    				}
        			demandQuery.setRelName(DesensitizationUtil
							.mobilePhone(demandQuery.getRelName()));
                    demandQuery.setAreaName(RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, demandQuery.getRearea()));
                    demandQuery.setFinancingModeName(RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN, demandQuery.getFinancingMode()));
                    // 加密
                    if (logonMap.isEmpty()) {
                        // 游客模式
//                        if (!SRRPConstant.ISOPEN.equals(demandQuery.getOpen())) {
//                            // 非公开项目
//                            demandQuery.setProjectName(SRRPConstant.DEFALUTDEMAND);
//                        }
                    } else {
                        // TODO登录模式待补充

                    }
                    showDataList.add(demandQuery);
                }
                gataWayInitInfo.setCount(String.valueOf(dataList.size()));
                gataWayInitInfo.setData(dataList);
            } else {
                gataWayInitInfo.setCount("0");
                gataWayInitInfo.setData(dataList);
            }
            this.writeJson(gataWayInitInfo, request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/initDD")
    public void gainDD(HttpServletRequest request, HttpServletResponse response) {
        QueryCondition queryDd = new QueryCondition();
        String ddValues = "";
    	List<DD> ddAreaType = RedisGetValue
				.getDataList(SRRPConstant.DD_AREA);
		List<DD> ddAreanNews = new ArrayList<DD>();
		for (DD dd : ddAreaType) {
			if ("3205".equals(dd.getDicCode().substring(0, 4))) {
				ddAreanNews.add(dd);
			}
		}
		 JSONArray json = JSONArray.fromObject(ddAreanNews);    
		 ddValues=json.toString();
        queryDd.setArea(ddValues);

        ddValues = redisManager.get(SRRPConstant.DD_INDUSTRY);
        queryDd.setIndustry(ddValues);

        ddValues = redisManager.get(SRRPConstant.DD_ENTPERIOD);
        queryDd.setEnterprisePeriod(ddValues);

        ddValues = redisManager.get(SRRPConstant.DD_FINACINGMONEY);
        queryDd.setFinanceAmout(ddValues);

        ddValues = redisManager.get(SRRPConstant.DD_FINACINGTURN);
        queryDd.setFinacingTurn(ddValues);
        
        this.writeJson(queryDd, request, response);
    }

    @RequestMapping(value = "/viewDetail")
    public String viewDetail(HttpServletRequest request, HttpServletResponse response) {
        Map<?, ?> logonMap = RedisGetValue.getUserInfo(request);
        String infoId = request.getParameter("infoId");
        GataWayDemandQuery gataWayDemandQuery = gataWayDemandQueryService.findDeamndInfoById(infoId);
        if (StringUtils.isNotEmpty(gataWayDemandQuery.getIndustry())) {
			String industryStr = gataWayDemandQuery.getIndustry();// 获取数据库中行业的展示
			if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
				gataWayDemandQuery.setIndustryName(RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2, gataWayDemandQuery.getIndustry()));
			} else {// 如果是一级的行业显示以及行业
				gataWayDemandQuery.setIndustryName(RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, gataWayDemandQuery.getIndustry()));
			}
		}
        gataWayDemandQuery.setRelName(DesensitizationUtil
				.chineseName(gataWayDemandQuery.getRelName()));
        gataWayDemandQuery.setRelPhone(DesensitizationUtil
				.mobilePhone(gataWayDemandQuery.getRelPhone()));
        gataWayDemandQuery.setAreaName(RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, gataWayDemandQuery.getRearea()));
        gataWayDemandQuery.setFinancingModeName(RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN, gataWayDemandQuery.getFinancingMode()));
        // 加密
        if (logonMap.isEmpty()) {
            // 游客模式
//            if (!SRRPConstant.ISOPEN.equals(gataWayDemandQuery.getOpen())) {
//                // 非公开项目
//                gataWayDemandQuery.setProjectName(SRRPConstant.DEFALUTDEMAND);
//            }
        } else {
            // TODO登录模式待补充

        }
        request.setAttribute("gataWayDemandQuery", gataWayDemandQuery);
        return "portal/detailfinace";
    }
    @RequestMapping(value = "/financeTalk")
    public void financeTalk(HttpServletRequest request, HttpServletResponse response) {
        Map<?, ?> logonMap = RedisGetValue.getUserInfo(request);
        Map<Object, Object> codeMap = new HashMap<>();
        codeMap.put("code", "00");
        if (logonMap.isEmpty()) {
        	codeMap.put("code", "02");
        }else{
        	String type=logonMap.get("type").toString();
        	if(type.equals("01")){
        		codeMap.put("code", "01");
        	}else{
        		codeMap.put("code", "03");
        	}
        }
        this.writeJson(codeMap, request, response);
    }
   
}

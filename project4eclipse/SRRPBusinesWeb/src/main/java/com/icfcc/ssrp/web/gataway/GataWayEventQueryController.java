package com.icfcc.ssrp.web.gataway;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayEventQueryInvestor;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayFinacingEventQuery;
import com.icfcc.SRRPDao.pojo.GataWayInitInfo;
import com.icfcc.SRRPService.gataway.GataWayFinacingEventQueryService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.session.RedisManager;
import com.icfcc.ssrp.web.SRRPBaseController;

/**
 * 门户投融事件查詢
 * 
 * @zhanglf
 */
@Controller
@RequestMapping(value = "/portal/eventQuery")
public class GataWayEventQueryController  extends SRRPBaseController{
    private static Logger log = LoggerFactory.getLogger(GataWayEventQueryController.class);

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private GataWayFinacingEventQueryService service;

    @RequestMapping(value = "/finacEvent")
    public void finacEvent(HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("getBaseHome=========="+getBaseHome());
        Map<?, ?> logonMap = RedisGetValue.getUserInfo(request);
        try {
            String queryCondition_ = request.getParameter("queryCondition");
            QueryCondition queryCondition = null;
            if (queryCondition_ != null && !"".equals(queryCondition_) && !"\"\"".equals(queryCondition_)) {
                queryCondition = (QueryCondition) JSON.parseObject(queryCondition_, QueryCondition.class);
            }
            List<GataWayFinacingEventQuery> dataList = service.findFinacingEventQuery(queryCondition);
            GataWayInitInfo finacingEventQuery = new GataWayInitInfo();
            if (dataList != null && dataList.size() > 0) {
                // 字典 映射
                List<GataWayFinacingEventQuery> dataListShow = null;
                dataListShow = new ArrayList<GataWayFinacingEventQuery>();
                for (GataWayFinacingEventQuery data : dataList) {
                    if (StringUtils.isNotEmpty(data.getIndustry())) {
    					String industryStr = data.getIndustry();// 获取数据库中行业的展示
    					if (industryStr.length() == 4) {// 如果是二级的行业显示二级行业
    						data.setIndustryName(RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2, data.getIndustry()));
    					} else {// 如果是一级的行业显示以及行业
    						data.setIndustryName(RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, data.getIndustry()));
    					}
    				}
                    data.setFinacingTurnName(RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN, data.getFinacingTurn()));
                    data.setFinanceStage(RedisGetValue.getValueByCode(SRRPConstant.DD_INVESTMENT, data.getFinanceStage()));
                    // 加密
                    if (logonMap == null || logonMap.isEmpty()) {
                        // 游客模式
                        if (SRRPConstant.ISOPEN.equals(data.getOpen())) {
                            // 非公开项目
//                            data.setProjectName(SRRPConstant.DEFALUTDEMAND);
//                            data.setInvestorName(SRRPConstant.DEFALUTINVERSTOR);
//                            data.setEnterpriseName(SRRPConstant.DEFALUTCOMPANY);
                        }
                    } else {
                        // TODO登录模式待补充

                    }
                    dataListShow.add(data);
                }
                finacingEventQuery.setCount(String.valueOf(dataList.size()));
                finacingEventQuery.setData(dataListShow);
            } else {
                finacingEventQuery.setCount("0");
                finacingEventQuery.setData(dataList);
            }
            this.writeJson(finacingEventQuery, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/viewDetail")
    public String viewDetail(HttpServletRequest request, HttpServletResponse response) {
        Map<?, ?> logonMap = RedisGetValue.getUserInfo(request);
        String eventId = request.getParameter("eventId");
        GataWayFinacingEventQuery finacingEventQuery = service.findFinacingEventById(eventId);
        finacingEventQuery.setFinacingTurnName(RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN,
                finacingEventQuery.getFinacingTurn()));
        finacingEventQuery.setAreaName(RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, finacingEventQuery.getReArea()));
        // 投资机构信息
        List<GataWayEventQueryInvestor> dataList = service.findInvestorByEventId(eventId);
        for(GataWayEventQueryInvestor invest : dataList){
        	invest.setInvestorNameStr(invest.getInvestorName());
        }
        // 加密
        if (!SRRPConstant.ISOPEN.equals(finacingEventQuery.getOpen())) {
        	// 非公开项目
            finacingEventQuery.setProjectName(SRRPConstant.DEFALUTDEMAND);
            finacingEventQuery.setInvestorName(SRRPConstant.DEFALUTINVERSTOR);
            finacingEventQuery.setEnterpriseName(SRRPConstant.DEFALUTCOMPANY);
            
            for (GataWayEventQueryInvestor invest : dataList) {
				invest.setInvestorNameStr(SRRPConstant.DEFALUTINVERSTOR);
			}
        }
        
        if (logonMap.isEmpty()) {
            // 游客模式
        } else {
            // TODO登录模式待补充	

        }
        
        request.setAttribute("finacingEventQuery", finacingEventQuery);
        request.setAttribute("dataList", dataList);
        return "portal/detailinvestevent";
    }

    @RequestMapping(value = "/initDD")
    public void gainDD(HttpServletRequest request, HttpServletResponse response) {
        QueryCondition queryDd = new QueryCondition();
        String ddValus = redisManager.get(SRRPConstant.DD_FINACINGTURN);
        queryDd.setFinacing_turn(ddValus);
        
        ddValus = redisManager.get(SRRPConstant.DD_INDUSTRY);
        queryDd.setIndustry(ddValus);
        
        this.writeJson(queryDd, request, response);
    }
    public static void main(String args[]) {
    }
    private static String getBaseHome() {
        String userHome = System.getProperty("user.dir");
        String temp = userHome == null ? null : new File(userHome).getAbsolutePath().replace('\\', '/');
        return temp + "/";
    }
}

package com.icfcc.ssrp.web.gataway;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.dd.DD;
import com.icfcc.SRRPService.gataway.staticize.GataWayDemandDetailService;
import com.icfcc.SRRPService.gataway.staticize.GataWayDemandService;
import com.icfcc.SRRPService.gataway.staticize.GataWayInvestorInfoService;
import com.icfcc.SRRPService.gataway.staticize.GataWayLinksService;
import com.icfcc.SRRPService.gataway.staticize.GataWayNewsService;
import com.icfcc.SRRPService.gataway.staticize.GataWayPartnerService;
import com.icfcc.SRRPService.gataway.staticize.GataWayService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.ssrp.session.RedisManager;

/**
 * 门户查詢
 * 
 * @zhanglf
 */
@Slf4j
@Controller
@RequestMapping(value = "/zhanglf")
public class MyTestController {
    private static Logger log = LoggerFactory.getLogger(MyTestController.class);

    // 门户统计数据
    @Autowired
    private GataWayService gataWayServiceFStatic;
    // 融资需求
    @Autowired
    private GataWayDemandService gataWayDemandService;
    // 门户新闻
    @Autowired
    private GataWayNewsService gataWayNewsService;
    // 投资动态
    @Autowired
    private GataWayInvestorInfoService gataWayInvestorInfoService;
    // 门户合作伙伴
    @Autowired
    private GataWayPartnerService gataWayPartnerService;
    // 门户友情链接
    @Autowired
    private GataWayLinksService gataWayLinksService;
    // 融资需求详细
    @Autowired
    private GataWayDemandDetailService gataWayDemandDetailService;

    @Autowired
    private RedisManager redisManager;

    @RequestMapping(value = "/controllerTest")
    public String controllerTest(HttpServletRequest request, HttpServletResponse response) {
        log.info("==========init==========is===========successed===========");
        // return "gataway/view/institutionalInvestor/investOrgQuery";
        return "WEB-INF/views/inverstorg/investOrgQuery";
    }

    
    @RequestMapping(value="/initInfo")
    public void userList(Model model,PageBean page,HttpServletRequest request, HttpServletResponse response){
        String queryConditionString = request.getParameter("queryCondition");
        System.out.println("queryConditionString=====" + queryConditionString);
        //查詢條件對象需要传到Service,进行sql拼装
        QueryCondition queryCondition = null;
        if (queryConditionString != null && !"".equals(queryConditionString) && !"\"\"".equals(queryConditionString)) {
            queryCondition = (QueryCondition) JSON.parseObject(queryConditionString, QueryCondition.class);
            System.out.println(queryCondition.getFinacing_turn());
        }
        List<?> dataList  = initDate();
        page.setList(dataList);
        page.setRecordCnt(0);
        if (CollectionUtils.isNotEmpty(dataList)) {
            page.setList(dataList);
            //设置总的页数
            page.setPageCnt(2);
            //设置总的条数
            page.setRecordCnt(30);
            page.pageResult(dataList,page.getRecordCnt(), page.getMaxSize(), page.getCurPage());
        }
        //将数据传输到前端
        this.writeJson(page, request, response);
    }
    @RequestMapping(value = "/initInfoTmp")
    public void initInfo(HttpServletRequest request, HttpServletResponse response) {
        String queryCondition = request.getParameter("queryCondition");
        System.out.println("queryCondition=====" + queryCondition);
        QueryCondition condition = null;
        if (queryCondition != null && !"".equals(queryCondition) && !"\"\"".equals(queryCondition)) {
            condition = (QueryCondition) JSON.parseObject(queryCondition, QueryCondition.class);
            System.out.println(condition.getFinacing_turn());
        }
        List<?> dataList  = initDate();
        this.writeJson(dataList, request, response);
        System.out.println("=========initInfo===========" + JSON.toJSONString(dataList));
    }

    //模拟service方法
    private List<?> initDate(){
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("name", "中科创业投资有限公司");
        dataMap.put("time", "2015-04-01");
        dataMap.put("org", "深圳");
        dataMap.put("type", "1");
        dataMap.put("capital", "631");
        dataMap.put("history", "1");
        Map<String, String> dataMap1 = new HashMap<String, String>();
        // {"name": "中科创业投资有限公司", "time": '2015-04-01', 'org': "深圳", 'type': 1,
        // "capital": 631, "history": 1}
        dataMap1.put("name", "中科创业投资有限公司");
        dataMap1.put("time", "2015-04-01");
        dataMap1.put("org", "深圳");
        dataMap1.put("type", "1");
        dataMap1.put("capital", "631");
        dataMap1.put("history", "1");
        Map<String, String> dataMap2 = new HashMap<String, String>();
        // {"name": "中科创业投资有限公司", "time": '2015-04-01', 'org': "深圳", 'type': 1,
        // "capital": 631, "history": 1}
        dataMap2.put("name", "中科创业投资有限公司");
        dataMap2.put("time", "2015-04-01");
        dataMap2.put("org", "深圳");
        dataMap2.put("type", "1");
        dataMap2.put("capital", "631");
        dataMap2.put("history", "1");
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        dataList.add(dataMap);
        dataList.add(dataMap2);
        dataList.add(dataMap1);
        return dataList;
    }    
    @RequestMapping(value = "/gainDD")
    public void gainDD(HttpServletRequest request, HttpServletResponse response) {
        String ddValus = redisManager.get("dd:finacing_turn");
        this.writeJson(JSON.parse(ddValus), request, response);
        System.out.println("=========initInfo===========" + JSON.parse(ddValus));
    }

    @RequestMapping(value = "/initDD")
    public void initDD(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<DD> dataList = new ArrayList<DD>();
            // 轮次
            DD dd12 = new DD();
            dd12.setDicCode("A");
            dd12.setDicName("A");
            dataList.add(dd12);
            DD dd13 = new DD();
            dd13.setDicCode("B");
            dd13.setDicName("B");
            dataList.add(dd13);
            DD dd21 = new DD();
            dd21.setDicCode("C");
            dd21.setDicName("C");
            dataList.add(dd21);
            for (int i = 0; i < 10; i++) {
                DD dd3 = new DD();
                dd3.setDicCode("C" + i);
                dd3.setDicName("C" + i);
                dataList.add(dd3);
            }
            // 地区
            dataList = new ArrayList<DD>();
            DD dd = new DD();
            dd.setDicCode("320502");
            dd.setDicName("沧浪区");
            dataList.add(dd);
            DD dd1 = new DD();
            dd1.setDicCode("320503");
            dd1.setDicName("平江区");
            dataList.add(dd1);
            DD dd2 = new DD();
            dd2.setDicCode("320506");
            dd2.setDicName("吴中区");
            dataList.add(dd2);
            DD dd3 = new DD();
            dd3.setDicCode("320507");
            dd3.setDicName("相城区");
            dataList.add(dd3);
            registerDD("dd:area", JSON.toJSONString(dataList));
            // 行业
            dataList = new ArrayList<DD>();
            DD dd41 = new DD();
            dd41.setDicCode("A");
            dd41.setDicName("农、林、牧、渔业");
            dataList.add(dd41);
            DD dd51 = new DD();
            dd51.setDicCode("B");
            dd51.setDicName("采掘业");
            dataList.add(dd51);
            DD dd61 = new DD();
            dd61.setDicCode("C");
            dd61.setDicName("制造业");
            dataList.add(dd61);
            registerDD("dd:industry", JSON.toJSONString(dataList));
            // 资本量
            dataList = new ArrayList<DD>();
            DD dd4 = new DD();
            dd4.setDicCode("A");
            dd4.setDicName("100M以下");
            dataList.add(dd4);
            DD dd5 = new DD();
            dd5.setDicCode("B");
            dd5.setDicName("100M-200M");
            dataList.add(dd5);
            DD dd6 = new DD();
            dd6.setDicCode("C");
            dd6.setDicName("200M-500M");
            dataList.add(dd6);

            DD dd7 = new DD();
            dd7.setDicCode("D");
            dd7.setDicName("500M-1000M");
            dataList.add(dd7);
            DD dd8 = new DD();
            dd8.setDicCode("E");
            dd8.setDicName("1000M以上");
            dataList.add(dd8);
            registerDD("dd:capital", JSON.toJSONString(dataList));

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
            redisManager.set(redisKey, redisValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void writeJson(Object object, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public static <V> void main(String args[]) {
        // List<DD> dataList = new ArrayList<DD>();
        // DD dd1 = new DD();
        // dd1.setDicCode("B");
        // dd1.setDicName("B");
        // dataList.add(dd1);
        // DD dd = new DD();
        // dd.setDicCode("A");
        // dd.setDicName("A");
        // dataList.add(dd);
        // DD dd2 = new DD();
        // dd2.setDicCode("C");
        // dd2.setDicName("C");
        // dataList.add(dd2);
        // System.out.println(JSON.toJSONString(dataList));
        // DD dd_ = new DD();
        // System.out.println(JSON.toJSONString(dd_));

        QueryCondition a = new QueryCondition();
        a.setFinacing_turn("B");
        System.out.println(JSON.toJSONString(a));
        QueryCondition condition = (QueryCondition) JSON.parseObject("{\"finacing_turn\":\"B\"}", QueryCondition.class);
        System.out.println(condition.getFinacing_turn());
    }
}

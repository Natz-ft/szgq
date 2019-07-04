package com.icfcc.ssrp.web;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能描述: [描述该类概要功能介绍]
 * </p>
 * 
 * @author zhanglf
 * @version
 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class GataMaker {
    private static String modelFolder = getBaseHome() + "model//";
    private static String modelFile = "index(template).html";
    private static String outFile = "D://demo//index//index.html";

    public static void main(String... strings) {
        System.out.println("=============" + getBaseHome());
        Map<String, Object> data = new HashMap<String, Object>();
        GataWay way = new GataWay();
        way.setName("张三");
        way.setCertno("19860301");
        data.put("way", way);
        // 汇总数据
        Map<String, Object> total = new HashMap<String, Object>();
        total.put("finance", 11111);
        total.put("enterprise", 22222);
        total.put("organize", 33333);
        total.put("demand", 333);
        data.put("total", total);

        // 项目需求列表
        Map<String, Object> proj1 = new HashMap<String, Object>();
        proj1.put("projName", "摩拜单车");
        proj1.put("projAmount", 1000);
        proj1.put("projPer", 10);
        proj1.put("projLunCi", "天使轮");
        proj1.put("projDetail", "详情1");
        Map<String, Object> proj2 = new HashMap<String, Object>();
        proj2.put("projName", "京东商城");
        proj2.put("projAmount", 2000);
        proj2.put("projPer", 20);
        proj2.put("projLunCi", "天使轮");
        proj2.put("projDetail", "详情2");
        Map<String, Object> proj3 = new HashMap<String, Object>();
        proj3.put("projName", "京东商城");
        proj3.put("projAmount", 3000);
        proj3.put("projPer", 30);
        proj3.put("projLunCi", "天使轮");
        proj3.put("projDetail", "详情3");
        Map<String, Object> proj4 = new HashMap<String, Object>();
        proj4.put("projName", "京东商城");
        proj4.put("projAmount", 3000);
        proj4.put("projPer", 30);
        proj4.put("projLunCi", "天使轮");
        proj4.put("projDetail", "详情3");
        Map<String, Object> proj5 = new HashMap<String, Object>();
        proj5.put("projName", "京东商城");
        proj5.put("projAmount", 3000);
        proj5.put("projPer", 30);
        proj5.put("projLunCi", "天使轮");
        proj5.put("projDetail", "详情3");
        List<Map<String, Object>> projList = new ArrayList<Map<String, Object>>();
        projList.add(proj1);
        projList.add(proj2);
        projList.add(proj3);
        // projList.add(proj4);
        // projList.add(proj5);
        data.put("projList", projList);
        // 新闻动态
        List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
        Map<String, Object> news = new HashMap<String, Object>();
        news.put("url", "index.html");
        news.put("title", "IDG资本于1992年开始在中国进行风险投资1");
        news.put("contents", "作为最早进入中国市场的外资投资基金，IDG资本已成为中国风险投资行...");
        news.put("date", "2017-01-01");
        Map<String, Object> news1 = new HashMap<String, Object>();
        news1.put("url", "index.html");
        news1.put("title", "IDG资本于1992年开始在中国进行风险投资2");
        news1.put("contents", "作为最早进入中国市场的外资投资基金，IDG资本已成为中国风险投资行...");
        news1.put("date", "2017-01-01");
        Map<String, Object> news2 = new HashMap<String, Object>();
        news2.put("url", "index.html");
        news2.put("title", "IDG资本于1992年开始在中国进行风险投资3");
        news2.put("contents", "作为最早进入中国市场的外资投资基金，IDG资本已成为中国风险投资行...");
        news2.put("date", "2017-01-01");
        newsList.add(news);
        newsList.add(news1);
        newsList.add(news2);
        data.put("newsList", newsList);
        // 投资机构动态
        List<Map<String, Object>> investList = new ArrayList<Map<String, Object>>();
        Map<String, Object> investors = new HashMap<String, Object>();
        investors.put("url", "index.html");
        investors.put("title", "IDG资本于1992年开始在中国进行风险投资4");
        investors.put("contents", "作为最早进入中国市场的外资投资基金，IDG资本已成为中国风险投资行...");
        investors.put("date", "2017-01-01");
        Map<String, Object> investors1 = new HashMap<String, Object>();
        investors1.put("url", "index.html");
        investors1.put("title", "IDG资本于1992年开始在中国进行风险投资5");
        investors1.put("contents", "作为最早进入中国市场的外资投资基金，IDG资本已成为中国风险投资行...");
        investors1.put("date", "2017-01-01");
        Map<String, Object> investors2 = new HashMap<String, Object>();
        investors2.put("url", "index.html");
        investors2.put("title", "IDG资本于1992年开始在中国进行风险投资6");
        investors2.put("contents", "作为最早进入中国市场的外资投资基金，IDG资本已成为中国风险投资行...");
        investors2.put("date", "2017-01-01");
        Map<String, Object> investors3 = new HashMap<String, Object>();
        investors3.put("url", "index.html");
        investors3.put("title", "IDG资本于1992年开始在中国进行风险投资6");
        investors3.put("contents", "作为最早进入中国市场的外资投资基金，IDG资本已成为中国风险投资行...");
        investors3.put("date", "2017-01-01");
        investList.add(investors3);
        Map<String, Object> investors4 = new HashMap<String, Object>();
        investors4.put("url", "index.html");
        investors4.put("title", "IDG资本于1992年开始在中国进行风险投资6");
        investors4.put("contents", "作为最早进入中国市场的外资投资基金，IDG资本已成为中国风险投资行...");
        investors4.put("date", "2017-01-01");
        investList.add(investors4);
        Map<String, Object> investors5 = new HashMap<String, Object>();
        investors5.put("url", "index.html");
        investors5.put("title", "IDG资本于1992年开始在中国进行风险投资6");
        investors5.put("contents", "作为最早进入中国市场的外资投资基金，IDG资本已成为中国风险投资行...");
        investors5.put("date", "2017-01-01");
        investList.add(investors5);
        investList.add(investors);
        investList.add(investors1);
        investList.add(investors2);
        data.put("investList", investList);
        // 合作伙伴
        List<Map<String, Object>> partnersList = new ArrayList<Map<String, Object>>();
        Map<String, Object> partners = new HashMap<String, Object>();
        partners.put("url", "index.html");
        partners.put("partners", "中国金融电子化公司");
        Map<String, Object> partners1 = new HashMap<String, Object>();
        partners1.put("url", "index.html");
        partners1.put("partners", "中国金融电子化公司234");
        Map<String, Object> partners2 = new HashMap<String, Object>();
        partners2.put("url", "index.html");
        partners2.put("partners", "中国金融电子化公司212323123");
        partnersList.add(partners);
        partnersList.add(partners1);
        partnersList.add(partners2);
        data.put("partnersList", partnersList);
        // 友情链接
        List<Map<String, Object>> friendlyLinkList = new ArrayList<Map<String, Object>>();
        Map<String, Object> friendlyLink = new HashMap<String, Object>();
        friendlyLink.put("url", "index.html");
        friendlyLink.put("friendlyLink", "中国金融电子化公司22222222222");
        Map<String, Object> friendlyLink1 = new HashMap<String, Object>();
        friendlyLink1.put("url", "index.html");
        friendlyLink1.put("friendlyLink", "中国金融电子化公司33333333333");
        Map<String, Object> friendlyLink2 = new HashMap<String, Object>();
        friendlyLink2.put("url", "index.html");
        friendlyLink2.put("friendlyLink", "中国金融电子化公司444444444444");
        friendlyLinkList.add(partners);
        friendlyLinkList.add(partners1);
        friendlyLinkList.add(partners2);
        data.put("friendlyLinkList", friendlyLinkList);

        try {
            Configuration cfg = new Configuration();
            // 设置模版路径（文件夹）
            System.out.println(modelFolder + modelFile);
            cfg.setDirectoryForTemplateLoading(new File(modelFolder));
            // 获取模版（文件名）
            Template template = cfg.getTemplate(modelFile);
            // 输出html的全路径
            File afile = new File(outFile);
            // 创建输出流
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile)));
            // 渲染模版
            template.process(data, out);
            System.out.println("=========maked=====is========end!=============");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getBaseHome() {
        String userHome = System.getProperty("user.dir");
        String temp = userHome == null ? null : new File(userHome).getAbsolutePath().replace('\\', '/');
        return temp + "/";
    }

}

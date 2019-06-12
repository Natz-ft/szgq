package com.icfcc.SRRPDao.pojo;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

/**
 * 门户查詢(投资机构查询，非静态化)
 * 
 * @zhanglf
 */
@Slf4j
@Controller
@RequestMapping(value = "/portal/investorQuery")
public class Example  {
    public static <V> void main(String args[]) {
        InvestorStatic result = new InvestorStatic();
        List<StaticIndustryData> industryData = new ArrayList<StaticIndustryData>();
        StaticIndustryData data1 = new StaticIndustryData();
        data1.setIndustryName("互联网");
        data1.setAmount("CNY60");
        data1.setScale("10%");
        industryData.add(data1);
        data1 = new StaticIndustryData();
        data1.setIndustryName("IT");
        data1.setAmount("CNY80");
        data1.setScale("90%");
        industryData.add(data1);
        // 数据填充
        result.setIndustryData(industryData);
        // 饼状图数据
        List<StaticPieData> pieDataList = new ArrayList<StaticPieData>();
        StaticPieData pieData = new StaticPieData();
       /* pieData.setLatitude("0生物技术1/医疗健康：20%");
        pieData.setScale("20");
        pieDataList.add(pieData);
        pieData = new StaticPieData();
        pieData.setLatitude("1生物技术2/医疗健康：80%");
        pieData.setScale("80");
        pieDataList.add(pieData);*/
        // 数据填充
//        result.setPieDate(pieDataList);
//        // 柱状图数据
//        List<StaticHistogramData> gramDataList = new ArrayList<StaticHistogramData>();
//        StaticHistogramData gramData = new StaticHistogramData();
//        gramData.setLatitude("互联网,生物技术/医疗健康,IT,信息技术,保险产业,卫生服务,政治资讯");
//        gramData.setScale("65,52,33,22,20,20,15");
//        gramDataList.add(gramData);
        // 数据填充
//        result.setHistogramData(gramDataList);
        System.out.println(JSON.toJSONString(result));

    }
}

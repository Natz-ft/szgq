package com.icfcc.ssrp.web.gataway;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.dd.DD;
import com.icfcc.SRRPService.gataway.staticize.GataWayDemandDetailService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.ssrp.session.RedisManager;

/**
 * 分页测试
 * 
 * @zhanglf
 */
@Slf4j
@Controller
@RequestMapping(value = "/Page")
public class TestPageController {
    private static Logger log = LoggerFactory.getLogger(TestPageController.class);

    // 融资需求详细
    @Autowired
    private GataWayDemandDetailService gataWayDemandDetailService;

    @Autowired
    private RedisManager redisManager;

    @RequestMapping(value = "/testPage")
    public String testPage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("=========in=======controllerTest===========");
        // makeFinaDemandDetail();
        // makeIndexHtml();
        System.out.println("=========in=======controllerTest====end=======");
        log.info("==========init==========is===========successed===========");
        // return "gataway/view/institutionalInvestor/investOrgQuery";
        return "inverstorg/investOrgQuery";
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
        List<DD> dataList = new ArrayList<DD>();
        DD dd1 = new DD();
        dd1.setDicCode("B");
        dd1.setDicName("B");
        dataList.add(dd1);
        DD dd = new DD();
        dd.setDicCode("A");
        dd.setDicName("A");
        dataList.add(dd);
        DD dd2 = new DD();
        dd2.setDicCode("C");
        dd2.setDicName("C");
        dataList.add(dd2);

        PageBean bean = new PageBean();
        bean.setList(dataList);
        bean.setCurPage(1);
        bean.setFirstPage(2);
        System.out.println(JSON.toJSONString(bean));

    }
}

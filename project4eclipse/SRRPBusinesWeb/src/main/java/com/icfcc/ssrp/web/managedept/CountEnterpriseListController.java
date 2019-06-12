package com.icfcc.ssrp.web.managedept;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanEnterprise;
import com.icfcc.SRRPDao.pojo.StaticResult;
import com.icfcc.SRRPService.enterprise.ReportService;

/**
 * 取最近一个月企业排名
 * @author loudw
 *
 */
@Controller
@RequestMapping(value = "/countEnterpriseList")
public class CountEnterpriseListController {
    private static Logger log = LoggerFactory.getLogger(CountEnterpriseListController.class);

  
    @Autowired
    private ReportService service;
    @RequestMapping(value = "/countEnterpriseInit")
	public String countEnterpriseInit(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/views/managedept/enterpirseList";
	}
    @RequestMapping(value = "getList")
    public String getountEnterpriseList(HttpServletRequest request, HttpServletResponse response) {
        try {
        	  Date date = new Date();//获取当前时间    
              Calendar calendar = Calendar.getInstance();    
              calendar.setTime(date);    
              calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
              calendar.getTime();//获取一年前的时间，或者一个月前的时间
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
              List<ReportBeanEnterprise> reportBeanEnterprise =service.generateReportBeanEnterprise(sdf.format(calendar.getTime()));
            request.setAttribute("countEnterpriseList", net.sf.json.JSONArray.fromObject(reportBeanEnterprise));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("======initMothlyRank=======the==========end==============");
		return "WEB-INF/views/managedept/enterpirseList";
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
}

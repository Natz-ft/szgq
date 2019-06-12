package com.icfcc.ssrp.web.managedept;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import com.icfcc.SRRPDao.pojo.CountReportAreaBean;
import com.icfcc.SRRPService.enterprise.ReportService;
import com.icfcc.credit.platform.util.SRRPConstant;

/**
 * 平台区域排名
 * 
 * @author loudw
 *
 */
@Controller
@RequestMapping(value = "/coutAreaRank",produces="text/html;charset=UTF-8")
public class CountAreaRankController {
    private static Logger log = LoggerFactory.getLogger(CountAreaRankController.class);

  
    @Autowired
    private ReportService service;

   
    @RequestMapping(value = "/initCountAreaRank")
    public String initCountAreaRank(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	//request.setCharacterEncoding("UTF-8");
		log.info(request.getCharacterEncoding());
    	return "WEB-INF/views/managedept/countAreaRank";
    }
    @RequestMapping(value = "/getCountAreaInfos")
    public void getCountAreaInfos(HttpServletRequest request, HttpServletResponse response) {
		log.info("==========getCountAreaInfos=======");
    	 try {
       	  Date date = new Date();//获取当前时间    
             Calendar calendar = Calendar.getInstance();    
             calendar.setTime(date);    
             calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
             calendar.getTime();//获取一年前的时间，或者一个月前的时间
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
         	List<CountReportAreaBean> reportBeanArea =service.generateReportBeanArea(sdf.format(calendar.getTime()));
            this.writeJson(reportBeanArea, request, response);
       } catch (Exception e) {
           e.printStackTrace();
           log.error(e.getMessage());
       }
    	
    }
   
    protected void writeJson(Object object, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
         	log.info(json);
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

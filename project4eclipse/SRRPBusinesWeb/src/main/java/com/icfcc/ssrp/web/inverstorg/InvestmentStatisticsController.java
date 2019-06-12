package com.icfcc.ssrp.web.inverstorg;



import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.enterprise.ReportBeanTrade;
import com.icfcc.SRRPDao.pojo.InvestorStatic;
import com.icfcc.SRRPDao.pojo.StaticHistogramData;
import com.icfcc.SRRPDao.pojo.StaticIndustryData;
import com.icfcc.SRRPDao.pojo.StaticPieData;
import com.icfcc.SRRPService.enterprise.RealTimeReportService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;
import com.icfcc.ssrp.web.SRRPBaseController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/investmentStatistics")
public class InvestmentStatisticsController extends SRRPBaseController {
	private static Logger _log = LoggerFactory.getLogger(InvestmentStatisticsController.class);
	// 获得投资机构的行业时时报表
	@Autowired
	private RealTimeReportService realTimeReportService;

	@RequestMapping(value = "/statistics")
	public String controllerTest(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/views/inverstorg/investmentStatisticsQuery";
	}
	@RequestMapping(value = "/histogramdata")
	public void resulthstoryInfo(Model model, PageBean page, HttpServletRequest request, HttpServletResponse response) {
		try {
			 DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
	            df.applyPattern("#0.##########");
			// 获取登录投资机构用户的id
			String investorId = RedisGetValue.getRedisUser(request, "orgNo");
			String level = RedisGetValue.getRedisUser(request, "level");
			String username = RedisGetValue.getRedisUser(request, "username");
			String timeStart = request.getParameter("startTime");
			String timeEnd = request.getParameter("endTime");
			List<ReportBeanTrade> dataList = null;
			if(level.equals("1")){//基金
				dataList = realTimeReportService.getTradeInvestByOrgStr(investorId, timeStart, timeEnd,5,username);
			}else{
				dataList = realTimeReportService.getTradeInvestByOrgStr(investorId, timeStart, timeEnd,5,"");
			}
			InvestorStatic result = new InvestorStatic();
			List<StaticIndustryData> industryData = new ArrayList<StaticIndustryData>();
			List<StaticPieData> pieDate = new ArrayList<StaticPieData>();
			List<StaticHistogramData> histogramData = new ArrayList<StaticHistogramData>();
			// 柱状图数据
			StaticHistogramData gramData = new StaticHistogramData();
			String xKeys = "";
			String yValues = "";
			DecimalFormat format = new DecimalFormat("0.00");
			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					ReportBeanTrade reportBeanTrade = dataList.get(i);
					// 列表数据
					StaticIndustryData staticIndustryData = new StaticIndustryData();
					staticIndustryData.setIndustryName(reportBeanTrade.getTradeDicname());
					staticIndustryData.setAmount(df.format(reportBeanTrade.getAmount()).toString()+" " +"CNY");
					staticIndustryData.setScale(df.format(reportBeanTrade.getRatio().doubleValue() * 100) + "");
					industryData.add(staticIndustryData);
					// 饼状图数据
					StaticPieData staticPieData = new StaticPieData();
					staticPieData.setName(
							reportBeanTrade.getTradeDicname() + ":" + df.format(reportBeanTrade.getRatio().doubleValue() * 100)+"%");
					staticPieData.setValue(reportBeanTrade.getAmount().toString());
					pieDate.add(staticPieData);
					if(i==0){
						xKeys = dataList.get(i).getTradeDicname();
						yValues = df.format(dataList.get(i).getAmount());
					}else{
					    xKeys = dataList.get(i).getTradeDicname() + "," + xKeys;
					    yValues = df.format(dataList.get(i).getAmount()) + "," + yValues;
					}
				}
				gramData.setxKey(xKeys);
				gramData.setyValue(yValues);
				histogramData.add(gramData);
				result.setHistogramData(histogramData);
				result.setBreaddata(pieDate);
				result.setIndustryData(industryData);
			}
			// 将数据传输到前端
			this.writeJson(JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect), request, response);
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
	}
}

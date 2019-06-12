package com.icfcc.ssrp.web.managedept;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.managedept.FinacingStatisticsNomberResult;
import com.icfcc.SRRPDao.jpa.entity.managedept.FinacingStatisticsResult;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPDao.pojo.CountReportAreaBean;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.managedept.CountReportService;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.web.SRRPBaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: CountFinanceController
 * @Description: TODO(主管机构-统计报表-融资统计)
 * @author loudw
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/countFinance")
public class CountFinanceController extends SRRPBaseController{
	private static Logger log = LoggerFactory.getLogger(CountFinanceController.class);
	// 注入在线提问解答业务层
	@Autowired
	private CountReportService service;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private PlatformUserService userService;
	@RequestMapping(value = "/countFinanceInit")
	public String countFinanceInit(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/views/managedept/countfinacing";
	}

	/**
	 * 获取苏州地图的json串
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getSuzhouJson")
	public void getSuzhouJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String filename = servletContext.getRealPath("/") + "static" + File.separator + "script" + File.separator
				+ "echarts" + File.separator + "suzhou.json";
		log.info(filename);
		String suzhoujson = this.readFileByLines(filename);
		suzhoujson = suzhoujson.substring(1, suzhoujson.length());
		this.writeJson(suzhoujson, request, response);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getResulthistory")
	public void getResulthistory(HttpServletRequest request, HttpServletResponse response) {
		String query_date = request.getParameter("query_date");
		if (!"".equals(query_date)) {
			CountReportAreaBean bean = service.generateReportBeanTotal(query_date);
			List<CountReportAreaBean> list = new ArrayList<CountReportAreaBean>();
			list.add(bean);
			Map<String, List> countmap = new HashMap<String, List>();
			countmap.put("count", list);
			this.writeJson(countmap, request, response);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "getmonthtotle")
	public void sumAmountByArea(HttpServletRequest request, HttpServletResponse response) {
		String query_date = request.getParameter("query_date");
		List<Map> list = null;
		if (!"".equals(query_date)) {
			list = service.sumAmountByArea(query_date);
		}
		Map<String, List> countmap = new HashMap<String, List>();
		countmap.put("data", list);
		this.writeJson(countmap, request, response);
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */

	private static String readFileByLines(String filename) {
		String str = "";
		File file = new File(filename);
		try {
			FileInputStream in = new FileInputStream(file);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			str = new String(buffer, "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return str;
	}

	/**
	 * 初始化融资统计
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/finacingStatisticsInitInfo")
	public String controllerTest(HttpServletRequest request, HttpServletResponse response) {
		return "WEB-INF/views/managedept/finacingStatistics";
	}

	/**
	 * 融资统计
	 * @param model
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping("/finacingStatisticsList")
	public void getcompaniesList(Model model, PageBean page, HttpServletRequest request, HttpServletResponse response) {
		try {
			String queryConditionString = request.getParameter("queryCondition");
			QueryCondition queryCondition = new QueryCondition();
			if (queryConditionString != null && !"".equals(queryConditionString)
					&& !"\"\"".equals(queryConditionString)) {
				queryCondition = (QueryCondition) JSON.parseObject(queryConditionString, QueryCondition.class);
			}
			String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
			if (SRRPConstant.USER_TYPE_03.equals(userType)) {// 区县金融办用户
																// 查询本区域的企业用户
				ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject()
						.getPrincipal();
				PlatformUser user = userService.getUser(shiroUser.getId());
				queryCondition.setArea(user.getDesc3());
			}

			//获取相应日期的发布需求个数  融资金额  解决融资金额个数
			queryCondition.setSqlFlag("finacingAmountSql");
			Map<String, String> finacingAmountMap = service.getMap(queryCondition);
//			queryCondition.setSqlFlag("demandNumberSql");
//			Map<String, String> demandNumberMap = service.getMap(queryCondition);
			queryCondition.setSqlFlag("solutionsNumberSql");
			Map<String, String> solutionsNumbeMap = service.getMap(queryCondition);
			
			//获取平台相应日期的平台企业数  机构数  用户数存入FinacingStatisticsNomberResult对象
			FinacingStatisticsNomberResult resultNomber = new FinacingStatisticsNomberResult();
			String uuid = UUID.randomUUID().toString().replace("-", "");
			resultNomber.setId(uuid);
			queryCondition.setSqlFlag("companyNumberSql");
			resultNomber.setCompanyNumber(service.getCount(queryCondition).get("statisticsCount"));
			queryCondition.setSqlFlag("investorNumberSql");
			resultNomber.setInvestorNumber(service.getCount(queryCondition).get("statisticsCount"));
			queryCondition.setSqlFlag("userNumberSql");
			resultNomber.setUserNumber(service.getCount(queryCondition).get("statisticsCount"));
			
			//获取融资统计列表
			List<FinacingStatisticsResult> dataList = service.getFinacingStatistics(queryCondition);
			FinacingStatisticsResult fsr = new FinacingStatisticsResult();
			//定义发布融资金额  融资金额  发布需求个数   解决融资金额个数的合计值
			Double finacingAmount = 0.00;
			Double demandAmount = 0.00;
			Integer demandNumber = 0;
			Integer solutionsNumber = 0;
			//遍历dataList把获取相应日期的发布需求个数  融资金额  解决融资金额个数赋值给融资需求列表并未合计赋值
			for(int i=0;i<dataList.size();i++){
			    fsr = dataList.get(i);
			    if(finacingAmountMap.containsKey(fsr.getFinacingTime())){
					fsr.setFinacingAmount(Double.parseDouble(finacingAmountMap.get(fsr.getFinacingTime())));
					fsr.setSolutionsNumber(Integer.parseInt(solutionsNumbeMap.get(fsr.getFinacingTime())));
			    }else{
					fsr.setFinacingAmount(Double.parseDouble("0.00"));
					fsr.setSolutionsNumber(Integer.parseInt("0"));
			    }
				finacingAmount = finacingAmount + fsr.getFinacingAmount();
			    solutionsNumber = solutionsNumber + fsr.getSolutionsNumber();

			    demandNumber = demandNumber + fsr.getDemandNumber();
			    demandAmount = demandAmount + fsr.getDemandAmount();
			}
			//新建融资统计对象 将合计值赋入并添加dataList中
			DecimalFormat df = new DecimalFormat("#.00");
			FinacingStatisticsResult finacingStatisticsResult = new FinacingStatisticsResult();
			finacingStatisticsResult.setFinacingTime("合计");
			finacingStatisticsResult.setDemandAmount(Double.parseDouble(df.format(demandAmount)));
			
			finacingStatisticsResult.setDemandNumber(demandNumber);
			finacingStatisticsResult.setSolutionsNumber(solutionsNumber);
			finacingStatisticsResult.setFinacingAmount(Double.parseDouble(df.format(finacingAmount)));
			//将dataList赋值给page
			page.setList(dataList);
			//将FinacingStatisticsNomberResult对象存入map 并赋值给page返回前端页面
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("resultNomber", resultNomber);
			map.put("finacingStatisticsResult", finacingStatisticsResult);
			page.setParamsMap(map);
			// 将数据传输到前端
			this.writeJson(page, request, response);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

}

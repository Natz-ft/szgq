package com.icfcc.ssrp.web.gataway;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.icfcc.SRRPDao.jpa.entity.QueryCondition;
import com.icfcc.SRRPDao.jpa.entity.dd.DD;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayInvestorQuery;
import com.icfcc.SRRPDao.pojo.GataWayInitInfo;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.SRRPService.gataway.GataWayInvestorQueryService;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.session.RedisManager;
import com.icfcc.ssrp.web.SRRPBaseController;

/**
 * 门户查詢(投资机构查询，非静态化)
 * 
 * @zhanglf
 */
@Slf4j
@Controller
@RequestMapping(value = "/portal/investorQuery")
public class GataWayInvestorQueryController extends SRRPBaseController {
	private static Logger log = LoggerFactory
			.getLogger(GataWayInvestorQueryController.class);

	private String logo = "../static/images/defaultInvestor.png";
	// 投资机构查询非静态化
	@Autowired
	private GataWayInvestorQueryService gataWayInvestorQueryService;
	@Autowired
	private InvestorService investorService;
	@Autowired
	private RedisManager redisManager;

	@RequestMapping(value = "/initInfo")
	public void initInfo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String queryCondition_ = request.getParameter("queryCondition");
			QueryCondition queryCondition = null;
			if (queryCondition_ != null && !"".equals(queryCondition_)
					&& !"\"\"".equals(queryCondition_)) {
				queryCondition = (QueryCondition) JSON.parseObject(
						queryCondition_, QueryCondition.class);
			}
			List<GataWayInvestorQuery> dataList = gataWayInvestorQueryService
					.findInvestorList(queryCondition);

			// 展示到页面
			GataWayInitInfo gataWayInitInfo = new GataWayInitInfo();
			if (dataList != null && dataList.size() > 0) {
				List<GataWayInvestorQuery> showDataList = new ArrayList<GataWayInvestorQuery>();
				GataWayInvestorQuery showObject = null;
				for (GataWayInvestorQuery dataObject : dataList) {
					showObject = dataObject;
					// 将字典code转为value
					// showObject.setCapitalType(RedisGetValue.getValueByCode(SRRPConstant.DD_CAPITAL,
					// dataObject.getCapital()));
					if (dataObject.getLogo() == null
							|| "".equals(dataObject.getLogo())) {
						showObject.setLogo(logo);
					}
					if(dataObject.getDescription().length()>200){
					    String description=dataObject.getDescription();
					    description=description.substring(0, 200)+"...";
					    dataObject.setDescription(description);
					}
					showDataList.add(showObject);
				}
				gataWayInitInfo.setCount(String.valueOf(dataList.size()));
				gataWayInitInfo.setData(showDataList);
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

	@RequestMapping(value = "/viewDetail")
	public String viewDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String investorId = request.getParameter("investorId");
		//根据机构的id查询机构详情
		Investor detailInfo =investorService.findInverstorById(investorId);
		
//		GataWayInvestorQuery detailInfo = gataWayInvestorQueryService
//				.findDemandById(investorId);
		if (detailInfo.getLogoPath()== null || "".equals(detailInfo.getLogoPath())) {
			detailInfo.setLogoPath("../"+logo);
		}
		String financeStageValues = "";
		String financeStage = detailInfo.getFinanceStage();
		if (financeStage != null && !"".equals(financeStage)
				&& !"--".equals(financeStage)) {
			if (financeStage.indexOf(",") > -1) {
				String tmp[] = financeStage.split(",");
				for (String key : tmp) {
					financeStageValues += RedisGetValue.getValueByCode(
							SRRPConstant.DD_INVESTMENT, key) + "&nbsp;&nbsp;";
				}

			}
		}
		detailInfo.setFinanceStageDicname(financeStageValues);
		String financeTradeValues = "";
		String financeTrade = detailInfo.getFinanceTrade();
		if (financeTrade != null && !"".equals(financeTrade)
				&& !"--".equals(financeTrade)) {
			if (financeTrade.indexOf(",") > -1) {
				String tmp[] = financeTrade.split(",");
				for (String key : tmp) {
					financeTradeValues += RedisGetValue.getValueByCode(
							SRRPConstant.DD_INDUSTRY, key) + "&nbsp;&nbsp;";
				}
			}
		}
		detailInfo.setFinanceTradeDicname(financeTradeValues);
		request.setAttribute("detailInfo", detailInfo);
		return "portal/detailinvest";
	}

	@RequestMapping(value = "/initDD")
	public void initDD(HttpServletRequest request, HttpServletResponse response) {
		QueryCondition queryDd = new QueryCondition();
		String ddValus = redisManager.get(SRRPConstant.DD_ORGTYPE);
		queryDd.setOrgType(ddValus);

		ddValus = redisManager.get(SRRPConstant.DD_CAPITALPOWER);
		queryDd.setCapitalPower(ddValus);

		List<DD> ddList = new ArrayList<DD>();
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);
		for (int i = 0; i < 12; i++) {
			DD dd = new DD();
			dd.setDicCode(String.valueOf(nowYear - i));
			dd.setDicName(String.valueOf(nowYear - i));
			ddList.add(dd);
		}
		DD dd = new DD();
		dd.setDicCode("yearbefor");
		dd.setDicName("2007年以前");
		ddList.add(dd);
		queryDd.setSetTime(JSON.toJSONString(ddList));
		this.writeJson(queryDd, request, response);
	}

	public static  void main(String args[]) {
	    String ss="公司作为国内最早的专业投资银行，秉承“与客户共成长”的理念，成为联系投资需求和资金需求的桥梁，为政府项目、大型基础建设项目及特大型企业提供各种融资方案和资金渠道，为上市公司和非上市公司提供财务方面、金融方面以及管理与战略方面等优质的金融服务，公司还直接参与了部分项目的投资并取得良好效益。公司成立至今，得到了客户和业界的普遍认同，积累了一批优质客户，形成了长期合作的战略关系，诚信铸就了良好的“苏投”品牌。";
	 System.out.println(ss.length());
	}
}

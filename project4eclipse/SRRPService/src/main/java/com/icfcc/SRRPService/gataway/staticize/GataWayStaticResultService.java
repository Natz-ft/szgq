package com.icfcc.SRRPService.gataway.staticize;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankArea;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankCompany;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankFinacingTurn;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankIndustry;
import com.icfcc.SRRPDao.jpa.entity.gataway.GataWayRankInvestor;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayStatic;
import com.icfcc.SRRPDao.jpa.entity.gataway.staticize.GataWayStaticResult;
import com.icfcc.SRRPDao.jpa.repository.gataway.GataWayRankEcologyDao;
import com.icfcc.SRRPDao.jpa.repository.gataway.staticize.GataWayStaticResultDao;
import com.icfcc.SRRPDao.pojo.StaticResult;
import com.icfcc.SRRPDao.pojo.StaticResultDeamd;
import com.icfcc.SRRPDao.pojo.demand.AreaData;
import com.icfcc.SRRPDao.pojo.demand.FinacTurnAndIndustryData;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

/**
 * <门户静态化运行成果>
 */
@Component
@Transactional(value = "transactionManager")
public class GataWayStaticResultService {
	private static Logger log = LoggerFactory
			.getLogger(GataWayStaticResultService.class);
	private static Map<String, String> reflectMap = new HashMap<String, String>();

	@Value("${mainOutPath}")
	private String mainOutPath;

	// 平台融资总额文件路径
	@Value("${result.staticresult.totalPlatformFinancing}")
	private String totalPlatformFinancingPath;

	// 平台用户文件路径
	@Value("${result.staticresult.platformUserCounts}")
	private String platformUserCountsPath;

	// 月度融资统计文件路径
	@Value("${result.staticresult.monthlyFinancingStatistics}")
	private String monthlyFinancingStatisticsPath;

	// 榜单排名
	@Value("${result.staticresult.monthlyRank}")
	private String monthlyRankPath;
	// 项目需求
	@Value("${result.staticresult.pushDemandPath}")
	private String pushDemandPath;
	// 投融事件
	@Value("${result.staticresult.succFinacedDemandPath}")
	private String succFinacedDemandPath;

	@Autowired
	private GataWayStaticResultDao gataWayStaticResultDao;

	@Autowired
	private GataWayRankEcologyDao rankDao;

	// 运行结果-平台融资总额、平台用户、月度融资计划
	public void writeJsonFile() throws Exception {

		List<GataWayStaticResult> dataList = findStaticResultList();
		// 发布融资总额
		String post = "0";

		// 解决融资总额
		String solve = "0";

		// 正在融资总额
		String todo = "0";

		// 企业数
		String company = "0";

		// 机构数
		String org = "0";
		// 外地机构数
		String otherOrg = "0";
		// 月度融资统计
		String count = "0";
        //截至统计日期
		String endTotalDate="";
		// 用户数据
		int totalUser = 0;

		if (dataList != null && dataList.size() > 0) {

			List<GataWayStaticResult> totalPlatformFinancingList = new ArrayList<GataWayStaticResult>();
			List<GataWayStaticResult> platformUserCountsList = new ArrayList<GataWayStaticResult>();
			List<GataWayStaticResult> monthlyRankList = new ArrayList<GataWayStaticResult>();
			int totalSum = 0;
			int orgTotalSum = 0;// 统计机构数量 苏州市内
			int orgOtherTotalSum= 0;// 统计机构数量
			int companyTotalSum = 0;// 统计企业数量
			StaticResult staticResult = new StaticResult();
			GataWayStaticResult orgResule=null;
			GataWayStaticResult companyResule=null;
			for (GataWayStaticResult result : dataList) {

				switch (result.getStaticType()) {
				case SRRPConstant.STATICRESULT01:
					post = String.valueOf(totalSum);
					totalPlatformFinancingList.add(result);
					break;
				case SRRPConstant.STATICRESULT02:
					solve = String.valueOf(totalSum);
					totalPlatformFinancingList.add(result);
					break;
				case SRRPConstant.STATICRESULT03:
					todo = String.valueOf(totalSum);
					totalPlatformFinancingList.add(result);
					break;
				case SRRPConstant.STATICRESULT04:
					if (result.getChangShu() != null
							&& !result.getChangShu().equals("")) {
						companyTotalSum += Double.parseDouble(result
								.getChangShu());
					}
					if (result.getGaoXin() != null
							&& !result.getGaoXin().equals("")) {
						companyTotalSum += Double.parseDouble(result
								.getGaoXin());
					}
					if (result.getGongYeYuan() != null
							&& !result.getGongYeYuan().equals("")) {
						companyTotalSum += Double.parseDouble(result
								.getGongYeYuan());
					}
					if (result.getGuSu() != null
							&& !result.getGuSu().equals("")) {
						companyTotalSum += Double.parseDouble(result.getGuSu());
					}
					if (result.getKunShan() != null
							&& !result.getKunShan().equals("")) {
						companyTotalSum += Double.parseDouble(result
								.getKunShan());
					}
					if (result.getTaiCang() != null
							&& !result.getTaiCang().equals("")) {
						companyTotalSum += Double.parseDouble(result
								.getTaiCang());
					}
					if (result.getWuJiang() != null
							&& !result.getWuJiang().equals("")) {
						companyTotalSum += Double.parseDouble(result
								.getWuJiang());
					}
					if (result.getWuZhong() != null
							&& !result.getWuZhong().equals("")) {
						companyTotalSum += Double.parseDouble(result
								.getWuZhong());
					}
					if (result.getXiangCheng() != null
							&& !result.getXiangCheng().equals("")) {
						companyTotalSum += Double.parseDouble(result
								.getXiangCheng());
					}
					if (result.getZhangJiaGang() != null
							&& !result.getZhangJiaGang().equals("")) {
						companyTotalSum += Double.parseDouble(result
								.getZhangJiaGang());
					}
					endTotalDate=result.getMonthly();
					companyResule=result;
					break;
				case SRRPConstant.STATICRESULT05:
					if (result.getChangShu() != null
							&& !result.getChangShu().equals("")) {
						orgTotalSum += Double.parseDouble(result.getChangShu());
					}
					if (result.getGaoXin() != null
							&& !result.getGaoXin().equals("")) {
						orgTotalSum += Double.parseDouble(result.getGaoXin());
					}
					if (result.getGongYeYuan() != null
							&& !result.getGongYeYuan().equals("")) {
						orgTotalSum += Double.parseDouble(result
								.getGongYeYuan());
					}
					if (result.getGuSu() != null
							&& !result.getGuSu().equals("")) {
						orgTotalSum += Double.parseDouble(result.getGuSu());
					}
					if (result.getKunShan() != null
							&& !result.getKunShan().equals("")) {
						orgTotalSum += Double.parseDouble(result.getKunShan());
					}
					if (result.getTaiCang() != null
							&& !result.getTaiCang().equals("")) {
						orgTotalSum += Double.parseDouble(result.getTaiCang());
					}
					if (result.getWuJiang() != null
							&& !result.getWuJiang().equals("")) {
						orgTotalSum += Double.parseDouble(result.getWuJiang());
					}
					if (result.getWuZhong() != null
							&& !result.getWuZhong().equals("")) {
						orgTotalSum += Double.parseDouble(result.getWuZhong());
					}
					if (result.getXiangCheng() != null
							&& !result.getXiangCheng().equals("")) {
						orgTotalSum += Double.parseDouble(result
								.getXiangCheng());
					}
					if (result.getZhangJiaGang() != null
							&& !result.getZhangJiaGang().equals("")) {
						orgTotalSum += Double.parseDouble(result
								.getZhangJiaGang());
					}
					if (result.getOther() != null
							&& !result.getOther().equals("")) {
						orgOtherTotalSum += Double.parseDouble(result
								.getOther());
					}
					endTotalDate=result.getMonthly();
					orgResule=result;
					break;

				case SRRPConstant.STATICRESULT06:
					count = String.valueOf(totalSum);
					monthlyRankList.add(result);
					break;
				default:
					break;
				}
			}
			// 平台用户
			company = String.valueOf(companyTotalSum);// 企业数量
			staticResult.setCompany(company);
			org = String.valueOf(orgTotalSum);// 机构数量
			otherOrg= String.valueOf(orgOtherTotalSum);// 外地机构数量
			staticResult.setOtherOrg(otherOrg);
			staticResult.setOrg(org);
			staticResult.setEndTotalDate(endTotalDate);
			staticResult.setOrguser(String.valueOf(totalUser));// 机构用户数
			platformUserCountsList.add(companyResule);
			platformUserCountsList.add(orgResule);
			staticResult.setData(platformUserCountsList);
			writeFile(mainOutPath + this.platformUserCountsPath,
					JSON.toJSONString(staticResult));
			// 平台融资总额
						StaticResult result = new StaticResult();
						result.setPost(post);
						result.setSolve(solve);
						result.setTodo(todo);
						result.setData(totalPlatformFinancingList);
						writeFile(mainOutPath + this.totalPlatformFinancingPath,
								JSON.toJSONString(result));

						

						// 月度融资统计
						result = new StaticResult();
						result.setCount(count);
						result.setData(monthlyRankList);
						writeFile(mainOutPath + this.monthlyFinancingStatisticsPath,
								JSON.toJSONString(result));
		}

	}

	// 运行成果-榜单排名
	public void writeRankJsonFile(String queryDate) throws Exception {
		// 初始化对应关系
		if (reflectMap.isEmpty()) {
			initMap();
		}
		//月度榜单排名
		StaticResult result = historyRankQuery(queryDate, false);
		writeFile(mainOutPath + this.monthlyRankPath, JSON.toJSONString(result));
		// 项目需求
		StaticResultDeamd resultDeamdPush = staticResultDeamd(queryDate,
				SRRPConstant.DEMANDSTATUS01);
		writeFile(mainOutPath + this.pushDemandPath, JSON.toJSONString(
				resultDeamdPush,
				SerializerFeature.DisableCircularReferenceDetect));
		// 投融事件
		StaticResultDeamd resultDeamdSuccess = staticResultDeamd(queryDate,
				SRRPConstant.DEMANDSTATUS03);
		writeFile(mainOutPath + this.succFinacedDemandPath, JSON.toJSONString(
				resultDeamdSuccess,
				SerializerFeature.DisableCircularReferenceDetect));
	}

	// 融资需求/投融事件
	public StaticResultDeamd staticResultDeamd(String queryDate, String status)
			throws Exception {
		StaticResultDeamd staticResultDeamd = new StaticResultDeamd();
		// 区域榜单
		List<GataWayRankArea> areaRank = rankDao.findRankAreaList(queryDate,
				status);
		double pushCount = 0;
		double finacMoney = 0;
		String timeId = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		cal.add(Calendar.MONTH, -1);
		timeId = sdf.format(cal.getTime());
		// 行业榜单
				List<String> countList=rankDao.getMaxCount(SRRPConstant.Max_rankAreaSql);
		        if(countList!=null && countList.isEmpty()){
		        	staticResultDeamd.setAreaTotalDate(countList.get(0).toString());
		        }else{
		        	staticResultDeamd.setAreaTotalDate(timeId);
		        }				
				List<String> countList1=rankDao.getMaxCount(SRRPConstant.Max_rankIndustrySql);
		        if(countList1!=null && countList1.isEmpty()){
		        	staticResultDeamd.setIndusryTotalDate(countList1.get(0).toString());
		        }else{
		        	staticResultDeamd.setIndusryTotalDate(timeId);
		        }
		        List<String> countList2=rankDao.getMaxCount(SRRPConstant.Max_finacTurnSql);
		        if(countList2!=null && countList2.isEmpty()){
		        	staticResultDeamd.setFinacTotalDate(countList2.get(0).toString());
		        }else{
		        	staticResultDeamd.setFinacTotalDate(timeId);

		        }		
		if (areaRank != null && areaRank.size() > 0) {
			AreaData areaData = new AreaData();
			List<GataWayStaticResult> data = new ArrayList<GataWayStaticResult>();
			GataWayStaticResult resultDemand = new GataWayStaticResult();
			GataWayStaticResult resultMoney = new GataWayStaticResult();
			for (GataWayRankArea dataArea : areaRank) {
				if (dataArea.getAdminarea() != null
						&& !"".equals(dataArea.getAdminarea())) {
					String reflectColumn = reflectMap.get(dataArea
							.getAdminarea());
					if (reflectColumn != null && !"".equals(reflectColumn)) {
						reflectSet(resultDemand, reflectColumn,
								String.valueOf(DigitFormatUtil
										.digitFormat(dataArea.getDemandNum())));
						reflectSet(resultMoney, reflectColumn,
								String.valueOf(DigitFormatUtil
										.digitFormat(dataArea.getFinanMoney())));
					}
				}
				pushCount += dataArea.getDemandNum();
				finacMoney += dataArea.getFinanMoney();
			}
			data.add(resultDemand);
			areaData.setPushCount(String.valueOf(pushCount));
			areaData.setFinacMoney(String.valueOf(finacMoney));
			data.add(resultMoney);
			areaData.setData(data);
			staticResultDeamd.setAreaData(areaData);
		}
		
		
		List<GataWayRankIndustry> industryRank = rankDao.findRankIndustryList(
				queryDate, status, "demandnum desc");
		if (industryRank != null && industryRank.size() > 0) {
			FinacTurnAndIndustryData industryData = new FinacTurnAndIndustryData();
			String xKey = "";
			String yValue = "";
			String zValue = "";
			int tmp = 0;
			for (GataWayRankIndustry dataIndustry : industryRank) {
				if (tmp > 0) {
					xKey = "," + xKey;
					yValue = "," + yValue;
					zValue = "," + zValue;
				}
				xKey = dataIndustry.getTrades() + xKey;
				yValue = DigitFormatUtil.digitFormat(dataIndustry
						.getFinanMoney()) + yValue;
				zValue = DigitFormatUtil.digitFormat(dataIndustry
						.getDemandNum()) + zValue;
				tmp++;
			}
			industryData.setX_key(xKey);
			industryData.setY_value(yValue);
			industryData.setZ_value(zValue);
			
			staticResultDeamd.setIndustryData(industryData);
		}
		
		// 统计融资需求项与融资金额
//		List<GataWayRankFinacingTurn> dataList = rankDao.findRankFinacTurnList(queryDate, status);
		// 融资轮次
		List<GataWayRankFinacingTurn> finacTurnRankDataList = rankDao.findRankFinacTurnList(queryDate, status,SRRPConstant.TYPE);
		// 月度统计
		List<GataWayRankFinacingTurn> monthlyDataList = rankDao.findRankFinacTurnList(queryDate, status,"02");
			String xKey = "";
			String yValue = "";
			String zValue = "";
			int tmp = 0;
			pushCount = 0;
			finacMoney = 0;
//			for (GataWayRankFinacingTurn data : dataList) {
//				if (SRRPConstant.TYPE.equals(data.getType())) {
//					finacTurnRankDataList.add(data);
//				} else {
//					monthlyDataList.add(data);
//				}
//			}
			// 融资轮次
			if (finacTurnRankDataList != null
					&& finacTurnRankDataList.size() > 0) {
				FinacTurnAndIndustryData finacTurnData = new FinacTurnAndIndustryData();
				for (GataWayRankFinacingTurn data : finacTurnRankDataList) {
					if (tmp > 0) {
						xKey += ",";
						yValue += ",";
						zValue += ",";
					}
					xKey += data.getDimension();
					yValue += DigitFormatUtil.digitFormat(data.getFinanMoney());
					zValue += DigitFormatUtil.digitFormat(data.getDemandNum());
					pushCount += data.getDemandNum();
					finacMoney += data.getFinanMoney();
					tmp++;
				}
				finacTurnData.setX_key(xKey);
				finacTurnData.setY_value(yValue);
				finacTurnData.setZ_value(zValue);
				finacTurnData.setFinacMoney(String.valueOf(finacMoney));
				finacTurnData.setPushCount(String.valueOf(pushCount));
				
				staticResultDeamd.setFinacTurnData(finacTurnData);
			}
		
			// 月度统计
			if (monthlyDataList != null && monthlyDataList.size() > 0) {
				FinacTurnAndIndustryData monthlyData = new FinacTurnAndIndustryData();
				xKey = "";
				yValue = "";
				zValue = "";
				tmp = 0;
				pushCount = 0;
				finacMoney = 0;
				for (GataWayRankFinacingTurn data : monthlyDataList) {
					if (tmp > 0) {
						xKey += ",";
						yValue += ",";
						zValue += ",";
					}
					xKey += data.getDimension();
					yValue += DigitFormatUtil.digitFormat(data.getFinanMoney());
					zValue += DigitFormatUtil.digitFormat(data.getDemandNum());
					pushCount += data.getDemandNum();
					finacMoney += data.getFinanMoney();
					tmp++;
				}
				monthlyData.setX_key(xKey);
				monthlyData.setY_value(yValue);
				monthlyData.setZ_value(zValue);
				monthlyData.setFinacMoney(String.valueOf(finacMoney));
				monthlyData.setPushCount(String.valueOf(pushCount));
				staticResultDeamd.setMonthlyData(monthlyData);
				
			}
		return staticResultDeamd;
	}

	// 运行成果-历史数据
	public StaticResult historyRankQuery(String queryDate, boolean hadHis)
			throws Exception {
		// 融资统计
		List<GataWayStatic> staticResult = new ArrayList<GataWayStatic>();
		if (hadHis) {
			staticResult = rankDao.findRankTotalList(queryDate);
		}
		// 企业榜单
		List<GataWayRankCompany> companyRank = rankDao
				.findRankCompanyList(queryDate);
		// 机构榜单
		List<GataWayRankInvestor> investorRank = rankDao
				.findRankInvestorList(queryDate);
		// 区域榜单
		List<GataWayRankArea> areaRank = rankDao.findRankAreaList(queryDate,
				SRRPConstant.FINANCPHASE31);
		if (areaRank != null && areaRank.size() > 0) {
			for (GataWayRankArea area : areaRank) {
				area.setAdminarea(RedisGetValue.getValueByCode(
						SRRPConstant.DD_AREA, area.getAdminarea()));
			}
		}
		// 行业榜单
		List<GataWayRankIndustry> industryRank = rankDao.findRankIndustryList(
				queryDate, SRRPConstant.FINANCPHASE31, "rankingnum asc");

		StaticResult result = new StaticResult();
		result.setStaticResult(staticResult);
		result.setCompanyRank(companyRank);
		result.setInvestorRank(investorRank);
		result.setAreaRank(areaRank);
		result.setIndustryRank(industryRank);
		return result;

	}

	/*
	 * 统计类型01：发布融资总额；02：解决融资总额；03：正在融资总额；04：平台企业家数；05：平台机构数；06：月度融资统计
	 */
	public List<GataWayStaticResult> findStaticResultList() {
		List<GataWayStaticResult> dataList = null;
		try {
			dataList = gataWayStaticResultDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return dataList;
	}

	private void writeFile(String filePath, String datas) throws IOException {
		FileWriter fw = new FileWriter(filePath);
		PrintWriter out = new PrintWriter(fw);
		out.write(datas);
		out.println();
		fw.close();
		out.close();
	}

	private void reflectSet(Object t, String setMethod, String values)
			throws Exception, IllegalArgumentException,
			InvocationTargetException {
		Method setReadOnly = t.getClass().getMethod(setMethod, String.class);
		setReadOnly.invoke(t, values);
	}

	public void initMap() {
		reflectMap.put("320508", "setGuSu");
		reflectMap.put("320505", "setGaoXin");
		reflectMap.put("320506", "setWuZhong");
		reflectMap.put("320507", "setXiangCheng");
		reflectMap.put("320581", "setChangShu");
		reflectMap.put("320582", "setZhangJiaGang");
		reflectMap.put("320583", "setKunShan");
		reflectMap.put("320584", "setWuJiang");
		reflectMap.put("320585", "setTaiCang");
		reflectMap.put("320586", "setGongYeYuan");
	}

	public String getTotalPlatformFinancingPath() {
		return totalPlatformFinancingPath;
	}

	public void setTotalPlatformFinancingPath(String totalPlatformFinancingPath) {
		this.totalPlatformFinancingPath = totalPlatformFinancingPath;
	}

	public String getPlatformUserCountsPath() {
		return platformUserCountsPath;
	}

	public void setPlatformUserCountsPath(String platformUserCountsPath) {
		this.platformUserCountsPath = platformUserCountsPath;
	}

	public String getMonthlyFinancingStatisticsPath() {
		return monthlyFinancingStatisticsPath;
	}

	public void setMonthlyFinancingStatisticsPath(
			String monthlyFinancingStatisticsPath) {
		this.monthlyFinancingStatisticsPath = monthlyFinancingStatisticsPath;
	}

	public String getMonthlyRankPath() {
		return monthlyRankPath;
	}

	public void setMonthlyRankPath(String monthlyRankPath) {
		this.monthlyRankPath = monthlyRankPath;
	}

	public GataWayStaticResultDao getGataWayStaticResultDao() {
		return gataWayStaticResultDao;
	}

	public void setGataWayStaticResultDao(
			GataWayStaticResultDao gataWayStaticResultDao) {
		this.gataWayStaticResultDao = gataWayStaticResultDao;
	}

	public static void main(String arg[]) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		String xKey = "";
		String yValue = "";
		String zValue = "";
		int tmp = 0;
		for (String key : list) {
			if (tmp > 0) {
				xKey = "," + xKey;
				yValue = "," + yValue;
				zValue = "," + zValue;
			}
			xKey = key + xKey;
			yValue = key + yValue;
			zValue = key + zValue;
			tmp++;
		}
		System.out.println(xKey);
		System.out.println(yValue);
		System.out.println(zValue);
	}
}

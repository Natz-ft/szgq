package com.icfcc.SRRPDao.jpa.entity.creditscore.score;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 常量类
 * 
 * @author zhangfan
 *
 */
public interface CapConstant {

		/**
		 * 评分卡类型
		 */
		public static final String SCORE_CARD = "990001001";

		
	/**
	 * 众微评分卡，评分维度
	 */
	public static final Map<String, String> SCORE_TYPE_ZW_MAP = new HashMap<String, String>() {
		{
			put("FSCORE", "财务水平");
			put("ESCORE", "经营环境");
			put("MSCORE", "管理水平");
			put("BSCORE", "基本素质");
			put("OSCORE", "经营能力");
			put("CSCORE", "信用记录");
		}
	};
	public static final Map<String, String> SCORE_TYPE_ZW_DFJC_MAP = new HashMap<String, String>() {
		{
			put("FSCORE", "基本信息");
			put("ESCORE", "财务信息");
			put("MSCORE", "融资担保状况");
			put("BSCORE", "社会责任");
			put("OSCORE", "信用历史");
		}
	};
	public static final Map<String, String> SCORE_TYPE_ZW_WRXR_MAP = new HashMap<String, String>() {
		{
			put("FSCORE", "盈利能力指标");
			put("ESCORE", "经营能力指标");
			put("MSCORE", "偿债能力指标");
			put("BSCORE", "发展能力指标");
			put("OSCORE", "征信专有指标");
		}
	};

	

	/**
	 * 众微评分卡，评分维度
	 */
	public static final Map<String, String> ANALYSIS_TARGET = new HashMap<String, String>() {
		{
			// 参考CapCompanyAnalysisVO定义
			put("011122", "amountend");
			put("011498", "amountendch");
			put("011499", "amountendldzchj");
			put("011999", "amountendzczj");
			put("012001", "amountenddqjk");
			put("012499", "amountendldfzhj");
			put("012501", "amountendcqjk");
			put("012999", "amountendfzhj");
			put("014999", "amountendsyzqyhj");
			put("026001", "lamountendyysr");
			put("026401", "lamountendyycb");
			put("026799", "lamountendyylr");
			put("026899", "lamountendlrze");
			put("026999", "lamountendjlr");
			put("030001", "amountturn");
			put("030002", "gamountturn");
			put("030003", "damountturn");
			put("030067", "ztaxamount");
			put("030069", "taxamount");
			put("040072", "turnin");
			put("050085", "yearimport");
			put("050086", "yearexport");
			put("060075", "samount");
			put("070076", "damount");
			put("080077", "qamount");
			put("090004", "origprcp");
			put("100084", "personcount");
			put("100082", "payamont");
			put("118101", "applycount");// 企业人数
			put("120001", "loannumbalance");// 贷款余额
			put("120002", "loannum");// 有贷户数量
		}
	};

	/**
	 * 信息筛选其他指标-小额贷款状态-海关评级-安全事故记录-税务部门评级-环保信用评价-荣誉表彰
	 */
	public static final Map<String, String> OTHER_TARGET = new HashMap<String, String>() {
		{
			// 小额贷款状态
			put("010087", "逾期");
			put("020087", "呆帐");
			put("030087", "正常");
			put("040087", "结清");
			// add by hechengtai at 2017-5-8 14:37:23 for BugID：1198 - 新增
			put("050087", "无小额贷款历史");
			// 海关评级
			put("010088", "高级认证");
			put("020088", "一般认证");
			put("030088", "失信企业");
			// add by hechengtai at 2017-5-9 10:37:40 for BugID：1198 - 新增
			put("040088", "无海关评级");
			// 安全事故记录1-存在较大安全事故 2 -不存在安全事故记录
			put("010089", "1");
			// add by hechengtai at 2017-5-9 10:37:40 for BugID：1198 - 新增
			put("020089", "2");
			// 税务部门评级
			put("010090", "非正常户");
			put("020090", "A级");
			// add by hechengtai at 2017-5-9 10:37:40 for BugID：1198 - 新增
			put("030090", "无税务评价");
			// 环保信用评价
			put("010091", "绿色");
			put("020091", "蓝色");
			put("030091", "黄色");
			put("040091", "红色");
			put("050091", "黑色");
			// add by hechengtai at 2017-5-8 14:45:02 for BugID：1198 - 新增
			put("060091", "无环保信用评价");
			// 荣誉表彰
			put("010096", "province");// 省
			put("020096", "city");// 市
			put("030096", "county");// 县
		}
	};
	/**
	 * 有贷户界面--负面信息
	 */
	public static final Map<String, String> NEGATIVE_INFO = new HashMap<String, String>() {
		{
			put("0092", "处罚");
			put("0098", "欠税");
			put("0099", "欠费");
			put("0104", "逾期");
			put("0106", "涉诉");
			// put("Others", "其他");

		}
	};
	/**
	 * 负面信息--其他信息
	 */
	public static final Map<String, String> OTHER_INFO = new HashMap<String, String>() {
		{
			put("0088", "海关资信级别");
			put("0090", "税务部门评价");
			put("0091", "环保信用评价");
		}
	};

	/**
	 * 归一化评分常量
	 */
	public static final Map<String, Double> SCORE_NORM = new HashMap<String, Double>() {
		{
			put("FSCORE", 170.59); // 财务水平
			put("ESCORE", 132.68); // 经营环境
			put("MSCORE", 47.21); // 管理水平
			put("BSCORE", 134.9); // 基本素质
			put("OSCORE", 89.1); // 经营能力
			put("CSCORE", 273.85); // 信用记录
		}
	};
	public static final Map<String, Double> SCORE_PARAM = new HashMap<String, Double>() {
		{
			put("FSCORE", -19.35); // 财务水平
			put("ESCORE", -20.18); // 经营环境
			put("MSCORE", -22.68); // 管理水平
			put("BSCORE", 7.83); // 基本素质
			put("OSCORE", 0.67); // 经营能力
			put("CSCORE", -159.53); // 信用记录
		}
	};
	public static final Map<String, Double> SCORE_DFJC_YSB_NORM = new HashMap<String, Double>() {
		{
			put("FSCORE", 128d); // 基本信息
			put("ESCORE", 70d); // 财务信息
			put("MSCORE", 120d); // 融资担保状况
			put("BSCORE", 60d); // 社会责任
			put("OSCORE", 200d); // 信用历史
		}
	};
	public static final Map<String, Double> SCORE_DFJC_YSB_PARAM = new HashMap<String, Double>() {
		{
			put("FSCORE", -60d); // 基本信息
			put("ESCORE", -76d); // 财务信息
			put("MSCORE", -15d); // 融资担保状况
			put("BSCORE", -15d); // 社会责任
			put("OSCORE", 72d); // 信用历史
		}
	};
	public static final Map<String, Double> SCORE_DFJC_WSB_NORM = new HashMap<String, Double>() {
		{
			put("FSCORE", 130d); // 基本信息
			put("ESCORE", 80d); // 财务信息
			put("MSCORE", 110d); // 融资担保状况
			put("BSCORE", 80d); // 社会责任
			put("OSCORE", 190d); // 信用历史
		}
	};
	public static final Map<String, Double> SCORE_DFJC_WSB_PARAM = new HashMap<String, Double>() {
		{
			put("FSCORE", -63d); // 基本信息
			put("ESCORE", -96d); // 财务信息
			put("MSCORE", -3d); // 融资担保状况
			put("BSCORE", -50d); // 社会责任
			put("OSCORE", 29d); // 信用历史
		}
	};

	
	/**
	 * 评分卡定义
	 * 
	 * @author zhangfan
	 *
	 */
	public static final class CREDIT_SCORE_CARD {
		// 众微评分卡
		public static final String ZW = "990001001";
		public static final String DFJC = "990001002";
		public static final String WRXR = "990001003";
		public static final String WSB = "0";
		public static final String YSB = "1";
		public static final String WEXE_MAX_SCORE = "850";

		// 东方金诚评分等级
		public static final String RATING_DFJC_AA = "AA";
		public static final String RATING_DFJC_A = "A";
		public static final String RATING_DFJC_BBB = "BBB";
		public static final String RATING_DFJC_BB = "BB";
		public static final String RATING_DFJC_B = "B";
		public static final String RATING_DFJC_CCC = "CCC";
		public static final String RATING_DFJC_CC = "CC";
		public static final String RATING_DFJC_C = "C";

		// 威尔希尔评分等级
		public static final String RATING_WEXE_R1 = "R1";
		public static final String RATING_WEXE_R2 = "R2";
		public static final String RATING_WEXE_R3 = "R3";
		public static final String RATING_WEXE_R4 = "R4";
		public static final String RATING_WEXE_R5 = "R5";

	}

	/**
	 * 评分评级
	 */
	public static final Map<String, String> SCORE_RATING = new LinkedHashMap<String, String>() {
		{
			put("R5", "550以下");
			put("R4", "[550,600)");
			put("R3", "[600,645)");
			put("R2", "[645,700)");
			put("R1", "700以上(含)");
		}
	};
	// 东方金诚 有/无社保评级
	public static final Map<String, String> SCORE_DFJC_YSB_RATING = new LinkedHashMap<String, String>() {
		{
			put("C", "360以下");
			put("CC", "[360,400)");
			put("CCC", "[400,431)");
			put("B", "[431,454)");
			put("BB", "[454,478)");
			put("BBB", "[478,517)");
			put("A", "[517,548)");
			put("AA", "548以上(含)");
		}
	};
	public static final Map<String, String> SCORE_DFJC_WSB_RATING = new LinkedHashMap<String, String>() {
		{
			put("C", "406以下");
			put("CC", "[406,444)");
			put("CCC", "[444,480)");
			put("B", "[480,500)");
			put("BB", "[500,546)");
			put("BBB", "[546,565)");
			put("A", "[565,602)");
			put("AA", "602以上");
		}
	};
	public static final Map<String, String> SCORE_WRXR_RATING = new LinkedHashMap<String, String>() {
		{
			put("R5", "[351,550]");
			put("R4", "[551,600]");
			put("R3", "[601,715]");
			put("R2", "[716,800]");
			put("R1", "[801,850]");
		}
	};
	public static final Map<String, String> WRXR_INDUSTRY_TYPE = new LinkedHashMap<String, String>() {
		{
			put("C", "制造业");
			put("M", "科学研究和技术服务业");
			put("F", "批发零售业");
			put("E", "建筑业");
			put("Other", "其他行业");
		}
	};
	

	/**
	 * 征信报告查询类别
	 */
	public static final class CREDIT_REPORT_TYPE {
		public static final String XXSX = "1";
		public static final String MBSX = "2";
		public static final String XYPF = "3";
	}

	/**
	 * 自定义报表
	 * 
	 * @author JD1
	 *
	 */
	public static final class CUST_REPORT {
		public static final String SUM_YEAR = "year";
		public static final String SUM_MONTH = "month";
		/**
		 * 筛选条件
		 */
		public static final String FILTTER_ORDER_1 = "ORDER_1";
		public static final String FILTTER_ORDER_2 = "ORDER_2";

		public static final String FILTTER_REGION = "region";
		public static final String FILTTER_SCALE = "scale";
		public static final String FILTTER_INSUSTRY = "insustry";
		public static final String FILTTER_REGITER_TYPE = "register_type";
		public static final String FILTTER_CHENGXIN = "chengxin";
		public static final String FILTTER_CORPTYPE = "corptype";
		public static final String FILTTER_LOANTYPE = "loantype";
		public static final Map<String, String> MAP_FILTTER_HIS = new HashMap<String, String>() {
			{
				put("region", "region");
				put("scale", "scale");
				put("insustry", "insustry");
				put("register_type", "register_type");
			}
		};
		public static final Map<String, String> MAP_FILTTER_OTHER = new HashMap<String, String>() {
			{
				put("chengxin", "chengxin");
				put("corptype", "corptype");
				put("loantype", "loantype");
			}
		};
		/**
		 * 汇总条件
		 */
		public static final String HUIZONG_ZICHANFUZHAI = "zichanfuzhai";
		public static final String HUIZONG_LIRUN = "lirun";
		public static final String HUIZONG_NASHUI = "nashui";
		public static final String HUIZONG_HAIGUAN = "haiguan";
		public static final String HUIZONG_SHUIDIANQI = "shuidianqi";
		public static final String HUIZONG_SMALLLOAN = "smallloan";
		public static final String HUIZONG_GONGJIJIN = "gongjijin";
		public static final String HUIZONG_SHEBAO = "shebao";
		public static final String HUIZONG_LOANINFO = "loaninfo";
		public static final String HUIZONG_ASSUREINFO = "assureinfo";

		public static final String[] HUIZONG_ARRAY = { CapConstant.CUST_REPORT.HUIZONG_ZICHANFUZHAI, CapConstant.CUST_REPORT.HUIZONG_LIRUN, CapConstant.CUST_REPORT.HUIZONG_NASHUI, CapConstant.CUST_REPORT.HUIZONG_HAIGUAN, CapConstant.CUST_REPORT.HUIZONG_SHUIDIANQI, CapConstant.CUST_REPORT.HUIZONG_SMALLLOAN, CapConstant.CUST_REPORT.HUIZONG_GONGJIJIN, CapConstant.CUST_REPORT.HUIZONG_SHEBAO, CapConstant.CUST_REPORT.HUIZONG_LOANINFO, CapConstant.CUST_REPORT.HUIZONG_ASSUREINFO };
		/**
		 * 汇总字段别名
		 */
		public static final String SUM_COLIMN_NAME_1 = "typestr1";
		public static final String SUM_COLIMN_NAME_2 = "typestr2";
		/**
		 * 汇总字企业数别名
		 */
		public static final String SUM_CORP_NUM = "corpnum";

		/**
		 * 企业类型编码
		 */
		public static final String CORP_TYPE_AUTH = "001";
		public static final String CORP_TYPE_LOAN = "002";
		public static final String CORP_TYPE_AUTH_CODE = "1";
		public static final String CORP_TYPE_LOAN_CODE = "1";

		/**
		 * 诚信类编码
		 */
		public static final String CHENGXIN_001 = "001";
		public static final String CHENGXIN_002 = "002";
		public static final String CHENGXIN_003 = "003";
		public static final String CHENGXIN_004 = "004";
		public static final String CHENGXIN_005 = "005";
		public static final String CHENGXIN_006 = "006";
		public static final String CHENGXIN_007 = "007";
		public static final String CHENGXIN_008 = "008";
		public static final String CHENGXIN_009 = "009";
		/**
		 * 000019表report_type
		 */
		public static final String REPORT_TYPE_ZICHANFUZHAI = "1";
		public static final String REPORT_TYPE_LIRUN = "2";

		/**
		 * 同比环比类型
		 */
		public static final String HUIZONG = "0";
		public static final String TONGBI = "1";
		public static final String HUANBI = "2";

		/**
		 * 结果map中数据存放类型
		 */
		public static final String TONGBI_O = "1";
		public static final String TONGBI_N = "2";
		public static final String HUANBI_O = "3";
		public static final String HUANBI_N = "4";
	}

	
	/**
	 * 产品应用常量
	 * 
	 * @author usr
	 *
	 */
	public static final class PRODUCT_APPLY {
		/**
		 * 时间类型-日线
		 */
		public static final String TIME_TYPE_DAY = "DAY";
		/**
		 * 时间类型-周线
		 */
		public static final String TIME_TYPE_WEEK = "WEEK";
		/**
		 * 时间类型-月线
		 */
		public static final String TIME_TYPE_MONTH = "MONTH";
		/**
		 * 产品使用
		 */
		public static final String PRODUCT = "PRODUCT";
		/**
		 * 访问人数
		 */
		public static final String VISITOR = "VISITOR";
		/**
		 * 专线连接
		 */
		public static final String ATTACH_INFO_ZXLJ = "3";
		/**
		 * 账号分配
		 */
		public static final String ATTACH_INFO_ZHFP = "6";
		/**
		 * 查询使用
		 */
		public static final String ATTACH_INFO_CXSY = "5";
		/**
		 * 授权征集
		 */
		public static final String ATTACH_INFO_SQZJ = "4";
		/**
		 * 政府部门
		 */
		public static final String DEPT_TYPE_ZFBM = "00";
		/**
		 * 政策性银行
		 */
		public static final String DEPT_TYPE_ZCXYH = "01";
		/**
		 * 国有商业银行
		 */
		public static final String DEPT_TYPE_GYSYYH = "02";
		/**
		 * 股份制商业银行
		 */
		public static final String DEPT_TYPE_GFZSYYH = "03";
		/**
		 * 城市商业银行
		 */
		public static final String DEPT_TYPE_CSSYYH = "04";
		/**
		 * 农村商业银行
		 */
		public static final String DEPT_TYPE_NCSYYH = "05";
		/**
		 * 村镇银行
		 */
		public static final String DEPT_TYPE_CZYH = "06";
		/**
		 * 外资银行
		 */
		public static final String DEPT_TYPE_WZSYYH = "07";
		/**
		 * 财务公司
		 */
		public static final String DEPT_TYPE_CWGS = "08";
		/**
		 * 担保公司
		 */
		public static final String DEPT_TYPE_DBGS = "09";
		/**
		 * 保险公司
		 */
		public static final String DEPT_TYPE_BXGS = "10";
		/**
		 * 小额贷款公司
		 */
		public static final String DEPT_TYPE_XEDKGS = "11";
		/**
		 * 投资公司
		 */
		public static final String DEPT_TYPE_TZGS = "12";
		/**
		 * 融资租赁公司
		 */
		public static final String DEPT_TYPE_RZZLGS = "13";
		/**
		 * 信托公司
		 */
		public static final String DEPT_TYPE_XTGS = "14";
		/**
		 * 其他
		 */
		public static final String DEPT_TYPE_OTHER = "98";
		/**
		 * 征信公司
		 */
		public static final String DEPT_TYPE_ZXGS = "99";

	}

	/**
	 * 信息采集取数不包括单位
	 */
	public static final Map<String, String> INFOCOLLECTION_EXIST_DEPT = new LinkedHashMap<String, String>() {
		{
			put("0014", "园区国税局");
			put("0017", "园区地税局");
			put("0023", "园区公积金中心");
			put("0015", "张家港保税区国税局");
			put("0018", "张家港保税区地税局");
		}
	};
	/**
	 * 信息采集及时性分数
	 */
	public static final String TIMELINESS_SCORE = "30";
	/**
	 * 完整性分数
	 */
	public static final String COMPLETE_SCORE = "60";

	/**
	 * 信息筛选 - 统计指标 - 所属日期 用来 标识 各项数据统计的日期维度 Y:年 M:年月
	 * add by hechengtai at 2017-5-31 11:32:19
	 */
	public static final Map<String, String> STATISTICS_DATE = new HashMap<String, String>() {
		{
			put("011122", "Y");
			put("011498", "Y");
			put("011499", "Y");
			put("011999", "Y");
			put("012001", "Y");
			put("012499", "Y");
			put("012501", "Y");
			put("012999", "Y");
			put("014999", "Y");
			put("026001", "Y");
			put("026401", "Y");
			put("026799", "Y");
			put("026899", "Y");
			put("026999", "Y");
			put("030001", "Y");
			put("030002", "Y");
			put("030003", "Y");
			put("030067", "Y");
			put("030069", "Y");
			put("040072", "Y");
			put("050085", "Y");
			put("050086", "Y");
			put("060075", "Y");
			put("070076", "Y");
			put("080077", "Y");
			put("090004", "M");
			put("100084", "Y");
			put("100082", "Y");
			put("118101", "M");
			put("120001", "M");
			put("120002", "M");
		}
	};
	/**
	 * 信用评分柱状图统计维度类型
	 * @author yang
	 *
	 */
	public static final class SCORERESULT_BAR {

		/**
		 * 全部
		 */
		public static final String ALL = "all";
		/**
		 * 行业
		 */
		public static final String INDUSTRY_NO = "INDUSTRY_NO";
		/**
		 * 区域
		 */
		public static final String REGION = "REGION";
		/**
		 * 规模
		 */
		public static final String SCALE = "SCALE";
		/**
		 * 企业性质
		 */
		public static final String ENT_TYPE = "ENT_TYPE";
		/**
		 * 投资类型
		 */
		public static final String INVESTMENT_TYPE = "INVESTMENT_TYPE";
	}
	/**
	 * 信用评分查询折线图
	 */
	public static final Map<String, String> SCORE_LINE = new HashMap<String, String>() {
		{
			put(SCORERESULT_BAR.ALL, "信用评分"); // 信用评分
			put(SCORERESULT_BAR.INDUSTRY_NO, "行业平均分"); // 同行业
			put(SCORERESULT_BAR.REGION, "同区域平均分"); // 同区域
			put(SCORERESULT_BAR.SCALE, "同规模平均分"); // 同规模
			put(SCORERESULT_BAR.ENT_TYPE, "同企业性质平均分"); // 同企业性质
			put(SCORERESULT_BAR.INVESTMENT_TYPE, "同投资类型平均分"); // 同投资类型
		}
	};
}

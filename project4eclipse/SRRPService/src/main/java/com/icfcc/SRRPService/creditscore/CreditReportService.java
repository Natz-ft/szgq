package com.icfcc.SRRPService.creditscore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.icfcc.SRRPDao.jpa.entity.creditscore.report.BDCVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.CWZBVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ColumnAndListVO;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ComplaintInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.EmptyConditionUtils;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.EvaluateInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.HGVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.LoanInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.NSVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.NegativeInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.OperateInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.PatentInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ReportConstant;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ReportVO;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.SDQVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.SeniorExecutiveInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ShareholderInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.SocialSecurityVo;
import com.icfcc.SRRPService.util.AESUtil;
import com.icfcc.credit.platform.util.JsonUtil;

@Service
public class CreditReportService {
	// 接口路径
	@Value("${reportURL}")
	public String url;
	// 系统用户
	@Value("${reporUser}")
	public String reporUser;
	// 访问类型
	@Value("${reason}")
	public String reason;
	// 密钥
	@Value("${key}")
	public String key;
	// 连接超时参数
	@Value("${connectionTimeout}")
	public Long connectionTimeout;
	// 响应超时参数
	@Value("${receiveTimeout}")
	public Long receiveTimeout;

	/**
	 * 查询信用报告方法
	 * 
	 * @param jsonstr
	 * @return
	 */
	public ReportVO getReportVo(String creditCode,boolean flag) {
		// 调用征信系统接口 71410087-1(测试)
		 String jsonstr=getResult(creditCode);
//		String jsonstr = "1|{\"a2\":\"show\",\"a2List\":[],\"b\":\"show\",\"b2\":\"show\",\"b2List\":[{\"b2_controlling_share\":\"93.53%\",\"b2_inv\":\"吴震宇\",\"b2_invtype\":\"自然人股东\",\"b2_subconam\":\"1,878\"},{\"b2_controlling_share\":\"6.47%\",\"b2_inv\":\"吴江新金城不锈钢制品厂\",\"b2_invtype\":\"个人独资企业\",\"b2_subconam\":\"130\"}],\"bList\":[{\"b_amount\":\"\",\"b_deposit_month\":\"201703\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"26,106.16\",\"b_sbrs\":\"31\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201702\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"22,701.12\",\"b_sbrs\":\"27\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201701\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"23,578.08\",\"b_sbrs\":\"28\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201612\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"21,824.16\",\"b_sbrs\":\"26\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201611\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,151.64\",\"b_sbrs\":\"24\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201610\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,151.64\",\"b_sbrs\":\"24\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201609\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"19,944.28\",\"b_sbrs\":\"24\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201608\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,812.6\",\"b_sbrs\":\"25\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201607\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"21,352.6\",\"b_sbrs\":\"25\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201606\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"21,412.6\",\"b_sbrs\":\"25\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201605\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,462.68\",\"b_sbrs\":\"24\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201604\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,682.56\",\"b_sbrs\":\"23\"}],\"before_last_year_hg\":\"2015\",\"before_last_year_tax\":\"2015\",\"before_last_year_weg\":\"2015\",\"c\":\"show\",\"c2\":\"show\",\"c2List\":[{\"c2_member_name\":\"吴震宇\",\"c2_member_type\":\"执行董事\"},{\"c2_member_name\":\"潘根林\",\"c2_member_type\":\"监事\"}],\"cList\":[{\"c_before_last_qmye\":\"663,862.39\",\"c_cn_name\":\"货币资金\",\"c_code\":\"1099\",\"c_current_qmye\":\"3,224,759.37\",\"c_last_qmye\":\"3,012,563.95\",\"c_last_time_qmye\":\"3,012,563.95\",\"c_ord\":1},{\"c_before_last_qmye\":\"9,080,981.40\",\"c_cn_name\":\"应收账款\",\"c_code\":\"1122\",\"c_current_qmye\":\"3,569,070.02\",\"c_last_qmye\":\"8,101,770.85\",\"c_last_time_qmye\":\"8,101,770.85\",\"c_ord\":2},{\"c_before_last_qmye\":\"57,488.00\",\"c_cn_name\":\"预付账款\",\"c_code\":\"1123\",\"c_current_qmye\":\"6,937,572.20\",\"c_last_qmye\":\"203,955.00\",\"c_last_time_qmye\":\"203,955.00\",\"c_ord\":3},{\"c_before_last_qmye\":\"4,112,764.89\",\"c_cn_name\":\"存货\",\"c_code\":\"1498\",\"c_current_qmye\":\"5,196,098.29\",\"c_last_qmye\":\"5,079,129.53\",\"c_last_time_qmye\":\"5,079,129.53\",\"c_ord\":4},{\"c_before_last_qmye\":\"29,047,201.15\",\"c_cn_name\":\"流动资产合计\",\"c_code\":\"1499\",\"c_current_qmye\":\"29,447,698.20\",\"c_last_qmye\":\"27,305,419.60\",\"c_last_time_qmye\":\"27,305,419.60\",\"c_ord\":5},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"长期股权投资\",\"c_code\":\"1511\",\"c_current_qmye\":\"8,500,000.00\",\"c_last_qmye\":\"8,500,000.00\",\"c_last_time_qmye\":\"8,500,000.00\",\"c_ord\":6},{\"c_before_last_qmye\":\"1,307,536.96\",\"c_cn_name\":\"固定资产净额\",\"c_code\":\"1698\",\"c_current_qmye\":\"1,188,668.57\",\"c_last_qmye\":\"1,199,353.67\",\"c_last_time_qmye\":\"1,199,353.67\",\"c_ord\":7},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"无形资产\",\"c_code\":\"1701\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":8},{\"c_before_last_qmye\":\"30,354,738.11\",\"c_cn_name\":\"资产总计\",\"c_code\":\"1999\",\"c_current_qmye\":\"39,136,366.77\",\"c_last_qmye\":\"37,004,773.27\",\"c_last_time_qmye\":\"37,004,773.27\",\"c_ord\":9},{\"c_before_last_qmye\":\"2,000,000.00\",\"c_cn_name\":\"短期借款\",\"c_code\":\"2001\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":10},{\"c_before_last_qmye\":\"-346,261.58\",\"c_cn_name\":\"应付账款\",\"c_code\":\"2202\",\"c_current_qmye\":\"843,717.70\",\"c_last_qmye\":\"2,361,897.64\",\"c_last_time_qmye\":\"2,361,897.64\",\"c_ord\":11},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"预收账款\",\"c_code\":\"2203\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":12},{\"c_before_last_qmye\":\"12,297,357.75\",\"c_cn_name\":\"流动负债合计\",\"c_code\":\"2499\",\"c_current_qmye\":\"14,687,496.21\",\"c_last_qmye\":\"13,476,147.62\",\"c_last_time_qmye\":\"13,476,147.62\",\"c_ord\":13},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"长期借款\",\"c_code\":\"2501\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":14},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"应付债券\",\"c_code\":\"2502\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":15},{\"c_before_last_qmye\":\"12,297,357.75\",\"c_cn_name\":\"负债合计\",\"c_code\":\"2999\",\"c_current_qmye\":\"14,687,496.21\",\"c_last_qmye\":\"13,476,147.62\",\"c_last_time_qmye\":\"13,476,147.62\",\"c_ord\":16},{\"c_before_last_qmye\":\"18,057,380.36\",\"c_cn_name\":\"所有者权益合计\",\"c_code\":\"4998\",\"c_current_qmye\":\"24,448,870.56\",\"c_last_qmye\":\"23,528,625.65\",\"c_last_time_qmye\":\"23,528,625.65\",\"c_ord\":17},{\"c_before_last_qmye\":\"30,354,738.11\",\"c_cn_name\":\"负债和所有者权益总计\",\"c_code\":\"4999\",\"c_current_qmye\":\"39,136,366.77\",\"c_last_qmye\":\"37,004,773.27\",\"c_last_time_qmye\":\"37,004,773.27\",\"c_ord\":18}],\"cfxx\":\"show\",\"cfxxList\":[],\"current_year_hg\":\"2017\",\"current_year_shui\":\"2017\",\"current_year_tax\":\"2017\",\"current_year_weg\":\"2017\",\"d2\":\"show\",\"d2_abuitem\":\"\",\"d2_cbuitem\":\"\",\"d2_corp_code\":\"57543357-9\",\"d2_corp_name\":\"吴江市新金城不锈钢制品有限公司\",\"d2_credit_code\":\"91320509575433579Q\",\"d2_dom\":\"吴江经济技术开发区厍浜村\",\"d2_entstatus\":\"在营（开业）企业\",\"d2_enttype\":\"有限责任公司(自然人投资或控股)\",\"d2_esdate\":\"2011-05-31\",\"d2_ic_num\":\"320584000293009\",\"d2_industryco\":\"金属制日用品制造\",\"d2_kjsbrs\":58,\"d2_legal_name\":\"吴震宇\",\"d2_legal_per\":\"吴震宇\",\"d2_legal_phone\":\"13776144980\",\"d2_op\":\"2011-05-31至2021-05-30\",\"d2_opscope\":\"不锈钢制品、金属制品、电加热设备、厨房设备、燃气具的生产、销售、安装与维护；厨房设备设计；室内外装饰装修工程设计、施工；水电、机电安装工程设计、施工；建筑材料、办公用品的销售。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\"d2_regcap\":\"2008万元人民币\",\"d2_tel\":\"13776144980\",\"data_df_cate\":\"['201503-04','201505-06','201507-08','201509-10','201511-12','201601-02','201603-04','201605-06','201607-08','201609-10','201611-12','201701-02']\",\"data_df_sery\":\"[]\",\"data_gsrs_cate\":\"['2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1']\",\"data_gsrs_sery\":\"[{'data':[62,62,62,48,48,48,58,58],'name':'公司人数'}]\",\"data_jlr_cate\":\"['2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1']\",\"data_jlr_sery\":\"[{'data':[-845147.88,863536.3,171906.29,-438251.54,-411151.6,327420.5,1473617.22,null],'name':'净利润'}]\",\"data_xdrzye_cate\":\"['2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1','2017Q2']\",\"data_xdrzye_sery\":\"[]\",\"data_yysr_cate\":\"['2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1']\",\"data_yysr_sery\":\"[{'data':[4263501.41,1.281379487E7,1.101946643E7,7723973.39,5392252.94,9303314.83,1.493298344E7,null],'name':'营业收入'}]\",\"data_zz_ye_cate\":\"['2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1']\",\"data_zz_ye_sery\":\"[{'data':[29080.71,290874.78,150084.01,159352.53,131682.71,178286.83,273925.85,92245.23],'name':'增值税与营业税合计'}]\",\"dyxx\":\"show\",\"dyxxList\":[],\"e\":\"show\",\"e_archive_date\":\"2016-03-30 14:11:57\",\"e_curr_time\":\"2017-12-18 10:07:17\",\"e_department_name\":\"苏州股权平台\",\"e_real_name\":\"苏州股权平台\",\"f\":\"show\",\"fList\":[{\"f_beforelastyear\":\"690,980.67\",\"f_create_time\":\"2017-04-12\",\"f_curyear\":\"92,245.23\",\"f_lastyear\":\"743,247.92\",\"f_max_month\":\"1-2月\",\"f_shuiz\":\"增值税\"},{\"f_beforelastyear\":\"186,275.36\",\"f_create_time\":\"2017-01-16\",\"f_curyear\":\"\",\"f_lastyear\":\"251,153.63\",\"f_max_month\":\"\",\"f_shuiz\":\"企业所得税\"},{\"f_beforelastyear\":\"50,717.04\",\"f_create_time\":\"2016-04-08\",\"f_curyear\":\"\",\"f_lastyear\":\"\",\"f_max_month\":\"\",\"f_shuiz\":\"营业税\"},{\"f_beforelastyear\":\"15,000.00\",\"f_create_time\":\"2017-04-12\",\"f_curyear\":\"2,780.00\",\"f_lastyear\":\"15,085.00\",\"f_max_month\":\"1-2月\",\"f_shuiz\":\"个人所得税\"},{\"f_beforelastyear\":\"942,973.07\",\"f_create_time\":\"\",\"f_curyear\":\"95,025.23\",\"f_lastyear\":\"1,009,486.55\",\"f_max_month\":\"-\",\"f_shuiz\":\"合计\"}],\"g\":\"show\",\"gpdb\":\"show\",\"gpdbList\":[],\"h\":\"show\",\"hList\":[],\"hc\":\"show\",\"hcList\":[],\"i\":\"show\",\"iList\":[],\"j\":\"show\",\"jList\":[{\"j_name\":\"一种消毒清洗机\",\"j_pat_cert_no\":\"2016202679755\",\"j_pat_date\":\"2016-09-29\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种智能式开水器\",\"j_pat_cert_no\":\"2016202679736\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种蜂窝式静电油烟净化器\",\"j_pat_cert_no\":\"2016202679717\",\"j_pat_date\":\"2016-11-15\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种节能程控环保开水器\",\"j_pat_cert_no\":\"201620267905X\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种燃气炒菜灶\",\"j_pat_cert_no\":\"2016202678911\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种食物保温柜\",\"j_pat_cert_no\":\"2016202677603\",\"j_pat_date\":\"2016-11-15\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种电磁炉\",\"j_pat_cert_no\":\"2016202676827\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种运水自吸式烟罩\",\"j_pat_cert_no\":\"201620267567X\",\"j_pat_date\":\"2016-11-15\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种推车式蒸柜\",\"j_pat_cert_no\":\"2016202621608\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"静电式餐饮油烟净化设备\",\"j_pat_cert_no\":\"2015207183535\",\"j_pat_date\":\"2016-01-24\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"热风循环消毒柜\",\"j_pat_cert_no\":\"2014204008421\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"推车式蒸饭车\",\"j_pat_cert_no\":\"2014204008417\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"摇柄下水\",\"j_pat_cert_no\":\"2014204007950\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"高筒下水\",\"j_pat_cert_no\":\"2014204007946\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"蓄热蒸饭车\",\"j_pat_cert_no\":\"2014204007931\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"消音风柜\",\"j_pat_cert_no\":\"2014204007927\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"熄火保护炉灶\",\"j_pat_cert_no\":\"2014204007912\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"环形排风灶\",\"j_pat_cert_no\":\"2013200507750\",\"j_pat_date\":\"2013-06-26\",\"j_pat_type\":\"实用新型\"}],\"jaxx\":\"show\",\"jaxxList\":[{\"jaxx_case_end_amount\":0,\"jaxx_case_end_date\":\"2016-07-18\",\"jaxx_case_end_type\":\"判决\",\"jaxx_case_no\":\"(2016)苏0509民催59号\",\"jaxx_case_person_state\":\"申请人\",\"jaxx_case_reson\":\"申请公示催告\",\"jaxx_case_type\":\"民事特殊\"},{\"jaxx_case_end_amount\":0,\"jaxx_case_end_date\":\"2016-07-18\",\"jaxx_case_end_type\":\"判决\",\"jaxx_case_no\":\"(2016)苏0509民催60号\",\"jaxx_case_person_state\":\"申请人\",\"jaxx_case_reson\":\"申请公示催告\",\"jaxx_case_type\":\"民事特殊\"},{\"jaxx_case_end_amount\":0,\"jaxx_case_end_date\":\"2016-02-19\",\"jaxx_case_end_type\":\"撤诉\",\"jaxx_case_no\":\"(2015)吴江商初字第01894号\",\"jaxx_case_person_state\":\"原告\",\"jaxx_case_reson\":\"股东出资纠纷\",\"jaxx_case_type\":\"民事一审\"},{\"jaxx_case_end_amount\":\"\",\"jaxx_case_end_date\":\"2015-11-18\",\"jaxx_case_end_type\":\"撤诉\",\"jaxx_case_no\":\"(2015)吴江商初字第01892号\",\"jaxx_case_person_state\":\"原告\",\"jaxx_case_reson\":\"股东出资纠纷\",\"jaxx_case_type\":\"民事一审\"}],\"k\":\"show\",\"k2\":\"show\",\"k2List\":[],\"k3\":\"show\",\"k3List\":[],\"kList\":[{\"k_award_date\":\"2015-05-28\",\"k_create_time\":\"2016-11-14\",\"k_evaluate_content\":\"苏州市第八批重合同守信用企业\",\"k_evaluate_grade\":\"\",\"k_evaluate_organ\":\"苏州市人民政府\",\"k_file_no\":\"苏府〔2015〕83号\"}],\"last_year_hg\":\"2016\",\"last_year_tax\":\"2016\",\"last_year_weg\":\"2016\",\"laxx\":\"show\",\"laxxList\":[],\"m\":\"show\",\"mList\":[],\"max_time\":\"201612\",\"mc\":\"show\",\"mcList\":[],\"n\":\"show\",\"nList\":[{\"n_before_last_qmye\":\"39,580,953.37\",\"n_cn_name\":\"主营业务收入\",\"n_code\":\"6001\",\"n_current_qmye\":\"37,352,524.60\",\"n_last_qmye\":\"38,676,849.42\",\"n_last_time_qmye\":\"38,676,849.42\",\"n_ord\":1},{\"n_before_last_qmye\":\"31,781,903.17\",\"n_cn_name\":\"主营业务成本\",\"n_code\":\"6401\",\"n_current_qmye\":\"30,398,068.23\",\"n_last_qmye\":\"31,776,709.63\",\"n_last_time_qmye\":\"31,776,709.63\",\"n_ord\":2},{\"n_before_last_qmye\":\"457,620.79\",\"n_cn_name\":\"营业费用\",\"n_code\":\"6601\",\"n_current_qmye\":\"471,910.99\",\"n_last_qmye\":\"430,465.99\",\"n_last_time_qmye\":\"430,465.99\",\"n_ord\":3},{\"n_before_last_qmye\":\"4,771,833.61\",\"n_cn_name\":\"管理费用\",\"n_code\":\"6602\",\"n_current_qmye\":\"6,076,252.04\",\"n_last_qmye\":\"6,015,768.14\",\"n_last_time_qmye\":\"6,015,768.14\",\"n_ord\":4},{\"n_before_last_qmye\":\"674,339.30\",\"n_cn_name\":\"财务费用\",\"n_code\":\"6603\",\"n_current_qmye\":\"-12,002.85\",\"n_last_qmye\":\"9,140.87\",\"n_last_time_qmye\":\"9,140.87\",\"n_ord\":5},{\"n_before_last_qmye\":\"2,107,019.96\",\"n_cn_name\":\"营业利润\",\"n_code\":\"6799\",\"n_current_qmye\":\"769,700.39\",\"n_last_qmye\":\"660,236.43\",\"n_last_time_qmye\":\"660,236.43\",\"n_ord\":6},{\"n_before_last_qmye\":\"0.00\",\"n_cn_name\":\"投资收益\",\"n_code\":\"6111\",\"n_current_qmye\":\"0.00\",\"n_last_qmye\":\"0.00\",\"n_last_time_qmye\":\"0.00\",\"n_ord\":7},{\"n_before_last_qmye\":\"2,204,114.42\",\"n_cn_name\":\"利润总额\",\"n_code\":\"6899\",\"n_current_qmye\":\"1,004,614.53\",\"n_last_qmye\":\"745,101.43\",\"n_last_time_qmye\":\"745,101.43\",\"n_ord\":8},{\"n_before_last_qmye\":\"1,645,122.94\",\"n_cn_name\":\"净利润\",\"n_code\":\"6999\",\"n_current_qmye\":\"951,634.58\",\"n_last_qmye\":\"471,245.29\",\"n_last_time_qmye\":\"471,245.29\",\"n_ord\":9}],\"o\":\"show\",\"oList\":[{\"o_before_last_index\":\"2.3621\",\"o_cn_name\":\"流动比率\",\"o_current_index\":\"2.0050\",\"o_last_index\":\"2.0262\",\"o_last_time_index\":\"2.0262\",\"o_ord\":1},{\"o_before_last_index\":\"2.0276\",\"o_cn_name\":\"速动比率\",\"o_current_index\":\"1.6512\",\"o_last_index\":\"1.6493\",\"o_last_time_index\":\"1.6493\",\"o_ord\":2},{\"o_before_last_index\":\"40.51%\",\"o_cn_name\":\"资产负债率\",\"o_current_index\":\"37.53%\",\"o_last_index\":\"36.42%\",\"o_last_time_index\":\"36.42%\",\"o_ord\":3},{\"o_before_last_index\":\"13.3815\",\"o_cn_name\":\"存货周转率\",\"o_current_index\":\"5.9168\",\"o_last_index\":\"6.9141\",\"o_last_time_index\":\"6.9141\",\"o_ord\":4},{\"o_before_last_index\":\"5.1965\",\"o_cn_name\":\"应收账款周转率\",\"o_current_index\":\"6.4010\",\"o_last_index\":\"4.5018\",\"o_last_time_index\":\"4.5018\",\"o_ord\":5},{\"o_before_last_index\":\"1.3451\",\"o_cn_name\":\"流动资产周转率\",\"o_current_index\":\"1.3163\",\"o_last_index\":\"1.3727\",\"o_last_time_index\":\"1.3727\",\"o_ord\":6},{\"o_before_last_index\":\"27.8329\",\"o_cn_name\":\"固定资产周转率\",\"o_current_index\":\"31.2832\",\"o_last_index\":\"30.8564\",\"o_last_time_index\":\"30.8564\",\"o_ord\":7},{\"o_before_last_index\":\"1.2831\",\"o_cn_name\":\"总资产周转率\",\"o_current_index\":\"0.9811\",\"o_last_index\":\"1.1484\",\"o_last_time_index\":\"1.1484\",\"o_ord\":8},{\"o_before_last_index\":\"19.70%\",\"o_cn_name\":\"毛利率\",\"o_current_index\":\"18.62%\",\"o_last_index\":\"17.84%\",\"o_last_time_index\":\"17.84%\",\"o_ord\":9},{\"o_before_last_index\":\"4.16%\",\"o_cn_name\":\"净利率\",\"o_current_index\":\"2.55%\",\"o_last_index\":\"1.22%\",\"o_last_time_index\":\"1.22%\",\"o_ord\":10},{\"o_before_last_index\":\"9.53%\",\"o_cn_name\":\"净资产收益率\",\"o_current_index\":\"3.97%\",\"o_last_index\":\"2.27%\",\"o_last_time_index\":\"2.27%\",\"o_ord\":11},{\"o_before_last_index\":\"5.33%\",\"o_cn_name\":\"总资产收益率\",\"o_current_index\":\"2.50%\",\"o_last_index\":\"1.40%\",\"o_last_time_index\":\"1.40%\",\"o_ord\":12}],\"qList\":[{\"q_beforelastyear\":\"15,496.40\",\"q_create_time\":\"2017-03-20\",\"q_curyear\":\"1,583.65\",\"q_is_share\":\"0\",\"q_lastyear\":\"30,377.20\",\"q_max_month\":\"1-2月\",\"q_zhh\":\"12032828100\"}],\"rList\":[],\"r_v_business_tax\":\"show\",\"r_v_corp_count\":\"show\",\"r_v_credit\":\"show\",\"r_v_credit_bscore\":48,\"r_v_credit_companyid\":\"2AEFC8FBA6157C52E053040812AC0DB0\",\"r_v_credit_corpname\":\"吴江市新金城不锈钢制品有限公司\",\"r_v_credit_creditrating\":\"一般\",\"r_v_credit_cscore\":100,\"r_v_credit_escore\":57,\"r_v_credit_fscore\":78,\"r_v_credit_industry\":\"C338\",\"r_v_credit_industry_level\":71,\"r_v_credit_mscore\":100,\"r_v_credit_oscore\":64,\"r_v_credit_ratingclass\":\"grade3\",\"r_v_credit_ratingdate\":\"2017年06月\",\"r_v_credit_region\":\"320509\",\"r_v_credit_region_level\":97,\"r_v_credit_rn\":1,\"r_v_credit_score_level\":70,\"r_v_credit_totalscore\":641,\"r_v_electric\":\"show\",\"r_v_income\":\"show\",\"r_v_loan\":\"show\",\"r_v_profit\":\"show\",\"report_number\":\"98000320171218100717786\",\"sList\":[],\"style\":\"201403\",\"styleType\":\"201403\",\"sxzx\":\"show\",\"sxzxList\":[],\"t\":\"show\",\"tList\":[],\"time_before_last_year_caiwu\":\"2014\",\"time_cfxx_create_time\":\"\",\"time_current_year\":\"2017\",\"time_current_year_caiwu\":\"2016\",\"time_dyxx_create_time\":\"\",\"time_hg_create_time\":\"2017-03-10\",\"time_last_year\":\"2016\",\"time_last_year_caiwu\":\"2015\",\"time_rzdb_create_time\":\"2017-03-24\",\"time_temp\":\"暂无数据\",\"time_template_type\":\"201401\",\"time_txxx_create_time\":\"\",\"time_xdxx_create_time\":\"\",\"time_xedk_create_time\":\"2017-04-16\",\"time_zcfzb_current_month\":\"12\",\"time_zl_create_time\":\"2017-04-10\",\"txxx\":\"show\",\"txxxList\":[],\"weg\":\"show\",\"xdxx\":\"show\",\"xdxxList\":[],\"z\":\"show\",\"zList\":[]}";
		ReportVO retvo = new ReportVO();
		if (jsonstr == null) {
			return null;
		} else {
			String jsonStatus = StringUtils.substringBefore(jsonstr, "|");
			System.out.println("信用报告返回的状态码:" + jsonStatus);
			if ("0".equals(jsonStatus)) {// 判断状态码 为0 返回空
				retvo.setExceptionMeessage(StringUtils.substringAfter(jsonstr,
						"|"));
				return retvo;
			}
		}
		String reportStr = "[" + StringUtils.substringAfter(jsonstr, "|") + "]";
//		 String reportStr=StringUtils.substringAfter(jsonstr, "|");
		JSONArray reportJson = new JSONArray();
		reportJson = JSONArray.fromObject(reportStr);
		System.out.println("解析调用信用报告返回的json:" + reportJson);
		Map<String, String> map = new HashMap<String, String>();
		JSONObject jo;
		for (int i = 0; i < reportJson.size(); i++) {
			jo = JSONObject.fromObject(reportJson.get(i));
			Iterator<?> it = jo.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				map.put(key, JsonUtil.getString(jo, key));
			}
		}
		retvo.setReport_number(getStrFMap(map, ReportConstant.REPORT_NUMBER));
		retvo.setCurr_time(getStrFMap(map, ReportConstant.E_CURR_TIME));
		retvo.setDepartment_name(getStrFMap(map,
				ReportConstant.E_DEPARTMENT_NAME));
		retvo.setReal_name(getStrFMap(map, ReportConstant.E_REAL_NAME));
		retvo.setArchive_date(getStrFMap(map, ReportConstant.E_ARCHIVE_DATE));
		retvo.setCorp_name(getStrFMap(map, ReportConstant.D2_CORP_NAME));
		// 地址
		String d2_dom = getStrFMap(map, ReportConstant.D2_DOM);
		String time_jgdz = getStrFMap(map, ReportConstant.TIME_JGDZ);
		if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_dom))
			retvo.setAddr_all(getStrFMap(map, ReportConstant.D2_DOM));
		else
			retvo.setAddr_all(getStrFMap(map, ReportConstant.TIME_JGDZ));
		// 机构代码
		String d2_credit_code = getStrFMap(map, ReportConstant.D2_CREDIT_CODE);
		String d2_corp_code = getStrFMap(map, ReportConstant.D2_CORP_CODE);
		String d2_ic_num = getStrFMap(map, ReportConstant.D2_IS_NUM);
		if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_credit_code)) {
			retvo.setOrg_code(d2_credit_code);
		} else if (EmptyConditionUtils
				.notEmptyStringSpaceNoMeaning(d2_corp_code)) {
			retvo.setOrg_code(d2_corp_code);
		} else if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_ic_num)) {
			retvo.setOrg_code(d2_ic_num);
		}
		// 注册资本
		retvo.setRegcap(getStrFMap(map, ReportConstant.D2_REGCAP));
		// 法定代表人
		String d2_legal_per = getStrFMap(map, ReportConstant.D2_LEGAL_PER);
		String d2_legal_name = getStrFMap(map, ReportConstant.D2_LEGAL_NAME);
		if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_legal_per)) {
			retvo.setLegal_per(d2_legal_per);
		} else {
			retvo.setLegal_per(d2_legal_name);
		}
		// 企业联系电话
		String d2_tel = getStrFMap(map, ReportConstant.D2_TEL);
		String d2_legal_phone = getStrFMap(map, ReportConstant.D2_LEGAL_PHONE);
		if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_tel)) {
			retvo.setTel(d2_tel);
		} else {
			retvo.setTel(d2_legal_phone);
		}

		// 类型
		retvo.setEnttype(getStrFMap(map, ReportConstant.D2_ENTTYPE));
		// 经营状态
		retvo.setEntstatus(getStrFMap(map, ReportConstant.D2_ENTSTATUS));
		// 成立日期
		retvo.setEsdate(getStrFMap(map, ReportConstant.D2_ESDATE));
		// 营业期限
		retvo.setOp(getStrFMap(map, ReportConstant.D2_OP));
		// 行业分类
		retvo.setIndustryco(getStrFMap(map, ReportConstant.D2_INDUSTRYCO));
		// 公司人数
		retvo.setKjsbrs(getStrFMap(map, ReportConstant.D2_KJSBRS));
		// 经营范围
		String d2_opscope = getStrFMap(map, ReportConstant.D2_OPSCOPE);
		String d2_abuitem = getStrFMap(map, ReportConstant.D2_ADBUITEM);
		String d2_cbuitem = getStrFMap(map, ReportConstant.D2_CBUITEM);
		if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_opscope)) {
			retvo.setOpscope(d2_opscope);
		} else {
			if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_abuitem)) {
				d2_abuitem = "许可经营项目：无";
			} else {
				d2_abuitem = "许可经营项目：" + d2_abuitem;
			}
			if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(d2_cbuitem)) {
				d2_cbuitem = "一般经营项目：无";
			} else {
				d2_cbuitem = "一般经营项目：" + d2_cbuitem;
			}
			retvo.setOpscope(d2_abuitem + "\r\n\n" + d2_cbuitem);
		}

		retvo.setGdxxlist(getb2List(getStrFMap(map, ReportConstant.B2LIST.TYPE)));
		retvo.setGgxxlist(getc2List(getStrFMap(map, ReportConstant.C2LIST.TYPE)));
		// 社保公积金缴存信息
		retvo.setSbgjjlist(getbList(getStrFMap(map, ReportConstant.BLIST.TYPE)));

		retvo.setZllist(getJList(getStrFMap(map, ReportConstant.JLIST.TYPE)));
		retvo.setPjlist(getKList(getStrFMap(map, ReportConstant.KLIST.TYPE)));
		// 资产负债主要信息
		retvo.setSzcfzlist(getZcfz(getStrFMap(map, ReportConstant.CList.TYPE)));
		// 利润表主要信息
		retvo.setLrlist(getLirun(getStrFMap(map, ReportConstant.NList.TYPE)));
		// 主要财务指标信息
		retvo.setCwzbVo(getCW(map));
		// 纳税信息
		retvo.setNsxxVo(getNSXX(map));
		// 海关进出口信息
		retvo.setHgjckxxVo(getHGJCK(map));
		// 水电气缴费信息
		retvo.setSdqxxVo(getSDQ(map));
		// 非银金融机构融资信息
		retvo.setWjqxelist(getMCList(getStrFMap(map, ReportConstant.MCLIST.TYPE)));
		retvo.setWjbrzlist(getTList(getStrFMap(map, ReportConstant.TLIST.TYPE)));
		/******************************** 2017-05-26 ***************************************************************/
		retvo.setWjqxdlist(getXDXXList(getStrFMap(map,
				ReportConstant.XDXXLIST.TYPE)));
		retvo.setTxlist(getTXXXList(getStrFMap(map,
				ReportConstant.TXXXLIST.TYPE)));

		// 抵押与查封信息
		// retvo.setFcdylist(getUList(getStrFMap(map,ReportConstant.ULIST.TYPE)));
		// retvo.setTddylist(getVList(getStrFMap(map,ReportConstant.VLIST.TYPE)));
		// retvo.setFccflist(getFccfList(getStrFMap(map,ReportConstant.FccfLIST.TYPE)));
		// retvo.setTdcflist(getTdcfList(getStrFMap(map,ReportConstant.TdcfLIST.TYPE)));
		retvo.setFcdylist(getDyxxList(getStrFMap(map,
				ReportConstant.DYXXLIST.TYPE)));
		retvo.setFccflist(getCfxxList(getStrFMap(map,
				ReportConstant.CFXXLIST.TYPE)));

		// 涉诉信息
		retvo.setLaxxlist(getLaxxList(getStrFMap(map,
				ReportConstant.LaxxLIST.TYPE)));
		retvo.setJaxxlist(getJaxxList(getStrFMap(map,
				ReportConstant.JaxxLIST.TYPE)));
		retvo.setSxbzxrlist(getSxzxList(getStrFMap(map,
				ReportConstant.SxzxLIST.TYPE)));
		// 负面信息
		retvo.setQjyqlist(getHList(getStrFMap(map, ReportConstant.HLIST.TYPE)));
		retvo.setCfxxlist(getIList(getStrFMap(map, ReportConstant.ILIST.TYPE)));
		retvo.setAqscwwclist(getGpdbList(getStrFMap(map,
				ReportConstant.GPDBLIST.TYPE)));
		retvo.setSwfzclist(getMList(getStrFMap(map, ReportConstant.MLIST.TYPE)));
		retvo.setQhgsxqylist(getZList(getStrFMap(map, ReportConstant.ZLIST.TYPE)));
		retvo.setHbfmlist(getK2List(getStrFMap(map, ReportConstant.K2LIST.TYPE)));
		retvo.setCpcjlist(getA2List(getStrFMap(map, ReportConstant.A2LIST.TYPE)));
		retvo.setQtfmlist(getK3List(getStrFMap(map, ReportConstant.K3LIST.TYPE)));

		/**
		 * 采集截至日期
		 */
		retvo.setTime_fccf_create_time(getStrFMap(map,
				ReportConstant.TIME_FCCF_CREATE_TIME));
		retvo.setTime_fdc_create_time(getStrFMap(map,
				ReportConstant.TIME_FDC_CREATE_TIME));
		retvo.setTime_hg_create_time(getStrFMap(map,
				ReportConstant.TIME_HG_CREATE_TIME));
		retvo.setTime_rzdb_create_time(getStrFMap(map,
				ReportConstant.TIME_RZDB_CREATE_TIME));
		retvo.setTime_tdcf_create_time(getStrFMap(map,
				ReportConstant.TIME_TDCF_CREATE_TIME));
		retvo.setTime_tddy_create_time(getStrFMap(map,
				ReportConstant.TIME_TDDY_CREATE_TIME));
		retvo.setTime_xedk_create_time(getStrFMap(map,
				ReportConstant.TIME_XEDK_CREATE_TIME));
		retvo.setTime_zl_create_time(getStrFMap(map,
				ReportConstant.TIME_ZL_CREATE_TIME));

		/***
		 * 附：重要指标历史趋势图
		 */
		retvo.setData_yysr_cate(getStrFMap(map,
				ReportConstant.TB.DATA_YYSR_CATE));
		retvo.setData_yysr_sery(getStrFMap(map,
				ReportConstant.TB.DATA_YYSR_SERY));

		retvo.setData_jlr_cate(getStrFMap(map, ReportConstant.TB.DATA_JLR_CATE));
		retvo.setData_jlr_sery(getStrFMap(map, ReportConstant.TB.DATA_JLR_SERY));

		retvo.setData_zz_ye_cate(getStrFMap(map,
				ReportConstant.TB.DATA_ZZ_YE_CATE));
		retvo.setData_zz_ye_sery(getStrFMap(map,
				ReportConstant.TB.DATA_ZZ_YE_SERY));

		retvo.setData_gsrs_cate(getStrFMap(map,
				ReportConstant.TB.DATA_GSRS_CATE));
		retvo.setData_gsrs_sery(getStrFMap(map,
				ReportConstant.TB.DATA_GSRS_SERY));

		retvo.setData_df_cate(getStrFMap(map, ReportConstant.TB.DATA_DF_CATE));
		retvo.setData_df_sery(getStrFMap(map, ReportConstant.TB.DATA_DF_SERY));

		retvo.setData_xdrzye_cate(getStrFMap(map,
				ReportConstant.TB.DATA_XDRZYE_CATE));
		retvo.setData_xdrzye_sery(getStrFMap(map,
				ReportConstant.TB.DATA_XDRZYE_SERY));
		return retvo;
	}

	public String getResult(String corpCode) {
		String result = null;
		System.out.println("调用信用报告接口:" + url);
		WebClient client = WebClient.create(url);
		ClientConfiguration config = WebClient.getConfig(client);
		config.getHttpConduit().getClient()
				.setConnectionTimeout(connectionTimeout);// 连接超时
		config.getHttpConduit().getClient().setReceiveTimeout(receiveTimeout); // 响应超时
		client.path("wsWebService/getReportInfo")
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON);
		System.out.println("调用信用报告接口参数(加密前):" + "name:" + reporUser
				+ ";corpCode;" + corpCode + ";reason:" + reason);
		String e_userName = AESUtil.encrypt(reporUser, key);
		String e_corpCode = AESUtil.encrypt(corpCode, key);
		String e_reason = AESUtil.encrypt(reason, key);
		System.out.println("调用信用报告接口参数(加密后):" + "e_userName:" + e_userName
				+ ";e_corpCode;" + e_corpCode + ";e_reason:" + e_reason);
		client.replaceQueryParam("userName", e_userName)
				.replaceQueryParam("corpCode", e_corpCode)
				.replaceQueryParam("reason", e_reason);
		result = AESUtil.decrypt(client.get(String.class), key);
		System.out.println("调用信用报告接口结束，返回结果:" + result);
		return result;
	}

	public List<OperateInfoVo> getZcfz(String jsonarray) {
		List<OperateInfoVo> retList = new ArrayList<OperateInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		OperateInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new OperateInfoVo();
			vo.setIndex(JsonUtil.getString(jo, ReportConstant.CList.C_ORD) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.CList.C_ORD));
			vo.setCn_name(JsonUtil
					.getString(jo, ReportConstant.CList.C_CN_NAME) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.CList.C_CN_NAME));
			vo.setBefore_last_qmye(JsonUtil.getString(jo,
					ReportConstant.CList.C_BEFORE_LAST_QMYE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CList.C_BEFORE_LAST_QMYE));
			vo.setLast_qmye(JsonUtil.getString(jo,
					ReportConstant.CList.C_LAST_QMYE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.CList.C_LAST_QMYE));
			vo.setCurrent_qmye(JsonUtil.getString(jo,
					ReportConstant.CList.C_CURRENT_QMYE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CList.C_CURRENT_QMYE));
			vo.setLast_time_qmye(JsonUtil.getString(jo,
					ReportConstant.CList.C_LAST_TIME_QMYE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CList.C_LAST_TIME_QMYE));
			retList.add(vo);
		}
		return retList;
	}

	public List<OperateInfoVo> getLirun(String jsonarray) {
		List<OperateInfoVo> retList = new ArrayList<OperateInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		OperateInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new OperateInfoVo();
			vo.setIndex(JsonUtil.getString(jo, ReportConstant.NList.N_ORD) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.NList.N_ORD));
			vo.setCn_name(JsonUtil
					.getString(jo, ReportConstant.NList.N_CN_NAME) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.NList.N_CN_NAME));
			vo.setBefore_last_qmye(JsonUtil.getString(jo,
					ReportConstant.NList.N_BEFORE_LAST_QMYE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.NList.N_BEFORE_LAST_QMYE));
			vo.setLast_qmye(JsonUtil.getString(jo,
					ReportConstant.NList.N_LAST_QMYE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.NList.N_LAST_QMYE));
			vo.setCurrent_qmye(JsonUtil.getString(jo,
					ReportConstant.NList.N_CURRENT_QMYE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.NList.N_CURRENT_QMYE));
			vo.setLast_time_qmye(JsonUtil.getString(jo,
					ReportConstant.NList.N_LAST_TIME_QMYE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.NList.N_LAST_TIME_QMYE));
			retList.add(vo);
		}
		return retList;
	}

	public ColumnAndListVO getZCFZ(Map<String, String> map) {
		ColumnAndListVO retvo = new ColumnAndListVO();
		String time_current_year_caiwu = getStrFMap(map,
				ReportConstant.TIME_CURRENT_YEAR_CAIWU);
		// 注意，如果time_current_year_caiwu为空，则显示“系统中不存在该企业近三年的主要财务指标信息。”
		if (!EmptyConditionUtils
				.notEmptyStringSpaceNoMeaning(time_current_year_caiwu))
			return retvo;
		String time_before_last_year_caiwu = getStrFMap(map,
				ReportConstant.TIME_BEFORE_LAST_YEAR_CAIWU);
		String time_last_year_caiwu = getStrFMap(map,
				ReportConstant.TIME_LAST_YEAR_CAIWU);
		String time_zcfzb_current_month = getStrFMap(map,
				ReportConstant.TIME_ZCFZB_CURRENT_MONTH);
		retvo.setCwzbvo(getCWTYPE1(map, time_current_year_caiwu));
		retvo.setBefore_last_year(time_before_last_year_caiwu);
		retvo.setLast_year(time_last_year_caiwu);
		retvo.setCurrent_year(time_current_year_caiwu);
		retvo.setG_current_max_month_1(time_zcfzb_current_month);
		return retvo;
	}

	public ColumnAndListVO getCW(Map<String, String> map) {
		ColumnAndListVO retvo = new ColumnAndListVO();
		String time_current_year_caiwu = getStrFMap(map,
				ReportConstant.TIME_CURRENT_YEAR_CAIWU);
		// 注意，如果time_current_year_caiwu为空，则显示“系统中不存在该企业近三年的主要财务指标信息。”
		if (!EmptyConditionUtils
				.notEmptyStringSpaceNoMeaning(time_current_year_caiwu))
			return retvo;
		String time_before_last_year_caiwu = getStrFMap(map,
				ReportConstant.TIME_BEFORE_LAST_YEAR_CAIWU);
		String time_last_year_caiwu = getStrFMap(map,
				ReportConstant.TIME_LAST_YEAR_CAIWU);
		String time_zcfzb_current_month = getStrFMap(map,
				ReportConstant.TIME_ZCFZB_CURRENT_MONTH);
		String style_type = getStrFMap(map, ReportConstant.STYLETYPE);
		retvo.setCwzbvo(getCWTYPE1(map, style_type));
		retvo.setBefore_last_year(time_before_last_year_caiwu);
		retvo.setLast_year(time_last_year_caiwu);
		retvo.setCurrent_year(time_current_year_caiwu);
		retvo.setG_current_max_month_1(time_zcfzb_current_month);
		return retvo;

	}

	public List<CWZBVo> getCWOther(Map<String, String> map) {
		List<CWZBVo> retlist = new ArrayList<CWZBVo>();
		int index = 1;
		retlist.add(new CWZBVo(index + "", "流动比率", getStrFMap(map,
				ReportConstant.CW.O_CURR_RATIO_0), getStrFMap(map,
				ReportConstant.CW.O_CURR_RATIO_1), getStrFMap(map,
				ReportConstant.CW.O_CURR_RATIO_2), getStrFMap(map,
				ReportConstant.CW.O_CURR_RATIO_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "速动比率", getStrFMap(map,
				ReportConstant.CW.O_LIQUI_RATIO_0), getStrFMap(map,
				ReportConstant.CW.O_LIQUI_RATIO_1), getStrFMap(map,
				ReportConstant.CW.O_LIQUI_RATIO_2), getStrFMap(map,
				ReportConstant.CW.O_LIQUI_RATIO_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "资产负债率", getStrFMap(map,
				ReportConstant.CW.O_ASSENT_LIAB_RATIO_0), getStrFMap(map,
				ReportConstant.CW.O_ASSENT_LIAB_RATIO_1), getStrFMap(map,
				ReportConstant.CW.O_ASSENT_LIAB_RATIO_2), getStrFMap(map,
				ReportConstant.CW.O_ASSENT_LIAB_RATIO_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "存货周转率", getStrFMap(map,
				ReportConstant.CW.O_INVEN_TURN_RATIO_0), getStrFMap(map,
				ReportConstant.CW.O_INVEN_TURN_RATIO_1), getStrFMap(map,
				ReportConstant.CW.O_INVEN_TURN_RATIO_2), getStrFMap(map,
				ReportConstant.CW.O_INVEN_TURN_RATIO_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "应收账款周转率", getStrFMap(map,
				ReportConstant.CW.O_ACCOU_RECE_TURN_0), getStrFMap(map,
				ReportConstant.CW.O_ACCOU_RECE_TURN_1), getStrFMap(map,
				ReportConstant.CW.O_ACCOU_RECE_TURN_2), getStrFMap(map,
				ReportConstant.CW.O_ACCOU_RECE_TURN_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "流动资产周转率", getStrFMap(map,
				ReportConstant.CW.O_CURR_ASS_TURN_0), getStrFMap(map,
				ReportConstant.CW.O_CURR_ASS_TURN_1), getStrFMap(map,
				ReportConstant.CW.O_CURR_ASS_TURN_2), getStrFMap(map,
				ReportConstant.CW.O_CURR_ASS_TURN_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "固定资产周转率", getStrFMap(map,
				ReportConstant.CW.O_FIXED_ASS_TURN_0), getStrFMap(map,
				ReportConstant.CW.O_FIXED_ASS_TURN_1), getStrFMap(map,
				ReportConstant.CW.O_FIXED_ASS_TURN_2), getStrFMap(map,
				ReportConstant.CW.O_FIXED_ASS_TURN_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "总资产周转率", getStrFMap(map,
				ReportConstant.CW.O_TOTAL_ASS_TURN_0), getStrFMap(map,
				ReportConstant.CW.O_TOTAL_ASS_TURN_1), getStrFMap(map,
				ReportConstant.CW.O_TOTAL_ASS_TURN_2), getStrFMap(map,
				ReportConstant.CW.O_TOTAL_ASS_TURN_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "毛利率", getStrFMap(map,
				ReportConstant.CW.o_rate_margin_0), getStrFMap(map,
				ReportConstant.CW.o_rate_margin_1), getStrFMap(map,
				ReportConstant.CW.o_rate_margin_2), getStrFMap(map,
				ReportConstant.CW.o_rate_margin_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "净利率", getStrFMap(map,
				ReportConstant.CW.O_NET_PROFIT_MARGIN_0), getStrFMap(map,
				ReportConstant.CW.O_NET_PROFIT_MARGIN_1), getStrFMap(map,
				ReportConstant.CW.O_NET_PROFIT_MARGIN_2), getStrFMap(map,
				ReportConstant.CW.O_NET_PROFIT_MARGIN_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "净资产收益率", getStrFMap(map,
				ReportConstant.CW.O_NET_ASS_REWARD_0), getStrFMap(map,
				ReportConstant.CW.O_NET_ASS_REWARD_1), getStrFMap(map,
				ReportConstant.CW.O_NET_ASS_REWARD_2), getStrFMap(map,
				ReportConstant.CW.O_NET_ASS_REWARD_3)));
		index++;
		retlist.add(new CWZBVo(index + "", "总资产收益率", getStrFMap(map,
				ReportConstant.CW.O_TOTAL_ASS_REWARD_0), getStrFMap(map,
				ReportConstant.CW.O_TOTAL_ASS_REWARD_1), getStrFMap(map,
				ReportConstant.CW.O_TOTAL_ASS_REWARD_2), getStrFMap(map,
				ReportConstant.CW.O_TOTAL_ASS_REWARD_3)));
		index++;
		return retlist;
	}

	public List<CWZBVo> getCWTYPE1(Map<String, String> map,
			String time_current_year_caiwu) {
		// if(!time_current_year_caiwu.equals("201404")&&!time_current_year_caiwu.equals("201405")&&!time_current_year_caiwu.equals("201406")&&!time_current_year_caiwu.equals("201407")){
		// 主要财务指标信息
		String oList = map.get("oList");
		JSONArray reportJson = new JSONArray();
		reportJson = JSONArray.fromObject(oList);
		List<CWZBVo> retlist = new ArrayList<CWZBVo>();
		JSONObject jo;
		int index = 1;
		for (int i = 0; i < reportJson.size(); i++) {
			jo = JSONObject.fromObject(reportJson.get(i));
			Iterator<?> it = jo.keys();
			Map<String, String> oMap = new HashMap<String, String>();
			while (it.hasNext()) {// 循环获取主要财务指标信息每项内容
				String key = it.next().toString();
				oMap.put(key, JsonUtil.getString(jo, key));
			}
			// 财务指标信息每项内容 放到list
			retlist.add(new CWZBVo(index + "", getStrFMap(oMap, "o_cn_name"),
					getStrFMap(oMap, "o_before_last_index"), getStrFMap(oMap,
							"o_last_time_index"), getStrFMap(oMap,
							"o_last_index"),
					getStrFMap(oMap, "o_current_index")));
			index++;
		}
		// return getCWOther(map);
		// }
		// List<CWZBVo> retlist = new ArrayList<CWZBVo>();
		// int index=1;
		//
		// retlist.add(new
		// CWZBVo(index+"","资产负债率",getStrFMap(map,ReportConstant.CW.O_ASSENT_LIAB_RATIO_0),getStrFMap(map,ReportConstant.CW.O_ASSENT_LIAB_RATIO_1),getStrFMap(map,ReportConstant.CW.O_ASSENT_LIAB_RATIO_2),getStrFMap(map,ReportConstant.CW.O_ASSENT_LIAB_RATIO_3)));
		// index++;
		// if(time_current_year_caiwu.equals("201407")){
		//
		// retlist.add(new
		// CWZBVo(index+"","拨备覆盖率",getStrFMap(map,ReportConstant.CW.O_PROVISION_COVERAGE_RATIO_0),getStrFMap(map,ReportConstant.CW.O_PROVISION_COVERAGE_RATIO_1),getStrFMap(map,ReportConstant.CW.O_PROVISION_COVERAGE_RATIO_2),getStrFMap(map,ReportConstant.CW.O_PROVISION_COVERAGE_RATIO_3)));
		// index++;
		// }
		// retlist.add(new
		// CWZBVo(index+"","总资产周转率",getStrFMap(map,ReportConstant.CW.O_TOTAL_ASS_TURN_0),getStrFMap(map,ReportConstant.CW.O_TOTAL_ASS_TURN_1),getStrFMap(map,ReportConstant.CW.O_TOTAL_ASS_TURN_2),getStrFMap(map,ReportConstant.CW.O_TOTAL_ASS_TURN_3)));
		// index++;
		// retlist.add(new
		// CWZBVo(index+"","净利率",getStrFMap(map,ReportConstant.CW.O_NET_PROFIT_MARGIN_0),getStrFMap(map,ReportConstant.CW.O_NET_PROFIT_MARGIN_1),getStrFMap(map,ReportConstant.CW.O_NET_PROFIT_MARGIN_2),getStrFMap(map,ReportConstant.CW.O_NET_PROFIT_MARGIN_3)));
		// index++;
		// retlist.add(new
		// CWZBVo(index+"","净资产收益率",getStrFMap(map,ReportConstant.CW.O_NET_ASS_REWARD_0),getStrFMap(map,ReportConstant.CW.O_NET_ASS_REWARD_1),getStrFMap(map,ReportConstant.CW.O_NET_ASS_REWARD_2),getStrFMap(map,ReportConstant.CW.O_NET_ASS_REWARD_3)));
		// index++;
		// retlist.add(new
		// CWZBVo(index+"","总资产收益率",getStrFMap(map,ReportConstant.CW.O_TOTAL_ASS_REWARD_0),getStrFMap(map,ReportConstant.CW.O_TOTAL_ASS_REWARD_1),getStrFMap(map,ReportConstant.CW.O_TOTAL_ASS_REWARD_2),getStrFMap(map,ReportConstant.CW.O_TOTAL_ASS_REWARD_3)));
		// index++;
		// retlist.add(new
		// CWZBVo(index+"","成本收入比",getStrFMap(map,ReportConstant.CW.O_TOTAL_COST_INCOME_COMPARE_0),getStrFMap(map,ReportConstant.CW.O_TOTAL_COST_INCOME_COMPARE_1),getStrFMap(map,ReportConstant.CW.O_TOTAL_COST_INCOME_COMPARE_2),getStrFMap(map,ReportConstant.CW.O_TOTAL_COST_INCOME_COMPARE_3)));
		// index++;
		// retlist.add(new
		// CWZBVo(index+"","综合赔付率",getStrFMap(map,ReportConstant.CW.O_GENERAL_LOSS_RATIO_0),getStrFMap(map,ReportConstant.CW.O_GENERAL_LOSS_RATIO_1),getStrFMap(map,ReportConstant.CW.O_GENERAL_LOSS_RATIO_2),getStrFMap(map,ReportConstant.CW.O_GENERAL_LOSS_RATIO_3)));
		// index++;
		//
		// if(time_current_year_caiwu.equals("201404")||time_current_year_caiwu.equals("201405")||time_current_year_caiwu.equals("201407")){
		//
		// retlist.add(new
		// CWZBVo(index+"","成本收入比",getStrFMap(map,ReportConstant.CW.O_TOTAL_COST_INCOME_COMPARE_0),getStrFMap(map,ReportConstant.CW.O_TOTAL_COST_INCOME_COMPARE_1),getStrFMap(map,ReportConstant.CW.O_TOTAL_COST_INCOME_COMPARE_2),getStrFMap(map,ReportConstant.CW.O_TOTAL_COST_INCOME_COMPARE_3)));
		// index++;
		// }
		// if(time_current_year_caiwu.equals("201406")){
		// retlist.add(new
		// CWZBVo(index+"","综合赔付率",getStrFMap(map,ReportConstant.CW.O_GENERAL_LOSS_RATIO_0),getStrFMap(map,ReportConstant.CW.O_GENERAL_LOSS_RATIO_1),getStrFMap(map,ReportConstant.CW.O_GENERAL_LOSS_RATIO_2),getStrFMap(map,ReportConstant.CW.O_GENERAL_LOSS_RATIO_3)));
		// index++;
		// }
		return retlist;
	}

	public ColumnAndListVO getNSXX(Map<String, String> map) {
		ColumnAndListVO retvo = new ColumnAndListVO();
		String before_last_year_tax = getStrFMap(map,
				ReportConstant.BEFORE_LAST_YEAR_TAX);
		String last_year_tax = getStrFMap(map, ReportConstant.LAST_YEAR_TAX);
		String current_year_tax = getStrFMap(map,
				ReportConstant.CURRENT_YEAR_TAX);
		retvo.setBefore_last_year(before_last_year_tax);
		retvo.setLast_year(last_year_tax);
		retvo.setCurrent_year(current_year_tax);
		retvo.setNsvo(getFList(getStrFMap(map, ReportConstant.FLIST.TYPE)));
		return retvo;
	}

	public List<NSVo> getFList(String jsonarray) {
		List<NSVo> retList = new ArrayList<NSVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		NSVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new NSVo();
			vo.setBeforelastyear(JsonUtil.getString(jo,
					ReportConstant.FLIST.F_BEFORELASTYEAR) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.FLIST.F_BEFORELASTYEAR));
			vo.setCuryear(JsonUtil
					.getString(jo, ReportConstant.FLIST.F_CURYEAR) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.FLIST.F_CURYEAR));
			vo.setLastyear(JsonUtil.getString(jo,
					ReportConstant.FLIST.F_LASTYEAR) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.FLIST.F_LASTYEAR));
			vo.setMax_month(JsonUtil.getString(jo,
					ReportConstant.FLIST.F_MAX_MONTH) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.FLIST.F_MAX_MONTH));
			vo.setShuiz(JsonUtil.getString(jo, ReportConstant.FLIST.F_SHUIZ) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.FLIST.F_SHUIZ));
			retList.add(vo);
		}
		return retList;
	}

	public boolean isString0(String str) {
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(str)
				&& !str.equals("0"))
			return false;
		else
			return true;
	}

	public ColumnAndListVO getHGJCK(Map<String, String> map) {
		ColumnAndListVO retvo = new ColumnAndListVO();

		String g_import_quota_total_0 = getStrFMap(map,
				ReportConstant.G_IMPORT_QUITA_TOTAL_0);
		String g_import_quota_total_1 = getStrFMap(map,
				ReportConstant.G_IMPORT_QUITA_TOTAL_1);
		String g_import_quota_total_2 = getStrFMap(map,
				ReportConstant.G_IMPORT_QUITA_TOTAL_2);
		String g_export_quota_total_0 = getStrFMap(map,
				ReportConstant.G_EXPORT_QUITA_TOTAL_0);
		String g_export_quota_total_1 = getStrFMap(map,
				ReportConstant.G_EXPORT_QUITA_TOTAL_1);
		String g_export_quota_total_2 = getStrFMap(map,
				ReportConstant.G_EXPORT_QUITA_TOTAL_2);

		String before_last_year_hg = getStrFMap(map,
				ReportConstant.BEFORE_LAST_YEAR_HG);
		String last_year_hg = getStrFMap(map, ReportConstant.LAST_YEAR_HG);
		String current_year_hg = getStrFMap(map, ReportConstant.CURRENT_YEAR_HG);
		String g_current_max_month_1 = getStrFMap(map,
				ReportConstant.G_CURRENT_MAX_MONTH_1);

		String time_hg_create_time = getStrFMap(map,
				ReportConstant.TIME_HG_CREATE_TIME);

		List<HGVo> retList = new ArrayList<HGVo>();

		if (!isString0(g_import_quota_total_0)
				|| !isString0(g_import_quota_total_1)
				|| !isString0(g_import_quota_total_2)
				|| !isString0(g_export_quota_total_0)
				|| !isString0(g_export_quota_total_1)
				|| !isString0(g_export_quota_total_2)) {
			return retvo;
		}
		HGVo jinvo = new HGVo();
		jinvo.setBeforelastyear(g_import_quota_total_0);
		jinvo.setCuryear(g_import_quota_total_2);
		jinvo.setLastyear(g_import_quota_total_1);
		jinvo.setTypeStr("进口");
		retList.add(jinvo);
		HGVo chuvo = new HGVo();
		chuvo.setBeforelastyear(g_export_quota_total_0);
		chuvo.setCuryear(g_export_quota_total_2);
		chuvo.setLastyear(g_export_quota_total_1);
		chuvo.setTypeStr("出口");
		retList.add(chuvo);

		retvo.setBefore_last_year(before_last_year_hg);
		retvo.setLast_year(last_year_hg);
		retvo.setCurrent_year(current_year_hg);
		retvo.setG_current_max_month_1(g_current_max_month_1);
		retvo.setHgjck(retList);
		return retvo;
	}

	/****************** 2017-05-25 ***************/
	public ColumnAndListVO getSDQ(Map<String, String> map) {
		ColumnAndListVO retvo = new ColumnAndListVO();
		String qList = getStrFMap(map, ReportConstant.SDQ.TYPE_Q);
		String rList = getStrFMap(map, ReportConstant.SDQ.TYPE_R);
		String sList = getStrFMap(map, ReportConstant.SDQ.TYPE_S);
		if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(qList)
				|| EmptyConditionUtils.notEmptyStringSpaceNoMeaning(rList)
				|| EmptyConditionUtils.notEmptyStringSpaceNoMeaning(sList)) {
			String before_last_year_weg = getStrFMap(map,
					ReportConstant.SDQ.BEFORE_LAST_YEAR_WEG);
			String last_year_weg = getStrFMap(map,
					ReportConstant.SDQ.LAST_YEAR_WEG);
			String current_year_weg = getStrFMap(map,
					ReportConstant.SDQ.CURRENT_YEAR_WEG);
			retvo.setBefore_last_year(before_last_year_weg);
			retvo.setLast_year(last_year_weg);
			retvo.setCurrent_year(current_year_weg);
			List<SDQVo> retList = null;
			SDQVo sdqvo = null;
			new JSONArray();
			// 水
			JSONArray qJsonarray = JSONArray.fromObject(qList);
			if (qJsonarray != null && qJsonarray.size() > 0) {
				retList = new ArrayList<SDQVo>();
				for (int i = 0; i < qJsonarray.size(); i++) {
					sdqvo = new SDQVo();
					JSONObject jo = JSONObject.fromObject(qJsonarray.get(i));
					sdqvo.setZhhStr(JsonUtil.getString(jo,
							ReportConstant.SDQ.Q_ZHH));
					sdqvo.setMax_month(JsonUtil.getString(jo,
							ReportConstant.SDQ.Q_MAX_MONTH));
					sdqvo.setBeforelastyear(JsonUtil.getString(jo,
							ReportConstant.SDQ.Q_BEFORELASTYEAR));
					sdqvo.setLastyear(JsonUtil.getString(jo,
							ReportConstant.SDQ.Q_LASTYEAR));
					sdqvo.setCuryear(JsonUtil.getString(jo,
							ReportConstant.SDQ.Q_CURYEAR));
					sdqvo.setIsShare(JsonUtil.getString(jo,
							ReportConstant.SDQ.Q_IS_SHARE));
					sdqvo.setTypeStr("水");
					retList.add(sdqvo);
				}
				retvo.setSvo(retList);
			}
			new JSONArray();
			// 电
			JSONArray rJsonarray = JSONArray.fromObject(rList);
			if (rJsonarray != null && rJsonarray.size() > 0) {
				retList = new ArrayList<SDQVo>();
				for (int i = 0; i < rJsonarray.size(); i++) {
					sdqvo = new SDQVo();
					JSONObject jo = JSONObject.fromObject(rJsonarray.get(0));
					sdqvo.setZhhStr(JsonUtil.getString(jo,
							ReportConstant.SDQ.R_ZHH));
					sdqvo.setMax_month(JsonUtil.getString(jo,
							ReportConstant.SDQ.R_MAX_MONTH));
					sdqvo.setBeforelastyear(JsonUtil.getString(jo,
							ReportConstant.SDQ.R_BEFORELASTYEAR));
					sdqvo.setLastyear(JsonUtil.getString(jo,
							ReportConstant.SDQ.R_LASTYEAR));
					sdqvo.setCuryear(JsonUtil.getString(jo,
							ReportConstant.SDQ.R_CURYEAR));
					sdqvo.setIsShare(JsonUtil.getString(jo,
							ReportConstant.SDQ.R_IS_SHARE));
					sdqvo.setTypeStr("电");
					retList.add(sdqvo);
				}
				retvo.setDvo(retList);
			}
			new JSONArray();
			// 气
			JSONArray sJsonarray = JSONArray.fromObject(sList);
			if (sJsonarray != null && sJsonarray.size() > 0) {
				retList = new ArrayList<SDQVo>();
				for (int i = 0; i < sJsonarray.size(); i++) {
					sdqvo = new SDQVo();
					JSONObject jo = JSONObject.fromObject(sJsonarray.get(0));
					sdqvo.setZhhStr(JsonUtil.getString(jo,
							ReportConstant.SDQ.S_ZHH));
					sdqvo.setMax_month(JsonUtil.getString(jo,
							ReportConstant.SDQ.S_MAX_MONTH));
					sdqvo.setBeforelastyear(JsonUtil.getString(jo,
							ReportConstant.SDQ.S_BEFORELASTYEAR));
					sdqvo.setLastyear(JsonUtil.getString(jo,
							ReportConstant.SDQ.S_LASTYEAR));
					sdqvo.setCuryear(JsonUtil.getString(jo,
							ReportConstant.SDQ.S_CURYEAR));
					sdqvo.setIsShare(JsonUtil.getString(jo,
							ReportConstant.SDQ.S_IS_SHARE));
					sdqvo.setTypeStr("气");
					retList.add(sdqvo);
				}
				retvo.setQvo(retList);
			}
		}
		return retvo;
	}

	public String getStrFMap(Map<String, String> map, String key) {
		if (map == null)
			return "";
		Object _o = map.get(key);

		if (_o != null)
			return _o.toString();
		else
			return "";
	}

	public List<ShareholderInfoVo> getb2List(String jsonarray) {
		List<ShareholderInfoVo> retList = new ArrayList<ShareholderInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		ShareholderInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new ShareholderInfoVo();
			vo.setInv(JsonUtil.getString(jo, ReportConstant.B2LIST.B2_INV) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.B2LIST.B2_INV));
			vo.setInvtype(JsonUtil.getString(jo,
					ReportConstant.B2LIST.B2_INVTYPE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.B2LIST.B2_INVTYPE));
			vo.setSubconam(JsonUtil.getString(jo,
					ReportConstant.B2LIST.B2_SUBCONAM) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.B2LIST.B2_SUBCONAM));
			retList.add(vo);
		}
		return retList;
	}

	public List<SeniorExecutiveInfoVo> getc2List(String jsonarray) {
		List<SeniorExecutiveInfoVo> retList = new ArrayList<SeniorExecutiveInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		SeniorExecutiveInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new SeniorExecutiveInfoVo();
			vo.setMember_name(JsonUtil.getString(jo,
					ReportConstant.C2LIST.C2_MEMBER_NAME) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.C2LIST.C2_MEMBER_NAME));
			vo.setMember_type(JsonUtil.getString(jo,
					ReportConstant.C2LIST.C2_MEMBER_TYPE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.C2LIST.C2_MEMBER_TYPE));
			retList.add(vo);
		}
		return retList;
	}

	public List<SocialSecurityVo> getbList(String jsonarray) {
		List<SocialSecurityVo> retList = new ArrayList<SocialSecurityVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		SocialSecurityVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new SocialSecurityVo();
			vo.setB_amount(JsonUtil
					.getString(jo, ReportConstant.BLIST.B_AMOUNT) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.BLIST.B_AMOUNT));
			String month = JsonUtil.getString(jo,
					ReportConstant.BLIST.B_DEPOSIT_MONTH);
			if (EmptyConditionUtils.notEmptyStringSpaceNoMeaning(month)) {
				vo.setB_deposit_month(month.substring(0, 4) + "年"
						+ month.substring(4, 6) + "月");
			}
			// vo.setB_deposit_month(JsonUtil.getString(jo,ReportConstant.BLIST.B_DEPOSIT_MONTH)==null?"":JsonUtil.getString(jo,ReportConstant.BLIST.B_DEPOSIT_MONTH));

			vo.setB_person_num(JsonUtil.getString(jo,
					ReportConstant.BLIST.B_PERSON_NUM) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.BLIST.B_PERSON_NUM));
			vo.setSbje(JsonUtil.getString(jo, ReportConstant.BLIST.B_SBJE) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.BLIST.B_SBJE));
			vo.setSbrs(JsonUtil.getString(jo, ReportConstant.BLIST.B_SBRS) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.BLIST.B_SBRS));
			retList.add(vo);
		}
		return retList;
	}

	public List<PatentInfoVo> getJList(String jsonarray) {
		List<PatentInfoVo> retList = new ArrayList<PatentInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		PatentInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new PatentInfoVo();
			vo.setName(JsonUtil.getString(jo, ReportConstant.JLIST.J_NAME) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.JLIST.J_NAME));
			vo.setPat_cert_no(JsonUtil.getString(jo,
					ReportConstant.JLIST.J_PAT_CERT_NO) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.JLIST.J_PAT_CERT_NO));
			vo.setPat_date(JsonUtil.getString(jo,
					ReportConstant.JLIST.J_PAT_DATE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.JLIST.J_PAT_DATE));
			vo.setPat_type(JsonUtil.getString(jo,
					ReportConstant.JLIST.J_PAT_TYPE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.JLIST.J_PAT_TYPE));
			retList.add(vo);
		}
		return retList;
	}

	public List<EvaluateInfoVo> getKList(String jsonarray) {
		List<EvaluateInfoVo> retList = new ArrayList<EvaluateInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		EvaluateInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new EvaluateInfoVo();
			vo.setAward_date(JsonUtil.getString(jo,
					ReportConstant.KLIST.K_AWARD_DATE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.KLIST.K_AWARD_DATE));
			vo.setCreate_time(JsonUtil.getString(jo,
					ReportConstant.KLIST.K_CREATE_TIME) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.KLIST.K_CREATE_TIME));
			vo.setEvaluate_content(JsonUtil.getString(jo,
					ReportConstant.KLIST.K_EVALUATE_CONTENT) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.KLIST.K_EVALUATE_CONTENT));
			vo.setEvaluate_grade(JsonUtil.getString(jo,
					ReportConstant.KLIST.K_EVALUATE_GRADE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.KLIST.K_EVALUATE_GRADE));
			vo.setEvaluate_organ(JsonUtil.getString(jo,
					ReportConstant.KLIST.K_EVALUATE_ORGAN) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.KLIST.K_EVALUATE_ORGAN));
			vo.setFile_no(JsonUtil
					.getString(jo, ReportConstant.KLIST.K_FILE_NO) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.KLIST.K_FILE_NO));
			retList.add(vo);
		}
		return retList;
	}

	public List<LoanInfoVo> getMCList(String jsonarray) {
		List<LoanInfoVo> retList = new ArrayList<LoanInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		LoanInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new LoanInfoVo();
			vo.setMc_actv_dt(JsonUtil.getString(jo,
					ReportConstant.MCLIST.MC_ACTV_DT) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.MCLIST.MC_ACTV_DT));
			vo.setMc_bank_name(JsonUtil.getString(jo,
					ReportConstant.MCLIST.MC_BANK_NAME) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.MCLIST.MC_BANK_NAME));
			vo.setMc_last_due_dt(JsonUtil.getString(jo,
					ReportConstant.MCLIST.MC_LAST_DUE_DT) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.MCLIST.MC_LAST_DUE_DT));
			vo.setMc_main_gur_typ(JsonUtil.getString(jo,
					ReportConstant.MCLIST.MC_MAIN_GUR_TYP) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.MCLIST.MC_MAIN_GUR_TYP));
			vo.setMc_os_prcp(JsonUtil.getString(jo,
					ReportConstant.MCLIST.MC_OS_PRCP) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.MCLIST.MC_OS_PRCP));
			retList.add(vo);
		}
		return retList;
	}

	public List<LoanInfoVo> getTList(String jsonarray) {
		List<LoanInfoVo> retList = new ArrayList<LoanInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		LoanInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new LoanInfoVo();
			vo.setT_actv_dt(JsonUtil.getString(jo,
					ReportConstant.TLIST.T_ACTV_DT) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.TLIST.T_ACTV_DT));
			vo.setT_assure_corp_name(JsonUtil.getString(jo,
					ReportConstant.TLIST.T_ASSURE_CORP_NAME) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TLIST.T_ASSURE_CORP_NAME));
			vo.setT_assure_prcp(JsonUtil.getString(jo,
					ReportConstant.TLIST.T_ASSURE_PRCP) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.TLIST.T_ASSURE_PRCP));
			vo.setT_bank_name(JsonUtil.getString(jo,
					ReportConstant.TLIST.T_BANK_NAME) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.TLIST.T_BANK_NAME));
			vo.setT_last_due_dt(JsonUtil.getString(jo,
					ReportConstant.TLIST.T_LAST_DUE_DT) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.TLIST.T_LAST_DUE_DT));
			vo.setT_unassure_type(JsonUtil.getString(jo,
					ReportConstant.TLIST.T_UNASSURE_TYPE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TLIST.T_UNASSURE_TYPE));
			retList.add(vo);
		}
		return retList;
	}

	/********************************* 2017-05-26 ********************************************************/
	public List<LoanInfoVo> getXDXXList(String jsonarray) {
		List<LoanInfoVo> retList = new ArrayList<LoanInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		JSONArray reportJson = new JSONArray().fromObject(jsonarray);
		LoanInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new LoanInfoVo();
			vo.setFinance_no(JsonUtil.getString(jo,
					ReportConstant.XDXXLIST.XDXX_FINANCE_NO) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.XDXXLIST.XDXX_FINANCE_NO));
			vo.setLoan_num_balance(JsonUtil.getString(jo,
					ReportConstant.XDXXLIST.XDXX_LOAN_NUM_BALANCE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.XDXXLIST.XDXX_LOAN_NUM_BALANCE));
			vo.setCurrency(JsonUtil.getString(jo,
					ReportConstant.XDXXLIST.XDXX_CURRENCY) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.XDXXLIST.XDXX_CURRENCY));
			vo.setLoan_date_send(JsonUtil.getString(jo,
					ReportConstant.XDXXLIST.XDXX_LOAN_DATE_SEND) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.XDXXLIST.XDXX_LOAN_DATE_SEND));
			vo.setLoan_date_expire(JsonUtil.getString(jo,
					ReportConstant.XDXXLIST.XDXX_LOAN_DATE_EXPIRE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.XDXXLIST.XDXX_LOAN_DATE_EXPIRE));
			vo.setGuarantee_type(JsonUtil.getString(jo,
					ReportConstant.XDXXLIST.XDXX_GUARANTEE_TYPE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.XDXXLIST.XDXX_GUARANTEE_TYPE));
			vo.setFive_category(JsonUtil.getString(jo,
					ReportConstant.XDXXLIST.XDXX_FIVE_CATEGORY) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.XDXXLIST.XDXX_FIVE_CATEGORY));
			retList.add(vo);
		}
		return retList;
	}

	public List<LoanInfoVo> getTXXXList(String jsonarray) {
		List<LoanInfoVo> retList = new ArrayList<LoanInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		LoanInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new LoanInfoVo();
			vo.setFinance_no(JsonUtil.getString(jo,
					ReportConstant.TXXXLIST.TXXX_FINANCE_NO) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TXXXLIST.TXXX_FINANCE_NO));
			vo.setLoan_num_balance(JsonUtil.getString(jo,
					ReportConstant.TXXXLIST.TXXX_COUNTERFOIL_NUM) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TXXXLIST.TXXX_COUNTERFOIL_NUM));
			vo.setCurrency(JsonUtil.getString(jo,
					ReportConstant.TXXXLIST.TXXX_CURRENCY) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TXXXLIST.TXXX_CURRENCY));
			vo.setLoan_date_send(JsonUtil.getString(jo,
					ReportConstant.TXXXLIST.TXXX_DISCOUNT_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TXXXLIST.TXXX_DISCOUNT_DATE));
			vo.setLoan_date_expire(JsonUtil.getString(jo,
					ReportConstant.TXXXLIST.TXXX_PROMISES_EXPIRE_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TXXXLIST.TXXX_PROMISES_EXPIRE_DATE));
			vo.setFive_category(JsonUtil.getString(jo,
					ReportConstant.TXXXLIST.TXXX_FIVE_CATEGORY) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TXXXLIST.TXXX_FIVE_CATEGORY));
			retList.add(vo);
		}
		return retList;
	}

	public List<ComplaintInfoVo> getLaxxList(String jsonarray) {
		List<ComplaintInfoVo> retList = new ArrayList<ComplaintInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		ComplaintInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new ComplaintInfoVo();
			vo.setLaxx_case_create_amount(JsonUtil.getString(jo,
					ReportConstant.LaxxLIST.LAXX_CASE_CREATE_AMOUNT) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.LaxxLIST.LAXX_CASE_CREATE_AMOUNT));
			vo.setLaxx_case_create_date(JsonUtil.getString(jo,
					ReportConstant.LaxxLIST.LAXX_CASE_CREATE_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.LaxxLIST.LAXX_CASE_CREATE_DATE));
			vo.setLaxx_case_no(JsonUtil.getString(jo,
					ReportConstant.LaxxLIST.LAxx_case_no) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.LaxxLIST.LAxx_case_no));
			vo.setLaxx_case_person_state(JsonUtil.getString(jo,
					ReportConstant.LaxxLIST.LAXX_CASE_PERSON_STATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.LaxxLIST.LAXX_CASE_PERSON_STATE));
			vo.setLaxx_case_reson(JsonUtil.getString(jo,
					ReportConstant.LaxxLIST.LAXX_CASE_RESON) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.LaxxLIST.LAXX_CASE_RESON));
			vo.setLaxx_case_status(JsonUtil.getString(jo,
					ReportConstant.LaxxLIST.LAXX_CASE_STATUS) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.LaxxLIST.LAXX_CASE_STATUS));
			vo.setLaxx_case_type(JsonUtil.getString(jo,
					ReportConstant.LaxxLIST.LAXX_CASE_TYPE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.LaxxLIST.LAXX_CASE_TYPE));
			retList.add(vo);
		}
		return retList;
	}

	public List<ComplaintInfoVo> getJaxxList(String jsonarray) {
		List<ComplaintInfoVo> retList = new ArrayList<ComplaintInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		ComplaintInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new ComplaintInfoVo();
			vo.setJaxx_case_end_amount(JsonUtil.getString(jo,
					ReportConstant.JaxxLIST.JAXX_CASE_CREATE_AMOUNT) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.JaxxLIST.JAXX_CASE_CREATE_AMOUNT));
			vo.setJaxx_case_end_date(JsonUtil.getString(jo,
					ReportConstant.JaxxLIST.JAXX_CASE_CREATE_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.JaxxLIST.JAXX_CASE_CREATE_DATE));
			vo.setJaxx_case_no(JsonUtil.getString(jo,
					ReportConstant.JaxxLIST.JAxx_case_no) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.JaxxLIST.JAxx_case_no));
			vo.setJaxx_case_person_state(JsonUtil.getString(jo,
					ReportConstant.JaxxLIST.JAXX_CASE_PERSON_STATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.JaxxLIST.JAXX_CASE_PERSON_STATE));
			vo.setJaxx_case_reson(JsonUtil.getString(jo,
					ReportConstant.JaxxLIST.JAXX_CASE_RESON) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.JaxxLIST.JAXX_CASE_RESON));
			vo.setJaxx_case_type(JsonUtil.getString(jo,
					ReportConstant.JaxxLIST.JAXX_CASE_TYPE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.JaxxLIST.JAXX_CASE_TYPE));
			vo.setJaxx_case_end_type(JsonUtil.getString(jo,
					ReportConstant.JaxxLIST.JAXX_CASE_END_TYPE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.JaxxLIST.JAXX_CASE_END_TYPE));
			retList.add(vo);
		}
		return retList;
	}

	public List<ComplaintInfoVo> getSxzxList(String jsonarray) {
		List<ComplaintInfoVo> retList = new ArrayList<ComplaintInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		ComplaintInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new ComplaintInfoVo();
			vo.setSxzx_case_courthouse(JsonUtil.getString(jo,
					ReportConstant.SxzxLIST.SXZX_CASE_COURTHOUSE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.SxzxLIST.SXZX_CASE_COURTHOUSE));
			vo.setSxzx_case_exe_no(JsonUtil.getString(jo,
					ReportConstant.SxzxLIST.SXZX_CASE_EXE_NO) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.SxzxLIST.SXZX_CASE_EXE_NO));
			vo.setSxzx_discreditable_state(JsonUtil.getString(jo,
					ReportConstant.SxzxLIST.SXZX_DISCREDITABLE_STATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.SxzxLIST.SXZX_DISCREDITABLE_STATE));
			vo.setSxzx_publish_date(JsonUtil.getString(jo,
					ReportConstant.SxzxLIST.SXZX_PUBLISH_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.SxzxLIST.SXZX_PUBLISH_DATE));
			retList.add(vo);
		}
		return retList;
	}

	public List<NegativeInfoVo> getHList(String jsonarray) {
		List<NegativeInfoVo> retList = new ArrayList<NegativeInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		NegativeInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new NegativeInfoVo();
			vo.setH_create_time(JsonUtil.getString(jo,
					ReportConstant.HLIST.H_CREATE_TIME) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.HLIST.H_CREATE_TIME));
			vo.setH_source(JsonUtil
					.getString(jo, ReportConstant.HLIST.H_SOURCE) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.HLIST.H_SOURCE));
			vo.setH_yqje(JsonUtil.getString(jo, ReportConstant.HLIST.H_YQJE) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.HLIST.H_YQJE));
			vo.setH_yqsj(JsonUtil.getString(jo, ReportConstant.HLIST.H_YQSJ) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.HLIST.H_YQSJ));
			retList.add(vo);
		}
		return retList;
	}

	public List<NegativeInfoVo> getIList(String jsonarray) {
		List<NegativeInfoVo> retList = new ArrayList<NegativeInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		NegativeInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new NegativeInfoVo();
			vo.setI_create_time(JsonUtil.getString(jo,
					ReportConstant.ILIST.I_CREATE_TIME) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ILIST.I_CREATE_TIME));
			vo.setI_illegal_act(JsonUtil.getString(jo,
					ReportConstant.ILIST.I_ILLEGAL_ACT) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ILIST.I_ILLEGAL_ACT));
			vo.setI_pun_content(JsonUtil.getString(jo,
					ReportConstant.ILIST.I_PUN_CONTENT) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ILIST.I_PUN_CONTENT));
			vo.setI_pun_deci(JsonUtil.getString(jo,
					ReportConstant.ILIST.I_PUN_DECI) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ILIST.I_PUN_DECI));
			vo.setI_pun_time(JsonUtil.getString(jo,
					ReportConstant.ILIST.I_PUN_TIME) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ILIST.I_PUN_TIME));
			vo.setI_pun_type(JsonUtil.getString(jo,
					ReportConstant.ILIST.I_PUN_TYPE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ILIST.I_PUN_TYPE));
			vo.setI_skssqsr(JsonUtil.getString(jo,
					ReportConstant.ILIST.I_SKSSQSR) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ILIST.I_SKSSQSR));
			retList.add(vo);
		}
		return retList;
	}

	public List<NegativeInfoVo> getGpdbList(String jsonarray) {
		List<NegativeInfoVo> retList = new ArrayList<NegativeInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		NegativeInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new NegativeInfoVo();
			vo.setGpdb_duty_level(JsonUtil.getString(jo,
					ReportConstant.GPDBLIST.GPDB_DUTY_LEVE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.GPDBLIST.GPDB_DUTY_LEVE));
			vo.setGpdb_expire_date(JsonUtil.getString(jo,
					ReportConstant.GPDBLIST.GPDB_EXPRIE_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.GPDBLIST.GPDB_EXPRIE_DATE));
			vo.setGpdb_flow_org(JsonUtil.getString(jo,
					ReportConstant.GPDBLIST.GPDB_FLOW_ORG) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.GPDBLIST.GPDB_FLOW_ORG));
			vo.setGpdb_problem_type(JsonUtil.getString(jo,
					ReportConstant.GPDBLIST.GPDB_PROBLEM_TYPE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.GPDBLIST.GPDB_PROBLEM_TYPE));
			vo.setGpdb_start_date(JsonUtil.getString(jo,
					ReportConstant.GPDBLIST.GPDB_START_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.GPDBLIST.GPDB_START_DATE));
			retList.add(vo);
		}
		return retList;
	}

	public List<NegativeInfoVo> getMList(String jsonarray) {
		List<NegativeInfoVo> retList = new ArrayList<NegativeInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		NegativeInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new NegativeInfoVo();
			vo.setM_create_time(JsonUtil.getString(jo,
					ReportConstant.MLIST.M_CREATE_TIME) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.MLIST.M_CREATE_TIME));
			vo.setM_fzchrdsj(JsonUtil.getString(jo,
					ReportConstant.MLIST.M_FZCHRDSJ) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.MLIST.M_FZCHRDSJ));
			vo.setM_rdbm(JsonUtil.getString(jo, ReportConstant.MLIST.M_RDBM) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.MLIST.M_RDBM));
			retList.add(vo);
		}
		return retList;
	}

	public List<NegativeInfoVo> getZList(String jsonarray) {
		List<NegativeInfoVo> retList = new ArrayList<NegativeInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		NegativeInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new NegativeInfoVo();
			vo.setZ_create_time(JsonUtil.getString(jo,
					ReportConstant.ZLIST.Z_CREATE_TIME) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ZLIST.Z_CREATE_TIME));
			vo.setZ_leib(JsonUtil.getString(jo, ReportConstant.ZLIST.Z_LEIB) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.ZLIST.Z_LEIB));
			retList.add(vo);
		}
		return retList;
	}

	public List<NegativeInfoVo> getK2List(String jsonarray) {
		List<NegativeInfoVo> retList = new ArrayList<NegativeInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		NegativeInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new NegativeInfoVo();
			vo.setK2_award_date(JsonUtil.getString(jo,
					ReportConstant.K2LIST.K2_AWARD_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K2LIST.K2_AWARD_DATE));
			vo.setK2_create_time(JsonUtil.getString(jo,
					ReportConstant.K2LIST.K2_CREATE_TIME) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K2LIST.K2_CREATE_TIME));
			vo.setK2_evaluate_content(JsonUtil.getString(jo,
					ReportConstant.K2LIST.K2_EVALUATE_CONTENT) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K2LIST.K2_EVALUATE_CONTENT));
			vo.setK2_evaluate_grade(JsonUtil.getString(jo,
					ReportConstant.K2LIST.K2_EVALUATE_GRADE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K2LIST.K2_EVALUATE_GRADE));
			vo.setK2_evaluate_organ(JsonUtil.getString(jo,
					ReportConstant.K2LIST.K2_EVALUATE_ORGAN) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K2LIST.K2_EVALUATE_ORGAN));
			retList.add(vo);
		}
		return retList;
	}

	public List<NegativeInfoVo> getA2List(String jsonarray) {
		List<NegativeInfoVo> retList = new ArrayList<NegativeInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		NegativeInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new NegativeInfoVo();
			vo.setA2_bhgxm(JsonUtil.getString(jo,
					ReportConstant.A2LIST.A2_BHGXM) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.A2LIST.A2_BHGXM));
			vo.setA2_ccsj(JsonUtil.getString(jo, ReportConstant.A2LIST.A2_CCSJ) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.A2LIST.A2_CCSJ));
			vo.setA2_cjjg(JsonUtil.getString(jo, ReportConstant.A2LIST.A2_CJJG) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.A2LIST.A2_CJJG));
			vo.setA2_cpmc(JsonUtil.getString(jo, ReportConstant.A2LIST.A2_CPMC) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.A2LIST.A2_CPMC));
			vo.setA2_jyjl(JsonUtil.getString(jo, ReportConstant.A2LIST.A2_JYJL) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.A2LIST.A2_JYJL));
			vo.setA2_shangb(JsonUtil.getString(jo,
					ReportConstant.A2LIST.A2_SHANGB) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.A2LIST.A2_SHANGB));
			retList.add(vo);
		}
		return retList;
	}

	public List<NegativeInfoVo> getK3List(String jsonarray) {
		List<NegativeInfoVo> retList = new ArrayList<NegativeInfoVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		NegativeInfoVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new NegativeInfoVo();
			vo.setK3_award_date(JsonUtil.getString(jo,
					ReportConstant.K3LIST.K3_AWARD_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K3LIST.K3_AWARD_DATE));
			vo.setK3_create_time(JsonUtil.getString(jo,
					ReportConstant.K3LIST.K3_CREATE_TIME) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K3LIST.K3_CREATE_TIME));
			vo.setK3_evaluate_content(JsonUtil.getString(jo,
					ReportConstant.K3LIST.K3_EVALUATE_CONTENT) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K3LIST.K3_EVALUATE_CONTENT));
			vo.setK3_evaluate_grade(JsonUtil.getString(jo,
					ReportConstant.K3LIST.K3_EVALUATE_GRADE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K3LIST.K3_EVALUATE_GRADE));
			vo.setK3_evaluate_organ(JsonUtil.getString(jo,
					ReportConstant.K3LIST.K3_EVALUATE_ORGAN) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.K3LIST.K3_EVALUATE_ORGAN));
			retList.add(vo);
		}
		return retList;
	}

	public List<BDCVo> getUList(String jsonarray) {
		List<BDCVo> retList = new ArrayList<BDCVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		BDCVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new BDCVo();
			vo.setZl(JsonUtil.getString(jo, ReportConstant.ULIST.U_REPOSE) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.ULIST.U_REPOSE));
			vo.setCertnum(JsonUtil.getString(jo,
					ReportConstant.ULIST.U_WARR_NUM) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ULIST.U_WARR_NUM));
			vo.setDyzqr(JsonUtil.getString(jo, ReportConstant.ULIST.U_MORTGA) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.ULIST.U_MORTGA));
			vo.setBz(JsonUtil.getString(jo, ReportConstant.ULIST.U_BIZ) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.ULIST.U_BIZ));
			vo.setAmount(JsonUtil.getString(jo, ReportConstant.ULIST.U_AMOUNT) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.ULIST.U_AMOUNT));
			vo.setStartdate(JsonUtil.getString(jo,
					ReportConstant.ULIST.U_START_DATE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ULIST.U_START_DATE));
			vo.setEnddate(JsonUtil.getString(jo,
					ReportConstant.ULIST.U_EDN_DATE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.ULIST.U_EDN_DATE));
			vo.setArea(JsonUtil.getString(jo, ReportConstant.ULIST.U_AREA) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.ULIST.U_AREA));
			retList.add(vo);
		}
		return retList;
	}

	public List<BDCVo> getVList(String jsonarray) {
		List<BDCVo> retList = new ArrayList<BDCVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		BDCVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new BDCVo();
			vo.setZl(JsonUtil.getString(jo, ReportConstant.VLIST.V_ZUOL) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.VLIST.V_ZUOL));
			vo.setCertnum(JsonUtil.getString(jo, ReportConstant.VLIST.V_TXQZH) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.VLIST.V_TXQZH));
			vo.setDyzqr(JsonUtil.getString(jo, ReportConstant.VLIST.V_DYQRMC) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.VLIST.V_DYQRMC));
			vo.setBz(JsonUtil.getString(jo, ReportConstant.VLIST.V_BIZ) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.VLIST.V_BIZ));
			vo.setAmount(JsonUtil.getString(jo, ReportConstant.VLIST.V_DYJE) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.VLIST.V_DYJE));
			vo.setStartdate(JsonUtil.getString(jo,
					ReportConstant.VLIST.V_START_DATE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.VLIST.V_START_DATE));
			vo.setEnddate(JsonUtil.getString(jo,
					ReportConstant.VLIST.V_END_DATE) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.VLIST.V_END_DATE));
			vo.setArea(JsonUtil.getString(jo, ReportConstant.VLIST.V_DYMJ) == null ? ""
					: JsonUtil.getString(jo, ReportConstant.VLIST.V_DYMJ));
			retList.add(vo);
		}
		return retList;
	}

	public List<BDCVo> getTdcfList(String jsonarray) {
		List<BDCVo> retList = new ArrayList<BDCVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		BDCVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new BDCVo();
			vo.setZl(JsonUtil.getString(jo,
					ReportConstant.TdcfLIST.TDCF_ADDRESS) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TdcfLIST.TDCF_ADDRESS));
			vo.setCertnum(JsonUtil.getString(jo,
					ReportConstant.TdcfLIST.TDCF_LAND_CRE_NO) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TdcfLIST.TDCF_LAND_CRE_NO));
			vo.setCfdw(JsonUtil.getString(jo,
					ReportConstant.TdcfLIST.TDCF_CORP_CLOSE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TdcfLIST.TDCF_CORP_CLOSE));
			vo.setCfnum(JsonUtil.getString(jo,
					ReportConstant.TdcfLIST.TDCF_CLOSE_CRE_NO) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TdcfLIST.TDCF_CLOSE_CRE_NO));
			vo.setStartdate(JsonUtil.getString(jo,
					ReportConstant.TdcfLIST.TDCF_CLOSE_BEGIN_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TdcfLIST.TDCF_CLOSE_BEGIN_DATE));
			vo.setEnddate(JsonUtil.getString(jo,
					ReportConstant.TdcfLIST.TDCF_CLOSE_END_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TdcfLIST.TDCF_CLOSE_END_DATE));
			vo.setCxdate(JsonUtil.getString(jo,
					ReportConstant.TdcfLIST.TDCF_CLOSE_CANCEL_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TdcfLIST.TDCF_CLOSE_CANCEL_DATE));
			vo.setArea(JsonUtil.getString(jo,
					ReportConstant.TdcfLIST.TDCF_LAND_AREA) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.TdcfLIST.TDCF_LAND_AREA));
			retList.add(vo);
		}
		return retList;
	}

	public List<BDCVo> getFccfList(String jsonarray) {
		List<BDCVo> retList = new ArrayList<BDCVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		BDCVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new BDCVo();
			vo.setZl(JsonUtil.getString(jo,
					ReportConstant.FccfLIST.FCCF_ADDRESS) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.FccfLIST.FCCF_ADDRESS));
			vo.setCertnum(JsonUtil.getString(jo,
					ReportConstant.FccfLIST.FCCF_BUILDING_CRE_NO) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.FccfLIST.FCCF_BUILDING_CRE_NO));
			vo.setCfdw(JsonUtil.getString(jo,
					ReportConstant.FccfLIST.FCCF_CORP_CLOSE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.FccfLIST.FCCF_CORP_CLOSE));
			vo.setCfnum(JsonUtil.getString(jo,
					ReportConstant.FccfLIST.FCCF_CLOSE_CRE_NO) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.FccfLIST.FCCF_CLOSE_CRE_NO));
			vo.setStartdate(JsonUtil.getString(jo,
					ReportConstant.FccfLIST.FCCF_CLOSE_BEGIN_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.FccfLIST.FCCF_CLOSE_BEGIN_DATE));
			vo.setEnddate(JsonUtil.getString(jo,
					ReportConstant.FccfLIST.FCCF_CLOSE_END_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.FccfLIST.FCCF_CLOSE_END_DATE));
			vo.setCxdate(JsonUtil.getString(jo,
					ReportConstant.FccfLIST.FCCF_CLOSE_CANCEL_DATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.FccfLIST.FCCF_CLOSE_CANCEL_DATE));
			vo.setArea(JsonUtil.getString(jo,
					ReportConstant.FccfLIST.FCCF_BUILDING_AREA) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.FccfLIST.FCCF_BUILDING_AREA));
			retList.add(vo);
		}
		return retList;
	}

	/***************** 2017-05-25 *****************************/
	public List<BDCVo> getDyxxList(String jsonarray) {
		List<BDCVo> retList = new ArrayList<BDCVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		BDCVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new BDCVo();
			vo.setType(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_BUSTYPE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_BUSTYPE));
			vo.setZl(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_ADDRESS) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_ADDRESS));
			vo.setCertnum(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_WARRNUM) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_WARRNUM));
			vo.setDyzqr(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_MORTGA) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_MORTGA));
			vo.setBz(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_CURRENCY) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_CURRENCY));
			vo.setAmount(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_AMOUNT) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_AMOUNT));
			vo.setStartdate(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_STARTDATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_STARTDATE));
			vo.setEnddate(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_ENDDATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_ENDDATE));
			vo.setLandarea(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_LANDAREA) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_LANDAREA));
			vo.setArea(JsonUtil.getString(jo,
					ReportConstant.DYXXLIST.DYXX_HOUSEAREA) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.DYXXLIST.DYXX_HOUSEAREA));
			retList.add(vo);
		}
		return retList;
	}

	public List<BDCVo> getCfxxList(String jsonarray) {
		List<BDCVo> retList = new ArrayList<BDCVo>();
		if (!EmptyConditionUtils.notEmptyStringSpaceNoMeaning(jsonarray)) {
			return retList;
		}
		new JSONArray();
		JSONArray reportJson = JSONArray.fromObject(jsonarray);
		BDCVo vo = null;
		for (int i = 0; i < reportJson.size(); i++) {
			JSONObject jo = JSONObject.fromObject(reportJson.get(i));
			vo = new BDCVo();
			vo.setType(JsonUtil.getString(jo,
					ReportConstant.CFXXLIST.CFXX_BUSTYPE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CFXXLIST.CFXX_BUSTYPE));
			vo.setZl(JsonUtil.getString(jo,
					ReportConstant.CFXXLIST.CFXX_ADDRESS) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CFXXLIST.CFXX_ADDRESS));
			vo.setCertnum(JsonUtil.getString(jo,
					ReportConstant.CFXXLIST.CFXX_CRENO) == null ? "" : JsonUtil
					.getString(jo, ReportConstant.CFXXLIST.CFXX_CRENO));
			vo.setCfdw(JsonUtil.getString(jo,
					ReportConstant.CFXXLIST.CFXX_CORPCLOSE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CFXXLIST.CFXX_CORPCLOSE));
			vo.setCfnum(JsonUtil.getString(jo,
					ReportConstant.CFXXLIST.CFXX_CLOSECRENO) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CFXXLIST.CFXX_CLOSECRENO));
			vo.setStartdate(JsonUtil.getString(jo,
					ReportConstant.CFXXLIST.CFXX_STARTDATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CFXXLIST.CFXX_STARTDATE));
			vo.setEnddate(JsonUtil.getString(jo,
					ReportConstant.CFXXLIST.CFXX_ENDDATE) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CFXXLIST.CFXX_ENDDATE));
			vo.setLandarea(JsonUtil.getString(jo,
					ReportConstant.CFXXLIST.CFXX_LANDAREA) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CFXXLIST.CFXX_LANDAREA));
			vo.setArea(JsonUtil.getString(jo,
					ReportConstant.CFXXLIST.CFXX_HOUSEAREA) == null ? ""
					: JsonUtil.getString(jo,
							ReportConstant.CFXXLIST.CFXX_HOUSEAREA));
			retList.add(vo);
		}
		return retList;
	}

	public static void main(String[] args) {
		String key = "E053040812ACB857";
		WebClient client = null;
		try {
			client = WebClient.create("http://192.168.10.19:8094/ee-ws/");// 保持线程安全状态的时间
			WebClient.getConfig(client);// 检索客户配置 返回客户配置实例
			// 获取与远程服务进行传输级通信的HTTP管道。设置接受返回结果的超时时间参数
			ClientConfiguration config = WebClient.getConfig(client);
			config.getHttpConduit().getClient().setConnectionTimeout(30000);// 连接超时
																			// Could
																			// not
																			// send
																			// Message.
			config.getHttpConduit().getClient().setReceiveTimeout(30000); // 响应超时
			client.path("wsWebService/getReportInfo")
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON);
			System.out.println("===================" + client.getCurrentURI());
			String e_userName = AESUtil.encrypt("szgqpt", key);// pssword:GFzx1234
			String e_corpCode = AESUtil.encrypt("57543357-9", key);
			String e_reason = AESUtil.encrypt("5", key);
			client.replaceQueryParam("userName", e_userName)
					.replaceQueryParam("corpCode", e_corpCode)
					.replaceQueryParam("reason", e_reason);
			String result = client.get(String.class);
		} catch (Exception e) {
			System.out.println("meassage=============" + e.getMessage());
			if (e.getMessage().contains("Read timed out")) {
				System.out.println("响应超时...");
			}
			if (e.getMessage().contains("connect timed out")) {
				System.out.println("连接超时...");
			}
			// TODO: handle exception
		}
		// System.out.println("json1==============="+ result);
		System.out.println("result==============="
				+ AESUtil.decrypt(client.get(String.class), key));
	}
	public static String enterpriseName(String name) {
		int sensitiveSize = name.lastIndexOf("区");
		if (sensitiveSize == -1) {
			sensitiveSize = name.lastIndexOf("市");
			if (sensitiveSize == -1) {
				sensitiveSize = 1;
			}
		}
		sensitiveSize++;
		if (StringUtils.isBlank(name)) {
			return "";
		}
		int length = StringUtils.length(name);
		return StringUtils.rightPad(StringUtils.left(name, sensitiveSize),
				length, "*").concat(StringUtils.right(name.trim(), 4));
	}
}

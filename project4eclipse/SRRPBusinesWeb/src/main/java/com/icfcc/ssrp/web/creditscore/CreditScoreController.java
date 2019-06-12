package com.icfcc.ssrp.web.creditscore;

import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctc.wstx.util.StringUtil;
import com.icfcc.SRRPDao.jpa.entity.creditscore.QueryLog;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ReportConstant;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ReportVO;
import com.icfcc.SRRPDao.jpa.entity.creditscore.score.CreditScoreBean;
import com.icfcc.SRRPDao.jpa.entity.creditscore.score.RpCompanyCreditscores;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyBase;
import com.icfcc.SRRPDao.jpa.entity.enterprise.Investor;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUser;
import com.icfcc.SRRPService.PlatformSystem.PlatformUserService;
import com.icfcc.SRRPService.creditscore.CreditReportService;
import com.icfcc.SRRPService.creditscore.CreditScoreService;
import com.icfcc.SRRPService.creditscore.QueryLogService;
import com.icfcc.SRRPService.enterprise.CompanyInfoService;
import com.icfcc.SRRPService.enterprise.InvestorService;
import com.icfcc.SRRPService.gataway.staticize.GataWayIndexService;
import com.icfcc.SRRPService.managedept.ManageDissentService;
import com.icfcc.credit.platform.util.JsonUtil;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.AESUtil;
import com.icfcc.ssrp.web.SRRPBaseController;

@Slf4j
@Controller
@RequestMapping(value = "/creditscore")
public class CreditScoreController extends SRRPBaseController {

	@Autowired
	private CreditScoreService scoreService;

	@Autowired
	private QueryLogService queryService;
	@Autowired
	private CreditReportService reportService;

	@Autowired
	private PlatformUserService serService;
	
	@Autowired
    private ManageDissentService manageDissentService;
	
	@Autowired
    private CompanyInfoService companyInfoService;
	
	 @Autowired
	 private GataWayIndexService investorService;
	
	private static Logger log = LoggerFactory
			.getLogger(CreditScoreController.class);

	/**
	 * 信用报告
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryReport")
	public String queryReport(HttpServletRequest request,
			HttpServletResponse response) {
		QueryLog querylog = new QueryLog();
		ReportVO vo = null;
		String name = null;// new String(.getBytes("iso8859-1"),"UTF-8");
		String path = null;
		String showFlag = request.getParameter("showFlag");// 证件类型
		String userId = RedisGetValue.getRedisUser(request, "userId");
        String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
		boolean flag = false;
		String enterpriseId="";
		try {
		  
			String creditCode = URLDecoder.decode(URLDecoder.decode(request.getParameter("certno"), "utf-8"), "utf-8");// 证件号码 
			String credittype = URLDecoder.decode(URLDecoder.decode(request.getParameter("certtype"), "utf-8"), "utf-8");// 证件类型
			credittype=AESUtil.desEncrypt(credittype,"123456qwerty1111");
			creditCode = AESUtil.desEncrypt(creditCode,"123456qwerty1111");
			querylog.setCertno(creditCode);
			querylog.setCerttype(credittype);
			querylog.setUserid(userId);
			querylog.setQuerytype("1");// 信用报告查询
			name = URLDecoder.decode(request.getParameter("name"), "utf-8");
			querylog.setQueryname(name);
			  //查看未读消息完之后删除异议提醒
			if(SRRPConstant.USER_TYPE_03.equals(userType)){
                CompanyBase base=companyInfoService.queryByCertno(creditCode);
                if(!StringUtils.isEmpty(base)){
                    manageDissentService.deleteByMessageId(base.getEnterpriseId(),userId,SRRPConstant.MESSAGE_TYPE_01);
                }else{
                    Investor invest=investorService.findInvestor(creditCode);
                    if(!StringUtils.isEmpty(invest)){
                        manageDissentService.deleteByMessageId(invest.getInvestorId(),userId,SRRPConstant.MESSAGE_TYPE_03);
                    }
                }
            }
			querylog.setBegintime(new Date());
			// String
//			String jsontu="1|[{\"a2\":\"show\",\"a2List\":[],\"b\":\"show\",\"b2\":\"show\",\"b2List\":[{\"b2_controlling_share\":\"93.53%\",\"b2_inv\":\"吴震宇\",\"b2_invtype\":\"自然人股东\",\"b2_subconam\":\"1,878\"},{\"b2_controlling_share\":\"6.47%\",\"b2_inv\":\"吴江新金城不锈钢制品厂\",\"b2_invtype\":\"个人独资企业\",\"b2_subconam\":\"130\"}],\"bList\":[{\"b_amount\":\"\",\"b_deposit_month\":\"201703\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"26,106.16\",\"b_sbrs\":\"31\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201702\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"22,701.12\",\"b_sbrs\":\"27\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201701\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"23,578.08\",\"b_sbrs\":\"28\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201612\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"21,824.16\",\"b_sbrs\":\"26\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201611\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,151.64\",\"b_sbrs\":\"24\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201610\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,151.64\",\"b_sbrs\":\"24\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201609\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"19,944.28\",\"b_sbrs\":\"24\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201608\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,812.6\",\"b_sbrs\":\"25\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201607\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"21,352.6\",\"b_sbrs\":\"25\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201606\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"21,412.6\",\"b_sbrs\":\"25\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201605\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,462.68\",\"b_sbrs\":\"24\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201604\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"20,682.56\",\"b_sbrs\":\"23\"}],\"before_last_year_hg\":\"2015\",\"before_last_year_tax\":\"2015\",\"before_last_year_weg\":\"2015\",\"c\":\"show\",\"c2\":\"show\",\"c2List\":[{\"c2_member_name\":\"吴震宇\",\"c2_member_type\":\"执行董事\"},{\"c2_member_name\":\"潘根林\",\"c2_member_type\":\"监事\"}],\"cList\":[{\"c_before_last_qmye\":\"663,862.39\",\"c_cn_name\":\"货币资金\",\"c_code\":\"1099\",\"c_current_qmye\":\"3,224,759.37\",\"c_last_qmye\":\"3,012,563.95\",\"c_last_time_qmye\":\"3,012,563.95\",\"c_ord\":1},{\"c_before_last_qmye\":\"9,080,981.40\",\"c_cn_name\":\"应收账款\",\"c_code\":\"1122\",\"c_current_qmye\":\"3,569,070.02\",\"c_last_qmye\":\"8,101,770.85\",\"c_last_time_qmye\":\"8,101,770.85\",\"c_ord\":2},{\"c_before_last_qmye\":\"57,488.00\",\"c_cn_name\":\"预付账款\",\"c_code\":\"1123\",\"c_current_qmye\":\"6,937,572.20\",\"c_last_qmye\":\"203,955.00\",\"c_last_time_qmye\":\"203,955.00\",\"c_ord\":3},{\"c_before_last_qmye\":\"4,112,764.89\",\"c_cn_name\":\"存货\",\"c_code\":\"1498\",\"c_current_qmye\":\"5,196,098.29\",\"c_last_qmye\":\"5,079,129.53\",\"c_last_time_qmye\":\"5,079,129.53\",\"c_ord\":4},{\"c_before_last_qmye\":\"29,047,201.15\",\"c_cn_name\":\"流动资产合计\",\"c_code\":\"1499\",\"c_current_qmye\":\"29,447,698.20\",\"c_last_qmye\":\"27,305,419.60\",\"c_last_time_qmye\":\"27,305,419.60\",\"c_ord\":5},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"长期股权投资\",\"c_code\":\"1511\",\"c_current_qmye\":\"8,500,000.00\",\"c_last_qmye\":\"8,500,000.00\",\"c_last_time_qmye\":\"8,500,000.00\",\"c_ord\":6},{\"c_before_last_qmye\":\"1,307,536.96\",\"c_cn_name\":\"固定资产净额\",\"c_code\":\"1698\",\"c_current_qmye\":\"1,188,668.57\",\"c_last_qmye\":\"1,199,353.67\",\"c_last_time_qmye\":\"1,199,353.67\",\"c_ord\":7},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"无形资产\",\"c_code\":\"1701\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":8},{\"c_before_last_qmye\":\"30,354,738.11\",\"c_cn_name\":\"资产总计\",\"c_code\":\"1999\",\"c_current_qmye\":\"39,136,366.77\",\"c_last_qmye\":\"37,004,773.27\",\"c_last_time_qmye\":\"37,004,773.27\",\"c_ord\":9},{\"c_before_last_qmye\":\"2,000,000.00\",\"c_cn_name\":\"短期借款\",\"c_code\":\"2001\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":10},{\"c_before_last_qmye\":\"-346,261.58\",\"c_cn_name\":\"应付账款\",\"c_code\":\"2202\",\"c_current_qmye\":\"843,717.70\",\"c_last_qmye\":\"2,361,897.64\",\"c_last_time_qmye\":\"2,361,897.64\",\"c_ord\":11},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"预收账款\",\"c_code\":\"2203\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":12},{\"c_before_last_qmye\":\"12,297,357.75\",\"c_cn_name\":\"流动负债合计\",\"c_code\":\"2499\",\"c_current_qmye\":\"14,687,496.21\",\"c_last_qmye\":\"13,476,147.62\",\"c_last_time_qmye\":\"13,476,147.62\",\"c_ord\":13},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"长期借款\",\"c_code\":\"2501\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":14},{\"c_before_last_qmye\":\"0.00\",\"c_cn_name\":\"应付债券\",\"c_code\":\"2502\",\"c_current_qmye\":\"0.00\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"0.00\",\"c_ord\":15},{\"c_before_last_qmye\":\"12,297,357.75\",\"c_cn_name\":\"负债合计\",\"c_code\":\"2999\",\"c_current_qmye\":\"14,687,496.21\",\"c_last_qmye\":\"13,476,147.62\",\"c_last_time_qmye\":\"13,476,147.62\",\"c_ord\":16},{\"c_before_last_qmye\":\"18,057,380.36\",\"c_cn_name\":\"所有者权益合计\",\"c_code\":\"4998\",\"c_current_qmye\":\"24,448,870.56\",\"c_last_qmye\":\"23,528,625.65\",\"c_last_time_qmye\":\"23,528,625.65\",\"c_ord\":17},{\"c_before_last_qmye\":\"30,354,738.11\",\"c_cn_name\":\"负债和所有者权益总计\",\"c_code\":\"4999\",\"c_current_qmye\":\"39,136,366.77\",\"c_last_qmye\":\"37,004,773.27\",\"c_last_time_qmye\":\"37,004,773.27\",\"c_ord\":18}],\"cfxx\":\"show\",\"cfxxList\":[],\"current_year_hg\":\"2017\",\"current_year_shui\":\"2017\",\"current_year_tax\":\"2017\",\"current_year_weg\":\"2017\",\"d2\":\"show\",\"d2_abuitem\":\"\",\"d2_cbuitem\":\"\",\"d2_corp_code\":\"57543357-9\",\"d2_corp_name\":\"吴江市新金城不锈钢制品有限公司\",\"d2_credit_code\":\"91320509575433579Q\",\"d2_dom\":\"吴江经济技术开发区厍浜村\",\"d2_entstatus\":\"在营（开业）企业\",\"d2_enttype\":\"有限责任公司(自然人投资或控股)\",\"d2_esdate\":\"2011-05-31\",\"d2_ic_num\":\"320584000293009\",\"d2_industryco\":\"金属制日用品制造\",\"d2_kjsbrs\":58,\"d2_legal_name\":\"吴震宇\",\"d2_legal_per\":\"吴震宇\",\"d2_legal_phone\":\"13776144980\",\"d2_op\":\"2011-05-31至2021-05-30\",\"d2_opscope\":\"不锈钢制品、金属制品、电加热设备、厨房设备、燃气具的生产、销售、安装与维护；厨房设备设计；室内外装饰装修工程设计、施工；水电、机电安装工程设计、施工；建筑材料、办公用品的销售。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\"d2_regcap\":\"2008万元人民币\",\"d2_tel\":\"13776144980\",\"data_df_cate\":\"['201503-04','201505-06','201507-08','201509-10','201511-12','201601-02','201603-04','201605-06','201607-08','201609-10','201611-12','201701-02']\",\"data_df_sery\":\"[]\",\"data_gsrs_cate\":\"['2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1']\",\"data_gsrs_sery\":\"[{'data':[62,62,62,48,48,48,58,58],'name':'公司人数'}]\",\"data_jlr_cate\":\"['2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1']\",\"data_jlr_sery\":\"[{'data':[-845147.88,863536.3,171906.29,-438251.54,-411151.6,327420.5,1473617.22,null],'name':'净利润'}]\",\"data_xdrzye_cate\":\"['2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1','2017Q2']\",\"data_xdrzye_sery\":\"[]\",\"data_yysr_cate\":\"['2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1']\",\"data_yysr_sery\":\"[{'data':[4263501.41,1.281379487E7,1.101946643E7,7723973.39,5392252.94,9303314.83,1.493298344E7,null],'name':'营业收入'}]\",\"data_zz_ye_cate\":\"['2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1']\",\"data_zz_ye_sery\":\"[{'data':[29080.71,290874.78,150084.01,159352.53,131682.71,178286.83,273925.85,92245.23],'name':'增值税与营业税合计'}]\",\"dyxx\":\"show\",\"dyxxList\":[],\"e\":\"show\",\"e_archive_date\":\"2016-03-30 14:11:57\",\"e_curr_time\":\"2017-12-18 10:07:17\",\"e_department_name\":\"苏州股权平台\",\"e_real_name\":\"苏州股权平台\",\"f\":\"show\",\"fList\":[{\"f_beforelastyear\":\"690,980.67\",\"f_create_time\":\"2017-04-12\",\"f_curyear\":\"92,245.23\",\"f_lastyear\":\"743,247.92\",\"f_max_month\":\"1-2月\",\"f_shuiz\":\"增值税\"},{\"f_beforelastyear\":\"186,275.36\",\"f_create_time\":\"2017-01-16\",\"f_curyear\":\"\",\"f_lastyear\":\"251,153.63\",\"f_max_month\":\"\",\"f_shuiz\":\"企业所得税\"},{\"f_beforelastyear\":\"50,717.04\",\"f_create_time\":\"2016-04-08\",\"f_curyear\":\"\",\"f_lastyear\":\"\",\"f_max_month\":\"\",\"f_shuiz\":\"营业税\"},{\"f_beforelastyear\":\"15,000.00\",\"f_create_time\":\"2017-04-12\",\"f_curyear\":\"2,780.00\",\"f_lastyear\":\"15,085.00\",\"f_max_month\":\"1-2月\",\"f_shuiz\":\"个人所得税\"},{\"f_beforelastyear\":\"942,973.07\",\"f_create_time\":\"\",\"f_curyear\":\"95,025.23\",\"f_lastyear\":\"1,009,486.55\",\"f_max_month\":\"-\",\"f_shuiz\":\"合计\"}],\"g\":\"show\",\"gpdb\":\"show\",\"gpdbList\":[],\"h\":\"show\",\"hList\":[],\"hc\":\"show\",\"hcList\":[],\"i\":\"show\",\"iList\":[],\"j\":\"show\",\"jList\":[{\"j_name\":\"一种消毒清洗机\",\"j_pat_cert_no\":\"2016202679755\",\"j_pat_date\":\"2016-09-29\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种智能式开水器\",\"j_pat_cert_no\":\"2016202679736\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种蜂窝式静电油烟净化器\",\"j_pat_cert_no\":\"2016202679717\",\"j_pat_date\":\"2016-11-15\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种节能程控环保开水器\",\"j_pat_cert_no\":\"201620267905X\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种燃气炒菜灶\",\"j_pat_cert_no\":\"2016202678911\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种食物保温柜\",\"j_pat_cert_no\":\"2016202677603\",\"j_pat_date\":\"2016-11-15\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种电磁炉\",\"j_pat_cert_no\":\"2016202676827\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种运水自吸式烟罩\",\"j_pat_cert_no\":\"201620267567X\",\"j_pat_date\":\"2016-11-15\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"一种推车式蒸柜\",\"j_pat_cert_no\":\"2016202621608\",\"j_pat_date\":\"2016-09-11\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"静电式餐饮油烟净化设备\",\"j_pat_cert_no\":\"2015207183535\",\"j_pat_date\":\"2016-01-24\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"热风循环消毒柜\",\"j_pat_cert_no\":\"2014204008421\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"推车式蒸饭车\",\"j_pat_cert_no\":\"2014204008417\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"摇柄下水\",\"j_pat_cert_no\":\"2014204007950\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"高筒下水\",\"j_pat_cert_no\":\"2014204007946\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"蓄热蒸饭车\",\"j_pat_cert_no\":\"2014204007931\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"消音风柜\",\"j_pat_cert_no\":\"2014204007927\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"熄火保护炉灶\",\"j_pat_cert_no\":\"2014204007912\",\"j_pat_date\":\"2014-12-06\",\"j_pat_type\":\"实用新型\"},{\"j_name\":\"环形排风灶\",\"j_pat_cert_no\":\"2013200507750\",\"j_pat_date\":\"2013-06-26\",\"j_pat_type\":\"实用新型\"}],\"jaxx\":\"show\",\"jaxxList\":[{\"jaxx_case_end_amount\":0,\"jaxx_case_end_date\":\"2016-07-18\",\"jaxx_case_end_type\":\"判决\",\"jaxx_case_no\":\"(2016)苏0509民催59号\",\"jaxx_case_person_state\":\"申请人\",\"jaxx_case_reson\":\"申请公示催告\",\"jaxx_case_type\":\"民事特殊\"},{\"jaxx_case_end_amount\":0,\"jaxx_case_end_date\":\"2016-07-18\",\"jaxx_case_end_type\":\"判决\",\"jaxx_case_no\":\"(2016)苏0509民催60号\",\"jaxx_case_person_state\":\"申请人\",\"jaxx_case_reson\":\"申请公示催告\",\"jaxx_case_type\":\"民事特殊\"},{\"jaxx_case_end_amount\":0,\"jaxx_case_end_date\":\"2016-02-19\",\"jaxx_case_end_type\":\"撤诉\",\"jaxx_case_no\":\"(2015)吴江商初字第01894号\",\"jaxx_case_person_state\":\"原告\",\"jaxx_case_reson\":\"股东出资纠纷\",\"jaxx_case_type\":\"民事一审\"},{\"jaxx_case_end_amount\":\"\",\"jaxx_case_end_date\":\"2015-11-18\",\"jaxx_case_end_type\":\"撤诉\",\"jaxx_case_no\":\"(2015)吴江商初字第01892号\",\"jaxx_case_person_state\":\"原告\",\"jaxx_case_reson\":\"股东出资纠纷\",\"jaxx_case_type\":\"民事一审\"}],\"k\":\"show\",\"k2\":\"show\",\"k2List\":[],\"k3\":\"show\",\"k3List\":[],\"kList\":[{\"k_award_date\":\"2015-05-28\",\"k_create_time\":\"2016-11-14\",\"k_evaluate_content\":\"苏州市第八批重合同守信用企业\",\"k_evaluate_grade\":\"\",\"k_evaluate_organ\":\"苏州市人民政府\",\"k_file_no\":\"苏府〔2015〕83号\"}],\"last_year_hg\":\"2016\",\"last_year_tax\":\"2016\",\"last_year_weg\":\"2016\",\"laxx\":\"show\",\"laxxList\":[],\"m\":\"show\",\"mList\":[],\"max_time\":\"201612\",\"mc\":\"show\",\"mcList\":[],\"n\":\"show\",\"nList\":[{\"n_before_last_qmye\":\"39,580,953.37\",\"n_cn_name\":\"主营业务收入\",\"n_code\":\"6001\",\"n_current_qmye\":\"37,352,524.60\",\"n_last_qmye\":\"38,676,849.42\",\"n_last_time_qmye\":\"38,676,849.42\",\"n_ord\":1},{\"n_before_last_qmye\":\"31,781,903.17\",\"n_cn_name\":\"主营业务成本\",\"n_code\":\"6401\",\"n_current_qmye\":\"30,398,068.23\",\"n_last_qmye\":\"31,776,709.63\",\"n_last_time_qmye\":\"31,776,709.63\",\"n_ord\":2},{\"n_before_last_qmye\":\"457,620.79\",\"n_cn_name\":\"营业费用\",\"n_code\":\"6601\",\"n_current_qmye\":\"471,910.99\",\"n_last_qmye\":\"430,465.99\",\"n_last_time_qmye\":\"430,465.99\",\"n_ord\":3},{\"n_before_last_qmye\":\"4,771,833.61\",\"n_cn_name\":\"管理费用\",\"n_code\":\"6602\",\"n_current_qmye\":\"6,076,252.04\",\"n_last_qmye\":\"6,015,768.14\",\"n_last_time_qmye\":\"6,015,768.14\",\"n_ord\":4},{\"n_before_last_qmye\":\"674,339.30\",\"n_cn_name\":\"财务费用\",\"n_code\":\"6603\",\"n_current_qmye\":\"-12,002.85\",\"n_last_qmye\":\"9,140.87\",\"n_last_time_qmye\":\"9,140.87\",\"n_ord\":5},{\"n_before_last_qmye\":\"2,107,019.96\",\"n_cn_name\":\"营业利润\",\"n_code\":\"6799\",\"n_current_qmye\":\"769,700.39\",\"n_last_qmye\":\"660,236.43\",\"n_last_time_qmye\":\"660,236.43\",\"n_ord\":6},{\"n_before_last_qmye\":\"0.00\",\"n_cn_name\":\"投资收益\",\"n_code\":\"6111\",\"n_current_qmye\":\"0.00\",\"n_last_qmye\":\"0.00\",\"n_last_time_qmye\":\"0.00\",\"n_ord\":7},{\"n_before_last_qmye\":\"2,204,114.42\",\"n_cn_name\":\"利润总额\",\"n_code\":\"6899\",\"n_current_qmye\":\"1,004,614.53\",\"n_last_qmye\":\"745,101.43\",\"n_last_time_qmye\":\"745,101.43\",\"n_ord\":8},{\"n_before_last_qmye\":\"1,645,122.94\",\"n_cn_name\":\"净利润\",\"n_code\":\"6999\",\"n_current_qmye\":\"951,634.58\",\"n_last_qmye\":\"471,245.29\",\"n_last_time_qmye\":\"471,245.29\",\"n_ord\":9}],\"o\":\"show\",\"oList\":[{\"o_before_last_index\":\"2.3621\",\"o_cn_name\":\"流动比率\",\"o_current_index\":\"2.0050\",\"o_last_index\":\"2.0262\",\"o_last_time_index\":\"2.0262\",\"o_ord\":1},{\"o_before_last_index\":\"2.0276\",\"o_cn_name\":\"速动比率\",\"o_current_index\":\"1.6512\",\"o_last_index\":\"1.6493\",\"o_last_time_index\":\"1.6493\",\"o_ord\":2},{\"o_before_last_index\":\"40.51%\",\"o_cn_name\":\"资产负债率\",\"o_current_index\":\"37.53%\",\"o_last_index\":\"36.42%\",\"o_last_time_index\":\"36.42%\",\"o_ord\":3},{\"o_before_last_index\":\"13.3815\",\"o_cn_name\":\"存货周转率\",\"o_current_index\":\"5.9168\",\"o_last_index\":\"6.9141\",\"o_last_time_index\":\"6.9141\",\"o_ord\":4},{\"o_before_last_index\":\"5.1965\",\"o_cn_name\":\"应收账款周转率\",\"o_current_index\":\"6.4010\",\"o_last_index\":\"4.5018\",\"o_last_time_index\":\"4.5018\",\"o_ord\":5},{\"o_before_last_index\":\"1.3451\",\"o_cn_name\":\"流动资产周转率\",\"o_current_index\":\"1.3163\",\"o_last_index\":\"1.3727\",\"o_last_time_index\":\"1.3727\",\"o_ord\":6},{\"o_before_last_index\":\"27.8329\",\"o_cn_name\":\"固定资产周转率\",\"o_current_index\":\"31.2832\",\"o_last_index\":\"30.8564\",\"o_last_time_index\":\"30.8564\",\"o_ord\":7},{\"o_before_last_index\":\"1.2831\",\"o_cn_name\":\"总资产周转率\",\"o_current_index\":\"0.9811\",\"o_last_index\":\"1.1484\",\"o_last_time_index\":\"1.1484\",\"o_ord\":8},{\"o_before_last_index\":\"19.70%\",\"o_cn_name\":\"毛利率\",\"o_current_index\":\"18.62%\",\"o_last_index\":\"17.84%\",\"o_last_time_index\":\"17.84%\",\"o_ord\":9},{\"o_before_last_index\":\"4.16%\",\"o_cn_name\":\"净利率\",\"o_current_index\":\"2.55%\",\"o_last_index\":\"1.22%\",\"o_last_time_index\":\"1.22%\",\"o_ord\":10},{\"o_before_last_index\":\"9.53%\",\"o_cn_name\":\"净资产收益率\",\"o_current_index\":\"3.97%\",\"o_last_index\":\"2.27%\",\"o_last_time_index\":\"2.27%\",\"o_ord\":11},{\"o_before_last_index\":\"5.33%\",\"o_cn_name\":\"总资产收益率\",\"o_current_index\":\"2.50%\",\"o_last_index\":\"1.40%\",\"o_last_time_index\":\"1.40%\",\"o_ord\":12}],\"qList\":[{\"q_beforelastyear\":\"15,496.40\",\"q_create_time\":\"2017-03-20\",\"q_curyear\":\"1,583.65\",\"q_is_share\":\"0\",\"q_lastyear\":\"30,377.20\",\"q_max_month\":\"1-2月\",\"q_zhh\":\"12032828100\"}],\"rList\":[],\"r_v_business_tax\":\"show\",\"r_v_corp_count\":\"show\",\"r_v_credit\":\"show\",\"r_v_credit_bscore\":48,\"r_v_credit_companyid\":\"2AEFC8FBA6157C52E053040812AC0DB0\",\"r_v_credit_corpname\":\"吴江市新金城不锈钢制品有限公司\",\"r_v_credit_creditrating\":\"一般\",\"r_v_credit_cscore\":100,\"r_v_credit_escore\":57,\"r_v_credit_fscore\":78,\"r_v_credit_industry\":\"C338\",\"r_v_credit_industry_level\":71,\"r_v_credit_mscore\":100,\"r_v_credit_oscore\":64,\"r_v_credit_ratingclass\":\"grade3\",\"r_v_credit_ratingdate\":\"2017年06月\",\"r_v_credit_region\":\"320509\",\"r_v_credit_region_level\":97,\"r_v_credit_rn\":1,\"r_v_credit_score_level\":70,\"r_v_credit_totalscore\":641,\"r_v_electric\":\"show\",\"r_v_income\":\"show\",\"r_v_loan\":\"show\",\"r_v_profit\":\"show\",\"report_number\":\"98000320171218100717786\",\"sList\":[],\"style\":\"201403\",\"styleType\":\"201403\",\"sxzx\":\"show\",\"sxzxList\":[],\"t\":\"show\",\"tList\":[],\"time_before_last_year_caiwu\":\"2014\",\"time_cfxx_create_time\":\"\",\"time_current_year\":\"2017\",\"time_current_year_caiwu\":\"2016\",\"time_dyxx_create_time\":\"\",\"time_hg_create_time\":\"2017-03-10\",\"time_last_year\":\"2016\",\"time_last_year_caiwu\":\"2015\",\"time_rzdb_create_time\":\"2017-03-24\",\"time_temp\":\"暂无数据\",\"time_template_type\":\"201401\",\"time_txxx_create_time\":\"\",\"time_xdxx_create_time\":\"\",\"time_xedk_create_time\":\"2017-04-16\",\"time_zcfzb_current_month\":\"12\",\"time_zl_create_time\":\"2017-04-10\",\"txxx\":\"show\",\"txxxList\":[],\"weg\":\"show\",\"xdxx\":\"show\",\"xdxxList\":[],\"z\":\"show\",\"zList\":[]}]";
			// String
			// jsontu="[{\"time_zcfzb_current_month\":\"08\"},{\"r_v_income\":\"show\"},{\"d2_update_date\":1447122834000},{\"data_gsrs_sery\":\"[{'data':[987,641,607,580,615,438,434,415],'name':'公司人数'}]\"},{\"d2_credit_code\":\"xxx\"},{\"o_rate_margin_3\":\"5.27%\"},{\"time_hg_create_time\":\"2017-03-10\"},{\"o_rate_margin_2\":\"7.30%\"},{\"b2\":\"show\"},{\"o_rate_margin_1\":\"8.41%\"},{\"o_rate_margin_0\":\"7.61%\"},{\"e_archive_date\":\"2015-11-10 10:33:54\"},{\"nList\":[{\"n_before_last_qmye\":\"1,044,212,012.28\",\"n_cn_name\":\"主营业务收入\",\"n_code\":\"6001\",\"n_current_qmye\":\"934,723,901.01\",\"n_last_qmye\":\"1,102,827,126.11\",\"n_last_time_qmye\":\"749,596,742.44\",\"n_ord\":1},{\"n_before_last_qmye\":\"964,785,304.93\",\"n_cn_name\":\"主营业务成本\",\"n_code\":\"6401\",\"n_current_qmye\":\"885,462,209.45\",\"n_last_qmye\":\"1,010,097,162.09\",\"n_last_time_qmye\":\"694,868,169.14\",\"n_ord\":2},{\"n_before_last_qmye\":\"\",\"n_cn_name\":\"营业费用\",\"n_code\":\"6601\",\"n_current_qmye\":\"\",\"n_last_qmye\":\"0.00\",\"n_last_time_qmye\":\"0.00\",\"n_ord\":3},{\"n_before_last_qmye\":\"7,714,344.01\",\"n_cn_name\":\"管理费用\",\"n_code\":\"6602\",\"n_current_qmye\":\"6,496,825.78\",\"n_last_qmye\":\"8,901,386.22\",\"n_last_time_qmye\":\"5,351,766.94\",\"n_ord\":4},{\"n_before_last_qmye\":\"6,504,314.73\",\"n_cn_name\":\"财务费用\",\"n_code\":\"6603\",\"n_current_qmye\":\"3,579,648.51\",\"n_last_qmye\":\"14,225,359.46\",\"n_last_time_qmye\":\"4,505,524.80\",\"n_ord\":5},{\"n_before_last_qmye\":\"31,296,255.42\",\"n_cn_name\":\"营业利润\",\"n_code\":\"6799\",\"n_current_qmye\":\"11,438,693.38\",\"n_last_qmye\":\"33,250,789.13\",\"n_last_time_qmye\":\"20,257,001.38\",\"n_ord\":6},{\"n_before_last_qmye\":\"1,500,000.00\",\"n_cn_name\":\"投资收益\",\"n_code\":\"6111\",\"n_current_qmye\":\"\",\"n_last_qmye\":\"600,000.00\",\"n_last_time_qmye\":\"\",\"n_ord\":7},{\"n_before_last_qmye\":\"42,879,770.96\",\"n_cn_name\":\"利润总额\",\"n_code\":\"6899\",\"n_current_qmye\":\"11,401,496.47\",\"n_last_qmye\":\"33,291,607.11\",\"n_last_time_qmye\":\"19,697,819.36\",\"n_ord\":8},{\"n_before_last_qmye\":\"35,137,690.58\",\"n_cn_name\":\"净利润\",\"n_code\":\"6999\",\"n_current_qmye\":\"11,401,496.47\",\"n_last_qmye\":\"25,118,705.33\",\"n_last_time_qmye\":\"11,888,814.73\",\"n_ord\":9}]},{\"before_last_year_weg\":\"2015\"},{\"d2_corp_area\":\"\"},{\"time_temp\":\"暂无数据\"},{\"time_current_year_caiwu\":\"2016\"},{\"c2\":\"show\"},{\"d2_opscope\":\"房屋xxx筑安装、市政工程、xxx筑装修装饰工程、消防设施工程、钢结构工程、地基与基础工程、机电设备安装的设计与施工。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\"},{\"d2_corp_name\":\"xxx有限公司\"},{\"before_last_year_hg\":\"2015\"},{\"cList\":[{\"c_before_last_qmye\":\"48,603,037.96\",\"c_cn_name\":\"货币资金\",\"c_code\":\"1099\",\"c_current_qmye\":\"100,330,548.83\",\"c_last_qmye\":\"93,122,827.58\",\"c_last_time_qmye\":\"98,600,383.63\",\"c_ord\":1},{\"c_before_last_qmye\":\"270,137,210.46\",\"c_cn_name\":\"应收账款\",\"c_code\":\"1122\",\"c_current_qmye\":\"634,559,653.27\",\"c_last_qmye\":\"342,515,003.39\",\"c_last_time_qmye\":\"395,248,579.55\",\"c_ord\":2},{\"c_before_last_qmye\":\"40,907,455.60\",\"c_cn_name\":\"预付账款\",\"c_code\":\"1123\",\"c_current_qmye\":\"103,366,024.28\",\"c_last_qmye\":\"130,565,311.32\",\"c_last_time_qmye\":\"219,316,069.63\",\"c_ord\":3},{\"c_before_last_qmye\":\"207,602,142.87\",\"c_cn_name\":\"存货\",\"c_code\":\"1498\",\"c_current_qmye\":\"205,602,795.11\",\"c_last_qmye\":\"67,525,538.42\",\"c_last_time_qmye\":\"21,242,561.69\",\"c_ord\":4},{\"c_before_last_qmye\":\"842,307,961.31\",\"c_cn_name\":\"流动资产合计\",\"c_code\":\"1499\",\"c_current_qmye\":\"1,305,644,790.55\",\"c_last_qmye\":\"873,572,400.02\",\"c_last_time_qmye\":\"1,056,545,827.62\",\"c_ord\":5},{\"c_before_last_qmye\":\"17,200,000.00\",\"c_cn_name\":\"长期股权投资\",\"c_code\":\"1511\",\"c_current_qmye\":\"12,300,000.00\",\"c_last_qmye\":\"12,300,000.00\",\"c_last_time_qmye\":\"12,300,000.00\",\"c_ord\":6},{\"c_before_last_qmye\":\"10,612,430.58\",\"c_cn_name\":\"固定资产净额\",\"c_code\":\"1698\",\"c_current_qmye\":\"11,843,435.43\",\"c_last_qmye\":\"12,131,226.95\",\"c_last_time_qmye\":\"12,415,818.47\",\"c_ord\":7},{\"c_before_last_qmye\":\"2,107,258.88\",\"c_cn_name\":\"无形资产\",\"c_code\":\"1701\",\"c_current_qmye\":\"2,107,258.88\",\"c_last_qmye\":\"2,107,258.88\",\"c_last_time_qmye\":\"2,107,258.88\",\"c_ord\":8},{\"c_before_last_qmye\":\"872,256,507.37\",\"c_cn_name\":\"资产总计\",\"c_code\":\"1999\",\"c_current_qmye\":\"1,347,211,297.09\",\"c_last_qmye\":\"912,570,556.19\",\"c_last_time_qmye\":\"1,092,649,775.52\",\"c_ord\":9},{\"c_before_last_qmye\":\"217,000,000.00\",\"c_cn_name\":\"短期借款\",\"c_code\":\"2001\",\"c_current_qmye\":\"134,000,000.00\",\"c_last_qmye\":\"138,000,000.00\",\"c_last_time_qmye\":\"138,000,000.00\",\"c_ord\":10},{\"c_before_last_qmye\":\"247,030,120.78\",\"c_cn_name\":\"应付账款\",\"c_code\":\"2202\",\"c_current_qmye\":\"716,185,716.15\",\"c_last_qmye\":\"253,810,659.14\",\"c_last_time_qmye\":\"289,064,407.00\",\"c_ord\":11},{\"c_before_last_qmye\":\"32,494,615.48\",\"c_cn_name\":\"预收账款\",\"c_code\":\"2203\",\"c_current_qmye\":\"15,009,803.01\",\"c_last_qmye\":\"51,464,053.08\",\"c_last_time_qmye\":\"214,604,942.89\",\"c_ord\":12},{\"c_before_last_qmye\":\"636,794,418.97\",\"c_cn_name\":\"流动负债合计\",\"c_code\":\"2499\",\"c_current_qmye\":\"1,082,699,736.78\",\"c_last_qmye\":\"656,284,937.37\",\"c_last_time_qmye\":\"848,094,047.30\",\"c_ord\":13},{\"c_before_last_qmye\":\"\",\"c_cn_name\":\"长期借款\",\"c_code\":\"2501\",\"c_current_qmye\":\"\",\"c_last_qmye\":\"0.00\",\"c_last_time_qmye\":\"\",\"c_ord\":14},{\"c_before_last_qmye\":\"\",\"c_cn_name\":\"应付债券\",\"c_code\":\"2502\",\"c_current_qmye\":\"\",\"c_last_qmye\":\"\",\"c_last_time_qmye\":\"\",\"c_ord\":15},{\"c_before_last_qmye\":\"636,794,418.97\",\"c_cn_name\":\"负债合计\",\"c_code\":\"2999\",\"c_current_qmye\":\"1,082,699,736.78\",\"c_last_qmye\":\"656,284,937.37\",\"c_last_time_qmye\":\"848,094,047.30\",\"c_ord\":16},{\"c_before_last_qmye\":\"235,462,088.40\",\"c_cn_name\":\"所有者权益合计\",\"c_code\":\"4998\",\"c_current_qmye\":\"264,511,560.31\",\"c_last_qmye\":\"256,285,618.82\",\"c_last_time_qmye\":\"244,555,728.22\",\"c_ord\":17},{\"c_before_last_qmye\":\"872,256,507.37\",\"c_cn_name\":\"负债和所有者权益总计\",\"c_code\":\"4999\",\"c_current_qmye\":\"1,347,211,297.09\",\"c_last_qmye\":\"912,570,556.19\",\"c_last_time_qmye\":\"1,092,649,775.52\",\"c_ord\":18}]},{\"txxxList\":[]},{\"d2_dom\":\"xxx\"},{\"o_liqui_ratio_3\":1.016},{\"o_total_ass_growth_rate_1\":\"4.56%\"},{\"o_total_ass_growth_rate_0\":\"40.73%\"},{\"a2List\":[]},{\"o_total_ass_growth_rate_3\":\"48.28%\"},{\"jaxx\":\"show\"},{\"o_total_ass_growth_rate_2\":\"25.20%\"},{\"jaxxList\":[{\"jaxx_case_end_amount\":0,\"jaxx_case_end_date\":\"2017-03-31\",\"jaxx_case_end_type\":\"维持\",\"jaxx_case_no\":\"(2016)xxx05xxxxxx1812xxx\",\"jaxx_case_person_state\":\"被告\",\"jaxx_case_reson\":\"xxx设工程施工合同纠纷\",\"jaxx_case_type\":\"xxx事二审\"}]},{\"current_year_weg\":\"2017\"},{\"o_liqui_ratio_0\":0.9967},{\"o_liqui_ratio_2\":1.2207},{\"d2\":\"show\"},{\"o_liqui_ratio_1\":1.2282},{\"data_zz_ye_cate\":\"['2015Q1','2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4']\"},{\"sxzx\":\"show\"},{\"rList\":[]},{\"data_df_cate\":\"['201503-04','201505-06','201507-08','201509-10','201511-12','201601-02','201603-04','201605-06','201607-08','201609-10','201611-12','201701-02']\"},{\"b\":\"show\"},{\"c\":\"show\"},{\"d2_contacts\":\"宁健\"},{\"e\":\"show\"},{\"f\":\"show\"},{\"g\":\"show\"},{\"h\":\"show\"},{\"i\":\"show\"},{\"j\":\"show\"},{\"k\":\"show\"},{\"m\":\"show\"},{\"data_yysr_cate\":\"['2015Q1','2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4']\"},{\"n\":\"show\"},{\"k3List\":[]},{\"o\":\"show\"},{\"d2_contact_phone\":\"\"},{\"before_last_year_tax\":\"2015\"},{\"t\":\"show\"},{\"d2_enttype\":\"有限责任公司(自然人投资或控股)\"},{\"d2_version\":0},{\"dyxx\":\"show\"},{\"z\":\"show\"},{\"time_xedk_create_time\":\"2017-04-16\"},{\"time_zl_create_time\":\"\"},{\"r_v_electric\":\"show\"},{\"cfxx\":\"show\"},{\"time_txxx_create_time\":\"2012-01\"},{\"mList\":[]},{\"d2_cbuitem\":\"\"},{\"max_time\":\"201608\"},{\"o_net_ass_reward_2\":\"4.95%\"},{\"o_net_profit_margin_0\":\"3.36%\"},{\"o_net_ass_reward_3\":\"4.41%\"},{\"o_net_profit_margin_1\":\"2.28%\"},{\"o_net_ass_reward_0\":\"16.00%\"},{\"o_net_profit_margin_2\":\"1.59%\"},{\"o_net_ass_reward_1\":\"10.22%\"},{\"o_net_profit_margin_3\":\"1.22%\"},{\"sxzxList\":[]},{\"data_xdrzye_cate\":\"['2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1','2017Q2']\"},{\"xdxxList\":[{\"xdxx_currency\":\"人xxx币\",\"xdxx_data_time\":\"2017-02\",\"xdxx_finance_no\":\"广发xxx\",\"xdxx_finance_type\":1,\"xdxx_five_category\":\"正常\",\"xdxx_guarantee_type\":\"保证\",\"xdxx_loan_date_expire\":\"2017-05-22\",\"xdxx_loan_date_send\":\"2016-06-27\",\"xdxx_loan_num_balance\":\"10,000,000.00\",\"xdxx_ord_num\":10000000}]},{\"r_v_corp_count\":\"show\"},{\"o_total_ass_reward_2\":\"1.21%\"},{\"o_total_ass_reward_1\":\"2.81%\"},{\"o_total_ass_reward_0\":\"4.71%\"},{\"txxx\":\"show\"},{\"laxxList\":[{\"laxx_case_create_amount\":\"\",\"laxx_case_create_date\":\"2017-04-12\",\"laxx_case_no\":\"(2017)xxx05xxx507xxx\",\"laxx_case_person_state\":\"被告\",\"laxx_case_reson\":\"xxx间借贷纠纷\",\"laxx_case_status\":\"审理\",\"laxx_case_type\":\"xxx事二审\"}]},{\"cfxxList\":[]},{\"o_total_ass_reward_3\":\"1.01%\"},{\"c2List\":[{\"c2_member_name\":\"xxx\",\"c2_member_type\":\"董事长\"},{\"c2_member_name\":\"xxx\",\"c2_member_type\":\"董事\"},{\"c2_member_name\":\"xxx\",\"c2_member_type\":\"董事\"},{\"c2_member_name\":\"xxx\",\"c2_member_type\":\"监事\"},{\"c2_member_name\":\"xxx\",\"c2_member_type\":\"其他人员\"},{\"c2_member_name\":\"xxx\",\"c2_member_type\":\"其他人员\"}]},{\"current_year_tax\":\"2017\"},{\"mcList\":[]},{\"current_year_hg\":\"2017\"},{\"b2List\":[{\"b2_controlling_share\":\"96.00%\",\"b2_inv\":\"xxx\",\"b2_invtype\":\"自然人股东\",\"b2_subconam\":\"14400\"},{\"b2_controlling_share\":\"4.00%\",\"b2_inv\":\"xxx有限公司\",\"b2_invtype\":\"企业法人\",\"b2_subconam\":\"600\"}]},{\"data_jlr_sery\":\"[{'data':[7513889.84,5867481.8,4126397.15,7610936.54,883743.45,1.024094203E7,null,null],'name':'净利润'}]\"},{\"xdxx\":\"show\"},{\"o_curr_ass_turn_2\":0.7893},{\"o_curr_ass_turn_1\":1.2851},{\"o_curr_ass_turn_0\":1.4475},{\"last_year_weg\":\"2016\"},{\"o_curr_ass_turn_3\":0.8594},{\"dyxxList\":[{\"dyxx_address\":\"xxx街1xxxxxx1幢3013室\",\"dyxx_amount\":\"0.0036\",\"dyxx_bustype\":\"不动产\",\"dyxx_currency\":\"人xxx币\",\"dyxx_enddate\":\"2019-06-27\",\"dyxx_housearea\":\"27.53\",\"dyxx_landarea\":\"5.86\",\"dyxx_mortga\":\"xxxxxx股份有限公司xxx州分行\",\"dyxx_startdate\":\"2016-06-27\",\"dyxx_warrnum\":\"xxx（2016）xxx明xxx8019671xxx\"}]},{\"zList\":[]},{\"gpdbList\":[]},{\"time_template_type\":\"201401\"},{\"d2_op\":\"1990-07-05至\"},{\"o_time_3\":\"201608\"},{\"o_time_2\":\"201508\"},{\"o_time_1\":\"201512\"},{\"o_time_0\":\"201412\"},{\"qList\":[{\"q_beforelastyear\":\"3150.00\",\"q_create_time\":\"2017-03-10\",\"q_curyear\":\"2478.60\",\"q_is_share\":\"0\",\"q_lastyear\":\"13608.00\",\"q_max_month\":\"1-2月\",\"q_zhh\":\"11190828\"},{\"q_beforelastyear\":\"35.00\",\"q_create_time\":\"2016-12-15\",\"q_curyear\":\"\",\"q_is_share\":\"1\",\"q_lastyear\":\"1960.00\",\"q_max_month\":\"1-10月\",\"q_zhh\":\"11190824\"},{\"q_beforelastyear\":\"7063.00\",\"q_create_time\":\"2016-04-08\",\"q_curyear\":\"\",\"q_is_share\":\"0\",\"q_lastyear\":\"\",\"q_max_month\":\"1-7月\",\"q_zhh\":\"11190776\"},{\"q_beforelastyear\":\"10248.00\",\"q_create_time\":\"\",\"q_curyear\":\"2478.60\",\"q_is_share\":\"\",\"q_lastyear\":\"15568.00\",\"q_max_month\":\"\",\"q_zhh\":\"总计\"}]},{},{\"hList\":[]},{\"d2_enabled\":\"2\"},{\"d2_ic_num\":\"320506000058768\"},{\"current_year_shui\":\"2017\"},{\"laxx\":\"show\"},{\"time_last_year\":\"2016\"},{\"o_assent_liab_ratio_3\":\"80.37%\"},{\"o_assent_liab_ratio_2\":\"77.62%\"},{\"last_year_tax\":\"2016\"},{\"o_assent_liab_ratio_1\":\"71.92%\"},{\"o_assent_liab_ratio_0\":\"73.01%\"},{\"d2_industryco\":\"房屋xxx筑业\"},{\"styleType\":\"201403\"},{\"d2_create_date\":1444979924000},{\"data_jlr_cate\":\"['2015Q1','2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4']\"},{\"report_number\":\"17049452\"},{\"gpdb\":\"show\"},{\"e_department_name\":\"征信公司业务管理\"},{\"time_dyxx_create_time\":\"2017-02-04\"},{\"k2List\":[]},{\"data_df_sery\":\"[{'data':[120060.05,148413.17,203765.41,152132.68,null,null,null,null,null,null,null,null],'name':'电费'}]\"},{\"d2_abuitem\":\"\"},{\"o_total_ass_turn_1\":1.2354},{\"o_total_ass_turn_2\":0.7628},{\"o_total_ass_turn_3\":0.8287},{\"k2\":\"show\"},{\"k3\":\"show\"},{},{\"o_total_ass_turn_0\":1.3997},{\"d2_create_by\":\"402881f148c5b43f0148c5be28650024\"},{\"time_before_last_year_caiwu\":\"2014\"},{\"d2_id\":\"ff80808150ccbacb0150d65a855a0f22\"},{\"data_zz_ye_sery\":\"[{'data':[9455077.19,1.23103173E7,5333384.78,3225072.16,1.055056705E7,1.307379453E7,null,null],'name':'增值税与营业税合计'}]\"},{\"d2_tel\":\"13913560559\"},{\"e_curr_time\":\"2017-05-25 16:24:29\"},{\"data_gsrs_cate\":\"['2015Q2','2015Q3','2015Q4','2016Q1','2016Q2','2016Q3','2016Q4','2017Q1']\"},{\"time_xdxx_create_time\":\"2017-03\"},{\"iList\":[{\"i_create_time\":\"2016-12-20\",\"i_illegal_act\":\"经查，xxx行为。\",\"i_pun_content\":\"处xxx种类：xxx款,xxx款金额：127312.00\",\"i_pun_deci\":\"xxxxxxxxx字〔2015〕xxx23xxx\",\"i_pun_time\":\"2015-06-12\",\"i_pun_type\":\"xxxxxx市住xxx局\",\"i_skssqsr\":\"\"}]},{\"time_cfxx_create_time\":\"2016-05-27\"},{\"data_yysr_sery\":\"[{'data':[2.684787514E8,4.8111799104E8,1.8205846052E8,1.7117192315E8,3.9867314066E8,4.6570552203E8,null,null],'name':'营业收入'}]\"},{\"weg\":\"show\"},{\"r_v_business_tax\":\"show\"},{\"d2_esdate\":\"1990-07-05\"},{\"o_accou_rece_turn_3\":1.9133},{\"o_accou_rece_turn_2\":2.2531},{\"o_accou_rece_turn_1\":3.6002},{\"o_accou_rece_turn_0\":5.9959},{\"last_year_hg\":\"2016\"},{\"o_inven_turn_ratio_0\":7.5909},{\"o_inven_turn_ratio_3\":6.5802},{\"o_inven_turn_ratio_2\":10.2195},{\"o_inven_turn_ratio_1\":11.0834},{\"mc\":\"show\"},{\"time_current_year\":\"2017\"},{\"o_curr_ratio_1\":1.3311},{\"d2_regcap\":\"15000万元人xxx币\"},{\"o_curr_ratio_2\":1.2458},{\"o_curr_ratio_3\":1.2059},{\"time_last_year_caiwu\":\"2015\"},{\"bList\":[{\"b_amount\":\"\",\"b_deposit_month\":\"201703\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"42,311.97\",\"b_sbrs\":\"53\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201702\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"43,914.15\",\"b_sbrs\":\"55\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201701\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"43,113.06\",\"b_sbrs\":\"54\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201612\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"43,914.15\",\"b_sbrs\":\"55\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201611\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"45,516.33\",\"b_sbrs\":\"57\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201610\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"56,076.3\",\"b_sbrs\":\"70\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201609\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"56,877.39\",\"b_sbrs\":\"71\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201608\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"56,877.39\",\"b_sbrs\":\"71\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201607\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"59,814.72\",\"b_sbrs\":\"72\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201606\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"59,814.72\",\"b_sbrs\":\"72\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201605\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"60,645.48\",\"b_sbrs\":\"73\"},{\"b_amount\":\"\",\"b_deposit_month\":\"201604\",\"b_deposit_propor\":\"\",\"b_person_num\":\"\",\"b_sbje\":\"61,476.24\",\"b_sbrs\":\"74\"}]},{\"d2_legal_per\":\"宁健\"},{\"d2_legal_name\":\"宁健\"},{\"kList\":[{\"k_award_date\":\"2015-03-12\",\"k_create_time\":\"2016-11-14\",\"k_evaluate_content\":\"2014年xxxxxx市xxx筑施工企业综合考评\",\"k_evaluate_grade\":\"房屋xxx筑: C\",\"k_evaluate_organ\":\"xxxxxx市住房和城乡xxx设局\",\"k_file_no\":\"xxx住xxx〔2015〕55xxx\"}]},{\"tList\":[]},{\"r_v_profit\":\"show\"},{\"d2_corp_code\":\"13822482-4\"},{\"sList\":[]},{\"time_rzdb_create_time\":\"2017-03-24\"},{\"jList\":[]},{\"data_xdrzye_sery\":\"[]\"},{\"d2_entstatus\":\"在营（开业）企业\"},{\"o_curr_ratio_0\":1.3227},{\"fList\":[{\"f_beforelastyear\":\"7,159,753.87\",\"f_create_time\":\"2016-08-03\",\"f_curyear\":\"\",\"f_lastyear\":\"2,774,971.37\",\"f_max_month\":\"\",\"f_shuiz\":\"企业所得税\"},{\"f_beforelastyear\":\"30,323,851.43\",\"f_create_time\":\"2016-07-06\",\"f_curyear\":\"\",\"f_lastyear\":\"23,624,361.58\",\"f_max_month\":\"\",\"f_shuiz\":\"营业税\"},{\"f_beforelastyear\":\"37,483,605.30\",\"f_create_time\":\"\",\"f_curyear\":\"\",\"f_lastyear\":\"26,399,332.95\",\"f_max_month\":\"-\",\"f_shuiz\":\"合计\"}]},{\"a2\":\"show\"},{\"e_real_name\":\"xxx\"},{\"o_fixed_ass_turn_0\":163.1979},{\"d2_legal_phone\":\"\"},{\"o_fixed_ass_turn_3\":77.976},{\"r_v_loan\":\"show\"},{\"o_fixed_ass_turn_2\":65.1024},{\"o_fixed_ass_turn_1\":96.9789},{\"d2_update_by\":\"402881f148c5b43f0148c5be28650024\"}]";
			log.info("查询企业或机构:" + creditCode + "的信用报告");
			// 首先判断当前的用户是否为投资机构用户
			// 判断当前的需求的状态
			Map<String, String> userInfos = RedisGetValue.getUserInfo(request);
			// 未登录强制登录
			if (userInfos.isEmpty()) {
				noLogin(request, response);
			}

			String orgNo = userInfos.get(SRRPConstant.LOGINORGNO);
			String userName = userInfos.get(SRRPConstant.LOGINUSERNAME);
			// 根据用户的id查询用户信息用户
			PlatformUser platformUser = serService.getUser(userId);
			if("01".equals(platformUser.getType())){
				flag=true;
			}
			vo= reportService.getReportVo(creditCode,flag);
			//约谈阶段：公开名称脱敏 
            if(!StringUtils.isEmpty(showFlag)){
                if(showFlag.equals("1")||showFlag.equals("2")){
                    vo.setCorp_name(enterpriseName(vo.getCorp_name()));
                }
            }else{
                if("01".equals(platformUser.getType())){
                    showFlag="0";
                }else{
                    showFlag="3";
                }
            }
			querylog.setEndtime(new Date());
			querylog.setIssuc("1");// 查询成功
			request.setAttribute("reportVo", vo);
			request.setAttribute("showFlag", showFlag);
			if (vo.getExceptionMeessage() != null) {
				path = "WEB-INF/views/creportscore/error";
			} else {
				path = "WEB-INF/views/creportscore/report/reportVeiws";
			}
			queryService.addquerylog(querylog);
		} catch (Exception e) {
		    e.printStackTrace();
			querylog.setEndtime(new Date());
			querylog.setIssuc("3");// 查询失败
			queryService.addquerylog(querylog);
			// 捕捉查询信用报告超时
			if(!StringUtils.isEmpty(e.getMessage())){
			    if (e.getMessage().contains("timed out")) {
	                vo = new ReportVO();
	                vo.setExceptionMeessage("查看信用报告超时，请稍后再试!");
	                request.setAttribute("reportVo", vo);
	                if("1".equals(showFlag)){
	                    request.setAttribute("showFlag", showFlag);
	                }
	                path = "WEB-INF/views/creportscore/error";
	                return path;
	            }else{
	                vo = new ReportVO();
	                vo.setExceptionMeessage("查看信用报告返回错误，请稍后再试!");
	                request.setAttribute("reportVo", vo);
	                path = "WEB-INF/views/creportscore/error";
	                return path;
	            }
			}else{
			    vo = new ReportVO();
			    vo.setExceptionMeessage("查看信用报告返回错误，请稍后再试!");
                request.setAttribute("reportVo", vo);
                path = "WEB-INF/views/creportscore/error";
                return path;
			}
			
		}
		return path;

	}

	/**
	 * 信用评分查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryScore")
	public String queryScore(HttpServletRequest request,
			HttpServletResponse response) {
		log.info("==========init==========is===========queryScore===========");
		QueryLog querylog = new QueryLog();
		CreditScoreBean csb = new CreditScoreBean();
		try {
		  String userId = RedisGetValue.getRedisUser(request, "userId");
		  String userType = RedisGetValue.getRedisUser(request, "orgNo");// 用户类型
			querylog.setBegintime(new Date());
			String creditType = request.getParameter("certtype");// 证件类型
			String creditCode = request.getParameter("certno");// 证件号码
			creditType=AESUtil.desEncrypt(creditType,"123456qwerty1111");
			creditCode = AESUtil.desEncrypt(creditCode,"123456qwerty1111");
			 //查看未读消息完之后删除异议提醒
			if(SRRPConstant.USER_TYPE_03.equals(userType)){
			    CompanyBase base=companyInfoService.queryByCertno(creditCode);
	            if(!StringUtils.isEmpty(base)){
	                manageDissentService.deleteByMessageId(base.getEnterpriseId(),userId,SRRPConstant.MESSAGE_TYPE_01);
	            }else{
	                Investor invest=investorService.findInvestor(creditCode);
	                if(!StringUtils.isEmpty(invest)){
	                    manageDissentService.deleteByMessageId(invest.getInvestorId(),userId,SRRPConstant.MESSAGE_TYPE_03);
	                }
	            }
			}
          
			RpCompanyCreditscores s = scoreService.queryScore(creditType,
					creditCode);
			if (notEmpty(s)) {
				// 存储机构代码
				String corpcredit = s.getCreditcode() == null ? "" : s
						.getCreditcode();
				csb.setCorpcredit(corpcredit);
				csb.setLevel(s.getLevel());
				// 雷达图
				String radar = s.getRadarjson();
				if (null != radar) {
					JSONObject vojoc = JSONObject.fromObject(radar);
					csb.setCreditscore(JsonUtil.getInt(vojoc, "creditscore"));
					csb.setRating(JsonUtil.getString(vojoc, "rating") == null ? ""
							: JsonUtil.getString(vojoc, "rating"));
					csb.setRadarResult(radar);
				}
				// 柱状图
				try {
					JSONObject bar = new JSONObject()
							.fromObject(s.getBarjson());
					csb.setAllData(bar.getString("totalinfo"));// 全部
					csb.setAreaData(bar.getString("areainfo"));// 区域
					csb.setTradeData(bar.getString("industryinfo"));// 行业
					csb.setScaleData(bar.getString("scaleinfo"));//
					csb.setEntiData(bar.getString("entinfo"));//
					csb.setInvestmentData(bar.getString("investmentinfo"));//
					queryBarData(csb, bar);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 折线图
				JSONObject lineDate = null;
				try {
					lineDate = queryLineData(s.getLinejson());
					csb.setLineData(lineDate.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 日志
			querylog.setCertno(creditCode);
			querylog.setCerttype(creditType);
			querylog.setIssuc("1");// 查询成功
			querylog.setUserid(userId);
			querylog.setQuerytype("2");// 查询类型 2 信用评分
			querylog.setEndtime(new Date());
		} catch (Exception e) {
			querylog.setIssuc("3");// 查询异常
			e.printStackTrace();
		} finally {
			queryService.addquerylog(querylog);
		}
		request.setAttribute("creditScoreBean", csb);
		return "WEB-INF/views/creportscore/score/creditScore";
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
        if (org.apache.commons.lang3.StringUtils.isBlank(name)) {
            return "";
        }
        int length = org.apache.commons.lang3.StringUtils.length(name);
        return org.apache.commons.lang3.StringUtils.rightPad(org.apache.commons.lang3.StringUtils.left(name, sensitiveSize),
                length, "*").concat(org.apache.commons.lang3.StringUtils.right(name.trim(), 4));
    }
	/**
	 * 雷达图
	 * 
	 * @param paramVO
	 * @return
	 * @throws Exception
	 */
	// public static JSONObject queryRadarData(String value,int score) throws
	// Exception {
	// Map<String, String> map = CapConstant.SCORE_TYPE_ZW_MAP;
	// int i = 0;
	// JSONArray jarray = new JSONArray();
	// JSONObject jobj = new JSONObject();
	// JSONObject vojo = JSONObject.fromObject(value);
	// double valueArray[] = new double[map.size()];
	// Iterator<String> iterator = map.keySet().iterator();
	// while (iterator.hasNext()) {
	// JSONObject newJobj = new JSONObject();
	// String key = (String) iterator.next();
	// newJobj.put("name", map.get(key));
	// newJobj.put("max", 1);
	// valueArray[i] = new
	// BigDecimal(Double.parseDouble(vojo.getString(key.toLowerCase()))/score).setScale(2,
	// BigDecimal.ROUND_HALF_UP).doubleValue();
	// jarray.add(newJobj);
	// i++;
	// }
	// jobj.put("title", 0);
	// jobj.put("param", jarray);
	// jobj.put("value", valueArray);
	// return jobj;
	// }

	/**
	 * 柱状图
	 */
	public static void queryBarData(CreditScoreBean csb, JSONObject bar)
			throws Exception {
		csb.setBarParam(bar.getString("XAxis"));
		new JSONObject();
		JSONObject totalinfo = JSONObject
				.fromObject(bar.getString("totalinfo")); //
		csb.setBarData(totalinfo.getJSONArray("corpNum").toString());// data
		csb.setMidLevel(Integer.parseInt(totalinfo.getString("midLevel")));
		csb.setMidName(totalinfo.getString("midName"));
		csb.setMidScore(totalinfo.getString("midScore"));
		csb.setTotal(Integer.parseInt(totalinfo.getString("totalCorp")));
		csb.setRankPercent(totalinfo.getString("rankPercent"));
	}

	/**
	 * 折线图
	 */
	public static JSONObject queryLineData(String lineDate) throws Exception {
		JSONObject datajo = JSONObject.fromObject(lineDate);
		String data = datajo.getString("data");
		String month = datajo.getString("monthstr");
		JSONObject jdata = new JSONObject();
		jdata.put("month", month);
		jdata.put("data", data);
		return jdata;
	}

	public static boolean notEmpty(RpCompanyCreditscores s) {
		boolean r = true;
		if (null == s) {
			r = false;
		}
		if (r) {
			if (s.equals("")) {
				r = false;
			} else {
				r = true;
			}
		}
		return r;
	}

}

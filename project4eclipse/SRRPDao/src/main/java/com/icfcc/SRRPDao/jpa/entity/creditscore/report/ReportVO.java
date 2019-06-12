package com.icfcc.SRRPDao.jpa.entity.creditscore.report;

import java.util.List;

import com.icfcc.SRRPDao.jpa.entity.creditscore.report.BDCVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ColumnAndListVO;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ComplaintInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.EvaluateInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.LoanInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.NegativeInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.OperateInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.PatentInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.SeniorExecutiveInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.ShareholderInfoVo;
import com.icfcc.SRRPDao.jpa.entity.creditscore.report.SocialSecurityVo;

public class ReportVO implements ICapVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7388445674439889162L;
	/***
	 * 重要指标历史趋势图
	 */
	//营业收入
	private String data_yysr_cate ="data_yysr_cate";
	private String data_yysr_sery ="data_yysr_sery";
	
	//净利润
	private String data_jlr_cate ="data_jlr_cate";
	private String data_jlr_sery ="data_jlr_sery";
	
	//增值税与营业税合计
	private String data_zz_ye_cate ="data_zz_ye_cate";
	private String data_zz_ye_sery ="data_zz_ye_sery";
	
	//公司人数
	private String data_gsrs_cate ="data_gsrs_cate";
	private String data_gsrs_sery ="data_gsrs_sery";
	
	//电费
	private String data_df_cate ="data_df_cate";
	private String data_df_sery ="data_df_sery";
	
	//小贷融资余额
	private String data_xdrzye_cate ="data_xdrzye_cate";
	private String data_xdrzye_sery ="data_xdrzye_sery";
	
	/**
	 * 采集截至日期
	 */
	//专利信息
	private String time_zl_create_time;
	//未解保的融资担保信息
	private String time_rzdb_create_time;
	//未结清小额贷款信息
	private String time_xedk_create_time;
	//海关进出口信息
	private String time_hg_create_time;
	//房产抵押登记信息
	private String time_fdc_create_time;
	//土地抵押登记信息
	private String time_tddy_create_time;
	//房产查封（含预查封）信息
	private String time_fccf_create_time;
	//土地查封信息
	private String time_tdcf_create_time;
	/**
	 * 基本信息
	 */
	//报告编号
	private String report_number;
	//报告日期
	private String curr_time;
	//查询机构
	private String department_name;
	//查询人员
	private String real_name;
	//采集授权生效日期
	private String archive_date;
	//名称
	private String corp_name;
	//注册地址全
	private String addr_all;
	//注册地址大
	private String addr_b;
	//注册地址小
	private String addr_s;
	//机构代码
	private String org_code;
	//注册资本
	private String regcap;
	//法定代表人
	private String legal_per;
	//企业联系电话
	private String tel;
	//类型
	private String  enttype;
	//经营状态
	private String   entstatus;
	//成立日期
	private String  esdate;
	//营业期限
	private String  op;
	//行业分类
	private String  industryco;
	//公司人数
	private String  kjsbrs;
	//经营范围
	private String  opscope;
	//股东信息
	private List<ShareholderInfoVo> gdxxlist;
		
	//董事、监事、高管信息
	private List<SeniorExecutiveInfoVo> ggxxlist;
	

	
	/**
	 * 社保公积金缴存信息
	 */
	//社保公积金缴存信息
	private List<SocialSecurityVo> sbgjjlist;
	/**
	 * 经营信息
	 */
	//资产负债表主要信息
	private List<OperateInfoVo> szcfzlist;
	//利润表主要信息
	private List<OperateInfoVo> lrlist;
	//主要财务指标信息
	private ColumnAndListVO cwzbVo;
	//纳税信息
	private ColumnAndListVO nsxxVo;
	//海关进出口信息
	private ColumnAndListVO hgjckxxVo;
	//水电气缴费信息
	private ColumnAndListVO sdqxxVo;
	/**
	 * 非银金融机构融资信息
	 */
	//未结清小额贷款信息
	private List<LoanInfoVo> wjqxelist;
	//未解保的融资担保信息
	private List<LoanInfoVo> wjbrzlist;
	/************************2017-05-26****************************************/
	//未结清信贷信息
	private List<LoanInfoVo> wjqxdlist;
	//贴现信息
	private List<LoanInfoVo> txlist;
	
	/**
	 * 抵押与查封信息
	 */
	//房产抵押登记信息	
	private List<BDCVo> fcdylist;
	//土地抵押登记信息
	private List<BDCVo> tddylist;
	//房产查封（含预查封）信息
	private List<BDCVo> fccflist;
	//土地查封信息
	private List<BDCVo> tdcflist;
	/**
	 * 涉诉信息
	 */
	//立案信息
	private List<ComplaintInfoVo> laxxlist;
	//结案信息
	private List<ComplaintInfoVo> jaxxlist;
	//失信被执行人信息
	private List<ComplaintInfoVo> sxbzxrlist;
	/**
	 * 负面信息
	 */
	//欠缴/逾期信息
	private List<NegativeInfoVo> qjyqlist;
	//处罚信息
	private List<NegativeInfoVo> cfxxlist;
	//安全生产挂牌督办未完成整改的信息
	private List<NegativeInfoVo> aqscwwclist;
	//税务部门非正常户信息
	private List<NegativeInfoVo> swfzclist;
	//海关失信企业信息
	private List<NegativeInfoVo> qhgsxqylist;
	//环保负面评价
	private List<NegativeInfoVo> hbfmlist;
	//产品抽检信息
	private List<NegativeInfoVo> cpcjlist;
	//其他负面信息
	private List<NegativeInfoVo> qtfmlist;
	
	
	/**
	 * 资质认证信息
	 */
	//专利信息
	private List<PatentInfoVo> zllist;
	//评价信息
	private List<EvaluateInfoVo> pjlist;
	
	//返回页面调用客户端的异常信息
	private String exceptionMeessage;
	
	public String getReport_number() {
		return report_number;
	}
	public void setReport_number(String report_number) {
		this.report_number = report_number;
	}
	public String getCurr_time() {
		return curr_time;
	}
	public void setCurr_time(String curr_time) {
		this.curr_time = curr_time;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getArchive_date() {
		return archive_date;
	}
	public void setArchive_date(String archive_date) {
		this.archive_date = archive_date;
	}
	public String getCorp_name() {
		return corp_name;
	}
	public void setCorp_name(String corp_name) {
		this.corp_name = corp_name;
	}
	public String getAddr_all() {
		return addr_all;
	}
	public void setAddr_all(String addr_all) {
		this.addr_all = addr_all;
	}
	public String getAddr_b() {
		return addr_b;
	}
	public void setAddr_b(String addr_b) {
		this.addr_b = addr_b;
	}
	public String getAddr_s() {
		return addr_s;
	}
	public void setAddr_s(String addr_s) {
		this.addr_s = addr_s;
	}
	
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getRegcap() {
		return regcap;
	}
	public void setRegcap(String regcap) {
		this.regcap = regcap;
	}
	public String getLegal_per() {
		return legal_per;
	}
	public void setLegal_per(String legal_per) {
		this.legal_per = legal_per;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEnttype() {
		return enttype;
	}
	public void setEnttype(String enttype) {
		this.enttype = enttype;
	}
	public String getEntstatus() {
		return entstatus;
	}
	public void setEntstatus(String entstatus) {
		this.entstatus = entstatus;
	}
	public String getEsdate() {
		return esdate;
	}
	public void setEsdate(String esdate) {
		this.esdate = esdate;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getIndustryco() {
		return industryco;
	}
	public void setIndustryco(String industryco) {
		this.industryco = industryco;
	}
	public String getKjsbrs() {
		return kjsbrs;
	}
	public void setKjsbrs(String kjsbrs) {
		this.kjsbrs = kjsbrs;
	}
	public String getOpscope() {
		return opscope;
	}
	public void setOpscope(String opscope) {
		this.opscope = opscope;
	}
	public List<ShareholderInfoVo> getGdxxlist() {
		return gdxxlist;
	}
	public void setGdxxlist(List<ShareholderInfoVo> gdxxlist) {
		this.gdxxlist = gdxxlist;
	}
	public List<SeniorExecutiveInfoVo> getGgxxlist() {
		return ggxxlist;
	}
	public void setGgxxlist(List<SeniorExecutiveInfoVo> ggxxlist) {
		this.ggxxlist = ggxxlist;
	}
	public List<SocialSecurityVo> getSbgjjlist() {
		return sbgjjlist;
	}
	public void setSbgjjlist(List<SocialSecurityVo> sbgjjlist) {
		this.sbgjjlist = sbgjjlist;
	}
	public List<PatentInfoVo> getZllist() {
		return zllist;
	}
	public void setZllist(List<PatentInfoVo> zllist) {
		this.zllist = zllist;
	}
	public List<EvaluateInfoVo> getPjlist() {
		return pjlist;
	}
	public void setPjlist(List<EvaluateInfoVo> pjlist) {
		this.pjlist = pjlist;
	}
	public List<OperateInfoVo> getSzcfzlist() {
		return szcfzlist;
	}
	public void setSzcfzlist(List<OperateInfoVo> szcfzlist) {
		this.szcfzlist = szcfzlist;
	}
	public List<OperateInfoVo> getLrlist() {
		return lrlist;
	}
	public void setLrlist(List<OperateInfoVo> lrlist) {
		this.lrlist = lrlist;
	}
	public List<NegativeInfoVo> getQjyqlist() {
		return qjyqlist;
	}
	public void setQjyqlist(List<NegativeInfoVo> qjyqlist) {
		this.qjyqlist = qjyqlist;
	}
	public List<NegativeInfoVo> getCfxxlist() {
		return cfxxlist;
	}
	public void setCfxxlist(List<NegativeInfoVo> cfxxlist) {
		this.cfxxlist = cfxxlist;
	}
	public List<NegativeInfoVo> getAqscwwclist() {
		return aqscwwclist;
	}
	public void setAqscwwclist(List<NegativeInfoVo> aqscwwclist) {
		this.aqscwwclist = aqscwwclist;
	}
	public List<NegativeInfoVo> getSwfzclist() {
		return swfzclist;
	}
	public void setSwfzclist(List<NegativeInfoVo> swfzclist) {
		this.swfzclist = swfzclist;
	}
	public List<NegativeInfoVo> getQhgsxqylist() {
		return qhgsxqylist;
	}
	public void setQhgsxqylist(List<NegativeInfoVo> qhgsxqylist) {
		this.qhgsxqylist = qhgsxqylist;
	}
	public List<NegativeInfoVo> getHbfmlist() {
		return hbfmlist;
	}
	public void setHbfmlist(List<NegativeInfoVo> hbfmlist) {
		this.hbfmlist = hbfmlist;
	}
	public List<NegativeInfoVo> getCpcjlist() {
		return cpcjlist;
	}
	public void setCpcjlist(List<NegativeInfoVo> cpcjlist) {
		this.cpcjlist = cpcjlist;
	}
	public List<NegativeInfoVo> getQtfmlist() {
		return qtfmlist;
	}
	public void setQtfmlist(List<NegativeInfoVo> qtfmlist) {
		this.qtfmlist = qtfmlist;
	}
	public List<ComplaintInfoVo> getLaxxlist() {
		return laxxlist;
	}
	public void setLaxxlist(List<ComplaintInfoVo> laxxlist) {
		this.laxxlist = laxxlist;
	}
	public List<ComplaintInfoVo> getJaxxlist() {
		return jaxxlist;
	}
	public void setJaxxlist(List<ComplaintInfoVo> jaxxlist) {
		this.jaxxlist = jaxxlist;
	}
	public List<ComplaintInfoVo> getSxbzxrlist() {
		return sxbzxrlist;
	}
	public void setSxbzxrlist(List<ComplaintInfoVo> sxbzxrlist) {
		this.sxbzxrlist = sxbzxrlist;
	}
	public List<LoanInfoVo> getWjqxelist() {
		return wjqxelist;
	}
	public void setWjqxelist(List<LoanInfoVo> wjqxelist) {
		this.wjqxelist = wjqxelist;
	}
	public List<LoanInfoVo> getWjbrzlist() {
		return wjbrzlist;
	}
	public void setWjbrzlist(List<LoanInfoVo> wjbrzlist) {
		this.wjbrzlist = wjbrzlist;
	}
	public String getTime_zl_create_time() {
		return time_zl_create_time;
	}
	public void setTime_zl_create_time(String time_zl_create_time) {
		this.time_zl_create_time = time_zl_create_time;
	}
	public String getTime_rzdb_create_time() {
		return time_rzdb_create_time;
	}
	public void setTime_rzdb_create_time(String time_rzdb_create_time) {
		this.time_rzdb_create_time = time_rzdb_create_time;
	}
	public String getTime_xedk_create_time() {
		return time_xedk_create_time;
	}
	public void setTime_xedk_create_time(String time_xedk_create_time) {
		this.time_xedk_create_time = time_xedk_create_time;
	}
	public String getTime_hg_create_time() {
		return time_hg_create_time;
	}
	public void setTime_hg_create_time(String time_hg_create_time) {
		this.time_hg_create_time = time_hg_create_time;
	}
	public String getTime_fdc_create_time() {
		return time_fdc_create_time;
	}
	public void setTime_fdc_create_time(String time_fdc_create_time) {
		this.time_fdc_create_time = time_fdc_create_time;
	}
	public String getTime_tddy_create_time() {
		return time_tddy_create_time;
	}
	public void setTime_tddy_create_time(String time_tddy_create_time) {
		this.time_tddy_create_time = time_tddy_create_time;
	}
	public String getTime_fccf_create_time() {
		return time_fccf_create_time;
	}
	public void setTime_fccf_create_time(String time_fccf_create_time) {
		this.time_fccf_create_time = time_fccf_create_time;
	}
	public String getTime_tdcf_create_time() {
		return time_tdcf_create_time;
	}
	public void setTime_tdcf_create_time(String time_tdcf_create_time) {
		this.time_tdcf_create_time = time_tdcf_create_time;
	}
	public ColumnAndListVO getCwzbVo() {
		return cwzbVo;
	}
	public void setCwzbVo(ColumnAndListVO cwzbVo) {
		this.cwzbVo = cwzbVo;
	}
	public ColumnAndListVO getNsxxVo() {
		return nsxxVo;
	}
	public void setNsxxVo(ColumnAndListVO nsxxVo) {
		this.nsxxVo = nsxxVo;
	}
	public ColumnAndListVO getHgjckxxVo() {
		return hgjckxxVo;
	}
	public void setHgjckxxVo(ColumnAndListVO hgjckxxVo) {
		this.hgjckxxVo = hgjckxxVo;
	}
	public ColumnAndListVO getSdqxxVo() {
		return sdqxxVo;
	}
	public void setSdqxxVo(ColumnAndListVO sdqxxVo) {
		this.sdqxxVo = sdqxxVo;
	}
	public List<BDCVo> getTddylist() {
		return tddylist;
	}
	public void setTddylist(List<BDCVo> tddylist) {
		this.tddylist = tddylist;
	}
	public List<BDCVo> getFccflist() {
		return fccflist;
	}
	public void setFccflist(List<BDCVo> fccflist) {
		this.fccflist = fccflist;
	}
	public List<BDCVo> getTdcflist() {
		return tdcflist;
	}
	public void setTdcflist(List<BDCVo> tdcflist) {
		this.tdcflist = tdcflist;
	}
	public List<BDCVo> getFcdylist() {
		return fcdylist;
	}
	public void setFcdylist(List<BDCVo> fcdylist) {
		this.fcdylist = fcdylist;
	}
	public String getData_yysr_cate() {
//		if(!StringUtils.isBlank(data_yysr_cate)){
//			data_yysr_cate =  data_yysr_cate.replaceAll("null", "0");
//		}
		return data_yysr_cate.replaceAll("null", "0");
	}
	public void setData_yysr_cate(String data_yysr_cate) {
		this.data_yysr_cate = data_yysr_cate;
	}
	public String getData_yysr_sery() {
		return data_yysr_sery.replaceAll("null", "0");
	}
	public void setData_yysr_sery(String data_yysr_sery) {
		this.data_yysr_sery = data_yysr_sery;
	}
	public String getData_jlr_cate() {
		return data_jlr_cate.replaceAll("null", "0");
	}
	public void setData_jlr_cate(String data_jlr_cate) {
		this.data_jlr_cate = data_jlr_cate;
	}
	public String getData_jlr_sery() {
		return data_jlr_sery.replaceAll("null", "0");
	}
	public void setData_jlr_sery(String data_jlr_sery) {
		this.data_jlr_sery = data_jlr_sery;
	}
	public String getData_zz_ye_cate() {
		return data_zz_ye_cate.replaceAll("null", "0");
	}
	public void setData_zz_ye_cate(String data_zz_ye_cate) {
		this.data_zz_ye_cate = data_zz_ye_cate;
	}
	public String getData_zz_ye_sery() {
		return data_zz_ye_sery.replaceAll("null", "0");
	}
	public void setData_zz_ye_sery(String data_zz_ye_sery) {
		this.data_zz_ye_sery = data_zz_ye_sery;
	}
	public String getData_gsrs_cate() {
		return data_gsrs_cate.replaceAll("null", "0");
	}
	public void setData_gsrs_cate(String data_gsrs_cate) {
		this.data_gsrs_cate = data_gsrs_cate;
	}
	public String getData_gsrs_sery() {
		return data_gsrs_sery.replaceAll("null", "0");
	}
	public void setData_gsrs_sery(String data_gsrs_sery) {
		this.data_gsrs_sery = data_gsrs_sery;
	}
	public String getData_df_cate() {
		return data_df_cate.replaceAll("null", "0");
	}
	public void setData_df_cate(String data_df_cate) {
		this.data_df_cate = data_df_cate;
	}
	public String getData_df_sery() {
		return data_df_sery.replaceAll("null", "0");
	}
	public void setData_df_sery(String data_df_sery) {
		this.data_df_sery = data_df_sery;
	}
	public String getData_xdrzye_cate() {
		return data_xdrzye_cate.replaceAll("null", "0");
	}
	public void setData_xdrzye_cate(String data_xdrzye_cate) {
		this.data_xdrzye_cate = data_xdrzye_cate;
	}
	public String getData_xdrzye_sery() {
		return data_xdrzye_sery.replaceAll("null", "0");
	}
	public void setData_xdrzye_sery(String data_xdrzye_sery) {
		this.data_xdrzye_sery = data_xdrzye_sery;
	}
	public List<LoanInfoVo> getWjqxdlist() {
		return wjqxdlist;
	}
	public void setWjqxdlist(List<LoanInfoVo> wjqxdlist) {
		this.wjqxdlist = wjqxdlist;
	}
	public List<LoanInfoVo> getTxlist() {
		return txlist;
	}
	public void setTxlist(List<LoanInfoVo> txlist) {
		this.txlist = txlist;
	}
	public String getExceptionMeessage() {
		return exceptionMeessage;
	}
	public void setExceptionMeessage(String exceptionMeessage) {
		this.exceptionMeessage = exceptionMeessage;
	}
	
	
	
}

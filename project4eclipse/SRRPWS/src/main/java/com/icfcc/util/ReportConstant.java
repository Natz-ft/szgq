package com.icfcc.util;

import java.math.BigDecimal;

public interface ReportConstant {
	
	public static final BigDecimal REPORT_RESULT_SUCCES=new BigDecimal(1);
	public static final BigDecimal REPORT_RESULT_FAIL=new BigDecimal(0);
	public static final BigDecimal REPORT_QUERY_TYPE=new BigDecimal(1);
	public static final String REPORT_VERSION="1";
	
	
	public static final String REPORT_NUMBER="report_number";
	public static final String E_CURR_TIME="e_curr_time";
	public static final String E_DEPARTMENT_NAME="e_department_name";
	public static final String E_REAL_NAME="e_real_name";
	public static final String E_ARCHIVE_DATE="e_archive_date";
	
	/**
	 * 
	 * 采集截至日期
	 */
	//专利信息
	public static final  String TIME_ZL_CREATE_TIME="time_zl_create_time";
	//未解保的融资担保信息
	public static final String TIME_RZDB_CREATE_TIME="time_rzdb_create_time";
	//未结清小额贷款信息
	public static final String TIME_XEDK_CREATE_TIME="time_xedk_create_time";
	//海关进出口信息
	public static final String TIME_HG_CREATE_TIME="time_hg_create_time";
	//房产抵押登记信息
	public static final String TIME_FDC_CREATE_TIME="time_fdc_create_time";
	//土地抵押登记信息
	public static final String TIME_TDDY_CREATE_TIME="time_tddy_create_time";
	//房产查封（含预查封）信息
	public static final String TIME_FCCF_CREATE_TIME="time_fccf_create_time";
	//土地查封信息
	public static final String TIME_TDCF_CREATE_TIME="time_tdcf_create_time";
	
	
	
	//日期
	public static final  String TIME_BEFORE_LAST_YEAR_CAIWU="time_before_last_year_caiwu";
	public static final  String TIME_LAST_YEAR_CAIWU="time_last_year_caiwu";
	public static final  String TIME_CURRENT_YEAR_CAIWU="time_current_year_caiwu";
	public static final  String TIME_ZCFZB_CURRENT_MONTH="time_zcfzb_current_month";
	public static final  String STYLETYPE="styleType";
	
	public static final  String BEFORE_LAST_YEAR_TAX="before_last_year_tax";
	public static final  String LAST_YEAR_TAX="last_year_tax";
	public static final  String CURRENT_YEAR_TAX="current_year_tax";
	
	public static final  String BEFORE_LAST_YEAR_HG="before_last_year_hg";
	public static final  String LAST_YEAR_HG="last_year_hg";
	public static final  String CURRENT_YEAR_HG="current_year_hg";
	public static final  String G_CURRENT_MAX_MONTH_1="g_current_max_month_1";
	
	public static final  String G_IMPORT_QUITA_TOTAL_0="g_import_quota_total_0";
	public static final  String G_EXPORT_QUITA_TOTAL_0="g_export_quota_total_0";
	public static final  String G_IMPORT_QUITA_TOTAL_1="g_import_quota_total_1";
	public static final  String G_EXPORT_QUITA_TOTAL_1="g_export_quota_total_1";
	public static final  String G_IMPORT_QUITA_TOTAL_2="g_import_quota_total_2";
	public static final  String G_EXPORT_QUITA_TOTAL_2="g_export_quota_total_2";
	
	public static final  String B2_INVTYPE_10="法人股东";
	public static final  String B2_INVTYPE_11="企业法人";
	public static final  String B2_INVTYPE_12="事业法人";
	public static final  String B2_INVTYPE_13="社团法人";
	public static final  String B2_INVTYPE_14="机关法人";
	public static final  String B2_INVTYPE_15="外商投资企业";
	public static final  String B2_INVTYPE_20="自然人股东";
	public static final  String B2_INVTYPE_30="外国(地区)投资者";
	public static final  String B2_INVTYPE_31="外国(地区)企业";
	public static final  String B2_INVTYPE_32="外国(地区)经济组织";
	public static final  String B2_INVTYPE_33="外商投资投资性公司";
	public static final  String B2_INVTYPE_34="外商投资创业投资企业";
	public static final  String B2_INVTYPE_35="外籍自然人";
	public static final  String B2_INVTYPE_36="华侨";
	public static final  String B2_INVTYPE_40="合伙企业";
	public static final  String B2_INVTYPE_50="个人独资企业";
	public static final  String B2_INVTYPE_90="其他投资者";
	
	/**
	 * 基本信息
	 */
	//报告编号
	public static final String QUERY_REASON="5";
//	//报告日期
//	public static final String curr_time;
//	//查询机构
//	public static final String department_name;
//	//查询人员
//	public static final String real_name;
//	//采集授权生效日期
//	public static final String archive_date;
	//名称
	public static final String D2_CORP_NAME="d2_corp_name";
	//注册地址全
	public static final String D2_DOM ="d2_dom";
	public static final String TIME_JGDZ ="time_jgdz";
	//机构代码
	public static final String D2_CREDIT_CODE="d2_credit_code";
	public static final String D2_CORP_CODE="d2_corp_code";
	public static final String D2_IS_NUM="d2_ic_num";
	//注册资本
	public static final String D2_REGCAP="d2_regcap";
	//法定代表人
	public static final String D2_LEGAL_PER="d2_legal_per";
	public static final String D2_LEGAL_NAME="d2_legal_name";
	//企业联系电话
	public static final String D2_TEL="d2_tel";
	public static final String D2_LEGAL_PHONE="d2_legal_phone";
	//类型
	public static final String  D2_ENTTYPE="d2_enttype";
	//经营状态
	public static final String   D2_ENTSTATUS="d2_entstatus";
	//成立日期
	public static final String  D2_ESDATE="d2_esdate";
	//营业期限
	public static final String  D2_OP="d2_op";
	//行业分类
	public static final String  D2_INDUSTRYCO="d2_industryco";
	//公司人数
	public static final String  D2_KJSBRS="d2_kjsbrs";
	//经营范围
	public static final String D2_OPSCOPE="d2_opscope";
	public static final String D2_ADBUITEM="d2_abuitem";
	public static final String D2_CBUITEM="d2_cbuitem";
	
	public static final class TB{
		//营业收入
		public static final String DATA_YYSR_CATE ="data_yysr_cate";
		public static final String DATA_YYSR_SERY ="data_yysr_sery";
		
		//净利润
		public static final String DATA_JLR_CATE ="data_jlr_cate";
		public static final String DATA_JLR_SERY ="data_jlr_sery";
		
		//增值税与营业税合计
		public static final String DATA_ZZ_YE_CATE ="data_zz_ye_cate";
		public static final String DATA_ZZ_YE_SERY ="data_zz_ye_sery";
		
		//公司人数
		public static final String DATA_GSRS_CATE ="data_gsrs_cate";
		public static final String DATA_GSRS_SERY ="data_gsrs_sery";
		
		//电费
		public static final String DATA_DF_CATE ="data_df_cate";
		public static final String DATA_DF_SERY ="data_df_sery";
		
		//小贷融资余额
		public static final String DATA_XDRZYE_CATE ="data_xdrzye_cate";
		public static final String DATA_XDRZYE_SERY ="data_xdrzye_sery";
	}
	public static final  class CW{
		public static final String O_ASSENT_LIAB_RATIO_0 = "o_assent_liab_ratio_0";
		public static final String O_TOTAL_ASS_TURN_0 = "o_total_ass_turn_0";
		public static final String O_NET_PROFIT_MARGIN_0 = "o_net_profit_margin_0";
		public static final String O_NET_ASS_REWARD_0 = "o_net_ass_reward_0";
		public static final String O_TOTAL_ASS_REWARD_0 = "o_total_ass_reward_0";
		public static final String O_TOTAL_COST_INCOME_COMPARE_0 = "o_total_cost_income_compare_0";
		
		public static final String O_ASSENT_LIAB_RATIO_1 = "o_assent_liab_ratio_1";
		public static final String O_TOTAL_ASS_TURN_1 = "o_total_ass_turn_1";
		public static final String O_NET_PROFIT_MARGIN_1 = "o_net_profit_margin_1";
		public static final String O_NET_ASS_REWARD_1 = "o_net_ass_reward_1";
		public static final String O_TOTAL_ASS_REWARD_1 = "o_total_ass_reward_1";
		public static final String O_TOTAL_COST_INCOME_COMPARE_1 = "o_total_cost_income_compare_1";
		
		public static final String O_ASSENT_LIAB_RATIO_2 = "o_assent_liab_ratio_2";
		public static final String O_TOTAL_ASS_TURN_2 = "o_total_ass_turn_2";
		public static final String O_NET_PROFIT_MARGIN_2 = "o_net_profit_margin_2";
		public static final String O_NET_ASS_REWARD_2 = "o_net_ass_reward_2";
		public static final String O_TOTAL_ASS_REWARD_2 = "o_total_ass_reward_2";
		public static final String O_TOTAL_COST_INCOME_COMPARE_2 = "o_total_cost_income_compare_2";
		
		public static final String O_ASSENT_LIAB_RATIO_3 = "o_assent_liab_ratio_3";
		public static final String O_TOTAL_ASS_TURN_3 = "o_total_ass_turn_3";
		public static final String O_NET_PROFIT_MARGIN_3 = "o_net_profit_margin_3";
		public static final String O_NET_ASS_REWARD_3 = "o_net_ass_reward_3";
		public static final String O_TOTAL_ASS_REWARD_3 = "o_total_ass_reward_3";
		public static final String O_TOTAL_COST_INCOME_COMPARE_3 = "o_total_cost_income_compare_3";
		
		public static final String O_GENERAL_LOSS_RATIO_0 = "o_general_loss_ratio_0";
		public static final String O_GENERAL_LOSS_RATIO_1 = "o_general_loss_ratio_1";
		public static final String O_GENERAL_LOSS_RATIO_2 = "o_general_loss_ratio_2";
		public static final String O_GENERAL_LOSS_RATIO_3 = "o_general_loss_ratio_3";
		
		public static final String  O_PROVISION_COVERAGE_RATIO_0="o_provision_coverage_ratio_0";
		public static final String  O_PROVISION_COVERAGE_RATIO_1="o_provision_coverage_ratio_1";
		public static final String  O_PROVISION_COVERAGE_RATIO_2="o_provision_coverage_ratio_2";
		public static final String  O_PROVISION_COVERAGE_RATIO_3="o_provision_coverage_ratio_3";
		
		public static final String  O_ACCOU_RECE_TURN_0 ="o_accou_rece_turn_0";
		public static final String  O_ACCOU_RECE_TURN_1 ="o_accou_rece_turn_1";
		public static final String  O_ACCOU_RECE_TURN_2 ="o_accou_rece_turn_2";
		public static final String  O_ACCOU_RECE_TURN_3 ="o_accou_rece_turn_3";
		
		public static final String o_rate_margin_0 = "o_rate_margin_0";
		public static final String o_rate_margin_1 = "o_rate_margin_1";
		public static final String o_rate_margin_2 = "o_rate_margin_2";
		public static final String o_rate_margin_3 = "o_rate_margin_3";
		
		public static final String O_FIXED_ASS_TURN_0 ="o_fixed_ass_turn_0";
		public static final String O_FIXED_ASS_TURN_1 ="o_fixed_ass_turn_1";
		public static final String O_FIXED_ASS_TURN_2 ="o_fixed_ass_turn_2";
		public static final String O_FIXED_ASS_TURN_3 ="o_fixed_ass_turn_3";
		
		public static final String O_CURR_ASS_TURN_0 = "o_curr_ass_turn_0";
		public static final String O_CURR_ASS_TURN_1 = "o_curr_ass_turn_1";
		public static final String O_CURR_ASS_TURN_2 = "o_curr_ass_turn_2";
		public static final String O_CURR_ASS_TURN_3 = "o_curr_ass_turn_3";
		
		
		public static final String  O_INVEN_TURN_RATIO_0="o_inven_turn_ratio_0";
		public static final String  O_INVEN_TURN_RATIO_1="o_inven_turn_ratio_1";
		public static final String  O_INVEN_TURN_RATIO_2="o_inven_turn_ratio_2";
		public static final String  O_INVEN_TURN_RATIO_3="o_inven_turn_ratio_3";
		
		public static final String  O_LIQUI_RATIO_0="o_liqui_ratio_0";
		public static final String  O_LIQUI_RATIO_1="o_liqui_ratio_1";
		public static final String  O_LIQUI_RATIO_2="o_liqui_ratio_2";
		public static final String  O_LIQUI_RATIO_3="o_liqui_ratio_3";
		
		public static final String  O_CURR_RATIO_0="o_curr_ratio_0";
		public static final String  O_CURR_RATIO_1="o_curr_ratio_1";
		public static final String  O_CURR_RATIO_2="o_curr_ratio_2";
		public static final String  O_CURR_RATIO_3="o_curr_ratio_3";
		
	}
	public static final  class B2LIST{
		public static final String TYPE = "b2List";
		public static final String B2_INV= "b2_inv";
		public static final String B2_INVTYPE= "b2_invtype";
		public static final String B2_SUBCONAM= "b2_subconam";
		public static final String B2_REGCAPCUR= "b2_regcapcur";
	}
	public static final  class C2LIST{
		public static final String TYPE = "c2List";
		public static final String C2_MEMBER_TYPE= "c2_member_type";
		public static final String C2_MEMBER_NAME= "c2_member_name";
	}
	
	public static final  class BLIST{
		public static final String TYPE = "bList";
		public static final String B_SBRS= "b_sbrs";
		public static final String B_SBJE= "b_sbje";
		public static final String B_PERSON_NUM= "b_person_num";
		public static final String B_AMOUNT= "b_amount";
		public static final String B_DEPOSIT_MONTH= "b_deposit_month";
	}
	
	public static final  class CList{
		public static final String TYPE = "cList";
		public static final String C_ORD= "c_ord";
		public static final String C_CN_NAME= "c_cn_name";
		public static final String C_BEFORE_LAST_QMYE= "c_before_last_qmye";
		public static final String C_LAST_QMYE= "c_last_qmye";
		public static final String C_CURRENT_QMYE= "c_current_qmye";
		public static final String C_LAST_TIME_QMYE= "c_last_time_qmye";
	}
	public static final  class NList{
		public static final String TYPE = "nList";
		public static final String N_ORD= "n_ord";
		public static final String N_CN_NAME= "n_cn_name";
		public static final String N_BEFORE_LAST_QMYE= "n_before_last_qmye";
		public static final String N_LAST_QMYE= "n_last_qmye";
		public static final String N_CURRENT_QMYE= "n_current_qmye";
		public static final String N_LAST_TIME_QMYE= "n_last_time_qmye";
		
	}
	
	public static final class SDQ{
		public static final String TYPE_Q ="qList";
		public static final String TYPE_R ="rList";
		public static final String TYPE_S ="sList";
		
		public static final  String BEFORE_LAST_YEAR_WEG="before_last_year_weg";
		public static final  String LAST_YEAR_WEG="last_year_weg";
		public static final  String CURRENT_YEAR_WEG="current_year_weg";
		public static final  String  Q_ZHH="q_zhh";
		public static final  String  R_ZHH="r_zhh";
		public static final  String  S_ZHH="s_huh";
		public static final  String  Q_BEFORELASTYEAR="q_beforelastyear";
		public static final  String  R_BEFORELASTYEAR="r_beforelastyear";
		public static final  String  S_BEFORELASTYEAR="s_beforelastyear";
		public static final  String  Q_LASTYEAR="q_lastyear";
		public static final  String  R_LASTYEAR="r_lastyear";
		public static final  String  S_LASTYEAR="s_lastyear";
		public static final  String  Q_CURYEAR="q_curyear";
		public static final  String  R_CURYEAR="r_curyear";
		public static final  String  S_CURYEAR="s_curyear";
		public static final  String  Q_MAX_MONTH="q_max_month";
		public static final  String  R_MAX_MONTH="r_max_month";
		public static final  String  S_MAX_MONTH="s_max_month";
		public static final  String  Q_IS_SHARE="q_is_share";
		public static final  String  R_IS_SHARE="r_is_share";
		public static final  String  S_IS_SHARE="s_is_share";

	}
	public static final  class MCLIST{
		public static final String TYPE = "mcList";
		public static final String MC_BANK_NAME= "mc_bank_name";
		public static final String MC_OS_PRCP= "mc_os_prcp";
		public static final String MC_ACTV_DT= "mc_actv_dt";
		public static final String MC_LAST_DUE_DT= "mc_last_due_dt";
		public static final String MC_MAIN_GUR_TYP= "mc_main_gur_typ";
	}
	
	
	public static final  class TLIST{
		public static final String TYPE = "tList";
		public static final String T_ASSURE_CORP_NAME= "t_assure_corp_name";
		public static final String T_BANK_NAME= "t_bank_name";
		public static final String T_ASSURE_PRCP= "t_assure_prcp";
		public static final String T_ACTV_DT= "t_actv_dt";
		public static final String T_LAST_DUE_DT= "t_last_due_dt";
		public static final String T_UNASSURE_TYPE= "t_unassure_type";
	}
	/***************************2017-05-26  lucifer******************************************************/
	public static final  class XDXXLIST{
		public static final String TYPE = "xdxxList";
		public static final String XDXX_FINANCE_NO= "xdxx_finance_no";
		public static final String XDXX_LOAN_NUM_BALANCE= "xdxx_loan_num_balance";
		public static final String XDXX_CURRENCY= "xdxx_currency";
		public static final String XDXX_LOAN_DATE_SEND= "xdxx_loan_date_send";
		public static final String XDXX_LOAN_DATE_EXPIRE= "xdxx_loan_date_expire";
		public static final String XDXX_GUARANTEE_TYPE= "xdxx_guarantee_type";
		public static final String XDXX_FIVE_CATEGORY= "xdxx_five_category";
	}
	public static final  class TXXXLIST{
		public static final String TYPE = "txxxList";
		public static final String TXXX_FINANCE_NO= "txxx_finance_no";
		public static final String TXXX_COUNTERFOIL_NUM= "txxx_counterfoil_num";
		public static final String TXXX_CURRENCY= "txxx_currency";
		public static final String TXXX_DISCOUNT_DATE= "txxx_discount_date";
		public static final String TXXX_PROMISES_EXPIRE_DATE= "txxx_promises_expire_date";
		public static final String TXXX_FIVE_CATEGORY= "txxx_five_category";
	}
	
	public static final  class ULIST{
		public static final String TYPE = "uList";
		public static final String U_REPOSE= "u_repose";
		public static final String U_WARR_NUM= "u_warr_num";
		
		public static final String U_MORTGA= "u_mortga";
		public static final String U_BIZ= "u_biz";
		public static final String U_AMOUNT= "u_amount";
		public static final String U_START_DATE= "u_start_date";
		public static final String U_EDN_DATE="u_edn_date";
		public static final String U_AREA="u_area";
	}
	public static final class DYXXLIST{
		public static final String TYPE = "dyxxList";
		public static final String DYXX_BUSTYPE = "dyxx_bustype";
		public static final String DYXX_ADDRESS= "dyxx_address";
		public static final String DYXX_WARRNUM= "dyxx_warrnum";
		
		public static final String DYXX_MORTGA= "dyxx_mortga";
		public static final String DYXX_CURRENCY= "dyxx_currency";
		public static final String DYXX_AMOUNT= "dyxx_amount";
		public static final String DYXX_STARTDATE= "dyxx_startdate";
		public static final String DYXX_ENDDATE="dyxx_enddate";
		public static final String DYXX_LANDAREA="dyxx_landarea";
		public static final String DYXX_HOUSEAREA="dyxx_housearea";
	}
	
	public static final  class VLIST{
		public static final String TYPE = "vList";
		public static final String V_ZUOL= "v_zuol";
		public static final String V_TXQZH= "v_txqzh";
		
		public static final String V_DYQRMC= "v_dyqrmc";
		public static final String V_BIZ= "v_biz";
		public static final String V_DYJE= "v_dyje";
		public static final String V_START_DATE= "v_start_date";
		public static final String V_END_DATE= "v_end_date";
		public static final String V_DYMJ= "v_dymj";
	}
	public static final  class FLIST{
		public static final String TYPE = "fList";

		public static final String F_SHUIZ= "f_shuiz";
		public static final String F_BEFORELASTYEAR= "f_beforelastyear";
		public static final String F_LASTYEAR= "f_lastyear";
		public static final String F_CURYEAR= "f_curyear";
		public static final String F_MAX_MONTH= "f_max_month";
	}
	
	public static final  class FccfLIST{
		public static final String TYPE = "fccfList";
		public static final String FCCF_ADDRESS= "fccf_address";
		public static final String FCCF_BUILDING_CRE_NO= "fccf_building_cre_no";
		
		public static final String FCCF_CORP_CLOSE= "fccf_corp_close";
		public static final String FCCF_CLOSE_CRE_NO= "fccf_close_cre_no";
		public static final String FCCF_CLOSE_BEGIN_DATE= "fccf_close_begin_date";
		public static final String FCCF_CLOSE_END_DATE= "fccf_close_end_date";
		public static final String FCCF_CLOSE_CANCEL_DATE= "fccf_close_cancel_date";
		public static final String FCCF_BUILDING_AREA= "fccf_building_area";
	}
	public static final  class CFXXLIST{
		public static final String TYPE = "cfxxList";
		public static final String CFXX_BUSTYPE = "cfxx_bustype";
		public static final String CFXX_ADDRESS= "cfxx_address";
		public static final String CFXX_CRENO= "cfxx_creno";
		
		public static final String CFXX_CORPCLOSE= "cfxx_corpclose";
		public static final String CFXX_CLOSECRENO= "cfxx_closecreno";
		public static final String CFXX_STARTDATE= "cfxx_startdate";
		public static final String CFXX_ENDDATE= "cfxx_enddate";
		public static final String CFXX_LANDAREA= "cfxx_landarea";
		public static final String CFXX_HOUSEAREA= "cfxx_housearea";
	}
	
	
	public static final  class TdcfLIST{
		public static final String TYPE = "tdcfList";
		public static final String TDCF_ADDRESS= "tdcf_address";
		public static final String TDCF_LAND_CRE_NO= "tdcf_land_cre_no";
		
		public static final String TDCF_CORP_CLOSE= "tdcf_corp_close";
		public static final String TDCF_CLOSE_CRE_NO= "tdcf_close_cre_no";
		public static final String TDCF_CLOSE_BEGIN_DATE= "tdcf_close_begin_date";
		public static final String TDCF_CLOSE_END_DATE= "tdcf_close_end_date";
		public static final String TDCF_CLOSE_CANCEL_DATE= "tdcf_close_cancel_date";
		public static final String TDCF_LAND_AREA= "tdcf_land_area";
	}
	
	
	public static final  class LaxxLIST{
		public static final String TYPE = "laxxList";
		public static final String LAxx_case_no= "laxx_case_no";
		public static final String LAXX_CASE_RESON= "laxx_case_reson";
		public static final String LAXX_CASE_CREATE_DATE= "laxx_case_create_date";
		public static final String LAXX_CASE_PERSON_STATE= "laxx_case_person_state";
		public static final String LAXX_CASE_TYPE= "laxx_case_type";
		public static final String LAXX_CASE_CREATE_AMOUNT= "laxx_case_create_amount";
		public static final String LAXX_CASE_STATUS= "laxx_case_status";
	}
	
	public static final  class JaxxLIST{
		public static final String TYPE = "jaxxList";
		public static final String JAxx_case_no= "jaxx_case_no";
		public static final String JAXX_CASE_RESON= "jaxx_case_reson";
		public static final String JAXX_CASE_CREATE_DATE= "jaxx_case_end_date";
		public static final String JAXX_CASE_PERSON_STATE= "jaxx_case_person_state";
		public static final String JAXX_CASE_TYPE= "jaxx_case_type";
		public static final String JAXX_CASE_CREATE_AMOUNT= "jaxx_case_end_amount";
		public static final String JAXX_CASE_END_TYPE= "jaxx_case_end_type";
	}
	
	
	public static final  class SxzxLIST{
		public static final String TYPE = "sxzxList";
		public static final String SXZX_CASE_EXE_NO= "sxzx_case_exe_no";
		public static final String SXZX_CASE_COURTHOUSE= "sxzx_case_courthouse";
		public static final String SXZX_PUBLISH_DATE= "sxzx_publish_date";
		public static final String SXZX_DISCREDITABLE_STATE= "sxzx_discreditable_state";
	}
	
	
	public static final  class HLIST{
		public static final String TYPE = "hList";
		public static final String H_SOURCE= "h_source";
		public static final String H_YQJE= "h_yqje";
		public static final String H_YQSJ= "h_yqsj";
		public static final String H_CREATE_TIME= "h_create_time";
	}
	
	public static final  class ILIST{
		public static final String TYPE = "iList";
		public static final String I_PUN_TYPE= "i_pun_type";
		public static final String I_PUN_DECI= "i_pun_deci";
		public static final String I_PUN_TIME= "i_pun_time";
		public static final String I_ILLEGAL_ACT= "i_illegal_act";
		public static final String I_SKSSQSR= "i_skssqsr";
		public static final String I_PUN_CONTENT= "i_pun_content";
		public static final String I_CREATE_TIME= "i_create_time";
	}
	
	public static final  class GPDBLIST{
		public static final String TYPE = "gpdbList";
		public static final String GPDB_DUTY_LEVE= "gpdb_duty_leve";
		public static final String GPDB_PROBLEM_TYPE= "gpdb_problem_type";
		public static final String GPDB_START_DATE= "gpdb_start_date";
		public static final String GPDB_EXPRIE_DATE= "gpdb_expire_date";
		public static final String GPDB_FLOW_ORG= "gpdb_flow_org";

	}
	
	public static final  class MLIST{
		public static final String TYPE = "mList";
		public static final String M_RDBM= "m_rdbm";
		public static final String M_FZCHRDSJ= "m_fzchrdsj";
		public static final String M_CREATE_TIME= "m_create_time";
	}
	public static final  class JLIST{
		public static final String TYPE = "jList";
		public static final String J_PAT_TYPE= "j_pat_type";
		public static final String J_PAT_CERT_NO= "j_pat_cert_no";
		public static final String J_NAME= "j_name";
		public static final String J_PAT_DATE= "j_pat_date";
	}
	
	public static final  class ZLIST{
		public static final String TYPE = "zList";
		public static final String Z_LEIB= "z_leib";
		public static final String Z_CREATE_TIME= "z_create_time";
	}
	
	
	public static final  class K2LIST{
		public static final String TYPE = "k2List";
		public static final String K2_EVALUATE_ORGAN = "k2_evaluate_organ";
		public static final String K2_EVALUATE_CONTENT= "k2_evaluate_content";
		public static final String K2_EVALUATE_GRADE= "k2_evaluate_grade";
		public static final String K2_AWARD_DATE= "k2_award_date";
		public static final String K2_CREATE_TIME= "k2_create_time";
	}
	
	public static final  class A2LIST{
		public static final String TYPE = "a2List";
		public static final String A2_CJJG = "a2_cjjg";
		public static final String A2_CCSJ= "a2_ccsj";
		public static final String A2_CPMC= "a2_cpmc";
		public static final String A2_SHANGB= "a2_shangb";
		public static final String A2_BHGXM= "a2_bhgxm";
		public static final String A2_JYJL= "a2_jyjl";
	}
	
	public static final  class K3LIST{
		public static final String TYPE = "k2List";
		public static final String K3_EVALUATE_ORGAN = "k3_evaluate_organ";
		public static final String K3_EVALUATE_CONTENT= "k3_evaluate_content";
		public static final String K3_EVALUATE_GRADE= "k3_evaluate_grade";
		public static final String K3_AWARD_DATE= "k3_award_date";
		public static final String K3_CREATE_TIME= "k3_create_time";
	}
	public static final  class KLIST{
		public static final String TYPE = "kList";
		public static final String K_EVALUATE_ORGAN= "k_evaluate_organ";
		public static final String K_EVALUATE_CONTENT= "k_evaluate_content";
		public static final String K_EVALUATE_GRADE= "k_evaluate_grade";
		public static final String K_FILE_NO= "k_file_no";
		public static final String K_AWARD_DATE= "k_award_date";
		public static final String K_CREATE_TIME= "k_create_time";
	}
}

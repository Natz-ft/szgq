package com.icfcc.SRRPDao.jpa.entity.companyInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CapCompanyInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5598945226727602549L;
	private Map<String, String> mapvo = new HashMap<String, String>();
	private Map<String, String> mapcolumnvo = new HashMap<String, String>();
	private Object scale=null;
	private Object region=null;
	private Object industry=null;
	private Object register_type=null;
	private Object investment_type=null;
	private Object yearnum=null;
	private Object auth=null;
	private Object lend=null;
	private Object loan_status=null;
	private Object hg_status=null;
	private Object aq_status=null;
	private Object nashui_status=null;
	private Object hb_status=null;
	private Object rongyu_status=null;
	private Object pffzstart=null;
	private Object pffzend=null;
	private Object xyfdj=null;
	private Object ss_status=null;
	private Object yhjr_status=null;
	private String pageSize="1";
	private String pageNum="10";
	private String total="10";
	private String fileName;
	/*****************************
	 * 财务指标 ***************************
	 */
	/**
	 * 资产负债科目
	 */

	// 总资产指标
	private String index1999F;
	// 总资产-最近年度
	private String index1999F_curr_amount;
	// 总资产-运算符
	private String index1999F_operator;
	// 总资产-连续两年增幅
	private String index1999F_two_rate_up;
	// 总资产-连续两年降幅
	private String index1999F_two_rate_down;
	// 总资产-连续三年增幅
	private String index1999F_three_rate_up;
	// 总资产-连续三年降幅
	private String index1999F_three_rate_down;

	// 负债合计指标
	private String index2999F;
	// 负债合计-最近年度
	private String index2999F_curr_amount;
	// 负债合计-运算符
	private String index2999F_operator;
	// 负债合计-连续两年增幅
	private String index2999F_two_rate_up;
	// 负债合计-连续两年降幅
	private String index2999F_two_rate_down;
	// 负债合计-连续三年增幅
	private String index2999F_three_rate_up;
	// 负债合计-连续三年降幅
	private String index2999F_three_rate_down;

	// 实收资本指标
	private String index4001F;
	// 实收资本-最近年度
	private String index4001F_curr_amount;
	// 实收资本-运算符
	private String index4001F_operator;
	// 实收资本-连续两年增幅
	private String index4001F_two_rate_up;
	// 实收资本-连续两年降幅
	private String index4001F_two_rate_down;
	// 实收资本-连续三年增幅
	private String index4001F_three_rate_up;
	// 实收资本-连续三年降幅
	private String index4001F_three_rate_down;

	// 净资产指标
	private String index4998F;
	// 净资产-最近年度
	private String index4998F_curr_amount;
	// 净资产-运算符
	private String index4998F_operator;
	// 净资产-连续两年增幅
	private String index4998F_two_rate_up;
	// 净资产-连续两年降幅
	private String index4998F_two_rate_down;
	// 净资产-连续三年增幅
	private String index4998F_three_rate_up;
	// 净资产-连续三年降幅
	private String index4998F_three_rate_down;

	/**
	 * 利润科目
	 */
	// 营业收入指标
	private String index6001F;
	// 营业收入-最近年度
	private String index6001F_curr_amount;
	// 营业收入-运算符
	private String index6001F_operator;
	// 营业收入-连续两年增幅
	private String index6001F_two_rate_up;
	// 营业收入-连续两年降幅
	private String index6001F_two_rate_down;
	// 营业收入-连续三年增幅
	private String index6001F_three_rate_up;
	// 营业收入-连续三年降幅
	private String index6001F_three_rate_down;

	// 营业利润指标
	private String index6799F;
	// 营业利润-最近年度
	private String index6799F_curr_amount;
	// 营业利润-运算符
	private String index6799F_operator;
	// 营业利润-连续两年增幅
	private String index6799F_two_rate_up;
	// 营业利润-连续两年降幅
	private String index6799F_two_rate_down;
	// 营业利润-连续三年增幅
	private String index6799F_three_rate_up;
	// 营业利润-连续三年降幅
	private String index6799F_three_rate_down;

	// 利润总额指标
	private String index6899F;
	// 利润总额-最近年度
	private String index6899F_curr_amount;
	// 利润总额-运算符
	private String index6899F_operator;
	// 利润总额-连续两年增幅
	private String index6899F_two_rate_up;
	// 利润总额-连续两年降幅
	private String index6899F_two_rate_down;
	// 利润总额-连续三年增幅
	private String index6899F_three_rate_up;
	// 利润总额-连续三年降幅
	private String index6899F_three_rate_down;

	// 净利润指标
	private String index6999F;
	// 净利润-最近年度
	private String index6999F_curr_amount;
	// 净利润-运算符
	private String index6999F_operator;
	// 净利润-连续两年增幅
	private String index6999F_two_rate_up;
	// 净利润-连续两年降幅
	private String index6999F_two_rate_down;
	// 净利润-连续三年增幅
	private String index6999F_three_rate_up;
	// 净利润-连续三年降幅
	private String index6999F_three_rate_down;

	/**
	 * 主要财务指标
	 */
	// 净资产收益率指标
	private String index8023F;
	// 净资产收益率-最近年度
	private String index8023F_curr_amount;
	// 净资产收益率-运算符
	private String index8023F_operator;
	// 净资产收益率-连续两年增幅
	private String index8023F_two_rate_up;
	// 净资产收益率-连续两年降幅
	private String index8023F_two_rate_down;
	// 净资产收益率-连续三年增幅
	private String index8023F_three_rate_up;
	// 净资产收益率-连续三年降幅
	private String index8023F_three_rate_down;

	// 总资产收益率指标
	private String index8024F;
	// 总资产收益率-最近年度
	private String index8024F_curr_amount;
	// 总资产收益率-运算符
	private String index8024F_operator;
	// 总资产收益率-连续两年增幅
	private String index8024F_two_rate_up;
	// 总资产收益率-连续两年降幅
	private String index8024F_two_rate_down;
	// 总资产收益率-连续三年增幅
	private String index8024F_three_rate_up;
	// 总资产收益率-连续三年降幅
	private String index8024F_three_rate_down;

	// 资本保值增值率指标
	private String index8052F;
	// 资本保值增值率-最近年度
	private String index8052F_curr_amount;
	// 资本保值增长率-运算符
	private String index8052F_operator;
	// 资本保值增值率-连续两年增幅
	private String index8052F_two_rate_up;
	// 资本保值增值率-连续两年降幅
	private String index8052F_two_rate_down;
	// 资本保值增值率-连续三年增幅
	private String index8052F_three_rate_up;
	// 资本保值增值率-连续三年降幅
	private String index8052F_three_rate_down;

	// 毛利率指标
	private String index8021F;
	// 毛利率-最近年度
	private String index8021F_curr_amount;
	// 毛利率-运算符
	private String index8021F_operator;
	// 毛利率-连续两年增幅
	private String index8021F_two_rate_up;
	// 毛利率-连续两年降幅
	private String index8021F_two_rate_down;
	// 毛利率-连续三年增幅
	private String index8021F_three_rate_up;
	// 毛利率-连续三年降幅
	private String index8021F_three_rate_down;

	// 净利率指标
	private String index8022F;
	// 净利率-最近年度
	private String index8022F_curr_amount;
	// 净利率-运算符
	private String index8022F_operator;
	// 净利率-连续两年增幅
	private String index8022F_two_rate_up;
	// 净利率-连续两年降幅
	private String index8022F_two_rate_down;
	// 净利率-连续三年增幅
	private String index8022F_three_rate_up;
	// 净利率-连续三年降幅
	private String index8022F_three_rate_down;

	// 销售费用收入比指标
	private String index8041F;
	// 销售费用收入比-最近年度
	private String index8041F_curr_amount;
	// 销售费用收入比-运算符
	private String index8041F_operator;
	// 销售费用收入比-连续两年增幅
	private String index8041F_two_rate_up;
	// 销售费用收入比-连续两年降幅
	private String index8041F_two_rate_down;
	// 销售费用收入比-连续三年增幅
	private String index8041F_three_rate_up;
	// 销售费用收入比-连续三年降幅
	private String index8041F_three_rate_down;

	// 管理费用收入比指标
	private String index8042F;
	// 管理费用收入比-最近年度
	private String index8042F_curr_amount;
	// 管理费用收入比-运算符
	private String index8042F_operator;
	// 管理费用收入比-连续两年增幅
	private String index8042F_two_rate_up;
	// 管理费用收入比-连续两年降幅
	private String index8042F_two_rate_down;
	// 管理费用收入比-连续三年增幅
	private String index8042F_three_rate_up;
	// 管理费用收入比-连续三年降幅
	private String index8042F_three_rate_down;

	// 期间费用收入比指标
	private String index8043F;
	// 期间费用收入比-最近年度
	private String index8043F_curr_amount;
	// 期间费用收入比-运算符
	private String index8043F_operator;
	// 期间费用收入比-连续两年增幅
	private String index8043F_two_rate_up;
	// 期间费用收入比-连续两年降幅
	private String index8043F_two_rate_down;
	// 期间费用收入比-连续三年增幅
	private String index8043F_three_rate_up;
	// 期间费用收入比-连续三年降幅
	private String index8043F_three_rate_down;

	// 资产负债率指标
	private String index8003F;
	// 资产负债率-最近年度
	private String index8003F_curr_amount;
	// 资产负债率-运算符
	private String index8003F_operator;
	// 资产负债率-连续两年增幅
	private String index8003F_two_rate_up;
	// 资产负债率-连续两年降幅
	private String index8003F_two_rate_down;
	// 资产负债率-连续三年增幅
	private String index8003F_three_rate_up;
	// 资产负债率-连续三年降幅
	private String index8003F_three_rate_down;

	// 刚性负债销售比指标
	private String index8051F;
	// 刚性负债销售比-最近年度
	private String index8051F_curr_amount;
	// 刚性负债销售比-运算符
	private String index8051F_operator;
	// 刚性负债销售比-连续两年增幅
	private String index8051F_two_rate_up;
	// 刚性负债销售比-连续两年降幅
	private String index8051F_two_rate_down;
	// 刚性负债销售比-连续三年增幅
	private String index8051F_three_rate_up;
	// 刚性负债销售比-连续三年降幅
	private String index8051F_three_rate_down;

	// 流动比率指标
	private String index8001F;
	// 流动比率-最近年度
	private String index8001F_curr_amount;
	// 流动比率-运算符
	private String index8001F_operator;
	// 流动比率-连续两年增幅
	private String index8001F_two_rate_up;
	// 流动比率-连续两年降幅
	private String index8001F_two_rate_down;
	// 流动比率-连续三年增幅
	private String index8001F_three_rate_up;
	// 流动比率-连续三年降幅
	private String index8001F_three_rate_down;

	// 速动比率指标
	private String index8002F;
	// 速动比率-最近年度
	private String index8002F_curr_amount;
	// 速动比率-运算符
	private String index8002F_operator;
	// 速动比率-连续两年增幅
	private String index8002F_two_rate_up;
	// 速动比率-连续两年降幅
	private String index8002F_two_rate_down;
	// 速动比率-连续三年增幅
	private String index8002F_three_rate_up;
	// 速动比率-连续三年降幅
	private String index8002F_three_rate_down;

	// 现金比率指标
	private String index8004F;
	// 现金比率-最近年度
	private String index8004F_curr_amount;
	// 现金比率-运算符
	private String index8004F_operator;
	// 现金比率-连续两年增幅
	private String index8004F_two_rate_up;
	// 现金比率-连续两年降幅
	private String index8004F_two_rate_down;
	// 现金比率-连续三年增幅
	private String index8004F_three_rate_up;
	// 现金比率-连续三年降幅
	private String index8004F_three_rate_down;

	// 总资产周转率指标
	private String index8015F;
	// 总资产周转率-最近年度
	private String index8015F_curr_amount;
	// 总资产周转率-运算符
	private String index8015F_operator;
	// 总资产周转率-连续两年增幅
	private String index8015F_two_rate_up;
	// 总资产周转率-连续两年降幅
	private String index8015F_two_rate_down;
	// 总资产周转率-连续三年增幅
	private String index8015F_three_rate_up;
	// 总资产周转率-连续三年降幅
	private String index8015F_three_rate_down;

	// 应收账款周转率指标
	private String index8012F;
	// 应收账款周转率-最近年度
	private String index8012F_curr_amount;
	// 应收账款周转率-运算符
	private String index8012F_operator;
	// 应收账款周转率-连续两年增幅
	private String index8012F_two_rate_up;
	// 应收账款周转率-连续两年降幅
	private String index8012F_two_rate_down;
	// 应收账款周转率-连续三年增幅
	private String index8012F_three_rate_up;
	// 应收账款周转率-连续三年降幅
	private String index8012F_three_rate_down;

	// 存货周转率指标
	private String index8011F;
	// 存货周转率-最近年度
	private String index8011F_curr_amount;
	// 存货周转率-运算符
	private String index8011F_operator;
	// 存货周转率-连续两年增幅
	private String index8011F_two_rate_up;
	// 存货周转率-连续两年降幅
	private String index8011F_two_rate_down;
	// 存货周转率-连续三年增幅
	private String index8011F_three_rate_up;
	// 存货周转率-连续三年降幅
	private String index8011F_three_rate_down;

	// 流动资产周转率指标
	private String index8013F;
	// 流动资产周转率-最近年度
	private String index8013F_curr_amount;
	// 流动资产周转率-运算符
	private String index8013F_operator;
	// 流动资产周转率-连续两年增幅
	private String index8013F_two_rate_up;
	// 流动资产周转率-连续两年降幅
	private String index8013F_two_rate_down;
	// 流动资产周转率-连续三年增幅
	private String index8013F_three_rate_up;
	// 流动资产周转率-连续三年降幅
	private String index8013F_three_rate_down;

	// 固定资产周转率指标
	private String index8014F;
	// 固定资产周转率-最近年度
	private String index8014F_curr_amount;
	// 固定资产周转率-运算符
	private String index8014F_operator;
	// 固定资产周转率-连续三年增幅
	private String index8014F_two_rate_up;
	// 固定资产周转率-连续三年降幅
	private String index8014F_two_rate_down;
	// 固定资产周转率-连续2年增幅
	private String index8014F_three_rate_up;
	// 固定资产周转率-连续2年降幅
	private String index8014F_three_rate_down;

	/*****************************
	 * 纳税指标 ***************************
	 */
	/**
	 * 流转税
	 */
	// 增值税指标
	private String index0067F;
	// 增值税-最近年度
	private String index0067F_curr_amount;
	// 增值税-运算符
	private String index0067F_operator;
	// 增值税-连续两年增幅
	private String index0067F_two_rate_up;
	// 增值税-连续两年降幅
	private String index0067F_two_rate_down;
	// 增值税-连续三年增幅
	private String index0067F_three_rate_up;
	// 增值税-连续三年降幅
	private String index0067F_three_rate_down;

	// 消费税指标
	private String index0070F;
	// 消费税-最近年度
	private String index0070F_curr_amount;
	// 消费税-运算符
	private String index0070F_operator;
	// 消费税-连续两年增幅
	private String index0070F_two_rate_up;
	// 消费税-连续两年降幅
	private String index0070F_two_rate_down;
	// 消费税-连续三年增幅
	private String index0070F_three_rate_up;
	// 消费税-连续三年降幅
	private String index0070F_three_rate_down;

	// 营业税指标
	private String index0068F;
	// 营业税-最近年度
	private String index0068F_curr_amount;
	// 营业税-运算符
	private String index0068F_operator;
	// 营业税-连续两年增幅
	private String index0068F_two_rate_up;
	// 营业税-连续两年降幅
	private String index0068F_two_rate_down;
	// 营业税-连续三年增幅
	private String index0068F_three_rate_up;
	// 营业税-连续三年降幅
	private String index0068F_three_rate_down;

	// 流转税合计指标
	private String index0071F;
	// 流转税合计-最近年度
	private String index0071F_curr_amount;
	// 流转税合计-运算符
	private String index0071F_operator;
	// 流转税合计-连续两年增幅
	private String index0071F_two_rate_up;
	// 流转税合计-连续两年降幅
	private String index0071F_two_rate_down;
	// 流转税合计-连续三年增幅
	private String index0071F_three_rate_up;
	// 流转税合计-连续三年降幅
	private String index0071F_three_rate_down;

	/**
	 * 所得税
	 */
	// 企业所得税指标
	private String index0069F;
	// 企业所得税-最近年度
	private String index0069F_curr_amount;
	// 企业所得税-运算符
	private String index0069F_operator;
	// 企业所得税-连续两年增幅
	private String index0069F_two_rate_up;
	// 企业所得税-连续两年降幅
	private String index0069F_two_rate_down;
	// 企业所得税-连续三年增幅
	private String index0069F_three_rate_up;
	// 企业所得税-连续三年降幅
	private String index0069F_three_rate_down;

	// 个人所得税指标
	private String index0072F;
	// 个人所得税-最近年度
	private String index0072F_curr_amount;
	// 个人所得税-运算符
	private String index0072F_operator;
	// 个人所得税-连续两年增幅
	private String index0072F_two_rate_up;
	// 个人所得税-连续两年降幅
	private String index0072F_two_rate_down;
	// 个人所得税-连续三年增幅
	private String index0072F_three_rate_up;
	// 个人所得税-连续三年降幅
	private String index0072F_three_rate_down;

	// 所得税合计指标
	private String index0073F;
	// 所得税合计-最近年度
	private String index0073F_curr_amount;
	// 所得税合计-运算符
	private String index0073F_operator;
	// 所得税合计-连续两年增幅
	private String index0073F_two_rate_up;
	// 所得税合计-连续两年降幅
	private String index0073F_two_rate_down;
	// 所得税合计-连续三年增幅
	private String index0073F_three_rate_up;
	// 所得税合计-连续三年降幅
	private String index0073F_three_rate_down;

	/**
	 * 其他
	 */
	// 个人所得税申报人数指标
	private String index0074F;
	// 个人所得税申报人数-最近年度
	private String index0074F_curr_amount;
	// 个人所得税申报人数-运算符
	private String index0074F_operator;
	// 个人所得税申报人数-连续两年增幅
	private String index0074F_two_rate_up;
	// 个人所得税申报人数-连续两年降幅
	private String index0074F_two_rate_down;
	// 个人所得税申报人数-连续三年增幅
	private String index0074F_three_rate_up;
	// 个人所得税申报人数-连续三年降幅
	private String index0074F_three_rate_down;

	/*****************************
	 * 其他指标 ***************************
	 */
	/**
	 * 水电气
	 */
	// 水费指标
	private String index0075F;
	// 水费-最近年度
	private String index0075F_curr_amount;
	// 水费-运算符
	private String index0075F_operator;
	// 水费-连续三年增幅
	private String index0075F_two_rate_up;
	// 水费-连续三年降幅
	private String index0075F_two_rate_down;
	// 水费-连续2年增幅
	private String index0075F_three_rate_up;
	// 水费-连续2年降幅
	private String index0075F_three_rate_down;

	// 电费指标
	private String index0076F;
	// 电费-最近年度
	private String index0076F_curr_amount;
	// 电费-运算符
	private String index0076F_operator;
	// 电费-连续两年增幅
	private String index0076F_two_rate_up;
	// 电费-连续两年降幅
	private String index0076F_two_rate_down;
	// 电费-连续三年增幅
	private String index0076F_three_rate_up;
	// 电费-连续三年降幅
	private String index0076F_three_rate_down;

	// 气费指标
	private String index0077F;
	// 气费-最近年度
	private String index0077F_curr_amount;
	// 气费-运算符
	private String index0077F_operator;
	// 气费-连续三年增幅
	private String index0077F_two_rate_up;
	// 气费-连续三年降幅
	private String index0077F_two_rate_down;
	// 气费-连续2年增幅
	private String index0077F_three_rate_up;
	// 气费-连续2年降幅
	private String index0077F_three_rate_down;

	// 用水量指标
	private String index0078F;
	// 用水量-最近年度
	private String index0078F_curr_amount;
	// 用水量-运算符
	private String index0078F_operator;
	// 用水量-连续两年增幅
	private String index0078F_two_rate_up;
	// 用水量-连续两年降幅
	private String index0078F_two_rate_down;
	// 用水量-连续三年增幅
	private String index0078F_three_rate_up;
	// 用水量-连续三年降幅
	private String index0078F_three_rate_down;

	// 用电量指标
	private String index0079F;
	// 用电量-最近年度
	private String index0079F_curr_amount;
	// 用电量-运算符
	private String index0079F_operator;
	// 用电量-连续两年增幅
	private String index0079F_two_rate_up;
	// 用电量-连续两年降幅
	private String index0079F_two_rate_down;
	// 用电量-连续三年增幅
	private String index0079F_three_rate_up;
	// 用电量-连续三年降幅
	private String index0079F_three_rate_down;

	// 用气量
	private String index0080F;
	// 用气量-最近年度
	private String index0080F_curr_amount;
	// 用气量-运算符
	private String index0080F_operator;
	// 用气量-连续两年增幅
	private String index0080F_two_rate_up;
	// 用气量-连续两年降幅
	private String index0080F_two_rate_down;
	// 用气量-连续三年增幅
	private String index0080F_three_rate_up;
	// 用气量-连续三年降幅
	private String index0080F_three_rate_down;

	/**
	 * 社保公积金
	 */
	// 社保缴纳金额指标
	private String index0081F;
	// 社保缴纳金额-最近年度
	private String index0081F_curr_amount;
	// 社保缴纳金额-运算符
	private String index0081F_operator;
	// 社保缴纳金额-连续两年增幅
	private String index0081F_two_rate_up;
	// 社保缴纳金额-连续两年降幅
	private String index0081F_two_rate_down;
	// 社保缴纳金额-连续三年增幅
	private String index0081F_three_rate_up;
	// 社保缴纳金额-连续三年降幅
	private String index0081F_three_rate_down;

	// 公积金缴纳金额指标
	private String index0082F;
	// 公积金缴纳金额-最近年度
	private String index0082F_curr_amount;
	// 公积金缴纳金额-运算符
	private String index0082F_operator;
	// 公积金缴纳金额-连续两年增幅
	private String index0082F_two_rate_up;
	// 公积金缴纳金额-连续两年降幅
	private String index0082F_two_rate_down;
	// 公积金缴纳金额-连续三年增幅
	private String index0082F_three_rate_up;
	// 公积金缴纳金额-连续三年降幅
	private String index0082F_three_rate_down;

	// 社保缴纳人数指标
	private String index0083F;
	// 社保缴纳人数-最近年度
	private String index0083F_curr_amount;
	// 社保缴纳人数-运算符
	private String index0083F_operator;
	// 社保缴纳人数-连续两年增幅
	private String index0083F_two_rate_up;
	// 社保缴纳人数-连续两年降幅
	private String index0083F_two_rate_down;
	// 社保缴纳人数-连续三年增幅
	private String index0083F_three_rate_up;
	// 社保缴纳人数-连续三年降幅
	private String index0083F_three_rate_down;

	// 公积金缴纳人数指标
	private String index0084F;
	// 公积金缴纳人数-最近年度
	private String index0084F_curr_amount;
	// 公积金缴纳人数-运算符
	private String index0084F_operator;
	// 公积金缴纳人数-连续两年增幅
	private String index0084F_two_rate_up;
	// 公积金缴纳人数-连续两年降幅
	private String index0084F_two_rate_down;
	// 公积金缴纳人数-连续三年增幅
	private String index0084F_three_rate_up;
	// 公积金缴纳人数-连续三年降幅
	private String index0084F_three_rate_down;

	/**
	 * 进出口
	 */
	// 进口总值指标
	private String index0085F;
	// 进口总值-最近年度
	private String index0085F_curr_amount;
	// 进口总值-运算符
	private String index0085F_operator;
	// 进口总值-连续两年增幅
	private String index0085F_two_rate_up;
	// 进口总值-连续两年降幅
	private String index0085F_two_rate_down;
	// 进口总值-连续三年增幅
	private String index0085F_three_rate_up;
	// 进口总值-连续三年降幅
	private String index0085F_three_rate_down;

	// 出口总值指标
	private String index0086F;
	// 出口总值-最近年度
	private String index0086F_curr_amount;
	// 出口总值-运算符
	private String index0086F_operator;
	// 出口总值-连续两年增幅
	private String index0086F_two_rate_up;
	// 出口总值-连续两年降幅
	private String index0086F_two_rate_down;
	// 出口总值-连续三年增幅
	private String index0086F_three_rate_up;
	// 出口总值-连续三年降幅
	private String index0086F_three_rate_down;

	/**
	 * 小额贷款状态
	 */
	// 逾期
	private String yuqi;
	// 呆帐
	private String daiz;
	// 存在未结清
	private String czwjq;
	// 无小贷历史
	private String wxdls;
	// 有小贷历史
	private String yxdls;

	/**
	 * 海关评级
	 */
	// 高级认证(AA)
	private String gjrz;
	// 一般认证(A)
	private String ybrz;
	// 失信企业
	private String sxqy;
	// C
	private String hgpjC;
	// D
	private String hgpjD;

	/**
	 * 安全事故记录
	 */
	// 存在较大安全事故
	private String bigaqsg;
	// 无安全事故记录
	private String wuaqsg;

	/**
	 * 税务部门评价
	 */
	// 纳税非正常户
	private String fzcns;
	// 纳税信用等级A级
	private String lsdjA;

	/**
	 * 环保信用评价
	 */
	// 绿色
	private String green;
	// 蓝色
	private String blue;
	// 红色
	private String yellow;
	// 红色
	private String red;
	// 黑色
	private String black;

	/**
	 * 行政处罚
	 */
	// 处罚次数指标
	private String index0092E;
	// 处罚次数
	private String index0092E_count;
	// 处罚次数运算符
	private String index0092E_operator;

	// 或 且 符号标识 add by hechengtai at 2017-5-10 15:10:58 for BugID:1199
	private String index0092E_operator_symbol;

	// 处罚金额指标
	private String index0093E;
	// 处罚金额
	private String index0093E_count;
	// 处罚金额运算符
	private String index0093E_operator;

	/**
	 * 社保公积金欠费欠缴
	 */
	// 欠缴次数指标
	private String index0094E;
	// 欠缴次数
	private String index0094E_count;
	// 欠缴次数运算符
	private String index0094E_operator;
	// 或 且 符号标识 add by hechengtai at 2017-5-10 15:10:58 for BugID:1199
	private String index0094E_operator_symbol;
	// 欠缴金额指标
	private String index0095E;
	// 欠缴金额
	private String index0095E_count;
	// 欠缴金额运算符
	private String index0095E_operator;

	/**
	 * 银行业金融机构贷款情况 add by hechengtai 2017-4-11 13:53:40 begin
	 */
	// 贷款余额指标
	private String index0108E;
	// 贷款余额数
	private String index0108E_count;
	// 贷款余额运算符
	private String index0108E_operator;

	// 授信银行数指标
	private String index0109E;
	// 授信银行数数
	private String index0109E_count;
	// 授信银行数运算符
	private String index0109E_operator;

	// 不良贷款余额指标
	private String index0110E;
	// 不良贷款余额数
	private String index0110E_count;
	// 不良贷款余额运算符
	private String index0110E_operator;

	// 未结清贷款笔数指标
	private String index0111E;
	// 未结清贷款笔数数
	private String index0111E_count;
	// 未结清贷款笔数运算符
	private String index0111E_operator;

	/**
	 * 是否授权 标识
	 */
	// 是否授权 标识 - 是
	private String authFlagYes ="false";

	// 是否授权 标识 - 否
	private String authFlagNo ="fasle";

	/**
	 * 银行业金融机构贷款情况 add by hechengtai 2017-4-11 13:53:40 end
	 */

	/**
	 * 荣誉表彰
	 */
	// 获省级荣誉表彰
	private String srybz;
	// 获市级荣誉表彰
	private String cityrybz;
	// 获县级荣誉表彰
	private String xrybz;

	/**
	 * 返回参数
	 */

	// 序号
	private String id;

	// 企业名称corp_name

	// 信用分
	private String creditscore;

	// 信用等级
	private String xydji;

	// 评价日期
	private String pjdate;

	// 是否有贷户loan_flag
	private String loanflaginfo;

	// 操作信用报告
	private String creditreport;

	// 授权标志
	private String authflaginfo;
	// 金融办授权标志
	private String financeauthflaginfo;


	public CapCompanyInfoVo() {

		mapvo.put("Index1999F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index1999F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index1999F_operator", "index1999F_operator");
		mapcolumnvo.put("Index1999F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index1999F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index1999F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index1999F_three_rate_down", "THREE_RATE");

		mapvo.put("Index2999F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index2999F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index2999F_operator", "index2999F_operator");
		mapcolumnvo.put("Index2999F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index2999F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index2999F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index2999F_three_rate_down", "THREE_RATE");

		mapvo.put("Index4001F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index4001F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index4001F_operator", "index4001F_operator");
		mapcolumnvo.put("Index4001F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index4001F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index4001F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index4001F_three_rate_down", "THREE_RATE");

		mapvo.put("Index4998F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index4998F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index4998F_operator", "index4998F_operator");
		mapcolumnvo.put("Index4998F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index4998F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index4998F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index4998F_three_rate_down", "THREE_RATE");

		mapvo.put("Index6001F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index6001F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index6001F_operator", "index6001F_operator");
		mapcolumnvo.put("Index6001F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index6001F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index6001F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index6001F_three_rate_down", "THREE_RATE");

		mapvo.put("Index6799F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index6799F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index6799F_operator", "index6799F_operator");
		mapcolumnvo.put("Index6799F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index6799F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index6799F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index6799F_three_rate_down", "THREE_RATE");

		mapvo.put("Index6899F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index6899F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index6899F_operator", "index6899F_operator");
		mapcolumnvo.put("Index6899F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index6899F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index6899F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index6899F_three_rate_down", "THREE_RATE");

		mapvo.put("Index6999F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index6999F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index6999F_operator", "index6999F_operator");
		mapcolumnvo.put("Index6999F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index6999F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index6999F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index6999F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8023F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8023F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8023F_operator", "index8023F_operator");
		mapcolumnvo.put("Index8023F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8023F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8023F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8023F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8024F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8024F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8024F_operator", "index8024F_operator");
		mapcolumnvo.put("Index8024F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8024F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8024F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8024F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8052F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8052F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8052F_operator", "index8052F_operator");
		mapcolumnvo.put("Index8052F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8052F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8052F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8052F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8021F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8021F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8021F_operator", "index8021F_operator");
		mapcolumnvo.put("Index8021F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8021F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8021F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8021F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8022F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8022F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8022F_operator", "index8022F_operator");
		mapcolumnvo.put("Index8022F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8022F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8022F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8022F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8041F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8041F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8041F_operator", "index8041F_operator");
		mapcolumnvo.put("Index8041F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8041F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8041F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8041F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8042F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8042F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8042F_operator", "index8042F_operator");
		mapcolumnvo.put("Index8042F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8042F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8042F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8042F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8043F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8043F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8043F_operator", "index8043F_operator");
		mapcolumnvo.put("Index8043F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8043F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8043F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8043F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8003F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8003F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8003F_operator", "index8003F_operator");
		mapcolumnvo.put("Index8003F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8003F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8003F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8003F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8051F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8051F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8051F_operator", "index8051F_operator");
		mapcolumnvo.put("Index8051F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8051F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8051F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8051F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8001F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8001F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8001F_operator", "index8001F_operator");
		mapcolumnvo.put("Index8001F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8001F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8001F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8001F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8002F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8002F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8002F_operator", "index8002F_operator");
		mapcolumnvo.put("Index8002F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8002F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8002F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8002F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8004F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8004F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8004F_operator", "index8004F_operator");
		mapcolumnvo.put("Index8004F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8004F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8004F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8004F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8015F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8015F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8015F_operator", "index8015F_operator");
		mapcolumnvo.put("Index8015F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8015F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8015F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8015F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8012F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8012F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8012F_operator", "index8012F_operator");
		mapcolumnvo.put("Index8012F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8012F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8012F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8012F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8011F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8011F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8011F_operator", "index8011F_operator");
		mapcolumnvo.put("Index8011F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8011F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8011F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8011F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8013F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8013F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8013F_operator", "index8013F_operator");
		mapcolumnvo.put("Index8013F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8013F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8013F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8013F_three_rate_down", "THREE_RATE");

		mapvo.put("Index8014F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index8014F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index8014F_operator", "index8014F_operator");
		mapcolumnvo.put("Index8014F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index8014F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index8014F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index8014F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0067F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0067F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0067F_operator", "index0067F_operator");
		mapcolumnvo.put("Index0067F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0067F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0067F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0067F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0070F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0070F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0070F_operator", "index0070F_operator");
		mapcolumnvo.put("Index0070F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0070F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0070F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0070F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0068F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0068F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0068F_operator", "index0068F_operator");
		mapcolumnvo.put("Index0068F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0068F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0068F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0068F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0071F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0071F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0071F_operator", "index0071F_operator");
		mapcolumnvo.put("Index0071F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0071F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0071F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0071F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0069F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0069F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0069F_operator", "index0069F_operator");
		mapcolumnvo.put("Index0069F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0069F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0069F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0069F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0072F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0072F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0072F_operator", "index0072F_operator");
		mapcolumnvo.put("Index0072F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0072F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0072F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0072F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0073F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0073F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0073F_operator", "index0073F_operator");
		mapcolumnvo.put("Index0073F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0073F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0073F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0073F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0074F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0074F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0074F_operator", "index0074F_operator");
		mapcolumnvo.put("Index0074F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0074F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0074F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0074F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0075F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0075F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0075F_operator", "index0075F_operator");
		mapcolumnvo.put("Index0075F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0075F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0075F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0075F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0076F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0076F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0076F_operator", "index0076F_operator");
		mapcolumnvo.put("Index0076F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0076F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0076F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0076F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0077F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0077F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0077F_operator", "index0077F_operator");
		mapcolumnvo.put("Index0077F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0077F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0077F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0077F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0078F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0078F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0078F_operator", "index0078F_operator");
		mapcolumnvo.put("Index0078F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0078F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0078F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0078F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0079F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0079F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0079F_operator", "index0079F_operator");
		mapcolumnvo.put("Index0079F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0079F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0079F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0079F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0080F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0080F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0080F_operator", "index0080F_operator");
		mapcolumnvo.put("Index0080F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0080F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0080F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0080F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0085F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0085F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0085F_operator", "index0085F_operator");
		mapcolumnvo.put("Index0085F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0085F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0085F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0085F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0086F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0086F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0086F_operator", "index0086F_operator");
		mapcolumnvo.put("Index0086F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0086F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0086F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0086F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0081F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0081F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0081F_operator", "index0081F_operator");
		mapcolumnvo.put("Index0081F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0081F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0081F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0081F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0082F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0082F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0082F_operator", "index0082F_operator");
		mapcolumnvo.put("Index0082F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0082F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0082F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0082F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0083F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0083F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0083F_operator", "index0083F_operator");
		mapcolumnvo.put("Index0083F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0083F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0083F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0083F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0084F", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0084F_curr_amount", "CURR_AMOUNT");
		mapcolumnvo.put("Index0084F_operator", "index0084F_operator");
		mapcolumnvo.put("Index0084F_two_rate_up", "TWO_RATE");
		mapcolumnvo.put("Index0084F_two_rate_down", "TWO_RATE");
		mapcolumnvo.put("Index0084F_three_rate_up", "THREE_RATE");
		mapcolumnvo.put("Index0084F_three_rate_down", "THREE_RATE");

		mapvo.put("Index0092E", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0092E_operator", "index0092E_operator");
		mapcolumnvo.put("Index0092E_operator_symbol", "index0092E_operator_symbol");
		mapcolumnvo.put("Index0092E_count", "ACCOUNT_OPTION");

		mapvo.put("Index0093E", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0093E_operator", "index0093E_operator");
		mapcolumnvo.put("Index0093E_count", "ACCOUNT_OPTION");

		mapvo.put("Index0094E", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0094E_operator", "index0094E_operator");
		mapcolumnvo.put("Index0094E_operator_symbol", "index0094E_operator_symbol");
		mapcolumnvo.put("Index0094E_count", "ACCOUNT_OPTION");

		mapvo.put("Index0095E", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0095E_operator", "index0095E_operator");
		mapcolumnvo.put("Index0095E_count", "ACCOUNT_OPTION");

		// add by hechengtai at 2017-4-11 14:49:07 begin
		mapvo.put("Index0108E", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0108E_operator", "index0108E_operator");
		mapcolumnvo.put("Index0108E_count", "ACCOUNT_OPTION");

		mapvo.put("Index0109E", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0109E_operator", "index0109E_operator");
		mapcolumnvo.put("Index0109E_count", "ACCOUNT_OPTION");

		mapvo.put("Index0110E", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0110E_operator", "index0110E_operator");
		mapcolumnvo.put("Index0110E_count", "ACCOUNT_OPTION");

		mapvo.put("Index0111E", "ACCOUNT_CODE");
		mapcolumnvo.put("Index0111E_operator", "index0111E_operator");
		mapcolumnvo.put("Index0111E_count", "ACCOUNT_OPTION");

		// add by hechengtai at 2017-4-11 14:49:07 end
	}

	public Object getScale() {
		return scale;
	}

	public void setScale(Object scale) {
		this.scale = scale;
	}

	public Object getRegion() {
		return region;
	}

	public void setRegion(Object region) {
		this.region = region;
	}

	public Object getIndustry() {
		return industry;
	}

	public void setIndustry(Object industry) {
		this.industry = industry;
	}

	public Object getRegister_type() {
		return register_type;
	}

	public void setRegister_type(Object register_type) {
		this.register_type = register_type;
	}

	public Object getInvestment_type() {
		return investment_type;
	}

	public void setInvestment_type(Object investment_type) {
		this.investment_type = investment_type;
	}

	public Object getYearnum() {
		return yearnum;
	}

	public void setYearnum(Object yearnum) {
		this.yearnum = yearnum;
	}

	public Object getAuth() {
		return auth;
	}

	public void setAuth(Object auth) {
		this.auth = auth;
	}

	public Object getLend() {
		return lend;
	}

	public void setLend(Object lend) {
		this.lend = lend;
	}

	public Object getLoan_status() {
		return loan_status;
	}

	public void setLoan_status(Object loan_status) {
		this.loan_status = loan_status;
	}

	public Object getHg_status() {
		return hg_status;
	}

	public void setHg_status(Object hg_status) {
		this.hg_status = hg_status;
	}

	public Object getAq_status() {
		return aq_status;
	}

	public void setAq_status(Object aq_status) {
		this.aq_status = aq_status;
	}

	public Object getNashui_status() {
		return nashui_status;
	}

	public void setNashui_status(Object nashui_status) {
		this.nashui_status = nashui_status;
	}

	public Object getHb_status() {
		return hb_status;
	}

	public void setHb_status(Object hb_status) {
		this.hb_status = hb_status;
	}

	public Object getRongyu_status() {
		return rongyu_status;
	}

	public void setRongyu_status(Object rongyu_status) {
		this.rongyu_status = rongyu_status;
	}

	public Object getPffzstart() {
		return pffzstart;
	}

	public void setPffzstart(Object pffzstart) {
		this.pffzstart = pffzstart;
	}

	public Object getPffzend() {
		return pffzend;
	}

	public void setPffzend(Object pffzend) {
		this.pffzend = pffzend;
	}

	public Object getXyfdj() {
		return xyfdj;
	}

	public void setXyfdj(Object xyfdj) {
		this.xyfdj = xyfdj;
	}

	public Object getSs_status() {
		return ss_status;
	}

	public void setSs_status(Object ss_status) {
		this.ss_status = ss_status;
	}

	public Object getYhjr_status() {
		return yhjr_status;
	}

	public void setYhjr_status(Object yhjr_status) {
		this.yhjr_status = yhjr_status;
	}

	public String getLoanflaginfo() {
		return loanflaginfo;
	}

	public void setLoanflaginfo(String loanflaginfo) {
		this.loanflaginfo = loanflaginfo;
	}

	public String getIndex1999F() {
		return index1999F;
	}

	public void setIndex1999F(String index1999F) {
		this.index1999F = index1999F;
	}

	public String getIndex1999F_curr_amount() {
		return index1999F_curr_amount;
	}

	public void setIndex1999F_curr_amount(String index1999F_curr_amount) {
		this.index1999F_curr_amount = index1999F_curr_amount;
	}

	public String getIndex1999F_operator() {
		return index1999F_operator;
	}

	public void setIndex1999F_operator(String index1999F_operator) {
		this.index1999F_operator = index1999F_operator;
	}

	public String getIndex1999F_two_rate_up() {
		return index1999F_two_rate_up;
	}

	public void setIndex1999F_two_rate_up(String index1999F_two_rate_up) {
		this.index1999F_two_rate_up = index1999F_two_rate_up;
	}

	public String getIndex1999F_two_rate_down() {
		return index1999F_two_rate_down;
	}

	public void setIndex1999F_two_rate_down(String index1999F_two_rate_down) {
		this.index1999F_two_rate_down = index1999F_two_rate_down;
	}

	public String getIndex1999F_three_rate_up() {
		return index1999F_three_rate_up;
	}

	public void setIndex1999F_three_rate_up(String index1999F_three_rate_up) {
		this.index1999F_three_rate_up = index1999F_three_rate_up;
	}

	public String getIndex1999F_three_rate_down() {
		return index1999F_three_rate_down;
	}

	public void setIndex1999F_three_rate_down(String index1999F_three_rate_down) {
		this.index1999F_three_rate_down = index1999F_three_rate_down;
	}

	public String getIndex2999F() {
		return index2999F;
	}

	public void setIndex2999F(String index2999F) {
		this.index2999F = index2999F;
	}

	public String getIndex2999F_curr_amount() {
		return index2999F_curr_amount;
	}

	public void setIndex2999F_curr_amount(String index2999F_curr_amount) {
		this.index2999F_curr_amount = index2999F_curr_amount;
	}

	public String getIndex2999F_operator() {
		return index2999F_operator;
	}

	public void setIndex2999F_operator(String index2999F_operator) {
		this.index2999F_operator = index2999F_operator;
	}

	public String getIndex2999F_two_rate_up() {
		return index2999F_two_rate_up;
	}

	public void setIndex2999F_two_rate_up(String index2999F_two_rate_up) {
		this.index2999F_two_rate_up = index2999F_two_rate_up;
	}

	public String getIndex2999F_two_rate_down() {
		return index2999F_two_rate_down;
	}

	public void setIndex2999F_two_rate_down(String index2999F_two_rate_down) {
		this.index2999F_two_rate_down = index2999F_two_rate_down;
	}

	public String getIndex2999F_three_rate_up() {
		return index2999F_three_rate_up;
	}

	public void setIndex2999F_three_rate_up(String index2999F_three_rate_up) {
		this.index2999F_three_rate_up = index2999F_three_rate_up;
	}

	public String getIndex2999F_three_rate_down() {
		return index2999F_three_rate_down;
	}

	public void setIndex2999F_three_rate_down(String index2999F_three_rate_down) {
		this.index2999F_three_rate_down = index2999F_three_rate_down;
	}

	public String getIndex4001F() {
		return index4001F;
	}

	public void setIndex4001F(String index4001F) {
		this.index4001F = index4001F;
	}

	public String getIndex4001F_curr_amount() {
		return index4001F_curr_amount;
	}

	public void setIndex4001F_curr_amount(String index4001F_curr_amount) {
		this.index4001F_curr_amount = index4001F_curr_amount;
	}

	public String getIndex4001F_operator() {
		return index4001F_operator;
	}

	public void setIndex4001F_operator(String index4001F_operator) {
		this.index4001F_operator = index4001F_operator;
	}

	public String getIndex4001F_two_rate_up() {
		return index4001F_two_rate_up;
	}

	public void setIndex4001F_two_rate_up(String index4001F_two_rate_up) {
		this.index4001F_two_rate_up = index4001F_two_rate_up;
	}

	public String getIndex4001F_two_rate_down() {
		return index4001F_two_rate_down;
	}

	public void setIndex4001F_two_rate_down(String index4001F_two_rate_down) {
		this.index4001F_two_rate_down = index4001F_two_rate_down;
	}

	public String getIndex4001F_three_rate_up() {
		return index4001F_three_rate_up;
	}

	public void setIndex4001F_three_rate_up(String index4001F_three_rate_up) {
		this.index4001F_three_rate_up = index4001F_three_rate_up;
	}

	public String getIndex4001F_three_rate_down() {
		return index4001F_three_rate_down;
	}

	public void setIndex4001F_three_rate_down(String index4001F_three_rate_down) {
		this.index4001F_three_rate_down = index4001F_three_rate_down;
	}

	public String getIndex4998F() {
		return index4998F;
	}

	public void setIndex4998F(String index4998F) {
		this.index4998F = index4998F;
	}

	public String getIndex4998F_curr_amount() {
		return index4998F_curr_amount;
	}

	public void setIndex4998F_curr_amount(String index4998F_curr_amount) {
		this.index4998F_curr_amount = index4998F_curr_amount;
	}

	public String getIndex4998F_operator() {
		return index4998F_operator;
	}

	public void setIndex4998F_operator(String index4998F_operator) {
		this.index4998F_operator = index4998F_operator;
	}

	public String getIndex4998F_two_rate_up() {
		return index4998F_two_rate_up;
	}

	public void setIndex4998F_two_rate_up(String index4998F_two_rate_up) {
		this.index4998F_two_rate_up = index4998F_two_rate_up;
	}

	public String getIndex4998F_two_rate_down() {
		return index4998F_two_rate_down;
	}

	public void setIndex4998F_two_rate_down(String index4998F_two_rate_down) {
		this.index4998F_two_rate_down = index4998F_two_rate_down;
	}

	public String getIndex4998F_three_rate_up() {
		return index4998F_three_rate_up;
	}

	public void setIndex4998F_three_rate_up(String index4998F_three_rate_up) {
		this.index4998F_three_rate_up = index4998F_three_rate_up;
	}

	public String getIndex4998F_three_rate_down() {
		return index4998F_three_rate_down;
	}

	public void setIndex4998F_three_rate_down(String index4998F_three_rate_down) {
		this.index4998F_three_rate_down = index4998F_three_rate_down;
	}

	public String getIndex6001F() {
		return index6001F;
	}

	public void setIndex6001F(String index6001F) {
		this.index6001F = index6001F;
	}

	public String getIndex6001F_curr_amount() {
		return index6001F_curr_amount;
	}

	public void setIndex6001F_curr_amount(String index6001F_curr_amount) {
		this.index6001F_curr_amount = index6001F_curr_amount;
	}

	public String getIndex6001F_operator() {
		return index6001F_operator;
	}

	public void setIndex6001F_operator(String index6001F_operator) {
		this.index6001F_operator = index6001F_operator;
	}

	public String getIndex6001F_two_rate_up() {
		return index6001F_two_rate_up;
	}

	public void setIndex6001F_two_rate_up(String index6001F_two_rate_up) {
		this.index6001F_two_rate_up = index6001F_two_rate_up;
	}

	public String getIndex6001F_two_rate_down() {
		return index6001F_two_rate_down;
	}

	public void setIndex6001F_two_rate_down(String index6001F_two_rate_down) {
		this.index6001F_two_rate_down = index6001F_two_rate_down;
	}

	public String getIndex6001F_three_rate_up() {
		return index6001F_three_rate_up;
	}

	public void setIndex6001F_three_rate_up(String index6001F_three_rate_up) {
		this.index6001F_three_rate_up = index6001F_three_rate_up;
	}

	public String getIndex6001F_three_rate_down() {
		return index6001F_three_rate_down;
	}

	public void setIndex6001F_three_rate_down(String index6001F_three_rate_down) {
		this.index6001F_three_rate_down = index6001F_three_rate_down;
	}

	public String getIndex6799F() {
		return index6799F;
	}

	public void setIndex6799F(String index6799F) {
		this.index6799F = index6799F;
	}

	public String getIndex6799F_curr_amount() {
		return index6799F_curr_amount;
	}

	public void setIndex6799F_curr_amount(String index6799F_curr_amount) {
		this.index6799F_curr_amount = index6799F_curr_amount;
	}

	public String getIndex6799F_operator() {
		return index6799F_operator;
	}

	public void setIndex6799F_operator(String index6799F_operator) {
		this.index6799F_operator = index6799F_operator;
	}

	public String getIndex6799F_two_rate_up() {
		return index6799F_two_rate_up;
	}

	public void setIndex6799F_two_rate_up(String index6799F_two_rate_up) {
		this.index6799F_two_rate_up = index6799F_two_rate_up;
	}

	public String getIndex6799F_two_rate_down() {
		return index6799F_two_rate_down;
	}

	public void setIndex6799F_two_rate_down(String index6799F_two_rate_down) {
		this.index6799F_two_rate_down = index6799F_two_rate_down;
	}

	public String getIndex6799F_three_rate_up() {
		return index6799F_three_rate_up;
	}

	public void setIndex6799F_three_rate_up(String index6799F_three_rate_up) {
		this.index6799F_three_rate_up = index6799F_three_rate_up;
	}

	public String getIndex6799F_three_rate_down() {
		return index6799F_three_rate_down;
	}

	public void setIndex6799F_three_rate_down(String index6799F_three_rate_down) {
		this.index6799F_three_rate_down = index6799F_three_rate_down;
	}

	public String getIndex6899F() {
		return index6899F;
	}

	public void setIndex6899F(String index6899F) {
		this.index6899F = index6899F;
	}

	public String getIndex6899F_curr_amount() {
		return index6899F_curr_amount;
	}

	public void setIndex6899F_curr_amount(String index6899F_curr_amount) {
		this.index6899F_curr_amount = index6899F_curr_amount;
	}

	public String getIndex6899F_operator() {
		return index6899F_operator;
	}

	public void setIndex6899F_operator(String index6899F_operator) {
		this.index6899F_operator = index6899F_operator;
	}

	public String getIndex6899F_two_rate_up() {
		return index6899F_two_rate_up;
	}

	public void setIndex6899F_two_rate_up(String index6899F_two_rate_up) {
		this.index6899F_two_rate_up = index6899F_two_rate_up;
	}

	public String getIndex6899F_two_rate_down() {
		return index6899F_two_rate_down;
	}

	public void setIndex6899F_two_rate_down(String index6899F_two_rate_down) {
		this.index6899F_two_rate_down = index6899F_two_rate_down;
	}

	public String getIndex6899F_three_rate_up() {
		return index6899F_three_rate_up;
	}

	public void setIndex6899F_three_rate_up(String index6899F_three_rate_up) {
		this.index6899F_three_rate_up = index6899F_three_rate_up;
	}

	public String getIndex6899F_three_rate_down() {
		return index6899F_three_rate_down;
	}

	public void setIndex6899F_three_rate_down(String index6899F_three_rate_down) {
		this.index6899F_three_rate_down = index6899F_three_rate_down;
	}

	public String getIndex6999F() {
		return index6999F;
	}

	public void setIndex6999F(String index6999F) {
		this.index6999F = index6999F;
	}

	public String getIndex6999F_curr_amount() {
		return index6999F_curr_amount;
	}

	public void setIndex6999F_curr_amount(String index6999F_curr_amount) {
		this.index6999F_curr_amount = index6999F_curr_amount;
	}

	public String getIndex6999F_operator() {
		return index6999F_operator;
	}

	public void setIndex6999F_operator(String index6999F_operator) {
		this.index6999F_operator = index6999F_operator;
	}

	public String getIndex6999F_two_rate_up() {
		return index6999F_two_rate_up;
	}

	public void setIndex6999F_two_rate_up(String index6999F_two_rate_up) {
		this.index6999F_two_rate_up = index6999F_two_rate_up;
	}

	public String getIndex6999F_two_rate_down() {
		return index6999F_two_rate_down;
	}

	public void setIndex6999F_two_rate_down(String index6999F_two_rate_down) {
		this.index6999F_two_rate_down = index6999F_two_rate_down;
	}

	public String getIndex6999F_three_rate_up() {
		return index6999F_three_rate_up;
	}

	public void setIndex6999F_three_rate_up(String index6999F_three_rate_up) {
		this.index6999F_three_rate_up = index6999F_three_rate_up;
	}

	public String getIndex6999F_three_rate_down() {
		return index6999F_three_rate_down;
	}

	public void setIndex6999F_three_rate_down(String index6999F_three_rate_down) {
		this.index6999F_three_rate_down = index6999F_three_rate_down;
	}

	public String getIndex8023F() {
		return index8023F;
	}

	public void setIndex8023F(String index8023F) {
		this.index8023F = index8023F;
	}

	public String getIndex8023F_curr_amount() {
		return index8023F_curr_amount;
	}

	public void setIndex8023F_curr_amount(String index8023F_curr_amount) {
		this.index8023F_curr_amount = index8023F_curr_amount;
	}

	public String getIndex8023F_operator() {
		return index8023F_operator;
	}

	public void setIndex8023F_operator(String index8023F_operator) {
		this.index8023F_operator = index8023F_operator;
	}

	public String getIndex8023F_two_rate_up() {
		return index8023F_two_rate_up;
	}

	public void setIndex8023F_two_rate_up(String index8023F_two_rate_up) {
		this.index8023F_two_rate_up = index8023F_two_rate_up;
	}

	public String getIndex8023F_two_rate_down() {
		return index8023F_two_rate_down;
	}

	public void setIndex8023F_two_rate_down(String index8023F_two_rate_down) {
		this.index8023F_two_rate_down = index8023F_two_rate_down;
	}

	public String getIndex8023F_three_rate_up() {
		return index8023F_three_rate_up;
	}

	public void setIndex8023F_three_rate_up(String index8023F_three_rate_up) {
		this.index8023F_three_rate_up = index8023F_three_rate_up;
	}

	public String getIndex8023F_three_rate_down() {
		return index8023F_three_rate_down;
	}

	public void setIndex8023F_three_rate_down(String index8023F_three_rate_down) {
		this.index8023F_three_rate_down = index8023F_three_rate_down;
	}

	public String getIndex8024F() {
		return index8024F;
	}

	public void setIndex8024F(String index8024F) {
		this.index8024F = index8024F;
	}

	public String getIndex8024F_curr_amount() {
		return index8024F_curr_amount;
	}

	public void setIndex8024F_curr_amount(String index8024F_curr_amount) {
		this.index8024F_curr_amount = index8024F_curr_amount;
	}

	public String getIndex8024F_operator() {
		return index8024F_operator;
	}

	public void setIndex8024F_operator(String index8024F_operator) {
		this.index8024F_operator = index8024F_operator;
	}

	public String getIndex8024F_two_rate_up() {
		return index8024F_two_rate_up;
	}

	public void setIndex8024F_two_rate_up(String index8024F_two_rate_up) {
		this.index8024F_two_rate_up = index8024F_two_rate_up;
	}

	public String getIndex8024F_two_rate_down() {
		return index8024F_two_rate_down;
	}

	public void setIndex8024F_two_rate_down(String index8024F_two_rate_down) {
		this.index8024F_two_rate_down = index8024F_two_rate_down;
	}

	public String getIndex8024F_three_rate_up() {
		return index8024F_three_rate_up;
	}

	public void setIndex8024F_three_rate_up(String index8024F_three_rate_up) {
		this.index8024F_three_rate_up = index8024F_three_rate_up;
	}

	public String getIndex8024F_three_rate_down() {
		return index8024F_three_rate_down;
	}

	public void setIndex8024F_three_rate_down(String index8024F_three_rate_down) {
		this.index8024F_three_rate_down = index8024F_three_rate_down;
	}

	public String getIndex8052F() {
		return index8052F;
	}

	public void setIndex8052F(String index8052F) {
		this.index8052F = index8052F;
	}

	public String getIndex8052F_curr_amount() {
		return index8052F_curr_amount;
	}

	public void setIndex8052F_curr_amount(String index8052F_curr_amount) {
		this.index8052F_curr_amount = index8052F_curr_amount;
	}

	public String getIndex8052F_operator() {
		return index8052F_operator;
	}

	public void setIndex8052F_operator(String index8052F_operator) {
		this.index8052F_operator = index8052F_operator;
	}

	public String getIndex8052F_two_rate_up() {
		return index8052F_two_rate_up;
	}

	public void setIndex8052F_two_rate_up(String index8052F_two_rate_up) {
		this.index8052F_two_rate_up = index8052F_two_rate_up;
	}

	public String getIndex8052F_two_rate_down() {
		return index8052F_two_rate_down;
	}

	public void setIndex8052F_two_rate_down(String index8052F_two_rate_down) {
		this.index8052F_two_rate_down = index8052F_two_rate_down;
	}

	public String getIndex8052F_three_rate_up() {
		return index8052F_three_rate_up;
	}

	public void setIndex8052F_three_rate_up(String index8052F_three_rate_up) {
		this.index8052F_three_rate_up = index8052F_three_rate_up;
	}

	public String getIndex8052F_three_rate_down() {
		return index8052F_three_rate_down;
	}

	public void setIndex8052F_three_rate_down(String index8052F_three_rate_down) {
		this.index8052F_three_rate_down = index8052F_three_rate_down;
	}

	public String getIndex8021F() {
		return index8021F;
	}

	public void setIndex8021F(String index8021F) {
		this.index8021F = index8021F;
	}

	public String getIndex8021F_curr_amount() {
		return index8021F_curr_amount;
	}

	public void setIndex8021F_curr_amount(String index8021F_curr_amount) {
		this.index8021F_curr_amount = index8021F_curr_amount;
	}

	public String getIndex8021F_operator() {
		return index8021F_operator;
	}

	public void setIndex8021F_operator(String index8021F_operator) {
		this.index8021F_operator = index8021F_operator;
	}

	public String getIndex8021F_two_rate_up() {
		return index8021F_two_rate_up;
	}

	public void setIndex8021F_two_rate_up(String index8021F_two_rate_up) {
		this.index8021F_two_rate_up = index8021F_two_rate_up;
	}

	public String getIndex8021F_two_rate_down() {
		return index8021F_two_rate_down;
	}

	public void setIndex8021F_two_rate_down(String index8021F_two_rate_down) {
		this.index8021F_two_rate_down = index8021F_two_rate_down;
	}

	public String getIndex8021F_three_rate_up() {
		return index8021F_three_rate_up;
	}

	public void setIndex8021F_three_rate_up(String index8021F_three_rate_up) {
		this.index8021F_three_rate_up = index8021F_three_rate_up;
	}

	public String getIndex8021F_three_rate_down() {
		return index8021F_three_rate_down;
	}

	public void setIndex8021F_three_rate_down(String index8021F_three_rate_down) {
		this.index8021F_three_rate_down = index8021F_three_rate_down;
	}

	public String getIndex8022F() {
		return index8022F;
	}

	public void setIndex8022F(String index8022F) {
		this.index8022F = index8022F;
	}

	public String getIndex8022F_curr_amount() {
		return index8022F_curr_amount;
	}

	public void setIndex8022F_curr_amount(String index8022F_curr_amount) {
		this.index8022F_curr_amount = index8022F_curr_amount;
	}

	public String getIndex8022F_operator() {
		return index8022F_operator;
	}

	public void setIndex8022F_operator(String index8022F_operator) {
		this.index8022F_operator = index8022F_operator;
	}

	public String getIndex8022F_two_rate_up() {
		return index8022F_two_rate_up;
	}

	public void setIndex8022F_two_rate_up(String index8022F_two_rate_up) {
		this.index8022F_two_rate_up = index8022F_two_rate_up;
	}

	public String getIndex8022F_two_rate_down() {
		return index8022F_two_rate_down;
	}

	public void setIndex8022F_two_rate_down(String index8022F_two_rate_down) {
		this.index8022F_two_rate_down = index8022F_two_rate_down;
	}

	public String getIndex8022F_three_rate_up() {
		return index8022F_three_rate_up;
	}

	public void setIndex8022F_three_rate_up(String index8022F_three_rate_up) {
		this.index8022F_three_rate_up = index8022F_three_rate_up;
	}

	public String getIndex8022F_three_rate_down() {
		return index8022F_three_rate_down;
	}

	public void setIndex8022F_three_rate_down(String index8022F_three_rate_down) {
		this.index8022F_three_rate_down = index8022F_three_rate_down;
	}

	public String getIndex8041F() {
		return index8041F;
	}

	public void setIndex8041F(String index8041F) {
		this.index8041F = index8041F;
	}

	public String getIndex8041F_curr_amount() {
		return index8041F_curr_amount;
	}

	public void setIndex8041F_curr_amount(String index8041F_curr_amount) {
		this.index8041F_curr_amount = index8041F_curr_amount;
	}

	public String getIndex8041F_operator() {
		return index8041F_operator;
	}

	public void setIndex8041F_operator(String index8041F_operator) {
		this.index8041F_operator = index8041F_operator;
	}

	public String getIndex8041F_two_rate_up() {
		return index8041F_two_rate_up;
	}

	public void setIndex8041F_two_rate_up(String index8041F_two_rate_up) {
		this.index8041F_two_rate_up = index8041F_two_rate_up;
	}

	public String getIndex8041F_two_rate_down() {
		return index8041F_two_rate_down;
	}

	public void setIndex8041F_two_rate_down(String index8041F_two_rate_down) {
		this.index8041F_two_rate_down = index8041F_two_rate_down;
	}

	public String getIndex8041F_three_rate_up() {
		return index8041F_three_rate_up;
	}

	public void setIndex8041F_three_rate_up(String index8041F_three_rate_up) {
		this.index8041F_three_rate_up = index8041F_three_rate_up;
	}

	public String getIndex8041F_three_rate_down() {
		return index8041F_three_rate_down;
	}

	public void setIndex8041F_three_rate_down(String index8041F_three_rate_down) {
		this.index8041F_three_rate_down = index8041F_three_rate_down;
	}

	public String getIndex8042F() {
		return index8042F;
	}

	public void setIndex8042F(String index8042F) {
		this.index8042F = index8042F;
	}

	public String getIndex8042F_curr_amount() {
		return index8042F_curr_amount;
	}

	public void setIndex8042F_curr_amount(String index8042F_curr_amount) {
		this.index8042F_curr_amount = index8042F_curr_amount;
	}

	public String getIndex8042F_operator() {
		return index8042F_operator;
	}

	public void setIndex8042F_operator(String index8042F_operator) {
		this.index8042F_operator = index8042F_operator;
	}

	public String getIndex8042F_two_rate_up() {
		return index8042F_two_rate_up;
	}

	public void setIndex8042F_two_rate_up(String index8042F_two_rate_up) {
		this.index8042F_two_rate_up = index8042F_two_rate_up;
	}

	public String getIndex8042F_two_rate_down() {
		return index8042F_two_rate_down;
	}

	public void setIndex8042F_two_rate_down(String index8042F_two_rate_down) {
		this.index8042F_two_rate_down = index8042F_two_rate_down;
	}

	public String getIndex8042F_three_rate_up() {
		return index8042F_three_rate_up;
	}

	public void setIndex8042F_three_rate_up(String index8042F_three_rate_up) {
		this.index8042F_three_rate_up = index8042F_three_rate_up;
	}

	public String getIndex8042F_three_rate_down() {
		return index8042F_three_rate_down;
	}

	public void setIndex8042F_three_rate_down(String index8042F_three_rate_down) {
		this.index8042F_three_rate_down = index8042F_three_rate_down;
	}

	public String getIndex8043F() {
		return index8043F;
	}

	public void setIndex8043F(String index8043F) {
		this.index8043F = index8043F;
	}

	public String getIndex8043F_curr_amount() {
		return index8043F_curr_amount;
	}

	public void setIndex8043F_curr_amount(String index8043F_curr_amount) {
		this.index8043F_curr_amount = index8043F_curr_amount;
	}

	public String getIndex8043F_operator() {
		return index8043F_operator;
	}

	public void setIndex8043F_operator(String index8043F_operator) {
		this.index8043F_operator = index8043F_operator;
	}

	public String getIndex8043F_two_rate_up() {
		return index8043F_two_rate_up;
	}

	public void setIndex8043F_two_rate_up(String index8043F_two_rate_up) {
		this.index8043F_two_rate_up = index8043F_two_rate_up;
	}

	public String getIndex8043F_two_rate_down() {
		return index8043F_two_rate_down;
	}

	public void setIndex8043F_two_rate_down(String index8043F_two_rate_down) {
		this.index8043F_two_rate_down = index8043F_two_rate_down;
	}

	public String getIndex8043F_three_rate_up() {
		return index8043F_three_rate_up;
	}

	public void setIndex8043F_three_rate_up(String index8043F_three_rate_up) {
		this.index8043F_three_rate_up = index8043F_three_rate_up;
	}

	public String getIndex8043F_three_rate_down() {
		return index8043F_three_rate_down;
	}

	public void setIndex8043F_three_rate_down(String index8043F_three_rate_down) {
		this.index8043F_three_rate_down = index8043F_three_rate_down;
	}

	public String getIndex8003F() {
		return index8003F;
	}

	public void setIndex8003F(String index8003F) {
		this.index8003F = index8003F;
	}

	public String getIndex8003F_curr_amount() {
		return index8003F_curr_amount;
	}

	public void setIndex8003F_curr_amount(String index8003F_curr_amount) {
		this.index8003F_curr_amount = index8003F_curr_amount;
	}

	public String getIndex8003F_operator() {
		return index8003F_operator;
	}

	public void setIndex8003F_operator(String index8003F_operator) {
		this.index8003F_operator = index8003F_operator;
	}

	public String getIndex8003F_two_rate_up() {
		return index8003F_two_rate_up;
	}

	public void setIndex8003F_two_rate_up(String index8003F_two_rate_up) {
		this.index8003F_two_rate_up = index8003F_two_rate_up;
	}

	public String getIndex8003F_two_rate_down() {
		return index8003F_two_rate_down;
	}

	public void setIndex8003F_two_rate_down(String index8003F_two_rate_down) {
		this.index8003F_two_rate_down = index8003F_two_rate_down;
	}

	public String getIndex8003F_three_rate_up() {
		return index8003F_three_rate_up;
	}

	public void setIndex8003F_three_rate_up(String index8003F_three_rate_up) {
		this.index8003F_three_rate_up = index8003F_three_rate_up;
	}

	public String getIndex8003F_three_rate_down() {
		return index8003F_three_rate_down;
	}

	public void setIndex8003F_three_rate_down(String index8003F_three_rate_down) {
		this.index8003F_three_rate_down = index8003F_three_rate_down;
	}

	public String getIndex8051F() {
		return index8051F;
	}

	public void setIndex8051F(String index8051F) {
		this.index8051F = index8051F;
	}

	public String getIndex8051F_curr_amount() {
		return index8051F_curr_amount;
	}

	public void setIndex8051F_curr_amount(String index8051F_curr_amount) {
		this.index8051F_curr_amount = index8051F_curr_amount;
	}

	public String getIndex8051F_operator() {
		return index8051F_operator;
	}

	public void setIndex8051F_operator(String index8051F_operator) {
		this.index8051F_operator = index8051F_operator;
	}

	public String getIndex8051F_two_rate_up() {
		return index8051F_two_rate_up;
	}

	public void setIndex8051F_two_rate_up(String index8051F_two_rate_up) {
		this.index8051F_two_rate_up = index8051F_two_rate_up;
	}

	public String getIndex8051F_two_rate_down() {
		return index8051F_two_rate_down;
	}

	public void setIndex8051F_two_rate_down(String index8051F_two_rate_down) {
		this.index8051F_two_rate_down = index8051F_two_rate_down;
	}

	public String getIndex8051F_three_rate_up() {
		return index8051F_three_rate_up;
	}

	public void setIndex8051F_three_rate_up(String index8051F_three_rate_up) {
		this.index8051F_three_rate_up = index8051F_three_rate_up;
	}

	public String getIndex8051F_three_rate_down() {
		return index8051F_three_rate_down;
	}

	public void setIndex8051F_three_rate_down(String index8051F_three_rate_down) {
		this.index8051F_three_rate_down = index8051F_three_rate_down;
	}

	public String getIndex8001F() {
		return index8001F;
	}

	public void setIndex8001F(String index8001F) {
		this.index8001F = index8001F;
	}

	public String getIndex8001F_curr_amount() {
		return index8001F_curr_amount;
	}

	public void setIndex8001F_curr_amount(String index8001F_curr_amount) {
		this.index8001F_curr_amount = index8001F_curr_amount;
	}

	public String getIndex8001F_operator() {
		return index8001F_operator;
	}

	public void setIndex8001F_operator(String index8001F_operator) {
		this.index8001F_operator = index8001F_operator;
	}

	public String getIndex8001F_two_rate_up() {
		return index8001F_two_rate_up;
	}

	public void setIndex8001F_two_rate_up(String index8001F_two_rate_up) {
		this.index8001F_two_rate_up = index8001F_two_rate_up;
	}

	public String getIndex8001F_two_rate_down() {
		return index8001F_two_rate_down;
	}

	public void setIndex8001F_two_rate_down(String index8001F_two_rate_down) {
		this.index8001F_two_rate_down = index8001F_two_rate_down;
	}

	public String getIndex8001F_three_rate_up() {
		return index8001F_three_rate_up;
	}

	public void setIndex8001F_three_rate_up(String index8001F_three_rate_up) {
		this.index8001F_three_rate_up = index8001F_three_rate_up;
	}

	public String getIndex8001F_three_rate_down() {
		return index8001F_three_rate_down;
	}

	public void setIndex8001F_three_rate_down(String index8001F_three_rate_down) {
		this.index8001F_three_rate_down = index8001F_three_rate_down;
	}

	public String getIndex8002F() {
		return index8002F;
	}

	public void setIndex8002F(String index8002F) {
		this.index8002F = index8002F;
	}

	public String getIndex8002F_curr_amount() {
		return index8002F_curr_amount;
	}

	public void setIndex8002F_curr_amount(String index8002F_curr_amount) {
		this.index8002F_curr_amount = index8002F_curr_amount;
	}

	public String getIndex8002F_operator() {
		return index8002F_operator;
	}

	public void setIndex8002F_operator(String index8002F_operator) {
		this.index8002F_operator = index8002F_operator;
	}

	public String getIndex8002F_two_rate_up() {
		return index8002F_two_rate_up;
	}

	public void setIndex8002F_two_rate_up(String index8002F_two_rate_up) {
		this.index8002F_two_rate_up = index8002F_two_rate_up;
	}

	public String getIndex8002F_two_rate_down() {
		return index8002F_two_rate_down;
	}

	public void setIndex8002F_two_rate_down(String index8002F_two_rate_down) {
		this.index8002F_two_rate_down = index8002F_two_rate_down;
	}

	public String getIndex8002F_three_rate_up() {
		return index8002F_three_rate_up;
	}

	public void setIndex8002F_three_rate_up(String index8002F_three_rate_up) {
		this.index8002F_three_rate_up = index8002F_three_rate_up;
	}

	public String getIndex8002F_three_rate_down() {
		return index8002F_three_rate_down;
	}

	public void setIndex8002F_three_rate_down(String index8002F_three_rate_down) {
		this.index8002F_three_rate_down = index8002F_three_rate_down;
	}

	public String getIndex8004F() {
		return index8004F;
	}

	public void setIndex8004F(String index8004F) {
		this.index8004F = index8004F;
	}

	public String getIndex8004F_curr_amount() {
		return index8004F_curr_amount;
	}

	public void setIndex8004F_curr_amount(String index8004F_curr_amount) {
		this.index8004F_curr_amount = index8004F_curr_amount;
	}

	public String getIndex8004F_operator() {
		return index8004F_operator;
	}

	public void setIndex8004F_operator(String index8004F_operator) {
		this.index8004F_operator = index8004F_operator;
	}

	public String getIndex8004F_two_rate_up() {
		return index8004F_two_rate_up;
	}

	public void setIndex8004F_two_rate_up(String index8004F_two_rate_up) {
		this.index8004F_two_rate_up = index8004F_two_rate_up;
	}

	public String getIndex8004F_two_rate_down() {
		return index8004F_two_rate_down;
	}

	public void setIndex8004F_two_rate_down(String index8004F_two_rate_down) {
		this.index8004F_two_rate_down = index8004F_two_rate_down;
	}

	public String getIndex8004F_three_rate_up() {
		return index8004F_three_rate_up;
	}

	public void setIndex8004F_three_rate_up(String index8004F_three_rate_up) {
		this.index8004F_three_rate_up = index8004F_three_rate_up;
	}

	public String getIndex8004F_three_rate_down() {
		return index8004F_three_rate_down;
	}

	public void setIndex8004F_three_rate_down(String index8004F_three_rate_down) {
		this.index8004F_three_rate_down = index8004F_three_rate_down;
	}

	public String getIndex8015F() {
		return index8015F;
	}

	public void setIndex8015F(String index8015F) {
		this.index8015F = index8015F;
	}

	public String getIndex8015F_curr_amount() {
		return index8015F_curr_amount;
	}

	public void setIndex8015F_curr_amount(String index8015F_curr_amount) {
		this.index8015F_curr_amount = index8015F_curr_amount;
	}

	public String getIndex8015F_operator() {
		return index8015F_operator;
	}

	public void setIndex8015F_operator(String index8015F_operator) {
		this.index8015F_operator = index8015F_operator;
	}

	public String getIndex8015F_two_rate_up() {
		return index8015F_two_rate_up;
	}

	public void setIndex8015F_two_rate_up(String index8015F_two_rate_up) {
		this.index8015F_two_rate_up = index8015F_two_rate_up;
	}

	public String getIndex8015F_two_rate_down() {
		return index8015F_two_rate_down;
	}

	public void setIndex8015F_two_rate_down(String index8015F_two_rate_down) {
		this.index8015F_two_rate_down = index8015F_two_rate_down;
	}

	public String getIndex8015F_three_rate_up() {
		return index8015F_three_rate_up;
	}

	public void setIndex8015F_three_rate_up(String index8015F_three_rate_up) {
		this.index8015F_three_rate_up = index8015F_three_rate_up;
	}

	public String getIndex8015F_three_rate_down() {
		return index8015F_three_rate_down;
	}

	public void setIndex8015F_three_rate_down(String index8015F_three_rate_down) {
		this.index8015F_three_rate_down = index8015F_three_rate_down;
	}

	public String getIndex8012F() {
		return index8012F;
	}

	public void setIndex8012F(String index8012F) {
		this.index8012F = index8012F;
	}

	public String getIndex8012F_curr_amount() {
		return index8012F_curr_amount;
	}

	public void setIndex8012F_curr_amount(String index8012F_curr_amount) {
		this.index8012F_curr_amount = index8012F_curr_amount;
	}

	public String getIndex8012F_operator() {
		return index8012F_operator;
	}

	public void setIndex8012F_operator(String index8012F_operator) {
		this.index8012F_operator = index8012F_operator;
	}

	public String getIndex8012F_two_rate_up() {
		return index8012F_two_rate_up;
	}

	public void setIndex8012F_two_rate_up(String index8012F_two_rate_up) {
		this.index8012F_two_rate_up = index8012F_two_rate_up;
	}

	public String getIndex8012F_two_rate_down() {
		return index8012F_two_rate_down;
	}

	public void setIndex8012F_two_rate_down(String index8012F_two_rate_down) {
		this.index8012F_two_rate_down = index8012F_two_rate_down;
	}

	public String getIndex8012F_three_rate_up() {
		return index8012F_three_rate_up;
	}

	public void setIndex8012F_three_rate_up(String index8012F_three_rate_up) {
		this.index8012F_three_rate_up = index8012F_three_rate_up;
	}

	public String getIndex8012F_three_rate_down() {
		return index8012F_three_rate_down;
	}

	public void setIndex8012F_three_rate_down(String index8012F_three_rate_down) {
		this.index8012F_three_rate_down = index8012F_three_rate_down;
	}

	public String getIndex8011F() {
		return index8011F;
	}

	public void setIndex8011F(String index8011F) {
		this.index8011F = index8011F;
	}

	public String getIndex8011F_curr_amount() {
		return index8011F_curr_amount;
	}

	public void setIndex8011F_curr_amount(String index8011F_curr_amount) {
		this.index8011F_curr_amount = index8011F_curr_amount;
	}

	public String getIndex8011F_operator() {
		return index8011F_operator;
	}

	public void setIndex8011F_operator(String index8011F_operator) {
		this.index8011F_operator = index8011F_operator;
	}

	public String getIndex8011F_two_rate_up() {
		return index8011F_two_rate_up;
	}

	public void setIndex8011F_two_rate_up(String index8011F_two_rate_up) {
		this.index8011F_two_rate_up = index8011F_two_rate_up;
	}

	public String getIndex8011F_two_rate_down() {
		return index8011F_two_rate_down;
	}

	public void setIndex8011F_two_rate_down(String index8011F_two_rate_down) {
		this.index8011F_two_rate_down = index8011F_two_rate_down;
	}

	public String getIndex8011F_three_rate_up() {
		return index8011F_three_rate_up;
	}

	public void setIndex8011F_three_rate_up(String index8011F_three_rate_up) {
		this.index8011F_three_rate_up = index8011F_three_rate_up;
	}

	public String getIndex8011F_three_rate_down() {
		return index8011F_three_rate_down;
	}

	public void setIndex8011F_three_rate_down(String index8011F_three_rate_down) {
		this.index8011F_three_rate_down = index8011F_three_rate_down;
	}

	public String getIndex8013F() {
		return index8013F;
	}

	public void setIndex8013F(String index8013F) {
		this.index8013F = index8013F;
	}

	public String getIndex8013F_curr_amount() {
		return index8013F_curr_amount;
	}

	public void setIndex8013F_curr_amount(String index8013F_curr_amount) {
		this.index8013F_curr_amount = index8013F_curr_amount;
	}

	public String getIndex8013F_operator() {
		return index8013F_operator;
	}

	public void setIndex8013F_operator(String index8013F_operator) {
		this.index8013F_operator = index8013F_operator;
	}

	public String getIndex8013F_two_rate_up() {
		return index8013F_two_rate_up;
	}

	public void setIndex8013F_two_rate_up(String index8013F_two_rate_up) {
		this.index8013F_two_rate_up = index8013F_two_rate_up;
	}

	public String getIndex8013F_two_rate_down() {
		return index8013F_two_rate_down;
	}

	public void setIndex8013F_two_rate_down(String index8013F_two_rate_down) {
		this.index8013F_two_rate_down = index8013F_two_rate_down;
	}

	public String getIndex8013F_three_rate_up() {
		return index8013F_three_rate_up;
	}

	public void setIndex8013F_three_rate_up(String index8013F_three_rate_up) {
		this.index8013F_three_rate_up = index8013F_three_rate_up;
	}

	public String getIndex8013F_three_rate_down() {
		return index8013F_three_rate_down;
	}

	public void setIndex8013F_three_rate_down(String index8013F_three_rate_down) {
		this.index8013F_three_rate_down = index8013F_three_rate_down;
	}

	public String getIndex8014F() {
		return index8014F;
	}

	public void setIndex8014F(String index8014F) {
		this.index8014F = index8014F;
	}

	public String getIndex8014F_curr_amount() {
		return index8014F_curr_amount;
	}

	public void setIndex8014F_curr_amount(String index8014F_curr_amount) {
		this.index8014F_curr_amount = index8014F_curr_amount;
	}

	public String getIndex8014F_operator() {
		return index8014F_operator;
	}

	public void setIndex8014F_operator(String index8014F_operator) {
		this.index8014F_operator = index8014F_operator;
	}

	public String getIndex8014F_two_rate_up() {
		return index8014F_two_rate_up;
	}

	public void setIndex8014F_two_rate_up(String index8014F_two_rate_up) {
		this.index8014F_two_rate_up = index8014F_two_rate_up;
	}

	public String getIndex8014F_two_rate_down() {
		return index8014F_two_rate_down;
	}

	public void setIndex8014F_two_rate_down(String index8014F_two_rate_down) {
		this.index8014F_two_rate_down = index8014F_two_rate_down;
	}

	public String getIndex8014F_three_rate_up() {
		return index8014F_three_rate_up;
	}

	public void setIndex8014F_three_rate_up(String index8014F_three_rate_up) {
		this.index8014F_three_rate_up = index8014F_three_rate_up;
	}

	public String getIndex8014F_three_rate_down() {
		return index8014F_three_rate_down;
	}

	public void setIndex8014F_three_rate_down(String index8014F_three_rate_down) {
		this.index8014F_three_rate_down = index8014F_three_rate_down;
	}

	public String getIndex0067F() {
		return index0067F;
	}

	public void setIndex0067F(String index0067F) {
		this.index0067F = index0067F;
	}

	public String getIndex0067F_curr_amount() {
		return index0067F_curr_amount;
	}

	public void setIndex0067F_curr_amount(String index0067F_curr_amount) {
		this.index0067F_curr_amount = index0067F_curr_amount;
	}

	public String getIndex0067F_operator() {
		return index0067F_operator;
	}

	public void setIndex0067F_operator(String index0067F_operator) {
		this.index0067F_operator = index0067F_operator;
	}

	public String getIndex0067F_two_rate_up() {
		return index0067F_two_rate_up;
	}

	public void setIndex0067F_two_rate_up(String index0067F_two_rate_up) {
		this.index0067F_two_rate_up = index0067F_two_rate_up;
	}

	public String getIndex0067F_two_rate_down() {
		return index0067F_two_rate_down;
	}

	public void setIndex0067F_two_rate_down(String index0067F_two_rate_down) {
		this.index0067F_two_rate_down = index0067F_two_rate_down;
	}

	public String getIndex0067F_three_rate_up() {
		return index0067F_three_rate_up;
	}

	public void setIndex0067F_three_rate_up(String index0067F_three_rate_up) {
		this.index0067F_three_rate_up = index0067F_three_rate_up;
	}

	public String getIndex0067F_three_rate_down() {
		return index0067F_three_rate_down;
	}

	public void setIndex0067F_three_rate_down(String index0067F_three_rate_down) {
		this.index0067F_three_rate_down = index0067F_three_rate_down;
	}

	public String getIndex0070F() {
		return index0070F;
	}

	public void setIndex0070F(String index0070F) {
		this.index0070F = index0070F;
	}

	public String getIndex0070F_curr_amount() {
		return index0070F_curr_amount;
	}

	public void setIndex0070F_curr_amount(String index0070F_curr_amount) {
		this.index0070F_curr_amount = index0070F_curr_amount;
	}

	public String getIndex0070F_operator() {
		return index0070F_operator;
	}

	public void setIndex0070F_operator(String index0070F_operator) {
		this.index0070F_operator = index0070F_operator;
	}

	public String getIndex0070F_two_rate_up() {
		return index0070F_two_rate_up;
	}

	public void setIndex0070F_two_rate_up(String index0070F_two_rate_up) {
		this.index0070F_two_rate_up = index0070F_two_rate_up;
	}

	public String getIndex0070F_two_rate_down() {
		return index0070F_two_rate_down;
	}

	public void setIndex0070F_two_rate_down(String index0070F_two_rate_down) {
		this.index0070F_two_rate_down = index0070F_two_rate_down;
	}

	public String getIndex0070F_three_rate_up() {
		return index0070F_three_rate_up;
	}

	public void setIndex0070F_three_rate_up(String index0070F_three_rate_up) {
		this.index0070F_three_rate_up = index0070F_three_rate_up;
	}

	public String getIndex0070F_three_rate_down() {
		return index0070F_three_rate_down;
	}

	public void setIndex0070F_three_rate_down(String index0070F_three_rate_down) {
		this.index0070F_three_rate_down = index0070F_three_rate_down;
	}

	public String getIndex0068F() {
		return index0068F;
	}

	public void setIndex0068F(String index0068F) {
		this.index0068F = index0068F;
	}

	public String getIndex0068F_curr_amount() {
		return index0068F_curr_amount;
	}

	public void setIndex0068F_curr_amount(String index0068F_curr_amount) {
		this.index0068F_curr_amount = index0068F_curr_amount;
	}

	public String getIndex0068F_operator() {
		return index0068F_operator;
	}

	public void setIndex0068F_operator(String index0068F_operator) {
		this.index0068F_operator = index0068F_operator;
	}

	public String getIndex0068F_two_rate_up() {
		return index0068F_two_rate_up;
	}

	public void setIndex0068F_two_rate_up(String index0068F_two_rate_up) {
		this.index0068F_two_rate_up = index0068F_two_rate_up;
	}

	public String getIndex0068F_two_rate_down() {
		return index0068F_two_rate_down;
	}

	public void setIndex0068F_two_rate_down(String index0068F_two_rate_down) {
		this.index0068F_two_rate_down = index0068F_two_rate_down;
	}

	public String getIndex0068F_three_rate_up() {
		return index0068F_three_rate_up;
	}

	public void setIndex0068F_three_rate_up(String index0068F_three_rate_up) {
		this.index0068F_three_rate_up = index0068F_three_rate_up;
	}

	public String getIndex0068F_three_rate_down() {
		return index0068F_three_rate_down;
	}

	public void setIndex0068F_three_rate_down(String index0068F_three_rate_down) {
		this.index0068F_three_rate_down = index0068F_three_rate_down;
	}

	public String getIndex0071F() {
		return index0071F;
	}

	public void setIndex0071F(String index0071F) {
		this.index0071F = index0071F;
	}

	public String getIndex0071F_curr_amount() {
		return index0071F_curr_amount;
	}

	public void setIndex0071F_curr_amount(String index0071F_curr_amount) {
		this.index0071F_curr_amount = index0071F_curr_amount;
	}

	public String getIndex0071F_operator() {
		return index0071F_operator;
	}

	public void setIndex0071F_operator(String index0071F_operator) {
		this.index0071F_operator = index0071F_operator;
	}

	public String getIndex0071F_two_rate_up() {
		return index0071F_two_rate_up;
	}

	public void setIndex0071F_two_rate_up(String index0071F_two_rate_up) {
		this.index0071F_two_rate_up = index0071F_two_rate_up;
	}

	public String getIndex0071F_two_rate_down() {
		return index0071F_two_rate_down;
	}

	public void setIndex0071F_two_rate_down(String index0071F_two_rate_down) {
		this.index0071F_two_rate_down = index0071F_two_rate_down;
	}

	public String getIndex0071F_three_rate_up() {
		return index0071F_three_rate_up;
	}

	public void setIndex0071F_three_rate_up(String index0071F_three_rate_up) {
		this.index0071F_three_rate_up = index0071F_three_rate_up;
	}

	public String getIndex0071F_three_rate_down() {
		return index0071F_three_rate_down;
	}

	public void setIndex0071F_three_rate_down(String index0071F_three_rate_down) {
		this.index0071F_three_rate_down = index0071F_three_rate_down;
	}

	public String getIndex0069F() {
		return index0069F;
	}

	public void setIndex0069F(String index0069F) {
		this.index0069F = index0069F;
	}

	public String getIndex0069F_curr_amount() {
		return index0069F_curr_amount;
	}

	public void setIndex0069F_curr_amount(String index0069F_curr_amount) {
		this.index0069F_curr_amount = index0069F_curr_amount;
	}

	public String getIndex0069F_operator() {
		return index0069F_operator;
	}

	public void setIndex0069F_operator(String index0069F_operator) {
		this.index0069F_operator = index0069F_operator;
	}

	public String getIndex0069F_two_rate_up() {
		return index0069F_two_rate_up;
	}

	public void setIndex0069F_two_rate_up(String index0069F_two_rate_up) {
		this.index0069F_two_rate_up = index0069F_two_rate_up;
	}

	public String getIndex0069F_two_rate_down() {
		return index0069F_two_rate_down;
	}

	public void setIndex0069F_two_rate_down(String index0069F_two_rate_down) {
		this.index0069F_two_rate_down = index0069F_two_rate_down;
	}

	public String getIndex0069F_three_rate_up() {
		return index0069F_three_rate_up;
	}

	public void setIndex0069F_three_rate_up(String index0069F_three_rate_up) {
		this.index0069F_three_rate_up = index0069F_three_rate_up;
	}

	public String getIndex0069F_three_rate_down() {
		return index0069F_three_rate_down;
	}

	public void setIndex0069F_three_rate_down(String index0069F_three_rate_down) {
		this.index0069F_three_rate_down = index0069F_three_rate_down;
	}

	public String getIndex0072F() {
		return index0072F;
	}

	public void setIndex0072F(String index0072F) {
		this.index0072F = index0072F;
	}

	public String getIndex0072F_curr_amount() {
		return index0072F_curr_amount;
	}

	public void setIndex0072F_curr_amount(String index0072F_curr_amount) {
		this.index0072F_curr_amount = index0072F_curr_amount;
	}

	public String getIndex0072F_operator() {
		return index0072F_operator;
	}

	public void setIndex0072F_operator(String index0072F_operator) {
		this.index0072F_operator = index0072F_operator;
	}

	public String getIndex0072F_two_rate_up() {
		return index0072F_two_rate_up;
	}

	public void setIndex0072F_two_rate_up(String index0072F_two_rate_up) {
		this.index0072F_two_rate_up = index0072F_two_rate_up;
	}

	public String getIndex0072F_two_rate_down() {
		return index0072F_two_rate_down;
	}

	public void setIndex0072F_two_rate_down(String index0072F_two_rate_down) {
		this.index0072F_two_rate_down = index0072F_two_rate_down;
	}

	public String getIndex0072F_three_rate_up() {
		return index0072F_three_rate_up;
	}

	public void setIndex0072F_three_rate_up(String index0072F_three_rate_up) {
		this.index0072F_three_rate_up = index0072F_three_rate_up;
	}

	public String getIndex0072F_three_rate_down() {
		return index0072F_three_rate_down;
	}

	public void setIndex0072F_three_rate_down(String index0072F_three_rate_down) {
		this.index0072F_three_rate_down = index0072F_three_rate_down;
	}

	public String getIndex0073F() {
		return index0073F;
	}

	public void setIndex0073F(String index0073F) {
		this.index0073F = index0073F;
	}

	public String getIndex0073F_curr_amount() {
		return index0073F_curr_amount;
	}

	public void setIndex0073F_curr_amount(String index0073F_curr_amount) {
		this.index0073F_curr_amount = index0073F_curr_amount;
	}

	public String getIndex0073F_operator() {
		return index0073F_operator;
	}

	public void setIndex0073F_operator(String index0073F_operator) {
		this.index0073F_operator = index0073F_operator;
	}

	public String getIndex0073F_two_rate_up() {
		return index0073F_two_rate_up;
	}

	public void setIndex0073F_two_rate_up(String index0073F_two_rate_up) {
		this.index0073F_two_rate_up = index0073F_two_rate_up;
	}

	public String getIndex0073F_two_rate_down() {
		return index0073F_two_rate_down;
	}

	public void setIndex0073F_two_rate_down(String index0073F_two_rate_down) {
		this.index0073F_two_rate_down = index0073F_two_rate_down;
	}

	public String getIndex0073F_three_rate_up() {
		return index0073F_three_rate_up;
	}

	public void setIndex0073F_three_rate_up(String index0073F_three_rate_up) {
		this.index0073F_three_rate_up = index0073F_three_rate_up;
	}

	public String getIndex0073F_three_rate_down() {
		return index0073F_three_rate_down;
	}

	public void setIndex0073F_three_rate_down(String index0073F_three_rate_down) {
		this.index0073F_three_rate_down = index0073F_three_rate_down;
	}

	public String getIndex0074F() {
		return index0074F;
	}

	public void setIndex0074F(String index0074F) {
		this.index0074F = index0074F;
	}

	public String getIndex0074F_curr_amount() {
		return index0074F_curr_amount;
	}

	public void setIndex0074F_curr_amount(String index0074F_curr_amount) {
		this.index0074F_curr_amount = index0074F_curr_amount;
	}

	public String getIndex0074F_operator() {
		return index0074F_operator;
	}

	public void setIndex0074F_operator(String index0074F_operator) {
		this.index0074F_operator = index0074F_operator;
	}

	public String getIndex0074F_two_rate_up() {
		return index0074F_two_rate_up;
	}

	public void setIndex0074F_two_rate_up(String index0074F_two_rate_up) {
		this.index0074F_two_rate_up = index0074F_two_rate_up;
	}

	public String getIndex0074F_two_rate_down() {
		return index0074F_two_rate_down;
	}

	public void setIndex0074F_two_rate_down(String index0074F_two_rate_down) {
		this.index0074F_two_rate_down = index0074F_two_rate_down;
	}

	public String getIndex0074F_three_rate_up() {
		return index0074F_three_rate_up;
	}

	public void setIndex0074F_three_rate_up(String index0074F_three_rate_up) {
		this.index0074F_three_rate_up = index0074F_three_rate_up;
	}

	public String getIndex0074F_three_rate_down() {
		return index0074F_three_rate_down;
	}

	public void setIndex0074F_three_rate_down(String index0074F_three_rate_down) {
		this.index0074F_three_rate_down = index0074F_three_rate_down;
	}

	public String getIndex0075F() {
		return index0075F;
	}

	public void setIndex0075F(String index0075F) {
		this.index0075F = index0075F;
	}

	public String getIndex0075F_curr_amount() {
		return index0075F_curr_amount;
	}

	public void setIndex0075F_curr_amount(String index0075F_curr_amount) {
		this.index0075F_curr_amount = index0075F_curr_amount;
	}

	public String getIndex0075F_operator() {
		return index0075F_operator;
	}

	public void setIndex0075F_operator(String index0075F_operator) {
		this.index0075F_operator = index0075F_operator;
	}

	public String getIndex0075F_two_rate_up() {
		return index0075F_two_rate_up;
	}

	public void setIndex0075F_two_rate_up(String index0075F_two_rate_up) {
		this.index0075F_two_rate_up = index0075F_two_rate_up;
	}

	public String getIndex0075F_two_rate_down() {
		return index0075F_two_rate_down;
	}

	public void setIndex0075F_two_rate_down(String index0075F_two_rate_down) {
		this.index0075F_two_rate_down = index0075F_two_rate_down;
	}

	public String getIndex0075F_three_rate_up() {
		return index0075F_three_rate_up;
	}

	public void setIndex0075F_three_rate_up(String index0075F_three_rate_up) {
		this.index0075F_three_rate_up = index0075F_three_rate_up;
	}

	public String getIndex0075F_three_rate_down() {
		return index0075F_three_rate_down;
	}

	public void setIndex0075F_three_rate_down(String index0075F_three_rate_down) {
		this.index0075F_three_rate_down = index0075F_three_rate_down;
	}

	public String getIndex0076F() {
		return index0076F;
	}

	public void setIndex0076F(String index0076F) {
		this.index0076F = index0076F;
	}

	public String getIndex0076F_curr_amount() {
		return index0076F_curr_amount;
	}

	public void setIndex0076F_curr_amount(String index0076F_curr_amount) {
		this.index0076F_curr_amount = index0076F_curr_amount;
	}

	public String getIndex0076F_operator() {
		return index0076F_operator;
	}

	public void setIndex0076F_operator(String index0076F_operator) {
		this.index0076F_operator = index0076F_operator;
	}

	public String getIndex0076F_two_rate_up() {
		return index0076F_two_rate_up;
	}

	public void setIndex0076F_two_rate_up(String index0076F_two_rate_up) {
		this.index0076F_two_rate_up = index0076F_two_rate_up;
	}

	public String getIndex0076F_two_rate_down() {
		return index0076F_two_rate_down;
	}

	public void setIndex0076F_two_rate_down(String index0076F_two_rate_down) {
		this.index0076F_two_rate_down = index0076F_two_rate_down;
	}

	public String getIndex0076F_three_rate_up() {
		return index0076F_three_rate_up;
	}

	public void setIndex0076F_three_rate_up(String index0076F_three_rate_up) {
		this.index0076F_three_rate_up = index0076F_three_rate_up;
	}

	public String getIndex0076F_three_rate_down() {
		return index0076F_three_rate_down;
	}

	public void setIndex0076F_three_rate_down(String index0076F_three_rate_down) {
		this.index0076F_three_rate_down = index0076F_three_rate_down;
	}

	public String getIndex0077F() {
		return index0077F;
	}

	public void setIndex0077F(String index0077F) {
		this.index0077F = index0077F;
	}

	public String getIndex0077F_curr_amount() {
		return index0077F_curr_amount;
	}

	public void setIndex0077F_curr_amount(String index0077F_curr_amount) {
		this.index0077F_curr_amount = index0077F_curr_amount;
	}

	public String getIndex0077F_operator() {
		return index0077F_operator;
	}

	public void setIndex0077F_operator(String index0077F_operator) {
		this.index0077F_operator = index0077F_operator;
	}

	public String getIndex0077F_two_rate_up() {
		return index0077F_two_rate_up;
	}

	public void setIndex0077F_two_rate_up(String index0077F_two_rate_up) {
		this.index0077F_two_rate_up = index0077F_two_rate_up;
	}

	public String getIndex0077F_two_rate_down() {
		return index0077F_two_rate_down;
	}

	public void setIndex0077F_two_rate_down(String index0077F_two_rate_down) {
		this.index0077F_two_rate_down = index0077F_two_rate_down;
	}

	public String getIndex0077F_three_rate_up() {
		return index0077F_three_rate_up;
	}

	public void setIndex0077F_three_rate_up(String index0077F_three_rate_up) {
		this.index0077F_three_rate_up = index0077F_three_rate_up;
	}

	public String getIndex0077F_three_rate_down() {
		return index0077F_three_rate_down;
	}

	public void setIndex0077F_three_rate_down(String index0077F_three_rate_down) {
		this.index0077F_three_rate_down = index0077F_three_rate_down;
	}

	public String getIndex0078F() {
		return index0078F;
	}

	public void setIndex0078F(String index0078F) {
		this.index0078F = index0078F;
	}

	public String getIndex0078F_curr_amount() {
		return index0078F_curr_amount;
	}

	public void setIndex0078F_curr_amount(String index0078F_curr_amount) {
		this.index0078F_curr_amount = index0078F_curr_amount;
	}

	public String getIndex0078F_operator() {
		return index0078F_operator;
	}

	public void setIndex0078F_operator(String index0078F_operator) {
		this.index0078F_operator = index0078F_operator;
	}

	public String getIndex0078F_two_rate_up() {
		return index0078F_two_rate_up;
	}

	public void setIndex0078F_two_rate_up(String index0078F_two_rate_up) {
		this.index0078F_two_rate_up = index0078F_two_rate_up;
	}

	public String getIndex0078F_two_rate_down() {
		return index0078F_two_rate_down;
	}

	public void setIndex0078F_two_rate_down(String index0078F_two_rate_down) {
		this.index0078F_two_rate_down = index0078F_two_rate_down;
	}

	public String getIndex0078F_three_rate_up() {
		return index0078F_three_rate_up;
	}

	public void setIndex0078F_three_rate_up(String index0078F_three_rate_up) {
		this.index0078F_three_rate_up = index0078F_three_rate_up;
	}

	public String getIndex0078F_three_rate_down() {
		return index0078F_three_rate_down;
	}

	public void setIndex0078F_three_rate_down(String index0078F_three_rate_down) {
		this.index0078F_three_rate_down = index0078F_three_rate_down;
	}

	public String getIndex0079F() {
		return index0079F;
	}

	public void setIndex0079F(String index0079F) {
		this.index0079F = index0079F;
	}

	public String getIndex0079F_curr_amount() {
		return index0079F_curr_amount;
	}

	public void setIndex0079F_curr_amount(String index0079F_curr_amount) {
		this.index0079F_curr_amount = index0079F_curr_amount;
	}

	public String getIndex0079F_operator() {
		return index0079F_operator;
	}

	public void setIndex0079F_operator(String index0079F_operator) {
		this.index0079F_operator = index0079F_operator;
	}

	public String getIndex0079F_two_rate_up() {
		return index0079F_two_rate_up;
	}

	public void setIndex0079F_two_rate_up(String index0079F_two_rate_up) {
		this.index0079F_two_rate_up = index0079F_two_rate_up;
	}

	public String getIndex0079F_two_rate_down() {
		return index0079F_two_rate_down;
	}

	public void setIndex0079F_two_rate_down(String index0079F_two_rate_down) {
		this.index0079F_two_rate_down = index0079F_two_rate_down;
	}

	public String getIndex0079F_three_rate_up() {
		return index0079F_three_rate_up;
	}

	public void setIndex0079F_three_rate_up(String index0079F_three_rate_up) {
		this.index0079F_three_rate_up = index0079F_three_rate_up;
	}

	public String getIndex0079F_three_rate_down() {
		return index0079F_three_rate_down;
	}

	public void setIndex0079F_three_rate_down(String index0079F_three_rate_down) {
		this.index0079F_three_rate_down = index0079F_three_rate_down;
	}

	public String getIndex0080F() {
		return index0080F;
	}

	public void setIndex0080F(String index0080F) {
		this.index0080F = index0080F;
	}

	public String getIndex0080F_curr_amount() {
		return index0080F_curr_amount;
	}

	public void setIndex0080F_curr_amount(String index0080F_curr_amount) {
		this.index0080F_curr_amount = index0080F_curr_amount;
	}

	public String getIndex0080F_operator() {
		return index0080F_operator;
	}

	public void setIndex0080F_operator(String index0080F_operator) {
		this.index0080F_operator = index0080F_operator;
	}

	public String getIndex0080F_two_rate_up() {
		return index0080F_two_rate_up;
	}

	public void setIndex0080F_two_rate_up(String index0080F_two_rate_up) {
		this.index0080F_two_rate_up = index0080F_two_rate_up;
	}

	public String getIndex0080F_two_rate_down() {
		return index0080F_two_rate_down;
	}

	public void setIndex0080F_two_rate_down(String index0080F_two_rate_down) {
		this.index0080F_two_rate_down = index0080F_two_rate_down;
	}

	public String getIndex0080F_three_rate_up() {
		return index0080F_three_rate_up;
	}

	public void setIndex0080F_three_rate_up(String index0080F_three_rate_up) {
		this.index0080F_three_rate_up = index0080F_three_rate_up;
	}

	public String getIndex0080F_three_rate_down() {
		return index0080F_three_rate_down;
	}

	public void setIndex0080F_three_rate_down(String index0080F_three_rate_down) {
		this.index0080F_three_rate_down = index0080F_three_rate_down;
	}

	public String getIndex0081F() {
		return index0081F;
	}

	public void setIndex0081F(String index0081F) {
		this.index0081F = index0081F;
	}

	public String getIndex0081F_curr_amount() {
		return index0081F_curr_amount;
	}

	public void setIndex0081F_curr_amount(String index0081F_curr_amount) {
		this.index0081F_curr_amount = index0081F_curr_amount;
	}

	public String getIndex0081F_operator() {
		return index0081F_operator;
	}

	public void setIndex0081F_operator(String index0081F_operator) {
		this.index0081F_operator = index0081F_operator;
	}

	public String getIndex0081F_two_rate_up() {
		return index0081F_two_rate_up;
	}

	public void setIndex0081F_two_rate_up(String index0081F_two_rate_up) {
		this.index0081F_two_rate_up = index0081F_two_rate_up;
	}

	public String getIndex0081F_two_rate_down() {
		return index0081F_two_rate_down;
	}

	public void setIndex0081F_two_rate_down(String index0081F_two_rate_down) {
		this.index0081F_two_rate_down = index0081F_two_rate_down;
	}

	public String getIndex0081F_three_rate_up() {
		return index0081F_three_rate_up;
	}

	public void setIndex0081F_three_rate_up(String index0081F_three_rate_up) {
		this.index0081F_three_rate_up = index0081F_three_rate_up;
	}

	public String getIndex0081F_three_rate_down() {
		return index0081F_three_rate_down;
	}

	public void setIndex0081F_three_rate_down(String index0081F_three_rate_down) {
		this.index0081F_three_rate_down = index0081F_three_rate_down;
	}

	public String getIndex0082F() {
		return index0082F;
	}

	public void setIndex0082F(String index0082F) {
		this.index0082F = index0082F;
	}

	public String getIndex0082F_curr_amount() {
		return index0082F_curr_amount;
	}

	public void setIndex0082F_curr_amount(String index0082F_curr_amount) {
		this.index0082F_curr_amount = index0082F_curr_amount;
	}

	public String getIndex0082F_operator() {
		return index0082F_operator;
	}

	public void setIndex0082F_operator(String index0082F_operator) {
		this.index0082F_operator = index0082F_operator;
	}

	public String getIndex0082F_two_rate_up() {
		return index0082F_two_rate_up;
	}

	public void setIndex0082F_two_rate_up(String index0082F_two_rate_up) {
		this.index0082F_two_rate_up = index0082F_two_rate_up;
	}

	public String getIndex0082F_two_rate_down() {
		return index0082F_two_rate_down;
	}

	public void setIndex0082F_two_rate_down(String index0082F_two_rate_down) {
		this.index0082F_two_rate_down = index0082F_two_rate_down;
	}

	public String getIndex0082F_three_rate_up() {
		return index0082F_three_rate_up;
	}

	public void setIndex0082F_three_rate_up(String index0082F_three_rate_up) {
		this.index0082F_three_rate_up = index0082F_three_rate_up;
	}

	public String getIndex0082F_three_rate_down() {
		return index0082F_three_rate_down;
	}

	public void setIndex0082F_three_rate_down(String index0082F_three_rate_down) {
		this.index0082F_three_rate_down = index0082F_three_rate_down;
	}

	public String getIndex0083F() {
		return index0083F;
	}

	public void setIndex0083F(String index0083F) {
		this.index0083F = index0083F;
	}

	public String getIndex0083F_curr_amount() {
		return index0083F_curr_amount;
	}

	public void setIndex0083F_curr_amount(String index0083F_curr_amount) {
		this.index0083F_curr_amount = index0083F_curr_amount;
	}

	public String getIndex0083F_operator() {
		return index0083F_operator;
	}

	public void setIndex0083F_operator(String index0083F_operator) {
		this.index0083F_operator = index0083F_operator;
	}

	public String getIndex0083F_two_rate_up() {
		return index0083F_two_rate_up;
	}

	public void setIndex0083F_two_rate_up(String index0083F_two_rate_up) {
		this.index0083F_two_rate_up = index0083F_two_rate_up;
	}

	public String getIndex0083F_two_rate_down() {
		return index0083F_two_rate_down;
	}

	public void setIndex0083F_two_rate_down(String index0083F_two_rate_down) {
		this.index0083F_two_rate_down = index0083F_two_rate_down;
	}

	public String getIndex0083F_three_rate_up() {
		return index0083F_three_rate_up;
	}

	public void setIndex0083F_three_rate_up(String index0083F_three_rate_up) {
		this.index0083F_three_rate_up = index0083F_three_rate_up;
	}

	public String getIndex0083F_three_rate_down() {
		return index0083F_three_rate_down;
	}

	public void setIndex0083F_three_rate_down(String index0083F_three_rate_down) {
		this.index0083F_three_rate_down = index0083F_three_rate_down;
	}

	public String getIndex0084F() {
		return index0084F;
	}

	public void setIndex0084F(String index0084F) {
		this.index0084F = index0084F;
	}

	public String getIndex0084F_curr_amount() {
		return index0084F_curr_amount;
	}

	public void setIndex0084F_curr_amount(String index0084F_curr_amount) {
		this.index0084F_curr_amount = index0084F_curr_amount;
	}

	public String getIndex0084F_operator() {
		return index0084F_operator;
	}

	public void setIndex0084F_operator(String index0084F_operator) {
		this.index0084F_operator = index0084F_operator;
	}

	public String getIndex0084F_two_rate_up() {
		return index0084F_two_rate_up;
	}

	public void setIndex0084F_two_rate_up(String index0084F_two_rate_up) {
		this.index0084F_two_rate_up = index0084F_two_rate_up;
	}

	public String getIndex0084F_two_rate_down() {
		return index0084F_two_rate_down;
	}

	public void setIndex0084F_two_rate_down(String index0084F_two_rate_down) {
		this.index0084F_two_rate_down = index0084F_two_rate_down;
	}

	public String getIndex0084F_three_rate_up() {
		return index0084F_three_rate_up;
	}

	public void setIndex0084F_three_rate_up(String index0084F_three_rate_up) {
		this.index0084F_three_rate_up = index0084F_three_rate_up;
	}

	public String getIndex0084F_three_rate_down() {
		return index0084F_three_rate_down;
	}

	public void setIndex0084F_three_rate_down(String index0084F_three_rate_down) {
		this.index0084F_three_rate_down = index0084F_three_rate_down;
	}

	public String getIndex0085F() {
		return index0085F;
	}

	public void setIndex0085F(String index0085F) {
		this.index0085F = index0085F;
	}

	public String getIndex0085F_curr_amount() {
		return index0085F_curr_amount;
	}

	public void setIndex0085F_curr_amount(String index0085F_curr_amount) {
		this.index0085F_curr_amount = index0085F_curr_amount;
	}

	public String getIndex0085F_operator() {
		return index0085F_operator;
	}

	public void setIndex0085F_operator(String index0085F_operator) {
		this.index0085F_operator = index0085F_operator;
	}

	public String getIndex0085F_two_rate_up() {
		return index0085F_two_rate_up;
	}

	public void setIndex0085F_two_rate_up(String index0085F_two_rate_up) {
		this.index0085F_two_rate_up = index0085F_two_rate_up;
	}

	public String getIndex0085F_two_rate_down() {
		return index0085F_two_rate_down;
	}

	public void setIndex0085F_two_rate_down(String index0085F_two_rate_down) {
		this.index0085F_two_rate_down = index0085F_two_rate_down;
	}

	public String getIndex0085F_three_rate_up() {
		return index0085F_three_rate_up;
	}

	public void setIndex0085F_three_rate_up(String index0085F_three_rate_up) {
		this.index0085F_three_rate_up = index0085F_three_rate_up;
	}

	public String getIndex0085F_three_rate_down() {
		return index0085F_three_rate_down;
	}

	public void setIndex0085F_three_rate_down(String index0085F_three_rate_down) {
		this.index0085F_three_rate_down = index0085F_three_rate_down;
	}

	public String getIndex0086F() {
		return index0086F;
	}

	public void setIndex0086F(String index0086F) {
		this.index0086F = index0086F;
	}

	public String getIndex0086F_curr_amount() {
		return index0086F_curr_amount;
	}

	public void setIndex0086F_curr_amount(String index0086F_curr_amount) {
		this.index0086F_curr_amount = index0086F_curr_amount;
	}

	public String getIndex0086F_operator() {
		return index0086F_operator;
	}

	public void setIndex0086F_operator(String index0086F_operator) {
		this.index0086F_operator = index0086F_operator;
	}

	public String getIndex0086F_two_rate_up() {
		return index0086F_two_rate_up;
	}

	public void setIndex0086F_two_rate_up(String index0086F_two_rate_up) {
		this.index0086F_two_rate_up = index0086F_two_rate_up;
	}

	public String getIndex0086F_two_rate_down() {
		return index0086F_two_rate_down;
	}

	public void setIndex0086F_two_rate_down(String index0086F_two_rate_down) {
		this.index0086F_two_rate_down = index0086F_two_rate_down;
	}

	public String getIndex0086F_three_rate_up() {
		return index0086F_three_rate_up;
	}

	public void setIndex0086F_three_rate_up(String index0086F_three_rate_up) {
		this.index0086F_three_rate_up = index0086F_three_rate_up;
	}

	public String getIndex0086F_three_rate_down() {
		return index0086F_three_rate_down;
	}

	public void setIndex0086F_three_rate_down(String index0086F_three_rate_down) {
		this.index0086F_three_rate_down = index0086F_three_rate_down;
	}

	public String getIndex0092E() {
		return index0092E;
	}

	public void setIndex0092E(String index0092E) {
		this.index0092E = index0092E;
	}

	public String getIndex0092E_count() {
		return index0092E_count;
	}

	public void setIndex0092E_count(String index0092E_count) {
		this.index0092E_count = index0092E_count;
	}

	public String getIndex0092E_operator() {
		return index0092E_operator;
	}

	public String getIndex0092E_operator_symbol() {
		return index0092E_operator_symbol;
	}

	public void setIndex0092E_operator_symbol(String index0092e_operator_symbol) {
		index0092E_operator_symbol = index0092e_operator_symbol;
	}

	public void setIndex0092E_operator(String index0092E_operator) {
		this.index0092E_operator = index0092E_operator;
	}

	public String getIndex0093E() {
		return index0093E;
	}

	public void setIndex0093E(String index0093E) {
		this.index0093E = index0093E;
	}

	public String getIndex0093E_count() {
		return index0093E_count;
	}

	public void setIndex0093E_count(String index0093E_count) {
		this.index0093E_count = index0093E_count;
	}

	public String getIndex0093E_operator() {
		return index0093E_operator;
	}

	public void setIndex0093E_operator(String index0093E_operator) {
		this.index0093E_operator = index0093E_operator;
	}

	public String getIndex0094E() {
		return index0094E;
	}

	public void setIndex0094E(String index0094E) {
		this.index0094E = index0094E;
	}

	public String getIndex0094E_count() {
		return index0094E_count;
	}

	public void setIndex0094E_count(String index0094E_count) {
		this.index0094E_count = index0094E_count;
	}

	public String getIndex0094E_operator() {
		return index0094E_operator;
	}

	public void setIndex0094E_operator(String index0094E_operator) {
		this.index0094E_operator = index0094E_operator;
	}

	public String getIndex0094E_operator_symbol() {
		return index0094E_operator_symbol;
	}

	public void setIndex0094E_operator_symbol(String index0094e_operator_symbol) {
		index0094E_operator_symbol = index0094e_operator_symbol;
	}

	public String getIndex0095E() {
		return index0095E;
	}

	public void setIndex0095E(String index0095E) {
		this.index0095E = index0095E;
	}

	public String getIndex0095E_count() {
		return index0095E_count;
	}

	public void setIndex0095E_count(String index0095E_count) {
		this.index0095E_count = index0095E_count;
	}

	public String getIndex0095E_operator() {
		return index0095E_operator;
	}

	public void setIndex0095E_operator(String index0095E_operator) {
		this.index0095E_operator = index0095E_operator;
	}

	public Map<String, String> getMapcolumnvo() {
		return mapcolumnvo;
	}

	public void setMapcolumnvo(Map<String, String> mapcolumnvo) {
		this.mapcolumnvo = mapcolumnvo;
	}

	public Map<String, String> getMapvo() {
		return mapvo;
	}

	public void setMapvo(Map<String, String> mapvo) {
		this.mapvo = mapvo;
	}

	public String getCreditscore() {
		return creditscore;
	}

	public void setCreditscore(String creditscore) {
		this.creditscore = creditscore;
	}

	public String getXydji() {
		return xydji;
	}

	public void setXydji(String xydji) {
		this.xydji = xydji;
	}

	public String getPjdate() {
		return pjdate;
	}

	public void setPjdate(String pjdate) {
		this.pjdate = pjdate;
	}

	public String getCreditreport() {
		return creditreport;
	}

	public void setCreditreport(String creditreport) {
		this.creditreport = creditreport;
	}

	public String getYuqi() {
		return yuqi;
	}

	public void setYuqi(String yuqi) {
		this.yuqi = yuqi;
	}

	public String getDaiz() {
		return daiz;
	}

	public void setDaiz(String daiz) {
		this.daiz = daiz;
	}

	public String getCzwjq() {
		return czwjq;
	}

	public void setCzwjq(String czwjq) {
		this.czwjq = czwjq;
	}

	public String getWxdls() {
		return wxdls;
	}

	public void setWxdls(String wxdls) {
		this.wxdls = wxdls;
	}

	public String getYxdls() {
		return yxdls;
	}

	public void setYxdls(String yxdls) {
		this.yxdls = yxdls;
	}

	public String getGjrz() {
		return gjrz;
	}

	public void setGjrz(String gjrz) {
		this.gjrz = gjrz;
	}

	public String getYbrz() {
		return ybrz;
	}

	public void setYbrz(String ybrz) {
		this.ybrz = ybrz;
	}

	public String getSxqy() {
		return sxqy;
	}

	public void setSxqy(String sxqy) {
		this.sxqy = sxqy;
	}

	public String getHgpjC() {
		return hgpjC;
	}

	public void setHgpjC(String hgpjC) {
		this.hgpjC = hgpjC;
	}

	public String getHgpjD() {
		return hgpjD;
	}

	public void setHgpjD(String hgpjD) {
		this.hgpjD = hgpjD;
	}

	public String getBigaqsg() {
		return bigaqsg;
	}

	public void setBigaqsg(String bigaqsg) {
		this.bigaqsg = bigaqsg;
	}

	public String getWuaqsg() {
		return wuaqsg;
	}

	public void setWuaqsg(String wuaqsg) {
		this.wuaqsg = wuaqsg;
	}

	public String getFzcns() {
		return fzcns;
	}

	public void setFzcns(String fzcns) {
		this.fzcns = fzcns;
	}

	public String getLsdjA() {
		return lsdjA;
	}

	public void setLsdjA(String lsdjA) {
		this.lsdjA = lsdjA;
	}

	public String getGreen() {
		return green;
	}

	public void setGreen(String green) {
		this.green = green;
	}

	public String getBlue() {
		return blue;
	}

	public void setBlue(String blue) {
		this.blue = blue;
	}

	public String getYellow() {
		return yellow;
	}

	public void setYellow(String yellow) {
		this.yellow = yellow;
	}

	public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
	}

	public String getBlack() {
		return black;
	}

	public void setBlack(String black) {
		this.black = black;
	}

	public String getSrybz() {
		return srybz;
	}

	public void setSrybz(String srybz) {
		this.srybz = srybz;
	}

	public String getCityrybz() {
		return cityrybz;
	}

	public void setCityrybz(String cityrybz) {
		this.cityrybz = cityrybz;
	}

	public String getXrybz() {
		return xrybz;
	}

	public void setXrybz(String xrybz) {
		this.xrybz = xrybz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthflaginfo() {
		return authflaginfo;
	}

	public void setAuthflaginfo(String authflaginfo) {
		this.authflaginfo = authflaginfo;
	}

	public String getFinanceauthflaginfo() {
		return financeauthflaginfo;
	}

	public void setFinanceauthflaginfo(String financeauthflaginfo) {
		this.financeauthflaginfo = financeauthflaginfo;
	}

	public String getIndex0108E() {
		return index0108E;
	}

	public void setIndex0108E(String index0108e) {
		index0108E = index0108e;
	}

	public String getIndex0108E_count() {
		return index0108E_count;
	}

	public void setIndex0108E_count(String index0108e_count) {
		index0108E_count = index0108e_count;
	}

	public String getIndex0108E_operator() {
		return index0108E_operator;
	}

	public void setIndex0108E_operator(String index0108e_operator) {
		index0108E_operator = index0108e_operator;
	}

	public String getIndex0109E() {
		return index0109E;
	}

	public void setIndex0109E(String index0109e) {
		index0109E = index0109e;
	}

	public String getIndex0109E_count() {
		return index0109E_count;
	}

	public void setIndex0109E_count(String index0109e_count) {
		index0109E_count = index0109e_count;
	}

	public String getIndex0109E_operator() {
		return index0109E_operator;
	}

	public void setIndex0109E_operator(String index0109e_operator) {
		index0109E_operator = index0109e_operator;
	}

	public String getIndex0110E() {
		return index0110E;
	}

	public void setIndex0110E(String index0110e) {
		index0110E = index0110e;
	}

	public String getIndex0110E_count() {
		return index0110E_count;
	}

	public void setIndex0110E_count(String index0110e_count) {
		index0110E_count = index0110e_count;
	}

	public String getIndex0110E_operator() {
		return index0110E_operator;
	}

	public void setIndex0110E_operator(String index0110e_operator) {
		index0110E_operator = index0110e_operator;
	}

	public String getIndex0111E() {
		return index0111E;
	}

	public void setIndex0111E(String index0111e) {
		index0111E = index0111e;
	}

	public String getIndex0111E_count() {
		return index0111E_count;
	}

	public void setIndex0111E_count(String index0111e_count) {
		index0111E_count = index0111e_count;
	}

	public String getIndex0111E_operator() {
		return index0111E_operator;
	}

	public void setIndex0111E_operator(String index0111e_operator) {
		index0111E_operator = index0111e_operator;
	}

	public String getAuthFlagYes() {
		return authFlagYes;
	}

	public void setAuthFlagYes(String authFlagYes) {
		this.authFlagYes = authFlagYes;
	}

	public String getAuthFlagNo() {
		return authFlagNo;
	}

	public void setAuthFlagNo(String authFlagNo) {
		this.authFlagNo = authFlagNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}


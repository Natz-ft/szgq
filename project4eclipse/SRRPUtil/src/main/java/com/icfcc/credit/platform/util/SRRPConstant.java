package com.icfcc.credit.platform.util;

import org.springframework.beans.factory.annotation.Value;


public class SRRPConstant {

	/**
	 * 字符集
	 */
	public static final String DEFAULTCHARTS = "gbk";

	/**
	 * Redis行业数据字典Key
	 */
	public static final String DD_INDUSTRY = "dd:industry";
	public static final String DD_INDUSTRY_2 = "dd:industry2";

	/**
	 * Redis地区数据字典Key14
	 */
	public static final String DD_AREA = "dd:area";
	/**
	 * Redis单选框类型数据字典Key15
	 */
	public static final String DD_RADIO = "dd:radio";
	/**
	 * Redis投资机构状态数据字典Key16
	 */
	public static final String DD_INVESTORSTATUS = "dd:investorstatus";
	/**
	 * Redis留言状态数据字典Key17
	 */
	public static final String DD_MSG = "dd:msg";

	/**
	 * Redis资本量数据字典Key
	 */
	public static final String DD_CAPITAL = "dd:capital";

	/**
	 * Redis资本实力数据字典Key19
	 */
	public static final String DD_CAPITALPOWER = "dd:capitalPower";
	/**
	 * Redis资本实力数据字典Key21
	 */
	public static final String DD_ENTPERIOD = "dd:enterprisePeriod";

	/**
	 * Redis证件代码类型数据字典Key
	 */
	public static final String DD_CERTIFICATE = "dd:certificate";
	/**
	 * Redis投资阶段数据字典Key
	 */
	public static final String DD_INVESTMENT = "dd:investment";
	/**
	 * Redis投资轮次数据字典Key
	 */
	public static final String DD_FINACINGTURN = "dd:finacingturn";
	/**
	 * Redis审核状态数据字典Key
	 */
	public static final String DD_AUDITSTATE = "dd:auditstate";
	
	public static final String DD_COMPANY_AUSTSTAUS = "dd:companyAuditstate";
	public static final String CompanyScale_v1 = "dd:CompanyScale_v1";
	public static final String DD_CORP_AREA="dd:corpArea";
	public static final String DD_REGISTER_TYPE="dd:register_type";
	public static final String DD_BIZTERM_V1="dd:bizterm_v1";
	public static final String DD_INVESTMENT_TYPE="dd:investment_type";
	public static final String lend_institution = "dd:lend_institution";
	public static final String auth_institution = "dd:auth_institution";
	public static final String SERVICE_CATEGORY_1 = "dd:SERVICE_CATEGORY_1";
	public static final String SERVICE_CATEGORY_2 = "dd:SERVICE_CATEGORY_2";
	public static final String DD_XYDJI="dd:xydji";
	public static final String AREA_Province = "dd:areaProvince";
	public static final String AREA_City = "dd:areaCity";
	public static final String AREA_County = "dd:areaCounty";
	/**
	 * Redis机构类型数据字典Key
	 */
	public static final String DD_ORGTYPE = "dd:orgtype";
	/**
	 * Redis异议类型数据字典Key
	 */
	public static final String DD_OBJECTION_TYPE="dd:objectiontype";
	/**
	 * Redis资本类型数据字典Key07
	 */
	public static final String DD_CAPITALTYPE = "dd:capitaltype";
	/**
	 * Redis需求状态数据字典Key22
	 */
	public static final String DD_DEMANDSTATUS = "dd:demandstatus";
	/**
	 * Redis任务状态数据字典Key23
	 */
	public static final String DD_TASKSTATUS = "dd:tasktatus";
	/**
	 * Redis企业相关附件文件类型字典Key24
	 */
	public static final String DD_FILETYPE = "dd:filetype";
	/**
	 * Redis企业相关附件文件类型字典Key24 组织形式
	 */
	public static final String DD_ORGFORM = "dd:orgform";
	
	/**
	 * Redis企业相关附件文件类型字典Key24 组织形式
	 */
	public static final String DD_SUBACTYPE = "dd:subactype";
	
	/**
	 * Redis企业相关附件文件类型字典Key24 组织形式
	 */
	public static final String DD_SUBACFORM = "dd:subacform";
	/**
	 * Redis融资阶段数据字典Key08
	 */
	public static final String DD_FINANCPHASE = "dd:financphase";
	/**
	 * Redis星级数据字典Key09
	 */
	public static final String DD_STARLEVEL = "dd:starlevel";
	/**
	 * Redis股东类型数据字典Key10
	 */
	public static final String DD_STOCKHOLDER = "dd:stockholder";
	/**
	 * Redis 出资形式 Key11
	 */
	public static final String DD_INVESTMENTTYPR = "dd:investmenttype";
	/**
	 * Redis信息批露类型
	 */
	public static final String DD_PUBLISHTYPE = "dd:publishtype";

	/**
	 * Redis融资金额范围
	 */
	public static final String DD_FINACINGMONEY = "dd:finacingmoney";
	/*
	 * 查询条件默认值
	 */
	public static final String QUERYCONDITION_DEFAULT = "0";

	/*
	 * 新闻阅读量
	 */
	public static final String NEWSREADAMOUNTPREFIX = "readamount:news";
	/*
	 * 公告阅读量
	 */
	public static final String NOTICEREADAMOUNTPREFIX = "readamount:notice";
	/**
	 * 新闻动态分类
	 */
	public static final String DD_NEWSTYPR = "dd:newsType";

	/*
	 * 阅读量起始值
	 */
	public static final String DEFALUTREADAMOUNT = "0";
	/*
	 * 阅读量递增值
	 */
	public static final int READAMOUNTINCREMENT = 1;
	/*
	 * 联系我们公告
	 */
	public static final String TYPE0001 = "0001";
	/*
	 * 联系我们常见问题
	 */
	public static final String TYPE0002 = "0002";

	/*
	 * 运行成果-发布融资总额
	 */
	public static final String STATICRESULT01 = "01";
	/*
	 * 运行成果-解决融资总额
	 */
	public static final String STATICRESULT02 = "02";
	/*
	 * 运行成果-正在融资总额
	 */
	public static final String STATICRESULT03 = "03";
	/*
	 * 运行成果-平台企业数
	 */
	public static final String STATICRESULT04 = "04";
	/*
	 * 运行成果-平台机构数
	 */
	public static final String STATICRESULT05 = "05";
	/*
	 * 运行成果-月度融资统计
	 */
	public static final String STATICRESULT06 = "06";

	/*
	 * 运行成果-平台机构用户数
	 */
	public static final String STATICRESULT07 = "07";
	/*
	 * 融资需求是否公开
	 */
	public static final String ISOPEN = "0";

	/*
	 * 默认投资机构名称
	 */
	public static final String DEFALUTINVERSTOR = "不公开的投资者";

	/*
	 * 默认企业名称
	 */
	public static final String DEFALUTCOMPANY = "不公开的企业";

	/*
	 * 默认融资需求名称
	 */
	public static final String DEFALUTDEMAND = "不公开的项目名";
	/*
	 * 默认融资需求名称
	 */
	public static final String DEFALUTEEVNT = "不公开的投资信息";
	/*
	 * 注册待审核
	 */
	public static final String REFISTER_STATUS_PENGDING = "1";
	/*
	 * 注册审核通过
	 */
	public static final String REFISTER_STATUS_PASS = "2";
	/*
	 * 注册审核不通过
	 */
	public static final String REFISTER_STATUS_NOPASS = "3";
	/*
	 * 注册待审核
	 */
	public static final String EDIT_STATUS_PENGDING = "4";
	/*
	 * 注册审核通过
	 */
	public static final String EDIT_STATUS_PASS = "5";
	/*
	 * 注册审核不通过
	 */
	public static final String EDIT_STATUS_NOPASS = "6";

	/**
	 * 用户信息-机构ID
	 */
	public static final String LOGINORGNO = "orgNo";
	/**
	 * 用户信息-用户名
	 */
	public static final String LOGINUSERNAME = "username";

	/**
	 * 用户信息-用户名
	 */
	public static final String LOGINUSERID = "userId";

	/**
	 * 需求状态-草稿
	 */

	public static final String DEMANDSTATUS00 = "00";
	/**
	 * 需求状态-发布
	 */

	public static final String DEMANDSTATUS01 = "01";
	/**
	 * 需求状态-融资中
	 */

	public static final String DEMANDSTATUS02 = "02";
	/**
	 * 需求状态-融资成功
	 */

	public static final String DEMANDSTATUS03 = "03";
	/**
	 * 需求状态-关闭
	 */

	public static final String DEMANDSTATUS99 = "99";
	/**
	 * 投资机构投资阶段-发布
	 */

	public static final String FINANCPHASE01 = "01";
	/**
	 * 投资机构投资阶段-关注
	 */

	public static final String FINANCPHASE11 = "11";
	/**
	 * 投资机构投资阶段-忽略
	 */

	public static final String FINANCPHASE12 = "12";
	/**
	 * 投资机构投资阶段-启动约谈
	 */

	public static final String FINANCPHASE21 = "21";
	/**
	 * 投资机构投资阶段-取消关注
	 */

	public static final String FINANCPHASE22 = "22";
	/**
	 * 投资机构投资阶段-投资成功
	 */

	public static final String FINANCPHASE31 = "31";
	/**
	 * 投资机构投资阶段-投资成功-未放款
	 */

	public static final String FINANCPHASE41 = "41";
	/**
	 * 投资机构投资阶段-投资成功-放款中(未披露)
	 */

	public static final String FINANCPHASE42 = "42";
	/**
	 * 投资机构投资阶段-投资成功-放款中(已披露)
	 */

	public static final String FINANCPHASE43 = "43";
	/**
	 * 投资机构投资阶段-投资成功-放款完成
	 */

	public static final String FINANCPHASE44 = "44";
	/**
	 * 投资机构投资阶段-投资成功-披露完成
	 */

	public static final String FINANCPHASE45 = "45";
	/**
	 * 投资机构投资阶段-拒绝投资
	 */

	public static final String FINANCPHASE32 = "32";
	/**
	 * 投资机构投资阶段-关闭
	 */

	public static final String FINANCPHASE99 = "99";

	/**
	 * 是否接收多个投资机构
	 */

	public static final String MULTI = "1";
	/**
	 * 已披露
	 */

	public static final String PUBLISHED = "0";
	/**
	 * 未披露
	 */

	public static final String UNPUBLISHED = "1";
	/**
	 * 已过期
	 */

	public static final String OVERED = "0";
	/**
	 * 未过期
	 */

	public static final String UNOVERED = "1";
	/**
	 * 展示
	 */
	public static final String SHOWTRUE = "0";
	/**
	 * 隐藏
	 */
	public static final String SHOWFALSE = "1";
	
	public static final String EXECUTEWATING = "00";

	public static final String EXECUTESTART = "01";

	public static final String EXECUTEXCEPTION = "02";

	public static final String EXECUTSUCC = "03";
	
	public static final String SCOREFLAG = "04";
	
	public static final String USER_TYPE_03 = "03";//区县金融办用户
	public static final String USER_TYPE_04 = "04";//市金融办用户
	public static final String USER_TYPE_05 = "05";//征信公司用户
	public static final String USER_TYPE_06 = "06";//征信公司待审核用户
	
	
	public static final String SMS_CONYENT_XJRB_REGIST = "提交的注册申请初审";
	public static final String SMS_CONYENT_SJRB_REGIST = "提交的注册申请复审";
	public static final String SMS_CONYENT_ZXGS_REGIST = "提交的注册申请激活";
	public static final String SMS_CONYENT_SJRB_ORG_UPDATE = "提交的注册申请审核";
	public static final String SMS_CONYENT_ZXGS_ORG_REGIST = "提交的注册申请激活";
	
	public static final String SMS_CONYENT_XJRB_UPDATE = "修改的企业信息初审";
	public static final String SMS_CONYENT_SJRB_UPDATE = "修改的企业信息复审";
	
	
	
	
	public static final String SMS_CONYENT_DSJRB_ORG_UPDATE = "提交的注册申请审核";
			// 运行成果-区域榜单
	public static final String Max_rankAreaSql = "select MAX(countdate) from platform_portal_rangking_company_area where 1=1 ";
			// 运行成果-行业榜单
	public static final String Max_rankIndustrySql = "select MAX(countdate) from platform_portal_rangking_company_industry where 1=1 ";
			// 运行成果-轮次统计
	public static final String Max_finacTurnSql = "select MAX(countdate) from platform_portal_rangking_finacturn where 1=1 ";
	
	/**
	 * 01：融资轮次统计；02月度统计
	 */
	public static final String TYPE = "01";
	public  static final String templatesName_warrant="investwarrant_templates.ftl";
	public  static final String templatesName_invest="investInfor_templates.ftl";
	
	public  static final String templatesName_rewardDeclare="rewardDeclare_templates.ftl";

	public  static final String  templatesName_enterprise="EnterpriseInfor_templates.ftl";
	public  static final String templatesName_enterwarrant="Enterprisewarrant_templates.ftl";
	public  static final  String ftlName_invest="注册申请表";
	public  static final  String ftlName_warrant="授权书";
	public  static final  String declare_name="奖励申报表";
	/**
	 * Redis币种数据字典key29
	 */
	public static final String DD_CURRENCY ="dd:currency" ;
	
	/**
	 * 人民币
	 */
	public static final String CNY_KEY ="01" ;
	/**
	 * 美元
	 */
	public static final String USD_KEY ="02" ;
	/**
	 * 业绩信息未填写，填写了原因
	 */
	public static final String  cause_of_performance ="33";
	
	
	/**
	 * 发送短信类别
	 */
	public static final String SMS_TYPE_DEMANDISOPEN = "01";//关注前-通知企业是否转公开
	public static final String SMS_TYPE_ISINVESTPUSH = "02";//关注后-通知企业是否披露信息给机构
	public static final String SMS_TYPE_ISINVESTFUNDPUSH = "03";//关注后-通知企业是否披露信息给基金
	public static final String SMS_TYPE_FOCUSBEFOEXPRICE = "04";//关注前-通知机构提示有效期
	public static final String SMS_TYPE_FOCUSAFTEREXPRICE = "05";//关注后-关注有效期提醒机构
	public static final String SMS_TYPE_TALKEXPRICEONE = "06";//约谈后-询问机构项目进度 第一次
	public static final String SMS_TYPE_TALKEXPRICETWO = "07";//约谈后-询问机构项目进度 第二次
	public static final String SMS_TYPE_ANSWEROPEN = "08";//企业回复转公开 
	public static final String SMS_TYPE_ANSWERINVEST1 = "061";//机构回复融资中
	public static final String SMS_TYPE_ANSWERINVEST2 = "062";//机构回复已投 
	public static final String SMS_TYPE_ANSWERINVEST3 = "063";//机构回复放弃投资
	public static final String SMS_TYPE_ANSWERERROR = "010";//回复内容格式错误
//	public static final String SMS_TYPE_ANSWERINVEST = "011";//机构回复放弃投资
	public static final String SMS_TYPE_ANSWERGIVEUP = "012";//放弃投资生效
	public static final String SMS_TYPE_ANSWERSUCESS = "013";//企业回复不愿意转公开

	/**
	 * 发送短信状态
	 */
	public static final String SMS_STATUS_WAITSEND = "00";//待发送
	public static final String SMS_STATUS_SEND = "01";//已发送
	public static final String SMS_STATUS_SENDERROR = "02";//发送失败
	public static final String SMS_STATUS_ANSWER = "03";//回复
	public static final String SMS_STATUS_ERRORANSWER = "04";//回复失败
	public static final String SMS_STATUS_NOANSWER = "05";//未回复作废
	public static final String SMS_STATUS_PLATFORUPDATE = "06";//未回复作废
	/**
	 * 回复标识
	 */
	public static final String SMS_ANSWER_1 = "1";//愿意
	public static final String SMS_ANSWER_2 = "2";//不愿意
	public static final String SMS_ANSWER_3 = "3";//放弃
	/**
	 * 导入征信系统返回标识
	 */
	public  static final String ZXSQ_RESULT_200 ="200";//成功
	public  static final String ZXSQ_RESULT_400 ="400";//失败
	
	/**
	 * 审核状态
	 */
	public  static final String DECLARER_RECEIVED_NO ="00";//待接收
	public  static final String DECLARER_RECEIVED_YSE ="01";//已接收
	public  static final String DECLARER_RECEIVED_CANCEL ="02";//已作废
	
	public  static final String DECLARER_DECLARE_YES ="1";//待接收
	public  static final String DECLARER_DECLARE_NO ="2";//已接收
	
	/**
     * 提交异议状态 (00:初始化，01，提交，02：修改,03：已解除)
     */
    public  static final String DISSENT_STATUS_00 ="00";
    public  static final String DISSENT_STATUS_01 ="01";
    public  static final String DISSENT_STATUS_02 ="02";
    public  static final String DISSENT_STATUS_03 ="03";
    
   
    
    /**
     * 提交异议类别00:企业异议；01：机构异议
     */
    public  static final String MESSAGE_TYPE_00 ="00";//新增企业异议提醒
    public  static final String MESSAGE_TYPE_01 ="01";//新增企业注册提醒
    
    public  static final String MESSAGE_TYPE_02 ="02";//新增机构异议提醒
    public  static final String MESSAGE_TYPE_03 ="03";//新增机构注册提醒
    
    public  static final String COMPANY_USER_STOP ="0";//新增机构注册提醒
    public  static final String COMPANY_USER_UP ="1";//新增机构注册提醒

}

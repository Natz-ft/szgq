package com.icfcc.credit.platform.util;

/**
 * <公共常量类>
 * 
 * @author Densen.Liu
 * @date 2017年3月30日
 */
public class CommonConstant {

	/**
	 * 代表个人征信
	 */
	public static final String P = "P";
	/**
	 * 代表企业征信
	 */
	public static final String O_FLAG = "E";
	/**
	 * 从标识码中分割业务号起始下标
	 */
	public static final int SubBegin = 15;
	/**
	 * 接口导入无基础段数据
	 */
	public static final String NOHEAD = "无对应基础段信息";
	/**
	 * 接口自校验的重复数据
	 */
	public static final String REPEAT = "重复数据";
	/**
	 * 上报状态-未上报
	 */
	public static final String STAT_UNREPORTED = "00";
	/**
	 * 上报状态-待更正
	 */
	public static final String STAT_CORRECT = "01";
	/**
	 * 上报状态-已上报
	 */
	public static final String STAT_REPORTED = "10";
	/**
	 * 上报状态-校验出错
	 */
	public static final String STAT_CHECKERROR = "30";
	/**
	 * 上报状态-删除未上报
	 */
	public static final String STAT_DELETE = "40";
	/**
	 * 上报状态-反馈成功
	 */
	public static final String STAT_SUCCEED = "20";
	/**
	 * 上报状态-反馈失败
	 */
	public static final String STAT_FAILED = "21";
	/**
	 * 上报状态-删除状态前缀
	 */
	public static final String DELETE_PREFIX = "4";
	/**
	 * 上报状态-删除已报送
	 */
	public static final String STAT_DELETED = "41";

	/**
	 * 上报状态-删除反馈失败
	 */
	public static final String STAT_DELFAILED = "42";
	/**
	 * 上报状态-删除反馈成功
	 */
	public static final String STAT_DELSUCCEED = "44";

	/**
	 * 上报状态-更正已删除
	 */
	public static final String STAT_UPDATE_DELETE = "45";

	/**
	 * 不上报标志默认值 0-正常上报，1-逾期不上报，2-收回逾期款项不上报，3-其他不上报
	 */
	public static final String NORPT_NORMAL = "0";

	/**
	 * 不上报标志逾期不上报
	 */
	public static final String NORPT_OVERDUE = "1";

	/**
	 * 不上报标志设置为其他不上报
	 */
	public static final String NORPT_NUREPORTED = "3";
	/**
	 * 整笔删除重新上报默认为不重新上报
	 */
	public static final String NO_REPORTED = "0";

	/**
	 * 整笔删除重新上报——重新上报标志
	 */
	public static final String RE_SUBMIT = "1";

	/**
	 * 操作员默认为admin
	 */
	public static final String CURRENT_USER = "System";
	/**
	 * 表示当前登录用户所属机构号
	 */
	public static final String CURRENT_COMPANY = "0";
	/**
	 * 数据库倒序关键字
	 */
	public final static String DIRECTION = "desc";
	/**
	 * 更新时间
	 */
	public static final String ORDERBY = "changeDate";

	/**
	 * 系统编号
	 */
	public static final String SYSTEM_ID = "000001";
	/**
	 * 单法人标识
	 */
	public static final String SINGLELEGAL = "1";
	/**
	 * 多法人标识
	 */
	public static final String MULTILEGAL = "2";
	/**
	 * 是否配置客户提供客户号 1:不提供; 2:提供 默认的情况:1 不提供客户号
	 */
	public static final String NO_OFFER_CUSTID = "1";
	/**
	 * 提供客户号
	 */
	public static final String OFFER_CUSTID = "2";
	/**
	 * /个人基本信息记录类型
	 */
	public static final String INFRECTYPE_BASICINFO = "110";

	/**
	 * 接口导入每页记录数
	 */
	public static final int PAGESIZE = 100;
	// public static final int PAGESIZE = 10;
	/**
	 * 从接口到业务表默认用户
	 */
	public static final String OPERATOR = "system";
	/**
	 * 整笔删除不重新上报标志
	 */
	public static final String RE_RPT_NO = "0";
	/**
	 * 整笔删除重新上报标志
	 */
	public static final String RE_RPT = "1";

	/**
	 * 每个报文号处理条数
	 */
	// public static final int MSGFILENUM=1000000;
	public static final int MSGFILENUM = 10000;
	/**
	 * 居民身份证及其他以公民身份证号为标 识的证件
	 */
	public static final String ID_CARD = "10";

	/**
	 * 批次号和报文号初始序列号
	 */
	public static final String SERIAL_NO = "001";

	/**
	 * 默认情况下，已处理数为0
	 */
	public static final long HANDLE_NUM = 0L;
	/**
	 * 表示检验阶段
	 */
	public static final String STAGE_CHECK = "0";
	/**
	 * 表示反馈阶段
	 */
	public static final String STAGE_FEEDBACK = "1";
	/**
	 * 上报错误表处理状态,0表明未处理
	 */
	public static final String STATE_PENDING = "0";
	/**
	 * 上报错误表处理状态,1表明未处理
	 */
	public static final String STATE_SOLVED = "1";

	/**
	 * 账户标识码获取业务号截取数值
	 */
	public static final int ACCTCODE_PREFIX_NUM = 16;

	/**
	 * 表名中业务类型标识-R为上报，B为业务，I为接口，H为历史
	 */
	public static final String TP_RPT = "R";

	/**
	 * 表名中业务类型标识-R为上报，B为业务，I为接口，H为历史
	 */
	public static final String TP_BUSS = "B";

	/**
	 * 表名中业务类型标识-R为上报，B为业务，I为接口，H为历史
	 */
	public static final String TP_HIS = "H";

	/**
	 * 文件头其实标识
	 */
	public static final String FILE_START_FLAG = "A";

	/**
	 * 文件头长度，报送固定为50位，补全三位，为050
	 */
	public static final String FILE_HEADER_LENGTH = "050";

	/**
	 * 信息记录版本号
	 */
	public static final String VERSION = "2.0.0";
	/**
	 * 信息记录模板类型-其他信息-两个空格
	 */
	public static final String TEMPLATE = "  ";

	/**
	 * 报文头-预留位
	 */
	public static final String RESERVED = "0";

	/**
	 * 护照的证件号码
	 */
	public static final String PASSPORT = "2";

	/**
	 * 港澳居民往来大陆通行证
	 */
	public static final String HKMACIDPASS = "5";
	/**
	 * 台湾同胞来往内地通行证
	 */
	public static final String TAIWANIDPASS = "6";

	/**
	 * 组织机构代码
	 */
	public static final String ORGCODE = "30";

	/**
	 * 统一社会信用代码
	 */
	public static final String CREDITCODE = "20";

	/**
	 * 中征码
	 */
	public static final String MEDIANCODE = "10";

	/**
	 * <基本信息-10-新增客户资料/首次上报>
	 */
	public static final String RPTDATECODEADD = "10";

	/**
	 * <基本信息-20-更新客户资料>
	 */
	public static final String RPTDATECODEUPDATE = "20";
	/**
	 * <基本信息记录-110>
	 */
	public static final String INFRECTYPE = "110";
	/**
	 * <可配置的公共URL>
	 */
	public static final String COMMONURL = "http://localhost:8080/creditplatformweb/systemConfig/findByName?name=";

	/**
	 * <个人证件有效期整笔删除>
	 */
	public static final String INIDEFCTINF_DEL = "134";
	/**
	 * <个人基本信息整笔删除请求记录>
	 */
	public static final String INFRECTYPE_DEL = "114";
	/**
	 * <加密因子>
	 */
	public static final String MD5_SALT = "JIAMI";
	/**
	 * <摘要名称>
	 */
	public static final String TOKEN = "TOKEN";
	/**
	 * <时间格式yyyy-MM-dd>
	 */
	public static final String DATEFMT = "yyyy-MM-dd";

	/**
	 * 财务报表报送时点说明代码：获取财务报表
	 */
	public static final String GET_FINANCE = "10";

	/**
	 * 财务报表报送时点说明代码：更新财务报表
	 */
	public static final String UPDATE_FINANCE = "20";
	/**
	 * 担保报送时点代码开户
	 */
	public static final String ACCOUNT_OPEN = "10";
	/**
	 * 担保报送时点代码关闭
	 */
	public static final String ACCOUNT_CLOSE = "20";
	/**
	 * 担保报送时点代码在保责任变化
	 */
	public static final String GUARRLTREPYMTINFSGMT_CHANGE = "30";
	/**
	 * 担保报送时点代码五级分类
	 */
	public static final String FIVECATE_CHANGE = "40";
	/**
	 * 担保报送时点代码其他变化
	 */
	public static final String OTHER_CHANGE = "50";
	/**
	 * <个人基本信息整笔删除信息记录类型>
	 */
	public static final String BASICDEL = "114";

	/**
	 * <处理中>
	 */
	public static final String PROCESSING = "1";
	/**
	 * <处理失败>
	 */
	public static final String PROCESS_FAILED = "2";
	/**
	 * <回滚中>
	 */
	public static final String ROLL_BACK_ING = "3";
	/**
	 * <回滚失败>
	 */
	public static final String ROLL_BACK_FAILED = "4";
	/**
	 * <处理完成>
	 */
	public static final String PROCESS_END = "5";
	/**
	 * <文件写入中>
	 */
	public static final String WRITE_FILE_ING = "6";
	/**
	 * <文件写入失败>
	 */
	public static final String WRITE_FILE_FAILED = "7";
	/**
	 * <文件写入完成>
	 */
	public static final String WRITE_FILE_END = "8";
	/**
	 * <回滚完成>
	 */
	public static final String ROLL_BACK_END = "9";

	/**
	 * <310-企业基本信息记录>
	 */
	public static final String EBBBBSSGMT_TYPE = "310";
	
	/**
	 * <340-企业基本信息记录>
	 */
	public static final String EBIB_INFRECTYPE = "340";

	/**
	 * <段标B>
	 */
	public static final String SGMT_CODE_B = "B";
	/**
	 * <段标C>
	 */
	public static final String SGMT_CODE_C = "C";
	/**
	 * <段标D>
	 */
	public static final String SGMT_CODE_D = "D";
	/**
	 * <段标E>
	 */
	public static final String SGMT_CODE_E = "E";
	/**
	 * <段标F>
	 */
	public static final String SGMT_CODE_F = "F";
	/**
	 * <段标G>
	 */
	public static final String SGMT_CODE_G = "G";
	/**
	 * <段标H>
	 */
	public static final String SGMT_CODE_H = "H";
	/**
	 * <段标I>
	 */
	public static final String SGMT_CODE_I = "I";

	/**
	 * <段标J>
	 */
	public static final String SGMT_CODE_J = "J";
	/**
	 * <段标K>
	 */
	public static final String SGMT_CODE_K = "K";
	/**
	 * <段标W>
	 */
	public static final String SGMT_CODE_W = "W";
	/**
	 * <段标X>
	 */
	public static final String SGMT_CODE_X = "X";
	/**
	 * <段标Y>
	 */
	public static final String SGMT_CODE_Y = "Y";
	/**
	 * <段标Z>
	 */
	public static final String SGMT_CODE_Z = "Z";
	/**
	 * <个人借贷模块标识>
	 */
	public static final String MODEL_TYEP_L = "L";
	/**
	 * <个人担保模块标识>
	 */
	public static final String MODEL_TYEP_G = "G";
	/**
	 * <个人基本信息模块标识>
	 */
	public static final String MODEL_TYEP_B = "B";
	/**
	 * <个人授信模块标识>
	 */
	public static final String MODEL_TYEP_C = "C";
	/**
	 * <抵质押模块标识>
	 */
	public static final String MODEL_TYEP_P = "P";
	/**
	 * <最高额保证合同模板标识>
	 */
	public static final String MODEL_TYEP_A = "A";
	/**
	 * <企业财务报表模块标识>
	 */
	public static final String MODEL_TYPE_F = "F";
	/**
	 * <非删除类报文判断标识>
	 */
	public static final String NORMAL_SUBMIT = "normal";
	/**
	 * <按段删除类报文判断标识>
	 */
	public static final String SOME_DEL_SUBMIT = "somedelsubmit";
	/**
	 * <整笔删除类报文判断标识>
	 */
	public static final String ALL_DEL_SUBMIT = "alldelsubmit";
	/**
	 * <启动状态为预处理>
	 */
	public static final long SUBMIT_STAT_PRESTART = 1L;
	/**
	 * <启动状态为处理>
	 */
	public static final long SUBMIT_STAT_START = 2L;
	/**
	 * <启动状态为结束>
	 */
	public static final long SUBMIT_STAT_END = 3L;
	/**
	 * 140-个人证件整合信息记录
	 */
	public static final String PBBL_INFRECTYPE = "140";
	/**
	 * 个人证件整合信息 证件关联关系有效标志 0:无效
	 */
	public static final String PBBL_OTHASSFLG_INVALID = "0";
	/**
	 * 个人证件整合信息 证件关联关系有效标志 1:有效
	 */
	public static final String PBBL_OTHASSFLG_VALID = "1";

	/**
	 * <已处理>
	 */
	public static final String ERROR_STAT = "1";
	/**
	 * <借贷模块>
	 */
	public static final String MODEL_L = "L";

	/**
	 * 企业基本信息 数据迁移 没有重复 有基础段
	 */
	public static final String NO_REPEAT_BUT_HAVE = "1";

}

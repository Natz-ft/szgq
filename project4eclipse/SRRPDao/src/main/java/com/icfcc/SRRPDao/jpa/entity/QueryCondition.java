package com.icfcc.SRRPDao.jpa.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class QueryCondition implements java.io.Serializable {

	private static final long serialVersionUID = 3903684524819260188L;

	// 每页最大记录数
	public int maxSize = 10;
	// 首页
	private int firstPage = 1;
	// 上一页
	private int prePage;
	// 下一页
	private int nextPage;
	// 尾页
	private int lastPage;
	// 当前页
	private int curPage = 1;

	// 机构ID
	private String investorId;
	// 轮次
	private String finacing_turn;
	// 需求阶段
	private String status;
	// 行业
	private String industry;
	// 币种
	private String currency;

	// 机构类型
	private String org_type;

	// 资本类型
	private String capital;

	// 资本实力
	private String capitalPower;

	// 地区
	private String area;

	// 项目名称
	private String projectName;

	private String publishFileName;
	private String publishFilePath;
	// 查询的开始时间
	private Date beginTime;

	// 查询的结束时间
	private Date endTime;

	// 查询开始时间
	private String beginTimeStr; // 开始时间

	// 查询结束
	private String endTimeStr; // 结束时间

	private String code;// 证件代码

	// 企业的id
	private String enterpriseId;

	// 所属区域
	private String rearea;

	// 现融资阶段
	private String financingphase;

	// 企业名称/证件代码
	private String nameAndCode;

	// 排序方式
	private String sortord;

	// 融资轮次
	private String finacingTurn;

	// 企业名称/项目名称
	private String nameAndProjectName;

	// 企业名称
	private String name;

	// 融资金额
	private String amount;

	//投资比例
	private Double ratio;
	
	// 融资金额标记
	private String amountCode;

	// 拟投资阶段
	private String financeStage;

	// 拟投资行业
	private String financeTrade;

	// 机构类型
	private String orgType;

	// 注册时间
	private String registeTime;

	// 排序方式
	private String OrderCase;

	// 机构名称
	private String investorName;

	// 是否为门户
	private boolean isPortal;

	// 信息类型
	private String infotype;

	// 资本类型
	private String capitalType;

	// 事件的ID
	private String eventId;

	// 是否制定多个投资机构
	private String multi;

	// 需求ID
	private String infoId;

	// 放款时间
	private String loanTime;

	// 纰漏信息标题
	private String infoTitle;

	// 纰漏信息内容
	private String infoContent;

	// 经营年限--字典
	private String enterprisePeriod;

	// 融资金额--字典
	private String financeAmout;

	// 名字或者证件代码
	private String nameOrCode;

	// 启用停用标志
	private String stopFlag;

	// 审核状态
	private String auditStatus;
	private String auditStage;
	// 成立时间
	private String setTime;

	private String operType;
	private String userId;

	// 要执行sql的标志
	private String sqlFlag;
	// 用户名
	private String userName;
	// 统计周期id标志
	private String statisticalCycleId;

	private int timeStart;
	private int timeEnd;

	public int getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(int timeStart) {
		this.timeStart = timeStart;
	}

	public int getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(int timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getStatisticalCycleId() {
		return statisticalCycleId;
	}

	public void setStatisticalCycleId(String statisticalCycleId) {
		this.statisticalCycleId = statisticalCycleId;
	}

	public String getSqlFlag() {
		return sqlFlag;
	}

	public void setSqlFlag(String sqlFlag) {
		this.sqlFlag = sqlFlag;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public String getNameOrCode() {
		return nameOrCode;
	}

	public void setNameOrCode(String nameOrCode) {
		this.nameOrCode = nameOrCode;
	}

	public String getStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(String stopFlag) {
		this.stopFlag = stopFlag;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}

	public String getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}

	public String getInfoId() {
		return infoId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getMulti() {
		return multi;
	}

	public void setMulti(String multi) {
		this.multi = multi;
	}

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	public String getInfotype() {
		return infotype;
	}

	public void setInfotype(String infotype) {
		this.infotype = infotype;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getFinacing_turn() {
		return finacing_turn;
	}

	public void setFinacing_turn(String finacing_turn) {
		this.finacing_turn = finacing_turn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOrg_type() {
		return org_type;
	}

	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}

	public String getOrderCase() {
		return OrderCase;
	}

	public void setOrderCase(String orderCase) {
		OrderCase = orderCase;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public String getFinanceStage() {
		return financeStage;
	}

	public void setFinanceStage(String financeStage) {
		this.financeStage = financeStage;
	}

	public String getFinanceTrade() {
		return financeTrade;
	}

	public void setFinanceTrade(String financeTrade) {
		this.financeTrade = financeTrade;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getRegisteTime() {
		return registeTime;
	}

	public void setRegisteTime(String registeTime) {
		this.registeTime = registeTime;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getRearea() {
		return rearea;
	}

	public void setRearea(String rearea) {
		this.rearea = rearea;
	}

	public String getFinancingphase() {
		return financingphase;
	}

	public void setFinancingphase(String financingphase) {
		this.financingphase = financingphase;
	}

	public String getNameAndCode() {
		return nameAndCode;
	}

	public void setNameAndCode(String nameAndCode) {
		this.nameAndCode = nameAndCode;
	}

	public String getSortord() {
		return sortord;
	}

	public void setSortord(String sortord) {
		this.sortord = sortord;
	}

	public String getFinacingTurn() {
		return finacingTurn;
	}

	public void setFinacingTurn(String finacingTurn) {
		this.finacingTurn = finacingTurn;
	}

	public String getNameAndProjectName() {
		return nameAndProjectName;
	}

	public void setNameAndProjectName(String nameAndProjectName) {
		this.nameAndProjectName = nameAndProjectName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmountCode() {
		return amountCode;
	}

	public void setAmountCode(String amountCode) {
		this.amountCode = amountCode;
	}

	public String getCapitalPower() {
		return capitalPower;
	}

	public void setCapitalPower(String capitalPower) {
		this.capitalPower = capitalPower;
	}

	public boolean isPortal() {
		return isPortal;
	}

	public void setPortal(boolean isPortal) {
		this.isPortal = isPortal;
	}

	public String getCapitalType() {
		return capitalType;
	}

	public void setCapitalType(String capitalType) {
		this.capitalType = capitalType;
	}

	public String getEnterprisePeriod() {
		return enterprisePeriod;
	}

	public void setEnterprisePeriod(String enterprisePeriod) {
		this.enterprisePeriod = enterprisePeriod;
	}

	public String getFinanceAmout() {
		return financeAmout;
	}

	public void setFinanceAmout(String financeAmout) {
		this.financeAmout = financeAmout;
	}

	public String getSetTime() {
		return setTime;
	}

	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public String getPublishFileName() {
		return publishFileName;
	}

	public void setPublishFileName(String publishFileName) {
		this.publishFileName = publishFileName;
	}

	public String getPublishFilePath() {
		return publishFilePath;
	}

	public void setPublishFilePath(String publishFilePath) {
		this.publishFilePath = publishFilePath;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	
	
}
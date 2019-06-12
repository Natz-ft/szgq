package com.icfcc.credit.platform.jpa.entity.system;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.icfcc.credit.platform.constants.VipCont;

/**
 * 
 * @author john 融资事件表实体类
 */
@Entity
@Table(name = "rp_finacing_event")
public class FinacingEvent {
	public static final String STATUS_WAIT = "01";// 未关注，待关注
	public static final String STATUS_FOCUS = "02";// 关注，待约谈
	public static final String STATUS_TOUCH = "03";// 约谈，待投资
	public static final String STATUS_INVEST = "04";// 投资，待放款
	public static final String STATUS_LOANED = "05";// 已放款
	public static final String STATUS_PUBLISHED = "06";// 已发布
	public static final String STATUS_UNFOCUS = "07";// 不关注
	public static final String STATUS_UNTOUCH = "08";// 不约谈
	public static final String STATUS_UNINVEST = "09";// 不投资

	@Id
	@Column(name = "event_id")
	// @GenericGenerator(name = "system-uuid", strategy = "uuid")
	// @GeneratedValue(generator = "system-uuid")
	private String eventId;// 融资事件id

	@Column(name = "info_id")
	// , insertable = false, updatable = false)
	private String infoId;

	@Column(name = "project_name")
	private String projectName;// 项目名称

	@Column(name = "enterprise_id")
	private String enterpriseId;// 企业id

	@Column(name = "investorg_id")
	private String investorgId;// 机构的id

	@Column(name = "status")
	private String status;// 需求进度

	@Column(name = "currency")
	private String currency;// 货币种类

	@Column(name = "amount")
	private String amount;// 投资金额

	@Column(name = "mailuser")
	private String mailuser;// 投递人

	@Column(name = "maildate")
	private Date maildate;// 投递时间

	@Column(name = "OPERUSER")
	private String operuser;// 操作人

	@Column(name = "ratio")
	private Double ratio;// 投资金额

	@Column(name = "exchange_ratio")
	private String exchangeRatio;// 投资金额兑换后人民币数值

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERDATE")
	private Date operdate;// 操作时间

	@Column(name = "publish_flag")
	private String publishFlag;//是否披露标志
	
	@Transient
	private String finacingStatusDicname;// 需求状态字典值
	
	@Transient
	private String currencyStr;
	
	@Transient
	private String amountStr;
	
	@Transient
	private String realUser;// 需求状态字典值
	
	@Transient
	private List<FinacingRecord> finacingRecordList;

	public List<FinacingRecord> getFinacingRecordList() {
		return finacingRecordList;
	}

	public void setFinacingRecordList(List<FinacingRecord> finacingRecordList) {
		this.finacingRecordList = finacingRecordList;
	}
	@Transient
	private String investorName;// 投资机构名

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}


	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getInvestorgId() {
		return investorgId;
	}

	public void setInvestorgId(String investorgId) {
		this.investorgId = investorgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMailuser() {
		return mailuser;
	}

	public void setMailuser(String mailuser) {
		this.mailuser = mailuser;
	}

	public Date getMaildate() {
		return maildate;
	}

	public void setMaildate(Date maildate) {
		this.maildate = maildate;
	}

	public String getOperuser() {
		return operuser;
	}

	public void setOperuser(String operuser) {
		this.operuser = operuser;
	}
	public String getCurrencyStr() {
		return VipCont.getValueByCode("dd:currency",this.currency);
	}

	public void setCurrencyStr(String currencyStr) {
		this.currencyStr = currencyStr;
	}
	public Date getOperdate() {
		return operdate;
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public String getExchangeRatio() {
		return exchangeRatio;
	}

	public void setExchangeRatio(String exchangeRatio) {
		this.exchangeRatio = exchangeRatio;
	}

	public String getRealUser() {
		return realUser;
	}

	public void setRealUser(String realUser) {
		this.realUser = realUser;
	}

	public String getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(String publishFlag) {
		this.publishFlag = publishFlag;
	}

	public String getAmountStr() {
		Double sumAmountD=Math.round(Double.valueOf(this.amount)*100000000)/100000000.00000000;
		String sumAmountS="";
		if(sumAmountD.intValue()-sumAmountD==0){//判断是否符合取整条件
			sumAmountS=	String.valueOf(sumAmountD.intValue());
		}else{
			sumAmountS=String.valueOf(sumAmountD);
		}
        return sumAmountS  + "   "+VipCont.getValueByCode("dd:currency",this.currency);
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	
}

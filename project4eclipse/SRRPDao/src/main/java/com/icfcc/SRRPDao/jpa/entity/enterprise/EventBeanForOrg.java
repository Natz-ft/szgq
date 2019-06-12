/**
 * 
 */
package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

/**
 * @author lijj
 *
 */
@Entity
public class EventBeanForOrg implements Serializable {

	private static final long serialVersionUID = -7278742436135924575L;

	@Column(name = "enterprise_name")
	private String enterpriseName;// 企业名称

	@Column(name = "status")
	private String status;// 需求状态

	@Column(name = "multi")
	private String multi;// 是否接受多个投资机构

	@Transient
	private String remindContent;// 提醒内容

	@Transient
	private String enterpriseNameShow;// 企业名称的展示字符串

	@Column(name = "score")
	private Integer score;// 企业评分

	@Column(name = "project_name")
	private String projectName;// 项目名称

	@Column(name = "event_id")
	private String eventId;// 融资事件id

	@Column(name = "enterprise_id")
	private String enterpriseId;// 企业的id

	@Column(name = "amount")
	private Double amount;// 投资金额

	@Column(name = "currency")
	private String currency;// 投资金额的币种

	@Column(name = "maildate")
	private Date maildate; // 投递时间

	@Column(name = "operdate")
	private Date operdate;

	@Column(name = "open")
	private String open;// 是否公开

	@Transient
	private String openStr;// 有效关注时间字符串
	
	@Transient
	private String remainTime;// 剩余时间

	@Column(name = "follow_time")
	private Date followTime;// 有效关注时间

	@Column(name = "demand_operdate")
	private Date demandOperdate;// 发布时间

	@Column(name = "rfd_currency")
	private String rfdCurrency;// 融资金额币种

	@Column(name = "reg_currency")
	private String regCurrency;// 企业注册资本币种
	
	@Column(name = "revoke_flag")
	private String revokeFlag;//主管是否撤销标识1为撤销0为未撤销
	
	@Column(name = "publish_flag")
	private String publishFlag;//主管是否撤销标识1为撤销0为未撤销
	
	public Date getDemandOperdate() {
		return demandOperdate;
	}

	public void setDemandOperdate(Date demandOperdate) {
		this.demandOperdate = demandOperdate;
	}
	
	@Transient
	private String eventAmount;
	
	@Transient
	private String eventAmountShow;

	@Transient
	private String rfdCurrencyDicname;// 融资金额币种字典值
	@Transient
	private String regCurrencyDicname;// 注册资金币种字典值
	@Transient
	private String currencyDicname;// 投资金额币种字典值

	@Transient
	private String finacingAmount;// 融资金额
	
	@Transient
	private String finacingAmountShow;// 融资金额

	@Id
	@Column(name = "info_id")
	private String infoId;// 融资需求id

	@Transient
	private String followTimeStr;// 有效关注时间字符串

	@Transient
	private String operdateStr;

	@Transient
	private String operTimeStr;// 操作日期

	@Transient
	private String showFlag;//用于判断信用报告是否展示
	
	@Transient
	private String demandOperdateStr;
	
	
	

	public String getOperTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.operdate);
	}

	public void setOperTimeStr(String operTimeStr) {
		this.operTimeStr = operTimeStr;
	}

	public String getDemandOperdateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.demandOperdate);
	}

	public void setDemandOperdateStr(String demandOperdateStr) {
		this.demandOperdateStr = demandOperdateStr;
	}

	@Transient
	private String maildateStr;// 投递时间字符串

	@Column
	private String codetype;// 证件号类型

	@Column
	private String code;// 证件号

	@Column(name = "demand_Status")
	private String demandStatus;// 融资需求表中该需求的状态

	@Transient
	private String isPublished;// 是否披露过 0：已披露；1：未披露

	@Transient
	private String isOvered;// 是否过期 0：过期；1：未过期

	@Column(name = "AMOUNTMIN")
	private Double amountMin = 0.0;

	@Column(name = "AMOUNTMAX")
	private Double amountMax = 0.0;

	
	public String getEventAmount() {
		if(this.amount!=null){
			Double sumAmountD=Math.round(Double.valueOf(this.amount)*1000000)/100000000.00000000;
			String sumAmountS="";
			if(sumAmountD.intValue()-sumAmountD==0){//判断是否符合取整条件
				sumAmountS=	String.valueOf(sumAmountD.intValue());
			}else{
				sumAmountS=String.valueOf(sumAmountD);
			}
			eventAmount= sumAmountS  + "   "+getCurrencyDicname();
//			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
////			 df.applyPattern("#0.#######");
//			eventAmount =this.amount/100+"   "+getCurrencyDicname();
		}
		return eventAmount;
	}

	public void setEventAmount(String eventAmount) {
		this.eventAmount = eventAmount;
	}
	
	public String getEventAmountShow() {
		if(this.amount!=null){
			Double sumAmountD=Math.round(Double.valueOf(this.amount)*1000000)/1000000.00000000;
			String sumAmountS="";
			if(sumAmountD.intValue()-sumAmountD==0){//判断是否符合取整条件
				sumAmountS=	String.valueOf(sumAmountD.intValue());
			}else{
				sumAmountS=String.valueOf(sumAmountD);
			}
			eventAmount= sumAmountS  + "   "+getCurrencyDicname();
		}
		return eventAmount;
	}

	public void setEventAmountShow(String eventAmount) {
		this.eventAmountShow = eventAmount;
	}

	public String getFinacingAmount() {
		String tmpAmount = "";
		if (this.amountMin == null || "".equals(this.amountMin)) {
			tmpAmount = "0";
		} else {
			tmpAmount = DigitFormatUtil.digitFormat(this.amountMin).toString();
		}
		if (this.amountMax == null || "".equals(this.amountMax)) {
			tmpAmount += "~" + "0" + " " + this.getRfdCurrencyDicname();
		} else {
			tmpAmount += "~"
					+ DigitFormatUtil.digitFormat(this.amountMax).toString()
					+ " " + this.getRfdCurrencyDicname();
		}
		return tmpAmount;
	}

	public void setFinacingAmount(String finacingAmount) {
		this.finacingAmount = finacingAmount;
	}
	
	public String getFinacingAmountShow() {
		String tmpAmount = "";
		if (this.amountMin == null || "".equals(this.amountMin)) {
			tmpAmount = "0";
		} else {
			tmpAmount = DigitFormatUtil.digitFormat(this.amountMin*100).toString();
		}
		if (this.amountMax == null || "".equals(this.amountMax)) {
			tmpAmount += "~" + "0" + " " + this.getRfdCurrencyDicname();
		} else {
			tmpAmount += "~"
					+ DigitFormatUtil.digitFormat(this.amountMax*100).toString()
					+ " " + this.getRfdCurrencyDicname();
		}
		return tmpAmount;
	}

	public void setFinacingAmountShow(String finacingAmount) {
		this.finacingAmountShow = finacingAmount;
	}

	public String getCodetype() {
		return codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMulti() {
		return multi;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public void setMulti(String multi) {
		this.multi = multi;
	}

	public String getOperdateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.operdate);
	}

	public void setOperdateStr(String operdateStr) {
		this.operdateStr = operdateStr;
	}

	public String getMaildateStr() {
		if (this.maildate == null) {
			return "长期";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this.maildate);
		}
	}

	public void setMaildateStr(String maildateStr) {
		this.maildateStr = maildateStr;
	}

	public String getFollowTimeStr() {
		if (this.followTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this.followTime);
		} else {
			return "长期";
		}

	}

	public void setFollowTimeStr(String followTimeStr) {
		this.followTimeStr = followTimeStr;
	}

	public Date getFollowTime() {
		return followTime;
	}

	public void setFollowTime(Date followTime) {
		this.followTime = followTime;
	}

	public String getRemindContent() {
		if (this.followTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return "该融资需求定向推送给您所在机构，" + "有效关注日期为" + sdf.format(this.followTime)
					+ "，请注意关注";
		} else {
			return "该融资需求定向推送给您所在机构，" + "有效关注日期为长期，请注意关注";
		}

	}

	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getMaildate() {
		return maildate;
	}

	public void setMaildate(Date maildate) {
		this.maildate = maildate;
	}

	public Date getOperdate() {
		return operdate;
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public Double getAmountMin() {
		return amountMin;
	}

	public void setAmountMin(Double amountMin) {
		this.amountMin = amountMin;
	}

	public Double getAmountMax() {
		return amountMax;
	}

	public void setAmountMax(Double amountMax) {
		this.amountMax = amountMax;
	}

	public String getIsOvered() {
		return isOvered;
	}

	public void setIsOvered(String isOvered) {
		this.isOvered = isOvered;
	}

	public String getDemandStatus() {
		return demandStatus;
	}

	public void setDemandStatus(String demandStatus) {
		this.demandStatus = demandStatus;
	}

	public String getRfdCurrency() {
		return rfdCurrency;
	}

	public void setRfdCurrency(String rfdCurrency) {
		this.rfdCurrency = rfdCurrency;
	}

	public String getRegCurrency() {
		return regCurrency;
	}

	public void setRegCurrency(String regCurrency) {
		this.regCurrency = regCurrency;
	}

	public String getRfdCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY,
				this.rfdCurrency);
	}

	public void setRfdCurrencyDicname(String rfdCurrencyDicname) {
		this.rfdCurrencyDicname = rfdCurrencyDicname;
	}

	public String getRegCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY,
				this.regCurrency);
	}

	public void setRegCurrencyDicname(String regCurrencyDicname) {
		this.regCurrencyDicname = regCurrencyDicname;
	}

	public String getCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY,
				this.currency);
	}

	public void setCurrencyDicname(String currencyDicname) {
		this.currencyDicname = currencyDicname;
	}

	public String getEnterpriseNameShow() {
		return enterpriseNameShow;
	}

	public void setEnterpriseNameShow(String enterpriseNameShow) {
		this.enterpriseNameShow = enterpriseNameShow;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getOpenStr() {
		if (this.open != null) {
			if ("0".equals(this.open)) {
				openStr = "公开";
			} else {
				openStr = "定投";
			}
		}
		return openStr;
	}

	public void setOpenStr(String openStr) {
		this.openStr = openStr;
	}

	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	public String getRevokeFlag() {
		return revokeFlag;
	}

	public void setRevokeFlag(String revokeFlag) {
		this.revokeFlag = revokeFlag;
	}

	public String getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(String publishFlag) {
		this.publishFlag = publishFlag;
	}
	
	
	@Transient
    private String projectNameShow;
	
	public String getProjectNameShow() {
		Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
		String[] str = this.projectName.split("~");
		StringBuffer sb = new StringBuffer();
		for (String s : str) {
			Matcher matcher = compile.matcher(s);
			if(matcher.find()) {
				String o = matcher.group();
				String n = String.valueOf((int)(Double.valueOf(o)*100));
				sb.append(s.replace(o, n)+"~");
			}
		}
		return sb.toString().substring(0, sb.length()-1).replace("百万元", "万元");
	}

	public void setProjectNameShow(String projectNameShow) {
		this.projectNameShow = projectNameShow;
	}
	
	
	
	

	

	
}

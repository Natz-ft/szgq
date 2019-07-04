package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 融资需求详情信息查询列表，返回值类型封装类
 * 
 * @author Administrator
 *
 */

@Entity
public class FinacingDemandInfoResultNew implements Serializable {

	private static final long serialVersionUID = -9182307008368341300L;

	@Id
	@Column(name = "INFO_ID", length = 32)
	private String infoId;

	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;

	@Column(name = "PROJECT_NAME", length = 60)
	private String projectName;

	// @Column(name = "AMOUNT")
	// private Double amount;

	@Column(name = "CURRENCY", length = 3)
	private String currency;//融资金额币种

	@Column(name = "FOLLOW_TIME")
	private Date followTime;

	@Column(name = "FINACING_TURN", length = 3)
	private String finacingTurn;

	@Transient
	private String finacingTurnDicname;// 融资轮次字典值

	@Column(name = "SELL", length = 1)
	private String sell;

	@Transient
	private String scale;

	@Transient
	private String enterpriseNameShow;// 企业名称的展示字符串
	
	@Column(name = "SCALEMIN", length = 10)
	private String scaleMin;

	@Column(name = "SCALEMAX", length = 10)
	private String scaleMax;

	@Column(name = "STATUS", length = 10)
	private String status;
	
	@Transient
	private String statusDicname;// 需求字典值

	@Column(name = "OPERDATE")
	private Date operdate;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "INDUSTRY", length = 4)
	private String industry;

	@Transient
	private String industry2;// 二级行业
	
	@Transient
	private boolean changeFlag;// 二级行业
	@Transient
	private String industry2Dicname;// 二级行业字典值
	
	@Transient
	private String industryStr;// 行业展示字段
	
	@Transient
	private String industryDicname;// 行业字典值

	@Transient
	private Date startTime;

	@Transient
	private Date endTime;

	@Column(name = "codetype", length = 4)
	private String codetype;// 证件类型

	@Column(name = "code", length = 20)
	private String code;// 证件号

	@Column(name = "score", length = 3)
	private Integer score;// 总评分

	@Column(name = "OPEN", length = 1)
	private String open; // 是否公开

	@Column(name = "APPOINT_INVESTOR", length = 100)
	private String appointInvestor;// 指定投资机构

	@Column(name = "AMOUNTMIN")
	private Double amountMin = 0.0;

	@Column(name = "AMOUNTMAX")
	private Double amountMax = 0.0;

	@Transient
	private String amount;// 关注时间字符串

	@Transient
	private String followTimeStr;// 关注时间字符串

	@Transient
	private String operdateStr;// 操作时间字符串

	@Transient
	private String openStr;// 操作时间字符串
	
	@Column(name = "showflag")
	private String showflag;// 该融资机构在事件表中是否有对应的数据 0 没有，1 有

	@Column(name = "event_id")
	private String eventId;

	@Column(name = "revoke_flag")
	private String revokeFlag;//主管是否撤销标识1为撤销0为未撤销
	
	
	@Transient
	private boolean clickFlag;// 详情以及企业的详情是否可以查询
	
	@Transient
	private String projectNameShow;
	
	@Transient
	private String amountShow;

	@Column(name = "rearea")
	//@Transient
	private String rearea;

	public String getRearea() {
		return rearea;
	}

	public void setRearea(String rearea) {
		this.rearea = rearea;
	}

	public String getProjectNameShow() {
		Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
		String[] str = projectName.split("~");
		StringBuffer sb = new StringBuffer();
		for (String s : str) {
			Matcher matcher = compile.matcher(s);
			if(matcher.find()) {
				
				String o = matcher.group();
				String n = String.valueOf((int)(Double.valueOf(o)*100));
				sb.append(s.replace(o, n)+"~");
			}
		}
		return sb.toString().substring(0, sb.length()-1).replace("百万", "万");
	}

	public void setProjectNameShow(String projectNameShow) {
		this.projectNameShow = projectNameShow;
	}

	public String getAmountShow() {
		String tmpAmount = "";
		if (this.amountMin == null || "".equals(this.amountMin)) {
			tmpAmount = "0";
		} else {
			tmpAmount = DigitFormatUtil.digitFormat(this.amountMin*100).toString();
		}
		if (this.amountMax == null || "".equals(this.amountMax)) {
			tmpAmount += "~" + "0" +" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
		} else {
			tmpAmount += "~"
					+ DigitFormatUtil.digitFormat(this.amountMax*100).toString()+" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
		}
		return tmpAmount;
	}

	public void setAmountShow(String amountShow) {
		this.amountShow = amountShow;
	}

	public String getAmount() {
		String tmpAmount = "";
		if (this.amountMin == null || "".equals(this.amountMin)) {
			tmpAmount = "0";
		} else {
			tmpAmount = DigitFormatUtil.digitFormat(this.amountMin).toString();
		}
		if (this.amountMax == null || "".equals(this.amountMax)) {
			tmpAmount += "~" + "0" +" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
		} else {
			tmpAmount += "~"
					+ DigitFormatUtil.digitFormat(this.amountMax).toString()+" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
		}
		return tmpAmount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getFollowTimeStr() {
		if(this.followTime!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this.followTime);
		}else{
			return "长期";
		}
	}

	public String getShowflag() {
		return showflag;
	}

	public void setShowflag(String showflag) {
		this.showflag = showflag;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public void setFollowTimeStr(String followTimeStr) {
		this.followTimeStr = followTimeStr;
	}

	public String getOperdateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(this.operdate);
		return sdf.format(this.operdate);
	}

	public void setOperdateStr(String operdateStr) {
		this.operdateStr = operdateStr;
	}

	public String getFinacingTurnDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN,
				this.finacingTurn);
	}

	public void setFinacingTurnDicname(String finacingTurnDicname) {
		this.finacingTurnDicname = finacingTurnDicname;
	}

	public String getStatusDicname() {
		if("1".equals(this.revokeFlag)){
			return "撤回";
		}else{
			return RedisGetValue.getValueByCode(SRRPConstant.DD_DEMANDSTATUS,
					this.status);
		}
		
	}

	public void setStatusDicname(String statusDicname) {
		this.statusDicname = statusDicname;
	}

	public String getIndustryDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY,
				this.industry);
	}

	public void setIndustryDicname(String industryDicname) {
		this.industryDicname = industryDicname;
	}

	public String getAppointInvestor() {
		return appointInvestor;
	}

	public void setAppointInvestor(String appointInvestor) {
		this.appointInvestor = appointInvestor;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getFollowTime() {
		return followTime;
	}

	public void setFollowTime(Date followTime) {
		this.followTime = followTime;
	}

	public String getFinacingTurn() {
		return finacingTurn;
	}

	public void setFinacingTurn(String finacingTurn) {
		this.finacingTurn = finacingTurn;
	}

	public String getSell() {
		if ("0".equals(this.sell)) {
			String tmpScale = "";
			if (this.scaleMin == null || "".equals(this.scaleMin)) {
				tmpScale = "0%";
			} else {
				tmpScale = DigitFormatUtil.digitFormat(this.scaleMin) + "%";
			}
			if (this.scaleMax == null || "".equals(this.scaleMax)) {
				tmpScale += "~" + "0%";
			} else {
				tmpScale += "~" + DigitFormatUtil.digitFormat(this.scaleMax)
						+ "%";
			}
			return tmpScale;
		} else {
			String tmpScale = "";
			if (this.scaleMin == null || "".equals(this.scaleMin)) {
				tmpScale = "0%";
			} else {
				tmpScale = DigitFormatUtil.digitFormat(this.scaleMin) + "%";
			}
			if (this.scaleMax == null || "".equals(this.scaleMax)) {
				tmpScale += "~" + "0%";
			} else {
				tmpScale += "~" + DigitFormatUtil.digitFormat(this.scaleMax)
						+ "%";
			}
			return tmpScale;
//			return "N/A";
		}
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOperdate() {
		return operdate;
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getScaleMin() {
		return scaleMin;
	}

	public void setScaleMin(String scaleMin) {
		this.scaleMin = scaleMin;
	}

	public String getScaleMax() {
		return scaleMax;
	}

	public void setScaleMax(String scaleMax) {
		this.scaleMax = scaleMax;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getOpenStr() {
		String openString;
		if("1".equals(this.open)){
			openString="定投";
		}else{
			openString="公开";
		}
		return openString;
	}

	public void setOpenStr(String openStr) {
		this.openStr = openStr;
	}

	public String getIndustry2() {
		return industry2;
	}

	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}

	public String getIndustry2Dicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2,
				this.industry2);
	}

	public void setIndustry2Dicname(String industry2Dicname) {
		this.industry2Dicname = industry2Dicname;
	}

	public String getIndustryStr() {
		return industryStr;
	}

	public void setIndustryStr(String industryStr) {
		this.industryStr = industryStr;
	}

	public String getEnterpriseNameShow() {
		return enterpriseNameShow;
	}

	public void setEnterpriseNameShow(String enterpriseNameShow) {
		this.enterpriseNameShow = enterpriseNameShow;
	}

	public boolean isChangeFlag() {
		return changeFlag;
	}

	public void setChangeFlag(boolean changeFlag) {
		this.changeFlag = changeFlag;
	}

	public boolean isClickFlag() {
		return clickFlag;
	}

	public void setClickFlag(boolean clickFlag) {
		this.clickFlag = clickFlag;
	}

	public String getRevokeFlag() {
		return revokeFlag;
	}

	public void setRevokeFlag(String revokeFlag) {
		this.revokeFlag = revokeFlag;
	}

	
}

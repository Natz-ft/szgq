package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.icfcc.credit.platform.constants.VipCont;

/**
 * 融资需求信息实体类
 */
@Entity
@Table(name = "rp_finacing_demand")
public class FinacingDemand implements Serializable{
	@Id
	@Column(name = "INFO_ID", length = 32)
	private String infoId;

	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;

	@Column(name = "PROJECT_NAME", length = 60)
	private String projectName;


	@Column(name = "FOLLOW_TIME")
	private Date followTime;

	@Column(name = "OPEN", length = 1)
	private String open;

	@Column(name = "CURRENCY", length = 3)
	private String currency;

	@Column(name = "SELL", length = 1)
	private String sell;

	@Column(name = "APPOINT_INVESTOR", length = 100)
	private String appointInvestor;

	@Column(name = "REL_NAME", length = 20)
	private String relName;

	@Column(name = "REL_PHONE", length = 20)
	private String relPhone;

	@Column(name = "DESCRIPTION", length = 2000)
	private String description;

	@Column(name = "MULTI", length = 10)
	private String multi;

	@Column(name = "STATUS", length = 10)
	private String status;

	@Column(name = "OPERUSER", length = 10)
	private String operuser;

	@Column(name = "OPERDATE")
	private Date operdate;

	@Column(name = "FINACING_TURN", length = 3)
	private String finacingTurn;
	
	@Column(name = "update_flag")
	private int updateFlag;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CLOSE_REASON")
	private byte[] closeReason;// 关闭原因

	@Transient
	private String appointInvestorStr;// 指定投资机构名称集合

	@Column(name = "AMOUNTMIN")
	private Double amountMin;

	@Column(name = "AMOUNTMAX")
	private Double amountMax;

	@Column(name = "SCALEMIN", length = 4)
	private Double scaleMin;

	@Column(name = "SCALEMAX", length = 4)
	private Double scaleMax;

	@Column(name = "system_look")
	private String systemLook="1";//主管是否查看标识0为查看1为未查看
	
	@Column(name = "revoke_flag")
	private String revokeFlag="0";//主管是否撤销标识1为撤销0为未撤销
	
	@Transient
	private String revokeStr;
	
	@Transient
	private String amount;
	@Transient
	private String openStr;
	@Transient
	private String finacingTurnStr;
	@Transient
	private String sysLookStr;
	@Transient
	private String currencyStr;
	@Transient
	private String statusStr;
	@Transient
	private String enterpriseName;
	
	@Transient
	private String filePath;
	
	@Transient
	private String fileName;
	
	
	@Transient
	private String sellScale;// 融资金额与资金类型拼接的字符串

	public String getSellScale() {
		 DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
         df.applyPattern("#0.##");
		
		if ("0".equals(this.sell)) {
			String tmpScale = "原股权转让    ";
			if (this.scaleMin == null || "".equals(this.scaleMin)) {
				tmpScale += "0%";
			} else {
				tmpScale +=  df.format(this.scaleMin) + "%";
			}
			if (this.scaleMax == null || "".equals(this.scaleMax)) {
				tmpScale += "~" + "0%";
			} else {
				tmpScale += "~" + df.format(this.scaleMax) + "%";
			}
			return tmpScale;
		} else {
			String tmpScale = "增资扩股    ";
			if (this.scaleMin == null || "".equals(this.scaleMin)) {
				tmpScale += "0%";
			} else {
				tmpScale +=  df.format(this.scaleMin) + "%";
			}
			if (this.scaleMax == null || "".equals(this.scaleMax)) {
				tmpScale += "~" + "0%";
			} else {
				tmpScale += "~" + df.format(this.scaleMax) + "%";
			}
			return tmpScale;
		}
	}

	public void setSellScale(String sellScale) {
		this.sellScale = sellScale;
	}
	public String getOpenStr() {
		String openStr="";
		if("0".equals(this.open)){
			openStr="公开";
		}else{
			openStr="定投";
		}
		return openStr;
	}

	public void setOpenStr(String openStr) {
		this.openStr = openStr;
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

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getAppointInvestor() {
		return appointInvestor;
	}

	public void setAppointInvestor(String appointInvestor) {
		this.appointInvestor = appointInvestor;
	}

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRelPhone() {
		return relPhone;
	}

	public void setRelPhone(String relPhone) {
		this.relPhone = relPhone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getAmount() {
		
		 DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
         df.applyPattern("#0.##");
         
		String tmpAmount = "";
		if (this.amountMin == null || "".equals(this.amountMin)) {
			tmpAmount = "0";
		} else {
			tmpAmount =df.format(this.amountMin*100).toString();
		}
		if (this.amountMax == null || "".equals(this.amountMax)) {
			tmpAmount += "~" + "0";
		} else {
			tmpAmount += "~" + df.format(this.amountMax*100).toString();
		}
		return tmpAmount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
	public String getMulti() {
		return multi;
	}

	public void setMulti(String multi) {
		this.multi = multi;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperuser() {
		return operuser;
	}

	public void setOperuser(String operuser) {
		this.operuser = operuser;
	}

	public Date getOperdate() {
		return operdate;
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

	public String getFinacingTurn() {
		return finacingTurn;
	}

	public void setFinacingTurn(String finacingTurn) {
		this.finacingTurn = finacingTurn;
	}

	public int getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getAppointInvestorStr() {
		return appointInvestorStr;
	}

	public void setAppointInvestorStr(String appointInvestorStr) {
		this.appointInvestorStr = appointInvestorStr;
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

	public Double getScaleMin() {
		return scaleMin;
	}

	public void setScaleMin(Double scaleMin) {
		this.scaleMin = scaleMin;
	}

	public Double getScaleMax() {
		return scaleMax;
	}

	public void setScaleMax(Double scaleMax) {
		this.scaleMax = scaleMax;
	}

	public String getCloseReason() throws UnsupportedEncodingException {
		if (closeReason == null || "".equals(closeReason)) {
			return "";
		}
		return new String(closeReason, "utf-8");
	}

	public void setCloseReason(String closeReason) throws UnsupportedEncodingException {
		if (!"".equals(closeReason) && closeReason!=null) {
			this.closeReason = closeReason.getBytes("utf-8");
		} else {
			this.closeReason = null;
		}
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
     
	

	public String getSystemLook() {
		return systemLook;
	}

	public void setSystemLook(String systemLook) {
		this.systemLook = systemLook;
	}

	public String getRevokeFlag() {
		return revokeFlag;
	}

	public void setRevokeFlag(String revokeFlag) {
		this.revokeFlag = revokeFlag;
	}

	public String getSysLookStr() {
		if("0".equals(systemLook)){
			sysLookStr="已读";
		}else{
			sysLookStr="未读";
		}
		return sysLookStr;
	}

	public void setSysLookStr(String sysLookStr) {
		this.sysLookStr = sysLookStr;
	}

	public String getStatusStr() {
		if("1".equals(revokeFlag)){
			return  "撤回";
		}else if("2".equals(revokeFlag)){
			return  "待审核";
		}else{
			return  VipCont.getValueByCode("dd:demandstatus",this.status);
		}
		
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCurrencyStr() {
		return VipCont.getValueByCode("dd:currency",this.currency);
	}

	public void setCurrencyStr(String currencyStr) {
		this.currencyStr = currencyStr;
	}

	public String getFinacingTurnStr() {
		return VipCont.getValueByCode("dd:finacingturn",this.finacingTurn);
	}

	public void setFinacingTurnStr(String finacingTurnStr) {
		this.finacingTurnStr = finacingTurnStr;
	}

	public String getRevokeStr() {
		if("0".equals(this.revokeFlag)){
			revokeStr="未撤回";
		}else{
			revokeStr="撤回";
		}
		return revokeStr;
	}

	public void setRevokeStr(String revokeStr) {
		this.revokeStr = revokeStr;
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

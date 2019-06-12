package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

import io.netty.util.internal.StringUtil;

/**
 * 融资事件表，融资记录表的关联查询实体类
 * 
 * @author john
 *
 */
@Entity
public class QueryFinacingDemandResult implements Serializable {

	private static final long serialVersionUID = 9113100975887997775L;

	@Id
	@Column(name = "INFO_ID", length = 32)
	private String infoId;

	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;

	@Column(name = "PROJECT_NAME", length = 60)
	private String projectName;

	@Column(name = "AMOUNTMIN")
	private Double amountMin = 0.0;

	@Column(name = "AMOUNTMAX")
	private Double amountMax = 0.0;

	@Column(name = "CURRENCY", length = 3)
	private String currency;

	@Temporal(TemporalType.DATE)
	@Column(name = "FOLLOW_TIME")
	private Date followTime;

	@Column(name = "FINACING_TURN", length = 3)
	private String finacingTurn;

	@Column(name = "SELL", length = 1)
	private String sell;

	@Column(name = "OPEN", length = 1)
	private String open;
	
	@Column(name = "SCALEMIN", length = 10)
	private String scaleMin;

	@Column(name = "SCALEMAX", length = 10)
	private String scaleMax;

	@Column(name = "STATUS", length = 10)
	private String status;

	@Column(name = "update_flag", length = 10)
	private int updateFlag;
	
	@Column(name = "revoke_flag")
	private String revokeFlag;//主管是否撤销标识1为撤销0为未撤销
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CLOSE_REASON")
	private byte[] closeReason;// 关闭原因
	
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERDATE")
	private Date operdate;

	@Transient
	private String amountCurrency;// 投资金额拼接字符串

	@Transient
	private String remainTime;//剩余有效关注时间
	
	@Transient
	private String amount;
	
	@Transient
	private String publish;
	@Transient
	private String followTimeStr;
	
	@Transient
	private String openStr;
	
	@Transient
	private String publishFlag;//2018年1月10日 10:27:27   LIWCH   添加了一个是否能够发布的标志，1表示可以发布，0表示不能够进行发布
	
	@Transient
	private String copyShowFlag;// 复制按钮是否展示 0：展示；1：不展示
	
	
	@Transient
	private String changeOpenFlag;//是否超过有效关注时间的标志  0：超过了；1：没有超过
	
	@Transient
    private String amountShow;
    
	public String getAmountShow() {
		String tmpAmount = "";
		if (this.amountMin == null || "".equals(this.amountMin)) {
			tmpAmount = "0";
		} else {
			tmpAmount = DigitFormatUtil.digitFormat(this.amountMin*100).toString();
		}
		if (this.amountMax == null || "".equals(this.amountMax)) {
			tmpAmount += "~" + "0";
		} else {
			tmpAmount += "~"
					+ DigitFormatUtil.digitFormat(this.amountMax*100).toString()+" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY,
							this.currency);
		}
		return tmpAmount;
	}

	public void setAmountShow(String amountShow) {
        this.amountShow = amountShow;
	}
	
	
	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(String publishFlag) {
		this.publishFlag = publishFlag;
	}

	@Transient
	private String statusDicname;// 需求状态字符串

	@Transient
	private String finacingTurnDicname;// 融资轮次字符串

	@Transient
	private String sellScale;// 融资金额与资金类型拼接的字符串

	@Transient
	private String scale;

	public String getSellScale() {
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

	public void setSellScale(String sellScale) {
		this.sellScale = sellScale;
	}

	public String getAmountCurrency() {
		return "CNY  " + this.amount;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public String getFinacingTurnDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN,
				this.finacingTurn);
	}

	public void setFinacingTurnDicname(String finacingTurnDicname) {
		this.finacingTurnDicname = finacingTurnDicname;
	}

	public String getStatusDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_DEMANDSTATUS,
				this.status);
	}

	public void setStatusDicname(String statusDicname) {
		this.statusDicname = statusDicname;
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

	public String getAmount() {
		String tmpAmount = "";
		if (this.amountMin == null || "".equals(this.amountMin)) {
			tmpAmount = "0";
		} else {
			tmpAmount = DigitFormatUtil.digitFormat(this.amountMin).toString();
		}
		if (this.amountMax == null || "".equals(this.amountMax)) {
			tmpAmount += "~" + "0";
		} else {
			tmpAmount += "~"
					+ DigitFormatUtil.digitFormat(this.amountMax).toString()+" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY,
							this.currency);
		}
		return tmpAmount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getFollowTime() {
		    return this.followTime;
		
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

	public String getOperdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(this.operdate!=null){
			return sdf.format(this.operdate);

		}else{
			return "";

		}
	}

	public void setOperdate(Date operdate) {
		this.operdate = operdate;
	}

	public String getCopyShowFlag() {
		return copyShowFlag;
	}

	public void setCopyShowFlag(String copyShowFlag) {
		this.copyShowFlag = copyShowFlag;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getOpenStr() {
		String openString="";
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

	public String getChangeOpenFlag() {
		return changeOpenFlag;
	}

	public void setChangeOpenFlag(String changeOpenFlag) {
		this.changeOpenFlag = changeOpenFlag;
	}

	public String getFollowTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(this.followTime!=null){
			if(this.status.equals("00")){
				followTimeStr="";
			}else{
				followTimeStr=sdf.format(followTime);
			}
		}else{
			followTimeStr="长期";
		}
		return followTimeStr;
	}

	public void setFollowTimeStr(String followTimeStr) {
		this.followTimeStr = followTimeStr;
	}

	public int getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getRevokeFlag() {
		return revokeFlag;
	}

	public void setRevokeFlag(String revokeFlag) {
		this.revokeFlag = revokeFlag;
	}

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}
	public String getCloseReason() throws UnsupportedEncodingException {
		if (closeReason == null || "".equals(closeReason)) {
			return "";
		}
		return new String(closeReason, "utf-8");
	}

	public void setCloseReason(String closeReason) throws UnsupportedEncodingException {
		if (!StringUtil.isNullOrEmpty(closeReason)) {
			this.closeReason = closeReason.getBytes("utf-8");
		} else {
			this.closeReason = null;
		}

	}
}

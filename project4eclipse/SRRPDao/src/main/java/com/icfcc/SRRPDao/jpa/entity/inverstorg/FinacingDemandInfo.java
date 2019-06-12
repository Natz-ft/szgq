package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import io.netty.util.internal.StringUtil;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import com.icfcc.ssrp.util.DigitFormatUtil;

/**
 * 融资需求信息实体类
 */
@Entity
@Table(name = "rp_finacing_demand")
public class FinacingDemandInfo implements Serializable {

	private static final long serialVersionUID = 5655672544175384019L;

	@Id
	@Column(name = "INFO_ID", length = 32)
	// @GenericGenerator(name = "system-uuid", strategy = "uuid")
	// @GeneratedValue(generator = "system-uuid")
	private String infoId;

	@Column(name = "ENTERPRISE_ID", length = 32)
	private String enterpriseId;

	@Column(name = "PROJECT_NAME", length = 60)
	private String projectName;

	// @Column(name = "AMOUNT")
	// private Double amount;

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

	// @Column(name = "SCALE", length = 10)
	// private Double scale;

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
	private int updateFlag=0;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CLOSE_REASON")
	private byte[] closeReason;// 关闭原因

	@Column(name = "system_look")
	private String systemLook="1";//主管是否查看标识0为查看1为未查看
	
	@Column(name = "revoke_flag")
	private String revokeFlag="0";//主管是否撤销标识1为撤销0为未撤销
	
	@Transient
	private String finacingTurnDicname;// 融资轮次字符串

	@Transient
	private String openDicname;

	@Transient
	private String appointInvestorStr;// 指定投资机构名称集合

	@Transient
	private String multiDicname; // 是否制定多个投资机构
                      
	@Column(name = "AMOUNTMIN")
	private Double amountMin;

	@Column(name = "AMOUNTMAX")
	private Double amountMax;

	@Column(name = "amountCNYmin")
	private Double amountCNYMin;//美元兑换成人民币后的最小金额

	@Column(name = "amountCNYmax")
	private Double amountCNYMax;//美元兑换成人民币后的最大金额
	
	@Column(name = "SCALEMIN", length = 4)
	private Double scaleMin;

	@Column(name = "SCALEMAX", length = 4)
	private Double scaleMax;
	@Transient
	private Date operdateStr;
	@Transient
	private String amount;
	
	@Transient
	private String amountCNY;//美元兑换成人民币后的融资区间

	@Transient
	private String currencyDicname;

	@Transient
	private String statusDicname;// 需求状态字符串
	
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
			tmpAmount += "~" + DigitFormatUtil.digitFormat(this.amountMax*100).toString();
		}
		return tmpAmount;
	}

	public void setAmountShow(String amountShow) {
        this.amountShow = amountShow;
	}
	
	@Transient
    private String projectNameShow;
    
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
		return sb.toString().substring(0, sb.length()-1).replace("百万元", "万元");
	}

	public void setProjectNameShow(String projectNameShow) {
        this.projectNameShow = projectNameShow;
	}

	public String getStatusDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_DEMANDSTATUS, this.status);
	}

	public void setStatusDicname(String statusDicname) {
		this.statusDicname = statusDicname;
	}

	public String getMultiDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.multi);
	}

	public void setMultiDicname(String multiDicname) {
		this.multiDicname = multiDicname;
	}

	public String getAppointInvestorStr() {
		return appointInvestorStr;
	}

	public void setAppointInvestorStr(String appointInvestorStr) {
		this.appointInvestorStr = appointInvestorStr;
	}

	public String getOpenDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_RADIO, this.open);
	}

	public void setOpenDicname(String openDicname) {
		this.openDicname = openDicname;
	}

	@Transient
	private String sellScale;// 融资金额与资金类型拼接的字符串

	public String getSellScale() {
		if ("0".equals(this.sell)) {
			String tmpScale = "原股权转让   ";
			if (this.scaleMin == null || "".equals(this.scaleMin)) {
				tmpScale += "0%";
			} else {
				tmpScale += DigitFormatUtil.digitFormat(this.scaleMin) + "%";
			}
			if (this.scaleMax == null || "".equals(this.scaleMax)) {
				tmpScale += "~" + "0%";
			} else {
				tmpScale += "~" + DigitFormatUtil.digitFormat(this.scaleMax) + "%";
			}
			return tmpScale;
		} else {
			String tmpScale = "增资扩股     ";
			if (this.scaleMin == null || "".equals(this.scaleMin)) {
				tmpScale += "0%";
			} else {
				tmpScale += DigitFormatUtil.digitFormat(this.scaleMin) + "%";
			}
			if (this.scaleMax == null || "".equals(this.scaleMax)) {
				tmpScale += "~" + "0%";
			} else {
				tmpScale += "~" + DigitFormatUtil.digitFormat(this.scaleMax) + "%";
			}
			return tmpScale;
//			return "N/A";
		}
	}

	public void setSellScale(String sellScale) {
		this.sellScale = sellScale;
	}

	public String getFinacingTurnDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN, this.finacingTurn);
	}

	public void setFinacingTurnDicname(String finacingTurnDicname) {
		this.finacingTurnDicname = finacingTurnDicname;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Date getOperdateStr() {
		if(this.status.equals("00")){
			return null;
		}else{
			return operdate;
		}
	}

	public void setOperdateStr(Date operdateStr) {
		this.operdateStr = operdateStr;
	}

	public String getAppointInvestor() {
		if (StringUtil.isNullOrEmpty(this.appointInvestor)) {
			return "";
		} else {
			return appointInvestor;
		}
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

	public String getFinacingTurn() {
		return finacingTurn;
	}

	public void setFinacingTurn(String finacingTurn) {
		this.finacingTurn = finacingTurn;
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
			tmpAmount += "~" + DigitFormatUtil.digitFormat(this.amountMax).toString();
		}
		return tmpAmount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getAmountCNY() {
		String tmpAmount = "";
		if (this.amountCNYMin == null || "".equals(this.amountCNYMin)) {
			tmpAmount = "0";
		} else {
			tmpAmount = DigitFormatUtil.digitFormat(this.amountCNYMin).toString();
		}
		if (this.amountMax == null || "".equals(this.amountCNYMax)) {
			tmpAmount += "~" + "0";
		} else {
			tmpAmount += "~" + DigitFormatUtil.digitFormat(this.amountCNYMax).toString();
		}
		return tmpAmount;
	}

	public void setAmountCNY(String amountCNY) {
		this.amountCNY = amountCNY;
	}

	public String getCurrencyDicname() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
	}

	public void setCurrencyDicname(String currencyDicname) {
		this.currencyDicname = currencyDicname;
	}

	public int getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
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

	public Double getAmountCNYMin() {
		return amountCNYMin;
	}

	public void setAmountCNYMin(Double amountCNYMin) {
		this.amountCNYMin = amountCNYMin;
	}

	public Double getAmountCNYMax() {
		return amountCNYMax;
	}

	public void setAmountCNYMax(Double amountCNYMax) {
		this.amountCNYMax = amountCNYMax;
	}
    
	
	
}

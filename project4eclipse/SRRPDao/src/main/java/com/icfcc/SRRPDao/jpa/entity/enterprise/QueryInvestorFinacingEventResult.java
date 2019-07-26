package com.icfcc.SRRPDao.jpa.entity.enterprise;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author LiWCh 融资事件，融资需求，机构表联合查询
 *
 */
@Data
@Entity
public class QueryInvestorFinacingEventResult implements Serializable {
	/**
	 * * fe.event_id 事件的id fe.info_id 信息的id fe.investorg_id 机构的id fe.currency货币
	 * fe.maildate 投资时间 fe.amount金额 fe.operdate操作时间 fe.project_name项目名称
	 * e.name机构名称 e.finance_stage投资阶段 f.finacing_turn融资轮次 e.org_type 机构类型
	 */
	private static final long serialVersionUID = -844532751100253366L;

	/**
	 * 融资事件，融资需求，机构表联合查询
	 * 
	 */
	@Column(name = "info_id")
	private String infoId;;// 融资信息的id

	@Column(name = "name")
	private String investorName;// 投资机构名称

	@Column(name = "finance_stage")
	private String financeStage;// 拟投资阶段

	@Id
	@Column(name = "event_id")
	private String eventId;// 融资事件id

	@Column(name = "investorg_id")
	private String investorgId;// 机构的id

	@Temporal(TemporalType.DATE)
	@Column(name = "maildate")
	private Date maildate;// 投递时间

	@Column(name = "project_name")
	private String projectName;// 项目名称

	@Column(name = "amount")
	private String amount;// 金额

	@Column(name = "currency")
	private String currency;// 货币

	@Temporal(TemporalType.DATE)
	@Column(name = "operdate")
	private Date operdate;//

	@Column(name = "finacing_turn")
	private String finacingTurn;// 融资轮次

	@Column(name = "status")
	private String status;// 融资进度

	@Column(name = "open")
	private String open;// 是否公开

	@Column(name = "org_type")
	private String orgType;// 机构的类型

	@Transient
	private String orgTypeDicname;// 机构类型字符串

	@Transient
	private String financeStageDicname;// 拟投资阶段字符串

	@Transient
	private String finacingTurnDic;// 融资轮次的字符串

	@Transient
	private String openStr;// 融资轮次的字符串

	@Transient
	private DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
	
	@Transient
    private String amountShow;
    
	public String getAmountShow() {
		this.amountShow="";
		if(this.amount==null || "".equals(this.amount)){
			amountShow="0";
			return amountShow +" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
		}
		return this.amount +" "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
	}

	public void setAmountShow(String amountShow) {
        this.amountShow = amountShow;
	}
	 
	 
	public String getAmount() {
		Double sumAmountD=Math.round(Double.valueOf(this.amount)*1000000)/100000000.00000000;
		String sumAmountS="";
		if(sumAmountD.intValue()-sumAmountD==0){//判断是否符合取整条件
			sumAmountS=	String.valueOf(sumAmountD.intValue());
		}else{
			sumAmountS=String.valueOf(sumAmountD);
		}
        return sumAmountS  + "   "+RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.currency);
//		return  DigitFormatUtil.digitFormat(this.amount) + "   CNY";
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFinacingTurnDic() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_FINACINGTURN,
				this.finacingTurn);
	}

	public void setFinacingTurnDic(String finacingTurnDic) {
		this.finacingTurnDic = finacingTurnDic;
	}

	public void setFinanceStageDicname(String financeStageDicname) {
		this.financeStageDicname = financeStageDicname;
	}

	public String getFinanceStageDicname() {
		return financeStageDicname;
	}

	public void setOrgTypeDicname(String orgTypeDicname) {
		this.orgTypeDicname = orgTypeDicname;
	}

	public String getOrgTypeDicname() {
		String[] orgTypes = this.orgType.split(",");
		String typeNames = "";
		for (String type : orgTypes) {
			typeNames += RedisGetValue.getValueByCode(SRRPConstant.DD_ORGTYPE, type)+"、";
		}
		if(typeNames.contains("、")){
			typeNames = typeNames.substring(0,typeNames.length()-1);
		}
		return typeNames;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getFinanceStage() {
		return financeStage;
	}

	public void setFinanceStage(String financeStage) {
		this.financeStage = financeStage;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getInvestorgId() {
		return investorgId;
	}

	public void setInvestorgId(String investorgId) {
		this.investorgId = investorgId;
	}

	public Date getMaildate() {
		return maildate;
	}

	public void setMaildate(Date maildate) {
		this.maildate = maildate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOperdate() {
		if (this.operdate == null) {
			return " ";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(this.operdate);
		}
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

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
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

}

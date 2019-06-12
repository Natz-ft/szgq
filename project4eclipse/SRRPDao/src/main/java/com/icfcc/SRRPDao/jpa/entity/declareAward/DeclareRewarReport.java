package com.icfcc.SRRPDao.jpa.entity.declareAward;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

import lombok.Data;

/**
 * 奖励申报投资记录表
 * 
 * @ClassName: InvestorRewarDeclareReport
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hugt
 * @date 2018年3月15日 上午9:17:15
 *
 */
@Data
@Entity
@Table(name = "rp_declare_award_report")
public class DeclareRewarReport implements Serializable {
	private static final long serialVersionUID = -2329051787898556399L;
	@Id
	@Column(name = "report_id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String reportId;//奖励申报投资记录id

	@Column(name = "declare_id")
	private String declareId;// 奖励申报id

	
	@Column(name = "event_id")
	private String eventId;// 事件id
	
	@Column(name = "achievement_name")
	private String subacName;// 管理基金名称

	@Column(name = "amac_record")
	private String amacRecord;//基金编号

	@Column(name = "achievement_address")
	private String achievementAddress;//基金工商注册地

	@Column(name = "achievement_code")
	private String achievementCode;// 基金证件号

	@Column(name = "invested_enterprise")
	private String investedEnterprise;//被投企业名称

	@Column(name = "invested_enterprise_code")
	private String investedEnterpriseCode;// 被投企业机构代码


	@Column(name = "investment_amount")
	private String investmentAmount;// 投资金额

	@Column(name = "ia_currency")
	private String iaCurrency;// 投资金额币种

	@Column(name = "serial_number")
	private String serialNumber;// 序号

	@Column(name = "remarks")
	private String remarks;// 备注

	@Transient
	private String investmentAmountStr;
	
	
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getDeclareId() {
		return declareId;
	}

	public void setDeclareId(String declareId) {
		this.declareId = declareId;
	}

	public String getSubacName() {
		return subacName;
	}

	public void setSubacName(String subacName) {
		this.subacName = subacName;
	}

	public String getAmacRecord() {
		return amacRecord;
	}

	public void setAmacRecord(String amacRecord) {
		this.amacRecord = amacRecord;
	}

	public String getAchievementAddress() {
		return achievementAddress;
	}

	public void setAchievementAddress(String achievementAddress) {
		this.achievementAddress = achievementAddress;
	}

	public String getAchievementCode() {
		return achievementCode;
	}

	public void setAchievementCode(String achievementCode) {
		this.achievementCode = achievementCode;
	}

	public String getInvestedEnterprise() {
		return investedEnterprise;
	}

	public void setInvestedEnterprise(String investedEnterprise) {
		this.investedEnterprise = investedEnterprise;
	}

	public String getInvestedEnterpriseCode() {
		return investedEnterpriseCode;
	}

	public void setInvestedEnterpriseCode(String investedEnterpriseCode) {
		this.investedEnterpriseCode = investedEnterpriseCode;
	}

	public String getInvestmentAmount() {
		return investmentAmount;
	}

	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public String getIaCurrency() {
		return iaCurrency;
	}

	public void setIaCurrency(String iaCurrency) {
		this.iaCurrency = iaCurrency;
	}

	public String getInvestmentAmountStr() {
		if(iaCurrency!=null){
			investmentAmountStr=fmtMicrometer(String.valueOf(investmentAmount))+" "+
					RedisGetValue.getValueByCode(SRRPConstant.DD_CURRENCY, this.iaCurrency);
		}
		return investmentAmountStr;
	}

	public void setInvestmentAmountStr(String investmentAmountStr) {
		this.investmentAmountStr = investmentAmountStr;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

/** 
 * 格式化数字为千分位显示； 
 * @param 要格式化的数字； 
 * @return 
 */  
public static String fmtMicrometer(String text)  
{  
    DecimalFormat df = null;  
    if(text.indexOf(".") > 0)  
    {  
        if(text.length() - text.indexOf(".")-1 == 0)  
        {  
            df = new DecimalFormat("###,##0.");  
        }else if(text.length() - text.indexOf(".")-1 == 1)  
        {  
            df = new DecimalFormat("###,##0.0");  
        }else  
        {  
            df = new DecimalFormat("###,##0.00");  
        }  
    }else   
    {  
        df = new DecimalFormat("###,##0");  
    }  
    double number = 0.0;  
    try {  
         number = Double.parseDouble(text);  
    } catch (Exception e) {  
        number = 0.0;  
    }  
    return df.format(number);  
} 
	
}

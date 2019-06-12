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
 * 奖励申报放款明细表
 * 
 * @ClassName: InvestorRewarDeclareReport
 * @Description: TODO(奖励申报放款明细表)
 * @author hugt
 * @date 2018年3月15日 上午9:17:15
 *
 */
@Data
@Entity
@Table(name = "rp_declare_report_record")
public class DeclareRewarReportRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8518681463060950888L;

	@Id
	@Column(name = "record_id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String recordId;//奖励申报投资记录id

	@Column(name = "declare_id")
	private String declareId;// 奖励申报id

	@Column(name = "loan_id")
	private String loanId;// 奖励申报id
	
	@Column(name = "event_id")
	private String eventId;// 事件id
	
	@Column(name = "loandate")
	private Date loandate;// 事件id
	
	@Column(name = "investment_amount")
	private String investmentAmount;// 投资金额

	@Column(name = "ia_currency")
	private String iaCurrency;// 投资金额币种

	@Column(name = "declare_status")
	private String status;// 状态

	@Transient
	private String investmentAmountStr;


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	

	public String getRecordId() {
		return recordId;
	}


	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}



	public String getDeclareId() {
		return declareId;
	}


	public void setDeclareId(String declareId) {
		this.declareId = declareId;
	}


	public String getLoanId() {
		return loanId;
	}


	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}


	public String getEventId() {
		return eventId;
	}


	public void setEventId(String eventId) {
		this.eventId = eventId;
	}


	public Date getLoandate() {
		return loandate;
	}


	public void setLoandate(Date loandate) {
		this.loandate = loandate;
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
		return investmentAmountStr;
	}


	public void setInvestmentAmountStr(String investmentAmountStr) {
		this.investmentAmountStr = investmentAmountStr;
	}
	
	
	
}

package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.icfcc.SRRPDao.jpa.entity.inverstorg.FinacingDemandInfo;
import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 
 * @author john 融资事件表实体类
 */
@Entity
@Table(name = "rp_finacing_event_his")
public class FinacingEventHis {
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
	private Double amount;// 投资金额

	@Column(name = "mailuser")
	private String mailuser;// 投递人

	@Column(name = "maildate")
	private Date maildate;// 投递时间

	@Column(name = "OPERUSER")
	private String operuser;// 操作人

	@Temporal(TemporalType.DATE)
	@Column(name = "OPERDATE")
	private Date operdate;// 操作时间

	@Transient
	private String finacingStatusDicname;// 需求状态字典值

	@Transient
	private List<FinacingRecord> finacingRecordList;

	// @Transient
	// private JSON finacingRecordList;//融资纪录的JSON字符串
	// public JSON getFinacingRecordList() {
	// return finacingRecordList;
	// }
	//
	// public void setFinacingRecordList(JSON finacingRecordList) {
	// this.finacingRecordList = finacingRecordList;
	// }

	public List<FinacingRecord> getFinacingRecordList() {
		return finacingRecordList;
	}

	public void setFinacingRecordList(List<FinacingRecord> finacingRecordList) {
		this.finacingRecordList = finacingRecordList;
	}

	public String getFinacingStatusDicname() {
		switch (this.status) {
		case SRRPConstant.FINANCPHASE41:
			return RedisGetValue.getValueByCode(SRRPConstant.DD_FINANCPHASE,
					SRRPConstant.FINANCPHASE31);
		case SRRPConstant.FINANCPHASE42:
			return RedisGetValue.getValueByCode(SRRPConstant.DD_FINANCPHASE,
					SRRPConstant.FINANCPHASE31);
		case SRRPConstant.FINANCPHASE43:
			return RedisGetValue.getValueByCode(SRRPConstant.DD_FINANCPHASE,
					SRRPConstant.FINANCPHASE31);
		case SRRPConstant.FINANCPHASE44:
			return RedisGetValue.getValueByCode(SRRPConstant.DD_FINANCPHASE,
					SRRPConstant.FINANCPHASE31);
		case SRRPConstant.FINANCPHASE45:
			return RedisGetValue.getValueByCode(SRRPConstant.DD_FINANCPHASE,
					SRRPConstant.FINANCPHASE31);
		}
		return RedisGetValue.getValueByCode(SRRPConstant.DD_FINANCPHASE,
				this.status);
	}

	public void setFinacingStatusDicname(String finacingStatusDicname) {
		this.finacingStatusDicname = finacingStatusDicname;
	}

	@Transient
	private String investorName;// 投资机构名

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	// @ManyToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name = "info_id")
	@Transient
	private FinacingDemandInfo finacingInfo;

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
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

	public FinacingDemandInfo getFinacingInfo() {
		return finacingInfo;
	}

	public void setFinacingInfo(FinacingDemandInfo finacingInfo) {
		this.finacingInfo = finacingInfo;
	}

}

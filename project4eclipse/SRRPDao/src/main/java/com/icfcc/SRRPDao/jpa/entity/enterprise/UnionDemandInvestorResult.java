package com.icfcc.SRRPDao.jpa.entity.enterprise;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

import lombok.Data;

/**
 * 关于融资机构对需求的操作的记录
 * 
 * @author Administrator
 *
 */
@Data
@Entity
public class UnionDemandInvestorResult implements Serializable {

	/**
	 * 相关字段梳理
	 * 
	 * 有机构的id，机构的名称，需求的id，融资事件的list
	 */
	@Column(name = "info_id")
	private String infoId;;// 融资信息的id

	@Id
	@Column(name = "investorg_id")
	private String investorId;// 投资机构id

	@Column(name = "name")
	private String investorName;;// 投资机构名称

	@Column(name = "enterprise_id")
	private String enterpriseId;;// 企业id

	@Column(name = "event_id")
	private String eventId;// 融资事件id

	@Column(name = "status")
	private String status;// 融资事件id
	
	@Column(name = "open")
	private String open;// 融资事件id
	
	@Column(name = "publish_flag")
	private String publishFlag;//是否披露标志
	
	@Transient
	private List<FinacingEvent> finacingEventList;// 融资事件的list
	
	@Transient
	private List<FinacingRecord> finacingRecordList;
	
	@Transient
	private String finacingStatusDicname;// 需求状态字典值
	
	@Transient
	private String orgStatus;// 需求状态字典值
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getInvestorId() {
		return investorId;
	}

	public void setInvestorId(String investorId) {
		this.investorId = investorId;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public List<FinacingEvent> getFinacingEventList() {
		return finacingEventList;
	}

	public void setFinacingEventList(List<FinacingEvent> finacingEventList) {
		this.finacingEventList = finacingEventList;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public List<FinacingRecord> getFinacingRecordList() {
		return finacingRecordList;
	}

	public void setFinacingRecordList(List<FinacingRecord> finacingRecordList) {
		this.finacingRecordList = finacingRecordList;
	}

	public String getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(String publishFlag) {
		this.publishFlag = publishFlag;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getOrgStatus() {
		switch (this.orgStatus) {
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
				this.orgStatus);
	}

	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}
	
}

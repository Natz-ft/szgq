package com.icfcc.SRRPDao.jpa.entity.gataway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 */
@Entity
@Table(name = "platform_portal_finacing_event_investor")
public class GataWayEventQueryInvestor implements java.io.Serializable
{

	private static final long serialVersionUID = 3903684524819260188L;
	@Id
	@Column(name = "id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	
	@Column(name="info_id")
	private String infoId;
	
	@Column(name = "investorname")
	private String investorName;

	@Column(name = "event_id")
	private String eventId;

	@Column(name = "amount", columnDefinition = "decimal(15,2)")
	private double amount;

	@Column(name = "org_type")
	private String orgType;

	@Column(name = "reserve")
	private String reserve;
	@Transient
	private String orgTypeStr;// 机构类型字典值
	@Transient
	private String reserveStr;// 机构类型字典值
	@Transient
	private String investorNameStr;// 机构类型字典值

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getInvestorNameStr()
	{
		return investorNameStr;
	}

	public void setInvestorNameStr(String investorNameStr)
	{
		this.investorNameStr = investorNameStr;
	}

	public String getReserveStr()
	{
		return RedisGetValue.getValueByCode(SRRPConstant.DD_CAPITALTYPE, this.reserve);
	}

	public void setReserveStr(String reserveStr)
	{
		this.reserveStr = reserveStr;
	}

	public String getOrgTypeStr()
	{
		return RedisGetValue.getValueByCode(SRRPConstant.DD_ORGTYPE, this.orgType);
	}

	public void setOrgTypeStr(String orgTypeStr)
	{
		this.orgTypeStr = orgTypeStr;
	}

	public String getInvestorName()
	{
		return investorName;
	}

	public void setInvestorName(String investorName)
	{
		this.investorName = investorName;
	}

	public String getEventId()
	{
		return eventId;
	}

	public void setEventId(String eventId)
	{
		this.eventId = eventId;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public String getOrgType()
	{
		return orgType;
	}

	public void setOrgType(String orgType)
	{
		this.orgType = orgType;
	}

	public String getReserve()
	{
		return reserve;
	}

	public void setReserve(String reserve)
	{
		this.reserve = reserve;
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getInfoId()
	{
		return infoId;
	}

	public void setInfoId(String infoId)
	{
		this.infoId = infoId;
	}
	
}
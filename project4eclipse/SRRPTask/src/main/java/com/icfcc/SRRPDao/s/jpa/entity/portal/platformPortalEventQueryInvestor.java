package com.icfcc.SRRPDao.s.jpa.entity.portal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_finacing_event_investor")
public class platformPortalEventQueryInvestor implements java.io.Serializable {

    private static final long serialVersionUID = 3903684524819260188L;
    @Id
	@Column(name = "id")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
    private String id;
    
    @Column(name = "event_id")
    private String eventId;
    
    @Column(name = "info_id")
    private String infoId;
    
    @Column(name = "investorname")
    private String investorName;
    
    @Column(name = "amount")
    private Double amount;

    @Column(name = "org_type")
    private String orgType;

    @Column(name = "reserve")
    private String reserve;
    
    public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
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
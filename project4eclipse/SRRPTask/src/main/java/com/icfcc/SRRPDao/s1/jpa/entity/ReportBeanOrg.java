package com.icfcc.SRRPDao.s1.jpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 企业上榜统计bean
 * 
 * @author lijj
 *
 */
@Entity
@Table(name = "rp_report_org")
public class ReportBeanOrg implements Serializable, Comparable<ReportBeanOrg> {

	private static final long serialVersionUID = 4151322816041435070L;

	@Id
	@Column(name = "rid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String rid;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "time_id")
	private String timeId;

	@Column(name = "rank_count")
	private Long rankCount;

	@Column(name = "time_point")
	private Date timePoint;
	
	@Column(name = "create_time")
	private Date createTime;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTimeId() {
		return timeId;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}

	public Long getRankCount() {
		return rankCount;
	}

	public void setRankCount(Long rankCount) {
		this.rankCount = rankCount;
	}

	public Date getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public int compareTo(ReportBeanOrg o) {
		return o.getAmount().compareTo(this.getAmount());
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

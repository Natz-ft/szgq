package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 融资统计bean
 * @author lijj
 *
 */
@Entity
@Table(name = "platform_portal_report_total")
public class PReportBeanTotal implements Serializable {

	private static final long serialVersionUID = -6685808204732933012L;

	@Id
	@Column(name = "rid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String rId;

	@Column(name = "time_id")
	private String timeId;
	@Column(name = "demand_amount")
	private BigDecimal demandAmount=new BigDecimal(0);
	@Column(name = "loan_amount")
	private BigDecimal loanAmount=new BigDecimal(0);
	@Column(name = "org_count")
	private Long orgCount=0L;
	@Column(name = "enterprise_count")
	private Long enterpriseCount=0L;
	@Column(name = "area")
	private String area;
	@Column(name = "create_time")
	private Date createTime;

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getTimeId() {
		return timeId;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}

	public BigDecimal getDemandAmount() {
		return demandAmount;
	}

	public void setDemandAmount(BigDecimal demandAmount) {
		this.demandAmount = demandAmount;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Long getOrgCount() {
		return orgCount;
	}

	public void setOrgCount(Long orgCount) {
		this.orgCount = orgCount;
	}

	public Long getEnterpriseCount() {
		return enterpriseCount;
	}

	public void setEnterpriseCount(Long enterpriseCount) {
		this.enterpriseCount = enterpriseCount;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

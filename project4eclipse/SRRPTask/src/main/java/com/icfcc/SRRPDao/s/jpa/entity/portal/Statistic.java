/**
 * 
 */
package com.icfcc.SRRPDao.s.jpa.entity.portal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lijj
 * 
 */
@Entity
@Table(name = "platform_portal_statictis")
public class Statistic implements Serializable {

	private static final long serialVersionUID = 7466945534159088519L;
	@Id
	@Column(name = "countdate")
	private String countDate;//统计时间
	@Column(name = "finaccount")
	private Double financingAmount;//融资总额
	@Column(name = "enterprisecount")
	private Long enterpriseCount;//企业数量
	@Column(name = "investorcount")
	private Long orgCount;//投资机构数量
	@Column(name = "demondcount")
	private Long financingCount;//需求总额

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public Double getFinancingAmount() {
		return financingAmount;
	}

	public void setFinancingAmount(Double financingAmount) {
		this.financingAmount = financingAmount;
	}

	public Long getEnterpriseCount() {
		return enterpriseCount;
	}

	public void setEnterpriseCount(Long enterpriseCount) {
		this.enterpriseCount = enterpriseCount;
	}

	public Long getOrgCount() {
		return orgCount;
	}

	public void setOrgCount(Long orgCount) {
		this.orgCount = orgCount;
	}

	public Long getFinancingCount() {
		return financingCount;
	}

	public void setFinancingCount(Long financingCount) {
		this.financingCount = financingCount;
	}

}

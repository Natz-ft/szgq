package com.icfcc.SRRPDao.jpa.entity.managedept;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 融资统计中平台企业数 机构数 用户数整合实体类
 * @author Administrator
 *
 */
@Entity
public class FinacingStatisticsNomberResult implements Serializable {

	private static final long serialVersionUID = 7679865253093007789L;

	@Id
	@Column(name = "id")
	private String id;

	@Transient
	private String companyNumber;

	@Transient
	private String investorNumber;

	@Transient
	private String userNumber;

	@Transient
	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	public String getInvestorNumber() {
		return investorNumber;
	}

	public void setInvestorNumber(String investorNumber) {
		this.investorNumber = investorNumber;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

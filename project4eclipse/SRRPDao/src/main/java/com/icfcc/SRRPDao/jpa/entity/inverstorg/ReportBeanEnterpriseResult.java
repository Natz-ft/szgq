package com.icfcc.SRRPDao.jpa.entity.inverstorg;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ReportBeanEnterpriseResult implements Serializable {

	private static final long serialVersionUID = -3027739633946431792L;
     
	@Id
	@Column(name = "enterprise_id")
	private String enterpriseId; // 企业ID

	@Column(name = "name")
	private String name; // 企业名称

	@Transient
	private String finacingTurn; // 融资轮次

	@Column(name = "cou_amount")
	private Double couAmount; // 融资总金额

	@Column(name = "cou")
	private Integer cou; // 上榜次数

	@Column(name = "rank")
	private Integer rank; // 名次

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFinacingTurn() {
		return finacingTurn;
	}

	public void setFinacingTurn(String finacingTurn) {
		this.finacingTurn = finacingTurn;
	}

	public Double getCouAmount() {
		return couAmount;
	}

	public void setCouAmount(Double couAmount) {
		this.couAmount = couAmount;
	}

	public Integer getCou() {
		return cou;
	}

	public void setCou(Integer cou) {
		this.cou = cou;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}

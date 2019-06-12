package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
public class QueryIndustryFinacingResult implements Serializable {

	private static final long serialVersionUID = -7402958804101028504L;
	@Id
	@Column(name = "industry")
	private String industry; // 行业

	@Column(name = "publish_count")
	private double publishCount; // 行业发布需求总数

	@Column(name = "publish_amount")
	private double publishAmount; // 行业融资总数融资总额

	@Column(name = "okcount")
	private double oKCount; // 行业解决需求总数

	@Column(name = "okamount")
	private double oKAmount; // 行业解决融资总金额

	@Transient
	private String industryDicname;// 融资轮次字符串

	@Transient
	private String industry2;// 二级行业
	
	@Transient
	private String industry2Dicname;// 二级行业字典值
	
	@Transient
	private String industryStr;// 行业展示字段
	
	 @Column(name = "rank")
	 private int rank; //根据行业就觉总数的排名

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public double getPublishCount() {
		return publishCount;
	}

	public void setPublishCount(double publishCount) {
		this.publishCount = publishCount;
	}

	public double getPublishAmount() {
		return publishAmount;
	}

	public void setPublishAmount(double publishAmount) {
		this.publishAmount = publishAmount;
	}

	public double getoKCount() {
		return oKCount;
	}

	public void setoKCount(double oKCount) {
		this.oKCount = oKCount;
	}

	public double getoKAmount() {
		return oKAmount;
	}

	public void setoKAmount(double oKAmount) {
		this.oKAmount = oKAmount;
	}

	public String getIndustryDicname() {
		return industryDicname;
	}

	public void setIndustryDicname(String industryDicname) {
		this.industryDicname = industryDicname;
	}

	 public int getRank() {
	 return rank;
	 }
	
	 public void setRank(int rank) {
	 this.rank = rank;
	 }

	public String getIndustry2() {
		return industry2;
	}

	public void setIndustry2(String industry2) {
		this.industry2 = industry2;
	}

	public String getIndustry2Dicname() {
		return industry2Dicname;
	}

	public void setIndustry2Dicname(String industry2Dicname) {
		this.industry2Dicname = industry2Dicname;
	}

	public String getIndustryStr() {
		return industryStr;
	}

	public void setIndustryStr(String industryStr) {
		this.industryStr = industryStr;
	}

}

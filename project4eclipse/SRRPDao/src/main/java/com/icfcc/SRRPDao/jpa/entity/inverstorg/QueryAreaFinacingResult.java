package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Data
public class QueryAreaFinacingResult implements Serializable {

	private static final long serialVersionUID = 1888626413867642555L;

	@Id
	@Column(name = "area")
	private String area; // 区域

	@Column(name = "rank")
	private int rank; // 根据区域根据区域融资总金额

	@Column(name = "publish_count")
	private double publishCount; // 区域发布需求总数

	@Column(name = "publish_amount")
	private double publishAmount; // 区域融资总数融资总额

	@Column(name = "okcount")
	private double oKCount; // 区域解决需求总数

	@Column(name = "okamount")
	private double oKAmount; // 区域解决融资总金额

	@Transient
	private String areaDicname;// 融资轮次字符串

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getAreaDicname() {
		return areaDicname;
	}

	public void setAreaDicname(String areaDicname) {
		this.areaDicname = areaDicname;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}

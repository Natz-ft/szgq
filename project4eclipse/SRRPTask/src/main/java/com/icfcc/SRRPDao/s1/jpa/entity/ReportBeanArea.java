package com.icfcc.SRRPDao.s1.jpa.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 企业投资实时报表对象
 * 
 * @author lijj
 *
 */
@Entity
@Table(name = "rp_report_area_rank")
public class ReportBeanArea implements Serializable, Comparable<ReportBeanArea> {

	private static final long serialVersionUID = -9175709708205009874L;
	private static final DecimalFormat formatter = new DecimalFormat("#.###");
	// private static final DecimalFormat formatter1 = new
	// DecimalFormat("#.###");

	@Id
	@Column(name = "rid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String rid;
	@Column(name = "time_id")
	private String timeId;
	@Column(name = "time_point")
	private Date timePoint;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "area")
	private String area;// 地区
	@Column(name = "amount")
	private Double amount;// 统计总金额
	@Column(name = "ratio")
	private Double ratio=0d;
	@Transient
	private Double allAmount=0d;

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

	public Double getRatio() {
//		if (this.allAmount != 0 && allAmount != null) {
//			return this.amount / this.allAmount;
//		} else {
			return 0d;
//		}
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public void setAllAmount(Double allAmount) {
		this.allAmount = allAmount;
	}

	public Double getAllAmount() {
		return allAmount;
	}

	public String getRatioString() {
		return formatter.format(getRatio());
	}

	public String getAmountString() {
		return formatter.format(getAmount() / 1000000);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public static void main(String[] arg) {
		System.out.println(100.23123 / 34.33);
	}

	@Override
	public int compareTo(ReportBeanArea o) {
		// TODO Auto-generated method stub
		return o.getAmount().compareTo(this.getAmount());
	}

	public String getTimeId() {
		return timeId;
	}

	public void setTimeId(String timeId) {
		this.timeId = timeId;
	}

	public Date getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(Date timePoint) {
		this.timePoint = timePoint;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

package com.icfcc.SRRPDao.jpa.entity.inverstorg;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 统计报表根据区域排名实体类
 * 
 * @author Liwc
 *
 */
@Entity
@Table(name = "rp_report_area")
public class ReportByArea implements Serializable {

	private static final long serialVersionUID = 2735667002429373218L;

	@Id
	@Column(name = "rid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String rid; // 主键id

	@Column(name = "area")
	private String area; // 所统计的区域

	@Column(name = "amount")
	private double amount; // 融资金额总值

	@Column(name = "demandnum")
	private double demandnum; // 需求数
	
	@Column(name = "status")
	private String status; // 需求状态
	/**
	 * 2018年1月5日 10:59:29   李万闯根据数据库添加字段   demandnum和status
	 */
	@Column(name = "time_id")
	private String timeId; // 时间的编号

	@Column(name = "time_point")
	private Date timePoint; // 时间点

	@Column(name = "rank")
	private String rank; // 名次

	@Column(name = "create_time")
	private Date CreateTime; // 创建时间

	@Transient
	private String areaDicName;// 取区域的字典值

	public String getAreaDicName() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_AREA, this.area);
	}

	public void setAreaDicName(String areaDicName) {
		this.areaDicName = areaDicName;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public double getAmount() {
		return amount;
	}

	public double getDemandnum() {
		return demandnum;
	}

	public void setDemandnum(double demandnum) {
		this.demandnum = demandnum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}

}

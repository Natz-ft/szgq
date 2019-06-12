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
 * 平台企业行业排名
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "rp_report_industry")
public class ReportByIndustry implements Serializable {

	private static final long serialVersionUID = -6665998680020465840L;

	@Id
	@Column(name = "rid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String rid;            //序号

	@Column(name = "industry")
	private String industry;       //行业

	@Column(name = "amount")
	private double amount;         //融资金额

	@Column(name = "demandnum")
	private double demandnum; // 需求数
	
	@Column(name = "status")
	private String status; // 需求状态
	/**
	 * 2018年1月5日 10:59:29   李万闯根据数据库添加字段   demandnum和status
	 */
	
	@Column(name = "time_id")
	private String timeId;         //时间编号

	@Column(name = "time_point")
	private Date timePoint;        //时间点

	@Column(name = "rank")
	private int rank;             //排名

	@Column(name = "create_time")
	private Date createTime;           //创建时间

	@Transient
	private String industryDicName;
	
	public String getIndustryDicName() {
		return RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY, this.industry);
	}

	public void setIndustryDicName(String industryDicName) {
		this.industryDicName = industryDicName;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

package com.icfcc.SRRPDao.jpa.entity.enterprise;

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

import com.icfcc.credit.platform.util.SRRPConstant;
import com.icfcc.ssrp.session.RedisGetValue;

/**
 * 企业投资实时报表对象
 * 
 * @author lijj
 *
 */
@Entity
@Table(name = "rp_report_industry")
public class ReportBeanTrade implements Serializable, Comparable<ReportBeanTrade> {

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
	@Column(name = "industry")
	private String industry;// 行业
	@Transient
	private String tradeDicname;// 行业字典值字符串
	@Column(name = "amount")
	private BigDecimal amount=new  BigDecimal(0);// 统计总金额
	@Transient
	private BigDecimal ratio=new BigDecimal(0);
	@Transient
	private Double allAmount;
	@Transient
	private Integer rank;
	
	public String getTradeDicname() {
		String tradeDicname =  RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY,this.industry);
		if(tradeDicname==null || tradeDicname=="--" || tradeDicname==""){
			tradeDicname=RedisGetValue.getValueByCode(SRRPConstant.DD_INDUSTRY_2,this.industry);
		}
		return tradeDicname;
	}

	public void setTradeDicname(String tradeDicname) {
		this.tradeDicname = tradeDicname;
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

    public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		if(amount==null){
			this.amount=new BigDecimal(0d);
		}
		this.amount = amount;
	}

	public BigDecimal getRatio() {
			return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

	public void setAllAmount(Double allAmount) {
		if(allAmount==null){
			this.allAmount=0d;
		}
		this.allAmount = allAmount;
		if (this.allAmount != 0 && allAmount != null) {
			this.ratio=this.getAmount().divide(new BigDecimal(this.allAmount),4, BigDecimal.ROUND_HALF_EVEN);
			if(this.ratio.compareTo(new BigDecimal(0))==0){
				this.ratio=this.getAmount().divide(new BigDecimal(this.allAmount),10, BigDecimal.ROUND_HALF_EVEN);
			}
		}
	}

	public Double getAllAmount() {
		return allAmount;
	}

	public String getRatioString() {
		return formatter.format(getRatio());
	}

	public String getAmountString() {
		return formatter.format(getAmount().doubleValue() );
	}

	public static void main(String[] arg) {
		System.out.println(100.23123 / 34.33);
	}

	@Override
	public int compareTo(ReportBeanTrade o) {
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

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}

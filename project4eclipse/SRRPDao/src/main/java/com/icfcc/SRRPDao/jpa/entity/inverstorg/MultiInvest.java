package com.icfcc.SRRPDao.jpa.entity.inverstorg;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 指定多个投资机构返回值实体类
 * @author Daiyx
 *
 */
@Entity
public class MultiInvest implements Serializable{

	private static final long serialVersionUID = 5129372721004294350L;
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String followId;
	
	@Transient
	private String eventId;//事件id
	
	@Transient
	private String infoId;//需求id
	
	@Transient
	private String status;//进度
	
	@Transient
	private String multi;//是否制定多个投资机构
	
	@Transient
	private Double amount;//投资机构投资金额
	@Transient
	private String currency;//投资币种
	@Transient
	private String followName1;//跟投1机构
	@Transient
	private String followName2;//跟投2机构
	@Transient
	private String followName3;//跟投3机构
	@Transient
	private String followName4;//跟投4机构
	@Transient
	private String followName5;//跟投5机构
	@Transient
	private Double amount1;//跟投1投资金额
	@Transient
	private Double amount2;//跟投2投资金额
	@Transient
	private Double amount3;//跟投3投资金额
	@Transient
	private Double amount4;//跟投4投资金额
	@Transient
	private Double amount5;//跟投5投资金额
	@Transient
	private String currency1;//跟投1币种
	@Transient
	private String currency2;//跟投2币种
	@Transient
	private String currency3;//跟投3币种
	@Transient
	private String currency4;//跟投4币种
	@Transient
	private String currency5;//跟投5币种
	
	
	public String getFollowId() {
		return followId;
	}
	public void setFollowId(String followId) {
		this.followId = followId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMulti() {
		return multi;
	}
	public void setMulti(String multi) {
		this.multi = multi;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getFollowName1() {
		return followName1;
	}
	public void setFollowName1(String followName1) {
		this.followName1 = followName1;
	}
	public String getFollowName2() {
		return followName2;
	}
	public void setFollowName2(String followName2) {
		this.followName2 = followName2;
	}
	public String getFollowName3() {
		return followName3;
	}
	public void setFollowName3(String followName3) {
		this.followName3 = followName3;
	}
	public String getFollowName4() {
		return followName4;
	}
	public void setFollowName4(String followName4) {
		this.followName4 = followName4;
	}
	public String getFollowName5() {
		return followName5;
	}
	public void setFollowName5(String followName5) {
		this.followName5 = followName5;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getAmount1() {
		return amount1;
	}
	public void setAmount1(Double amount1) {
		this.amount1 = amount1;
	}
	public Double getAmount2() {
		return amount2;
	}
	public void setAmount2(Double amount2) {
		this.amount2 = amount2;
	}
	public Double getAmount3() {
		return amount3;
	}
	public void setAmount3(Double amount3) {
		this.amount3 = amount3;
	}
	public Double getAmount4() {
		return amount4;
	}
	public void setAmount4(Double amount4) {
		this.amount4 = amount4;
	}
	public Double getAmount5() {
		return amount5;
	}
	public void setAmount5(Double amount5) {
		this.amount5 = amount5;
	}
	public String getCurrency1() {
		return currency1;
	}
	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}
	public String getCurrency2() {
		return currency2;
	}
	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}
	public String getCurrency3() {
		return currency3;
	}
	public void setCurrency3(String currency3) {
		this.currency3 = currency3;
	}
	public String getCurrency4() {
		return currency4;
	}
	public void setCurrency4(String currency4) {
		this.currency4 = currency4;
	}
	public String getCurrency5() {
		return currency5;
	}
	public void setCurrency5(String currency5) {
		this.currency5 = currency5;
	}
	
}

package com.icfcc.SRRPDao.jpa.entity.gataway.staticize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

/**
 */
@Data
@Entity
@Table(name = "platform_portal_statisbytype")
public class GataWayStaticResult implements java.io.Serializable {

	private static final long serialVersionUID = 3903684524819260188L;

	@Id
	@Column(name = "id", length = 32)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	/*
	 * 统计类型01：发布融资总额；02：解决融资总额；03：正在融资总额；04：平台企业家数；05：平台机构数；06：月度融资统计
	 * ；07：平台机构用户数
	 */
	@Column(name = "statictype")
	private String staticType;

	@Column(name = "zhangjiagang")
	public String zhangJiaGang = "0";

	@Column(name = "changshu")
	public String changShu = "0";

	@Column(name = "taicang")
	private String taiCang = "0";

	@Column(name = "kunshan")
	private String kunShan = "0";

	@Column(name = "xiangcheng")
	private String xiangCheng = "0";

	@Column(name = "gongyeyuan")
	private String gongYeYuan = "0";

	@Column(name = "gusu")
	private String guSu = "0";

	@Column(name = "gaoxin")
	private String gaoXin = "0";

	@Column(name = "wuzhong")
	private String wuZhong = "0";

	@Column(name = "wujiang")
	private String wuJiang = "0";
	@Column(name = "other")
	private String other = "0";
	@Column(name = "total")
	private String total;
	 @Column(name = "monthly")
	    private String monthly;
	public String getStaticType() {
		return staticType;
	}

	public void setStaticType(String staticType) {
		this.staticType = staticType;
	}

	public String getZhangJiaGang() {
		return zhangJiaGang;
	}

	public void setZhangJiaGang(String zhangJiaGang) {
		this.zhangJiaGang = zhangJiaGang;
	}

	public String getChangShu() {
		return changShu;
	}

	public void setChangShu(String changShu) {
		this.changShu = changShu;
	}

	public String getTaiCang() {
		return taiCang;
	}

	public void setTaiCang(String taiCang) {
		this.taiCang = taiCang;
	}

	public String getKunShan() {
		return kunShan;
	}

	public void setKunShan(String kunShan) {
		this.kunShan = kunShan;
	}

	public String getXiangCheng() {
		return xiangCheng;
	}

	public void setXiangCheng(String xiangCheng) {
		this.xiangCheng = xiangCheng;
	}

	public String getGongYeYuan() {
		return gongYeYuan;
	}

	public void setGongYeYuan(String gongYeYuan) {
		this.gongYeYuan = gongYeYuan;
	}

	public String getGuSu() {
		return guSu;
	}

	public void setGuSu(String guSu) {
		this.guSu = guSu;
	}

	public String getGaoXin() {
		return gaoXin;
	}

	public void setGaoXin(String gaoXin) {
		this.gaoXin = gaoXin;
	}

	public String getWuZhong() {
		return wuZhong;
	}

	public void setWuZhong(String wuZhong) {
		this.wuZhong = wuZhong;
	}

	public String getWuJiang() {
		return wuJiang;
	}

	public void setWuJiang(String wuJiang) {
		this.wuJiang = wuJiang;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	

	public String getMonthly() {
		return monthly;
	}

	public void setMonthly(String monthly) {
		this.monthly = monthly;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
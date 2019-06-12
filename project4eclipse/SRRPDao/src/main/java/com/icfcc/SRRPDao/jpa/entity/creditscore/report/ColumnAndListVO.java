package com.icfcc.SRRPDao.jpa.entity.creditscore.report;
import java.util.List;


public class ColumnAndListVO implements ICapVO{
/**
	 * 
	 */
	private static final long serialVersionUID = -3003291005422033534L;
public String current_year;
public String last_year;
public String before_last_year;
public String g_current_max_month_1;
//水电气
public List<SDQVo> sdqvo; 
//海关进出口
public List<HGVo> hgjck;

//纳税信息
public List<NSVo> nsvo;

//主要财务指标信息
public List<CWZBVo> cwzbvo;
/**
 * 水
 */
public List<SDQVo> svo;
/**
 * 电
 */
public List<SDQVo> dvo;
/**
 * 气
 */
public List<SDQVo> qvo;

public String getCurrent_year() {
	return current_year;
}

public void setCurrent_year(String current_year) {
	this.current_year = current_year;
}

public String getLast_year() {
	return last_year;
}

public void setLast_year(String last_year) {
	this.last_year = last_year;
}

public String getBefore_last_year() {
	return before_last_year;
}

public void setBefore_last_year(String before_last_year) {
	this.before_last_year = before_last_year;
}

public List<SDQVo> getSdqvo() {
	return sdqvo;
}

public void setSdqvo(List<SDQVo> sdqvo) {
	this.sdqvo = sdqvo;
}

public List<HGVo> getHgjck() {
	return hgjck;
}

public void setHgjck(List<HGVo> hgjck) {
	this.hgjck = hgjck;
}

public List<NSVo> getNsvo() {
	return nsvo;
}

public void setNsvo(List<NSVo> nsvo) {
	this.nsvo = nsvo;
}

public List<CWZBVo> getCwzbvo() {
	return cwzbvo;
}

public void setCwzbvo(List<CWZBVo> cwzbvo) {
	this.cwzbvo = cwzbvo;
}

public String getG_current_max_month_1() {
	return g_current_max_month_1;
}

public void setG_current_max_month_1(String g_current_max_month_1) {
	this.g_current_max_month_1 = g_current_max_month_1;
}

public List<SDQVo> getSvo() {
	return svo;
}
/**
 * 水
 * @param svo
 */
public void setSvo(List<SDQVo> svo) {
	this.svo = svo;
}

public List<SDQVo> getDvo() {
	return dvo;
}
/**
 * 电
 * @param dvo
 */
public void setDvo(List<SDQVo> dvo) {
	this.dvo = dvo;
}

public List<SDQVo> getQvo() {
	return qvo;
}
/**
 * 气
 * @param qvo
 */
public void setQvo(List<SDQVo> qvo) {
	this.qvo = qvo;
}



}

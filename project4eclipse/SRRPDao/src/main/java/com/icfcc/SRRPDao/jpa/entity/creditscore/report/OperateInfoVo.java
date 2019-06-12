package com.icfcc.SRRPDao.jpa.entity.creditscore.report;

import java.util.HashMap;
import java.util.Map;


public class OperateInfoVo implements ICapVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6375142149662963684L;

	private Map columnMap = new HashMap(); 
	
	private String  index;
	private String  cn_name;
	private String  before_last_qmye;
	private String  last_qmye;
	private String  current_qmye;
	/***************************2017-05-24***********************************/
	private String last_time_qmye;
	public Map getColumnMap() {
		return columnMap;
	}
	public void setColumnMap(Map columnMap) {
		this.columnMap = columnMap;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getCn_name() {
		return cn_name;
	}
	public void setCn_name(String cn_name) {
		this.cn_name = cn_name;
	}
	public String getBefore_last_qmye() {
		return before_last_qmye;
	}
	public void setBefore_last_qmye(String before_last_qmye) {
		this.before_last_qmye = before_last_qmye;
	}
	public String getLast_qmye() {
		return last_qmye;
	}
	public void setLast_qmye(String last_qmye) {
		this.last_qmye = last_qmye;
	}
	public String getCurrent_qmye() {
		return current_qmye;
	}
	public void setCurrent_qmye(String current_qmye) {
		this.current_qmye = current_qmye;
	}
	public String getLast_time_qmye() {
		return last_time_qmye;
	}
	public void setLast_time_qmye(String last_time_qmye) {
		this.last_time_qmye = last_time_qmye;
	}
	
	
}

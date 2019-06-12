package com.icfcc.credit.platform.jpa.entity.system;

import java.util.Date;

public class IndustryVo implements java.io.Serializable {

	private static final long serialVersionUID = 3903684524819260188L;

	// 每页最大记录数
	public String id ;
	// 首页
	private String pId;
	// 上一页
	private String name;
	// 下一页
	private boolean checked=false;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	

	
}
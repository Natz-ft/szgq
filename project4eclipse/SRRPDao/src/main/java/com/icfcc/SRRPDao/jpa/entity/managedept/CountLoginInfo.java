package com.icfcc.SRRPDao.jpa.entity.managedept;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
*
 */
@Entity
public class CountLoginInfo implements Serializable {

	private static final long serialVersionUID = -2236482709539842485L;

	@Id
	@Column(name = "user_name")
	private String userName; //

	@Column(name = "area")
	private String area; //

	@Column(name = "nick_name")
	private String nickName; //

	@Column(name = "type")
	private String type;

	@Column(name = "cnt")
	private Integer cnt; //

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
}

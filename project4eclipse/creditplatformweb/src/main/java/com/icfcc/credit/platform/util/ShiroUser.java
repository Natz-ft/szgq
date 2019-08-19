package com.icfcc.credit.platform.util;

import com.google.common.base.Objects;

import java.io.Serializable;

public class ShiroUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4655974944456405926L;

	public String id;

	public String name;
	
	public String nickname;
	
	public String org;


	public ShiroUser(String id, String nickname, String name,String org) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.name = name;
		this.org = org;
	}


	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNickname() {
		return nickname;
	}
	
	public String getOrg() {
		return org;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return nickname;
	}
	/**
	 * 重载hashCode,只计算nickname;
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(nickname);
	}
	/**
	 * 重载equals,只计算nickname;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ShiroUser other = (ShiroUser) obj;
		if (nickname == null) {
			if (other.nickname != null) {
				return false;
			}
		} else if (!nickname.equals(other.nickname)) {
			return false;
		}
		return true;
	}
}

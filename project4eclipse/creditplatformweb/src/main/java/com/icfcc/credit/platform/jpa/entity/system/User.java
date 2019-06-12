package com.icfcc.credit.platform.jpa.entity.system;

import java.io.Serializable;
/**
 * 
* @ClassName: User
* @Description: TODO(这里用一句话描述这个类的作用)
* @author hugt
* @date 2017年9月14日 下午6:46:05
*
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + "]";
	}
}

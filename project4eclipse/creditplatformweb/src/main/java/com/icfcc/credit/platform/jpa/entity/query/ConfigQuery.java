package com.icfcc.credit.platform.jpa.entity.query;

import com.icfcc.credit.platform.util.PageBean;

public class ConfigQuery  extends PageBean{
	
	 private String search_LIKE_configName;

	public String getSearch_LIKE_configName() {
		return search_LIKE_configName;
	}

	public void setSearch_LIKE_configName(String search_LIKE_configName) {
		this.search_LIKE_configName = search_LIKE_configName;
	}


}

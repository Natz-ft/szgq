package com.icfcc.SRRPService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.s1.jpa.entity.PlatformConfig;
import com.icfcc.SRRPDao.s1.jpa.repository.PlatformConfigDao;



/**
 * <功能详细描述>
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformConfigService {
	private static Logger log = LoggerFactory.getLogger(PlatformConfigService.class);
	@Autowired
	private PlatformConfigDao systemConfigDao;

	public Map<String, String> findByConfigNames() {
		Map<String, String> map=new HashMap<String, String>();
		List<PlatformConfig> list=systemConfigDao.findByConfigNames();
		for(PlatformConfig config:list){
			map.put(config.getConfigName(), config.getConfigValue());
		}
		
		return map;
	}

	/**
	 * <根据ID删除配置信息>
	 * 
	 * @param valueOf
	 */
	public void deleteConfigById(Long id) {
		systemConfigDao.delete(id);
	}

	
	
	/**
	 * <一句话功能简述>
	 * 
	 * @param configName
	 * @return
	 */
	public PlatformConfig getSystemConfigByName(String configName) {
		return systemConfigDao.findByConfigName(configName);
	}

	public String getConfigValueByName(String configName) {
		PlatformConfig sc = getSystemConfigByName(configName);
		if (null != sc) {
			return sc.getConfigValue();
		} else {
			return "";
		}
	}

}
package com.icfcc.credit.platform.service.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.PlatformConfig;
import com.icfcc.credit.platform.jpa.repository.system.PlatformConfigDao;
import com.icfcc.credit.platform.util.jpa.PageUtil;

/**
 * <功能详细描述>
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformConfigService {
	private static Logger log = LoggerFactory.getLogger(PlatformConfigService.class);
	@Autowired
	private PlatformConfigDao systemConfigDao;

	public List<PlatformConfig> queryAllSystemConfig() {
		return (List<PlatformConfig>) systemConfigDao.findAll();
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
	 * <分页查询配置信息>
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<PlatformConfig> getSystemConfigList(Map<String, Object> searchParams, int pageNumber, int pageSize, String direction, String orderBy) {
		PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
		Specification<PlatformConfig> spec = PageUtil.buildSpecification(searchParams, PlatformConfig.class);
		return systemConfigDao.findAll(spec, pageRequest);
	}

	/**
	 * <查询配置>
	 * 
	 * @param id
	 * @return
	 */
	public PlatformConfig getSystemConfigById(Long id) {
		return systemConfigDao.findOne(id);
	}

	/**
	 * <保存>
	 * 
	 * @param config
	 */
	public void saveSystemConfig(PlatformConfig config) {
		try {
			config.setCreateTime(new Date());
			config.setUpdateTime(new Date());
			PlatformConfig systemConfig = systemConfigDao.save(config);
			log.info("SystemConfig:" + systemConfig);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	/**
	 * <修改>
	 */
	public void updateSystemConfig(PlatformConfig config) {
		try {
			config.setUpdateTime(new Date());
			PlatformConfig systemConfig = systemConfigDao.save(config);
			log.info("SystemConfig:" + systemConfig);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
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
			return null;
		}
	}

}
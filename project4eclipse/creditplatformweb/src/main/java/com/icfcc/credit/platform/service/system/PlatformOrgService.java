package com.icfcc.credit.platform.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.PlatformOrg;
import com.icfcc.credit.platform.jpa.entity.system.PlatformOrgConfig;
import com.icfcc.credit.platform.jpa.repository.system.PlatformOrgConfigDao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformOrgDao;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.jpa.PageUtil;

/**
 * <机构配置>
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformOrgService {
	private static Logger log = LoggerFactory.getLogger(PlatformOrgService.class);

	@Autowired
	private PlatformOrgDao systemSysOrgDao;
	@Autowired
	private PlatformOrgConfigDao orgConfigDao;

	public List<PlatformOrg> findByAllValid() {
		List<PlatformOrg> listOrg = null;
		try {
			listOrg = systemSysOrgDao.findByAllValid(Constant.EXISTSTATE, Constant.STARTFLAG);
			log.info("listOrg:" + listOrg);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return listOrg;
	}

	public Map<String, String> getOrg() {
		Map<String, String> map = new HashMap<String, String>();
		List<PlatformOrg> listOrg = null;
		try {
			listOrg = systemSysOrgDao.findByAllValid(Constant.EXISTSTATE, Constant.STARTFLAG);
			log.info("listOrg:" + listOrg);
			if (CollectionUtils.isNotEmpty(listOrg)) {
				for (int i = 0; i < listOrg.size(); i++) {
					PlatformOrg org = listOrg.get(i);
					map.put(org.getOrgid(), org.getOrgName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return map;
	}

	/**
	 * 删除机构配置管理表
	 * 
	 * @param org
	 */
	public void deleteOrgConfig(String id) {
		orgConfigDao.deleteById(id);
	}

	/**
	 * 修改机构配置管理表
	 */
	public void updateOrgConfig(PlatformOrgConfig orgConfig){
		
		orgConfigDao.save(orgConfig);
	}
	
	
	/**
	 * 根据ID 查询 配置管理
	 * 
	 * @param id
	 * @return
	 */
	public List<PlatformOrgConfig> findById(String id) {

		return orgConfigDao.findById(id);
	}

	/**
	 * 添加机构配置管理表
	 * 
	 * @param org
	 */
	public void saveOrgConfig(PlatformOrgConfig orgConfig) {
		orgConfigDao.save(orgConfig);
	}

	/**
	 * 添加机构
	 * 
	 * @param org
	 */
	public PlatformOrg saveOrg(PlatformOrg org) {
		try {
			PlatformOrg orgSave = systemSysOrgDao.save(org);
			return orgSave;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	/**
	 * 修改机构
	 */
	public PlatformOrg updateOrg(PlatformOrg org) {
		try {
			PlatformOrg SysOrg = systemSysOrgDao.save(org);
			return SysOrg;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 机构配置管理
	 * 
	 * @param appid
	 * @return
	 */
	public List<PlatformOrgConfig> findByAppid(String appid) {
		return orgConfigDao.findByAppid(appid);
	}

	/**
	 * 机构表 orgid 查询
	 * 
	 * @param orgid
	 * @return
	 */
	public List<PlatformOrg> findByOrgid(String orgid) {
		return systemSysOrgDao.findByOrgid(orgid, Constant.EXISTSTATE);
	}

	/**
	 * 机构表 删除
	 * 
	 * @param orgid
	 */
	public void deleteByOrgid(String orgid) {
		try {
			systemSysOrgDao.updateByOrgid(Constant.DELETESTATE, orgid);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 机构表 获取按钮分页列表 <功能详细描述>
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param direction
	 * @param orderBy
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Page<PlatformOrg> getSystemOrg(Map<String, Object> searchParams, int pageNumber, int pageSize, String direction, String orderBy) {
		try {
			PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
			Specification<PlatformOrg> spec = PageUtil.buildSpecification(searchParams, PlatformOrg.class);
			return systemSysOrgDao.findAll(spec, pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 机构 配置管理 获取按钮分页列表 <功能详细描述>
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param direction
	 * @param orderBy
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Page<PlatformOrgConfig> getSystemOrgConfig(Map<String, Object> searchParams, int pageNumber, int pageSize, String direction, String orderBy) {
		PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
		Specification<PlatformOrgConfig> spec = PageUtil.buildSpecification(searchParams, PlatformOrgConfig.class);
		return orgConfigDao.findAll(spec, pageRequest);
	}

	/***
	 * 机构配置表 查询
	 * 
	 * @return
	 */
	public List<PlatformOrgConfig> getSystemOrgConfig() {

		return (List<PlatformOrgConfig>) orgConfigDao.findAll();
	}

	/**
	 * 查询已经启用应用配置
	 * 
	 * @param startFlag
	 * @return
	 */
	public List<PlatformOrgConfig> findByStartFlag(String startFlag, String pageType) {
		return orgConfigDao.findByStartFlag(startFlag, pageType);
	}

	public List<PlatformOrg> findById(String id, Short deleteState) {
		List<PlatformOrg> listOrg = null;
		try {
			listOrg = systemSysOrgDao.findById(id, deleteState);
			return listOrg;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	public List<PlatformOrgConfig> findByAppid(String startFlag, String pageType, String appid) {
		List<PlatformOrgConfig> listOrgConfig = null;
		try {
			listOrgConfig = orgConfigDao.findByAppid(startFlag, pageType, appid);
			log.info("listOrgConfig" + listOrgConfig);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return listOrgConfig;
	}
}

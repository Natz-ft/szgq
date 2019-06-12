package com.icfcc.credit.platform.service.system;

import java.util.ArrayList;
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

import com.icfcc.credit.platform.jpa.entity.system.PlatformButton;
import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleButton;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserRole;
import com.icfcc.credit.platform.jpa.repository.system.PlatformButtonDao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformRoleButtonDao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformUserRoleDao;
import com.icfcc.credit.platform.util.cache.CacheEvict;
import com.icfcc.credit.platform.util.cache.Cacheable;
import com.icfcc.credit.platform.util.jpa.PageUtil;

/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 按钮服务
 * 
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformButtonService {
	private static Logger log = LoggerFactory.getLogger(PlatformButtonService.class);

	@Autowired
	private PlatformRoleButtonDao systemRoleButtonDao;

	@Autowired
	private PlatformButtonDao systemButtonDao;

	@Autowired
	private PlatformUserRoleDao systemUserRoleDao;

	private static final String CACHE_KEY = "luna.com.firefly.service.system.SystemButtonService";

	/**
	 * 根据用户ID查询该用户下的所有有权限的的按钮代码 <功能详细描述>
	 * 
	 * @param userId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<String> findButtonCodesByUserId(String userId) {
		List<String> buttonList = null;
		try {
			buttonList = new ArrayList<String>();
			List<PlatformUserRole> userRoleRels = systemUserRoleDao.findByUserId(userId);
			for (PlatformUserRole SystemUserRole : userRoleRels) {
				buttonList.addAll(this.findRoleButtonCodesByRoleId(SystemUserRole.getRoleId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return buttonList;
	}

	/**
	 * 根据角色ID查询该角色下的所有有权限的按钮代码 <功能详细描述>
	 * 
	 * @param roleId
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<String> findRoleButtonCodesByRoleId(Long roleId) {
		List<String> buttonCode = null;
		try {
			buttonCode = new ArrayList<String>();
			List<PlatformRoleButton> roleButtonList = systemRoleButtonDao.findByRoleId(roleId);
			for (PlatformRoleButton SystemRoleButton : roleButtonList) {
				buttonCode.add(SystemRoleButton.getButtonCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return buttonCode;
	}

	/**
	 * 获取按钮分页列表 <功能详细描述>
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param direction
	 * @param orderBy
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public Page<PlatformButton> getButtonList(Map<String, Object> searchParams, int pageNumber, int pageSize, String direction, String orderBy) {
		try {
			PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
			Specification<PlatformButton> spec = PageUtil.buildSpecification(searchParams, PlatformButton.class);
			return systemButtonDao.findAll(spec, pageRequest);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 根据按钮代码查询按钮信息 <功能详细描述>
	 * 
	 * @param buttonCode
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@Cacheable(key = CACHE_KEY)
	public PlatformButton findByButtonCode(String buttonCode) {
		try {
			return systemButtonDao.findByButtonCode(buttonCode);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 根据按钮代码查询按钮信息 <功能详细描述>
	 * 
	 * @param buttonCode
	 * @return
	 * @see [类、类#方法、类#成员]
	 */

	public PlatformButton findOneByButtonCode(String buttonCode) {
		try {
			return systemButtonDao.findByButtonCode(buttonCode);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 保存按钮信息
	 * 
	 * @param buttonLatest
	 * @param buttonOld
	 * @see [类、类#方法、类#成员]
	 */
	/* @CacheEvict(key = CACHE_KEY) */
	public void saveButton(PlatformButton button) {
		try {
			systemButtonDao.save(button);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 修改
	 */
	public void updateButton(PlatformButton button){
		try {
			systemButtonDao.save(button);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 删除按钮信息以及其与角色的关系 <功能详细描述>
	 * 
	 * @param buttonCode
	 * @see [类、类#方法、类#成员]
	 */
	@CacheEvict(key = CACHE_KEY)
	public void deleteButtonByButtonCode(String buttonCode) {
		try {
			systemRoleButtonDao.deleteByButtonCode(buttonCode);
			systemButtonDao.deleteByButtonCode(buttonCode);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 
	 * @param buttonId
	 */
	public void deleteButtonByButtonId(long buttonId) {
		try {
			systemButtonDao.deleteByButtonId(buttonId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public PlatformButton findByButtonId(long buttonId) {
		try {
			return systemButtonDao.findByButtonId(buttonId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	public List<PlatformButton> getAllButtonList() {
		try {
			return (List<PlatformButton>) systemButtonDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

}

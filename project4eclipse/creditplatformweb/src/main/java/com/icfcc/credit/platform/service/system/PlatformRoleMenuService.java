package com.icfcc.credit.platform.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleMenu;
import com.icfcc.credit.platform.jpa.repository.system.PlatformRoleMenuDao;
import com.icfcc.credit.platform.jpa.repository.system.SystemRoleMenuDao;

@Component
@Transactional(value = "transactionManager")
public class PlatformRoleMenuService {
	@Autowired
    private PlatformRoleMenuDao roleMenuRelDao;
	/**
	 * 增加角色菜单关系表
	 */
	public void addRoleMenu(PlatformRoleMenu systemRoleMenu){
		roleMenuRelDao.save(systemRoleMenu);
	}
	public void deleteMenuByRoleID(Long RoleId){
		roleMenuRelDao.deleteByRoleId(RoleId);
	}
}

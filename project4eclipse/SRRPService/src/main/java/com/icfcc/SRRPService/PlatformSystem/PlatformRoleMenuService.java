package com.icfcc.SRRPService.PlatformSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformRoleMenu;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformRoleMenuDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.SystemRoleMenuDao;

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

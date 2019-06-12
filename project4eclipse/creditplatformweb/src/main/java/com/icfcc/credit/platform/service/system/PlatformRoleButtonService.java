package com.icfcc.credit.platform.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleButton;
import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleMenu;
import com.icfcc.credit.platform.jpa.repository.system.PlatformRoleButtonDao;
import com.icfcc.credit.platform.jpa.repository.system.SystemRoleMenuDao;
/**
 * 
* @ClassName: PlatformRoleButtonService
* 快捷菜单服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
* @author hugt
* @date 2017年9月14日 下午7:32:55
*
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformRoleButtonService {
	@Autowired
    private PlatformRoleButtonDao roleButtonRelDao;
	/**
	 * 增加角色菜单关系表
	 */
	public void addRoleButton(PlatformRoleButton systemRoleButton){
		roleButtonRelDao.save(systemRoleButton);
	}
	/**
	 * 修改角色菜单关系表
	 * @param RoleId
	 */
	public void updateRoleButton(PlatformRoleButton systemRoleButton){
		roleButtonRelDao.save(systemRoleButton);
	}
	public void deleteButtonByRoleID(Long RoleId){
		roleButtonRelDao.deleteByRoleId(RoleId);
	}
}

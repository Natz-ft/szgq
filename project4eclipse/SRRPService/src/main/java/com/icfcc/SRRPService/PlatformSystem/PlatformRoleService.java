package com.icfcc.SRRPService.PlatformSystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformRole;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformRoleButton;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformRoleMenu;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformUserRole;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformRoleButtonDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformRoleDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformRoleMenuDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformUserRoleDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;
import com.icfcc.credit.platform.util.ShiroUser;

/**
 * <角色管理接口> <处理角色信息接口>
 * 
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformRoleService {

	@Autowired
	private PlatformRoleDao roleDao;

	@Autowired
	private PlatformRoleMenuDao roleMenuDao;

	@Autowired
	private PlatformRoleButtonDao roleButtonRelDao;

	@Autowired
	private PlatformUserRoleDao userRoleDao;

	public static final String BUTTON_PREFIX = "button_";

	/**
	 * <保存角色信息>
	 * 
	 * @param entity
	 */
	public void saveRole(PlatformRole entity) {
		roleDao.save(entity);
	}

	/**
	 * <更新角色和菜单关系信息>
	 * 
	 * @param menuId
	 * @param roleId
	 */
	public void updateRoleMenuRel(String menuId, String roleId) {
		Long id = Long.valueOf(roleId);
		roleMenuDao.deleteByRoleId(id);
		PlatformRoleButton rbr = null;
		List<PlatformRoleButton> rbrList = new ArrayList<PlatformRoleButton>();
		List<PlatformRoleMenu> rmrList = new ArrayList<PlatformRoleMenu>();
		PlatformRoleMenu rmr = null;
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

		if (!"".equals(menuId)) {
			String[] menuIds = menuId.split(",");
			roleButtonRelDao.deleteByRoleId(Long.parseLong(roleId));
			for (String ids : menuIds) {
				if (ids.startsWith(BUTTON_PREFIX)) {

					String buttonCode = ids.substring(ids
							.indexOf(BUTTON_PREFIX) + BUTTON_PREFIX.length());
					rbr = new PlatformRoleButton();
					rbr.setButtonCode(buttonCode);
					rbr.setCreateTime(new Date());
					rbr.setRoleId(Long.parseLong(roleId));
					rbr.setCreateUser(user.name);
					rbrList.add(rbr);
				} else {
					rmr = new PlatformRoleMenu();
					rmr.setCreateTime(new Date());
					rmr.setCreateUser(user.name);
					rmr.setMenuId(ids);
					rmr.setRoleId(id);
					rmrList.add(rmr);
				}
			}
			roleButtonRelDao.save(rbrList);
			roleMenuDao.save(rmrList);
		}
	}

	/**
	 * <更新角色和用户关系信息>
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void InsertUserRoleRel(String userId, String roleId) {
		Long rId = Long.valueOf(roleId);
		Long count = userRoleDao.getCountByUserRoleId(userId, rId);
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (1 > count) {
			PlatformUserRole urr = new PlatformUserRole();
			urr.setRoleId(rId);
			urr.setUserId(userId);
			urr.setCreateTime(new Date());
			urr.setCreateUser(user.nickname);
			userRoleDao.save(urr);
		}
	}

	public void InveInsertUserRoleRel(String userId, String roleId,
			String nicName) {
		Long rId = Long.valueOf(roleId);
		Long count = userRoleDao.getCountByUserRoleId(userId, rId);
		if (1 > count) {
			PlatformUserRole urr = new PlatformUserRole();
			urr.setRoleId(rId);
			urr.setUserId(userId);
			urr.setCreateTime(new Date());
			urr.setCreateUser(nicName);
			userRoleDao.save(urr);
		}
	}

	/**
	 * 给角色更新权限
	 * 
	 * @param UserId
	 * @param RoleId
	 */
	public void UpdateUserRoleRel(PlatformUserRole userRole) {
		userRoleDao.save(userRole);
	}

	/**
	 * <删除角色和用户关系信息>
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void DelUserRoleRel(String userId, String roleId) {
		Long rId = Long.valueOf(roleId);
		userRoleDao.deleteByUserRoleId(userId, rId);

	}

	/**
	 * 根据用户id删除所有其拥有的角色
	 * 
	 * @param userId
	 */
	public void DelAllRole(String userId) {
		userRoleDao.deleteByUserId(userId);
	}

	/**
	 * <根据roleId 获取角色和菜单的关系信息>
	 * 
	 * @param roleId
	 * @return
	 */
	public List<PlatformRoleMenu> getRoleMenuRel(Long roleId) {
		return roleMenuDao.findByRoleId(roleId);
	}

	/**
	 * <根据userId 获取用户和角色的关系信息>
	 * 
	 * @param roleId
	 * @return
	 */
	public List<PlatformUserRole> getUserRoleRel(String userId) {
		List<PlatformUserRole> rel = new ArrayList<PlatformUserRole>();
		rel = userRoleDao.findByUserId(userId);
		return rel;
	}

	/**
	 * <根据id获得角色信息>
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public PlatformRole getRole(Long id) {
		return roleDao.findOne(id);
	}

	/**
	 * <获得所有角色信息>
	 * 
	 * @return
	 */
	public List<PlatformRole> getAllRole() {
		return roleDao.findAll();
	}

	/**
	 * <删除角色信息>
	 * 
	 * @param id
	 */
	public void deleteRole(Long id) {
		roleDao.delete(id);
	}

	/**
	 * <获得角色信息列表>
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<PlatformRole> getRoleList(Map<String, Object> searchParams,
			int pageNumber, int pageSize) {
		PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber,
				pageSize);
		Specification<PlatformRole> spec = PageUtil.buildSpecification(
				searchParams, PlatformRole.class);
		return roleDao.findAll(spec, pageRequest);
	}

	/**
	 * <获得用户没有绑定的角色信息列表>
	 * 
	 * @param roleId
	 * @return
	 */
	public List<PlatformRole> getRoleLists(List<Long> roleId) {
		return roleDao.findByIdNotIn(roleId);

	}

	public PlatformUserRole getRoleById(String userId) {
		return userRoleDao.getRoleById(userId);
	}

	/**
	 * <根据角色名查询>
	 * 
	 * @param roleName
	 * @return
	 */
	public PlatformRole findRoleByName(String roleName) {
		return roleDao.findByName(roleName);
	}

	/**
	 * <一句话功能简述>
	 * 
	 * @param roleName
	 * @param roleId
	 * @return
	 */
	public PlatformRole findRoleByNameAndId(String roleName, Long roleId) {
		return roleDao.findByIdAndName(roleId, roleName);
	}

	/**
	 * <一句话功能简述>
	 * 
	 * @param roleId
	 * @return
	 */
	public PlatformRole findOne(Long roleId) {
		return roleDao.findOne(roleId);
	}

	public List<String> findRoleButtonCodesByRoleId(Long roleId) {
		List<String> buttonCode = new ArrayList<String>();
		List<PlatformRoleButton> roleButtonList = roleButtonRelDao
				.findByRoleId(roleId);
		for (PlatformRoleButton SystemRoleButton : roleButtonList) {
			buttonCode.add(SystemRoleButton.getButtonCode());
		}
		return buttonCode;
	}

	/**
	 * <根据roleId 获取角色和按钮的关系信息>
	 * 
	 * @param roleId
	 * @return
	 */
	public List<PlatformRoleButton> getRolebuttonRel(long roleId) {
		return roleButtonRelDao.findByRoleId(roleId);
	}

}

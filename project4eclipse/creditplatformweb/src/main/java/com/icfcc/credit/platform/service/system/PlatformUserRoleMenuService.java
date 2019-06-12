package com.icfcc.credit.platform.service.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.PlatformMenu;
import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleMenu;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserRole;
import com.icfcc.credit.platform.jpa.repository.system.PlatformMenu1Dao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformMenuDao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformRoleMenuDao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformUserRoleDao;
import com.icfcc.credit.platform.jpa.repository.system.SysUserRoleDao;

@Component
@Transactional(value = "transactionManager")
public class PlatformUserRoleMenuService {
	private static Logger log = LoggerFactory.getLogger(PlatformUserRoleMenuService.class);

	
	@Autowired
	private PlatformUserRoleDao sysUserRoleDao;

	@Autowired
	private PlatformRoleMenuDao sysRoleMenuDao;

	@Autowired
	private PlatformMenuDao sysMenuDao;

	public List<PlatformUserRole> findByUserId(String userId) {
		List<PlatformUserRole> list = null;
		try {
			list = sysUserRoleDao.findByUserId(userId);
			log.error("List<SystemUserRole>:" + list);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return list;
	}

	public List<PlatformRoleMenu> findByRoleId(Long roleId) {
		List<PlatformRoleMenu> list = null;
		try {
			list = sysRoleMenuDao.findByRoleId(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return list;
	}

	public List<PlatformMenu> findById(String id) {
		List<PlatformMenu> list = null;
		return list = sysMenuDao.findById(id);
	}

	public List<String> findByIdLink(String userId) {
		// 获取角色
		List<PlatformUserRole> listUserRoleRel = sysUserRoleDao.findByUserId(userId);
		List<String> listLink = new ArrayList<String>();
		List<PlatformRoleMenu> listRoleMenu = new ArrayList<PlatformRoleMenu>();
		try {
			if (CollectionUtils.isNotEmpty(listUserRoleRel)) {
				for (int a = 0; a < listUserRoleRel.size(); a++) {
					PlatformUserRole userRole = listUserRoleRel.get(a);
					if (userRole != null) {
						// 通过角色关联菜单
						listRoleMenu = sysRoleMenuDao.findByRoleId(userRole.getRoleId());
						if (CollectionUtils.isNotEmpty(listRoleMenu)) {
							for (int i = 0; i < listRoleMenu.size(); i++) {
								PlatformRoleMenu roleMenu = listRoleMenu.get(i);
								if (roleMenu != null) {
									List<PlatformMenu> listMenu = sysMenuDao.findById(roleMenu.getMenuId());
									for (int j = 0; j < listMenu.size(); j++) {
										PlatformMenu systemMenu = listMenu.get(j);
										if (systemMenu != null) {
											String link = systemMenu.getLink();
											if (StringUtils.isNotBlank(link)) {
												dealUrl(listLink, link);
											}
//											listLink.add();
										}
									}
								}
							}
						}
						//
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
		}
		return listLink;
	}

	private void dealUrl(List<String> listLink, String link) {
		try {
			String linkSpc = link.substring(0,link.lastIndexOf("/"));
			log.info("link is substr:"+linkSpc);
			listLink.add(linkSpc);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	public List<String> getAllMenu() {
		List<String> listLink = new ArrayList<String>();
		try {
			List<PlatformMenu> list = (List<PlatformMenu>) sysMenuDao.findAll();
			PlatformMenu menu = null;
			if (CollectionUtils.isNotEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					menu = list.get(i);
					if (menu != null) {
						String link = menu.getLink();
						if (StringUtils.isNotBlank(link)) {
							dealUrl(listLink, link);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return listLink;
	}
}

package com.icfcc.SRRPService.PlatformSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformMenu;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformMenuDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformRoleMenuDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformUserRoleDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.SystemRoleMenuDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;

/**
 * <功能详细描述>
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformMenuService {

	@Autowired
	private PlatformMenuDao menuDao;

	@Autowired
	private PlatformRoleMenuDao roleMenuRelDao;

	@Autowired
	private PlatformUserRoleDao userRoleRelDao;

	/**
	 * <根据 parentId获得menu列表>
	 * 
	 * @param parentId
	 * @return
	 */
	public List<PlatformMenu> getMenuByParentId(String parentId) {
		List<PlatformMenu> list = menuDao.findByParentIdOrderBySortAsc(parentId);
		for (PlatformMenu menu : list) {
			if (menu != null) {
				List<PlatformMenu> childMenus = getMenuByParentId(menu.getId());
				menu.setChildMenus(childMenus);
			}
		}
		return list;
	}

	/**
	 * <保存menu>
	 * 
	 * @param menu
	 */
	public void saveMenu(PlatformMenu menu) {
		menuDao.save(menu);
	}

	/**
	 * <修改menu>
	 * 
	 * @param menu
	 */
	public void updateMenu(PlatformMenu menu) {
		menuDao.save(menu);
	}

	/**
	 * <更新菜单信息>
	 * 
	 * @param menu
	 */
	public void updateMenu(String parentId, Integer sort) {
		menuDao.updateSort(parentId, sort);
	}

	/**
	 * <根据parentId获得最大的sortnum>
	 * 
	 * @param parentId
	 * @return
	 */
	public Integer getMaxSortNumByParentId(String parentId) {
		return menuDao.getMaxSortNumByParentId(parentId);
	}

	/**
	 * <获得菜单管理信息>
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<PlatformMenu> getMenuList(Map<String, Object> searchParams, int pageNumber, int pageSize) {
		String parentId = "parentId";
		String sortnum = "sort";
		PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, parentId, sortnum);
		Specification<PlatformMenu> spec = PageUtil.buildSpecification(searchParams, PlatformMenu.class);

		return menuDao.findAll(spec, pageRequest);
	}

	/**
	 * <菜单管理信息 分页查询配置>
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param direction
	 * @param orderBy
	 * @return
	 */
	public Page<PlatformMenu> getMenuList(Map<String, Object> searchParams, int pageNumber, int pageSize, String direction, String orderBy) {
		PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
		Specification<PlatformMenu> spec = PageUtil.buildSpecification(searchParams, PlatformMenu.class);
		return menuDao.findAll(spec, pageRequest);
	}

	/**
	 * <获取菜单列表>
	 * 
	 * @return
	 */
	public List<PlatformMenu> getAllMenuList() {
		return (List<PlatformMenu>) menuDao.findAll();
	}

	/**
	 * <根据menuId查询>
	 * 
	 * @param id
	 * @return
	 */
	public PlatformMenu getMenuById(String id) {
		return menuDao.findOne(id);
	}

	/**
	 * <删除 menu>
	 * 
	 * @param valueOf
	 */
	public void deleteMenuById(String menuId) {
		roleMenuRelDao.deleteByMenuId(menuId);
		menuDao.delete(menuId);
	}

	/**
	 * 根据userId获取菜单列表 当前菜单较少，采取一次查询后封装 后续菜单增加后，可多次查询DB再封装
	 * 
	 * @param string
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<PlatformMenu> getMenuByUserId(String string) {
		List<PlatformMenu> retMenus = Lists.newArrayList();
		List<Long> roleIdList = new ArrayList<Long>();
		roleIdList = userRoleRelDao.findRoleIdByUserId(string);
		List<String> menuIdList = roleMenuRelDao.findMenuIdByRoleId(roleIdList);
		if (!CollectionUtils.isEmpty(menuIdList)) {
			List<PlatformMenu> menus = menuDao.findByMenuIds(menuIdList);
			retMenus = newSortMenu(retMenus, menus);
		}
		return retMenus;
	}

	private List<PlatformMenu> newSortMenu(List<PlatformMenu> retMenus, List<PlatformMenu> menus) {
		if (!CollectionUtils.isEmpty(menus)) {
			for (PlatformMenu node1 : menus) {
				boolean mark = false;
				for (PlatformMenu node2 : menus) {
					if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
						mark = true;
						if (node2.getChildMenus() == null)
							node2.setChildMenus(new ArrayList<PlatformMenu>());
						node2.getChildMenus().add(node1);
						break;
					}
				}
				if (!mark) {
					retMenus.add(node1);
				}
			}
		}
		Collections.sort(retMenus);
		return retMenus;
	}

	private List<PlatformMenu> oldSortMenu(List<PlatformMenu> retMenus, List<PlatformMenu> menus) {
		if (!CollectionUtils.isEmpty(menus)) {
			Map<String, PlatformMenu> parentMenus = Maps.newHashMap();

			// 封装一级主menu
			for (PlatformMenu menu : menus) {
				if (menu != null && menu.getParentId() != null && "0".equals(menu.getParentId())) {
					parentMenus.put(menu.getId(), menu);
				}
			}

			// 封装子menu列表到主menu
			PlatformMenu parentMenu = null;
			List<PlatformMenu> childMenus = null;
			for (PlatformMenu menu : menus) {
				if (menu != null && menu.getParentId() != null) {
					parentMenu = parentMenus.get(menu.getParentId());
					if (parentMenu != null) {
						childMenus = parentMenu.getChildMenus();
						if (null == childMenus) {
							childMenus = Lists.newArrayList();
						}
						childMenus.add(menu);

						parentMenu.setChildMenus(childMenus);
					}
				}
			}

			retMenus = Lists.newArrayList(parentMenus.values());

			// 对子menu进行排序
			for (PlatformMenu SystemMenu : retMenus) {
				Collections.sort(SystemMenu.getChildMenus(), new Comparator<PlatformMenu>() {
					@Override
					public int compare(PlatformMenu o1, PlatformMenu o2) {
						return o1.getSort() - o2.getSort();
					}
				});
			}

			// 对主menu进行排序
			Collections.sort(retMenus, new Comparator<PlatformMenu>() {
				@Override
				public int compare(PlatformMenu o1, PlatformMenu o2) {
					return o1.getSort() - o2.getSort();
				}
			});
		}
		return retMenus;
	}

	/**
	 * < 根据Id 查询数据>
	 * 
	 * @param id
	 * @return
	 */
	public List<PlatformMenu> findById(String id) {
		return menuDao.findById(id);
	}

}

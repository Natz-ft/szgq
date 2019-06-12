package com.icfcc.credit.platform.web.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.icfcc.credit.platform.jpa.entity.query.CompanyBaseSupplement;
import com.icfcc.credit.platform.jpa.entity.system.PlatformMenu;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUser;
import com.icfcc.credit.platform.service.business.CompanyInfoService;
import com.icfcc.credit.platform.service.system.PlatformBusinessPartnerService;
import com.icfcc.credit.platform.service.system.PlatformButtonService;
import com.icfcc.credit.platform.service.system.PlatformConfigService;
import com.icfcc.credit.platform.service.system.PlatformContactusDistService;
import com.icfcc.credit.platform.service.system.PlatformContactusService;
import com.icfcc.credit.platform.service.system.PlatformDemandService;
import com.icfcc.credit.platform.service.system.PlatformDicService;
import com.icfcc.credit.platform.service.system.PlatformEventService;
import com.icfcc.credit.platform.service.system.PlatformFaqService;
import com.icfcc.credit.platform.service.system.PlatformFriendlyLinksService;
import com.icfcc.credit.platform.service.system.PlatformMenuService;
import com.icfcc.credit.platform.service.system.PlatformMessageService;
import com.icfcc.credit.platform.service.system.PlatformNewsService;
import com.icfcc.credit.platform.service.system.PlatformQuickMenuService;
import com.icfcc.credit.platform.service.system.PlatformRoleMenuService;
import com.icfcc.credit.platform.service.system.PlatformRoleService;
import com.icfcc.credit.platform.service.system.PlatformUploadService;
import com.icfcc.credit.platform.service.system.PlatformUserService;
import com.icfcc.credit.platform.service.system.SrrpSynchronizeTaskService;
import com.icfcc.credit.platform.util.HttpResult;
import com.icfcc.credit.platform.util.HttpStatus;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.credit.platform.web.BaseController;

/**
 * <功能详细描述>
 */
public abstract class PlatformBasicController extends BaseController {

	@Autowired
	protected PlatformRoleService roleService;

	@Autowired
	protected PlatformDicService systemDicService;

	@Autowired
	protected PlatformButtonService buttonService;

	@Autowired
	protected PlatformMenuService sysMenuService;

	@Autowired
	protected PlatformUserService userService;

	@Autowired
	protected PlatformConfigService systemConfigService;

	@Autowired
	protected PlatformMessageService systemMessageService;

	@Autowired
	protected PlatformMenuService systemMenuService;

	@Autowired
	protected PlatformRoleMenuService systemRoleMenuService;
	@Autowired
	protected PlatformNewsService systemContentNewsService;
	@Autowired
	protected PlatformBusinessPartnerService systemContentPartnerService;
	@Autowired
	protected PlatformFriendlyLinksService systemContentLinksService;
	@Autowired
	protected PlatformFaqService systemContentFaqService;
	@Autowired
	protected PlatformQuickMenuService systemContentQuickMenuService;
	@Autowired
	protected PlatformContactusService systemContentContactusService;
	@Autowired
	protected PlatformContactusDistService systemContentContactusDistService;
	@Autowired
	protected PlatformUploadService platformUploadService;
	
	@Autowired
	protected PlatformDemandService platformDemandService;
	@Autowired
	protected SrrpSynchronizeTaskService taskService;
	@Autowired
	protected CompanyInfoService companyInfoService;
	
	@Autowired
	protected  PlatformEventService eventService;
	/**
	 * 取出Shiro中的当前用户.
	 */
	public ShiroUser getCurrentUser() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user;
	}

	public HttpResult getCorrectResult() {
		HttpResult result = new HttpResult();
		result.setResult(HttpStatus.OK);
		return result;
	}

	public HttpResult getErrorResult() {
		HttpResult result = new HttpResult();
		result.setResult("300", "操作失败");
		return result;
	}

	public List<PlatformMenu> getAllUserMenu() {

		// Object obj=
		// UserSessionManager.getInstance().get(TagOrSessionConstants.SESSION_MENUDISPLAY);
		List<PlatformMenu> menus = null;
		// if(null!=obj && obj instanceof List) {
		// menus = (List<SystemMenu>)obj;
		// }else {
		ShiroUser user = getCurrentUser();
		PlatformUser platUser = null;
		String type = null;
		boolean iSselected = false;
		platUser = userService.getUser(user.getId());
		type = platUser.getType();
		if (type != null) {
			if ("02".equals(type)) {
				iSselected = getSelectedMenu(user.getOrg());
			}
			if (platUser.getDesc2() != null) {
				if (platUser.getDesc2().equals("01")) {// 存量企业首次登陆
					iSselected = true;
				}
			}

		}

		menus = sysMenuService.getMenuByUserId(user.getId(), iSselected);
		// UserSessionManager.getInstance().put(TagOrSessionConstants.SESSION_MENUDISPLAY,
		// menus);
		// }
		return menus;
	}

	private boolean getSelectedMenu(String org) {
		CompanyBaseSupplement company = null;
		boolean iSselected = false;
		company = sysMenuService.findCompanyBaseSupplement(org);
		if (company == null) {// 如果不存在,则为false
			iSselected = true;
		}

		return iSselected;

	}

	public List<PlatformMenu> getSubMenuList(String menuId) {
		List<PlatformMenu> menus = getAllUserMenu();
		return getSubMenuAndSelfNodeList(menus, menuId);
	}

	/*
	 * 获得节点及其子节点
	 */
	public List<PlatformMenu> getSubMenuAndSelfNodeList(List<PlatformMenu> menus, String menuId) {

		for (PlatformMenu menu : menus) {

			if (menuId.equals(menu.getId())) {
				List<PlatformMenu> list = new ArrayList<PlatformMenu>();
				// 添加根节点
				list.add(menu);
				// 获取一级子节点
				List<PlatformMenu> subList = menu.getChildren();
				if (null != subList && subList.size() > 0) {
					// 添加一级子节点
					list.addAll(subList);
					for (PlatformMenu submenu : subList) {
						// 获取二级子节点
						List<PlatformMenu> grandson = submenu.getChildren();
						if (null != grandson && grandson.size() > 0) {
							// 添加二级子节点
							list.addAll(grandson);
							for (PlatformMenu mostson : grandson) {
								// 获取三级子节点
								List<PlatformMenu> most = mostson.getChildren();
								if (null != most && most.size() > 0) {
									// 添加三级子节点
									list.addAll(most);
								}
							}
						}
					}
				}
				return list;
			}
			List<PlatformMenu> childMenus = menu.getChildren();
			if (null != childMenus) {
				getSubMenuAndSelfNodeList(childMenus, menuId);
			}
		}
		return null;
	}

}

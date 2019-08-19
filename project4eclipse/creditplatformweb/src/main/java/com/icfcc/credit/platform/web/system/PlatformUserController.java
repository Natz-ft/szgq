package com.icfcc.credit.platform.web.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.icfcc.credit.platform.constants.DD;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.jpa.entity.system.*;
import com.icfcc.credit.platform.service.system.PlatformOrgService;
import com.icfcc.credit.platform.session.RedisCacheManager;
import com.icfcc.credit.platform.session.RedisManager;
import com.icfcc.credit.platform.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 用户管理类，用于管理员增删改查用户 重置用户密码 停用用户账户 解锁用户账户 用户添加角色
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "admin/user")
public class PlatformUserController extends PlatformBasicController {
	private static Logger log = LoggerFactory
			.getLogger(PlatformUserController.class);
	@Autowired
	private PlatformOrgService orgService;
	@Autowired
	private RedisManager redisManager;
	private static final String UNLOCK = "解锁用户";
	private static final String SUPER = "1";

	private String keyPrefix = "shiro_redis_cache:";

	/**
	 * 用户分页信息
	 *
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userList")
	public String userList(Model model, PageBean page,
			HttpServletRequest request) {
		Page<PlatformUser> queryResults = null;
		String isSuper = "0";
		try {
			Map<String, Object> searchParams = processRequestParams(page,
					request);
			ShiroUser SystemUser = userService.getCurrentNickName();
			Map<String, String> orgMap = orgService.getOrg();
			// 判断当前登陆 是超级管理员还是系统管理员
			// 1.超级管理员 管理系统用户
			// 2.系统用户 管理主管部门用户和内容管理用户
			if (SUPER.equals(SystemUser.getId())) {
				searchParams.put("IN_org", "01,02,03,04,05,06");
				isSuper = "1";
			} else {
				orgMap.remove("01");
				searchParams.put("IN_org", "02,03,04,05,06");
			}
			queryResults = userService.getUsers(searchParams,
					page.getCurPage(), page.getMaxSize(), VipCont.DIRECTION,
					"createTime");
			processQueryResults(model, page, queryResults);
			// 以下用于用户列表中不显示超级管理员
			Iterator<PlatformUser> iterator = queryResults.iterator();
			while (iterator.hasNext()) {
				String id = iterator.next().getId();
				if (SUPER.equals(id)) {
					iterator.remove();
				}
			}
             
			List<PlatformUser> userList = (List<PlatformUser>) page.getList();
			List<PlatformUserLoginLog> successLog = new ArrayList<PlatformUserLoginLog>();
			if (null != userList) {
				for (PlatformUser user : userList) {
					// 获取登录历史信息，时间，ip，类型等,list存在两条记录，第一条为成功，第二条为失败
					List<PlatformUserLoginLog> loglist = userService
							.getLoginLog(user.getId());
					// 获取成功登录时间，并存入successLog，用于主界面展示上次成功登录时间
					successLog.add(loglist.get(0));
				}
			}
			model.addAttribute("successLog", successLog);
			// 获取机构
			HttpSession session = request.getSession();
			session.setAttribute("isSuper", isSuper);
			session.setAttribute("org", orgMap);
			List<DD> dataList = VipCont.getValueList("dd:area");
			 Map<String, String> dataMap = new HashMap<String, String>();
			for (DD dd : dataList) {
				if (dd.getDicCode().contains("3205")) {
					dataMap.put(dd.getDicCode(), dd.getDicName());
				} 
			}
			session.setAttribute("dataList", dataMap);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "system/user/userList";
	}

	/**
	 * 用于检查用户名是否唯一
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "checkUsername")
	@ResponseBody
	public String checkUsername(HttpServletRequest request) {
		String validateId = request.getParameter("fieldId");
		String username = request.getParameter("fieldValue");
		PlatformUser user = userService.findUserByUserName(username);
		String[] validateReturn = new String[3];
		if (user == null) {
			validateReturn[0] = validateId;
			validateReturn[1] = "true";
			validateReturn[2] = "可以使用";
		} else {
			validateReturn[0] = validateId;
			validateReturn[1] = "false";
			validateReturn[2] = "不可以使用";
		}
		return "success";
	}

	/**
	 * 添加用户
	 *
	 * @param user
	 */
	@RequestMapping(value = "addUser")
	@ResponseBody
	public String addUser(ResultBean rs, PlatformUser user) {
		// 创建时间和创建人id
		PlatformUser isuUserName = null;
		try {
			ShiroUser createUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipal();
			isuUserName = userService.findUserByUserName(user.getUsername());
			if (isuUserName != null) {
				rs.setCode("00002");
			} else {
				user.setCreateUser(createUser.getName());
				user.setCreateTime(new Date());
				user.setType("04");// 其他用户
				userService.addSystemUser(user);
				rs = ResultBean.sucessResultBean();
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 用于新建页面，获取下拉列表信息，此处为机构信息
	 *
	 * @return
	 */
	@RequestMapping(value = "createPage")
	@ResponseBody
	public String addUser(Model model, HttpServletRequest request) {
		// 此处dept实际根据页面参数的name值获取
		Map<String, String> org = systemDicService.getMapByType("dept");

		String result = JSON.toJSONString(org);
		return result;
	}

	/**
	 * 用于显示修改页面，需要展示信息有：新建页面所有信息以及上次成功登录时间，上次失败登录时间， 登录ip，修改人，修改时间
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "detail")
	public ModelAndView userDetail(String id) {
		ModelAndView model = new ModelAndView();
		model.setViewName("system/user/detail");
		PlatformUser user = userService.getUser(id);
		/*
		 * if(user.getCreateUser()!=null){ String
		 * createUser=getUsernameById(user.getCreateUser());
		 * user.setCreateUser(createUser); } if(user.getUpdateUser()!=null){
		 * String updateUser=getUsernameById(user.getUpdateUser());
		 * user.setUpdateUser(updateUser); }
		 */
		// 获取登录历史信息，时间，ip,类型等,list存在两天记录，第一条为成功，第二条为失败
		List<PlatformUserLoginLog> list = userService.getLoginLog(user.getId());
		model.addObject("user", user);
		model.addObject("loglist", list);
		return model;
	}

	private String getUsernameById(String id) {
		PlatformUser user = userService.getUser(id);
		String username = user.getUsername();
		return username;
	}

	/**
	 * 更新用户信息
	 *
	 * @param rs
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "updateUser")
	@ResponseBody
	public String updateUser(ResultBean rs, HttpServletRequest req, String id) {
		try {
			PlatformUser user = userService.getUser(id);
			String nickname = req.getParameter("nickname");
			//String type = req.getParameter("type");
			String org = req.getParameter("org");
			String dept = req.getParameter("dept");
			String telephone = req.getParameter("telephone");
			String email = req.getParameter("email");
			String postCode = req.getParameter("postCode");
			String address = req.getParameter("address");
			String desc1 = req.getParameter("desc1");
			String desc2 = req.getParameter("desc2");
			String desc3 = req.getParameter("desc3");
			String desc4 = req.getParameter("desc4");
			user.setNickname(nickname);
			//user.setType(type);
			user.setOrg(org);
			user.setDept(dept);
			user.setTelephone(telephone);
			user.setEmail(email);
			user.setPostCode(postCode);
			user.setAddress(address);
			user.setDesc1(desc1);
			user.setDesc2(desc2);
			user.setDesc3(desc3);
			user.setDesc4(desc4);
			// 修改者id和时间
			ShiroUser updateUser = getCurrentUser();
			user.setUpdateUser(updateUser.getName());
			user.setUpdateTime(new Date());
			userService.addSystemUser(user);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 删除用户
	 *
	 * @param rs
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delUser")
	@ResponseBody
	public String delUser(ResultBean rs, String id) {
		String[] ids = id.split(",");
		try {
			for (int i = 0; i < ids.length; i++) {
				if (StringUtils.isNotBlank(ids[i])) {
					userService.deleteUser(ids[i]);
				}
			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 重置用户密码
	 *
	 * @param rs
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "resetPassword")
	@ResponseBody
	public String resetPassword(ResultBean rs, String id) {
		try {
			PlatformUser user = userService.getUser(id);
			userService.resetPassword(user);
			userService.addSystemUser(user);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 停用用户，需要录入用户停用原因
	 *
	 * @param rs
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "stopUser")
	@ResponseBody
	public String stopUser(ResultBean rs, String id, String stopReason) {
		try {
			if (userService.isSupervisor(id)) {
				throw new ServiceException("不能停用超级管理员");
			}
			PlatformUser user = userService.getUser(id);
			user.setStopTime(new Date());
			user.setStopFlag(2);
			user.setStopReason(stopReason);
			userService.saveUser(user);
			removeSessionId(user.getId());
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	private void removeSessionId(String userId) {
		WebApplicationContext wac = ContextLoader
				.getCurrentWebApplicationContext();
		RedisCacheManager redisCacheManager = (RedisCacheManager) wac
				.getBean("redisCacheManager");
		Cache<Object, Object> cache = redisCacheManager
				.getCache("shiro-kickout-session");
		String sessionId = cache.get(userId).toString();
		cache.remove(sessionId);
	}

	/**
	 * 重新启用用户，停用标志为0,移除停用原因及时间
	 *
	 * @param rs
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "startUser")
	@ResponseBody
	public String startUser(ResultBean rs, String id) {
		try {
			PlatformUser user = userService.getUser(id);
			user.setStopTime(null);
			user.setStopFlag(1);
			user.setStopReason(null);
			userService.addSystemUser(user);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 解锁用户
	 *
	 * @param rs
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "unlockUser")
	@ResponseBody
	public String unlockUser(ResultBean rs, String id,
			HttpServletRequest request) {
		try {
			PlatformUser user = userService.getUser(id);
			user.setLockFlag(0);
			userService.addSystemUser(user);
			PlatformUserLoginLog log = new PlatformUserLoginLog();
			log.setUserId(user.getId());
			log.setSuccessFlag(1);
			log.setLoginTime(SysDate.getSysDate());
			String ip = request.getRemoteAddr();
			log.setLoginIp(ip);
			log.setFailReason(user.getNickname() + "UNLOCK");
			userService.insertLoginLog(log);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * <更改密码展示界面>
	 *
	 * @return
	 */
	@RequestMapping(value = "changePassword")
	public ModelAndView changePassword() {
		ModelAndView model = new ModelAndView();
		model.setViewName("system/user/change_password");
		return model;
	}

	/**
	 * <主管重置更改密码展示界面>
	 *
	 * @return
	 */
	@RequestMapping(value = "investorRePassword")
	public ModelAndView InvestorRePassword(String userName) {
		ModelAndView model = new ModelAndView();
		model.addObject("userName", userName);
		model.setViewName("system/user/Investor_repassword");
		return model;
	}

	@RequestMapping(value = "investorfinalPassword")
	@ResponseBody
	public String investorfinalPassword(ResultBean rs, String password,
			String newpassword, String userName, HttpServletRequest req) {
		try {
			// 获取当前用户
			/*
			 * ShiroUser user = getCurrentUser();
			 */PlatformUser systemUser = userService
					.findUserByUserName(userName);
			// PlatformUser systemUser = userService.getUser(user.getId());
			userService.changePassword(systemUser, password, newpassword);
			HttpSession session = req.getSession();
			session.setAttribute("pwd", "changed");
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * <用户更改自身密码>
	 *
	 * @param rs
	 * @param password
	 * @param newpassword
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "finalPassword")
	@ResponseBody
	public String finalPassword(ResultBean rs, HttpServletRequest req) {
		try {

			String newpassword = req.getParameter("newpassword");
			// 获取当前用户
			ShiroUser user = getCurrentUser();
			PlatformUser systemUser = userService.getUser(user.getId());
			HttpSession session = req.getSession();
			if ("02".equals(systemUser.getType()) || "0201".equals(systemUser.getType())) {
				CompanyInfoVo companyRestVo = new CompanyInfoVo();
				companyRestVo.setName(user.getNickname());
				companyRestVo.setCode(user.getName());
				companyRestVo.setUser_pwd(newpassword);
				Map maps = userService.addCompanyBaseUser(companyRestVo, "02");
				String validStatus=(String) maps.get("validStatus");//1 修改密码成功，0修改密码失败
				if(validStatus!=null){
					if("1".equals(validStatus)){//修改密码成功
						//调用接口修改密码成功，则进行本地的密码修改
						systemUser.setPlainPassword(newpassword);
						userService.entryptPassword(systemUser);
						session.setAttribute("pwd", "changed");
						rs = ResultBean.sucessResultBean();
					}else{//修改密码失败
						rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
					}
				}
			} else {
				// 去除原密码
				// 对输入原密码加密后与原密码比较
				systemUser.setPlainPassword(newpassword);
				userService.entryptPassword(systemUser);
				session.setAttribute("pwd", "changed");
				rs = ResultBean.sucessResultBean();
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	@RequestMapping(value = "getAjaxPassWord")
	@ResponseBody
	public String getAjaxPassWord(ResultBean rs, HttpServletRequest req) {
		try {

			String password = req.getParameter("password");
			String newpassword = req.getParameter("newpassword");
			// 获取当前用户
			ShiroUser user = getCurrentUser();
			PlatformUser systemUser = userService.getUser(user.getId());
			// 去除原密码
			String oldpassword = systemUser.getPassword();
			// 对输入原密码加密后与原密码比较
			byte[] salt = Encodes.decodeHex(systemUser.getSalt());
			byte[] hashPassword = Digests.sha1(password.getBytes(), salt, 1024);
			String hashPass = Encodes.encodeHex(hashPassword);
			// entryptPassword(user);
			// 如果不相同，抛出异常
			rs = ResultBean.sucessResultBean();
			if (oldpassword == null || !oldpassword.equals(hashPass)) {
				rs.setCode("00002");
			}
			if (userService.isHistoryPassword(systemUser, newpassword)) {// 历史记录最近三次相同
				rs.setCode("00003");
			}

		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * <用于返回角色列表与操作界面>
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "searchRole")
	public ModelAndView searchRole(String id) {
		ModelAndView model = new ModelAndView();
		model.setViewName("system/user/grant_role");
		try {
			// SystemUser user=userService.getUser(id);
			List<PlatformRole> list = roleService.getAllRole();
//			if (list != null && list.size() > 0) {
//				list.remove(0);
//			}
			List<PlatformRole> rolelist = new ArrayList<PlatformRole>();
			List<PlatformUserRole> list2 = roleService.getUserRoleRel(id);
			for (PlatformUserRole userRole : list2) {
				PlatformRole role = roleService.findOne(userRole.getRoleId());
				rolelist.add(role);
			}
			list.removeAll(rolelist);
			model.addObject("list", list);
			model.addObject("roleList", rolelist);
			model.addObject("id", id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return model;
	}

	/**
	 * 关联用户与角色
	 *
	 * @param rs
	 * @param userId
	 * @param select
	 * @return
	 */
	@RequestMapping(value = "relateRole")
	@ResponseBody
	public String relateRole(ResultBean rs, String userId, String select) {
		try {
			// 需要先删除原有的关联关系，然后存入新的关联关系
			roleService.DelAllRole(userId);
			if (select != null) {
				String[] str = select.split(",");
				for (String roleId : str) {
					roleService.InsertUserRoleRel(userId, roleId);
				}
			}
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	@RequestMapping(value = "MenuTree")
	@ResponseBody
	public String newMenu(HttpServletRequest req, HttpServletResponse rep) {
		List<PlatformMenu> menus = null;
		menus = getAllUserMenu();
		String json = JSON.toJSONString(menus,
				SerializerFeature.DisableCircularReferenceDetect);
		System.out.println("============json=========================" + json);
		return json;
	}
}

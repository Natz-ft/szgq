package com.icfcc.credit.platform.web.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.alibaba.fastjson.JSON;
import com.icfcc.credit.platform.constants.DD;
import com.icfcc.credit.platform.constants.VipCont;
import com.icfcc.credit.platform.constants.investorArea;
import com.icfcc.credit.platform.jpa.entity.system.IndustryVo;
import com.icfcc.credit.platform.jpa.entity.system.InvestorManageAchievement;
import com.icfcc.credit.platform.jpa.entity.system.InvestorSubaccount;
import com.icfcc.credit.platform.jpa.entity.system.PlatformRole;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUser;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserLoginLog;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserRole;
import com.icfcc.credit.platform.service.system.InvestorManageAchievementsService;
import com.icfcc.credit.platform.service.system.InvestorSubaccountService;
import com.icfcc.credit.platform.service.system.PlatformConfigService;
import com.icfcc.credit.platform.service.system.PlatformOrgService;
import com.icfcc.credit.platform.session.RedisCacheManager;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.credit.platform.util.SysDate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/inverstorUser")
public class InverstorgUserController extends PlatformBasicController {
	private static Logger log = LoggerFactory.getLogger(PlatformUserController.class);
	@Autowired
	private PlatformOrgService orgService;
	@Autowired
	private PlatformConfigService config;
	@Autowired      
	private InvestorSubaccountService investorSubaccountService;
	@Autowired      
	private InvestorManageAchievementsService investorManageAchievementsService;
	private static final String UNLOCK = "解锁用户";
	private static final String SUPER = "1";

	/**
	 * 用户分页信息
	 * 
	 * @param model
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userList")
	public String userList(Model model, PageBean page, HttpServletRequest request) {
		Page<PlatformUser> queryResults = null;
		try {
			Map<String, Object> searchParams = processRequestParams(page, request);
			ShiroUser SystemUser = userService.getCurrentNickName();
			searchParams.put("EQ1_createUser", SystemUser.getName());
			/*
			 * ShiroUser SystemUser=userService.getCurrentNickName();
			 * searchParams.put("EQ1_org", SystemUser.getOrg());
			 */
			queryResults = userService.getUsers(searchParams, page.getCurPage(), page.getMaxSize(), VipCont.DIRECTION,
					"createTime");
			processQueryResults(model, page, queryResults);
			// 以下用于用户列表中不显示超级管理员
			Iterator<PlatformUser> iterator = queryResults.iterator();
			while (iterator.hasNext()) {
				String userName = iterator.next().getUsername();
				if (SystemUser.getName().equals(userName)) {
					iterator.remove();
				}
			}
			List<PlatformUser> userList = (List<PlatformUser>) page.getList();
			List<PlatformUserLoginLog> successLog = new ArrayList<PlatformUserLoginLog>();
			if (null != userList) {
				for (PlatformUser user : userList) {
					// 获取登录历史信息，时间，ip，类型等,list存在两条记录，第一条为成功，第二条为失败
					List<PlatformUserLoginLog> loglist = userService.getLoginLog(user.getId());
					InvestorSubaccount subaccount=investorSubaccountService.findById(user.getId());
					user.setSubacName(subaccount.getSubacName());
					// 获取成功登录时间，并存入successLog，用于主界面展示上次成功登录时间
					successLog.add(loglist.get(0));
				}
			}
			model.addAttribute("successLog", successLog);
			// 获取机构
			Map<String, String> orgMap = orgService.getOrg();
			HttpSession session = request.getSession();
			session.setAttribute("org", orgMap);
			String password = config.getConfigValueByName("password");
			session.setAttribute("password", password);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "system/user/inverstorUserList";
	}

	@RequestMapping(value = "createInvestUser")
	public ModelAndView createPage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		// 获取拟投资阶段字典值集合
		List<DD> ddfinanceTrade = VipCont.getValueList("dd:industry");
		List<DD> ddfinanceStage = VipCont.getValueList("dd:investment");

		List<DD> ddsubacType = VipCont.getValueList("dd:subactype");
		List<DD> ddsubacForm = VipCont.getValueList("dd:subacform");
		List<DD> ddCurrency = VipCont.getValueList("dd:currency");
		List<investorArea> areaProvince=VipCont.getAeaDataList("dd:areaProvince");
		List<investorArea> areaCity=VipCont.getAeaDataList("dd:areaCity");
		List<investorArea> areaCounty=VipCont.getAeaDataList("dd:areaCounty");
		List<DD> ddcerttype = VipCont.getValueList("dd:certificate");
		
		model.addObject("areaProvince", net.sf.json.JSONArray.fromObject(areaProvince));
		model.addObject("areaCity", net.sf.json.JSONArray.fromObject(areaCity));
		model.addObject("areaCounty", net.sf.json.JSONArray.fromObject(areaCounty));
		model.addObject("ddcerttype", ddcerttype);
		model.addObject("ddCurrency", ddCurrency);
		model.addObject("ddsubacType", ddsubacType);
		model.addObject("ddsubacForm", ddsubacForm);
		model.addObject("ddfinanceTrade", ddfinanceTrade);
		model.addObject("ddfinanceStage", ddfinanceStage);
		model.setViewName("system/user/createInvestUser");
		// model.addObject("ddfinanceTrade",ddfinanceTrade);
		return model;
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
	public String addUser(ResultBean rs, HttpServletRequest request, HttpServletResponse response) {
		PlatformUser investUser = null;
		try {
			// 获取页面传来的参数
			String investorString = request.getParameter("investorData");
			InvestorSubaccount subaccount = null;
			if (investorString != null && !"".equals(investorString) && !"\"\"".equals(investorString)) {
				subaccount = (InvestorSubaccount) JSON.parseObject(investorString, InvestorSubaccount.class);
			}
			ShiroUser createUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			investUser = userService.findUserByUserName(subaccount.getUserName());
			if (investUser != null) {
				rs.setCode("00002");
			} else {
				investUser = new PlatformUser();
				investUser.setUsername(subaccount.getUserName());
				investUser.setNickname(subaccount.getUserName());
				investUser.setEmail(subaccount.getEmail());
				investUser.setTelephone(subaccount.getRelPhone());
				investUser.setCreateUser(createUser.getId());
				investUser.setType("01");
				investUser.setOrg(createUser.getOrg());
				investUser.setDesc1("1");
				investUser.setCreateUser(createUser.getName());
				investUser.setCreateTime(new Date());
				investUser = userService.addsubacUser(investUser);
				subaccount.setUserId(investUser.getId());
				investorSubaccountService.save(subaccount);
				rs = ResultBean.sucessResultBean();
				relateRole(investUser.getUsername());
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	public void relateRole(String userName) {
		try {
			PlatformUser user = userService.findUserByUserName(userName);
			// 需要先删除原有的关联关系，然后存入新的关联关系
			roleService.DelAllRole(user.getId());
			String roleId = config.getConfigValueByName("invesComUserRole");
			roleService.InveInsertUserRoleRel(user.getId(), roleId, user.getNickname());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用于新建页面，获取下拉列表信息，此处为机构信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "createPage")
	@ResponseBody
	public String addUser() {
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
		PlatformUser user = userService.getUser(id);
		// 获取拟投资阶段字典值集合
		List<DD> ddfinanceTrade = VipCont.getValueList("dd:industry");
		List<DD> ddfinanceStage = VipCont.getValueList("dd:investment");
		List<DD> ddsubacType = VipCont.getValueList("dd:subactype");
		List<DD> ddsubacForm = VipCont.getValueList("dd:subacform");
		List<DD> ddCurrency = VipCont.getValueList("dd:currency");
		List<investorArea> areaProvince=VipCont.getAeaDataList("dd:areaProvince");
		List<investorArea> areaCity=VipCont.getAeaDataList("dd:areaCity");
		List<investorArea> areaCounty=VipCont.getAeaDataList("dd:areaCounty");
		List<DD> ddcerttype = VipCont.getValueList("dd:certificate");
		InvestorSubaccount subac = investorSubaccountService.findById(id);

		List<IndustryVo> industryVo = checkIndustry(subac.getFinanceTrade());
		// 将investor中拟投资阶段与拟投资行业字典值切割并赋value值
		model.addObject("industryVo", net.sf.json.JSONArray.fromObject(industryVo).toString());
		model.addObject("ddCurrency", ddCurrency);
		model.addObject("ddsubacType", ddsubacType);
		model.addObject("ddsubacForm", ddsubacForm);
		model.addObject("ddfinanceTrade", ddfinanceTrade);
		model.addObject("ddfinanceStage", ddfinanceStage);
		model.addObject("areaProvince", net.sf.json.JSONArray.fromObject(areaProvince));
		model.addObject("areaCity", net.sf.json.JSONArray.fromObject(areaCity));
		model.addObject("areaCounty", net.sf.json.JSONArray.fromObject(areaCounty));
		model.addObject("ddcerttype", ddcerttype);
		model.setViewName("system/user/createInvestUser");
		model.addObject("subac", subac);
		model.addObject("user", user);
		model.setViewName("system/user/InvestUserDetail");
		return model;
	}

	// 回显行业二级
	public List<IndustryVo> checkIndustry(String industryStr) {
		List<DD> ddfinanceTrade = VipCont.getValueList("dd:industry");
		List<DD> ddfinanceTrade2 = VipCont.getValueList("dd:industry2");
		String[] values = industryStr.toString().split(",");
		List<String> industryStrList = java.util.Arrays.asList(values);
		List<IndustryVo> list = new ArrayList<IndustryVo>();
		for (String industry : industryStrList) {
			IndustryVo vo = new IndustryVo();
			if (industry.length() == 2) {
				String name = VipCont.getValueByCode("dd:industry", industry);
				vo.setId(industry);
				vo.setName(name);
			} else {
				String name = VipCont.getValueByCode("dd:industry2", industry);
				vo.setId(industry);
				vo.setName(name);
			}
			list.add(vo);
		}
		return list;
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
	public String updateUser(ResultBean rs, HttpServletRequest request) {
		try {

			// 获取页面传来的参数
			String investorString = request.getParameter("investorData");
			InvestorSubaccount subaccount = null;
			if (investorString != null && !"".equals(investorString) && !"\"\"".equals(investorString)) {
				subaccount = (InvestorSubaccount) JSON.parseObject(investorString, InvestorSubaccount.class);
			}
			ShiroUser createUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			PlatformUser investUser = null;
			investUser = userService.findUserByUserName(subaccount.getUserName());
			investUser.setNickname(subaccount.getUserName());
			investUser.setUpdateUser(createUser.getName());
			investUser.setUpdateTime(new Date());
			userService.updateSystemUser(investUser);
			subaccount.setUserId(investUser.getId());
			System.out.println("id=====================" + subaccount.getSubac_id());
			investorSubaccountService.update(subaccount);
			rs = ResultBean.sucessResultBean();
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}

	/**
	 * 动态验证
	 * 
	 * @param
	 */
	@RequestMapping(value = "/ajaxValidatorUseName")
	@ResponseBody
	public String ajaxValidatorUseName(HttpServletRequest request, HttpServletResponse respons) {
		ResultBean rs = null;
		try {
			// 判定是否在业务库存在
			String username = request.getParameter("userName");
			PlatformUser isExist = userService.findUserByUserName(username);
			System.out.println("username=======:" + username);
			if (isExist != null) {
				rs = new ResultBean(Constant.ERRORCODE, "请勿重复注册！");
			} else {
				rs = new ResultBean(Constant.SUCCESSCODE, "可以注册！");
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 动态验证
	 * 
	 * @param
	 */
	@RequestMapping(value = "/ajaxValidatorSubacName")
	@ResponseBody
	public String ajaxValidatorSubacName(HttpServletRequest request, HttpServletResponse respons) {
		ResultBean rs = null;
		try {
			// 判定是否在业务库存在
			String subacName = request.getParameter("subacName");
			ShiroUser createUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			String orgId = createUser.getOrg();
			PlatformUser isExist = userService.findUserBySubacNameInfo(subacName,orgId);
			if (isExist != null) {
				rs = new ResultBean(Constant.ERRORCODE, "请勿重复注册！");
			} else {
				rs = new ResultBean(Constant.SUCCESSCODE, "可以注册！");
			}
		} catch (Exception e) {
			rs = new ResultBean(Constant.ERRORCODE, Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
     * 动态验证
     * 
     * @param
     */
    @RequestMapping(value = "/ajaxValidatorCertno")
    @ResponseBody
    public String ajaxValidatorCertno(HttpServletRequest request, HttpServletResponse respons) {
        ResultBean rs = null;
        try {
            // 判定是否在业务库存在
            String certno = request.getParameter("certno");
            PlatformUser isExist = userService.findUserByUserName(certno);
            if (isExist != null) {
                rs = new ResultBean(Constant.ERRORCODE, "请勿重复注册！");
            } else {
                rs = new ResultBean(Constant.SUCCESSCODE, "可以注册！");
            }
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
					// investorSubaccountService.deleteById(ids[i]);
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
			String password = config.getConfigValueByName("password");
			rs = ResultBean.sucessResultBean();
			rs.setValue(password);
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
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		RedisCacheManager redisCacheManager = (RedisCacheManager) wac.getBean("redisCacheManager");
		Cache<Object, Object> cache = redisCacheManager.getCache("shiro-kickout-session");
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
	public String unlockUser(ResultBean rs, String id, HttpServletRequest request) {
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
	public String finalPassword(ResultBean rs, String password, String newpassword, HttpServletRequest req) {
		try {
			// 获取当前用户
			ShiroUser user = getCurrentUser();
			PlatformUser systemUser = userService.getUser(user.getId());
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
			if (list != null && list.size() > 0) {
				list.remove(0);
			}
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

	/**
	 * 填写基金名称  基金存在回显该基金信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "subacNameValue")
	@ResponseBody
	public String findInvestorDetail(HttpServletRequest request,HttpServletResponse respons) {
		InvestorManageAchievement investUser = null;
		try {
			String inputValue = request.getParameter("inputValue");
			ShiroUser createUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			String orgId = createUser.getOrg();
			investUser = investorManageAchievementsService.findUserBySubacName(inputValue, orgId);
			if(investUser!=null){
				List<IndustryVo> industryVo = new ArrayList<IndustryVo>();
				industryVo = checkIndustry(investUser.getInvestTrade());
				investUser.setIndustryVo(net.sf.json.JSONArray.fromObject(industryVo).toString());
				investUser.setCode(Constant.SUCCESSCODE);
			}else{
				investUser=new InvestorManageAchievement();
				investUser.setCode(Constant.ERRORCODE);
			}
		} catch (Exception e) {
			if(investUser!=null){
				investUser.setCode(Constant.ERRORCODE);
			}else{
				investUser=new InvestorManageAchievement();
				investUser.setCode(Constant.ERRORCODE);
			}
			log.error(e.getMessage());
		}
		return investUser.toJSONStr();
	}
	
}
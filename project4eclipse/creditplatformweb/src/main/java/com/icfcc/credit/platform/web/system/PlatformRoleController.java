package com.icfcc.credit.platform.web.system;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icfcc.credit.platform.jpa.entity.system.PlatformButton;
import com.icfcc.credit.platform.jpa.entity.system.PlatformMenu;
import com.icfcc.credit.platform.jpa.entity.system.PlatformRole;
import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleButton;
import com.icfcc.credit.platform.jpa.entity.system.PlatformRoleMenu;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUser;
import com.icfcc.credit.platform.service.system.PlatformButtonService;
import com.icfcc.credit.platform.service.system.PlatformRoleButtonService;
import com.icfcc.credit.platform.service.system.PlatformRoleService;
import com.icfcc.credit.platform.util.Constant;
import com.icfcc.credit.platform.util.PageBean;
import com.icfcc.credit.platform.util.ResultBean;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.credit.platform.util.SysDate;

@Slf4j
@Controller
@Scope("prototype")
@RequestMapping(value="roleManage")
public class PlatformRoleController extends PlatformBasicController {
	@Autowired
	PlatformRoleService systemRoleService;
	@Autowired
	PlatformButtonService systemButtonService;
	
	@Autowired
    protected PlatformRoleButtonService systemRoleButtonService;
	
	private static Logger log = LoggerFactory.getLogger(PlatformConfigController.class);
	private static final String SUPER="1";
	private static final String ADMINROLEID = "47";
    private static final String SYSTEMROLEID = "50";
	/**
	 * 角色列表获取方法
	 * @return
	 */
	@RequestMapping(value="roleList")
	public String roleList(Model model,PageBean page,HttpServletRequest request){
		Page<PlatformRole>  queryResults = null;
		try{
	        Map<String, Object> searchParams = processRequestParams(page,request);
//	        ShiroUser SystemUser=userService.getCurrentNickName();
//	        searchParams.put("EQ_createUser", SystemUser.getName());
	        queryResults = systemRoleService.getRoleList(searchParams, page.getCurPage(),page.getMaxSize() );
		}catch(Exception e){
			log.error(e.getMessage());
		}
		processQueryResults(model,page, queryResults);
		//以下用于用户列表中不显示超级管理员
//		Iterator<PlatformRole> iterator = queryResults.iterator();
//		while(iterator.hasNext()){
//			Long id = iterator.next().getId();
//			if(SUPER.equals(id)){
//				iterator.remove();
//			}
//		}
		return "system/role/roleList";
	}
	/**
	 * 系统权限的赋予方法
	 * @param id
	 * @return
	 */
	
	@RequestMapping(value="empower")
	public ModelAndView empower(String id){
		ModelAndView model=new ModelAndView();
		model.setViewName("system/role/empower");
		//通过角色id，查找拥有的菜单
		List<PlatformRoleMenu> list=systemRoleService.getRoleMenuRel(Long.parseLong(id));
		List<PlatformMenu> menulist=new ArrayList<PlatformMenu>();
		//循环通过rolemenu之间的关系，拿到当前角色所拥有的菜单，并存到menulist中
		for(PlatformRoleMenu roleMenu:list){
			String menuId=roleMenu.getMenuId();
			PlatformMenu menu=systemMenuService.getMenuById(menuId);
			menulist.add(menu);
		}
		//系统拥有的权限，此处用模拟
		List<PlatformMenu> trees = new ArrayList<PlatformMenu>();
		List<PlatformRoleMenu> treesList=null;
		ShiroUser SystemUser=userService.getCurrentNickName();
		 if(SUPER.equals(SystemUser.getId())){
			 trees=systemMenuService.getAllMenuList();
	        }
//		 else{
//				treesList=systemRoleService.getRoleMenuRel(Long.parseLong(SYSTEMROLEID));
//
//	        }
//			for(PlatformRoleMenu roleMenu:treesList){
//				String menuId=roleMenu.getMenuId();
//				PlatformMenu menu=systemMenuService.getMenuById(menuId);
//				trees.add(menu);
//			}
		trees = systemMenuService.getAllMenuList();
		model.addObject("roleId", id);
    	model.addObject("menus", trees);
    	model.addObject("menulist", menulist);
    	//获取所选择角色的菜单
		return model;
	}
	/**
	 * 按钮权限的赋予方法
	 * @param id
	 * @return
	 */
	
	@RequestMapping(value="empowerButton")
	public ModelAndView empowerbutton(String id){
		ModelAndView model=new ModelAndView();
	
		model.setViewName("system/role/empowerButton");
		//通过角色id，查找拥有的按钮
		List<PlatformRoleButton> list=systemRoleService.getRolebuttonRel(Long.parseLong(id));
		List<PlatformButton> buttonlist=new ArrayList<PlatformButton>();
		//循环通过rolebutton之间的关系，拿到当前角色所拥有的按钮，并存到buttonlist中
		for(PlatformRoleButton rolebutton:list){
			String buttonCode = rolebutton.getButtonCode();
			PlatformButton button = systemButtonService.findOneByButtonCode(buttonCode);
			buttonlist.add(button);
		}
		//系统拥有的权限，此处用模拟
		List<PlatformButton> trees = null;
		try{
			trees = systemButtonService.getAllButtonList();
		}catch(Exception e){
			log.error("empowerButton:"+e.getMessage());
		}
		model.addObject("roleId", id);
		model.addObject("buttons", trees);
		model.addObject("buttonlist", buttonlist);
		//获取所选择角色的菜单
		return model;
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="grantPower")
	@ResponseBody
	public String grantPower(ResultBean rs,String id){
		try{
			ShiroUser user = getCurrentUser();
			String[] ids=id.split(",");
			//ids[0]表示角色id
			Long roleId=Long.parseLong(ids[0]);
			systemRoleMenuService.deleteMenuByRoleID(roleId);
			for(int i=1;i<ids.length;i++){
				PlatformRoleMenu roleMenu=new PlatformRoleMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(ids[i]);
				roleMenu.setCreateTime(SysDate.getSysDate());
				roleMenu.setCreateUser(user.getName());
				systemRoleMenuService.addRoleMenu(roleMenu);
			}
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error("grantPower:"+e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="grantPowerButton")
	@ResponseBody
	public String grantPowerButton(ResultBean rs,String id){
		try{
			ShiroUser user = getCurrentUser();
			String[] ids=id.split(",");
			//ids[0]表示角色id
			Long roleId=Long.parseLong(ids[0]);
			systemRoleButtonService.deleteButtonByRoleID(roleId);
			for(int i=1;i<ids.length;i++){
				PlatformButton button = systemButtonService.findByButtonId(Long.parseLong(ids[i]));
				String buttonCode = button.getButtonCode();
				PlatformRoleButton roleButton=new PlatformRoleButton();
				roleButton.setRoleId(roleId);
				roleButton.setButtonCode(buttonCode);
				roleButton.setCreateTime(new Date());
				roleButton.setCreateUser(user.getName());
				systemRoleButtonService.addRoleButton(roleButton);
			}
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error("grantPower:"+e.getMessage());
		}
		return rs.toJSONStr();
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value="insertRole")
	@ResponseBody
	public String insertRole(ResultBean rs,PlatformRole role){
		try{
			//获取当前用户与时间，赋给新角色的创建人和时间
			ShiroUser user=getCurrentUser();
			String createUser=user.getName();
			role.setCreateUser(createUser);
			role.setCreateTime(new Date());
			systemRoleService.saveRole(role);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error("insertRole:"+e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 删除角色，一次删除一组角色,删除完成后跳转到角色列表页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delRole")
	@ResponseBody
	public String delRole(ResultBean rs,String id){
		String[] ids=id.split(",");
		try{
			for(int i=0;i<ids.length;i++){
				if(StringUtils.isNotBlank(ids[i])){
					Long sid=Long.parseLong(ids[i]);
						systemRoleService.deleteRole(sid);
				}
			}
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error(e.getMessage());
		}
		return rs.toJSONStr();
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="updateRole")
	public ModelAndView updateRole(String id){
		ModelAndView model=new ModelAndView();
		model.setViewName("system/role/update_page");;
		PlatformRole role=systemRoleService.getRole(Long.parseLong(id));
		model.addObject("role", role);
		return model;
	}
	/**
	 * 
	 * @param req
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="finalRole")
	@ResponseBody
	public String finalRole(ResultBean rs,PlatformRole role){
		try{
			role.setUpdateTime(new Date());
			systemRoleService.saveRole(role);
			rs = ResultBean.sucessResultBean();
		}catch(Exception e){
			rs = new ResultBean(Constant.ERRORCODE,Constant.ERRORMSG);
			log.error("finalRole:"+e.getMessage());
		}
		return rs.toJSONStr();
	}
}

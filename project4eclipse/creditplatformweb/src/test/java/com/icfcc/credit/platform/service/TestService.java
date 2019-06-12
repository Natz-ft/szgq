package com.icfcc.credit.platform.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Splitter;
import com.icfcc.credit.platform.jpa.entity.system.PlatformMenu;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUser;
import com.icfcc.credit.platform.service.system.PlatformMenuService;
import com.icfcc.credit.platform.service.system.PlatformRoleService;
import com.icfcc.credit.platform.service.system.PlatformUserService;
import com.icfcc.credit.platform.util.MD5;
import com.icfcc.credit.platform.util.ShiroUser;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestService {
	 @Autowired
	    protected PlatformMenuService sysMenuService;
	 
	
	 @Autowired
		protected PlatformUserService userService;
	 @Autowired
	    protected PlatformRoleService roleService;
	@Test
	public void test(){
		System.out.println("=====================================");
		System.out.println(MD5.MD5("abc").equals("900150983cd24fb0d6963f7d28e17f72"));
		System.out.println("=====================================");
	}
	@Test
	public void test1(){
		List<PlatformMenu> menus = getAllUserMenu();
	    String json = JSON.toJSONString(menus, SerializerFeature.DisableCircularReferenceDetect);

		System.out.println("====================================="+json);
	}
   public List<PlatformMenu> getAllUserMenu() {
    	
    	List<PlatformMenu> menus = null;
    	boolean falag=false;
    	menus = sysMenuService.getMenuByUserId("2c90ee455f527fe3015f531d1637004c",falag);//用户id
    	
		return menus;
	}
public List<PlatformMenu> getSubMenuAndSelfNodeList( List<PlatformMenu>menus, String menuId){
	   List<PlatformMenu> menuList =  new ArrayList<PlatformMenu>();
	   List<PlatformMenu> listChildMenu=  new ArrayList<PlatformMenu>();
    	for(PlatformMenu menu : menus){
    		
    		if(menuId.equals(menu.getId())){
    			List<PlatformMenu> list=  new ArrayList<PlatformMenu>();
    			//添加根节点
    			list.add(menu);
    			//获取一级子节点
    			List<PlatformMenu> subList = menu.getChildren();
    			if(null!= subList && subList.size() >0) {
    				//添加一级子节点
    				list.addAll(subList);
    				for(PlatformMenu submenu : subList){
    					//获取二级子节点
    					List<PlatformMenu>  grandson = submenu.getChildren();
    					if(null!=grandson && grandson.size() >0) {
    						//添加二级子节点
    						list.addAll(grandson);
    						for(PlatformMenu mostson:grandson){
    							//获取三级子节点
    							List<PlatformMenu> most=mostson.getChildren();
    							if(null!=most && most.size() >0){
    								//添加三级子节点
    								list.addAll(most);
    							}
    						}
    					}
    				}
    			}
    			return list;
    		}
    		List<PlatformMenu> childMenus = menu.getChildren();
    		if(null!= childMenus){
    			getSubMenuAndSelfNodeList(childMenus,menuId);
    		}
    	}
    	return null;
    }
		@Test
		public void addUser(){
			
			String userStr="jrbxiangcheng01,相城区金融办,03,320507,400-8285065,92d2f1337c6f12a54c7f607f409cb9db*jrbkunshan01,昆山市金融办,03,320583,400-8285065,46df8ed67b0fcdd71177c7f03a11dcd5*jrbzhjg01,张家港市金融办,03,320582,400-8285065,1c56062ca2fc5d341d8a36ce87df7cd5*jrbyuanqu01,工业园区金融办,03,320599,400-8285065,0b6465fdd02239a80d07294bea9f1603*jrbwuzhong01,吴中区金融办,03,320506,400-8285065,118b35548711f0ac5bf6aabca380ed2b*jrbtaicang01,太仓市金融办,03,320585,400-828506,1348193107a569d500d1da778551419a*jrbgaoxin01,高新区金融办,03,320505,400-8285065,19c1228e779d1d869b5f285d8912f639*jrbwujiang01,吴江区金融办,03,320584,400-8285065,0c220f0de88fce432b5ac42e9e355417*jrbchangshu01,常熟市金融办,03,320581,400-8285065,ac1a35ce7a178dc327c9d8653c009e26*jrbgusu01,姑苏区金融办,03,320508,400-8285065,4e91201f6459cd76f4333fbc1b395c95";
//			String userStr="zqd001,周勤第,04,400-8285065,d60ec2de554c0cb3ba740444df613e68*jrbzl01,张黎,04,13913506796,797cb93f8b1159e6dc68b2b7fddd6c55*zhouqd,管理员,04,400-8285065,eabd8ce9404507aa8c22714d3f5eada9*jrbszr01,宋主任,04,400-8285065,cef1872e216f04655ab4f9a9d5c4f777*jrblzx01,刘钟潇,04,15962218166,02e29d26495dcf2d4cfe535fe47e486a*jrbxjj01,许晶晶,04,13656216929,1df2d998afbe73f27209dcc903cd5ca6*jrbcl01,陈亮,04,18888888888,613b32e204b70322186d65230ee52632*jrbhjc01,环境处,04,400-8285065,7db4e19c401c5c224fb0d42c3748079d*jrbjjh01,金吉鸿,04,18888888888,430dbe4b3dfdcc54a41992163dae1e44*luss,陆姗姗,04,13771817917,5104916dea2605a5f4dde98776c87467*mcszzyl01,张云莲,04,400-8285065,2739b4928009a799814bd18571753d9e*jrblmq01,陆名琦,04,13405159701,88094541648560dd82aa25f688e7a0d7*jrbzy01,朱瑛,04,18888888888,e154c4af916d1a4bfec6d386dfe258b6";

			List<String> result = Splitter.on("*").trimResults().splitToList(userStr); 
			for(String use:result){
				List<String> result1 = Splitter.on(",").trimResults().splitToList(use); 
				PlatformUser user=new PlatformUser();
				user.setUsername(result1.get(0));
				user.setNickname(result1.get(1));
				user.setOrg(result1.get(2));
				user.setDesc3(result1.get(3));
				user.setTelephone(result1.get(4));
				user.setPlainPassword(result1.get(5));
				user.setCreateUser("system");
				user.setCreateTime(new Date());
				user.setType("04");// 其他用户
				try {
//					PlatformUser userNew=userService.addSystemUser1(user);
//					roleService.DelAllRole(userNew.getId());
//			        roleService.InveInsertUserRoleRel(userNew.getId(), "23",userNew.getNickname());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			

			
		 }
		public void relateRole(String userName) {//赋予用户角色
	        try {
	        	PlatformUser user=userService.findUserByUserName(userName);
	            //需要先删除原有的关联关系，然后存入新的关联关系
	            roleService.DelAllRole(user.getId());
//	            String roleId="54";
	            roleService.InveInsertUserRoleRel(user.getId(), "23",user.getNickname());
	        } catch (Exception e) {
	           e.printStackTrace();
	        }
	    }
}

package com.icfcc.credit.platform.service.system;

import com.icfcc.credit.platform.exception.CaptchaException;
import com.icfcc.credit.platform.exception.StopUserException;
import com.icfcc.credit.platform.exception.SuCompanyUserErrorException;
import com.icfcc.credit.platform.jpa.entity.query.CompanyBase;
import com.icfcc.credit.platform.jpa.entity.system.CompanyInfoVo;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUser;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserLoginLog;
import com.icfcc.credit.platform.security.UsernamePasswordCaptchaToken;
import com.icfcc.credit.platform.service.business.CompanyInfoService;
import com.icfcc.credit.platform.util.Digests;
import com.icfcc.credit.platform.util.Encodes;
import com.icfcc.credit.platform.util.ShiroUser;
import com.icfcc.credit.platform.web.system.CheckCodeServlet;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


public class ShiroDbRealm extends AuthorizingRealm {
	
	private static final String INVALID_PWD_ERR_MSG = "用户名或密码不正确";

	private static final String INVALID_CAPTCHA_ERR_MSG = "验证码错误";
	
	private static final String STOP_USER="账号已被停用，请联系管理员";

	private static final String SIMPLE_PASS="密码强度弱，已拒绝登录，请修改密码后登录";
	
	private static final String LOCK_USER="账号长时间未登录已被锁定,请联系管理员解锁";
	
	private static final String TOOMANY_TIMES_WRONG="由于您连续输入错误密码，账户已被锁定";
	
	private static final String SUUSER_NOSTUTS_ERR_MSG = "此用户在金服平台未审核，待审核通过后登陆；";

	@Autowired
	private PlatformUserService userService;
	@Autowired
    protected PlatformRoleService roleService;
	@Autowired
	private PlatformConfigService config;
	@Autowired
	private CompanyInfoService companyInfoService;
	public void setUserService(PlatformUserService userService) {
		this.userService = userService;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
//		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
//		SystemUser user=userService.findUserByUserName(shiroUser.name);
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		return info;
	}
	
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PlatformUserService.HASH_ALGORITHM);
		matcher.setHashIterations(PlatformUserService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authcToken;
		System.out.println("begin login ...."+new Date());
		String username = token.getUsername();
		String passOrg = token.getPassword1();
		String validStatus=null;
		String result=null;
		String source=null;
		if (null == username) {
			throw new AccountException(INVALID_PWD_ERR_MSG);
		}
		
		// 增加判断验证码逻辑
		String captcha = token.getCaptcha();
		String exitCode = (String) SecurityUtils.getSubject().getSession()
				.getAttribute(CheckCodeServlet.KEY_CAPTCHA);
		if (null == captcha || !captcha.equalsIgnoreCase(exitCode)) {
			throw new CaptchaException(INVALID_CAPTCHA_ERR_MSG);
		}

		PlatformUser user = userService.findUserByUserName(username);
		PlatformUserLoginLog login=new PlatformUserLoginLog();
		login.setLoginTime(new Date());
		ServletRequest request = ((WebSubject)SecurityUtils.getSubject()).getServletRequest();
		String ip=request.getRemoteAddr();
		login.setLoginIp(ip);
		SimpleAuthenticationInfo simpleAuthenticationInfo=null;

		if ( null!= user) {
			login.setUserId(user.getId());
			login.setUserType(user.getType());
			if(user.getType().contains("02")){//企业用户登陆验证
				//如果用户存在，根据用户id，记录登录信息
				CompanyInfoVo companyRestVo=new CompanyInfoVo();
			    companyRestVo.setCode(username);
			    companyRestVo.setUser_pwd(String.valueOf(token.getPassword()));
			    System.out.println("=====================开始远程认证验证:==="+new Date());
				Map maps = userService.addCompanyBaseUser(companyRestVo,"03");//远程认证
			    System.out.println("=====================结束远程认证验证:==="+new Date());
				validStatus=(String) maps.get("validStatus");//验证   成功，用户不存在
				result=(String) maps.get("result");//0 未注册  1未注册  2.审核通过
				source=(String) maps.get("source");//数据来源  0 金服	  1 股权平台
				if(validStatus!=null){
					if("0".equals(validStatus)){//判断验证状态
						if("0".equals(source)){//金服平台
							if(result.equals("1")){//审核未通过
								System.out.println("用户验证登陆失败原因：在金服平台未审核,请待审核通过。");
								login.setFailReason(SUUSER_NOSTUTS_ERR_MSG);
								userService.insertLoginLog(login);
								throw new SuCompanyUserErrorException(SUUSER_NOSTUTS_ERR_MSG);
							}
						}
						if(result!=null){
							if(result.equals("0")){//未注册
								System.out.println("用户验证登陆失败原因：未注册");
								login.setFailReason(INVALID_PWD_ERR_MSG);
								userService.insertLoginLog(login);
								throw new AccountException(INVALID_PWD_ERR_MSG);
							}
							if(result.equals("5")){//停用
	                           login.setFailReason(STOP_USER);
	                          userService.insertLoginLog(login);
	                          throw new StopUserException(STOP_USER);
							}
						}else{
							login.setFailReason(INVALID_PWD_ERR_MSG);
							userService.insertLoginLog(login);
							throw new AccountException(INVALID_PWD_ERR_MSG);						}
						
					}else{

						login.setFailReason(INVALID_PWD_ERR_MSG);
						userService.insertLoginLog(login);
						byte[] salt = Digests.generateSalt(8);
						String s = String.valueOf(token.getPassword());
				        byte[] hashPassword = Digests.sha1(s.getBytes(), salt, 1024);
						String password=Encodes.encodeHex(hashPassword);
						user.setPassword(password);
				    	user.setSalt(Encodes.encodeHex(salt));
				    	userService.saveUser(user);

						ShiroUser shiroUser = new ShiroUser(user.getId(),user.getNickname(), user.getUsername(), user.getOrg());
						simpleAuthenticationInfo=new SimpleAuthenticationInfo(shiroUser,password,ByteSource.Util.bytes(salt), getName());
					}
				}
				
			}else{//其他用户登陆验证
				//userService.tooManyWrong(user);
				//需要获取ip
				
				//验证锁定与停用标志
				if(checkStop(user)){
					login.setFailReason(STOP_USER);
					userService.insertLoginLog(login);
					throw new StopUserException(STOP_USER);
				}else{
					
				}
			}
			
			
			//这里先认为登录失败，那么肯定是密码错误，如果成功登录，会在controller里边改变failReason
			login.setFailReason(INVALID_PWD_ERR_MSG);
			userService.insertLoginLog(login);
			byte[] salt = Encodes.decodeHex(user.getSalt());
			System.out.println("end login ...."+new Date());

			ShiroUser shiroUser = new ShiroUser(user.getId(),user.getNickname(), user.getUsername(), user.getOrg());
			simpleAuthenticationInfo = new SimpleAuthenticationInfo(shiroUser, user.getPassword(),ByteSource.Util.bytes(salt), getName());

		}else{
			CompanyInfoVo companyRestVo=new CompanyInfoVo();
		    companyRestVo.setCode(username);
		    companyRestVo.setUser_pwd(String.valueOf(token.getPassword()));
			Map maps = userService.addCompanyBaseUser(companyRestVo,"03");//远程认证
			validStatus=(String) maps.get("validStatus");//验证   成功，用户不存在
			result=(String) maps.get("result");//0 未注册  1未注册  2.审核通过
			source=(String) maps.get("source");//数据来源  0 金服	  1 股权平台
			System.out.println("validStatus:=================="+validStatus);
			System.out.println("result:======================="+result);
			System.out.println("source:======================="+source);
			if(validStatus!=null){
				if("0".equals(validStatus)){
					if("0".equals(source)){//金服平台
						if(result.equals("1")){//审核未通过
							System.out.println("用户验证登陆失败原因：在金服平台未审核,请待审核通过。");
							login.setFailReason(SUUSER_NOSTUTS_ERR_MSG);
							userService.insertLoginLog(login);
							throw new SuCompanyUserErrorException(SUUSER_NOSTUTS_ERR_MSG);
						}
					}
					if(result!=null){
						if(result.equals("0")){//未注册
							System.out.println("用户验证登陆失败原因：未注册");
							login.setFailReason(INVALID_PWD_ERR_MSG);
							userService.insertLoginLog(login);
							throw new AccountException(INVALID_PWD_ERR_MSG);
						}
						if(result.equals("5")){//停用
                            login.setFailReason(STOP_USER);
                           userService.insertLoginLog(login);
                           throw new StopUserException(STOP_USER);
                         }
					}else{//密码错误
						login.setFailReason(INVALID_PWD_ERR_MSG);
						userService.insertLoginLog(login);
						throw new AccountException(INVALID_PWD_ERR_MSG);		
						}
					
				}else{//成功登陆
					CompanyBase baseInfo =companyInfoService.queryByCertno(username);
					if(baseInfo!=null){
						String newUserId = UUID.randomUUID().toString();
						byte[] salt = Digests.generateSalt(8);
						String s = String.valueOf(token.getPassword());
				        byte[] hashPassword = Digests.sha1(s.getBytes(), salt, 1024);
						String password=Encodes.encodeHex(hashPassword);
						user = new PlatformUser();
//						user.setId(newUserId);
						user.setOrg(baseInfo.getEnterpriseId());
						user.setStopFlag(1);
						user.setUsername(username);//用户名
						user.setNickname(baseInfo.getName());//用户昵称
				    	user.setCreateTime(new Date());//创建时间
				    	user.setResetFlag(1);//是否重新修改密码
				    	user.setPwdUpdateTime(new Date());
				    	user.setUserSource("02");//来源
				    	user.setType("0201");//用户类型 机构用户
				    	user.setDesc2("01");
				    	user.setPassword(password);
				    	user.setSalt(Encodes.encodeHex(salt));
				    	user=userService.saveEnterPriseUser(user);
						relateRole(username);
						login.setUserId(user.getId());
						login.setUserType(user.getType());
						login.setFailReason(INVALID_PWD_ERR_MSG);
						userService.insertLoginLog(login);

						ShiroUser shiroUser = new ShiroUser(user.getId(),user.getNickname(), user.getUsername(), user.getOrg());
						simpleAuthenticationInfo=new SimpleAuthenticationInfo(shiroUser,password,ByteSource.Util.bytes(salt), getName());
					}else{
						throw new AccountException(INVALID_PWD_ERR_MSG);	
					}
					
				}
			}
			System.out.println("end login ...."+new Date());
		}
		return simpleAuthenticationInfo;
	}
	 public void relateRole(String userName) {
	        try {
	        	PlatformUser user=userService.findUserByUserName(userName);
	            //需要先删除原有的关联关系，然后存入新的关联关系
	            roleService.DelAllRole(user.getId());
	            String roleId=config.getConfigValueByName("entpriseUserRole");
	            roleService.InveInsertUserRoleRel(user.getId(), roleId,user.getNickname());
	        } catch (Exception e) {
	           e.printStackTrace();
	        }
		}
	private boolean checkStop(PlatformUser user){
		if(user.getStopFlag()==null ||user.getStopFlag()==1){
			return false;
		}
    	return true;
    }
	
	
	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
//	public static class ShiroUser implements Serializable {
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 4655974944456405926L;
//
//		public String id;
//
//		public String name;
//		
//		public String nickname;
//		
//		public ShiroUser(String id, String nickname, String name) {
//			super();
//			this.id = id;
//			this.nickname = nickname;
//			this.name = name;
//		}
//
//		public String getId() {
//			return id;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public String getNickname() {
//			return nickname;
//		}
//		
//		/**
//		 * 本函数输出将作为默认的<shiro:principal/>输出.
//		 */
//		@Override
//		public String toString() {
//			return nickname;
//		}
//		/**
//		 * 重载hashCode,只计算nickname;
//		 */
//		@Override
//		public int hashCode() {
//			return Objects.hashCode(nickname);
//		}
//		/**
//		 * 重载equals,只计算nickname;
//		 */
//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj) {
//				return true;
//			}
//			if (obj == null) {
//				return false;
//			}
//			if (getClass() != obj.getClass()) {
//				return false;
//			}
//			ShiroUser other = (ShiroUser) obj;
//			if (nickname == null) {
//				if (other.nickname != null) {
//					return false;
//				}
//			} else if (!nickname.equals(other.nickname)) {
//				return false;
//			}
//			return true;
//		}
//	}


	/**
	 * 密码强度
	 *
	 * @return Z = 字母 S = 数字 T = 特殊字符
	 */

    /*
    一、假定密码字符数范围6-16，除英文数字和字母外的字符都视为特殊字符：
    弱：^[0-9A-Za-z]{6,16}$
    中：^(?=.{6,16})[0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z]*$
    强：^(?=.{6,16})([0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z]*){2,}$
    二、假定密码字符数范围6-16，密码字符允许范围为ASCII码表字符：
    弱：^[0-9A-Za-z]{6,16}$
    中：^(?=.{6,16})[0-9A-Za-z]*[\x00-\x2f\x3A-\x40\x5B-\xFF][0-9A-Za-z]*$
    强：^(?=.{6,16})([0-9A-Za-z]*[\x00-\x2F\x3A-\x40\x5B-\xFF][0-9A-Za-z]*){2,}$
    */
	public String checkPassword(String passwordStr) {
		String regexZ = "\\d*";
		String regexS = "[a-zA-Z]+";
		String regexT = "\\W+$";
		String regexZT = "\\D*";
		String regexST = "[\\d\\W]*";
		String regexZS = "\\w*";
		String regexZST = "[\\w\\W]*";

		if (passwordStr.matches(regexZ)||passwordStr.matches(regexS)||passwordStr.matches(regexT)) {
			return "弱";
		}
		if (passwordStr.matches(regexZT)||passwordStr.matches(regexST)||passwordStr.matches(regexZS)) {
			return "中";
		}
		if (passwordStr.matches(regexZST)) {
			return "强";
		}
		return "强";
	}

}



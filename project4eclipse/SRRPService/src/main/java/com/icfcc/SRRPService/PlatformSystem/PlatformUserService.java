package com.icfcc.SRRPService.PlatformSystem;

import com.alibaba.fastjson.JSON;
import com.icfcc.SRRPDao.jpa.entity.enterprise.CompanyInfoVo;
import com.icfcc.SRRPDao.jpa.entity.platformSystem.*;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformUserActionLogDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformUserDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformUserHistoryPasswordDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformUserLoginLogDao;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.impl.PlatformUserLoginStatisticsImpl;
import com.icfcc.SRRPService.util.AESUtil;
import com.icfcc.SRRPService.util.jpa.PageUtil;
import com.icfcc.credit.platform.util.Digests;
import com.icfcc.credit.platform.util.Encodes;
import com.icfcc.credit.platform.util.MD5;
import com.icfcc.credit.platform.util.ShiroUser;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.shiro.SecurityUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional(value = "transactionManager")
public class PlatformUserService {
	
	private static final String INVALID_PWD_ERR_MSG = "用户名或密码不正确";
	
	public static final String STOP_MESSAGE = "账户已经停用，请联系系统管理员。";
	
	public static final String LOCK_MESSAGE1 = "由于您连续输入错误密码，账户已被锁定。";
	
	public static final String LOCK_MESSAGE2 = "由于长时间未登录，账户已被锁定，请联系系统管理员。";
	
	public static final String HASH_ALGORITHM = "SHA-1";
    
    public static final int HASH_INTERATIONS = 1024;
    
    private static final int SALT_SIZE = 8;
    
    private static final String PASSWORD="password";
    
    private static final String ERRORTIMES="errortimes";
    
    private static final String LOCKTIME="locktime";
    
    private static final String PWDNEEDCHANGE="pwdneedchange";
    
    private static final String MAXTIME="maxtime";
    
    private static final String STOPDAY="stopday";

    @Autowired
	private PlatformUserLoginStatisticsImpl platformUserLoginStatistics;
    
	@Autowired
	private PlatformUserDao userDao;
	
	@Autowired
	private PlatformUserActionLogDao actionDao;
	
	@Autowired
	private PlatformUserLoginLogDao loginDao;
	
	@Autowired
	private PlatformUserHistoryPasswordDao hisPasswordDao;
	
	@Autowired
	private PlatformConfigService config;
	
    private static Logger logger = LoggerFactory.getLogger(PlatformUserService.class);
    @Value("${enterPrise_URL}")
	public String enterPriseURL;
 // 密钥
 	@Value("${key}")
 	public String key;
	/**
     * <查询分页返回的用户列表信息>
     * 
     * @param searchParams 查询参数
     * @param pageNumber 查询起始页
     * @param pageSize 查询页大小
     * @param sortType 排序方式
     * @return
     * 
     */
	public Page<PlatformUser> getUsers(Map<String, Object> searchParams, int pageNumber, int pageSize,String direction, String orderBy)
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<PlatformUser> spec = PageUtil.buildSpecification(searchParams, PlatformUser.class);
        return userDao.findAll(spec, pageRequest);
    }
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<PlatformUser> getAllUser()
    {
        return (List<PlatformUser>)userDao.findAll();
    }
	
	/**
	 * 根据用户ID获取用户
	 * @param id
	 * @return
	 */
	public PlatformUser getUser(String id)
    {
        return userDao.findOne(id);
    }
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public PlatformUser findUserByUserName(String username){
		return userDao.findByUserName(username);
	}
	/**
	 * 根据机构ID获取用户
	 * @param username
	 * @return
	 */
	public PlatformUser findUserByInvestId(String id){
		return userDao.findUserByInvestId(id);
	}
	public void deleteByInvestId(String id){
		 userDao.deleteByInvestId(id);
	}
	public PlatformUser findByUserID(String id){
		 return userDao.findOne(id);
	}
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public void updateInvestorUserFlag(String orgId,int stopFlag ){
		 userDao.updateStatus(stopFlag,orgId);
	}
	public List<PlatformUser> findByUserOrg(String orgId){
		return userDao.findByUserOrg(orgId);
	}
	public List<PlatformUser> findSjrbUser(){
        return userDao.findSjrbUser();
    }
	public List<PlatformUser> findQjrbUser(){
        return userDao.findQjrbUser();
    }
	public PlatformUser findUser(String orgId){
		return userDao.findUser(orgId);
	}
	public void saveUser(PlatformUser SystemUser){
		userDao.save(SystemUser);
	}
	/**
	 * 增加用户
	 * @param SystemUser
	 */
	public void addSystemUser(PlatformUser SystemUser)
    {
		String password=config.getConfigValueByName(PASSWORD);
		if(StringUtils.isBlank(password)){
			password="Aa_12345";
		}
		SystemUser.setPlainPassword(MD5.MD5(password));
		entryptPassword(SystemUser);
        userDao.save(SystemUser);
    }
	public void saveSystemUser(PlatformUser SystemUser)
    {
        userDao.save(SystemUser);
    }
	/**
	 * 增加用户
	 * @param SystemUser
	 */
	public void addSystemUserNew(PlatformUser SystemUser)
    {
		String password=SystemUser.getPassword();
		System.out.println("password======================="+password);
		if(StringUtils.isBlank(password)){
			password="Aa_12345";
		}
		SystemUser.setPlainPassword((password));
		entryptPassword(SystemUser);
        userDao.save(SystemUser);
    }
	//同步到金服企业信息
	public Map addCompanyBaseUser(CompanyInfoVo companyRestVo,String type) { 
		String result ="";
//		String url="http://61.155.174.41";
		System.out.println("调用信用报告接口:"+enterPriseURL);
		WebClient client = WebClient.create(enterPriseURL);
		client.path("/webSzyhWSClient").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		//封装企业信息
    	JSONObject json = JSONObject.fromObject(companyRestVo);
    	String companyInfoJsonStr = AESUtil.encrypt(json.toString(),key);//加密
    	String typeStr = AESUtil.encrypt(type, key);//加密
    	//传参
    	client.replaceQueryParam("companyInfoJsonStr", companyInfoJsonStr).replaceQueryParam("type", typeStr);;
    	result =client.get(String.class);
    	result=AESUtil.decrypt(result, key);//解密
    	Map maps = (Map)JSON.parse(result);  //转map
		return maps;
	}
	//更新股权库企业信息
	public Map getCompanyInfo(CompanyInfoVo companyRestVo) { 
        String result ="";
//      String url="http://61.155.174.41";
        System.out.println("调用信用报告接口:"+enterPriseURL);
        WebClient client = WebClient.create(enterPriseURL);
        client.path("/getCompanyInfo").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
        //封装企业信息
        JSONObject json = JSONObject.fromObject(companyRestVo);
        String companyInfoJsonStr = AESUtil.encrypt(json.toString(),key);//加密
        //传参
        client.replaceQueryParam("companyInfoJsonStr", companyInfoJsonStr);
        result =client.get(String.class);
        result=AESUtil.decrypt(result, key);//解密
        Map maps = (Map)JSON.parse(result);  //转map
        return maps;
    }
	//同步股权库企业信息
	public Map updateCompanyInfo(CompanyInfoVo companyRestVo) { 
        String result ="";
//      String url="http://61.155.174.41";
        System.out.println("调用信用报告接口:"+enterPriseURL);
        WebClient client = WebClient.create(enterPriseURL);
        client.path("/updateCompanyInfo").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
        //封装企业信息
        JSONObject json = JSONObject.fromObject(companyRestVo);
        String companyInfoJsonStr = AESUtil.encrypt(json.toString(),key);//加密
        //传参
        client.replaceQueryParam("companyInfoJsonStr", companyInfoJsonStr);
        result =client.get(String.class);
        result=AESUtil.decrypt(result, key);//解密
        Map maps = (Map)JSON.parse(result);  //转map
        return maps;
    }
	/**
	 * 修改用户
	 * @param SystemUser
	 */
	public void updateSystemUser(PlatformUser SystemUser)
    {
        userDao.save(SystemUser);
    }
	
	
	public void deleteUser(String id)
    {
        if (isSupervisor(id))
        {
            logger.warn("操作员{}尝试删除超级管理员用户", getCurrentNickName().getNickname());
            throw new ServiceException("不能删除超级管理员用户");
        }
        userDao.delete(id);
        
    }
	/**
	 * 需要提供管理员id
	 */
	public boolean isSupervisor(String id){
		if(id.equals("1"))
			return true;
		return false;
	}
	/**
     * 取出Shiro中的当前用户NickName.
     */
	public ShiroUser getCurrentNickName()
    {
        ShiroUser SystemUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
        return SystemUser;
    }
    
    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(PlatformUser SystemUser)
    {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        SystemUser.setSalt(Encodes.encodeHex(salt));
        
        byte[] hashPassword = Digests.sha1(SystemUser.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        SystemUser.setPassword(Encodes.encodeHex(hashPassword));
        
        PlatformUserHistoryPassword history=new PlatformUserHistoryPassword();
        history.setCreateTime(new Date());
        history.setUserId(SystemUser.getId());
        history.setSalt(SystemUser.getSalt());
        history.setPassword(SystemUser.getPassword());
        hisPasswordDao.save(history);
    }
    public void changePassword(PlatformUser user,String password,String newpassword){
    	//去除原密码
    	String oldpassword=user.getPassword();
    	//对输入原密码加密后与原密码比较
    	user.setPlainPassword(password);
    	byte[] salt=Encodes.decodeHex(user.getSalt());
    	byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    	//entryptPassword(user);
    	//如果不相同，抛出异常
    	if(oldpassword==null || !oldpassword.equals(user.getPassword())){
    		throw new ServiceException("原密码错误");
    	}
    	if(isHistoryPassword(user,newpassword)){
    		throw new ServiceException("与最近三次内历史密码相同");
    	}
    	//如果相同，把提交的新密码作为用户密码
    	user.setPlainPassword(newpassword);
    	entryptPassword(user);
    	//重置标志变为1
    	user.setResetFlag(1);
    	//密码修改日期
    	user.setPwdUpdateTime(new Date());
    }
    public boolean isHistoryPassword(PlatformUser user,String password){
    	List<PlatformUserHistoryPassword> list = hisPasswordDao.findByUserId(user.getId());
    	if(list==null||list.isEmpty()){
    		return false;
    	}
    	for(PlatformUserHistoryPassword history:list){
    		byte[] salt=Encodes.decodeHex(history.getSalt());
        	byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
        	if(hashPassword.equals(history.getPassword())){//如果相同，证明在历史密码中存在
        		return true;
        	}
    	}
    	return false;
    }
    
    /**
     * 连续n天未登录,锁定该用户,登录前执行
     * @param user
     */
    public void isLockUser(PlatformUser user){
    	//获取登录日志记录
    	List<PlatformUserLoginLog> list=loginDao.findByUserId(user.getId());
    	if(list==null||list.isEmpty()){
    		return;
    	}
    	Long lastSuccessTime=System.currentTimeMillis();
    	for(PlatformUserLoginLog log:list){
    		//成功登录
    		if(log.getSuccessFlag()!=null &&log.getSuccessFlag()==1 ){
    			lastSuccessTime=log.getLoginTime().getTime();
    			break;
    		}
    	}
    	Long now=System.currentTimeMillis();
    	int day=(int)(now-lastSuccessTime)/1000/60/60/24;
    	//获取多长时间不登录，锁定用户的参数值
    	String stopDay=config.getConfigValueByName(STOPDAY);
    	if(StringUtils.isBlank(stopDay)){
    		stopDay="30";
		}
    	int stopday=Integer.parseInt(stopDay);
    	if(day>stopday){
    		user.setLockFlag(2);
    	}
    }
    public void tooManyWrong(PlatformUser user){
 	   List<PlatformUserLoginLog> list=loginDao.findByUserId(user.getId());
 	   //如果为空，则认为是新用户，不进行此验证
 	   if(list==null || list.size()==0){
 		   return;
 	   }
 	   //获取最后一次登录时间
 	   PlatformUserLoginLog lastLogin=list.get(0);
 	   Long now=System.currentTimeMillis();
 	   boolean isLessOneHour = lastLoginlessOneHour(user, lastLogin, now);
 	   if(!isLessOneHour){
 		   return;
 	   }
 	   boolean isLessThree = loginLogLessThree(user, list);
 	   if(isLessThree) {
 		   return;
 	   }  
 	   int count=0;
 	   //用于记录倒数第一次失败时间
 	   Long one=0L;
 	   //用于记录倒数第三次失败时间
 	   Long three=0L;
 	   
 	  String errortimes=config.getConfigValueByName(ERRORTIMES);
	   if(StringUtils.isBlank(errortimes)){
		  errortimes="20";//设置默认为20次,固定时间连续输错20次,锁定用户。
	   }
	   int times=Integer.parseInt(errortimes);
	   String maxtime=config.getConfigValueByName(MAXTIME);
	   if(StringUtils.isBlank(maxtime)){
		 maxtime="1";//设置默认为1小时,1小时连续输错n次,锁定用户。
	   }
	   int max=Integer.parseInt(maxtime);
	   
 	   for(PlatformUserLoginLog log:list){
 		   //如果有登录成功，则直接跳过次验证
 		   if(log.getSuccessFlag()==1){
 			  if(user.getLockFlag()==1){
 				   user.setLockFlag(0);
 			   }
 			   return;
 		   }
 		   //由于密码错误
 		   if(INVALID_PWD_ERR_MSG.equals(log.getFailReason())){
 			   count++;
 			   if(count==1){
 				   one=log.getLoginTime().getTime();
 			   }
 			   
 			   //超过3次，返回true,代表已经达到三次
 			   if(count==times){
 				   //一小时失败错三次
 				   three=log.getLoginTime().getTime();
 				   if((one-three)/1000/60/60>max){
 					   user.setLockFlag(0);
 					   return ;
 				   }
 				   //表示固定时间内错误次数达到上限，暂时锁定用户。
 				   user.setLockFlag(1);
 				   return;
 			   }
 		   }
 	   }
    }
	private boolean loginLogLessThree(PlatformUser user,
			List<PlatformUserLoginLog> list) {
	   //记录不超过三次
	   if(list.size()<3){
		  if(user.getLockFlag()==1){
			   user.setLockFlag(0);
		  }
		  return true;
	   }
	   return false;
	}
	private boolean lastLoginlessOneHour(PlatformUser user,
			PlatformUserLoginLog lastLogin, Long now) {
		if(lastLogin!=null){
		   Long lastLoginTime=lastLogin.getLoginTime().getTime();
		   String lockTime=config.getConfigValueByName(LOCKTIME);
		   int lockhour=Integer.parseInt(lockTime);
		   //上次登录超过1个小时
		   if((now-lastLoginTime)/1000/60/60>lockhour){
			   if(user.getLockFlag()==1){
				   user.setLockFlag(0);
			   }
			   return false;
		   }
	   }
		return true;
	}

    /**
     * false需要强制更改密码，判断条件为重置标志为0，或者超过n天未更改密码。登录成功之后执行。
     * @param user
     * @return
     */
    public boolean PWDNeedChange(PlatformUser user){
    	//重置标志为0
    	if(user.getResetFlag()==null||user.getResetFlag()==0){
    		return true;
    	}
    	String pwdneedchange =config.getConfigValueByName(PWDNEEDCHANGE);
    	if(StringUtils.isBlank(pwdneedchange)){
    		pwdneedchange="30";
    	}
    	int pwdchangeday=Integer.parseInt(pwdneedchange);
    	//超过固定天未更改密码，默认30
    	Long update=user.getPwdUpdateTime().getTime();
    	Long now=System.currentTimeMillis();
    	if(update!=null){
    		int day=(int)(now-update)/1000/60/60/24;
    		if(day>pwdchangeday){
        		return true;
        	}
    	}
    	return false;
    }
    public void resetPassword(PlatformUser SystemUser){
    	SystemUser.setResetFlag(0);
    	SystemUser.setPwdUpdateTime(new Date());
    	String password=config.getConfigValueByName(PASSWORD);
		if(StringUtils.isBlank(password)){
			password="Aa_12345";
		}
    	SystemUser.setPlainPassword(MD5.MD5(password));
		entryptPassword(SystemUser);
    }
    
    public void changeUserPassword(PlatformUser SystemUser){
    	SystemUser.setPwdUpdateTime(new Date());
		entryptPassword(SystemUser);
    }
    
    /**
     * 用于获取用户登录日志，上次成功登录时间和上次失败登录时间
     * @return
     */
    public List<PlatformUserLoginLog> getLoginLog(String userId){
    	//获取对应id的所有日志记录
    	List<PlatformUserLoginLog> list=loginDao.findByUserId(userId);
    	//用于存入一条成功和一条失败记录
    	List<PlatformUserLoginLog> log=new ArrayList<PlatformUserLoginLog>();
    	PlatformUserLoginLog newlog=new PlatformUserLoginLog();
    	if(list==null||list.isEmpty()){
    		log.add(newlog);//使用空代替成功和失败记录
    		log.add(newlog);//使用空代替成功和失败记录
    		return log;
    	}
    	for(PlatformUserLoginLog login:list){
    		Integer successFlag = login.getSuccessFlag();
    		if(successFlag==1){
    			log.add(login);
    			break;
    		}
    	}
    	//如果没有成功记录，用空值代替
    	if(log.size()==0){
    		log.add(newlog);
    	}
    	for(PlatformUserLoginLog login:list){
    		Integer successFlag = login.getSuccessFlag();
    		if(successFlag==0){
    			log.add(login);
    			break;
    		}
    	}
    	if(log.size()==1){
    		log.add(newlog);
    	}
    	return log;
//    	SystemUserLoginLog log1=list.get(0);
//    	if(log1.getSuccessFlag()==null || log1.getSuccessFlag()==0){
//    		for(SystemUserLoginLog login:list){
//        		if(login.getSuccessFlag()!=null && login.getSuccessFlag()==1){
//        			log.add(login);
//        			break;
//        		}
//    		}
//    		if(log.size()==0){//未找到成功登录记录
//    			log.add(newlog);//用空值代替成功记录
//    		}
//    		log.add(log1);
//    	}else{
//    		log.add(log1);
//    		for(SystemUserLoginLog login:list){
//        		if(login.getSuccessFlag()==null || login.getSuccessFlag()==0){
//        			log.add(login);
//        			break;
//        		}
//    		}
//    		if(log.size()==1){//未找到失败登录记录
//    			log.add(newlog);//用空值代替失败记录
//    		}
//    	}
//		return log;
    }
    
    public List<PlatformUserLoginLog> getAllLoginLog(String userId){
    	return loginDao.findByUserId(userId);
    }
    /**
     * 无论登录成功失败，都需要进行记录,登录后执行
     * @param log
     */
    public void insertLoginLog(PlatformUserLoginLog log){
    	loginDao.save(log);
    }
    /**
     * <查询分页返回的用户登录日志列表信息>
     * 
     */
	public Page<PlatformUserLoginLog> getUserLoginLogs(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize);
        Specification<PlatformUserLoginLog> spec = PageUtil.buildSpecification(searchParams, PlatformUserLoginLog.class);
        return loginDao.findAll(spec, pageRequest);
    }
    
    /**
     * 行为记录日志，进行更新是记录
     * @param log
     */
    public void saveActionLog(PlatformUserActionLog log){
    	actionDao.save(log);
    }
    /**
     * <查询分页返回的用户行为日志列表信息>
     * 
     */
	public Page<PlatformUserActionLog> getUserActionLogs(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize);
        Specification<PlatformUserActionLog> spec = PageUtil.buildSpecification(searchParams, PlatformUserActionLog.class);
        return actionDao.findAll(spec, pageRequest);
    }
    
    /**
     * 用户更改密码是，存贮其使用过的密码，做为历史密码。
     * @param pwd
     */
    public void savePassword(PlatformUserHistoryPassword pwd){
    	hisPasswordDao.save(pwd);
    }
    /**
     * 删除历史密码，历史密码只记录最近n次的
     * @param id
     */
    public void deletePassword(String id){
    	hisPasswordDao.delete(id);
    }
    /**
     * 根据id，得到历史密码集合,用于判断新密码是否是最近使用过的
     */
    public List<PlatformUserHistoryPassword> getHistoryPassword(String id){
    	return hisPasswordDao.findByUserId(id);
    }



	public List<PlatformUserLoginStatistics> findAll(String time){
		return platformUserLoginStatistics.findall(time);
	}


}

//package com.icfcc.credit.platform.service.system;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.hibernate.service.spi.ServiceException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.icfcc.credit.platform.jpa.entity.system.SystemUser;
//import com.icfcc.credit.platform.jpa.repository.system.SystemUserDao;
//import com.icfcc.credit.platform.service.system.ShiroDbRealm.ShiroUser;
//import com.icfcc.credit.platform.util.Digests;
//import com.icfcc.credit.platform.util.Encodes;
//import com.icfcc.credit.platform.util.SysDate;
//import com.icfcc.credit.platform.util.jpa.DynamicSpecifications;
//import com.icfcc.credit.platform.util.jpa.SearchFilter;
//
///**
// * 用户管理类.
// */
//@Component
//@Transactional(value = "transactionManager")
//public class AccountService
//{
//    
//    public static final String HASH_ALGORITHM = "SHA-1";
//    
//    public static final int HASH_INTERATIONS = 1024;
//    
//    private static final int SALT_SIZE = 8;
//    
//    private static Logger logger = LoggerFactory.getLogger(AccountService.class);
//    
//    private SystemUserDao systemUserDao;
//    
//    /**
//     * <获得用户信息列表>
//     * 
//     * @param searchParams
//     * @param pageNumber
//     * @param pageSize
//     * @return
//     */
//    public Page<SystemUser> getUserList(Map<String, Object> searchParams, int pageNumber, int pageSize)
//    {
//        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
//        Specification<SystemUser> spec = buildSpecification(searchParams);
//        return systemUserDao.findAll(spec, pageRequest);
//    }
//    
//    public List<SystemUser> getAllUser()
//    {
//        return (List<SystemUser>)systemUserDao.findAll();
//    }
//    
//    public SystemUser getUser(Long id)
//    {
//        return systemUserDao.findOne(id);
//    }
//    
//    public SystemUser findUserByLoginName(String loginName)
//    {
//        
//        Map<String, Object> searchParams = new HashMap<String, Object>();
//        searchParams.put("EQ_loginName", loginName);
//        Specification<SystemUser> spec = buildSpecification(searchParams);
//        List<SystemUser> userList = systemUserDao.findAll(spec);
//        if (!userList.isEmpty())
//        {
//            return userList.get(0);
//        }
//        else
//        {
//            return null;
//        }
//        
//    }
//    
//    public void registerUser(SystemUser SystemUser)
//    {
//        entryptPassword(SystemUser);
//        SystemUser.setRoles("SystemUser");
//        SystemUser.setRegisterDate(SysDate.getSysDate());
//        systemUserDao.save(SystemUser);
//    }
//    
//    public void addSystemUser(SystemUser SystemUser)
//    {
//        SystemUser.setRegisterDate(SysDate.getSysDate());
//        systemUserDao.save(SystemUser);
//    }
//    
//    public void updateUser(SystemUser SystemUser)
//    {
//        if (StringUtils.isNotBlank(SystemUser.getPlainPassword()))
//        {
//            entryptPassword(SystemUser);
//        }
//        systemUserDao.save(SystemUser);
//    }
//    
//    public void deleteUser(Long id)
//    {
//        if (isSupervisor(id))
//        {
//            logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
//            throw new ServiceException("不能删除超级管理员用户");
//        }
//        systemUserDao.delete(id);
//        
//    }
//    
//    /**
//     * 判断是否超级管理员.
//     */
//    private boolean isSupervisor(Long id)
//    {
//        return id == 1;
//    }
//    
//    /**
//     * 取出Shiro中的当前用户LoginName.
//     */
//    private String getCurrentUserName()
//    {
//        ShiroUser SystemUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
//        return SystemUser.loginName;
//    }
//    
//    /**
//     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
//     */
//    private void entryptPassword(SystemUser SystemUser)
//    {
//        byte[] salt = Digests.generateSalt(SALT_SIZE);
//        SystemUser.setSalt(Encodes.encodeHex(salt));
//        
//        byte[] hashPassword = Digests.sha1(SystemUser.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
//        SystemUser.setPassword(Encodes.encodeHex(hashPassword));
//    }
//    
//    @Autowired
//    public void setUserDao(SystemUserDao userDao)
//    {
//        this.systemUserDao = userDao;
//    }
//    
//    /**
//     * 创建分页请求.
//     */
//    private PageRequest buildPageRequest(int pageNumber, int pagzSize)
//    {
//        Sort sort = null;
//        return new PageRequest(pageNumber - 1, pagzSize, sort);
//    }
//    
//    /**
//     * 创建动态查询条件组合.
//     */
//    private Specification<SystemUser> buildSpecification(Map<String, Object> searchParams)
//    {
//        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//        // filters.put("firm.firmId", new SearchFilter("firm.firmId",
//        // Operator.EQ, userId));
//        Specification<SystemUser> spec = DynamicSpecifications.bySearchFilter(filters.values(), SystemUser.class);
//        return spec;
//    }
//}

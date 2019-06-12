package com.icfcc.credit.platform.service.system;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.exception.ServiceException;
import com.icfcc.credit.platform.jpa.entity.system.PlatformUserLoginLog;
import com.icfcc.credit.platform.jpa.repository.system.PlatformUserDao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformUserLoginLogDao;
import com.icfcc.credit.platform.util.jpa.PageUtil;

@Component
@Transactional(value = "transactionManager")
public class PlatformUserLoginService {
	
	private static Logger log = LoggerFactory.getLogger(PlatformUserLoginService.class);
	@Autowired
	private PlatformUserLoginLogDao userLoginDao;
	@Autowired
	private PlatformUserDao userDao;

	/**
	 * <获取分页信息,指定排序>
	 */
	public Page<PlatformUserLoginLog> getPage(
			Map<String, Object> searchParams, int pageNumber, int pageSize,String direction, String orderBy) {
		PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize,direction,orderBy);
        Specification<PlatformUserLoginLog> spec=PageUtil.buildSpecification(searchParams, PlatformUserLoginLog.class);
		return userLoginDao.findAll(spec, pageRequest);
	}
	
	/**
	 * <根据id删除bean>
	 * @param id
	 */
	public void delete(String id) {
		try {
			userLoginDao.delete(id);
			log.info("删除实体成功");
		} catch (Exception e) {
			log.error("删除失败");
			log.error(e.getMessage());
		}
	}
	/**
	 * 根据用户id查找用户
	 */
	public void find(String id){
		userDao.findOne(id);
	}
	/**
	 * 根据用户id查找用户
	 */
	public PlatformUserLoginLog findOne(String id){
		PlatformUserLoginLog findOne = userLoginDao.findOne(id);
		return findOne;
	}

	public void deleteIds(String id) throws ServiceException {

		String[] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			if(StringUtils.isNotBlank(ids[i])){
				PlatformUserLoginLog findOne = userLoginDao.findOne(ids[i]);
				Date loginTime = findOne.getLoginTime();
				Calendar calendar = Calendar.getInstance();
				calendar.add(calendar.MONTH, -6);
				if(!loginTime.before(calendar.getTime())){
					userLoginDao.delete(ids[i]);
				}else{
					throw new ServiceException();
				}
			}
		}
	}
	
}

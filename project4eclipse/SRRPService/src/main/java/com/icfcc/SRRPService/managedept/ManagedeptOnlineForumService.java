package com.icfcc.SRRPService.managedept;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.managedept.ManagedeptOnlineForum;
import com.icfcc.SRRPDao.jpa.repository.managedept.ManagedeptOnlineForumDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;
/**
 * 
* @ClassName: ManagedeptOnlineForumService
* @Description: TODO(在线提问解的业务层)
* @author hugt
* @date 2017年9月19日 上午10:53:17
*
 */
@Component
@Transactional(value = "transactionManager")
public class ManagedeptOnlineForumService {
	
	@Autowired
	private ManagedeptOnlineForumDao onlineForumDao;
	
	public Page<ManagedeptOnlineForum> getPage(Map<String, Object> searchParams, int pageNumber, int pageSize,String direction, String orderBy){
		PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize,direction,orderBy);
        Specification<ManagedeptOnlineForum> spec=PageUtil.buildSpecification(searchParams, ManagedeptOnlineForum.class);
        Page<ManagedeptOnlineForum> page=null;
        try {
        	page=onlineForumDao.findAll(spec, pageRequest);
		} catch (Exception e) {
            e.printStackTrace();
			// TODO: handle exception
		}
		return page;
	}
	
	/**
	 * 根据用户id查找用户
	 */
	public ManagedeptOnlineForum findOne(String id){
		ManagedeptOnlineForum onlineForum = onlineForumDao.findOne(id);
		return onlineForum;
	}

	/**
	 * 修改
	 * @param message
	 */
	public void update(ManagedeptOnlineForum onlineForum){
		onlineForumDao.save(onlineForum);
	}
}

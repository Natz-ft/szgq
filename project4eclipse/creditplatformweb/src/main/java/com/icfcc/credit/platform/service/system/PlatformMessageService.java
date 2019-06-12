package com.icfcc.credit.platform.service.system;




import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;




import com.icfcc.credit.platform.jpa.entity.system.PlatformMessage;
import com.icfcc.credit.platform.jpa.repository.system.PlatformMessageDao;
import com.icfcc.credit.platform.util.jpa.PageUtil;


/**
 * 
 * 消息服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformMessageService {
	@Autowired
	private PlatformMessageDao systemMessageDao;
	
	public PlatformMessage findById(Long id){
		PlatformMessage message=systemMessageDao.findById(id);
		return message;
	}
	public ArrayList<PlatformMessage> findAll(){
		ArrayList<PlatformMessage> list=new ArrayList<PlatformMessage>();
		list=systemMessageDao.findAll();
		return list;
	}
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Long id){
		systemMessageDao.deleteById(id);
	}
	/**
	 * 保存
	 * @param message
	 */
	public void save(PlatformMessage message){
		systemMessageDao.save(message);
	}
	/**
	 * 修改
	 * @param message
	 */
	public void update(PlatformMessage message){
		systemMessageDao.save(message);
	}
	
	
	/**
     * <分页查询配置信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<PlatformMessage> getSystemMessageList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
    	
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<PlatformMessage> spec = PageUtil.buildSpecification(searchParams, PlatformMessage.class);
        return systemMessageDao.findAll(spec, pageRequest);
    }
	
}

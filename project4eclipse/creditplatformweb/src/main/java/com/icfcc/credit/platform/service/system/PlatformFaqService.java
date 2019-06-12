package com.icfcc.credit.platform.service.system;




import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.PlatformFaq;
import com.icfcc.credit.platform.jpa.repository.system.PlatformFaqDao;
import com.icfcc.credit.platform.util.jpa.PageUtil;


/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 常见问题服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformFaqService {
	@Autowired
	private PlatformFaqDao systemContentFaqDao;
	
	public PlatformFaq findById(Long id){
		PlatformFaq faq=systemContentFaqDao.findById(id);
		return faq;
	}
	public ArrayList<PlatformFaq> findAll(){
		ArrayList<PlatformFaq> list=new ArrayList<PlatformFaq>();
		list=systemContentFaqDao.findAll();
		return list;
	}
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Long id){
		systemContentFaqDao.deleteById(id);
	}
	/**
	 * 保存
	 * @param message
	 */
	public void save(PlatformFaq faq){
		systemContentFaqDao.save(faq);
	}
	/**
	 * 修改
	 * @param message
	 */
	public void update(PlatformFaq faq){
		systemContentFaqDao.save(faq);
	}
	
	
	/**
     * <分页查询配置信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<PlatformFaq> getSystemContentFaqList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
    	
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<PlatformFaq> spec = PageUtil.buildSpecification(searchParams, PlatformFaq.class);
        return systemContentFaqDao.findAll(spec, pageRequest);
    }
	
}

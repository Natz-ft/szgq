package com.icfcc.SRRPService.platformContent;




import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformfriendlyLinks;
import com.icfcc.SRRPDao.jpa.repository.paltformContent.PlatformFriendlyLinksDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;


/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 友情链接服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformFriendlyLinksService {
	@Autowired
	private PlatformFriendlyLinksDao SystemContentLinksDao;
	
	public PlatformfriendlyLinks findById(Long id){
		PlatformfriendlyLinks links=SystemContentLinksDao.findById(id);
		return links;
	}
	public ArrayList<PlatformfriendlyLinks> findAll(){
		ArrayList<PlatformfriendlyLinks> list=new ArrayList<PlatformfriendlyLinks>();
		list=SystemContentLinksDao.findAll();
		return list;
	}
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Long id){
		SystemContentLinksDao.deleteById(id);
	}
	/**
	 * 保存
	 * @param message
	 */
	public void save(PlatformfriendlyLinks links){
		SystemContentLinksDao.save(links);
	}
	/**
	 * 修改
	 * @param message
	 */
	public void update(PlatformfriendlyLinks links){
		SystemContentLinksDao.save(links);
	}
	
	
	/**
     * <分页查询配置信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<PlatformfriendlyLinks> getSystemContentLinksList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
    	
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<PlatformfriendlyLinks> spec = PageUtil.buildSpecification(searchParams, PlatformfriendlyLinks.class);
        return SystemContentLinksDao.findAll(spec, pageRequest);
    }
	
}

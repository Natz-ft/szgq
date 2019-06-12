package com.icfcc.SRRPService.platformContent;




import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformNews;
import com.icfcc.SRRPDao.jpa.repository.paltformContent.PlatformNewsDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;


/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 新闻服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformNewsService {
	@Autowired
	private PlatformNewsDao systemContentNewsDao;
	
	public PlatformNews findById(Long id){
		PlatformNews news=systemContentNewsDao.findById(id);
		return news;
	}
	public ArrayList<PlatformNews> findAll(){
		ArrayList<PlatformNews> list=new ArrayList<PlatformNews>();
		list=systemContentNewsDao.findAll();
		return list;
	}
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Long id){
		systemContentNewsDao.deleteById(id);
	}
	/**
	 * 保存
	 * @param message
	 */
	public void save(PlatformNews news){
		systemContentNewsDao.save(news);
	}
	/**
	 * 修改
	 * @param message
	 */
	public void update(PlatformNews news){
		systemContentNewsDao.save(news);
	}
	
	
	/**
     * <分页查询配置信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<PlatformNews> getSystemContentNewsList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
    	
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<PlatformNews> spec = PageUtil.buildSpecification(searchParams, PlatformNews.class);
        return systemContentNewsDao.findAll(spec, pageRequest);
    }
	
}

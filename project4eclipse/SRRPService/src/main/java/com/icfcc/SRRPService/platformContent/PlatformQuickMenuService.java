package com.icfcc.SRRPService.platformContent;




import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformQuickMenu;
import com.icfcc.SRRPDao.jpa.repository.paltformContent.PlatformQuickMenuDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;


/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 快捷菜单服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformQuickMenuService {
	@Autowired
	private PlatformQuickMenuDao systemContentQuickMenuDao;
	
	public PlatformQuickMenu findById(Long id){
		PlatformQuickMenu quickMenu=systemContentQuickMenuDao.findById(id);
		return quickMenu;
	}
	public ArrayList<PlatformQuickMenu> findAll(){
		ArrayList<PlatformQuickMenu> list=new ArrayList<PlatformQuickMenu>();
		list=systemContentQuickMenuDao.findAll();
		return list;
	}
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Long id){
		systemContentQuickMenuDao.deleteById(id);
	}
	/**
	 * 保存
	 * @param message
	 */
	public void save(PlatformQuickMenu quickMenu){
		systemContentQuickMenuDao.save(quickMenu);
	}
	/**
	 * 修改
	 * @param message
	 */
	public void update(PlatformQuickMenu quickMenu){
		systemContentQuickMenuDao.save(quickMenu);
	}
	
	
	/**
     * <分页查询配置信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<PlatformQuickMenu> getSystemContentQuickMenuList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
    	
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<PlatformQuickMenu> spec = PageUtil.buildSpecification(searchParams, PlatformQuickMenu.class);
        return systemContentQuickMenuDao.findAll(spec, pageRequest);
    }
	
}

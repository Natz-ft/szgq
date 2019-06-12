package com.icfcc.SRRPService.platformContent;




import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformContactus;
import com.icfcc.SRRPDao.jpa.repository.paltformContent.PlatformContactusDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;


/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 联系我们服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformContactusService {
	@Autowired
	private PlatformContactusDao systemContentRelationDao;
	
	public PlatformContactus findById(Long id){
		PlatformContactus relation=systemContentRelationDao.findById(id);
		return relation;
	}
	public ArrayList<PlatformContactus> findAll(){
		ArrayList<PlatformContactus> list=new ArrayList<PlatformContactus>();
		list=systemContentRelationDao.findAll();
		return list;
	}
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Long id){
		systemContentRelationDao.deleteById(id);
	}
	/**
	 * 保存
	 * @param message
	 */
	public void save(PlatformContactus relation){
		systemContentRelationDao.save(relation);
	}
	/**
	 * 修改
	 * @param message
	 */
	public void update(PlatformContactus relation){
		systemContentRelationDao.save(relation);
	}
	
	
	/**
     * <分页查询配置信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<PlatformContactus> getSystemContentContactusList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
    	
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<PlatformContactus> spec = PageUtil.buildSpecification(searchParams, PlatformContactus.class);
        return systemContentRelationDao.findAll(spec, pageRequest);
    }
	
}

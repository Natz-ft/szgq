package com.icfcc.SRRPService.platformContent;




import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformContactusDist;
import com.icfcc.SRRPDao.jpa.repository.paltformContent.PlatformContactusDistDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;

/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 联系我们_各个分区服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformContactusDistService {
	@Autowired
	private PlatformContactusDistDao systemContentContactusDistDao;
	
	public PlatformContactusDist findById(Long id){
		PlatformContactusDist dist=systemContentContactusDistDao.findById(id);
		return dist;
	}
	public ArrayList<PlatformContactusDist> findAll(){
		ArrayList<PlatformContactusDist> list=new ArrayList<PlatformContactusDist>();
		list=systemContentContactusDistDao.findAll();
		return list;
	}
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Long id){
		systemContentContactusDistDao.deleteById(id);
	}
	/**
	 * 保存
	 * @param message
	 */
	public void save(PlatformContactusDist dist){
		systemContentContactusDistDao.save(dist);
	}
	/**
	 * 修改
	 * @param message
	 */
	public void update(PlatformContactusDist dist){
		systemContentContactusDistDao.save(dist);
	}
	
	
	/**
     * <分页查询配置信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<PlatformContactusDist> getSystemContentContactusDistList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
    	
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<PlatformContactusDist> spec = PageUtil.buildSpecification(searchParams, PlatformContactusDist.class);
        return systemContentContactusDistDao.findAll(spec, pageRequest);
    }
	
}

package com.icfcc.SRRPService.platformContent;




import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformContent.PlatformBusinessPartner;
import com.icfcc.SRRPDao.jpa.repository.paltformContent.PlatformBusinessPartnerDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;







/**
* @author hugt
* @date 2017年9月14日 下午7:29:14
 * 合作单位服务
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformBusinessPartnerService {
	@Autowired
	private PlatformBusinessPartnerDao SystemContentPartnerDao;
	
	public PlatformBusinessPartner findById(Long id){
		PlatformBusinessPartner news=SystemContentPartnerDao.findById(id);
		return news;
	}
	public ArrayList<PlatformBusinessPartner> findAll(){
		ArrayList<PlatformBusinessPartner> list=new ArrayList<PlatformBusinessPartner>();
		list=SystemContentPartnerDao.findAll();
		return list;
	}
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Long id){
		SystemContentPartnerDao.deleteById(id);
	}
	/**
	 * 保存
	 * @param message
	 */
	public void save(PlatformBusinessPartner news){
		SystemContentPartnerDao.save(news);
	}
	/**
	 * 修改
	 * @param message
	 */
	public void update(PlatformBusinessPartner news){
		SystemContentPartnerDao.save(news);
	}
	
	
	/**
     * <分页查询配置信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<PlatformBusinessPartner> getSystemContentPartnerList(Map<String, Object> searchParams, 
    		int pageNumber, int pageSize,String direction, String orderBy){
    	
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<PlatformBusinessPartner> spec = PageUtil.buildSpecification(searchParams, PlatformBusinessPartner.class);
        return SystemContentPartnerDao.findAll(spec, pageRequest);
    }
	
}

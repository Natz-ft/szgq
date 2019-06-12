package com.icfcc.SRRPService.PlatformSystem;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.SRRPDao.jpa.entity.platformSystem.PlatformFunction;
import com.icfcc.SRRPDao.jpa.repository.paltformSystem.PlatformFunctionDao;
import com.icfcc.SRRPService.util.jpa.PageUtil;


/**
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 * 功能管理
 */
@Component
@Transactional(value = "transactionManager")
public class PlatformFunctionService {
	
@Autowired
private PlatformFunctionDao systemFunctionDao;

/**
 *  根据 方法名和类路径 查询 数据
 * @param methodName
 * @param servicePath
 * @return
 */
public List<PlatformFunction> getFunctionMethodNameAndServicePath(String methodName, String servicePath){
	
	return systemFunctionDao.getFunctionMethodNameAndServicePath(methodName, servicePath) ;
}

/**
 * 功能日志管理
 * 获取按钮分页列表 <功能详细描述>
 * 
 * @param searchParams
 * @param pageNumber
 * @param pageSize
 * @param direction
 * @param orderBy
 * @return
 * @see [类、类#方法、类#成员]
 */
public Page<PlatformFunction> getSystemFunctionList(Map<String, Object> searchParams, int pageNumber, int pageSize,
    String direction, String orderBy)
{
    PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
    Specification<PlatformFunction> spec = PageUtil.buildSpecification(searchParams, PlatformFunction.class);
    return systemFunctionDao.findAll(spec, pageRequest);
}

	/**
	 * 功能日志表添加 
	 * @param systemFunction
	 */
	public  void  saveSystemFunction(PlatformFunction  systemFunction  ){
		
		systemFunctionDao.save(systemFunction);
	}
	/**
	 * 功能日志表修改
	 * @param systemFunction
	 */
	public  void  updateSystemFunction(PlatformFunction  systemFunction  ){
		
		systemFunctionDao.save(systemFunction);
	}
	/**
	 *  功能日志表 删除
	 * @param id
	 */
	public    void deleteSystemFunctionById( String id ){
		
		systemFunctionDao.deletePlatformFunctionById(id);
	}
	
	/**
	 * Id 查询配置
	 * @return
	 */
	public  PlatformFunction  getSystemFunctionById(String id ){
	 
	return	systemFunctionDao.findOne(id);
	}

}

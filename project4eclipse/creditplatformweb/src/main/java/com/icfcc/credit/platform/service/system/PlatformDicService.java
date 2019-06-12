package com.icfcc.credit.platform.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icfcc.credit.platform.jpa.entity.system.PlatformDic;
import com.icfcc.credit.platform.jpa.entity.system.PlatformDicType;
import com.icfcc.credit.platform.jpa.repository.system.PlatformDicDao;
import com.icfcc.credit.platform.jpa.repository.system.PlatformDicTypeDao;
import com.icfcc.credit.platform.util.jpa.PageUtil;

/**
 * <系统字典业务接口>
 * @author hugt
 * @date 2017年9月14日 下午7:29:14
 */

@Component
@Transactional(value = "transactionManager")
public class PlatformDicService
{
    
    @Autowired
    private PlatformDicDao systemDicDao;
    @Autowired
    private PlatformDicTypeDao systemDicTypeDao;
    @Value("${redic.dic.url}")
	public String dicUrl;// 更新redis
 // 更新redis
    public void HttpClientGetSRRP() {
		try {
				HttpClient httpClient = HttpClients.createDefault();
				HttpGet get = new HttpGet(dicUrl);
				httpClient.execute(get);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    public List<PlatformDicType> getDicType(String type)
    {
        return systemDicTypeDao.findList();
    }
    
    public PlatformDicType findByType(String type)
    {
        return systemDicTypeDao.findByType(type);
    }
    public Map<String, String> getDicType() {
		Map<String, String> map = new HashMap<String, String>();
		List<PlatformDicType> list = null;
		try {
			list = systemDicTypeDao.findList();
			if (CollectionUtils.isNotEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					PlatformDicType type = list.get(i);
					map.put(type.getType(), type.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
    /**
     * <根据类型查询字典数据>
     * 
     * @param dicChannelOpenLevel
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<PlatformDic> getDicListByType(String type)
    {
        return systemDicDao.findByType(type);
    }
    
    
    /**
     * <根据type和value查询数据字典>
     * 
     * @param type
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    public PlatformDic getDicByTypeAndValue(String type, String value)
    {
        return systemDicDao.findByTypeAndValue(type, value);
    }
    
    /**
     * <根据type和name查询数据字典>
     * 
     * @param type
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    public PlatformDic getDicByTypeAndName(String type, String name)
    {
        return systemDicDao.findByTypeAndName(type, name);
    }
  
    
    /**
     * <查询系统字典表>
     * 
     * @param type
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Page<PlatformDic> getSysDicList(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        String[] orderBy = {"validityDate", "type"};
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, "desc", orderBy);
        Specification<PlatformDic> spec = PageUtil.buildSpecification(searchParams, PlatformDic.class);
        return systemDicDao.findAll(spec, pageRequest);
    }
    
    /**
     * <根据id获得系统字典表信息>
     * 
     * @param id
     * @return
     */
    public PlatformDic getSystemDic(Long id)
    {
        return systemDicDao.findOne(id);
    }
    
    /**
     * <保存系统字典表信息>
     * 
     * @param entity
     */
    public void saveSysDic(PlatformDic entity)
    {
        systemDicDao.save(entity);
    }
    
    /**
     * <修改系统字代表信息>
     */
    public void updateSysDic(PlatformDic entity)
    {
        systemDicDao.save(entity);
    }
    /**
     * <删除系统字典表信息>
     * 
     * @param id
     */
    public void deleteSystemDic(Long id)
    {
        systemDicDao.delete(id);
    }
    
    /**
     * 
     * <查询系统字典表信息，字典兑换使用><br />
     * <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Map<String, String> getSystemDicKeyValue()
    {
        Map<String, String> result = new LinkedHashMap<String, String>();
        List<PlatformDic> sdList = (List<PlatformDic>)systemDicDao.findList();
        for (PlatformDic sd : sdList)
        {
            result.put(sd.getType() + "_" + sd.getValue(), sd.getName());
        }
        return result;
        
    }
    /**
     * 根据字典类型拿到名称和类型的键值对集合，用于动态生成下拉列表
     * @param type
     * @return
     */
    public Map<String, String> getMapByType(String type){
    	List<PlatformDic> list=systemDicDao.findByType(type);
    	Map<String, String> map=new LinkedHashMap<String, String>();
    	if(!list.isEmpty()){
    		for(PlatformDic dic:list){
        		map.put(dic.getName(), dic.getValue());
        	}
    	}
    	return map;
    }
    /**
     * 用于回显数据,单个数据
     * @param type
     * @param value
     * @return
     */
    public String converseValue(String type,String value){
    	PlatformDic dic=getDicByTypeAndValue(type, value);
    	return dic.getName();
    }
    /**
     * 用于回显数据,集合数据
     * @param type
     * @param value
     * @return
     */
    public List<String> converseValue(String type,List<String> values){
    	List<String> names=new ArrayList<String>();
    	for(String value:values){
    		PlatformDic dic=getDicByTypeAndValue(type, value);
    		names.add(dic.getName());
    	}
    	return names;
    }
    
    public List<PlatformDic> getAllSystemDic(){
    	List<PlatformDic> list = (List<PlatformDic>)systemDicDao.findList();
    	return list;
    }
}

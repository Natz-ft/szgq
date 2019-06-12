package com.icfcc.credit.platform.util.redis;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.ValueOperations;

public interface RedisService
{
    
    /**
     * 通过key获取普通的缓存数据
     * 
     * @return
     */
    public String getCacheValue(String key, Integer dbIndex);
    
    /**
     * 创建普通的缓存数据
     * 
     * @param key
     * @param value
     */
    public void setCacheValue(String key, String value, Integer dbIndex);
    
    /**
     * 通过key获取普通的缓存数据
     * 
     * @return
     */
    public String getCacheValue(String key);
    
    /**
     * 创建普通的缓存数据
     * 
     * @param key
     * @param value
     */
    public void setCacheValue(String key, String value);
    
    /**
     * 批量插入串号区域基本信息
     */
    // public void multiSet(List<BssCityDevice> bssCityDevices,Integer dbIndex);
    /**
     * 创建生命周期的缓存数据 seconds:失效时间
     * 
     * @param key
     * @param value
     */
    public void setCacheValue(String key, Integer seconds, String value, Integer dbIndex);
    
    /**
     * 通过key删除缓存数据
     * 
     * @param dbIndex
     * @param key
     */
    /**
     * 更新失效时间
     */
    public void setExpire(final String key, final Integer seconds, final Integer dbIndex);
    
    public void delBykey(Integer dbIndex, String key);
    
    /**
     * 把数据清空
     * 
     * @param dbIndex
     */
    public void redisFlushdb(Integer dbIndex);
    
    /**
     * 获取指定库的所有key
     * 
     * @param dbIndex
     * @return
     */
    public List<String> getAllKeys(Integer dbIndex);
    
    /**
     * hash map 放入数据
     */
    public void hashMapSetValue(String mapKey, String key, String value, Integer dbIndex);
    
    public void hashMapSetValue(String mapKey, Map<byte[], byte[]> value, Integer dbIndex);
    
    public Map<String, String> hashMapGetAllValue(String mapKey, Integer dbIndex);
    
    public String hashMapGetValue(String mapKey, String key, Integer dbIndex);
    
    public void hashMapMgetAllValue(String[] mapKeys, Integer dbIndex);
    
    public void hashMapDelByKey(String mapKey, String key, Integer dbIndex);
    
    /**
     * redis set 集合处理
     */
    public void hashSetAddValue(String setKey, String value, Integer dbIndex);
    
    public void hashSetDelMember(String setKey, String member, Integer dbIndex);
    
    public Long hashSetCount(String setKey, Integer dbIndex);
    
    public ValueOperations<Object, Object> getOpsForValue();
}

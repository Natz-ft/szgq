package com.icfcc.credit.platform.util.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import redis.clients.util.SafeEncoder;

@Service
public class RedisServiceImpl implements RedisService
{
    
    @Resource
    RedisTemplate<Object, Object> redisTemplate;
    
    @Override
    public String getCacheValue(final String key, final Integer dbIndex)
    {
        return this.redisTemplate.execute(new RedisCallback<String>()
        {
            @Override
            public String doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                byte[] temp = connection.get(key.getBytes());
                if (temp != null)
                {
                    return SafeEncoder.encode(temp);
                }
                return null;
            }
        });
    }
    
    @Override
    public void setCacheValue(final String key, final String value, final Integer dbIndex)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.set(key.getBytes(), value.getBytes());
                return null;
            }
        });
    }
    
    @Override
    public String getCacheValue(final String key)
    {
        return this.redisTemplate.execute(new RedisCallback<String>()
        {
            @Override
            public String doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                byte[] temp = connection.get(key.getBytes());
                if (temp != null)
                {
                    return SafeEncoder.encode(temp);
                }
                return null;
            }
        });
    }
    
    @Override
    public void setCacheValue(final String key, final String value)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.set(key.getBytes(), value.getBytes());
                return null;
            }
        });
    }
    
    @Override
    public void setCacheValue(final String key, final Integer seconds, final String value, final Integer dbIndex)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.setEx(key.getBytes(), seconds, value.getBytes());
                return null;
            }
        });
    }
    
    /**
     * 
     * 更新失效时间
     */
    @Override
    public void setExpire(final String key, final Integer seconds, final Integer dbIndex)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.expire(key.getBytes(), seconds);
                return null;
            }
        });
    }
    
    @Override
    public void delBykey(final Integer dbIndex, final String key)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.del(key.getBytes());
                return null;
            }
        });
    }
    
    /**
     * 清空数据库 param:Integer dbIndex(数据库的标识)
     */
    public void redisFlushdb(final Integer dbIndex)
    {
        
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.flushDb();
                return null;
            }
        });
    }
    
    @Override
    public List<String> getAllKeys(final Integer dbIndex)
    {
        return this.redisTemplate.execute(new RedisCallback<List<String>>()
        {
            @Override
            public List<String> doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                List<String> result = new ArrayList<String>();
                connection.select(dbIndex);
                Set<byte[]> keys = connection.keys("*".getBytes());
                for (byte[] key : keys)
                {
                    result.add(SafeEncoder.encode(key));
                }
                return result;
            }
        });
    }
    
    @Override
    public void hashMapSetValue(final String mapKey, final String key, final String value, final Integer dbIndex)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.hSet(mapKey.getBytes(), key.getBytes(), value.getBytes());
                return null;
            }
        });
    }
    
    @Override
    public void hashMapSetValue(final String mapKey, final Map<byte[], byte[]> value, final Integer dbIndex)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.hMSet(mapKey.getBytes(), value);
                return null;
            }
        });
        
    }
    
    @Override
    public String hashMapGetValue(final String mapKey, final String key, final Integer dbIndex)
    {
        return this.redisTemplate.execute(new RedisCallback<String>()
        {
            @Override
            public String doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                byte[] temp = connection.hGet(mapKey.getBytes(), key.getBytes());
                if (temp != null)
                {
                    return SafeEncoder.encode(temp);
                }
                return null;
            }
        });
    }
    
    @Override
    public Map<String, String> hashMapGetAllValue(final String mapKey, final Integer dbIndex)
    {
        return this.redisTemplate.execute(new RedisCallback<Map<String, String>>()
        {
            @Override
            public Map<String, String> doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                Map<String, String> result = new HashMap<String, String>();
                connection.select(dbIndex);
                Map<byte[], byte[]> temp = connection.hGetAll(mapKey.getBytes());
                
                for (Map.Entry<byte[], byte[]> entry : temp.entrySet())
                {
                    result.put(SafeEncoder.encode(entry.getKey()), SafeEncoder.encode(entry.getValue()));
                }
                return result;
            }
        });
    }
    
    @Override
    public void hashMapMgetAllValue(final String[] mapKeys, final Integer dbIndex)
    {
        
    }
    
    @Override
    public void hashMapDelByKey(final String mapKey, final String key, final Integer dbIndex)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.hDel(mapKey.getBytes(), key.getBytes());
                return null;
            }
        });
    }
    
    @Override
    public void hashSetAddValue(final String setKey, final String value, final Integer dbIndex)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.sAdd(setKey.getBytes(), value.getBytes());
                return null;
            }
        });
    }
    
    @Override
    public void hashSetDelMember(final String setKey, final String member, final Integer dbIndex)
    {
        this.redisTemplate.execute(new RedisCallback<Object>()
        {
            @Override
            public Object doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                connection.sRem(setKey.getBytes(), member.getBytes());
                return null;
            }
        });
        
    }
    
    @Override
    public Long hashSetCount(final String setKey, final Integer dbIndex)
    {
        return this.redisTemplate.execute(new RedisCallback<Long>()
        {
            @Override
            public Long doInRedis(RedisConnection connection)
                throws DataAccessException
            {
                connection.select(dbIndex);
                return connection.sCard(setKey.getBytes());
            }
        });
        
    }
    
    @Override
    public ValueOperations<Object, Object> getOpsForValue()
    {
        // TODO Auto-generated method stub
        return redisTemplate.opsForValue();
    }
    
}

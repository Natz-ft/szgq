/**
 *  Copyright (c)  2017-2027 ICFCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of ICFCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with ICFCC.
 */
package com.icfcc.credit.cache;

import java.util.List;
import java.util.Set;

import org.redisson.config.Config;

import com.icfcc.credit.platform.util.RedissonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

/**
 * <>
 * @author Densen.Liu
 * @date 2017年4月12日
 */
public class RedisTest {

    
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "172.16.110.246");
        // / Jedis implements Closable. Hence, the jedis instance will be
        // auto-closed after the last statement.
        try (Jedis jedis = pool.getResource()) {
            // / ... do stuff here ... for example
            jedis.set("foo", "bar");
            String foobar = jedis.get("foo");
            jedis.zadd("sose", 0, "car");
            jedis.zadd("sose", 0, "bike");
            Set<String> sose = jedis.zrange("sose", 0, -1);
            System.out.println(foobar);
            System.out.println(sose);
        }
        // / ... when closing your application:
        pool.close();
//	Jedis jedis = new Jedis("172.16.110.246");  
//	jedis.set("liu", "densen");
//        try {  
//            Transaction transaction = jedis.multi();  
//            //transaction.lpush("key", "11");  
//            //transaction.lpush("key", "22");  
//            jedis.set("liu", "dongsheng");
//            int a = 6 / 0;  
//           // transaction.lpush("key", "33");  
//            List<Object> list = transaction.exec();  
//        } catch (Exception e) {  
//            e.printStackTrace();
//        }  
//        System.out.println(jedis.get("liu")); 
    }
    
    public void testRedission(){
	Config config = new Config();
        config.useSingleServer().setAddress("172.16.110.246:6379");
	RedissonUtil.getRemoteRedisson(config);
    }
}

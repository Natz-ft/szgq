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

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * <>
 * @author Densen.Liu
 * @date 2017年4月13日
 */
public class JedisTest {

//    @Test
//    public void test1(){
//	 Jedis jedis = new Jedis("localhost");  
//	 Transaction transaction = jedis.multi();  
//	 transaction.lpush("key", "11");  
//	 transaction.lpush("key", "22");    
//	 transaction.lpush("key", "33");  
//	 List<Object> list = transaction.exec();
//	 System.out.println(jedis.lrange("key",0,10)); 
//    }
    
    public void test2() {  
        Jedis jedis = new Jedis("localhost");  
        try {  
            Transaction transaction = jedis.multi();  
            transaction.lpush("key", "11");  
            transaction.lpush("key", "22");  
            int a = 6 / 0;  
            transaction.lpush("key", "33");  
            List<Object> list = transaction.exec();  
        } catch (Exception e) {  
      
        }  
        System.out.println(jedis.lrange("key",0,10)); 
    } 
}

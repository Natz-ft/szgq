package com.icfcc.SRRPService.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	
	private static RedisUtil self;
	
	static{
		
	}

	 /**        
     * 数据源       
     */       
    private JedisPool jedisPool;  
    
    private String host;
    
    private Integer port;
      
    public static RedisUtil getSelf() {
		return self;
	}

	public static void setSelf(RedisUtil self) {
		RedisUtil.self = self;
	}

	/**       
     * 获取数据库连接        
     * @return conn        
     */       
    public Jedis getConnection() {  
        Jedis jedis=null;            
        try {     
        	//jedisPool=new JedisPool(new JedisPoolConfig(),host,port);
            jedis=jedisPool.getResource();            
        } catch (Exception e) {                
            e.printStackTrace();            
        }            
        return jedis;        
    }     
      
    /**        
     * 关闭数据库连接        
     * @param conn        
     */       
    public void closeConnection(Jedis jedis) {            
        if (null != jedis) {                
            try {                    
                jedisPool.returnResource(jedis);                
            } catch (Exception e) {  
                    e.printStackTrace();                
            }            
        }        
    }    
      
    /**        
     * 设置连接池        
     * @param 数据源       
     */       
    public void setJedisPool(JedisPool JedisPool) {  
        this.jedisPool = JedisPool;        
    }         
      
    /**        
     * 获取连接池        
     * @return 数据源        
     */       
    public JedisPool getJedisPool() {  
        return jedisPool;        
    }       
    
	public static void main(String[] args) {
		JedisPool jp=new JedisPool(new JedisPoolConfig(),"172.16.110.197",6379);
		
		
		int i=0;
		for(;;){
			Jedis j=jp.getResource();
			j.hgetAll("BASIC");
			System.out.println(i++);
			System.out.print(jp.getNumIdle()+";");
			System.out.print(jp.getNumWaiters()+";");
			System.out.println(jp.getNumActive());
			//j.close();
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}

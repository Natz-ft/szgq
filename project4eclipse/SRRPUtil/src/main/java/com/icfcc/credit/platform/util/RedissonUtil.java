/**
 *  Copyright (c)  2017-2027 ICFCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of ICFCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with ICFCC.
 */
package com.icfcc.credit.platform.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * <Redisson操作工具类>
 * 
 * @author Densen.Liu
 * @date 2017年4月18日
 */
public class RedissonUtil {

	private RedissonUtil() {
	};

	private static RedissonClient localRedisson;

	private static RedissonClient remoteRedisson;
	/**
	 * 集群部署方式
	 */
	private static final String CLUSTER_DEPLOY = "1";
	/**
	 * 单点部署方式
	 */
	private static final String SINGLE_DEPLOY = "2";
	/**
	 * 主从部署方式
	 */
	private static final String MASTER_SLAVE_DEPLOY = "3";
	/**
	 * <返回一个默认本地的RedissonClient>
	 * 
	 * @return
	 * @author Densen.Liu
	 * @date 2017年4月18日
	 */
	public static RedissonClient getLocalRedissonOld() {
		if (localRedisson == null) {
			Properties prop = new Properties();
			String path = System.getProperty("catalina.home");
			try {
				InputStream in=new FileInputStream(path+File.separator+"config"+File.separator+"redis.properties");
				prop.load(in);
				String host = prop.getProperty("redis.host");
				String port = prop.getProperty("redis.port");
				Config config = new Config();
				config.useSingleServer().setAddress(host + ":" + port);
				localRedisson = Redisson.create(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return localRedisson;
	}
	
	/**
	 * <兼容REDIS 各种部署模式的实例化，返回一个默认本地的RedissonClient>
	 * 
	 * @return 
	 * @author jerry.chen
	 * @date 2017年4月18日
	 */
	public static RedissonClient getLocalRedisson() {
		if (localRedisson == null) {
			Properties prop = new Properties();
			String path = System.getProperty("catalina.home");
			try {
				InputStream in=new FileInputStream(path+File.separator+"config"+File.separator+"redis.properties");
				prop.load(in);
				
				String flag = prop.getProperty("redis.deploymodel");
				String host = prop.getProperty("redis.host");
				String host2 = prop.getProperty("redis.host2");
				Config config = new Config();
				String str = "";
				if(CLUSTER_DEPLOY.equals(flag) ){
					//集群
					host = host.trim();
					String[] array = host.split(",");
					config.useClusterServers().setScanInterval(2000)
					.addNodeAddress(array );
				}else if(SINGLE_DEPLOY.equals(flag)){
					//单例
					config.useSingleServer().setAddress(host);
				}else if (MASTER_SLAVE_DEPLOY.equals(flag)){
					//主从
					host2 = host2.trim();
					String[] slaveHostArray = host2.split(",");
					String masterHost = host.trim();
					config.useMasterSlaveServers().setMasterAddress(masterHost)
					.addSlaveAddress(slaveHostArray);
				}
				localRedisson = Redisson.create(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return localRedisson;
	}
	
	public static void main(String[] args) {
		Properties prop = new Properties();
		String path = "D:"+File.separator+"tomcat-7"+File.separator+"config"+File.separator+"redis.properties";
		try {
			InputStream in=new FileInputStream(path);  
			prop.load(in);
			String host = prop.getProperty("redis.host");
			String port = prop.getProperty("redis.port");
			Config config = new Config();
			config.useSingleServer().setAddress(host + ":" + port);
			localRedisson = Redisson.create(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * <返回一个指定参数的远程RedissonClient>
	 * 
	 * @return
	 * @author Densen.Liu
	 * @date 2017年4月18日
	 */
	public static RedissonClient getRemoteRedisson(Config config) {
		if (remoteRedisson == null) {
			remoteRedisson = Redisson.create(config);
		}
		return remoteRedisson;
	}
}

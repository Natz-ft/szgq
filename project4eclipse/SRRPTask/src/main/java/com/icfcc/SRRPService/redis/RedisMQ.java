package com.icfcc.SRRPService.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisMQ {

	private RedisUtil redisUtil;

	public RedisUtil getRedisUtil() {
		return redisUtil;
	}

	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	/**
	 * 发布一个消息
	 * @param channel
	 * @param message
	 */
	public void publish(String channel, String message) {
		Jedis jedis = redisUtil.getConnection();
		try{
		jedis.publish(channel, message);
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	/**
	 * 启动一个订阅
	 * @param channel
	 */
	public void subscribe(final String channel){
		final RedisListener listener = new RedisListener();
		Thread t=new Thread(){
			@Override
			public void run() {
				Jedis jedis=redisUtil.getConnection();
				jedis.subscribe(listener, channel);
			}
		};
		t.start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public class RedisListener extends JedisPubSub {
		// 取得订阅的消息后的处理
		public void onMessage(String channel, String message) {
			System.out.println(channel + "=" + message);
		}

		// 初始化订阅时候的处理
		public void onSubscribe(String channel, int subscribedChannels) {
			// System.out.println(channel + "=" + subscribedChannels);
		}

		// 取消订阅时候的处理
		public void onUnsubscribe(String channel, int subscribedChannels) {
			// System.out.println(channel + "=" + subscribedChannels);
		}

		// 初始化按表达式的方式订阅时候的处理
		public void onPSubscribe(String pattern, int subscribedChannels) {
			// System.out.println(pattern + "=" + subscribedChannels);
		}

		// 取消按表达式的方式订阅时候的处理
		public void onPUnsubscribe(String pattern, int subscribedChannels) {
			// System.out.println(pattern + "=" + subscribedChannels);
		}

		// 取得按表达式的方式订阅的消息后的处理
		public void onPMessage(String pattern, String channel, String message) {
			System.out.println(pattern + "=" + channel + "=" + message);
		}

	}

}

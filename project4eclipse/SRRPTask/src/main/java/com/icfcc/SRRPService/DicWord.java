package com.icfcc.SRRPService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.icfcc.SRRPService.redis.RedisUtil;
import com.icfcc.credit.platform.util.SpringContextUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class DicWord {

	private Logger log = LoggerFactory.getLogger(DicWord.class);

	public static String DICCACHE = "dicCache";

	private static DicWord dicWord;

	private SystemDicService systemDicService;
	private Constants constants;

	private Map<String, String> dicTypeMap;

	private RedisUtil redisUtil;
	private Jedis jedis;
	private DicRedisListener listener;

	private static String prefix = "dic_";

	private static String dicChannel = "_dicChannel";

	private Boolean redisDown = false;

	static {
		dicWord = new DicWord();
		dicWord.init();
	}

	private DicWord() {
	}

	public void init() {
		systemDicService = (SystemDicService) SpringContextUtil.getBean("systemDicService");
		constants = (Constants) SpringContextUtil.getBean("constants");
		dicTypeMap = constants.getDicTypeMap();
		try {
			redisUtil = (RedisUtil) SpringContextUtil.getBean("redisUtil");
			jedis = redisUtil.getConnection();
			if (jedis != null) {
				listener = new DicRedisListener();
				Thread t = new Thread() {
					@Override
					public void run() {
						jedis.subscribe(listener, dicChannel);
					}
				};
				t.start();
			} else {
				redisDown = true;
			}

		} catch (Exception e) {
			redisDown = true;
			// e.printStackTrace();
		}
	}

	public Boolean getRedisDown() {
		return redisDown;
	}

	public void setRedisDown(Boolean redisDown) {
		this.redisDown = redisDown;
	}

	public static DicWord getDicWord() {
		return dicWord;
	}

	@PostConstruct
	public void constructDicWord() {
		DicWord.getDicWord();
	}

	public static Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

	public Map<String, Map<String, String>> getMap() {
		return map;
	}

	/**
	 * 根据类型，获取键值对集合，用于加载下拉列表
	 * 
	 * @param type
	 * @return
	 */
	public Map<String, String> getDicByType(String type) {
		Map<String, String> res = map.get(type);
		// LinkedHashMap<String,String>
		// res=(LinkedHashMap<String,String>)cache.get(type);
		if (res == null) {
			res = (LinkedHashMap<String, String>) systemDicService.getMapByType(type);
			map.put(type, res);
			log.info("diccache create with " + type);
		}
		return res;
	}

	/**
	 * rediscache
	 * 
	 * @param type
	 * @return
	 */
	public Map<String, String> getDicWithRedisByType(String type) {

		String redisKey = prefix + type;
		Map<String, String> res = map.get(type);
		if (!redisDown) {
			if (res == null) {
				try {
					Jedis jedis = redisUtil.getConnection();
					Map<String, String> redisMap = jedis.hgetAll(redisKey);
					try {
						if (redisMap == null || redisMap.size() == 0) {
							res = (Map<String, String>) systemDicService.getMapByType(type);
							jedis.hmset(redisKey, res);
							//jedis.close();
							map.put(type, res);
							log.info("diccache create with " + type);
							return res;
						} else {
							res = jedis.hgetAll(redisKey);
							//jedis.close();
							map.put(type, res);
							return res;
						}
					} finally {
						if (jedis != null) {
							jedis.close();
						}
					}
				} catch (Exception e) {
					redisDown = true;
					e.printStackTrace();
					log.info("redis server had been down!");
				}
			}
		} else {
			res = getDicByType(type);
		}
		return res;
	}

	/**
	 * 当前进程更新字典后需要发布到redis
	 * 
	 * @param type
	 * @param dic
	 */
	public void publishNewDic(String type, Map<String, String> dic) {
		if (!redisDown) {
			Jedis jedis = redisUtil.getConnection();
			try {
				jedis.hmset(prefix + type, dic);
				jedis.publish(dicChannel, type);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
		}
	}

	/**
	 * 直接从redis更新字典到本地
	 * 
	 * @param type
	 */
	public void updateDicFromRedis(String type) {
		if (!redisDown) {
			Jedis jedis = redisUtil.getConnection();
			Map<String, String> res = null;
			try {
				res = jedis.hgetAll(prefix + type);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}
			if (res != null) {
				map.put(type, res);
			}
		}
	}

	public String getTypeName(String code) {
		return dicTypeMap.get(code);
	}

	public class DicRedisListener extends JedisPubSub {

		// 取得订阅的消息后的处理
		public void onMessage(String channel, String message) {
			System.out.println(channel + "=" + message);
			DicWord.getDicWord().updateDicFromRedis(message);
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

package com.icfcc.credit.platform.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;


import redis.clients.jedis.Jedis;
public interface DicCache {
	Map<String, LinkedHashMap<String, String>> getCachMap();
	Jedis getCachRedis();
}

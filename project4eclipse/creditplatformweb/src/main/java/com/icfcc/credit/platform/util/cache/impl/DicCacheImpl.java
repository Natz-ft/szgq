package com.icfcc.credit.platform.util.cache.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.icfcc.credit.platform.jpa.entity.business.DicWord;
import com.icfcc.credit.platform.util.cache.DicCache;

import redis.clients.jedis.Jedis;
@Component
public class DicCacheImpl implements DicCache {

	@Override
	public Map<String, LinkedHashMap<String, String>> getCachMap() {
		Map<String, LinkedHashMap<String, String>> map = DicWord.getDicWord().getMap();
		return map;
	}

	@Override
	public Jedis getCachRedis() {
		// TODO Auto-generated method stub
		return null;
	}


}

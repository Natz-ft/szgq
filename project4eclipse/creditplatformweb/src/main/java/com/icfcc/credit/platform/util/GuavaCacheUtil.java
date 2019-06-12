package com.icfcc.credit.platform.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.icfcc.credit.platform.jpa.entity.system.PlatformMenu;

public class GuavaCacheUtil {
	private static LoadingCache<String, String> cache = null;
	private static LoadingCache<String, List<String>> cacheList = null;

	public static LoadingCache<String, String> getCache(final List<PlatformMenu> list) {
		// 模拟数据
		cache = CacheBuilder.newBuilder()//
		        .refreshAfterWrite(30, TimeUnit.MINUTES)// 给定时间内没有被读/写访问，则回收。
		        .maximumSize(100).// 设置缓存个数
		        build(new CacheLoader<String, String>() {
			        @Override
			        public String load(String key) throws Exception {
				        System.out.println(key + " load in cache");
				        return getPerson(key);
			        }

			        // 此时一般我们会进行相关处理，如到数据库去查询
			        private String getPerson(String key) throws Exception {
				        System.out.println(key + " query");
				        for (PlatformMenu p : list) {
					        if (p.getId().equals(key))
						        return p.getLink();
				        }
				        return null;
			        }
		        });
		return cache;
	}

	public static LoadingCache<String, List<String>> getListCache(final List<String> list, String key) {
		List<PlatformMenu> listMenu = new ArrayList<PlatformMenu>();
		final Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put(key, list);
		// 模拟数据
		cacheList = CacheBuilder.newBuilder()//
		        .refreshAfterWrite(30, TimeUnit.MINUTES)// 给定时间内没有被读/写访问，则回收。
		        .maximumSize(100).// 设置缓存个数
		        build(new CacheLoader<String, List<String>>() {
			        @Override
			        public List<String> load(String key) throws Exception {
				        System.out.println(key + " load in cache");
				        return getMenu(key);
			        }

			        // 此时一般我们会进行相关处理，如到数据库去查询
			        private List<String> getMenu(String key) throws Exception {
				        List<String> list = map.get(key);
				        if (CollectionUtils.isNotEmpty(list)) {
					        return list;
				        }
				        return null;
			        }
		        });
		return cacheList;
	}
}

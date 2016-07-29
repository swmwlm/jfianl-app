package com.shoukeplus.jFinal.filter.shiro;

import org.apache.shiro.cache.ehcache.EhCacheManager;

public class MyShiroCacheManager extends EhCacheManager {
	public MyShiroCacheManager(){
		setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		setCacheManager(net.sf.ehcache.CacheManager.create());
	}
}

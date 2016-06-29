package com.langmy.jFinal.handler.xss.xssfilter;

import com.langmy.jFinal.handler.xss.xssfilter.config.XssConfiguration;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.HashMap;
import java.util.Map;

public class XssFilter {

	private static String DEFAULT_CONFIG = "jsoup-default-filter.xml";
	private static final Map<String, XssFilter> instanceMap = new HashMap<String, XssFilter>();
	private Whitelist whitelist;

	private XssFilter(Whitelist whitelist) {
		this.whitelist = whitelist;
	}

	public static XssFilter getInstance() {
		return getInstance(DEFAULT_CONFIG);
	}

	public static XssFilter getInstance(String fileName) {
		XssFilter filter = instanceMap.get(fileName);
		if (filter != null) {
			return filter;
		}
		synchronized (XssFilter.class) {
			filter = instanceMap.get(fileName);
			if (filter != null) {
				return filter;
			}
			filter = new XssFilter(XssConfiguration.newInstance(fileName));
			instanceMap.put(fileName, filter);
			return filter;
		}
	}

	public String doFilter(String dirty) {
		return Jsoup.clean(dirty, whitelist);
	}

}
package com.langmy.jFinal.handler.xss;

import com.langmy.jFinal.handler.xss.xssfilter.XssFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {

	private HttpServletRequest originRequest;

	public HttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		originRequest = request;
	}

	/**
	 * 重写并过滤getParameter方法
	 *
	 * @param name name
	 * @return param
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (null == value) {
			return null;
		}
		return XssFilter.getInstance().doFilter(value);
	}

	/**
	 * 重写并过滤getParameterValues方法
	 *
	 * @param name name
	 * @return value
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (null == values) {
			return null;
		}
		for (int i = 0; i < values.length; i++) {

			values[i] = XssFilter.getInstance().doFilter(values[i]);
		}
		return values;
	}

	/**
	 * 重写并过滤getParameterMap方法
	 *
	 * @return parammap
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> paramsMap = super.getParameterMap();
		// 对于paramsMap为空的直接return
		if (null == paramsMap || paramsMap.isEmpty()) {
			return paramsMap;
		}

		HashMap newParamsMap = new HashMap(paramsMap);
		for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			if (null == values) {
				continue;
			}
			String[] newValues = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				newValues[i] = XssFilter.getInstance().doFilter(values[i]);
			}
			newParamsMap.put(key, values);
		}
		return Collections.unmodifiableMap(newParamsMap);
	}

	/**
	 * 获取原始的request
	 *
	 * @return
	 */
	public HttpServletRequest getOriginRequest() {
		return originRequest;
	}

	/**
	 * 获取最原始的request的静态方法
	 *
	 * @return
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
		if (req instanceof HttpServletRequestWrapper) {
			return ((HttpServletRequestWrapper) req).getOriginRequest();
		}
		return req;
	}
}
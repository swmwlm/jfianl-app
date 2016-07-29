package com.shoukeplus.jFinal.handler;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;
import com.shoukeplus.jFinal.common.utils.ext.matcher.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FakeStaticHandler extends Handler {

	public static AntPathMatcher antPathMatcher = new AntPathMatcher();

	private String viewPostfix;

	public FakeStaticHandler() {
		viewPostfix = ".html";
	}

	public FakeStaticHandler(String viewPostfix) {
		if (StrKit.isBlank(viewPostfix))
			throw new IllegalArgumentException("viewPostfix can not be blank.");
		this.viewPostfix = viewPostfix;
	}

	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		if ("/".equals(target)) {
			next.handle(target, request, response, isHandled);
			return;
		}
		//关于JFinal (;jsessionid=)形式URL丢session问题解决方法
		//修复 url:test;jsessionid=XXXXXXXXXXX 形式url会话丢失问题
		int indexJ = target.toLowerCase().lastIndexOf(";jsessionid");
		target = indexJ == -1 ? target : target.substring(0, indexJ);

		//去除伪路径,跳转到真实的路径
		int indexP = target.lastIndexOf(viewPostfix);
		target = indexP == -1 ? target : target.substring(0, indexP);

		next.handle(target, request, response, isHandled);
	}
}
package com.langmy.jFinal.handler.xss;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AttackHandler extends Handler {
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		request = new HttpServletRequestWrapper(request);
		next.handle(target, request, response, isHandled);
	}
}
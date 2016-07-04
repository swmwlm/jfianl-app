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

		target = target.replace(";JSESSIONID", "?JSESSIONID");
		//i18n不支持json
//    if (!ThreadLocalKit.isJson())
//      request.setAttribute("i18n", I18N.me());

		//if (target.indexOf('.') == -1) {
		//	HandlerKit.renderError404(request, response, isHandled);
		//	return ;
		//}

		int index = target.lastIndexOf(viewPostfix);
		if (index != -1)
			target = target.substring(0, index);

		next.handle(target, request, response, isHandled);
	}
}
package com.langmy.jFinal.common.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HtmlHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,
					   HttpServletResponse response, boolean[] isHandled) {
		 if (target.toLowerCase().endsWith(".html")){
			 request.setAttribute("html",target);
			 target = "/jhtml";
		 }
		nextHandler.handle(target, request, response, isHandled);
	}

}
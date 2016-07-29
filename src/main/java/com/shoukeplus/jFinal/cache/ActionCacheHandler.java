package com.shoukeplus.jFinal.cache;

import com.jfinal.core.Action;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.render.RenderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * action的缓存处理器
 * 判断Action 是否有注解ActionCache.class,设置 缓存
 * 然后,通过JFreemarkerRender,重写render方法来获取缓存,进行页面呈现
 */
public class ActionCacheHandler extends Handler {
	static String[] urlPara = {null};

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		Action action = JFinal.me().getAction(target, urlPara);
		if (action == null) {
			next.handle(target, request, response, isHandled);
			return;
		}
		ActionCache actionCache = action.getMethod().getAnnotation(ActionCache.class);
		if (actionCache == null) {
			actionCache = action.getControllerClass().getAnnotation(ActionCache.class);
			if (actionCache == null) {
				next.handle(target, request, response, isHandled);
				return;
			}
		}
		String cacheKey = target;

		String queryString = request.getQueryString();
		if (queryString != null) {
			queryString = "?" + queryString;
			cacheKey += queryString;
		}

		ActionCacheManager.enableCache(request);
		ActionCacheManager.setCacheKey(request, cacheKey);
		ActionCacheManager.setCacheContentType(request, actionCache.contentType());

		String renderContent = ActionCacheManager.getCache(cacheKey);

		if (renderContent != null) {
			response.setContentType(actionCache.contentType());
			PrintWriter pw = null;
			try {
				pw = response.getWriter();
				pw.write(renderContent);
				isHandled[0] = true;
			} catch (Exception e) {
				RenderFactory.me().getErrorRender(500).setContext(request, response, action.getViewPath()).render();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		} else {
			next.handle(target, request, response, isHandled);
		}
	}
}

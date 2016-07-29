package com.shoukeplus.jFinal.render;

import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.RenderException;
import com.shoukeplus.jFinal.cache.ActionCacheManager;
import freemarker.template.Template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 可以自定义自己的实现
 */
public class JFreemarkerRender extends FreeMarkerRender {
	public JFreemarkerRender(String view) {
		super(view);
	}

	/**
	 * 在渲染给用户前后,做一些有意义的事情;
	 * 比如,缓存请求响应的内容;利用Jsoup替换一些html元素等操作;
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void render() {
		if (!ActionCacheManager.isEnableCache(request)) {
			super.render();
			return;
		}
		response.setContentType(ActionCacheManager.getCacheContentType(request));

		Map data = new HashMap();
		String attrName = null;

		for (Enumeration<String> attrs = request.getAttributeNames(); attrs.hasMoreElements(); ) {
			attrName = attrs.nextElement();
			data.put(attrName, request.getAttribute(attrName));
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter osw = null;
		PrintWriter pw = null;

		try {
			osw = new OutputStreamWriter(baos);
			pw = response.getWriter();

			Template template = getConfiguration().getTemplate(view);
			template.process(data, osw);
			osw.flush();

			String renderContent = new String(baos.toByteArray());

			//CDN处理,例如,替换图片地址 为CND地址;
			//renderContent=processCDN(renderContent);

			pw.write(renderContent);
			ActionCacheManager.putCache(request, renderContent);
		} catch (Exception e) {
			throw new RenderException(e);
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
				}
			}
			if (pw != null) {
				pw.close();
			}
		}

	}
}

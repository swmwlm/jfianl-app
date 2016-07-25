package com.shoukeplus.jFinal.render;

import com.jfinal.render.FreeMarkerRender;

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void render() {
		super.render();
	}
}

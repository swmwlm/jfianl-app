package com.shoukeplus.jFinal.beetl;

import com.jfinal.render.Render;
import org.beetl.ext.jfinal.BeetlRender;
import org.beetl.ext.jfinal.BeetlRenderFactory;

/**
 * 继承BeetlRenderFactory，调用MyBeetlRender，实现视图耗时计算
 */
public class MyBeetlRenderFactory extends BeetlRenderFactory {
	@Override
	public Render getRender(String view) {
		BeetlRender render = new MyBeetlRender(groupTemplate, view);
		return render;
	}

	@Override
	public String getViewExtension() {
//		return super.getViewExtension();
		return ".html";
	}
}
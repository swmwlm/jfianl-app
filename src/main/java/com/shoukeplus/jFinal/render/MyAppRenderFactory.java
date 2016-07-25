package com.shoukeplus.jFinal.render;

import com.jfinal.render.IMainRenderFactory;
import com.jfinal.render.Render;

public class MyAppRenderFactory implements IMainRenderFactory {
	@Override
	public Render getRender(String view) {
		return new JFreemarkerRender(view);
	}

	@Override
	public String getViewExtension() {
		return ".ftl";
	}
}

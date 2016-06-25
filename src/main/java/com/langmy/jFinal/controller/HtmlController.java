package com.langmy.jFinal.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import org.apache.commons.lang3.StringUtils;

import java.io.File;


public class HtmlController extends Controller {
	public void index(){

		String html = getAttrForStr("html");
		if(StringUtils.isNotEmpty(html) && new File(JFinal.me().getServletContext().getRealPath(html)).exists()){
			render(html);
		}else{
			//不存在404
			redirect("html/404.html");
		}
		
	}
}
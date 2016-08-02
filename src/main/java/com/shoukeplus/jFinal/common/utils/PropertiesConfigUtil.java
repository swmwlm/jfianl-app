package com.shoukeplus.jFinal.common.utils;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

/**
 * 单例模式实现读取配置文件,优先加载生产,若生产不存在,则加载测试环境;
 */
public class PropertiesConfigUtil {
	private static PropertiesConfigUtil propertiesConfigUtil=null;
	private Prop prop=null;

	private PropertiesConfigUtil(){
		prop=loadProp();
	}

	public static synchronized PropertiesConfigUtil getInstance(){
		if(propertiesConfigUtil==null){
			propertiesConfigUtil=new PropertiesConfigUtil();
		}
		return propertiesConfigUtil;
	}
	private Prop loadProp() {
		try {
			return PropKit.use("application_pro.properties");
		} catch (Exception e) {
			return PropKit.use("application.properties");
		}
	}
	public Prop getJProp(){
		return prop;
	}
	public String getUploadPathDisk(){
		return prop.get("uploadPathDisk");
	}
	public String getUploadPathVistHost(){
		return prop.get("uploadPathVistHost");
	}
}

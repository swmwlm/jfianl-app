package com.shoukeplus.jFinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.model.Link;
import com.shoukeplus.jFinal.common.model.Section;
import com.shoukeplus.jFinal.common.utils.DateUtil;

import java.util.Date;

public class CommonInterceptor implements Interceptor {


    public void intercept(Invocation ai) {
        Controller controller = ai.getController();

        String target=controller.getRequest().getRequestURI();
        String contextPath=controller.getRequest().getContextPath();

        controller.setAttr("siteTitle", AppConstants.getValue("siteTitle"));
        // 获取今天时间，放到session里
        controller.setSessionAttr("today", DateUtil.formatDate(new Date()));
        //后台
        if(target.startsWith(contextPath+"/admin")){
            ai.invoke();
            return;
        }
        // 查询板块
        controller.setAttr("sections", Section.dao.findShow());
        // 查询友链
        controller.setAttr("links", Link.dao.findAll());
        controller.setAttr("qq_meta", AppConstants.getValue("qq_meta"));
        controller.setAttr("sina_meta", AppConstants.getValue("sina_meta"));
        controller.setAttr("baidu_site_meta", AppConstants.getValue("baidu_site_meta"));
        controller.setAttr("google_site_meta", AppConstants.getValue("google_site_meta"));
        controller.setAttr("bing_site_meta", AppConstants.getValue("bing_site_meta"));
        controller.setAttr("beian_name", AppConstants.getValue("beian_name"));
        controller.setAttr("tongji_js", AppConstants.getValue("tongji_js"));
        ai.invoke();
    }
}
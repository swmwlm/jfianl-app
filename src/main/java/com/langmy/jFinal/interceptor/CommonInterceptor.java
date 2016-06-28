package com.langmy.jFinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.langmy.jFinal.common.AppConstants;
import com.langmy.jFinal.common.model.Link;
import com.langmy.jFinal.common.model.Section;
import com.langmy.jFinal.common.model.User;
import com.langmy.jFinal.common.utils.DateUtil;
import com.langmy.jFinal.common.utils.StrUtil;

import java.util.Date;

public class CommonInterceptor implements Interceptor {


    public void intercept(Invocation ai) {
        // session cookie 互换
        String user_cookie = ai.getController().getCookie(AppConstants.USER_COOKIE);
        User user_session = (User) ai.getController().getSession().getAttribute(AppConstants.USER_SESSION);
        if (StrUtil.isBlank(user_cookie) && user_session != null) {
            ai.getController().setCookie(AppConstants.USER_COOKIE, StrUtil.getEncryptionToken(user_session.getStr("token")), 30 * 24 * 60 * 60);
        } else if (!StrUtil.isBlank(user_cookie) && user_session == null) {
            User user = User.dao.findByToken(StrUtil.getDecryptToken(user_cookie));
            ai.getController().setSessionAttr(AppConstants.USER_SESSION, user);
        }
        Controller controller = ai.getController();
        // 获取今天时间，放到session里
        controller.setSessionAttr("today", DateUtil.formatDate(new Date()));
        // 查询板块
        controller.setAttr("sections", Section.dao.findShow());
        // 查询友链
        controller.setAttr("links", Link.dao.findAll());
        controller.setAttr("siteTitle", AppConstants.getValue("siteTitle"));
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
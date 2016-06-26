package com.langmy.jFinal.controller.admin;

import com.langmy.jFinal.common.controller.BaseController;
import com.langmy.jFinal.config.AppConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class AdminController extends BaseController {
    public void login() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            render("login.html");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            System.out.println(getRequest().getAttribute("shiroLoginFailure"));
            String username = getPara("username");
            String password = getPara("password");
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                subject.login(usernamePasswordToken);
                setSessionAttr(AppConstants.SESSION_ADMIN_USERNAME, username);
                redirect("/admin/index");
            } catch (AuthenticationException e) {
                e.printStackTrace();
                setAttr("error", "用户名或密码错误");
                render("login.html");
            }
        }
    }

    @RequiresPermissions("user:disabled")
    public void disabled() {
        //以后实现
    }

    public void index(){
        render("main.html");
    }

}
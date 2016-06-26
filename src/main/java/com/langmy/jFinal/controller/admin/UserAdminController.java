package com.langmy.jFinal.controller.admin;

import com.langmy.jFinal.common.controller.BaseController;
import com.langmy.jFinal.model.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class UserAdminController extends BaseController {

    @RequiresPermissions("menu:user")
    public void index() {
        String username = getPara("username");
        String email = getPara("email");
        setAttr("page", User.dao.page(getParaToInt("p", 1), defaultPageSize(), username, email));
        setAttr("nickname", username);
        setAttr("email", email);
        render("index.ftl");
    }

    @RequiresPermissions("user:disabled")
    public void disabled() {
        //以后实现
    }

}
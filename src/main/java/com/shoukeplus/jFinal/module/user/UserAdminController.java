package com.shoukeplus.jFinal.module.user;

import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;


@ControllerBind(controllerKey = "/admin/user", viewPath = "/WEB-INF/pages/admin/user")
public class UserAdminController extends BaseController {

    @RequiresPermissions("menu:user")
    public void index() {
        String nickname = getPara("nickname");
        String email = getPara("email");
        setAttr("page", User.dao.page(getParaToInt("p", 1), defaultPageSize(), nickname, email));
        setAttr("nickname", nickname);
        setAttr("email", email);
        render("index.ftl");
    }

    @RequiresPermissions("user:disabled")
    public void disabled() {
        //以后实现
    }

}

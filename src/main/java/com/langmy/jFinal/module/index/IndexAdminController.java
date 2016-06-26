package com.langmy.jFinal.module.index;

import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.SecurityUtils;

@ControllerBind(controllerKey = "/admin", viewPath = "/admin")
public class IndexAdminController extends BaseController {

//    @RequiresPermissions("menu:index")
    public void index() {
        render("main.html");
    }

    public void logout() {
        SecurityUtils.getSubject().logout();
        redirect("/adminlogin");
    }
}
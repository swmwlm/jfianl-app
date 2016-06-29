package com.langmy.jFinal.module.mission;

import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Mission;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

@ControllerBind(controllerKey = "/admin/mission", viewPath = "/WEB-INF/pages/admin/mission")
public class MissionAdminController extends BaseController {

    @RequiresPermissions("menu:mission")
    public void index() {
        setAttr("page", Mission.dao.paginate(getParaToInt("p", 1), defaultPageSize()));
        render("index.ftl");
    }
}

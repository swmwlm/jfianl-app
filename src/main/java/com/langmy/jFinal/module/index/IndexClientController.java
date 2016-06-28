package com.langmy.jFinal.module.index;

import com.jfinal.plugin.activerecord.Page;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.User;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;

@ControllerBind(controllerKey = "/api/index")
public class IndexClientController extends BaseController {

    public void index() {
        String tab = getPara("tab", "all");
        String q = getPara("q");
        Integer l = getParaToInt("l", 0);
        if (l != null && l > 0) {
            tab = "all";
        }
        Page<User> page = User.dao.paginate(getParaToInt("p", 1),
                getParaToInt("size", defaultPageSize()), tab, q, 1, l);
        for (User t : page.getList()) {
//            t.set("content", )
        }
        success(page);
    }
}
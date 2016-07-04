package com.shoukeplus.jFinal.module.link;

import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Link;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

@ControllerBind(controllerKey = "/admin/link", viewPath = "/WEB-INF/pages/admin/link")
public class LinkAdminController extends BaseController {

    @RequiresPermissions("menu:link")
    public void index() {
        setAttr("admin_links", Link.dao.findAll());
        render("index.ftl");
    }

    @RequiresPermissions("link:add")
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            render("add.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            Integer maxDisplayIndex = Link.dao.maxDisplayIndex();
            if (maxDisplayIndex == null) maxDisplayIndex = 0;
            getModel(Link.class).set("display_index", maxDisplayIndex + 1).save();
            clearCache(AppConstants.LINKCACHE, AppConstants.LINKLISTKEY);
            redirect("/admin/link");
        }
    }

    @RequiresPermissions("link:edit")
    public void edit() {
        String method = getRequest().getMethod();
        Integer id = getParaToInt("id");
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("link", Link.dao.findById(id));
            render("edit.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            getModel(Link.class).update();
            clearCache(AppConstants.LINKCACHE, AppConstants.LINKLISTKEY);
            redirect("/admin/link");
        }
    }

    @RequiresPermissions("link:delete")
    public void delete() {
        Integer id = getParaToInt("id");
        if (id == null) {
            error(AppConstants.OP_ERROR_MESSAGE);
        } else {
            try {
                Link.dao.deleteById(id);
                clearCache(AppConstants.LINKCACHE, AppConstants.LINKLISTKEY);
                success();
            } catch (Exception e) {
                e.printStackTrace();
                error(AppConstants.DELETE_FAILURE);
            }
        }
    }

    @RequiresPermissions("link:sort")
    public void sort() {
        Integer[] ids = getParaValuesToInt("ids");
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Link.dao.findById(ids[i]).set("display_index", i + 1).update();
            }
            clearCache(AppConstants.LINKCACHE, AppConstants.LINKLISTKEY);
        }
        redirect("/admin/link");
    }
}

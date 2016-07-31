package com.shoukeplus.jFinal.module.rollimages;

import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.AdminUser;
import com.shoukeplus.jFinal.common.model.Dict;
import com.shoukeplus.jFinal.common.model.RollImages;
import com.shoukeplus.jFinal.common.utils.DateUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

@ControllerBind(controllerKey = "/admin/rollimages", viewPath = "/WEB-INF/pages/admin/rollimages")
public class RollImagesAdminController extends BaseController {

    // 查询轮播图列表
    @RequiresPermissions("menu:rollimages")
    public void index() {
        setAttr("rollimageDict", Dict.dao.getList4Type("slider"));
        Integer dictId = getParaToInt("dictId", -1);
        setAttr("page", RollImages.dao.page(getParaToInt("p", 1), defaultPageSize(), dictId));
        setAttr("dictId", dictId);
        render("index.ftl");
    }

    // 添加轮播图
    @RequiresPermissions("rollimages:add")
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("rollimageDict", Dict.dao.getList4Type("slider"));
            setAttr("targetDict", Dict.dao.getList4Type("target"));
            render("add.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            AdminUser adminUser = getAdminUser();

            RollImages rollImages = getModel(RollImages.class, "rollImages");
            rollImages.setCreatedTime(DateUtil.getCurrentDateTime());
            rollImages.setCreator(adminUser.getId());
            rollImages.save();
            redirect("/admin/rollimages/index");
        }
    }

    // 编辑轮播图
    @RequiresPermissions("rollimages:edit")
    public void edit() {
        String method = getRequest().getMethod();
        Integer id = getParaToInt("id");
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("rollimageDict", Dict.dao.getList4Type("slider"));
            setAttr("targetDict", Dict.dao.getList4Type("target"));
            setAttr("rollimage", RollImages.dao.findById(id));
            render("edit.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            AdminUser adminUser = getAdminUser();

            RollImages rollImages = getModel(RollImages.class, "rollImages");
            rollImages.setUpdatedTime(DateUtil.getCurrentDateTime());
            rollImages.setLastModifier(adminUser.getId());
            rollImages.update();
            redirect("/admin/rollimages/index");
        }
    }

    // 删除轮播图
    @RequiresPermissions("rollimages:delete")
    public void delete() {
        Integer id = getParaToInt("id");
        if (id == null) {
            error(AppConstants.OP_ERROR_MESSAGE);
        } else {
            try {
                RollImages.dao.deleteById(id);
                success();
            } catch (Exception e) {
                e.printStackTrace();
                error(AppConstants.DELETE_FAILURE);
            }
        }
    }

}

package com.shoukeplus.jFinal.module.dict;

import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Dict;
import com.shoukeplus.jFinal.common.utils.DateUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

@ControllerBind(controllerKey = "/admin/dict", viewPath = "/WEB-INF/pages/admin/sysconfig/dict")
public class DictAdminController extends BaseController {

    @RequiresPermissions("setting:dict")
    public void index() {
        String value = getPara("name");
        setAttr("page", Dict.dao.page(getParaToInt("p", 1), defaultPageSize(), value));
        setAttr("name", value);
        render("index.ftl");
    }

    @RequiresPermissions("setting:dict")
    public void delete() {
        Integer id = getParaToInt("id");
        if (id != null) {
            if (Dict.dao.deleteById(id)) {
                success();
            } else {
                error("删除失败");
            }
        } else {
            error(AppConstants.OP_ERROR_MESSAGE);
        }
    }

    @RequiresPermissions("setting:dict")
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            render("add.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {

            Dict dict = new Dict();
            dict.setType(getPara("type"));
            dict.setKey(getPara("key"));
            dict.setValue(getPara("value"));
            dict.setSort(getParaToInt("sort"));
            dict.setRemark(getPara("remark"));
            dict.setCreatedTime(DateUtil.getCurrentDateTime());
            dict.save();

            redirect("/admin/dict/index");
        }
    }

    @RequiresPermissions("setting:dict")
    public void edit() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            Dict dict = Dict.dao.findById(getParaToInt(0));
            setAttr("dict", dict);
            render("edit.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            Dict dict = Dict.dao.findById(getParaToInt("id"));
            dict.set("type", getPara("type")).set("key",getPara("key")).set("value",getPara("value")).set("sort",getParaToInt("sort"))
                    .set("remark", getPara("remark")).set("updatedTime",DateUtil.getCurrentDateTime())
                    .update();
            redirect("/admin/dict/index");
        }
    }

}

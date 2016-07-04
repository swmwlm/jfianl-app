package com.shoukeplus.jFinal.module.dict;

import com.jfinal.upload.UploadFile;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Dict;
import com.shoukeplus.jFinal.common.model.Label;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.Date;

@ControllerBind(controllerKey = "/admin/dict", viewPath = "/WEB-INF/pages/admin/sysconfig")
public class DictAdminController extends BaseController {

    @RequiresPermissions("menu:setting")
    public void index() {
        String value = getPara("name");
        setAttr("page", Dict.dao.page(getParaToInt("p", 1), defaultPageSize(), value));
        setAttr("name", value);
        render("dict/index.ftl");
    }

    @RequiresPermissions("menu:setting")
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

    @RequiresPermissions("menu:setting")
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            render("add.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            UploadFile uploadFile = getFile("img", AppConstants.UPLOAD_DIR_LABEL);
            StringBuffer labelImg = new StringBuffer();
            if (uploadFile != null) {
                labelImg.append("/")
                        .append(AppConstants.UPLOAD_DIR)
                        .append("/")
                        .append(AppConstants.UPLOAD_DIR_LABEL)
                        .append("/")
                        .append(uploadFile.getFileName());
            }
            Label label = new Label();
            label.set("name", getPara("name"))
                    .set("description", getPara("description"))
                    .set("in_time", new Date())
                    .set("topic_count", 0)
                    .set("img", labelImg.toString())
                    .save();
            redirect("/admin/label/index");
        }
    }

    @RequiresPermissions("menu:setting")
    public void edit() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            Label label = Label.dao.findById(getParaToInt(0));
            setAttr("label", label);
            render("edit.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            UploadFile uploadFile = getFile("img", AppConstants.UPLOAD_DIR_LABEL);
            StringBuffer labelImg = new StringBuffer();
            if (uploadFile != null) {
                labelImg.append("/")
                        .append(AppConstants.UPLOAD_DIR)
                        .append("/")
                        .append(AppConstants.UPLOAD_DIR_LABEL)
                        .append("/")
                        .append(uploadFile.getFileName());
            }
            Label label = Label.dao.findById(getParaToInt("id"));
            label.set("name", getPara("name"))
                    .set("description", getPara("description"))
                    .set("img", labelImg.toString())
                    .update();
            redirect("/admin/label/index");
        }
    }

}

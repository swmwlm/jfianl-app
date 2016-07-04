package com.shoukeplus.jFinal.module.label;

import com.jfinal.upload.UploadFile;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Label;
import com.shoukeplus.jFinal.common.model.LabelTopicId;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.Date;

@ControllerBind(controllerKey = "/admin/label", viewPath = "/WEB-INF/pages/admin/label")
public class LabelAdminController extends BaseController {

    @RequiresPermissions("menu:label")
    public void index() {
        String name = getPara("name");
        setAttr("page", Label.dao.page(getParaToInt("p", 1), defaultPageSize(), name));
        setAttr("name", name);
        render("index.ftl");
    }

    @RequiresPermissions("label:delete")
    public void delete() {
        Integer id = getParaToInt("id");
        if (id != null) {
            if (Label.dao.deleteById(id)) {
                //删除中间表里关联的话题
                LabelTopicId.dao.deleteByLid(id);
                success();
            } else {
                error("删除失败");
            }
        } else {
            error(AppConstants.OP_ERROR_MESSAGE);
        }
    }

    @RequiresPermissions("label:add")
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

    @RequiresPermissions("label:edit")
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

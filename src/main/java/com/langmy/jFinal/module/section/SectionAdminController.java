package com.langmy.jFinal.module.section;

import com.langmy.jFinal.common.AppConstants;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Section;
import com.langmy.jFinal.common.utils.StrUtil;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

@ControllerBind(controllerKey = "/admin/section", viewPath = "/WEB-INF/pages/admin/section")
public class SectionAdminController extends BaseController {

    // 查询板块列表
    @RequiresPermissions("menu:section")
    public void index() {
        setAttr("admin_sections", Section.dao.findAll());
        render("index.ftl");
    }

    // 添加板块
    @RequiresPermissions("section:add")
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            render("add.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            String name = getPara("name");
            Integer show_status = getParaToInt("show_status");
            String tab = getPara("tab");
            Section section = new Section();
            section.set("name", name).set("show_status", show_status).set("tab", tab).set("display_index", 99).save();
            // clear cache
            clearCache(AppConstants.SECTIONCACHE, null);
            redirect("/admin/section/index");
        }
    }

    // 编辑板块
    @RequiresPermissions("section:edit")
    public void edit() {
        String method = getRequest().getMethod();
        Integer id = getParaToInt("id");
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("section", Section.dao.findById(id));
            render("edit.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            String name = getPara("name");
            Integer show_status = getParaToInt("show_status");
            String tab = getPara("tab");
            Section section = Section.dao.findById(id);
            section.set("name", name).set("show_status", show_status).set("tab", tab).update();
            // clear cache
            clearCache(AppConstants.SECTIONCACHE, null);
            redirect("/admin/section/index");
        }
    }

    // 排序
    @RequiresPermissions("section:sort")
    public void sort() {
        Integer[] ids = getParaValuesToInt("ids");
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Section section = Section.dao.findById(ids[i]);
                section.set("display_index", i + 1).update();
            }
        }
        // clear cache
        clearCache(AppConstants.SECTIONCACHE, null);
        redirect("/admin/section/index");
    }

    // 删除板块
    @RequiresPermissions("section:delete")
    public void delete() {
        Integer id = getParaToInt("id");
        if (id == null) {
            error(AppConstants.OP_ERROR_MESSAGE);
        } else {
            try {
                Section.dao.deleteById(id);
                clearCache(AppConstants.SECTIONCACHE, null);
                success();
            } catch (Exception e) {
                e.printStackTrace();
                error(AppConstants.DELETE_FAILURE);
            }
        }
    }

    @RequiresPermissions("section:setDefault")
    public void setDefault() {
        String tab = getPara("tab");
        if (StrUtil.isBlank(tab)) {
            error("设置失败");
        } else {
            Section defaultSection = Section.dao.findDefault();
            if (!tab.equals(defaultSection.getStr("tab"))) {
                defaultSection.set("default_show", 0).update();
                Section section = Section.dao.findByTab(tab);
                section.set("default_show", 1).update();
                clearCache(AppConstants.SECTIONCACHE, null);
            }
            success();
        }
    }
}

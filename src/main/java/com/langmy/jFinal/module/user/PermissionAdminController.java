package com.langmy.jFinal.module.user;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Permission;
import com.langmy.jFinal.common.model.RolePermission;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.List;

@ControllerBind(controllerKey = "/admin/permission", viewPath = "/WEB-INF/pages/admin/sysconfig/permission")
public class PermissionAdminController extends BaseController {

    @RequiresPermissions("setting:permission")
    public void index() {
        Integer permissionId = getParaToInt("permissionId");
        List<Permission> permissions = Permission.dao.findAll();
        if (permissionId == null) {
            Permission permission = permissions.get(0);
            permissionId = permission.getInt("id");
        }
        setAttr("permissionId", permissionId);
        setAttr("permissions", permissions);
        render("index.ftl");
    }

    @RequiresPermissions("setting:permission")
    public void add() {
        try {
            Integer pid = getParaToInt("pid");
            String name = getPara("name");
            String description = getPara("description");
            Permission permission = new Permission();
            permission.set("pid", pid)
                    .set("name", name)
                    .set("description", description)
                    .save();
            success(permission);
        } catch (Exception e) {
            e.printStackTrace();
            error("保存失败");
        }
    }

    @RequiresPermissions("setting:permission")
    public void edit() {
        try {
            Integer id = getParaToInt("id");
            Integer pid = getParaToInt("pid");
            String name = getPara("name");
            String description = getPara("description");
            Permission permission = new Permission();
            permission.set("id", id)
                    .set("pid", pid)
                    .set("name", name)
                    .set("description", description)
                    .update();
            success(permission);
        } catch (Exception e) {
            e.printStackTrace();
            error("更新失败");
        }
    }

    @RequiresPermissions("setting:permission")
    @Before(Tx.class)
    public void delete() {
        Integer id = getParaToInt("id");
        Integer pid = getParaToInt("pid");
        Permission.dao.deleteById(id);
        //删除于角色关联的权限
        RolePermission.dao.deleteByPermissionId(id);
        success(pid);
    }
}

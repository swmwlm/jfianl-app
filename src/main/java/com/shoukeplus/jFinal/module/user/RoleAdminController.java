package com.shoukeplus.jFinal.module.user;

import com.jfinal.aop.Before;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Permission;
import com.shoukeplus.jFinal.common.model.Role;
import com.shoukeplus.jFinal.common.model.RolePermission;
import com.shoukeplus.jFinal.common.model.UserRole;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

@ControllerBind(controllerKey = "/admin/role", viewPath = "/WEB-INF/pages/admin/sysconfig/role")
public class RoleAdminController extends BaseController {

    @RequiresPermissions("setting:role")
    public void index() {
        setAttr("page", Role.dao.page(getParaToInt("p", 1), defaultPageSize()));
        render("index.ftl");
    }

    @RequiresPermissions("setting:role")
    @Before(Tx.class)
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("permissions", Permission.dao.findAll());
            render("add.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            Role role = getModel(Role.class);
            role.save();
            Integer[] pids = getParaValuesToInt("permissions");
            Role.dao.correlationPermission(role.getInt("id"), pids);
            redirect("/admin/role");
        }
    }

    @RequiresPermissions("setting:role")
    public void edit() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            Integer id = getParaToInt("id");
            if (id != null) {
                Role role = Role.dao.findById(id);
                setAttr("role", role);
                setAttr("permissions", Permission.dao.findAll());
                setAttr("rolePermissions", RolePermission.dao.findByRoleId(id));
                render("edit.ftl");
            } else {
                LogKit.error("角色ID不能为空");
                renderError(500);
            }
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            Role role = getModel(Role.class);
            role.update();
            Integer[] pids = getParaValuesToInt("permissions");
            Role.dao.correlationPermission(role.getInt("id"), pids);
            //清楚所有shiro缓存,保证权限及时生效
            clearCache(AppConstants.SHIROCACHE, null);
            redirect("/admin/role");
        }
    }

    @RequiresPermissions("setting:role")
    @Before(Tx.class)
    public void delete() {
        Integer roleId = getParaToInt("id");
        if (roleId == null) {
            renderError(500);
        } else {
            Role.dao.deleteById(roleId);
            //删除关联的用户
            UserRole.dao.deleteByRoleId(roleId);
            //删除关联的权限
            RolePermission.dao.deleteByRoleId(roleId);
            success();
        }
    }
}

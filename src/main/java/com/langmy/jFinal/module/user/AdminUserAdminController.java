package com.langmy.jFinal.module.user;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.langmy.jFinal.common.AppConstants;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.AdminUser;
import com.langmy.jFinal.common.model.Role;
import com.langmy.jFinal.common.model.UserRole;
import com.langmy.jFinal.common.utils.PasswordHelper;
import com.langmy.jFinal.common.utils.StrUtil;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import java.util.Date;

@ControllerBind(controllerKey = "/admin/adminuser", viewPath = "/WEB-INF/pages/admin/sysconfig/adminuser")
public class AdminUserAdminController extends BaseController {

    @RequiresPermissions("setting:adminuser")
    public void index() {
        Page<AdminUser> page = AdminUser.dao.page(getParaToInt("p", 1), defaultPageSize());
        setAttr("page", page);
        render("index.ftl");
    }

    @RequiresPermissions("setting:adminuser")
    @Before(Tx.class)
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("roles", Role.dao.findAll());
            render("add.ftl");
        } else {
            AdminUser adminUser = new AdminUser();
            adminUser.set("username", getPara("username"))
                    .set("password", getPara("password"))
                    .set("in_time", new Date());
            PasswordHelper.encryptPassword(adminUser);
            adminUser.save();
            //处理关联角色
            Integer[] roles = getParaValuesToInt("roles");
            AdminUser.dao.correlationRole(adminUser.getInt("id"), roles);
            redirect("/admin/adminuser");
        }
    }

    @RequiresPermissions("setting:adminuser")
    @Before(Tx.class)
    public void edit() {
        Integer id = getParaToInt("id");
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("adminUser", AdminUser.dao.findById(id));
            setAttr("roles", Role.dao.findAll());
            setAttr("uroles", UserRole.dao.findByUserId(id));
            render("edit.ftl");
        } else {
            String password = getPara("password");
            String username = getPara("username");
            AdminUser adminUser = new AdminUser();
            adminUser.set("id", id)
                    .set("username", username)
                    .set("in_time", new Date());
            if (StrUtil.notBlank(password)) {
                adminUser.set("password", password);
                PasswordHelper.encryptPassword(adminUser);
            }
            adminUser.update();
            //处理关联角色
            Integer[] roles = getParaValuesToInt("roles");
            AdminUser.dao.correlationRole(id, roles);
            //清理缓存保证角色设置及时生效
            clearCache(AppConstants.SHIROCACHE, AppConstants.ROLECACHEKEY + username);
            redirect("/admin/adminuser");
        }
    }

    @RequiresPermissions("setting:adminuser")
    @Before(Tx.class)
    public void delete() {
        Integer id = getParaToInt("id");
        if (id == null) {
            renderError(500);
        } else {
            AdminUser.dao.deleteById(id);
            //删除关联的角色
            UserRole.dao.deleteByUserId(id);
            success();
        }
    }

    @RequiresPermissions("setting:modifypwd")
    public void modifypwd() {
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            AdminUser adminUser = AdminUser.dao.findByUsername(username);
            setAttr("adminUser", adminUser);
            render("modifypwd.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            String password = getPara("password");
            if (StrUtil.isBlank(password)) {
                setAttr("errMsg", "密码不能为空");
                render("modifypwd.ftl");
            } else {
                AdminUser adminUser = new AdminUser();
                adminUser.set("id", getParaToInt("id"))
                        .set("username", username)
                        .set("password", password);
                PasswordHelper.encryptPassword(adminUser);
                adminUser.update();
                subject.logout();
                redirect("/adminlogin");
            }
        }
    }

}

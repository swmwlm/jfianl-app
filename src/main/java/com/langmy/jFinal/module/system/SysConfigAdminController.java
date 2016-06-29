package com.langmy.jFinal.module.system;

import com.langmy.jFinal.common.AppConstants;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.SysConfig;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.Map;

@ControllerBind(controllerKey = "/admin/sysconfig", viewPath = "/WEB-INF/pages/admin/sysconfig")
public class SysConfigAdminController extends BaseController {

    @RequiresPermissions("setting:sysconfig")
    public void index() {
        Map<String, Object> map = SysConfig.dao.findAll2Map();
        setAttrs(map);
        render("index.ftl");
    }

    /**
     * 更新系统设置
     */
    @RequiresPermissions("setting:sysconfig")
    public void save() {
        SysConfig.dao.update("siteTitle", getPara("siteTitle"));
        SysConfig.dao.update("pageSize", getPara("pageSize"));
        SysConfig.dao.update("qq_appId", getPara("qq_appId"));
        SysConfig.dao.update("qq_appKey", getPara("qq_appKey"));
        SysConfig.dao.update("qq_redirect_URI", getPara("qq_redirect_URI"));
        SysConfig.dao.update("sina_clientId", getPara("sina_clientId"));
        SysConfig.dao.update("sina_clientSercret", getPara("sina_clientSercret"));
        SysConfig.dao.update("emailSmtp", getPara("emailSmtp"));
        SysConfig.dao.update("emailSender", getPara("emailSender"));
        SysConfig.dao.update("emailUsername", getPara("emailUsername"));
        SysConfig.dao.update("emailPassword", getPara("emailPassword"));
        SysConfig.dao.update("qq_meta", getPara("qq_meta"));
        SysConfig.dao.update("sina_meta", getPara("sina_meta"));
        SysConfig.dao.update("baidu_site_meta", getPara("baidu_site_meta"));
        SysConfig.dao.update("google_site_meta", getPara("google_site_meta"));
        SysConfig.dao.update("bing_site_meta", getPara("bing_site_meta"));
        SysConfig.dao.update("beian_name", getPara("beian_name"));
        SysConfig.dao.update("sina_redirect_URI", getPara("sina_redirect_URI"));
        SysConfig.dao.update("tongji_js", getPara("tongji_js"));
        clearCache(AppConstants.SYSCONFIGCACHE, AppConstants.SYSCONFIGCACHEKEY);
        redirect("/admin/sysconfig");
    }
    @RequiresPermissions("setting:druid")
    public void druid() {
        render("druid/index.ftl");
    }
}

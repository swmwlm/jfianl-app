package com.shoukeplus.jFinal.common;

import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;
import com.shoukeplus.jFinal.common.model.AdminUser;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.Result;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class BaseController extends Controller {
    public Integer defaultPageSize() {
        return StrUtil.str2int(AppConstants.getValue("pageSize"));
    }

    public void success() {
        success(null);
    }

    public void success(Object object) {
        renderJson(new Result(AppConstants.CODE_SUCCESS, AppConstants.DESC_SUCCESS, object));
    }

    public void error(String message) {
        renderJson(new Result(AppConstants.CODE_FAILURE, message, null));
    }

    /**
     * 根据cacheName, cacheKey来清除缓存
     * cacheName 必填，cacheKey选填，不填的话为null
     *
     * @param cacheName
     * @param cacheKey
     */
    public void clearCache(String cacheName, Object cacheKey) {
        if (cacheKey == null) {
            CacheKit.removeAll(cacheName);
        } else {
            CacheKit.remove(cacheName, cacheKey);
        }
    }

    /**
     * 根据用户令牌获取用户信息
     *
     * @param token
     * @return
     */
    public User getUser(String token) {
        return User.dao.findByToken(token);
    }

    /**
     * 根据用户名查询后台用户
     *
     * @return
     */
    public AdminUser getAdminUser() {
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        return AdminUser.dao.findByUsername(username);
    }
}
package com.shoukeplus.jFinal.common;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.NotAction;
import com.jfinal.plugin.ehcache.CacheKit;
import com.shoukeplus.jFinal.common.model.AdminUser;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.Result;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import com.shoukeplus.jFinal.render.JCaptchaRender;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        //Subject subject = SecurityUtils.getSubject();
        //String username = subject.getPrincipal().toString();
        //return AdminUser.dao.findByUsername(username);
        return (AdminUser) getRequest().getAttribute(AppConstants.CURRENT_ADMIN_USER);
    }
    /**
     * 处理前台传递的对象数组 参数
     * @param modelName
     * @return
     */
    public <T> List<T> getModels(Class<T> modelClass, String modelName) {
        List<String> nos = getModelsNoList(modelName);
        List<T> list = new ArrayList<T>();
        for (String no : nos) {
            T m = getModel(modelClass, modelName + "[" + no + "]");
            if (m != null) {
                list.add(m);
            }
        }
        return list;
    }

    private List<String> getModelsNoList(String modelName) {
        // 提取标号
        List<String> list = new ArrayList<>();
        String modelNameAndLeft = modelName + "[";
        Map<String, String[]> parasMap = getRequest().getParameterMap();
        for (Map.Entry<String, String[]> e : parasMap.entrySet()) {
            String paraKey = e.getKey();
            if (paraKey.startsWith(modelNameAndLeft)) {
                String no = paraKey.substring(paraKey.indexOf("[") + 1,
                        paraKey.indexOf("]"));
                if (!list.contains(no)) {
                    list.add(no);
                }
            }
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public boolean validateCaptcha(String paraName) {
        return JCaptchaRender.validate(this,getPara(paraName));
    }

    @Override
    @Before(NotAction.class)
    public void renderCaptcha() {
        render(new JCaptchaRender(this));
    }

    @Before(NotAction.class)
    public String getIPAddress() {
        String ip = getRequest().getHeader("X-getRequest()ed-For");
        if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("X-Forwarded-For");
        }
        if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getRemoteAddr();
        }
        return ip;
    }

    @Before(NotAction.class)
    public String getUserAgent() {
        return getRequest().getHeader("User-Agent");
    }
    @Before(NotAction.class)
    public SessionDAO getSessionDao(){
        SecurityManager securityManager= SecurityUtils.getSecurityManager();
        DefaultWebSecurityManager defaultWebSecurityManager= (DefaultWebSecurityManager) securityManager;
        DefaultSessionManager defaultSessionManager= (DefaultSessionManager) defaultWebSecurityManager.getSessionManager();
        return defaultSessionManager.getSessionDAO();
    }
}
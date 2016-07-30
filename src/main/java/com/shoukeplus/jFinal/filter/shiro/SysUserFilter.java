package com.shoukeplus.jFinal.filter.shiro;

import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.model.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SysUserFilter extends PathMatchingFilter {

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute(AppConstants.CURRENT_ADMIN_USER, AdminUser.dao.findByUsername(username));
        return true;
    }
}
package com.shoukeplus.jFinal.module.notification;

import com.jfinal.aop.Before;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Notification;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import com.shoukeplus.jFinal.interceptor.UserInterceptor;

@ControllerBind(controllerKey = "/notification", viewPath = "/WEB-INF/pages")
public class NotificationController extends BaseController {

    @Before(UserInterceptor.class)
    public void countnotread() {
        User user = getSessionAttr(AppConstants.USER_SESSION);
        if (user == null) {
            error(AppConstants.DESC_FAILURE);
        } else {
            try {
                int count = Notification.dao.countNotRead(user.getStr("id"));
                success(count);
            } catch (Exception e) {
                e.printStackTrace();
                error(AppConstants.DESC_FAILURE);
            }
        }
    }
}

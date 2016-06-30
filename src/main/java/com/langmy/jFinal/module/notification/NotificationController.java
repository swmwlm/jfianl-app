package com.langmy.jFinal.module.notification;

import com.jfinal.aop.Before;
import com.langmy.jFinal.common.AppConstants;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Notification;
import com.langmy.jFinal.common.model.User;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import com.langmy.jFinal.interceptor.UserInterceptor;

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

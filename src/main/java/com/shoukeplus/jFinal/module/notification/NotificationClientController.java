package com.shoukeplus.jFinal.module.notification;

import com.jfinal.aop.Before;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Notification;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import com.shoukeplus.jFinal.interceptor.ClientInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/api/notification")
@Before(ClientInterceptor.class)
public class NotificationClientController extends BaseController {

    public void countnotread() {
        String token = getPara("token");
        User user = getUser(token);
        int count = Notification.dao.countNotRead(user.getStr("id"));
        success(count);
    }

    public void index() {
        String token = getPara("token");
        User user = getUser(token);
        List<Notification> notifications = Notification.dao.findNotReadByAuthorId(user.getStr("id"));
        List<Notification> oldMessages = Notification.dao.findReadByAuthorId(user.getStr("id"), 20);
        //将消息置为已读
        Notification.dao.updateNotification(user.getStr("id"));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("notifications", notifications);
        map.put("oldMessages", oldMessages);
        success(map);
    }
}

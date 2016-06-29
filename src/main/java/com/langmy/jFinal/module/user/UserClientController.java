package com.langmy.jFinal.module.user;

import com.jfinal.aop.Before;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Collect;
import com.langmy.jFinal.common.model.Topic;
import com.langmy.jFinal.common.model.User;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import com.langmy.jFinal.interceptor.ClientInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/api/user")
@Before(ClientInterceptor.class)
public class UserClientController extends BaseController {

    public void index() {
        Map<String, Object> map = new HashMap<String, Object>();
        String token = getPara("token");
        User user = getUser(token);
        User resultUser = new User();
        resultUser.set("nickname", user.get("nickname"));
        resultUser.set("signature", user.get("signature"));
        resultUser.set("avatar", user.get("avatar"));
        resultUser.set("score", user.get("score"));
        resultUser.set("url", user.get("url"));
        resultUser.set("in_time", user.get("in_time"));
        map.put("user", resultUser);
        List<Topic> topics = Topic.dao.paginateByAuthorId(1, 10, user.getStr("id")).getList();
        map.put("topics", topics);
        List<Collect> collects = Collect.dao.findByAuthorIdWithTopic(user.getStr("id"));
        map.put("collects", collects);
        success(map);
    }

    public void userinfo() {
        String token = getPara("token");
        User user = getUser(token);
        if (user == null) {
            error("无效令牌");
        } else {
            User resultUser = new User();
            resultUser.set("nickname", user.get("nickname"));
            resultUser.set("signature", user.get("signature"));
            resultUser.set("avatar", user.get("avatar"));
            resultUser.set("score", user.get("score"));
            resultUser.set("url", user.get("url"));
            resultUser.set("in_time", user.get("in_time"));
            success(resultUser);
        }
    }
}

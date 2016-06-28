package com.langmy.jFinal.module.index;

import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Mission;
import com.langmy.jFinal.common.model.Reply;
import com.langmy.jFinal.common.model.Topic;
import com.langmy.jFinal.common.model.User;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.List;

@ControllerBind(controllerKey = "/admin", viewPath = "/WEB-INF/pages/admin")
public class IndexAdminController extends BaseController {

    @RequiresPermissions("menu:index")
    public void index() {
        //今日话题
        List<Topic> topics = Topic.dao.findToday();
        setAttr("topics", topics);
        //今日回复
        List<Reply> replies = Reply.dao.findToday();
        setAttr("replies", replies);
        //今日签到
        List<Mission> missions = Mission.dao.findToday();
        setAttr("missions", missions);
        //今日用户
        List<User> users = User.dao.findToday();
        setAttr("users", users);
        render("index.ftl");
    }

    public void logout() {
        SecurityUtils.getSubject().logout();
        redirect("/adminlogin");
    }
}
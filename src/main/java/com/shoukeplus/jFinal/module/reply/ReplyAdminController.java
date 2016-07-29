package com.shoukeplus.jFinal.module.reply;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.AdminLog;
import com.shoukeplus.jFinal.common.model.AdminUser;
import com.shoukeplus.jFinal.common.model.Reply;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.Date;

@ControllerBind(controllerKey = "/admin/reply", viewPath = "/WEB-INF/pages/admin/reply")
public class ReplyAdminController extends BaseController {

    @RequiresPermissions("menu:reply")
    public void index() {
        setAttr("page", Reply.dao.page(getParaToInt("p", 1), defaultPageSize()));
        render("index.ftl");
    }

    @RequiresPermissions("reply:delete")
    @Before(Tx.class)
    public void delete() {
        String id = getPara("id");
        try {
            Reply reply = Reply.dao.findById(id);
            String content = reply.getStr("content");
            User user = User.dao.findById(reply.get("author_id"));
            reply.set("id", id).set("content", "回复已被删除").set("isdelete", 1).update();
            if (user.getInt("score") <= 2) {
                user.set("score", 0).update();
            } else {
                user.set("score", user.getInt("score") - 2).update();
            }
            //日志记录
            AdminUser adminUser = getAdminUser();
            AdminLog adminLog = new AdminLog();
            adminLog.set("uid", adminUser.getInt("id"))
                    .set("target_id", id)
                    .set("source", "reply")
                    .set("in_time", new Date())
                    .set("action", "delete")
                    .set("message", content)
                    .save();
            success();
        } catch (Exception e) {
            e.printStackTrace();
            error(AppConstants.DELETE_FAILURE);
        }
    }
}

package com.shoukeplus.jFinal.module.reply;

import com.jfinal.aop.Before;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Notification;
import com.shoukeplus.jFinal.common.model.Reply;
import com.shoukeplus.jFinal.common.model.Topic;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import com.shoukeplus.jFinal.interceptor.ClientInterceptor;

import java.util.Date;
import java.util.List;

@ControllerBind(controllerKey = "/api/reply")
@Before(ClientInterceptor.class)
public class ReplyClientController extends BaseController {

    public void create() {
        String tid = getPara("tid");
        String token = getPara("token");
        Date date = new Date();
        Topic topic = Topic.dao.findById(tid);
        User user = getUser(token);
        if (topic == null) {
            error("回复的话题不存在");
        } else {
            // 回复数量+1
            topic.set("reply_count", topic.getInt("reply_count") + 1).update();
            // 增加1积分
            Reply reply = new Reply();
            String content = StrUtil.transHtml(getPara("content"));
            reply.set("id", StrUtil.getUUID())
                    .set("tid", tid)
                    .set("content", content)
                    .set("in_time", date)
                    .set("author_id", user.get("id"));
            reply.save();
            //话题最后回复时间更新
            topic.set("last_reply_time", date).set("last_reply_author_id", user.get("id")).update();
            //用户积分增加
            user.set("score", user.getInt("score") + 1).update();
            List<String> ats = StrUtil.findAt(content);
            //发送通知
            boolean flag = false;
            if (ats.size() > 0) {
                for (String nickname : ats) {
                    User user1 = User.dao.findByNickname(nickname);
                    if (user1 != null) {
                        //将@xx转换成链接
                        content = content.replace("@" + nickname, "<a href='" + "/user/" + user1.getStr("id") + "'>@" + nickname + "</a>");
                        if (!user1.getStr("id").equals(user.getStr("id"))) {
                            Notification collectNoti = new Notification();
                            collectNoti.set("target_id", tid + "#" + reply.get("id"))
                                    .set("read", 0)
                                    .set("action", AppConstants.NOTIFICATION_MESSAGE)
                                    .set("message", topic.get("title"))
                                    .set("from_author_id", user.get("id"))
                                    .set("author_id", user1.get("id"))
                                    .set("in_time", date)
                                    .set("source", "topic").save();
                        }
                        if (user1.getStr("id").equals(topic.getStr("author_id"))) {
                            flag = true;
                        }
                    }
                }
            }
            //更新内容
            reply.set("content", content).update();
            //通知话题作者
            if (!user.getStr("id").equals(topic.getStr("author_id")) && !flag) {
                Notification collectNoti = new Notification();
                collectNoti.set("target_id", tid + "#" + reply.get("id"))
                        .set("read", 0)
                        .set("action", AppConstants.NOTIFICATION_MESSAGE1)
                        .set("message", topic.get("title"))
                        .set("from_author_id", user.get("id"))
                        .set("author_id", topic.get("author_id"))
                        .set("in_time", date)
                        .set("source", "topic").save();
            }
            List<Reply> replies = Reply.dao.findByTid(tid);
            success(replies);
        }
    }
}

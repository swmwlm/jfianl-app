package com.langmy.jFinal.module.user;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.langmy.jFinal.common.AppConstants;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Collect;
import com.langmy.jFinal.common.model.Notification;
import com.langmy.jFinal.common.model.Topic;
import com.langmy.jFinal.common.model.User;
import com.langmy.jFinal.common.utils.FileUploadUtil;
import com.langmy.jFinal.common.utils.ImageUtil;
import com.langmy.jFinal.common.utils.StrUtil;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import com.langmy.jFinal.interceptor.UserInterceptor;

import java.util.List;


@ControllerBind(controllerKey = "/user", viewPath = "/WEB-INF/pages")
public class UserController extends BaseController {

    public void index() {
        String id = getPara(0);
        User user = User.dao.findById(id);
        Integer day = User.dao.findDayByAuthorId(id);
        if (user != null) {
            setAttr("current_user", user);
            setAttr("day", day);
            Page<Collect> collectPage = Collect.dao.findByAuthorId(getParaToInt("p", 1),
                    defaultPageSize(), user.getStr("id"));
            setAttr("collectPage", collectPage);
            Page<Topic> topics = Topic.dao.paginateByAuthorId(1, 5, user.getStr("id"));
            setAttr("topics", topics);
            //查询我回复的话题
            Page<Topic> myReplyTopics = Topic.dao.paginateMyReplyTopics(1, 5, user.getStr("id"));
            setAttr("myReplyTopics", myReplyTopics);
//            if (!AgentUtil.getAgent(getRequest()).equals(AgentUtil.WEB)) render("mobile/user/index.ftl");
            render("front/user/index.ftl");
        } else {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        }
    }

    public void collects() {
        String uid = getPara(0);
        User user = User.dao.findById(uid);
        if (user == null) {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        } else {
            setAttr("current_user", user);
            Page<Collect> collectPage = Collect.dao.findByAuthorIdWithTopic(getParaToInt("p", 1),
                    defaultPageSize(), user.getStr("id"));
            setAttr("page", collectPage);
            render("front/user/collects.ftl");
        }
    }

    public void topics() {
        String uid = getPara(0);
        User user = User.dao.findById(uid);
        if (user == null) {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        } else {
            setAttr("current_user", user);
            Page<Topic> page = Topic.dao.paginateByAuthorId(getParaToInt("p", 1),
                    defaultPageSize(), user.getStr("id"));
            setAttr("page", page);
            render("front/user/topics.ftl");
        }
    }

    public void replies() {
        String uid = getPara(0);
        User user = User.dao.findById(uid);
        if (user == null) {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        } else {
            setAttr("current_user", user);
            Page<Topic> myReplyTopics = Topic.dao.paginateMyReplyTopics(getParaToInt("p", 1), defaultPageSize(), user.getStr("id"));
            setAttr("page", myReplyTopics);
            render("front/user/replies.ftl");
        }
    }

    public void top100() {
        List<User> top100 = User.dao.findBySize(100);
        setAttr("top100", top100);
        render("front/user/top100.ftl");
    }

    @Before(UserInterceptor.class)
    public void message() {
        String uid = getPara(0);
        if (StrUtil.isBlank(uid)) renderText(AppConstants.OP_ERROR_MESSAGE);
        List<Notification> notifications = Notification.dao.findNotReadByAuthorId(uid);
        setAttr("notifications", notifications);
        Page<Notification> oldMessages = Notification.dao.paginate(getParaToInt("p", 1),
                defaultPageSize(), uid);
        setAttr("oldMessages", oldMessages);
        //将消息置为已读
        Notification.dao.updateNotification(uid);
        render("front/user/message.ftl");
    }

    @Before(UserInterceptor.class)
    public void setting() throws InterruptedException {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("save", getPara("save"));
            render("front/user/setting.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            User user = getSessionAttr(AppConstants.USER_SESSION);
            String url = getPara("url");
            String nickname = getPara("nickname");
            if (!user.getStr("nickname").equalsIgnoreCase(nickname) && User.dao.findByNickname(nickname) != null) {
                error("此昵称已被注册,请更换昵称");
            } else {
                if (!StrUtil.isBlank(url)) {
                    if (!url.substring(0, 7).equalsIgnoreCase("http://")) {
                        url = "http://" + url;
                    }
                }
                user.set("url", StrUtil.transHtml(url))
                        .set("nickname", StrUtil.noHtml(nickname).trim())
                        .set("signature", StrUtil.transHtml(getPara("signature"))).update();
                //保存成功
                setSessionAttr(AppConstants.USER_SESSION, user);
                success();
            }
        }
    }

    @Before(UserInterceptor.class)
    public void cancelBind() {
        String pt = getPara("pt");
        if (StrUtil.isBlank(pt)) {
            error("非法操作");
        } else {
            User user = (User) getSession().getAttribute(AppConstants.USER_SESSION);
            if (pt.equalsIgnoreCase(AppConstants.QQ)) {
                user.set("qq_open_id", null)
                        .set("qq_avatar", null)
                        .set("qq_nickname", null)
                        .update();
            } else if (pt.equalsIgnoreCase(AppConstants.SINA)) {
                user.set("sina_open_id", null)
                        .set("sina_avatar", null)
                        .set("sina_nickname", null)
                        .update();
            }/* else if(pt.equalsIgnoreCase(Constants.ThirdLogin.WECHAT)) {
                user.set("wechat_open_id", null)
                        .set("wechat_avatar", null)
                        .update();
            }*/
            setSessionAttr(AppConstants.USER_SESSION, user);
            setCookie(AppConstants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
            success();
        }
    }

    @Before(UserInterceptor.class)
    public void uploadAvatar() throws Exception {

        String relativePath=FileUploadUtil.upload(AppConstants.UPLOAD_DIR_AVATAR,getFiles(AppConstants.UPLOAD_DIR_AVATAR));

        User user = (User) getSession().getAttribute(AppConstants.USER_SESSION);
        user.set("avatar", relativePath).update();
        //裁剪图片
        String realPath=AppConstants.UPLOAD_DIR+relativePath;
        ImageUtil.zoomImage(realPath, realPath, 100, 100);
        redirect("/user/setting");
    }

}

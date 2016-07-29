package com.shoukeplus.jFinal.module.collect;

import com.jfinal.aop.Before;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Collect;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import com.shoukeplus.jFinal.interceptor.UserInterceptor;

import java.util.Date;


@ControllerBind(controllerKey = "/collect", viewPath = "/WEB-INF/pages")
public class CollectController extends BaseController {

    @Before(UserInterceptor.class)
    public void index() {
        String tid = getPara(0);
        User user = getSessionAttr(AppConstants.USER_SESSION);
        Collect collect = Collect.dao.findByTidAndAuthorId(tid, user.getStr("id"));
        if (collect != null) {
            error("已经收藏过，无需再次收藏");
        } else {
            collect = new Collect();
            boolean b = collect.set("tid", tid)
                    .set("author_id", user.get("id"))
                    .set("in_time", new Date()).save();
            if (!b) {
                error("收藏失败");
            }
            success();
        }
    }

    @Before(UserInterceptor.class)
    public void delete() {
        String tid = getPara(0);
        User user = getSessionAttr(AppConstants.USER_SESSION);
        Collect collect = Collect.dao.findByTidAndAuthorId(tid, user.getStr("id"));
        if (collect == null) {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        } else {
            boolean b = Collect.dao.deleteByTidAndAuthorId(tid, user.getStr("id"));
            if (!b) {
                error("取消收藏失败");
            }
            success();
        }
    }
}

package com.shoukeplus.jFinal.module.collect;

import com.jfinal.aop.Before;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Collect;
import com.shoukeplus.jFinal.common.model.Topic;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import com.shoukeplus.jFinal.interceptor.ClientInterceptor;

import java.util.Date;


@ControllerBind(controllerKey = "/api/collect")
@Before(ClientInterceptor.class)
public class CollectClientController extends BaseController {

    public void index() {
        String tid = getPara("tid");
        if (StrUtil.isBlank(tid)) {
            error("话题id不能为空");
        } else if (Topic.dao.findById(tid) == null) {
            error("无效话题");
        } else {
            String token = getPara("token");
            //根据token获取用户信息
            User user = getUser(token);
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
    }

    public void delete() {
        String tid = getPara("tid");
        if (StrUtil.isBlank(tid)) {
            error("话题id不能为空");
        } else if (Topic.dao.findById(tid) == null) {
            error("无效话题");
        } else {
            String token = getPara("token");
            User user = getUser(token);
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
}

package com.shoukeplus.jFinal.module.mission;

import com.jfinal.aop.Before;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Mission;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.DateUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import com.shoukeplus.jFinal.interceptor.UserInterceptor;

import java.util.Date;
import java.util.Random;

@ControllerBind(controllerKey = "/mission", viewPath = "/WEB-INF/pages")
public class MissionController extends BaseController {

    // 每日登录后，点击领取积分，可用于发帖，回复等
    @Before(UserInterceptor.class)
    public void daily() {
        User sessionUser = getSessionAttr(AppConstants.USER_SESSION);
        User user = User.dao.findById(sessionUser.get("id"));
        // 查询今天是否获取过奖励
        Mission mission = Mission.dao.findByInTime(user.getStr("id"),
                DateUtil.formatDate(new Date()) + " 00:00:00",
                DateUtil.formatDate(new Date()) + " 23:59:59");
        if (mission == null) {
            //查询最后一次签到记录
            Mission lastMission = Mission.dao.findLastByAuthorId(user.getStr("id"));
            Integer day = 1;
            if (lastMission != null) {
                String lastMissionInTimeStr = DateUtil.formatDate(lastMission.getDate("in_time"));
                String beforeDateStr = DateUtil.formatDate(DateUtil.getDateBefore(new Date(), 1));
                if (lastMissionInTimeStr != null && lastMissionInTimeStr.equalsIgnoreCase(beforeDateStr)) {
                    day = lastMission.getInt("day") + 1;
                }
            }
            Random random = new Random();
            int score = random.nextInt(10) + 1;// 随机积分，1-10分
            user.set("score", user.getInt("score") + score)
                    .set("mission", DateUtil.formatDate(new Date())).update();
            getModel(Mission.class)
                    .set("score", score)
                    .set("author_id", user.get("id"))
                    .set("day", day)
                    .set("in_time", new Date()).save();
            setAttr("score", score);
            setAttr("day", day);
        } else {
            setAttr("msg", "msg");
        }
        setSessionAttr(AppConstants.USER_SESSION, user);
        render("front/mission/result.ftl");
    }

    public void top10() {
        setAttr("missionList", Mission.dao.findTop10());
        render("front/mission/top10.ftl");
    }
}

package com.langmy.jFinal.module.mission;

import com.jfinal.aop.Before;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Mission;
import com.langmy.jFinal.common.model.User;
import com.langmy.jFinal.common.utils.DateUtil;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import com.langmy.jFinal.interceptor.ClientInterceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@ControllerBind(controllerKey = "/api/mission")
@Before(ClientInterceptor.class)
public class MissionClientController extends BaseController {

    // 每日登录后，点击领取积分
    public void daily() {
        String token = getPara("token");
        User user = getUser(token);
        Map<String, Object> map = new HashMap<String, Object>();
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
            user.set("score", user.getInt("score") + score).set("mission", DateUtil.formatDate(new Date())).update();
            getModel(Mission.class)
                    .set("score", score)
                    .set("author_id", user.get("id"))
                    .set("day", day)
                    .set("in_time", new Date()).save();
            map.put("score", score);
            map.put("day", day);
        } else {
            map.put("msg", "您今天已经领取了奖励，明天在来吧");
        }
        success(map);
    }
}

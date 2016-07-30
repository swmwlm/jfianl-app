package com.shoukeplus.jFinal.module.index;

import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Mission;
import com.shoukeplus.jFinal.common.model.Reply;
import com.shoukeplus.jFinal.common.model.Topic;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
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
	// 不使用自己的登出,使用shiro提供的拦截器logout
	//public void logout() {
	//	try {
	//		SecurityUtils.getSubject().logout();
	//	} catch (Exception e) { //ignore
	//	}
	//	redirect("/adminlogin");
	//}
}
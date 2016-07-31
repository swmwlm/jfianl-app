package com.shoukeplus.jFinal.module.shiro;

import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.AdminUser;
import com.shoukeplus.jFinal.common.model.AdminUserRunas;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * 即允许一个用户假装为另一个用户（如果他们允许）的身份进行访问。
 */
@ControllerBind(controllerKey = "/admin/runas", viewPath = "/WEB-INF/pages/admin/shiro/runas")
public class RunAsController extends BaseController {
	public void index() {
		AdminUser adminUser = getAdminUser();
		List<Integer> fromUserIds = AdminUserRunas.dao.findFromUserIds(adminUser.getId());
		List<AdminUser> fromUsers = AdminUser.dao.findByIds(fromUserIds);
		setAttr("fromUsers", fromUsers);
		List<Integer> toUserIds = AdminUserRunas.dao.findToUserIds(adminUser.getId());
		setAttr("toUserIds", toUserIds);

		List<AdminUser> allUsers = AdminUser.dao.findAll();
		allUsers.remove(adminUser);
		setAttr("allUsers", allUsers);

		Subject subject = SecurityUtils.getSubject();
		setAttr("isRunas", subject.isRunAs());
		if (subject.isRunAs()) {
			String previousUsername =
					(String) subject.getPreviousPrincipals().getPrimaryPrincipal();
			setAttr("previousUsername", previousUsername);
		}

		render("index.ftl");
	}

	public void grant() {
		Integer toUserId = getParaToInt(0);
		AdminUser adminUser = getAdminUser();
		if (adminUser.getId().equals(toUserId)) {
			setAttr("msg", "自己不能切换到自己的身份");
			redirect("/admin/runas");
		}
		AdminUserRunas.dao.grantRunAs(adminUser.getId(), toUserId);

		setAttr("msg", "操作成功");
		redirect("/admin/runas");
	}

	public void revoke() {
		Integer toUserId = getParaToInt(0);
		AdminUser adminUser = getAdminUser();
		AdminUserRunas.dao.revokeRunAs(adminUser.getId(), toUserId);
		setAttr("msg", "操作成功");
		redirect("/admin/runas");
	}

	public void switchTo() {
		Integer switchToUserId = getParaToInt(0);
		AdminUser adminUser = getAdminUser();
		Subject subject = SecurityUtils.getSubject();

		AdminUser switchToUser = AdminUser.dao.findById(switchToUserId);

		if (adminUser.getId().equals(switchToUserId)) {
			setAttr("msg", "自己不能切换到自己的身份");
			redirect("/admin/runas");
		}

		if (switchToUser == null || !AdminUserRunas.dao.exists(switchToUserId, adminUser.getId())) {
			setAttr("msg", "对方没有授予您身份，不能切换");
			redirect("/admin/runas");
		}

		subject.runAs(new SimplePrincipalCollection(switchToUser.getUsername(), ""));
		setAttr("msg", "操作成功");
		redirect("/admin/runas");
	}

	public void switchBack() {

		Subject subject = SecurityUtils.getSubject();
		if (subject.isRunAs()) {
			subject.releaseRunAs();
		}
		setAttr("msg", "操作成功");
		redirect("/admin/runas");
	}
}

package com.shoukeplus.jFinal.module.shiro;

import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.util.Collection;

@ControllerBind(controllerKey = "/admin/session", viewPath = "/WEB-INF/pages/admin/shiro/session")
public class SessionController extends BaseController {
	private SessionDAO sessionDAO=getSessionDao();
	public void index() {
		Collection<Session> sessions =  sessionDAO.getActiveSessions();

		PrincipalCollection principalCollection;
		String name;
		for(Session sess:sessions){
			sess.setAttribute("isForceLogout",sess.getAttribute(AppConstants.SESSION_FORCE_LOGOUT_KEY)!=null);
			principalCollection = (PrincipalCollection)sess.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(principalCollection!=null){
				name= (String) principalCollection.getPrimaryPrincipal();
				sess.setAttribute("name",name);
			}
			else {
				name="";
				sess.setAttribute("name", name);
			}
		}
		setAttr("sessions", sessions);
		setAttr("sessionCount", sessions.size());

		render("index.ftl");
	}
	public void forceLogout() {
		String sessionId=getPara("id");
		try {
			Session session = sessionDAO.readSession(sessionId);
			if(session != null) {
				session.setAttribute(AppConstants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
			}
		} catch (Exception e) {/*ignore*/}
		setAttr("msg", "强制退出成功！");
		redirect("/admin/session");
	}
}

package com.langmy.jFinal.module.index;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.langmy.jFinal.common.AppConstants;
import com.langmy.jFinal.common.model.User;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ControllerBind(controllerKey = "/", viewPath = "/")
public class IndexController extends Controller{
	
	public static Logger LOG  = LoggerFactory.getLogger(IndexController.class);
	
	public void index(){
		List<User> users= User.dao.find("select * from sec_user");
		setAttr("users", users);
		render("front/index.html");
	}


	/**
	 * 加入缓存;
	 * @Before(CacheInterceptor.class)
	 * @CacheName("userList")
	 * 清除缓存:
	 * @Before(EvictInterceptor.class)
	 * @CacheName("userList")
	 */
	@Before(CacheInterceptor.class)
	@CacheName("userList")
	public void testDB() {
		List<User> users = User.dao.find("select * from user");
		if(LOG.isDebugEnabled()){
			LOG.debug(users.toString());
		}
		setAttr("user", users);
		renderFreeMarker("/test.html");
	}
	
	public void covertJson(){
		renderJson(new User().set("user_acc", "acc"));
	}


	/**
	 * 后台管理登录
	 * 默认账号admin
	 * 默认密码123456
	 * 对应表 sec_user
	 */
	public void adminLogin() {
		String method = getRequest().getMethod();
		if (method.equalsIgnoreCase(AppConstants.GET)) {
			render("front/adminLogin.html");
		} else if (method.equalsIgnoreCase(AppConstants.POST)) {
			String username = getPara("username");
			String password = getPara("password");
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
			try {
				subject.login(usernamePasswordToken);
				setSessionAttr(AppConstants.SESSION_ADMIN_USERNAME, username);
				redirect("/admin/index");
			} catch (AuthenticationException e) {
				e.printStackTrace();
				setAttr("error", "用户名或密码错误");
				render("front/adminLogin.html");
			}
		}
	}

	public void login() {
		renderText("asdfasdf");
	}
}

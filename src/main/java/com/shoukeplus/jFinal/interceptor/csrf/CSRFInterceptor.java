package com.shoukeplus.jFinal.interceptor.csrf;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 1.初衷,本打算使用REFERER进行判断,但是,这种方式不友好;比如,当用户直接回车访问(手工访问时),referer为null,无法判断是否是csrf攻击;
 * 2.改用,session发放token;只对post请求进行CSRF拦截判断,因为如果get也判断,会导致直接访问url时,转向403页面,不友好;
 * 3.在做增删改操作时,尽量post请求,来过滤CSRF攻击;
 * 		3.1,当某些action不想进行CSRF拦截时,可以加@Clear(CSRFInterceptor.class)
 * 		3.2,在需要post提交的地方,引入common.ftl和csrf.js两个文件
 */
public class CSRFInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		BaseController controller = (BaseController) ai.getController();
		HttpServletRequest request = controller.getRequest();

		HttpSession session = request.getSession();
		String sToken = (String)session.getAttribute(AppConstants.CSRF_TOKEN);

		if(sToken == null){
			sToken = CSRFTokenManager.generateToken(request);
			session.setAttribute(AppConstants.CSRF_TOKEN,sToken);
			ai.invoke();
			return;
		} else{
			String method = request.getMethod();
			if (method.equalsIgnoreCase(AppConstants.POST)&&!CSRFTokenManager.verifyToken(request)) {
				controller.renderError(403);
			}
			ai.invoke();
			return;
		}
	}

}
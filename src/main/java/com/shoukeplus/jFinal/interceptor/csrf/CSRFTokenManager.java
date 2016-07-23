package com.shoukeplus.jFinal.interceptor.csrf;

import com.shoukeplus.jFinal.common.AppConstants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class CSRFTokenManager {
	public static boolean verifyToken(HttpServletRequest request){
		synchronized (request){
			String token=request.getParameter(AppConstants.CSRF_TOKEN);
			if(token==null){
				token=request.getHeader(AppConstants.CSRF_TOKEN);
			}
			if(token==null){
				return false;
			}
			HttpSession session=request.getSession();
			String tokenInSession= (String) session.getAttribute(AppConstants.CSRF_TOKEN);
			return StringUtils.equals(token,tokenInSession);
		}
	}
	public static String generateToken(HttpServletRequest request){
		synchronized (request){
			HttpSession session=request.getSession();
			String token= (String) session.getAttribute(AppConstants.CSRF_TOKEN);
			if(token==null){
				token= UUID.randomUUID().toString();
				session.setAttribute(AppConstants.CSRF_TOKEN,token);
			}
			return token;
		}
	}

}

package com.bitc.cjh.inerceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
		if ((String)session.getAttribute("userEmail") == null) {
			
			response.sendRedirect("/main");
			return false;
		}
		else {

			session.setMaxInactiveInterval(1800);
			return true;
		}
	}
}

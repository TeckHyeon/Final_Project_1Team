package com.bitc.cjh.interceptor;

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
			System.out.println("====== interceptor ======");
			System.out.println("비 로그인 상태 : ");
			System.out.println((String)session.getAttribute("userEmail"));
			
			response.sendRedirect("/main");
			return false;
		}
		else {
			System.out.println("====== interceptor ======");
			System.out.println("로그인 상태 : ");
			System.out.println((String)session.getAttribute("userEmail"));
			
			session.setMaxInactiveInterval(1800);
			return true;
		}
	}
}

package com.springboot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("\n-------- Login.preHandle --- ");
		System.out.println("Request URL: " + request.getRequestURL());
		
		/*HttpSession session = request.getSession();
		
		if(session.getAttribute("isLoggedIn") != null) {
			System.out.println("I'm still logged in!");
			if(request.getRequestURL().indexOf("login") > 0) {
				response.sendRedirect(request.getContextPath() + "/admin/index");
			}
		} else if(request.getRequestURL().indexOf("login") > 0){
			System.out.println("login");
		} else {
			System.out.println("You are not logged in!");
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}*/
		
		return true;
	}
}

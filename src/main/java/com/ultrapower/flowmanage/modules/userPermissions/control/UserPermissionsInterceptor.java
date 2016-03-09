package com.ultrapower.flowmanage.modules.userPermissions.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserPermissionsInterceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		System.out.println("==============执行顺序: 1、preHandle================");
		String url = request.getRequestURL().toString();
		String reqUrl = request.getRequestURI().replace(request.getContextPath(), "");
		System.out.println("==================请求路径url："+url);
		System.out.println("==================请求路径reqUrl："+reqUrl);
		if(url == null){
			request.getRequestDispatcher("/erre.jsp").forward(request, response);
			return false;
		}
		return true;
	}
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		
		System.out.println("==============执行顺序: 2、postHandle================");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
					throws Exception {
		System.out.println("==============执行顺序: 3、afterCompletion================");
		
	}

}

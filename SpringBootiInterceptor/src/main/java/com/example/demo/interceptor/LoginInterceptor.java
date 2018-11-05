package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor  implements HandlerInterceptor {

	@Override
	public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		long startTime = System.currentTimeMillis();
		 System.out.println("\n-------- LogInterception.preHandle --- ");
		System.out.println("Request URL :" +request.getRequestURL());
		System.out.println("startTime" +startTime);
		request.setAttribute("startTime", startTime);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
		
		 System.out.println("\n-------- LogInterception.postHandle --- ");
		System.out.println("Request URL :" +request.getRequestURL());
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		
		  System.out.println("\n-------- LogInterception.afterCompletion --- ");
		
		System.out.println("Request URL :" +request.getRequestURL());
		long startTime = (long)request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		System.out.println("endTime" +endTime);
		System.out.println("Time taken : " +(startTime-endTime));
		
	}
}

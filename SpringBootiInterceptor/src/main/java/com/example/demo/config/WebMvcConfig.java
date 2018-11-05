package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.AdminInterceptor;
import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.interceptor.OldLoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Override
	public  void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new LoginInterceptor());
		
		registry.addInterceptor(new OldLoginInterceptor()).addPathPatterns("/admin/oldLogin");
		
		registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/*").excludePathPatterns("/admin/oldLogin");
	}

}

package com.example.demo.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	
	@Bean(name ="localeResolver")
	public CookieLocaleResolver getLocaleResolver() {
		  CookieLocaleResolver resolver= new CookieLocaleResolver();
	        resolver.setCookieDomain("myAppLocaleCookie");
	        // 60 minutes 
	        resolver.setCookieMaxAge(60*60); 
	        return resolver;
	}
	
	@Bean(name ="messageSource")
	public MessageSource getMessageResource() {
		
		ReloadableResourceBundleMessageSource message = new ReloadableResourceBundleMessageSource();
		message.setBasename("classpath:i18n/messages");
		message.setDefaultEncoding("UTF-8");
		return message;
		
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		
		localeInterceptor.setParamName("lang");
		registry.addInterceptor(localeInterceptor).addPathPatterns("/*");
		
	}

}

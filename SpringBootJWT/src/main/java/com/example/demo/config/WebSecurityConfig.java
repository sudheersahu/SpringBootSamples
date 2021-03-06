package com.example.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.JWTAuthenticationFilter;
import com.example.demo.filter.JWTLoginFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers(HttpMethod.POST, "/login").permitAll() //
        .antMatchers(HttpMethod.GET, "/login").permitAll() // For Test on Browser
        // Need authentication.
        .anyRequest().authenticated()
        //
        .and()
        //
        // Add Filter 1 - JWTLoginFilter
        //
        .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
        //
        // Add Filter 2 - JWTAuthenticationFilter
        //
        .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		return encoder;
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		 String password = "123";
		 
	        String encrytedPassword = this.passwordEncoder().encode(password);
	        System.out.println("Encoded password of 123=" + encrytedPassword);
	        
	        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> mngConfig = auth.inMemoryAuthentication();
	        
	        UserDetails user1 = User.withUsername("tom").password(encrytedPassword).roles("USER").build();
	        UserDetails user2 = User.withUsername("jerry").password(encrytedPassword).roles("USER").build();
	        mngConfig.withUser(user1);
	        mngConfig.withUser(user2);
		
	}

}

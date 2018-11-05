package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPasswordUtils {
	
	public static String encrytePassword(String password) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
		
	}
	
	public static void main(String[] args) {
		
		String password = "123";
		
		System.out.println("Encryte Password " +encrytePassword(password));
	}

}

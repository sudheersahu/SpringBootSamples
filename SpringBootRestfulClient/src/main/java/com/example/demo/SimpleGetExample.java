package com.example.demo;

import org.springframework.web.client.RestTemplate;

public class SimpleGetExample {
	
	static final String URL_EMPLOYEES = "http://localhost:8080/employees";
	 
    static final String URL_EMPLOYEES_XML = "http://localhost:8080/employees.xml";
    static final String URL_EMPLOYEES_JSON = "http://localhost:8080/employees.json";
    
    public static void main(String[] args) {
    	RestTemplate template = new RestTemplate();
    	 String result = template.getForObject(URL_EMPLOYEES, String.class);
    	 System.out.println(result);
    }

}

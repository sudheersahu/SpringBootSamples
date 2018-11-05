package com.example.demo;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GetWithHeaderExample {

	 public static final String USER_NAME = "tom";
	    public static final String PASSWORD = "123";
	    
	static final String URL_EMPLOYEES = "http://localhost:8080/employees";
	
	public static void main(String[] args) {
		
		HttpHeaders headers = new HttpHeaders();
		
		 String auth = USER_NAME + ":" + PASSWORD;
	        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
	        String authHeader = "Basic " + new String(encodedAuth);
	        headers.set("Authorization", authHeader);
	        
		headers.setAccept(Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON}));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("my_other_key", "my_other_value");
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		RestTemplate template =new RestTemplate();
		ResponseEntity<String> response = template.exchange(URL_EMPLOYEES, HttpMethod.GET, entity, String.class);
		
		String result = response.getBody();
		
		System.out.println(result);
	}
}

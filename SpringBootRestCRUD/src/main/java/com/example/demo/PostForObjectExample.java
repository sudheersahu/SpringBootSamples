package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Employee;

public class PostForObjectExample {

	static final String URL_CREATE_EMPLOYEE = "http://localhost:8080/employee";

	public static void main(String[] args) {

		String empNo = "E11";

		Employee emp = new Employee(empNo, "Tom", "Cleck");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
		headers.setContentType(MediaType.APPLICATION_XML);

		HttpEntity<Employee> request = new HttpEntity<>(emp, headers);

		RestTemplate template = new RestTemplate();

		Employee e = template.postForObject(URL_CREATE_EMPLOYEE, request, Employee.class);

		if (e != null && e.getEmpNo() != null) {

			System.out.println("Employee created: " + e.getEmpNo());
		} else {
			System.out.println("Something error!");
		}

	}

}

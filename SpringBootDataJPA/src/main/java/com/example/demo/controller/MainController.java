package com.example.demo.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Controller
public class MainController {
	
	@Autowired
	private EmployeeRepository repository;
	
	  private static final String[] NAMES = new String[] { "Tom", "Jerry", "Donald" };
	
	 @ResponseBody
	    @RequestMapping("/")
	    public String home() {
	        String html = "";
	        html += "<ul>";
	        html += " <li><a href='/testInsert'>Test Insert</a></li>";
	        html += " <li><a href='/showAllEmployee'>Show All Employee</a></li>";
	        html += " <li><a href='/showFullNameLikeTom'>Show All 'Tom'</a></li>";
	        html += " <li><a href='/deleteAllEmployee'>Delete All Employee</a></li>";
	        html += "</ul>";
	        return html;
	    }
	
	 @ResponseBody
	 @RequestMapping("/testInsert")
	 public String testInsert() {
		 Long empIdMax = this.repository.getMaxId();
		 
	        Employee employee = new Employee();
	 
	        int random = new Random().nextInt(3);
	 
	        long id = empIdMax + 1;
	        String fullName = NAMES[random] + " " + id;
	 
	        employee.setId(id);
	        employee.setEmpNo("E" + id);
	        employee.setFullName(fullName);
	        employee.setHireDate(new Date());
	        this.repository.save(employee);
	 
	        return "Inserted: " + employee;
     }
	 
	@ResponseBody
	@RequestMapping("/showAllEmployee")
	public String showAllEmployee() {
		
	 Iterable<Employee> employees =	this.repository.findAll();
	 
	 String html = "";
     for (Employee emp : employees) {
         html += emp + "<br>";
     }
		return html;
		
	}

	@ResponseBody
	@RequestMapping("/showFullNameLikeTom")
	public String showFullNameLikeTom() {
		 Iterable<Employee> employees =	this.repository.findByFullNameLike("Tom");
		 
		 String html = "";
	     for (Employee emp : employees) {
	         html += emp + "<br>";
	     }
			return html;
		
	}
	
	@ResponseBody
	@RequestMapping("/deleteAllEmployee")
	public String deleteAllEmployee() {
		
		this.repository.deleteAll();
		return "Deleted!";
		
		
	}
}

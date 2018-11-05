package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Person;

@Controller
public class MainController {
	
	static List<Person> persons = new ArrayList<Person>();
	
	static {
		
		persons.add(new Person("Sudheer","Sahu"));
		persons.add(new Person("Sfdjas","Sahu"));
		
	}
	

	@RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
	public String index(Model model) {
		
		String message = "Hello Spring Boot + JSP";
		
		model.addAttribute("message", message);
		
		return "index";
	}
	
	@RequestMapping(value = {"/personList"}, method = RequestMethod.GET)
	public String viewPersonList(Model model) {
		
		model.addAttribute("persons", persons);
		
		return "personList";
	}
}

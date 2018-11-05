package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value = {"/", "login1"}, method = RequestMethod.GET)
public String staticResource(Model model) {
	return "login1";
}
	
	
}

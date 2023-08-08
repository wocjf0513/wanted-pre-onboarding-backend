package com.wantedpreonboardingbackend.posts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.BeanNameViewResolver;

@Controller
public class HomeController {

	@GetMapping("/")
	public String homePage() {
		return "index";
	}
}
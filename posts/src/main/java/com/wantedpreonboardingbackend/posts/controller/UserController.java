package com.wantedpreonboardingbackend.posts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wantedpreonboardingbackend.posts.dto.UserDTO;
import com.wantedpreonboardingbackend.posts.entity.User;
import com.wantedpreonboardingbackend.posts.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/test/addUser")
	@ResponseBody
	public String testAddUser() {
		User newUser=User.builder().email("wocjf0513").password(bCryptPasswordEncoder.encode("1234")).build();
		userRepository.save(newUser);
		
		return "hello";
	}

	//회원가입
	
	//로그인
	
}

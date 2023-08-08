package com.wantedpreonboardingbackend.posts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wantedpreonboardingbackend.posts.Authorities;
import com.wantedpreonboardingbackend.posts.config.security.jwt.JwtToken;
import com.wantedpreonboardingbackend.posts.dto.UserDTO;
import com.wantedpreonboardingbackend.posts.entity.User;
import com.wantedpreonboardingbackend.posts.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	private final UserService userService;
	
	
	@PostMapping("/auth")
	public ResponseEntity<JwtToken> login(@RequestBody UserDTO user){
		if(userService.isExsistedUser(user)) {
			JwtToken token=userService.generateToken(user);
			return ResponseEntity.ofNullable(token);
		}
		else {
			return ResponseEntity.ofNullable(null);
		}
		
	}
	
	@PutMapping("/")
	public ResponseEntity<UserDTO> join(@RequestBody UserDTO user){
		/*
		 * 이메일 조건: @ 포함
			비밀번호 조건: 8자 이상
			비밀번호는 반드시 암호화하여 저장해 주세요.
		 */
		user.setRole(Authorities.USER);
		if(userService.vertifyAccount(user)) {
			User savedUser=userService.saveAccount(user);
			
			if(savedUser==null)
				return ResponseEntity.ofNullable(null);
			else
				return ResponseEntity.ok(null);
			
		}
		else {
			return ResponseEntity.ofNullable(null);
		}
		
	}
	
	
}

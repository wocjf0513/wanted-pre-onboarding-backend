package com.wantedpreonboardingbackend.posts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wantedpreonboardingbackend.posts.auth.Authorities;
import com.wantedpreonboardingbackend.posts.config.security.jwt.JwtProvider;
import com.wantedpreonboardingbackend.posts.config.security.jwt.JwtToken;
import com.wantedpreonboardingbackend.posts.dto.UserDTO;
import com.wantedpreonboardingbackend.posts.entity.User;
import com.wantedpreonboardingbackend.posts.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtProvider jwtProvider;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtProvider jwtProvider){
		this.userRepository=userRepository;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
		this.jwtProvider=jwtProvider;
	}
	
	public boolean isExsistedUser(UserDTO user) {
		User retrivedUser=userRepository.findByEmail(user.getEmail()).orElseThrow();
		if(retrivedUser!=null && bCryptPasswordEncoder.matches(user.getPassword(), retrivedUser.getPassword())) {
			user.setRole(retrivedUser.getRole());
			return true;
		}
		return false;
		
	}
	
	public JwtToken generateToken(UserDTO user) {
		
		List<SimpleGrantedAuthority> list=Arrays.stream(user.getRole().getValue().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),list);
		//Authentication authentication=authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		JwtToken token=jwtProvider.createToken(authenticationToken);
		
		return token;
	}
	
	public boolean vertifyAccount(UserDTO user) {
		
		if(user.getEmail().contains("@") && user.getPassword().length()>=8) {
			User retrivedUser=userRepository.findByEmail(user.getEmail()).orElse(null);
			if(retrivedUser==null) {
				return true;
			}
			else {
				return false;
			}
			
		}
		else {
			return false;
		}
	}
	
	public UserDTO saveAccount(UserDTO user) {
		User newUser=User.builder().email(user.getEmail())
				.password(bCryptPasswordEncoder.encode(user.getPassword()))
				.role(Authorities.USER).build();
		return new UserDTO(userRepository.save(newUser));	
	}

	
	
}

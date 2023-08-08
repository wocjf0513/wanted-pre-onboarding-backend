package com.wantedpreonboardingbackend.posts.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wantedpreonboardingbackend.posts.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserDetailServiceImpl implements UserDetailsService{

	private final UserRepository userRepository;
	
	public UserDetailServiceImpl(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("cusotm load 실행 중 "+username);
		return userRepository.findByEmail(username).map(user-> new UserDetailsVO(user)).orElseThrow(()-> new UserNotFoundException(username));
	}
	

}

package com.wantedpreonboardingbackend.posts.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	private BCryptPasswordEncoder passwordEncoder;
	
	public CustomAuthenticationProvider(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.passwordEncoder=bCryptPasswordEncoder;
	}
	

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken token=(UsernamePasswordAuthenticationToken) authentication;
		String userEmail=token.getName();
		String userPw=(String)token.getCredentials();
		log.info("provider 진행중");
		UserDetailsVO userDetailsVO=(UserDetailsVO) userDetailServiceImpl.loadUserByUsername(userEmail);
		
		if(!passwordEncoder.matches(userPw,  userDetailsVO.getPassword())) {
			throw new BadCredentialsException(userEmail+" Invalid password");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetailsVO, userPw, null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	

}

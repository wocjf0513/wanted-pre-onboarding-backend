package com.wantedpreonboardingbackend.posts.config.security;

import javax.naming.AuthenticationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	public Authentication authenticate(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		log.info(request.getParameter("username")+" "+request.getParameter("password"));
		UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(request.getParameter("username")
				, request.getParameter("password"));
		setDetails(request, token);
		return this.getAuthenticationManager().authenticate(token);
	}
	

}

//filter 에서 username passwrod token 생성 후, manager가 

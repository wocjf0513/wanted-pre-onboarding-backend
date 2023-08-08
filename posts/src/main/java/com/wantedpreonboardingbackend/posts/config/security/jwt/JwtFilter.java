package com.wantedpreonboardingbackend.posts.config.security.jwt;

import java.io.IOException;

import javax.naming.AuthenticationException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;


//genericBeanFilter 등록시, 두 번 등록 돼 두번 호출된다.
//SpringBoot는 filter 객체는 다 filter chain에 등록하기 때문이다.
@Log4j2
public class JwtFilter extends OncePerRequestFilter{
	
	private final JwtProvider jwtProvider;
	
	public JwtFilter(JwtProvider jwtProvider) {
		this.jwtProvider=jwtProvider;
	}


	private String resolveToken(HttpServletRequest request) {
		String token=request.getHeader("Authorization");
		
		if(!StringUtils.isEmpty(token) && token.startsWith("Bearer")) {
			return token.substring(7);
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String token=resolveToken(request);
		if(!StringUtils.isEmpty(token) && jwtProvider.validateToken(token)) {
			Authentication authentication= jwtProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
		
	}


}

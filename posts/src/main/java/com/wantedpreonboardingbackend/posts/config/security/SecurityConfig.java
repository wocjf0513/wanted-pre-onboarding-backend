package com.wantedpreonboardingbackend.posts.config.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;



@Log4j2
@EnableWebSecurity //스프링 핋터 체인 필터 등록
@Configuration
public class SecurityConfig{

	
	 @Bean
	  public AuthenticationManager authenticationManagerBean() {
	    List<AuthenticationProvider> authenticationProviderList = new ArrayList();
	    authenticationProviderList.add(customAuthenticationProvider());
	    ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
	    return authenticationManager;
	  }
	 
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CustomAuthenticationFilter customAuthenticationFilter() throws Exception{
		CustomAuthenticationFilter customAuthenticationFilter=new CustomAuthenticationFilter(authenticationManagerBean());
		customAuthenticationFilter.setFilterProcessesUrl("/login/process");
		customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
		return customAuthenticationFilter;
	}
	
	
	
	@Bean
	public CustomLoginSuccessHandler customLoginSuccessHandler(){
		return new CustomLoginSuccessHandler();
	}

	@Bean
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider(bCryptPasswordEncoder());
	}
	
	
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		   http
           .authorizeHttpRequests((authz) -> authz
        		   .anyRequest().authenticated()
        		   
           )
           .formLogin().permitAll()
           .and()
           .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build(); 
	}
	


}

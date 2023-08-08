package com.wantedpreonboardingbackend.posts.config.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.wantedpreonboardingbackend.posts.config.security.jwt.JwtFilter;
import com.wantedpreonboardingbackend.posts.config.security.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;



@Log4j2
@EnableWebSecurity //스프링 핋터 체인 필터 등록
@Configuration
public class SecurityConfig{
	
	private final JwtProvider jwtProvider;
	public SecurityConfig( JwtProvider jwtProvider) {
		this.jwtProvider=jwtProvider;
	}
	
//	 @Bean
//	  public AuthenticationManager authenticationManagerBean() {
//	    List<AuthenticationProvider> authenticationProviderList = new ArrayList();
//	    authenticationProviderList.add(customAuthenticationProvider());
//	    ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//	    return authenticationManager;
//	  }
	 
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public CustomAuthenticationFilter customAuthenticationFilter() throws Exception{
//		CustomAuthenticationFilter customAuthenticationFilter=new CustomAuthenticationFilter(authenticationManagerBean());
//		customAuthenticationFilter.setFilterProcessesUrl("/login/process");
//		customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
//		return customAuthenticationFilter;
//	}
	
	
	
//	@Bean
//	public CustomLoginSuccessHandler customLoginSuccessHandler(){
//		return new CustomLoginSuccessHandler();
//	}
//
//	@Bean
//	public CustomAuthenticationProvider customAuthenticationProvider() {
//		return new CustomAuthenticationProvider(bCryptPasswordEncoder());
//	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		
	 
			http
			 .httpBasic().disable()
	         .formLogin().disable()
	         .csrf().disable()
	         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
			   http
			   .authorizeHttpRequests()
			   .requestMatchers(new AntPathRequestMatcher("/auth/**")).authenticated()
			   .anyRequest().permitAll();
		
		   http
		   .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
//			http
//			  .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
				  
		   

	
          
           
		
		return http.build(); 
	}
	


}

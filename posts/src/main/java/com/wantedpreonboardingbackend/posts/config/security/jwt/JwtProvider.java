package com.wantedpreonboardingbackend.posts.config.security.jwt;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.wantedpreonboardingbackend.posts.Authorities;
import com.wantedpreonboardingbackend.posts.config.security.UserDetailsVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider  {
	
	
	private final byte[] secretStrKey;
	
	
	public JwtProvider(@Value("${jwt.secret}") String secretKey){
		secretStrKey = Base64.getEncoder().encode(secretKey.getBytes());
	}
	
	public JwtToken createToken(Authentication authentication) {
		
		String authorities=authentication.getAuthorities()
				.stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		String acessToken=Jwts.builder()
				.setSubject(authentication.getName())
				.claim("auth",authorities)
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
				.signWith(SignatureAlgorithm.HS256, secretStrKey)
				.compact();
		
		String refreshToken=Jwts.builder()
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24*3))
				.signWith(SignatureAlgorithm.HS256, secretStrKey)
				.compact();
		
		return JwtToken.builder()
				.grantType("Bearer")
				.accessToken(acessToken)
				.refreshToken(refreshToken)
				.build();
	}
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(secretStrKey).build().parseClaimsJws(token);
			return true;
		}catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token",e);
		}catch(ExpiredJwtException e) {
			log.info("Expired JWT Token",e);
		}catch(UnsupportedJwtException e) {
			log.info("Unsupported JWT Token",e);
		}catch(IllegalArgumentException e) {
			log.info("JWT claims string is empty",e);
		}
		return false;
	}
	
	public Authentication getAuthentication(String accessToken) {
		Claims claims=parseClaims(accessToken);
		
		if(claims.get("auth")==null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		Collection<? extends GrantedAuthority> authorities=
				Arrays.stream(claims.get("auth").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		UserDetails principal=new User(claims.getSubject(),"",authorities);
		return new UsernamePasswordAuthenticationToken(principal, "",authorities);
	}
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(secretStrKey).build().parseClaimsJws(accessToken).getBody();
		}catch(ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}


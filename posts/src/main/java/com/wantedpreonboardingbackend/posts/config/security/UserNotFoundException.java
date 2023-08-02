package com.wantedpreonboardingbackend.posts.config.security;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String username){
		super(username + "NotFoundException");
	}
}

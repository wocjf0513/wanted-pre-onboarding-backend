package com.wantedpreonboardingbackend.posts.auth;

public enum Authorities{
	USER("USER");
	
	private final String value;
	
	Authorities(String value){
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
}

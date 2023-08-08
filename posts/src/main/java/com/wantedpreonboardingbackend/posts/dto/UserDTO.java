package com.wantedpreonboardingbackend.posts.dto;

import java.util.ArrayList;
import java.util.List;

import com.wantedpreonboardingbackend.posts.Authorities;
import com.wantedpreonboardingbackend.posts.entity.Post;

import lombok.Data;

@Data
public class UserDTO {
	
	private long userId;
	
	private String email;
	
	private String password;
	
	private Authorities role;

	private List<Post> posts=new ArrayList<Post>();
	
	public UserDTO(String email, String password){
		this.email=email;
		this.password=password;
	}
	
	public void addPost(Post newPost) {
		posts.add(newPost);
	}
	
	
	

}

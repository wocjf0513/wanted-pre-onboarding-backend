package com.wantedpreonboardingbackend.posts.dto;

import java.util.ArrayList;
import java.util.List;

import com.wantedpreonboardingbackend.posts.Authorities;
import com.wantedpreonboardingbackend.posts.entity.Post;
import com.wantedpreonboardingbackend.posts.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	
	private long userId;
	
	private String password;
	
	private String email;
	
	private Authorities role;

	private List<PostDTO> posts=new ArrayList<PostDTO>();
	
	public UserDTO(User user){
		this.email=user.getEmail();
		this.role=user.getRole();
		user.getPosts().stream().forEach(x->this.posts.add(new PostDTO(x)));
		//password를 담지 않음.
	}
	
	public void addPost(PostDTO newPost) {
		posts.add(newPost);
	}
	
	
	

}

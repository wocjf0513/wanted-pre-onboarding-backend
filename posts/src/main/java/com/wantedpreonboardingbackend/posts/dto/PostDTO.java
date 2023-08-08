package com.wantedpreonboardingbackend.posts.dto;

import com.wantedpreonboardingbackend.posts.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

	private long postId;
	
	private String content;
	
	private User writerId;
	
	PostDTO(String content, User writerId){
		this.content=content;
		this.writerId=writerId;
	}
	
}

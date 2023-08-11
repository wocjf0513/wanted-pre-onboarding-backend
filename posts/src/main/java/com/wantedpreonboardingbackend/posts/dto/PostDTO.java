package com.wantedpreonboardingbackend.posts.dto;

import com.wantedpreonboardingbackend.posts.entity.Post;
import com.wantedpreonboardingbackend.posts.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

	//post 작성자와 postId와 post를 쓴 사람 이메일 
	private long postId;
	
	private String content;
	
	private String writer;
	
	public PostDTO(Post post){
		this.postId=post.getPostId();
		this.content=post.getContent();
		this.writer=post.getWriterId().getEmail();
	}
	
}

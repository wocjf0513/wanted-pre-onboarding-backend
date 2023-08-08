package com.wantedpreonboardingbackend.posts.entity;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
public class Post {
	@Id
	@GeneratedValue (strategy =GenerationType.AUTO)
	private long postId;
	
	private String content;
	
	@ManyToOne(targetEntity = User.class, fetch=FetchType.LAZY)
	private User writerId;
	
	@Builder
	Post(String content, User writerId){
		this.content=content;
		this.writerId=writerId;
	}
	
}

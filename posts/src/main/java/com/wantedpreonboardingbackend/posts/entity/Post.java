package com.wantedpreonboardingbackend.posts.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
public class Post {
	@Id
	@GeneratedValue (strategy =GenerationType.AUTO)
	private long postId;
	
	private String content;
	
	@JsonIgnore
	@ManyToOne(targetEntity = User.class, fetch=FetchType.LAZY)
	private User writerId;

	Post(long postId, String content, User writerId){
		this.content=content;
		this.writerId=writerId;
		this.postId=postId;
	}
	
}

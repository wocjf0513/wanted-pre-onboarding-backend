package com.wantedpreonboardingbackend.posts.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.wantedpreonboardingbackend.posts.Authorities;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	
	@Nullable
	@Column
	private String email;
	
	@Nullable
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Authorities role;

	@OneToMany(mappedBy = "writerId", cascade = {CascadeType.ALL})
	private List<Post> posts=new ArrayList<Post>();
	
	@Builder
	User(String email, String password, Authorities role){
		this.email=email;
		this.password=password;
		this.role=role;
	}
	
	public void addPost(Post newPost) {
		posts.add(newPost);
	}
	
	
	

}

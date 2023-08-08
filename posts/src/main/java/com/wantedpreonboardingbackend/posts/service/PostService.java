package com.wantedpreonboardingbackend.posts.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantedpreonboardingbackend.posts.config.security.UserDetailsVO;
import com.wantedpreonboardingbackend.posts.dto.PostDTO;
import com.wantedpreonboardingbackend.posts.entity.Post;
import com.wantedpreonboardingbackend.posts.entity.User;
import com.wantedpreonboardingbackend.posts.repository.PostRepository;
import com.wantedpreonboardingbackend.posts.repository.UserRepository;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PostService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	public PostService(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository=postRepository;
		this.userRepository=userRepository;
	}

	public PostDTO savePost(PostDTO post, String content) {
		UsernamePasswordAuthenticationToken auth=(UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();

		User retrivedUser=userRepository.findByEmail(auth.getName()).orElse(null);
		
		if(retrivedUser==null) {
			log.info("작성자 이메일이 db에 없다.");
			return null;
		}
		else {
		Post newPost=Post.builder().writerId(retrivedUser).content(post.getContent()).build();
		postRepository.save(newPost);
		//post id가 생성됐음.
	
		retrivedUser.getPosts().add(newPost);
		//post id가 생성되기 전임... 어떻게 해야 되지.
		return null;
	}
}

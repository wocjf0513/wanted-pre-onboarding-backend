package com.wantedpreonboardingbackend.posts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.wantedpreonboardingbackend.posts.dto.PostDTO;
import com.wantedpreonboardingbackend.posts.entity.Post;
import com.wantedpreonboardingbackend.posts.entity.User;
import com.wantedpreonboardingbackend.posts.repository.PostRepository;
import com.wantedpreonboardingbackend.posts.repository.UserRepository;

import jakarta.transaction.Transactional;
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

	public PostDTO savePost(PostDTO post) {
		UserDetails user=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User retrivedUser=userRepository.findByEmail(user.getUsername()).orElse(null);
		if(retrivedUser==null) {
			log.info("작성자 이메일이 db에 없다.");
			return null;
		}
		else {
		Post newPost=Post.builder().writerId(retrivedUser).content(post.getContent()).build();
		Post savePost=postRepository.save(newPost);
		//post id가 생성됐음.
	
			if(savePost==null) {
				return null;
			}
			else {
				retrivedUser.getPosts().add(savePost);
				userRepository.save(retrivedUser);
				return new PostDTO(savePost);
			}
		}
	}
	
	public PostDTO getPost(long postId){
		Post retrivedPost=postRepository.findById(postId).orElseThrow();
		
		return new PostDTO(retrivedPost);
	}
	
	public Page<PostDTO> getPosts(Pageable pageable){
		Page<Post> pagesOrigin=postRepository.findAll(pageable);
		StringBuilder sb=new StringBuilder();
		pagesOrigin.stream().forEach(x->sb.append(x.getContent()));
		Page<PostDTO> pages=postRepository.findAll(pageable).map(x->new PostDTO(x));
		return pages;
	}
	
	public boolean isWriter(long postId) {
		UserDetails user=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PostDTO retrivedPostDto=new PostDTO(postRepository.findById(postId).orElseThrow());
		if(user.getUsername().equals(retrivedPostDto.getWriter())){
			return true;
		}
		else {
			return false;
		}
	}
	
	public PostDTO deletePost(long postId) {
		if(isWriter(postId)) {
			postRepository.deleteById(postId);
			return new PostDTO();
		}
		else {
		    return null;
		}
	}
	
	@Transactional
	public PostDTO rewritePost(String content, long postId) {
		if(isWriter(postId)) {
			log.info(content);
			Post retrivedPost=postRepository.getById(postId);
			retrivedPost.setContent(content);
			
			return new PostDTO(retrivedPost);
		}
		else {
			return null;
		}
	}
}


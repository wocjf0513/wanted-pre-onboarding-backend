package com.wantedpreonboardingbackend.posts.controller;

import org.springframework.data.domain.DomainEvents;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wantedpreonboardingbackend.posts.dto.PostDTO;
import com.wantedpreonboardingbackend.posts.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
	
	private final PostService postService;
	
	public PostController(PostService postService){
		this.postService=postService;
	}
	
	//게시글 생성
	@PostMapping("/")
	public ResponseEntity<PostDTO> writePost(@RequestBody PostDTO post){
		//save하는거, 작성자는 securitycontext에서 가져온다.
		postService.savePost(post);
		//security contextholder에 저장이 되나.
		return ResponseEntity.ofNullable(null);
	}
	
	//게시글 목록 조회
	@GetMapping("/")
	public ResponseEntity<PostDTO> viewPosts(){
		//pagication 별 조회
		return ResponseEntity.ofNullable(null);
	}
	//특정 게시글 조회
	@GetMapping("/{postid}")
	public ResponseEntity<PostDTO> viewOnePost(@PathVariable("postid") long postid){
		return ResponseEntity.ofNullable(null);
	}
	
	//특정 게시글 수정
	@PatchMapping("/{postid}")
	public ResponseEntity<PostDTO> rewriteOnePost(@PathVariable("postid") long postid){
		//게시글 작성자만
		return ResponseEntity.ofNullable(null);
	}
	
	//특정 게시글 삭제
	@DeleteMapping("/{postid}")
	public ResponseEntity<PostDTO> deleteOnePost(@PathVariable("postid") long postid){
		//게시글 작성자만
		return ResponseEntity.ofNullable(null);
	}

}

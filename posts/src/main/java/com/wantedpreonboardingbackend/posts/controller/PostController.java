package com.wantedpreonboardingbackend.posts.controller;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wantedpreonboardingbackend.posts.dto.PostDTO;
import com.wantedpreonboardingbackend.posts.service.PostService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/post")
public class PostController {
	
	private final PostService postService;
	
	public PostController(PostService postService){
		this.postService=postService;
	}
	
	//게시글 생성
	@PutMapping("")
	public ResponseEntity<PostDTO> writePost(@RequestBody PostDTO post){
		//save하는거, 작성자는 securitycontext에서 가져온다.
		try {
			return ResponseEntity.ofNullable(postService.savePost(post));
		}
		catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<PostDTO>(HttpStatusCode.valueOf(400));
		}
		
	}
	
	//게시글 목록 조회
	@GetMapping("")
	public ResponseEntity<Page<PostDTO>> viewPosts(@PageableDefault(page=0, size=10) Pageable page){
		try {
			Page<PostDTO> pages=postService.getPosts(page);
			if(pages==null) {
				throw new NoSuchElementException();
			}
			return ResponseEntity.ofNullable(pages);
		}
		catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<Page<PostDTO>>(HttpStatusCode.valueOf(400));
		}
	}
	//특정 게시글 조회
	@GetMapping("/{postid}")
	public ResponseEntity<PostDTO> viewOnePost(@PathVariable("postid") long postId){
		try {
			return ResponseEntity.ofNullable(postService.getPost(postId));
		}
		catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<PostDTO>(HttpStatusCode.valueOf(400));
		}
	}
	
	//특정 게시글 수정
	@PatchMapping("/{postid}")
	public ResponseEntity<PostDTO> rewriteOnePost(@RequestParam String content, @PathVariable("postid") long postId){
		//게시글 작성자만
		try {
			return ResponseEntity.ofNullable(postService.rewritePost(content, postId));
		}
		catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<PostDTO>(HttpStatusCode.valueOf(400));
		}
	}
	
	//특정 게시글 삭제
	@DeleteMapping("/{postid}")
	public ResponseEntity<PostDTO> deleteOnePost(@PathVariable("postid") long postId){
		//게시글 작성자만
		try {
			return ResponseEntity.ofNullable(postService.deletePost(postId));
		}
		catch(Exception e) {
			log.info(e.getMessage());
			return new ResponseEntity<PostDTO>(HttpStatusCode.valueOf(400));
		}
		
	}

}

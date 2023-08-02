package com.wantedpreonboardingbackend.posts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wantedpreonboardingbackend.posts.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}

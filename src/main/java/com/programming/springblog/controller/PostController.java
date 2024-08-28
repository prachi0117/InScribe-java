package com.programming.springblog.controller;

import com.programming.springblog.dto.PostDto;
import com.programming.springblog.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{id}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("id") Integer userId) {
		PostDto createPost = this.postService.createPost(postDto, userId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

    // get by user

	@GetMapping("/user/{id}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("id") Integer userId) {

		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

    // get all posts

	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPost(){
		
		List<PostDto> allPosts = this.postService.getAllPost();
		return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
	}


	// get post details by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {

		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);

	}

    // delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted !!", true);
	}

	// update post

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {

		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}
}



package com.programming.springblog.service;

import com.programming.springblog.dto.CommentDto;
import com.programming.springblog.dto.PostDto;
import com.programming.springblog.model.Post;

import java.util.List;


public interface PostService {

    //creat epost
    PostDto createPost(PostDto postDto,Integer id);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    void deletePost( Integer postId);
   
    //get all posts
    List<PostDto> getAllPost();

    //getsinglepost
    PostDto getPostById(Integer postId);

    //get post by user
    List<PostDto> getPostsByUser(Integer userId);

    //search posts
    List<Post> searchPosts(String keyword);

    CommentDto createComment(CommentDto commentDto, Integer postId);

	void deleteComment(Integer commentId);



    
}

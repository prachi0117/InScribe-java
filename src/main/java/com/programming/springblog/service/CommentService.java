package com.programming.springblog.service;

import com.programming.springblog.dto.CommentDto;


public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId);

	void deleteComment(Integer commentId);

	void saveComment(CommentDto commentDto);
    
}

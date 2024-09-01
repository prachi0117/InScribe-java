package com.programming.springblog.service;

import com.programming.springblog.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId);
    void deleteComment(Integer commentId);
}

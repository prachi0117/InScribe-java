package com.programming.springblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programming.springblog.model.Comment;
import com.programming.springblog.model.Post;

public interface CommentRepository  extends JpaRepository<Comment	, Integer> {
    List<Comment> findByPost(Post post);
}
package com.programming.springblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.programming.springblog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}

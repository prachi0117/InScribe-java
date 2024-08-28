package com.programming.springblog.repository;

import com.programming.springblog.model.Post;
import com.programming.springblog.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
}
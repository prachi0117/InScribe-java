package com.programming.springblog.dto;


import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.programming.springblog.model.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PostDto {
    private Long id;
    private String content;
    private String title;
    private String username;

    private String imageUrl;
	private List<CommentDto> comments;

   


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
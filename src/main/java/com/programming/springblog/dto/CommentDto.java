package com.programming.springblog.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CommentDto {
    public CommentDto() {
        this.id = id;
        this.content = content;
        this.username = username;
        this.postId = postId;
        this.createdOn = createdOn;
    }
    private Integer id;
    private String content;
    private String username; // To hold the username of the comment's author
    private Long postId;
    private Instant createdOn;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public Instant getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}

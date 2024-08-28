package com.programming.springblog.dto;


import java.util.HashSet;
import java.util.Set;

import com.programming.springblog.model.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String content;
    private String title;
    private String username;
    
	private Set<CommentDto> comments=new HashSet<>();

    // public PostDto(String title, String content) {
    //     this.title = title;
    //     this.content = content;
    // }


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
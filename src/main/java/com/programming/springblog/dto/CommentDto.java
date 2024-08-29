package com.programming.springblog.dto;

import org.springframework.data.jpa.repository.Query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
	
	private int id;

	private String content;

	private int postId;
	private String username;

}

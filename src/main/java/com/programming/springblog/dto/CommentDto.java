package com.programming.springblog.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private String content;
    private String username; // To hold the username of the comment's author
    private Long postId;
    private Instant createdOn;
}

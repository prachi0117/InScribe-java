package com.programming.springblog.service;

import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.programming.springblog.dto.CommentDto;
import com.programming.springblog.exception.ResourceNotFoundException;
import com.programming.springblog.model.Comment;
import com.programming.springblog.model.Post;
import com.programming.springblog.model.User;
import com.programming.springblog.repository.CommentRepository;
import com.programming.springblog.repository.PostRepository;
import com.programming.springblog.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        // Retrieve the post by ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        // Map CommentDto to Comment entity
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        // Get the current username from the Security Context
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        
        // Retrieve the User object
        User currentUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        comment.setUser(currentUser);
        comment.setCreatedOn(Instant.now());

        // Save the comment to the repository
        Comment savedComment = commentRepository.save(comment);

        // Create a new CommentDto from the saved Comment and set the username
        CommentDto savedCommentDto = modelMapper.map(savedComment, CommentDto.class);
        savedCommentDto.setUsername(currentUser.getUserName());

        return savedCommentDto;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
        
        // Ensure the comment belongs to the logged-in user
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if (!comment.getUser().getUserName().equals(username)) {
            throw new RuntimeException("You do not have permission to delete this comment");
        }

        commentRepository.delete(comment);
    }
}

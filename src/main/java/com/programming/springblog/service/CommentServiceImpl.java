package com.programming.springblog.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.programming.springblog.model.Comment;
import com.programming.springblog.model.Post;
import com.programming.springblog.model.User;
import com.programming.springblog.exception.ResourceNotFoundException;
import com.programming.springblog.dto.CommentDto;
import com.programming.springblog.repository.CommentRepository;
import com.programming.springblog.repository.PostRepository;
import com.programming.springblog.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        
        Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        // Get current user's username from SecurityContext
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        comment.setUser(currentUser);

        Comment savedComment = commentRepository.save(comment);

        CommentDto savedCommentDto = modelMapper.map(savedComment, CommentDto.class);
        savedCommentDto.setUsername(currentUser.getUserName());

        return savedCommentDto;
    }
   
    
    
    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
        this.commentRepository.delete(comment);
    }

    @Override
    public void saveComment(CommentDto commentDto) {
        // Convert CommentDto to Comment entity
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        
        // Assuming you have a method to find the Post by ID
        Post post = postRepository.findById(commentDto.getPostId())
            .orElseThrow(() -> new RuntimeException("Post not found"));

        // Assuming you have a method to find the User by username
        User user = userRepository.findByUserName(commentDto.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        comment.setPost(post);
        comment.setUser(user);

        // Save the comment
        commentRepository.save(comment);
    }

}

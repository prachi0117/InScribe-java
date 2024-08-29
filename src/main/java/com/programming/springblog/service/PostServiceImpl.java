package com.programming.springblog.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.programming.springblog.model.Comment;
import com.programming.springblog.model.Post;
import com.programming.springblog.model.User;
import com.programming.springblog.dto.CommentDto;
import com.programming.springblog.dto.PostDto;
import com.programming.springblog.exception.ResourceNotFoundException;
import com.programming.springblog.repository.CommentRepository;
import com.programming.springblog.repository.PostRepository;
import com.programming.springblog.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));

       
        Post post = this.modelMapper.map(postDto, Post.class);
       
        post.setUsername(user);
        

        Post newPost = this.postRepository.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }


    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
       
        
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));


        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
       

        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);

    }


    @Override
    public void deletePost(Integer postId) {
        
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        this.postRepository.delete(post);


    }


    @Override
    public List<PostDto> getAllPost() {

        List<Post> allPosts = this.postRepository.findAll();
        List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());


        return postDtos;
    }

    
    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }
    

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
       
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ", "userId ", userId));
        List<Post> posts = this.postRepository.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }


    @Override
    public List<Post> searchPosts(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchPosts'");
    }

    @Override
public CommentDto createComment(CommentDto commentDto, Integer postId) {
    // Retrieve the post by ID
    Post post = this.postRepository.findById(postId)
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

    // Save the comment to the repository
    Comment savedComment = this.commentRepository.save(comment);

    // Create a new CommentDto from the saved Comment and set the username
    CommentDto savedCommentDto = modelMapper.map(savedComment, CommentDto.class);
    savedCommentDto.setUsername(currentUser.getUserName());

    // Debugging output
    System.out.println("Comment created by: " + savedCommentDto.getUsername());

    return savedCommentDto;
}

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
        this.commentRepository.delete(comment);
    }

}
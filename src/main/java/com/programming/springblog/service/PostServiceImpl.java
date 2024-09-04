package com.programming.springblog.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import org.jsoup.Jsoup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

    @Autowired
    public PostServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Configure ModelMapper to map the username in PostDto
        this.modelMapper.typeMap(Post.class, PostDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUserName(), PostDto::setUsername);
        });

        // Configure ModelMapper to map the username in CommentDto
        this.modelMapper.typeMap(Comment.class, CommentDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUser().getUserName(), CommentDto::setUsername);
        });
    }

    @Override
    public PostDto createPost(PostDto postDto, Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
    
        // Map PostDto to Post entity
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setUser(user);
    
        // Extract the first image URL from the content and set it
        String imageUrl = extractFirstImageUrl(post.getContent());
        post.setImageUrl(imageUrl);
    
        // Save the Post entity
        Post newPost = this.postRepository.save(post);
    
        // Convert the saved post to PostDto
        PostDto createdPostDto = this.modelMapper.map(newPost, PostDto.class);
        createdPostDto.setUsername(user.getUserName());
    
        // Set the image URL in PostDto as well
        createdPostDto.setImageUrl(imageUrl);
    
        return createdPostDto;
    }
    

    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);
                    postDto.setUsername(post.getUser().getUserName());

                    String imageUrl = extractFirstImageUrl(post.getContent());
                    postDto.setImageUrl(imageUrl);

                    return postDto;
                })
                .collect(Collectors.toList());
    }

    private String extractFirstImageUrl(String content) {
        if (content != null && content.contains("<img")) {
            // Parse the HTML content to extract the first <img> tag
            Document doc = Jsoup.parse(content);
            Element img = doc.selectFirst("img"); // Select the first <img> tag
    
            if (img != null) {
                String imageUrl = img.attr("src");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    return imageUrl;
                }
            }
        }
        return "/images/spotlight2.jpg"; // Return a default image URL if none found
    }
    

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        this.postRepository.delete(post);
    }

   

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Post> posts = this.postRepository.findByUser(user);

        return posts.stream()
                .map(post -> {
                    PostDto postDto = modelMapper.map(post, PostDto.class);
                    postDto.setUsername(post.getUser().getUserName());
                    
                    return postDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        postDto.setUsername(post.getUser().getUserName());

        // Extract the first image URL from the content
        String imageUrl = extractFirstImageUrl(post.getContent());
        postDto.setImageUrl(imageUrl);

        // Set usernames for comments
        postDto.setComments(post.getComments().stream()
            .map(comment -> {
                CommentDto commentDto = this.modelMapper.map(comment, CommentDto.class);
                commentDto.setUsername(comment.getUser().getUserName());
                return commentDto;
            })
            .collect(Collectors.toList()));

        return postDto;
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchPosts'");
    }

}

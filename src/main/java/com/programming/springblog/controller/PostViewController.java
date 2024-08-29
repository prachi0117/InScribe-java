package com.programming.springblog.controller;

import com.programming.springblog.dto.CommentDto;
import com.programming.springblog.dto.PostDto;
import com.programming.springblog.exception.ResourceNotFoundException;
import com.programming.springblog.model.User;
import com.programming.springblog.repository.UserRepository;
import com.programming.springblog.service.CommentService;
import com.programming.springblog.service.PostService;

import java.security.Principal;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/posts")
public class PostViewController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    
    @Autowired
    private CommentService commentService;

    // Display form to create a new post create new post
    @GetMapping("/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new PostDto());
        return "postform"; // returns the form view to create a new post
    }
   

    @PostMapping("/new")
    public String createPost(@RequestParam Map<String, String> formData, Principal principal) {
        Integer userId = getUserIdFromPrincipal(principal); // Get user ID from security context
        
        // Create a new PostDto manually from form data
        PostDto postDto = new PostDto();
        postDto.setTitle(formData.get("title"));
        postDto.setContent(formData.get("content"));
        
        PostDto createdPost = postService.createPost(postDto, userId);
        return "redirect:/posts/" + createdPost.getId(); // Redirects to the post's detail page
    }

    @PostMapping("/{postId}/comment")
    public String submitComment(@PathVariable("postId") Integer postId,
    @RequestParam("content") String content, 
    @RequestParam("username") String username) {
    // Handle saving the comment
    CommentDto commentDto = new CommentDto();
    commentDto.setContent(content);
    commentDto.setUsername(username);
    commentDto.setPostId(postId);  // Ensure the postId is set

    // Assuming you have a service to save the comment
    commentService.saveComment(commentDto);

    // Redirect back to the post's detail page
    return "redirect:/posts/" + postId;
    }
    
    private Integer getUserIdFromPrincipal(Principal principal) {
        // Assuming the principal name is the username (email, etc.)
        String username = principal.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user.getId(); // Return the user ID
    }
    


    // Display all posts
    @GetMapping("/all")
    public String viewAllPosts(Model model) {
        List<PostDto> posts = postService.getAllPost();
        model.addAttribute("posts", posts);
        return "getallpost"; // The name of the Thymeleaf template for displaying posts
    }
    

    // Display a single post by its ID
    @GetMapping("/{postId}")
    public String showPostById(@PathVariable("postId") Integer postId, Model model) {
        // Fetch the post details using the service
        PostDto postDto = postService.getPostById(postId);
        postDto.setContent(Utility.stripHtmlTags(postDto.getContent()));
        // Add the post details to the model
        model.addAttribute("post", postDto);

        // Return the view name to render the post details
        return "fragments/posts"; // Ensure 'post.html' exists in your templates folder
    }



    // Display form to update a post
    @GetMapping("/{postId}/edit")
    public String showEditPostForm(@PathVariable("postId") Integer postId, Model model) {
        PostDto postDto = postService.getPostById(postId);
        model.addAttribute("post", postDto);
        return "postform"; // returns the form view to edit a post
    }

    // Handle form submission to update a post
    @PostMapping("/{postId}/edit")
    public String updatePost(@PathVariable("postId") Integer postId, @ModelAttribute("post") PostDto postDto) {
        postService.updatePost(postDto, postId);
        return "redirect:/posts/" + postId; // redirects to the updated post's detail page
    }

    // Handle post deletion
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") Integer postId) {
        postService.deletePost(postId);
        return "redirect:/posts/all"; // redirects to the list of all posts after deletion
    }


}

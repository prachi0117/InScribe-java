package com.programming.springblog.controller;

import com.programming.springblog.dto.CommentDto;
import com.programming.springblog.dto.PostDto;
import com.programming.springblog.exception.ResourceNotFoundException;
import com.programming.springblog.model.Post;
import com.programming.springblog.model.User;
import com.programming.springblog.repository.PostRepository;
import com.programming.springblog.repository.UserRepository;
import com.programming.springblog.service.CommentService;
import com.programming.springblog.service.PostService;


import java.security.Principal;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.List;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostViewController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentService commentService;


   
    // Display form to create a new post
    @GetMapping("/new")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new PostDto());
        return "postform"; // returns the form view to create a new post
    }

    @PostMapping("/new")
    public String createPost(@RequestParam Map<String, String> formData, Principal principal) {
        Integer userId = getUserIdFromPrincipal(principal);
    
        // Create a new PostDto manually from form data
        PostDto postDto = new PostDto();
        postDto.setTitle(formData.get("title"));
    
        // Extract content and image URL
        String content = formData.get("content");
        String imageUrl = extractImageUrl(content);
        postDto.setImageUrl(imageUrl);
        
        String cleanedContent = cleanContent(content, postDto);
        postDto.setContent(cleanedContent);
    
        // Save the post
        PostDto createdPost = postService.createPost(postDto, userId);
        return "redirect:/posts/" + createdPost.getId();
    }


    private String extractImageUrl(String htmlContent) {
    Document document = Jsoup.parse(htmlContent);
    Element imgElement = document.selectFirst("img");
    if (imgElement != null) {
        return imgElement.attr("src");
    }
    return null;
}

private String cleanContent(String htmlContent, PostDto postDto) {
    Document document = Jsoup.parse(htmlContent);
    Elements imgs = document.select("img");
    
    // Keep only image tags, remove everything else
    document.body().children().remove(); // Remove all child elements
    document.body().appendChildren(imgs); // Add back only the images

    return document.body().html(); // Return only the cleaned content
}
private String cleanAllContent(String htmlContent, PostDto postDto) {
    Document document = Jsoup.parse(htmlContent);
    document.select("img").remove();
    return document.body().html();
}
    private Integer getUserIdFromPrincipal(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user.getId(); // Return the user ID
    }

    // Display all posts
    @GetMapping("/all")
    public String viewAllPosts(Model model) {
        List<PostDto> posts = postService.getAllPost();

        
        for (PostDto post : posts) {
            String cleanedContent = cleanAllContent(post.getContent(),post);
            post.setContent(cleanedContent);
        }
        
        
        model.addAttribute("posts", posts);
        return "getallpost"; // The name of the Thymeleaf template for displaying posts
    }

    
    // Display a single post by its ID
    @GetMapping("/{postId}")
    public String showPostById(@PathVariable("postId") Long postId, Model model) {
        // Fetch the post details using the service
        PostDto postDto = postService.getPostById(postId);
        postDto.setContent(Utility.stripHtmlTags(postDto.getContent()));

        // Initialize a new CommentDto for the comment form
        CommentDto commentDto = new CommentDto();
        commentDto.setPostId(postId); // Set the post ID for the comment

        // Add the post and commentDto to the model
        model.addAttribute("post", postDto);
        model.addAttribute("commentDto", commentDto);

        // Return the view name to render the post details
        return "fragments/posts"; // Ensure 'posts.html' exists in your templates folder
    }

    // Display form to update a post
    @GetMapping("/{postId}/edit")
    public String showEditPostForm(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = postService.getPostById(postId);
        model.addAttribute("post", postDto);
        return "postform"; // returns the form view to edit a post
    }

    // Handle form submission to update a post
    @PostMapping("/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long postId, @ModelAttribute("post") PostDto postDto) {
        postService.updatePost(postDto, postId);
        return "redirect:/posts/" + postId; // redirects to the updated post's detail page
    }

    // Handle post deletion
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return "redirect:/posts/all"; // redirects to the list of all posts after deletion
    }

    @GetMapping("/{postId}/delete")
    public String deletePostGet(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return "redirect:/posts/all"; // Redirect to the list of all posts after deletion
    }

    @GetMapping("/posts/user/{userId}")
    public String viewPostsByUser(@PathVariable("userId") Integer userId, Model model) {
        // Fetch posts by user from the service
        List<PostDto> posts = postService.getPostsByUser(userId);
    
        // Clean the content of each post to remove divs with images (if needed)
        for (PostDto post : posts) {
            String cleanedContent = cleanContent(post.getContent(), post);
            post.setContent(cleanedContent);
        }
    
        // Add the posts to the model
        model.addAttribute("posts", posts);
    
        // Return the name of the Thymeleaf template for displaying the user's posts
        return "getallpost";
    }
    

    // Handle comment submission
    @PostMapping("/{postId}/comment")
    public String addComment(@PathVariable("postId") Long postId, @ModelAttribute("commentDto") CommentDto commentDto, Principal principal) {
        Integer userId = getUserIdFromPrincipal(principal); // Get user ID from security context
        commentDto.setId(userId); // Set the user ID for the comment
        commentDto.setPostId(postId); // Ensure the comment is linked to the post
        
        commentService.createComment(commentDto, postId); // Call the service to add the comment
        return "redirect:/posts/" + postId; // Redirect back to the post's detail page
    }
}

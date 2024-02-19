package com.dianawu.blogApp.controller;

import com.dianawu.blogApp.dto.CommentDto;
import com.dianawu.blogApp.dto.PostDto;
import com.dianawu.blogApp.entity.Comment;
import com.dianawu.blogApp.service.CommentService;
import com.dianawu.blogApp.service.PostService;
import com.dianawu.blogApp.util.ROLE;
import com.dianawu.blogApp.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private PostService postService; // use interface to decouple the implementation
    private CommentService commentService;

    @Autowired
    public PostController(PostService postService, CommentService commentService) {

        this.postService = postService;
        this.commentService = commentService;

    }

    // create handler methods, GET request and return a model and view
    @GetMapping("/admin/posts")
    public String posts(Model model) {

        String role = SecurityUtils.getRole();
        List<PostDto> posts = null;
        if (ROLE.ROLE_ADMIN.name().equals(role)) {
            posts = postService.findAllPosts();
        }else {
            posts = postService.findPostsByUser();
        }

        model.addAttribute("posts", posts);
        return "/admin/posts"; // return the view name (thyemleaf template)
    }

    // New Post
    @GetMapping("/admin/posts/newpost")
    public String newPost(Model model) {
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "/admin/create_post";
    }

    @PostMapping("/admin/posts")
    public String createPost(@Valid @ModelAttribute ("post") PostDto postDto,
                             BindingResult result,
                             Model model) {  //binds the form data to the postDto object when a POST request is made to /admin/posts.
        if (result.hasErrors()) {
            model.addAttribute("post", postDto);
            return "/admin/create_post";
        }

        postDto.setUrl(getUrl(postDto.getTitle()));
        postService.createPost(postDto);
        return "redirect:/admin/posts";
    }

    // Edit Post
    @GetMapping("/admin/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId")Long postId,
                               Model model) {
        PostDto postDto = postService.findPostById(postId);
        model.addAttribute("post", postDto);
        return "admin/edit_post";
    }
    @PostMapping("/admin/posts/{postId}")
    public String updatePost(@PathVariable("postId") Long postId,
                             @Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("post", postDto);
            return "admin/edit_post";
        }
        postDto.setId(postId);
        postService.updatePost(postDto);
        return "redirect:/admin/posts";
    }

    // Delete Post
    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    // View Post
    @GetMapping("/admin/posts/{postUrl}/view")
    public String viewPost(@PathVariable("postUrl") String postUrl,
                           Model model) {
        PostDto postDto = postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);
        return "admin/view_post";

    }

    // Search Post
    @GetMapping("/admin/posts/search") // GET request to /admin/posts/search?query=keyword
    public String searchPost(@RequestParam(value = "query") String query,
                             Model model) {
        List<PostDto> posts = postService.searchPosts(query);
        model.addAttribute("posts", posts);

        return "admin/posts";


    }

    // List Comments
    @GetMapping("/admin/posts/comments")
    public String postComments(Model model){
        String role = SecurityUtils.getRole();
        List<CommentDto> comments = null;
        if (ROLE.ROLE_ADMIN.name().equals(role)){
            comments = commentService.findAllComments();
        }else {
            comments = commentService.findCommentsByPost();
        }
        
        model.addAttribute("comments", comments);
        return "admin/comments";
    }

    // Delete Comment
    @GetMapping("/admin/posts/comments/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return "redirect:/admin/posts/comments";
    }


    // other methods
    private static String getUrl(String postTitle){
        String title = postTitle.trim().toLowerCase();
        String url = title.replaceAll("\\s+", "-"); //Regular expression matches one or more whitespace characters
        url = url.replaceAll("[^a-zA-Z0-9-]", ""); //Regular expression matches any character that is not a letter or a digit
        return url;
    }

}

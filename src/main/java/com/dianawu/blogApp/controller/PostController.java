package com.dianawu.blogApp.controller;

import com.dianawu.blogApp.dto.PostDto;
import com.dianawu.blogApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PostController {

    private PostService postService; // use interface to decouple the implementation

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create handler methods, GET request and return a model and view
    @GetMapping("/admin/posts")
    public String posts(Model model) {

        List<PostDto> posts = postService.findAllPosts();

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

}

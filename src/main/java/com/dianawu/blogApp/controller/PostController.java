package com.dianawu.blogApp.controller;

import com.dianawu.blogApp.dto.PostDto;
import com.dianawu.blogApp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    // other methods
    private static String getUrl(String postTitle){
        String title = postTitle.trim().toLowerCase();
        String url = title.replaceAll("\\s+", "-"); //Regular expression matches one or more whitespace characters
        url = url.replaceAll("[^a-zA-Z0-9-]", ""); //Regular expression matches any character that is not a letter or a digit
        return url;
    }

}

package com.dianawu.blogApp.controller;

import com.dianawu.blogApp.dto.CommentDto;
import com.dianawu.blogApp.dto.PostDto;
import com.dianawu.blogApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {
    private PostService postService;

    @Autowired
    public BlogController(PostService postService) {
        this.postService = postService;
    }

    // view blog posts http://localhost:8080/
    @GetMapping("/")
    public String viewBlogPosts(Model model) {
        List<PostDto> postsResponse= postService.findAllPosts();
        model.addAttribute("postsResponse", postsResponse);
        return "blog/view_posts";
    }

    // view post request http://localhost:8080/post/{postUrl}
    @GetMapping("/post/{postUrl}")
    private String showPost(@PathVariable String postUrl,
                            Model model) {
        PostDto post = postService.findPostByUrl(postUrl);
        CommentDto commentDto = new CommentDto();
        List<PostDto> recommendedPosts = postService.recommendedPostsByUser(post.getCreatedBy().getId());

        model.addAttribute("post", post);
        model.addAttribute("comment", commentDto);
        model.addAttribute("recommendedPosts", recommendedPosts);

        return "blog/blog_post";
    }

    // blog search request http://localhost:8080/page/search?query={query}
    @GetMapping("/page/search")
    public String searchPost(@RequestParam(value ="query" )String query,
                             Model model) {
        List<PostDto> postsResponse = postService.searchPosts(query);
        model.addAttribute("postsResponse", postsResponse);

        return "blog/view_posts";

    }


}

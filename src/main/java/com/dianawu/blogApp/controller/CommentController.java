package com.dianawu.blogApp.controller;

import com.dianawu.blogApp.dto.CommentDto;
import com.dianawu.blogApp.dto.PostDto;
import com.dianawu.blogApp.service.CommentService;
import com.dianawu.blogApp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private CommentService commentService;
    private PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    // Create a comment
    @PostMapping("/{postUrl}/comments")
    public String createComment(@PathVariable("postUrl") String postUrl,
                                Model model,
                                @Valid @ModelAttribute("comment") CommentDto commentDto,
                                BindingResult result){ // @ModelAttribute binds the form data to the object ${comment}
        PostDto postDto = postService.findPostByUrl(postUrl);

        if(result.hasErrors()){
            model.addAttribute("post", postDto);
            model.addAttribute("comment", commentDto);
            return "blog/blog_post";
        }

        commentService.createComment(postUrl, commentDto);

        return "redirect:/post/"+postUrl; // redirect to the post controller /post/{postUrl}
    }



}

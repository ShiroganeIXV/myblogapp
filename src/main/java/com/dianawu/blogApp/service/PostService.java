package com.dianawu.blogApp.service;

import com.dianawu.blogApp.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> findAllPosts();

    void createPost(PostDto postDto);
}

package com.dianawu.blogApp.service;

import com.dianawu.blogApp.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> findPostsByUser();

    List<PostDto> findAllPosts();

    void createPost(PostDto postDto);

    PostDto findPostById(Long postId);

    void updatePost(PostDto postDto);

    void deletePost(Long postId);

    PostDto findPostByUrl(String postUrl);

    List<PostDto> searchPosts(String query);

    List<PostDto> recommendedPostsByUser(Long userId);
}

package com.dianawu.blogApp.service.Impl;

import com.dianawu.blogApp.dto.PostDto;
import com.dianawu.blogApp.entity.Post;
import com.dianawu.blogApp.mapper.PostMapper;
import com.dianawu.blogApp.repository.PostRepository;
import com.dianawu.blogApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostMapper::mapToDto) //.map((post) -> PostMapper.mapToDto(post)) -- lambda expression
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(PostDto postDto) {
        Post post = PostMapper.mapToEntity(postDto);
        postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return PostMapper.mapToDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = PostMapper.mapToEntity(postDto);
        postRepository.save(post);
    }


}

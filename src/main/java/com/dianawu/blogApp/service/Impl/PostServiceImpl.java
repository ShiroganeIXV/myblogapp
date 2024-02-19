package com.dianawu.blogApp.service.Impl;

import com.dianawu.blogApp.dto.PostDto;
import com.dianawu.blogApp.entity.Post;
import com.dianawu.blogApp.entity.User;
import com.dianawu.blogApp.mapper.PostMapper;
import com.dianawu.blogApp.repository.PostRepository;
import com.dianawu.blogApp.repository.UserRepository;
import com.dianawu.blogApp.service.PostService;
import com.dianawu.blogApp.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository,UserRepository userRepository) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PostDto> findPostsByUser() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Long userId = createdBy.getId();
       List<Post> posts = postRepository.findPostsByUser(userId);
        return posts.stream()
                .map(PostMapper::mapToDto)
                .collect(Collectors.toList());
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
        String email = SecurityUtils.getCurrentUser().getUsername();
        User user = userRepository.findByEmail(email);
        Post post = PostMapper.mapToEntity(postDto);
        post.setCreatedBy(user);
        postRepository.save(post);
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return PostMapper.mapToDto(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Post post = PostMapper.mapToEntity(postDto);
        post.setCreatedBy(createdBy);
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);

    }

    @Override
    public PostDto findPostByUrl(String postUrl) {
        Post post = postRepository.findByUrl(postUrl).get();
        return PostMapper.mapToDto(post);
    }

    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.searchPost(query);
        return posts.stream()
                .map(PostMapper::mapToDto)
                .collect(Collectors.toList());
    }


}
